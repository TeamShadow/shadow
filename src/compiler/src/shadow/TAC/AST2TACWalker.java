
package shadow.TAC;

import shadow.AST.AbstractASTVisitor;
import shadow.AST.ASTWalker.WalkType;
import shadow.TAC.nodes.TACAssign;
import shadow.TAC.nodes.TACBinaryOperation;
import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTName;
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
	}
	
	public TACNode getEntry() {
		return entryNode;
	}
	
	public TACNode getExit() {
		return exitNode;
	}

	private void linkToEnd(TACNode node) {
		if(entryNode == null) { // we don't have a tree yet
			entryNode = node;
		} else {
			exitNode.insertAfter(node);
		}

		exitNode = node;
	}

	private void linkToStart(TACNode node) {
		if(entryNode == null) { // we don't have a tree yet
			exitNode = node;
		} else {
			entryNode.insertBefore(node);
		}
		
		entryNode = node;
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
		linkToEnd(newNode);
		
		for(int i=2; i < node.jjtGetNumChildren(); ++i) {
			astOp2 = node.jjtGetChild(i);

			op1 = new TACVariable(curTemp, astOp2.getType());	// not sure about the type

			if(astOp2 instanceof ASTLiteral || astOp2 instanceof ASTName) {
				op2 = new TACVariable(astOp2.getImage(), astOp2.getType(), astOp2 instanceof ASTLiteral);
			} else { // we have a more complex expression
				AST2TAC a2t = new AST2TAC(astOp2);
				TACAssign tempNode = (TACAssign)a2t.convert();

				// this needs to come before us, as it needs be calculated before us
				linkToStart(tempNode);

				op2 = tempNode.getTarget();
			}
			
			curTemp = getTempSymbol();
			target = new TACVariable(curTemp, node.getType());
			
			newNode = new TACBinaryOperation(target, op1, op2, TACOperation.ADDITION);
		
			linkToEnd(newNode);	
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
