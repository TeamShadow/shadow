package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACBlock extends TACOperand
{
	private TACBlock parent;
	private TACLabelRef breakLabel, continueLabel, landingpadLabel,
			unwindLabel;
	private List<TACLabelRef> catchLabels;
	private TACLabelRef recoverLabel, doneLabel, cleanupLabel;
	private TACDestinationPhiRef cleanupPhi;
	public TACBlock()
	{
		this(null, null);
	}
	public TACBlock(TACBlock parentBlock)
	{
		this(null, parentBlock);
	}
	public TACBlock(TACNode node)
	{
		this(node, null);
	}
	public TACBlock(TACNode node, TACBlock parentBlock)
	{
		super(node);
		parent = parentBlock;
		breakLabel = null;
		continueLabel = null;
		landingpadLabel = null;
		unwindLabel = null;
		catchLabels = null;
		recoverLabel = null;
		cleanupLabel = null;
	}

	@Override
	public Type getType()
	{
		return null;
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
		return breakLabel;
	}
	public TACBlock getBreakBlock()
	{
		return getBreakBlock(null);
	}
	public TACBlock getBreakBlock(TACBlock last)
	{
		return getBreakBlock(this, last);
	}
	public TACBlock getNextBreakBlock()
	{
		return getNextBreakBlock(null);
	}
	public TACBlock getNextBreakBlock(TACBlock last)
	{
		return getBreakBlock(getParent(), last);
	}
	private TACBlock getBreakBlock(TACBlock block, TACBlock last)
	{
		while (block != last && !block.hasBreak())
			block = block.getParent();
		return block;
	}
	public TACBlock addBreak()
	{
		if (breakLabel != null)
			throw new IllegalStateException("Break label already added.");
		breakLabel = new TACLabelRef(this);
		return this;
	}

	public boolean hasContinue()
	{
		return continueLabel != null;
	}
	public TACLabelRef getContinue()
	{
		return continueLabel;
	}
	public TACBlock getContinueBlock()
	{
		return getContinueBlock(null);
	}
	public TACBlock getContinueBlock(TACBlock last)
	{
		return getContinueBlock(this, last);
	}
	public TACBlock getNextContinueBlock()
	{
		return getNextContinueBlock(null);
	}
	public TACBlock getNextContinueBlock(TACBlock last)
	{
		return getContinueBlock(getParent(), last);
	}
	private TACBlock getContinueBlock(TACBlock block, TACBlock last)
	{
		while (block != last && !block.hasContinue())
			block = block.getParent();
		return block;
	}
	public TACBlock addContinue()
	{
		if (continueLabel != null)
			throw new IllegalStateException("Continue label already added.");
		continueLabel = new TACLabelRef(this);
		return this;
	}

	public boolean hasLandingpad()
	{
		return getLandingpad() != null;
	}
	public TACLabelRef getLandingpad()
	{
		if (landingpadLabel != null)
			return landingpadLabel;
		return parent == null ? null : parent.getLandingpad();
	}
	public TACLandingpad getLandingpadNode()
	{
		TACNode node = getLandingpad().getLabel();
		do
			node = node.getNext();
		while (!(node instanceof TACLandingpad));
		return (TACLandingpad)node;
	}
	public TACBlock addLandingpad()
	{
		if (landingpadLabel != null)
			throw new IllegalStateException("Landingpad label already added.");
		landingpadLabel = new TACLabelRef(this);
		return this;
	}

	public boolean hasUnwind()
	{
		return getUnwind() != null;
	}
	public TACLabelRef getUnwind()
	{
		if (unwindLabel != null)
			return unwindLabel;
		return parent == null ? null : parent.getUnwind();
	}
	public TACUnwind getUnwindNode()
	{
		TACNode node = getLandingpad().getLabel();
		while (!(node instanceof TACUnwind))
			node = node.getNext();
		return (TACUnwind)node;
	}
	public TACBlock addUnwind()
	{
		if (unwindLabel != null)
			throw new IllegalStateException("Unwind label already added.");
		unwindLabel = new TACLabelRef(this);
		return this;
	}

	public int getNumCatches()
	{
		return catchLabels != null ? catchLabels.size() : 0;
	}
	public TACLabelRef getCatch(int num)
	{
		return catchLabels.get(num);
	}
	public TACCatch getCatchNode(int num)
	{
		TACNode node = getCatch(num).getLabel();
		do
			node = node.getNext();
		while (!(node instanceof TACCatch));
		return (TACCatch)node;
	}
	public TACBlock addCatch()
	{
		return addCatches(1);
	}
	public TACBlock addCatches(int num)
	{
		if (catchLabels != null)
			throw new IllegalStateException("Catch labels already added.");
		if (num != 0)
		{
			catchLabels = new ArrayList<TACLabelRef>(num);
			for (int i = 0; i < num; i++)
				catchLabels.add(new TACLabelRef(this));
		}
		return this;
	}

	public TACLabelRef getRecover()
	{
		if (recoverLabel != null)
			return recoverLabel;
		return parent == null ? null : parent.getRecover();
	}
	public TACBlock addRecover()
	{
		if (recoverLabel != null)
			throw new IllegalStateException("Recover label already added.");
		recoverLabel = new TACLabelRef(this);
		return this;
	}

	public TACLabelRef getDone()
	{
		if (doneLabel != null)
			return doneLabel;
		return parent == null ? null : parent.getDone();
	}
	public TACBlock addDone()
	{
		if (doneLabel != null)
			throw new IllegalStateException("Done label already added.");
		doneLabel = new TACLabelRef(this);
		return this;
	}

	public boolean hasCleanup()
	{
		return getCleanup() != null;
	}
	public TACLabelRef getCleanup()
	{
		if (cleanupLabel != null)
			return cleanupLabel;
		return parent == null ? null : parent.getCleanup();
	}
	public TACDestinationPhiRef getCleanupPhi()
	{
		if (cleanupPhi != null)
			return cleanupPhi;
		return parent == null ? null : parent.getCleanupPhi();
	}
	public TACBlock getCleanupBlock()
	{
		return getCleanupBlock(null);
	}
	public TACBlock getCleanupBlock(TACBlock last)
	{
		return getCleanupBlock(this, last);
	}
	public TACBlock getNextCleanupBlock()
	{
		return getNextCleanupBlock(null);
	}
	public TACBlock getNextCleanupBlock(TACBlock last)
	{
		return getCleanupBlock(getParent(), last);
	}
	private static TACBlock getCleanupBlock(TACBlock block, TACBlock last)
	{
		while (block != last && block.cleanupLabel == null)
			block = block.getParent();
		return block;
	}
	public TACBlock addCleanup()
	{
		if (cleanupLabel != null)
			throw new IllegalStateException("Cleanup label already added.");
		cleanupLabel = new TACLabelRef(this);
		cleanupPhi = new TACDestinationPhiRef(this);
		return this;
	}

	@Override
	public int getNumOperands()
	{
		int operands = 0;
		if (parent != null)
			operands++;
		if (breakLabel != null)
			operands++;
		if (continueLabel != null)
			operands++;
		if (landingpadLabel != null)
			operands++;
		if (catchLabels != null)
			operands += catchLabels.size();
		if (recoverLabel != null)
			operands++;
		if (cleanupLabel != null)
			operands++;
		return operands;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (parent != null && num-- == 0)
			return parent;
		if (breakLabel != null && num-- == 0)
			return breakLabel;
		if (continueLabel != null && num-- == 0)
			return continueLabel;
		if (landingpadLabel != null && num-- == 0)
			return landingpadLabel;
		if (catchLabels != null)
			if (num < catchLabels.size())
				return catchLabels.get(num);
			else
				num -= catchLabels.size();
		if (recoverLabel != null && num-- == 0)
			return recoverLabel;
		if (cleanupLabel != null && num-- == 0)
			return cleanupLabel;
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
