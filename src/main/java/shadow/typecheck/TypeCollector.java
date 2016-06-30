/*
 * Copyright 2015 Team Shadow
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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
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
import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.doctool.Documentation;
import shadow.parser.javacc.ASTClassOrInterfaceBody;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPrimaryPrefix;
import shadow.parser.javacc.ASTPrimitiveType;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTTypeArguments;
import shadow.parser.javacc.ASTTypeParameters;
import shadow.parser.javacc.ASTUnqualifiedName;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowFileParser;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.ShadowParser.TypeKind;
import shadow.parser.javacc.SimpleNode;
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
	private final Map<Type,Node> typeTable = new HashMap<Type,Node>();
	// Map of file paths (without extensions) to nodes.
    private final Map<String,Node> fileTable = new HashMap<String,Node>();		
	private final boolean useSourceFiles;
	// Holds all of the imports we know about.
	private final List<String> importList = new ArrayList<String>(); 
	private final LinkedList<Object> importedItems = new LinkedList<Object>();
	private final Configuration config;
	
	private File currentFile;
	private Type mainType = null;
	private String currentName = "";	
	
	/**
	 * Creates a new <code>TypeCollector</code> with the given tree of packages. 
	 * @param p					package tree
	 * @param useSourceFiles	if true, always use <tt>.shadow</tt> instead of <tt>.meta</tt> files
	 * @throws ConfigurationException
	 */
	public TypeCollector( Package p, boolean useSourceFiles ) throws ConfigurationException {		
		super( p );		  
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
	public  Map<String,Node> getFileTable() {
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
    public Map<Type,Node> collectTypes( File mainFile )
    		throws ParseException, ShadowException, TypeCheckException, IOException, ConfigurationException {
        List<File> initialFiles = new ArrayList<File>();
        initialFiles.add( mainFile );
        return collectTypes( initialFiles, true );
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
    public Map<Type, Node> collectTypes( List<File> files )
    		throws ParseException, ShadowException, TypeCheckException, IOException, ConfigurationException {
        return collectTypes( files, false );
    }
   
    /*
     * Calls the full <code>collectTypes</code> and might call it a second time
     * if needed to determine what should be recompiled.
     */
    private Map<Type, Node> collectTypes( List<File> files, boolean hasMain ) throws ParseException, ShadowException, TypeCheckException, IOException, ConfigurationException {
        Set<String> mustRecompile = new HashSet<String>();
        Map<String, TreeSet<String>> dependencies = new HashMap<String, TreeSet<String>>();
        
        // Initial type collection
        collectTypes( files, hasMain, mustRecompile, dependencies );
        
        // Files needing recompilation may trigger other files to get recompiled.
        // Figure out which ones and redo the whole type collection process.
        if( errorList.size() == 0 && !useSourceFiles && mustRecompile.size() > 0 ) {
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
        	collectTypes( files, hasMain, mustRecompile, null );
		}        
		
        // Check packages for errors.
		checkPackageDirectories(packageTree);
		
		if( errorList.size() > 0 ) {
			printErrors();
			printWarnings();
			throw errorList.get(0);
		}
		
		printWarnings();
		
		// Return a table of all the types and their corresponding nodes.
		return typeTable;
	}
    
    /*
     * Does actual collection of types based on a list of files. 
     */
    private void collectTypes(List<File> files, boolean hasMain,
    		Set<String> mustRecompile, Map<String,TreeSet<String>> dependencies)
    		throws ParseException, ShadowException, TypeCheckException, IOException, ConfigurationException {
        // Create and fill the initial set of files to be checked.
        TreeSet<String> uncheckedFiles = new TreeSet<String>();
        
        String main = null; // May or may not be null, based on hasMain.
        if (files.isEmpty()) {
            throw new ConfigurationException("No files provided for typechecking");
        }
        else if (hasMain) {
            // Assume the main file is the first and only file.
            main = stripExtension(files.get(0).getCanonicalPath());
            uncheckedFiles.add(main);
        }
        else {
            for (File file : files) {
            	String path = stripExtension(file.getCanonicalPath());
            	uncheckedFiles.add(path);   
            }
        }
       
        FilenameFilter filter = 
	        new FilenameFilter() {
	            public boolean accept(File dir, String name) {
	                return name.endsWith(".shadow");
	            }
	        };
				
		/* Add standard imports. */		
		File standard = new File( config.getSystemImport().toFile(), "shadow" + File.separator + "standard" );
		if( !standard.exists() )
			throw new ConfigurationException("Invalid path to shadow:standard: " + standard.getCanonicalPath());
		
		TreeSet<String> standardDependencies = new TreeSet<String>(); 
		
		File[] imports = standard.listFiles( filter );
		for( File file :  imports ) {
			String name = stripExtension(file.getCanonicalPath());			
			uncheckedFiles.add(name);
			standardDependencies.add(name);
		}
		
		/* Add io imports (necessary for console programs). */		
		File io = new File( config.getSystemImport().toFile(), "shadow" + File.separator + "io" );
		if( !io.exists() )
			throw new ConfigurationException("Invalid path to shadow:io: " + io.getCanonicalPath());
		
		imports = standard.listFiles( filter );
		for( File file :  imports ) {
			String name = stripExtension(file.getCanonicalPath());			
			uncheckedFiles.add(name);
			standardDependencies.add(name);
		}

		/* As long as there are unchecked files, remove one and process it. */
		while( !uncheckedFiles.isEmpty() ) {			
			String canonical = uncheckedFiles.first();
			uncheckedFiles.remove(canonical);	
			
			File canonicalFile = new File(canonical + ".shadow");
			
			// Depending on the circumstances, the compiler may choose to either
			// compile/recompile source files, or rely on existing binaries/IR.
			if( canonicalFile.exists() ) {											
				File meta = new File(canonical + ".meta");
				File llvm = new File(canonical + ".ll");
				
				// If source compilation was not requested and the binaries exist
				// that are newer than the source, use those binaries.
				if( !useSourceFiles &&
					!mustRecompile.contains(canonical) &&
					meta.exists() && meta.lastModified() >= canonicalFile.lastModified() &&
					llvm.exists() && llvm.lastModified() >= meta.lastModified())
					canonicalFile = meta;				
				else
					mustRecompile.add(canonical);
			}
			else if (!useSourceFiles)
				canonicalFile  = new File(canonical + ".meta");

			ShadowParser parser = new ShadowFileParser(canonicalFile);				
			currentFile = canonicalFile;
		    Node node = parser.CompilationUnit();

		    // Make another collector to walk the current file. 
			TypeCollector collector = new TypeCollector( new Package(), useSourceFiles );
			// Keeping a current files gives us a file whose directory we can check against.
			collector.currentFile = currentFile; 
			ASTWalker walker = new ASTWalker( collector );		
			walker.walk( node );	
			
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
						addWarning(Error.MISMATCHED_PACKAGE, message);
					}											
				}
				catch(PackageException e) {
					addError(Error.INVALID_PACKAGE, e.getMessage() );				
				}
			}			
			
			// Copy errors for the other collector into our error list.
			if( collector.errorList.size() > 0 )
				errorList.addAll(collector.errorList);
			
			// Copy warnings.
			if( collector.warningList.size() > 0 )
				warningList.addAll(collector.warningList);
			
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
			File[] directoryFiles = canonicalFile.getParentFile().listFiles( filter );		

			for( File file :  directoryFiles ) {
				String name = stripExtension(file.getCanonicalPath()); 
				if( !fileTable.containsKey(name) )
					uncheckedFiles.add(name);
				
				if( dependencySet != null )
					dependencySet.add(name);
			}
			
			/* Copy file table from other collector into our table. */
			Map<Type,Node> otherNodeTable = collector.typeTable;
			for( Type type : otherNodeTable.keySet() ) {
				if( !typeTable.containsKey(type) ) {
					Node otherNode = otherNodeTable.get(type);					
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
		File path1 = null;
		
		/* Gets the directory of the first type and compares all others to it. */
		if( types.size() > 1 ) {
			for( Type type : types ) {
				if( firstType == null ) {
					firstType = type;
					path1 = typeTable.get( type ).getFile().getParentFile();
				}
				else {					
					File path2 = typeTable.get( type ).getFile().getParentFile();
					if( !path1.equals( path2 ) )
						addWarning(Error.MISMATCHED_PACKAGE, "Type " + firstType +
								" and " + type + " both belong to package " + _package +
								" but are defined in different directories");
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
	private Object createType( SimpleNode node, Modifiers modifiers, 
			Documentation documentation, TypeKind kind ) throws ShadowException {		 
		String typeName;
		
		if( node.jjtGetNumChildren() > 0 &&
				(node.jjtGetChild( 0 ) instanceof ASTUnqualifiedName) ) {
			
			if( currentType == null ) {
				String name = node.jjtGetChild(0).getImage();									
				currentPackage = packageTree.addQualifiedPackage(name);
			}
			else {
				addError(Error.INVALID_PACKAGE, "Package can only be defined by outermost classes" );
				return WalkType.NO_CHILDREN;
			}
		}
		
		String image = node.getImage();	
		
		/* For outer types, check that type name matches file name (if defined in a file), 
		 * and that the package name matches the directory path. */
		if( currentType == null && node.getFile() != null ) {
			File file = node.getFile();
			String fileName = stripExtension( file.getName() );
			if( !fileName.equals( image ) ) { // Check file name.
				addError(Error.INVALID_FILE, "Type " + image +
						" must be declared in a file named " +
						image + ".shadow or " + image + ".meta" );
				return WalkType.NO_CHILDREN;
			}
			else { // Check package path.			
				Package _package = currentPackage;
				File parent = file.getParentFile();
				
				while( _package != packageTree && parent != null ) {	
					if( !_package.getName().equals( parent.getName() ) ) {
						addError(Error.INVALID_PACKAGE, "Type " + image +
								" cannot be added to package " + currentPackage.getQualifiedName() +
								" unless it is defined in directory " + currentPackage.getPath() );
						return WalkType.NO_CHILDREN;						
					}
					
					parent = parent.getParentFile();
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
			   (image.equals( "Boolean" ) ||
				image.equals( "Byte" ) ||
				image.equals( "Code" ) ||
				image.equals( "Short" ) ||
				image.equals( "Int" ) ||
				image.equals( "Long" ) ||
				image.equals( "Float" ) ||
				image.equals( "Double" ) ||
				image.equals( "UByte" ) ||
				image.equals( "UInt" ) ||
				image.equals( "ULong" ) ||
				image.equals( "UShort" )) )			
			image = image.toLowerCase();				
		
		// Current name contains package or outer class.
		if( currentType == null )
			typeName = currentName + image; 
		else
			typeName = currentName + ":" + image;
		
		if( lookupType( typeName ) != null ) {
			addError(Error.MULTIPLY_DEFINED_SYMBOL, "Type " + typeName + " already defined" );
			return WalkType.NO_CHILDREN;
		}
		else { // Set kind of type and create it with the appropriate name and outer type.			
			Type type = null;			
			switch( kind ) {			
			case CLASS:
				type = new ClassType(image, modifiers, documentation, currentType );				
				break;
			case ENUM:				
				type = new EnumType(image, modifiers, documentation, currentType );
				break;			
			case EXCEPTION:
				type = new ExceptionType(image, modifiers, documentation, currentType );
				break;
			case INTERFACE:
				type = new InterfaceType(image, modifiers, documentation);
				break;
			case SINGLETON:
				type = new SingletonType(image, modifiers, documentation, currentType );
				break;
			default:
				throw new ShadowException( "Unsupported type" );
			}
			
			// Put new type inside of outer type, if it exists.
			if( currentType != null && currentType instanceof ClassType &&					
				(kind == TypeKind.CLASS || kind == TypeKind.ENUM || kind == TypeKind.EXCEPTION) )					
				((ClassType)currentType).addInnerClass(image, (ClassType)type); 
			
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
				case "ClassSet":		Type.CLASS_SET = (ClassType) type; break;
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
				case "Number":			Type.NUMBER = (InterfaceType) type; break;
				case "Object":			Type.OBJECT = (ClassType) type; break;				
				case "short":			Type.SHORT = (ClassType)type; break;
				case "String":			Type.STRING = (ClassType) type; break;
				case "ubyte":			Type.UBYTE = (ClassType)type; break;
				case "uint":			Type.UINT = (ClassType)type; break;
				case "ulong":			Type.ULONG = (ClassType)type; break;				
				case "UnexpectedNullException": Type.UNEXPECTED_NULL_EXCEPTION = (ExceptionType)type; break;
				case "ushort":			Type.USHORT = (ClassType)type; break;
				}
			}		
			
			// Let type know what it has imported.
			type.addImportedItems( importedItems );
			
			// Put new type in its package.
			try	{			
				currentPackage.addType( type );
			}
			catch( PackageException e ) {
				addError( Error.INVALID_PACKAGE, e.getMessage() );
				return WalkType.NO_CHILDREN;
			}
			
			// Update the type of the declaration node and the current declaration type.
			node.setType( type );	
			declarationType = type;
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	/*
	 * Adds a type or a whole package to the current list of imports.
	 */
	public boolean addImport( String name ) {
		String separator = File.separator; // Adds some platform independence.		
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
				importPath = currentFile.toPath().getParent().resolve(importPath);
				
				/* No @, must be a whole package import. 
				 * Add everything in the directory. */
				if( !path.contains("@")) {  						
					File fullPath = new File( importPath.toFile(), path );
					if( fullPath.isDirectory() ) {
						File[] matchingShadow = fullPath.listFiles(
						new FilenameFilter() {							
							@Override
							public boolean accept(File dir, String name) {
								return name.endsWith(".shadow");
							}
						} );
						

						File[] matchingMeta = fullPath.listFiles(
						new FilenameFilter() {							
							@Override
							public boolean accept(File dir, String name) {
								return name.endsWith(".meta");
							}    
						} );
						
						try {						
							for( File file : matchingShadow )							
								importList.add(stripExtension(file.getCanonicalPath()));
															
							for( File file : matchingMeta ) {
								String canonicalPath = stripExtension(file.getCanonicalPath());
								if( !importList.contains( canonicalPath ) )
									importList.add( canonicalPath );
							}
							
							success = true;
						}
						catch (IOException e) {}												
					}
				}
				/* Single file import. */
				else {  
					File shadowVersion;
					File metaVersion;
					String fixedPath;
				
					if( path.startsWith("default") ) {
						fixedPath = path.replaceFirst( "default@", "" );
						shadowVersion = new File( currentFile.getParent(),  fixedPath + ".shadow");
						metaVersion = new File( currentFile.getParent(),  fixedPath + ".meta");
					}
					else {
						fixedPath = path.replaceAll("@", separator);
						shadowVersion = new File( importPath.toFile(), fixedPath + ".shadow" );
						metaVersion = new File( importPath.toFile(), fixedPath + ".meta" );
					}
					
					try {						
						if( shadowVersion.exists() ) {							
							importList.add(stripExtension(shadowVersion.getCanonicalPath()));							
							success = true;						
						}
						else if( metaVersion.exists() ) {
							importList.add(stripExtension(metaVersion.getCanonicalPath()));							
							success = true;						
						}
					} 
					catch (IOException e) {}					
				}
				
				if( success )
					return true;
			}
		}
		else
			addError(Error.INVALID_IMPORT, "No import paths specified, cannot import " + name);
		
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
	
	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit ) {
			currentPackage = packageTree;
			importedItems.clear();
			importedItems.add("shadow:standard");			
			currentName = "";
		}
		
		return WalkType.PRE_CHILDREN;			
	}	
	
	@Override
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit ) {
			String name = node.getImage();
			if( node.jjtGetNumChildren() > 0 ) // Has @ sign.
				name = node.jjtGetChild(0).getImage() + "@" + name;
			
			if( addImport( name ) )
				importedItems.add(name);
			else
				addError(Error.INVALID_IMPORT, "No file found for import " + name);		
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit ) {
			Type type = node.getType().getTypeWithoutTypeArguments();
			typeTable.put(type, node );
			node.jjtGetParent().setType( type );
			return WalkType.POST_CHILDREN;
		}
		else
			return createType(node, node.getModifiers(), 
					node.getDocumentation(), node.getKind());		
	}
	
	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit ) {
			typeTable.put(node.getType().getTypeWithoutTypeArguments(), node );
			return WalkType.POST_CHILDREN;
		}
		else
			return createType(node, node.getModifiers(),
					node.getDocumentation(), TypeKind.ENUM);
	}	

	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit ) { // Leaving a type.		
			currentType = currentType.getOuter();
			if( currentType == null )
				currentName = currentPackage.getQualifiedName();
			else
				currentName = currentType.getTypeName();
		}
		else { // Entering a type.							
			currentType = node.jjtGetParent().getType();
			currentName = currentType.getTypeName();				
		}
			
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit) throws ShadowException {	
		if( declarationType != null )
			declarationType.setParameterized( true );	
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit ) {			
			if ( node.jjtGetNumChildren() > 0) {
				boolean colon = true;
				Node first = node.jjtGetChild(0);
				String name = first.getImage();
				if( first instanceof ASTUnqualifiedName ) {
					name += "@";
					colon = false;
				}
				
				for( int i = 1; i < node.jjtGetNumChildren(); i++ )  {	
					if( colon )
						name += ":";
					else
						colon = true;
					
					name += node.jjtGetChild(i).getImage();					
				}
				
				node.setImage(name);
				
				if( first instanceof ASTUnqualifiedName ) {
					String importName = name;					
					if( importName.contains(":"))
						importName = importName.substring(0, importName.indexOf(':'));
					if( !addImport( importName ) )
						addError(Error.INVALID_IMPORT, "No file found for import " + importName);
				}
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit ) {
			/* Triggers an import (adding a file to the compilation process) 
			 * since there's an ASTUnqualifiedName@ */
			if( node.jjtGetNumChildren() > 0 ) {
				Node child = node.jjtGetChild( 0 );				
				
				if( child instanceof ASTUnqualifiedName ) {
					String name = child.getImage() + "@" +  node.getImage();
					if( !addImport( name ) )
						addError( Error.INVALID_IMPORT, "No file found for import " + name );
				}
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit( ASTTypeArguments node, Boolean secondVisit ) throws ShadowException {	
		if( secondVisit ) {
			StringBuilder builder = new StringBuilder();
			builder.append("<");
			for( int i = 0; i < node.jjtGetNumChildren(); i++ ) {
				Node child = node.jjtGetChild(i);
				if( i > 0 )
					builder.append(", ");
				builder.append(child.getImage());
			}
			builder.append(">");
			node.setImage(builder.toString());
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit( ASTReferenceType node, Boolean secondVisit ) throws ShadowException {		
		if( secondVisit ) {
			StringBuilder builder = new StringBuilder(node.jjtGetChild(0).getImage());
			List<Integer> dimensions = node.getArrayDimensions();
			
			for( int i = 0; i < dimensions.size(); i++ ) {				
				builder.append("[");
				
				for( int j = 1; j < dimensions.get(i); j++ )
					builder.append(",");				
				
				builder.append("[");
			}				
			
			node.setImage(builder.toString());
		}
	
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit) throws ShadowException {		
		node.setType( nameToPrimitiveType( node.getImage() ) );		
		return WalkType.NO_CHILDREN;			
	}
}
