package shadow;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
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

import org.apache.logging.log4j.Logger;

import shadow.doctool.tag.TagManager.BlockTagType;
import shadow.output.llvm.LLVMOutput;
import shadow.parse.Context;
import shadow.parse.ParseException;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
import shadow.tac.TACBuilder;
import shadow.tac.TACModule;
import shadow.tac.analysis.ControlFlowGraph;
import shadow.typecheck.BaseChecker;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeChecker;
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
 * @author Claude Abounegm
 */
public class Main {

	// Version of the Shadow compiler
	public static final String VERSION = "0.7.5";
	public static final String MINIMUM_LLVM_VERSION = "3.8";

	// These are the error codes returned by the compiler
	public static final int NO_ERROR = 0;
	public static final int FILE_NOT_FOUND_ERROR = -1;
	public static final int PARSE_ERROR = -2;
	public static final int TYPE_CHECK_ERROR = -3;
	public static final int COMPILE_ERROR = -4;
	public static final int COMMAND_LINE_ERROR = -5;
	public static final int CONFIGURATION_ERROR = -6;

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
	 * @param args
	 *            Command line arguments to control the compiler
	 */
	public static void main(String[] args) {
		try {
			run(args);
		} catch (FileNotFoundException e) {
			System.err.println("FILE NOT FOUND: " + e.getLocalizedMessage());
			System.exit(FILE_NOT_FOUND_ERROR);
		} catch (ParseException e) {
			System.exit(PARSE_ERROR);
		} catch (IOException e) {
			System.err.println("FILE DEPENDENCY ERROR: " + e.getLocalizedMessage());
			System.exit(TYPE_CHECK_ERROR);
		} catch (org.apache.commons.cli.ParseException e) {
			System.err.println("COMMAND LINE ERROR: " + e.getLocalizedMessage());
			Arguments.printHelp();
			System.exit(COMMAND_LINE_ERROR);
		} catch (ConfigurationException e) {
			System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
			Arguments.printHelp();
			System.exit(CONFIGURATION_ERROR);
		} catch (TypeCheckException e) {
			System.exit(TYPE_CHECK_ERROR);
		} catch (CompileException e) {
			System.exit(COMPILE_ERROR);
		} catch (ShadowException e) {
			System.err.println("ERROR IN FILE: " + e.getLocalizedMessage());
			System.exit(TYPE_CHECK_ERROR);
		}
	}

	// Check LLVM version using lexical comparison
	private static void checkLLVMVersion() throws ConfigurationException {
		String LLVMVersion = Configuration.getLLVMVersion();
		if (LLVMVersion.compareTo(MINIMUM_LLVM_VERSION) < 0) {
			String error = "LLVM version " + MINIMUM_LLVM_VERSION + " or higher is required for Shadow " + VERSION
					+ ", but ";
			if (LLVMVersion.isEmpty())
				error += "no LLVM installation found.";
			else
				error += "version " + LLVMVersion + " found.";
			throw new ConfigurationException(error);
		}
	}

