package shadow.typecheck.type;

public abstract class GetSetType extends Type
{	
	protected MethodSignature getter;
	protected MethodSignature setter;
	private boolean isLoad = true;
	private boolean isStore = false;
	
	protected GetSetType()
	{
		super(null);
	}
	
	public MethodSignature getGetter()
	{
		return getter;
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
}
