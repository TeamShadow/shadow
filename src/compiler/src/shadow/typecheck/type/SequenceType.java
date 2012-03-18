package shadow.typecheck.type;

import java.util.LinkedList;
import java.util.List;

import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.parser.javacc.SimpleNode;

public class SequenceType extends Type
{	
	protected List<ModifiedType> types = new LinkedList<ModifiedType>(); /** List of return types */	

	public SequenceType() {		
		this( new LinkedList<ModifiedType>()  );		
	}

	public SequenceType(List<ModifiedType> modifiedTypes)
	{
		super(null, 0, null, Kind.SEQUENCE);
		
		types = modifiedTypes;
	}

	public void addType(ModifiedType type) {
		types.add(type);
	}
	
	public List<ModifiedType> getTypes()
	{
		return types;
	}
	
	public int size()
	{
		return types.size();
	}
	
	public boolean isEmpty()
	{
		return types.isEmpty();
	}
	
	
	public boolean canAccept( List<ModifiedType> inputTypes )
	{
		if( types.size() != inputTypes.size() )
			return false;
		
		for( int i = 0; i < types.size(); i++ )
			if( !inputTypes.get(i).getType().isSubtype(types.get(i).getType()) )
				return false;
		
		return true;		
	}
	
	public boolean canAccept( SequenceType sequenceType )
	{
		return canAccept( sequenceType.getTypes() );
	}
	
	public boolean canAccept( ModifiedType type )
	{
		if( types.size() != 1 )
			return false;
		
		return type.getType().isSubtype(types.get(0).getType());		
	}
	
	public boolean matchesNullables( List<ModifiedType> otherTypes )
	{	
		if( types.size() != otherTypes.size() )
			return false;		
		
		for( int i = 0; i < types.size(); i++ )
			if( !ModifierSet.isNullable(types.get(i).getModifiers()) && ModifierSet.isNullable(otherTypes.get(i).getModifiers()) )
				return false;		
		
		return true;		
	}
	
	public boolean matchesNullables( SequenceType other )
	{
		return matchesNullables( other.getTypes() );	
	}	
	
	public String toString() {
		StringBuilder builder = new StringBuilder("(");
		boolean first = true;
		
		for(ModifiedType type: types)
		{			
			if( !first )
				builder.append(",");
			
			Type p = type.getType();
			
			if( ModifierSet.isFinal(type.getModifiers()))
				builder.append("final ");
			
			if( ModifierSet.isNullable(type.getModifiers()))
				builder.append("nullable ");
			
			if(p.typeName == null) // method type
				builder.append(p.toString());
			else
				builder.append(p.typeName);
			
			first = false;
		}
		
		builder.append(" )");
		
		return builder.toString();
	}

	public boolean equals(Object o)
	{
		if (o == Type.NULL)
			return true;
		if( o != null && o instanceof SequenceType )
			return types.equals(((SequenceType)o).types);
		else
			return false;
	}
		
	public boolean matches(List<ModifiedType> inputTypes)
	{
		if( types.size() != inputTypes.size() )
			return false;
		
		for( int i = 0; i < types.size(); i++ )		
			if( !inputTypes.get(i).getType().equals(types.get(i).getType()) || inputTypes.get(i).getModifiers() != types.get(i).getModifiers() )
				return false;
		
		return true;		
	}
	
	public boolean matches(SequenceType inputTypes)
	{
		return matches( inputTypes.getTypes() );		
	}
	
	
	public void setNodeType(SimpleNode node)
	{
		if( types.size() == 1 )
		{
			ModifiedType modifiedType = types.get(0); 
			node.setType(modifiedType.getType());
			if( ModifierSet.isNullable(modifiedType.getModifiers()))
				node.addModifier(ModifierSet.NULLABLE);			
		}
		else
			node.setType(this);
	}

	public boolean matchesTypesAndModifiers(SequenceType inputTypes)
	{
		if( types.size() != inputTypes.types.size() )
			return false;
		
		for( int i = 0; i < types.size(); i++ )		
			if( !inputTypes.types.get(i).getType().equals(types.get(i).getType()) || inputTypes.types.get(i).getModifiers() != types.get(i).getModifiers() )
				return false;
		
		return true;		
	}

}
