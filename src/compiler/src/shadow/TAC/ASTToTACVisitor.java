
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
import shadow.TAC.nodes.TACNode.TACComparison;
import shadow.TAC.nodes.TACNode.TACOperation;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.parser.javacc.ASTBitwiseAndExpression;
import shadow.parser.javacc.ASTBitwiseExclusiveOrExpression;
import shadow.parser.javacc.ASTBitwiseOrExpression;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTBlockStatement;
import shadow.parser.javacc.ASTConditionalAndExpression;
import shadow.parser.javacc.ASTConditionalExclusiveOrExpression;
import shadow.parser.javacc.ASTConditionalExpression;
import shadow.parser.javacc.ASTConditionalOrExpression;
import shadow.parser.javacc.ASTEqualityExpression;
import shadow.parser.javacc.ASTExpression;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFormalParameters;
import shadow.parser.javacc.ASTIsExpression;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTLocalVariableDeclaration;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTMethodDeclarator;
import shadow.parser.javacc.ASTMultiplicativeExpression;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPrimaryExpression;
import shadow.parser.javacc.ASTPrimaryPrefix;
import shadow.parser.javacc.ASTPrimitiveType;
import shadow.parser.javacc.ASTRelationalExpression;
import shadow.parser.javacc.ASTRotateExpression;
import shadow.parser.javacc.ASTSequence;
import shadow.parser.javacc.ASTShiftExpression;
import shadow.parser.javacc.ASTStatement;
import shadow.parser.javacc.ASTStatementExpression;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.ASTUnaryExpression;
import shadow.parser.javacc.ASTUnaryExpressionNotPlusMinus;
import shadow.parser.javacc.ASTVariableDeclarator;
import shadow.parser.javacc.ASTVariableDeclaratorId;
import shadow.parser.javacc.ASTVariableInitializer;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;

public class ASTToTACVisitor extends AbstractASTVisitor {
	private static int tempCounter = 0;	/** Counter for making temporary variables */
//	private TACNode entryNode = null;
//	private TACNode exitNode = null;
	
	/**
	 * Walks a portion of an AST and creates a TAC tree.
	 * This class does all of the heavy lifting in converting from AST -> TAC.
	 */
	public ASTToTACVisitor() {
	}
	
/*	public TACNode getEntry() {
		return entryNode;
	}
	
	public TACNode getExit() {
		return exitNode;
	}
*/
	
/*	private void linkToStart(TACNode node) {
		if(entryNode == null) { // we don't have a tree yet
			exitNode = node;
		} else {
			entryNode.insertBefore(node);
		}
		
		entryNode = node;
	}
	
	private void linkToEnd(TACNode node) {
		if(entryNode == null) { // we don't have a tree yet
			entryNode = node;
		} else {
			exitNode.insertAfter(node);
		}

		exitNode = node;
	}
*/
	
	/**
	 * Links a TAC path to the end of an AST Node's TAC
	 * @param entry The start of the tree to link to the end.
	 * @param exit The end of the tree to link to the end.
	 */
	private void linkToEnd(Node astNode, TACNode entry, TACNode exit) {
		SimpleNode node = (SimpleNode)astNode;
		
		// if the AST doesn't have an entry, then make it the same as this one
		if(node.getEntryNode() == null) {
			node.setEntryNode(entry);
		} else {
			TACNode astExit = node.getExitNode();
			
			entry.setParent(astExit);
			astExit.setNext(entry);
		}

		node.setExitNode(exit);	// update the AST's exit to be this one
	}
	
	private void linkToEnd(Node astNode, TACNode tacNode) {
		linkToEnd(astNode, tacNode, tacNode);	// simply the same as entry = exit = tacNode
	}

	/**
	 * Needs more work to make sure it's unique.
	 * @return A new temporary symbol.
	 */
	static public String getTempSymbol() {
		return "temp_" + tempCounter++;
	}
	
