package shadow.TAC;

import shadow.TAC.nodes.TACAssign;
import shadow.TAC.nodes.TACBinaryOperation;
import shadow.TAC.nodes.TACBranch;
import shadow.TAC.nodes.TACJoin;
import shadow.TAC.nodes.TACNoOp;
import shadow.TAC.nodes.TACNode;
import shadow.TAC.nodes.TACUnaryOperation;

public class TACGraphVizVisitor extends AbstractTACVisitor {

	public TACGraphVizVisitor(TACNode root) {
		super(root);
	}
	
	public void start() {
		System.out.println("digraph CFG {");
	}
	
	public void end() {
		System.out.println("}");
	}
	
	public void visit(TACNode node) {
		System.out.println("BLAH: " + node);
	}
	
	public void visit(TACAssign node) {
		if(node.getNext() != null) {
			System.out.print("\"" + node + "\"");
			System.out.print(" -> ");
			System.out.println("\"" + node.getNext() + "\"");
		}
	}
	
	public void visit(TACBinaryOperation node) {
		if(node.getNext() != null) {
			System.out.print("\"" + node + "\"");
			System.out.print(" -> ");
			System.out.println("\"" + node.getNext() + "\"");
		}
	}
	
	public void visit(TACBranch node) {
		TACBranch branch = (TACBranch)node;
		
		System.out.print("\"" + branch + "\"");
		System.out.print(" -> ");
		System.out.print("\"" + branch.getTrueEntry() + "\"");
		System.out.println(" [ label = \"T\" ]; ");
		
		
		System.out.print("\"" + branch + "\"");
		System.out.print(" -> ");
		System.out.print("\"" + branch.getFalseEntry() + "\"");
		System.out.println(" [ label = \"F\" ]; ");
	}
	
	public void visit(TACJoin node) {
		if(node.getNext() != null) {
			System.out.print("\"" + node + "\"");
			System.out.print(" -> ");
			System.out.println("\"" + node.getNext() + "\"");
		}
	}
	
	public void visit(TACNoOp node) {
		if(node.getNext() != null) {
			System.out.print("\"" + node + "\"");
			System.out.print(" -> ");
			System.out.println("\"" + node.getNext() + "\"");
		}
	}

	public void visit(TACUnaryOperation node) {
		if(node.getNext() != null) {
			System.out.print("\"" + node + "\"");
			System.out.print(" -> ");
			System.out.println("\"" + node.getNext() + "\"");
		}
	}

}
