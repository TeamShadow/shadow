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

public class TACBinary extends TACBinaryMethod
{		
	public TACBinary(TACNode node, TACOperand firstOperand, char op,
			TACOperand secondOperand)
	{
		this(node, firstOperand, new BinaryOperation(firstOperand, secondOperand, op), secondOperand);
	}	

	public TACBinary(TACNode node, TACOperand firstOperand, BinaryOperation op,
			TACOperand secondOperand)
	{
		super(node, firstOperand, op, secondOperand);
		
		//only "pure" operators (not method-based ones) are allowed in TACBinary
		if( op.hasMethod() )
			throw new InternalError("Binary operator " + op + " is invalid on types " + op.getFirst().getType() + " and " + op.getSecond().getType());
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
