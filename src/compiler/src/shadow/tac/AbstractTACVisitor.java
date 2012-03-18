package shadow.tac;

import java.io.IOException;

import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACBranchPhi;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACComparison;
import shadow.tac.nodes.TACGetLength;
import shadow.tac.nodes.TACIndexed;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariable;
import shadow.typecheck.type.ClassType;

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
	public ClassType getType()
	{
		return module.getClassType();
	}
	
	public void visit(TACNode node) throws IOException
	{
		node.accept(this);
	}
	
	public abstract void startFile() throws IOException;
	public abstract void endFile() throws IOException;
	
	public abstract void startFields() throws IOException;
	public abstract void endFields() throws IOException;
	
	public abstract void startMethod(TACMethod method) throws IOException;
	public abstract void endMethod(TACMethod method) throws IOException;

	public abstract void visit(TACAllocation node) throws IOException;
	public abstract void visit(TACAssign node) throws IOException;
	public abstract void visit(TACBinary node) throws IOException;
	public abstract void visit(TACBranch node) throws IOException;
	public abstract void visit(TACCall node) throws IOException;
	public abstract void visit(TACCast node) throws IOException;
	public abstract void visit(TACComparison node) throws IOException;
	public abstract void visit(TACGetLength node) throws IOException;
	public abstract void visit(TACIndexed node) throws IOException;
	public abstract void visit(TACLabel node) throws IOException;
	public abstract void visit(TACLiteral node) throws IOException;
	public abstract void visit(TACPhi node) throws IOException;
	public abstract void visit(TACBranchPhi node) throws IOException;
	public abstract void visit(TACReference node) throws IOException;
	public abstract void visit(TACReturn node) throws IOException;
	public abstract void visit(TACSequence node) throws IOException;
	public abstract void visit(TACUnary node) throws IOException;
	public abstract void visit(TACVariable node) throws IOException;
}
