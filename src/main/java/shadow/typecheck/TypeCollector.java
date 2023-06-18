/*
 * Copyright 2017 Team Shadow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package shadow.typecheck;

import org.antlr.v4.runtime.RuleContext;
import shadow.*;
import shadow.doctool.Documentation;
import shadow.parse.Context;
import shadow.parse.ParseChecker;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.*;
import shadow.typecheck.Package.PackageException;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The <code>TypeCollector</code> class is central to the first stage of type-checking. It is given
 * one or more files to type-check and collects all the types imported by these files so that there
 * is enough information for full type-checking and compilation.
 *
 * @author Barry Wittman
 */
@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class TypeCollector extends ScopedChecker {

  // Map of types to the AST nodes that define them, useful for error messages.
  private final Map<Type, Context> typeTable;
  // Map of file paths (without extensions) to nodes.
  private final Map<Path, Context> fileTable = new HashMap<>();
  private final boolean useSourceFiles;
  private final boolean typeCheckOnly;

  // Holds all of the imports we know about.
  private final Map<String, PathWithContext> importedTypes =
      new HashMap<>(); // Type name -> file path and import statement
  private final Set<Path> usedTypes = new HashSet<>(); // File paths

  /* Paths where we can search for imports.
  The LinkedHashMap ensures
  1) Identical paths will only appear once
  2) The order of insertion will be preserved, searching the first imports first
  3) Source paths map to binary paths
  */
  private final List<Path> importPaths = new ArrayList<>();

  // Store standard imports once for efficiency
  private final Map<String, PathWithContext> standardImportedTypes;
  private final Path standardSourcePath;
  private Path currentFile;
  private Type mainType = null;
  private String currentName = "";

  // A stack of metadata about the currently enclosing class declarations
  private final Deque<TypeDeclaration> typeDeclarations = new LinkedList<>();

  /**
   * Metadata associated with a class-like type declaration - includes the names of fields, methods,
   * inner types, and type parameters.
   *
   * <p>"Class-like" types include interfaces, enums, and the outermost compilation unit.
   */
  private static class TypeDeclaration {
    private final Set<String> methods = new HashSet<>();
    private final Set<String> fields = new HashSet<>();
    private final Set<String> localTypes = new HashSet<>();
    private final Set<String> typeParameters = new HashSet<>();

     public TypeDeclaration() {}

    public void addMethod(String method) {
      methods.add(method);
    }

    public void addField(String field) {
      fields.add(field);
    }

    public void addLocalType(String type) {
      localTypes.add(type);
    }

    public void addTypeParameter(String type) {
      typeParameters.add(type);
    }

    public boolean containsMethod(String method) {
      return methods.contains(method);
    }

    public boolean containsField(String field) {
      return fields.contains(field);
    }

    public boolean containsLocalType(String type) {
      return localTypes.contains(type);
    }

    public boolean containsTypeParameter(String type) {
      return typeParameters.contains(type);
    }
  }

  // Java is stupid
  private static class PathWithContext {
    public final Path source;
    public final NameContext context;

    public PathWithContext(Path sourcePath, NameContext context) {
      this.source = sourcePath;
      this.context = context;
    }
  }
  /**
   * Creates a new <code>TypeCollector</code> with the given tree of packages.
   *
   * @param p package tree
   * @param useSourceFiles if true, always use <tt>.shadow</tt> instead of <tt>.meta</tt> files
   */
  public TypeCollector(
      Package p, ErrorReporter reporter, boolean useSourceFiles, boolean typeCheckOnly)
      throws ConfigurationException, IOException {
    super(p, reporter);
    this.useSourceFiles = useSourceFiles;
    this.typeCheckOnly = typeCheckOnly;
    typeTable = new HashMap<>();

    List<Path> systemPaths = Configuration.getConfiguration().getSystem();
    standardSourcePath = systemPaths.get(Configuration.SOURCE).resolve("shadow").resolve("standard");
    standardImportedTypes = getStandardImports(standardSourcePath);
  }

  private TypeCollector(
      Package p,
      Map<Type, Context> typeTable,
      Path standardSourcePath,
      Map<String, PathWithContext> standardImportedTypes,
      ErrorReporter reporter,
      boolean useSourceFiles,
      boolean typeCheckOnly) {
    super(p, reporter);
    this.typeTable = typeTable;
    this.useSourceFiles = useSourceFiles;
    this.typeCheckOnly = typeCheckOnly;

    // Standard imports
    this.standardSourcePath = standardSourcePath;
    this.standardImportedTypes = standardImportedTypes;
  }

  /**
   * Gets the main type used when collecting types.
   *
   * @return main type if specified, <code>null</code> otherwise
   */
  public Type getMainType() {
    return mainType;
  }

  /**
   * Gets the table that maps each file path (without extension) to the node of the top-level class,
   * enum, interface, or exception declared in that file.
   *
   * @return map from files to nodes
   */
  public Map<Path, Context> getFileTable() {
    return fileTable;
  }

  /**
   * Calls <code>collectTypes</code> with one main file. The main file should be an absolute,
   * normalized (no redundant dots) path.
   *
   * @param mainFile main file to be type-checked or compiled
   * @return map from types to nodes
   * @throws ShadowException thrown if there's a problem collecting types
   * @throws IOException thrown if files are inaccessible
   * @throws ConfigurationException thrown if there's a problem with Configuration
   */
  public Map<Type, Context> collectTypes(Path mainFile)
      throws ShadowException, IOException, ConfigurationException {
    List<Path> initialFiles = new ArrayList<>();
    mainFile = mainFile.normalize().toAbsolutePath();
    initialFiles.add(mainFile);
    return collectTypes(initialFiles, new HashMap<>(), true);
  }

  /**
   * Calls <code>collectTypes</code> with one main file, whose source is given in source. Used for
   * type-checking files currently being edited in an IDE.
   *
   * @param source the complete source code to check
   * @param mainFile main file to be type-checked or compiled
   * @return map from types to nodes
   * @throws ShadowException thrown if there's a problem collecting types
   * @throws IOException thrown if files are inaccessible
   * @throws ConfigurationException thrown if there's a problem with Configuration
   */
  @SuppressWarnings("unused")
  public Map<Type, Context> collectTypes(String source, Path mainFile)
      throws ShadowException, IOException, ConfigurationException {
    List<Path> initialFiles = new ArrayList<>();
    mainFile = mainFile.normalize().toAbsolutePath();
    initialFiles.add(mainFile);
    Map<Path, String> activeFiles = new HashMap<>();
    activeFiles.put(mainFile, source);
    return collectTypes(initialFiles, activeFiles, true);
  }

  /**
   * called for documentation or type-checking only.
   *
   * @param files files to be type-checked
   * @return map from types to nodes
   * @throws ShadowException thrown if there's a problem collecting types
   * @throws IOException thrown if files are inaccessible
   * @throws ConfigurationException thrown if there's a problem with Configuration
   */
  public Map<Type, Context> collectTypes(List<Path> files)
      throws ShadowException, IOException, ConfigurationException {
    return collectTypes(files, new HashMap<>(), false);
  }

  /*
   * Calls the full <code>collectTypes</code> and might call it a second time
   * if needed to determine what should be recompiled.
   */
  private Map<Type, Context> collectTypes(
      List<Path> files, Map<Path, String> activeFiles, boolean hasMain)
      throws ShadowException, IOException, ConfigurationException {
    Set<Path> mustCompile = new HashSet<>();
    Map<Path, TreeSet<Path>> dependencies = new HashMap<>();

    // Initial type collection
    collectTypes(files, hasMain, activeFiles, mustCompile, dependencies);

    /* Classes needing recompilation may trigger other files to get
     * recompiled. Changes in the number or organization of methods
     * or class information could invalidate compiled code.  Here, we
     * figure out which files need (re)compilation and redo the whole type
     * collection process.
     */
    if (getErrorReporter().getErrorList().size() == 0
        && !useSourceFiles
        && mustCompile.size() > 0) {
      // Create a new set, otherwise adding new recompilations can trigger unnecessary ones.
      Set<Path> updatedMustRecompile = new HashSet<>(mustCompile);

      // For all files that do not already need to be recompiled,
      // check to see if their dependencies do.
      for (Map.Entry<Path, TreeSet<Path>> entry : dependencies.entrySet())
        if (!updatedMustRecompile.contains(entry.getKey()))
          for (Path dependency : entry.getValue())
            if (mustCompile.contains(dependency)) {
              updatedMustRecompile.add(entry.getKey());
              break;
            }

      mustCompile = updatedMustRecompile;
      clear(); // Clears out all internal representations and types.

      // Collect types again with updated recompilation requirements.
      collectTypes(files, hasMain, activeFiles, mustCompile, null);
    }

    // Check packages for errors.
    checkPackageDirectories(packageTree);
    printAndReportErrors();

    // Return a table of all the types and their corresponding nodes.
    return typeTable;
  }
  /*
   * Does actual collection of types based on a list of files.
   * The map activeFiles contains the (perhaps updated) source of files
   * that might not be saved into files yet.  Usually, this information
   * comes from a file being edited in an IDE.
   */
  private void collectTypes(
      List<Path> files,
      boolean hasMain,
      Map<Path, String> activeFiles,
      Set<Path> mustCompile,
      Map<Path, TreeSet<Path>> dependencies)
      throws ShadowException, IOException, ConfigurationException {
    // Create and fill the initial set of files to be checked.
    TreeSet<Path> uncheckedFiles = new TreeSet<>();


    Path mainSource = null;
    if (files.isEmpty()) throw new ConfigurationException("No files provided for typechecking");

    for (Path file : files) {
      Path path = stripExtension(file);
      uncheckedFiles.add(path);

      // Assume the main file is the first and only file.
      if (hasMain)
        mainSource = path;
    }


    /* Check standard imports. */
    if (!Files.exists(standardSourcePath))
      throw new ConfigurationException("Invalid path to shadow:standard: " + standardSourcePath);

    TreeSet<Path> standardDependencies = new TreeSet<>();

    // Adds all files in the standard directory (including sub-directories)
    for (Map.Entry<String, PathWithContext> entry : standardImportedTypes.entrySet()) {
      Path file = entry.getValue().source;
      uncheckedFiles.add(file);
      standardDependencies.add(file);
    }

    /* A few io classes are absolutely necessary for a console program. */
    Path ioSource = standardSourcePath.resolveSibling("io");
    if (!Files.exists(ioSource)) throw new ConfigurationException("Invalid path to io: " + ioSource);

    uncheckedFiles.add(ioSource.resolve("Console"));
    uncheckedFiles.add(ioSource.resolve("File"));
    uncheckedFiles.add(ioSource.resolve("IOException"));
    uncheckedFiles.add(ioSource.resolve("Path"));

    Map<Path, Path> imports = Configuration.getConfiguration().getImport();

    /* As long as there are unchecked files, remove one and process it. */
    while (!uncheckedFiles.isEmpty()) {
      Path canonical = uncheckedFiles.first();
      uncheckedFiles.remove(canonical);

      Path canonicalFile = BaseChecker.addExtension(canonical, ".shadow");
      String source = activeFiles.get(canonicalFile);
      Path binaryPath = BaseChecker.addExtension(Main.getBinaryPath(canonical, imports), ".o");

      // Depending on the circumstances, the compiler may choose to either
      // compile/recompile source files, or rely on existing binaries.
      if (Files.exists(canonicalFile)) {
        Path meta = BaseChecker.addExtension(canonical, ".meta");

        // If source compilation was not requested and the binaries exist
        // that are newer than the source, use those binaries.
        if (!useSourceFiles
            && !mustCompile.contains(canonical)
            && source == null
            &&
            // Always do the full .shadow file for the main file if typechecking
            (!typeCheckOnly || !hasMain || !files.get(0).equals(canonicalFile))
            &&
            // Only use .meta if it's newer than .shadow
            Files.exists(meta)
            && Files.getLastModifiedTime(meta).compareTo(Files.getLastModifiedTime(canonicalFile))
                >= 0
            &&
            // Also, only use .meta if we're not going to need to recompile it into an object file
            (typeCheckOnly
                || (Files.exists(binaryPath)
                    && Files.getLastModifiedTime(binaryPath).compareTo(Files.getLastModifiedTime(meta))
                        >= 0))) {
          canonicalFile = meta;
          // Loggers.SHADOW.info("Using meta file: " + meta);
        } else {
          mustCompile.add(canonical);
          // Loggers.SHADOW.info("Using Shadow file: " + canonical);
        }
      } else if (!useSourceFiles) canonicalFile = BaseChecker.addExtension(canonical, ".meta");

      currentFile = canonicalFile;

      // Use the semantic checker to parse the file
      ParseChecker checker = new ParseChecker(new ErrorReporter(Loggers.PARSER));
      CompilationUnitContext node;
      // If there's an updated source, use that
      // Otherwise, read from the file
      if (source != null) node = checker.getCompilationUnit(source, currentFile);
      else node = checker.getCompilationUnit(currentFile);
      checker.printAndReportErrors();

      node.setBinaryPath(binaryPath);

      // Make another collector to walk the current file.
      TypeCollector collector =
          new TypeCollector(
              packageTree, // Use our existing package
              typeTable,
              standardSourcePath,
              standardImportedTypes,
              getErrorReporter(),
              useSourceFiles,
              typeCheckOnly);

      // Keeping a current file gives us a file whose directory we can check against.
      collector.setCurrentFile(currentFile, node);
      collector.visit(node);

      fileTable.put(canonical, node);

      if (canonical.equals(mainSource)) mainType = node.getType();

      /* Track the dependencies for this file (if dependencies are being used).
       * If any of its dependencies need to be recompiled, this file will need
       * to be recompiled.
       */
      TreeSet<Path> dependencySet = null;

      if (dependencies != null) {
        dependencySet = new TreeSet<>(standardDependencies);
        dependencies.put(canonical, dependencySet);
      }

      for (Path _import : collector.usedTypes) {
        if (!fileTable.containsKey(_import)) uncheckedFiles.add(_import);

        if (dependencySet != null) dependencySet.add(_import);
      }
    }

    Collection<Type> packageLessTypes = packageTree.getTypes();
    if (mainType != null && packageLessTypes.size() > 0 && !packageLessTypes.contains(mainType)) {
      // Imported class has default package but the main type doesn't.
      // The only classes without a package that will be imported will be
      // in the same directory as the main type.
      // Implication: classes in the same directory have different packages.

      for (Type type : packageLessTypes) {

        addError(
            new TypeCheckException(
                Error.MISMATCHED_PACKAGE,
                "Type "
                    + type
                    + " belongs to the default package, but types defined in the same directory belong to other packages"));
      }
    }
  }

  private void setCurrentFile(Path currentFile, CompilationUnitContext node)
      throws ConfigurationException {
    this.currentFile = currentFile;

    // Possible sources for imports (order matters)
    importedTypes.clear();
    importPaths.clear();

    // If the file has package information, back up so that the import root is the above the package
    // information
    Path sourceParent = currentFile.getParent();
    if (node != null) {
      UnqualifiedNameContext unqualifiedName = null;
      if (node.classOrInterfaceDeclaration() != null)
        unqualifiedName = node.classOrInterfaceDeclaration().unqualifiedName();
      else if (node.enumDeclaration() != null)
        unqualifiedName = node.enumDeclaration().unqualifiedName();

      if (unqualifiedName != null) {
        String text = unqualifiedName.getText();
        if (!text.equals("default")) {
          String[] parts = text.split(":");
          for (int i = 0; i < parts.length; ++i) {
            sourceParent = sourceParent.getParent();
          }
        }
      }
    }

    importPaths.add(sourceParent.normalize());
    Configuration configuration = Configuration.getConfiguration();
    importPaths.addAll(configuration.getImport().keySet());
    importPaths.add(standardSourcePath);

    // Import everything from the standard library
    importedTypes.putAll(standardImportedTypes);

    // And everything from the current directory
    addImports(currentFile.getParent(), null);
  }

  /*
   * Add all the files in a directory as imports.
   */
  private boolean addImports(Path directory, NameContext context) {
    try (Stream<Path> stream = Files.list(directory)) {
      return stream
          .filter(
              file -> {
                String fileName = file.getFileName().toString();
                Path noExtension = stripExtension(file);
                if (fileName.endsWith(".shadow")) {
                  return true;
                }
                if (fileName.endsWith(".meta")) {
                  String typeName = noExtension.getFileName().toString();
                  return !Files.exists(file.resolveSibling(typeName + ".shadow"));
                }
                return false;
              })
          // Yucky stream way to make sure that calling addImport() on all files succeeds
          .allMatch(file -> addImport(file, context, true));
    } catch (IOException e) {
      return false;
    }
  }

  /*
   * Add a single file as an import.
   * Returns true if successful.
   * Returns false if that type name was already imported.
   */
  private boolean addImport(Path file, NameContext context, boolean directory) {
    Path filePath = stripExtension(file);

    String typeName;
    if (context == null || directory) typeName = filePath.getFileName().toString();
    else
      // Last identifier is type name
      typeName = context.Identifier(context.Identifier().size() - 1).getText();

    // Put in list of imported types and see if anything else has the same name
    PathWithContext oldPathWithContext =
        importedTypes.put(typeName, new PathWithContext(filePath, context));

    // If something else had the same name and also had a context, two imports are colliding
    // (Automatic imports like the standard library and the current directory don't have import
    // contexts)
    if (oldPathWithContext != null && oldPathWithContext.context != null) {
      // If it's a directory, put the old thing back.
      // Because it would otherwise be annoying, directory imports that happen to have a collision
      // won't overwrite the existing.
      // Likewise, the compiler will issue a warning instead of an error.
      if (directory) importedTypes.put(typeName, oldPathWithContext);

      return false;
    }

    // For .meta files, an import signals that the type was actually used
    if (currentFile.getFileName().toString().endsWith(".meta")) usedTypes.add(filePath);

    return true;
  }

  public Map<String, PathWithContext> getStandardImports(Path standardSourcePath)
      throws IOException {
    // Map type names to paths
    try (Stream<Path> stream = Files.walk(standardSourcePath)) {
      return stream
          .filter(file -> file.toString().endsWith(".shadow"))
          .collect(
              Collectors.toUnmodifiableMap(
                  // Type name
                  file -> stripExtension(file.getFileName().toString()),
                  // Path
                  file ->  new PathWithContext(stripExtension(file), null)));
    }
  }

  /*
   * Checks to make sure that all types in a package are defined by files in the same directory.
   */
  private void checkPackageDirectories(Package _package) {
    Collection<Type> types = _package.getTypes();

    Type firstType = null;
    Path path1 = null;

    /* Gets the directory of the first type and compares all others to it. */
    if (types.size() > 1) {
      for (Type type : types) {
        if (firstType == null) {
          firstType = type;
          // path1 = typeTable.get( type ).getFile().getParentFile();
          path1 = typeTable.get(type).getSourcePath().getParent();
        } else {
          Path path2 = typeTable.get(type).getSourcePath().getParent();
          if (!path1.equals(path2))
            addWarning(
                new TypeCheckException(
                    Error.MISMATCHED_PACKAGE,
                    "Type "
                        + firstType
                        + " and "
                        + type
                        + " both belong to package "
                        + _package
                        + " but are defined in different directories"));
        }
      }
    }

    // Recursively check child packages.
    for (Package child : _package.getChildren().values()) checkPackageDirectories(child);
  }

  /*
   * Creates a new type and puts it in the correct package.
   * If the type is a standard type, it will also have a static
   * type variable associated with it.
   */
  private Type createType(
      Context node,
      Modifiers modifiers,
      Documentation documentation,
      String kind,
      String packageName,
      String name) {
    String typeName;
    Type type = Type.UNKNOWN;
    node.setType(type); // will be Unknown unless set with a real type

    if (packageName != null) {
      if (currentType == null) currentPackage = packageTree.addQualifiedPackage(packageName);
      else {
        addError(node, Error.INVALID_PACKAGE, "Package can only be defined by outermost classes");
        return type;
      }
    }

    /* For outer types, check that type name matches file name (if defined in a file),
     * and that the package name matches the directory path. */
    if (currentType == null && node.getSourcePath() != null) {
      Path file = node.getSourcePath();
      String fileName = stripExtension(file.getFileName().toString());
      if (!fileName.equals(name)) { // Check file name.
        addError(
            node,
            Error.INVALID_FILE,
            "Type "
                + name
                + " must be declared in a file named "
                + name
                + ".shadow or "
                + name
                + ".meta");
        return type;
      } else { // Check package path.
        Package _package = currentPackage;
        Path parent = file.getParent();

        while (_package != packageTree && parent != null) {
          if (!_package.getName().equals(parent.getFileName().toString())) {
            addError(
                node,
                Error.INVALID_PACKAGE,
                "Type "
                    + name
                    + " cannot be added to package "
                    + currentPackage.getQualifiedName()
                    + " unless it is defined in directory "
                    + currentPackage.getPath());
            return type;
          }

          parent = parent.getParent();
          _package = _package.getParent();
        }
      }
    } else { // inner type
      // if the outer type is locked, then make this inner type locked too
      if (currentType.getModifiers().isLocked()) modifiers.addModifier(Modifiers.LOCKED);
    }

    // Fix type names for primitive types.
    if (currentPackage.getQualifiedName().equals("shadow:standard")
        && (name.equals("Boolean")
            || name.equals("Byte")
            || name.equals("Code")
            || name.equals("Short")
            || name.equals("Int")
            || name.equals("Long")
            || name.equals("Float")
            || name.equals("Double")
            || name.equals("UByte")
            || name.equals("UInt")
            || name.equals("ULong")
            || name.equals("UShort"))) name = name.toLowerCase();

    // Current name contains package or outer class.
    if (currentType == null) typeName = currentName + name;
    else typeName = currentName + ":" + name;

    if (lookupType(node, typeName) != null) {
      addError(node, Error.MULTIPLY_DEFINED_SYMBOL, "Type " + typeName + " already defined");
      return type;
    } else { // Set kind of type and create it with the appropriate name and outer type.
      switch (kind) {
        case "class":
          ClassType classType = new ClassType(name, modifiers, documentation, currentType);
          captureClassType(classType, typeName);
          type = classType;
          break;
        case "enum":
          type = new EnumType(name, modifiers, documentation, currentType);
          break;
        case "exception":
          ExceptionType exceptionType = new ExceptionType(name, modifiers, documentation, currentType);
          captureExceptionType(exceptionType, typeName);
          type = exceptionType;
          break;
        case "interface":
          InterfaceType interfaceType = new InterfaceType(name, modifiers, documentation);
          captureInterfaceType(interfaceType, typeName);
          type = interfaceType;
          break;
        case "singleton":
          SingletonType singletonType = new SingletonType(name, modifiers, documentation, currentType);
          captureSingletonType(singletonType, typeName);
          type = singletonType;
          break;
        case "attribute":
          AttributeType attributeType = new AttributeType(name, documentation, currentType);
          captureAttributeType(attributeType, typeName);
          type = attributeType;
          break;
      }

      // Put new type inside outer type, if it exists.
      if (currentType != null) {
        currentType.addInnerType(name, type);
      }

      // Put new type in its package.
      try {
        currentPackage.addType(type);
      } catch (PackageException e) {
        addError(node, Error.INVALID_PACKAGE, e.getMessage());
        return type;
      }

      // Update the type of the declaration node and the current declaration type.
      node.setType(type);
      declarationType = type;
    }

    return type;
  }

  private void captureInterfaceType(InterfaceType interfaceType, String typeName) {
    if (currentPackage.getQualifiedName().equals("shadow:standard")) {
      switch (typeName) {
        case "CanAdd":
          Type.CAN_ADD = interfaceType;
          break;
        case "CanCompare":
          Type.CAN_COMPARE = interfaceType;
          break;
        case "CanDivide":
          Type.CAN_DIVIDE = interfaceType;
          break;
        case "CanEqual":
          Type.CAN_EQUAL = interfaceType;
          break;
        case "CanModulus":
          Type.CAN_MODULUS = interfaceType;
          break;
        case "CanMultiply":
          Type.CAN_MULTIPLY = interfaceType;
          break;
        case "CanNegate":
          Type.CAN_NEGATE = interfaceType;
          break;
        case "CanSubtract":
          Type.CAN_SUBTRACT = interfaceType;
          break;
        case "CanIndex":
          Type.CAN_INDEX = interfaceType;
          break;
        case "CanIndexNullable":
          Type.CAN_INDEX_NULLABLE = interfaceType;
          break;
        case "CanIndexStore":
          Type.CAN_INDEX_STORE = interfaceType;
          break;
        case "CanIndexStoreNullable":
          Type.CAN_INDEX_STORE_NULLABLE = interfaceType;
          break;
        case "CanIterate":
          Type.CAN_ITERATE = interfaceType;
          break;
        case "CanIterateNullable":
          Type.CAN_ITERATE_NULLABLE = interfaceType;
          break;
        case "CanRun":
          Type.CAN_RUN = interfaceType;
          break;
        case "Integer":
          Type.INTEGER = interfaceType;
          break;
        case "Iterator":
          Type.ITERATOR = interfaceType;
          break;
        case "IteratorNullable":
          Type.ITERATOR_NULLABLE = interfaceType;
          break;
        case "Number":
          Type.NUMBER = interfaceType;
          break;
      }
    }
  }

  private void captureExceptionType(ExceptionType exceptionType, String typeName) {
    if (currentPackage.getQualifiedName().equals("shadow:standard")) {
      switch (typeName) {
        case "AssertException":
          Type.ASSERT_EXCEPTION = exceptionType;
          break;
        case "CastException":
          Type.CAST_EXCEPTION = exceptionType;
          break;
        case "Exception":
          Type.EXCEPTION = exceptionType;
          break;
        case "IndexOutOfBoundsException":
          Type.INDEX_OUT_OF_BOUNDS_EXCEPTION = exceptionType;
          break;
        case "InterfaceCreateException":
          Type.INTERFACE_CREATE_EXCEPTION = exceptionType;
          break;
        case "UnexpectedNullException":
          Type.UNEXPECTED_NULL_EXCEPTION = exceptionType;
          break;
      }
    }
  }

  // Captures standard singleton types for later reference during compilation
  private void captureSingletonType(SingletonType singletonType, String typeName) {
    if (currentPackage.getQualifiedName().equals("shadow:standard")) {
      switch (typeName) {
        case "CurrentThread":
          Type.CURRENT_THREAD = singletonType;
      }
    }
  }

  // Captures standard class types for later reference during compilation
  private void captureClassType(ClassType classType, String typeName) {
    if (currentPackage.getQualifiedName().equals("shadow:standard")) {
      switch (typeName) {
        case "AddressMap":
          Type.ADDRESS_MAP = classType;
          break;
        case "Array":
          Type.ARRAY = classType;
          break;
        case "ArrayNullable":
          Type.ARRAY_NULLABLE = classType;
          break;
        case "Attribute":
          Type.ATTRIBUTE = classType;
          break;
        case "Class":
          Type.CLASS = classType;
          break;
        case "boolean":
          Type.BOOLEAN = classType;
          break;
        case "byte":
          Type.BYTE = classType;
          break;
        case "code":
          Type.CODE = classType;
          break;
        case "double":
          Type.DOUBLE = classType;
          break;
        case "Enum":
          Type.ENUM = classType;
          break; // the base class for enum is not an enum
        case "float":
          Type.FLOAT = classType;
          break;
        case "GenericClass":
          Type.GENERIC_CLASS = classType;
          break;
        case "int":
          Type.INT = classType;
          break;
        case "long":
          Type.LONG = classType;
          break;
        case "Method":
          Type.METHOD = classType;
          break;
        case "MethodTable":
          Type.METHOD_TABLE = classType;
          break;
        case "Object":
          Type.OBJECT = classType;
          break;
        case "short":
          Type.SHORT = classType;
          break;
        case "String":
          Type.STRING = classType;
          break;
        case "ubyte":
          Type.UBYTE = classType;
          break;
        case "uint":
          Type.UINT = classType;
          break;
        case "ulong":
          Type.ULONG = classType;
          break;
        case "ushort":
          Type.USHORT = classType;
          break;
        case "Thread":
          Type.THREAD = classType;
          break;
      }
    } else if (currentPackage.getQualifiedName().equals("shadow:natives")) {
      if ("Pointer".equals(typeName)) {
        Type.POINTER = classType;
      }
    }
  }

  // Captures first-party attribute types for later reference during compilation
  private void captureAttributeType(AttributeType type, String typeName) {
    if (currentPackage.getQualifiedName().equals("shadow:standard:attributes")) {
      switch (typeName) {
        case "ImportAssembly":
          AttributeType.IMPORT_ASSEMBLY = type;
          break;
        case "ExportAssembly":
          AttributeType.EXPORT_ASSEMBLY = type;
          break;
        case "ImportNative":
          AttributeType.IMPORT_NATIVE = type;
          break;
        case "ExportNative":
          AttributeType.EXPORT_NATIVE = type;
          break;
        case "ImportMethod":
          AttributeType.IMPORT_METHOD = type;
          break;
        case "ExportMethod":
          AttributeType.EXPORT_METHOD = type;
          break;
      }
    }
  }

  /*
   * Find the path associated with a given import name, either a file
   * or a whole directory.
   */

  private Path findPath(String name) {
    String separator = FileSystems.getDefault().getSeparator(); // Adds some platform independence.
    if (separator.equals("\\")) // Hack for Windows to deal with backslash escaping.
    separator = "\\\\";

    int atIndex = name.indexOf('@');
    boolean isDirectory = atIndex == -1;
    // If there's a colon after the @, we're importing an inner type,
    // but we only need the outer class for the file name
    if (atIndex != -1 && name.indexOf(':', atIndex + 1) != -1)
      name = name.substring(0, name.indexOf(':', atIndex + 1));

    String path = name.replaceAll(":", separator);
    if (path.startsWith("default@")) path = path.replaceFirst("default@", "");
    else path = path.replaceFirst("@", separator);

    for (Path importPath : importPaths) {
      // If an import path is relative, resolving it against the
      // current source file will make it absolute.
      // If it's absolute, no change will happen.
      importPath = currentFile.resolveSibling(importPath);

      if (isDirectory) {
        Path directory = importPath.resolve(path);
        if (Files.isDirectory(directory)) {
          return directory;
        }
      } else {
        Path shadowVersion = importPath.resolve(path + ".shadow");
        Path metaVersion = importPath.resolve(path + ".meta");
        if (Files.exists(shadowVersion)) {
          return shadowVersion;
        }
        else if (Files.exists(metaVersion)) {
          return metaVersion;
        }
      }
    }

    return null;
  }

  /**
   * Clears out the data structures within the collector, returning it to a state similar to just
   * after construction. Thie method calls its <code>super</code> version to clear out the <code>
   * BaseChecker</code> structures as well.
   */
  @Override
  public void clear() {
    super.clear();

    typeTable.clear();
    currentName = "";
    currentFile = null;
    mainType = null;
    fileTable.clear();
    importedTypes.clear();

    typeDeclarations.clear();

    Type.clearTypes();
  }

  /* AST visitor methods below this point. */

  @Override
  public Void visitCompilationUnit(ShadowParser.CompilationUnitContext ctx) {

    currentPackage = packageTree;

    currentName = "";

    // Special TypeDeclaration to keep track of the outermost type in a file
    TypeDeclaration compilationUnit = new TypeDeclaration();

    if (ctx.classOrInterfaceDeclaration() != null) {
      compilationUnit.addLocalType(ctx.classOrInterfaceDeclaration().Identifier().getText());
    } else if (ctx.enumDeclaration() != null) {
      compilationUnit.addLocalType(ctx.enumDeclaration().Identifier().getText());
    }

    typeDeclarations.addFirst(compilationUnit);
    visitChildren(ctx);
    typeDeclarations.removeFirst();

    return null;
  }

  // Imports handled here
  @Override
  public Void visitName(ShadowParser.NameContext ctx) {
    // visitChildren(ctx);  no need to visit children?
    String name = ctx.getText();

    // Single class
    if (name.contains("@")) {
      Path file = findPath(name);
      if (file == null)
        addError(ctx, Error.INVALID_IMPORT, "No file found for type " + name);
      else if (!addImport(file, ctx, false))
        addError(ctx, Error.IMPORT_COLLIDES, "Type " + name + " collides with existing import");
    }
    // Whole package
    else {
      Path directory = findPath(name);
      if (directory == null)
        addError(ctx, Error.INVALID_IMPORT, "No directory found for package " + name);
      else if (!addImports(directory, ctx))
        addWarning(
            ctx,
            Error.IMPORT_COLLIDES,
            "One or more types in package " + name + " collide with an existing import");
    }

    return null;
  }

  @Override
  public Void visitClassOrInterfaceDeclaration(
      ShadowParser.ClassOrInterfaceDeclarationContext ctx) {

    String packageName = null;
    if (ctx.unqualifiedName() != null) packageName = ctx.unqualifiedName().getText();

    if (ctx.isList() == null) { // no is list, so mark Object as used
      Path object = standardSourcePath.resolve("Object");
      usedTypes.add(object);
    }

    Type type =
        createType(
            ctx,
            ctx.getModifiers(),
            ctx.getDocumentation(),
            ctx.getChild(0).getText(),
            packageName,
            ctx.Identifier().getText());

    addMembers(ctx.classOrInterfaceBody().classOrInterfaceBodyDeclaration());

    visitChildren(ctx);

    // It's important to visit children first because how types are stored in the typeTable depends
    // on it
    type = type.getTypeWithoutTypeArguments();
    typeTable.put(type, ctx);
    // Set type on compilation unit
    if (!type.hasOuter()) ((Context) ctx.getParent()).setType(type);

    removeMembers();

    if (!type.hasOuter()) updateImports(type);

    return null;
  }

  private void updateImports(Type type) {
    // Let type know what it has imported.
    // Imported items should be empty at this point.
    Set<NameContext> usedDirectories = new HashSet<>();
    Set<NameContext> potentiallyUnusedDirectories = new HashSet<>();
    for (Entry<String, PathWithContext> entry : importedTypes.entrySet()) {
      // Only import the types that were actually used
      Path path = entry.getValue().source;
      NameContext context = entry.getValue().context;
      if (usedTypes.contains(path)) {
        type.addImportedItem(entry.getKey(), new Type.ImportInformation(path, context));
        if (context != null && context.unqualifiedName() == null) usedDirectories.add(context);
      } else if (context != null) {
        if (context.unqualifiedName() != null) // Fully qualified type
        addWarning(
              context,
              Error.UNUSED_IMPORT,
              "Import for type " + context.getText() + " is not used");
        else // Whole directory, but maybe something in it is used?
        potentiallyUnusedDirectories.add(context);
      }
    }

    for (NameContext context : potentiallyUnusedDirectories) {
      if (!usedDirectories.contains(context))
        addWarning(
            context,
            Error.UNUSED_IMPORT,
            "Import for package " + context.getText() + " is not used");
    }
  }

  /*
   * Records the fields and methods so that we can later determine whether
   * an identifier is a field, a method, or a type.
   * Also adds inner classes so that we don't go looking for them elsewhere.
   */
  private void addMembers(List<ClassOrInterfaceBodyDeclarationContext> declarations) {
    TypeDeclaration enclosingClass = new TypeDeclaration();

    for (ClassOrInterfaceBodyDeclarationContext context : declarations) {
      if (context.fieldDeclaration() != null) {
        for (VariableDeclaratorContext declarator : context.fieldDeclaration().variableDeclarator())
          enclosingClass.addField(declarator.generalIdentifier().getText());
      } else if (context.methodDeclaration() != null) {
        enclosingClass.addMethod(
            context.methodDeclaration().methodDeclarator().generalIdentifier().getText());
      } else if (context.classOrInterfaceDeclaration() != null) {
        enclosingClass.addLocalType(context.classOrInterfaceDeclaration().Identifier().getText());
      } else if (context.enumDeclaration() != null) {
        enclosingClass.addLocalType(context.enumDeclaration().Identifier().getText());
      }
    }

    typeDeclarations.addFirst(enclosingClass);
  }

  private void removeMembers() {
    typeDeclarations.removeFirst();
  }

  private boolean isMethod(String symbol) {
    return typeDeclarations.getFirst().containsMethod(symbol);
  }

  private boolean isField(String symbol) {
    return typeDeclarations.getFirst().containsField(symbol);
  }

  private boolean isLocalType(String symbol) {
    for (TypeDeclaration typeDeclaration : typeDeclarations)
      if (typeDeclaration.containsLocalType(symbol)) return true;

    return false;
  }

  @Override
  public Void visitEnumDeclaration(ShadowParser.EnumDeclarationContext ctx) {
    String packageName = null;
    if (ctx.unqualifiedName() != null) packageName = ctx.unqualifiedName().getText();

    Type type =
        createType(
            ctx,
            ctx.getModifiers(),
            ctx.getDocumentation(),
            "enum",
            packageName,
            ctx.Identifier().getText());

    addMembers(ctx.enumBody().classOrInterfaceBodyDeclaration());

    visitChildren(ctx);
    type = type.getTypeWithoutTypeArguments();
    typeTable.put(type, ctx);
    // Set type on compilation unit
    ((Context) ctx.getParent()).setType(type);

    removeMembers();

    if (!type.hasOuter()) updateImports(type);

    return null;
  }


  @Override
  public Void visitClassOrInterfaceBody(ShadowParser.ClassOrInterfaceBodyContext ctx) {
    // Set current type and name
    Type outerType = currentType;
    currentType = ((Context) ctx.getParent()).getType();
    currentName = currentType.getTypeName();

    visitChildren(ctx);

    // Go back to outer type and name
    currentType = outerType;
    if (currentType == null) currentName = currentPackage.getQualifiedName();
    else currentName = currentType.getTypeName();
    return null;
  }

  @Override
  public Void visitTypeParameters(ShadowParser.TypeParametersContext ctx) {
    visitChildren(ctx);

    if (declarationType != null) declarationType.setParameterized(true);

    return null;
  }

  @Override
  public Void visitTypeParameter(ShadowParser.TypeParameterContext ctx) {
    typeDeclarations.getFirst().addTypeParameter(ctx.Identifier().getText());

    visitChildren(ctx);

    return null;
  }

  @Override
  public Void visitClassOrInterfaceType(ShadowParser.ClassOrInterfaceTypeContext ctx) {
    //	(unqualifiedName '@')? Identifier ( ':' Identifier )* typeArguments?

    String name =
        ctx.Identifier(0)
            .getText(); // Only the outermost class (since that's the only one that can have its own
    // file)
    if (ctx.unqualifiedName() != null) {
      name = ctx.unqualifiedName().getText() + "@" + name;
      Path file = findPath(name);
      if (file == null) addError(ctx, Error.INVALID_IMPORT, "No file found for type " + name);
      else usedTypes.add(stripExtension(file));
    } else if (!isTypeParameter(name) && !isLocalType(name)) {
      if (importedTypes.containsKey(name)) usedTypes.add(importedTypes.get(name).source);
      else addError(ctx, Error.UNDEFINED_TYPE, "Type " + name + " not defined in current context");
    }

    visitChildren(ctx);

    return null;
  }

  private boolean isTypeParameter(String typeName) {
    return typeDeclarations.getFirst().containsTypeParameter(typeName);
  }

  @Override
  public Void visitPrimaryExpression(ShadowParser.PrimaryExpressionContext ctx) {

    ShadowParser.PrimaryPrefixContext prefix = ctx.primaryPrefix();

    // Triggers an import (adding a file to the compilation process)
    // since there's an UnqualifiedName@
    if (prefix.unqualifiedName() != null) {
      String name = prefix.unqualifiedName().getText() + "@" + prefix.generalIdentifier().getText();
      Path file = findPath(name);
      if (file == null) addError(ctx, Error.INVALID_IMPORT, "No file found for type " + name);
      else usedTypes.add(stripExtension(file));
    }
    // This case is complex:
    // There's an identifier that could be a class, but it's got to have a suffix.
    else if (prefix.generalIdentifier() != null && ctx.primarySuffix().size() > 0) {
      String symbol = prefix.generalIdentifier().getText();

      // A local variable, a member variable, or a method would hide a class name
      // (and a type parameter or a locally declared type is already covered),
      // so we eliminate those possibilities first.
      if (findSymbol(symbol) == null
          && !isField(symbol)
          && !isMethod(symbol)
          && !isTypeParameter(symbol)
          && !isLocalType(symbol)) {
        ShadowParser.PrimarySuffixContext suffix = ctx.primarySuffix(0); // First suffix

        // If the symbol comes before :class
        // or :CONSTANT or :InnerClass
        // or :create,
        // we know it's a class
        if (suffix.classSpecifier() != null
            || suffix.scopeSpecifier() != null
            || suffix.allocation() != null) {
          if (importedTypes.containsKey(symbol)) usedTypes.add(importedTypes.get(symbol).source);
          else
            addError(
                ctx, Error.UNDEFINED_TYPE, "Type " + symbol + " not defined in current context");
        }
        // If it comes before a method or a property, there's a chance it's a singleton class.
        // Thus, we look up the type, but we don't throw an error if we don't find it,
        // since an error message here would be confusing: The programmer probably misspelled a
        // variable name.
        else if (suffix.method() != null || suffix.property() != null) {
          if (importedTypes.containsKey(symbol)) usedTypes.add(importedTypes.get(symbol).source);
        }
      }
    }

    visitChildren(ctx);

    return null;
  }

  @Override
  public Void visitFormalParameter(ShadowParser.FormalParameterContext ctx) {
    visitChildren(ctx);
    addSymbol(ctx.Identifier().getText(), ctx);

    return null;
  }

  @Override
  public Void visitBlock(ShadowParser.BlockContext ctx) {
    openScope();
    visitChildren(ctx);
    closeScope();

    return null;
  }

  @Override
  public Void visitPrimitiveType(ShadowParser.PrimitiveTypeContext ctx) {
    // No children
    // ctx.setType(nameToPrimitiveType( ctx.getText() ) );
    return null;
  }

  @Override
  public Void visitCatchStatement(ShadowParser.CatchStatementContext ctx) {
    openScope(); // For catch parameter
    visitChildren(ctx);
    closeScope();

    return null;
  }

  @Override
  public Void visitCreateBlock(ShadowParser.CreateBlockContext ctx) {
    openScope();
    visitChildren(ctx);
    closeScope();

    return null;
  }

  @Override
  public Void visitForeachStatement(ShadowParser.ForeachStatementContext ctx) {
    openScope();
    visitChildren(ctx);
    closeScope();

    return null;
  }

  @Override
  public Void visitForeachInit(ShadowParser.ForeachInitContext ctx) {
    visitChildren(ctx);
    addSymbol(ctx.Identifier().getText(), ctx);

    return null;
  }

  @Override
  public Void visitForStatement(ShadowParser.ForStatementContext ctx) {
    openScope();
    visitChildren(ctx);
    closeScope();

    return null;
  }

  @Override
  public Void visitCreateDeclaration(ShadowParser.CreateDeclarationContext ctx) {
    openScope();
    visitChildren(ctx);
    closeScope();

    return null;
  }

  @Override
  public Void visitDestroyDeclaration(ShadowParser.DestroyDeclarationContext ctx) {
    openScope();
    visitChildren(ctx);
    closeScope();

    return null;
  }

  @Override
  public Void visitInlineMethodDefinition(ShadowParser.InlineMethodDefinitionContext ctx) {
    openScope();
    visitChildren(ctx);
    closeScope();

    return null;
  }

  @Override
  public Void visitLocalMethodDeclaration(ShadowParser.LocalMethodDeclarationContext ctx) {
    addSymbol(ctx.methodDeclarator().generalIdentifier().getText(), ctx);
    openScope();
    visitChildren(ctx);
    closeScope();

    return null;
  }

  @Override
  public Void visitLocalVariableDeclaration(ShadowParser.LocalVariableDeclarationContext ctx) {
    visitChildren(ctx);

    // Add variables
    for (ShadowParser.VariableDeclaratorContext declarator : ctx.variableDeclarator())
      addSymbol(declarator.generalIdentifier().getText(), declarator); // Add to local scope

    return null;
  }

  @Override
  public Void visitSequenceVariable(ShadowParser.SequenceVariableContext ctx) {
    addSymbol(ctx.Identifier().getText(), ctx);

    return null;
  }

  @Override
  public Void visitMethodDeclaration(ShadowParser.MethodDeclarationContext ctx) {
    openScope();
    visitChildren(ctx);
    closeScope();

    return null;
  }
}
