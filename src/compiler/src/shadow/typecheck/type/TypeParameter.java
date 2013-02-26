package shadow.typecheck.type;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TypeParameter extends Type
{
	private Set<Type> bounds = new HashSet<Type>();

	public TypeParameter(String typeName)
	{
		super(typeName, new Modifiers(), null);
		bounds.add(Type.OBJECT);
	}
	
	public void addBound(Type type) {
		bounds.add(type);
	}
	
	public Set<Type> getBounds()
	{
		return bounds;
	}
	
	public void setBounds(Set<Type> bounds)
	{
		this.bounds = bounds;
	}

	public boolean canAccept(ModifiedType modifiedType) {
		Type type = modifiedType.getType();
		for( Type bound : bounds )
			if( !type.isSubtype(bound) )
				return false;		
		
		return true;
	}
	
	public boolean canTakeSubstitution(Type type)
	{
		if( type instanceof TypeParameter )
		{
			TypeParameter typeParameter = (TypeParameter) type;
			return typeName.equals(typeParameter.typeName);
		}
		else
			
			return false;
	}
	
	public boolean isSubtype(Type type)
	{		
		if( equals(type) || type == Type.OBJECT )
			return true;
		
		for( Type bound : bounds )
			if( bound.isSubtype(type) )
				return true;
		
		return false;
	}
	
	public Type replace(SequenceType values, SequenceType replacements )
	{
		for( int i = 0; i < values.size(); i++ )
		{
			if( values.get(i).getType().getTypeName().equals(getTypeName()))
				return replacements.get(i).getType();
		}
		
		return this;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder(getTypeName());
		boolean first = true;
		
		if( bounds.size() > 1 ) //always contains Object
			builder.append(" is ");
		
		for(Type bound : bounds )
		{			
			if( !first )
				builder.append(" and ");
			
			if( bound != Type.OBJECT )
			{
				builder.append(bound.toString());				
				first = false;
			}
		}
		
		return builder.toString();
	}
	
	public List<MethodSignature> getMethods(String methodName)
	{
		Set<MethodSignature> signatures = new HashSet<MethodSignature>();
		for(Type bound : bounds )					
			signatures.addAll(bound.getMethods(methodName));
		
		return new ArrayList<MethodSignature>(signatures);
	}

	@Override
	public boolean isRecursivelyParameterized() {
		return isParameterized();
	}

	@Override
	public void printMetaFile(PrintWriter out, String linePrefix) {
		// should never get called
	}

	@Override
	public boolean isDescendentOf(Type type) {		
		// should never get called
		return false;
	}

	@Override
	public boolean hasInterface(InterfaceType type) {
		return false;
	}
	

	@Override
	protected void recursivelyGetAllMethods(List<MethodSignature> methodList) {
		// should never get called
	}

	@Override
	protected void recursivelyOrderAllMethods(List<MethodSignature> methodList) {
		// should never get called
	}
}