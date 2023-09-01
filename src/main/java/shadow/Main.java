package shadow;

import org.apache.logging.log4j.Logger;
import shadow.doctool.tag.TagManager.BlockTagType;
import shadow.interpreter.ConstantFieldInterpreter;
import shadow.output.llvm.IrOutput;
import shadow.parse.Context;
import shadow.parse.ParseException;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
import shadow.tac.TACBuilder;
import shadow.tac.TACModule;
import shadow.tac.analysis.ControlFlowGraph;
import shadow.typecheck.*;
import shadow.typecheck.type.*;

import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Barry Wittman
 * @author Claude Abounegm
 * @author Brian Stottler
 * @author Jacob Young
 * @author Bill Speirs
 */
public class Main {

  // Version of the Shadow compiler
  public static final String VERSION = "0.8.5";
  public static final String MINIMUM_CLANG_VERSION = "10.0";

  // These are the error codes returned by the compiler
  public enum Error {
    NO_ERROR,
    FILE_NOT_FOUND_ERROR,
    PARSE_ERROR,
    TYPE_CHECK_ERROR,
    COMPILE_ERROR,
    COMMAND_LINE_ERROR,
    CONFIGURATION_ERROR,
    DOCUMENTATION_ERROR
  }

  private static final Logger logger = Loggers.SHADOW;
  private static final Charset UTF8 = StandardCharsets.UTF_8;

  // Data for the current compilation

  private final Configuration config;


  /** The linker command used to specify an output file */
  private final List<String> linkCommand = new ArrayList<>();

  // Important, job-specific compiler flags
  private final boolean checkOnly; // Run only parser and type-checker
  private final boolean link; // Compile the given file, but do not link
  private final boolean humanReadable;
  private final List<Path> files = new ArrayList<>();

  // Metadata related to a Shadow program's main class
  private String mainClass;
  private boolean mainArguments;

  private final Arguments arguments;



  /**
   * This is the starting point of the compiler.
   *
   * @param args Command line arguments to control the compiler
   */
  public static void main(String[] args) {
    try {
      new Main(args).run();
    } catch (FileNotFoundException e) {
      System.err.println("FILE NOT FOUND: " + e.getLocalizedMessage());
      System.exit(Error.FILE_NOT_FOUND_ERROR.ordinal());
    } catch (ParseException e) {
      System.exit(Error.PARSE_ERROR.ordinal());
    } catch (IOException e) {
      System.err.println("FILE DEPENDENCY ERROR: " + e.getLocalizedMessage());
      System.exit(Error.TYPE_CHECK_ERROR.ordinal());
    } catch (org.apache.commons.cli.ParseException | CommandLineException e) {
      System.err.println("COMMAND LINE ERROR: " + e.getLocalizedMessage());
      Arguments.printHelp();
      System.exit(Error.COMMAND_LINE_ERROR.ordinal());
    } catch (ConfigurationException e) {
      System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
      Arguments.printHelp();
      System.exit(Error.CONFIGURATION_ERROR.ordinal());
    } catch (TypeCheckException e) {
      System.exit(Error.TYPE_CHECK_ERROR.ordinal());
    } catch (CompileException e) {
      System.exit(Error.COMPILE_ERROR.ordinal());
    } catch (ShadowException e) {
      System.err.println("ERROR IN FILE: " + e.getLocalizedMessage());
      System.exit(Error.TYPE_CHECK_ERROR.ordinal());
    }
    System.exit(Error.NO_ERROR.ordinal());
  }

