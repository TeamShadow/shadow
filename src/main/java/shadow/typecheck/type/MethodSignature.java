package shadow.typecheck.type;

import java.util.Collections;
import java.util.List;

import shadow.doctool.Documentation;
import shadow.parser.javacc.SignatureNode;

public class MethodSignature implements Comparable<MethodSignature> {
	protected final MethodType type;
	protected final String symbol;
	private final SignatureNode node;	/** The AST node that corresponds to the branch of the tree for this method */
	private final MethodSignature wrapped;
	private MethodSignature signatureWithoutTypeArguments;
	private Type outer;

	private MethodSignature(MethodType type, String symbol, Type outer, SignatureNode node, MethodSignature wrapped) 
	{
		this.type = type;
		this.symbol = symbol;
		this.node = node;
		this.wrapped = wrapped;
		this.outer = outer;
		signatureWithoutTypeArguments = this;
	}
	public MethodSignature(MethodType type, String symbol, Type outer, SignatureNode node) 
	{
		this(type, symbol, outer, node, null);
	}
	
	public MethodSignature(Type enclosingType, String symbol, 
			Modifiers modifiers, Documentation documentation, SignatureNode node) 
	{		
		this(new MethodType(enclosingType, modifiers, documentation), symbol, enclosingType, node);
	}
	
