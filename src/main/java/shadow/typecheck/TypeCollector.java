/*
 * Copyright 2016 Team Shadow
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
public class TypeCollector extends BaseChecker {
	
	// Map of types to the AST nodes that define them, useful for error messages.
	private final Map<Type,Context> typeTable = new HashMap<Type,Context>();
	// Map of file paths (without extensions) to nodes.
    private final Map<String,Context> fileTable = new HashMap<String,Context>();		
	private final boolean useSourceFiles;
	// Holds all of the imports we know about.
	private final List<String> importList = new ArrayList<String>(); 
	private final LinkedList<Object> importedItems = new LinkedList<Object>();
	private final Configuration config;
	
	private Path currentFile;
	private Type mainType = null;
	private String currentName = "";	

	
	/**
	 * Creates a new <code>TypeCollector</code> with the given tree of packages. 
	 * @param p					package tree
	 * @param useSourceFiles	if true, always use <tt>.shadow</tt> instead of <tt>.meta</tt> files
	 * @throws ConfigurationException
	 */
	public TypeCollector( Package p, ErrorReporter reporter, boolean useSourceFiles ) throws ConfigurationException {		
		super( p, reporter );		  
		this.useSourceFiles = useSourceFiles;
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
     * @throws ParseException
     * @throws ShadowException
     * @throws TypeCheckException
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
     * @throws ParseException
     * @throws ShadowException
     * @throws TypeCheckException
     * @throws IOException
     * @throws ConfigurationException
     */
    public Map<Type, Context> collectTypes( String source, Path mainFile )
    		throws ParseException, ShadowException, TypeCheckException, IOException, ConfigurationException {
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
     * @throws ParseException
     * @throws ShadowException
     * @throws TypeCheckException
     * @throws IOException
     * @throws ConfigurationException
     */
    public Map<Type, Context> collectTypes( List<Path> files )
    		throws ParseException, ShadowException, TypeCheckException, IOException, ConfigurationException {
        return collectTypes( files, new HashMap<Path, String>(), false );
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
        if (files.isEmpty()) {
            throw new ConfigurationException("No files provided for typechecking");
        }
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
				
		/* Add standard imports. */		
		Path standard = config.getSystemImport().resolve("shadow").resolve("standard");
		if( !Files.exists(standard) )
			throw new ConfigurationException("Invalid path to shadow:standard: " + Main.canonicalize(standard));
		
		TreeSet<String> standardDependencies = new TreeSet<String>(); 
		
		try (DirectoryStream<Path> p = Files.newDirectoryStream(standard, "*.shadow")) {
			for (Path file : p) {
				String name = stripExtension(Main.canonicalize(file));
				uncheckedFiles.add(name);
				standardDependencies.add(name);
			}
		}

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
					source != null &&
					Files.exists(meta) && Files.getLastModifiedTime(meta).compareTo(Files.getLastModifiedTime(canonicalFile)) >= 0 &&
					Files.exists(llvm) && Files.getLastModifiedTime(llvm).compareTo(Files.getLastModifiedTime(meta)) >= 0)
					canonicalFile = meta;				
				else
					mustRecompile.add(canonical);
			}
			else if (!useSourceFiles)
				canonicalFile  = Paths.get(canonical + ".meta");
			
			currentFile = canonicalFile;    
		    
		    //Use the semantic checker to parse the file
		    ParseChecker checker = new ParseChecker(new ErrorReporter(Loggers.PARSER));
		    Context node;
		    //if there's an updated source, use that
		    //otherwise, read from the file
		    if( source != null  )
		    	node = checker.getCompilationUnit(source, currentFile);
		    else
		    	node = checker.getCompilationUnit(currentFile);

		    // Make another collector to walk the current file. 
			TypeCollector collector = new TypeCollector( new Package(), getErrorReporter(), useSourceFiles );
			// Keeping a current files gives us a file whose directory we can check against.
			collector.currentFile = currentFile; 
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
			
			for( String _import : collector.importList ) {
				if( !fileTable.containsKey(_import) )
					uncheckedFiles.add(_import);
				
				if( dependencySet != null )
					dependencySet.add(_import);
			}
			
			/* Add files in the directory after imports. */		
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(canonicalFile.getParent(), "*.shadow")) {
				for (Path file : stream) {
					String name = stripExtension(file.toAbsolutePath().normalize().toString());
					if (!fileTable.containsKey(name))
						uncheckedFiles.add(name);

					if (dependencySet != null)
						dependencySet.add(name);
				}
			}
			
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
			
			if( currentPackage.getQualifiedName().equals("shadow:natives") ) {
				switch( typeName ) {
					case "ShadowPointer": Type.SHADOW_POINTER = (ClassType)type; break;
				}
			}
			
			// Let type know what it has imported.
			type.addImportedItems( importedItems );
			
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
	 * Adds a type or a whole package to the current list of imports.
	 */
	public boolean addImport( String name ) {
		
		
		String separator = FileSystems.getDefault().getSeparator(); // Adds some platform independence.		
		if( separator.equals("\\"))		   // Hack for Windows to deal with backslash escaping.
			separator = "\\\\";
		String path = name.replaceAll(":", separator);
		
		/* Which import paths are used depends on whether the requested package
		 * is in the standard library or not. */
		List<Path> importPaths;
		if (path.startsWith("shadow")) {
			importPaths = new ArrayList<Path>();
			importPaths.add(config.getSystemImport());
		}
		else 
			importPaths = config.getImports();
		
		boolean success = false;				
		
		if( importPaths != null && importPaths.size() > 0 ) {
			for( Path importPath : importPaths ) {	
				// If an import path is relative, resolving it against the
				// current source file will make it absolute.
				// If it's absolute, no change will happen.
				importPath = currentFile.getParent().resolve(importPath);
				
				/* No @, must be a whole package import. 
				 * Add everything in the directory. */
				if( !path.contains("@")) {  						
					Path fullPath = importPath.resolve(path);
					if( Files.isDirectory(fullPath) ) {
						try {
							try (DirectoryStream<Path> p = Files.newDirectoryStream(fullPath, "*.shadow")) {
								for (Path file : p)
									importList.add(stripExtension(file.toAbsolutePath().normalize().toString()));
							}
							
							try (DirectoryStream<Path> p = Files.newDirectoryStream(fullPath, "*.meta")) {
								for (Path file : p) {
									String canonicalPath = stripExtension(file.toAbsolutePath().normalize().toString());
									if (!importList.contains(canonicalPath))
										importList.add(canonicalPath);
								}
							}
							
							success = true;
						}
						catch (IOException e) {}												
					}
				}
				/* Single file import. */
				else {  
					Path shadowVersion;
					Path metaVersion;
					String fixedPath;
				
					if( path.startsWith("default") ) {
						fixedPath = path.replaceFirst( "default@", "" );
						shadowVersion = currentFile.getParent().resolve(fixedPath + ".shadow");
						metaVersion = currentFile.getParent().resolve(fixedPath + ".meta");
					}
					else {
						fixedPath = path.replaceAll("@", separator);
						shadowVersion = importPath.resolve(fixedPath + ".shadow" );
						metaVersion = importPath.resolve(fixedPath + ".meta" );
					}					
											
					if( Files.exists(shadowVersion) ) {							
						importList.add(stripExtension(shadowVersion.toAbsolutePath().normalize().toString()));							
						success = true;						
					}
					else if( Files.exists(metaVersion) ) {
						importList.add(stripExtension(metaVersion.toAbsolutePath().normalize().toString()));							
						success = true;						
					}									
				}
				
				if( success )
					return true;
			}
		}
		else
			addError(new TypeCheckException(Error.INVALID_IMPORT, "No import paths specified, cannot import " + name));
		
		return false;
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
		importedItems.clear();	
		importList.clear();
		
		Type.clearTypes();
	}
	
	/* AST visitor methods below this point. */
	
	
	@Override public Void visitCompilationUnit(ShadowParser.CompilationUnitContext ctx) 
	{ 
		currentPackage = packageTree;
		importedItems.clear();
		importedItems.add("shadow:standard");			
		currentName = "";
		return visitChildren(ctx);
	}
	
	@Override public Void visitName(ShadowParser.NameContext ctx)
	{ 
		//visitChildren(ctx);  no need to visit children?		
		String name = ctx.getText();
		
		if( addImport( name ) )
			importedItems.add(name);
		else
			addError(ctx, Error.INVALID_IMPORT, "No file found for import " + name);
		
		return null;
	}
	
	@Override public Void visitClassOrInterfaceDeclaration(ShadowParser.ClassOrInterfaceDeclarationContext ctx)
	{
		String packageName = null;
		if( ctx.unqualifiedName() != null )
			packageName = ctx.unqualifiedName().getText();
		
		Type type = createType(ctx, ctx.getModifiers(), ctx.getDocumentation(), ctx.getChild(0).getText(), packageName, ctx.Identifier().getText() );
		
		visitChildren(ctx); 
		
		//It's important to visit children first because how types are stored in the typeTable depends on it
		type = type.getTypeWithoutTypeArguments(); //necessary?
		typeTable.put(type, ctx);
		((Context)ctx.getParent()).setType(type);		
		return null;
	}
	
	@Override public Void visitEnumDeclaration(ShadowParser.EnumDeclarationContext ctx)
	{ 
		String packageName = null;
		if( ctx.unqualifiedName() != null )
			packageName = ctx.unqualifiedName().getText();
		
		Type type = createType(ctx, ctx.getModifiers(), ctx.getDocumentation(), "enum", packageName, ctx.Identifier().getText() );
		
		visitChildren(ctx);
		
		typeTable.put(type.getTypeWithoutTypeArguments(), ctx );		
		return null;
	}
	
	@Override public Void visitClassOrInterfaceBody(ShadowParser.ClassOrInterfaceBodyContext ctx)
	{ 
		//set current type and name
		Type outerType = currentType;
		currentType = ((Context)ctx.getParent()).getType();
		currentName = currentType.getTypeName();	
		
		visitChildren(ctx);
		
		//go back to outer type and name
		currentType = outerType;
		if( currentType == null )
			currentName = currentPackage.getQualifiedName();
		else
			currentName = currentType.getTypeName();		
		return null;
	}
	
	@Override public Void visitTypeParameters(ShadowParser.TypeParametersContext ctx)
	{ 
		//no children visited
		if( declarationType != null )
			declarationType.setParameterized( true );		
		return null; 
	}
	
	
	@Override public Void visitClassOrInterfaceType(ShadowParser.ClassOrInterfaceTypeContext ctx)
	{ 		
		visitChildren(ctx);
		
		if( ctx.unqualifiedName() != null ) {
			String importName = ctx.getText();					
			if( importName.contains(":"))
				importName = importName.substring(0, importName.indexOf(':'));
			if( !addImport( importName ) )
				addError(ctx, Error.INVALID_IMPORT, "No file found for import " + importName);
		}
		return null;
	}
	
	@Override public Void visitPrimaryPrefix(ShadowParser.PrimaryPrefixContext ctx)
	{ 
		visitChildren(ctx);
		
		// Triggers an import (adding a file to the compilation process) 
		// since there's an UnqualifiedName@
		if( ctx.unqualifiedName() != null ) {
			String name = ctx.getText();
			if( !addImport( name ) )
				addError(ctx, Error.INVALID_IMPORT, "No file found for import " + name );
		}		
		return null;
	}	
	
	@Override public Void visitPrimitiveType(ShadowParser.PrimitiveTypeContext ctx) 
	{
		//no children
		ctx.setType(nameToPrimitiveType( ctx.getText() ) );
		return null;
	}
}
