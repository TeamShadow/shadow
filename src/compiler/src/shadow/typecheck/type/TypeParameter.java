package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.List;

public class TypeParameter extends ClassInterfaceBaseType
{
	private List<Type> bounds = new ArrayList<Type>();

	public TypeParameter(String typeName)
	{
		super(typeName, 0, null);
	}
	
	public void addBound(Type type) {
		bounds.add(type);
	}
	
	public List<Type> getBounds()
	{
		return bounds;
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
			
			for( Type bound : typeParameter.bounds )
			{
				boolean found = false;
				for( int i = 0; i < bounds.size() && !found; i++ )
				{
					if( bounds.get(i).isSubtype(bound) )
						found = true;					
				}
				
				if( !found )
					return false;
			}
			
			return true;			
		}
		else
		{
			for( Type bound : bounds )
				if( bound.isSubtype(type) )
					return true;
			
			return false;
		}
	}
	
	public boolean isSubtype(Type type)
	{		
		if( equals(type) )
			return true;
		
		for( Type bound : bounds )
			if( bound.isSubtype(type) )
				return true;
		
		return false;
	}
	
	public Type replace(List<TypeParameter> values, List<ModifiedType> replacements )
	{
		for( int i = 0; i < values.size(); i++ )
			if( values.get(i).getTypeName().equals(getTypeName()))
				return replacements.get(i).getType();
		
		return this;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder(getTypeName());
		boolean first = true;
		
		if( !bounds.isEmpty() )
			builder.append(" is ");
		
		for(Type bound : bounds )
		{			
			if( !first )
				builder.append(" and ");
					
			builder.append(bound.toString());
			
			first = false;
		}
		
		return builder.toString();
	}
	
}