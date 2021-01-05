package shadow.interpreter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.ShadowException;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACConstantRef;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACLocalLoad;
import shadow.tac.nodes.TACLocalStore;
import shadow.tac.nodes.TACMethodName;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACUnary;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Type;
import shadow.interpreter.InterpreterException.Error;

/**
 * Interpreter that walks TAC nodes in order to determine the values for members marked constant.
 * At present, method calls for primitive types and strings are hardcoded, but a future version of the
 * interpreter should actually walk method definitions, failing only if it reaches unsupported native
 * methods.
 *
 * @deprecated Compile-time constant evaluation is being moved to {@link ASTInterpreter}. There may
 * be future cases where interpretation of TAC nodes is desired, so this code is being preserved.
 *
 * @author Barry Wittman
 * @author Jacob Young
 *
 */
public class TACInterpreter extends TACAbstractVisitor {
	private Map<String, ShadowValue> constants;
	private Map<String, ShadowValue> variables = new HashMap<String, ShadowValue>();

	public TACInterpreter(Map<String, ShadowValue> constants) {
		this.constants = constants;
	}

	@Override
	public void visit(TACLiteral node) throws ShadowException {
		node.setData(node.getValue());
	}

	public static ShadowValue evaluate(TACUnary node) throws ShadowException {
		ShadowValue data = null;
		ShadowValue op = value(TACOperand.value(node.getOperand()));

		switch(node.getOperation()) {
		case "-": data = op.negate(); break;
		case "#": data = new ShadowString(op.toString()); break;
		case "~": data = op.bitwiseComplement(); break;
		case "!": data = op.not(); break;
		}

		return data;
	}		

	@Override
	public void visit(TACUnary node) throws ShadowException {
		node.setData(evaluate(node));
	}