	/** PUNTING ON THIS FOR NOW */
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException {
		node.getType();
		
		return WalkType.NO_CHILDREN;
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
		String operators = node.getImage();	// get the operators
		SimpleNode astOp1 = (SimpleNode)node.jjtGetChild(0);
		SimpleNode astOp2 = (SimpleNode)node.jjtGetChild(1);
		
		// link the first two operator's TAC paths into this one
		TACNode op1ExitNode = astOp1.getExitNode();
		TACNode op2ExitNode = astOp2.getExitNode();
		
		linkToEnd(node, astOp1.getEntryNode(), op1ExitNode);
		linkToEnd(node, astOp2.getEntryNode(), op2ExitNode);
		
		// get the first two TACVariables
		TACVariable op1 = op1ExitNode instanceof TACAssign ? ((TACAssign)op1ExitNode).getTarget() : ((TACNoOp)op1ExitNode).getVariable();
		TACVariable op2 = op2ExitNode instanceof TACAssign ? ((TACAssign)op2ExitNode).getTarget() : ((TACNoOp)op2ExitNode).getVariable();
		TACVariable target = new TACVariable(getTempSymbol(), node.getType());
		
		// create a binary operator with the first two children
		TACBinaryOperation newNode = new TACBinaryOperation(target, op1, op2, symbol2Operation(operators.charAt(0))); 
		
		// link in the node, setting the node's entry & exit if needed
		linkToEnd(node, newNode);
		
		// now we only process a single var at a time, combining with the previous temp
		for(int i=2; i < node.jjtGetNumChildren(); ++i) {
			astOp2 = (SimpleNode)node.jjtGetChild(i);

			// link in the TAC path for the next operator
			op2ExitNode = astOp2.getExitNode();
			linkToEnd(node, astOp2.getEntryNode(), op2ExitNode);

			op1 = target;	// the target of the previous one
			op2 = op2ExitNode instanceof TACAssign ? ((TACAssign)op2ExitNode).getTarget() : ((TACNoOp)op2ExitNode).getVariable();
			
			target = new TACVariable(getTempSymbol(), node.getType());
			
			newNode = new TACBinaryOperation(target, op1, op2, symbol2Operation(operators.charAt(i-1)));
		
			linkToEnd(node, newNode);	
		}
	}
	
	private void linkBlankNode(SimpleNode node) {
		SimpleNode childNode = (SimpleNode)node.jjtGetChild(0);
		
		// set this node's entry & exit to be the same as it's child
		node.setEntryNode(childNode.getEntryNode());
		node.setExitNode(childNode.getExitNode());
		return;	// nothing else to do here
	}
	
/*	public static Node flatten( Node node ) //used for conditional statements, etc.
	{
		while( node.jjtGetNumChildren() == 1) //may want to make this smarter
			node = node.jjtGetChild(0);
		
		return node;
	}
*/
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

			branch.setTrueEntry(new TACNoOp(null, branch));
			branch.setFalseEntry(new TACNoOp(null, branch));
			
