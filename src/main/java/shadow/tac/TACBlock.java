package shadow.tac;

import java.util.ArrayList;
import java.util.List;

import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.typecheck.type.PointerType;
import shadow.typecheck.type.SimpleModifiedType;

/**
 * Represents blocks of Shadow code, usually surrounded by braces.
 * Blocks critically contain references to the many labels that they
 * may branch to.
 * @author Jacob Young
 * @author Barry Wittman 
 */
public class TACBlock
{
	private TACBlock parent;
	private TACLabel breakLabel, continueLabel, landingpadLabel,
			unwindLabel;
	private List<TACLabel> catchLabels;
	private TACLabel recoverLabel, doneLabel, cleanupLabel;
	private TACPhi cleanupPhi;
	private TACMethod method;

	public TACBlock(TACMethod method)
	{
		this(method, null);	
	}
	
	public TACBlock(TACNode node, TACBlock parentBlock)
	{
		this(node.getMethod(), parentBlock);
		node.setBlock(this);
	}
	
	private TACBlock(TACMethod method, TACBlock parentBlock)
	{
		parent = parentBlock;
		breakLabel = null;
		continueLabel = null;
		landingpadLabel = null;
		unwindLabel = null;
		catchLabels = null;
		recoverLabel = null;
		cleanupLabel = null;
		this.method = method;		
	}
	
	public TACMethod getMethod()
	{
		return method;
	}

	public TACBlock getParent()
	{
		return parent;
	}

	public boolean hasBreak()
	{
		return breakLabel != null;
	}
	public TACLabel getBreak()
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
		breakLabel = new TACLabel(method);
		return this;
	}
	public boolean hasContinue()
	{
		return continueLabel != null;
	}
	public TACLabel getContinue()
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
		continueLabel = new TACLabel(method);
		return this;
	}

	public boolean hasLandingpad()
	{
		return getLandingpad() != null;
	}
	public TACLabel getLandingpad()
	{
		if (landingpadLabel != null)
			return landingpadLabel;
		return parent == null ? null : parent.getLandingpad();
	}
	public TACBlock addLandingpad()
	{
		if (landingpadLabel != null)
			throw new IllegalStateException("Landingpad label already added.");
		landingpadLabel = new TACLabel(method);
		return this;
	}

	public boolean hasUnwind()
	{
		return getUnwind() != null;
	}
	public TACLabel getUnwind()
	{
		if (unwindLabel != null)
			return unwindLabel;
		return parent == null ? null : parent.getUnwind();
	}
	public TACBlock addUnwind()
	{
		if (unwindLabel != null)
			throw new IllegalStateException("Unwind label already added.");
		unwindLabel = new TACLabel(method);
		return this;
	}

	public int getNumCatches()
	{
		return catchLabels != null ? catchLabels.size() : 0;
	}
	public TACLabel getCatch(int num)
	{
		return catchLabels.get(num);
	}
	public TACCatch getCatchNode(int num)
	{
		TACNode node = getCatch(num);
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
		if (num > 0) {
			catchLabels = new ArrayList<TACLabel>(num);
			for (int i = 0; i < num; i++)
				catchLabels.add(new TACLabel(method));
		}
		return this;
	}

	public TACLabel getRecover()
	{
		if (recoverLabel != null)
			return recoverLabel;
		return parent == null ? null : parent.getRecover();
	}
	
	public boolean hasRecover()
	{
		return getRecover() != null;
	}
	
	public TACBlock addRecover()
	{
		if (recoverLabel != null)
			throw new IllegalStateException("Recover label already added.");
		recoverLabel = new TACLabel(method);
		return this;
	}

	public TACLabel getDone()
	{
		if (doneLabel != null)
			return doneLabel;
		return parent == null ? null : parent.getDone();
	}
	public TACBlock addDone()
	{
		if (doneLabel != null)
			throw new IllegalStateException("Done label already added.");
		doneLabel = new TACLabel(method);
		return this;
	}

	public boolean hasCleanup()
	{
		//return getCleanup() != null;
		return cleanupLabel != null;
	}
	public TACLabel getCleanup()
	{
		if (cleanupLabel != null)
			return cleanupLabel;
		return parent == null ? null : parent.getCleanup();
	}
	public TACPhi getCleanupPhi()
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
		cleanupLabel = new TACLabel(method);		
		cleanupPhi = new TACPhi(null, method.addTempLocal(new SimpleModifiedType(new PointerType())));
		return this;
	}
}
