package shadow.output.graphviz;

import shadow.output.AbstractTACVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACComparison;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACPhiBranch;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariable;

public class TACGraphVizVisitor extends AbstractTACVisitor {

	public TACGraphVizVisitor(TACModule theClass) {
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
	
	public void visit(TACAllocation node) {
		printNode(node, true);
	}
	
	public void visit(TACAssign node) {
		printNode(node, true);
	}
	
	public void visit(TACUnary node) {
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
		
		tmp = branch.getTrueBranch();
//		while(tmp instanceof TACNoOp) {
//			tmp = tmp.getNext();
//		}
		
		System.out.print("\"" + tmp + "\"");
		System.out.println(" [ label = \"T\" ]; ");
		
		
		System.out.print("\"" + branch + "\"");
		System.out.print(" -> ");

		tmp = branch.getFalseBranch();
//		while(tmp instanceof TACNoOp) {
//			tmp = tmp.getNext();
//		}
		
		System.out.print("\"" + tmp + "\"");
		System.out.println(" [ label = \"F\" ]; ");

		System.out.println("\"" + branch + "\" -> \"" + branch.getBranch() + "\" [ style = \"dashed\" ];");
	}
	
//	public void visit(TACLoop node) {
//		TACNode tmp = null;
//		
//		System.out.print("\"" + node + "\"");
//		System.out.print(" -> ");
//		
//		tmp = node.getLoopNode();
////		while(tmp instanceof TACNoOp) {
////			tmp = tmp.getNext();
////		}
//		
//		System.out.println("\"" + tmp + "\"");
//		
//		
//		System.out.print("\"" + node + "\"");
//		System.out.print(" -> ");
//
//		tmp = node.getBreakNode();
////		while(tmp instanceof TACNoOp) {
////			tmp = tmp.getNext();
////		}
//		
//		System.out.print("\"" + tmp + "\"");
//		System.out.println(" [ label = \"B\" ]; ");
//	}
	
	private void printNode(TACNode node, boolean newline) {
		if(node.getNext() != null) {
			System.out.print("\"" + node + "\"");
			System.out.print(" -> ");
			
			TACNode next = node.getNext();
	
			//
			// This might have broken things with branching
			//
//			while(next instanceof TACNoOp) {
//				next = next.getNext();
//			}
			
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

	@Override
	public void visit(TACCall node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACCast node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACComparison node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACLiteral node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACPhi node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACPhiBranch node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACReference node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACReturn node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACSequence node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACVariable node) {
		// TODO Auto-generated method stub
		
	}
}
