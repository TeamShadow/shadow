package shadow.tac;

import shadow.parser.javacc.ShadowException;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACDispatch;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLength;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNodeRef;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariableRef;

public interface TACVisitor
{
	public abstract void startFile(TACModule module) throws ShadowException;
	public abstract void endFile(TACModule module) throws ShadowException;

	public abstract void startMethod(TACMethod method)
			throws ShadowException;
	public abstract void endMethod(TACMethod method) throws ShadowException;

	public abstract void visit(TACArrayRef node) throws ShadowException;
	public abstract void visit(TACBinary node) throws ShadowException;
	public abstract void visit(TACBranch node) throws ShadowException;
	public abstract void visit(TACCall node) throws ShadowException;
	public abstract void visit(TACCast node) throws ShadowException;
	public abstract void visit(TACClass node) throws ShadowException;
	public abstract void visit(TACDispatch node) throws ShadowException;
	public abstract void visit(TACFieldRef node) throws ShadowException;
	public abstract void visit(TACLabel node) throws ShadowException;
	public abstract void visit(TACLabelRef node) throws ShadowException;
	public abstract void visit(TACLength node) throws ShadowException;
	public abstract void visit(TACLiteral node) throws ShadowException;
	public abstract void visit(TACLoad node) throws ShadowException;
	public abstract void visit(TACNewArray node) throws ShadowException;
	public abstract void visit(TACNewObject node) throws ShadowException;
	public abstract void visit(TACNodeRef node) throws ShadowException;
	public abstract void visit(TACReference node) throws ShadowException;
	public abstract void visit(TACReturn node) throws ShadowException;
	public abstract void visit(TACSequence node) throws ShadowException;
	public abstract void visit(TACSequenceRef node) throws ShadowException;
	public abstract void visit(TACStore node) throws ShadowException;
	public abstract void visit(TACUnary node) throws ShadowException;
	public abstract void visit(TACVariableRef node) throws ShadowException;
}
