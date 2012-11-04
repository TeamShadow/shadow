package shadow.typecheck.type;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import shadow.parser.javacc.Node;

//this class may only cause problems

public class OldInstantiatedType extends ClassType {

	//private List<ModifiedType> typeArguments; 
	private ClassInterfaceBaseType baseType;
	private ClassInterfaceBaseType instantiatedType = null;

	public ClassInterfaceBaseType getBaseType()
	{
		return baseType;
	}
	
	public void setBaseType(ClassInterfaceBaseType type)
	{
		baseType = type;
	}
	
	public OldInstantiatedType(String name)
	{
		super(name, null);
		
	}
	
	/*
	public List<ModifiedType> getTypeArguments()
	{
		return typeArguments;
	}
	*/
	
	/*
	public OldInstantiatedType(ClassInterfaceBaseType type, List<ModifiedType> arguments) {
		super(type.getTypeName(), type.getModifiers(), type.getOuter());
		baseType = type;
		typeArguments = arguments;		
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(baseType.getTypeName());
		builder.append("<");
		
		for( int i = 0; i < typeArguments.size(); i++ )
		{
			if( i > 0 )
				builder.append(", ");
			builder.append(typeArguments.get(i).toString());			
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
			
			for( int i = 0; i < typeArguments.size(); i++ )
			{
				ModifiedType a = typeArguments.get(i);
				ModifiedType b = instantiatedType.typeArguments.get(i);
				if( !a.getType().equals(b.getType()) || a.getModifiers() != b.getModifiers() )					
					return false;
			}
			
			return true;			
		}
		else
			return false;		
	}
	*/
	
	/*
	public List<MethodSignature> getOrderedMethods()
	{
		List<MethodSignature> methodsList = super.getOrderedMethods();
		
		ListIterator<MethodSignature> iter = methodsList.listIterator();
		while( iter.hasNext() )
			iter.set(iter.next().replace(baseType.getTypeParameters(), typeArguments));
		
		return methodsList;		
	}	
	

	//type arguments must match exactly
	//base type can be a subtype
	public boolean isSubtype(Type t)
	{
		if( equals(t) || t == Type.OBJECT )
			return true;
		
		if( t instanceof InstantiatedType )
		{
			InstantiatedType type = (InstantiatedType) t;
		
			if( !baseType.isSubtype(type.getBaseType()) )
				return false;
			
			if( typeArguments.size() != type.typeArguments.size())
				return false;
			
			for( int i = 0; i < typeArguments.size(); i++ )
				if( !typeArguments.get(i).equals(type.typeArguments.get(i)) )
					return false;			
			
			return true;
		}
		else
			return false;
	}
	
	
	public ClassInterfaceBaseType getInstantiatedType()
	{
		if( instantiatedType == null )
			instantiatedType = baseType.replace(baseType.getTypeParameters(), typeArguments);
		
		return instantiatedType;		
	}
	
	public List<MethodSignature> getMethods(String methodName) {
		return getInstantiatedType().getMethods(methodName);
	}
	
	public Node getField(String fieldName) {
		return getInstantiatedType().getField(fieldName);
	}
	
	public ModifiedType getFieldType (String fieldName) {
		return getInstantiatedType().getFieldType(fieldName);
	}
	
	public Map<String, Node> getFields() {
		return getInstantiatedType().getFields();
	}
	
	*/
}
