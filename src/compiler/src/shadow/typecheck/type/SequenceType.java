package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import shadow.TypeCheckException;
import shadow.TypeCheckException.Error;
import shadow.parser.javacc.ASTAssignmentOperator.AssignmentType;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.BaseChecker;
import shadow.typecheck.BaseChecker.SubstitutionType;

public class SequenceType extends Type implements Iterable<ModifiedType>, List<ModifiedType>
{	
	private List<ModifiedType> types; /** List of return types */
	
	public SequenceType(ModifiedType type) {
		this();
		add(type);
	}
	
	public SequenceType(Type type) {
		this(new SimpleModifiedType(type));		
	}	

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
	
	
	/*
	public boolean acceptsAssignment( Type type, SubstitutionType substitutionType, List<String> reasons )
	{
		if( type instanceof SequenceType )		
			return canAccept( (SequenceType)type, substitutionType, reasons );
		else
			return canAccept( new SimpleModifiedType( type, new Modifiers()), substitutionType, reasons );
	}
	*/
		
	//public boolean canAccept( List<ModifiedType> inputTypes, SubstitutionType substitutionType, List<String> reasons )
	public boolean canAccept( SequenceType inputTypes, SubstitutionType substitutionType, List<TypeCheckException> errors )
	{		
		if( types.size() != inputTypes.size() )
		{
			BaseChecker.addError(errors, Error.INVALID_ASSIGNMENT, "Sequence type " + inputTypes + " does not have the same number of elements as sequence type " + this);
			return false;
		}
		
		for( int i = 0; i < types.size(); i++ )
		{	
			if( types.get(i) != null )
			{
				ModifiedType left = types.get(i);
				ModifiedType right = inputTypes.get(i);
				
				if( !BaseChecker.checkAssignment(left, right, AssignmentType.EQUAL, substitutionType, errors ))
					return false;
			}			
		}
		
		return true;		
	}
	
	public boolean canAccept( SequenceType inputTypes )
	{		
		return canAccept(inputTypes, SubstitutionType.BINDING);		
	}
	
	public boolean canAccept( SequenceType inputTypes, SubstitutionType substitutionType )
	{		
		return canAccept(inputTypes, substitutionType, null);		
	}
	
	public boolean canAccept( ModifiedType type )
	{		
		return canAccept(type, SubstitutionType.BINDING);		
	}
	
	public boolean canAccept( ModifiedType type, SubstitutionType substitutionType )
	{		
		return canAccept(type, substitutionType, null);		
	}
	
	public boolean canAccept( ModifiedType inputType, SubstitutionType substitutionType, List<TypeCheckException> errors )
	{	
		if( substitutionType.equals( SubstitutionType.BINDING ) )
		{
			SequenceType input = new SequenceType();
			input.add(inputType);		
			return canAccept(input, substitutionType, errors);
		}
		else
		{			
			if( inputType.getType() instanceof SequenceType )
				return canAccept( (SequenceType)inputType.getType(), substitutionType, errors );
			
			//for splats
			for( ModifiedType modifiedType : types )
			{
				if( !BaseChecker.checkAssignment(modifiedType, inputType, AssignmentType.EQUAL, substitutionType, errors))
					return false;
			}
			
			return true;
		}
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
	
	@Override
	public String getMangledNameWithGenerics()
	{
		StringBuilder builder = new StringBuilder("_L");		
		
		//to distinguish generics that have the same type name, we have to add packages as well
		for(ModifiedType modifiedType: types)
		{
			builder.append(modifiedType.getType().getMangledName());
			//Type type = modifiedType.getType();
			//shadow.typecheck.Package _package = type.getPackage();
			//builder.append(type.getPackage().getMangledName() + type.getMangledNameWithGenerics());
		}
			
		builder.append("_R");
		
		return builder.toString();
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

	@Override
	public boolean equals(Object o)
	{
		if (o == Type.NULL)
			return true;
		
		if( o != null && o instanceof SequenceType )
		{	
			SequenceType inputTypes = (SequenceType)o; 
			
			if( types.size() != inputTypes.size() )
				return false;	
		
			for( int i = 0; i < types.size(); i++ )		
				if( inputTypes.get(i) == null || types.get(i) == null || !inputTypes.get(i).getType().equals(getType(i)) || !inputTypes.get(i).getModifiers().equals(get(i).getModifiers()) )
					return false;
			
			return true;
		}		
		
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
	
	public void setNodeType(SimpleNode node)
	{
		if( types.size() == 1 )
		{
			ModifiedType modifiedType = types.get(0); 
			node.setType(modifiedType.getType());
			if( modifiedType.getModifiers().isNullable())
				node.addModifier(Modifiers.NULLABLE);
			
			if( modifiedType.getModifiers().isReadonly())
				node.addModifier(Modifiers.READONLY);
			
			if( modifiedType.getModifiers().isImmutable())
				node.addModifier(Modifiers.IMMUTABLE);			
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
	public List<ModifiedType> subList(int fromIndex, int toIndex)
	{		
		return types.subList(fromIndex, toIndex);
	}
	
	@Override
	public boolean isSubtype(Type t)
	{
		if( equals(t) )
			return true;
		
		if ( t instanceof SequenceType )
		{
			SequenceType inputTypes = (SequenceType)t;			
			
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
					
					if( !type.isSubtype(inputType) )
						return false;
					
					//if either type is immutable, it will work out no matter what
					//if both are mutable, their modifiers had better both be immutable or both mutable
					if( !type.getModifiers().isImmutable() && !inputType.getModifiers().isImmutable() )
					{
						//inputModifiers = modifiers
						
						//if this is immutable, storage reference must be readonly or immutable (temporary readonly isn't good enough)
						if( modifiers.isImmutable() && !inputModifiers.isImmutable() && !inputModifiers.isReadonly() )
							return false;
						
						//you can't put a readonly thing into a mutable reference
						//but you can put a mutable thing into a readonly reference (but not an immutable one)
						if( modifiers.isReadonly() && !inputModifiers.isReadonly() )
							return false;
						
						if( inputModifiers.isImmutable() && !modifiers.isImmutable() )
							return false;
					}					
					
					if( modifiers.isNullable() && !inputModifiers.isNullable() )
						return false;	
				}			
			}			
			return true;
		}
		else
			return false;
	}
}
