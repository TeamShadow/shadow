package shadow.typecheck.type;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.parser.javacc.SimpleNode;

public class SequenceType extends Type implements Iterable<ModifiedType>, List<ModifiedType>
{	
	private List<ModifiedType> types = new LinkedList<ModifiedType>(); /** List of return types */	

	public SequenceType() {		
		this( new LinkedList<ModifiedType>()  );		
	}

	public SequenceType(List<ModifiedType> modifiedTypes)
	{
		super(null, 0, null, Kind.SEQUENCE);		
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
	
	
	public boolean canAccept( List<ModifiedType> inputTypes, Type container )
	{	
		if( (container instanceof InstantiatedType) && isParameterized() )
		{
			InstantiatedType instantiatedType = (InstantiatedType)container;
			SequenceType replaced = replace( instantiatedType.getParameters(), instantiatedType.getArgumentTypes() );
			return replaced.canAccept( inputTypes );
		}
		else
			return canAccept( inputTypes );				
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
	
	public boolean canAccept( ModifiedType type )
	{
		if( types.size() != 1 )
			return false;
		
		return type.getType().isSubtype(types.get(0).getType());		
	}
	
	public boolean acceptsNullables( List<ModifiedType> otherTypes )
	{	
		if( types.size() != otherTypes.size() )
			return false;		
		
		for( int i = 0; i < types.size(); i++ )
			if( !ModifierSet.isNullable(types.get(i).getModifiers()) && ModifierSet.isNullable(otherTypes.get(i).getModifiers()) )
				return false;		
		
		return true;		
	}	
	
	public boolean acceptsNullables( ModifiedType type )
	{	
		if( types.size() != 1 )
			return false;		
		
		return ModifierSet.isNullable(types.get(0).getModifiers()) || !ModifierSet.isNullable(type.getModifiers());
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
			return matches((SequenceType)o);
		else
			return false;
	}
	
	@Override
	public SequenceType replace(List<TypeParameter> values, List<ModifiedType> replacements )
	{		
		SequenceType temp = new SequenceType();
		
		for( int i = 0; i < types.size(); i++ )
		{
			ModifiedType type = types.get(i);
			SimpleModifiedType dummy = new SimpleModifiedType( type.getType().replace( values, replacements), type.getModifiers() );
			temp.add( dummy );
		}
		
		return temp;
	}
		
	public boolean matches(List<ModifiedType> inputTypes, Type container)
	{
		if( (container instanceof InstantiatedType) && isParameterized() )
		{
			InstantiatedType instantiatedType = (InstantiatedType)container;
			SequenceType replaced = replace( instantiatedType.getParameters(), instantiatedType.getArgumentTypes() );
			return replaced.matches( inputTypes );
		}
		else
			return matches( inputTypes );
	}
	
	public boolean matches(List<ModifiedType> inputTypes)
	{
		if( types.size() != inputTypes.size() )
			return false;
		
	
		for( int i = 0; i < types.size(); i++ )		
			if( !inputTypes.get(i).getType().equals(getType(i)) )
				return false;
		
		return true;		
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

	@Override
	public Iterator<ModifiedType> iterator() {		
		return types.iterator();
	}	

	@Override
	public ModifiedType get(int i) {		
		return types.get(i);
	}
	
	public Type getType(int i) {		
		return types.get(i).getType();
	}
	
	public int getModifiers(int i) {		
		return types.get(i).getModifiers();
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

}
