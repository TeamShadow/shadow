
package shadow.TAC;

import java.util.List;

import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker.WalkType;
import shadow.AST.AbstractASTVisitor;
import shadow.TAC.nodes.TACAllocation;
import shadow.TAC.nodes.TACAssign;
import shadow.TAC.nodes.TACBinaryOperation;
import shadow.TAC.nodes.TACBranch;
import shadow.TAC.nodes.TACJoin;
import shadow.TAC.nodes.TACLoop;
import shadow.TAC.nodes.TACNoOp;
import shadow.TAC.nodes.TACNode;
import shadow.TAC.nodes.TACNode.TACComparison;
import shadow.TAC.nodes.TACNode.TACOperation;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTAllocationExpression;
import shadow.parser.javacc.ASTArrayDimsAndInits;
import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.parser.javacc.ASTBitwiseAndExpression;
import shadow.parser.javacc.ASTBitwiseExclusiveOrExpression;
import shadow.parser.javacc.ASTBitwiseOrExpression;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTBlockStatement;
import shadow.parser.javacc.ASTBooleanLiteral;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTConditionalAndExpression;
import shadow.parser.javacc.ASTConditionalExclusiveOrExpression;
import shadow.parser.javacc.ASTConditionalExpression;
import shadow.parser.javacc.ASTConditionalOrExpression;
import shadow.parser.javacc.ASTEqualityExpression;
import shadow.parser.javacc.ASTExpression;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFormalParameters;
import shadow.parser.javacc.ASTIfStatement;
import shadow.parser.javacc.ASTIsExpression;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTLocalVariableDeclaration;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTMethodDeclarator;
import shadow.parser.javacc.ASTMultiplicativeExpression;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTNullLiteral;
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
import shadow.parser.javacc.ASTWhileStatement;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.type.Type;

public class ASTToTACVisitor extends AbstractASTVisitor {
	private static int tempCounter = 0;	/** Counter for making temporary variables */
	
	/**
	 * Walks a portion of an AST and creates a TAC tree.
	 * This class does all of the heavy lifting in converting from AST -> TAC.
	 */
	public ASTToTACVisitor() {
	}
	
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
		case '|':
			return TACOperation.OR;
		case '&':
			return TACOperation.AND;
		case '^':
			return TACOperation.XOR;
		case 'r':
			return TACOperation.RSHIFT;
		case 'l':
			return TACOperation.LSHIFT;
		case 'R':
			return TACOperation.RROTATE;
		case 'L':
			return TACOperation.LROTATE;
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
		if(symbol.equals("is")) return TACComparison.IS;
		
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
		
		System.out.println("OPERATORS: " + operators);
		
		// link the first two operator's TAC paths into this one
		TACNode op1ExitNode = astOp1.getExitNode();
		TACNode op2ExitNode = astOp2.getExitNode();
		
		linkToEnd(node, astOp1.getEntryNode(), op1ExitNode);
		linkToEnd(node, astOp2.getEntryNode(), op2ExitNode);
		
		// get the first two TACVariables
		TACVariable op1 = op1ExitNode instanceof TACAssign ? ((TACAssign)op1ExitNode).getTarget() : ((TACNoOp)op1ExitNode).getVariable();
		TACVariable op2 = op2ExitNode instanceof TACAssign ? ((TACAssign)op2ExitNode).getTarget() : ((TACNoOp)op2ExitNode).getVariable();
		
		// create the target variable and link it in
		TACVariable target = new TACVariable(getTempSymbol(), node.getType());
		TACAllocation targetAlloc = new TACAllocation(target, op2ExitNode);
		linkToEnd(node, targetAlloc);
		
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
			
			// create a new target and link it in
			target = new TACVariable(getTempSymbol(), node.getType());
			targetAlloc = new TACAllocation(target, null);
			linkToEnd(node, targetAlloc);

