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
 */

public class TACBinary extends TACOperand
{
	public static class Operation
	{		
		// CONCATENATION(Type.STRING, '#');

		private MethodSignature method;
		private ModifiedType result;		
		private String string;
		private boolean negate = false;
		private int direction = 0;
				
		public Operation( ModifiedType firstType, ModifiedType secondType, char op )
		{			
			String operation = "";
			string = String.valueOf(op);
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
			case '!': operation = "equals"; negate = true; string = "!="; break;
			
			case '<': operation = "compare"; direction = -1; break;
			case '>': operation = "compare"; direction = 1; break;
			case '{': operation = "compare"; direction = 1; negate = true; string = "<="; break;
			case '}': operation = "compare"; direction = -1; negate = true; string = ">="; break;
			
			//no methods for these
			case 'o': string = "or"; break;
			case 'x': string = "xor"; break;
			case 'a': string = "and"; break;
			
			default:
				throw new InternalError("Unknown operator: " + op);
			}
			
			if( operation.isEmpty() ) //boolean operations
				result = new SimpleModifiedType(Type.BOOLEAN);
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
		
		@Override
		public String toString()
		{
			return string;
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
		this(node, firstOperand, new Operation(firstOperand, secondOperand, op), secondOperand);
	}

	public TACBinary(TACNode node, TACOperand firstOperand, Operation op,
			TACOperand secondOperand)
	{
		super(node);
		ModifiedType firstType = firstOperand, secondType = secondOperand;
		if (firstType.getType() instanceof PropertyType)
			firstType = ((PropertyType)firstType.getType()).getGetType();
		if (secondType.getType() instanceof PropertyType)
			secondType = ((PropertyType)secondType.getType()).getGetType();
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
		first = check(firstOperand, firstType);
		second = check(secondOperand, secondType);
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
