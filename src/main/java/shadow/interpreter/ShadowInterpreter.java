package shadow.interpreter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariableRef;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Type;
/**
 * Interpreter that walks TAC nodes in order to determine the values for variables marked constant.
 * At present, method calls for primitive types and strings are hardcoded, but a future version of the
 * interpreter should actually walk method definitions, failing only if it reaches unsupported native
 * methods.
 * 
 * @author Barry Wittman
 * @author Jacob Young
 *
 */
public class ShadowInterpreter extends TACAbstractVisitor
{
	
	Map<String, ShadowValue> variables = new HashMap<String, ShadowValue>();
	
	@Override
	public void visit(TACLiteral node) throws ShadowException
	{
		node.setData(node.getValue());
	}

	@Override
	public void visit(TACUnary node) throws ShadowException
	{
		ShadowValue data = null;
		ShadowValue op = value(node.getOperand());

		switch(node.getOperation())
		{
		case "-": data = op.negate(); break;
		case "#": data = new ShadowString(op.toString()); break;
		case "~": data = op.bitwiseComplement(); break;
		case "!": data = op.not(); break;
		}
		  
		node.setData(data);
	}

	@Override
	public void visit(TACBinary node) throws ShadowException
	{
		ShadowValue left = value(node.getFirst()),
				right = value(node.getSecond());
		
		ShadowValue data = null;
		
		switch( node.getOperation() )
		{
		case "+":
		case "-":
		case "*":
		case "/":
		case "%":
		case "|":
		case "&":
		case "^":
		case "==":			
		case "!=":			
		case "<":			
		case "<=":			
		case ">":			
		case ">=":
			if( left.isStrictSubtype(right) )
				left = left.cast(right.getType());
			else if( right.isStrictSubtype(left ))
				right = right.cast(left.getType());

			//sure, a bit ugly
			switch( node.getOperation() )
			{
			case "+":
				data = left.add(right); break;
			case "-":
				data = left.subtract(right); break;
			case "*":
				data = left.multiply(right); break;
			case "/":
				data = left.divide(right); break;
			case "%":
				data = left.modulus(right); break;
			case "|":
				data = left.bitwiseOr(right); break;
			case "&":
				data = left.bitwiseAnd(right); break;
			case "^":
				data = left.bitwiseXor(right); break;
			case "==":
				data = left.equal(right); break;
			case "!=":
				data = left.notEqual(right); break;
			case "<":
				data = left.lessThan(right); break;
			case "<=":
				data = left.lessThanOrEqual(right); break;
			case ">":
				data = left.greaterThan(right); break;
			case ">=":
				data = left.greaterThanOrEqual(right); break;
			}			
			break;
			
		case "<<":
			data = left.leftShift(right); break;			
		case ">>":
			data = left.rightShift(right); break;
		case "<<<":
			data = left.leftRotate(right); break;
		case ">>>":
			data = left.rightRotate(right); break;			
			
		case "or":
			data = left.or(right); break;			
		case "xor":
			data = left.xor(right); break;
		case "and":
			data = left.and(right); break;
		case "#": //ever happens?
			data = new ShadowString(left.toString() + right.toString()); break;
		}
		
		node.setData(data);
	}
	
	@Override
	public void visit(TACStore node) throws ShadowException
	{
		TACReference reference = node.getReference();
		TACOperand data = node.getValue();		
		
		if( reference instanceof TACVariableRef )
		{
			TACVariable variable = ((TACVariableRef)reference).getVariable();
			variables.put(variable.getName(), value(data));
		}
		else
			throw new UnsupportedOperationException();
	}
	
	@Override
	public void visit(TACLoad node) throws ShadowException
	{		
		TACReference reference = node.getReference();
		
		if( reference instanceof TACVariableRef )
		{
			TACVariable variable = ((TACVariableRef)reference).getVariable();
			node.setData(variables.get(variable.getName()));
		}
		else
			throw new UnsupportedOperationException();
	}
	
