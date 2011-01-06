
package shadow.TAC;

import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker.WalkType;
import shadow.AST.AbstractASTVisitor;
import shadow.TAC.nodes.TACAssign;
import shadow.TAC.nodes.TACBinaryOperation;
import shadow.TAC.nodes.TACBranch;
import shadow.TAC.nodes.TACJoin;
import shadow.TAC.nodes.TACNoOp;
import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.parser.javacc.ASTConditionalAndExpression;
import shadow.parser.javacc.ASTConditionalExpression;
import shadow.parser.javacc.ASTConditionalOrExpression;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTIfStatement;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTMultiplicativeExpression;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTSequence;
import shadow.parser.javacc.ASTStatementExpression;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;

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
	
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException {
		node.getType();
		
		return WalkType.NO_CHILDREN;
	}
	
	/**
	 * Given an AST node, creates the corresponding TACVariable including recursing down the AST if needed.
	 * @param node The node to convert to a TACVariable.
	 * @return A new TACVariable which might have been a recursive call which is linked into the TAC tree
	 * @throws ShadowException
	 */
	private TACVariable createTACVariable(Node node) throws ShadowException {
		ASTUtils.DEBUG(node, "CREATING VAR: " + node.getImage());
		if(node instanceof ASTLiteral || node instanceof ASTName) {
			return new TACVariable(node.getImage(), node.getType(), node instanceof ASTLiteral);
		} else {
			AST2TAC a2t = new AST2TAC(node);
			a2t.convert();

			// this needs to come before us, as it needs be calculated before us
			linkToEnd(a2t.getEntry(), a2t.getExit());

			return ((TACAssign)a2t.getExit()).getTarget();
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
	
	private static TACComparison symbol2Comparison(String symbol) {
		if(symbol.equals(">")) return TACComparison.GREATER;
		if(symbol.equals(">=")) return TACComparison.GREATER_EQUAL;
		if(symbol.equals("<")) return TACComparison.LESS;
		if(symbol.equals("<=")) return TACComparison.LESS_EQUAL;
		if(symbol.equals("==")) return TACComparison.EQUAL;
		if(symbol.equals("!=")) return TACComparison.NOT_EQUAL;
		
		return null;
	}
	
	/**
	 * Given an AST node that is the start of any binary operation, will create the appropriate TAC tree.
	 * @param node The AST root node of the binary operation
	 * @param operation The operation of that AST node
	 * @throws ShadowException
	 */
	public void visitArithmetic(Node node) throws ShadowException {
		String operators = node.getImage();
		Node astOp1 = node.jjtGetChild(0);
		Node astOp2 = node.jjtGetChild(1);
		
		String curTemp = getTempSymbol();
		TACVariable target = new TACVariable(curTemp, node.getType());
		
		// get the first two TACVariables
		TACVariable op1 = createTACVariable(astOp1);
		TACVariable op2 = createTACVariable(astOp2);
		
		TACBinaryOperation newNode = new TACBinaryOperation(target, op1, op2, symbol2Operation(operators.charAt(0))); 
		
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
	
	public Object visit(ASTAdditiveExpression node, Boolean secondVisit) throws ShadowException {
		visitArithmetic(node);
		return WalkType.NO_CHILDREN;
	}
	
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit) throws ShadowException {
		visitArithmetic(node);
		return WalkType.NO_CHILDREN;
	}
	
/*	public Object visit(ASTLiteral node, Boolean secondVisit) throws ShadowException {
		TACVariable literal = new TACVariable(node.getImage(), node.getType(), true);
		
		linkToEnd(literal);
		
		return WalkType.NO_CHILDREN;
	}
*/
	
	public Object visit(ASTStatementExpression node, Boolean secondVisit) throws ShadowException {
		Node varNode = node.jjtGetChild(0);
		
		if(varNode instanceof ASTSequence) {
			return WalkType.NO_CHILDREN;	// TODO: Implement this
		} else if(node.jjtGetNumChildren() == 3) { // assignment statement
			ASTAssignmentOperator assignNode = (ASTAssignmentOperator)node.jjtGetChild(1);
			Node rhsNode = node.jjtGetChild(2);
			if( rhsNode instanceof ASTConditionalExpression )
				rhsNode = flatten( rhsNode );
			
			TACNode assign = null;
			TACVariable lhs = new TACVariable(varNode.getImage(), varNode.getType(), false);
			TACVariable rhs = createTACVariable(rhsNode);
			
			switch(assignNode.getAssignmentType()) {
				case ANDASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.AND);
					break;
				case EQUAL:
					assign = new TACAssign(lhs, rhs);
					break;
				case LEFTROTATEASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.LROTATE);
					break;
				case LEFTSHIFTASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.LSHIFT);
					break;
				case MINUSASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.SUBTRACTION);
					break;
				case ORASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.OR);
					break;
				case PLUSASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.ADDITION);
					break;
				case REFASSIGN:
					break;
				case MODASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.MOD);
					break;
				case RIGHTROTATEASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.RROTATE);
					break;
				case RIGHTSHIFTASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.RSHIFT);
					break;
				case SLASHASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.DIVISION);
					break;
				case STARASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.MULTIPLICATION);
					break;
				case XORASSIGN:
					assign = new TACBinaryOperation(lhs, lhs, rhs, TACOperation.XOR);
					break;
			}

			linkToEnd(assign);	// link the final assign to the end
		}
		
		return WalkType.NO_CHILDREN;
	}
	
	public static Node flatten( Node node ) //used for conditional statements, etc.
	{
		while( node.jjtGetNumChildren() == 1) //may want to make this smarter
			node = node.jjtGetChild(0);
		
		return node;
	}

	
	public Object visit(ASTIfStatement node, Boolean secondVisit) throws ShadowException {
		ConditionalBranch branch = visitComparison((SimpleNode)flatten(node.jjtGetChild(0)));
		TACJoin join = null;
		
		AST2TAC a2t = new AST2TAC(node.jjtGetChild(1));
		a2t.convert();
		
		TACNode trueEntry = a2t.getEntry();
		TACNode trueExit = a2t.getExit();
		
		branch.trueExit.insertAfter(trueEntry);

		// only an if
		if(node.jjtGetNumChildren() == 2) {
			// create the join and we're done
			join = new TACJoin(trueExit, branch.falseExit);
			
//			if(branch.entryNode.getJoin() == null)
				branch.entryNode.setJoin(join);
			
		} else { // we have an else branch too
			a2t = new AST2TAC(node.jjtGetChild(2));
			a2t.convert();
			
			TACNode falseEntry = a2t.getEntry();
			TACNode falseExit = a2t.getExit();
			
			branch.falseExit.insertAfter(falseEntry);
			
			join = new TACJoin(trueExit, falseExit);
			
			branch.entryNode.setJoin(join);
		}
		
		linkToEnd(branch.entryNode, join);
		
		return WalkType.NO_CHILDREN;
	}
	
	// if only we could return multiple things in Java... wouldn't need this!
	private class ConditionalBranch {
		public TACBranch entryNode;
//		public TACNode entryNode;
		public TACNode trueExit;
		public TACNode falseExit;
	}
	
	public ConditionalBranch visitComparison(SimpleNode node) {
		ConditionalBranch ret = new ConditionalBranch();
		Node astLHS = node.jjtGetChild(0);
		Node astRHS = node.jjtGetChild(1);
		
		//
		// Need to somehow handle the case where someone has if(true)
		//
		
		if(node instanceof ASTConditionalOrExpression) { // OR expression
			ConditionalBranch lhsBranch = visitComparison((SimpleNode)astLHS);
			ConditionalBranch rhsBranch = visitComparison((SimpleNode)astRHS);
			TACJoin join = new TACJoin(lhsBranch.trueExit, rhsBranch.trueExit);
			
//			lhsBranch.trueExit.setNext(join);
//			rhsBranch.trueExit.setNext(join);
			
			lhsBranch.falseExit.setNext(rhsBranch.entryNode);	// set the false path
			rhsBranch.entryNode.setParent(lhsBranch.falseExit);	// set the parent link
			
			ret.entryNode = lhsBranch.entryNode;
			ret.trueExit = join;
			ret.falseExit = rhsBranch.falseExit;
			
			ret.entryNode.setJoin(join);
			rhsBranch.entryNode.setJoin(join);
			
		} else if(node instanceof ASTConditionalAndExpression) { // AND expression
			ConditionalBranch lhsBranch = visitComparison((SimpleNode)astLHS);
			ConditionalBranch rhsBranch = visitComparison((SimpleNode)astRHS);
			TACJoin join = new TACJoin(lhsBranch.falseExit, rhsBranch.falseExit);
			
//			lhsBranch.falseExit.setNext(join);
//			rhsBranch.falseExit.setNext(join);

			lhsBranch.trueExit.setNext(rhsBranch.entryNode);	// set the true path
			rhsBranch.entryNode.setParent(lhsBranch.trueExit);	// set the parent link
			
			ret.entryNode = lhsBranch.entryNode;
			ret.trueExit = rhsBranch.trueExit;
			ret.falseExit = join;
			
			ret.entryNode.setJoin(join);
			rhsBranch.entryNode.setJoin(join);
			
		} else { // standard comparision
			TACVariable lhs = new TACVariable(astLHS.getImage(), astLHS.getType(), astLHS instanceof ASTLiteral);
			TACVariable rhs = new TACVariable(astRHS.getImage(), astRHS.getType(), astRHS instanceof ASTLiteral);
			
			TACBranch branch = new TACBranch(lhs, rhs, symbol2Comparison(node.getImage()));

			branch.setTrueEntry(new TACNoOp(branch));
			branch.setFalseEntry(new TACNoOp(branch));
			
			ret.entryNode = branch;
			ret.falseExit = branch.getFalseEntry();
			ret.trueExit = branch.getTrueEntry();
		}
		
		return ret;
	}
}
