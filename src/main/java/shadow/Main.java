package shadow;

import org.apache.logging.log4j.Logger;
import shadow.doctool.tag.TagManager.BlockTagType;
import shadow.interpreter.ConstantFieldInterpreter;
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
import shadow.typecheck.type.*;

import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Bill Speirs
 * @author Barry Wittman
 * @author Jacob Young
 * @author Brian Stottler
 * @author Claude Abounegm
 */
public class Main {

  // Version of the Shadow compiler
  public static final String VERSION = "0.8";
  public static final String MINIMUM_LLVM_VERSION = "6.0";
  public static final String MINIMUM_WINDOWS_LLVM_VERSION = "10.0";

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

  private static final Charset UTF8 = StandardCharsets.UTF_8;

  /**
   * This is the starting point of the compiler.
   *
   * @param args Command line arguments to control the compiler
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
    System.exit(NO_ERROR);
  }

  // Check LLVM version using lexical comparison
  private static void checkLLVMVersion() throws ConfigurationException {
    String LLVMVersion = Configuration.getLLVMVersion();

    int comparison;
    if (Configuration.isWindows()) // Higher versions of LLVM are needed for Windows because of EH
    comparison = compareVersions(LLVMVersion, MINIMUM_WINDOWS_LLVM_VERSION);
    else comparison = compareVersions(LLVMVersion, MINIMUM_LLVM_VERSION);

    if (comparison < 0) {
      String error =
          "LLVM version "
              + MINIMUM_LLVM_VERSION
              + " or higher is required for Shadow "
              + VERSION
              + ", but ";
      if (LLVMVersion.isEmpty()) error += "no LLVM installation found.";
      else error += "version " + LLVMVersion + " found.";
      throw new ConfigurationException(error);
    }
  }

  private static int versionStringToInt(String number) {
    int start = 0;
    int end = 0;

    // Start at the beginning, since alphabetic characters indicating alpha or similar might be at
    // the end
    while (end < number.length() && Character.isDigit(number.charAt(end))) ++end;

    try {
      return Integer.parseInt(number.substring(start, end));
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  private static int compareVersions(String v1, String v2) {
    String[] strings1 = v1.split("\\.");
    String[] strings2 = v2.split("\\.");
    int size = Math.max(strings1.length, strings2.length);
    for (int i = 0; i < size; ++i) {
      int value1, value2;
      if (i < strings1.length) value1 = versionStringToInt(strings1[i].trim());
      else value1 = 0;
      if (i < strings2.length) value2 = versionStringToInt(strings2[i].trim());
      else value2 = 0;
      if (value1 != value2) return value1 - value2;
    }

    return 0;
  }

  public static void run(String[] args)
      throws ShadowException, IOException, org.apache.commons.cli.ParseException,
          ConfigurationException {

    long timing = System.currentTimeMillis();

    // Detect and establish the current settings and arguments
    Arguments compilerArgs = new Arguments(args);

    // Detect and establish the current settings based on the arguments
    config =
        Configuration.buildConfiguration(
            compilerArgs.getMainFileArg(), compilerArgs.getConfigFileArg(), false);
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

    if (isCompile) checkLLVMVersion();

    logger.info("Configuration took: " + (System.currentTimeMillis() - timing) + "ms");

    // Begin the checking/compilation process
    long startTime = System.currentTimeMillis();

    List<String> linkCommand = new ArrayList<>(config.getLinkCommand(currentJob));
    List<Path> cFiles = new ArrayList<>();

    generateObjectFiles(cFiles, linkCommand);

    if (isCompile) {
      // Compile and add the C source files to get linked
      if (!compileCSourceFiles(
          system.resolve(Paths.get("shadow", "c-source")).normalize(), cFiles, linkCommand)) {
        logger.error("Failed to compile one or more C source files.");
        throw new CompileException("FAILED TO COMPILE");
      }

      logger.info("Building for target \"" + config.getTarget() + "\"");
      Path mainLL;
      if (config.getOs().equals("Windows")) {
        if (mainArguments) mainLL = Paths.get("shadow", "MainWindows.ll");
        else mainLL = Paths.get("shadow", "NoArgumentsWindows.ll");
      } else {
        if (mainArguments) mainLL = Paths.get("shadow", "Main.ll");
        else mainLL = Paths.get("shadow", "NoArguments.ll");
      }

      mainLL = system.resolve(mainLL);
      BufferedReader main = Files.newBufferedReader(mainLL, UTF8);

      linkCommand.add("-x");
      linkCommand.add("assembler");
      linkCommand.add("-");

      // Usually llc
      Process compile =
          new ProcessBuilder(
                  config.getLlc(),
                  "-mtriple",
                  config.getTarget(),
                  "--filetype=asm",
                  config.getOptimizationLevel())
              .redirectError(Redirect.INHERIT)
              .start();

      // Usually clang or clang++
      Process link =
          new ProcessBuilder(linkCommand)
              .redirectOutput(Redirect.INHERIT)
              .redirectError(Redirect.INHERIT)
              .start();

      try {
        new Pipe(compile.getInputStream(), link.getOutputStream()).start();
        String line = main.readLine();
        final OutputStream out = compile.getOutputStream();

        while (line != null) {
          line = line.replace("shadow.test..Test", mainClass) + System.lineSeparator();
          out.write(line.getBytes());
          line = main.readLine();
        }

        try {
          main.close();
          compile.getOutputStream().flush();
          compile.getOutputStream().close();
        } catch (IOException ignored) {
        }

        timing = System.currentTimeMillis();
        if (compile.waitFor() != 0) throw new CompileException("FAILED TO COMPILE");
        logger.info(
            "Compilation of main file took: " + (System.currentTimeMillis() - timing) + "ms");

        timing = System.currentTimeMillis();
        if (link.waitFor() != 0) throw new CompileException("FAILED TO LINK");
        logger.info("Linking took: " + (System.currentTimeMillis() - timing) + "ms");

      } catch (InterruptedException ignored) {
      } finally {
        compile.destroy();
        link.destroy();
      }

      logger.info("SUCCESS: Built in " + (System.currentTimeMillis() - startTime) + "ms");
    }
  }

  // Assumes compileCommand contains two empty slots at the end:
  // The first is for the output file name
  // The last is for the input file name
  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  private static boolean compileCSourceFile(
      Path cFile, List<String> compileCommand, List<String> assembleCommand) throws IOException {
    String inputFile = canonicalize(cFile);
    String outputFile = inputFile + ".o";
    Path outputPath = Paths.get(outputFile);

    assembleCommand.add(outputFile);

    if (currentJob.isForceRecompile()
        || !Files.exists(outputPath)
        || Files.getLastModifiedTime(outputPath).compareTo(Files.getLastModifiedTime(cFile)) < 0) {
      logger.info("Generating assembly code for " + cFile.getFileName());
      compileCommand.set(compileCommand.size() - 2, outputFile);
      compileCommand.set(compileCommand.size() - 1, inputFile);
      return runCCompiler(compileCommand, cFile.getParent());
    }

    logger.info("Using pre-existing assembly code for " + cFile.getFileName());
    return true;
  }

  private static boolean compileCSourceFiles(
      Path cSourcePath, List<Path> cShadowFiles, List<String> assembleCommand)
      throws IOException, ConfigurationException {

    // No need to compile anything if there are no c-source files
    if (!Files.exists(cSourcePath)) {
      logger.error("The c-source directory was not found and is necessary for the Shadow runtime.");
      return false;
    }

    // Compile the files to assembly, to be ready for linkage
    List<String> compileCommand = new ArrayList<>();

    if (Configuration.getConfiguration().getOs().equals("Mac")) {
      compileCommand.add("clang");
      String[] version = System.getProperty("os.version").split("\\.");
      compileCommand.add("-mmacosx-version-min=" + version[0] + "." + version[1]);
      // compileCommand.add("-Wall");
    } else compileCommand.add("clang");

    compileCommand.add("-m" + Configuration.getConfiguration().getArch());

    compileCommand.add("-O3");

    compileCommand.add("-c");

    // include directories to be in the search path of gcc
    compileCommand.add(
        "-I" + cSourcePath.resolve(Paths.get("include")).toFile().getCanonicalPath());
    compileCommand.add(
        "-I"
            + cSourcePath
                .resolve(Paths.get("include", "platform", config.getOs()))
                .toFile()
                .getCanonicalPath());
    compileCommand.add(
        "-I"
            + cSourcePath
                .resolve(Paths.get("include", "platform", "Arch" + config.getArch()))
                .toFile()
                .getCanonicalPath());

    compileCommand.add("-o");
    compileCommand.add(null); // Location for output name
    compileCommand.add(null); // Location for .c file

    /*
     * The compiling of the C files is done in two stages:
     * 1. We traverse the `c-source` directory looking for `.c` files
     * and compile them.
     * 2. The cFiles list contains all the .c files found
     * while generating LLVM for .shadow files. Each `.c` file is compiled
     * using a gcc run. So, if there are 100 .c files, we will run gcc (clang) 100
     * times.
     */