	public void addParameter(String name, ModifiedType node) 
	{
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

	public boolean canAccept( SequenceType argumentTypes )
	{
		return type.canAccept(argumentTypes);		
	}

	public boolean equals(Object o)
	{
		if( o != null && o instanceof MethodSignature )
		{
			MethodSignature ms = (MethodSignature)o;			
			return ms.symbol.equals(symbol) && ms.type.equals(type) && ms.outer.equals(outer);
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
	
	// These are the true return types that the compiler will use	
	public SequenceType getFullReturnTypes() {	
		Type outerType = getOuter();
		if (isCreate()) {
			SimpleModifiedType modified;				
			if( outerType instanceof InterfaceType )
				modified = new SimpleModifiedType(Type.OBJECT);
			else
				modified = new SimpleModifiedType(outerType);
			
			if( outerType.isPrimitive() )
				modified.getModifiers().addModifier(Modifiers.NULLABLE);
			
			return new SequenceType(Collections.<ModifiedType>singletonList(modified));
		}
		
		//if( isWrapper() || outerType instanceof InterfaceType )
			return signatureWithoutTypeArguments.type.getReturnTypes();
		//else
			//return type.getReturnTypes();
	}
	
	// These are the true parameter types that the compiler will use
	public SequenceType getFullParameterTypes() {
		SequenceType paramTypes = new SequenceType();
		Type outerType = getOuter();
		if (isCreate() || outerType instanceof InterfaceType ) //since actual object is unknown, assume Object for all interface methods
			paramTypes.add(new SimpleModifiedType(Type.OBJECT));
		else
			paramTypes.add(new SimpleModifiedType(outerType)); // this
			
		if( isCreate() && getOuter().hasOuter() )
				paramTypes.add(new SimpleModifiedType(getOuter().getOuter()));			
		
		MethodType methodType;
		
		//if( isWrapper() || getOuter() instanceof InterfaceType )
			methodType = signatureWithoutTypeArguments.type;
		//else
			//methodType = type;		
		 
		if (methodType.isParameterized())
			for (int i = methodType.getTypeParameters().size(); i > 0; i--)
				paramTypes.add(new SimpleModifiedType(Type.CLASS));
		//TODO: add twice as many?  class type + method table?
				
		for (ModifiedType parameterType : methodType.getParameterTypes())
			paramTypes.add(parameterType);	
			
		return paramTypes;
	}
	
	//like toString() but includes outer type to differentiate
	public String hashString() {
		//StringBuilder sb = new StringBuilder(getModifiers().toString());
		//sb.append(" ").append("" + outer).append(":").append(symbol);
		StringBuilder sb = new StringBuilder("" + outer).append(":").append(symbol);
		
		//nothing more for destroy
		if( !symbol.equals("destroy") ) {			
			sb.append(type.parametersToString());
			
			//no return types for create
			if( !symbol.equals("create") )
				sb.append(" => ").append(type.getReturnTypes());
		}
		
		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(getModifiers().toString());
		sb.append(symbol);		
		
		//nothing more for destroy
		if( !symbol.equals("destroy") ) {			
			sb.append(type.parametersToString());
			
			//no return types for create
			if( !symbol.equals("create") )
				sb.append(" => ").append(type.getReturnTypes());
		}
		
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return hashString().hashCode();
	}
	
	//Is it only the wrapped ones that correspond to interface methods?
	//If so, those are the ones that need special generic attention
	public String getMangledName() {
		StringBuilder sb = new StringBuilder();
		
		if( isWrapper() )		
			sb.append(getOuter().toString(Type.MANGLE | Type.TYPE_PARAMETERS | Type.CONVERT_ARRAYS));
		else
			sb.append(getOuter().toString(Type.MANGLE));
		
		sb.append("_M").append(Type.mangle(symbol)).append(type.getTypeWithoutTypeArguments().toString(Type.MANGLE | Type.TYPE_PARAMETERS));
		
		if (isWrapper())
			sb.append("_W");
		
		return sb.toString();
	}

	public List<String> getParameterNames() {
		return type.getParameterNames();
	}
	
	public SequenceType getParameterTypes() {
		return type.getParameterTypes();
	}
	
	public SequenceType getReturnTypes()
	{
		return type.getReturnTypes();
	}
	
	public Type getOuter()
	{
		return outer;
	}
	
	public MethodType getMethodType() {
		return type;
	}
	
	public MethodSignature replace(List<ModifiedType> values,
			List<ModifiedType> replacements) throws InstantiationException {
		MethodSignature replaced = new MethodSignature(type.replace(values, replacements), symbol, outer.replace(values, replacements), node);
		replaced.signatureWithoutTypeArguments = signatureWithoutTypeArguments;
		
		return replaced;
	}
	
	public MethodSignature partiallyReplace(List<ModifiedType> values,
			List<ModifiedType> replacements) {		
		MethodSignature replaced = new MethodSignature(type.partiallyReplace(values, replacements), symbol, outer.partiallyReplace(values, replacements), node);
		replaced.signatureWithoutTypeArguments = signatureWithoutTypeArguments;
		return replaced;
	}
	

	public void updateFieldsAndMethods() throws InstantiationException {
		type.updateFieldsAndMethods();
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
		return hashString().compareTo(o.hashString());
	}

	public boolean isCreate() {		
		return symbol.equals("create");
	}
	
	public boolean isCopy()
	{
		return symbol.equals("copy");
	}
	
	public boolean isGet()
	{	
		return ( type.getModifiers().isGet() && type.getReturnTypes().size() == 1 && type.getParameterTypes().isEmpty() );	
	}
	
	public boolean isSet()
	{	
		return ( type.getModifiers().isSet() && type.getReturnTypes().isEmpty() && type.getParameterTypes().size() == 1 );	
	}
	
	public boolean isNative()
	{
		return type.getModifiers().isNative();
	}
	
	public boolean isVoid()
	{
		return type.getReturnTypes().size() == 0;
	}
	
	public boolean isSingle()
	{
		return type.getReturnTypes().size() == 1;
	}	

	public boolean isSequence()
	{
		return type.getReturnTypes().size() > 1;
	}
	
	public ModifiedType getSingleReturnType()
	{
		if (!isSingle())
			throw new IllegalStateException();
		return getReturnTypes().get(0);
	}
	
	public SequenceType getSequenceReturnTypes()
	{
		if (!isSequence())
			throw new IllegalStateException();
		return getReturnTypes();
	}
	
	public boolean isWrapper() {
		return wrapped != null;
	}
	public MethodSignature getWrapped() {
		return wrapped;
	}

	public MethodSignature wrap(MethodSignature wrapped) {		
		Modifiers modifiers = new Modifiers(wrapped.getModifiers().getModifiers());
		modifiers.removeModifier(Modifiers.NATIVE);		
		MethodType methodType;
		/*if( outer instanceof InterfaceType )		
			methodType = signatureWithoutTypeArguments.type.copy(modifiers);
		else*/
			methodType = type.copy(modifiers);
		MethodSignature wrapper = new MethodSignature(methodType, symbol, outer, node, wrapped);
		wrapper.signatureWithoutTypeArguments = signatureWithoutTypeArguments;
		return wrapper;
	}

	//makes a copy of the method signature with a different outer type	 
	public MethodSignature copy(Type newOuter) {
		return new MethodSignature(type, symbol, newOuter, node, wrapped);		
	}

	public void setOuter(Type outer) {
		this.outer = outer;
	}

	public boolean hasDocumentation()
	{
		return type.hasDocumentation();
	}
	
	public Documentation getDocumentation()
	{
		return type.getDocumentation();
	}

}
