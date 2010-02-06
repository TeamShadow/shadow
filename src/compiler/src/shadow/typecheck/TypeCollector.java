package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import shadow.AST.AbstractASTVisitor;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTExtendsList;
import shadow.parser.javacc.ASTImplementsList;
import shadow.parser.javacc.ASTViewDeclaration;
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
	protected HashMap<String,Type> typeTable  = new HashMap<String, Type>();
	protected HashMap<Type,List<String>> extendsTable = new HashMap<Type,List<String>>();
	protected HashMap<Type,List<String>> implementsTable = new HashMap<Type,List<String>>();
	protected Type currentClass = null;
	protected String currentName = "";
	
	
	public TypeCollector(boolean debug)
	{		
		super(debug);
		// put all of our built-in types into the TypeTable
		typeTable.put(Type.OBJECT.getTypeName(),	Type.OBJECT);
		typeTable.put(Type.BOOLEAN.getTypeName(),	Type.BOOLEAN);
		typeTable.put(Type.BYTE.getTypeName(),		Type.BYTE);
		typeTable.put(Type.CODE.getTypeName(),		Type.CODE);
		typeTable.put(Type.SHORT.getTypeName(),		Type.SHORT);
		typeTable.put(Type.INT.getTypeName(),		Type.INT);
		typeTable.put(Type.LONG.getTypeName(),		Type.LONG);
		typeTable.put(Type.FLOAT.getTypeName(),		Type.FLOAT);
		typeTable.put(Type.DOUBLE.getTypeName(),	Type.DOUBLE);
		typeTable.put(Type.STRING.getTypeName(),	Type.STRING);
		typeTable.put(Type.UBYTE.getTypeName(),		Type.UBYTE);
		typeTable.put(Type.UINT.getTypeName(),		Type.UINT);
		typeTable.put(Type.ULONG.getTypeName(),		Type.ULONG);
		typeTable.put(Type.USHORT.getTypeName(),	Type.USHORT);
		typeTable.put(Type.NULL.getTypeName(),		Type.NULL);	
	}
	
	public HashMap<String,Type> produceTypeTable()
	{
		//this is supposed to find the parents for everything
		List<String> list;
		for( Type type : typeTable.values() )
		{			
			if( type instanceof ClassType ) //includes error, exception, and enum (for now)
			{
				ClassType classType = (ClassType)type;
				if( extendsTable.containsKey(type))
				{
					list = extendsTable.get(type);
					//more sophisticated lookup taking naming into account may be required
					classType.setExtendType((ClassType)typeTable.get(list.get(0)));
				}
				
				if( implementsTable.containsKey(type))
				{
					list = implementsTable.get(type);
					//more sophisticated lookup taking naming into account may be required
					for( String name : list )
						classType.addImplementType((InterfaceType)typeTable.get(name));
				}				
			}
			else if( type instanceof InterfaceType ) 
			{
				InterfaceType interfaceType = (InterfaceType)type;
				if( extendsTable.containsKey(type))
				{
					list = extendsTable.get(type);
					//more sophisticated lookup taking naming into account may be required
					for( String name : list )
						interfaceType.addExtendType((InterfaceType)typeTable.get(name));
				}				
			}
		}		
		
		return typeTable;		
	}

	public Object visit(ASTClassOrInterfaceDeclaration node, Object secondVisit) throws ShadowException {
		
		if( (Boolean)secondVisit )		
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
		if( typeTable.containsKey(currentName) )		
			//change this to standard typechecker errors as soon as we figure out which class to put those in
			throw new ShadowException("Whoa, shit!  You've already added " + currentName + " to the type table!" );
		else
		{
			
			Type type = null;
			
			switch( kind )
			{
			case CLASS:
				type = new ClassType(currentName, modifiers, currentClass );
				break;
			case ENUM:
				//enum may need some fine tuning
				type = new EnumType(currentName, modifiers, currentClass );
				break;
			case ERROR:
				type = new ErrorType(currentName, modifiers, currentClass );
				break;
			case EXCEPTION:
				type = new ExceptionType(currentName, modifiers, currentClass );
				break;
			case INTERFACE:
				type = new InterfaceType(currentName, modifiers, currentClass );
				break;			
			case VIEW:
				//add support for views eventually
				break;
			default:
				throw new ShadowException("Unsupported type!" );
			}
			
			typeTable.put( currentName, type  );
			
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				if( node.jjtGetChild(i).getClass() == ASTExtendsList.class )
					addExtends( (ASTExtendsList)node.jjtGetChild(i), type );
				else if( node.jjtGetChild(i).getClass() == ASTImplementsList.class )
					addImplements( (ASTImplementsList)node.jjtGetChild(i), type );
			
			currentClass = type;
		}
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
	
	private void exitType( SimpleNode node )
	{
		//	remove innermost class
		int index = currentName.lastIndexOf('.'); 
		if( index == -1 )
			currentName = "";
		else
			currentName = currentName.substring(0, index);
		
		currentClass = currentClass.getOuter();
	}
	
	public Object visit(ASTEnumDeclaration node, Object secondVisit) throws ShadowException {
		
		if( (Boolean)secondVisit )		
			exitType( node );		
		else
			enterType( node, node.getModifiers(), Type.Kind.ENUM );
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTViewDeclaration node, Object secondVisit) throws ShadowException {
		if( (Boolean)secondVisit )		
			exitType( node );		
		else
			enterType( node, node.getModifiers(), Type.Kind.VIEW );
		
		return WalkType.POST_CHILDREN;
	}

}
