package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of binary operator
 * Example: x + y 
 * @author Jacob Young
 * @author Barry Wittman
 */

public class TACBinary extends TACOperand
{
	public static class Operation
	{		
		// CONCATENATION(Type.STRING, '#');
		
		public enum Comparison { NONE, LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN_OR_EQUAL };

		private MethodSignature method;
		private ModifiedType result;		
		private String string;
		private Comparison comparison;
		private ModifiedType first;
		private ModifiedType second;
		
		public Operation( ModifiedType firstType, ModifiedType secondType, char op )
		{
			this( firstType, secondType, op, false );
			
		}
				
		public Operation( ModifiedType firstType, ModifiedType secondType, char op, boolean noMethod )
		{
			
			if (firstType.getType() instanceof PropertyType)
				firstType = ((PropertyType)firstType.getType()).getGetType();
			if (secondType.getType() instanceof PropertyType)
				secondType = ((PropertyType)secondType.getType()).getGetType();
			
			first = firstType;
			second = secondType;
			
			String operation = "";
			string = String.valueOf(op);
			comparison = Comparison.NONE;
			switch( op )
			{
			case '+': operation = "add"; break;
			case '-': operation = "subtract"; break;
			case '*': operation = "multiply"; break;
			case '/': operation = "divide"; break;
			case '%': operation = "modulus"; break;

			case '|': operation = "bitOr"; break;
			case '^': operation = "bitXor"; break;
			case '&': operation = "bitAnd"; break;
			case 'l': operation = "bitShiftLeft"; string = "<<"; break;
			case 'r': operation = "bitShiftRight"; string = ">>";  break;
			case 'L': operation = "bitRotateLeft"; string = "<<<"; break;
			case 'R': operation = "bitRotateLeft"; string = ">>>"; break;

			case '=': operation = "equal"; string = "=="; break;
			case '!': operation = "equals"; string = "!="; break;
			
			case '<': operation = "compare"; comparison = Comparison.LESS_THAN; break;
			case '>': operation = "compare"; comparison = Comparison.GREATER_THAN; break;
			case '{': operation = "compare"; comparison = Comparison.LESS_THAN_OR_EQUAL; string = "<="; break;
			case '}': operation = "compare"; comparison = Comparison.GREATER_THAN_OR_EQUAL; string = ">="; break;
			
			//no methods for these
			case 'o': string = "or"; break;
			case 'x': string = "xor"; break;
			case 'a': string = "and"; break;
			
			default:
				throw new InternalError("Unknown operator: " + op);
			}
			
			if( operation.isEmpty() || operation.equals("compare") ) //boolean operations and comparisons
				result = new SimpleModifiedType(Type.BOOLEAN);
			else if( noMethod ) //direct computations for int values using +, -, *, /, and %
				result = new SimpleModifiedType(Type.INT);			
			else
			{
				Type methodClass = firstType.getType();
				method = methodClass.getMatchingMethod(operation, new SequenceType(secondType));
				result = method.getReturnTypes().get(0);
			}			
		}
					
		public boolean hasMethod()
		{
			return method != null;
		}
		
		public ModifiedType getResultType()
		{
			return result;
		}
		
		public ModifiedType getFirst()
		{
			return first;
		}
		
		public ModifiedType getSecond()
		{
			return second;
		}
		
		@Override
		public String toString()
		{
			return string;
		}
		
		public MethodSignature getMethod()
		{
			return method;
		}
		
		public Comparison getComparison()
		{
			return comparison;
		}
	}

	private Operation operation;
	private TACOperand first, second;

	/*
	public TACBinary(TACOperand firstOperand, Operation op,
			TACOperand secondOperand)
	{
		this(null, firstOperand, op, secondOperand);
	}
	*/
	public TACBinary(TACNode node, TACOperand firstOperand, char op,
			TACOperand secondOperand)
	{
		this(node, firstOperand, new Operation(firstOperand, secondOperand, op, true), secondOperand);
	}
	

	public TACBinary(TACNode node, TACOperand firstOperand, Operation op,
			TACOperand secondOperand)
	{
		super(node);

		/*
		firstType = op.getFirstType();
		secondType = op.getSecondType();
		*/
		/*	
		else //and, or, xor are the only ones without methods
		{
			firstType = secondType = this; //all booleans
		}
		*/
		
		operation = op;
		first = check(firstOperand, op.getFirst());
		second = check(secondOperand, op.getSecond());
		
		//only a few basic operations are supported in TACBinary
		//everything else is based on methods from operator overloading
		switch( operation.toString() )
		{
		case "+":
		case "-":
		case "*":
		case "/":
		case "%":
		case "<":
		case ">":
		case "<=":
		case ">=":
			if( !first.getType().equals(Type.INT) || !second.getType().equals(Type.INT) )
				throw new InternalError("Binary operator " + operation + " is invalid on types " + first.getType() + " and " + second.getType());
			break;
		case "and":
		case "or":
		case "xor":
			if( !first.getType().equals(Type.BOOLEAN) || !second.getType().equals(Type.BOOLEAN) )
				throw new InternalError("Binary operator " + operation + " is invalid on types " + first.getType() + " and " + second.getType());
			break;
		default:
			throw new InternalError("Binary operator " + operation + " is invalid");			
		}		
	}

	public TACOperand getFirst()
	{
		return first;
	}
	public Operation getOperation()
	{
		return operation;
	}	
	public TACOperand getSecond()
	{
		return second;
	}

	@Override
	public Type getType()
	{
		return operation.getResultType().getType();
	}
	@Override
	public int getNumOperands()
	{
		return 2;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return first;
		if (num == 1)
			return second;
		throw new IndexOutOfBoundsException();
	}
	
	

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	private static boolean paren = false;
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		boolean isParen = paren;
		if (isParen)
			sb.append('(');
		paren = true;
		sb.append(first).append(' ').append(operation).append(' ');
		paren = true;
		sb.append(second);
		if (isParen)
			sb.append(')');
		paren = false;
		return sb.toString();
	}
}
