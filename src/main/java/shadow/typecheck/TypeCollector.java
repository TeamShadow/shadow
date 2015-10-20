package shadow.typecheck;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import shadow.Configuration;
import shadow.ConfigurationException;
import shadow.Job;
import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
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

public class TypeCollector extends BaseChecker {
	
	private Map<Type,Node> nodeTable = new HashMap<Type,Node>(); //for errors and also resolving type parameters	
	private String currentName = "";
	private Map<String, Node> files = new HashMap<String, Node>();	
	private File currentFile;
	private Type mainType = null;
	private Job currentJob;
	private Configuration config;
	
	protected LinkedList<Object> importedItems = new LinkedList<Object>();	
	
	public TypeCollector(HashMap< Package, HashMap<String, Type>> typeTable, ArrayList<String> importList, Package p, Job currentJob) throws ConfigurationException {		
		super(typeTable, importList, p);
		this.currentJob = currentJob;
		config = Configuration.getConfiguration();
	}	
	
	public Map<Type,Node> getNodeTable() {
		return nodeTable;
	}				
	
	public Type getMainType() {
		return mainType;
	}
		
	public Map<Type, Node> collectTypes(File mainFile) throws ParseException, ShadowException, TypeCheckException, IOException, ConfigurationException {			
		//Create walker
		ASTWalker walker = new ASTWalker( this );		
		TreeSet<String> uncheckedFiles = new TreeSet<String>();
		String main = stripExtension(mainFile.getCanonicalPath());
		mainFile = mainFile.getCanonicalFile();
		boolean forceGenerate = currentJob.isForceRecompile();
		//add file to be checked to list
		uncheckedFiles.add(main);
		
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return name.endsWith(".shadow");
			}
		};
				
		//add standard imports		
		File standard = new File( config.getSystemImport().toFile(), "shadow" + File.separator + "standard" );
		if( !standard.exists() )
			throw new ConfigurationException("Invalid path to shadow:standard: " + standard.getCanonicalPath());
			
		File[] imports = standard.listFiles( filter );
		for( File file :  imports )			
			uncheckedFiles.add(stripExtension(file.getCanonicalPath()));
		
		//add io imports
		File io = new File( config.getSystemImport().toFile(), "shadow" + File.separator + "io" );
		if( !io.exists() )
			throw new ConfigurationException("Invalid path to shadow:io: " + io.getPath());
		
		imports = io.listFiles( filter );
		for( File file :  imports )			
			uncheckedFiles.add(stripExtension(file.getCanonicalPath()));
		
		//add utility imports
		File utility = new File( config.getSystemImport().toFile(), "shadow" + File.separator + "utility" );
		if( !utility.exists() )
			throw new ConfigurationException("Invalid path to shadow:utility: " + utility.getPath());
		
		imports = utility.listFiles( filter );
		for( File file :  imports )			
			uncheckedFiles.add(stripExtension(file.getCanonicalPath()));
						
		while(!uncheckedFiles.isEmpty())
		{			
			String canonical = uncheckedFiles.first();
			uncheckedFiles.remove(canonical);	
							
			File canonicalFile = new File(canonical + ".shadow");
			if( canonicalFile.exists() ) {											
				File meta = new File( canonical + ".meta" );
				File llvm = new File( canonical + ".ll" );
				if( !forceGenerate &&
					meta.exists() && meta.lastModified() >= canonicalFile.lastModified() &&
					llvm.exists() && llvm.lastModified() >= meta.lastModified() &&
					!canonicalFile.equals(mainFile) )//check for more recent .meta file
					canonicalFile = meta;
			}
			else		
				canonicalFile  = new File(canonical + ".meta");		
			
			ShadowParser parser = new ShadowFileParser(canonicalFile);				
			currentFile = canonicalFile;
		    Node node = parser.CompilationUnit();
		    		    
		    HashMap<Package, HashMap<String, Type>> otherTypes = new HashMap<Package, HashMap<String, Type>> ();			    
			TypeCollector collector = new TypeCollector(otherTypes, new ArrayList<String>(), new Package(otherTypes), currentJob);
			collector.currentFile = currentFile; //for now, so that we have a file whose directory we can check
			walker = new ASTWalker( collector );		
			walker.walk(node);	
			
			if( canonical.equals(main) )
				mainType = node.getType();
			
			files.put(canonical, node);
			
			//copy other types into our package tree				
			for( Package p : otherTypes.keySet() )
			{
				//if package already exists, it won't be recreated
				Package newPackage = packageTree.addQualifiedPackage(p.getQualifiedName(), typeTable);
				try
				{	
					HashMap<String, Type> types = otherTypes.get(p);
					newPackage.addTypes( types  );					
					
					if( mainType != null && newPackage == packageTree && mainType.getPackage() != packageTree  ) {
						//imported class has default package but the main type doesn't
						//the only classes without a package that will be imported will be in the same directory as the main type
						//implication: classes in the same directory have different packages
						for(Type type : types.values()) {
							String message = "Type " + type + " belongs to the default package, but types defined in the same directory belong to other packages";
							addWarning(Error.MISMATCHED_PACKAGE, message);
						}
					}											
				}
				catch(PackageException e)
				{
					addError(Error.INVALID_PACKAGE, e.getMessage() );				
				}
			}
			
			//copy any errors into our error list
			if( collector.getErrorCount() > 0 )
				errorList.addAll(collector.errorList);
			
			//copy any warnings
			if( collector.getWarningCount() > 0 )
				warningList.addAll(collector.warningList);
			
			for( String _import : collector.getImportList() )
			{
				if( !files.containsKey(_import) )
					uncheckedFiles.add(_import);					
			}
			
			//Add files in directory after imports
			File[] directoryFiles = canonicalFile.getParentFile().listFiles( filter );		

			for( File file :  directoryFiles )
			{
				String name = stripExtension(file.getCanonicalPath()); 
				if( !files.containsKey(name) )
					uncheckedFiles.add(name);
			}
			
			//copy tables from other file into our central table
			Map<Type,Node> otherNodeTable = collector.nodeTable;
			for( Type type : otherNodeTable.keySet() ) {
				if( !nodeTable.containsKey(type) ) {
					Node otherNode = otherNodeTable.get(type);					
					nodeTable.put(type, otherNode);
				}
			}
		}
		
		checkPackageDirectories(packageTree);
		
		if( errorList.size() > 0 )
		{
			printErrors();
			printWarnings();
			throw errorList.get(0);
		}
		
		printWarnings();
		
		//return the node corresponding to the file being compiled
		return nodeTable;
	}
	
	private void checkPackageDirectories(Package _package) {		
		Collection<Type> types = _package.getTypes();
		
		Type firstType = null;
		File path1 = null;
		
		if( types.size() > 1 ) {
			for( Type type : types ) {
				if( firstType == null ) {
					firstType = type;
					path1 = nodeTable.get(type).getFile().getParentFile();
				}
				else {					
					File path2 = nodeTable.get(type).getFile().getParentFile();					
					
					if( !path1.equals(path2))
						addWarning(Error.MISMATCHED_PACKAGE, "Type " + firstType + " and " + type + " both belong to package " + _package + " but are defined in different directories");
				}
			}
		}
		
		for( Package child : _package.getChildren().values()  )
			checkPackageDirectories(child);
	}

	private Object createType( SimpleNode node, Modifiers modifiers, TypeKind kind ) throws ShadowException
	{		 
		String typeName;
		
		if( node.jjtGetNumChildren() > 0 && (node.jjtGetChild(0) instanceof ASTUnqualifiedName) )
		{
			
			if( currentType == null )
			{
				String name = node.jjtGetChild(0).getImage();									
				currentPackage = packageTree.addQualifiedPackage(name, typeTable);
			}
			else
			{
				addError(Error.INVALID_PACKAGE, "Package can only be defined by outermost classes" );
				return WalkType.NO_CHILDREN;
			}
		}
		
		String image = node.getImage();	
		
		//for outer types, check that type name matches file name (if using a file)
		if( currentType == null && node.getFile() != null )
		{
			File file = node.getFile();
			String fileName = stripExtension(file.getName());
			if( !fileName.equals(image) )
			{
				addError(Error.INVALID_FILE, "Type " + image + " must be declared in a file named " + image + ".shadow or " + image + ".meta" );
				return WalkType.NO_CHILDREN;
			}
			else //check packages
			{
				Package _package = currentPackage;
				File parent = file.getParentFile();
				
				while( _package != packageTree && parent != null )
				{	
					if( !_package.getName().equals(parent.getName()) )
					{
						addError(Error.INVALID_PACKAGE, "Type " + image + " cannot be added to package " + currentPackage.getQualifiedName() + " unless it is defined in directory " + currentPackage.getPath() );
						return WalkType.NO_CHILDREN;						
					}
					
					parent = parent.getParentFile();
					_package = _package.getParent();
				}
			}			
		}		
		
		
		if( currentPackage.getQualifiedName().equals("shadow:standard"))
		{
			if( image.equals("Boolean") ||
				image.equals("Byte") ||
				image.equals("Code") ||
				image.equals("Short") ||
				image.equals("Int") ||
				image.equals("Long") ||
				image.equals("Float") ||
				image.equals("Double") ||
				image.equals("UByte") ||
				image.equals("UInt") ||
				image.equals("ULong") ||
				image.equals("UShort") )
			{
				image = image.toLowerCase();				
			}
		}	
		
		if( currentType == null )
			typeName = currentName + image; //package name is separate
		else
			typeName = currentName + ":" + image;
		
		if( lookupType(typeName) != null )
		{
			addError(Error.MULTIPLY_DEFINED_SYMBOL, "Type " + typeName + " already defined" );
			return WalkType.NO_CHILDREN;
		}
		else
		{			
			Type type = null;
			
			switch( kind )
			{			
			case CLASS:
				type = new ClassType(typeName, modifiers, currentType );				
				break;
			case ENUM:				
				type = new EnumType(typeName, modifiers, currentType );
				break;			
			case EXCEPTION:
				type = new ExceptionType(typeName, modifiers, currentType );
				break;
			case INTERFACE:
				type = new InterfaceType(typeName, modifiers);
				break;
			case SINGLETON:
				type = new SingletonType(typeName, modifiers, currentType );
				break;
			default:
				throw new ShadowException("Unsupported type!" );
			}
			
			if( currentType != null && 
				currentType instanceof ClassType &&					
				( kind == TypeKind.CLASS ||
				kind == TypeKind.ENUM ||				
				kind == TypeKind.EXCEPTION) )
			{					
					((ClassType)currentType).addInnerClass(image, (ClassType)type); 
			}
			
			//Special case for system types			
			if( currentPackage.getQualifiedName().equals("shadow:standard"))
			{	
				switch( typeName )
				{
				//case "AbstractClass":	Type.ABSTRACT_CLASS = (ClassType) type; break;
				case "AddressMap":		Type.ADDRESS_MAP = (ClassType) type; break;
				case "Array":			Type.ARRAY = (ClassType) type; break;
				case "ArrayClass":		Type.ARRAY_CLASS = (ClassType) type; break;
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
				case "Method":			Type.METHOD = (ClassType)type; break;
				//case "MethodClass":		Type.METHOD_CLASS = (ClassType) type; break;
				case "Number":			Type.NUMBER = (InterfaceType) type; break;
				case "Object":			Type.OBJECT = (ClassType) type; break;				
				case "short":			Type.SHORT = (ClassType)type; break;
				case "String":			Type.STRING = (ClassType) type; break;
				case "ubyte":			Type.UBYTE = (ClassType)type; break;
				case "uint":			Type.UINT = (ClassType)type; break;
				case "ulong":			Type.ULONG = (ClassType)type; break;
				case "UnboundMethod":	Type.UNBOUND_METHOD = (ClassType)type; break;
				case "UnexpectedNullException": Type.UNEXPECTED_NULL_EXCEPTION = (ExceptionType)type; break;
				case "ushort":			Type.USHORT = (ClassType)type; break;
				}
			}
		
			
			type.addImportedItems( importedItems );
			
			try
			{			
				addType( type, currentPackage );
			}
			catch(PackageException e)
			{
				addError(Error.INVALID_PACKAGE, e.getMessage() );
				return WalkType.NO_CHILDREN;
			}
			
			node.setType(type);	
			declarationType = type;
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public boolean addImport( String name )
	{
		String separator = File.separator; // Hopefully platform independent
		if( separator.equals("\\"))
			separator = "\\\\";
		String path = name.replaceAll(":", separator);
		
		// Which import paths are used depends on whether the requested package
		// is in the standard library or not
		List<Path> importPaths;
		if (path.startsWith("shadow")) {
			importPaths = new ArrayList<Path>();
			importPaths.add(config.getSystemImport());
		} else {
			importPaths = config.getImports();
		}
		
		boolean success = false;				
		
		if( importPaths != null && importPaths.size() > 0 )
		{
			for( Path importPath : importPaths )
			{	
				// If an import path is relative, resolve it against the
				// current source file
				importPath = currentFile.toPath().getParent().resolve(importPath);
				
				if( !path.contains("@"))  //no @, must be a whole package import
				{		
					File fullPath = new File( importPath.toFile(), path );
					if( fullPath.isDirectory() )
					{
						File[] matchingShadow = fullPath.listFiles( new FilenameFilter(){							
							@Override
							public boolean accept(File dir, String name)
							{
								return name.endsWith(".shadow");
							}    }   );
						

						File[] matchingMeta = fullPath.listFiles( new FilenameFilter(){							
							@Override
							public boolean accept(File dir, String name)
							{
								return name.endsWith(".meta");
							}    }   );
						
						try 
						{						
							for( File file : matchingShadow )							
									importList.add(stripExtension(file.getCanonicalPath()));
															
							for( File file : matchingMeta )
							{
								String canonicalPath = stripExtension(file.getCanonicalPath());
								if( !importList.contains(canonicalPath))
									importList.add(canonicalPath);
							}
							
							success = true;
						}
						catch (IOException e) 
						{}												
					}
				}
				else
				{
					File shadowVersion;
					File metaVersion;
				
					if( path.startsWith("default"))
					{
						path = path.replaceFirst("default@", "");
						shadowVersion = new File( currentFile.getParent(),  path + ".shadow");
						metaVersion = new File( currentFile.getParent(),  path + ".meta");
					}
					else
					{
						path = path.replaceAll("@", separator);
						shadowVersion = new File( importPath.toFile(), path + ".shadow" );
						metaVersion = new File( importPath.toFile(), path + ".meta" );
					}
					
					try
					{						
						if( shadowVersion.exists() )
						{							
							importList.add(stripExtension(shadowVersion.getCanonicalPath()));							
							success = true;						
						}
						else if( metaVersion.exists() )
						{
							importList.add(stripExtension(metaVersion.getCanonicalPath()));							
							success = true;						
						}
					} 
					catch (IOException e)
					{						
					}
					
				}
				
				if( success )
					return true;
			}	
			
		}
		else
			addError(Error.INVALID_IMPORT, "No import paths specified, cannot import " + name);
		
		return false;
	}
	
	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )	
		{
			currentPackage = packageTree;
			importedItems.clear();
			importedItems.add("shadow:standard");			
			currentName = "";
		}
		
		return WalkType.PRE_CHILDREN;			
	}
	
	
	@Override
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			String name = node.getImage();
			if( node.jjtGetNumChildren() > 0 ) //has @ sign
				name = node.jjtGetChild(0).getImage() + "@" + name;
			
			if( addImport( name ) )
				importedItems.add(name);
			else
				addError(Error.INVALID_IMPORT, "No file found for import " + name);		
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	//Visitors below this point
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
		{
			Type type = node.getType().getTypeWithoutTypeArguments();
			nodeTable.put(type, node );
			node.jjtGetParent().setType( type );
			return WalkType.POST_CHILDREN;
		}
		else
			return createType( node, node.getModifiers(), node.getKind() );
	}
	
	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
		{
			nodeTable.put(node.getType().getTypeWithoutTypeArguments(), node );
			return WalkType.POST_CHILDREN;
		}
		else
			return createType( node, node.getModifiers(), TypeKind.ENUM );
	}	

	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit ) //leaving a type
		{
			currentType = currentType.getOuter();
			if( currentType == null )
				currentName = currentPackage.getQualifiedName();
			else
				currentName = currentType.getTypeName();
		}
		else //entering a type
		{					
			currentType = node.jjtGetParent().getType();
			currentName = currentType.getTypeName();				
		}
			
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit) throws ShadowException
	{	
		if( declarationType != null )
			declarationType.setParameterized(true);	
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{			
			if ( node.jjtGetNumChildren() > 0)
			{
				boolean colon = true;
				Node first = node.jjtGetChild(0);
				String name = first.getImage();
				if( first instanceof ASTUnqualifiedName )
				{
					name += "@";
					colon = false;
				}
				
				for( int i = 1; i < node.jjtGetNumChildren(); i++ ) 
				{	
					if( colon )
						name += ":";
					else
						colon = true;
					
					name += node.jjtGetChild(i).getImage();					
				}
				
				node.setImage(name);
				
				if( first instanceof ASTUnqualifiedName )
				{
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
	
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			//triggers an import (adding a file to the compilation process) 
			//since there's an ASTUnqualifiedName@
			if( node.jjtGetNumChildren() > 0 ) 
			{
				Node child = node.jjtGetChild(0);				
				
				if( child instanceof ASTUnqualifiedName )
				{
					String name = child.getImage() + "@" +  node.getImage();
					if( !addImport( name ) )
						addError(Error.INVALID_IMPORT, "No file found for import " + name);
				}
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTTypeArguments node, Boolean secondVisit) throws ShadowException
	{	
		if( secondVisit )
		{
			StringBuilder builder = new StringBuilder();
			builder.append("<");
			for( int i = 0; i < node.jjtGetNumChildren(); i++ ) 
			{
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
	
	public Object visit(ASTReferenceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			StringBuilder builder = new StringBuilder(node.jjtGetChild(0).getImage());
			List<Integer> dimensions = node.getArrayDimensions();
			
			for( int i = 0; i < dimensions.size(); i++ )
			{
				
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
	public Object visit(ASTPrimitiveType node, Boolean secondVisit) throws ShadowException
	{		
		node.setType(nameToPrimitiveType(node.getImage()));		
		return WalkType.NO_CHILDREN;			
	}

	public void setNodeTable(Map<Type, Node> nodeTable) {
		this.nodeTable = nodeTable;		
	}
}
