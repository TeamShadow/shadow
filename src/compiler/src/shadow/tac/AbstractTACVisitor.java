package shadow.tac;

import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACComparison;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACBranchPhi;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariable;
import shadow.typecheck.type.ClassInterfaceBaseType;

public abstract class AbstractTACVisitor
{
	private TACModule module;
	public AbstractTACVisitor(TACModule theClass)
	{
		this.module = theClass;
	}
	
	public TACModule getModule()
	{
		return module;
	}
	public ClassInterfaceBaseType getType()
	{
		return module.getClassType();
	}
	
	public void visit(TACNode node)
	{
		node.accept(this);
	}
	
	public abstract void startFile();
	public abstract void endFile();
	
	public abstract void startFields();
	public abstract void endFields();
	
	public abstract void startMethod(TACMethod method);
	public abstract void endMethod(TACMethod method);

	public abstract void visit(TACAllocation node);
	public abstract void visit(TACAssign node);
	public abstract void visit(TACBinary node);
	public abstract void visit(TACBranch node);
	public abstract void visit(TACCall node);
	public abstract void visit(TACCast node);
	public abstract void visit(TACComparison node);
	public abstract void visit(TACLabel node);
	public abstract void visit(TACLiteral node);
	public abstract void visit(TACPhi node);
	public abstract void visit(TACBranchPhi node);
	public abstract void visit(TACReference node);
	public abstract void visit(TACReturn node);
	public abstract void visit(TACSequence node);
	public abstract void visit(TACUnary node);
	public abstract void visit(TACVariable node);
}
