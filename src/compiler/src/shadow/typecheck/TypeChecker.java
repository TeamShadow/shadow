package shadow.typecheck;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;

import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTLocalVariableDeclaration;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPrimitiveType;
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
	        System.out.println("BAD TYPE CHECK");
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
		curType = null;
		
		return false;
	}
	
	public Object visit(ASTName node, Object data) throws ShadowException {
		boolean found = false;
		
		// look-up the symbol in the table
		for(HashMap<String, String> map:symbolTable) {
			if(map.containsKey(node.getImage())) {
				found = true;
				break;
			}
		}
		
		if(!found)
			throw new ShadowException("Undefined symbol: " + node.getImage());
		
		return false;
	}
	
}
