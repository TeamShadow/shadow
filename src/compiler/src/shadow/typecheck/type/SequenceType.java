package shadow.typecheck.type;

import java.util.LinkedList;
import java.util.List;

import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.typecheck.type.Type.Kind;

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
	
	public boolean canAccept( List<ModifiedType> inputTypes )
	{
		if( types.size() != inputTypes.size() )
			return false;
		
		for( int i = 0; i < types.size(); i++ )
			if( !inputTypes.get(i).getType().isSubtype(types.get(i).getType()) )
				return false;
		
		return true;
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		
		for(ModifiedType p:types) {
			if(p.getType().typeName == null) // method type or sequence type
				sb.append(p.getType().toString());
			else
				sb.append(p.getType().typeName);
			
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
