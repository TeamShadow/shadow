package shadow.typecheck.type;

import java.util.LinkedList;
import java.util.List;

import shadow.typecheck.type.Type.Kind;

public class SequenceType extends Type
{	
	protected List<Type> types; /** List of return types */

	public SequenceType() {		
		this( new LinkedList<Type>()  );		
	}

	public SequenceType(List<Type> types)
	{
		super(null, 0, null, Kind.SEQUENCE);
		this.types = types;
	}

	public void addType(Type type) {
		types.add(type);
	}
	
	public List<Type> getTypes()
	{
		return types;
	}
	
	public boolean canAccept( List<Type> inputTypes )
	{
		if( types.size() != inputTypes.size() )
			return false;
		
		for( int i = 0; i < types.size(); i++ )
			if( !inputTypes.get(i).isSubtype(types.get(i)))
				return false;
		
		return true;
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		
		for(Type p:types) {
			if(p.typeName == null) // method type or sequence type
				sb.append(p.toString());
			else
				sb.append(p.typeName);
			
			sb.append(",");
		}

		if(types.size() == 0) //maybe this shouldn't be allowed
			sb.append(" )");
		else
			sb.setCharAt(sb.lastIndexOf(","), ')');
		
		return sb.toString();
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

}