	public static void run(String[] args) throws FileNotFoundException, ParseException, ShadowException, IOException,
	org.apache.commons.cli.ParseException, ConfigurationException, TypeCheckException, CompileException {

		// Detect and establish the current settings and arguments
		Arguments compilerArgs = new Arguments(args);

		// Detect and establish the current settings based on the arguments
		config = Configuration.buildConfiguration(compilerArgs.getMainFileArg(), compilerArgs.getConfigFileArg(),
				false);
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

		// Important settings
		Path system = config.getSystemImport();
		boolean isCompile = !currentJob.isCheckOnly() && !currentJob.isNoLink();

		if (isCompile)
			checkLLVMVersion();

		// Begin the checking/compilation process
		long startTime = System.currentTimeMillis();

		List<String> linkCommand = new ArrayList<>();
		linkCommand.add(config.getLlvmLink()); // usually llvm-link
		linkCommand.add("-");
		List<Path> cFiles = new ArrayList<>();

		generateLLVM(cFiles, linkCommand);

		if (isCompile) {
			List<String> assembleCommand = new ArrayList<String>(config.getLinkCommand(currentJob));

			// compile and add the C source files to the assembler
			if (!compileCSourceFiles(system.resolve(Paths.get("shadow", "c-source")).normalize(), cFiles,
					assembleCommand)) {
				logger.error("Failed to compile one or more C source files.");
				throw new CompileException("FAILED TO COMPILE");
			}

			logger.info("Building for target \"" + config.getTarget() + "\"");
			Path mainLL;

			if (mainArguments)
				mainLL = Paths.get("shadow", "Main.ll");
			else
				mainLL = Paths.get("shadow", "NoArguments.ll");

			mainLL = system.resolve(mainLL);
			BufferedReader main = Files.newBufferedReader(mainLL, UTF8);

			Process link = new ProcessBuilder(linkCommand).redirectError(Redirect.INHERIT).start();			
			// usually llc
			Process compile = new ProcessBuilder(config.getLlc(), "-mtriple", config.getTarget(),
					config.getOptimizationLevel())
					/* .redirectOutput(new File("a.s")) */.redirectError(Redirect.INHERIT).start();
			Process assemble = new ProcessBuilder(assembleCommand).redirectOutput(Redirect.INHERIT)
					.redirectError(Redirect.INHERIT).start();

			try {
				new Pipe(link.getInputStream(), compile.getOutputStream()).start();
				new Pipe(compile.getInputStream(), assemble.getOutputStream()).start();
				String line = main.readLine();
				final OutputStream out = link.getOutputStream();

				while (line != null) {
					line = line.replace("shadow.test..Test", mainClass) + System.lineSeparator();
					out.write(line.getBytes());
					line = main.readLine();
				}

				try {
					main.close();
					link.getOutputStream().flush();
					link.getOutputStream().close();
				} catch (IOException ex) {
				}

				long sectionStart = System.currentTimeMillis();
				if (link.waitFor() != 0)
					throw new CompileException("FAILED TO LINK");
				logger.info("LLVM link finished in " + (System.currentTimeMillis() - sectionStart) + "ms");

				sectionStart = System.currentTimeMillis();
				if (compile.waitFor() != 0)
					throw new CompileException("FAILED TO COMPILE");
				logger.info("LLVM compilation finished in " + (System.currentTimeMillis() - sectionStart) + "ms");

				sectionStart = System.currentTimeMillis();
				if (assemble.waitFor() != 0)
					throw new CompileException("FAILED TO ASSEMBLE");
				logger.info("Assembly finished in " + (System.currentTimeMillis() - sectionStart) + "ms");

			} catch (InterruptedException ex) {
			} finally {
				link.destroy();				
				compile.destroy();
				assemble.destroy();
			}

			logger.info("SUCCESS: Built in " + (System.currentTimeMillis() - startTime) + "ms");
		}
	}

