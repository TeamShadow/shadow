package shadow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	// These are the error codes returned by the compiler
	public static final int NO_ERROR				=  0;
	public static final int FILE_NOT_FOUND_ERROR	= -1;
	public static final int PARSE_ERROR				= -2;
	public static final int TYPE_CHECK_ERROR		= -3;
	public static final int TAC_ERROR				= -4;
	public static final int COMPILE_ERROR	 		= -5;
	public static final int COMMAND_LINE_ERROR		= -6;
	public static final int CONFIGURATION_ERROR		= -7;

	private static final Logger logger = Loggers.SHADOW;
	private static final Configuration config = Configuration.getInstance();
	
	// Metadata related to a Shadow program's main class
	private static String mainClass;
	private static boolean mainArguments;
	
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
			printHelp();
			System.exit(COMMAND_LINE_ERROR);
		}
		catch (ConfigurationException e) {
			System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
			printHelp();
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
	
	public static void run(String[] args) throws  FileNotFoundException, ParseException, ShadowException, IOException, org.apache.commons.cli.ParseException, ConfigurationException, TypeCheckException, CompileException
	{		
		// Create our command-line options
		Options options = Configuration.createCommandLineOptions();
		CommandLineParser cliParser = new PosixParser();
		CommandLine commandLine = cliParser.parse(options, args);

		// Print help if there are no args or options, or if the 'h' option is present
		if ( commandLine.hasOption("h") )
		{
			printHelp();
			return;
		}

		// parse out the command line
		// throws exceptions if there are problems
		config.parse(commandLine);

		File system = config.getSystemImport();

		String unwindFile = new File( system, "shadow" + File.separator + "Unwind" + config.getArch() + ".ll" ).getCanonicalPath();
		String OSFile = new File( system, "shadow" + File.separator + config.getOs() + ".ll" ).getCanonicalPath();
		
		List<String> linkCommand = new ArrayList<String>();
		linkCommand.add("llvm-link");
		linkCommand.add("-");
		linkCommand.add(unwindFile);
		linkCommand.add(OSFile);

		// Begin the checking/compilation process
		long startTime = System.currentTimeMillis();
		
		generateLLVM(linkCommand, false);
		
		if (!config.isCheckOnly() && !config.isNoLink())
		{
			// any output after this point is important, avoid getting it mixed in with previous output
			System.out.println();
			System.out.flush();
			try { Thread.sleep(250); }
			catch (InterruptedException ex) { }
			
			// Get the appropriate LLVM target "triple"
			String target;
			if( config.hasTarget() )
				target = config.getTarget();
			else
				target = getTarget();
			
			logger.info("Building for target '" + target + "'");
			
			List<String> assembleCommand;
			
			if( config.hasLinkCommand() )
				assembleCommand = config.getLinkCommand();
			else {					
				assembleCommand = new ArrayList<String>();							
				assembleCommand.add("gcc");
				//assembleCommand.add("-g");
				assembleCommand.add("-x");
				assembleCommand.add("assembler");
				assembleCommand.add("-");					
				
				if (config.getOs().equals("Linux")) {
					assembleCommand.add("-lm");
					assembleCommand.add("-lrt");
				}
				
				//assembleCommand.add("-m" + config.getArch());	
			}
				
			if( config.hasOutput() )
			{
				assembleCommand.add("-o");
				assembleCommand.add(config.getOutput().getPath());
			}
			
			BufferedReader main;
			
			if ( mainArguments )
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
				
				while (line != null) {
					line = line.replace("_Pshadow_Ptest_CTest", mainClass) + System.getProperty("line.separator");
					link.getOutputStream().write(line.getBytes());
					line = main.readLine();
				}					

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
			
			logger.info("SUCCESS: Built in " + (System.currentTimeMillis() - startTime) + "ms");
		}
	}
	
	/* 
	 * Because no system is in place for reusing existing .ll files,
	 * forceRegenerate currently has no effect.
	 */
	/**
	 * Ensures that LLVM code exists for all dependencies of a main-method-
	 * containing class/file.
	 * 
	 * @param forceGenerate		Forces all .ll files to be newly generated
	 * @return					Important metadata about the main method
	 */
	private static void generateLLVM(List<String> linkCommand, boolean forceGenerate) throws IOException, ShadowException, ParseException, ConfigurationException, TypeCheckException {
		HashSet<String> files = new HashSet<String>();
		HashSet<String> checkedFiles = new HashSet<String>();
		
		HashSet<Generic> generics = new HashSet<Generic>();
		HashSet<Array> arrays = new HashSet<Array>();
		
		TypeChecker checker = new TypeChecker(false);
		TACBuilder tacBuilder = new TACBuilder();
		
		String mainFileName = stripExt(config.getMainFile().getCanonicalPath()); 
		files.add(mainFileName);
		

		// If compiling, add critical dependencies
		if( !config.isCheckOnly() ) {
			File system = config.getSystemImport();
			
			File standard = new File(system, "shadow" + File.separator + "standard");
			File io = new File(system, "shadow" + File.separator + "io");
			
			// Necessary standard files
			addShadowFile(standard, "Array", files);
			addShadowFile(standard, "AddressMap", files);
			addShadowFile(standard, "ArrayClass", files);
			addShadowFile(standard, "Class", files);
			addShadowFile(standard, "Exception", files);
			addShadowFile(standard, "GenericClass", files);
			addShadowFile(standard, "Iterator", files);
			addShadowFile(standard, "Object", files);
			addShadowFile(standard, "String", files);
			addShadowFile(standard, "System", files);
			addShadowFile(standard, "OutOfMemoryException", files);
			addShadowFile(standard, "CastException", files);
			addShadowFile(standard, "IndexOutOfBoundsException", files);
			addShadowFile(standard, "AssertException", files);
			
			// Necessary io files
			addShadowFile(io, "Console", files);
			addShadowFile(io, "File", files);
			addShadowFile(io, "IOException", files);
			addShadowFile(io, "Path", files);
		}
		
		// Begin generating .ll files
		while( !files.isEmpty() ) {
			String currentPath = files.iterator().next();
			File currentFile = new File(currentPath + ".shadow");
			 
			logger.info("Generating LLVM code for " + currentFile.getName());
		
			// Get the start time for the compile
			long startTime = System.currentTimeMillis();
		
			// Type check the AST
			Node node = null;
		
			try {
				node = checker.typeCheck(currentFile);
				
				// Get all the other needed files
				if( !config.isCheckOnly() )
					checker.addFileDependencies(node.getType(), files, checkedFiles);
			}
			catch( TypeCheckException e ) {
				logger.error(currentFile.getPath() + " FAILED TO TYPE CHECK");
				throw e;
			}
		
			if( config.isCheckOnly() ) { // we are only parsing & type checking
				long stopTime = System.currentTimeMillis();
				logger.info("FILE " + currentFile.getPath() + " TYPE CHECKED IN " + (stopTime - startTime) + "ms");
			}
			else {
				for( TACModule module : tacBuilder.build(node) ) {
					if( currentPath.equals(mainFileName) ) {
						Type type = module.getType();
						mainClass = type.getMangledName();
						
						SequenceType arguments = new SequenceType(new ArrayType(Type.STRING));							
						if( type.getMatchingMethod("main", arguments) != null )
							mainArguments= true;
						else if( type.getMatchingMethod("main", new SequenceType()) != null )
							mainArguments = false;
						else
							throw new ShadowException("File " + currentFile.getName() + " does not contain an appropriate main() method");							
					}
					// Debug prints
					logger.debug(module.toString());
		
					// Write to file
					String name = module.getName().replace(':', '$');
					File llvmFile = new File(currentFile.getParentFile(), name + ".ll");
					File nativeFile = new File(currentFile.getParentFile(), name + ".native.ll");
					LLVMOutput output = new LLVMOutput(llvmFile);
					output.build(module);
					
					generics.addAll(output.getGenerics());						
					arrays.addAll(output.getArrays());
					
					if( llvmFile.exists() )
						linkCommand.add(llvmFile.getCanonicalPath());
					
					if( nativeFile.exists() )
						linkCommand.add(nativeFile.getCanonicalPath());
				}
		
				long stopTime = System.currentTimeMillis();
		
				logger.info("Generated " + currentFile.getPath() + ".ll in " 
						+ (stopTime - startTime) + "ms");
			}					
			
			files.remove(currentPath);
			checkedFiles.add(currentPath);
			
			// After all LLVM is generated, make a special generics file
			if( !config.isCheckOnly() && files.isEmpty() ) {
				File genericsFile = new File(config.getMainFile().getParent(), 
						config.getMainFile().getName().replace(".shadow", ".generics.shadow"));
				LLVMOutput interfaceOutput = new LLVMOutput(genericsFile);
				interfaceOutput.setGenerics(generics, arrays);
				interfaceOutput.buildGenerics();
		
				linkCommand.add(interfaceOutput.getFile().getCanonicalPath());
			}
			
			Type.clearTypes();
		}
	}
	
	/** Returns the target platform to be used by the LLVM compiler */
	private static String getTarget() throws ConfigurationException, IOException {
		// Some reference available here:
		// http://llvm.org/docs/doxygen/html/Triple_8h_source.html
		
		// Calling 'llc --version' for current target information
		// Note: Most of the LLVM tools also have this option
		Process process = new ProcessBuilder("llc", "-version").redirectErrorStream(true).start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String versionOutput = "";
		String line = null;
		while( (line = reader.readLine()) != null ) {
		   versionOutput += line + "\n";
		}
		
		// Create the regular expression required to find the target "triple"
		Pattern pattern = Pattern.compile("(Default target:\\s)([\\w\\-]+)");
		Matcher matcher = pattern.matcher(versionOutput);
		
		if( matcher.find() ) {
			return matcher.group(2);
		}
		else {
			throw new ConfigurationException(
					"Unable to find target in 'llc --version' output:\n" 
					+ versionOutput);
		}
	}
	
	private static void addShadowFile(File fileDir, String fileName, Collection<String> files) throws IOException {
		File file = new File(fileDir, fileName + ".shadow");
		
		files.add(stripExt(file.getCanonicalPath()));
	}

	public static String stripExt(String filepath) {
		return filepath.substring(0, filepath.lastIndexOf("."));
	}
	
	private static void printHelp() {
		new HelpFormatter().printHelp("shadowc <mainSource.shadow> [-o <output>] [-c <config.xml>]", Configuration.createCommandLineOptions());
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
