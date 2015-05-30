package shadow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.cli.HelpFormatter;
import org.apache.logging.log4j.Logger;

import shadow.output.llvm.Array;
import shadow.output.llvm.Generic;
import shadow.output.llvm.GenericArray;
import shadow.output.llvm.LLVMOutput;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACBuilder;
import shadow.tac.TACModule;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeChecker;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

/**
 * @author Bill Speirs
 * @author Barry Wittman
 * @author Jacob Young
 * @author Brian Stottler
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
	private static Configuration config;
	private static Job currentJob;

	// Metadata related to a Shadow program's main class
	private static String mainClass;
	private static boolean mainArguments;
	
	private static final Charset UTF8 = Charset.forName("UTF-8");

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

	public static void run(String[] args) throws  FileNotFoundException, ParseException, ShadowException, IOException, org.apache.commons.cli.ParseException, ConfigurationException, TypeCheckException, CompileException {

		// Detect and establush the current settings and arguments
		Arguments compilerArgs = new Arguments(args);
		config = Configuration.buildConfiguration(compilerArgs, false);
		currentJob = new Job(compilerArgs);

		// Print help if the HELP option is set
		if( compilerArgs.hasOption("h") ) {
			printHelp();
			return;
		}

		Path system = config.getSystemImport();

		Path unwindFile = Paths.get("shadow", "Unwind" + config.getArch() + ".ll");
		unwindFile = system.resolve(unwindFile);
		
		// Locate the file defining platform-specific system calls
		Path OsFile = Paths.get("shadow" + File.separator + config.getOs() + ".ll" );
		OsFile = system.resolve(OsFile);

		List<String> linkCommand = new ArrayList<String>();
		linkCommand.add("llvm-link");
		linkCommand.add("-");
		linkCommand.add(unwindFile.toString());
		linkCommand.add(OsFile.toString());

		// Begin the checking/compilation process
		long startTime = System.currentTimeMillis();

		generateLLVM(linkCommand);

		if (!currentJob.isCheckOnly() && !currentJob.isNoLink())
		{
			// any output after this point is important, avoid getting it mixed in with previous output
			System.out.println();
			System.out.flush();
			try { Thread.sleep(250); }
			catch (InterruptedException ex) { }

			logger.info("Building for target \"" + config.getTarget() + "\"");

			List<String> assembleCommand = config.getLinkCommand(currentJob);

			Path mainLL;

			if( mainArguments )
				mainLL = Paths.get("shadow", "Main.ll");
			else
				mainLL = Paths.get("shadow", "NoArguments.ll");

			mainLL = system.resolve(mainLL);
			BufferedReader main = Files.newBufferedReader(mainLL, UTF8);
			
			Process link = new ProcessBuilder(linkCommand).redirectError(Redirect.INHERIT).start();
			Process optimize = new ProcessBuilder("opt", "-mtriple", config.getTarget(), "-O3").redirectError(Redirect.INHERIT).start();
			Process compile = new ProcessBuilder("llc", "-mtriple", config.getTarget(), "-O3")./*redirectOutput(new File("a.s")).*/redirectError(Redirect.INHERIT).start();
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


	/**
	 * Ensures that LLVM code exists for all dependencies of a main-method-
	 * containing class/file.This involves either finding an existing .ll file
	 * (which has been updated more recently than the corresponding source file
	 * or building a new one
	 */
	private static void generateLLVM(List<String> linkCommand) throws IOException, ShadowException, ParseException, ConfigurationException, TypeCheckException {
		
		Type.clearTypes();
		HashSet<Generic> generics = new HashSet<Generic>();
		HashSet<Array> arrays = new HashSet<Array>();

		TypeChecker checker = new TypeChecker();
		TACBuilder tacBuilder = new TACBuilder();

		Path mainFile = currentJob.getMainFile();
		String mainFileName = stripExt(mainFile.toString()); 

		List<Node> nodes;

		try
		{
			nodes = checker.typeCheck(mainFile.toFile(), currentJob);
		}
		catch( TypeCheckException e ) {
			logger.error(mainFile + " FAILED TO TYPE CHECK");
			throw e;
		}

		if( !currentJob.isCheckOnly() ) {		
			for( Node node : nodes ) {
				File file = node.getFile();
				String name = stripExt(file.getName());
				String path = stripExt(file.getCanonicalPath());
				File llvmFile = new File(path + ".ll");

				//if the LLVM didn't exist, the full .shadow file would have been used
				if( file.getPath().endsWith(".meta") ) {
					logger.info("Using pre-existing LLVM code for " + name);
					addToLink(node.getType(), file, linkCommand, generics, arrays );
				}
				else {
					logger.info("Generating LLVM code for " + name);
					for( TACModule module : tacBuilder.build(node) ) {
						Type type = module.getType();

						if( path.equals(mainFileName) && !type.hasOuter() ) {							
							mainClass = type.getMangledName();
							SequenceType arguments = new SequenceType(new ArrayType(Type.STRING));							
							if( type.getMatchingMethod("main", arguments) != null )
								mainArguments= true;
							else if( type.getMatchingMethod("main", new SequenceType()) != null )
								mainArguments = false;
							else
								throw new ShadowException("File " + file.getPath() + " does not contain an appropriate main() method");							
						}
						// Debug prints
						logger.debug(module.toString());

						// Write to file
						String className = typeToFileName(type);
						llvmFile = new File(file.getParentFile(), className + ".ll");
						File nativeFile = new File(file.getParentFile(), className + ".native.ll");
						LLVMOutput output = new LLVMOutput(llvmFile);
						output.build(module);

						generics.addAll(output.getGenerics());						
						arrays.addAll(output.getArrays());

						if( llvmFile.exists() )
							linkCommand.add(llvmFile.getCanonicalPath());
						else
							throw new ShadowException("Failed to generate " + llvmFile.getPath());

						if( nativeFile.exists() )
							linkCommand.add(nativeFile.getCanonicalPath());
					}
				}
			}

			// After all LLVM is generated, make a special generics file
			Path genericsFile = Paths.get(mainFile.toString().replace(".shadow", ".generics.shadow"));
			LLVMOutput interfaceOutput = new LLVMOutput(genericsFile.toFile());
			interfaceOutput.setGenerics(generics, arrays);
			interfaceOutput.buildGenerics();	
			linkCommand.add(interfaceOutput.getFile().getCanonicalPath());
		}
	}

	private static void addToLink( Type type, File file, List<String> linkCommand, HashSet<Generic> generics, HashSet<Array> arrays ) throws IOException, ShadowException {
		
		String name = typeToFileName(type);
		File llvmFile = new File(file.getParentFile(), name + ".ll");
		File nativeFile = new File(file.getParentFile(), name + ".native.ll");

		for (Type referenced : type.getReferencedTypes()  ) {
			if( referenced.isFullyInstantiated() ) {				
				if( referenced.getTypeWithoutTypeArguments().equals(Type.ARRAY))
					generics.add(new GenericArray(referenced));
				else
					generics.add(new Generic(referenced));
			}			
			else if( referenced instanceof ArrayType )
				arrays.add(new Array((ArrayType)referenced));
		}		

		if( llvmFile.exists() )
			linkCommand.add(llvmFile.getCanonicalPath());
		else
			throw new ShadowException("File not found: " + llvmFile.getPath());


		if( nativeFile.exists() )
			linkCommand.add(nativeFile.getCanonicalPath());		

		if( type instanceof ClassType ) {
			ClassType classType = (ClassType) type;
			for( Type inner : classType.getInnerClasses().values() )
				addToLink( inner, file, linkCommand, generics, arrays );
		}
	}


	public static String stripExt(String filepath) {
		return filepath.substring(0, filepath.lastIndexOf("."));
	}

	private static void printHelp() {
		new HelpFormatter().printHelp("shadowc <mainSource.shadow> [-o <output>] [-c <config.xml>]", Arguments.getOptions());
	}

	/** A simple class used to redirect an InputStream into a specified OutputStream */
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
	
	/** 
	 * Finds the standard file/class name for a primitive type (e.g. uint to 
	 * UInt) 
	 */
	private static String typeToFileName(Type type) {
		
		String name = type.getTypeName().replace(':', '$');
		if( type.isPrimitive() ) { // hack to produce Int.ll instead of int.ll
			if( name.startsWith("u") ) //UShort instead of ushort
				name = name.substring(0,2).toUpperCase() + name.substring(2);
			else
				name = name.substring(0,1).toUpperCase() + name.substring(1);
		}
		return name;
	}
}
