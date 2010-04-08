
package shadow.TAC;

import shadow.AST.AbstractASTVisitor;
import shadow.AST.ASTWalker.WalkType;
import shadow.TAC.nodes.TACAssign;
import shadow.TAC.nodes.TACBinaryOperation;
import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTMultiplicativeExpression;
import shadow.parser.javacc.ASTName;
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

	/**
	 * Links a single node to the end of this tree.
	 * @param node The node to link to the end of the tree.
	 */
	private void linkToEnd(TACNode node) {
		if(entryNode == null) { // we don't have a tree yet
			entryNode = node;
		} else {
			exitNode.insertAfter(node);
		}

		exitNode = node;
	}

	/**
	 * Links an entire tree to the end of this tree.
	 * @param entry The start of the tree to link to the end.
	 * @param exit The end of the tree to link to the end.
	 */
	private void linkToEnd(TACNode entry, TACNode exit) {
		if(entryNode == null) {
			entryNode = entry;
		} else {
			entry.setParent(exitNode);
			exitNode.setNext(entry);
		}

		exitNode = exit;
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
		visitArithmetic(node, TACOperation.ADDITION);
		return WalkType.NO_CHILDREN;
	}
	
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit) throws ShadowException {
		visitArithmetic(node, TACOperation.MULTIPLICATION);
		return WalkType.NO_CHILDREN;
	}
	
	/**
	 * Given an AST node, creates the corresponding TACVariable including recursing down the AST if needed.
	 * @param node The node to convert to a TACVariable.
	 * @return A new TACVariable which might have been a recursive call which is linked into the TAC tree
	 * @throws ShadowException
	 */
	private TACVariable createTACVariable(Node node) throws ShadowException {
		if(node instanceof ASTLiteral || node instanceof ASTName) {
			return new TACVariable(node.getImage(), node.getType(), node instanceof ASTLiteral);
		} else {
			AST2TAC a2t = new AST2TAC(node);
			TACAssign tempNode = (TACAssign)a2t.convert();

			// this needs to come before us, as it needs be calculated before us
			linkToEnd(a2t.getEntry(), a2t.getExit());

			return tempNode.getTarget();
		}
	}
	
	private static TACOperation symbol2Operation(char symbol) {
		switch(symbol) {
		case '+':
			return TACOperation.ADDITION;
		case '-':
			return TACOperation.SUBTRACTION;
		case '*':
			return TACOperation.MULTIPLICATION;
		case '/':
			return TACOperation.DIVISION;
		case '%':
			return TACOperation.MOD;
		}
		
		return null;
	}
	
	/**
	 * Given an AST node that is the start of any binary operation, will create the appropriate TAC tree.
	 * @param node The AST root node of the binary operation
	 * @param operation The operation of that AST node
	 * @throws ShadowException
	 */
	public void visitArithmetic(Node node, TACOperation operation) throws ShadowException {
		String operators = node.getImage();
		Node astOp1 = node.jjtGetChild(0);
		Node astOp2 = node.jjtGetChild(1);
		
		String curTemp = getTempSymbol();
		TACVariable target = new TACVariable(curTemp, node.getType());
		
		// get the first two TACVariables
		TACVariable op1 = createTACVariable(astOp1);
		TACVariable op2 = createTACVariable(astOp2);
		
		TACBinaryOperation newNode = new TACBinaryOperation(target, op1, op2, operation); 
		
		// link in the node
		linkToEnd(newNode);
		
		// now we only process a single var at a time, combining with the previous temp
		for(int i=2; i < node.jjtGetNumChildren(); ++i) {
			astOp2 = node.jjtGetChild(i);

			op1 = new TACVariable(curTemp, astOp2.getType());	// not sure about the type
			op2 = createTACVariable(astOp2);
			
			curTemp = getTempSymbol();
			target = new TACVariable(curTemp, node.getType());
			
			newNode = new TACBinaryOperation(target, op1, op2, symbol2Operation(operators.charAt(i-1)));
		
			linkToEnd(newNode);	
		}
	}
	
}
