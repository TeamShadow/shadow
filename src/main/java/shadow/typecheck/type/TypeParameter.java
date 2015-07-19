package shadow.typecheck.type;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypeParameter extends Type
{
	private Set<Type> bounds = new HashSet<Type>();	
	private boolean toStringRecursion = false; //keeps type parameters that have bounds containing themselves from infinitely recursing
	//private TypeParameter recursiveParameterCheck = null; //keeps infinite type parameters checking from happening

	public TypeParameter(String typeName, Type outer)
	{
		super(typeName, new Modifiers(), outer);
		bounds.add(Type.OBJECT);
	}
	
	public ClassType getClassBound()
	{
		for( Type bound : bounds )
			if( bound instanceof ClassType )
				return (ClassType)bound;
				
		return Type.OBJECT;
	}
	
	
	public void addBound(Type type) {		
		//only one class at a time
		if( type instanceof ClassType )		
			bounds.remove(getClassBound());		
		
		bounds.add(type);
		invalidateHashName();
	}
	
	public Set<Type> getBounds()
	{
		return bounds;
	}
	
	public void setBounds(Set<Type> bounds)
	{
		this.bounds = bounds;
		invalidateHashName();
	}

	/*
	@Override
	public boolean acceptsAssignment(Type type, ASTAssignmentOperator.AssignmentType assignmentType, List<String> reasons)
	{	
		if( equals(type) )
			return true;
		
		for( Type bound : bounds )
			if( !type.isSubtype(bound) )
				return false;		
		
		return true;
	}
	*/
	
	public boolean acceptsSubstitution(Type type) 
	{
		if( equals(type) )
			return true;
		
		SequenceType values = new SequenceType(this);	
		SequenceType replacements = new SequenceType(type);		
		
		Set<Type> substitutedBounds = new HashSet<Type>();
		try
		{
			for( Type bound : bounds )
				substitutedBounds.add( bound.replace(values, replacements));
			
			for( Type bound : substitutedBounds )
				if( !type.isSubtype(bound) )
					return false;	
		}
		catch(InstantiationException e)
		{}
		
		return true;
	}
	
	public boolean isSubtype(Type type)
	{		
		if( equals(type) || type == Type.OBJECT )
			return true;
		
		for( Type bound : bounds )
			if( bound.isSubtype(type) )
				return true;
		
		if( type instanceof TypeParameter )
		{
			TypeParameter other = (TypeParameter) type;
			//every bound must be met by us
			for( Type otherBound : other.bounds )
			{
				boolean met = false;
				for( Type bound : bounds  )
				{
					if( bound.isSubtype(otherBound) )
					{
						met = true;
						break;
					}					
				}
				
				if( !met )
					return false;				
			}
			
			return true;			
		}
		
		return false;
	}
	
	
	
	@Override
	public boolean equals(Type o)
	{
		if( o != null && o instanceof TypeParameter )
		{				
			if( o == this )
				return true;
			
			TypeParameter type = (TypeParameter) o;				
			return type.getTypeName().equals(getTypeName()) && getOuter() == type.getOuter();
		}
		else
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
	
	public Type partiallyReplace(SequenceType values, SequenceType replacements )
	{
		return replace( values, replacements );
	}
	
	
	@Override
	public void updateFieldsAndMethods() throws InstantiationException
	{
		Set<Type> toRemove = new HashSet<Type>();
		Set<Type> toAdd = new HashSet<Type>();
		
		for( Type type : bounds ) {
			if( type instanceof UninstantiatedType ) {			
				toRemove.add(type);
				toAdd.add(((UninstantiatedType)type).instantiate());
			}
		}
		
		bounds.removeAll(toRemove);
		bounds.addAll(toAdd);
	}
		
	public String toString(boolean withBounds) {
		StringBuilder builder = new StringBuilder(getTypeName());
		boolean first = true;
		
		if( !toStringRecursion && withBounds && bounds.size() > 1 ) //always contains Object
		{	
			toStringRecursion = true;			
			builder.append(" is ");
			
			for(Type bound : bounds )
			{	
				if( bound != Type.OBJECT )
				{			
					if( !first )
						builder.append(" and ");
					
					builder.append(bound.toString(withBounds));				
					first = false;
				}
			}
			
			toStringRecursion = false;
		}
		
		return builder.toString();
	}
	
	@Override
	public String getMangledNameWithGenerics(boolean convertArrays) {		
		return getClassBound().getMangledNameWithGenerics(true);
	}	
	
	
	
	public String getMangledName()
	{
		//return "_C" + getTypeName();
		return getClassBound().getMangledName();
	}
	
	
	
	public List<MethodSignature> getAllMethods(String methodName)
	{
		return getMethods(methodName);
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
	public boolean hasInterface(InterfaceType type)
	{
		for(Type bound : bounds)
			if( bound.hasInterface(type))
				return true;
		
		return false;
	}	
	
	@Override
	public boolean hasUninstantiatedInterface(InterfaceType type)
	{
		for(Type bound : bounds)
			if( bound.hasUninstantiatedInterface(type))
				return true;
		
		return false;
	}
}