package shadow.tac.nodes;

import java.util.LinkedList;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACNode;
import shadow.typecheck.type.Type;

public class TACBlock extends TACOperand
{
	private TACBlock parent;
	private TACLabelRef breakLabel, continueLabel, landingpadLabel,
			recoverLabel, cleanupLabel;
	private List<TACLabelRef> catchLabels;
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
		breakLabel = continueLabel = landingpadLabel = recoverLabel =
				cleanupLabel = null;
		catchLabels = new LinkedList<TACLabelRef>();
	}

	@Override
	public Type getType()
	{
		return null;
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
		if (!hasBreak() && hasParent())
			return getParent().getBreak();
		return breakLabel;
	}
	public void addBreak()
	{
		if (hasBreak())
			throw new IllegalStateException("Break label already added.");
		breakLabel = new TACLabelRef(this);
	}

	public boolean hasContinue()
	{
		return continueLabel != null;
	}
	public TACLabelRef getContinue()
	{
		if (!hasContinue() && hasParent())
			return getParent().getContinue();
		return continueLabel;
	}
	public void addContinue()
	{
		if (hasContinue())
			throw new IllegalStateException("Continue label already added.");
		continueLabel = new TACLabelRef(this);
	}

	public boolean hasLandingpad()
	{
		return landingpadLabel != null;
	}
	public TACLabelRef getLandingpad()
	{
		if (!hasLandingpad() && hasParent())
			return getParent().getLandingpad();
		return landingpadLabel;
	}
	public TACLandingpad getLandingpadNode()
	{
		return (TACLandingpad)getLandingpad().getLabel().getNext();
	}
	public TACLabelRef addLandingpad()
	{
		if (hasLandingpad())
			throw new IllegalStateException("Landingpad label already added.");
		return landingpadLabel = new TACLabelRef(this);
	}

	public boolean hasCatches()
	{
		return !catchLabels.isEmpty();
	}
	public int getNumCatches()
	{
		return catchLabels.size();
	}
	public TACLabelRef getCatch(int num)
	{
		return catchLabels.get(num);
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
		if (hasCatches())
			throw new IllegalStateException("Catch labels already added.");
		for (int i = 0; i < num; i++)
			catchLabels.add(new TACLabelRef(this));
		return catchLabels;
	}

	public boolean hasRecover()
	{
		return recoverLabel != null;
	}
	public TACLabelRef getRecover()
	{
		if (!hasRecover() && hasParent())
			return getParent().getRecover();
		return recoverLabel;
	}
	public TACLabelRef addRecover()
	{
		if (hasRecover())
			throw new IllegalStateException("Recover label already added.");
		return recoverLabel = new TACLabelRef(this);
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
		if (hasCleanup())
			throw new IllegalStateException("Cleanup label already added.");
		return cleanupLabel = new TACLabelRef(this);
	}

	@Override
	public int getNumOperands()
	{
		int operands = getNumCatches();
		if (hasParent())
			operands++;
		if (hasBreak())
			operands++;
		if (hasContinue())
			operands++;
		if (hasLandingpad())
			operands++;
		if (hasRecover())
			operands++;
		if (hasCleanup())
			operands++;
		return operands;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (hasParent() && num-- == 0)
			return getParent();
		if (hasBreak() && num-- == 0)
			return getBreak();
		if (hasContinue() && num-- == 0)
			return getContinue();
		if (hasLandingpad() && num-- == 0)
			return getLandingpad();
		if (hasRecover() && num-- == 0)
			return getRecover();
		if (hasCleanup() && num-- == 0)
			return getCleanup();
		return getCatch(num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
