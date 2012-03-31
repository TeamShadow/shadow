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
import shadow.parser.javacc.ASTPrimaryPrefix;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTTypeArgument;
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
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.BaseChecker.Error;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ErrorType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InstantiatedType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.Type.Kind;
import shadow.typecheck.type.TypeParameter;

public class TypeCollector extends BaseChecker
{	
	private Map<Type,Node> nodeTable = new HashMap<Type,Node>(); //for errors and also resolving type parameters
	private Map<Type,List<String>> extendsTable = new HashMap<Type,List<String>>();	
	private Map<Type,List<String>> implementsTable = new HashMap<Type,List<String>>();
	
	private String currentName = "";
	private Map<File, Node> files = new HashMap<File, Node>();
	
	public TypeCollector(boolean debug,HashMap< Package, HashMap<String, ClassInterfaceBaseType>> typeTable, LinkedList<File> importList, Package p )
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
	
	private void updateTypeParameters()
	{	
		//add type parameters to declarations
		for( Package p : getTypeTable().keySet() )
		{
			for( ClassInterfaceBaseType type : getTypeTable().get(p).values() ) //look through all types, updating their extends and implements
			{	
				TreeSet<String> missingTypes = new TreeSet<String>();				
				Node declarationNode = nodeTable.get(type);
				if( declarationNode != null )	
				{
					currentType = type;			
				
					//need special cases for Class or Array?
					for( int i = 0; i < declarationNode.jjtGetNumChildren(); i++ )
					{
						Node child = declarationNode.jjtGetChild(i);
						
						if( (child instanceof ASTExtendsList) || (child instanceof ASTImplementsList) )
						{
							for( int j = 0; j < child.jjtGetNumChildren(); j++ )
								updateTypeParameters( (ASTClassOrInterfaceType)(child.jjtGetChild(j)), type.getParameters(), missingTypes  );
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
				
				//add type parameters to each type's declaration (if present)
				Node declarationNode = nodeTable.get(type);
				if( declarationNode != null )
				{
					currentType = type;
					for( int i = 0; i < declarationNode.jjtGetNumChildren(); i++ )
						if( declarationNode.jjtGetChild(i) instanceof ASTTypeParameters )
							processTypeParameters( type, (ASTTypeParameters)(declarationNode.jjtGetChild(i)), missingTypes );
				}
				
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
						else if( type.getKind() == Kind.CLASS || type.getKind() == Kind.ARRAY )													
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
	
	private void updateTypeParameters(ASTClassOrInterfaceType node, List<TypeParameter> parameterList, TreeSet<String> missingTypes)
	{
		//[ LOOKAHEAD(UnqualifiedName() "@") UnqualifiedName() "@"  ] ClassOrInterfaceTypeSuffix() (  LOOKAHEAD(2) "." ClassOrInterfaceTypeSuffix() )*
		String typeName = node.getImage();	
		ClassInterfaceBaseType type = lookupType(typeName);
		
		if(type == null)
		{
			addError(node, Error.UNDEF_TYP, typeName);
			type = Type.UNKNOWN;
		}
		else
		{
			//if (currentType instanceof ClassType)
			//	((ClassType)currentType).addReferencedType(type);
			
			//Container<T, List<String>, String, Thing<K>>.Stuff<U>		
			
			ClassInterfaceBaseType current = type;
			ClassInterfaceBaseType next = null;
			
			//walk backwards up the type, snapping up parameters
			//we go backwards because we need to set outer types
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
							ASTReferenceType argument = (ASTReferenceType) (typeArguments.jjtGetChild(j).jjtGetChild(0));
							Node typeNode = argument.jjtGetChild(0); 
							if(  typeNode instanceof ASTClassOrInterfaceType )
								updateTypeParameters( (ASTClassOrInterfaceType) typeNode, parameterList, missingTypes );
							
							argument.setType(typeNode.getType());
							arguments.add(argument);
						}
						
						typeArguments.setType(arguments);
						
						List<TypeParameter> parameters = current.getParameters();
						if( checkTypeArguments( parameters, arguments ) )
						{
							InstantiatedType instantiatedType = new InstantiatedType(current, arguments);
							child.setType(instantiatedType);
							if( i == node.jjtGetNumChildren() - 1 )
								type = instantiatedType;								
							
							if( next != null )							
								next.setOuter(instantiatedType); //should only happen if next is an instantiated type too
							
							next = instantiatedType;
							current = instantiatedType.getBaseType().getOuter();
						}
						else
						{
							addError( child, Error.TYPE_MIS, "Type arguments " + arguments + " do not match type parameters " + parameters );
							break;
						}
						
					}
				}				
			}			
		}
				
		node.setType(type);		
	}


	//This disgusting code handles type parameters
	//It must be called before field and method checking (since other classes may be dependent on the information)
	//It must be called after all the types have been collected, otherwise it may depend on unknown types
	private void processTypeParameters(Type parentType, ASTTypeParameters parameters, TreeSet<String> missingTypes)
	{	
		for( int i = 0; i < parameters.jjtGetNumChildren(); i++ )
			processTypeParameter( (ASTTypeParameter)(parameters.jjtGetChild(i)), missingTypes );
		
		for( int i = 0; i < parameters.jjtGetNumChildren(); i++ )
		{
			TypeParameter parameter = (TypeParameter)(parameters.jjtGetChild(i).getType());
			for( TypeParameter existing : parentType.getParameters() )
				if( existing.getTypeName().equals( parameter.getTypeName() ) )
					addError( parameters, Error.MULT_SYM, "Multiply defined type parameter " + existing.getTypeName() );
			
			parentType.addParameter(parameter);
		}
		
		parentType.setParameterized(true);
	}

	public void processTypeParameter(ASTTypeParameter node, TreeSet<String> missingTypes)
	{		
		String symbol = node.getImage();
		TypeParameter typeParameter = new TypeParameter(symbol);		
		node.setType(typeParameter);
		
		if( node.jjtGetNumChildren() > 0 )
		{
			ASTTypeBound bound = (ASTTypeBound)(node.jjtGetChild(0));				
			for( int i = 0; i < bound.jjtGetNumChildren(); i++ )
			{
				processTypeBound( bound, missingTypes );
				typeParameter.addBound(bound.jjtGetChild(i).getType());
			}
		}
	}


	private void processTypeBound(ASTTypeBound bound, TreeSet<String> missingTypes)
	{	
		currentMethod = null;		
		
		for( int i = 0; i < bound.jjtGetNumChildren(); i++ )
		{
			Node child = bound.jjtGetChild(i); //must be ASTClassOrInterfaceType
			Type type = lookupType( child.getImage() );
			
			if( type == null )
				missingTypes.add(child.getImage());
			else
				child.setType(type);
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
				
		for( int i = 0; i < fileList.size(); i++ )
		{		
			File other = fileList.get(i);
			File canonicalFile = other.getCanonicalFile();
			
			if( !files.containsKey(canonicalFile) ) //don't double add
			{
				ShadowParser parser = new ShadowParser(new FileInputStream(canonicalFile));
			    SimpleNode otherNode = parser.CompilationUnit();
			    
			    HashMap<Package, HashMap<String, ClassInterfaceBaseType>> otherTypes = new HashMap<Package, HashMap<String, ClassInterfaceBaseType>> ();			    
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
				
				for( File file : collector.getImportList() )
				{
					if( !fileList.contains(file) )
						fileList.add(file);					
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
		
		updateMissingTypes();
		
		/*
		 * type parameters are updated separately because they require knowledge of
		 * the type hierarchy constructed in updateMissingTypes() 
		 */		
		updateTypeParameters();
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
			currentType = (ClassInterfaceBaseType)node.jjtGetParent().getType();
			currentName = currentType.getTypeName();				
		}
			
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{
		//TODO: Make this work
		/* Can be in:
		 * ExtendsList (do import if @)
		 * ImplementsList (do import if @)
		 * TypeBound (do import if @)
		 * TypeArgument (do import if @)
		 * ReferenceType (do import if @)
		 * ArrayAllocation (do import if @)
		 * ConstructorInvocation (do import if @) ALL CASES
		 */
		
		if( secondVisit )
		{			
			if ( node.jjtGetNumChildren() > 0)
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
				
				if( child instanceof ASTUnqualifiedName )
					if( !addImport( name ) )
						addError(node, "No file found for import " + name);					
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTClassOrInterfaceTypeSuffix node, Boolean secondVisit) throws ShadowException
	{	/*	
		if( secondVisit )
		{
			if( node.jjtGetNumChildren() > 0 ) 
			{
				Node child = node.jjtGetChild(0); 
				if( child instanceof ASTTypeArguments )
					node.setImage(node.getImage() + child.getImage());
			}
		}*/
		return WalkType.POST_CHILDREN;
	}	
	
	
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			if( node.jjtGetNumChildren() > 0 ) 
			{
				Node child = node.jjtGetChild(0);				
				//triggers an import
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
	
	
	
	public Object visit(ASTTypeArgument node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )		
			node.setImage(node.jjtGetChild(0).getImage());
	
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
			ClassInterfaceBaseType type = null;
				
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
				else if( typeName.equals("Array"))
					Type.ARRAY = (ClassType) type;
			}
			
			addType( type, currentPackage );
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
	
	
	/*
	private void addTypeParameters( ASTTypeParameters node, Type type )
	{
		List<TypeParameterRepresentation> list = new LinkedList<TypeParameterRepresentation>();
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
		{
			TypeParameterRepresentation representation = constructTypeParameterRepresentation( (ASTTypeParameter)(node.jjtGetChild(i)) );
			list.add( representation );
			node.addRepresentation(representation);
		}
		
		typeParameterTable.put(type, list);
		
	}
	*/
	
	public Object visit(ASTTypeParameter node, Boolean secondVisit) throws ShadowException
	{
		//t = <IDENTIFIER>  { jjtThis.setImage(t.image); } [ TypeBound() ]
		if( secondVisit )
		{			
			/*
			TypeParameterRepresentation representation = new TypeParameterRepresentation( node.getImage() );
			if( node.jjtGetNumChildren() > 0 )
			{
				ASTTypeBound bound = (ASTTypeBound)(node.jjtGetChild(0));
				representation.addBounds( bound.getRepresentations() );
			}
			
			node.setRepresentation(representation);
			*/
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTTypeBound node, Boolean secondVisit) throws ShadowException
	{
		//"is" ClassOrInterfaceType() ( "and" ClassOrInterfaceType() )*
		if( secondVisit )
		{
			/*
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			{
				ASTClassOrInterfaceType child = (ASTClassOrInterfaceType)(node.jjtGetChild(i)); 
				TypeParameterRepresentation representation = new TypeParameterRepresentation( child.getImage() );
								
				node.addRepresentation(representation);
			}
			*/
			//TODO: fix this!
			//It can all be simplified by making a set of the classes needed
			//Perhaps this should all be pushed back to the next phase of the type checker
			
			
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	/*
	
	private TypeParameterRepresentation constructTypeParameterRepresentation( ASTTypeParameter parameter )
	{
		
		// t = <IDENTIFIER>  { jjtThis.setImage(t.image); } [ TypeBound() ]

		TypeParameterRepresentation representation = new TypeParameterRepresentation( parameter.getImage() );
		if( parameter.jjtGetNumChildren() > 0 )
			addBounds( representation, (ASTTypeBound)(parameter.jjtGetChild(0)) );
		
		return null;		
	}
	
	private void addBounds( TypeParameterRepresentation representation, ASTTypeBound bound )
	{
		//"is" ClassOrInterfaceType() ( "and" ClassOrInterfaceType() )*
		
		//for( int i = 0; i < bound.jjtGetNumChildren(); i++ )
		//probably won't need this
	}
	*/
	
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
//						if( metaVersion.exists() && metaVersion.lastModified() >= shadowVersion.lastModified() )												
//							importList.add(metaVersion);
//						else
							importList.add(shadowVersion);	
						
						success = true;						
					}
//					else if( metaVersion.exists() )
//					{
//						importList.add(metaVersion);							
//						success = true;						
//					}
				}
				
				if( success )
					return true;
			}	
			
		}
		else
			addError(Error.UNDEF_TYP, "No import paths specified, cannot import " + name);
		
		return false;
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
