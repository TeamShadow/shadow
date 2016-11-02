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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;

import shadow.output.llvm.LLVMOutput;
import shadow.parse.Context;
import shadow.parse.ParseException;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
import shadow.tac.TACBuilder;
import shadow.tac.TACModule;
import shadow.tac.analysis.ControlFlowGraph;
import shadow.typecheck.BaseChecker;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeChecker;
import shadow.typecheck.TypeCollector;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

/**
 * @author Bill Speirs
 * @author Barry Wittman
 * @author Jacob Young
 * @author Brian Stottler
 */
public class Main {
	
	// Version of the Shadow compiler
	public static final String VERSION 				= "0.7a";	
	public static final String MINIMUM_LLVM_VERSION  = "3.6";
		
	// These are the error codes returned by the compiler
	public static final int NO_ERROR				=  0;
	public static final int FILE_NOT_FOUND_ERROR	= -1;
	public static final int PARSE_ERROR				= -2;
	public static final int TYPE_CHECK_ERROR		= -3;	
	public static final int COMPILE_ERROR	 		= -4;
	public static final int COMMAND_LINE_ERROR		= -5;
	public static final int CONFIGURATION_ERROR		= -6;

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
	public static void main(String[] args) {
		try {
			run(args);
		}
		catch(FileNotFoundException e) {
			System.err.println("FILE NOT FOUND: " + e.getLocalizedMessage());
			System.exit(FILE_NOT_FOUND_ERROR);
		}
		catch(ParseException e) {
			System.err.println("PARSE ERROR: " + e.getLocalizedMessage());
			System.exit(PARSE_ERROR);
		}
		catch (IOException e) {
			System.err.println("FILE DEPENDENCY ERROR: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(TYPE_CHECK_ERROR);
		}
		catch (org.apache.commons.cli.ParseException e) {
			System.err.println("COMMAND LINE ERROR: " + e.getLocalizedMessage());
			Arguments.printHelp();
			System.exit(COMMAND_LINE_ERROR);
		}
		catch (ConfigurationException e) {
			System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
			Arguments.printHelp();
			System.exit(CONFIGURATION_ERROR);
		}
		catch (TypeCheckException e) {
			System.err.println("TYPE CHECK ERROR: " + e.getLocalizedMessage());
			System.exit(TYPE_CHECK_ERROR);
		}
		catch (CompileException e) {
			System.err.println("COMPILATION ERROR: " + e.getLocalizedMessage());
			System.exit(COMPILE_ERROR);
		}
		catch (ShadowException e) {
			System.err.println("ERROR IN FILE: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(TYPE_CHECK_ERROR);
		}
	}

	public static void run(String[] args) throws  FileNotFoundException, ParseException, ShadowException, IOException, org.apache.commons.cli.ParseException, ConfigurationException, TypeCheckException, CompileException {
		// Detect and establish the current settings and arguments
		Arguments compilerArgs = new Arguments(args);
		
		// Detect and establish the current settings based on the arguments
		config = Configuration.buildConfiguration(compilerArgs.getMainFileArg(),
				compilerArgs.getConfigFileArg(), false);
		currentJob = new Job(compilerArgs);		
		
		// Print help and exit
		if (compilerArgs.hasOption(Arguments.HELP)) {
			Arguments.printHelp();
			return;
		}
			
		// Print information and exit
		// Must come after building configuration, since configuration helps
		// us find the correct LLVM installation
		if (compilerArgs.hasOption(Arguments.INFORMATION)) {
			Arguments.printInformation();
			return;
		}

		Path system = config.getSystemImport();

		Path unwindFile = Paths.get("shadow", "Unwind" + config.getArch() + ".ll");
		unwindFile = system.resolve(unwindFile);
		
		// Locate the file defining platform-specific system calls
		Path OsFile = Paths.get("shadow" + File.separator + config.getOs() + ".ll" );
		OsFile = system.resolve(OsFile);

		List<String> linkCommand = new ArrayList<String>();
		linkCommand.add(config.getLlvmLink()); //usually llvm-link
		linkCommand.add("-");
		linkCommand.add(unwindFile.toString());
		linkCommand.add(OsFile.toString());

		// Begin the checking/compilation process
		long startTime = System.currentTimeMillis();

		Set<String> generics = new HashSet<String>();
		Set<String> arrays = new HashSet<String>();
		
		generateLLVM(linkCommand, generics, arrays);

		if (!currentJob.isCheckOnly() && !currentJob.isNoLink()) {			
			// Check LLVM version using lexical comparison
			String LLVMVersion = Configuration.getLLVMVersion(); 
			if( LLVMVersion.compareTo(MINIMUM_LLVM_VERSION) < 0 ) {
				String error = "LLVM version " + MINIMUM_LLVM_VERSION + " or higher is required for Shadow " + VERSION + ", but ";
				if( LLVMVersion.isEmpty() )
					error += "no LLVM installation found.";
				else
					error += "version " + LLVMVersion + " found.";				
				logger.error(error);
				return;
			}
			
			// any output after this point is important, avoid getting it mixed in with previous output
			System.out.flush();
			try { Thread.sleep(500); }
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
			
			String endian = "e"; //little Endian	
			String pointerAlignment = "p:" + config.getArch() + ":" + config.getArch() + ":" + config.getArch();
			String dataAlignment = "i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:64:64-f16:16:16-f32:32:32-f64:64:64";
			String aggregateAlignment = "a:0:" + config.getArch();
			String nativeIntegers = "n8:16:32:64";
			String dataLayout = "-default-data-layout=" + endian + "-" + pointerAlignment + "-" + dataAlignment + "-" + aggregateAlignment + "-" + nativeIntegers;
			
			String optimisationLevel = "-O3"; // set to empty string to check for race conditions in Threads.
			Process link = new ProcessBuilder(linkCommand).redirectError(Redirect.INHERIT).start();
			//usually opt
			Process optimize = new ProcessBuilder(config.getOpt(), "-mtriple", config.getTarget(), optimisationLevel, dataLayout).redirectError(Redirect.INHERIT).start();
			//usually llc
			Process compile = new ProcessBuilder(config.getLlc(), "-mtriple", config.getTarget(), optimisationLevel)/*.redirectOutput(new File("a.s"))*/.redirectError(Redirect.INHERIT).start();
			Process assemble = new ProcessBuilder(assembleCommand).redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();

			try {
				new Pipe(link.getInputStream(), optimize.getOutputStream()).start();
				new Pipe(optimize.getInputStream(), compile.getOutputStream()).start();
				new Pipe(compile.getInputStream(), assemble.getOutputStream()).start();
				String line = main.readLine();				
				final OutputStream out = link.getOutputStream();				
				while (line != null) {
					
					if( line.contains("@main")) { //declare externally defined generics
						for( String generic : generics )
							out.write(LLVMOutput.declareGeneric(generic).getBytes());
						for( String array : arrays )
							out.write(LLVMOutput.declareArray(array).getBytes());	
						
						out.write(System.lineSeparator().getBytes());
					}
					else if( line.trim().startsWith("%genericSet"))
						line = line.replace("%genericSize", "" + generics.size()*2);
					else if( line.trim().startsWith("%arraySet"))
						line = line.replace("%arraySize", "" + arrays.size()*2);
					else if( line.trim().startsWith("invoke")) { 
						//add in all externally declared generics
						LLVMOutput.addGenerics("%genericSet", generics, false, out);
						LLVMOutput.addGenerics("%arraySet", arrays, true, out);						
					}					
					
					line = line.replace("shadow.test..Test", mainClass) + System.lineSeparator();
					out.write(line.getBytes());
					line = main.readLine();
				}

				try {
					main.close();				
					link.getOutputStream().flush();				
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
	 * containing class/file. This involves either finding an existing .ll file
	 * (which has been updated more recently than the corresponding source file)
	 * or building a new one
	 */
	private static void generateLLVM(List<String> linkCommand, Set<String> generics, Set<String> arrays) throws IOException, ShadowException, ParseException, ConfigurationException, TypeCheckException, CompileException {		
		Type.clearTypes();		

		Path mainFile = currentJob.getMainFile();
		String mainFileName = BaseChecker.stripExtension(TypeCollector.canonicalize(mainFile)); 

		//just type check until ANTLR migration is finished
		try {
			//TypeChecker generates a list of AST nodes corresponding to classes needing compilation
			for( Context node : TypeChecker.typeCheck(mainFile, currentJob.isForceRecompile()) ) {				
				Path file = node.getPath();
				
				if( currentJob.isCheckOnly() ) {				
					//performs checks to make sure all paths return, there is no dead code, etc.
					//no need to check interfaces or .meta files (no code in either case)
					if( !file.toString().endsWith(".meta")  )
						optimizeTAC( new TACBuilder().build(node), true );
				}
				else {				
					String name = BaseChecker.stripExtension(file.getFileName().toString());
					String path = BaseChecker.stripExtension(TypeCollector.canonicalize(file));
					Path llvmFile = Paths.get(path + ".ll");
					
					Type type = node.getType();
					
					//set data for main class
					if( path.equals(mainFileName) ) {							
						mainClass = type.toString(Type.MANGLE);
						SequenceType arguments = new SequenceType(new ArrayType(Type.STRING));							
						if( type.getMatchingMethod("main", arguments) != null )
							mainArguments = true;
						else if( type.getMatchingMethod("main", new SequenceType()) != null )
							mainArguments = false;
						else
							throw new CompileException("File " + file + " does not contain an appropriate main() method");							
					}
				
					//if the LLVM didn't exist, the full .shadow file would have been used				
					if( file.toString().endsWith(".meta") ) {
						logger.info("Using pre-existing LLVM code for " + name);
						addToLink(node.getType(), file, linkCommand);
						LLVMOutput.readGenericAndArrayClasses( llvmFile, generics, arrays );
					}
					else {
						logger.info("Generating LLVM code for " + name);
						//gets top level class
						TACModule module = optimizeTAC( new TACBuilder().build(node), false );
	
						// Write to file
						String className = typeToFileName(type);						
						llvmFile = file.getParent().resolve(className + ".ll");						
						Path nativeFile = file.getParent().resolve(className + ".native.ll");
						LLVMOutput output = new LLVMOutput(llvmFile);
						try {					
							output.build(module);
						}
						catch(ShadowException e) {
							logger.error(file + " FAILED TO COMPILE");
							output.close();							
							Files.deleteIfExists(llvmFile);
							throw new CompileException(e.getMessage());
						}
	
						if( Files.exists(llvmFile) )
							linkCommand.add(TypeCollector.canonicalize(llvmFile));
						else
							throw new CompileException("Failed to generate " + llvmFile);
	
						if( Files.exists(nativeFile) )
							linkCommand.add(TypeCollector.canonicalize(nativeFile));						
						
						//it's important to add generics after generating the LLVM, since more are found
						generics.addAll(output.getGenericClasses());						
						arrays.addAll(output.getArrayClasses());
					}
				}				
			}
		}
		catch( TypeCheckException e ) {
			logger.error(mainFile + " FAILED TO TYPE CHECK");
			throw e;
		}	
	}

	
	/* 
	 * This method contains all the Shadow-specific TAC optimization,
	 * including constant propagation, control flow analysis, and
	 * data flow analysis.
	 */ 
	private static TACModule optimizeTAC(TACModule module, boolean checkOnly) throws ShadowException, TypeCheckException {		
		
		if( !(module.getType() instanceof InterfaceType) ) {			
			List<TACModule> innerClasses = module.getAllInnerClasses();
			List<TACModule> modules = new ArrayList<TACModule>(innerClasses.size() + 1);
			modules.add(module);
			modules.addAll(innerClasses);
			
			ErrorReporter reporter = new ErrorReporter(Loggers.TYPE_CHECKER);
			
			
			List<ControlFlowGraph> graphs = module.optimizeTAC(reporter, checkOnly);
			
			//get all used fields and all used private methods
			Map<Type, Set<String>> allUsedFields = new HashMap<Type, Set<String>>();
			Set<MethodSignature> allUsedPrivateMethods = new HashSet<MethodSignature>();
			for( ControlFlowGraph graph : graphs ) {
				Map<Type, Set<String>> usedFields = graph.getUsedFields();
				for( Entry<Type, Set<String>> entry : usedFields.entrySet() ) {
					Set<String> fields = allUsedFields.get(entry.getKey());
					if( fields == null ) {
						fields = new HashSet<String>();
						allUsedFields.put(entry.getKey(), fields);
					}					
					fields.addAll(entry.getValue());
				}
				
				allUsedPrivateMethods.addAll(graph.getUsedPrivateMethods());
			}

			for( TACModule class_ : modules ) {
				//check field initialization
				class_.checkFieldInitialization(reporter, graphs);
				
				//give warnings if fields are never used
				Type type = class_.getType();
				//feels ugly, but Array and ArrayNullable have a data field used only by native methods
				if( !type.equals(Type.ARRAY) && !type.equals(Type.ARRAY_NULLABLE)) {
					Set<String> usedFields = allUsedFields.get(type);
					for( Entry<String, VariableDeclaratorContext> entry  : type.getFields().entrySet() ) {
						if( !entry.getValue().getModifiers().isConstant() && !usedFields.contains(entry.getKey()))
							reporter.addWarning(entry.getValue(), TypeCheckException.Error.UNUSED_FIELD, "Field " + entry.getKey() + " is never used");
					}
				}
				
				//give warnings if private methods are never used
				for( List<MethodSignature> signatures : type.getMethodMap().values() )
					for( MethodSignature signature : signatures )
						if( signature.getModifiers().isPrivate() && !allUsedPrivateMethods.contains(signature.getSignatureWithoutTypeArguments()) && !signature.getSymbol().endsWith("Native"))
							reporter.addWarning(signature.getNode(), TypeCheckException.Error.UNUSED_METHOD, "Private method " + signature.getSymbol() + signature.getMethodType() + " is never used");
				
			}					
			
			reporter.printAndReportErrors();
		}
		
		return module;
	}	

	private static void addToLink( Type type, Path file, List<String> linkCommand ) throws IOException, ShadowException {
		
		String name = typeToFileName(type);
		
		
		Path llvmFile = file.getParent().resolve(name + ".ll");
		Path nativeFile = file.getParent().resolve(name + ".native.ll");

		if( Files.exists(llvmFile) )
			linkCommand.add(TypeCollector.canonicalize(llvmFile));
		else
			throw new CompileException("File not found: " + llvmFile);


		if( Files.exists(nativeFile) )
			linkCommand.add(TypeCollector.canonicalize(nativeFile));
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
	 * Finds the standard file/class name for a type, fixing capitalization 
	 * for primitive types (e.g. uint toUInt). 
	 */
	private static String typeToFileName(Type type) {
		
		String name = type.getTypeName();
		if( type.isPrimitive() ) { // hack to produce Int.ll instead of int.ll
			if( name.startsWith("u") ) //UShort instead of ushort
				name = name.substring(0,2).toUpperCase() + name.substring(2);
			else
				name = name.substring(0,1).toUpperCase() + name.substring(1);
		}
		return name;
	}
	
	public static Job getJob() {
		return currentJob;
	}
}
