package shadow.typecheck.type;

import java.util.List;

import shadow.parser.javacc.Node;

public class MethodSignature {
	protected final MethodType type;
	protected final String symbol;
	private final Node node;	/** The AST node that corresponds to the branch of the tree for this method */

	public MethodSignature(MethodType type, String symbol, Node node) {
		this.type = type;
		this.symbol = symbol;
		this.node = node;
	}
	
	public MethodSignature(ClassInterfaceBaseType enclosingType, String symbol, int modifiers, Node node) {
		type = new MethodType(enclosingType, modifiers);
		this.symbol = symbol;
		this.node = node;
	}
	
	public void addParameter(String name, Node node) {
		this.type.addParameter(name, node);
	}
	
	public ModifiedType getParameterType(String paramName) 
	{
		return type.getParameterType(paramName);		
	}
	
	public boolean containsParam(String paramName) {
		return type.containsParam(paramName);
	}
	
	public void addReturn(ModifiedType ret) {
		type.addReturn(ret);
	}
	
	public int getModifiers() {
		return type.getModifiers();
	}
	
	public String getSymbol() {
		return symbol;
	}

	public Node getNode() {
		return node;
	}
		
	public boolean matches( SequenceType argumentTypes, Type container )
	{
		return type.matches(argumentTypes, container );		
	}
	
	public boolean canAccept( SequenceType argumentTypes, Type container )
	{
		return type.canAccept(argumentTypes, container );		
	}	
	
	public boolean matches( SequenceType argumentTypes )
	{
		return type.matches(argumentTypes);		
	}
	
	public boolean canAccept( SequenceType argumentTypes )
	{
		return type.canAccept(argumentTypes);		
	}

	public boolean equals(Object o)
	{
		if( o != null && o instanceof MethodSignature )
		{
			MethodSignature ms = (MethodSignature)o;			
			return ms.symbol.equals(symbol) && ms.type.equals(type);
		}
		else
			return false;
	}
	
	public boolean isIndistinguishable(MethodSignature signature )
	//isIndistinguishable() differs from equals() in that differences in return types are ignored
	{
		if( signature != null && signature.symbol.equals(symbol) )			
			return type.matchesParams(signature.type );		
		else
			return false;
	}
	

	public String toString() {
		return symbol + " " + type.toString();
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	
	public String getMangledName() {
		return "_M" + symbol + type.getMangledName();
	}
	
	public MethodType getMethodType() {
		return type;
	}
	
	public MethodSignature replace(List<TypeParameter> parameters,
			List<ModifiedType> replacements) {
		return new MethodSignature(type.replace(parameters, replacements), symbol, node);
	}
	
	public boolean matchesInterface(MethodSignature interfaceSignature) {
			return interfaceSignature.symbol.equals(symbol) && 					
					interfaceSignature.type.matchesInterface(type);
	}
}
