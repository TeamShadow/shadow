
package shadow.TAC;

import shadow.AST.AbstractASTVisitor;
import shadow.AST.ASTWalker.WalkType;
import shadow.TAC.nodes.TACBinaryOperation;
import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTSequence;
import shadow.parser.javacc.ASTStatementExpression;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;

public class AST2TACWalker extends AbstractASTVisitor {
	private TACNode entryNode;	/** The first node in the tree */
	private TACNode exitNode;	/** The last node in the tree... only 1 exit always */
	
	private static int tempCounter = 0;
	
	/**
	 * Walks a portion of an AST and creates a TAC tree.
	 * This class does all of the heavy lifting in converting from AST -> TAC.
	 */
	public AST2TACWalker() {
		// setup with a no-op as it's easier to use setNode
		entryNode = exitNode = new TACNode("NO-OP", null);
	}
	
	public TACNode getEntry() {
		return entryNode;
	}
	
	public TACNode getExit() {
		return exitNode;
	}
	
	/**
	 * Needs more work to make sure it's unique.
	 * @return A new temporary symbol.
	 */
	static public String getTempSymbol() {
		return "temp_" + tempCounter++;
	}

	public Object visit(ASTAdditiveExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Node astOp1 = node.jjtGetChild(0);
		Node astOp2 = node.jjtGetChild(1);

		String curTemp = getTempSymbol();
		TACVariable target = new TACVariable(curTemp, node.getType());
		TACVariable op1 = new TACVariable(astOp1.getImage(), astOp1.getType(), astOp1 instanceof ASTLiteral);
		TACVariable op2 = new TACVariable(astOp2.getImage(), astOp2.getType(), astOp2 instanceof ASTLiteral);
		
		TACBinaryOperation newNode = new TACBinaryOperation(target, op1, op2, TACOperation.ADDITION); 
		
		// link in the node
		exitNode.setNext(newNode);
		exitNode = newNode;
		
		for(int i=2; i < node.jjtGetNumChildren(); ++i) {
			astOp2 = node.jjtGetChild(i);

			op1 = new TACVariable(curTemp, astOp2.getType());	// not sure about the type
			op2 = new TACVariable(astOp2.getImage(), astOp2.getType(), astOp2 instanceof ASTLiteral);
			
			curTemp = getTempSymbol();
			target = new TACVariable(curTemp, node.getType());
			
			newNode = new TACBinaryOperation(target, op1, op2, TACOperation.ADDITION);
			
			exitNode.setNext(newNode);
			exitNode = newNode;
		}
		
		return WalkType.POST_CHILDREN; // ???
	}
	
	public Object visit(ASTStatementExpression node, Boolean secondVisit) throws ShadowException {
		Node child = node.jjtGetChild(0);
		
		if( child instanceof ASTSequence ) {
			return WalkType.POST_CHILDREN; // we don't handle this yet
		} else { //primary expression
			
		}
		
		return WalkType.POST_CHILDREN;
	}
}