  public Main(String[] args)
      throws org.apache.commons.cli.ParseException,
          IOException,
          CommandLineException,
          ConfigurationException {

    // Detect and establish the current settings and arguments
    arguments = new Arguments(args);

    String mainFile = null;
    if (arguments.getFiles().length > 0) mainFile = arguments.getFiles()[0];

    // Detect and establish the current settings based on the arguments
    config = Configuration.buildConfiguration(mainFile, arguments.getConfigFileArg(), false);

    // Check relevant command line flags
    checkOnly = arguments.hasOption(Arguments.TYPECHECK);
    link =
            !arguments.hasOption(Arguments.NO_LINK)
                    && !arguments.hasOption(Arguments.BUILD_SYSTEM)
                    && !checkOnly;
    humanReadable = arguments.hasOption(Arguments.READABLE);

    // Redundant for normal use, but it helps to assume warnings are not errors when running automated tests
    Loggers.setWarningsAsErrors(false);

    // Deal with warning flags
    if (arguments.hasOption(Arguments.WARNING)) {
      String flag = arguments.getWarningFlag();
      if (flag.equals("error")) Loggers.setWarningsAsErrors(true);
      else System.err.println("Unknown warning flag: " + flag);
    }

    String[] fileNames = arguments.getFiles();

    if (arguments.hasOption(Arguments.INFORMATION)) {
      if (fileNames.length > 0)
        throw new CommandLineException(
                "Requests for compiler information should not include files to compile");
    } else if (arguments.hasOption(Arguments.HELP)) {
      if (fileNames.length > 0)
        throw new CommandLineException(
                "Requests for help information should not include files to compile");
    } else if (arguments.hasOption(Arguments.BUILD_SYSTEM)) {
      if (fileNames.length > 0)
        throw new CommandLineException(
                "Input files should not be specified when building the system library");
      else
        addDirectories(
                config
                        .getSystem()
                        .get(Configuration.SOURCE)
                        .resolve("shadow"));
    } else if (fileNames.length > 0) {
      Map<Path, Path> imports = config.getImport();
      for (String file : fileNames) {
        Path path = null;
        for (Path _import : imports.keySet()) {
          Path candidate = _import.resolve(Paths.get(file));
          if (Files.exists(candidate)) path = candidate.toAbsolutePath().normalize();
          break;
        }

        if (path == null) throw new FileNotFoundException("Source file at " + file + " not found");
        else if (!path.toString().endsWith(".shadow"))
          throw new CommandLineException("Source file " + file + " does not end with .shadow");
        else files.add(path);
      }

    } else throw new CommandLineException("No input files");
  }

  public void run() throws IOException, ShadowException, ConfigurationException {
    // Print information
    // Must come after building configuration, since configuration helps
    // us find the correct clang installation
    if (arguments.hasOption(Arguments.INFORMATION)) Arguments.printInformation();

    // Print help
    if (arguments.hasOption(Arguments.HELP)) Arguments.printHelp();

    if (arguments.hasOption(Arguments.INFORMATION) || arguments.hasOption(Arguments.HELP))
      return;

    linkCommand.addAll(config.getLinkCommand());

    // Important settings
    List<Path> system = config.getSystem();
    Path systemInclude = system.get(Configuration.INCLUDE);
    Path systemSource = system.get(Configuration.SOURCE);
    boolean isCompile = !checkOnly;

    if (isCompile) checkClangVersion();

    // Begin the checking/compilation process
    long startTime = System.currentTimeMillis();

    List<Path> cFiles = new ArrayList<>();
    generateObjectFiles(cFiles);

    if (isCompile) {
      // Compile and add the C source files to get linked
      if (!compileCSourceFiles(systemInclude, cFiles)) {
        logger.error("Failed to compile one or more C source files.");
        throw new CompileException("FAILED TO COMPILE");
      }

      if (link) {
        logger.info("Building for target \"" + config.getTarget() + "\"");

        // Create linker output command
        Path outputFile;

        // See if an output file was specified
        if (arguments.hasOption(Arguments.OUTPUT)) {
          outputFile = Paths.get(arguments.getOutputFileArg());

          // Resolve it if necessary
          outputFile = files.get(0).resolveSibling(outputFile);
        } else {
          // Determine a path to the default output file
          Path outputName = BaseChecker.stripExtension(files.get(0));
          outputFile = properExecutableName(outputName);
        }

        linkCommand.add("-o");
        linkCommand.add(outputFile.toString());

        Path mainLL;
        if (config.getOs().equals("Windows")) {
          if (mainArguments) mainLL = Paths.get("MainWindows.ll");
          else mainLL = Paths.get("NoArgumentsWindows.ll");
        } else {
          if (mainArguments) mainLL = Paths.get("Main.ll");
          else mainLL = Paths.get("NoArguments.ll");
        }

        mainLL = systemSource.resolve(mainLL);
        BufferedReader main = Files.newBufferedReader(mainLL, UTF8);

        // Read main and compile into temporary object
        Map<Path, Path> imports = config.getImport();
        Path parent = getBinaryPath(files.get(0), imports).getParent();
        Path temporaryMain = Files.createTempFile(parent, "main", ".o");
        StringBuilder builder = new StringBuilder();
        String line = main.readLine();

        while (line != null) {
          line = line.replace("shadow.test..Test", mainClass) + System.lineSeparator();
          builder.append(line);
          line = main.readLine();
        }
        main.close();

        linkCommand.add(
                compileIrStream(
                        new ByteArrayInputStream(builder.toString().getBytes()), temporaryMain));

        logger.info("Linking object files...");

        // Usually clang
        Process link =
                new ProcessBuilder(linkCommand)
                        .redirectOutput(Redirect.INHERIT)
                        .redirectError(Redirect.INHERIT)
                        .start();
        try {
          if (link.waitFor() != 0) throw new CompileException("FAILED TO LINK");
        } catch (InterruptedException | CompileException ignored) {
        } finally {
          link.destroy();
          Files.delete(temporaryMain);
        }
      }
      logger.info("SUCCESS: Built in " + (System.currentTimeMillis() - startTime) + "ms");
    }
  }

