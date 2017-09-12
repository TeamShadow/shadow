package shadow.interpreter;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shadow.ShadowException;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACMethodName;
import shadow.tac.nodes.TACOperand;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Type;

public class ShadowString extends ShadowValue
{
	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	private String value;
	public ShadowString(String value)
	{
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.STRING;
	}
	public String getValue()
	{
		return this.value;
	}
	
	@Override
	public ShadowValue cast(Type type) throws ShadowException
	{
		if( !type.equals(Type.STRING) )
			throw new UnsupportedOperationException("Cannot convert type " + Type.STRING + " to " + type);
		
		return this;
	}
	
	public ShadowValue convert(Type type) throws ShadowException
	{
		if( type.equals(Type.STRING))
			return this;
		
		if( type.equals(Type.BYTE) )
			return new ShadowInteger(new BigInteger(value), 1, true);
		else if( type.equals(Type.SHORT) )
			return new ShadowInteger(new BigInteger(value), 2, true);
		else if( type.equals(Type.INT) )
			return new ShadowInteger(new BigInteger(value), 4, true);
		else if( type.equals(Type.LONG) )
			return new ShadowInteger(new BigInteger(value), 8, true);		
		else if( type.equals(Type.UBYTE) )
			return new ShadowInteger(new BigInteger(value), 1, false);
		else if( type.equals(Type.USHORT) )
			return new ShadowInteger(new BigInteger(value), 2, false);
		else if( type.equals(Type.UINT) )
			return new ShadowInteger(new BigInteger(value), 4, false);
		else if( type.equals(Type.ULONG) )
			return new ShadowInteger(new BigInteger(value), 8, false);
		else if( type.equals(Type.DOUBLE) )
			return new ShadowDouble( Double.parseDouble(value));
		else if( type.equals(Type.FLOAT) )
			return new ShadowFloat( Float.parseFloat(value));
		
		throw new UnsupportedOperationException("Cannot convert type " + Type.STRING + " to " + type);		
	}
	
	
	
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowString(getValue());
	}
	
	@Override
	public ShadowInteger hash() throws ShadowException
	{		
		int code = 0;		
		byte[] data = value.getBytes(StandardCharsets.UTF_8);
		
		for( int i = 0; i < data.length; i += 1 )
		{
			code *= 31;
			code += Math.abs(data[i]);
		}
		
		return new ShadowInteger( BigInteger.valueOf(code), 4, false);		
	}
	
	@Override
	public String toString()
	{
		return "\"" + getValue() + "\"";
	}
	
	public static ShadowString parseString(String string)
	{
		StringBuilder builder = new StringBuilder(
				string.substring(1, string.length() - 1));
		int index = 0; while ((index = builder.indexOf("\\", index)) != -1)
		{
			switch (builder.charAt(index + 1))
			{
				case 'b':
					builder.replace(index, index + 2, "\b");
					break;
				case 't':
					builder.replace(index, index + 2, "\t");
					break;
				case 'n':
					builder.replace(index, index + 2, "\n");
					break;
				case 'f':
					builder.replace(index, index + 2, "\f");
					break;
				case 'r':
					builder.replace(index, index + 2, "\r");
					break;
				case '\"':
					builder.replace(index, index + 2, "\"");
					break;
				case '\'':
					builder.replace(index, index + 2, "\'");
					break;
				case '\\':
					builder.replace(index, index + 2, "\\");
				case 'u':
					//add in high surrogates and full 32-bit values at some point
					char code = (char)Integer.parseInt(builder.substring(index + 2, index + 6), 16);
					builder.replace(index, index + 6, String.valueOf(code));
					break;
				default:
					throw new IllegalArgumentException("Unknown escape sequence \\" + builder.charAt(index + 1));
			}
			index++;
		}
		return new ShadowString(builder.toString());
	}
	
	public static boolean isSupportedMethod(MethodSignature signature) {
		if( signature.getOuter().equals(Type.STRING) ) {
			switch( signature.getSymbol() ) {
			case "index":
			case "size":
			case "isEmpty":
			case "substring":			
			case "toLowerCase":
			case "toUpperCase":
			case "equal":
			case "compare":
			case "toString": 
			case "hash":
			case "toByte": 
			case "toUByte":
			case "toShort":
			case "toUShort":
			case "toInt":
			case "toUInt":
			case "toLong":
			case "toULong":
			case "toFloat":
			case "toDouble":
				return true;
			
			case "concatenate":
				return signature.getParameterTypes().get(0).getType().equals(Type.STRING);
			}
		}
		
		return false;
	}
	
	public ShadowValue callMethod(TACCall call) throws ShadowException {
		MethodSignature signature = ((TACMethodName)call.getMethodRef()).getSignature();
		
		try
		{		
			List<TACOperand> operands = call.getParameters();
			List<ShadowValue> parameters = new ArrayList<ShadowValue>(operands.size() - 1);
			
			for( int i = 1; i < operands.size(); ++i  ) {
				TACOperand operand = TACOperand.value(operands.get(i));
				TACLiteral literal = (TACLiteral)operand;
				parameters.add(literal.getValue());
			}									
			
			switch( signature.getSymbol() ) {
			case "index":
			{
				int index = ((ShadowInteger)parameters.get(0)).getValue().intValue();
				return new ShadowInteger(BigInteger.valueOf(value.getBytes(UTF8)[index]), 1, true);
			}
			case "size": return new ShadowInteger(value.length()); 
			case "isEmpty": return new ShadowBoolean(value.isEmpty());
			case "substring":
			{
				byte[] bytes = value.getBytes(UTF8);
				int firstIndex = ((ShadowInteger)parameters.get(0)).getValue().intValue();
				int secondIndex = bytes.length;
				if( parameters.size() == 2 ) 
					secondIndex = ((ShadowInteger)parameters.get(1)).getValue().intValue();
				bytes = Arrays.copyOfRange(bytes, firstIndex, secondIndex);
				return new ShadowString(new String(bytes, UTF8));			
			}			
			case "toLowerCase":	return new ShadowString(value.toLowerCase());
			case "toUpperCase": return new ShadowString(value.toUpperCase());
			case "toString":  return this;
			case "hash": return hash();
			case "toByte": return new ShadowInteger(new BigInteger(value), 1, true);
			case "toUByte": return new ShadowInteger(new BigInteger(value), 1, false);
			case "toShort": return new ShadowInteger(new BigInteger(value), 2, true);
			case "toUShort": return new ShadowInteger(new BigInteger(value), 2, false);
			case "toInt": return new ShadowInteger(new BigInteger(value), 4, true);
			case "toUInt": return new ShadowInteger(new BigInteger(value), 4, false);
			case "toLong": return new ShadowInteger(new BigInteger(value), 8, true);
			case "toULong": return new ShadowInteger(new BigInteger(value), 8, false);
			case "toFloat": return new ShadowFloat(Float.parseFloat(value));
			case "toDouble": return new ShadowDouble(Double.parseDouble(value));
			case "concatenate": return new ShadowString(value + ((ShadowString)parameters.get(0)).value);
			}
		}
		//anything that is a problem should bring is down here to throw the ShadowException
		catch(Exception e)
		{}
		
		throw new InterpreterException("Evaluation of string method " + signature.getSymbol() + signature.getMethodType() + " failed");
	}	
}