    // The filter to find .c files in the `c-source` folder.
    try (DirectoryStream<Path> paths = Files.newDirectoryStream(cSourcePath, "*.c")) {
      for (Path cFile : paths)
        if (!compileCSourceFile(cFile, compileCommand, assembleCommand)) return false;
    }

    for (Path cFile : cShadowFiles) {
      if (!compileCSourceFile(cFile, compileCommand, assembleCommand)) return false;
    }

    return true;
  }

  private static boolean runCCompiler(List<String> compileCommand, Path cSourceDirectory)
      throws IOException {
    try {
      return new ProcessBuilder(compileCommand)
              .directory(cSourceDirectory.toFile())
              .redirectError(Redirect.INHERIT)
              .start()
              .waitFor()
          == 0;
    } catch (InterruptedException ignored) {
    }

    return false;
  }

  /*
   * Ensures that LLVM code exists for all dependencies of a main-method-
   * containing class/file. This involves either finding an existing .ll file
   * (which has been updated more recently than the corresponding source file)
   * or building a new one
   */
  private static void generateObjectFiles(List<Path> cFiles, List<String> linkCommand)
      throws IOException, ShadowException, ConfigurationException {

    Path shadow = config.getSystemImport().resolve("shadow");

    // Add architecture-dependent exception handling code
    if (Configuration.getConfiguration().getOs().equals("Windows"))
      linkCommand.add(optimizeLLVMFile(shadow.resolve("UnwindWindows" + config.getArch() + ".ll")));
    else linkCommand.add(optimizeLLVMFile(shadow.resolve("Unwind" + config.getArch() + ".ll")));

    // Add platform-specific system code
    linkCommand.add(optimizeLLVMFile(shadow.resolve(config.getOs() + ".ll")));

    // Add shared code
    linkCommand.add(optimizeLLVMFile(shadow.resolve("Shared.ll")));

    Path mainFile = currentJob.getMainFile();
    String mainFileName = BaseChecker.stripExtension(canonicalize(mainFile));

    ErrorReporter reporter = new ErrorReporter(Loggers.TYPE_CHECKER);

    long timing = System.currentTimeMillis();

    // TypeChecker generates a list of AST nodes corresponding to
    // classes needing compilation
    TypeChecker.TypeCheckerOutput typecheckerOutput =
        TypeChecker.typeCheck(
            mainFile, currentJob.isForceRecompile(), reporter, currentJob.isCheckOnly());

    logger.info("Type-checking took: " + (System.currentTimeMillis() - timing) + "ms");
    timing = System.currentTimeMillis();

    ConstantFieldInterpreter.evaluateConstants(
        typecheckerOutput.packageTree, typecheckerOutput.nodes);

    logger.info("Constant evaluation took: " + (System.currentTimeMillis() - timing) + "ms");
    timing = System.currentTimeMillis();

    // As an optimization, print .meta files for the .shadow files being checked
    typecheckerOutput.nodes.stream()
        .filter((node) -> !node.isFromMetaFile())
        .forEach(TypeChecker::printMetaFile);

    logger.info("Meta file generation took: " + (System.currentTimeMillis() - timing) + "ms");
    timing = System.currentTimeMillis();

    try {
      for (Context node : typecheckerOutput.nodes) {
        Path file = node.getPath();

        if (currentJob.isCheckOnly()) {
          // Performs checks to make sure all paths return, there is
          // no dead code, etc.
          // No need to check interfaces, attributes, or .meta files (no code in
          // those cases)
          if (!node.isFromMetaFile() && !(node.getType() instanceof AttributeType))
            optimizeTAC(new TACBuilder().build(node), reporter);
        } else {
          String name = BaseChecker.stripExtension(file.getFileName().toString());
          String path = BaseChecker.stripExtension(canonicalize(file));

          Type type = node.getType();

          // Set data for main class
          if (path.equals(mainFileName)) {
            mainClass = type.toString(Type.MANGLE);
            SequenceType arguments = new SequenceType(new ArrayType(Type.STRING));
            if (type.getMatchingMethod("main", arguments) != null) mainArguments = true;
            else if (type.getMatchingMethod("main", new SequenceType()) != null)
              mainArguments = false;
            else
              throw new CompileException(
                  "File " + file + " does not contain an appropriate main() method");
          }

          String className = typeToFileName(type);
          Path cFile = file.getParent().resolve(className + ".c").normalize();
          if (Files.exists(cFile)) cFiles.add(cFile);

          Path objectFile = file.getParent().resolve(className + ".o");
          Path llvmFile = file.getParent().resolve(className + ".ll");
          Path nativeFile = file.getParent().resolve(className + ".native.ll");
          Path nativeObjectFile = file.getParent().resolve(className + ".native.o");

          // if the LLVM bitcode didn't exist, the full .shadow file would
          // have been used
          if (node.isFromMetaFile()) {
            logger.info("Using pre-existing LLVM code for " + name);
            if (Files.exists(objectFile)) linkCommand.add(canonicalize(objectFile));
            else if (Files.exists(llvmFile)) linkCommand.add(optimizeLLVMFile(llvmFile));
            else throw new CompileException("File not found: " + objectFile);
          } else {
            logger.info("Generating LLVM code for " + name);
            // gets top level class
            TACModule module = optimizeTAC(new TACBuilder().build(node), reporter);
            linkCommand.add(compileShadowFile(file, module));
          }

          if (Files.exists(nativeObjectFile)) linkCommand.add(canonicalize(nativeObjectFile));
          else if (Files.exists(nativeFile)) linkCommand.add(optimizeLLVMFile(nativeFile));
        }
      }

      logger.info("Building object files took: " + (System.currentTimeMillis() - timing) + "ms");

      reporter.printAndReportErrors();

    } catch (TypeCheckException e) {
      logger.error(mainFile + " FAILED TO TYPE CHECK");
      throw e;
    }
  }

  private static String compileLLVMStream(InputStream stream, String path, Path LLVMPath)
      throws CompileException {
    Path objectPath = Paths.get(path + ".o");
    String objectFile = canonicalize(objectPath);

    boolean success = false;
    Process compile = null;

    try {
      compile =
          new ProcessBuilder(
                  config.getLlc(),
                  "-mtriple",
                  config.getTarget(),
                  config.getLLVMOptimizationLevel(), /*config.getDataLayout(),*/
                  "--filetype=obj",
                  "-o",
                  objectFile)
              .start();
      new Pipe(stream, compile.getOutputStream()).start();
      if (compile.waitFor() != 0) throw new CompileException("FAILED TO COMPILE " + LLVMPath);
      success = true;
    } catch (IOException | InterruptedException e) {
      throw new CompileException("FAILED TO COMPILE " + LLVMPath);
    } finally {
      if (!success) {
        try {
          Files.deleteIfExists(objectPath);
        } catch (IOException ignored) {
        }
      }

      if (compile != null) compile.destroy();
    }

    return objectFile;
  }

  private static String optimizeLLVMStream(InputStream stream, String path, Path LLVMPath)
      throws CompileException {
    Process optimize = null;
    try {
      optimize =
          new ProcessBuilder(
                  config.getOpt(),
                  "-mtriple",
                  config.getTarget(),
                  config.getLLVMOptimizationLevel(),
                  config.getDataLayout())
              .redirectError(Redirect.INHERIT)
              .start();
      new Pipe(stream, optimize.getOutputStream()).start();
      String objectFile = compileLLVMStream(optimize.getInputStream(), path, LLVMPath);
      if (optimize.waitFor() != 0) throw new CompileException("FAILED TO OPTIMIZE " + LLVMPath);

      return objectFile;
    } catch (IOException | InterruptedException e) {
      throw new CompileException("FAILED TO OPTIMIZE " + LLVMPath);
    } finally {
      if (optimize != null) optimize.destroy();
    }
  }

  private static String optimizeLLVMFile(Path LLVMPath) throws CompileException {
    String LLVMFile = canonicalize(LLVMPath);
    String path = BaseChecker.stripExtension(LLVMFile);
    try {
      return optimizeLLVMStream(Files.newInputStream(LLVMPath), path, LLVMPath);
    } catch (IOException e) {
      throw new CompileException("FAILED TO OPTIMIZE " + LLVMPath);
    }
  }

  private static String compileShadowFile(Path shadowPath, TACModule module)
      throws CompileException {
    String shadowFile = canonicalize(shadowPath);
    String path = BaseChecker.stripExtension(shadowFile);
    Process optimize = null;

    try {
      LLVMOutput output = null;
      OutputStream out;

      if (currentJob.isHumanReadable()) out = Files.newOutputStream(Paths.get(path + ".ll"));
      else {
        optimize =
            new ProcessBuilder(
                    config.getOpt(),
                    "-mtriple",
                    config.getTarget(),
                    config.getLLVMOptimizationLevel(),
                    config.getDataLayout())
                .redirectError(Redirect.INHERIT)
                .start();
        out = optimize.getOutputStream();
      }

      try {
        // Generate LLVM
        output = new LLVMOutput(out);
        output.build(module);
      } catch (ShadowException e) {
        logger.error("FAILED TO COMPILE " + shadowFile);
        throw new CompileException(e.getMessage());
      } finally {
        if (output != null) output.close();
      }

      if (currentJob.isHumanReadable()) return optimizeLLVMFile(Paths.get(path + ".ll"));
      else {
        @SuppressWarnings("ConstantConditions")
        String objectFile = compileLLVMStream(optimize.getInputStream(), path, shadowPath);
        if (optimize.waitFor() != 0) throw new CompileException("FAILED TO OPTIMIZE " + shadowFile);
        return objectFile;
      }
    } catch (IOException | InterruptedException e) {
      throw new CompileException("FAILED TO OPTIMIZE " + shadowFile);
    } finally {
      if (optimize != null) optimize.destroy();
    }
  }

  /*
   * This method contains all the Shadow-specific TAC optimization, including
   * constant propagation, control flow analysis, and data flow analysis.
   */
  public static TACModule optimizeTAC(TACModule module, ErrorReporter reporter) {

    if (!(module.getType() instanceof InterfaceType)) {
      List<TACModule> innerClasses = module.getAllInnerClasses();
      List<TACModule> modules = new ArrayList<>(innerClasses.size() + 1);
      modules.add(module);
      modules.addAll(innerClasses);

      List<ControlFlowGraph> graphs = module.optimizeTAC(reporter);

      // Get all used fields and all used private methods
      Map<Type, Set<String>> allUsedFields = new HashMap<>();
      Set<MethodSignature> allUsedPrivateMethods = new HashSet<>();
      for (ControlFlowGraph graph : graphs) {
        if (!graph.getMethod().getSignature().isCopy()
            && !graph.getMethod().getSignature().isDestroy()) {
          Map<Type, Set<String>> usedFields = graph.getUsedFields();
          for (Entry<Type, Set<String>> entry : usedFields.entrySet()) {
            Set<String> fields =
                allUsedFields.computeIfAbsent(entry.getKey(), k -> new HashSet<>());
            fields.addAll(entry.getValue());
          }

          allUsedPrivateMethods.addAll(graph.getUsedPrivateMethods());
        }
      }

      for (TACModule class_ : modules) {
        // No attribute member checking for now
        if (class_.getType() instanceof AttributeType) continue;

        // Check field initialization
        class_.checkFieldInitialization(reporter, graphs);

        // Give warnings if fields are never used
        Type type = class_.getType();
        Set<String> usedFields = allUsedFields.computeIfAbsent(type, k -> new HashSet<>());
        for (Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet()) {
          if (!entry.getValue().getModifiers().isConstant()
              && !usedFields.contains(entry.getKey())
              && entry.getValue().getDocumentation().getBlockTags(BlockTagType.UNUSED).isEmpty()) {
            reporter.addWarning(
                entry.getValue().generalIdentifier(),
                TypeCheckException.Error.UNUSED_FIELD,
                "Field " + entry.getKey() + " is never used");
          }
        }

        // Give warnings if private methods are never used
        for (List<MethodSignature> signatures : type.getMethodMap().values()) {
          for (MethodSignature signature : signatures) {
            if (signature.getModifiers().isPrivate()
                && !allUsedPrivateMethods.contains(signature.getSignatureWithoutTypeArguments())
                && !signature.isImport()
                && !signature.isExport()
                && signature.getDocumentation().getBlockTags(BlockTagType.UNUSED).isEmpty()
                && !signature.isDestroy()) {

              Context node = signature.getNode();
              if (node instanceof ShadowParser.MethodDeclarationContext)
                node = ((ShadowParser.MethodDeclarationContext) node).methodDeclarator();
              else if (node instanceof ShadowParser.CreateDeclarationContext)
                node = ((ShadowParser.CreateDeclarationContext) node).createDeclarator();

              reporter.addWarning(
                  node,
                  TypeCheckException.Error.UNUSED_METHOD,
                  "Private method "
                      + signature.getSymbol()
                      + signature.getMethodType()
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
    private final InputStream input;
    private final OutputStream output;

    public Pipe(InputStream inputStream, OutputStream outputStream) {
      input = inputStream;
      output = outputStream;
    }

    @Override
    public void run() {
      try {
        try {
          byte[] buffer = new byte[8096];
          int read = input.read(buffer);
          while (read >= 0) {
            output.write(buffer, 0, read);
            read = input.read(buffer);
          }
        } finally {
          try {
            input.close();
          } catch (IOException ignored) {
          }
          try {
            output.flush();
          } catch (IOException ignored) {
          }
          try {
            output.close();
          } catch (IOException ignored) {
          }
        }
      } catch (IOException ignored) {
      }
    }
  }

  /**
   * Finds the standard file/class name for a type, fixing capitalization for primitive types (e.g.
   * uint toUInt).
   */
  private static String typeToFileName(Type type) {

    String name = type.getTypeName();
    if (type.isPrimitive()) { // hack to produce Int.ll instead of int.ll
      if (name.startsWith("u")) // UShort instead of ushort
      name = name.substring(0, 2).toUpperCase() + name.substring(2);
      else name = name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    return name;
  }

  public static Job getJob() {
    return currentJob;
  }

  public static String canonicalize(Path path) {
    Path absolute = path.toAbsolutePath();
    Path normalized = absolute.normalize();
    return normalized.toString();
  }
}
