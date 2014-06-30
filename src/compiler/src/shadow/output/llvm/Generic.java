package shadow.output.llvm;

import java.util.ArrayList;
import java.util.Map.Entry;

import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public class Generic implements Comparable<Generic>
{	
	private String name;
	private String mangledName;
	private String mangledGeneric;	
	boolean isInterface;
	ArrayList<String> parameters = new ArrayList<String>();
	ArrayList<String> interfaces = new ArrayList<String>();
	
	String typeLayout = "";
	
	String parent = null;

	public Generic(Type type)
	{
		name = type.toString();
		mangledName = type.getMangledNameWithGenerics();
		mangledGeneric = type.getMangledName();						
		isInterface = type instanceof InterfaceType;
		
		if( !isInterface )
		{
			Type base = type.getTypeWithoutTypeArguments();
			StringBuilder sb = new StringBuilder("%" + mangledGeneric + " = type { ");
		
			sb.append(simplify(Type.CLASS)).append(", ");
		
			//then the method table
			sb.append(simplify(Type.OBJECT));

			if (type.isPrimitive())
				sb.append(", ").append(simplify(type));
			else			
				for (Entry<String, ? extends ModifiedType> field :
					((ClassType)base).orderAllFields())
					sb.append(", ").append(simplify(field.getValue().getType()));
			typeLayout = sb.append(" }").toString();
			
			
			ClassType classType = (ClassType) type;
			if( classType.getExtendType() == null )
				parent = "null";
			else
				parent = "@\"" + classType.getExtendType().getMangledNameWithGenerics() + "_class\"";
		}
		
		for( ModifiedType parameter : type.getTypeParametersIncludingOuterClasses() )
		{
			Type parameterType = parameter.getType();
			
			if( parameterType instanceof ArrayType )
				parameterType = ((ArrayType)parameterType).convertToGeneric();
				
			addParameter(parameterType.getMangledNameWithGenerics());
		}		
	}
	
	public String getTypeLayout()
	{
		return typeLayout;
	}
	
	private static String simplify(Type type)
	{
		if( type.isPrimitive() )		
			return '%' + type.getTypeName();		
		else if( type instanceof InterfaceType )		
			return "{ " + simplify(Type.OBJECT) + ", " + simplify(Type.OBJECT) + " }";			
		else if( type instanceof ArrayType )		
			return "{ " + simplify(Type.OBJECT) + "*, [" + ((ArrayType)type).getDimensions() + " x " + simplify(Type.INT) + "] }";			
		else if( type instanceof MethodType )
			throw new UnsupportedOperationException("Method types not yet supported");
		else
			return "%\"" + Type.OBJECT.getMangledName() + "\"*";
	}
	
	public void addParent(String parent)
	{
		this.parent = parent;
	}
	
	public String getParent()
	{
		return parent;
	}
	
	public boolean isInterface()
	{
		return isInterface;
	}
	
	public ArrayList<String> getParameters()
	{
		return parameters;
	}
	
	public void addParameter(String parameter)
	{
		parameters.add(parameter);		
	}
	
	public ArrayList<String> getInterfaces()
	{
		return interfaces;
	}
	
	public void addInterface(String _interface)
	{
		interfaces.add(_interface);		
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getMangledName()
	{
		return mangledName;
	}
	
	public String getMangledGeneric()
	{
		return mangledGeneric;
	}
	
	@Override
	public int hashCode()
	{
		return mangledName.hashCode();		
	}
	
	@Override
	public int compareTo(Generic other)
	{
		return mangledName.compareTo(other.mangledName);		
	}
	
	@Override
	public String toString()
	{
		return mangledName;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if( other instanceof Generic )		
			return mangledName.equals(((Generic)other).mangledName);
		else
			return false;
	}
}
