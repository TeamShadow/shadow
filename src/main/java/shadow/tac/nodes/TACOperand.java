package shadow.tac.nodes;

import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public abstract class TACOperand extends TACNode implements ModifiedType
{
	private Object data;
	private TACLocalStore store = null;
	private TACStore memoryStore = null;
	
	public boolean hasLocalStore() {
		return store != null;
	}
	
	public boolean hasMemoryStore() {
		return memoryStore != null;
	}
	
	public void setLocalStore(TACLocalStore store) {
		this.store = store;
	}
	

	public void setMemoryStore(TACStore store) {
		this.memoryStore = store;
	}
	
	public TACStore getMemoryStore() {
		return memoryStore;
	}	
	
	
	public TACLocalStore getLocalStore() {
		return store;
	}	
	
	public Object getData()
	{
		return data;
	}
	
	public void setData(Object data)
	{
		this.data = data;
	}

	protected TACOperand(TACNode node)
	{
		super(node);
	}

	@Override
	public Modifiers getModifiers()
	{
		return new Modifiers();
	}
	@Override
	public abstract Type getType();
	@Override
	public void setType(Type type)
	{
		throw new UnsupportedOperationException();
	}
	

	protected TACOperand checkVirtual(ModifiedType type, TACNode node)
	{	
		//Type thing1 = getType();
		//Type thing2 = type.getType();		
		
		if( getType().isSubtype(type.getType()) && (getType().isPrimitive() || type.getType().isPrimitive()) && getModifiers().isNullable() != type.getModifiers().isNullable() )
			return TACCast.cast(node, type, this);
				
		if (getType().isStrictSubtype(type.getType()))
			return TACCast.cast(node, type, this);		
		
		/* allows cast from Object[] to Array<Object>  */		 
		if(( (type.getType() instanceof ArrayType) && getType().equals(type.getType())  && !(getType() instanceof ArrayType) ))
			return TACCast.cast(node, type, this);		
		
		if( type.getType().isParameterized() && getType().getTypeWithoutTypeArguments().isStrictSubtype(type.getType()))
			return TACCast.cast(node, type, this);
		
		//if it got past the typechecker, we need to cast this type parameter into a real thing
		if( (getType() instanceof TypeParameter) && !(type.getType() instanceof TypeParameter)  )
			return TACCast.cast(node, type, this);
		
		return this;
	}
	
	/*
	 * Whether or not a value can propagate forward when doing constant and value propagation.
	 * By default, TACOperands cannot.
	 * A guideline: Anything whose result is stored in a temporary variable cannot.
	 */	
	public boolean canPropagate() {
		return false;
	}
	
	/*
	protected TACOperand checkVirtual(ModifiedType type, TACNode node, boolean allowDowncast)
	{
		if (getType().isStrictSubtype(type.getType()))
			return new TACCast(node, type, this);
		if( allowDowncast && type.getType().isStrictSubtype(getType()))
			return new TACCast(node, type, this);
		return this;
	}

	//does this sometimes need a toString thrown in?
	protected TACOperand checkVirtual(ModifiedType type, TACNode node)
	{
		return checkVirtual(type, node, false);
	}
	*/	
}
