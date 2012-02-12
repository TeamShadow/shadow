package shadow.typecheck;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import shadow.Configuration;
import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTClassOrInterfaceBody;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTClassOrInterfaceTypeSuffix;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTExtendsList;
import shadow.parser.javacc.ASTImplementsList;
import shadow.parser.javacc.ASTImportDeclaration;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPackageDeclaration;
import shadow.parser.javacc.ASTTypeArgument;
import shadow.parser.javacc.ASTTypeArguments;
import shadow.parser.javacc.ASTTypeParameter;
import shadow.parser.javacc.ASTTypeParameters;
import shadow.parser.javacc.ASTUnqualifiedName;
import shadow.parser.javacc.ASTViewDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ErrorType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.Type.Kind;
import shadow.typecheck.type.TypeParameterRepresentation;

public class TypeCollector extends BaseChecker
{	
	protected Map<Type,List<String>> extendsTable = new HashMap<Type,List<String>>();
	protected Map<Type,Node> nodeTable = new HashMap<Type,Node>(); //for errors only
	protected Map<Type,List<String>> implementsTable = new HashMap<Type,List<String>>();
	protected Map<Type,List<TypeParameterRepresentation>> typeParameterTable = new HashMap<Type,List<TypeParameterRepresentation>>();
	protected String currentName = "";
	protected Map<File, Node> files = new HashMap<File, Node>();
	
	public TypeCollector(boolean debug,HashMap< Package, HashMap<String, Type>> typeTable, LinkedList<File> importList, Package p )
	{		
		super(debug, typeTable, importList, p );
		// put all of our built-in types into the TypeTable		
		
		//addType(Type.OBJECT);
		//addType(Type.STRING);
		addType(Type.BOOLEAN);
		addType(Type.BYTE);
		addType(Type.CODE);
		addType(Type.SHORT);
		addType(Type.INT);
		addType(Type.LONG);
		addType(Type.FLOAT);
		addType(Type.DOUBLE);
	
		addType(Type.UBYTE);
		addType(Type.UINT);
		addType(Type.ULONG);
		addType(Type.USHORT);
		addType(Type.NULL);	
		
		addType(Type.ENUM);
		addType(Type.ERROR);	
		addType(Type.EXCEPTION);
	}
	
	
	private void updateMissingTypes()
	{
		List<String> list;
		
		for( Package p : getTypeTable().keySet() )
		{
			for( Type type : getTypeTable().get(p).values() ) //look through all types, updating their extends and implements
			{	
				TreeSet<String> missingTypes = new TreeSet<String>();
				
				if( type instanceof ClassType ) //includes error, exception, and enum (for now)
				{		
					ClassType classType = (ClassType)type;
					if( !type.isBuiltIn() )
					{						
						if( extendsTable.containsKey(type))
						{
							list = extendsTable.get(type);
							ClassType parent = (ClassType)lookupTypeStartingAt(list.get(0), classType.getOuter()); //only one thing in extends lists for classes
							if( parent == null )						
								missingTypes.add(list.get(0));
							else							
								classType.setExtendType(parent);
						}
						else if( type.getKind() == Kind.CLASS )													
							classType.setExtendType(Type.OBJECT);						
						else if( type.getKind() == Kind.ENUM )
							classType.setExtendType(Type.ENUM);
						else if( type.getKind() == Kind.ERROR )
							classType.setExtendType(Type.ERROR);
						else if( type.getKind() == Kind.EXCEPTION )
							classType.setExtendType(Type.EXCEPTION);
						
						if( implementsTable.containsKey(type))
						{
							list = implementsTable.get(type);			
							for( String name : list )
							{
								InterfaceType _interface = (InterfaceType)lookupTypeStartingAt(name, classType.getOuter());
								if( _interface == null )							
									missingTypes.add(name);
								else							
									classType.addInterface(_interface);
							}
						}
					}
					else //built-in types
					{
						if( type != Type.OBJECT ) //special case to keep Object from being its own parent
							classType.setExtendType(Type.OBJECT);						
					}
				}
				else if( type instanceof InterfaceType ) 
				{
					InterfaceType interfaceType = (InterfaceType)type;
					if( extendsTable.containsKey(type))
					{
						list = extendsTable.get(type);
						for( String name : list )
						{
							InterfaceType _interface = (InterfaceType)lookupTypeStartingAt(name, interfaceType.getOuter());
							if( _interface == null )						
								missingTypes.add(name);
							else							
								interfaceType.addExtendType(_interface);
						}
					}				
				}
				
				if( missingTypes.size() > 0 )	
					addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot define type " + type + " because it depends on the following undefined types " + missingTypes);			
			}
		}
	}
	
	
	public void collectTypes(File input, Node node) throws ParseException, ShadowException, IOException
	//includes files in the same directory
	{			
		//Walk over file being checked
		ASTWalker walker = new ASTWalker( this );		
		walker.walk(node);
		files.put( input.getCanonicalFile(), node );
		
		//add files in directory BEFORE imports
		File[] directoryFiles = input.getParentFile().listFiles( new FilenameFilter()
				{
					public boolean accept(File dir, String name)
					{
						return name.endsWith(".shadow");
					}
				}
		);		
		List<File> fileList = new ArrayList<File>();
		fileList.addAll(Arrays.asList(directoryFiles));	
		
		//Add standard imports
		String path = Configuration.getInstance().getSystemImport() + File.separator + "shadow" + File.separator + "standard" ;
		fileList.add(new File(path, "Object.shadow" ));
		fileList.add(new File(path, "Class.shadow" ));
		fileList.add(new File(path, "String.shadow" ));
		
		//Add import list
		fileList.addAll(getImportList());
		
		for( File other : fileList )
		{		
			File canonicalFile = other.getCanonicalFile();
			
			if( !files.containsKey(canonicalFile) ) //don't double add
			{
				ShadowParser parser = new ShadowParser(new FileInputStream(canonicalFile));
			    SimpleNode otherNode = parser.CompilationUnit();
			    
			    HashMap<Package, HashMap<String, Type>> otherTypes = new HashMap<Package, HashMap<String, Type>> ();			    
				TypeCollector collector = new TypeCollector(debug, otherTypes, new LinkedList<File>(), new Package(otherTypes));
				walker = new ASTWalker( collector );		
				walker.walk(otherNode);
		
				files.put(canonicalFile, otherNode);				
				
				//copy other types into our package tree				
				for( Package p : otherTypes.keySet() )
				{	
					//if package already exists, it won't be recreated
					Package newPackage = packageTree.addFullyQualifiedPackage(p.getFullyQualifiedName(), typeTable);
					newPackage.addTypes( otherTypes.get(p) );
				}
				
				//copy any errors into our error list
				if( collector.getErrorCount() > 0 )
					errorList.addAll(collector.errorList);
			}
		}	
		
		updateMissingTypes();					
	}
	
