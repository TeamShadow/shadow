package shadow.tac;

import shadow.parser.javacc.ShadowException;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBlock;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACClass.TACClassData;
import shadow.tac.nodes.TACClass.TACMethodTable;
import shadow.tac.nodes.TACCopyMemory;
import shadow.tac.nodes.TACGlobal;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLength;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACLocalLoad;
import shadow.tac.nodes.TACLocalStore;
import shadow.tac.nodes.TACLongToPointer;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACNodeRef;
import shadow.tac.nodes.TACNot;
import shadow.tac.nodes.TACParameter;
import shadow.tac.nodes.TACPhiRef;
import shadow.tac.nodes.TACPhiRef.TACPhi;
import shadow.tac.nodes.TACPhiStore;
import shadow.tac.nodes.TACPointerToLong;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSame;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceElement;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACTypeId;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACUnwind;

public abstract class TACAbstractVisitor implements TACVisitor {	
	/*
	public void walk(TACNode node) throws ShadowException
	{
		if (node instanceof TACSimpleNode)
			walk((TACSimpleNode)node);
		else if (node instanceof TACTree)
			throw new Error("Should not be walking TACTree: " + node.getClass());
			//walk((TACTree)node);
		else
			throw new Error("Unknown subclass of TACNode: " + node.getClass());
	}
	
	
	public void walk(TACTree nodes) throws ShadowException
	{
		for (TACSimpleNode node : nodes)
			visit(node);
	}
	
	
	public void walk(TACSimpleNode nodes) throws ShadowException
	{
		TACNode temp = nodes;
		do
			if ((temp = temp.getNext()) instanceof TACSimpleNode)
				visit((TACSimpleNode)temp);
		while (temp != nodes);
	}
	*/
	
	public void walk(TACNode nodes) throws ShadowException
	{
		TACNode temp = nodes;
		do
		{
			visit(temp);
			temp = temp.getNext();
		}
		while (temp != nodes);
	}
	
	protected void visit(TACNode node) throws ShadowException
	{
		node.accept(this);
	}

//	@Override
//	public void visit(TACArrayRef node) throws ShadowException { }	
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
	public void visit(TACClassData node) throws ShadowException { }
//	@Override
//	public void visit(TACConstantRef node) throws ShadowException { }	
	@Override
	public void visit(TACCopyMemory node) throws ShadowException { }	
//	@Override
//	public void visit(TACFieldRef node) throws ShadowException { }
//	@Override
//	public void visit(TACGenericArrayRef node) throws ShadowException { }
	@Override
	public void visit(TACLabel node) throws ShadowException { }	
	@Override
	public void visit(TACLandingpad node) throws ShadowException { }
	@Override
	public void visit(TACLength node) throws ShadowException { }
	@Override
	public void visit(TACLiteral node) throws ShadowException { }
	@Override
	public void visit(TACLoad node) throws ShadowException { }	
	@Override
	public void visit(TACLocalLoad node) throws ShadowException { }
	@Override
	public void visit(TACLocalStore node) throws ShadowException { }
	@Override
	public void visit(TACLongToPointer node) throws ShadowException { }	
	@Override
	public void visit(TACMethodRef node) throws ShadowException { }
	@Override
	public void visit(TACMethodTable node) throws ShadowException { }
	@Override
	public void visit(TACNewArray node) throws ShadowException { }
	@Override
	public void visit(TACNewObject node) throws ShadowException { }
	@Override
	public void visit(TACNodeRef node) throws ShadowException { }
	@Override
	public void visit(TACNot node) throws ShadowException { }
	@Override
	public void visit(TACPhi node) throws ShadowException { }
	@Override
	public void visit(TACPhiRef node) throws ShadowException { }
	@Override
	public void visit(TACPhiStore node) throws ShadowException { }
	@Override
	public void visit(TACPointerToLong node) throws ShadowException { }	
	@Override
	public void visit(TACSequenceElement node) throws ShadowException { }	
//	@Override
//	public void visit(TACReference node) throws ShadowException { }
	@Override
	public void visit(TACResume node) throws ShadowException { }
	@Override
	public void visit(TACReturn node) throws ShadowException { }
	@Override
	public void visit(TACSame node) throws ShadowException { }
	@Override
	public void visit(TACSequence node) throws ShadowException { }
//	@Override
//	public void visit(TACSingletonRef node) throws ShadowException { }
	@Override
	public void visit(TACStore node) throws ShadowException { }
	@Override
	public void visit(TACThrow node) throws ShadowException { }
	@Override
	public void visit(TACTypeId node) throws ShadowException { }
	@Override
	public void visit(TACUnary node) throws ShadowException { }
	@Override
	public void visit(TACUnwind node) throws ShadowException { }
	@Override
	public void visit(TACGlobal node) throws ShadowException { }
	@Override
	public void visit(TACParameter node) throws ShadowException { }	
}
