package shadow.typecheck;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import shadow.parser.AbstractASTVisitor;
import shadow.parser.javacc.*;

public class TypeChecker extends AbstractASTVisitor {

	public static void main(String[] args) {
	    try {
//	        String fileName = "tests/class_test.shadow";
	        String fileName = "src/shadow/parser/test/statements/for.shadow";
	        FileInputStream fis = new FileInputStream(fileName);        
	        ShadowParser parser = new ShadowParser(fis);
	        TypeChecker tc = new TypeChecker();
	        ASTWalker walker = new ASTWalker(tc);
	        
//	        parser.enableDebug();

	        SimpleNode node = parser.CompilationUnit();
	        
	        walker.walk(node);
	        
	        System.out.println("GOOD PARSE");

	    } catch (ParseException e) {
	        System.out.println("BAD PARSE");
	        System.out.println(e.getMessage());
	    } catch (ShadowException se) {
	    	System.out.println("BAD TYPE CHECK");
	    	System.out.println(se.getMessage());
	    } catch (Error e) {
	        System.out.println("Ooops");
	        System.out.println(e.getMessage());
	    } catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	    }       
	}

	@Override
	public Object visit(SimpleNode node, Object data) throws ShadowException {
		return null;
	}

	@Override
	public Object visit(ASTCompilationUnit node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTPackageDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTImportDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTModifiers node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTTypeDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTViewDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTExtendsList node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTImplementsList node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTEnumDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTEnumBody node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTEnumConstant node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTTypeParameters node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTTypeParameter node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTTypeBound node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBody node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBodyDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTFieldDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTVariableDeclarator node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTVariableDeclaratorId node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTVariableInitializer node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTArrayInitializer node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTMethodDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTMethodDeclarator node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTFormalParameters node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTFormalParameter node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTConstructorDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTExplicitConstructorInvocation node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTInitializer node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTType node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTReferenceType node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTStaticArrayType node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTFunctionType node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTClassOrInterfaceType node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTTypeArguments node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTTypeArgument node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTWildcardBounds node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTPrimitiveType node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTResultType node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTResultTypes node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTName node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTNameList node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTAssignmentOperator node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTConditionalExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTConditionalOrExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTConditionalAndExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTInclusiveOrExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTExclusiveOrExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTAndExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTEqualityExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTInstanceOfExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTRelationalExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTShiftExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTRotateExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTAdditiveExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTMultiplicativeExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTUnaryExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTCastExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTPrimaryExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTMemberSelector node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTPrimaryPrefix node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTPrimarySuffix node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTLiteral node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTBooleanLiteral node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTNullLiteral node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTArguments node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTArgumentList node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTAllocationExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTArrayDimsAndInits node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTAssertStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTLabeledStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTBlock node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTBlockStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTLocalVariableDeclaration node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTEmptyStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTStatementExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTSwitchStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTSwitchLabel node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTIfStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTWhileStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTDoStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTForeachStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTForStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTForInit node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTStatementExpressionList node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTForUpdate node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTBreakStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTContinueStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTReturnStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTThrowStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTSynchronizedStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTTryStatement node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTRIGHTROTATE node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTRIGHTSHIFT node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTMemberValuePairs node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTMemberValuePair node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTMemberValue node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTMemberValueArrayInitializer node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTDefaultValue node, Object data) {
		return null;
	}

}
