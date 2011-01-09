package shadow.output.graphviz;

import shadow.TAC.nodes.TACAssign;
import shadow.TAC.nodes.TACBinaryOperation;
import shadow.TAC.nodes.TACBranch;
import shadow.TAC.nodes.TACJoin;
import shadow.TAC.nodes.TACNoOp;
import shadow.TAC.nodes.TACNode;
import shadow.TAC.nodes.TACUnaryOperation;
import shadow.output.AbstractTACVisitor;

public class TACGraphVizVisitor extends AbstractTACVisitor {

	public TACGraphVizVisitor(TACNode root) {
		super(root);
	}
	
	public void start() {
		System.out.println("digraph CFG {");
		System.out.println("node [shape=box];");
	}
	
	public void end() {
		System.out.println("}");
	}
	
	public void visit(TACNode node) {
		System.out.println("BLAH: " + node);
	}
	
	public void visit(TACAssign node) {
		printNode(node, true);
	}
	
	public void visit(TACBinaryOperation node) {
		printNode(node, true);
	}
	
	public void visit(TACBranch node) {
		TACBranch branch = (TACBranch)node;
		
		System.out.print("\"" + branch + "\"");
		System.out.print(" -> ");
		System.out.print("\"" + branch.getTrueEntry().getNext() + "\"");
		System.out.println(" [ label = \"T\" ]; ");
		
		
		System.out.print("\"" + branch + "\"");
		System.out.print(" -> ");
		System.out.print("\"" + branch.getFalseEntry().getNext() + "\"");
		System.out.println(" [ label = \"F\" ]; ");
	}
	
	public void visit(TACJoin node) {
		printNode(node, true);
	}
	
	public void visit(TACNoOp node) {
//		printNode(node, true);
	}

	public void visit(TACUnaryOperation node) {
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
