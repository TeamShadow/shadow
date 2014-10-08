package shadow.tac.nodes;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public abstract class TACOperand extends TACSimpleNode implements ModifiedType
{
	private Object data;	
	
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
		if( getType().isSubtype(type.getType()) && (getType().isPrimitive() || type.getType().isPrimitive()) && getModifiers().isNullable() != type.getModifiers().isNullable() )
			return new TACCast(node, type, this);
				
		if (getType().isStrictSubtype(type.getType()))
			return new TACCast(node, type, this);
		
		if( type.getType().isParameterized() && getType().getTypeWithoutTypeArguments().isStrictSubtype(type.getType()))
			return new TACCast(node, type, this);
		
		//if it got past the typechecker, we need to cast this type parameter into a real thing
		if( (getType() instanceof TypeParameter) && !(type.getType() instanceof TypeParameter)  )
			return new TACCast(node, type, this);
		
		return this;
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
