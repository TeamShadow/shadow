package shadow.typecheck;

import shadow.interpreter.InterpreterException;
import shadow.parse.Context;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.*;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Abstract base class for checkers that need information about method and variable scope.
 *
 * @author Barry Wittman
 */
public abstract class ScopedChecker extends BaseChecker {
  private final Deque<Scope> scopes = new LinkedList<>();

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

  /** Open a new scope inside the current scope. */
  protected void openScope() {
    scopes.addFirst(new Scope(currentMethod.isEmpty() ? null : currentMethod.getFirst()));
  }

  /** Close the current scope. */
  protected void closeScope() {
    scopes.pop();
  }

  /**
   * Add a new symbol (variable) to the current scope with the given modified type. The modified
   * type of the symbol is usually the Context (AST node) representing it.
   *
   * @param name the name of the symbol
   * @param type the modified type of the symbol
   */
  protected void addSymbol(String name, ModifiedType type) {
    if (scopes.isEmpty()) {
      if (type instanceof Context)
        addError(
            (Context) type,
            Error.INVALID_STRUCTURE,
            "Declaration of " + name + " is illegal outside of a defined scope");
      else
        addError(
            new TypeCheckException(
                Error.INVALID_STRUCTURE,
                "Declaration of " + name + " is illegal outside of a defined scope"));
    } else {
      for (Scope scope : scopes) {
        if (scope.containsSymbol(name)) { // we look at all enclosing scopes
          if (type instanceof Context)
            addError(
                (Context) type,
                Error.MULTIPLY_DEFINED_SYMBOL,
                "Symbol " + name + " cannot be redefined in this context");
          else
            addError(
                new TypeCheckException(
                    Error.MULTIPLY_DEFINED_SYMBOL,
                    "Symbol " + name + " cannot be redefined in this context"));
          return;
        }
      }

      scopes.getFirst().addSymbol(name, type);
    }
  }

  public boolean setTypeFromContext(Context node, String name, Type context) {
    if (context instanceof TypeParameter) {
      TypeParameter typeParameter = (TypeParameter) context;
      for (Type type : typeParameter.getBounds())
        if (setTypeFromContext(node, name, type)) return true;

      return setTypeFromContext(node, name, typeParameter.getClassBound());
    } else {
      Modifiers methodModifiers = Modifiers.NO_MODIFIERS;
      if (!currentMethod.isEmpty()) methodModifiers = currentMethod.getFirst().getModifiers();

      // Check fields first
      if (context.containsField(name)) {
        Context field = context.getField(name);
        node.setType(field.getType());
        node.addModifiers(field.getModifiers());

        if (!fieldIsAccessible(field, currentType))
          addError(
                  field, Error.ILLEGAL_ACCESS, "Field " + name + " not accessible from this context");
        else {
          if (methodModifiers.isImmutable() || methodModifiers.isReadonly())
            node.getModifiers().upgradeToTemporaryReadonly();
          else node.addModifiers(Modifiers.ASSIGNABLE);
        }

        return true;
      }

      // Next check methods
      if (context.recursivelyContainsMethod(name)) {
        node.setType(new UnboundMethodType(name, context));
        if (methodModifiers != null && methodModifiers.isImmutable())
          node.addModifiers(Modifiers.IMMUTABLE);
        else if (methodModifiers != null && methodModifiers.isReadonly())
          node.addModifiers(Modifiers.READONLY);
        return true;
      }

      // Finally check constants
      if (context.recursivelyContainsConstant(name)) {
        Context field = context.recursivelyGetConstant(name);
        node.setType(field.getType());
        node.addModifiers(field.getModifiers());

        if (!fieldIsAccessible(field, currentType))
          addError(
                  field,
                  Error.ILLEGAL_ACCESS,
                  "Constant " + name + " not accessible from this context");

        return true;
      }
    }

    return false;
  }


  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  public static boolean fieldIsAccessible(Context field, Type type) {
    // Constants are not all public
    if (field.getModifiers().isPublic()) return true;

    // If inside class
    Type checkedType = type.getTypeWithoutTypeArguments();
    Type enclosing = field.getEnclosingType().getTypeWithoutTypeArguments();

    while (checkedType != null) {
      if (enclosing.equals(checkedType)) return true;
      checkedType = checkedType.getOuter();
    }

    checkedType = type.getTypeWithoutTypeArguments();
    if (field.getModifiers().isProtected() && checkedType instanceof ClassType) {
      ClassType classType = ((ClassType) checkedType).getExtendType();
      while (classType != null) {
        if (enclosing.equals(classType)) {
          return true;
        }

        classType = classType.getExtendType();
      }
    }

    return false;
  }

