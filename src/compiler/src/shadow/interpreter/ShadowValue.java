package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public abstract class ShadowValue implements ModifiedType
{
	private Modifiers modifiers;
	protected ShadowValue()
	{
		this.modifiers = new Modifiers();
	}
	protected ShadowValue(int modifiers)
	{
		this.modifiers = new Modifiers(modifiers);
	}
	@Override
	public final Modifiers getModifiers()
	{
		return modifiers;
	}
	@Override
	public final void setType(Type type)
	{
		throw new UnsupportedOperationException();
	}

	protected abstract ShadowValue cast(Type type) throws ShadowException;
	public abstract ShadowValue copy() throws ShadowException;
	public ShadowValue freeze() throws ShadowException
	{
		if (getModifiers().isImmutable())
			return this;
		ShadowValue copy = copy();
		copy.getModifiers().addModifier(Modifiers.IMMUTABLE);
		return copy;
	}
	@Override
	public String toString()
	{
		return getType().getQualifiedName();
	}

	public static final ShadowValue NULL = new ShadowValue(Modifiers.NULLABLE)
	{
		@Override
		public Type getType()
		{
			return Type.NULL;
		}
		@Override
		protected ShadowValue cast(Type type) throws ShadowException
		{
			return this;
		}
		@Override
		public ShadowValue copy() throws ShadowException
		{
			return this;
		}
	};
	public static ShadowValue getDefault(ModifiedType type)
			throws ShadowException
	{
		if (type.getModifiers().isNullable())
			return NULL;
		if (type instanceof ArrayType)
		{
			ArrayType arrayType = (ArrayType)type;
			return new ShadowArray(arrayType,
					new int[arrayType.getDimensions()]);
		}
		throw new ShadowException("Unsupported type " + type.getType());
	}
}
