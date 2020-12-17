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

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import shadow.Configuration;
import shadow.ConfigurationException;
import shadow.Loggers;
import shadow.Main;
import shadow.ShadowException;
import shadow.doctool.Documentation;
import shadow.parse.Context;
import shadow.parse.ParseChecker;
import shadow.parse.ParseException;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.ClassOrInterfaceBodyDeclarationContext;
import shadow.parse.ShadowParser.CompilationUnitContext;
import shadow.parse.ShadowParser.UnqualifiedNameContext;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
import shadow.typecheck.Package.PackageException;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

/**
 * The <code>TypeCollector</code> class is central to the first stage of type-checking.
 * It is given one or more files to type-check and collects all the types imported by
 * these files so that there is enough information for full type-checking and compilation.
 * 
 * @author Barry Wittman 
 */
public class TypeCollector extends ScopedChecker {

	// Map of types to the AST nodes that define them, useful for error messages.
	private final Map<Type,Context> typeTable = new HashMap<Type,Context>();
	// Map of file paths (without extensions) to nodes.
	private final Map<String,Context> fileTable = new HashMap<String,Context>();		
	private final boolean useSourceFiles;
	private final boolean typeCheckOnly;

	// Holds all of the imports we know about.
	private final Map<String,String> importedTypes = new HashMap<>(); // Type name -> file path
	private final Set<String> usedTypes = new HashSet<>(); // File paths
		
	// Paths where we can search for imports. 
	private final List<Path> importPaths = new ArrayList<>();

	//private final Set<Object> importedItems = new HashSet<>();

	// Types already known that don't need to be looked up again
	//private final Set<String> knownTypes = new HashSet<>();

	

	private final Configuration config;

	private Path currentFile;
	private Type mainType = null;
	private String currentName = "";	

	private final LinkedList<Set<String>> methods = new LinkedList<>();
	private final LinkedList<Set<String>> fields = new LinkedList<>();
	private final LinkedList<Set<String>> localTypes = new LinkedList<>();
	// Current type parameters for the current class
	private final LinkedList<Set<String>> typeParameters = new LinkedList<>();

	/**
	 * Creates a new <code>TypeCollector</code> with the given tree of packages. 
	 * @param p					package tree
	 * @param useSourceFiles	if true, always use <tt>.shadow</tt> instead of <tt>.meta</tt> files
	 * @throws ConfigurationException
	 */
	public TypeCollector( Package p, ErrorReporter reporter, boolean useSourceFiles, boolean typeCheckOnly ) throws ConfigurationException {		
		super( p, reporter );		  
		this.useSourceFiles = useSourceFiles;
		this.typeCheckOnly = typeCheckOnly;
		config = Configuration.getConfiguration();
	}			

	/**
	 * Gets the main type used when collecting types.
	 * @return					main type if specified, <code>null</code> otherwise
	 */
	public Type getMainType() {
		return mainType;
	}


	/**
	 * Gets the table that maps each file path (without extension) to the node of the
	 * top-level class, enum, interface, or exception declared in that file.  
	 * @return					map from files to nodes
	 */
	public  Map<String,Context> getFileTable() {
		return fileTable;
	}

