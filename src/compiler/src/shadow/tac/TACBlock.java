package shadow.tac;

import shadow.tac.nodes.TACLabelRef;

public class TACBlock
{
	private TACLabelRef breakLabel, continueLabel, cleanupLabel;
	public TACBlock(TACLabelRef cleanup)
	{
		breakLabel = null;
		continueLabel = null;
		cleanupLabel = cleanup;
	}
	public TACBlock(TACLabelRef breakTo, TACLabelRef continueTo)
	{
		breakLabel = breakTo;
		continueLabel = continueTo;
		cleanupLabel = null;
	}

	public boolean hasBreak()
	{
		return breakLabel != null;
	}
	public TACLabelRef getBreak()
	{
		return breakLabel;
	}

	public boolean hasContinue()
	{
		return continueLabel != null;
	}
	public TACLabelRef getContinue()
	{
		return continueLabel;
	}

	public boolean hasCleanup()
	{
		return cleanupLabel != null;
	}
	public TACLabelRef getCleanup()
	{
		return cleanupLabel;
	}
}
