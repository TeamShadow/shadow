package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import shadow.parser.javacc.SimpleNode;

public class SequenceType extends Type implements Iterable<ModifiedType>, List<ModifiedType>
{	
	private List<ModifiedType> types; /** List of return types */	

	public SequenceType() {		
		this( new ArrayList<ModifiedType>()  );		
	}

	public SequenceType(List<ModifiedType> modifiedTypes)
	{
		super(null, new Modifiers(), null);		
		types = modifiedTypes;
	}	
	
	@Override
	public int size()
	{
		return types.size();
	}
	
	@Override
	public boolean isEmpty()
	{
		return types.isEmpty();
	}
	
	public boolean canAccept( List<ModifiedType> inputTypes, List<String> reasons )
	{		
		if( types.size() != inputTypes.size() )
			return false;
		
		for( int i = 0; i < types.size(); i++ )
		{	
			if( types.get(i) != null )
			{
				Type inputType = inputTypes.get(i).getType();
				Modifiers inputModifiers  = inputTypes.get(i).getModifiers();
				Type type = types.get(i).getType();
				Modifiers modifiers = types.get(i).getModifiers();
							
				if( type instanceof TypeParameter  )
				{			
					TypeParameter parameter = (TypeParameter) type;
					if( !parameter.canAccept( inputTypes.get(i)  ) )
					{
						if( reasons != null )
							reasons.add(inputType + " cannot be substituted for " + type);
						return false;
					}
				}
				else if( !inputType.isSubtype(type) )
				{
					if( reasons != null )
						reasons.add(inputType + " is not a subtype of " + type);
					return false;
				}
				
				//if either type is immutable, it will work out no matter what
				//if both are mutable, their modifiers had better both be immutable or both mutable
				if( !type.getModifiers().isImmutable() && !inputType.getModifiers().isImmutable() &&
					modifiers.isImmutable() != inputModifiers.isImmutable() )
				{
					if( reasons != null )
					{				
						String reason = "";
						if( modifiers.isImmutable() )
							reason += "immutable ";
						reason += type + " is not compatible with ";
						if( inputModifiers.isImmutable() )
							reason += "immutable ";
						reason += inputType;
						
						reasons.add(reason);
					}
					return false;
				}
				
				if( !modifiers.isNullable() && inputModifiers.isNullable() )
				{
					if( reasons != null )
						reasons.add("non-nullable " + type + " cannot accept nullable " + inputType);
					
					return false;
				}	
			}			
		}
		
		return true;		
	}	
	
	
	public boolean canReturn( List<ModifiedType> inputTypes )
	{		
		if( types.size() != inputTypes.size() )
			return false;
		
		for( int i = 0; i < types.size(); i++ )
		{	
			if( types.get(i) != null )
			{
				Type inputType = inputTypes.get(i).getType();
				Modifiers inputModifiers  = inputTypes.get(i).getModifiers();
				Type type = types.get(i).getType();
				Modifiers modifiers = types.get(i).getModifiers();
				
				if( !inputType.isSubtype(type) )
					return false;
				
				//if either type is immutable, it will work out no matter what
				//if both are mutable, their modifiers had better both be immutable or both mutable
				if( !type.getModifiers().isImmutable() && !inputType.getModifiers().isImmutable() &&
					modifiers.isImmutable() != inputModifiers.isImmutable() )
					return false;				
				
				if( !modifiers.isNullable() && inputModifiers.isNullable() )
					return false;	
			}			
		}
		
		return true;		
	}	
	
	
	public boolean canAccept( List<ModifiedType> inputTypes )
	{		
		return canAccept( inputTypes, null);		
	}
	
	public boolean canAccept( ModifiedType type )
	{		
		return canAccept(type, null);		
	}
	
	public boolean canAccept( ModifiedType type, List<String> reasons )
	{		
		ArrayList<ModifiedType> list = new ArrayList<ModifiedType>(1);
		list.add(type);
		
		return canAccept(list, reasons);		
	}
	
	public String toString()
	{
		return toString("(", ")", false);		
	}
	
	public String toString(boolean withBounds)
	{
		return toString("(", ")", withBounds);		
	}
	
	public String toString(String begin, String end) {
		return toString(begin, end, false);
	}

	public String toString(String begin, String end, boolean withBounds ) {
		StringBuilder builder = new StringBuilder(begin);
		boolean first = true;
		
		for(ModifiedType type: types)
		{			
			if( !first )
				builder.append(", ");
								
			
			if( type != null )
			{
				builder.append(type.getModifiers().toString());
				builder.append(type.getType().toString(withBounds));
			}
			
			first = false;
		}
		
		builder.append(end);
		
		return builder.toString();
	}
	