	/** 
	 * Calls <code>collectTypes</code> with one main file.
	 * @param mainFile			main file to be type-checked or compiled
	 * @return					map from types to nodes
	 * @throws ShadowException
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public Map<Type, Context> collectTypes( Path mainFile )
			throws ParseException, ShadowException, TypeCheckException, IOException, ConfigurationException {
		List<Path> initialFiles = new ArrayList<Path>();
		initialFiles.add( mainFile );
		return collectTypes( initialFiles, new HashMap<Path, String>(), true );
	}

	/** 
	 * Calls <code>collectTypes</code> with one main file, whose source is given in source 
	 * @param source			the complete source code to check
	 * @param mainFile			main file to be type-checked or compiled
	 * @return					map from types to nodes
	 * @throws ShadowException
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public Map<Type, Context> collectTypes( String source, Path mainFile )
			throws ShadowException, IOException, ConfigurationException {
		List<Path> initialFiles = new ArrayList<Path>();
		mainFile = mainFile.toAbsolutePath().normalize();
		initialFiles.add( mainFile );
		Map<Path,String> activeFiles = new HashMap<Path, String>();
		activeFiles.put(mainFile, source);
		return collectTypes( initialFiles, activeFiles, true );
	}


	/** 
	 * Calls <code>collectTypes</code> with multiple, non-main files.
	 * This proxy method is usually called for documentation or type-checking only.
	 * @param files				files to be type-checked
	 * @return					map from types to nodes
	 * @throws ShadowException
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public Map<Type, Context> collectTypes( List<Path> files )
			throws ShadowException, IOException, ConfigurationException {
		return collectTypes( files, new HashMap<Path, String>(), false );
	}

	private static void recursivelyAddFiles(Path start, Set<String> uncheckedFiles, Set<String> standardDependencies) throws IOException {
		List<Path> directories = new LinkedList<>();

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(start)) {
			for (Path file : stream) {
				if(Files.isDirectory(file)) {
					// Watch out for . and .. entries
					if(!Files.isSameFile(file, start) && !Files.isSameFile(file, start.getParent()))
						 directories.add(file);
				}
				else if(file.toString().endsWith(".shadow")) {
					String name = stripExtension(Main.canonicalize(file));
					uncheckedFiles.add(name);
					standardDependencies.add(name);
				}
			}
		}

		for(Path directory : directories)
			recursivelyAddFiles(directory, uncheckedFiles, standardDependencies);
	}

	/*
	 * Calls the full <code>collectTypes</code> and might call it a second time
	 * if needed to determine what should be recompiled.
	 */
	private Map<Type, Context> collectTypes( List<Path> files, Map<Path,String> activeFiles, boolean hasMain ) throws ShadowException, IOException, ConfigurationException {
		Set<String> mustRecompile = new HashSet<String>();
		Map<String, TreeSet<String>> dependencies = new HashMap<String, TreeSet<String>>();

		// Initial type collection
		collectTypes( files, hasMain, activeFiles, mustRecompile, dependencies );

		// Files needing recompilation may trigger other files to get recompiled.
		// Figure out which ones and redo the whole type collection process.
		if( getErrorReporter().getErrorList().size() == 0 && !useSourceFiles && mustRecompile.size() > 0 ) {
			// Create a new set, otherwise adding new recompilations can trigger unnecessary ones.
			Set<String> updatedMustRecompile = new HashSet<String>(mustRecompile);

			// For all files that do not already need to be recompiled,
			// check to see if their dependencies do.
			for(Map.Entry<String, TreeSet<String>> entry : dependencies.entrySet() )
				if( !updatedMustRecompile.contains(entry.getKey()) )
					for( String dependency : entry.getValue() )
						if( mustRecompile.contains(dependency) ) {
							updatedMustRecompile.add(entry.getKey());
							break;
						}

			mustRecompile = updatedMustRecompile;        	
			clear(); // Clears out all internal representations and types.

			// Collect types again with updated recompilation requirements.
			collectTypes( files, hasMain, activeFiles, mustRecompile, null );
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
	private void collectTypes(List<Path> files, boolean hasMain, Map<Path,String> activeFiles,
			Set<String> mustRecompile, Map<String,TreeSet<String>> dependencies)
					throws ShadowException, IOException, ConfigurationException {
		// Create and fill the initial set of files to be checked.
		TreeSet<String> uncheckedFiles = new TreeSet<String>();

		String main = null; // May or may not be null, based on hasMain.
		if (files.isEmpty())
			throw new ConfigurationException("No files provided for typechecking");
		else if (hasMain) {
			// Assume the main file is the first and only file.
			main = stripExtension(Main.canonicalize(files.get(0)));
			uncheckedFiles.add(main);
		}
		else {
			for (Path file : files) {
				String path = stripExtension(Main.canonicalize(file));
				uncheckedFiles.add(path);
			}
		}   

		/* Check standard imports. */		
		Path standard = config.getSystemImport().resolve("shadow").resolve("standard").normalize();
		if( !Files.exists(standard) )
			throw new ConfigurationException("Invalid path to shadow:standard: " + Main.canonicalize(standard));

		TreeSet<String> standardDependencies = new TreeSet<String>(); 

		// Adds all files in the standard directory (including sub-directories)
		recursivelyAddFiles(standard, uncheckedFiles, standardDependencies);
		
		/* A few io classes are absolutely necessary for a console program. */
		Path io = config.getSystemImport().resolve("shadow").resolve("io").normalize();
		if( !Files.exists(io) )
			throw new ConfigurationException("Invalid path to io: " + Main.canonicalize(io));
		
		uncheckedFiles.add(stripExtension(Main.canonicalize(io.resolve("Console.shadow"))));
		uncheckedFiles.add(stripExtension(Main.canonicalize(io.resolve("File.shadow"))));
		uncheckedFiles.add(stripExtension(Main.canonicalize(io.resolve("IOException.shadow"))));
		uncheckedFiles.add(stripExtension(Main.canonicalize(io.resolve("Path.shadow"))));	

		/* As long as there are unchecked files, remove one and process it. */
		while( !uncheckedFiles.isEmpty() ) {			
			String canonical = uncheckedFiles.first();
			uncheckedFiles.remove(canonical);	

			Path canonicalFile = Paths.get(canonical + ".shadow");
			String source = activeFiles.get(canonicalFile);

			// Depending on the circumstances, the compiler may choose to either
			// compile/recompile source files, or rely on existing binaries/IR.
			if( Files.exists(canonicalFile) ) {											
				Path meta = Paths.get(canonical + ".meta");
				Path llvm = Paths.get(canonical + ".bc");

				// If source compilation was not requested and the binaries exist
				// that are newer than the source, use those binaries.
				if( !useSourceFiles &&
						!mustRecompile.contains(canonical) &&
						source == null &&
						// Always do the full .shadow file for the main file if typechecking
						(!typeCheckOnly || !hasMain || !files.get(0).equals(canonicalFile)) &&
						// Only use .meta if it's newer than .shadow 
						Files.exists(meta) && Files.getLastModifiedTime(meta).compareTo(Files.getLastModifiedTime(canonicalFile)) >= 0 &&
						// Also, only use .meta if we're not going to need to recompile it into an LLVM 
						(typeCheckOnly || (Files.exists(llvm) && Files.getLastModifiedTime(llvm).compareTo(Files.getLastModifiedTime(meta)) >= 0)))
					canonicalFile = meta;				
				else
					mustRecompile.add(canonical);
			}
			else if (!useSourceFiles)
				canonicalFile  = Paths.get(canonical + ".meta");

			currentFile = canonicalFile;    

			// Use the semantic checker to parse the file
			ParseChecker checker = new ParseChecker(new ErrorReporter(Loggers.PARSER));
			CompilationUnitContext node;
			// If there's an updated source, use that
			// Otherwise, read from the file
			if( source != null  )
				node = checker.getCompilationUnit(source, currentFile);
			else
				node = checker.getCompilationUnit(currentFile); 
			checker.printAndReportErrors();		    

			// Make another collector to walk the current file. 
			TypeCollector collector = new TypeCollector( new Package(), getErrorReporter(), useSourceFiles, typeCheckOnly );
			// Keeping a current files gives us a file whose directory we can check against.
			collector.setCurrentFile(currentFile, node);
			collector.visit(node);				

			if( canonical.equals(main) )
				mainType = node.getType();

			fileTable.put(canonical, node);

			/* Copy types from other collector into our package tree. */	
			for( Type type : collector.packageTree ) {
				try {				
					packageTree.addQualifiedPackage( type.getPackage().toString() ).addType( type );					
					if( mainType != null && type.getPackage() == packageTree &&
							mainType.getPackage() != packageTree ) {
						// Imported class has default package but the main type doesn't.
						// The only classes without a package that will be imported will be
						// in the same directory as the main type.
						// Implication: classes in the same directory have different packages.
						String message = "Type " + type +
								" belongs to the default package, but types defined in the same directory belong to other packages";
						addWarning(new TypeCheckException(Error.MISMATCHED_PACKAGE, message));
					}											
				}
				catch(PackageException e) {
					addError(new TypeCheckException(Error.INVALID_PACKAGE, e.getMessage()));				
				}
			}

			/* Track the dependencies for this file (if dependencies are being used).
			 * If any of its dependencies need to be recompiled, this file will need
			 * to be recompiled.
			 */

			TreeSet<String> dependencySet = null;			

			if( dependencies != null ) {
				dependencySet = new TreeSet<String>( standardDependencies );
				dependencies.put( canonical, dependencySet );
			}

			for( String _import : collector.usedTypes ) {
				if( !fileTable.containsKey(_import) )
					uncheckedFiles.add(_import);

				if( dependencySet != null )
					dependencySet.add(_import);
			}

			/* Add files in the directory after imports. */		
			/*
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(canonicalFile.getParent(), "*.shadow")) {
				for (Path file : stream) {
					String name = stripExtension(file.toAbsolutePath().normalize().toString());
					if (!fileTable.containsKey(name))
						uncheckedFiles.add(name);

					if (dependencySet != null)
						dependencySet.add(name);
				}
			}
			 */

			/* Copy file table from other collector into our table. */
			Map<Type,Context> otherNodeTable = collector.typeTable;
			for( Type type : otherNodeTable.keySet() ) {
				if( !typeTable.containsKey(type) ) {
					Context otherNode = otherNodeTable.get(type);					
					typeTable.put(type, otherNode);
				}
			}
		}
	}

	private void setCurrentFile(Path currentFile, CompilationUnitContext node) throws IOException {
		this.currentFile = currentFile;
		importedTypes.clear();
		
		// Standard imports
		Path standard = config.getSystemImport().resolve("shadow").resolve("standard").normalize();
		
		// Possible sources for imports (order matters)
		importPaths.clear();
		
		
		// If the file has package information, back up so that the import root is the above the package information
		Path parent = currentFile.getParent();
		if(node != null) {
			UnqualifiedNameContext unqualifiedName = null;
			if(node.classOrInterfaceDeclaration() != null)
				unqualifiedName = node.classOrInterfaceDeclaration().unqualifiedName();
			else if(node.enumDeclaration() != null)
				unqualifiedName = node.enumDeclaration().unqualifiedName();
			
			
			if(unqualifiedName != null) {
				String text = unqualifiedName.getText();
				if(!text.equals("default")) {
					String[] parts = text.split(":");
					for(int i = 0; i < parts.length; ++i)
						parent = parent.getParent();
				}				
			}
		}
		
		importPaths.add(parent.normalize());
		importPaths.addAll(config.getImports());
		importPaths.add(standard);
				
		
		// Actually import everything from the standard library
		recursivelyAddImports(standard);		
		
		// And everything from the current directory
		addImports(currentFile.getParent()); 	
	}
	
	/*
	 * Add all the files in a directory as imports.
	 */
	private boolean addImports(Path directory) {
		boolean success = true;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			for (Path file : stream) {
				// Watch out for . and .. entries
				if(!Files.isDirectory(file)) {
					String typeName = stripExtension(file.getFileName().toString());
					
					if(file.toString().endsWith(".shadow") || 
					  (file.toString().endsWith(".meta")  && !Files.exists(file.resolveSibling(typeName + ".shadow")))) {
						if(!addImport(file))
							success = false;
					}
				}					
			}
		}
		catch (IOException e) {
			success = false;
		}
		
		return success;
	}
	
	/*
	 * Add a single file as an import.
	 * Returns true if successful.
	 * Returns false if that type name was already imported.
	 */
	private boolean addImport(Path file) {
		String filePath = stripExtension(Main.canonicalize(file));
		String typeName = stripExtension(file.getFileName().toString());
		// Put in list of imported types and see if anything else has the same name
		String oldPath = importedTypes.put(typeName, filePath);
		
		// For .meta files, an import signals that the type was actually used
		if(currentFile.getFileName().toString().endsWith(".meta"))
			usedTypes.add(filePath);
				
		// If something else had the same name...
		if(oldPath != null) {
			Path path = Paths.get(oldPath).getParent();
			Path standardPath = config.getSystemImport().resolve("shadow").resolve("standard").normalize();
			Path currentPath = currentFile.getParent();
			// We're allowed to overwrite names from standard or immediately in the current path, but not other things.
			if(!path.startsWith(standardPath) && !path.equals(currentPath))
				return false;
		}
		
		
		return true;
	}

	
	private void recursivelyAddImports(Path path) throws IOException {
		List<Path> directories = new LinkedList<>();

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path file : stream) {
				// Watch out for . and .. entries
				if(Files.isDirectory(file)) {
					if(!Files.isSameFile(file, path) && !Files.isSameFile(file, path.getParent()))
						directories.add(file.normalize());
				}
				else if(file.toString().endsWith(".shadow")) {
					String filePath = stripExtension(file.toAbsolutePath().normalize().toString());
					String typeName = stripExtension(file.getFileName().toString());
					importedTypes.put(typeName, filePath);			
				}					
			}
		}

