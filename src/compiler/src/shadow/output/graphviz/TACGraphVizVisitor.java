package shadow.output.graphviz;

import shadow.TAC.TACClass;
import shadow.TAC.TACMethod;
import shadow.output.AbstractTACVisitor;
import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinaryOperation;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACJoin;
import shadow.tac.nodes.TACLoop;
import shadow.tac.nodes.TACNoOp;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACUnaryOperation;

public class TACGraphVizVisitor extends AbstractTACVisitor {

	public TACGraphVizVisitor(TACClass theClass) {
		super(theClass);
	}
	
	@Override
	public void startFile() {
		System.out.println("digraph CFG {");
		System.out.println("node [shape=box];");
	}

	@Override
	public void endFile() {
		System.out.println("}");
	}

	@Override
	public void startFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startMethod(TACMethod method) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endMethod(TACMethod method) {
		// TODO Auto-generated method stub
		
	}

	public void visit(TACNode node) {
		printNode(node, true);
	}
	
	public void visit(TACAssign node) {
		printNode(node, true);
	}
	
	public void visit(TACAllocation node) {
		printNode(node, true);
	}
	
	public void visit(TACBinary node) {
		printNode(node, true);
	}
	
	public void visit(TACBranch node) {
		TACBranch branch = (TACBranch)node;
		TACNode tmp = null;
		
		System.out.print("\"" + branch + "\"");
		System.out.print(" -> ");
		
		tmp = branch.getTrueEntry();
		while(tmp instanceof TACNoOp) {
			tmp = tmp.getNext();
		}
		
		System.out.print("\"" + tmp + "\"");
		System.out.println(" [ label = \"T\" ]; ");
		
		
		System.out.print("\"" + branch + "\"");
		System.out.print(" -> ");

		tmp = branch.getFalseEntry();
		while(tmp instanceof TACNoOp) {
			tmp = tmp.getNext();
		}
		
		System.out.print("\"" + tmp + "\"");
		System.out.println(" [ label = \"F\" ]; ");

		System.out.println("\"" + branch + "\" -> \"" + branch.getJoin() + "\" [ style = \"dashed\" ];");
	}
	
	public void visit(TACLoop node) {
		TACLoop branch = (TACLoop)node;
		TACNode tmp = null;
		
		System.out.print("\"" + branch + "\"");
		System.out.print(" -> ");
		
		tmp = branch.getLoopNode();
		while(tmp instanceof TACNoOp) {
			tmp = tmp.getNext();
		}
		
		System.out.println("\"" + tmp + "\"");
		
		
		System.out.print("\"" + branch + "\"");
		System.out.print(" -> ");

		tmp = branch.getBreakNode();
		while(tmp instanceof TACNoOp) {
			tmp = tmp.getNext();
		}
		
		System.out.print("\"" + tmp + "\"");
		System.out.println(" [ label = \"B\" ]; ");
	}
	
	public void visit(TACJoin node) {
		printNode(node, true);
	}
	
	public void visit(TACNoOp node) {
//		printNode(node, true);
	}

	public void visit(TACUnary node) {
		printNode(node, true);
	}
	
	private void printNode(TACNode node, boolean newline) {
		if(node.getNext() != null) {
			System.out.print("\"" + node + "\"");
			System.out.print(" -> ");
			
			TACNode next = node.getNext();
	
			//
			// This might have broken things with branching
			//
			while(next instanceof TACNoOp) {
				next = next.getNext();
			}
			
/*			if(node.getNext() instanceof TACNoOp)
				next = node.getNext().getNext();
			else
				next = node.getNext();
*/
			if(newline)
				System.out.println("\"" + next + "\"");
			else
				System.out.print("\"" + next + "\"");
		}
	}

}
