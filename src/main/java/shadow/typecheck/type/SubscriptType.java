package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.List;

import shadow.typecheck.BaseChecker;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeCheckException.Error;

public class SubscriptType extends PropertyType
{	
	private ModifiedType index;			
	
	public SubscriptType(MethodSignature getter, ModifiedType index, UnboundMethodType method, ModifiedType prefix, Type context) {	
		super(getter, method, prefix, context);
		this.index = index;			
	}
	
	public ModifiedType getIndex() {
		return index;
	}
	
	//this will probably never be used
	@Override
	public boolean isSubtype(Type other) 
	{
		if( other instanceof SubscriptType )
		{
			SubscriptType otherIndex = (SubscriptType)other;
			//contravariant on index type		
			
			if( !otherIndex.index.getType().isSubtype(index.getType()))
				return false;
			
			return super.isSubtype(other);
		}
		
		return false;
	}
	
	@Override
	public List<TypeCheckException> applyInput(ModifiedType input) {
		
		List<TypeCheckException> errors = new ArrayList<TypeCheckException>();
		UnboundMethodType method = getMethod();
		Type outer = method.getOuter();
		String name = method.getTypeName();
		SequenceType arguments = new SequenceType();	
		arguments.add(index);
		arguments.add(input);
		Type context = getContext();
		
		ModifiedType prefix = getPrefix();
		
		MethodSignature signature = outer.getMatchingMethod(name, arguments);
		
		if( signature == null )
			BaseChecker.addError(errors, Error.INVALID_SUBSCRIPT, "Subscript cannot accept input of type " + input.getType(), input.getType());
		else {			
			setSetter(signature);			
			if( !BaseChecker.methodIsAccessible(signature, context) )
				BaseChecker.addError(errors, Error.ILLEGAL_ACCESS, "Subscript is not accessible from this context");
			
			if( !prefix.getModifiers().isMutable() && signature.getModifiers().isMutable()  )			
				BaseChecker.addError(errors, Error.ILLEGAL_ACCESS, "Mutable subscript cannot be called from " + (prefix.getModifiers().isImmutable() ? "immutable" : "readonly") + " context");
		}		
		
		return errors;
	}

	@Override
	public SubscriptType replace(List<ModifiedType> values,
			List<ModifiedType> replacements) throws InstantiationException {		
		
		ModifiedType replacedIndex = new SimpleModifiedType(index.getType().replace(values, replacements), index.getModifiers());
		UnboundMethodType replacedMethod = getMethod().replace(values, replacements);
		MethodSignature replacedGetter = null;		
		if( getGetter() != null )
			replacedGetter = getGetter().replace(values, replacements);
		
		SubscriptType replacement = new SubscriptType( replacedGetter, replacedIndex, replacedMethod, getPrefix(), getContext());
			
		if( getSetter() != null )					
			replacement.setSetter(getSetter().replace(values, replacements));
			
		return replacement;
	}
	
	@Override
	public SubscriptType partiallyReplace(List<ModifiedType> values,
			List<ModifiedType> replacements) {		
		
		ModifiedType replacedIndex = new SimpleModifiedType(index.getType().partiallyReplace(values, replacements), index.getModifiers());
		UnboundMethodType replacedMethod = getMethod().partiallyReplace(values, replacements);
		MethodSignature replacedGetter = null;		
		if( getGetter() != null )
			replacedGetter = getGetter().partiallyReplace(values, replacements);
		
		SubscriptType replacement = new SubscriptType( replacedGetter, replacedIndex,  replacedMethod, getPrefix(), getContext());
			
		if( getSetter() != null )					
			replacement.setSetter(getSetter().partiallyReplace(values, replacements));
			
		return replacement;
	}
	
	@Override
	public String toString(boolean withBounds)
	{
		StringBuilder sb = new StringBuilder(getGetType().getType().toString(withBounds));
					
		sb.append(" <= [");
		sb.append(index.getType().toString(withBounds));
		sb.append("]");
				
		if( isSettable() )
		{
			sb.append(" <= ");
			sb.append(getSetType().getType().toString(withBounds));
		}		
		return sb.toString();		
	}
}
