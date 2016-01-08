package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.List;

import shadow.doctool.Documentation;

public class MethodType extends ClassType {
	protected List<String> parameterNames; /** List of parameter names */
	protected SequenceType parameterTypes; /** List of parameter types */
	protected SequenceType returns; /** List of return types */
	protected boolean inline = false; /** Whether the method is defined inline */ 
	
	public MethodType() {
		this(null, new Modifiers(), null);
	}

	public MethodType(Type outer, Modifiers modifiers, Documentation documentation) {
		super(null, modifiers, documentation, outer);
		parameterNames = new ArrayList<String>();
		parameterTypes = new SequenceType();
		returns = new SequenceType();
		typeWithoutTypeArguments = this;
		setExtendType(Type.METHOD); // added
	}
	
	@Override
	public Type getOuter() {
		return null;
	}
	
	public MethodType copy(Type outer, Modifiers modifiers)
	{
		MethodType copiedType = new MethodType(outer, modifiers, getDocumentation());
		copiedType.parameterNames.addAll(parameterNames);
		copiedType.parameterTypes.addAll(parameterTypes);
		copiedType.returns.addAll(returns);
		copiedType.typeWithoutTypeArguments = typeWithoutTypeArguments;
		return copiedType;
	}
	
	//used to copy a MethodType with different modifiers
	public MethodType copy(Modifiers modifiers)
	{
		return copy(super.getOuter(), modifiers);
	}	
	
	//this method is used to see if particular return values inside the method can be given back as return values
	public boolean canReturn( SequenceType returnTypes)
	{
		return returns.canAccept(returnTypes);
	}
	
	public boolean canReturn( ModifiedType type )
	{		
		return returns.canAccept(type);
	}
	
	public boolean returnsNothing()
	{
		return returns.isEmpty();
	}		

	
	public boolean canAccept( SequenceType argumentTypes )
	{
		return parameterTypes.canAccept(argumentTypes);
	}
	
	public boolean canAccept(ModifiedType candidate)
	{
		return parameterTypes.canAccept(candidate);	
	}
	
	public void addParameter(String name, ModifiedType type) {
		parameterNames.add(name);
		parameterTypes.add(type);
		invalidateHashName();
	}
	
	public void addParameter(ModifiedType type) {
		parameterNames.add(null); // have to add to keep in synch
		parameterTypes.add(type);
		invalidateHashName();
	}
	
	public ModifiedType getParameterType(String paramName) {
		for(int i=0; i < parameterNames.size(); ++i) {
			if(parameterNames.get(i).equals(paramName))
				return parameterTypes.get(i);
		}
			
		return null;
	}
	
	public boolean containsParam(String paramName) {
		for(int i=0; i < parameterNames.size(); ++i) {
			if(parameterNames.get(i).equals(paramName))
				return true;
		}
			
		return false;
	}
	
	public List<String> getParameterNames() {
		return parameterNames;
	}
	
	public SequenceType getParameterTypes() {
		return parameterTypes;
	}
	
	public void addReturn(ModifiedType type) {
		returns.add(type);	
		invalidateHashName();
	}
	
	public SequenceType getReturnTypes() {
		return returns;
	}
	
	/**
	 * Need to override equals as we're doing special things	
	 */
	@Override
	public boolean equals(Type o) {
		if( o != null && o instanceof MethodType )
		{		
			MethodType methodType = (MethodType)o;			
			return matchesParams( methodType ) && matchesReturns( methodType );
		}
		else
			return false;
	}
	
	public boolean matchesParams( MethodType other )
	{		
		return parameterTypes.matches(other.parameterTypes);		
	}
	
	public boolean matchesReturns( MethodType other )
	{		
		return returns.matches(other.returns);
	}

	@Override
	public String toString(int options) {	
		if( (options & MANGLE) != 0 ) {
			return parameterTypes.toString("", "", options);
		}			
			
		return parameterTypes.toString(options) + " => " + returns.toString(options);
	}	
	
	public String parametersToString()
	{
		StringBuilder sb = new StringBuilder("(");
		
		for( int i = 0; i < parameterTypes.size(); i++ )
		{
			if( i != 0 )
				sb.append(", ");
			sb.append( parameterTypes.get(i).getModifiers() );
			sb.append(parameterTypes.get(i).getType());
			sb.append(" ");
			sb.append(parameterNames.get(i));			
		}	
			
		sb.append(")");
		
		return sb.toString();
	}
	
	public MethodType getTypeWithoutTypeArguments()
	{		
		return (MethodType)typeWithoutTypeArguments;
	}
	

	
	@Override
	public MethodType replace(List<ModifiedType> values, List<ModifiedType> replacements ) throws InstantiationException
	{	
		MethodType replaced = new MethodType(super.getOuter(), getModifiers(), getDocumentation());	
		
		replaced.parameterNames = parameterNames;
		replaced.parameterTypes = parameterTypes.replace(values, replacements);
		replaced.returns = returns.replace(values, replacements);
		
		replaced.typeWithoutTypeArguments = typeWithoutTypeArguments;
				
		return replaced;
	}
	
	@Override
	public MethodType partiallyReplace(List<ModifiedType> values, List<ModifiedType> replacements )
	{	
		MethodType replaced = new MethodType(super.getOuter(), getModifiers(), getDocumentation());	
		
		replaced.parameterNames = parameterNames;
		replaced.parameterTypes = parameterTypes.partiallyReplace(values, replacements);
		replaced.returns = returns.partiallyReplace(values, replacements);
		
		replaced.typeWithoutTypeArguments = typeWithoutTypeArguments;
				
		return replaced;
	}
	
	public void updateFieldsAndMethods() throws InstantiationException {
		parameterTypes.updateFieldsAndMethods();
		returns.updateFieldsAndMethods();		
		typeWithoutTypeArguments = this;		
	}
	

	//covariant returns and contravariant parameters
	public boolean matchesInterface(MethodType type) {
		boolean value = returns.isSubtype(type.returns) && type.parameterTypes.isSubtype(parameterTypes);
		return value;
	}
	
	public boolean isSubtype(Type t)
	{
		if( t == UNKNOWN )
			return false;
	
		if( equals(t) || t == Type.OBJECT )
			return true;
		
		if( t instanceof MethodType )
		{
			MethodType otherMethod = (MethodType) t;
			return returns.isSubtype(otherMethod.returns) && otherMethod.parameterTypes.isSubtype(parameterTypes);
		}
		else
			return false;
	}
	
	public void setInline( boolean inline )
	{
		this.inline = inline;
	}
	
	public boolean isInline()
	{
		return inline;
	}
}