	public static ShadowValue evaluate(TACBinary node) throws ShadowException {
		ShadowValue data = null;
		ShadowValue left = value(TACOperand.value(node.getFirst())),
				right = value(TACOperand.value(node.getSecond()));

		switch( node.getOperation() ) {
		case "+":
		case "-":
		case "*":
		case "/":
		case "%":
		case "|":
		case "&":
		case "^":
		case "==":			
		case "===":
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
			switch( node.getOperation() ) {
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
			case "===":
				if( (left.getType().isPrimitive() && right.getType().isPrimitive()) ||
						( left instanceof ShadowNull && right instanceof ShadowNull ) )
					data = left.equal(right); 
				else
					throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Interpreter cannot perform reference comparison on non-primitive types");
				break;				
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
			data = left.bitShiftLeft(right); break;			
		case ">>":
			data = left.bitShiftRight(right); break;
		case "<<<":
			data = left.bitRotateLeft(right); break;
		case ">>>":
			data = left.bitRotateRight(right); break;			

		case "or":
			data = left.or(right); break;			
		case "xor":
			data = left.xor(right); break;
		case "and":
			data = left.and(right); break;
		case "#": //ever happens?
			data = new ShadowString(left.toString() + right.toString()); break;
		}

		return data;
	}

	@Override
	public void visit(TACBinary node) throws ShadowException {		
		node.setData(evaluate(node));
	}	

	@Override
	public void visit(TACLocalStore node) throws ShadowException {
		TACVariable variable = node.getVariable();
		TACOperand data = node.getValue();		
		variables.put(variable.getName(), value(data));				
	}


	@Override
	public void visit(TACStore node) throws ShadowException {
		/*
		TACReference reference = node.getReference();
		TACOperand data = node.getValue();		

		if( reference instanceof TACVariableRef ) {
			TACVariable variable = ((TACVariableRef)reference).getVariable();
			variables.put(variable.getName(), value(data));
		}
		else
		 */
		throw new UnsupportedOperationException();
	}


	@Override
	public void visit(TACLoad node) throws ShadowException {		
		TACReference reference = node.getReference();

		if( reference instanceof TACConstantRef ) {
			TACConstantRef constant = ((TACConstantRef)reference);	
			String name = constant.getPrefixType().toString() + ":" + constant.getName();
			ShadowValue data = constants.get(name);
			if( data == null )
				throw new InterpreterException(Error.CIRCULAR_REFERENCE, "Initialization dependencies prevent the value of constant " + name + " from being used here");
			node.setData(data);
		}
		//should never happen
		/*
		else if( reference instanceof TACVariableRef ) {
			TACVariable variable = ((TACVariableRef)reference).getVariable();
			node.setData(variables.get(variable.getName()));
		}
		 */
		else
			throw new UnsupportedOperationException();
	}

	@Override
	public void visit(TACLocalLoad node) throws ShadowException {		
		TACVariable variable = node.getVariable();
		node.setData(variables.get(variable.getName()));		
	}

	@Override
	public void visit(TACCall node) throws ShadowException {
		TACMethodRef methodRef = node.getMethodRef();

		if( methodRef instanceof TACMethodName ) {
			TACMethodName method = (TACMethodName) methodRef;
			MethodSignature signature = method.getSignature();
			List<TACOperand> parameters = node.getParameters();

			if( signature.isCreate() ) {

				//must be String
				if( !signature.getOuter().equals(Type.STRING) )
					throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Cannot call method " + signature);
				else {
					if( parameters.size() == 1 )
						node.setData(new ShadowString(""));
					else if( parameters.size() == 2 ) {
						ShadowValue value = value(parameters.get(1));
						if( value instanceof ShadowString ) 
							node.setData(value);
						else
							throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Cannot call method " + signature);
					}
					else
						throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Cannot call method " + signature);
				}
			}
			else {
				ShadowValue prefix = value(parameters.get(0));
				ShadowValue[] arguments = new ShadowValue[parameters.size() - 1];
				for( int i = 1; i < parameters.size(); i++ )			
					arguments[i - 1] = value(parameters.get(i)); //first argument is always the prefix

				
				
				
				ShadowValue data = prefix.callMethod(method.getName(), arguments);
				/*

				//don't know how to deal with any ShadowObject methods yet
				if( prefix instanceof ShadowObject )
					throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Cannot call method " + method.getName());

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
					if( arguments.length == 1 && prefix instanceof ShadowInteger ) {
						ShadowInteger integer = (ShadowInteger)prefix;
						ShadowInteger base = (ShadowInteger) arguments[0];
						data = new ShadowString(integer.toString(base.getValue().intValue()));
					}
					else if( arguments.length == 0 )
						data = new ShadowString(prefix.toString());
					break;

					//string functions
				case "concatenate":
				case "size":
				case "isEmpty":
				case "substring":
				case "toLowerCase":
				case "toUpperCase":
					if( prefix instanceof ShadowString ) {
						ShadowString string = (ShadowString) prefix;
						switch( method.getName()  ) {
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
							if( arguments.length == 1 ) {
								int start = ((ShadowInteger)arguments[0]).getValue().intValue();
								data = new ShadowString(string.getValue().substring(start));
							}
							else if( arguments.length == 2) {
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
				case "bitXor":	{
					ShadowValue first = prefix;
					ShadowValue second = arguments[0];

					if( first.isStrictSubtype(second))
						first = first.cast(second.getType());
					else if( second.isStrictSubtype(first))
						second = second.cast(first.getType());

					switch( method.getName()  ) {
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
				case "abs": data = ((ShadowNumeric)prefix).abs(); break;
				case "cos": data = ((ShadowNumeric)prefix).cos(); break;
				case "sin": data = ((ShadowNumeric)prefix).sin(); break;
				case "power": data = ((ShadowNumeric)prefix).power((ShadowNumber)arguments[0]); break;
				case "squareRoot": data = ((ShadowNumeric)prefix).squareRoot(); break;
				case "logBase10": data = ((ShadowNumeric)prefix).logBase10(); break;
				case "logBase2": data = ((ShadowNumeric)prefix).logBase2(); break;
				case "logBaseE": data = ((ShadowNumeric)prefix).logBaseE(); break;
				case "max": data = ((ShadowNumeric)prefix).max((ShadowNumber)arguments[0]); break;
				case "min": data = ((ShadowNumeric)prefix).min((ShadowNumber)arguments[0]); break;

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
				*/

				node.setData(data);
			}
		}
		else
			throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Cannot call method reference " + methodRef.toString());
	}

	@Override
	public void visit(TACNewObject node) throws ShadowException { 
		if( !node.getClassType().equals(Type.STRING)  )
			throw new InterpreterException(Error.INVALID_CREATE, "Cannot create non-String type " + node.getClassType());
	}


	private static ShadowValue value(TACOperand node) throws ShadowException {		
		Object value = node.getData();
		if (value instanceof ShadowValue)
			return (ShadowValue)value;
		throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Operand does not contain a value");
	}
}
