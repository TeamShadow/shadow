package shadow.output;

import shadow.ShadowException;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACOperand;

public class Cleanup extends AbstractOutput
{
	private static Cleanup instance;
	private Cleanup()
	{
		super((TabbedLineWriter)null);
	}
	public synchronized static Cleanup getInstance()
	{
		if (instance == null)
			instance = new Cleanup();
		return instance;
	}

	@Override
	protected void visit(TACNode node) throws ShadowException
	{
		if (node instanceof TACOperand)
			((TACOperand)node).setData(null);
		for (TACOperand operand : node)
			operand.setData(null);
	}
}
