package shadow.doctool;

import org.apache.logging.log4j.Logger;
import shadow.*;
import shadow.doctool.output.DocumentationTemplate;
import shadow.doctool.output.StandardTemplate;
import shadow.parse.ParseException;
import shadow.typecheck.Package;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.type.Type;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentationTool {
  private static final String SRC_EXTENSION = ".shadow";
  private static final String PKG_INFO_FILE = "package-info.txt";

  private static final Logger logger = Loggers.DOC_TOOL;

  public static void main(String[] args) {
    try {
      document(args);
    } catch (FileNotFoundException e) {
      System.err.println("FILE NOT FOUND: " + e.getLocalizedMessage());
      System.exit(Main.Error.FILE_NOT_FOUND_ERROR.ordinal());
    } catch (DocumentationException e) {
      System.err.println("DOCUMENTATION ERROR: " + e.getLocalizedMessage());
      e.printStackTrace();
      System.exit(Main.Error.DOCUMENTATION_ERROR.ordinal());
    } catch (ParseException e) {
      System.err.println("PARSE ERROR: " + e.getLocalizedMessage());
      System.exit(Main.Error.PARSE_ERROR.ordinal());
    } catch (IOException e) {
      System.err.println("FILE DEPENDENCY ERROR: " + e.getLocalizedMessage());
      e.printStackTrace();
      System.exit(Main.Error.TYPE_CHECK_ERROR.ordinal());
    } catch (org.apache.commons.cli.ParseException e) {
      System.err.println("COMMAND LINE ERROR: " + e.getLocalizedMessage());
      DocumentationArguments.printHelp();
      System.exit(Main.Error.COMMAND_LINE_ERROR.ordinal());
    } catch (ConfigurationException e) {
      System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
      DocumentationArguments.printHelp();
      System.exit(Main.Error.CONFIGURATION_ERROR.ordinal());
    } catch (TypeCheckException e) {
      System.err.println("TYPE CHECK ERROR: " + e.getLocalizedMessage());
      System.exit(Main.Error.TYPE_CHECK_ERROR.ordinal());
    } catch (ShadowException e) {
      System.err.println("ERROR IN FILE: " + e.getLocalizedMessage());
      e.printStackTrace();
      System.exit(Main.Error.TYPE_CHECK_ERROR.ordinal());
    } catch (HelpRequestedException ignored) {
    }
  }

  // TODO: Subdivide this into more manageable pieces
  public static void document(String[] args)
      throws org.apache.commons.cli.ParseException,
          ConfigurationException,
          IOException,
          shadow.ShadowException,
          HelpRequestedException {
    // Detect and establish the current settings and arguments
    DocumentationArguments arguments = new DocumentationArguments(args);

    // Exit if help was requested (Arguments handles printing)
    if (arguments.hasOption(Arguments.HELP)) return;

    Configuration.buildConfiguration(
        arguments.getMainArguments()[0], arguments.getConfigFileArg(), false);

    /* TYPECHECKING */

    long startTime = System.currentTimeMillis(); // Time the type checking

    // Generate a list of source files from the command line arguments.
    // If packages/directories are specified, they will be searched for
    // source files
    Map<String, Documentation> pkgDocs = new HashMap<>();
    List<Path> sourceFiles = getRequestedFiles(arguments.getMainArguments(), pkgDocs);

    // Perform basic type-checking on each source file
    Set<Type> typesToDocument = DocumentationTypeChecker.typeCheck(sourceFiles);
    Set<Package> packagesToDocument = new HashSet<>();

    logger.info(
        "Successfully type-checked all files in "
            + (System.currentTimeMillis() - startTime)
            + "ms");

    /* DOCUMENTATION INFO ORGANIZATION */

    startTime = System.currentTimeMillis(); // Time the documentation

    Path currentDirectory = Paths.get(File.separator).toAbsolutePath().normalize();

    // If a directory was provided use it. Otherwise, create docs/ in the
    // current working directory
    Path outputDirectory;
    if (arguments.hasOption(DocumentationArguments.OUTPUT_DIR))
      outputDirectory = currentDirectory.resolve(arguments.getOutputDirectory()).toAbsolutePath().normalize();
    else outputDirectory = Paths.get("docs").toAbsolutePath().normalize();

    // Capture visible inner classes for documentation
    List<Type> outerClasses = new ArrayList<>(typesToDocument);
    for (Type outer : outerClasses)
      for (Type inner : outer.getInnerTypes().values())
        if (inner.getModifiers().isPublic() || inner.getModifiers().isProtected())
          typesToDocument.add(inner);

    // Capture all packages of classes being documented
    for (Type type : typesToDocument) packagesToDocument.addAll(type.getAllPackages());

    // Associate packages with package-info files
    for (Package pkg : packagesToDocument) {
      String pkgName = pkg.getQualifiedName();
      // The default package cannot be documented
      if (!pkgName.isEmpty() && pkgDocs.containsKey(pkgName))
        pkg.setDocumentation(pkgDocs.get(pkgName));
    }

    /* FORMATTED DOCUMENTATION GENERATION */

    DocumentationTemplate template =
        new StandardTemplate(arguments.getTemplateArgs(), typesToDocument, packagesToDocument);
    template.write(outputDirectory);

    logger.info(
        "Successfully generated all documentation in "
            + (System.currentTimeMillis() - startTime)
            + "ms");
  }

  /**
   * Locates all requested source files, either specified directly or within specified
   * packages/directories. Verifies that the files actually exist
   */
  public static List<Path> getRequestedFiles(
      String[] givenPaths, Map<String, Documentation> pkgDocs)
      throws IOException, ShadowException, ConfigurationException {
    List<Path> sourceFiles = new ArrayList<>();
    Map<Path, Path> imports = Configuration.getConfiguration().getImport();
    Path current = null;
    for (String path : givenPaths) {

      for (Path _import : imports.keySet()) {
        Path candidate = _import.resolve(Paths.get(path));
        if (Files.exists(candidate)) current = candidate.toAbsolutePath().normalize();
        break;
      }

      // Ensure that the source file exists
      if (current == null) throw new FileNotFoundException("File at " + path + " not found");

      // If the file is a directory, process it as a package
      if (Files.isDirectory(current)) sourceFiles.addAll(getPackageFiles(current, true, pkgDocs));
      else if (current.toString().endsWith(SRC_EXTENSION)) sourceFiles.add(current);
      else if (current.getFileName().toString().equals(PKG_INFO_FILE))
        processPackageInfo(current, pkgDocs);
      else
        // Only do this for explicitly requested files
        throw new DocumentationException(
            "File at "
                + current
                + " is not a package "
                + "directory, "
                + PKG_INFO_FILE
                + " file, or a source "
                + "file ending in "
                + SRC_EXTENSION);
    }
    return sourceFiles;
  }

  /**
   * Finds all Shadow source files within a directory (a package)
   *
   * @param recursive Determines whether subdirectories/subpackages are also searched
   */
  public static List<Path> getPackageFiles(
      Path directory, boolean recursive, Map<String, Documentation> pkgDocs)
      throws IOException, ShadowException {
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
      List<Path> files = new ArrayList<>();

      for (Path filePath : stream) {
        // Capture all source files
        if (filePath.toString().endsWith(SRC_EXTENSION)) files.add(filePath);
        // Capture any package-info files
        else if (filePath.getFileName().toString().equals(PKG_INFO_FILE))
          processPackageInfo(filePath, pkgDocs);
        // Recurse into subdirectories if desired
        else if (recursive && Files.isDirectory(filePath))
          files.addAll(getPackageFiles(filePath, true, pkgDocs));
      }
      return files;
    }
  }

  private static final Pattern pkgDeclaration =
      Pattern.compile("package[ \\t\\f]+([a-zA-Z][a-zA-Z0-9_]*(:[a-zA-Z][a-zA-Z0-9_]*)*);\\s*$");

  /**
   * Captures the documentation from a package-info file and places it into a central map for later
   * use
   */
  private static void processPackageInfo(Path infoFile, Map<String, Documentation> pkgDocs)
      throws IOException, ShadowException {
    /* Get the contents of the file */
    String declarationLine;
    DocumentationBuilder docBuilder;
    try (BufferedReader info = Files.newBufferedReader(infoFile, StandardCharsets.UTF_8)) {
      declarationLine = null;
      docBuilder = new DocumentationBuilder();
      while (info.ready()) {
        String line = info.readLine();
        if (info.ready()) // Capture all but the last line as comment text
        docBuilder.appendLine(line);
        else declarationLine = line; // The last line is the declaration
      }
    }
    if (declarationLine == null)
      throw new DocumentationException("No lines in file: " + infoFile.toAbsolutePath());

    /* Parse the package declaration and doc comment */
    Matcher matcher = pkgDeclaration.matcher(declarationLine);
    if (!matcher.find() || matcher.start() != 0)
      throw new DocumentationException(
          "No valid package declaration on" + " last line: " + infoFile.toAbsolutePath());
    String pkgName = matcher.group(1);
    if (!insideValidDirectory(pkgName, infoFile))
      throw new DocumentationException(
          "Declaration of package "
              + pkgName
              + " does not match file path: "
              + infoFile.toAbsolutePath());

    /* Add the package documentation to the central map */
    if (pkgDocs.containsKey(pkgName))
      logger.warn(
          "Already found a package-info file for "
              + pkgName
              + ", ignoring: "
              + infoFile.toAbsolutePath());
    pkgDocs.put(pkgName, docBuilder.process());
  }

  /** Determines whether a package-info file is in a valid directory */
  private static boolean insideValidDirectory(String pkgName, Path infoFile) {
    String[] packages = pkgName.split(":");
    Path current = infoFile.getParent();
    for (int i = packages.length - 1; i >= 0; --i) {
      if (current != null && !current.getFileName().toString().equals(packages[i])) return false;
      if (current == null) return false;
      current = current.getParent();
    }
    return true;
  }
}
