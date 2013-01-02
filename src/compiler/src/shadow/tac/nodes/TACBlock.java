package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACBlock extends TACOperand
{
	private TACBlock parent;
	private TACLabelRef breakLabel, continueLabel, landingpadLabel;
	private List<TACLabelRef> catchLabels;
	private TACLabelRef recoverLabel, cleanupLabel;
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

	public TACLabelRef getBreak()
	{
		if (breakLabel != null)
			return breakLabel;
		if (parent != null)
			return parent.getBreak();
		return null;
	}
	public TACLabelRef addBreak()
	{
		if (breakLabel != null)
			throw new IllegalStateException("Break label already added.");
		return breakLabel = new TACLabelRef(this);
	}

	public TACLabelRef getContinue()
	{
		if (continueLabel != null)
			return continueLabel;
		return parent == null ? null : parent.getContinue();
	}
	public TACLabelRef addContinue()
	{
		if (continueLabel != null)
			throw new IllegalStateException("Continue label already added.");
		return continueLabel = new TACLabelRef(this);
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
		return (TACLandingpad)getLandingpad().getLabel().getNext();
	}
	public TACLabelRef addLandingpad()
	{
		if (landingpadLabel != null)
			throw new IllegalStateException("Landingpad label already added.");
		return landingpadLabel = new TACLabelRef(this);
	}

	public int getNumCatches()
	{
		if (catchLabels != null)
			return catchLabels.size();
		return parent == null ? 0 : parent.getNumCatches();
	}
	public TACLabelRef getCatch(int num)
	{
		if (catchLabels != null)
			return catchLabels.get(num);
		return parent == null ? null : parent.getCatch(num);
	}
	public TACCatch getCatchNode(int num)
	{
		return (TACCatch)getCatch(num).getLabel().getNext();
	}
	public TACLabelRef addCatch()
	{
		return addCatches(1).get(0);
	}
	public List<TACLabelRef> addCatches(int num)
	{
		if (catchLabels != null)
			throw new IllegalStateException("Catch labels already added.");
		if (num == 0)
			return Collections.emptyList();
		catchLabels = new ArrayList<TACLabelRef>(num);
		for (int i = 0; i < num; i++)
			catchLabels.add(new TACLabelRef(this));
		return catchLabels;
	}

	public TACLabelRef getRecover()
	{
		if (recoverLabel != null)
			return recoverLabel;
		return parent == null ? null : parent.getRecover();
	}
	public TACLabelRef addRecover()
	{
		if (recoverLabel != null)
			throw new IllegalStateException("Recover label already added.");
		return recoverLabel = new TACLabelRef(this);
	}

	public TACLabelRef getCleanup()
	{
		return cleanupLabel;
	}
	public TACLabelRef addCleanup()
	{
		if (cleanupLabel != null)
			throw new IllegalStateException("Cleanup label already added.");
		return cleanupLabel = new TACLabelRef(this);
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
