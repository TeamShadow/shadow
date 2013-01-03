package shadow.tac;

import shadow.parser.javacc.ShadowException;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBlock;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACInit;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLength;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACNodeRef;
import shadow.tac.nodes.TACNot;
import shadow.tac.nodes.TACPropertyRef;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSame;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceRef;
import shadow.tac.nodes.TACSingletonRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariableRef;

public abstract class TACAbstractVisitor implements TACVisitor
{
	public void walk(TACNodeList nodeList) throws ShadowException
	{
		for (TACNode node : nodeList)
			node.accept(this);
	}
	public void walkTo(TACNode node) throws ShadowException
	{
		TACNode temp = node;
		do
			(temp = temp.getNext()).accept(this);
		while (temp != node);
	}

	@Override
	public void visit(TACArrayRef node) throws ShadowException { }
	@Override
	public void visit(TACBinary node) throws ShadowException { }
	@Override
	public void visit(TACBlock node) throws ShadowException { }
	@Override
	public void visit(TACBranch node) throws ShadowException { }
	@Override
	public void visit(TACCall node) throws ShadowException { }
	@Override
	public void visit(TACCast node) throws ShadowException { }
	@Override
	public void visit(TACCatch node) throws ShadowException { }
	@Override
	public void visit(TACClass node) throws ShadowException { }
	@Override
	public void visit(TACFieldRef node) throws ShadowException { }
	@Override
	public void visit(TACInit node) throws ShadowException { }
	@Override
	public void visit(TACLabel node) throws ShadowException { }
	@Override
	public void visit(TACLabelRef node) throws ShadowException { }
	@Override
	public void visit(TACLandingpad node) throws ShadowException { }
	@Override
	public void visit(TACLength node) throws ShadowException { }
	@Override
	public void visit(TACLiteral node) throws ShadowException { }
	@Override
	public void visit(TACLoad node) throws ShadowException { }
	@Override
	public void visit(TACMethodRef node) throws ShadowException { }
	@Override
	public void visit(TACNewArray node) throws ShadowException { }
	@Override
	public void visit(TACNewObject node) throws ShadowException { }
	@Override
	public void visit(TACNodeRef node) throws ShadowException { }
	@Override
	public void visit(TACNot node) throws ShadowException { }
	@Override
	public void visit(TACPropertyRef node) throws ShadowException { }
	@Override
	public void visit(TACReference node) throws ShadowException { }
	@Override
	public void visit(TACReturn node) throws ShadowException { }
	@Override
	public void visit(TACSame node) throws ShadowException { }
	@Override
	public void visit(TACSequence node) throws ShadowException { }
	@Override
	public void visit(TACSequenceRef node) throws ShadowException { }
	@Override
	public void visit(TACSingletonRef node) throws ShadowException { }
	@Override
	public void visit(TACStore node) throws ShadowException { }
	@Override
	public void visit(TACUnary node) throws ShadowException { }
	@Override
	public void visit(TACVariableRef node) throws ShadowException { }
}
