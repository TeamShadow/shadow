package shadow.typecheck.type;

import java.util.List;

public class InstantiatedType extends ClassType {

	private List<ModifiedType> argumentTypes; 
	private Type baseType;

	public Type getBaseType()
	{
		return baseType;
	}
	
	public void setBaseType(Type type)
	{
		baseType = type;
	}
	
	
	public List<ModifiedType> getArgumentTypes()
	{
		return argumentTypes;
	}
	
	
	public InstantiatedType(Type type, List<ModifiedType> arguments) {
		super(type.getTypeName(), type.getModifiers(), type.getOuter(), Kind.INSTANTIATED);
		// TODO Auto-generated constructor stub
		baseType = type;
		argumentTypes = arguments;		
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(baseType.getTypeName());
		builder.append("<");
		
		for( int i = 0; i < argumentTypes.size(); i++ )
		{
			if( i > 0 )
				builder.append(", ");
			builder.append(argumentTypes.get(i).toString());			
		}
		
		builder.append(">");
		
		return builder.toString();
	}
	
	public boolean equals(Object other)
	{
		if( other != null && other instanceof InstantiatedType )
		{
			InstantiatedType instantiatedType = (InstantiatedType)other;
			if( !baseType.equals(instantiatedType.baseType) )
				return false;
			
			for( int i = 0; i < argumentTypes.size(); i++ )
				if( !argumentTypes.get(i).equals(instantiatedType.argumentTypes.get(i)) )
					return false;
			
			return true;			
		}
		else
			return false;		
	}

	//type arguments must match exactly
	//base type can be a subtype
	public boolean isSubtype(InstantiatedType type) {
		
		if( !baseType.isSubtype(type.getBaseType()) )
			return false;
		
		if( argumentTypes.size() != type.argumentTypes.size())
			return false;
		
		for( int i = 0; i < argumentTypes.size(); i++ )
			if( !argumentTypes.get(i).equals(type.argumentTypes.get(i)) )
				return false;			
		
		return true;
	}
}
