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
	        
//	        parser.enableDebug();

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
	protected String curSymbol = null;
	protected String curType = null;
	protected Node curRoot = null;
	protected LinkedList<String> children = null;
	
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
	
	public Object visit(ASTFieldDeclaration node, Object secondVisit) throws ShadowException {		
		if((Boolean)secondVisit)
			curRoot = null;
		else
			curRoot = node;
		return true;
	}
	
	public Object visit(ASTLocalVariableDeclaration node, Object secondVisit) throws ShadowException {		
		if((Boolean)secondVisit)
			curRoot = null;
		else
			curRoot = node;
		return true;
	}
	
	public Object visit(ASTPrimitiveType node, Object data) throws ShadowException {
		
		if(curRoot != null)
			curType = node.getImage();	// get the type
		return false;
	}
	
	public Object visit(ASTVariableDeclaratorId node, Object data) throws ShadowException {
		if(curRoot == null)
				return false;
		
		curSymbol = node.getImage();	// get the symbol
		
		// make sure we don't already have this symbol
		if(symbolTable.getFirst().containsKey(curSymbol))
			throw new ShadowException("Multiply defined symbol");
		
		if(curType == null)
			throw new ShadowException("We have a symbol but no type for it");
		
		// insert our symbols
		symbolTable.getFirst().put(curSymbol, curType);
		
		System.out.println("ADDED: " + curType + " " + curSymbol);
		
		// reset our vars
		curSymbol = null;
		
		return false;
	}
	
	public Object visit(ASTName node, Object data) throws ShadowException {
		String type = getType(node.getImage());
		
		if(type == null)
			throw new ShadowException("Undefined symbol: " + node.getImage());
		
		// check if someone is looking for children
		if(children != null)
			children.add(getType(node.getImage()));
		
		return false;
	}
	
	public Object visit(ASTLiteral node, Object data) throws ShadowException {
		// check if someone is looking for children
		if(children != null)
			children.add(node.getType().getTypeName());
		
		return false;
	}
	
	public Object visit(ASTRelationalExpression node, Object secondVisit) throws ShadowException {
		if(!(Boolean)secondVisit) {
			if(node.jjtGetNumChildren() == 1)
				return false;	// we don't need to do any of this
			children = new LinkedList<String>();	// create our children list
		} else {
			checkChildrenTypes();	// make sure all the children are the same type
			children = null;	// reset
		}
		return true;
	}
	
	public Object visit(ASTEqualityExpression node, Object secondVisit) throws ShadowException {
		if(!(Boolean)secondVisit) {
			if(node.jjtGetNumChildren() == 1)
				return false;	// we don't need to do any of this
			children = new LinkedList<String>();	// create our children list
		} else {
			checkChildrenTypes();	// make sure all the children are the same type
			children = null;		// reset
		}
		return true;
	}
	
	/**
	 * Goes through all of the children and ensure they are of the same type.
	 * @throws ShadowException If one of the children's types does not match
	 */
	protected void checkChildrenTypes() throws ShadowException {
		// go through all the names and make sure they're the same type
		String firstType = children.get(0);
		
		for(int i=0; i < children.size(); ++i) {
			if(!firstType.equals(children.get(i)))
				throw new ShadowException("Type mismatch: " + firstType + " != " + children.get(i));
		}
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
