package shadow.interpreter;

import java.math.BigInteger;

import com.sun.corba.se.impl.io.TypeMismatchException;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowInteger extends ShadowNumber {
		
	private final int size;  //in bytes
	private final boolean signed;
	private BigInteger value;
	private final BigInteger max;
	private final BigInteger min;
	
	public ShadowInteger(BigInteger value, int size, boolean signed)
	{
		super(Modifiers.IMMUTABLE);
		this.value = value;
		this.size = size;
		this.signed = signed;
		
		if( signed )
		{
			max = new BigInteger("2").pow(size * 8 - 1);
			min = max.negate();
		}
		else
		{
			max = new BigInteger("2").pow(size * 8);
			min = BigInteger.ZERO;
		}
		
		fixValue();
	}
	
	private void fixValue()
	{
		if( signed )
		{
			while( value.compareTo(max) >= 0 || value.compareTo(min) < 0 )
			{
				if( value.compareTo(max) >= 0 )
				{
					value = value.subtract(max);
					value = value.add(min);					
				}
				
				if( value.compareTo(min) < 0 )
				{
					value = value.subtract(min);
					value = value.add(max);				
				}
			}
		}
		else
		{
			value = value.mod(max);
		}
	}
	

	@Override
	public Type getType() {
		if( signed )
		{
			switch( size )
			{
			case 1: return Type.BYTE;
			case 2: return Type.SHORT;
			case 4: return Type.INT;
			case 8: return Type.LONG;
			}
		}
		else
		{
			switch( size )
			{
			case 1: return Type.UBYTE;
			case 2: return Type.USHORT;
			case 4: return Type.UINT;
			case 8: return Type.ULONG;
			}
		}

		return null;
	}

	public BigInteger getValue()
	{
		return value;
	}
	
	@Override
	public ShadowInteger negate() throws ShadowException
	{	
		if( signed )
			return new ShadowInteger(value.negate(), size, signed);
		
		throw new UnsupportedOperationException("Unsigned values cannot be negated");
	}
	
	@Override
	public ShadowInteger bitwiseComplement() throws ShadowException
	{	
		return new ShadowInteger(value.negate().subtract(BigInteger.ONE), size, signed);
	}
	

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		if (type.equals(Type.BYTE))
			return new ShadowInteger(value, 1, true);
		if (type.equals(Type.SHORT))
			return new ShadowInteger(value, 2, true);
		if (type.equals(Type.INT))
			return new ShadowInteger(value, 4, true);
		if (type.equals(Type.LONG))
			return new ShadowInteger(value, 8, true);
		if (type.equals(Type.UBYTE))
			return new ShadowInteger(value, 1, false);
		if (type.equals(Type.USHORT))
			return new ShadowInteger(value, 2, false);
		if (type.equals(Type.UINT))
			return new ShadowInteger(value, 4, false);
		if (type.equals(Type.ULONG))
			return new ShadowInteger(value, 8, false);
		if (type.equals(Type.DOUBLE))
			return new ShadowDouble( value.doubleValue() );
		if (type.equals(Type.FLOAT ))
			return new ShadowFloat( value.floatValue() );
		throw new UnsupportedOperationException("Cannot cast " + getType() + " to " + type);
	}
	
	public ShadowInteger add(ShadowValue other) throws ShadowException
	{	
		if( getType().equals(other.getType()) )
		{
			ShadowInteger input = (ShadowInteger)other;			
			return new ShadowInteger( value.add(input.value), size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());		
	}
	
	public ShadowInteger subtract(ShadowValue other) throws ShadowException
	{	
		if( getType().equals(other.getType()) )
		{
			ShadowInteger input = (ShadowInteger)other;			
			return new ShadowInteger( value.subtract(input.value), size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowInteger multiply(ShadowValue other) throws ShadowException
	{	
		if( getType().equals(other.getType()) )
		{
			ShadowInteger input = (ShadowInteger)other;			
			return new ShadowInteger( value.multiply(input.value), size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowInteger divide(ShadowValue other) throws ShadowException
	{	
		if( getType().equals(other.getType()) )
		{
			ShadowInteger input = (ShadowInteger)other;			
			return new ShadowInteger( value.divide(input.value), size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowInteger modulus(ShadowValue other) throws ShadowException
	{	
		if( getType().equals(other.getType()) )
		{
			ShadowInteger input = (ShadowInteger)other;			
			return new ShadowInteger( value.mod(input.value), size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowInteger leftShift(ShadowValue other) throws ShadowException
	{	
		if( other instanceof ShadowInteger )
		{
			ShadowInteger input = (ShadowInteger)other;			
			return new ShadowInteger( value.shiftLeft(input.value.mod(new BigInteger("64")).intValue()), size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowInteger rightShift(ShadowValue other) throws ShadowException
	{	
		if( other instanceof ShadowInteger )
		{
			ShadowInteger input = (ShadowInteger)other;			
			return new ShadowInteger( value.shiftRight(input.value.mod(new BigInteger("64")).intValue()), size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowInteger leftRotate(ShadowValue other) throws ShadowException
	{	
		if( other instanceof ShadowInteger )
		{
			ShadowInteger input = (ShadowInteger)other;
			int shift = input.value.mod(new BigInteger("64")).intValue();
			BigInteger result = value.shiftLeft(shift);
			
			if( signed )
				result.mod(max.multiply(new BigInteger("2")));
			
			result = result.or(value.shiftRight(8 * size - shift));
			
			return new ShadowInteger( result, size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
		
	}
	
	public ShadowInteger rightRotate(ShadowValue other) throws ShadowException
	{	
		if( other instanceof ShadowInteger )
		{
			ShadowInteger input = (ShadowInteger)other;
			int shift = input.value.mod(new BigInteger("64")).intValue();
			BigInteger result = value.shiftLeft(8 * size - shift);
			
			if( signed )
				result.mod(max.multiply(new BigInteger("2")));
			
			result = result.or(value.shiftRight(shift));
			
			return new ShadowInteger( result, size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowBoolean equal(ShadowValue other) throws ShadowException
	{	
		if( other instanceof ShadowInteger )
		{
			ShadowInteger input = (ShadowInteger) other;			
			return new ShadowBoolean(value.equals(input.value));
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowBoolean lessThan(ShadowValue other) throws ShadowException
	{	
		if( other instanceof ShadowInteger )
		{
			ShadowInteger input = (ShadowInteger) other;			
			return new ShadowBoolean(value.compareTo(input.value) < 0);
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowBoolean lessThanOrEqual(ShadowValue other) throws ShadowException
	{	
		if( other instanceof ShadowInteger )
		{
			ShadowInteger input = (ShadowInteger) other;			
			return new ShadowBoolean(value.compareTo(input.value) <= 0);
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowBoolean greaterThan(ShadowValue other) throws ShadowException
	{	
		if( other instanceof ShadowInteger )
		{
			ShadowInteger input = (ShadowInteger) other;			
			return new ShadowBoolean(value.compareTo(input.value) > 0);
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowBoolean greaterThanOrEqual(ShadowValue other) throws ShadowException
	{	
		if( other instanceof ShadowInteger )
		{
			ShadowInteger input = (ShadowInteger) other;			
			return new ShadowBoolean(value.compareTo(input.value) >= 0);
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowInteger bitwiseAnd(ShadowValue other) throws ShadowException
	{
		if( getType().equals(other.getType()) )
		{
			ShadowInteger input = (ShadowInteger)other;			
			return new ShadowInteger( value.and(input.value), size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowInteger bitwiseOr(ShadowValue other) throws ShadowException
	{
		if( getType().equals(other.getType()) )
		{
			ShadowInteger input = (ShadowInteger)other;			
			return new ShadowInteger( value.or(input.value), size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}
	
	public ShadowInteger bitwiseXor(ShadowValue other) throws ShadowException
	{
		if( getType().equals(other.getType()) )
		{
			ShadowInteger input = (ShadowInteger)other;			
			return new ShadowInteger( value.xor(input.value), size, signed);			
		}
		
		throw new TypeMismatchException("Type " + getType() + " does not match " + other.getType());
	}	
	
	
	@Override
	public ShadowInteger copy() throws ShadowException
	{
		return new ShadowInteger(value, size, signed);
	}
	
	public ShadowInteger hash() throws ShadowException
	{
		if( size < 8 )
			return (ShadowInteger)cast(Type.UINT);
		
		ShadowInteger first = (ShadowInteger)cast(Type.UINT);
		ShadowInteger second = (ShadowInteger)rightShift(new ShadowInteger(BigInteger.valueOf(32), 4, false)).cast(Type.UINT);			
		
		return first.bitwiseXor(second);		
	}
	
	
	@Override
	public String toString()
	{
		return value.toString();
	}
	
	public String toString(int base)
	{
		return value.toString(base);
	}
	
	@Override
	public ShadowInteger abs()
	{
		return new ShadowInteger(value.abs(), size, false);		
	}

	@Override
	public ShadowDouble cos() throws ShadowException {
		return new ShadowDouble(Math.cos(value.doubleValue()));
	}
	@Override
	public ShadowDouble sin() throws ShadowException {
		return new ShadowDouble(Math.sin(value.doubleValue()));
	}
	@Override
	public ShadowDouble power(ShadowNumber number) throws ShadowException {
		double exponent = ((ShadowDouble)number.cast(Type.DOUBLE)).getValue();		
		return new ShadowDouble(Math.pow(value.doubleValue(), exponent));
	}
	@Override
	public ShadowDouble squareRoot() throws ShadowException {
		return new ShadowDouble(Math.sqrt(value.doubleValue()));
	}
	@Override
	public ShadowDouble logBase10() throws ShadowException {
		return new ShadowDouble(Math.log10(value.doubleValue()));
	}
	@Override
	public ShadowDouble logBase2() throws ShadowException {
		return new ShadowDouble(Math.log(value.doubleValue())/Math.log(2.0));
	}
	@Override
	public ShadowDouble logBaseE() throws ShadowException {
		return new ShadowDouble(Math.log(value.doubleValue()));
	}
	@Override
	public ShadowInteger max(ShadowNumber number) throws ShadowException {
		ShadowInteger other = (ShadowInteger)number;			
		return new ShadowInteger(value.max(other.value), Math.max(size, other.size), signed || other.signed);
	}
	@Override
	public ShadowNumber min(ShadowNumber number) throws ShadowException {
		ShadowInteger other = (ShadowInteger)number;			
		return new ShadowInteger(value.min(other.value), Math.max(size, other.size), signed || other.signed);

	}
	
	public ShadowInteger ones() throws ShadowException
	{
		int count = 0;
		if( value.compareTo(BigInteger.ZERO) < 0 )
			count = value.bitLength() + 1 + (value.bitLength() - value.bitCount());
		else
			count = value.bitCount();
		
		return new ShadowInteger( BigInteger.valueOf(count), size, signed);		
	}
	
	public ShadowInteger trailingZeroes() throws ShadowException
	{
		int count = 0;
		if( value.compareTo(BigInteger.ZERO) == 0 )
			count = 8 * size;
		else
		{
			for( int i = 0; i < value.bitLength() && !value.testBit(i); i++ )
				count++;
		}
		
		return new ShadowInteger( BigInteger.valueOf(count), size, signed);		
	}
	
	public ShadowInteger leadingZeroes() throws ShadowException
	{
		int count = 0;
		 if( value.compareTo(BigInteger.ZERO) < 0 )
			count = 0;
		else
			count = 8 * size - value.bitLength();		
		
		return new ShadowInteger( BigInteger.valueOf(count), size, signed);				
		
	}

	public ShadowInteger flipEndian() throws ShadowException
	{
		BigInteger result = BigInteger.ZERO;
		BigInteger mask = BigInteger.valueOf(0xFF);
		
		for( int i = 0; i < size; i++ )
			result = result.or(value.shiftRight(8*(size - i - 1)).and(mask).shiftLeft(i));
		
		
		return new ShadowInteger( result, size, signed);
	}
	

}
