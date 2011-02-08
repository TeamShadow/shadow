package shadow.typecheck;

import java.util.List;

import shadow.parser.javacc.Node;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.Type;

public class MethodSignature {
	protected final MethodType type;
	protected final String symbol;
	private final Node node;	/** The AST node that corresponds to the branch of the tree for this method */
	
	public MethodSignature(String symbol, int modifiers, Node node) {
		type = new MethodType(modifiers);
		this.symbol = symbol;
		this.node = node;
	}
	
	public void addParameter(String name, Node node) {
		this.type.addParameter(name, node);
	}
	
	public Node getParameterType(String paramName) 
	{
		return type.getParameterType(paramName);		
	}
	
	public boolean containsParam(String paramName) {
		return type.containsParam(paramName);
	}
	
	public void addReturn(Type ret) {
		type.addReturn(ret);
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getLineNumber() {
		return node.getLine();
	}
	
	public Node getASTNode() {
		return node;
	}
	
	public boolean matches( List<Type> argumentTypes )
	{
		return type.matches(argumentTypes);		
	}
	
	public boolean canAccept( List<Type> argumentTypes )
	{
		return type.canAccept(argumentTypes);		
	}	

	public boolean equals(Object o) {
		if( o != null && o instanceof MethodSignature )
		{
			MethodSignature ms = (MethodSignature)o;
			
			return ms.symbol.equals(symbol) && ms.type.equals(type);
		}
		else
			return false;
	}

	public String toString() {
		return symbol + " " + type.toString();
	}
	
	public String getMangledName() {
		return symbol + "_" + type.getMangledName();
	}
	
	public MethodType getMethodType() {
		return type;
	}
}