			ret.entryNode = branch;
			ret.falseExit = branch.getFalseEntry();
			ret.trueExit = branch.getTrueEntry();
		}
		
		return ret;
	}

	/**
	 * Used at the start of most visit methods to link in empty AST nodes.
	 * @param node The node to check.
	 * @return POST_CHILDREN if it should return, PRE_CHILDREN if it's OK
	 */
	private WalkType cleanupNode(Node node) {
		if(node.jjtGetNumChildren() == 1) {
			linkBlankNode((SimpleNode)node);
			return WalkType.POST_CHILDREN;
		}
		
		return WalkType.PRE_CHILDREN;
	}
	
	//
	// Below here you'll find the various visit methods
	//
	public Object visit(ASTLiteral node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		TACVariable literal = new TACVariable(node.getImage(), node.getType(), true);
		TACNoOp noop = new TACNoOp(literal, null, null);
		
		ASTUtils.DEBUG(node, noop.toString());
		
		// set the entry & exit to this node
		((SimpleNode)node).setEntryNode(noop);
		((SimpleNode)node).setExitNode(noop);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		TACVariable variable = new TACVariable(node.getImage(), node.getType());
		TACNoOp noop = new TACNoOp(variable, null, null);
		
		ASTUtils.DEBUG(node, noop.toString());
		
		// set the entry & exit to this node
		((SimpleNode)node).setEntryNode(noop);
		((SimpleNode)node).setExitNode(noop);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTPrimaryExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTUnaryExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTRotateExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTShiftExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTRelationalExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTIsExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTEqualityExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBitwiseAndExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBitwiseExclusiveOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBitwiseOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalAndExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalExclusiveOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTVariableDeclaratorId node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTVariableDeclarator node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTPrimitiveType node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTLocalVariableDeclaration node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		SimpleNode type = (SimpleNode)node.jjtGetChild(0);
		
		for(int i=1; i < node.jjtGetNumChildren(); ++i) {
			SimpleNode astVar = (SimpleNode)node.jjtGetChild(i);
			TACVariable var = new TACVariable(astVar.jjtGetChild(0).getImage(), type.getType());
			TACAssign assign = null;
			
			if(astVar.jjtGetNumChildren() == 2)	{ // we have an initializer
				TACNode entry = ((SimpleNode)astVar.jjtGetChild(1)).getEntryNode();
				TACNode exit = ((SimpleNode)astVar.jjtGetChild(1)).getExitNode();
				linkToEnd(node, entry, exit);
				
				if(exit instanceof TACAssign)
					assign = new TACAssign(var, ((TACAssign)exit).getTarget());
				else
					assign = new TACAssign(var, ((TACNoOp)exit).getVariable());
			}
			else
				assign = new TACAssign(var, TACVariable.getDefault(type.getType()));
			
			linkToEnd(node, assign);
		}
		
		TACNode entry = ((SimpleNode)node).getEntryNode();
		ASTUtils.DEBUG("ENTRY NODE: " + entry);
		
		entry.dump("");
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBlockStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		// go through all the BlockStatements and link them in
		for(int i=0; i < node.jjtGetNumChildren(); ++i) {
			SimpleNode blockStmt = (SimpleNode)node.jjtGetChild(i);
			
			linkToEnd(node, blockStmt.getEntryNode(), blockStmt.getExitNode());
		}
		
		((SimpleNode)node).getEntryNode().dump("BLOCK: ");
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTFormalParameters node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMethodDeclarator node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		// this first one will always be the block of the method
		SimpleNode block = (SimpleNode)node.jjtGetChild(1);
		
		// link in the block's TAC path as it is the method's path
		linkToEnd(node, block.getEntryNode(), block.getExitNode());

		return WalkType.POST_CHILDREN;
	}
	

	public Object visit(ASTAdditiveExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;

		ASTUtils.DEBUG(node);

		visitArithmetic(node);
		
		((SimpleNode)node).getEntryNode().dump("");
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		visitArithmetic(node);
		return WalkType.NO_CHILDREN;
	}
	
	public Object visit(ASTStatementExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;

		SimpleNode lhsNode = (SimpleNode)node.jjtGetChild(0);
		
		// this is prob wrong because we use the full AST now
		if(lhsNode instanceof ASTSequence) {
			return WalkType.NO_CHILDREN;	// TODO: Implement this
		} else if(node.jjtGetNumChildren() == 3) { // assignment statement
			ASTAssignmentOperator assignNode = (ASTAssignmentOperator)node.jjtGetChild(1);
			SimpleNode rhsNode = (SimpleNode)node.jjtGetChild(2);
			
			// link in the LHS & RHS
			TACNode lhsExitNode = lhsNode.getExitNode();
			TACNode rhsExitNode = rhsNode.getExitNode();
			
			linkToEnd(node, lhsNode.getEntryNode(), lhsExitNode);
			linkToEnd(node, rhsNode.getEntryNode(), rhsExitNode);
			
			TACNode assign = null;
			TACVariable lhs = lhsExitNode instanceof TACAssign ? ((TACAssign)lhsExitNode).getTarget() : ((TACNoOp)lhsExitNode).getVariable();
			TACVariable rhs = rhsExitNode instanceof TACAssign ? ((TACAssign)rhsExitNode).getTarget() : ((TACNoOp)rhsExitNode).getVariable();
			
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

			linkToEnd(node, assign);	// link the final assign to the end
			
			node.getEntryNode().dump("STMT EX: ");
		}
		
		return WalkType.NO_CHILDREN;
	}
	
	public Object visit(ASTStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		visitArithmetic(node);
		return WalkType.NO_CHILDREN;
	}
	

	
/*	public Object visit(ASTIfStatement node, Boolean secondVisit) throws ShadowException {
		ConditionalBranch branch = visitComparison((SimpleNode)flatten(node.jjtGetChild(0)));
		TACJoin join = null;
		
		ASTMethodToTAC a2t = new ASTMethodToTAC(node.jjtGetChild(1));
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
			a2t = new ASTMethodToTAC(node.jjtGetChild(2));
			a2t.convert();
			
			TACNode falseEntry = a2t.getEntry();
			TACNode falseExit = a2t.getExit();
			
			branch.falseExit.insertAfter(falseEntry);
			
			join = new TACJoin(trueExit, falseExit);
			
			branch.entryNode.setJoin(join);
		}
		
		linkToEnd(node, branch.entryNode, join);
		
		return WalkType.NO_CHILDREN;
	}
*/	
}
