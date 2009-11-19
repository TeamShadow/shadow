package shadow.TAC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTPrimitiveType;
import shadow.parser.javacc.ASTVariableDeclaratorId;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.ASTWalker;
import shadow.typecheck.AbstractASTVisitor;

public class TACBuilder extends AbstractASTVisitor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    try {
	        String fileName = "src/shadow/typecheck/test/basic.shadow";
	        FileInputStream fis = new FileInputStream(fileName);        
	        ShadowParser parser = new ShadowParser(fis);
	        TACBuilder tac = new TACBuilder();
	        ASTWalker walker = new ASTWalker(tac);
	        
//	        parser.enableDebug();

	        SimpleNode node = parser.CompilationUnit();
	        
	        node.dump("");
	        
	        walker.walk(node);
	        
	        tac.dump(tac.getRoot(), " ");
	        
	        System.out.println("GOOD TAC BUILD");

	    } catch (ParseException e) {
	        System.out.println("BAD PARSE");
	        System.out.println(e.getMessage());
	    } catch (ShadowException se) {
	    	System.out.println("BAD TAC BUILD");
	    	System.out.println(se.getMessage());
	    } catch (Error e) {
	        System.out.println("Ooops");
	        System.out.println(e.getMessage());
	    } catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	    }       
	}

	protected TACNode root;
	protected TACNode curNode;
	
	public TACBuilder() {
		curNode = null;
		root = null;
	}
	
	public TACNode getRoot() {
		return root;
	}
	
	public void dump(TACNode node, String prefix) {
		System.out.println(prefix + node.toString());
		
		if(node instanceof TACClass) {
			TACClass c = (TACClass)node;
			
			System.out.println(prefix + "MEMBERS: ");
			for(TACVariable v:c.getMembers())
				dump(v, prefix + " ");
			
			System.out.println(prefix + "METHODS: ");
			for(TACMethod m:c.getMethods())
				dump(m, prefix + " ");
			
			System.out.println(prefix + "CLASSES: ");
			for(TACClass cs:c.getClasses())
				dump(cs, prefix + " ");
		} else if(node instanceof TACBranch) {
			TACBranch b = (TACBranch)node;
			
			System.out.println(prefix + "TRUE BRANCH: ");
			for(TACNode n:b.getTrueBranch())
				dump(n, prefix + " ");
			
			System.out.println(prefix + "FALSE BRANCH: ");
			for(TACNode n:b.getFalseBranch())
				dump(n, prefix + " ");
		}
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Object secondVisit) throws ShadowException {
		// this is our first node
		if(root == null) {
			root = new TACClass(node.getImage(), null);
			curNode = root;
		} else {	// inner class
			TACClass c = new TACClass(node.getImage(), curNode);
			((TACClass)curNode).addClass(c);
			curNode = c;
		}
		
		((TACClass)curNode).setModifier(node.getModifiers());
		
		return false;	// don't want to visit again
	}

	public Object visit(ASTFieldDeclaration node, Object secondVisit) throws ShadowException {
		if(!(Boolean)secondVisit) {
			TACVariable v = new TACVariable("", curNode);	// real name set later
			curNode = v;	// set this to the current node while we get the children
		} else {
			TACVariable v = (TACVariable)curNode;

			curNode = curNode.getParent(); // move off this back to the class
			
			((TACClass)curNode).addMember(v);
		}		
		
		return true;
	}

	public Object visit(ASTPrimitiveType node, Object secondVisit) throws ShadowException {
		if(curNode instanceof TACVariable) {
			TACVariable v = (TACVariable)curNode;
			
			v.setType(node.getImage());
		}
		return false;
	}
	
	public Object visit(ASTVariableDeclaratorId node, Object secondVisit) throws ShadowException {
		TACVariable v = (TACVariable)curNode;
		
		v.setName(node.getImage());
		
		return false;
	}
}
