package shadow.typecheck.type;

import java.util.List;

import shadow.parser.javacc.SignatureNode;

public class MethodSignature implements Comparable<MethodSignature> {
	protected final MethodType type;
	protected final String symbol;
	private final SignatureNode node;	/** The AST node that corresponds to the branch of the tree for this method */
	private final MethodSignature wrapped;
	private MethodSignature signatureWithoutTypeArguments;

	private MethodSignature(MethodType type, String symbol, SignatureNode node, MethodSignature wrapped) {
		this.type = type;
		this.symbol = symbol;
		this.node = node;
		this.wrapped = wrapped;
		signatureWithoutTypeArguments = this;
	}

	public MethodSignature(MethodType type, String symbol, SignatureNode node) {
		this(type, symbol, node, null);
	}
	
	public MethodSignature(Type enclosingType, String symbol, Modifiers modifiers, SignatureNode node) {		
		this(new MethodType(enclosingType, modifiers), symbol, node);
	}
	
	public void addParameter(String name, ModifiedType node) {
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
	
	public Modifiers getModifiers() {
		return type.getModifiers();
	}
	
	public String getSymbol() {
		return symbol;
	}

	public SignatureNode getNode() {
		return node;
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
		if( symbol.equals("create") )
			return getModifiers() + symbol + type.parametersToString();
		else if( symbol.equals("destroy") )
			return getModifiers() + symbol;
		
		return getModifiers() + symbol + type.parametersToString() + " => " + type.getReturnTypes();
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	public String getMangledName() {
		return "_M" + symbol + type.getMangledName();
	}

	public List<String> getParameterNames()
	{
		return type.getParameterNames();
	}
	
	public SequenceType getParameterTypes()
	{
		return type.getParameterTypes();
	}
	
	public SequenceType getReturnTypes()
	{
		return type.getReturnTypes();
	}
	
	public Type getOuter()
	{
		return type.getOuter();
	}
	
	public MethodType getMethodType() {
		return type;
	}
	
	public MethodSignature replace(SequenceType values,
			SequenceType replacements) {
		MethodSignature replaced = new MethodSignature(type.replace(values, replacements), symbol, node);
		replaced.signatureWithoutTypeArguments = signatureWithoutTypeArguments;
		return replaced;
	}
	
	public MethodSignature getSignatureWithoutTypeArguments()
	{
		return signatureWithoutTypeArguments; 
	}
	
	public boolean matchesInterface(MethodSignature interfaceSignature) {
			return interfaceSignature.symbol.equals(symbol) && 					
					type.matchesInterface(interfaceSignature.type);
	}

	@Override
	public int compareTo(MethodSignature o) {
		return toString().compareTo(o.toString());
	}

	public boolean isCreate() {		
		return symbol.equals("create");
	}
	
	public boolean isGet()
	{	
		return ( node.getModifiers().isGet() && type.getReturnTypes().size() == 1 && type.getParameterTypes().isEmpty() );	
	}
	
	public boolean isSet()
	{	
		return ( node.getModifiers().isSet() && type.getReturnTypes().isEmpty() && type.getParameterTypes().size() == 1 );	
	}

	public boolean isWrapper() {
		return wrapped != null;
	}
	public MethodSignature getWrapped() {
		return wrapped;
	}
	public MethodSignature wrap(MethodSignature wrapped) {
		return new MethodSignature(type, symbol, node, wrapped);
	}
}