	public Map<File, Node> getFiles()
	{
		return files;
	}
	
/*	public void linkTypeTable()
	{
		//this is supposed to find the parents for everything
		List<String> list;
		for( Type type : getTypeTable().values() )
		{	
			if( type instanceof ClassType ) //includes error, exception, and enum (for now)
			{
				if( !type.isBuiltIn() )
				{
					ClassType classType = (ClassType)type;
					if( extendsTable.containsKey(type))
					{
						list = extendsTable.get(type);
						ClassType parent = (ClassType)lookupType(list.get(0), classType.getOuter()); //only one thing in extends lists for classes
						if( parent == null )
							addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot extend undefined class " + list.get(0));
						else
							classType.setExtendType(parent);
					}
					else if( type.getKind() == Kind.CLASS )
						classType.setExtendType(Type.OBJECT);
					else if( type.getKind() == Kind.ENUM )
						classType.setExtendType(Type.ENUM);
					else if( type.getKind() == Kind.ERROR )
						classType.setExtendType(Type.ERROR);
					else if( type.getKind() == Kind.EXCEPTION )
						classType.setExtendType(Type.EXCEPTION);
					
					if( implementsTable.containsKey(type))
					{
						list = implementsTable.get(type);			
						for( String name : list )
						{
							InterfaceType _interface = (InterfaceType)lookupType(name, classType.getOuter());
							if( _interface == null )
								addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot implement undefined interface " + name);
							else							
								classType.addImplementType(_interface);
						}
					}
				}
			}
			else if( type instanceof InterfaceType ) 
			{
				InterfaceType interfaceType = (InterfaceType)type;
				if( extendsTable.containsKey(type))
				{
					list = extendsTable.get(type);
					for( String name : list )
					{
						InterfaceType _interface = (InterfaceType)lookupType(name, interfaceType.getOuter());
						if( _interface == null )
							addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot extend undefined interface " + name);
						else							
							interfaceType.addExtendType(_interface);
					}
				}				
			}
		}	
	}
*/
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
			finalizeType( node );
		else
			createType( node, node.getModifiers(), node.getKind() );
			
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
			finalizeType( node );
		else
			createType( node, node.getModifiers(), Type.Kind.ENUM );

		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTViewDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )
			finalizeType( node );
		else
			createType( node, node.getModifiers(), Type.Kind.VIEW );
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit ) //leaving a type
		{
			currentType = currentType.getOuter();
			if( currentType == null )
				currentName = currentPackage.getFullyQualifiedName();
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
	
	
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{			
			boolean dot = true;
			Node child = node.jjtGetChild(0);
			String name = child.getImage();
			if( child instanceof ASTUnqualifiedName )
			{
				name += "@";
				dot = false;
			}
			
			for( int i = 1; i < node.jjtGetNumChildren(); i++ ) 
			{	
				if( dot )
					name += ".";
				else
					dot = true;
				
				child = node.jjtGetChild(i);
				name += child.getImage();					
			}
			
			node.setImage(name);
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTClassOrInterfaceTypeSuffix node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			if( node.jjtGetNumChildren() > 0 ) 
			{
				Node child = node.jjtGetChild(0); 
				if( child instanceof ASTTypeArguments )
				{
					ASTTypeArguments arguments = (ASTTypeArguments)child;
					node.setRepresentations(arguments.getRepresentations());
				}
			}
		}
		return WalkType.POST_CHILDREN;
	}	
	
	public Object visit(ASTTypeArguments node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			for( int i = 0; i < node.jjtGetNumChildren(); i++ ) 
			{
				ASTTypeArgument child = (ASTTypeArgument)(node.jjtGetChild(i));
				node.addRepresentation(child.getRepresentation());
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTTypeArgument node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			for( int i = 0; i < node.jjtGetNumChildren(); i++ ) 
			{
				ASTTypeArgument child = (ASTTypeArgument)(node.jjtGetChild(i));
				//TODO: fix this
				//node.addRepresentation(child.getRepresentation());
			}
		}
		return WalkType.POST_CHILDREN;
	}

	
	private void createType( SimpleNode node, int modifiers, Kind kind ) throws ShadowException
	{		 
		String typeName;
		
		if( currentType == null )
			typeName = currentName + node.getImage(); //package name is separate
		else
			typeName = currentName + "." + node.getImage();
		
		if( lookupType(typeName) != null )
			addError( node, Error.MULT_SYM, "Type " + typeName + " already defined" );
		else
		{			
			Type type = null;
			
			switch( kind )
			{
			case CLASS:
				type = new ClassType(typeName, modifiers, currentType );
				break;
			case ENUM:
				//enum may need some fine tuning
				type = new EnumType(typeName, modifiers, currentType );
				break;
			case ERROR:
				type = new ErrorType(typeName, modifiers, currentType );
				break;
			case EXCEPTION:
				type = new ExceptionType(typeName, modifiers, currentType );
				break;
			case INTERFACE:
				type = new InterfaceType(typeName, modifiers, currentType );
				break;			
				
			case VIEW:
				//add support for views eventually			
			default:
				throw new ShadowException("Unsupported type!" );
			}
			
			
			
			//Special case for system types
			if( currentPackage.getFullyQualifiedName().equals("shadow.standard"))
			{
				if( typeName.equals("Object") )
					Type.OBJECT = (ClassType) type;
				else if( typeName.equals("Class"))
					Type.CLASS  = (ClassType) type;
				else if( typeName.equals("String"))
					Type.STRING = (ClassType) type;
			}
			
			addType( type, currentPackage );
			node.setType(type);			
		}
	}
	
	private void finalizeType( SimpleNode node )
	{		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ ) {
			Node child = node.jjtGetChild(i); 
			if( child.getClass() == ASTExtendsList.class )
				addExtends( (ASTExtendsList)child, node.getType());
			else if( child.getClass() == ASTImplementsList.class )
				addImplements( (ASTImplementsList)child, node.getType() );
			else if( child.getClass() == ASTTypeParameters.class )
				addTypeParameters( (ASTTypeParameters)child, node.getType() );
		}
	}
	
	private void addTypeParameters( ASTTypeParameters node, Type type )
	{
		List<TypeParameterRepresentation> list = new LinkedList<TypeParameterRepresentation>();
		
		//TODO: Fix this to properly add type parameters
		
		
		//typeParameterTable
		
		/*
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			list.add( node.jjtGetChild(i).getImage() );
		
		extendsTable.put(type, list);
		*/
		nodeTable.put(type, node.jjtGetParent() );
	}
	
	private TypeParameterRepresentation constructTypeParameterRepresentation( ASTTypeParameter parameter )
	{
		//TODO: fix this
		//TypeParameterRepresentation representation = new 
		return null;		
	}
	
	
	private void addExtends( ASTExtendsList node, Type type )
	{
		List<String> list = new LinkedList<String>();
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			list.add( node.jjtGetChild(i).getImage() );
		
		extendsTable.put(type, list);
		nodeTable.put(type, node.jjtGetParent() );
	}
	
	public void addImplements( ASTImplementsList node, Type type )
	{
		List<String> list = new LinkedList<String>();
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			list.add( node.jjtGetChild(i).getImage() );
		
		implementsTable.put(type, list);
		nodeTable.put(type, node.jjtGetParent() );
	}

	
	public Object visit(ASTPackageDeclaration node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			String name = node.jjtGetChild(0).getImage();									
			currentPackage = packageTree.addFullyQualifiedPackage(name, typeTable);			
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			if( node.jjtGetNumChildren() > 0 )
			{
				Node child = node.jjtGetChild(0);
				node.setImage( child.getImage() + "@" + node.getImage() );
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	
	@Override
	public Object visit(ASTImportDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )
		{
			String name = node.jjtGetChild(0).getImage();
			String separator = File.separator; //platform independence, we hope 
			if( separator.equals("\\"))
				separator = "\\\\";
			String path = name.replaceAll("\\.", separator);
			List<File> importPaths = Configuration.getInstance().getImports();
			boolean success = false;				
			
			if( importPaths != null && importPaths.size() > 0 )
			{
				for( File importPath : importPaths )
				{	
					if( !path.contains("@"))  //no @, must be a whole package import
					{		
						File fullPath = new File( importPath, path );
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
							
							for( File file : matchingShadow )
							{
								String prefix = file.getName().substring(0, file.getName().lastIndexOf(".shadow"));
								File metaVersion = new File( file.getParent(), prefix + ".meta"  );
								if( metaVersion.exists() && metaVersion.lastModified() >= file.lastModified() )
									importList.add(metaVersion);
								else
									importList.add(file);
							}
							
							for( File file : matchingMeta )
								if( !importList.contains(file))
									importList.add(file);
							
							success = true;						
						}
					}
					else
					{
						path = path.replaceAll("@", separator);
						File shadowVersion = new File( importPath, path + ".shadow" );
						File metaVersion = new File( importPath, path + ".meta" );
						if( shadowVersion.exists() )
						{
							if( metaVersion.exists() && metaVersion.lastModified() >= shadowVersion.lastModified() )												
								importList.add(metaVersion);
							else
								importList.add(shadowVersion);	
							
							success = true;						
						}
						else if( metaVersion.exists() )
						{
							importList.add(metaVersion);							
							success = true;						
						}
					}
					
					if( success )
						break;
				}			
				
				if( !success )
					addError(node, "No file found for import " + name);
			}
			else
				addError(node, "No import paths specified, cannot import " + name);						
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
		{
			currentPackage = packageTree;
			currentName = "";
		}
		
		return WalkType.POST_CHILDREN;			
	}
}