	private static boolean compileCSourceFiles(Path cSourcePath, List<Path> cShadowFiles, List<String> assembleCommand)
			throws IOException, ConfigurationException {

		// no need to compile anything if there are no c-source files
		if( !Files.exists(cSourcePath) ) {
			logger.error("The c-source directory was not found and is necessary for the Shadow runtime.");
			return false;
		}

		// compile the files to assembly, to be ready for linkage
		List<String> compileCommand = new ArrayList<String>();

		if (Configuration.getConfiguration().getOs().equals("Mac")) {
			compileCommand.add("gcc");
			String[] version = System.getProperty("os.version").split("\\.");
			compileCommand.add("-mmacosx-version-min=" + version[0] + "." + version[1]);
			// compileCommand.add("-Wall");			
		} else
			compileCommand.add("gcc");

		compileCommand.add("-O3");


		compileCommand.add("-S");

		// include directories to be in the search path of gcc
		compileCommand.add("-I" + cSourcePath.resolve(Paths.get("include")).toFile().getCanonicalPath());
		compileCommand.add("-I"
				+ cSourcePath.resolve(Paths.get("include", "platform", config.getOs())).toFile().getCanonicalPath());
		compileCommand.add("-I" + cSourcePath.resolve(Paths.get("include", "platform", "Arch" + config.getArch()))
		.toFile().getCanonicalPath());

		/*
		 * The compiling of the C files is done in two stages: 1. We traverse
		 * the `c-source` directory looking for `.c` files, and we add those to
		 * the coreCFiles list. All those files are compiled in one gcc run, and
		 * the corresponding .s files are generated next to the .c files, with
		 * the same name. 2. The cFiles list contains all the .c files found
		 * while generating LLVM for .shadow files. Each `.c` file is compiled
		 * using a gcc run. So, if there are 100 .c files, we will run gcc 100
		 * times.
		 */

		// we create a new compileCommand list to compile only the core .c
		// files, we use the original compileCommand
		// later on for the separate .c files.
		List<String> coreCompileCommand = new ArrayList<>(compileCommand);

		// the filter to only find .c files in the `c-source` folder.		
		try (DirectoryStream<Path> paths = Files.newDirectoryStream(cSourcePath, "*.c")) {
			for (Path cFile : paths) {
				if (shouldCompileCFile(cFile, assembleCommand)) {
					coreCompileCommand.add(canonicalize(cFile));
				}				
			}
		}		

		// if any files were to be compiled, we run the compiler, otherwise, we
		// skip.
		if (coreCompileCommand.size() > compileCommand.size()) {
			if (!runCCompiler(coreCompileCommand, cSourcePath)) {
				return false;
			}
		}

		compileCommand.add(null);
		// we compile each Shadow c file on its own
		for (Path cFile : cShadowFiles) {
			// checks if the files should be compiled, and add the .s file path
			// to the assembleCommand
			// list whether or not the file needs to be compiled.
			if (shouldCompileCFile(cFile, assembleCommand)) {
				compileCommand.set(compileCommand.size() - 1, canonicalize(cFile));
				if (!runCCompiler(compileCommand, cFile.getParent())) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean runCCompiler(List<String> compileCommand, Path cSourceDirectory) throws IOException {
		try {
			return new ProcessBuilder(compileCommand).directory(cSourceDirectory.toFile()).redirectError(Redirect.INHERIT)
					.start().waitFor() == 0;
		} catch (InterruptedException e) {
		}

		return false;
	}

	public static boolean shouldCompileCFile(Path currentFile, List<String> assembleCommand) throws IOException {
		Path assemblyPath = Paths.get(BaseChecker.stripExtension(currentFile.toString()) + ".s").normalize();
		assembleCommand.add(assemblyPath.toAbsolutePath().toString());

		if (currentJob.isForceRecompile() || !Files.exists(assemblyPath) || Files.getLastModifiedTime(assemblyPath)
				.compareTo(Files.getLastModifiedTime(currentFile)) < 0) {
			logger.info("Generating Assembly code for " + currentFile.getFileName());
			return true;
		}

		logger.info("Using pre-existing Assembly code for " + currentFile.getFileName());
		return false;
	}

	/*
	 * Ensures that LLVM code exists for all dependencies of a main-method-
	 * containing class/file. This involves either finding an existing .ll file
	 * (which has been updated more recently than the corresponding source file)
	 * or building a new one
	 */
	private static void generateLLVM(List<Path> cFiles, List<String> linkCommand) throws IOException, ShadowException,
	ParseException, ConfigurationException, TypeCheckException, CompileException {

		Path shadow = config.getSystemImport().resolve("shadow");

		// Add architecture-dependent exception handling code
		linkCommand.add(optimizeLLVMFile(shadow.resolve("Unwind" + config.getArch() + ".ll")));

		// Add platform-specific system code
		linkCommand.add(optimizeLLVMFile(shadow.resolve(config.getOs() + ".ll")));

		// Add shared code
		linkCommand.add(optimizeLLVMFile(shadow.resolve("Shared.ll")));

		Path mainFile = currentJob.getMainFile();
		String mainFileName = BaseChecker.stripExtension(canonicalize(mainFile));

		try {
			ErrorReporter reporter = new ErrorReporter(Loggers.TYPE_CHECKER);
			// TypeChecker generates a list of AST nodes corresponding to
			// classes needing compilation
			for (Context node : TypeChecker.typeCheck(mainFile, currentJob.isForceRecompile(), reporter)) {
				Path file = node.getPath();

				if (currentJob.isCheckOnly()) {
					// performs checks to make sure all paths return, there is
					// no dead code, etc.
					// no need to check interfaces or .meta files (no code in
					// either case)
					if (!file.toString().endsWith(".meta"))
						optimizeTAC(new TACBuilder().build(node), reporter, true);
				} else {
					String name = BaseChecker.stripExtension(file.getFileName().toString());
					String path = BaseChecker.stripExtension(canonicalize(file));

					Type type = node.getType();

					// set data for main class
					if (path.equals(mainFileName)) {
						mainClass = type.toString(Type.MANGLE);
						SequenceType arguments = new SequenceType(new ArrayType(Type.STRING));
						if (type.getMatchingMethod("main", arguments) != null)
							mainArguments = true;
						else if (type.getMatchingMethod("main", new SequenceType()) != null)
							mainArguments = false;
						else
							throw new CompileException(
									"File " + file + " does not contain an appropriate main() method");
					}

					String className = typeToFileName(type);
					Path cFile = file.getParent().resolve(className + ".c").normalize();
					if( Files.exists(cFile) )
						cFiles.add(cFile);


					Path bitcodeFile = file.getParent().resolve(className + ".bc");
					Path llvmFile = file.getParent().resolve(className + ".ll");
					Path nativeFile = file.getParent().resolve(className + ".native.ll");
					Path nativeBitcodeFile = file.getParent().resolve(className + ".native.bc");

					// if the LLVM bitcode didn't exist, the full .shadow file would
					// have been used
					if( file.toString().endsWith(".meta") ) {
						logger.info("Using pre-existing LLVM code for " + name);
						if (Files.exists(bitcodeFile))
							linkCommand.add(canonicalize(bitcodeFile));
						else if( Files.exists(llvmFile) )
							linkCommand.add(optimizeLLVMFile(llvmFile));
						else
							throw new CompileException("File not found: " + bitcodeFile);
					}
					else {
						logger.info("Generating LLVM code for " + name);
						// gets top level class						
						TACModule module = optimizeTAC(new TACBuilder().build(node), reporter, false);
						linkCommand.add(optimizeShadowFile(file, module));						
					}

					if( Files.exists(nativeBitcodeFile))
						linkCommand.add(canonicalize(nativeBitcodeFile));
					else if( Files.exists(nativeFile) )
						linkCommand.add(optimizeLLVMFile(nativeFile));					
				}
			}

			reporter.printAndReportErrors();			

		} catch (TypeCheckException e) {
			logger.error(mainFile + " FAILED TO TYPE CHECK");
			throw e;
		}
	}

	private static String optimizeLLVMFile(Path LLVMPath) throws CompileException {
		String LLVMFile = canonicalize(LLVMPath);
		String path = BaseChecker.stripExtension(LLVMFile);
		Path bitcodePath = Paths.get(path + ".bc");
		String bitcodeFile = canonicalize(bitcodePath); 

		boolean success = false;
		Process optimize = null;

		try {
			optimize = new ProcessBuilder(config.getOpt(), "-mtriple", config.getTarget(),
					config.getOptimizationLevel(), config.getDataLayout(), LLVMFile, "-o", bitcodeFile)
					.redirectError(Redirect.INHERIT).start();
			if( optimize.waitFor() != 0 )
				throw new CompileException("FAILED TO OPTIMIZE " + LLVMFile);

			success = true;			
		} 
		catch (IOException | InterruptedException e) {
			throw new CompileException("FAILED TO OPTIMIZE " + LLVMFile);
		}
		finally {
			if( !success ) {
				try {
					Files.deleteIfExists(bitcodePath);
				} catch (IOException e) {}
			}

			if( optimize != null )
				optimize.destroy();
		}

		return bitcodeFile;
	}

	private static String optimizeShadowFile(Path shadowPath, TACModule module) throws CompileException {
		String shadowFile = canonicalize(shadowPath);
		String path = BaseChecker.stripExtension(shadowFile);
		Path bitcodePath = Paths.get(path + ".bc");
		String bitcodeFile = canonicalize(bitcodePath); 
		boolean success = false;
		Process optimize = null;

		try {			
			LLVMOutput output = null;
			OutputStream out = null;

			if( currentJob.isHumanReadable() )
				out = Files.newOutputStream(Paths.get(path + ".ll"));
			else {
				optimize = new ProcessBuilder(config.getOpt(), "-mtriple", config.getTarget(),
						config.getOptimizationLevel(), config.getDataLayout(), "-o", bitcodeFile)
						.redirectError(Redirect.INHERIT).start();
				out = optimize.getOutputStream();
			}

			try {
				// Generate LLVM
				output = new LLVMOutput(out);
				output.build(module);
			} catch (ShadowException e) {
				logger.error("FAILED TO COMPILE " + shadowFile);				
				throw new CompileException(e.getMessage());
			}
			finally {
				if( output != null )
					output.close();
			}

			if( currentJob.isHumanReadable() ) {
				success = true;
				return optimizeLLVMFile(Paths.get(path + ".ll"));
			}
			else if (optimize.waitFor() != 0)
				throw new CompileException("FAILED TO OPTIMIZE " + shadowFile);

			success = true;

		} 
		catch (IOException | InterruptedException e) {
			throw new CompileException("FAILED TO OPTIMIZE " + shadowFile);
		}
		finally {
			if( !success ) {
				try {
					Files.deleteIfExists(bitcodePath);
				} catch (IOException e) {}
			}

			if( optimize != null )
				optimize.destroy();

		}

		return bitcodeFile;
	}

	/*
	 * This method contains all the Shadow-specific TAC optimization, including
	 * constant propagation, control flow analysis, and data flow analysis.
	 */
	public static TACModule optimizeTAC(TACModule module, ErrorReporter reporter, boolean checkOnly) {

		if (!(module.getType() instanceof InterfaceType)) {
			List<TACModule> innerClasses = module.getAllInnerClasses();
			List<TACModule> modules = new ArrayList<TACModule>(innerClasses.size() + 1);
			modules.add(module);
			modules.addAll(innerClasses);

			List<ControlFlowGraph> graphs = module.optimizeTAC(reporter, checkOnly);

			// get all used fields and all used private methods
			Map<Type, Set<String>> allUsedFields = new HashMap<Type, Set<String>>();
			Set<MethodSignature> allUsedPrivateMethods = new HashSet<MethodSignature>();
			for (ControlFlowGraph graph : graphs) {
				if( !graph.getMethod().getSignature().isCopy() && !graph.getMethod().getSignature().isDestroy() ) {				
					Map<Type, Set<String>> usedFields = graph.getUsedFields();
					for (Entry<Type, Set<String>> entry : usedFields.entrySet()) {
						Set<String> fields = allUsedFields.get(entry.getKey());
						if (fields == null) {
							fields = new HashSet<String>();
							allUsedFields.put(entry.getKey(), fields);
						}
						fields.addAll(entry.getValue());
					}

					allUsedPrivateMethods.addAll(graph.getUsedPrivateMethods());
				}
			}

			for (TACModule class_ : modules) {
				// check field initialization
				class_.checkFieldInitialization(reporter, graphs);

				// give warnings if fields are never used
				Type type = class_.getType();
				Set<String> usedFields = allUsedFields.get(type);
				for (Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet()) {
					if (!entry.getValue().getModifiers().isConstant() && !usedFields.contains(entry.getKey())
							&& entry.getValue().getDocumentation().getBlockTags(BlockTagType.UNUSED).isEmpty()) {
						reporter.addWarning(entry.getValue().generalIdentifier(), TypeCheckException.Error.UNUSED_FIELD,
								"Field " + entry.getKey() + " is never used");
					}
				}

				// give warnings if private methods are never used
				for (List<MethodSignature> signatures : type.getMethodMap().values()) {
					for (MethodSignature signature : signatures) {
						if (signature.getModifiers().isPrivate()
								&& !allUsedPrivateMethods.contains(signature.getSignatureWithoutTypeArguments())
								&& !signature.getSymbol().startsWith("$") && !signature.isExtern()
								&& signature.getDocumentation().getBlockTags(BlockTagType.UNUSED).isEmpty()
								&& !signature.isDestroy()) {

							Context node = signature.getNode();
							if( node instanceof ShadowParser.MethodDeclarationContext)
								node = ((ShadowParser.MethodDeclarationContext)node).methodDeclarator();
							else if( node instanceof ShadowParser.CreateDeclarationContext )
								node = ((ShadowParser.CreateDeclarationContext)node).createDeclarator();


							reporter.addWarning(node, TypeCheckException.Error.UNUSED_METHOD,
									"Private method " + signature.getSymbol() + signature.getMethodType()
									+ " is never used");
						}
					}
				}
			}
		}

		return module;
	}

	/*
	 * A simple class used to redirect an InputStream into a specified
	 * OutputStream
	 */
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
					} catch (IOException ex) {
					}
					try {
						output.flush();
					} catch (IOException ex) {
					}
					try {
						output.close();
					} catch (IOException ex) {
					}
				}
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * Finds the standard file/class name for a type, fixing capitalization for
	 * primitive types (e.g. uint toUInt).
	 */
	private static String typeToFileName(Type type) {

		String name = type.getTypeName();
		if (type.isPrimitive()) { // hack to produce Int.ll instead of int.ll
			if (name.startsWith("u")) // UShort instead of ushort
				name = name.substring(0, 2).toUpperCase() + name.substring(2);
			else
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		return name;
	}

	public static Job getJob() {
		return currentJob;
	}


	public static String canonicalize(Path path)
	{
		return path.toAbsolutePath().normalize().toString();
	}

}