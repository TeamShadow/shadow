package shadow.tac;

import shadow.tac.nodes.TACCatchPad;
import shadow.tac.nodes.TACCatchSwitch;
import shadow.tac.nodes.TACCleanupPad;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPad;
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
	private TACLabel breakLabel, continueLabel;
	private TACCatchSwitch catchSwitch;
	private TACCatchPad catchPad;
	private TACLabel recoverLabel, doneLabel, catchLabel;
	// Although it seems like overkill, we need many different items for a cleanup:
	private TACCleanupPad cleanupPad;	// The cleanuppad, where exceptions will visit as they unwind
	private TACLabel cleanupLabel;		// Label for the actual cleaning up code, which everything will visit, even if not unwinding
	private TACPhi cleanupPhi;			// Phi for returning to wherever we were before doing the cleanup
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
		TACBlock block = this;
		while (block != null && !block.hasBreak())
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
		TACBlock block = this;
		while (block != null && !block.hasContinue())
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
	public TACCatchSwitch getCatchSwitch()
	{
		if (catchSwitch != null)
			return catchSwitch;
		return parent == null ? null : parent.getCatchSwitch();
	}
	public TACBlock addCatchSwitch(TACCatchSwitch catchSwitch)
	{
		if (this.catchSwitch != null)
			throw new IllegalStateException("Catch switch already added.");
		this.catchSwitch = catchSwitch;
		return this;
	}
	public TACCatchPad getCatchPad()
	{
		if (catchPad != null)
			return catchPad;
		return parent == null ? null : parent.getCatchPad();
	}
	public TACBlock addCatchPad(TACCatchPad catchPad)
	{
		if (this.catchPad != null)
			throw new IllegalStateException("Catch pad already added.");
		this.catchPad = catchPad;
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

	public TACCleanupPad getCleanupPad()
	{
		if (cleanupPad != null)
			return cleanupPad;
		return parent == null ? null : parent.getCleanupPad();
	}
	
	public TACLabel getUnwind() {
		TACBlock currentBlock = this;
		while(currentBlock != null) {
			if(currentBlock.catchSwitch != null)
				return currentBlock.catchSwitch.getLabel();
			if(currentBlock.cleanupPad != null)
				return currentBlock.cleanupPad.getLabel();
			
			currentBlock = currentBlock.getParent();
		}
		
		return null;
	}
	
	public TACPad getParentPad() {
		TACBlock currentBlock = this;
		while(currentBlock != null) {
			if(currentBlock.catchPad != null)
				return currentBlock.catchPad;
			if(currentBlock.cleanupPad != null)
				return currentBlock.cleanupPad;
			
			currentBlock = currentBlock.getParent();
		}
		
		return null;
	}
	
	/*
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
	*/
	public TACBlock addCleanup()
	{
		if (cleanupLabel != null)
			throw new IllegalStateException("Cleanup label already added.");
		cleanupLabel = new TACLabel(method);
		cleanupPhi = new TACPhi(null, method.addTempLocal(new SimpleModifiedType(new PointerType())));
		return this;
	}
	
	public TACBlock addCatch()
	{
		if (catchLabel != null)
			throw new IllegalStateException("Catch label already added.");
		catchLabel = new TACLabel(method);
		return this;
	}
	
	public TACLabel getCatch()
	{
		if (catchLabel != null)
			return catchLabel;
		return parent == null ? null : parent.getCatch();
	}

	public TACBlock addCleanupPad(TACCleanupPad cleanupPad) {
		if (this.cleanupPad != null)
			throw new IllegalStateException("Cleanup pad already added.");
		this.cleanupPad = cleanupPad;
		return this;		
	}
}
