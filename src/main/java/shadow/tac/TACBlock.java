package shadow.tac;

import shadow.Configuration;
import shadow.tac.TACMethod.TACFinallyFunction;
import shadow.tac.nodes.*;
import shadow.typecheck.type.PointerType;
import shadow.typecheck.type.SimpleModifiedType;

/**
 * Represents blocks of Shadow code, usually surrounded by braces. Blocks critically contain
 * references to the many labels that they may branch to.
 *
 * @author Jacob Young
 * @author Barry Wittman
 */
public class TACBlock {
  private final TACBlock parent;
  private TACLabel breakLabel, continueLabel;
  private TACLabel recoverLabel, doneLabel, catchesLabel;
  // Although it seems like overkill, we need many different items for a cleanup:
  private TACLabel
      cleanupLabel; // Label for the actual cleaning up code, which everything will visit, even if
                    // not unwinding
  private TACPhi cleanupPhi; // Phi for returning to wherever we were before doing the cleanup

  private TACLabel cleanupUnwindLabel; // Label for the cleanup done when unwinding

  private final TACMethod method;
  private boolean unwindTarget =
      false; // Used to see if the block can be reached by unwinding, important for finally
             // code-generation
  private boolean cleanupTarget =
      false; // Used to see if the block contains a cleanup, important for finally code-generation
  private TACFinallyFunction finallyFunction = null;
  private TACLandingPad landingPad;

  public TACBlock(TACMethod method) {
    this(method, null);
  }

  public TACBlock(TACNode node, TACBlock parentBlock) {
    this(node.getMethod(), parentBlock);
    node.setBlock(this);
  }

  private TACBlock(TACMethod method, TACBlock parentBlock) {
    parent = parentBlock;
    this.method = method;
  }

  public TACMethod getMethod() {
    return method;
  }

  public TACBlock getParent() {
    return parent;
  }

  public boolean hasBreak() {
    return breakLabel != null;
  }

  public TACLabel getBreak() {
    return breakLabel;
  }

  public TACBlock getBreakBlock() {
    TACBlock block = this;
    while (block != null && !block.hasBreak()) block = block.getParent();
    return block;
  }

  public TACBlock addBreak() {
    if (breakLabel != null) throw new IllegalStateException("Break label already added.");
    breakLabel = new TACLabel(method);
    return this;
  }

  public boolean hasContinue() {
    return continueLabel != null;
  }

  public TACLabel getContinue() {
    return continueLabel;
  }

  public TACBlock getContinueBlock() {
    TACBlock block = this;
    while (block != null && !block.hasContinue()) block = block.getParent();
    return block;
  }

  public TACBlock addContinue() {
    if (continueLabel != null) throw new IllegalStateException("Continue label already added.");
    continueLabel = new TACLabel(method);
    return this;
  }

  public TACLabel getRecover() {
    if (recoverLabel != null) return recoverLabel;
    return parent == null ? null : parent.getRecover();
  }

  public boolean hasRecover() {
    return getRecover() != null;
  }

  public void addRecover() {
    if (recoverLabel != null) throw new IllegalStateException("Recover label already added.");
    recoverLabel = new TACLabel(method);
  }

  public TACLabel getDone() {
    if (doneLabel != null) return doneLabel;
    return parent == null ? null : parent.getDone();
  }

  public TACBlock addDone() {
    if (doneLabel != null) throw new IllegalStateException("Done label already added.");
    doneLabel = new TACLabel(method);
    return this;
  }

  public boolean hasCleanup() {
    // return getCleanup() != null;
    return cleanupLabel != null;
  }

  public TACLabel getCleanup() {
    if (cleanupLabel != null) return cleanupLabel;
    return parent == null ? null : parent.getCleanup();
  }

  public TACLabel getCleanupUnwind() {
    if (cleanupUnwindLabel != null) return cleanupUnwindLabel;
    return parent == null ? null : parent.getCleanupUnwind();
  }

  public TACPhi getCleanupPhi() {
    if (cleanupPhi != null) return cleanupPhi;
    return parent == null ? null : parent.getCleanupPhi();
  }

  public TACLabel getUnwind() {
    TACBlock currentBlock = this;
    while (currentBlock != null) {
      if (currentBlock.catchesLabel != null) return currentBlock.catchesLabel;
      if (currentBlock.cleanupUnwindLabel != null) return currentBlock.cleanupUnwindLabel;

      currentBlock = currentBlock.getParent();
    }

    return null;
  }

  public TACBlock addCleanup() {
    if (cleanupLabel != null) throw new IllegalStateException("Cleanup label already added.");
    cleanupLabel = new TACLabel(method);
    cleanupPhi = new TACPhi(null, method.addTempLocal(new SimpleModifiedType(new PointerType())));
    cleanupUnwindLabel = new TACLabel(method);
    if (!Configuration.isWindows()) landingPad = new TACLandingPad(method);
    return this;
  }

  public void addCatches() {
    if (catchesLabel != null) throw new IllegalStateException("Catches label already added.");
    catchesLabel = new TACLabel(method);
    if (!Configuration.isWindows()) landingPad = new TACLandingPad(method);
  }

  public TACLabel getCatches() {
    if (catchesLabel != null) return catchesLabel;
    return parent == null ? null : parent.getCatches();
  }

  public TACLandingPad getLandingPad() {
    if (landingPad != null) return landingPad;
    return parent == null ? null : parent.getLandingPad();
  }

  public boolean isUnwindTarget() {
    return unwindTarget;
  }

  /*
   * Method calls and throws make it possible to unwind, perhaps all the way.
   */
  public void addUnwindSource() {
    TACBlock block = this;
    while (block != null) {
      if (block.hasCleanup()) // only finally blocks
      block.unwindTarget = true;
      block = block.getParent();
    }
  }

  public void setCleanupTarget() {
    cleanupTarget = true;
  }

  // Used to see if code is inside of a cleanup
  // If it is, we don't report an error for dead code removal
  public boolean isInsideCleanup() {
    TACBlock block = this;
    while (block != null) {
      if (block.cleanupTarget) return true;

      block = block.getParent();
    }

    return false;
  }

  public void setFinallyFunction(TACFinallyFunction function) {
    finallyFunction = function;
  }

  public TACFinallyFunction getFinallyFunction() {
    TACBlock current = this;
    while (current != null) {
      if (current.finallyFunction != null) return current.finallyFunction;

      current = current.parent;
    }

    return null;
  }
}
