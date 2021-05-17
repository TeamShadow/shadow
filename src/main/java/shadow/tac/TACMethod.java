package shadow.tac;

import shadow.Configuration;
import shadow.ShadowException;
import shadow.interpreter.ShadowUndefined;
import shadow.output.text.TextOutput;
import shadow.tac.nodes.*;
import shadow.typecheck.type.*;

import java.io.StringWriter;
import java.util.*;

public class TACMethod {

  public class TACFinallyFunction {

    private TACNode node;
    private final int number;
    private final TACFinallyFunction outerFinallyFunction;
    private TACReturn return_;
    private TACVariable exceptionStorage;

    public TACFinallyFunction(TACFinallyFunction outerFinallyFunction) {
      this.outerFinallyFunction = outerFinallyFunction;
      number = finallyFunctions.size();
      finallyFunctions.add(this);
    }

    public TACFinallyFunction getOuterFinallyFunction() {
      return outerFinallyFunction;
    }

    public void setReturn(TACReturn return_) {
      this.return_ = return_;
    }

    public TACVariable getExceptionStorage() {
      return exceptionStorage;
    }

    public void setExceptionStorage(TACVariable variable) {
      exceptionStorage = variable;
    }

    public TACReturn getReturn() {
      return return_;
    }

    public TACNode getNode() {
      return node;
    }

    public void setNode(TACNode node) {
      this.node = node;
    }

    public MethodSignature getSignature() {
      return TACMethod.this.getSignature();
    }

    public int getNumber() {
      return number;
    }
  }

  private final MethodSignature signature;
  private final Map<String, TACVariable> locals;
  private final Map<String, TACVariable> parameters;
  private final Deque<Map<String, TACVariable>> scopes;
  private int labelCounter = 0; // counter to keep label numbering unique
  private int variableCounter = 0; // counter to keep variable number unique
  private TACNode node;
  private TACFinallyFunction cleanupFinallyFunction;
  private final List<TACFinallyFunction> finallyFunctions = new ArrayList<>();

  public TACMethod(MethodSignature methodSignature) {
    signature = methodSignature;
    locals = new LinkedHashMap<>();
    parameters = new LinkedHashMap<>();
    scopes = new LinkedList<>();
    enterScope();
  }

  public TACNode getNode() {
    return node;
  }

  public void setNode(TACNode node) {
    this.node = node;
  }

  public TACMethod addParameters(TACNode node, boolean isWrapped) {
    Type prefixType = signature.getOuter();
    int parameter = 0;

    if (prefixType instanceof InterfaceType) prefixType = Type.OBJECT;

    ModifiedType modifiedType = new SimpleModifiedType(prefixType);
    // We mark the primitive "this" as nullable, to show that it's the object version of the
    // primitive
    if (prefixType.isPrimitive() && (signature.isCreate() || isWrapped))
      modifiedType.getModifiers().addModifier(Modifiers.NULLABLE);

    if (signature.isCreate())
      new TACLocalStore(
          node,
          addParameter(modifiedType, "this"),
          TACCast.cast(
              node,
              modifiedType,
              new TACParameter(node, new SimpleModifiedType(Type.OBJECT), parameter++)));
    else if (!signature.isImport())
      new TACLocalStore(
          node,
          addParameter(modifiedType, "this"),
          new TACParameter(node, modifiedType, parameter++));

    MethodType type = signature.getMethodType();
    if (isWrapped) type = signature.getSignatureWithoutTypeArguments().getMethodType();

    for (String name : type.getParameterNames()) {
      ModifiedType parameterType = type.getParameterType(name);
      new TACLocalStore(
          node,
          addParameter(parameterType, name),
          new TACParameter(node, parameterType, parameter++));
    }

    if (!signature.isCreate()) {
      SequenceType returnTypes = signature.getFullReturnTypes();

      // create variable to hold returns, needed for GC cleanup before return
      if (returnTypes.size() == 1) {
        TACVariable variable = addLocal(returnTypes.get(0), "return");
        // non-GC variables use SSA, which needs an initial storage for phi nodes
        if (!variable.needsGarbageCollection())
          new TACLocalStore(
              node, variable, new TACLiteral(node, new ShadowUndefined(variable.getType())));
      } else if (returnTypes.size() > 1) {
        for (int i = 0; i < returnTypes.size(); ++i) {
          TACVariable variable = addLocal(returnTypes.get(i), "_return" + i);
          // non-GC variables use SSA, which needs an initial storage for phi nodes
          if (!variable.needsGarbageCollection())
            new TACLocalStore(
                node, variable, new TACLiteral(node, new ShadowUndefined(variable.getType())));
        }
      }
    }

    return this;
  }

