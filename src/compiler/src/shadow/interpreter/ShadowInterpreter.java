package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACUnary;

public class ShadowInterpreter extends TACAbstractVisitor
{
	@Override
	public void visit(TACLiteral node) throws ShadowException
	{
		node.setData(node.getValue());
	}

	@Override
	public void visit(TACUnary node) throws ShadowException
	{
		ShadowValue op = value(node.getOperand());
		if (node.getOperation().equals("-"))
		{
			if (op instanceof ShadowFloat)
				node.setData( new ShadowFloat(-((ShadowFloat)op).getValue()));
			else if (op instanceof ShadowDouble)
				node.setData( new ShadowDouble(-((ShadowDouble)op).getValue()));
			else if ( op instanceof ShadowByte )
				node.setData( new ShadowByte((byte)-((ShadowByte)op).getValue()));
			else if ( op instanceof ShadowInt )
				node.setData( new ShadowInt(-((ShadowInt)op).getValue()));
			else if ( op instanceof ShadowLong )
				node.setData( new ShadowLong(-((ShadowLong)op).getValue()));
			else if ( op instanceof ShadowShort )
				node.setData( new ShadowShort((short)-((ShadowShort)op).getValue()));
		}
	}

	@Override
	public void visit(TACBinary node) throws ShadowException
	{
		ShadowValue left = value(node.getFirst()),
				right = value(node.getSecond());
		if (node.getOperation().equals("/"))
		{
			if (left instanceof ShadowFloat &&
					right instanceof ShadowFloat)
				node.setData( new ShadowFloat(((ShadowFloat)left).getValue() /
						((ShadowFloat)right).getValue()));
			else if (left instanceof ShadowDouble &&
					right instanceof ShadowDouble)
				node.setData( new ShadowDouble(((ShadowDouble)left).getValue() /
						((ShadowDouble)right).getValue()));
		}
	}

	private static ShadowValue value(TACOperand node)
	{
		Object value = node.getData();
		if (value instanceof ShadowValue)
			return (ShadowValue)value;
		throw new NullPointerException();
	}
}
