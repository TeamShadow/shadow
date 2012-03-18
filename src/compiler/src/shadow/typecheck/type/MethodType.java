package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import shadow.parser.javacc.ShadowParser.ModifierSet;

public class MethodType extends Type {
	protected List<String> paramNames;
	protected SequenceType paramTypes;
	protected SequenceType returns; /** List of return types */

	public MethodType() {
		this(null, 0);
	}

	public MethodType(Type outer, int modifiers) {
		super(null, modifiers, outer, Kind.METHOD);
		paramNames = new ArrayList<String>();
		paramTypes = new SequenceType();
		returns = new SequenceType();
	}
	
	public boolean matches( List<ModifiedType> argumentTypes )
	{
		return paramTypes.matches(argumentTypes);
	}
	
	public boolean matches( SequenceType inputTypes )
	{
		return paramTypes.matchesTypesAndModifiers( inputTypes );		
	}
	
	/*
	public boolean returns( List<Type> returnTypes )
	{
		if( returns.size() != returnTypes.size() )
			return false;
		
		for( int i = 0; i < returns.size(); i++ )
			if( !returns.get(i).equals(returnTypes.get(i)))
				return false;
		
		return true;
	}
	*/


	//this method is used to see if particular return values inside the method can be given back as return values
	public boolean canReturn( List<ModifiedType> returnTypes )
	{
		return returns.canAccept(returnTypes);
	}
	
	public boolean canReturn( SequenceType returnType )
	{
		return returns.canAccept(returnType);
	}
	
	public boolean canReturn( ModifiedType type )
	{		
		return returns.canAccept(type);
	}
	
	public boolean returnsNothing()
	{
		return returns.isEmpty();
	}		
	
	public boolean canAccept( List<ModifiedType> argumentTypes )
	{
		return paramTypes.canAccept(argumentTypes);
	}
	
	public boolean canAccept( SequenceType argumentTypes )
	{
		return paramTypes.canAccept(argumentTypes);
	}
	
	public void addParameter(String name, ModifiedType type) {
		paramNames.add(name);
		paramTypes.add(type);
	}
	
	public void addParameter(ModifiedType type) {
		paramNames.add(null); // have to add to keep in synch
		paramTypes.add(type);
	}
	
	public ModifiedType getParameterType(String paramName) {
		for(int i=0; i < paramNames.size(); ++i) {
			if(paramNames.get(i).equals(paramName))
				return paramTypes.get(i);
		}
			
		return null;
	}
	
	public boolean containsParam(String paramName) {
		for(int i=0; i < paramNames.size(); ++i) {
			if(paramNames.get(i).equals(paramName))
				return true;
		}
			
		return false;
	}
	
	public List<String> getParameterNames() {
		return paramNames;
	}
	
	public SequenceType getParameterTypes() {
		return paramTypes;
	}
	
	public void addReturn(ModifiedType type) {
		returns.add(type);		
	}
	
	public SequenceType getReturnTypes() {
		return returns;
	}
	
	/**
	 * Need to override equals as we're doing special things	
	 */
	public boolean equals(Object o) {
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
		return paramTypes.matches(other.paramTypes);		
	}
	
	public boolean matchesReturns( MethodType other )
	{		
		return returns.matches(other.returns);
	}

	public String toString()
	{		
		return paramTypes.toString() + " => " + returns.toString();
	}
	
	@Override
	public String getMangledName() {
		StringBuilder sb = new StringBuilder();

		for (ModifiedType type : paramTypes)
			sb.append("_R").append(type.getType().getMangledName());
		
//		for (Type r : returns)
//			sb.append("_R").append(r.getMangledName());
		
		return sb.toString();
	}

}