  private void addCleanup(
      Set<TACVariable> storedVariables, Set<TACCallFinallyFunction> cleanupCalls) {

    // First node is label, second is return
    // Use the return as the anchor
    TACNode anchor = cleanupFinallyFunction.getNode().getNext();
    boolean addedDecrements = false;

    // Add each decrement before the last thing (the indirect branch)
    for (TACVariable variable : storedVariables)
      // Return doesn't get cleaned up, so that it has an extra reference count
      if (!variable.isReturn()) {
        new TACChangeReferenceCount(anchor, variable, false);
        addedDecrements = true;
      }

    // Get rid of finally function calls if nothing happens inside
    if (!addedDecrements) {
      for (TACCallFinallyFunction call : cleanupCalls) call.remove();

      removeFinallyFunction(cleanupFinallyFunction);
      cleanupFinallyFunction = null;
    }
  }

  void addGarbageCollection() {
    // only stored variables needed to have their reference counts decremented at the end of the
    // method
    Set<TACVariable> storedVariables = new HashSet<>();

    TACNode start = getNode();

    // Keep track of all the initial parameter stores
    // if a method parameter is never stored again (which is the typical case),
    // then we will neither need to increment it nor clean it up
    Map<TACVariable, TACParameter> parameterStores = new HashMap<>();
    Set<TACCallFinallyFunction> cleanupCalls = new HashSet<>();

    // fix this!  Run stores in a separate loop, after arrays and calls?
    boolean changed = true;
    while (changed) {

      changed = false;

      TACNode node = start.getNext();
      while (node != start) {
        TACNode next = node.getNext();
        // Update changed only if newly garbage collected
        // and creating new things that might need to be garbage collected

        if (!node.isGarbageCollected()) { // if already GC, skip it
          if (node instanceof TACLocalStore) {
            TACLocalStore store = (TACLocalStore) node;
            TACVariable variable = store.getVariable();
            // Type that needs garbage collection will be stored in alloc'ed variable
            if (variable.needsGarbageCollection()) {
              store.setGarbageCollected(true);

              // This optimization works because the initial parameter stores come first in a method
              if (store.getValue() instanceof TACParameter)
                parameterStores.put(variable, (TACParameter) store.getValue());
              else {
                storedVariables.add(variable);
                // a parameter's value is being changed
                if (parameterStores.containsKey(variable)) {
                  TACParameter parameter = parameterStores.get(variable);
                  parameter.setIncrement(true);
                  parameterStores.remove(variable);
                }
              }
            }
          } else if (node instanceof TACLocalLoad) {
            TACLocalLoad load = (TACLocalLoad) node;
            TACVariable variable = load.getVariable();
            if (variable.needsGarbageCollection()) load.setGarbageCollected(true);
          } else if (node instanceof TACPhi) {
            TACPhi phi = (TACPhi) node;
            TACVariable variable = phi.getVariable();
            if (variable.needsGarbageCollection()) phi.setGarbageCollected(true);
          } else if (node instanceof TACStore) {
            TACStore store = (TACStore) node;
            TACReference reference = store.getReference();

            if (reference.needsGarbageCollection()) store.setGarbageCollected(true);
          } else if (node instanceof TACNewArray) {
            TACNewArray newArray = (TACNewArray) node;
            if (newArray.hasLocalStore()) {
              TACLocalStore store = newArray.getLocalStore();
              // local stores should not be incremented, since they are returned with an existing
              // increment
              if (store.getVariable().needsGarbageCollection()) store.setIncrementReference(false);
            } else if (newArray.hasMemoryStore()) {
              TACStore store = newArray.getMemoryStore();
              if (store.getReference().needsGarbageCollection()) store.setIncrementReference(false);
            } else {
              TACVariable temp = addTempLocal(newArray);
              // store into temporary for reference count purposes (and change next)
              TACLocalStore store = new TACLocalStore(next, temp, (TACNewArray) node, false);
              store.setGarbageCollected(true);
              storedVariables.add(temp);
              // No change here because we're pulling from an existing base class object
            }
          } else if (node instanceof TACCall) {
            TACCall call = (TACCall) node;
            call.setGarbageCollected(true); // marks the call as handled
            if (call.hasLocalStore()) {
              TACLocalStore store = call.getLocalStore();
              // local stores should not be incremented, since they are returned with an existing
              // increment
              if (store.getVariable().needsGarbageCollection()) store.setIncrementReference(false);
            } else if (call.hasMemoryStore()) {
              TACStore store = call.getMemoryStore();
              if (store.getReference().needsGarbageCollection()) store.setIncrementReference(false);
            } else if (!call.isDelegatedCreate()) {
              // if method return is not saved, save it for ref counting purposes
              // complex case because of possible multiple return values
              // delegated create return values should not be GC'd
              SequenceType returns = call.getMethodRef().getFullReturnTypes();
              TACNode anchor = next;
              if (call.getNoExceptionLabel() != null) anchor = call.getNoExceptionLabel().getNext();

              if (returns.size() == 1) {
                TACVariable temp = addTempLocal(call);
                // store into temporary for reference count purposes (and change next)
                if (temp.needsGarbageCollection()) new TACLocalStore(anchor, temp, call, false);
              } else if (returns.size() > 1) {
                for (int i = 0; i < returns.size(); ++i) {
                  TACVariable temp = addTempLocal(returns.get(i));
                  // store into temporary for reference count purposes (and change next)
                  if (temp.needsGarbageCollection())
                    new TACLocalStore(anchor, temp, new TACSequenceElement(anchor, call, i), false);
                }
              }
            }
          }
          // Used to note cleanup calls in case we don't end up using them
          else if (node instanceof TACCallFinallyFunction) {
            TACCallFinallyFunction call = (TACCallFinallyFunction) node;
            if (call.getFinallyFunction() == cleanupFinallyFunction) cleanupCalls.add(call);
          }
        }

        node = node.getNext();
      }
    }

    addCleanup(storedVariables, cleanupCalls);
  }

