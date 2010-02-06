package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTExtendsList;
import shadow.parser.javacc.ASTImplementsList;
import shadow.parser.javacc.ASTViewDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ErrorType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.Type.Kind;

public class TypeCollector extends BaseChecker
{	
	protected Map<Type,List<String>> extendsTable = new HashMap<Type,List<String>>();
	protected Map<Type,Node> nodeTable = new HashMap<Type,Node>(); //for errors only
	protected Map<Type,List<String>> implementsTable = new HashMap<Type,List<String>>();	
	protected String currentName = "";
	
	
	public TypeCollector(boolean debug)
	{		
		super(debug, new HashMap<String, Type>(), new LinkedList<String>() );
		// put all of our built-in types into the TypeTable
		addType(Type.OBJECT.getTypeName(),	Type.OBJECT);
		addType(Type.BOOLEAN.getTypeName(),	Type.BOOLEAN);
		addType(Type.BYTE.getTypeName(),		Type.BYTE);
		addType(Type.CODE.getTypeName(),		Type.CODE);
		addType(Type.SHORT.getTypeName(),		Type.SHORT);
		addType(Type.INT.getTypeName(),		Type.INT);
		addType(Type.LONG.getTypeName(),		Type.LONG);
		addType(Type.FLOAT.getTypeName(),		Type.FLOAT);
		addType(Type.DOUBLE.getTypeName(),	Type.DOUBLE);
		addType(Type.STRING.getTypeName(),	Type.STRING);
		addType(Type.UBYTE.getTypeName(),		Type.UBYTE);
		addType(Type.UINT.getTypeName(),		Type.UINT);
		addType(Type.ULONG.getTypeName(),		Type.ULONG);
		addType(Type.USHORT.getTypeName(),	Type.USHORT);
		addType(Type.NULL.getTypeName(),		Type.NULL);	
	}
	
	public void linkTypeTable()
	{
		//this is supposed to find the parents for everything
		List<String> list;
		for( Type type : getTypeTable().values() )
		{	
			if( type instanceof ClassType ) //includes error, exception, and enum (for now)
			{
				ClassType classType = (ClassType)type;
				if( extendsTable.containsKey(type))
				{
					list = extendsTable.get(type);
					ClassType parent = (ClassType)lookupType(list.get(0), classType.getOuter());
					if( parent == null )
						addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot extend undefined class " + list.get(0));
					else
						classType.setExtendType(parent);
				}
				else
					classType.setExtendType(Type.OBJECT);
				
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

	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )		
			exitType( node );		
		else
			enterType( node, node.getModifiers(), node.getKind() );
			
		return WalkType.POST_CHILDREN;
	}	
	

	
	private void enterType( SimpleNode node, int modifiers, Kind kind ) throws ShadowException
	{		 
		if( !currentName.isEmpty() )
			currentName += ".";
		
		currentName += node.getImage();	
		if( lookupType(currentName) != null )
			addError( node, Error.MULT_SYM, "Type " + currentName + " already defined" );
		else
		{			
			Type type = null;
			
			switch( kind )
			{
			case CLASS:
				type = new ClassType(currentName, modifiers, currentType );
				break;
			case ENUM:
				//enum may need some fine tuning
				type = new EnumType(currentName, modifiers, currentType );
				break;
			case ERROR:
				type = new ErrorType(currentName, modifiers, currentType );
				break;
			case EXCEPTION:
				type = new ExceptionType(currentName, modifiers, currentType );
				break;
			case INTERFACE:
				type = new InterfaceType(currentName, modifiers, currentType );
				break;			
			case VIEW:
				//add support for views eventually
				break;
			default:
				throw new ShadowException("Unsupported type!" );
			}
			
			addType( currentName, type  );
			
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				if( node.jjtGetChild(i).getClass() == ASTExtendsList.class )
					addExtends( (ASTExtendsList)node.jjtGetChild(i), type );
				else if( node.jjtGetChild(i).getClass() == ASTImplementsList.class )
					addImplements( (ASTImplementsList)node.jjtGetChild(i), type );
			
			currentType = type;
		}
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
	
	private void exitType( SimpleNode node )
	{
		//	remove innermost class
		int index = currentName.lastIndexOf('.'); 
		if( index == -1 )
			currentName = "";
		else
			currentName = currentName.substring(0, index);
		
		currentType = currentType.getOuter();
	}
	

	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )		
			exitType( node );		
		else
			enterType( node, node.getModifiers(), Type.Kind.ENUM );
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTViewDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )		

			exitType( node );		
		else
			enterType( node, node.getModifiers(), Type.Kind.VIEW );
		
		return WalkType.POST_CHILDREN;
	}

}
