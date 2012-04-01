package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.List;

import shadow.typecheck.MethodSignature;

public class InstantiatedType extends ClassType {

	private List<ModifiedType> argumentTypes; 
	private ClassInterfaceBaseType baseType;
	private Type instantiatedType = null;

	public ClassInterfaceBaseType getBaseType()
	{
		return baseType;
	}
	
	public void setBaseType(ClassInterfaceBaseType type)
	{
		baseType = type;
	}
	
	
	public List<ModifiedType> getArgumentTypes()
	{
		return argumentTypes;
	}
	
	
	public InstantiatedType(ClassInterfaceBaseType type, List<ModifiedType> arguments) {
		super(type.getTypeName(), type.getModifiers(), type.getOuter());
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
			{
				ModifiedType a = argumentTypes.get(i);
				ModifiedType b = instantiatedType.argumentTypes.get(i);
				if( !a.getType().equals(b.getType()) || a.getModifiers() != b.getModifiers() )					
					return false;
			}
			
			return true;			
		}
		else
			return false;		
	}
	
	public List<MethodSignature> getMethodList()
	{
		List<MethodSignature> methodsList = super.getMethodList();
		
		List<MethodSignature> instantiatedList = new ArrayList<MethodSignature>();
		for( MethodSignature signature : methodsList )
			instantiatedList.add(signature.replace( baseType.getParameters(), argumentTypes));
		
		return instantiatedList;		
	}	

	//type arguments must match exactly
	//base type can be a subtype
	public boolean isSubtype(Type t)
	{
		if( equals(t) )
			return true;
		
		if( t instanceof InstantiatedType )
		{
			InstantiatedType type = (InstantiatedType) t;
		
			if( !baseType.isSubtype(type.getBaseType()) )
				return false;
			
			if( argumentTypes.size() != type.argumentTypes.size())
				return false;
			
			for( int i = 0; i < argumentTypes.size(); i++ )
				if( !argumentTypes.get(i).equals(type.argumentTypes.get(i)) )
					return false;			
			
			return true;
		}
		else
			return false;
	}
	
	public Type getInstantiatedType()
	{
		if( instantiatedType == null )
			instantiatedType = baseType.replace(baseType.getParameters(), argumentTypes);
		
		return instantiatedType;		
	}
}
