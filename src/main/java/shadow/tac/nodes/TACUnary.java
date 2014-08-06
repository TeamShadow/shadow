package shadow.tac.nodes;

import shadow.parser.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of unary operator
 * Example: -x
 * @author Jacob Young
 */

public class TACUnary extends TACOperand
{
	//private UnaryOperation operation;
	private TACOperand operand;
	private ModifiedType result;
	private String operation;
	
	//only for !
	public TACUnary(TACNode node, String op, TACOperand operand)
	{
		this( node, op, operand, new SimpleModifiedType(Type.BOOLEAN), new SimpleModifiedType(Type.BOOLEAN));
	}

	public TACUnary(TACNode node, MethodSignature signature, String op,	TACOperand operand)
	{
		this( node, op, operand, new SimpleModifiedType(signature.getOuter()), signature.getReturnTypes().get(0) );
	}
	
	private TACUnary(TACNode node, String op, TACOperand operand, ModifiedType operandType, 
			ModifiedType resultType )
	{
		super(node);	
		
		if (operandType.getType() instanceof PropertyType)
			operandType = ((PropertyType)operandType.getType()).getGetType();
		
		operation = op;
		this.operand = check(operand, operandType);
		result = resultType;
	}

	public String getOperation()
	{
		return operation;
	}
	public TACOperand getOperand()
	{
		return operand;
	}

	@Override
	public Type getType()
	{
		return result.getType();
	}
	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return operand;
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "" + operation + operand;
	}
}
