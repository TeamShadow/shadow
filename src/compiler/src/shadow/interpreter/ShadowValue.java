package shadow.interpreter;

import java.math.BigInteger;

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
	
	public ShadowValue negate() throws ShadowException
	{	
		throw new UnsupportedOperationException("Negate operation not supported");
	}
	
	public ShadowValue bitwiseComplement() throws ShadowException
	{	
		throw new UnsupportedOperationException("Bitwise complement operation not supported");
	}
	
	public ShadowBoolean not() throws ShadowException
	{	
		throw new UnsupportedOperationException("Not operation not supported");
	}
	
	public ShadowValue cat(ShadowValue value) throws ShadowException
	{	
		return new ShadowString(toString() + value.toString());
	}
	
	//binary operations
	public ShadowValue add(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Add operation not supported");
	}
	
	public ShadowValue subtract(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Subtract operation not supported");
	}
	
	public ShadowValue multiply(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Multiply operation not supported");
	}
	
	public ShadowValue divide(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Divide operation not supported");
	}
	
	public ShadowValue modulus(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Modulus operation not supported");
	}
	
	public ShadowValue leftShift(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Left shift operation not supported");
	}
	
	public ShadowValue rightShift(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Right shift operation not supported");
	}
	
	public ShadowValue leftRotate(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Left rotate operation not supported");
	}
	
	public ShadowValue rightRotate(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Right rotate operation not supported");
	}
	
	public ShadowBoolean equal(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Equal operation not supported");
	}
	
	public final ShadowBoolean notEqual(ShadowValue value) throws ShadowException
	{	
		try
		{		
			ShadowBoolean result = equal(value);
			return new ShadowBoolean(!result.getValue());
		}
		catch(Exception e)
		{
			throw new UnsupportedOperationException("Not equal operation not supported");
		}		
	}
	
	public ShadowBoolean lessThan(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Less than operation not supported");	
	}
	
	public ShadowBoolean lessThanOrEqual(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Less than or equal operation not supported");
	}
	
	public ShadowBoolean greaterThan(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Greater than operation not supported");	
	}
	
	public ShadowBoolean greaterThanOrEqual(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Greater than or equal operation not supported");
	}
	
	public ShadowBoolean or(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Or operation not supported");
	}
	
	public ShadowBoolean xor(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("Exclusive or operation not supported");
	}
	
	public ShadowBoolean and(ShadowValue value) throws ShadowException
	{	
		throw new UnsupportedOperationException("And operation not supported");
	}
	
	public ShadowValue bitwiseAnd(ShadowValue value) throws ShadowException
	{
		throw new UnsupportedOperationException("Bitwise and operation not supported");
	}
	
	public ShadowValue bitwiseOr(ShadowValue value) throws ShadowException
	{
		throw new UnsupportedOperationException("Bitwise or operation not supported");
	}
	
	public ShadowValue bitwiseXor(ShadowValue value) throws ShadowException
	{
		throw new UnsupportedOperationException("Bitwise xor operation not supported");
	}	
	
	public final boolean isSubtype(ShadowValue other)
	{
		return getType().isSubtype(other.getType());
	}
	
	public final boolean isStrictSubtype(ShadowValue other)
	{
		return getType().isStrictSubtype(other.getType());
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
	
	public ShadowInteger hash()  throws ShadowException {throw new UnsupportedOperationException("Hash not supported"); }
	
	public boolean equals(ShadowValue second) throws ShadowException
	{
		ShadowValue first = this;
		
		if( first.isStrictSubtype(second) )
			first = first.cast(second.getType());
		else if( second.isStrictSubtype(first))
			second = second.cast(first.getType());
		
		if( first.getType().equals(second.getType()) )
		{
			if( first instanceof ShadowInteger )
			{
				BigInteger value1 = ((ShadowInteger)first).getValue();
				BigInteger value2 = ((ShadowInteger)second).getValue();
				return value1.equals(value2);
			}
			else if( first instanceof ShadowFloat )
			{
				float value1 = ((ShadowFloat)first).getValue();
				float value2 = ((ShadowFloat)second).getValue();
				return value1 == value2;
			}
			else if( first instanceof ShadowDouble )
			{
				double value1 = ((ShadowDouble)first).getValue();
				double value2 = ((ShadowDouble)second).getValue();
				return value1 == value2;
			}
			else if( first instanceof ShadowString )
			{
				String value1 = ((ShadowString)first).getValue();
				String value2 = ((ShadowString)second).getValue();
				return value1.equals(value2);				
			}
		}
		
		return false;
	}
	
	public int compareTo(ShadowValue second) throws ShadowException
	{
		ShadowValue first = this;
		
		if( first.isStrictSubtype(second) )
			first = first.cast(second.getType());
		else if( second.isStrictSubtype(first))
			second = second.cast(first.getType());
		
		if( first.getType().equals(second.getType()) )
		{
			if( first instanceof ShadowInteger )
			{
				BigInteger value1 = ((ShadowInteger)first).getValue();
				BigInteger value2 = ((ShadowInteger)second).getValue();
				return value1.compareTo(value2);
			}
			else if( first instanceof ShadowFloat )
			{
				float value1 = ((ShadowFloat)first).getValue();
				float value2 = ((ShadowFloat)second).getValue();
				return Float.compare(value1, value2);
			}
			else if( first instanceof ShadowDouble )
			{
				double value1 = ((ShadowDouble)first).getValue();
				double value2 = ((ShadowDouble)second).getValue();
				return Double.compare(value1,value2);
			}
			else if( first instanceof ShadowString )
			{
				String value1 = ((ShadowString)first).getValue();
				String value2 = ((ShadowString)second).getValue();
				return value1.compareTo(value2);				
			}
		}
		
		throw new UnsupportedOperationException("Cannot compare types " + first.getType() + " and " + second.getType());
	}
}
