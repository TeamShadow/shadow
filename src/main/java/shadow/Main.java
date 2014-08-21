/**
 *
 */
package shadow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;

import shadow.output.llvm.Array;
import shadow.output.llvm.Generic;
import shadow.output.llvm.LLVMOutput;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACBuilder;
import shadow.tac.TACModule;
import shadow.typecheck.TypeChecker;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;


/**
 * @author Bill Speirs
 * @author Barry Wittman
 * @author Jacob Young
 */
public class Main {
	/*
	 * These are the error codes returned by the compiler
	 */
	public static final int NO_ERROR				=  0;
	public static final int FILE_NOT_FOUND_ERROR	= -1;
	public static final int PARSE_ERROR				= -2;
	public static final int TYPE_CHECK_ERROR		= -3;
	public static final int TAC_ERROR				= -4;
	public static final int COMPILE_ERROR	 		= -5;
	public static final int COMMAND_LINE_ERROR		= -6;
	public static final int CONFIGURATION_ERROR		= -7;

	private static final Logger logger = Loggers.SHADOW;

	/**
	 * This is the starting point of the compiler.
	 *
	 * @param args Command line arguments to control the compiler
	 */
	public static void main(String[] args)
	{
		try
		{
			run(args);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("FILE NOT FOUND: " + e.getLocalizedMessage());
			System.exit(FILE_NOT_FOUND_ERROR);
		}
		catch(ParseException e)
		{
			System.err.println("PARSE ERROR: " + e.getLocalizedMessage());
			System.exit(PARSE_ERROR);
		}
		catch (ShadowException e)
		{
			System.err.println("ERROR IN FILE: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(TYPE_CHECK_ERROR);
		}
		catch (IOException e)
		{
			System.err.println("FILE DEPENDENCY ERROR: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(TYPE_CHECK_ERROR);
		}
		catch (org.apache.commons.cli.ParseException e)
		{
			System.err.println("COMMAND LINE ERROR: " + e.getLocalizedMessage());
			System.exit(COMMAND_LINE_ERROR);
		}
		catch (ConfigurationException e) {
			System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
			System.exit(CONFIGURATION_ERROR);
		}
		catch (TypeCheckException e)
		{
			System.err.println("TYPE CHECK ERROR: " + e.getLocalizedMessage());
			System.exit(TYPE_CHECK_ERROR);
		}
		catch (CompileException e)
		{
			System.err.println("COMPILATION ERROR: " + e.getLocalizedMessage());
			System.exit(COMPILE_ERROR);
		}
	}

	private static String getPath( File directory, String name ) throws IOException
	{
		File file = new File( directory, name + ".shadow" );
		String path = file.getCanonicalPath();
		path = path.substring(0, path.lastIndexOf(".")); //strip extension
		return path;
	}

	public static void run(String[] args) throws  FileNotFoundException, ParseException, ShadowException, IOException, org.apache.commons.cli.ParseException, ConfigurationException, TypeCheckException, CompileException
	{
		Configuration config = Configuration.getInstance();

		// create our command-line options
		Options options = Configuration.createCommandLineOptions();
		CommandLineParser cliParser = new PosixParser();
		CommandLine commandLine = cliParser.parse(options, args);

		// see if we should print the help
		if(commandLine.hasOption("h"))
		{
			new HelpFormatter().printHelp("shadow", options);
			return;
		}

		// parse out the command line
		// throws exceptions if there are problems
		config.parse(commandLine);

		File system = config.getSystemImport();

		TypeChecker checker = new TypeChecker(false);
		TACBuilder tacBuilder = new TACBuilder();
		List<String> linkCommand = new ArrayList<String>();
		linkCommand.add("llvm-link");
		linkCommand.add("-");
		String unwindFile = new File( system, "shadow" + File.separator + "Unwind" + config.getArch() + ".ll" ).getCanonicalPath();
		linkCommand.add( unwindFile);
		String OSFile = new File( system, "shadow" + File.separator + config.getOs() + ".ll" ).getCanonicalPath();
		linkCommand.add( OSFile);		
		
		TreeSet<String> files;
		HashSet<String> checkedFiles = new HashSet<String>();

		// loop through the source files, compiling them
		while(config.hasNext())
		{
			File mainFile = config.next();
			files = null;
			HashSet<Generic> generics = new HashSet<Generic>();
			HashSet<Array> arrays = new HashSet<Array>();
			String mainClass = null;
			boolean mainArguments = false;
			
			do
			{
				File currentFile;
				String currentPath;
				
				if( files == null )
				{
					currentFile = mainFile;
					currentPath = mainFile.getCanonicalPath();
					//strip extension
					currentPath = currentPath.substring(0, currentPath.lastIndexOf("."));
					files = new TreeSet<String>();
					files.add(currentPath);

					if( !config.isCheckOnly() )
					{
						File standard = new File( system, "shadow" + File.separator + "standard" );
						File io = new File( system, "shadow" + File.separator + "io" );

						//minimum standard files needed for compilation
						files.add( getPath( standard, "Array" ));
						files.add( getPath( standard, "AddressMap" ));
						files.add( getPath( standard, "ArrayClass" ));
						files.add( getPath( standard, "Class" ));						
						files.add( getPath( standard, "Exception" ));
						files.add( getPath( standard, "GenericClass" ));
						files.add( getPath( standard, "Iterator" ));
						//files.add( getPath( standard, "MethodClass" ));
						files.add( getPath( standard, "Object" ));
						files.add( getPath( standard, "String" ));
						files.add( getPath( standard, "System" ));
						files.add( getPath( standard, "OutOfMemoryException" ));
						files.add( getPath( standard, "CastException" ));
						files.add( getPath( standard, "IndexOutOfBoundsException" ));

						files.add( getPath( io, "Console" ));
						files.add( getPath( io, "File" ));
						files.add( getPath( io, "IOException" ));
						files.add( getPath( io, "Path" ));
					}
				}
				else
				{
					currentPath = files.first();
					currentFile = new File(currentPath + ".shadow");
				}

				logger.info("Compiling " + currentFile.getName());

				// get the start time for the compile
				long startTime = System.currentTimeMillis();

				// type check the AST
				Node node = null;

				try
				{
					node = checker.typeCheck(currentFile);
					//get all the other needed files
					if( !config.isCheckOnly() )
						checker.addFileDependencies(node.getType(), files, checkedFiles);
				}
				catch( TypeCheckException e )
				{
					logger.error(currentFile.getPath() + " FAILED TO TYPE CHECK");
					throw e;
				}

				if(config.isCheckOnly()) // we are only parsing & type checking
				{
					long stopTime = System.currentTimeMillis();
					logger.info("FILE " + currentFile.getPath() + " CHECKED IN " + (stopTime - startTime) + "ms");
				}
				else
				{
					for(TACModule module : tacBuilder.build(node))
					{
						if (mainClass == null)
						{
							Type type = module.getType();
							mainClass = type.getMangledName();
							
							SequenceType arguments = new SequenceType(new ArrayType(Type.STRING));							
							if( type.getMatchingMethod("main", arguments) != null )
								mainArguments = true;
							else if( type.getMatchingMethod("main", new SequenceType()) != null )
								mainArguments = false;
							else
								throw new ShadowException("File " + currentFile.getName() + " does not contain an appropriate main() method");							
						}
						//Debug prints
						logger.debug(module.toString());
						//System.out.println(module);

						// build the LLVM
						// LLVMOutput(true).build(module);

						// verify the LLVM
						//new LLVMOutput(false).build(module);

						// write to file
						String name = module.getName().replace(':', '$');
						File llvmFile = new File(currentFile.getParent(), name + ".ll");
						LLVMOutput output = new LLVMOutput(llvmFile);
						output.build(module);
						
						generics.addAll(output.getGenerics());						
						arrays.addAll(output.getArrays());
						
						if (llvmFile.exists())
							linkCommand.add(llvmFile.getCanonicalPath());
						File nativeFile = new File(currentFile.getParent(), name + ".native.ll");
						if (nativeFile.exists())
							linkCommand.add(nativeFile.getCanonicalPath());
					}

					long stopTime = System.currentTimeMillis();

					logger.info("COMPILED " + currentFile.getPath() + " in " + (stopTime - startTime) + "ms");
				}					
				
				files.remove( currentPath );
				checkedFiles.add( currentPath );

				//after all LLVM generated, make a special generics file
				if( !config.isCheckOnly() && files.isEmpty() )
				{
					File genericsFile = new File( mainFile.getParent(), mainFile.getName().replace(".shadow", ".generics.shadow"));
					LLVMOutput interfaceOutput = new LLVMOutput( genericsFile );
					interfaceOutput.setGenerics(generics, arrays);
					interfaceOutput.buildGenerics();

					linkCommand.add(interfaceOutput.getFile().getCanonicalPath());
				}
				
				Type.clearTypes();		
				
			} while( !files.isEmpty() );			
			
			if (!config.isCheckOnly() && !config.isNoLink())
			{
				// any output after this point is important, avoid getting it mixed in with previous output
				System.out.println();
				System.out.flush();
				try { Thread.sleep(250); }
				catch (InterruptedException ex) { }
				
				String target;
				List<String> assembleCommand = new ArrayList<String>();
				assembleCommand.add("gcc");
				//assembleCommand.add("-g");
				assembleCommand.add("-x");
				assembleCommand.add("assembler");
				assembleCommand.add("-");
				if (config.getOs().equals("Linux")) {
					target = "x86_64-gnu-linux";
					assembleCommand.add("-lm");
					assembleCommand.add("-lrt");
				}
				else if( config.getArch() == 32 )
				{			 
					//target = "i686-w64-mingw32";
					target = "i386-unknown-mingw32";
					//assembleCommand.set(0, System.getProperty("user.home") + "/.wine/drive_c/MinGW/bin/gcc.exe");
				}
				else 
				{
					//target = "i686-w64-mingw32";
					target = "x86_64-w64-mingw32";
				}
				
				//assembleCommand.add("-m" + config.getArch());
				
				/*//old
				 else if (System.getProperty("os.name").equals("Linux")) {			 
					target = "i686-w64-mingw32";
					assembleCommand.set(0, System.getProperty("user.home") + "/.wine/drive_c/MinGW/bin/gcc.exe");
				} else {
					target = "i386-unknown-mingw32";
				}
				*/
				
				if( config.hasOutput() )
				{
					assembleCommand.add("-o");
					assembleCommand.add(config.getOutput().getPath());
				}
				
				BufferedReader main;
				
				if( mainArguments )
					main = new BufferedReader(new FileReader( new File( system, "shadow" + File.separator + "Main.ll")));
				else
					main = new BufferedReader(new FileReader( new File( system, "shadow" + File.separator + "NoArguments.ll")));
				Process link = new ProcessBuilder(linkCommand).redirectError(Redirect.INHERIT).start();
				Process optimize = new ProcessBuilder("opt", "-mtriple", target, "-O3").redirectError(Redirect.INHERIT).start();
				Process compile = new ProcessBuilder("llc", "-mtriple", target, "-O3")./*redirectOutput(new File("a.s")).*/redirectError(Redirect.INHERIT).start();
				Process assemble = new ProcessBuilder(assembleCommand).redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();
				try {
					new Pipe(link.getInputStream(), optimize.getOutputStream()).start();
					new Pipe(optimize.getInputStream(), compile.getOutputStream()).start();
					new Pipe(compile.getInputStream(), assemble.getOutputStream()).start();
					String line = main.readLine();
					
					//File replaced = new File("ReplacedMain.ll");
					//FileOutputStream stream = new FileOutputStream(replaced);
					
					while (line != null) {
						line = line.replace("_Pshadow_Ptest_CTest", mainClass) + System.getProperty("line.separator");
						link.getOutputStream().write(line.getBytes());
						//stream.write(line.getBytes());
						line = main.readLine();
					}
					
					//stream.close();
					try {
						main.close();
					} catch (IOException ex) { }
					try {
						link.getOutputStream().flush();
					} catch (IOException ex) { }
					try {
						link.getOutputStream().close();
					} catch (IOException ex) { }
					if (link.waitFor() != 0)
						throw new CompileException("FAILED TO LINK");
					if (optimize.waitFor() != 0)
						throw new CompileException("FAILED TO OPTIMIZE");
					if (compile.waitFor() != 0)
						throw new CompileException("FAILED TO COMPILE");
					if (assemble.waitFor() != 0)
						throw new CompileException("FAILED TO ASSEMBLE");
				} catch (InterruptedException ex) {
				} finally {
					link.destroy();
					optimize.destroy();
					compile.destroy();
					assemble.destroy();
				}
				logger.info("SUCCESS");
			}
		}
	}

	private static class Pipe extends Thread {
		private InputStream input;
		private OutputStream output;
		public Pipe(InputStream inputStream, OutputStream outputStream) {
			input = inputStream;
			output = outputStream;
		}
		@Override
		public void run() {
			try {
				try {
					byte[] buffer = new byte[1024];
					int read = input.read(buffer);
					while (read >= 0) {
						output.write(buffer, 0, read);
						read = input.read(buffer);
					}
				} finally {
					try {
						input.close();
					} catch (IOException ex) { }
					try {
						output.flush();
					} catch (IOException ex) { }
					try {
						output.close();
					} catch (IOException ex) { }
				}
			} catch (IOException ex) { }
		}
	}
}