	@Override
	public void visit(TACCall node) throws ShadowException
	{
			TACMethodRef method = node.getMethod();
			MethodSignature signature = method.getSignature();
			List<TACOperand> parameters = node.getParameters();
			
			if( signature.isCreate() ) {
				
				//must be String
				if( !signature.getOuter().equals(Type.STRING) )
					throw new ShadowException("Cannot call method " + signature);
				else {
					if( parameters.size() == 1 )
						node.setData(new ShadowString(""));
					else if( parameters.size() == 2 ) {
						ShadowValue value = value(parameters.get(1));
						if( value instanceof ShadowString ) 
							node.setData(value);
						else
							throw new ShadowException("Cannot call method " + signature);
					}
					else
						throw new ShadowException("Cannot call method " + signature);
				}
			}
			else {
				ShadowValue prefix = value(method.getPrefix());
				ShadowValue[] arguments = new ShadowValue[parameters.size() - 1];
				for( int i = 1; i < parameters.size(); i++ )			
					arguments[i - 1] = value(parameters.get(i)); //first argument is always the prefix
				
				ShadowValue data = null;
				Type type = null;
				
				//don't know how to deal with any ShadowObject methods yet
				if( prefix instanceof ShadowObject )
					throw new ShadowException("Cannot call method " + method.getName());
				
				switch( method.getName() )
				{
				//universal
				case "equal": data = new ShadowBoolean(prefix.equals(arguments[0])); break;
				case "compare": data = new ShadowInteger(BigInteger.valueOf(prefix.compareTo(arguments[0])), 4, true); break;
				case "hash": data = prefix.hash(); break;
				
				//conversion
				case "toByte": type = Type.BYTE;
				case "toCode": type = Type.CODE;
				case "toDouble": type = Type.DOUBLE;
				case "toFloat": type = Type.FLOAT;
				case "toInt": type = Type.INT;
				case "toLong": type = Type.LONG;
				case "toShort": type = Type.SHORT;
				case "toUByte": type = Type.UBYTE;
				case "toUInt": type = Type.UINT;
				case "toULong": type = Type.ULONG;
				case "toUShort": type = Type.USHORT;
				
				if( prefix instanceof ShadowString )
					data = ((ShadowString)prefix).convert(type);
				else				
					data = prefix.cast(type);
				
				break;
				
				case "toString":
					if( arguments.length == 1 && prefix instanceof ShadowInteger )
					{
						ShadowInteger integer = (ShadowInteger)prefix;
						ShadowInteger base = (ShadowInteger) arguments[0];
						data = new ShadowString(integer.toString(base.getValue().intValue()));
					}
					else if( arguments.length == 0 )
					{
						data = new ShadowString(prefix.toString());					
					} 
					break;
					
				//string functions
				case "concatenate":
				case "size":
				case "isEmpty":
				case "substring":
				case "toLowerCase":
				case "toUpperCase":
					if( prefix instanceof ShadowString )
					{
						ShadowString string = (ShadowString) prefix;
						switch( method.getName()  )
						{
						case "concatenate":
							data = new ShadowString(string.getValue() + ((ShadowString)arguments[0]).getValue()); break;
						case "size":
							data = new ShadowInteger(BigInteger.valueOf(string.getValue().length()), 4, true); break;
						case "isEmpty":					
							data = new ShadowBoolean(string.getValue().length() == 0); break;						
						case "toLowerCase":
							data = new ShadowString(string.getValue().toLowerCase()); break;
						case "toUpperCase":
							data = new ShadowString(string.getValue().toUpperCase()); break;
						case "substring":
							if( arguments.length == 1 )
							{
								int start = ((ShadowInteger)arguments[0]).getValue().intValue();
								data = new ShadowString(string.getValue().substring(start));
							}
							else if( arguments.length == 2)
							{
								int start = ((ShadowInteger)arguments[0]).getValue().intValue();
								int end = ((ShadowInteger)arguments[1]).getValue().intValue();
								data = new ShadowString(string.getValue().substring(start, end));
							}					
						}					
					}
					break;
				
				//unary
				case "negate": data = prefix.negate(); break;
				case "not": data = prefix.not(); break;
				case "bitComplement": data = prefix.bitwiseComplement(); break;			
				
				//normal math operations
				case "add":
				case "subtract":				
				case "multiply":
				case "divide":
				case "modulus":
				case "bitOr":		
				case "bitAnd":
				case "bitXor":	
				{
					ShadowValue first = prefix;
					ShadowValue second = arguments[0];
					
					if( first.isStrictSubtype(second))
						first = first.cast(second.getType());
					else if( second.isStrictSubtype(first))
						second = second.cast(first.getType());
					
					switch( method.getName()  )
					{
					case "add":	data = first.add(second); break;					
					case "subtract": data = first.subtract(second); break;
					case "multiply": data = first.multiply(second); break;
					case "divide": data = first.divide(second); break;
					case "modulus": data = first.modulus(second); break;
					case "bitOr": data = first.bitwiseOr(second); break;		
					case "bitAnd": data = first.bitwiseAnd(second); break;
					case "bitXor": data = first.bitwiseXor(second); break;
					}
				}
				break;
				
				case "bitRotateLeft": data = prefix.leftRotate(arguments[0]); break;
				case "bitRotateRight": data = prefix.rightRotate(arguments[0]); break;
				case "bitShiftLeft": data = prefix.leftShift(arguments[0]); break;
				case "bitShiftRight": data = prefix.rightShift(arguments[0]); break;		
				
							
				case "toUnsigned": 
				case "abs": data = ((ShadowNumber)prefix).abs(); break;
				case "cos": data = ((ShadowNumber)prefix).cos(); break;
				case "sin": data = ((ShadowNumber)prefix).sin(); break;
				case "power": data = ((ShadowNumber)prefix).power((ShadowNumber)arguments[0]); break;
				case "squareRoot": data = ((ShadowNumber)prefix).squareRoot(); break;
				case "logBase10": data = ((ShadowNumber)prefix).logBase10(); break;
				case "logBase2": data = ((ShadowNumber)prefix).logBase2(); break;
				case "logBaseE": data = ((ShadowNumber)prefix).logBaseE(); break;
				case "max": data = ((ShadowNumber)prefix).max((ShadowNumber)arguments[0]); break;
				case "min": data = ((ShadowNumber)prefix).min((ShadowNumber)arguments[0]); break;
				
				case "floor":
					if( prefix instanceof ShadowDouble )
						data = ((ShadowDouble)prefix).floor();
					else if( prefix instanceof ShadowFloat )
						data = ((ShadowFloat)prefix).floor();
					break;
				case "ceiling":
					if( prefix instanceof ShadowDouble )
						data = ((ShadowDouble)prefix).ceiling();
					else if( prefix instanceof ShadowFloat )
						data = ((ShadowFloat)prefix).ceiling();
					break;
				case "round":
					if( prefix instanceof ShadowDouble )
						data = ((ShadowDouble)prefix).round();
					else if( prefix instanceof ShadowFloat )
						data = ((ShadowFloat)prefix).round();
					break;
					
				case "ones":
					if( prefix instanceof ShadowInteger )
						data = ((ShadowInteger)prefix).ones();
					break;
				case "trailingZeroes":
					if( prefix instanceof ShadowInteger )
						data = ((ShadowInteger)prefix).trailingZeroes();
					break;
				case "leadingZeroes":
					if( prefix instanceof ShadowInteger )
						data = ((ShadowInteger)prefix).leadingZeroes();
					break;
				case "flipEndian":
					if( prefix instanceof ShadowInteger )
						data = ((ShadowInteger)prefix).flipEndian();
					break;				
				}
				
				node.setData(data);
		}
	}
	
	@Override
	public void visit(TACNewObject node) throws ShadowException { 
		if( !node.getClassType().equals(Type.STRING)  )
			throw new ShadowException("Cannot create non-String type " + node.getClassType());
	}
	

	private static ShadowValue value(TACOperand node)
	{
		Object value = node.getData();
		if (value instanceof ShadowValue)
			return (ShadowValue)value;
		throw new NullPointerException();
	}
}