  public void addParameters(TACNode node) {
    addParameters(node, false);
  }

  public MethodSignature getSignature() {
    return signature;
  }

  public Collection<TACVariable> getLocals() {
    return locals.values();
  }

  public Collection<TACVariable> getParameters() {
    return parameters.values();
  }

  public TACVariable getThis() {
    return locals.get("this");
  }

  public TACVariable getParameter(String name) {
    return parameters.get(name);
  }

  public void enterScope() {
    scopes.push(new HashMap<>());
  }

  private TACVariable addParameter(ModifiedType type, String name) {
    TACVariable variable = addLocal(type, name);
    parameters.put(name, variable);

    return variable;
  }

  public TACVariable addLocal(ModifiedType type, String name) {
    TACVariable variable = new TACVariable(type, name, this);

    while (locals.containsKey(variable.getName())) variable.rename();
    locals.put(variable.getName(), variable);
    //noinspection ConstantConditions
    scopes.peek().put(name, variable);
    return variable;
  }

  public TACVariable addTempLocal(ModifiedType type) {
    return addLocal(type, "_temp");
  }

  public TACVariable getLocal(String name) {
    TACVariable result = null;
    Iterator<Map<String, TACVariable>> iterator = scopes.iterator();
    while (result == null && iterator.hasNext()) result = iterator.next().get(name);
    return result;
  }

  public void exitScope() {
    scopes.pop();
    if (scopes.isEmpty()) throw new NoSuchElementException();
  }

  @Override
  public String toString() {
    StringWriter writer = new StringWriter();
    try {
      new TextOutput(writer).build(this, null);
    } catch (ShadowException ex) {
      return "Error";
    }

    return writer.toString();
  }

  public int incrementLabelCounter() {
    return labelCounter++;
  }

  public int incrementVariableCounter() {
    return variableCounter++;
  }

  private static boolean isUsedVariable(TACVariable variable) {
    return variable.needsGarbageCollection()
        || (variable.isFinallyVariable()
            && (variable.getOriginalName().equals("_exception")
                || !variable.getOriginalName().startsWith("_exception")));
  }

  private void addAllocations(
      TACNode start,
      Set<TACVariable> usedLocals,
      Set<TACChangeReferenceCount> referenceCountChanges,
      Set<TACVariable> finallyUsedLocals) {
    TACNode node = start;

    do {
      if (node instanceof TACLocalLoad) {
        TACLocalLoad load = (TACLocalLoad) node;
        TACVariable variable = load.getVariable();
        if (finallyUsedLocals != null) {
          finallyUsedLocals.add(variable);
          variable.makeFinallyVariable();
        }

        if (isUsedVariable(variable)) usedLocals.add(variable);
      } else if (node instanceof TACLocalStore) {
        TACLocalStore store = (TACLocalStore) node;
        TACVariable variable = store.getVariable();
        if (finallyUsedLocals != null) {
          finallyUsedLocals.add(variable);
          variable.makeFinallyVariable();
        }

        if (isUsedVariable(variable)) usedLocals.add(variable);
      } else if (node instanceof TACChangeReferenceCount) {
        TACChangeReferenceCount change = (TACChangeReferenceCount) node;
        TACVariable variable = change.getVariable();
        if (!change.isField()) referenceCountChanges.add(change);

        if (finallyUsedLocals != null) {
          finallyUsedLocals.add(variable);
          variable.makeFinallyVariable();
        }
      } else if (node instanceof TACPhi) {
        TACPhi phi = (TACPhi) node;
        TACVariable variable = phi.getVariable();

        // Being present in a phi doesn't mean it's really used in the finally
        // But it still needs to be treated like a finally variable so that the variable is
        // stored/loaded (and not used in phis)
        if (finallyUsedLocals != null) variable.makeFinallyVariable();

        if (isUsedVariable(variable)) usedLocals.add(variable);
      }
      node = node.getNext();
    } while (node != start);
  }

