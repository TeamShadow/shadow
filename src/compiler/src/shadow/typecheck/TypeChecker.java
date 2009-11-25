package shadow.typecheck;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;

import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTEqualityExpression;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTLocalVariableDeclaration;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPrimitiveType;
import shadow.parser.javacc.ASTRelationalExpression;
import shadow.parser.javacc.ASTVariableDeclaratorId;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.SimpleNode;

public class TypeChecker extends AbstractASTVisitor {

	public static void main(String[] args) {
	    try {
	        String fileName = "src/shadow/typecheck/test/basic.shadow";
	        FileInputStream fis = new FileInputStream(fileName);        
	        ShadowParser parser = new ShadowParser(fis);
	        TypeChecker tc = new TypeChecker();
	        ASTWalker walker = new ASTWalker(tc);
	        SimpleNode node = parser.CompilationUnit();
	        
	        node.dump("");
	        
	        walker.walk(node);
	        
	        System.out.println("GOOD TYPE CHECK");

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
	
	protected LinkedList<HashMap<String, String>> symbolTable;
	protected Node curRoot = null;
	
	public TypeChecker() {
		symbolTable = new LinkedList<HashMap<String, String>>();
		symbolTable.add(new HashMap<String, String>());
	}

	public Object visit(ASTBlock node, Object secondVisit) throws ShadowException {
		// we have a new scope, so we need a new HashMap in the linked list
		if((Boolean)secondVisit)
			symbolTable.removeFirst();
		else
			symbolTable.addFirst(new HashMap<String, String>());
		
		return true;
	}
	
	public void addVarDec(SimpleNode node) throws ShadowException {
		// a field dec has a type followed by 1 or more idents
		String type = node.jjtGetChild(0).getImage();
		
		// go through inserting all the idents
		for(int i=1; i < node.jjtGetNumChildren(); ++i) {
			String symbol = node.jjtGetChild(i).getImage();
			
			// make sure we don't already have this symbol
			if(symbolTable.getFirst().containsKey(symbol))
				throw new ShadowException("Multiply defined symbol");
			
			System.out.println("ADDING: " + type + " " + symbol);
			symbolTable.getFirst().put(symbol, type);
		}
	}
	
	public Object visit(ASTFieldDeclaration node, Object secondVisit) throws ShadowException {
		addVarDec(node);
		
		return false;
	}

	public Object visit(ASTLocalVariableDeclaration node, Object secondVisit) throws ShadowException {		
		addVarDec(node);
		
		return false;
	}
	
	public Object visit(ASTName node, Object data) throws ShadowException {
		
		return false;
	}
	
	public Object visit(ASTRelationalExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		// get the two types
		String t1 = node.jjtGetChild(0).getType();
		String t2 = node.jjtGetChild(1).getType();
		
		// TODO: Add in all the types that we can compare here
		if(!t1.equals("int") && !t1.equals("double") && !t1.equals("float"))
			throw new ShadowException("INCORRECT TYPE: " + t1 + " used in relation");
		
		if(!t2.equals("int") && !t2.equals("double") && !t2.equals("float"))
			throw new ShadowException("INCORRECT TYPE: " + t2 + " used in relation");
		
		node.setType("Boolean");	// relations are always booleans
		
		return false;
	}
	
	public Object visit(ASTEqualityExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		// get the two types
		String t1 = node.jjtGetChild(0).getType();
		String t2 = node.jjtGetChild(1).getType();
		
		// TODO: Add in subtyping
		if(!t1.equals(t2))
			throw new ShadowException("TYPE MISMATCH: " + t1 + " and " + t2 + " are not comparable");
		
		node.setType("Boolean");	// relations are always booleans
		
		return false;
	}

	/**
	 * Walks through all the symbol tables looking for the first occurrence of this symbol
	 * TODO: We'll need to add proper scoping not just first symbol to this
	 * @param symbol The symbol to lookup
	 * @return The type or null if not found
	 */
	public String getType(String symbol) {
		// go through all the symbols looking for this one
		for(HashMap<String, String> map:symbolTable) {
			if(map.containsKey(symbol))
				return map.get(symbol);
		}
		
		return null;
	}
	
}