  public boolean setTypeFromName(Context node, String name) {
    // next go through the scopes trying to find the variable
    ModifiedType declaration = findSymbol(name);

    if (declaration != null) {
      node.setType(declaration.getType());
      node.addModifiers(declaration.getModifiers());
      node.addModifiers(Modifiers.ASSIGNABLE);
      return true;
    }

    // now check the parameters of the methods
    MethodType methodType;

    for (Context method : currentMethod) {
      methodType = (MethodType) method.getType();

      if (methodType != null && methodType.containsParam(name)) {
        node.setType(methodType.getParameterType(name).getType());
        node.addModifiers(methodType.getParameterType(name).getModifiers());
        node.addModifiers(
                Modifiers
                        .ASSIGNABLE); // is this right?  Shouldn't all method parameters be unassignable?
        return true;
      }
    }

    // check to see if it's a field or a method
    if (setTypeFromContext(node, name, currentType)) return true;

    // is it a type?
    Type type = lookupType(node, name);

    if (type != null) {
      currentType.addUsedType(type);
      node.setType(type);
      node.addModifiers(Modifiers.TYPE_NAME);
      return true;
    }

    return false;
  }

  /**
   * Similar to addSymbol() but updates a symbol that already exists. Useful for interpretation.
   * The symbol must already exist.
   *
   * @param name the name of the symbol
   * @param type the modified type of the symbol
   */

  public void setSymbol(String name, ModifiedType type) {
    if (scopes.isEmpty()) {
      if (type instanceof Context)
        addError(
                (Context) type,
                Error.INVALID_STRUCTURE,
                "Declaration of " + name + " is illegal outside of a defined scope");
      else
        addError(
                new TypeCheckException(
                        Error.INVALID_STRUCTURE,
                        "Declaration of " + name + " is illegal outside of a defined scope"));
    } else {
      for (Scope scope : scopes) {
        if (scope.containsSymbol(name)) { // we look at all enclosing scopes
          scope.addSymbol(name, type);
          return;
        }
      }
      addError(new InterpreterException(InterpreterException.Error.UNDEFINED_SYMBOL, "Variable" + name + " has not been declared"));
    }
  }


  /**
   * Find a symbol (variable) with a given name, searching from the current scope back through
   * enclosing scopes.
   *
   * @param name symbol name
   * @return modified type (usually Context AST node) associated with the symbol or null if not
   *     found
   */
  public ModifiedType findSymbol(String name) {
    for (Scope scope : scopes) {
      if (scope.containsSymbol(name)) {
        //noinspection StatementWithEmptyBody
        if (scope.getEnclosingMethod() != null
            && scope.getEnclosingMethod() != scopes.getFirst().getEnclosingMethod()) {
          // situation where we are pulling a variable from an outer method
          // it must be final!
          // local method declarations don't count

          // TODO: add a check to deal with this, even without final

          // if( !(node instanceof ASTLocalMethodDeclaration) && !node.getModifiers().isFinal() )
          //	addError(Error.INVL_TYP, "Variables accessed by local methods from outer methods must
          // be marked final");
        }
        return scope.getSymbol(name);
      }
    }

    return null;
  }
}
