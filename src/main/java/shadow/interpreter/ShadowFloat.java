package shadow.interpreter;

import java.math.BigInteger;

import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowTypeMismatchException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowFloat extends ShadowNumber
{
	private float value;
	public ShadowFloat(float value)
	{
		super(Modifiers.IMMUTABLE);
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.FLOAT;
	}
	public float getValue()
	{
		return value;
	}

	@Override
	public ShadowFloat negate() throws ShadowException
	{
		return new ShadowFloat(-value);
	}

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		BigInteger integer = BigInteger.valueOf(Math.round((double)value));

		if (type.equals(Type.BYTE))
			return new ShadowInteger(integer, 1, true);
		if (type.equals(Type.SHORT))
			return new ShadowInteger(integer, 2, true);
		if (type.equals(Type.INT))
			return new ShadowInteger(integer, 4, true);
		if (type.equals(Type.LONG))
			return new ShadowInteger(integer, 8, true);
		if (type.equals(Type.UBYTE))
			return new ShadowInteger(integer, 1, false);
		if (type.equals(Type.USHORT))
			return new ShadowInteger(integer, 2, false);
		if (type.equals(Type.UINT))
			return new ShadowInteger(integer, 4, false);
		if (type.equals(Type.ULONG))
			return new ShadowInteger(integer, 8, false);
		if (type.equals(Type.FLOAT))
			return new ShadowFloat(getValue());
		if (type.equals(Type.DOUBLE))
			return new ShadowDouble(getValue());

		throw new UnsupportedOperationException("Cannot cast " + getType() + " to " + type);
	}

	@Override
	public ShadowFloat add(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowFloat )
		{
			ShadowFloat input = (ShadowFloat) other;
			return new ShadowFloat(value + input.value);
		}

		throw new ShadowTypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}

	@Override
	public ShadowFloat subtract(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowFloat )
		{
			ShadowFloat input = (ShadowFloat) other;
			return new ShadowFloat(value - input.value);
		}

		throw new ShadowTypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}

	@Override
	public ShadowFloat multiply(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowFloat )
		{
			ShadowFloat input = (ShadowFloat) other;
			return new ShadowFloat(value * input.value);
		}

		throw new ShadowTypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}

	@Override
	public ShadowFloat divide(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowFloat )
		{
			ShadowFloat input = (ShadowFloat) other;
			return new ShadowFloat(value / input.value);
		}

		throw new ShadowTypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}

	@Override
	public ShadowFloat modulus(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowFloat )
		{
			ShadowFloat input = (ShadowFloat) other;
			return new ShadowFloat(value % input.value);
		}

		throw new ShadowTypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}

	@Override
	public ShadowBoolean equal(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowFloat )
		{
			ShadowFloat input = (ShadowFloat) other;
			return new ShadowBoolean(value == input.value);
		}

		throw new ShadowTypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}

	@Override
	public ShadowBoolean lessThan(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowFloat )
		{
			ShadowFloat input = (ShadowFloat) other;
			return new ShadowBoolean(value <= input.value);
		}

		throw new ShadowTypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}

	@Override
	public ShadowBoolean lessThanOrEqual(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowFloat )
		{
			ShadowFloat input = (ShadowFloat) other;
			return new ShadowBoolean(value <= input.value);
		}

		throw new ShadowTypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}

	@Override
	public ShadowBoolean greaterThan(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowFloat )
		{
			ShadowFloat input = (ShadowFloat) other;
			return new ShadowBoolean(value > input.value);
		}

		throw new ShadowTypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}

	@Override
	public ShadowBoolean greaterThanOrEqual(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowFloat )
		{
			ShadowFloat input = (ShadowFloat) other;
			return new ShadowBoolean(value >= input.value);
		}

		throw new ShadowTypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}

	@Override
	public ShadowInteger hash() throws ShadowException
	{
		return new ShadowInteger(BigInteger.valueOf(Float.floatToIntBits(getValue())), 4, false);
	}


	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowFloat(getValue());
	}
	@Override
	public String toString()
	{
		return Float.toString(getValue());
	}

	@Override
	public ShadowFloat abs()
	{
		return new ShadowFloat(Math.abs(value));
	}

	@Override
	public ShadowFloat cos() throws ShadowException {
		return new ShadowFloat((float)Math.cos(value));
	}
	@Override
	public ShadowFloat sin() throws ShadowException {
		return new ShadowFloat((float)Math.sin(value));
	}
	@Override
	public ShadowFloat power(ShadowNumber number) throws ShadowException {
		double exponent = ((ShadowDouble)number.cast(Type.DOUBLE)).getValue();
		return new ShadowFloat((float)Math.pow(value, exponent));
	}
	@Override
	public ShadowFloat squareRoot() throws ShadowException {
		return new ShadowFloat((float)Math.sqrt(value));
	}
	@Override
	public ShadowFloat logBase10() throws ShadowException {
		return new ShadowFloat((float)Math.log10(value));
	}
	@Override
	public ShadowFloat logBase2() throws ShadowException {
		return new ShadowFloat((float)(Math.log(value)/Math.log(2.0)));
	}
	@Override
	public ShadowFloat logBaseE() throws ShadowException {
		return new ShadowFloat((float)Math.log(value));
	}
	@Override
	public ShadowFloat max(ShadowNumber number) throws ShadowException {
		float other = ((ShadowFloat)number.cast(Type.FLOAT)).getValue();
		return new ShadowFloat(Math.max(value, other));
	}
	@Override
	public ShadowFloat min(ShadowNumber number) throws ShadowException {
		float other = ((ShadowFloat)number.cast(Type.FLOAT)).getValue();
		return new ShadowFloat(Math.min(value, other));
	}

	public ShadowFloat floor() throws ShadowException
	{
		return new ShadowFloat((float)Math.floor(value));
	}

	public ShadowFloat ceiling() throws ShadowException
	{
		return new ShadowFloat((float)Math.ceil(value));
	}

	public ShadowFloat round() throws ShadowException
	{
		return new ShadowFloat(Math.round(value));
	}
	
	public static ShadowFloat parseFloat(String string)
	{		
		return new ShadowFloat(Float.parseFloat(string));
	}
}
