package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.parse.Context;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.ModifiedType;

/**
 * Abstract base class for checkers that need information about method
 * and variable scope.
 * 
 * @author Barry Wittman
 */
public abstract class ScopedChecker extends BaseChecker {

	/* List of scopes with a hash of symbols & types for each scope. */
	private LinkedList<Map<String, ModifiedType>> symbolTable;
	/* Keeps track of the method associated with each scope (sometimes null). */
	private LinkedList<Context> scopeMethods;
	/* Whether or not we are currently inside a decorator. */
	private boolean decoratorScope;
	
	
	public ScopedChecker(Package packageTree, ErrorReporter reporter) {
		super(packageTree, reporter);
		symbolTable = new LinkedList<>();
		scopeMethods = new LinkedList<>();
	}
	
	/**
	 * Open a new scope inside the current scope.
	 */
	protected void openScope() {
		// We have a new scope, so we need a new HashMap in the linked list.
		symbolTable.addFirst(new HashMap<String, ModifiedType>());
		
		if( currentMethod.isEmpty() )
			scopeMethods.addFirst(null);
		else
			scopeMethods.addFirst(currentMethod.getFirst());		
	}
	
	/**
	 * Close the current scope.
	 */
	protected void closeScope() {		
		symbolTable.removeFirst();
		scopeMethods.removeFirst();
	}
	
	protected List<Map<String,ModifiedType>> getSymbolTable() {
		return symbolTable;
	}
	
	/**
	 * Add a new symbol (variable) to the current scope with the given modified type.
	 * The modified type of the symbol is usually the Context (AST node) representing it.
	 * 
	 * @param name the name of the symbol
	 * @param type the modified type of the symbol
	 */
	protected void addSymbol( String name, ModifiedType type ) {	
		if( symbolTable.size() == 0 ) {
			if( type instanceof Context)
				addError((Context)type, Error.INVALID_STRUCTURE, "Declaration of " + name + " is illegal outside of a defined scope");
			else
				addError(new TypeCheckException(Error.INVALID_STRUCTURE, "Declaration of " + name + " is illegal outside of a defined scope"));
		}
		else {
			boolean found = false;
		
			for( Map<String, ModifiedType> scope : symbolTable ) {			
				if( scope.containsKey( name ) ) { //we look at all enclosing scopes
					if( type instanceof Context)
						addError((Context)type, Error.MULTIPLY_DEFINED_SYMBOL, "Symbol " + name + " cannot be redefined in this context");
					else
						addError(new TypeCheckException(Error.MULTIPLY_DEFINED_SYMBOL, "Symbol " + name + " cannot be redefined in this context"));
					found = true;
					break;
				}
			}
			
			if( !found )			
				symbolTable.getFirst().put(name, type);  // Uses node for modifiers
		}
	}
	
	/**
	 * Find a symbol (variable) with a given name, searching from the current
	 * scope back through enclosing scopes.
	 * 
	 * @param name symbol name
	 * @return modified type (usually Context AST node) associated with the symbol or null if not found 
	 */
	protected ModifiedType findSymbol( String name ) {
		ModifiedType type = null;
		for( int i = 0; i < symbolTable.size(); i++ ) {
			Map<String,ModifiedType> map = symbolTable.get(i);		
			if( (type = map.get(name)) != null ) {
				Context method = scopeMethods.get(i);
				if( method != null && method != currentMethod.getFirst() ) {
					//situation where we are pulling a variable from an outer method
					//it must be final!
					//local method declarations don't count
					
					//TODO: add a check to deal with this, even without final
					
					//if( !(node instanceof ASTLocalMethodDeclaration) && !node.getModifiers().isFinal() )
					//	addError(Error.INVL_TYP, "Variables accessed by local methods from outer methods must be marked final");
				}
				return type;
			}
		}		
		
		return type;
	}
	
	protected void setDecoratorScope(boolean value) {
		decoratorScope = value;
	}
	
	protected boolean isDecoratorScope() {
		return decoratorScope;
	}
}
