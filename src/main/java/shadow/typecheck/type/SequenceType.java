package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import shadow.ShadowException;
import shadow.parse.Context;
import shadow.parse.Context.AssignmentKind;
import shadow.typecheck.BaseChecker;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.BaseChecker.SubstitutionKind;
import shadow.typecheck.TypeCheckException.Error;

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
	public boolean canAccept( SequenceType inputTypes, SubstitutionKind substitutionType, List<ShadowException> errors )
	{		
		if( types.size() != inputTypes.size() )
		{
			ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT, "Sequence type " + inputTypes + " does not have the same number of elements as sequence type " + this);
			return false;
		}
		
		for( int i = 0; i < types.size(); i++ )
		{	
			if( types.get(i) != null )
			{
				ModifiedType left = types.get(i);
				ModifiedType right = inputTypes.get(i);
				
				if( !BaseChecker.checkSubstitution(left, right, AssignmentKind.EQUAL, substitutionType, errors ))
					return false;
			}			
		}
		
		return true;		
	}
	
	public boolean canAccept( SequenceType inputTypes )
	{		
		return canAccept(inputTypes, SubstitutionKind.BINDING);		
	}
	
	public boolean canAccept( SequenceType inputTypes, SubstitutionKind substitutionType )
	{		
		return canAccept(inputTypes, substitutionType, null);		
	}
	
	public boolean canAccept( ModifiedType type )
	{		
		return canAccept(type, SubstitutionKind.BINDING);		
	}
	
	public boolean canAccept( ModifiedType type, SubstitutionKind substitutionType )
	{		
		return canAccept(type, substitutionType, null);		
	}
	
	public boolean canAccept( ModifiedType inputType, SubstitutionKind substitutionType, List<ShadowException> errors ) {	
		if( substitutionType.equals( SubstitutionKind.BINDING ) ) {
			SequenceType input = new SequenceType();
			input.add(inputType);		
			return canAccept(input, substitutionType, errors);
		}
		else {			
			if( inputType.getType() instanceof SequenceType )
				return canAccept( (SequenceType)inputType.getType(), substitutionType, errors );
			
			//for splats
			for( ModifiedType modifiedType : types )			
				if( !BaseChecker.checkSubstitution(modifiedType, inputType, AssignmentKind.EQUAL, substitutionType, errors))
					return false;
			
			return true;
		}
	}
	
	@Override
	public String toString(int options) {
		return toString("(", ")", options);		
	}
	
	public String toString(String begin, String end) {
		return toString(begin, end, 0);
	}	
	
	/*
	public String getQualifiedName(boolean withBounds) 
	{		
		return toStringWithQualifiedParameters(withBounds);
	}
	*/

	public String toString(String begin, String end, int options ) {
		StringBuilder builder = new StringBuilder(begin);
		boolean first = true;
		 
		int i = 0;
		if((options & Type.MANGLE_EXTERN) != 0) ++i;
		
		for(; i < types.size(); ++i) {
			ModifiedType modifiedType = types.get(i);
			if( (options & MANGLE ) != 0 )
				builder.append("_");			
			else if( first )
				first = false;			 
			else
				builder.append(",");
			
			Type type = modifiedType.getType();
			Modifiers modifiers = modifiedType.getModifiers();
			
			if( (options & MANGLE) != 0 ) {
				//raw primitive types
				if( type.isPrimitive() && !modifiers.isNullable()  )
					builder.append(type.getTypeName());
				else
					builder.append(type.toString(options));
			}
			else if( type != null ) {
				builder.append(modifiers.toString());
				builder.append(type.toString(options));
			}
		}		
		
		builder.append(end);
		
		return builder.toString();
	}

	@Override
	public boolean equals(Type type)
	{
		if (type == Type.NULL)
			return true;
		
		if( type != null && type instanceof SequenceType )
		{	
			SequenceType inputTypes = (SequenceType)type; 
			
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
	public SequenceType replace(List<ModifiedType> values, List<ModifiedType> replacements) throws InstantiationException
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
	
	
	@Override
	public SequenceType partiallyReplace(List<ModifiedType> values, List<ModifiedType> replacements)
	{		
		SequenceType temp = new SequenceType();
		
		for( int i = 0; i < types.size(); i++ )
		{
			ModifiedType type = types.get(i);
			if( type != null )
			{
				SimpleModifiedType dummy = new SimpleModifiedType( type.getType().partiallyReplace( values, replacements), type.getModifiers() );
				temp.add( dummy );
			}
			else
				temp.add( null );
		}
		
		return temp;
	}
	
	@Override
	public void updateFieldsAndMethods() throws InstantiationException
	{
		for( int i = 0; i < types.size(); ++i ) {
			Type type = types.get(i).getType();			
			if( type instanceof UninstantiatedType ) {
				type = ((UninstantiatedType)type).instantiate();
				types.set(i, new SimpleModifiedType(type, types.get(i).getModifiers()));
			}
			else if( type instanceof ArrayType && ((ArrayType)type).recursivelyGetBaseType() instanceof UninstantiatedType ) {
				type = ((ArrayType)type).instantiate();
				types.set(i, new SimpleModifiedType(type, types.get(i).getModifiers()));
			}
		}
	}
		
	public boolean matches(List<ModifiedType> inputTypes)
	{
		if( inputTypes == null )
			return false;
		
		if( types.size() != inputTypes.size() )
			return false;		
	
		for( int i = 0; i < types.size(); i++ )
		{			
			if( inputTypes.get(i) == null || types.get(i) == null )
				return false;
			/*
			if( get(i).getModifiers().isNullable() != inputTypes.get(i).getModifiers().isNullable() )
			{	
				if( get(i).getModifiers().isNullable() && inputTypes.get(i).getType().equals(Type.NULL))
					continue;
				
				if( inputTypes.get(i).getModifiers().isNullable() && get(i).getType().equals(Type.NULL))
					continue;
				
				return false;
			}
			*/			
			
			if( !inputTypes.get(i).getType().equals(getType(i)) )
				return false;
		}
		
		return true;		
	}
	
	public void setContextType(Context node)
	{
		if( types.size() == 1 )
		{
			ModifiedType modifiedType = types.get(0); 
			node.setType(modifiedType.getType());
			if( modifiedType.getModifiers().isNullable())
				node.getModifiers().addModifier(Modifiers.NULLABLE);
			
			if( modifiedType.getModifiers().isReadonly())
				node.getModifiers().addModifier(Modifiers.READONLY);
			
			if( modifiedType.getModifiers().isImmutable())
				node.getModifiers().addModifier(Modifiers.IMMUTABLE);			
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
		invalidateHashName();
		return types.remove(o);		
	}

	@Override
	public boolean containsAll(Collection<?> c) {		
		return types.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ModifiedType> c) {
		invalidateHashName();
		return types.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends ModifiedType> c) {
		invalidateHashName();
		return types.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		invalidateHashName();
		return types.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		invalidateHashName();
		return types.retainAll(c);
	}

	@Override
	public void clear() {
		invalidateHashName();
		types.clear();
	}

	@Override
	public ModifiedType set(int index, ModifiedType element) {
		invalidateHashName();
		return types.set(index, element);
	}

	@Override
	public void add(int index, ModifiedType element) {
		types.add(index, element);
		invalidateHashName();
	}

	@Override
	public ModifiedType remove(int index) {		
		invalidateHashName();
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