  /** Add allocations of garbage collected variables and variables used in finally functions. */
  public void addAllocations() {
    Set<TACVariable> usedLocals = new HashSet<>();
    Set<TACChangeReferenceCount> referenceCountChanges = new HashSet<>();

    Map<TACFinallyFunction, Set<TACVariable>> finallyUsedLocalsMap = new HashMap<>();
    Set<TACVariable> allFinallyLocals = new HashSet<>();

    addAllocations(node, usedLocals, referenceCountChanges, null);

    for (TACFinallyFunction function : finallyFunctions) {
      Set<TACVariable> finallyUsedLocals = new HashSet<>();
      addAllocations(function.getNode(), usedLocals, referenceCountChanges, finallyUsedLocals);
      finallyUsedLocalsMap.put(function, finallyUsedLocals);
      allFinallyLocals.addAll(finallyUsedLocals);
    }

    // Remove reference count changes for unused variables
    for (TACChangeReferenceCount change : referenceCountChanges) {
      if (!usedLocals.contains(change.getVariable()))
        change.remove(); // Takes node out of linked list
    }

    TACNode anchor = node.getNext(); // first is a label, then all the allocations

    // Put allocations in method for used variables
    List<TACVariable> locallyEscapedVariables = new ArrayList<>();
    for (TACVariable variable : usedLocals) {
      new TACAllocateVariable(anchor, variable);
      if (allFinallyLocals.contains(variable)) locallyEscapedVariables.add(variable);
    }

    if (locallyEscapedVariables.size() > 0) new TACLocalEscape(anchor, locallyEscapedVariables);

    // Put allocations in finally functions for used variables
    for (TACFinallyFunction function : finallyFunctions) {
      anchor = function.getNode().getNext(); // first is a label
      Set<TACVariable> finallyUsedLocals = finallyUsedLocalsMap.get(function);
      for (TACVariable variable : finallyUsedLocals) {
        // new TACAllocateVariable(anchor, variable);
        // new TACLocalStore(anchor, variable, new TACLocalRecover(anchor, variable), false);
        // _exception is a special variable used for all exceptions
        // _exception1, _exception2, etc. are special variables used only in finally functions to
        // store in-flight exceptions
        if (variable.getOriginalName().equals("_exception")
            || !variable.getOriginalName().startsWith("_exception"))
          new TACLocalRecover(
              anchor,
              variable,
              locallyEscapedVariables.indexOf(variable)); // The local recover handles it all?
        else new TACAllocateVariable(anchor, variable);
      }

      // Store in-flight exception into finally exception holder
      if (!Configuration.isWindows() && function.getExceptionStorage() != null)
        new TACLocalStore(
            anchor,
            function.getExceptionStorage(),
            new TACLocalLoad(anchor, getLocal("_exception")));
    }
  }

  /*
   * For purposes of detecting variables that are not initialized
   * the compiler stores undefined values into local variables
   * these cause problems for garbage collection, since it tries
   * to increment the reference count on undef, crashing the program.
   * This method removes all undefined stores.
   * This method is also an ideal place to turn off decrementing for
   * variables that are null.
   */
  public void removeUndefinedStores() {
    TACNode start = this.node;
    TACNode node = start;

    do {
      if (node instanceof TACLocalStore) {
        TACLocalStore store = (TACLocalStore) node;
        // Small optimization here:
        // If a GC variable had no previous store,
        // then this must be the "first" store to it,
        // and there's no need to decrement the old contents.
        // Note that there will be stores of null added in later for most of these.
        if (store.isGarbageCollected() && !store.hasPreviousStore())
          store.setDecrementReference(false);

        if (store.getValue() instanceof TACLiteral) {
          TACLiteral literal = (TACLiteral) store.getValue();
          if (literal.getValue() instanceof ShadowUndefined) node = node.remove();
        }
      }
      node = node.getNext();
    } while (node != start);
  }

  public void setCleanupFinallyFunction(TACFinallyFunction function) {
    cleanupFinallyFunction = function;
  }

  public List<TACFinallyFunction> getFinallyFunctions() {
    return finallyFunctions;
  }

  public void removeFinallyFunction(TACFinallyFunction function) {
    finallyFunctions.remove(function);
  }
}
