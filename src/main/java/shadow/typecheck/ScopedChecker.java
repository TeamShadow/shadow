package shadow.typecheck;

import java.util.*;

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
	private final Deque<Scope> scopes = new LinkedList<>();

	/* Whether or not we are currently inside a decorator. */
	private boolean decoratorScope;

	/** Contains all metadata associated with a given scope */
	private static class Scope {
		private final Map<String, ModifiedType> symbolTable = new HashMap<>();

		/* Keeps track of the method associated with each scope (sometimes null). */
		private final Context enclosingMethod;

		public Scope(Context enclosingMethod) {
			this.enclosingMethod = enclosingMethod;
		}

		public Context getEnclosingMethod() {
			return enclosingMethod;
		}

		public boolean containsSymbol(String name) {
			return symbolTable.containsKey(name);
		}

		public ModifiedType getSymbol(String name) {
			return symbolTable.get(name);
		}

		public void addSymbol(String name, ModifiedType type) {
			symbolTable.put(name, type);
		}
	}
	
	
	public ScopedChecker(Package packageTree, ErrorReporter reporter) {
		super(packageTree, reporter);
	}
	
	/**
	 * Open a new scope inside the current scope.
	 */
	protected void openScope() {
		scopes.addFirst(new Scope(currentMethod.isEmpty() ? null : currentMethod.getFirst()));
	}
	
	/**
	 * Close the current scope.
	 */
	protected void closeScope() {
		scopes.pop();
	}
	
	/**
	 * Add a new symbol (variable) to the current scope with the given modified type.
	 * The modified type of the symbol is usually the Context (AST node) representing it.
	 * 
	 * @param name the name of the symbol
	 * @param type the modified type of the symbol
	 */
	protected void addSymbol( String name, ModifiedType type ) {	
		if(scopes.isEmpty()) {
			if( type instanceof Context)
				addError((Context)type, Error.INVALID_STRUCTURE, "Declaration of " + name + " is illegal outside of a defined scope");
			else
				addError(new TypeCheckException(Error.INVALID_STRUCTURE, "Declaration of " + name + " is illegal outside of a defined scope"));
		}
		else {
			for(Scope scope : scopes) {
				if( scope.containsSymbol( name ) ) { //we look at all enclosing scopes
					if( type instanceof Context)
						addError((Context)type, Error.MULTIPLY_DEFINED_SYMBOL, "Symbol " + name + " cannot be redefined in this context");
					else
						addError(new TypeCheckException(Error.MULTIPLY_DEFINED_SYMBOL, "Symbol " + name + " cannot be redefined in this context"));
					return;
				}
			}

			scopes.getFirst().addSymbol(name, type);
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
		for (Scope scope : scopes) {
			if (scope.containsSymbol(name)) {
				if (scope.getEnclosingMethod() != null && scope.getEnclosingMethod() != scopes.getFirst().getEnclosingMethod()) {
					//situation where we are pulling a variable from an outer method
					//it must be final!
					//local method declarations don't count

					//TODO: add a check to deal with this, even without final

					//if( !(node instanceof ASTLocalMethodDeclaration) && !node.getModifiers().isFinal() )
					//	addError(Error.INVL_TYP, "Variables accessed by local methods from outer methods must be marked final");
				}
				return scope.getSymbol(name);
			}
		}

		return null;
	}
	
	protected void setDecoratorScope(boolean value) {
		decoratorScope = value;
	}
	
	protected boolean isDecoratorScope() {
		return decoratorScope;
	}
}