		for(Path directory : directories)
			recursivelyAddImports(directory);
	}

	/*
	 * Checks to make sure that all types in a package are defined by files in the same directory.
	 */
	private void checkPackageDirectories(Package _package) {		
		Collection<Type> types = _package.getTypes();

		Type firstType = null;
		Path path1 = null;

		/* Gets the directory of the first type and compares all others to it. */
		if( types.size() > 1 ) {
			for( Type type : types ) {
				if( firstType == null ) {
					firstType = type;
					//path1 = typeTable.get( type ).getFile().getParentFile();
					path1 = typeTable.get( type ).getPath().getParent();
				}
				else {					
					Path path2 = typeTable.get( type ).getPath().getParent();
					if( !path1.equals( path2 ) )
						addWarning(new TypeCheckException(Error.MISMATCHED_PACKAGE, "Type " + firstType +
								" and " + type + " both belong to package " + _package +
								" but are defined in different directories"));
				}
			}
		}

		// Recursively check child packages.
		for( Package child : _package.getChildren().values()  )
			checkPackageDirectories(child);
	}

	/*
	 * Creates a new type and puts it in the correct package.
	 * If the type is a standard type, it will also have a static
	 * type variable associated with it. 
	 */
	private Type createType( Context node, Modifiers modifiers, 
			Documentation documentation, String kind, String packageName, String name ) {		 
		String typeName;
		Type type = Type.UNKNOWN;
		node.setType(type); //will be Unknown unless set with a real type

		if( packageName != null ) {			
			if( currentType == null )
				currentPackage = packageTree.addQualifiedPackage(packageName);			
			else {
				addError(node, Error.INVALID_PACKAGE, "Package can only be defined by outermost classes");				
				return type;
			}
		}

		/* For outer types, check that type name matches file name (if defined in a file), 
		 * and that the package name matches the directory path. */
		if( currentType == null && node.getPath() != null ) {
			Path file = node.getPath();
			String fileName = stripExtension( file.getFileName().toString() );
			if( !fileName.equals( name ) ) { // Check file name.
				addError(node, Error.INVALID_FILE, "Type " + name +
						" must be declared in a file named " +
						name + ".shadow or " + name + ".meta" );
				return type;
			}
			else { // Check package path.			
				Package _package = currentPackage;
				Path parent = file.getParent();

				while( _package != packageTree && parent != null ) {	
					if( !_package.getName().equals( parent.getFileName().toString() ) ) {
						addError(node, Error.INVALID_PACKAGE, "Type " + name +
								" cannot be added to package " + currentPackage.getQualifiedName() +
								" unless it is defined in directory " + currentPackage.getPath() );
						return type;						
					}

					parent = parent.getParent();
					_package = _package.getParent();
				}
			}			
		}
		else { //inner type
			//if the outer type is locked, then make this inner type locked too
			if( currentType.getModifiers().isLocked() )
				modifiers.addModifier(Modifiers.LOCKED);
		}

		// Fix type names for primitive types.
		if( currentPackage.getQualifiedName().equals("shadow:standard") && 		
				(name.equals( "Boolean" ) ||
						name.equals( "Byte" ) ||
						name.equals( "Code" ) ||
						name.equals( "Short" ) ||
						name.equals( "Int" ) ||
						name.equals( "Long" ) ||
						name.equals( "Float" ) ||
						name.equals( "Double" ) ||
						name.equals( "UByte" ) ||
						name.equals( "UInt" ) ||
						name.equals( "ULong" ) ||
						name.equals( "UShort" )) )			
			name = name.toLowerCase();				

		// Current name contains package or outer class.
		if( currentType == null )
			typeName = currentName + name; 
		else
			typeName = currentName + ":" + name;

		if( lookupType( node, typeName ) != null ) {
			addError(node, Error.MULTIPLY_DEFINED_SYMBOL, "Type " + typeName + " already defined" );			
			return type;
		}
		else { // Set kind of type and create it with the appropriate name and outer type.
			switch( kind ) {			
			case "class":
				type = new ClassType(name, modifiers, documentation, currentType );				
				break;
			case "enum":				
				type = new EnumType(name, modifiers, documentation, currentType );
				break;			
			case "exception":
				type = new ExceptionType(name, modifiers, documentation, currentType );
				break;
			case "interface":
				type = new InterfaceType(name, modifiers, documentation);
				break;
			case "singleton":
				type = new SingletonType(name, modifiers, documentation, currentType );
				break;			
			}

			// Put new type inside of outer type, if it exists.
			if( currentType != null && currentType instanceof ClassType &&					
					(kind.equals("class") || kind.equals("enum") || kind.equals("exception")) )					
				((ClassType)currentType).addInnerClass(name, (ClassType)type); 

			// Special case for standard types needed in the compiler.			
			if( currentPackage.getQualifiedName().equals("shadow:standard")) {	
				switch( typeName ) {
				case "AddressMap":		Type.ADDRESS_MAP = (ClassType) type; break;
				case "Array":			Type.ARRAY = (ClassType) type; break;
				case "ArrayNullable":	Type.ARRAY_NULLABLE = (ClassType) type; break;
				case "AssertException": Type.ASSERT_EXCEPTION = (ExceptionType) type; break;
				case "CanAdd":			Type.CAN_ADD = (InterfaceType)type; break;
				case "CanCompare":		Type.CAN_COMPARE = (InterfaceType) type; break;
				case "CanDivide":		Type.CAN_DIVIDE = (InterfaceType)type; break;
				case "CanEqual":		Type.CAN_EQUAL = (InterfaceType) type; break;	
				case "CanModulus":		Type.CAN_MODULUS = (InterfaceType)type; break;
				case "CanMultiply":		Type.CAN_MULTIPLY = (InterfaceType)type; break;
				case "CanNegate":		Type.CAN_NEGATE = (InterfaceType)type; break;
				case "CanSubtract":		Type.CAN_SUBTRACT = (InterfaceType)type; break;
				case "CanIndex":		Type.CAN_INDEX = (InterfaceType) type; break;
				case "CanIndexNullable":	Type.CAN_INDEX_NULLABLE = (InterfaceType) type; break;				
				case "CanIndexStore":	Type.CAN_INDEX_STORE = (InterfaceType) type; break;
				case "CanIndexStoreNullable":	Type.CAN_INDEX_STORE_NULLABLE = (InterfaceType) type; break;
				case "CanIterate":		Type.CAN_ITERATE = (InterfaceType) type; break;
				case "CanIterateNullable":	Type.CAN_ITERATE_NULLABLE = (InterfaceType) type; break;
				case "CastException":	Type.CAST_EXCEPTION = (ExceptionType) type; break;
				case "Class":			Type.CLASS = (ClassType) type; break;
				case "boolean":			Type.BOOLEAN = (ClassType)type; break;
				case "byte":			Type.BYTE = (ClassType)type; break;
				case "code":			Type.CODE = (ClassType)type; break;
				case "double":			Type.DOUBLE = (ClassType)type; break;
				case "Enum":			Type.ENUM = (ClassType) type; break;//the base class for enum is not an enum								
				case "Exception":		Type.EXCEPTION = (ExceptionType) type; break;
				case "float":			Type.FLOAT = (ClassType)type; break;
				case "GenericClass":	Type.GENERIC_CLASS = (ClassType) type; break;
				case "IndexOutOfBoundsException": Type.INDEX_OUT_OF_BOUNDS_EXCEPTION = (ExceptionType) type; break;
				case "int":				Type.INT = (ClassType) type; break;
				case "Integer":			Type.INTEGER = (InterfaceType) type; break;
				case "InterfaceCreateException": Type.INTERFACE_CREATE_EXCEPTION = (ExceptionType) type; break;
				case "Iterator":		Type.ITERATOR = (InterfaceType) type; break;
				case "IteratorNullable":	Type.ITERATOR_NULLABLE = (InterfaceType) type; break;
				case "long":			Type.LONG = (ClassType)type; break;	
				case "Method":			Type.METHOD = (ClassType)type; break;
				case "MethodTable":		Type.METHOD_TABLE = (ClassType)type; break;
				case "Number":			Type.NUMBER = (InterfaceType) type; break;
				case "Object":			Type.OBJECT = (ClassType) type; break;				
				case "short":			Type.SHORT = (ClassType)type; break;
				case "String":			Type.STRING = (ClassType) type; break;
				case "ubyte":			Type.UBYTE = (ClassType)type; break;
				case "uint":			Type.UINT = (ClassType)type; break;
				case "ulong":			Type.ULONG = (ClassType)type; break;				
				case "UnexpectedNullException": Type.UNEXPECTED_NULL_EXCEPTION = (ExceptionType)type; break;
				case "ushort":			Type.USHORT = (ClassType)type; break;
				case "CanRun":			Type.CAN_RUN = (InterfaceType)type; break;
				case "Thread":			Type.THREAD = (ClassType)type; break;
				}
			}

			if (currentPackage.getQualifiedName().equals("shadow:standard:decorators")) {
				switch( typeName ) {
				case "Decorator": Type.DECORATOR = (InterfaceType)type; break;
				case "MethodDecorator": Type.METHOD_DECORATOR = (InterfaceType)type; break;
				case "CompilerDecorator": Type.COMPILER_DECORATOR = (InterfaceType)type; break;

				case "ImportAssembly": Type.IMPORT_ASSEMBLY = (ClassType)type; break;
				case "ImportNative": Type.IMPORT_NATIVE = (ClassType)type; break;
				case "ImportMethod": Type.IMPORT_METHOD = (ClassType)type; break;

				case "ExportNative": Type.EXPORT_NATIVE = (ClassType)type; break;
				case "ExportAssembly": Type.EXPORT_ASSEMBLY = (ClassType)type; break;
				case "ExportMethod": Type.EXPORT_METHOD = (ClassType)type; break;
				}
			}

			if( currentPackage.getQualifiedName().equals("shadow:natives") ) {
				switch( typeName ) {
				case "Pointer": Type.POINTER = (ClassType)type; break;
				}
			}


			// Put new type in its package.
			try	{			
				currentPackage.addType( type );
			}
			catch( PackageException e ) {
				addError( node, Error.INVALID_PACKAGE, e.getMessage() );
				return type;
			}

			// Update the type of the declaration node and the current declaration type.
			node.setType( type );	
			declarationType = type;
		}

		return type;
	}


	/*
	 * Find the path associated with a given import name, either a file
	 * or a whole directory.
	 */
	
	private Path findPath(String name) {
		String separator = FileSystems.getDefault().getSeparator(); // Adds some platform independence.		
		if( separator.equals("\\"))		   // Hack for Windows to deal with backslash escaping.
			separator = "\\\\";

		boolean isDirectory = !name.contains("@");
		
		String path = name.replaceAll(":", separator);
		if(path.startsWith("default@"))
			path = path.replaceFirst( "default@", "" );
		else
			path = path.replaceFirst("@", separator);

		for( Path importPath : importPaths ) {	
			// If an import path is relative, resolving it against the
			// current source file will make it absolute.
			// If it's absolute, no change will happen.
			importPath = currentFile.getParent().resolve(importPath);

			if(isDirectory) {
				Path directory = importPath.resolve(path);
				if(Files.exists(directory) && Files.isDirectory(directory))
					return directory;
			}
			else {
				Path shadowVersion = importPath.resolve(path + ".shadow" );
				Path metaVersion = importPath.resolve(path + ".meta" );
				if( Files.exists(shadowVersion))						
					return shadowVersion;				
				else if( Files.exists(metaVersion) )
					return metaVersion;
			}					
		}

		return null;
	}

	/**
	 * Clears out the data structures within the collector,
	 * returning it to a state similar to just after construction.
	 * Thie method calls its <code>super</code> version to clear out the
	 * <code>BaseChecker</code> structures as well. 
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

		methods.clear();
		fields.clear();
		localTypes.clear();
		typeParameters.clear();

		Type.clearTypes();
	}

	/* AST visitor methods below this point. */


	@Override public Void visitCompilationUnit(ShadowParser.CompilationUnitContext ctx) { 
		currentPackage = packageTree;

		currentName = "";
		
		Set<String> typeSet = new HashSet<>();
		if(ctx.classOrInterfaceDeclaration() != null)		
			typeSet.add(ctx.classOrInterfaceDeclaration().Identifier().getText());
		else if(ctx.enumDeclaration() != null)
			typeSet.add(ctx.enumDeclaration().Identifier().getText());		
		localTypes.addFirst(typeSet);
		visitChildren(ctx);
		localTypes.removeFirst();
		
		return null;
	}

	
	// Imports handled here
	@Override public Void visitName(ShadowParser.NameContext ctx) { 
		//visitChildren(ctx);  no need to visit children?		
		String name = ctx.getText();

		// Single class
		if(name.contains("@")) {
			Path file = findPath(name);
			if(file == null)
				addError(ctx, Error.INVALID_IMPORT, "No file found for type " + name);
			else if(!addImport(file))
				addError(ctx, Error.IMPORT_COLLIDES, "Type " + name + " collides with existing import");
		}
		// Whole package
		else {
			Path directory = findPath(name);			
			if(directory == null)			
				addError(ctx, Error.INVALID_IMPORT, "No directory found for package " + name);
			else if(!addImports(directory))
				addError(ctx, Error.IMPORT_COLLIDES, "One or more types in package " + name + " collide with an existing import");
		}

		return null;
	}

	@Override public Void visitClassOrInterfaceDeclaration(ShadowParser.ClassOrInterfaceDeclarationContext ctx) {
		String packageName = null;
		if( ctx.unqualifiedName() != null )
			packageName = ctx.unqualifiedName().getText();
		
		if(ctx.isList() == null) { // no is list, so mark Object as used
			Path object = config.getSystemImport().resolve("shadow").resolve("standard").resolve("Object");
			usedTypes.add(Main.canonicalize(object));	
		}

		Type type = createType(ctx, ctx.getModifiers(), ctx.getDocumentation(), ctx.getChild(0).getText(), packageName, ctx.Identifier().getText() );

		addMembers(ctx.classOrInterfaceBody().classOrInterfaceBodyDeclaration());
		addTypeParameters();

		visitChildren(ctx); 

		//It's important to visit children first because how types are stored in the typeTable depends on it
		type = type.getTypeWithoutTypeArguments(); // Necessary?
		typeTable.put(type, ctx);
		((Context)ctx.getParent()).setType(type);		

		removeMembers();
		removeTypeParameters();
		
		if(!type.hasOuter())
			updateImports(type);

		return null;
	}
	
	private void updateImports(Type type) {

		// Let type know what it has imported.
		// Imported items should be empty at this point.
		Map<String, Object> items = type.getImportedItems();
		for(Map.Entry<String, String> entry : importedTypes.entrySet()) {
			// Only import the types that were actually used
			if(usedTypes.contains(entry.getValue()))
				items.put(entry.getKey(), entry.getValue());
		}
	}

	private void addTypeParameters() {
		typeParameters.addFirst(new HashSet<>());		
	}

	private void removeTypeParameters() {
		typeParameters.removeFirst();
	}

	/*
	 * Records the fields and methods so that we can later determine whether
	 * an identifier is a field, a method, or a type.
	 * Also adds inner classes so that we don't go looking for them elsewhere.
	 */	
	private void addMembers(List<ClassOrInterfaceBodyDeclarationContext> declarations) {
		Set<String> fieldSet = new HashSet<>();
		Set<String> methodSet = new HashSet<>();
		Set<String> typeSet = new HashSet<>();
		

		for(ClassOrInterfaceBodyDeclarationContext context : declarations) {
			if(context.fieldDeclaration() != null) {
				for(VariableDeclaratorContext declarator : context.fieldDeclaration().variableDeclarator())
					fieldSet.add(declarator.generalIdentifier().getText());
			}
			else if(context.methodDeclaration() != null) {
				methodSet.add(context.methodDeclaration().methodDeclarator().generalIdentifier().getText());
			}		
			else if(context.classOrInterfaceDeclaration() != null) {
				typeSet.add(context.classOrInterfaceDeclaration().Identifier().getText());
			}
			else if(context.enumDeclaration() != null) {
				typeSet.add(context.enumDeclaration().Identifier().getText());
			}
		}

		fields.addFirst(fieldSet);
		methods.addFirst(methodSet);
		localTypes.addFirst(typeSet);
	}

	private void removeMembers() {
		fields.removeFirst();
		methods.removeFirst();
		localTypes.removeFirst();
	}

	private boolean isMethod(String symbol) {
		return methods.getFirst().contains(symbol);
	}

	private boolean isField(String symbol) {
		return fields.getFirst().contains(symbol);
	}
	
	private boolean isLocalType(String symbol) {
		for(Set<String> types : localTypes)
			if(types.contains(symbol))
				return true;
		
		return false;		
	}

	@Override public Void visitEnumDeclaration(ShadowParser.EnumDeclarationContext ctx) { 
		String packageName = null;
		if( ctx.unqualifiedName() != null )
			packageName = ctx.unqualifiedName().getText();

		Type type = createType(ctx, ctx.getModifiers(), ctx.getDocumentation(), "enum", packageName, ctx.Identifier().getText() );

		addMembers(ctx.enumBody().classOrInterfaceBodyDeclaration());

		visitChildren(ctx);
		type = type.getTypeWithoutTypeArguments(); //necessary?
		typeTable.put(type, ctx);
		((Context)ctx.getParent()).setType(type);	

		removeMembers();
		
		if(!type.hasOuter())
			updateImports(type);

		return null;
	}

	@Override public Void visitClassOrInterfaceBody(ShadowParser.ClassOrInterfaceBodyContext ctx) { 
		// Set current type and name
		Type outerType = currentType;
		currentType = ((Context)ctx.getParent()).getType();
		currentName = currentType.getTypeName();	

		visitChildren(ctx);

		// Go back to outer type and name
		currentType = outerType;
		if(currentType == null)
			currentName = currentPackage.getQualifiedName();
		else
			currentName = currentType.getTypeName();		
		return null;
	}

	@Override public Void visitTypeParameters(ShadowParser.TypeParametersContext ctx) { 
		visitChildren(ctx);

		if( declarationType != null )
			declarationType.setParameterized( true );		

		return null; 
	}

	@Override
	public Void visitTypeParameter(ShadowParser.TypeParameterContext ctx) {
		typeParameters.getFirst().add(ctx.Identifier().getText());

		visitChildren(ctx);

		return null;
	}

	@Override public Void visitClassOrInterfaceType(ShadowParser.ClassOrInterfaceTypeContext ctx) {
		//	(unqualifiedName '@')? Identifier ( ':' Identifier )* typeArguments?

		String name = ctx.Identifier(0).getText();  // Only the outermost class (since that's the only one that can have its own file)
		if(ctx.unqualifiedName() != null) {
			name = ctx.unqualifiedName().getText() + "@" + name;
			Path file = findPath(name);
			if(file == null)
				addError(ctx, Error.INVALID_IMPORT, "No file found for type " + name);
			else
				usedTypes.add(stripExtension(Main.canonicalize(file)));
		}
		else if(!isTypeParameter(name) && !isLocalType(name)) {
			if(importedTypes.containsKey(name))
				usedTypes.add(importedTypes.get(name));
			else
				addError(ctx, Error.UNDEFINED_TYPE, "Type " + name + " not defined in current context");
		}

		visitChildren(ctx);

		return null;
	}

	private boolean isTypeParameter(String typeName) {
		return typeParameters.getFirst().contains(typeName);
	}

	@Override public Void visitPrimaryExpression(ShadowParser.PrimaryExpressionContext ctx) { 

		ShadowParser.PrimaryPrefixContext prefix = ctx.primaryPrefix();

		// Triggers an import (adding a file to the compilation process) 
		// since there's an UnqualifiedName@
		if(prefix.unqualifiedName() != null) {
			String name = prefix.unqualifiedName().getText() + "@" + prefix.generalIdentifier().getText();
			Path file = findPath(name);
			if(file == null)
				addError(ctx, Error.INVALID_IMPORT, "No file found for type " + name);
			else
				usedTypes.add(stripExtension(Main.canonicalize(file)));
		}
		// This case is complex:
		// There's an identifier that could be a class, but it's got to have a suffix.
		else if(prefix.generalIdentifier() != null && ctx.primarySuffix().size() > 0) {	
			String symbol = prefix.generalIdentifier().getText();

			// A local variable, a member variable, or a method would hide a class name
			// (and a type parameter or a locally declared type is already covered),
			// so we eliminate those possibilities first.
			if(findSymbol(symbol) == null && !isField(symbol) && !isMethod(symbol) &&
					!isTypeParameter(symbol) && !isLocalType(symbol)) {
				ShadowParser.PrimarySuffixContext suffix = ctx.primarySuffix(0); // First suffix

				// If the symbol comes before :class
				// or :CONSTANT or :InnerClass
				// or :create,
				// we know it's a class
				if(suffix.classSpecifier() != null || suffix.scopeSpecifier() != null || suffix.allocation() != null) {
					if(importedTypes.containsKey(symbol))
						usedTypes.add(importedTypes.get(symbol));
					else
						addError(ctx, Error.UNDEFINED_TYPE, "Type " + symbol + " not defined in current context");
				}
				// If it comes before a method or a property, there's a chance it's a singleton class.
				// Thus, we look up the type, but we don't throw an error if we don't find it,
				// since an error message here would be confusing: The programmer probably misspelled a variable name.
				else if(suffix.method() != null || suffix.property() != null) {
					if(importedTypes.containsKey(symbol))
						usedTypes.add(importedTypes.get(symbol));
				}
			}			
		}

		visitChildren(ctx);

		return null;
	}

	@Override public Void visitFormalParameter(ShadowParser.FormalParameterContext ctx) { 		
		visitChildren(ctx); 
		addSymbol( ctx.Identifier().getText(), ctx );

		return null;
	}

	@Override public Void visitBlock(ShadowParser.BlockContext ctx) { 
		openScope();
		visitChildren(ctx);
		closeScope();

		return null;		
	}

	@Override public Void visitPrimitiveType(ShadowParser.PrimitiveTypeContext ctx)  {
		// No children
		// ctx.setType(nameToPrimitiveType( ctx.getText() ) );
		return null;
	}

	@Override public Void visitCatchStatement(ShadowParser.CatchStatementContext ctx) { 
		openScope(); // For catch parameter
		visitChildren(ctx);
		closeScope();

		return null;
	}

	@Override public Void visitCreateBlock(ShadowParser.CreateBlockContext ctx) { 
		openScope();
		visitChildren(ctx);
		closeScope();

		return null;
	}

	@Override public Void visitForeachStatement(ShadowParser.ForeachStatementContext ctx) { 
		openScope();
		visitChildren(ctx);
		closeScope();

		return null;
	}

	@Override public Void visitForeachInit(ShadowParser.ForeachInitContext ctx) { 
		visitChildren(ctx);		
		addSymbol(ctx.Identifier().getText(), ctx);

		return null;
	}


	@Override public Void visitForStatement(ShadowParser.ForStatementContext ctx) { 
		openScope();
		visitChildren(ctx);
		closeScope();

		return null;
	}

	@Override public Void visitCreateDeclaration(ShadowParser.CreateDeclarationContext ctx) {		
		openScope();
		visitChildren(ctx);
		closeScope();

		return null;
	}

	@Override public Void visitDestroyDeclaration(ShadowParser.DestroyDeclarationContext ctx) {
		openScope();
		visitChildren(ctx);
		closeScope();

		return null;
	}

	@Override public Void visitInlineMethodDefinition(ShadowParser.InlineMethodDefinitionContext ctx) { 
		openScope();
		visitChildren(ctx);
		closeScope();

		return null;
	}

	@Override public Void visitLocalMethodDeclaration(ShadowParser.LocalMethodDeclarationContext ctx) { 					
		addSymbol(ctx.methodDeclarator().generalIdentifier().getText(), ctx);
		openScope();
		visitChildren(ctx);
		closeScope();

		return null;	
	}

	@Override public Void visitLocalVariableDeclaration(ShadowParser.LocalVariableDeclarationContext ctx) {		
		visitChildren(ctx);
		
		// Add variables
		for( ShadowParser.VariableDeclaratorContext declarator : ctx.variableDeclarator() )			
			addSymbol( declarator.generalIdentifier().getText(), declarator ); // Add to local scope

		return null;
	}

	@Override public Void visitSequenceVariable(ShadowParser.SequenceVariableContext ctx) { 
		addSymbol(ctx.Identifier().getText(), ctx);

		return null;
	}

	@Override public Void visitMethodDeclaration(ShadowParser.MethodDeclarationContext ctx) {
		openScope();
		visitChildren(ctx);
		closeScope();

		return null;
	}
}
