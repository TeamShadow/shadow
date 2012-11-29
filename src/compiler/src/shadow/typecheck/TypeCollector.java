package shadow.typecheck;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
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
import shadow.parser.javacc.ASTFunctionType;
import shadow.parser.javacc.ASTImplementsList;
import shadow.parser.javacc.ASTImportDeclaration;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPrimaryPrefix;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTResultType;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.ASTTypeArguments;
import shadow.parser.javacc.ASTTypeBound;
import shadow.parser.javacc.ASTTypeParameter;
import shadow.parser.javacc.ASTTypeParameters;
import shadow.parser.javacc.ASTUnqualifiedName;
import shadow.parser.javacc.ASTViewDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.ShadowParser.TypeKind;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.Package.PackageException;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ErrorType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.ViewType;

public class TypeCollector extends BaseChecker
{	
	private Map<Type,Node> nodeTable = new HashMap<Type,Node>(); //for errors and also resolving type parameters
	private Map<Type,List<String>> extendsTable = new HashMap<Type,List<String>>();	
	private Map<Type,List<String>> implementsTable = new HashMap<Type,List<String>>();
	
	private String currentName = "";
	private Map<String, Node> files = new HashMap<String, Node>();
	
	private TypeChecker typeChecker;
	
	public TypeCollector(boolean debug,HashMap< Package, HashMap<String, ClassInterfaceBaseType>> typeTable, ArrayList<String> importList, Package p, TypeChecker typeChecker )
	{		
		super(debug, typeTable, importList, p );
		
		this.typeChecker = typeChecker;
		
		// put built-in types into the TypeTable
		// Object, String, Class, and Array are added separately
		// since there are files that correspond to them
		// Will all type eventually have files?
	
		/*
		try
	{		
		addType(Type.BOOLEAN);
		addType(Type.BYTE);
		addType(Type.CODE);
		addType(Type.SHORT);
		//addType(Type.INT);
		addType(Type.LONG);
		addType(Type.FLOAT);
		addType(Type.DOUBLE);
	
		addType(Type.UBYTE);
		addType(Type.UINT);
		addType(Type.ULONG);
		addType(Type.USHORT);
		addType(Type.NULL);	
		
		addType(Type.ENUM);
		
	}
	catch(PackageException e)
	{
		addError( Error.INVL_TYP, e.getMessage() );				
	}*/
		
//		addType(Type.ERROR);	
//		addType(Type.EXCEPTION);
	}
	
		
	private void updateTypeParameters()
	{	
		//sets the correct types for type parameters in declarations
		for( Package p : getTypeTable().keySet() )
		{
			for( ClassInterfaceBaseType type : getTypeTable().get(p).values() ) //look through all types, updating their extends and implements
			{	
				TreeSet<String> missingTypes = new TreeSet<String>();				
				Node declarationNode = nodeTable.get(type);
				if( declarationNode != null )	
				{
					currentType = type;			
									
					for( int i = 0; i < declarationNode.jjtGetNumChildren(); i++ )
					{
						Node child = declarationNode.jjtGetChild(i);
						
						if( declarationNode.jjtGetChild(i) instanceof ASTTypeParameters )
							processTypeParameters( type, (ASTTypeParameters)(declarationNode.jjtGetChild(i)), missingTypes );
						
						if( child instanceof ASTExtendsList )
						{
							if( currentType instanceof ClassType )
							{
								if( child.jjtGetNumChildren() > 0 )
								{
									updateTypeParameters( (ASTClassOrInterfaceType)(child.jjtGetChild(0)), missingTypes);
									Type childType = child.jjtGetChild(0).getType();
									
									((ClassType) currentType).setExtendType((ClassType) childType);
								}
							}
							else
							{
								InterfaceType interfaceType = (InterfaceType) currentType;
								ArrayList<InterfaceType> extendTypes = interfaceType.getExtendTypes();
								
								for( int j = 0; j < child.jjtGetNumChildren(); j++ )
								{
									updateTypeParameters( (ASTClassOrInterfaceType)(child.jjtGetChild(j)), missingTypes);
									Type childType = child.jjtGetChild(j).getType();
									
									extendTypes.set(j, (InterfaceType) childType );									
								}
							}						
						}
						else if( child instanceof ASTImplementsList )
						{					
							ClassType classType = (ClassType) currentType;
							ArrayList<InterfaceType> implementsTypes = classType.getInterfaces();							
							
							for( int j = 0; j < child.jjtGetNumChildren(); j++ )
							{
								updateTypeParameters( (ASTClassOrInterfaceType)(child.jjtGetChild(j)), missingTypes);
								Type childType = child.jjtGetChild(j).getType();
																
								implementsTypes.set(j, (InterfaceType) childType);									
							}							
						}
					}
				}					
									
				
				if( missingTypes.size() > 0 )	
					addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot define type " + type + " because it depends on the following undefined types " + missingTypes);		
				
			}
		}
	}
	
	
	private void updateMissingTypes()
	{
		List<String> list;
		
		for( Package p : getTypeTable().keySet() )
		{
			for( ClassInterfaceBaseType type : getTypeTable().get(p).values() ) //look through all types, updating their extends and implements
			{	
				TreeSet<String> missingTypes = new TreeSet<String>();
				//graph.addNode(type);
				
				
				Node declarationNode = nodeTable.get(type);
				if( declarationNode != null )				
					currentType = type;
				
				if( type instanceof ClassType ) //includes error, exception, and enum (for now)
				{		
					ClassType classType = (ClassType)type;
					if( type != Type.OBJECT ) //special case to keep Object from being its own parent
					{
						if( extendsTable.containsKey(type))
						{
							list = extendsTable.get(type);
							ClassInterfaceBaseType baseType;
							String typeName = list.get(0);
							if( typeName.contains("@") )
								baseType = lookupType(typeName);
							else
								baseType = lookupTypeStartingAt(typeName, classType.getOuter()); //only one thing in extends lists for classes 
							
							if( baseType == null )							
								missingTypes.add(list.get(0));
							else if( baseType instanceof ClassType )
								classType.setExtendType((ClassType) baseType);
							else
								addError( declarationNode, Error.INVL_TYP, baseType + "is not a class type");
						}										
						else if( type instanceof EnumType )
							classType.setExtendType(Type.ENUM);
						else if( type instanceof ErrorType )
							classType.setExtendType(Type.ERROR);
						else if( type instanceof ExceptionType )
							classType.setExtendType(Type.EXCEPTION);
						else if( type instanceof ArrayType )													
							classType.setExtendType(Type.ARRAY);
						else if( type instanceof ClassType )													
							classType.setExtendType(Type.OBJECT);		
						
						if( implementsTable.containsKey(type))
						{
							list = implementsTable.get(type);			
							for( String name : list )
							{
								ClassInterfaceBaseType baseType = lookupTypeStartingAt(name, classType.getOuter());
								
								if( baseType == null )							
									missingTypes.add(name);
								else if( baseType instanceof InterfaceType )																
									classType.addInterface((InterfaceType)baseType);								
								else
									addError( declarationNode, Error.INVL_TYP, baseType + "is not an interface type");
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
							ClassInterfaceBaseType baseType =lookupTypeStartingAt(name, interfaceType.getOuter());
														
							if( baseType == null )							
								missingTypes.add(name);
							else if( baseType instanceof InterfaceType )	
								interfaceType.addExtendType((InterfaceType)baseType);																
							else
								addError( declarationNode, Error.INVL_TYP, baseType + "is not an interface type");							
						}
					}				
				}
									
				
				if( missingTypes.size() > 0 )	
					addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot define type " + type + " because it depends on the following undefined types " + missingTypes);			
			}
		}
	}
	
	private void updateTypeParameters(ASTType node, TreeSet<String> missingTypes)
	{
		Node child = node.jjtGetChild(0);
		
		if( child instanceof ASTReferenceType )
			updateTypeParameters( (ASTReferenceType)child, missingTypes );
		else if( child instanceof ASTFunctionType )
			updateTypeParameters( (ASTFunctionType)child, missingTypes );
			
		//PrimitiveTypes are ignored
		//StaticArrayTypes... well, they may never exist
			
		node.setType(child.getType());
	}
	
	private void updateTypeParameters(ASTFunctionType node,
			TreeSet<String> missingTypes) {

		MethodType methodType = new MethodType(currentType, new Modifiers());
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
		{
			Node child = node.jjtGetChild(i);
			if( child instanceof ASTType )
			{
				updateTypeParameters( (ASTType) child, missingTypes  );
				methodType.addParameter(child);
			}
			else
			{	
				for( int j = 0; j < child.jjtGetNumChildren(); j++ )
				{
					updateTypeParameters( (ASTResultType) child.jjtGetChild(j), missingTypes  );
					methodType.addReturn(child.jjtGetChild(j));
				}
			}
		}	
		
		node.setType(methodType);		
	}

	private void updateTypeParameters(ASTResultType node, TreeSet<String> missingTypes) {
		
		Node child = node.jjtGetChild(1); //child 0 is always Modifiers
		
		updateTypeParameters( (ASTType)child , missingTypes  );
		node.setType(child.getType());
	}

	private void updateTypeParameters(ASTReferenceType node, TreeSet<String> missingTypes)
	{
		Node child = node.jjtGetChild(0);
		
		if( child instanceof ASTClassOrInterfaceType )
			updateTypeParameters( (ASTClassOrInterfaceType) child, missingTypes );
		
		List<Integer> dimensions = node.getArrayDimensions();
		
		if( dimensions.size() == 0 )
			node.setType(child.getType());
		else
			node.setType(new ArrayType(child.getType(), dimensions));	
	}
	
	
	// updates type parameters inside of extends and implements lists (and bounds lists)
	private void updateTypeParameters(ASTClassOrInterfaceType node, TreeSet<String> missingTypes)
	{
		String typeName = node.getImage();	
		ClassInterfaceBaseType type = lookupType(typeName); //retrieve the type without type parameters
		
		if(type == null)
		{
			addError(node, Error.UNDEF_TYP, typeName);			
			type = Type.UNKNOWN;
			missingTypes.add(typeName);
		}
		else
		{	
			//Container<T, List<String>, String, Thing<K>>.Stuff<U>		
			
			ClassInterfaceBaseType current = type;
			ClassInterfaceBaseType next = null;
			
			// walk backwards up the type, snapping up parameters
			// we go backwards because we need to set outer types
			for( int i = node.jjtGetNumChildren() - 1; i >= 0; i-- )
			{
				Node child = node.jjtGetChild(i);				
				if( child instanceof ASTClassOrInterfaceTypeSuffix  )
				{					
					if( child.jjtGetNumChildren() > 0 ) //has type parameters
					{						
						ASTTypeArguments typeArguments = (ASTTypeArguments) child.jjtGetChild(0);
						SequenceType arguments = new SequenceType();
						for( int j = 0; j < typeArguments.jjtGetNumChildren(); j++ )
						{
							ASTType argument = (ASTType) (typeArguments.jjtGetChild(j));
							//recursively update the type parameters of the type parameters...							
							updateTypeParameters( argument, missingTypes );
							
							arguments.add(argument);
						}						
						typeArguments.setType(arguments);					
						
						//InstantiatedType instantiatedType = new InstantiatedType(current, arguments);
						ClassInterfaceBaseType instantiatedType = current.replace(current.getTypeParameters(), arguments);
						//child.setType(instantiatedType.getInstantiatedType());
						child.setType(instantiatedType);
						if( i == node.jjtGetNumChildren() - 1 )
							type = instantiatedType;								
						
						if( next != null )							
							next.setOuter(instantiatedType); //should only happen if next is an instantiated type too
						
						next = instantiatedType;
						current = instantiatedType.getOuter();
					}
				}				
			}			
		}
		
		//reset the type now that it has type parameters 
		node.setType(type);		
	}


	/**
	 * Adds type parameters to declarations and checks that the same parameter is not added multiple times.
	 * It must be called before field and method checking (since other classes may be dependent on the information).
	 * It must be called after all the types have been collected, otherwise it may depend on unknown types.
	 * @param parentType
	 * @param parameters
	 * @param missingTypes
	 */
	
	private void processTypeParameters(Type parentType, ASTTypeParameters parameters, TreeSet<String> missingTypes)
	{		
		for( int i = 0; i < parameters.jjtGetNumChildren(); i++ )
			processTypeParameter(parentType, (ASTTypeParameter)(parameters.jjtGetChild(i)), missingTypes );
	}

	public void processTypeParameter(Type parentType, ASTTypeParameter node, TreeSet<String> missingTypes)
	{		
		String symbol = node.getImage();
		TypeParameter typeParameter = new TypeParameter(symbol);		
		node.setType(typeParameter);
		
		
		if( parentType.isParameterized() )
			for( ModifiedType existing : parentType.getTypeParameters() )
				if( existing.getType().getTypeName().equals( typeParameter.getTypeName() ) )
					addError( node, Error.MULT_SYM, "Multiply defined type parameter " + existing.getType().getTypeName() );
		
		parentType.addTypeParameter(node);
		
		if( node.jjtGetNumChildren() > 0 )
		{
			ASTTypeBound bound = (ASTTypeBound)(node.jjtGetChild(0));				
			for( int i = 0; i < bound.jjtGetNumChildren(); i++ )
			{
				processTypeBound( bound, missingTypes );
				typeParameter.addBound((ClassInterfaceBaseType)bound.jjtGetChild(i).getType());
			}
		}
	}


	private void processTypeBound(ASTTypeBound bound, TreeSet<String> missingTypes)
	{	
		currentMethod.clear();		
		
		for( int i = 0; i < bound.jjtGetNumChildren(); i++ )
		{
			ASTClassOrInterfaceType child = (ASTClassOrInterfaceType) bound.jjtGetChild(i); //must be ASTClassOrInterfaceType			
			updateTypeParameters(child, missingTypes);
		}		
	}

	

	public void collectTypes(File input, Node node) throws ParseException, ShadowException, IOException
	//includes files in the same directory
	{			
		//Walk over file being checked
		ASTWalker walker = new ASTWalker( this );		
		walker.walk(node);
		String canonicalPath = input.getCanonicalPath(); 
		files.put( stripExtension(canonicalPath), node );
		
		List<String> fileList = new ArrayList<String>();
		
		//Add import list
		for( String file : getImportList() )
			fileList.add(file);
		
		//add files in directory after imports
		File[] directoryFiles = input.getParentFile().listFiles( new FilenameFilter()
				{
					public boolean accept(File dir, String name)
					{
						return name.endsWith(".shadow");
					}
				}
		);		

		for( File file :  directoryFiles )
			fileList.add(stripExtension(file.getCanonicalPath()));	
		
		//Add standard imports
		//change this to pulling everything from the directory?
		/*
		String path = Configuration.getInstance().getSystemImport().getCanonicalPath() + File.separator + "shadow" + File.separator + "standard" + File.separator;
		fileList.add(path + "Object");
		fileList.add(path + "Class");
		fileList.add(path + "String");
		fileList.add(path + "Exception");
		fileList.add(path + "Int");
		*/
		
		//add standard imports		
		File standardDirectory = new File( Configuration.getInstance().getSystemImport(), "shadow" + File.separator + "standard" );
		File[] standardImports = standardDirectory.listFiles( new FilenameFilter()
				{
					public boolean accept(File dir, String name)
					{
						return name.endsWith(".shadow");
					}
				}
		);
		
		for( File file :  standardImports )
			fileList.add(stripExtension(file.getCanonicalPath()));	
		
						
		for(int i = 0; i < fileList.size(); i++ )
		{			
			String canonical = fileList.get(i);
								
			if( !files.containsKey(canonical) ) //don't double add
			{
				
				File canonicalFile = new File(canonical + ".shadow");
				if( !canonicalFile.equals(input) ) //always read the shadow file for the input file
				{
					if( canonicalFile.exists() )  
					{											
						File meta = new File( canonical + ".meta" );
						if( meta.exists() && meta.lastModified() >= canonicalFile.lastModified() ) //check for more recent .meta file
							canonicalFile = meta;
					}
					else
					{
						canonicalFile  = new File(canonical + ".meta");
					}
				}
				
				ShadowParser parser = new ShadowParser(new FileInputStream(canonicalFile));
				typeChecker.setCurrentFile(canonicalFile);
			    SimpleNode otherNode = parser.CompilationUnit();
			    
			    HashMap<Package, HashMap<String, ClassInterfaceBaseType>> otherTypes = new HashMap<Package, HashMap<String, ClassInterfaceBaseType>> ();			    
				TypeCollector collector = new TypeCollector(debug, otherTypes, new ArrayList<String>(), new Package(otherTypes), typeChecker);
				walker = new ASTWalker( collector );		
				walker.walk(otherNode);				
		
				files.put(canonical, otherNode);				
				
				//copy other types into our package tree				
				for( Package p : otherTypes.keySet() )
				{
					//if package already exists, it won't be recreated
					Package newPackage = packageTree.addFullyQualifiedPackage(p.getFullyQualifiedName(), typeTable);
					try
					{	
						newPackage.addTypes( otherTypes.get(p) );
					}
					catch(PackageException e)
					{
						addError( node, Error.INVL_TYP, e.getMessage() );				
					}
				}
				
				//copy any errors into our error list
				if( collector.getErrorCount() > 0 )
					errorList.addAll(collector.errorList);
				
				for( String _import : collector.getImportList() )
				{
					if( !fileList.contains(_import) )
						fileList.add(_import);					
				}
				
				//copy tables from other file into our central table
				Map<Type,Node> otherNodeTable = collector.nodeTable;
				for( Type type : otherNodeTable.keySet() )
					if( !nodeTable.containsKey(type) )
						nodeTable.put(type, otherNodeTable.get(type));
				
				Map<Type,List<String>> otherExtendsTable = collector.extendsTable;
				for( Type type : otherExtendsTable.keySet() )
					if( !extendsTable.containsKey(type) )
						extendsTable.put(type, otherExtendsTable.get(type));
				
				Map<Type,List<String>> otherImplementsTable = collector.implementsTable;
				for( Type type : otherImplementsTable.keySet() )
					if( !implementsTable.containsKey(type) )
						implementsTable.put(type, otherImplementsTable.get(type));
			}
		}	
		
				
		updateMissingTypes(); //add all types to graph for type parameter dependence resolution
		
		/*
		 * type parameters are updated separately because they require knowledge of
		 * the type hierarchy constructed in updateMissingTypes() 
		 */			
		updateTypeParameters();	
	}


	public Map<String, Node> getFiles()
	{
		return files;
	}
	
	private void createType( SimpleNode node, Modifiers modifiers, TypeKind kind ) throws ShadowException
	{		 
		String typeName;
		
		if( node.jjtGetNumChildren() > 0 && (node.jjtGetChild(0) instanceof ASTUnqualifiedName) )
		{
			
			if( currentType == null )
			{
				String name = node.jjtGetChild(0).getImage();									
				currentPackage = packageTree.addFullyQualifiedPackage(name, typeTable);
			}
			else
				addError( node, Error.INVL_TYP, "Only outermost classes can define a package" );			
		}
		
		String image = node.getImage();		
		if( currentPackage.getFullyQualifiedName().equals("shadow.standard"))
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
			addError( node, Error.MULT_SYM, "Type " + typeName + " already defined" );
			node.setType(Type.UNKNOWN);
		}
		else
		{			
			ClassInterfaceBaseType type = null;	
			
			switch( kind )
			{			
			case CLASS:
				type = new ClassType(typeName, modifiers, currentType );
				break;
			case ENUM:				
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
			case SINGLETON:
				type = new SingletonType(typeName, modifiers, currentType );
				break;
			case VIEW:
				type = new ViewType(typeName, modifiers );
				break;
			default:
				throw new ShadowException("Unsupported type!" );
			}			
			
			//Special case for system types			
			if( currentPackage.getFullyQualifiedName().equals("shadow.standard"))
			{	
				if( typeName.equals("Object") )
					Type.OBJECT = (ClassType) type;
				else if( typeName.equals("Class") )
					Type.CLASS = (ClassType) type;
				else if( typeName.equals("Array"))
					Type.ARRAY = (ClassType) type;
				else if( typeName.equals("Enum"))
					Type.ENUM = (ClassType) type; //the base class for enum is not an enum
				else if( typeName.equals("Exception"))
					Type.EXCEPTION = (ExceptionType) type;
				else if( typeName.equals("Error"))
					Type.ERROR = (ErrorType) type;
				else if( typeName.equals("boolean"))
					Type.BOOLEAN = (ClassType)type;
				else if( typeName.equals("byte"))
					Type.BYTE = (ClassType)type;
				else if( typeName.equals("code"))
					Type.CODE = (ClassType)type;
				else if( typeName.equals("double"))
					Type.DOUBLE = (ClassType)type;
				else if( typeName.equals("float"))
					Type.FLOAT = (ClassType)type;
				else if( typeName.equals("int") )
					Type.INT = (ClassType) type;
				else if( typeName.equals("long"))				
					Type.LONG = (ClassType)type;
				else if( typeName.equals("short"))
					Type.SHORT = (ClassType)type;
				else if( typeName.equals("ubyte"))
					Type.UBYTE = (ClassType)type;
				else if( typeName.equals("uint"))
					Type.UINT = (ClassType)type;
				else if( typeName.equals("ulong"))
					Type.ULONG = (ClassType)type;
				else if( typeName.equals("ushort"))
					Type.USHORT = (ClassType)type;
				else if( typeName.equals("String") )
					Type.STRING = (ClassType) type;
				else if( typeName.equals("CanCompare"))
					Type.CAN_COMPARE = (InterfaceType) type;
				else if( typeName.equals("CanIndex"))
					Type.CAN_INDEX = (InterfaceType) type;
				else if( typeName.equals("CanIterate"))
					Type.CAN_ITERATE = (InterfaceType) type;
				else if( typeName.equals("Number"))
					Type.NUMBER = (InterfaceType) type;
			}			
			
			try
			{			
				addType( type, currentPackage );
			}
			catch(PackageException e)
			{
				addError( node, Error.INVL_TYP, e.getMessage() );				
			}
			
			node.setType(type);			
		}
	}
	