	public boolean isAssignable()
	{
		for( ModifiedType type : this )
			if( type != null && type.getModifiers().isConstant() )
				return false;	
		
		return true;		
	}

	public boolean equals(Object o)
	{
		if (o == Type.NULL)
			return true;
		if( o != null && o instanceof SequenceType )
			return exactlyMatches((SequenceType)o);
		else
			return false;
	}
	
	@Override
	public SequenceType replace(SequenceType values, SequenceType replacements)
	{		
		SequenceType temp = new SequenceType();
		
		for( int i = 0; i < types.size(); i++ )
		{
			ModifiedType type = types.get(i);
			if( type != null )
			{
				SimpleModifiedType dummy = new SimpleModifiedType( type.getType().replace( values, replacements), type.getModifiers() );
				temp.add( dummy );
			}
			else
				temp.add( null );
		}
		
		return temp;
	}
		
	public boolean matches(List<ModifiedType> inputTypes)
	{
		if( types.size() != inputTypes.size() )
			return false;
		
	
		for( int i = 0; i < types.size(); i++ )
			if( inputTypes.get(i) == null || types.get(i) == null || !inputTypes.get(i).getType().equals(getType(i)) )
				return false;
			
		
		
		return true;		
	}
	
	
	public boolean exactlyMatches(List<ModifiedType> inputTypes)
	{
		if( types.size() != inputTypes.size() )
			return false;	
	
		for( int i = 0; i < types.size(); i++ )		
			if( inputTypes.get(i) == null || types.get(i) == null || !inputTypes.get(i).getType().equals(getType(i)) || !inputTypes.get(i).getModifiers().equals(get(i).getModifiers()) )
				return false;
		
		return true;		
	}
	
	public void setNodeType(SimpleNode node)
	{
		if( types.size() == 1 )
		{
			ModifiedType modifiedType = types.get(0); 
			node.setType(modifiedType.getType());
			if( modifiedType.getModifiers().isNullable())
				node.addModifier(Modifiers.NULLABLE);			
		}
		else
			node.setType(this);
	}

	@Override
	public Iterator<ModifiedType> iterator() {		
		return types.iterator();
	}	

	@Override
	public ModifiedType get(int i) {		
		return types.get(i);
	}
	
	public Type getType(int i) {
		ModifiedType modifiedType = types.get(i); 		
		return modifiedType == null ? null : modifiedType.getType();
	}
	
	public Modifiers getModifiers(int i) {		
		ModifiedType modifiedType = types.get(i); 		
		return modifiedType == null ? null : modifiedType.getModifiers();
	}


	@Override
	public boolean contains(Object o) {
		return types.contains(o);
	}

	@Override
	public Object[] toArray() {		
		return types.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return types.toArray(a);		
	}

	@Override
	public boolean add(ModifiedType e) {
		return types.add(e);
	}

	@Override
	public boolean remove(Object o) {		
		return types.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {		
		return types.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ModifiedType> c) {
		return types.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends ModifiedType> c) {
		return types.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {		
		return types.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {		
		return types.retainAll(c);
	}

	@Override
	public void clear() {
		types.clear();
	}

	@Override
	public ModifiedType set(int index, ModifiedType element) {		
		return types.set(index, element);
	}

	@Override
	public void add(int index, ModifiedType element) {
		types.add(index, element);		
	}

	@Override
	public ModifiedType remove(int index) {		
		return types.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return types.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {		
		return types.lastIndexOf(o);
	}

	@Override
	public ListIterator<ModifiedType> listIterator() {		
		return types.listIterator();
	}

	@Override
	public ListIterator<ModifiedType> listIterator(int index) {		
		return types.listIterator(index);
	}

	@Override
	public List<ModifiedType> subList(int fromIndex, int toIndex) {		
		return types.subList(fromIndex, toIndex);
	}

	public boolean canSubstitute(List<ModifiedType> inputTypes) {	
		if( types.size() != inputTypes.size() )
			return false;
		
		for( int i = 0; i < types.size(); i++ )
		{
			ModifiedType modifiedInput = inputTypes.get(i);
			ModifiedType modifiedType = types.get(i);
			
			if( modifiedInput == null || modifiedType == null )
				return false;
			
			Type input = modifiedInput.getType();
			Type type = modifiedType.getType();
			
			if( type instanceof TypeParameter )
			{
				TypeParameter parameter = (TypeParameter)type;
				if( !parameter.canTakeSubstitution(input))
					return false;				
			}			
			else if( !input.isSubtype(type) )
				return false;
		}
		
		return true;	
	}
	
	public boolean isSubtype(Type t)
	{
		if( equals(t) )
			return true;		
		
		if ( t instanceof SequenceType )
			return ((SequenceType)t).canAccept(this);
		else
			return false;
	}
}
