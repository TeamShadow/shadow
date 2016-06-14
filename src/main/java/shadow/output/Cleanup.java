package shadow.output;

import shadow.parser.javacc.ShadowException;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACSimpleNode;

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
		if( node instanceof TACSimpleNode )		
			for (TACOperand operand : (TACSimpleNode)node)
				operand.setData(null);
	}
}
