package shadow.typecheck;

import java.util.HashMap;

import shadow.AST.AbstractASTVisitor;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTViewDeclaration;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.Type.Kind;

public class TypeCollector extends AbstractASTVisitor
{
	protected HashMap<String,Type> typeTable  = new HashMap<String, Type>();
	protected HashMap<Type,String> parentTable = new HashMap<Type,String>();
	protected Type currentClass = null;
	protected String currentName = "";
	
	
	public TypeCollector()
	{			
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
		//thought it was going to be easy, but interfaces can have multiple parents, etc.
		return typeTable;		
	}

	public Object visit(ASTClassOrInterfaceDeclaration node, Object secondVisit) throws ShadowException {
		
		if( (Boolean)secondVisit )		
			exitType( node );		
		else
			enterType( node, node.getModifiers(), node.getKind() );
			
		return WalkType.POST_CHILDREN;
	}	
	

	
	public void enterType( SimpleNode node, int modifiers, Kind kind )
	{
		
		if( !currentName.isEmpty() )
			currentName += ".";
		
		currentName += node.getImage();	
		if( typeTable.containsKey(currentName) )
		{
			//change this to standard typechecker errors as soon as we figure out which class to put those in
			System.err.println("Whoa, shit!  You've already added " + currentName + " to the type table!" );
			System.exit(-666);
		}
		else
		{
			
			Type type = new Type(currentName, modifiers, currentClass, kind );
			typeTable.put( currentName, type  );
			currentClass = type;
		}
	}
	
	public void exitType( SimpleNode node )
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