	private void finalizeType( SimpleNode node )
	{		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ ) {
			Node child = node.jjtGetChild(i); 
			if( child instanceof ASTExtendsList )
				addExtends( (ASTExtendsList)child, node.getType());
			else if( child instanceof ASTImplementsList )
				addImplements( (ASTImplementsList)child, node.getType() );	
		}
		
		nodeTable.put(node.getType(), node );
	}
	
	private void addExtends( ASTExtendsList node, Type type )
	{
		List<String> list = new LinkedList<String>();
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			list.add( node.jjtGetChild(i).getImage() );
		
		extendsTable.put(type, list);		
	}
	
	public void addImplements( ASTImplementsList node, Type type )
	{
		List<String> list = new LinkedList<String>();
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			list.add( node.jjtGetChild(i).getImage() );
		
		implementsTable.put(type, list);		
	}
	
	public boolean addImport( String name )
	{
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
						shadowVersion = new File( typeChecker.getCurrentFile().getParent(),  path + ".shadow");
						metaVersion = new File( typeChecker.getCurrentFile().getParent(),  path + ".meta");
					}
					else
					{
						path = path.replaceAll("@", separator);
						shadowVersion = new File( importPath, path + ".shadow" );
						metaVersion = new File( importPath, path + ".meta" );
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
			addError(Error.UNDEF_TYP, "No import paths specified, cannot import " + name);
		
		return false;
	}

	//Visitors below this point
	
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
			createType( node, node.getModifiers(), TypeKind.ENUM );

		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTViewDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )
			finalizeType( node );
		else
			createType( node, node.getModifiers(), TypeKind.VIEW );
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException
	{		
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
			currentType = (ClassInterfaceBaseType)node.jjtGetParent().getType();
			currentName = currentType.getTypeName();				
		}
			
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{			
			if ( node.jjtGetNumChildren() > 0)
			{
				boolean colon = true;
				Node child = node.jjtGetChild(0);
				String name = child.getImage();
				if( child instanceof ASTUnqualifiedName )
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
					
					child = node.jjtGetChild(i);
					name += child.getImage();					
				}
				
				node.setImage(name);
				
				if( child instanceof ASTUnqualifiedName )
					if( !addImport( name ) )
						addError(node, "No file found for import " + name);					
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			//triggers an import since there's an ASTUnqualifiedName@
			if( node.jjtGetNumChildren() > 0 ) 
			{
				Node child = node.jjtGetChild(0);				
				
				if( child instanceof ASTUnqualifiedName )
				{
					String name = child.getImage() + "@" +  node.getImage();
					if( !addImport( name ) )
						addError(node, "No file found for import " + name);
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
			if( !addImport( name ) )
				addError(node, "No file found for import " + name);		
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