  public static Path properExecutableName(Path executableName) {
    if (System.getProperty("os.name").contains("Windows"))
      executableName = BaseChecker.addExtension(executableName, ".exe");
    return executableName.normalize().toAbsolutePath();
  }

  private void addDirectories(Path directory) {
    try (Stream<Path> stream = Files.list(directory)) {
      stream
              .filter(file -> Files.isDirectory(file) && !file.getFileName().toString().equals("test"))
              .forEach(this::addShadowFiles);
    } catch (IOException ignored) {
    }
  }

  private void addShadowFiles(Path directory) {
    try (Stream<Path> stream = Files.walk(directory)) {
      stream.filter(file -> file.toString().endsWith(".shadow")).forEach(files::add);
    } catch (IOException ignored) {
    }
  }




  // Check clang version using lexical comparison
  private static void checkClangVersion() throws ConfigurationException {
    String clangVersion = Configuration.getClangVersion();

    int comparison = compareVersions(clangVersion, MINIMUM_CLANG_VERSION);

    if (comparison < 0) {
      String error =
          "clang version "
              + MINIMUM_CLANG_VERSION
              + " or higher is required for Shadow "
              + VERSION
              + ", but ";
      if (clangVersion.isEmpty()) error += "no clang installation found.";
      else error += "version " + clangVersion + " found.";
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

  public static int compareVersions(String v1, String v2) {
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

  public static Path getBinaryPath(Path path, Map<Path, Path> sourceToBinaries)
      throws ConfigurationException {
    Path parent = path;
    while (parent != null && !sourceToBinaries.containsKey(parent)) parent = parent.getParent();

    if (parent == null)
      throw new ConfigurationException("Binary path not found for source file " + path);

    Path binaryParent = sourceToBinaries.get(parent);
    Path fromParentToChild = parent.relativize(path);
    return binaryParent.resolve(fromParentToChild);
  }

  // Assumes compileCommand contains two empty slots at the end:
  // The first is for the output file name
  // The last is for the input file name
  private boolean compileCSourceFile(
      Path cFile, Path binaryPath, List<String> compileCommand)
      throws IOException {

    String binaryFile = binaryPath.toString();
    linkCommand.add(binaryFile);

    if (!Files.exists(binaryPath)
        || Files.getLastModifiedTime(binaryPath).compareTo(Files.getLastModifiedTime(cFile)) < 0) {
      logger.info("Generating assembly code for " + cFile.getFileName());
      compileCommand.set(compileCommand.size() - 2, binaryFile);
      compileCommand.set(compileCommand.size() - 1, cFile.toString());
      return runCCompiler(compileCommand, cFile.getParent());
    }

    logger.info("Using pre-existing assembly code for " + binaryPath.getFileName());
    return true;
  }

  private boolean compileCSourceFiles(
      Path includePath, List<Path> cShadowFiles)
      throws IOException, ConfigurationException {

    // No need to compile anything if there are no c-source files
    if (!Files.exists(includePath)) {
      logger.error("The include directory was not found and is necessary for the Shadow runtime.");
      return false;
    }

    // Compile the files to assembly, to be ready for linkage
    List<String> compileCommand = new ArrayList<>();

    if (config.getOs().equals("Mac")) {
      compileCommand.add("clang");
      String[] version = System.getProperty("os.version").split("\\.");
      compileCommand.add("-mmacosx-version-min=" + version[0] + "." + version[1]);
      compileCommand.add("-femulated-tls");
    } else compileCommand.add("clang");

    // Architecture, optimization, and exception support
    compileCommand.add("-m" + config.getArchitecture());
    compileCommand.add("-O3");
    compileCommand.add("-fexceptions");

    // Partial compilation
    compileCommand.add("-c");

    // Include directories to be in the search path of clang
    compileCommand.add("-I" + includePath);
    compileCommand.add("-I" + includePath.resolve("platform").resolve(config.getOs()));
    compileCommand.add(
        "-I" + includePath.resolve("platform").resolve("Arch" + config.getArchitecture()));

    // Input and output files
    compileCommand.add("-o");
    compileCommand.add(null); // Location for output name
    compileCommand.add(null); // Location for .c file

    Map<Path, Path> imports = config.getImport();

    for (Path cFile : cShadowFiles) {
      Path binaryPath = BaseChecker.addExtension(getBinaryPath(cFile, imports), ".o");
      if (!compileCSourceFile(cFile, binaryPath, compileCommand)) return false;
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
   * Ensures that .o files exist for all dependencies of a main-method-
   * containing class/file. This involves either finding an existing .o file
   * (which has been updated more recently than the corresponding source file)
   * or building a new one
   */
  private void generateObjectFiles(List<Path> cFiles)
      throws IOException, ShadowException, ConfigurationException {

    List<Path> system = config.getSystem();
    Path systemBinary = system.get(Configuration.BINARY);
    Path systemSource = system.get(Configuration.SOURCE);

    boolean isWindows = config.getOs().equals("Windows");
    Path unwindSource =
        systemSource.resolve(
            "Unwind" + (isWindows ? "Windows" : "") + config.getArchitecture() + ".ll");
    Path unwindBinary =
        systemBinary.resolve(
            "Unwind" + (isWindows ? "Windows" : "") + config.getArchitecture() + ".o");
    linkCommand.add(compileIrFile(unwindSource, unwindBinary));

    // Add platform-specific system code
    linkCommand.add(
            compileIrFile(
            systemSource.resolve(config.getOs() + ".ll"),
            systemBinary.resolve(config.getOs() + ".o")));

    // Add shared code
    linkCommand.add(
            compileIrFile(systemSource.resolve("Shared.ll"), systemBinary.resolve("Shared.o")));

    ErrorReporter reporter = new ErrorReporter(Loggers.TYPE_CHECKER);

    // TypeChecker generates a list of AST nodes corresponding to
    // classes needing compilation
    TypeChecker.TypeCheckerOutput typecheckerOutput =
        TypeChecker.typeCheck(
            files, reporter, checkOnly);

    List<Type> typesIncludingInner =
        typecheckerOutput.nodes.stream().map(Context::getType).collect(Collectors.toList());
    typecheckerOutput.nodes.stream()
        .map(Context::getType)
        .map(Type::recursivelyGetInnerTypes)
        .forEach(typesIncludingInner::addAll);

    reporter.printAndReportErrors();

    ConstantFieldInterpreter.evaluateConstants(typecheckerOutput.packageTree, typesIncludingInner);

    // TODO: Add enum evaluations here (right after constants?)

    if (link) {
      // Set data for main class
      Type mainType = typecheckerOutput.main.getType();
      mainClass = mainType.toString(Type.MANGLE);
      SequenceType arguments = new SequenceType(new ArrayType(Type.STRING));
      if (mainType.getMatchingMethod("main", arguments) != null) mainArguments = true;
      else if (mainType.getMatchingMethod("main", new SequenceType()) != null)
        mainArguments = false;
      else
        throw new CompileException(
            "File " + files.get(0) + " does not contain an appropriate main() method");
    }

    try {
      for (Context node : typecheckerOutput.nodes) {
        // As an optimization, print .meta files for the .shadow files being checked
        // Attributes never generate .meta files because their original .shadow files are
        // interpreted
        if (!node.isFromMetaFile()) TypeChecker.printMetaFile(node);

        Path file = node.getSourcePath();

        if (checkOnly) {
          // Performs checks to make sure all paths return, there is
          // no dead code, etc.
          // No need to check interfaces or .meta files (no code in
          // those cases)
          if (!node.isFromMetaFile() && !(node.getType() instanceof InterfaceType))
            optimizeTAC(new TACBuilder().build(node), reporter);
        } else {
          Path path = BaseChecker.stripExtension(file);
          Path name = path.getFileName();

          Type type = node.getType();
          String className = typeToFileName(type);
          Path cFile = file.resolveSibling(className + ".c");
          if (Files.exists(cFile)) cFiles.add(cFile);

          Path irFile = file.resolveSibling(className + ".ll");
          Path binaryPath = node.getBinaryPath();

          Path nativeFile = file.resolveSibling(className + ".native.ll");
          Path nativeObject = BaseChecker.changeExtension(binaryPath, ".native.o");

          // If the LLVM IR bitcode or compiled object code didn't exist, the full .shadow file would
          // have been used (except for attributes, which are always interpreted)
          if (node.isFromMetaFile()) {
            if (node.getType() instanceof AttributeType)
              logger.info("Interpreting Shadow for " + name);
            else {
              logger.info("Using pre-existing object code for " + name);
              if (Files.exists(binaryPath)) linkCommand.add(binaryPath.toString());
              else if (Files.exists(irFile))
                linkCommand.add(compileIrFile(irFile, binaryPath));
              else throw new CompileException("File not found: " + binaryPath);
            }
          } else {
            if (node.getType() instanceof AttributeType)
              logger.info("Interpreting Shadow for " + name);
            else logger.info("Generating object code for " + name);
            // Gets top level class
            TACModule module = optimizeTAC(new TACBuilder().build(node), reporter);
            if (reporter.getErrorList().isEmpty())
              linkCommand.add(compileShadowFile(file, binaryPath, module));
          }

          if (Files.exists(nativeFile))
            linkCommand.add(compileIrFile(nativeFile, nativeObject));
        }

        reporter.printAndReportErrors();
      }
    } catch (TypeCheckException e) {
      logger.error(files.get(0) + " FAILED TO TYPE CHECK");
      throw e;
    } catch (RuntimeException e) {
      if (e.getCause() instanceof ShadowException) throw (ShadowException) e.getCause();
      else throw e;
    }
  }

  private Process getCompiler(String objectFile) throws IOException {
    if (config.getOs().equals("Mac")) {
      return new ProcessBuilder(
              config.getClang(),
              // "-mtriple",
              // config.getTarget(),
              config.getOptimizationLevel(), /*config.getDataLayout(),*/
              "-femulated-tls", // needed for Mac
              "-c",
              "-x",
              "ir",
              // "--filetype=obj",
              "-w", // Warning: Turns off all warnings
              "-o",
              objectFile,
              "-")
          .redirectError(Redirect.INHERIT)
          .start();
    } else {
      return new ProcessBuilder(
              config.getClang(),
              // "-mtriple",
              // config.getTarget(),
              config.getOptimizationLevel(),
              "-c",
              "-x",
              "ir",
              // "--filetype=obj",
              "-w", // Warning: Turns off all warnings
              "-o",
              objectFile,
              "-")
          .redirectError(Redirect.INHERIT)
          .start();
    }
  }

  private static void createDirectories(Path binaryPath) throws CompileException {
    Path parent = binaryPath.getParent();
    if (!Files.isDirectory(parent)) {
      try {
        Files.createDirectories(parent);
      } catch (IOException e) {
        throw new CompileException("COULD NOT CREATE DIRECTORY " + parent);
      }
    }
  }

  private String compileIrModule(Path shadowFile, Path binaryPath, TACModule module)
      throws CompileException {
    createDirectories(binaryPath);
    String binaryFile = binaryPath.toString();

    boolean success = false;
    Process compile = null;
    IrOutput output;

    try {
      compile = getCompiler(binaryFile);
      output = new IrOutput(new BufferedOutputStream(compile.getOutputStream()));
      output.build(module);
      output.close();
      if (compile.waitFor() != 0) throw new CompileException("FAILED TO COMPILE " + binaryFile);
      success = true;
    } catch (IOException | InterruptedException e) {
      throw new CompileException("FAILED TO COMPILE " + binaryFile);
    } catch (ShadowException e) {
      logger.error("FAILED TO COMPILE " + shadowFile);
      throw new CompileException(e.getMessage());
    } finally {
      try {
        if (!success) Files.deleteIfExists(binaryPath);
      } catch (IOException ignored) {
      }
      if (compile != null) compile.destroy();
    }
    return binaryFile;
  }

  private String compileIrStream(InputStream stream, Path binaryPath)
      throws CompileException {
    createDirectories(binaryPath);
    String binaryFile = binaryPath.toString();

    boolean success = false;
    Process compile = null;

    try {
      compile = getCompiler(binaryFile);
      new Pipe(stream, compile.getOutputStream()).start();
      if (compile.waitFor() != 0) throw new CompileException("FAILED TO COMPILE " + binaryFile);
      success = true;
    } catch (IOException | InterruptedException e) {
      throw new CompileException("FAILED TO COMPILE " + binaryFile);
    } finally {
      if (!success) {
        try {
          Files.deleteIfExists(binaryPath);
        } catch (IOException ignored) {
        }
      }
      if (compile != null) compile.destroy();
    }

    return binaryFile;
  }

  private String compileIrFile(Path irPath, Path binaryPath) throws CompileException {
    try {
      if (Files.exists(binaryPath) && Files.getLastModifiedTime(binaryPath).compareTo(Files.getLastModifiedTime(irPath)) >= 0)
        return binaryPath.toString();
      else
        return compileIrStream(Files.newInputStream(irPath), binaryPath);
    } catch (IOException e) {
      throw new CompileException("FAILED TO COMPILE " + irPath);
    }
  }

  private String compileShadowFile(Path shadowFile, Path binaryPath, TACModule module)
      throws CompileException {

    if (humanReadable) {
      try {
        // Generate LLVM IR
        Path irFile = BaseChecker.changeExtension(shadowFile, ".ll");
        OutputStream out = Files.newOutputStream(irFile);
        IrOutput output = new IrOutput(new BufferedOutputStream(out));
        output.build(module);
        output.close();
        out.close();
        return compileIrFile(irFile, binaryPath);
      } catch (IOException | ShadowException e) {
        logger.error("FAILED TO COMPILE " + shadowFile);
        throw new CompileException(e.getMessage());
      }
    } else return compileIrModule(shadowFile, binaryPath, module);
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
        // Check field initialization
        class_.checkFieldInitialization(reporter, graphs);
        Type type = class_.getType();

        // Check that attribute fields are initialized, but don't worry if they aren't used
        if (type instanceof AttributeType) continue;

        // Give warnings if fields are never used
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
}
