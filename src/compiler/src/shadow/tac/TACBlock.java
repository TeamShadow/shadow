package shadow.tac;

import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACNode;

public class TACBlock
{
	private TACBlock parent;
	private TACLabelRef breakLabel, continueLabel, recoverLabel, cleanupLabel;
	public TACBlock()
	{
		this(null);
	}
	public TACBlock(TACBlock parentBlock)
	{
		parent = parentBlock;
		breakLabel = continueLabel = recoverLabel = cleanupLabel = null;
	}

	public boolean hasParent()
	{
		return parent != null;
	}
	public TACBlock getParent()
	{
		return parent;
	}

	public boolean hasBreak()
	{
		return breakLabel != null;
	}
	public TACLabelRef getBreak()
	{
		if (breakLabel == null && parent != null)
			return parent.getBreak();
		return breakLabel;
	}
	public TACLabelRef addBreak()
	{
		return addBreak(null);
	}
	public TACLabelRef addBreak(TACNode node)
	{
		if (breakLabel != null)
			throw new IllegalStateException("Break label already created.");
		return breakLabel = new TACLabelRef(node);
	}

	public boolean hasContinue()
	{
		return continueLabel != null;
	}
	public TACLabelRef getContinue()
	{
		if (breakLabel == null && parent != null)
			return parent.getContinue();
		return continueLabel;
	}
	public TACLabelRef addContinue()
	{
		return addContinue(null);
	}
	public TACLabelRef addContinue(TACNode node)
	{
		if (continueLabel != null)
			throw new IllegalStateException("Continue label already created.");
		return continueLabel = new TACLabelRef(node);
	}

	public boolean hasRecover()
	{
		return recoverLabel != null;
	}
	public TACLabelRef getRecover()
	{
		return recoverLabel;
	}
	public TACLabelRef addRecover()
	{
		if (breakLabel == null && parent != null)
			return parent.getRecover();
		return addRecover(null);
	}
	public TACLabelRef addRecover(TACNode node)
	{
		if (recoverLabel != null)
			throw new IllegalStateException("Recover label already created.");
		return recoverLabel = new TACLabelRef(node);
	}

	public boolean hasCleanup()
	{
		return cleanupLabel != null;
	}
	public TACLabelRef getCleanup()
	{
		return cleanupLabel;
	}
	public TACLabelRef addCleanup()
	{
		return addCleanup(null);
	}
	public TACLabelRef addCleanup(TACNode node)
	{
		if (cleanupLabel != null)
			throw new IllegalStateException("Cleanup label already created.");
		return cleanupLabel = new TACLabelRef(node);
	}
}