			// create the operator and link it in
			newNode = new TACBinaryOperation(target, op1, op2, symbol2Operation(operators.charAt(i-1)));
			linkToEnd(node, newNode);	
		}
	}
	
	/**
	 * Used at the start of most visit methods to link in empty AST nodes.
	 * @param node The node to check.
	 * @return POST_CHILDREN if it should return, PRE_CHILDREN if it's OK
	 */
	private WalkType cleanupNode(Node node) {
		if(node.jjtGetNumChildren() == 1) {
			SimpleNode childNode = (SimpleNode)node.jjtGetChild(0);
			
			// set this node's entry & exit to be the same as it's child
			((SimpleNode)node).setEntryNode(childNode.getEntryNode());
			((SimpleNode)node).setExitNode(childNode.getExitNode());
			return WalkType.POST_CHILDREN;
		}
		
		return WalkType.PRE_CHILDREN;
	}

	/**
	 * Visit a comparison node and create the appropriate assignment
	 * @param node One of ASTEqualityExpression, ASTIsExpression, ASTRelationalExpression
	 */
	public void visitComparison(SimpleNode node) {
		SimpleNode astVar1 = (SimpleNode)node.jjtGetChild(0);
		SimpleNode astVar2 = (SimpleNode)node.jjtGetChild(1);
		
		// link the first two operator's TAC paths into this one
		TACNode var1ExitNode = astVar1.getExitNode();
		TACNode var2ExitNode = astVar2.getExitNode();
		
		linkToEnd(node, astVar1.getEntryNode(), var1ExitNode);
		linkToEnd(node, astVar2.getEntryNode(), var2ExitNode);
		
		// get the first two TACVariables
		TACVariable var1 = var1ExitNode instanceof TACAssign ? ((TACAssign)var1ExitNode).getTarget() : ((TACNoOp)var1ExitNode).getVariable();
		TACVariable var2 = var2ExitNode instanceof TACAssign ? ((TACAssign)var2ExitNode).getTarget() : ((TACNoOp)var2ExitNode).getVariable();
		
		// allocate our return value
		TACVariable ret = new TACVariable(getTempSymbol(), Type.BOOLEAN);
		TACAllocation alloc = new TACAllocation(ret);

		// construct the branch and link it in
		TACBranch branch = new TACBranch(var1, var2, symbol2Comparison(node.getImage()));
		alloc.setNext(branch);
		branch.setParent(alloc);

		// create the branches
		TACAssign trueBranch = new TACAssign(ret, TACVariable.getBooleanLiteral(true));
		TACAssign falseBranch = new TACAssign(ret, TACVariable.getBooleanLiteral(false));
		
		// link in the branches
		branch.setTrueEntry(trueBranch);
		trueBranch.setParent(branch);
		branch.setFalseEntry(falseBranch);
		falseBranch.setParent(branch);
		
		// create our join
		TACJoin join = new TACJoin(trueBranch, falseBranch);
		
		// link in the join
		trueBranch.setNext(join);
		falseBranch.setNext(join);
		
		// create our NoOp to hold our var
		TACNoOp noop = new TACNoOp(ret, join);
		
		join.setNext(noop);
		
		// link into the node
		linkToEnd(node, alloc, noop);
	}
	

	
	//
	// Below here you'll find the various visit methods
	//
	
	public void visitLiteral(SimpleNode node) {
		TACVariable literal = new TACVariable(node.getImage(), node.getType(), true);
		TACNoOp noop = new TACNoOp(literal, null, null);
		
		// set the entry & exit to this node
		((SimpleNode)node).setEntryNode(noop);
		((SimpleNode)node).setExitNode(noop);
	}
	
	public Object visit(ASTBooleanLiteral node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		visitLiteral(node);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTNullLiteral node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		visitLiteral(node);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTLiteral node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		visitLiteral(node);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTAllocationExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		SimpleNode typeNode = (SimpleNode)node.jjtGetChild(0);
		
		// we either have a primitive array or a class (possibly array)
		if(typeNode instanceof ASTPrimitiveType) {
			List<Integer> arrayDims = ((ASTArrayDimsAndInits)node.jjtGetChild(1)).getArrayDimensions();
			
			TACVariable var = new TACVariable("", typeNode.getType(), arrayDims);
			TACAllocation alloc = new TACAllocation(var, null);
			TACNoOp noop = new TACNoOp(var, alloc);
			alloc.setNext(noop);
			
			linkToEnd(node, alloc, noop);
		} else if(typeNode instanceof ASTClassOrInterfaceType) {
			
		} else {
			throw new ShadowException("UNKNOWN TYPE");
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		TACVariable variable = new TACVariable(node.getImage(), node.getType());
		TACNoOp noop = new TACNoOp(variable, null, null);
		
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
		
		ASTUtils.DEBUG(node);

		visitArithmetic(node);
		
		((SimpleNode)node).getEntryNode().dump("ADD");

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTShiftExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;

		ASTUtils.DEBUG(node);

		visitArithmetic(node);
		
		((SimpleNode)node).getEntryNode().dump("ADD");

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTRelationalExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		visitComparison(node);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTIsExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		visitComparison(node);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTEqualityExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		visitComparison(node);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBitwiseAndExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		visitArithmetic(node);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBitwiseExclusiveOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		visitArithmetic(node);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBitwiseOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		visitArithmetic(node);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalAndExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;

		// create our current branch & join, to be made later
		TACBranch curBranch = null;
		TACJoin curJoin = null;
		
		// the entry into this TAC path
		TACNode entryNode = null;
		
		// go through each var in the AND which will be NoOps of stuff further down the AST
		for(int i=0; i < node.jjtGetNumChildren(); ++i) {
			SimpleNode child = (SimpleNode)node.jjtGetChild(i);
			TACVariable curVar = ((TACNoOp)child.getExitNode()).getVariable();
			
			// create the branch
			TACBranch tmpBranch = new TACBranch(curVar, TACVariable.getBooleanLiteral(true), TACComparison.EQUAL);
			
			if(curBranch != null) {
				// link in the child's TAC path
				curBranch.setTrueEntry(child.getEntryNode());
				child.getEntryNode().setParent(curBranch);
				child.getExitNode().setNext(tmpBranch);
				tmpBranch.setParent(child.getExitNode());
			} else {
				// link in the child's TAC path
				entryNode = child.getEntryNode();
				tmpBranch.setParent(child.getExitNode());
				child.getExitNode().setNext(tmpBranch);
			}
			
			curBranch = tmpBranch;
			
			if(curJoin != null) {
				curBranch.setFalseEntry(curJoin);	// the false exit for the branch is really
				curJoin.setTrueExit(curBranch);		// the true exit for the join... seems backwards, it's not
				
				// create the join and link it in
				TACJoin tmpJoin = new TACJoin(null, curJoin);
				curJoin.setNext(tmpJoin);
				curJoin = tmpJoin;
			} else {
				// create the join
				curJoin = new TACJoin(null, curBranch);
				curBranch.setFalseEntry(curJoin);
			}
		}
		
		// create variable and allocation for the result and link it in
		TACVariable tmpVar = new TACVariable(getTempSymbol(), Type.BOOLEAN);
		TACAllocation tmpAlloc = new TACAllocation(tmpVar, null);
		
		entryNode.setParent(tmpAlloc);
		tmpAlloc.setNext(entryNode);

		// create the true and false assigns
		TACAssign trueBranch = new TACAssign(tmpVar, TACVariable.getBooleanLiteral(true));
		TACAssign falseBranch = new TACAssign(tmpVar, TACVariable.getBooleanLiteral(false));

		// link in the true branch
		curBranch.setTrueEntry(trueBranch);
		trueBranch.setParent(curBranch);
		
		// link in the false branch
		curJoin = (TACJoin) curJoin.getFalseExit();	// need to link around the extra join
		curJoin.setNext(falseBranch);
		falseBranch.setParent(curJoin);
		
		// create a new join for the true & false branches, and link in
		curJoin = new TACJoin(trueBranch, falseBranch);
		trueBranch.setNext(curJoin);
		falseBranch.setNext(curJoin);
		
		// create our NoOp to hold our variable
		TACNoOp noop = new TACNoOp(tmpVar, curJoin);
		curJoin.setNext(noop);
		
		// we start with tmpAlloc as it gets linked in above entryNode
		linkToEnd(node, tmpAlloc, noop);
		
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
		
		// create our current branch & join, to be made later
		TACBranch curBranch = null;
		TACJoin curJoin = null;
		
		// the entry into this TAC path
		TACNode entryNode = null;
		
		// go through each var in the AND which will be NoOps of stuff further down the AST
		for(int i=0; i < node.jjtGetNumChildren(); ++i) {
			SimpleNode child = (SimpleNode)node.jjtGetChild(i);
			TACVariable curVar = ((TACNoOp)child.getExitNode()).getVariable();
			
			// create the branch
			TACBranch tmpBranch = new TACBranch(curVar, TACVariable.getBooleanLiteral(true), TACComparison.EQUAL);
			
			if(curBranch != null) {
				// link in the child's TAC path
				curBranch.setFalseEntry(child.getEntryNode());
				child.getEntryNode().setParent(curBranch);
				child.getExitNode().setNext(tmpBranch);
				tmpBranch.setParent(child.getExitNode());
			} else {
				// link in the child's TAC path
				entryNode = child.getEntryNode();
				tmpBranch.setParent(child.getExitNode());
				child.getExitNode().setNext(tmpBranch);
			}
			
			curBranch = tmpBranch;
			
			if(curJoin != null) {
				curBranch.setTrueEntry(curJoin);	// the true exit for the branch is really
				curJoin.setFalseExit(curBranch);	// the false exit for the join... seems backwards, it's not
				
				// create the join and link it in
				TACJoin tmpJoin = new TACJoin(curJoin, null);
				curJoin.setNext(tmpJoin);
				curJoin = tmpJoin;
			} else {
				// create the join
				curJoin = new TACJoin(curBranch, null);
				curBranch.setTrueEntry(curJoin);
			}
		}

		// create variable and allocation for the result and link it in
		TACVariable tmpVar = new TACVariable(getTempSymbol(), Type.BOOLEAN);
		TACAllocation tmpAlloc = new TACAllocation(tmpVar, null);
		
		entryNode.setParent(tmpAlloc);
		tmpAlloc.setNext(entryNode);

		// create the true and false assigns
		TACAssign trueBranch = new TACAssign(tmpVar, TACVariable.getBooleanLiteral(true));
		TACAssign falseBranch = new TACAssign(tmpVar, TACVariable.getBooleanLiteral(false));
		
		// link in the true branch
		curBranch.setFalseEntry(falseBranch);
		falseBranch.setParent(curBranch);
		
		// link in the false branch
		curJoin = (TACJoin) curJoin.getTrueExit();	// need to link around the extra join
		curJoin.setNext(trueBranch);
		trueBranch.setParent(curJoin);
		
		// create a new join for the true & false branches, and link in
		curJoin = new TACJoin(trueBranch, falseBranch);
		trueBranch.setNext(curJoin);
		falseBranch.setNext(curJoin);
		
		// create our NoOp to hold our variable
		TACNoOp noop = new TACNoOp(tmpVar, curJoin);
		curJoin.setNext(noop);
		
		// we start with tmpAlloc as it gets linked in above entryNode
		linkToEnd(node, tmpAlloc, noop);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		// if we got here, we have a: x ? y : z expression
		
		// get the conditional stuff
		SimpleNode conditional = (SimpleNode)node.jjtGetChild(0);
		TACNode conEntry = conditional.getEntryNode();
		TACNode conExit = conditional.getExitNode();
		TACVariable conVar = ((TACNoOp)conExit).getVariable();
		
		// get the stuff for the true branch
		SimpleNode trueBranch = (SimpleNode)node.jjtGetChild(1);
		TACNode trueEntry = trueBranch.getEntryNode();
		TACNode trueExit = trueBranch.getExitNode();
		TACVariable trueVar = trueExit instanceof TACAssign ? ((TACAssign)trueExit).getTarget() : ((TACNoOp)trueExit).getVariable();
		
		// get the stuff for the false branch
		SimpleNode falseBranch = (SimpleNode)node.jjtGetChild(2);
		TACNode falseEntry = falseBranch.getEntryNode();
		TACNode falseExit = falseBranch.getExitNode();
		TACVariable falseVar = falseExit instanceof TACAssign ? ((TACAssign)falseExit).getTarget() : ((TACNoOp)falseExit).getVariable();
		
		// create a temp var to hold the result
		String resVarSymbol = getTempSymbol();

		// create the branch & link it in
		TACBranch branch = new TACBranch(conVar, TACVariable.getBooleanLiteral(true), TACComparison.EQUAL);
		conExit.setNext(branch);
		branch.setParent(conExit);
		
		// create the true branch
		TACVariable trueTmpVar = new TACVariable(resVarSymbol, trueVar.getType());
		TACAllocation trueTmpAlloc = new TACAllocation(trueTmpVar);
		TACAssign trueAssign = new TACAssign(trueTmpVar, trueVar);
		
		// link in the true branch
		branch.setTrueEntry(trueTmpAlloc);
		trueTmpAlloc.setParent(branch);
		trueTmpAlloc.setNext(trueEntry);
		trueEntry.setParent(trueTmpAlloc);
		trueExit.setNext(trueAssign);
		trueAssign.setParent(trueExit);
		
		// create the false branch
		TACVariable falseTmpVar = new TACVariable(resVarSymbol, falseVar.getType());
		TACAllocation falseTmpAlloc = new TACAllocation(falseTmpVar);
		TACAssign falseAssign = new TACAssign(falseTmpVar, falseVar);

		
		// link in the false branch
		branch.setFalseEntry(falseTmpAlloc);
		falseTmpAlloc.setParent(branch);
		falseTmpAlloc.setNext(falseEntry);
		falseEntry.setParent(falseTmpAlloc);
		falseExit.setNext(falseAssign);
		falseAssign.setParent(falseExit);
		
		// create the join for this branch and link it in
		TACJoin join = new TACJoin(trueAssign, falseAssign);
		trueAssign.setNext(join);
		falseAssign.setNext(join);
		
		// not really sure which I should pick here
		TACNoOp retVar = new TACNoOp(new TACVariable(resVarSymbol, trueVar.getType()), join);
		join.setNext(retVar);
		
		// link in the path
		linkToEnd(node, conEntry, retVar);
		
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
			TACAllocation alloc = new TACAllocation(var);
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
			
			// link the alloc & assign
			alloc.setNext(assign);
			assign.setParent(alloc);
			
			// link to the end
			linkToEnd(node, alloc, assign);
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
		
		((SimpleNode)node).getEntryNode().dump("ADD");
		
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
	
	public Object visit(ASTIfStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		// get the conditional stuff
		SimpleNode conditional = (SimpleNode)node.jjtGetChild(0);
		TACNode conEntry = conditional.getEntryNode();
		TACNode conExit = conditional.getExitNode();
		TACVariable conVar = ((TACNoOp)conExit).getVariable();
		
		// get the stuff for the true branch
		SimpleNode trueBranch = (SimpleNode)node.jjtGetChild(1);
		TACNode trueEntry = trueBranch.getEntryNode();
		TACNode trueExit = trueBranch.getExitNode();
		
		// create the branch
		TACBranch branch = new TACBranch(conVar, TACVariable.getBooleanLiteral(true), TACComparison.EQUAL);

		// link the branch in
		conExit.setNext(branch);
		branch.setParent(conExit);
		branch.setTrueEntry(trueEntry);
		trueEntry.setParent(branch);
		
		// create the join for this branch and link it in
		TACJoin join = new TACJoin(trueExit, null);
		trueExit.setNext(join);
		
		if(node.jjtGetNumChildren() == 2) {
			// just link the branch & join, no false path
			join.setFalseExit(branch);
			branch.setFalseEntry(join);
		} else { // we have an else path
			// get the stuff for the false branch
			SimpleNode falseBranch = (SimpleNode)node.jjtGetChild(2);
			TACNode falseEntry = falseBranch.getEntryNode();
			TACNode falseExit = falseBranch.getExitNode();
			
			// link in the branch & join
			branch.setFalseEntry(falseEntry);
			falseEntry.setParent(branch);
			join.setFalseExit(falseExit);
			falseExit.setNext(join);
		}
		
		// link in the path
		linkToEnd(node, conEntry, join);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTWhileStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit || cleanupNode(node) == WalkType.POST_CHILDREN)
			return WalkType.POST_CHILDREN;
		
		// get the conditional stuff
		SimpleNode conditional = (SimpleNode)node.jjtGetChild(0);
		TACNode conEntry = conditional.getEntryNode();
		TACNode conExit = conditional.getExitNode();
		TACVariable conVar = ((TACNoOp)conExit).getVariable();
		
		// link in the conditional
		linkToEnd(node, conEntry, conExit);
		
		// get the stuff for the loop
		SimpleNode loopBranch = (SimpleNode)node.jjtGetChild(1);
		TACNode loopEntry = loopBranch.getEntryNode();
		TACNode loopExit = loopBranch.getExitNode();
		
		// create the loop node
		TACLoop loop = new TACLoop(conVar, TACComparison.EQUAL, TACVariable.getBooleanLiteral(true));
		
		// link in the entry and exit for the loop
		loop.setLoopNode(loopEntry);
		loopEntry.setParent(loop);
		loopExit.setNext(loop);
		
		// we need a NoOp for the exit of our loop so it links in properly
		TACNoOp noop = new TACNoOp(loop, null);
		loop.setBreakNode(noop);
		
		loop.dump("LOOP: ");
		
		linkToEnd(node, loop, noop);

		return WalkType.POST_CHILDREN;
	}
	
}
