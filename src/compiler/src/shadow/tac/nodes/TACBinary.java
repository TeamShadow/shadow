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
	private BinaryOperation operation;
	private TACOperand first, second;
	
	public TACBinary(TACNode node, TACOperand firstOperand, char op,
			TACOperand secondOperand)
	{
		this(node, firstOperand, new BinaryOperation(firstOperand, secondOperand, op), secondOperand);
	}	

	public TACBinary(TACNode node, TACOperand firstOperand, BinaryOperation op,
			TACOperand secondOperand)
	{
		super(node);
		
		operation = op;
		first = check(firstOperand, op.getFirst());
		second = check(secondOperand, op.getSecond());
		
		//only "pure" operators (not method-based ones) are allowed in TACBinary
		if( op.hasMethod() )
			throw new InternalError("Binary operator " + op + " is invalid on types " + op.getFirst().getType() + " and " + op.getSecond().getType());
	}
	
	public TACOperand getFirst()
	{
		return first;
	}
	public BinaryOperation getOperation()
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
		sb.append(getFirst()).append(' ').append(getOperation()).append(' ');
		paren = true;
		sb.append(getSecond());
		if (isParen)
			sb.append(')');
		paren = false;
		return sb.toString();
	}
}
