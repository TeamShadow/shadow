package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.List;

import shadow.typecheck.BaseChecker;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeCheckException.Error;

public class PropertyType extends Type
{	
	private MethodSignature getter;
	private MethodSignature setter;
	private UnboundMethodType method;
	private ModifiedType prefix;
	private Type context;
	private boolean isLoad = true;
	private boolean isStore = false;
		
	public List<TypeCheckException> applyInput(ModifiedType input) {
		
		List<TypeCheckException> errors = new ArrayList<TypeCheckException>();
		
		if( method == null ) {
			if( isGettable() )
				BaseChecker.addError(errors, Error.INVALID_PROPERTY, "Cannot assign to non-set property " + getter.getSymbol());
			else
				BaseChecker.addError(errors, Error.INVALID_PROPERTY, "Cannot assign to non-set property"); //should never happen, but...
			return errors;
		}
		
		Type outer = method.getOuter();
		String name = method.getTypeName();
		SequenceType arguments = new SequenceType();		
		arguments.add(input);		
		
		MethodSignature signature = outer.getMatchingMethod(name, arguments);
		
		if( signature == null )
			BaseChecker.addError(errors, Error.INVALID_PROPERTY, "Property " + name + " cannot accept input of type " + input.getType(), input.getType());
		else {			
			setSetter(signature);			
			if( !BaseChecker.methodIsAccessible(signature, context) )
				BaseChecker.addError(errors, Error.ILLEGAL_ACCESS, "Property " + name + " is not accessible from this context");
			
			if( !prefix.getModifiers().isMutable() && signature.getModifiers().isMutable()  )			
				BaseChecker.addError(errors, Error.ILLEGAL_ACCESS, "Mutable property " + name + " cannot be called from " + (prefix.getModifiers().isImmutable() ? "immutable" : "readonly") + " context");
		}		
		
		return errors;
	}
	
	public PropertyType(MethodSignature getter, UnboundMethodType method, ModifiedType prefix, Type context)
	{
		super(null);
		this.getter = getter;
		this.method = method;
		this.prefix = prefix;
		this.context = context;
	}
	
	public ModifiedType getPrefix() {
		return prefix;
	}
	
	public Type getContext() {
		return context;
	}

	public MethodSignature getGetter()
	{
		return getter;
	}
	
	public UnboundMethodType getMethod() {
		return method;
	}
	
	protected void setSetter(MethodSignature setter) {
		this.setter = setter;
	}
	
	public MethodSignature getSetter()
	{
		return setter;
	}
	
	public ModifiedType getGetType()
	{
		if( getter == null )
			return null;
		
		return getter.getReturnTypes().get(0);
	}
	
	public ModifiedType getSetType()
	{
		if( setter == null )
			return null;
		
		//last input parameter, works for both indexing and properties
		ModifiedType input = setter.getParameterTypes().get(setter.getParameterTypes().size() - 1);
		ModifiedType type = new SimpleModifiedType(input.getType(), input.getModifiers());
		type.getModifiers().addModifier(Modifiers.ASSIGNABLE);
		return type;
	}
	
	public boolean isGettable()
	{
		return getter != null;		
	}
	
	public boolean isSettable()
	{
		return setter != null;
	}
	
	public void setLoadOnly()
	{
		isLoad = true;
		isStore = false;
	}
	
	public void setStoreOnly()
	{
		isLoad = false;
		isStore = true;
	}
	
	public void setLoadStore()
	{
		isLoad = isStore = true;
	}
	
	public boolean isLoad()
	{
		return isLoad;
	}
	
	public boolean isStore()
	{
		return isStore;
	}
	
	@Override
	//probably never gets used
	public boolean isSubtype(Type other) {
		if( other instanceof PropertyType && this.getClass().equals(other.getClass()) )
		{
			PropertyType otherProperty = (PropertyType)other;
			if( otherProperty.getter != null )
			{
				if( getGetter() == null )
					return false;
				//covariant on get
				if( !getGetType().getType().isSubtype(otherProperty.getGetType().getType()) )
					return false;
			}
			
			if( otherProperty.getSetter() != null )
			{
				if( getSetter() == null )
					return false;
				//contravariant on set
				if( !otherProperty.getGetType().getType().isSubtype(getGetType().getType()) )
					return false;
			}			
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void updateFieldsAndMethods() throws InstantiationException
	{
		if( getGetter() != null )
			getGetter().updateFieldsAndMethods();
		
		if( getSetter() != null )
			getSetter().updateFieldsAndMethods();
	}

	@Override
	public PropertyType replace(List<ModifiedType> values,
			List<ModifiedType> replacements) throws InstantiationException {
		
		MethodSignature replacedGetter = null;		
		UnboundMethodType replacedMethod = getMethod().replace(values, replacements);
		if( getMethod() != null )
			replacedGetter = getGetter().replace(values, replacements);		
			
		PropertyType replacement = new PropertyType( replacedGetter, replacedMethod, prefix, context);
		
		if( getSetter() != null )
			replacement.setSetter( getSetter().replace(values, replacements));
		
		return replacement;
	}
	
	@Override
	public PropertyType partiallyReplace(List<ModifiedType> values,
			List<ModifiedType> replacements) {
		
		MethodSignature replacedGetter = null;		
		UnboundMethodType replacedMethod = getMethod().partiallyReplace(values, replacements);
		if( getGetter() != null )
			replacedGetter = getGetter().partiallyReplace(values, replacements);		
			
		PropertyType replacement = new PropertyType( replacedGetter, replacedMethod, prefix, context);
		
		if( getSetter() != null )
			replacement.setSetter(getSetter().partiallyReplace(values, replacements));
		
		return replacement;
	}
	

	
	@Override
	public String toString(boolean withBounds)
	{
		StringBuilder sb = new StringBuilder("[");
		
		if( isGettable() )
		{
			sb.append("get: ");
			sb.append(getGetType().getType().toString(withBounds));
		}
		
		if( isGettable() && isSettable() )
		{
			sb.append(", ");
		}
		
		if( isSettable() )
		{
			sb.append("set: ");
			sb.append(getSetType().getType().toString(withBounds));
		}
		sb.append("]");
		return sb.toString();		
	}
}
