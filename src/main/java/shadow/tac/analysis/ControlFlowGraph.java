package shadow.tac.analysis;

import shadow.Loggers;
import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowNull;
import shadow.parse.Context;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
import shadow.tac.TACMethod;
import shadow.tac.TACMethod.TACFinallyFunction;
import shadow.tac.TACVariable;
import shadow.tac.nodes.*;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.*;

import java.util.*;
import java.util.Map.Entry;

/**
 * Represents the various paths which code execution can take within a method. Useful for
 * determining whether or not a method returns in all cases.
 *
 * @author Brian Stottler
 * @author Barry Wittman
 */
public class ControlFlowGraph extends ErrorReporter implements Iterable<ControlFlowGraph.Block> {
  private Block root;
  private String cachedString; // Saves toString() from re-walking the graph
  private final Map<TACLabel, Block> nodeBlocks = new LinkedHashMap<>();
  private final TACMethod method;
  // field names can be repeated in inner classes
  private final Map<Type, Set<String>> usedFields = new HashMap<>();
  // Method signatures include information that identifies class
  private final Set<MethodSignature> usedPrivateMethods = new HashSet<>();
  private final Map<TACFinallyFunction, List<TACCallFinallyFunction>> finallyFunctionCalls =
      new HashMap<>();

  /** Create a control flow graph for a method. */
  public ControlFlowGraph(TACMethod method) {
    super(Loggers.TYPE_CHECKER);
    this.method = method;

    // a method can only directly use fields in its class and outer classes
    Type type = method.getSignature().getOuter();
    while (type != null) {
      usedFields.put(type.getTypeWithoutTypeArguments(), new HashSet<>());
      type = type.getOuter();
    }

    // addGarbageCollection();
    createBlocks(method);
    addEdges();
  }

  /**
   * Get the method associated with this graph.
   *
   * @return method
   */
  public TACMethod getMethod() {
    return method;
  }

  /*
   * Adds edges between all blocks.
   */
  private void addEdges() {
    for (Block block : nodeBlocks.values()) addEdges(block);
  }

  @Override
  public Iterator<ControlFlowGraph.Block> iterator() {
    return nodeBlocks.values().iterator();
  }

  public Map<Type, Set<String>> getUsedFields() {
    return usedFields;
  }

  public Set<MethodSignature> getUsedPrivateMethods() {
    return usedPrivateMethods;
  }

  /*
   * Adds edges from a block to all blocks it branches to.
   */
  @SuppressWarnings("SuspiciousMethodCalls")
  private void addEdges(Block block) {
    TACNode node = block.getLabel();
    boolean done = false;

    while (!done) {
      if (node instanceof TACBranch) {
        TACBranch branch = (TACBranch) node;
        if (branch.isConditional()) {
          TACOperand condition = branch.getCondition();
          // Hack that allows things like if(true) or while(false) to be handled
          if (condition instanceof TACLiteral
              && ((TACLiteral) condition).getValue() instanceof ShadowBoolean) {
            ShadowBoolean value = (ShadowBoolean) ((TACLiteral) condition).getValue();
            if (value.getValue()) {
              block.addBranch(nodeBlocks.get(branch.getTrueLabel()));
              branch.convertToDirect(branch.getTrueLabel());
            } else {
              block.addBranch(nodeBlocks.get(branch.getFalseLabel()));
              branch.convertToDirect(branch.getFalseLabel());
            }
          } else {
            block.addBranch(nodeBlocks.get(branch.getTrueLabel()));
            block.addBranch(nodeBlocks.get(branch.getFalseLabel()));
          }
        } else if (branch.isDirect()) block.addBranch(nodeBlocks.get(branch.getLabel()));
        // The following can happen now if a TACCallFinallyFunction was removed
        else if (branch.isIndirect() && !(branch.getPrevious() instanceof TACCallFinallyFunction)) {
          // Branch to every possible destination
          TACPhi phi = branch.getPhi();
          Map<TACLabel, TACOperand> destinations = phi.getPreviousStores();
          if (destinations.size() == 1) {
            TACLabelAddress address = (TACLabelAddress) destinations.values().iterator().next();
            TACLabel label = address.getLabel();
            branch.convertToDirect(label);
            block.addBranch(nodeBlocks.get(label));
          } else
            for (TACOperand destination : destinations.values()) {
              TACLabelAddress address = (TACLabelAddress) destination;
              block.addBranch(nodeBlocks.get(address.getLabel()));
            }
        }
      }
      // Handles cases where a method call can cause a catchable exception
      else if (node instanceof TACCall) {
        TACCall call = (TACCall) node;
        TACLabel unwindLabel = call.getBlock().getUnwind();

        if (unwindLabel != null) {
          block.addBranch(nodeBlocks.get(unwindLabel));
          block.addBranch(nodeBlocks.get(call.getNoExceptionLabel()));
        }
      } else if (node instanceof TACCallFinallyFunction) {
        TACCallFinallyFunction callFinallyFunction = (TACCallFinallyFunction) node;
        // Node should always be a label
        block.addBranch(nodeBlocks.get(callFinallyFunction.getFinallyFunction().getNode()));
      }
      // Weird case only inside of finally functions
      else if (node instanceof TACReturn) {
        TACReturn return_ = (TACReturn) node;
        TACFinallyFunction function = return_.getBlock().getFinallyFunction();
        if (!return_.hasReturnValue() && function != null) {
          List<TACCallFinallyFunction> list = finallyFunctionCalls.get(function);
          if (list != null) {
            for (TACCallFinallyFunction callFinallyFunction : list) {
              TACNode next = callFinallyFunction.getNext();

              if (next instanceof TACBranch) {
                TACBranch branch = (TACBranch) next;
                if (branch.isDirect()) block.addBranch(nodeBlocks.get(branch.getLabel()));
                else {
                  Map<TACLabel, TACOperand> destinations = branch.getPhi().getPreviousStores();
                  for (TACOperand destination : destinations.values()) {
                    TACLabelAddress address = (TACLabelAddress) destination;
                    block.addBranch(nodeBlocks.get(address.getLabel()));
                  }
                }
              } else if (next instanceof TACCleanupRet) {
                TACCleanupRet cleanupRet = (TACCleanupRet) callFinallyFunction.getNext();
                TACLabel unwindLabel = cleanupRet.getUnwind();
                if (unwindLabel != null) block.addBranch(nodeBlocks.get(unwindLabel));
              }
            }
          }
        }
      } else if (node instanceof TACThrow) {
        TACThrow throw_ = (TACThrow) node;
        TACLabel unwindLabel = throw_.getBlock().getUnwind();

        if (unwindLabel != null) block.addBranch(nodeBlocks.get(unwindLabel));
      } else if (node instanceof TACCatchRet) {
        TACCatchRet catchRet = (TACCatchRet) node;
        block.addBranch(nodeBlocks.get(catchRet.getLabel()));
      } else if (node instanceof TACCatchPad) {
        TACCatchPad catchPad = (TACCatchPad) node;

        if (catchPad.getSuccessor() != null)
          block.addBranch(nodeBlocks.get(catchPad.getSuccessor()));
        else {
          TACLabel unwindLabel = node.getBlock().getUnwind();
          if (unwindLabel != null) block.addBranch(nodeBlocks.get(unwindLabel));
        }
      }

      if (node == block.getLast()) done = true;
      else node = node.getNext();
    }
  }

  @SuppressWarnings("ConstantConditions")
  private void visitNodes(TACNode start, Set<TACVariable> usedLocalVariables, boolean starting) {
    boolean done = false;
    Block block = null;
    TACNode node = start;

    // Loop through circular linked-list
    while (!done) {
      if (node instanceof TACLabel) {
        TACLabel label = (TACLabel) node;
        block = new Block(label);
        if (starting) {
          root = block;
          starting = false;
        }
        nodeBlocks.put(label, block);
      } else if (node instanceof TACReturn) {
        if (node.getBlock().getFinallyFunction() != null) {
          block.flagUnwinds();
        } else block.flagReturns();
      } else if (node instanceof TACThrow
          || node instanceof TACCleanupRet
          || node instanceof TACResume) {
        block.flagUnwinds();
      }
      // record field usage, for warnings
      else if (node instanceof TACLoad) {
        TACLoad load = (TACLoad) node;
        TACReference ref = load.getReference();
        if (ref instanceof TACFieldRef) {
          TACFieldRef field = (TACFieldRef) ref;
          Type prefixType = field.getPrefixType().getTypeWithoutTypeArguments();
          Set<String> fields = usedFields.get(prefixType);
          if (fields != null) fields.add(field.getName());
          else {
            fields = new HashSet<>();
            fields.add(field.getName());
            usedFields.put(prefixType, fields);
          }
        }
      }
      // record private method usage, for warnings
      else if (node instanceof TACCall) {
        TACCall call = (TACCall) node;
        TACMethodRef methodRef = call.getMethodRef();
        if (methodRef instanceof TACMethodName) {
          TACMethodName methodName = (TACMethodName) methodRef;
          MethodSignature signature = methodName.getSignature().getSignatureWithoutTypeArguments();
          if (signature.getModifiers().isPrivate()
              && signature.getOuter().encloses(method.getThis().getType()))
            usedPrivateMethods.add(signature);
        }
      } else if (node instanceof TACCallFinallyFunction) {
        TACCallFinallyFunction callFinallyFunction = (TACCallFinallyFunction) node;
        TACFinallyFunction function = callFinallyFunction.getFinallyFunction();
        List<TACCallFinallyFunction> list = finallyFunctionCalls.get(function);
        if (list == null) {
          list = new ArrayList<>();
          list.add(callFinallyFunction);
          finallyFunctionCalls.put(function, list);
        } else list.add(callFinallyFunction);
      }
      // record local variable usage, for warnings and optimizations
      else if (node instanceof TACLocalLoad) {
        TACLocalLoad load = (TACLocalLoad) node;
        usedLocalVariables.add(load.getVariable());
      }
      // these phi nodes are the ones added for indirect breaks on finally blocks
      else if (node instanceof TACPhi) {
        TACPhi phi = (TACPhi) node;
        usedLocalVariables.add(phi.getVariable());
      }

      block.addNode(node);

      node = node.getNext();
      if (node == start) done = true;
    }
  }

  /*
   * Divides the code in a method into blocks.
   */
  private void createBlocks(TACMethod method) {
    TACNode node = method.getNode();
    Set<TACVariable> usedLocalVariables = new HashSet<>();

    // Record all used local variables
    visitNodes(node, usedLocalVariables, true);
    for (TACFinallyFunction function : method.getFinallyFunctions())
      visitNodes(function.getNode(), usedLocalVariables, false);

    // Give warnings for unused local variables
    // Skip all copy methods, since they're automatically generated (and often don't use the
    // "addresses" variable)
    if (!method.getSignature().getSymbol().equals("copy"))
      for (TACVariable variable : method.getLocals()) {
        String name = variable.getName();
        // special compiler-created variables (_temp, _exception, etc.) start with _
        // no need to check on "this"

        if (!name.startsWith("_") && !name.equals("this") && !name.equals("return")) {
          if (!usedLocalVariables.contains(variable)) {
            ModifiedType type = variable.getModifiedType();
            // kind of a hack to get the declaration
            if (type instanceof Context) {
              Context declaration = (Context) type;
              // we don't add warnings for formal parameters: method parameters and catch parameters
              if (!(declaration instanceof ShadowParser.FormalParameterContext)) {
                if (declaration instanceof ShadowParser.VariableDeclaratorContext)
                  declaration =
                      ((ShadowParser.VariableDeclaratorContext) declaration).generalIdentifier();

                addWarning(
                    declaration,
                    Error.UNUSED_VARIABLE,
                    "Local variable " + variable.getOriginalName() + " is never used");
              }
            } else
              addWarning(
                  method.getSignature().getNode(),
                  Error.UNUSED_VARIABLE,
                  "Local variable " + variable.getOriginalName() + " is never used");
          }
        }
      }
  }

  /**
   * Removes blocks of code that are unreachable.
   *
   * @return true if at least one block was removed
   */
  public boolean removeUnreachableCode() {
    boolean edgesUpdated;
    boolean changed = false;

    do {
      edgesUpdated = false;
      Set<Block> reachable = new HashSet<>();
      findReachable(root, reachable);

      Set<Block> unreachable = new HashSet<>(nodeBlocks.values());
      unreachable.removeAll(reachable);

      if (unreachable.size() > 0) changed = true;

      // If edges are updated in the process, new nodes might become unreachable
      for (Block block : unreachable) {
        if (block.removeNodes()) edgesUpdated = true;
        nodeBlocks.remove(block.getLabel());
        block.removeEdges();
      }

      // blocks with indirect branches might need to update outgoing
      // based on changing phi situations
      for (Block block : reachable) if (block.updatePhiBranches()) edgesUpdated = true;

    } while (edgesUpdated);

    cachedString = null; // reset cachedString
    return changed;
  }

  /*
   * Find all blocks reachable from the starting block.
   */
  private static void findReachable(Block block, Set<Block> visited) {
    if (!visited.contains(block)) {
      visited.add(block);
      for (Block branch : block.getOutgoing()) findReachable(branch, visited);
    }
  }

  /** Traverses the graph to determine if the corresponding method returns in all cases. */
  public boolean returns() {
    Set<Block> visited = new HashSet<>();

    return returns(root, visited);
  }

  /*
   * Recursive helper to see if all blocks starting at block return.
   */
  private static boolean returns(Block block, Set<Block> visited) {
    if (visited.contains(block)) return true;

    visited.add(block);

    // A block should either branch or return
    if (block.branches() > 0) {
      // Ensure everything we can branch to returns
      for (Block child : block.getOutgoing()) if (!returns(child, visited)) return false;
      return true;
    } else {
      // If we don't branch, this block should return directly or unwind by throwing an uncaught
      // exception
      return block.returnsDirectly() || block.unwinds();
    }
  }

  /*
   * Generates a String representation of the graph.
   */
  private String generateString() {
    Set<Block> visited = new HashSet<>();
    List<IndexedString> strings = new ArrayList<>();

    generateString(root, visited, strings);

    // Order the strings for each block and combine them
    Collections.sort(strings);
    StringBuilder builder = new StringBuilder();
    for (IndexedString string : strings) builder.append(string.getString()).append("\n");

    return builder.toString();
  }

  /*
   * Traverses the graph to build a readable print-out of it. Useful for
   * debugging.
   */
  private static void generateString(
      Block current, Set<Block> visited, List<IndexedString> strings) {
    if (visited.contains(current)) return;

    visited.add(current);

    List<Integer> children = new ArrayList<>();
    for (Block child : current.getOutgoing()) {
      children.add(child.getNumber());
      generateString(child, visited, strings);
    }

    // Lead with the current block's ID
    StringBuilder output = new StringBuilder(current.getNumber() + ": ");

    // List the blocks it can branch to
    Collections.sort(children);
    for (int i = 0; i < children.size(); ++i) {
      if (i != 0) output.append(", ");
      output.append(children.get(i)).append(" ");
    }

    // Indicate whether or not it returns directly
    if (current.returnsDirectly()) output.append("(RETURNS)");
    else if (current.unwinds()) output.append("(UNWINDS)");

    strings.add(new IndexedString(current.getNumber(), output.toString()));
  }

  /**
   * Adds phi nodes to blocks where needed. This method *must* be called in order to create the phi
   * nodes needed for constant propagation, checking for undefined variables, and proper LLVM IR
   * generation.
   */
  public void addPhiNodes() {
    Map<Block, Map<TACVariable, TACLocalStorage>> lastDefinitions = new HashMap<>();

    Collection<Block> blocks = nodeBlocks.values();

    // find the last definition of every variable in every block
    for (Block block : blocks) lastDefinitions.put(block, block.getLastStores());

    // use those definitions when constructing phi nodes
    for (Block block : blocks) block.addPhiNodes(lastDefinitions);
  }

  /**
   * Propagate constants and other values through the SSA representation. This method is not
   * optional if we want to produce legal LLVM IR. Propagating constants will also find undefined
   * local variables, adding appropriate error messages to the error list.
   *
   * @return true if constants propagated more deeply than before
   */
  public boolean propagateConstants() {
    List<Block> blocks = getReversePostorder();

    boolean done = false;
    boolean changed = false;
    Set<TACLocalLoad> undefinedLoads = new HashSet<>();

    while (!done) {
      done = true;
      undefinedLoads.clear();
      for (Block block : blocks)
        if (block.propagateValues(undefinedLoads)) {
          done = false;
          changed = true;
        }
    }

    for (TACLocalLoad undefined : undefinedLoads)
      // _exception is a special variable used only for exception handling
      // indirect breaks for finally make its value tricky, but it's guaranteed to never *really* be
      // undefined
      if (!undefined.getVariable().getOriginalName().startsWith("_exception")
          && !undefined.getVariable().getOriginalName().equals("return")
          && !undefined.getVariable().getOriginalName().startsWith("_return"))
        addError(
            undefined.getContext(),
            Error.UNDEFINED_VARIABLE,
            "Variable "
                + undefined.getVariable().getOriginalName()
                + " may not have been defined before use");

    return changed;
  }

  @Override
  public String toString() {
    if (cachedString == null) cachedString = generateString();

    return cachedString;
  }

  /**
   * Returns a list giving the graph in reverse postorder. Reverse postorder is considered to be an
   * easy ordering of a rooted, directed graph that reduces the number of children visited before
   * their parents are visited. Cycles in the graph, of course, make the notion of children and
   * parents somewhat fuzzy. This ordering reduces the running time for the data flow equations to
   * reach equilibrium.
   *
   * @return list of blocks in reverse postorder with respect to the root
   */
  public List<Block> getReversePostorder() {
    List<Block> list = new ArrayList<>(nodeBlocks.size());
    Set<Block> visited = new HashSet<>(nodeBlocks.size() * 2);
    postorder(root, list, visited);
    Collections.reverse(list); // Reverse list
    return list;
  }

  private static void postorder(Block block, List<Block> list, Set<Block> visited) {
    if (!visited.contains(block)) {
      visited.add(block);
      for (Block child : block.outgoing) postorder(child, list, visited);
      list.add(block);
    }
  }

  public Set<String> getInitializedFields(
      Set<String> alreadyInitialized,
      Set<String> thisStores,
      Map<MethodSignature, StorageData> methodData,
      Set<String> fieldsNeedingInitialization) {
    Map<Block, Set<String>> initialLoadsBeforeStores = new HashMap<>();
    Map<Block, Set<String>> initialStores = new HashMap<>();
    Map<Block, Set<String>> allLoadsBeforeStores = new HashMap<>();
    Map<Block, Set<String>> allStores = new HashMap<>();

    Set<String> loads = new TreeSet<>();
    List<Block> blocks =
        getReversePostorder(); // Converges faster since many blocks will be visited only after
    // their predecessors

    for (Block block : blocks) {
      Set<String> loadsBeforeStores = new TreeSet<>();
      Set<String> stores = new TreeSet<>();

      block.recordLoadsAndStoresInCreates(
          method.getThis().getType(), loadsBeforeStores, stores, loads, thisStores, methodData);

      initialLoadsBeforeStores.put(block, loadsBeforeStores);
      initialStores.put(block, stores);

      if (block == root) {
        Set<String> set = new TreeSet<>(loadsBeforeStores);
        set.removeAll(alreadyInitialized);
        allLoadsBeforeStores.put(block, set);

        set = new TreeSet<>(stores);
        set.addAll(alreadyInitialized);
        allStores.put(block, set);
      } else {
        allLoadsBeforeStores.put(block, new TreeSet<>());
        allStores.put(
            block,
            new TreeSet<>(
                fieldsNeedingInitialization)); // For data flow equations, assume everything is
        // stored initially
      }
    }

    boolean changed = true;
    Map<Block, Set<String>> allPreviousStores = new HashMap<>();

    // keep going as long as changes to the stores are being detected
    while (changed) {
      changed = false;

      for (Block block : blocks) {
        if (block != root && !block.hasIndirectBranch() && !block.isEndOfFinally()) {
          // Find previous stores, the intersection of the stores of previous blocks
          Set<String> previousStores = null;

          for (Block parent : block.incoming) {
            Set<String> parentStores;

            // Indirect branching blocks are tricky
            // Probably only one parent of the indirect
            // block will reach the child we're looking at
            if (parent.hasIndirectBranch()) {
              parentStores = initialStores.get(parent);
              TACBranch phiBranch = (TACBranch) parent.getLast();
              for (Entry<TACLabel, TACOperand> entry :
                  phiBranch.getPhi().getPreviousStores().entrySet()) {
                TACLabelAddress address = (TACLabelAddress) entry.getValue();
                if (address.getLabel().getNumber() == block.getLabel().getNumber())
                  parentStores = union(parentStores, allStores.get(nodeBlocks.get(entry.getKey())));
              }
            }
            // Ends of finally blocks are basically indirect branching blocks
            else if (parent.isEndOfFinally()) {
              parentStores = initialStores.get(parent);
              TACReturn return_ = (TACReturn) parent.getLast();
              TACFinallyFunction function = return_.getBlock().getFinallyFunction();
              if (!return_.hasReturnValue() && function != null) {
                List<TACCallFinallyFunction> list = finallyFunctionCalls.get(function);
                if (list != null) {
                  boolean unwindToCurrentBlock = false;
                  // See if current block is the unwind
                  for (TACCallFinallyFunction callFinallyFunction : list) {
                    TACNode next = callFinallyFunction.getNext();
                    if (next instanceof TACCleanupRet) {
                      TACCleanupRet cleanupRet = (TACCleanupRet) next;
                      TACLabel unwindLabel = cleanupRet.getUnwind();
                      if (block.getLabel().equals(unwindLabel))
                        unwindToCurrentBlock = true;
                    }
                  }

                  for (TACCallFinallyFunction callFinallyFunction : list) {
                    TACNode next = callFinallyFunction.getNext();
                    if (!unwindToCurrentBlock && next instanceof TACBranch) {
                      TACBranch branch = (TACBranch) next;
                      if (branch.isIndirect()) {
                        for (Entry<TACLabel, TACOperand> entry :
                            branch.getPhi().getPreviousStores().entrySet()) {
                          TACLabelAddress address = (TACLabelAddress) entry.getValue();
                          if (address.getLabel().getNumber() == block.getLabel().getNumber())
                            parentStores =
                                union(parentStores, allStores.get(nodeBlocks.get(entry.getKey())));
                        }
                      } else if (branch.isDirect()) parentStores = allStores.get(parent);
                    } else if (unwindToCurrentBlock && next instanceof TACCleanupRet) {
                      parentStores = allStores.get(parent);
                    }
                  }
                }
              }
            } else parentStores = allStores.get(parent);

            if (previousStores == null) previousStores = parentStores;
            else previousStores = intersect(previousStores, parentStores);
          }
          allPreviousStores.put(block, previousStores); // keep record for later error reporting

          // get current state of stores and loads before stores
          Set<String> stores = allStores.get(block);
          Set<String> loadsBeforeStores = allLoadsBeforeStores.get(block);

          // record size, to detect changes
          int storesSize = stores.size();
          int loadsBeforeSize = loadsBeforeStores.size();

          // update stores
          stores.clear();
          stores.addAll(initialStores.get(block));
          assert previousStores != null;
          stores.addAll(previousStores);

          // update loads before stores
          loadsBeforeStores.addAll(initialLoadsBeforeStores.get(block));
          loadsBeforeStores.removeAll(previousStores);

          if (storesSize != stores.size() || loadsBeforeSize != loadsBeforeStores.size())
            changed = true;
        }
      }
    }

    // root has no previous stores
    allPreviousStores.put(root, new TreeSet<>());

    // find blocks that return and record errors for fields that are used before they are
    // initialized
    List<Block> returningBlocks = new ArrayList<>();
    for (Block block : nodeBlocks.values()) {
      if (block.returnsDirectly()) returningBlocks.add(block);
      Set<String> loadsBeforeStores = allLoadsBeforeStores.get(block);
      if (loadsBeforeStores.size() > 0)
        block.addErrorsForUninitializedFields(allPreviousStores.get(block), methodData);
    }

    // check all loads of fields that might contain a "this"
    for (String field : thisStores) {
      // field might contain "this" and is therefore illegal to load
      if (loads.contains(field))
        addError(
            method.getSignature().getNode(),
            Error.READ_OF_THIS_IN_CREATE,
            "Field "
                + field
                + " that might contain a reference to \"this\" cannot be read in a create or methods called by a create");
    }

    // intersect all of the fields initialized by the time any returning block is reached
    Set<String> initializedFields = null;
    for (Block block : returningBlocks)
      if (initializedFields == null) initializedFields = allStores.get(block);
      else initializedFields = intersect(initializedFields, allStores.get(block));

    return initializedFields;
  }

  public StorageData getLoadsBeforeStoresInMethods(Type type, CallGraph callGraph) {
    StorageData data = new StorageData();
    for (Block block : nodeBlocks.values())
      block.recordLoadsBeforeStoresInMethods(type, data, callGraph);

    return data;
  }

  private Set<String> intersect(Set<String> a, Set<String> b) {
    Set<String> result = new TreeSet<>();

    // always iterate over smaller set
    if (b.size() < a.size()) {
      Set<String> temp = a;
      a = b;
      b = temp;
    }

    for (String string : a) if (b.contains(string)) result.add(string);

    return result;
  }

  private Set<String> union(Set<String> a, Set<String> b) {
    Set<String> result = new TreeSet<>(a);
    result.addAll(b);
    return result;
  }

  // constants are already taken care of
  // nullable fields are initialized to null
  // primitive types are initialized to reasonable defaults
  // arrays (surprisingly) are value types, initialized with lengths of zero
  public static boolean needsInitialization(ModifiedType modifiedType) {
    Modifiers modifiers = modifiedType.getModifiers();
    Type type = modifiedType.getType();

    return !modifiers.isConstant()
        && !modifiers.isNullable()
        && !type.isPrimitive()
        && !(type instanceof ArrayType)
        && !(type instanceof SingletonType);
  }

  public void addCallEdges(CallGraph calls, Type type) {
    for (Block block : this) block.addCallEdges(calls, type);
  }

  /*
   * Represents a contiguous sequence of TAC nodes, unbroken by branch
   * statements.
   */
  public class Block implements Iterable<TACNode> {
    private Set<Block> outgoing = new HashSet<>();
    private final Set<Block> incoming = new HashSet<>();
    private final TACLabel label;
    private boolean returns = false;
    private boolean unwinds = false;
    private TACNode lastNode = null; // last TAC node in this block

    public boolean isEndOfFinally() {
      return lastNode instanceof TACReturn && lastNode.getBlock().getFinallyFunction() != null;
    }

    public boolean hasIndirectBranch() {
      return lastNode instanceof TACBranch && ((TACBranch) lastNode).isIndirect();
    }

    @Override
    public String toString() {
      return "Block " + label.getNumber();
    }

    public void addErrorsForUninitializedFields(
        Set<String> previousStores, Map<MethodSignature, StorageData> methodData) {
      Set<String> stores = new HashSet<>(previousStores);
      Type type = method.getThis().getType();

      for (TACNode node : this) {
        if (node instanceof TACStore) {
          TACStore store = (TACStore) node;
          if (store.getReference() instanceof TACFieldRef) {
            TACFieldRef field = (TACFieldRef) store.getReference();
            // we don't care about nullables or primitives
            if (operandIsThis(field.getPrefix(), type) && needsInitialization(field))
              stores.add(field.getName());
            else if (operandIsThis(store.getValue(), type))
              for (Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet())
                if (!stores.contains(entry.getKey()) && needsInitialization(entry.getValue()))
                  addError(
                      node.getContext(),
                      Error.UNINITIALIZED_FIELD,
                      "Current object cannot be stored in a field or array before field "
                          + entry.getKey()
                          + " is initialized");
          } else if (operandIsThis(store.getValue(), type)) { // store of "this" somewhere
            for (Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet())
              if (!stores.contains(entry.getKey()) && needsInitialization(entry.getValue()))
                addError(
                    node.getContext(),
                    Error.UNINITIALIZED_FIELD,
                    "Current object cannot be stored in a field or array before field "
                        + entry.getKey()
                        + " is initialized");
          }
        } else if (node instanceof TACLoad) {
          TACLoad load = (TACLoad) node;
          if (load.getReference() instanceof TACFieldRef) {
            TACFieldRef field = (TACFieldRef) load.getReference();
            // we don't care about nullables or primitives
            // class is a special case, guaranteed to be initialized
            if (operandIsThis(field.getPrefix(), type)
                && !stores.contains(field.getName())
                && !field.getName().equals("class")
                && needsInitialization(field))
              addError(
                  node.getContext(),
                  Error.UNINITIALIZED_FIELD,
                  "Field " + field.getName() + " may have been used without being initialized");
          }
        } else if (node instanceof TACCall) {
          TACCall call = (TACCall) node;
          TACMethodRef methodRef = call.getMethodRef();
          if (methodRef instanceof TACMethodName) {
            MethodSignature signature = ((TACMethodName) methodRef).getSignature();
            if (operandIsThis(call.getPrefix(), type)) {
              // see if it's in the list of methods whose field usage we track
              // otherwise, it would have already caused an error
              if (methodData.containsKey(signature)) {
                Set<String> fields = methodData.get(signature).getLoadsBeforeStores();
                for (String field : fields)
                  if (!stores.contains(field))
                    addError(
                        node.getContext(),
                        Error.UNINITIALIZED_FIELD,
                        "Method call is not permitted before field " + field + " is initialized");
              }
            }
          }
        }
      }
    }

    public void recordLoadsBeforeStoresInMethods(Type type, StorageData data, CallGraph callGraph) {
      Set<String> stores = new HashSet<>();

      for (TACNode node : this) {
        if (node instanceof TACCall) {
          TACCall call = (TACCall) node;
          // Freak out if we're calling a method that takes "this"
          // as a parameter and it's not one of the methods we're tracking
          // All possible fields could be affected
          if (!callGraph.contains(call.getMethod().getSignature())) {
            for (int i = 0; i < call.getNumParameters(); ++i)
              if (operandIsThis(call.getParameter(i), type)) {
                for (Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet())
                  if (!stores.contains(entry.getKey()) && needsInitialization(entry.getValue()))
                    data.getLoadsBeforeStores().add(entry.getKey());
                break;
              }
          }
        } else
          recordLoadsAndStores(
              node,
              type,
              data.getLoadsBeforeStores(),
              stores,
              data.getLoads(),
              data.getThisStores());
      }
    }

    public void recordLoadsAndStoresInCreates(
        Type type,
        Set<String> loadsBeforeStores,
        Set<String> stores,
        Set<String> loads,
        Set<String> thisStores,
        Map<MethodSignature, StorageData> methodData) {
      for (TACNode node : this) {
        if (node instanceof TACCall) {
          TACCall call = (TACCall) node;
          if (call.getMethodRef() instanceof TACMethodName) {
            MethodSignature signature = ((TACMethodName) call.getMethodRef()).getSignature();
            if (signature.isImport() || operandIsThis(call.getPrefix(), type)) {
              // creates are handled separately, and we have to assume that native code works
              if (!signature.isCreate() && !signature.isImport()) {
                // if we've recorded the fields a method uses, add those to the loads before stores
                if (methodData.containsKey(signature)) {
                  loadsBeforeStores.addAll(methodData.get(signature).getLoadsBeforeStores());
                  loads.addAll(methodData.get(signature).getLoads());
                  thisStores.addAll(methodData.get(signature).getThisStores());
                }
                // otherwise, it's not a legal method to call, probably because it's unlocked
                else
                  addError(
                      node.getContext(),
                      Error.ILLEGAL_ACCESS,
                      "Cannot call unlocked method "
                          + signature.getSymbol()
                          + signature.getMethodType()
                          + " from a create");
              }
            }
            // an inner class call, perhaps even a create
            else if (methodData.containsKey(signature)) {
              loadsBeforeStores.addAll(methodData.get(signature).getLoadsBeforeStores());
              loads.addAll(methodData.get(signature).getLoads());
              thisStores.addAll(methodData.get(signature).getThisStores());
            }
            // not a call inside the current file
            else {
              for (int i = 1;
                  i < call.getNumParameters();
                  ++i) // always an extra parameter for "this"
              if (operandIsThis(call.getParameter(i), type)) {
                  addError(
                      node.getContext(),
                      Error.ILLEGAL_ACCESS,
                      "Current object cannot be passed as a parameter inside of its create");
                  break;
                }
            }
          } else if (call.getMethodRef() instanceof TACMethodPointer) {
            for (int i = 1;
                i < call.getNumParameters();
                ++i) // always an extra parameter for "this"
            if (operandIsThis(call.getParameter(i), type)) {
                addError(
                    node.getContext(),
                    Error.ILLEGAL_ACCESS,
                    "Current object cannot be passed as a parameter inside of its create");
                break;
              }
          }
        } else recordLoadsAndStores(node, type, loadsBeforeStores, stores, loads, thisStores);
      }
    }

    private void recordLoadsAndStores(
        TACNode node,
        Type type,
        Set<String> loadsBeforeStores,
        Set<String> stores,
        Set<String> loads,
        Set<String> thisStores) {
      if (node instanceof TACStore) {
        TACStore store = (TACStore) node;
        if (store.getReference() instanceof TACFieldRef) {
          TACFieldRef field = (TACFieldRef) store.getReference();
          if (operandIsThis(field.getPrefix(), type) && needsInitialization(field)) {
            stores.add(field.getName());
            if (operandIsThis(store.getValue(), type)) // store of "this" inside current object
            thisStores.add(field.getName());
          } else if (operandIsThis(store.getValue(), type))
            // might leak all data
            for (Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet())
              if (!stores.contains(entry.getKey()) && needsInitialization(entry.getValue()))
                loadsBeforeStores.add(entry.getKey());
        } else if (operandIsThis(
            store.getValue(), type)) { // store of "this", but not in a field in the current object
          // might leak all data
          for (Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet())
            if (!stores.contains(entry.getKey()) && needsInitialization(entry.getValue()))
              loadsBeforeStores.add(entry.getKey());
        }
      } else if (node instanceof TACLoad) {
        TACLoad load = (TACLoad) node;
        if (load.getReference() instanceof TACFieldRef) {
          TACFieldRef field = (TACFieldRef) load.getReference();
          // class is a special case, guaranteed to be initialized
          if (operandIsThis(field.getPrefix(), type) && !field.getName().equals("class")) {
            if (needsInitialization(field)) {
              if (!stores.contains(field.getName())) loadsBeforeStores.add(field.getName());
              loads.add(field.getName());
            }
          }
        }
      }
    }

    private TACOperand getValue(TACOperand op) {
      TACOperand start;
      do {
        start = op;
        while (op instanceof TACCast) {
          TACCast cast = (TACCast) op;
          op = cast.getOperand(0);
        }
        if (op instanceof TACUpdate) op = ((TACUpdate) op).getValue();
      } while (start != op);

      return op;
    }

    private boolean operandIsThis(TACOperand op, Type type) {
      Set<TACPhi> visitedPhis = new HashSet<>();
      return operandIsThis(op, type, visitedPhis);
    }

    // as long as the data flow analysis has already happened, it should be hard to sneak
    // in a variable that stores "this" without us detecting it
    private boolean operandIsThis(TACOperand op, Type type, Set<TACPhi> visitedPhis) {
      op = getValue(op);

      // current type
      if (method.getThis().getType().equals(type)) {
        if (op instanceof TACParameter) {
          TACParameter parameter = (TACParameter) op;
          return parameter.getNumber() == 0; // First parameter is always "this"
        } else if (op instanceof TACLocalLoad) {
          TACLocalLoad load = (TACLocalLoad) op;
          return load.getVariable().equals(method.getThis());
        } else if (op instanceof TACLocalStore) {
          TACLocalStore store = (TACLocalStore) op;
          return store.getVariable().equals(method.getThis());
        } else if (op instanceof TACPhi) {
          TACPhi phi = (TACPhi) op;
          if (visitedPhis.contains(phi)) return false;

          visitedPhis.add(phi);
          for (TACOperand value : phi.getPreviousStores().values()) {
            if (operandIsThis(value, type, visitedPhis)) return true;
          }
        }
      }

      return false;
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Block)) return false;

      if (this == other) return true;

      Block block = (Block) other;
      return toString().equals(block.toString());
    }

    @Override
    public int hashCode() {
      return toString().hashCode();
    }

    /*
     * Gets the number of the label the block starts with.
     */
    public int getNumber() {
      return label.getNumber();
    }

    /*
     * Propagates constants through updatable nodes.
     * If a variable is found to be undefined where it's loaded,
     * adds the load to a list of undefined loads.
     */
    public boolean propagateValues(Set<TACLocalLoad> undefinedLoads) {
      boolean changed = false;
      Set<TACUpdate> currentlyUpdating = new HashSet<>();
      // Can't use the iterator because of node removal

      for (TACNode node : this) {
        if (node instanceof TACUpdate)
          if (((TACUpdate) node).update(currentlyUpdating)) changed = true;

        if (node instanceof TACLocalLoad) {
          TACLocalLoad load = (TACLocalLoad) node;
          if (load.isUndefined()) undefinedLoads.add(load);
        } else if (node instanceof TACBranch) {
          TACBranch branch = (TACBranch) node;
          if (branch.isConditional() && branch.getCondition() instanceof TACUpdate) {
            TACUpdate update = (TACUpdate) branch.getCondition();
            if (update.update(currentlyUpdating)) changed = true;

            // Simplifying branch
            if (update.getValue() instanceof TACLiteral) {
              TACLiteral literal = (TACLiteral) update.getValue();
              boolean value = ((ShadowBoolean) literal.getValue()).getValue();
              TACLabel trueLabel = branch.getTrueLabel();
              TACLabel falseLabel = branch.getFalseLabel();
              if (value) {
                branch.convertToDirect(trueLabel);
                Block falseBlock = nodeBlocks.get(falseLabel);
                outgoing.remove(falseBlock);
                falseBlock.incoming.remove(this);

                falseBlock.removePhiInput(this);
              } else {
                branch.convertToDirect(falseLabel);
                Block trueBlock = nodeBlocks.get(trueLabel);
                outgoing.remove(trueBlock);
                trueBlock.incoming.remove(this);

                trueBlock.removePhiInput(this);
              }
              changed = true;
            }
          }
        }
      }

      return changed;
    }

    public void removeEdges() {
      for (Block block : incoming) block.outgoing.remove(this);
      for (Block block : outgoing) block.incoming.remove(this);
    }

    public Block(TACLabel label) {
      this.label = label;
    }

    /*
     * Finds the last time that each variable in a block is stored to.
     */
    public Map<TACVariable, TACLocalStorage> getLastStores() {
      Map<TACVariable, TACLocalStorage> stores = new HashMap<>();
      for (TACNode node : this) {
        if (node instanceof TACLocalStorage) {
          TACLocalStorage store = (TACLocalStorage) node;
          stores.put(store.getVariable(), store);
        }
      }
      return stores;
    }

    /*
     * Finds the previous time that a variable has been stored to.
     * If none, a phi node is inserted.
     */
    private TACLocalStorage getPreviousStore(
        TACVariable variable, Map<Block, Map<TACVariable, TACLocalStorage>> lastStores) {
      Map<TACVariable, TACLocalStorage> stores = lastStores.get(this);
      TACLocalStorage store = stores.get(variable);
      // defined by block
      if (store != null) return store;

      // else insert phi node after label
      TACPhi phi = new TACPhi(label.getNext(), variable);
      if (variable.needsGarbageCollection()) phi.setGarbageCollected(true);
      stores.put(variable, phi);
      for (Block block : incoming)
        phi.addPreviousStore(block.getLabel(), block.getPreviousStore(variable, lastStores));
      return phi;
    }

    // uses a BFS to see if a variable had a previous assignment
    public boolean hasPreviousStore(
        TACVariable variable, Map<Block, Map<TACVariable, TACLocalStorage>> lastStores) {
      Set<Block> visited = new HashSet<>();
      Deque<Block> queue = new ArrayDeque<>();
      queue.addLast(this); // start with current block

      while (!queue.isEmpty()) {
        Block block = queue.removeFirst();
        visited.add(block);

        for (Block parent : block.incoming) {
          if (lastStores.get(parent).containsKey(variable)) return true;
          else if (!visited.contains(parent)) queue.addLast(parent);
        }
      }

      return false;
    }

    /*
     * Adds phi nodes as needed to the current block based on the last
     * definitions of variables in other blocks.
     */
    public void addPhiNodes(Map<Block, Map<TACVariable, TACLocalStorage>> lastStores) {
      Map<TACVariable, TACLocalStorage> stores = lastStores.get(this);
      Map<TACVariable, TACLocalStorage> predecessors = new HashMap<>();

      for (TACNode node : this) {
        if (node instanceof TACLocalStorage) { // both TACLocalStore and TACPhiStore
          TACLocalStorage store = (TACLocalStorage) node;
          TACVariable variable = store.getVariable();

          // Useful to know if there was a previous store
          // primarily for GC: no need to decrement something that was never assigned
          if (node instanceof TACLocalStore) {
            TACLocalStore localStore = (TACLocalStore) node;
            if (predecessors.get(variable) != null) {
              TACOperand value = predecessors.get(variable).getValue();
              if (!(value instanceof TACLiteral)
                  || !(((TACLiteral) value).getValue() instanceof ShadowNull))
                localStore.setPreviousStore(true);
            } else if (hasPreviousStore(variable, lastStores)) localStore.setPreviousStore(true);
          }

          predecessors.put(variable, store);
        } else if (node instanceof TACLocalLoad) {
          TACLocalLoad load = (TACLocalLoad) node;
          TACVariable variable = load.getVariable();
          TACOperand store = predecessors.get(variable);
          if (store == null) {
            // add phi
            TACPhi phi = new TACPhi(label.getNext(), variable);
            if (variable.needsGarbageCollection()) phi.setGarbageCollected(true);
            if (!stores.containsKey(variable)) stores.put(variable, phi);
            predecessors.put(variable, phi);
            for (Block block : incoming)
              phi.addPreviousStore(block.getLabel(), block.getPreviousStore(variable, lastStores));
            load.setPreviousStore(phi);
          } else load.setPreviousStore(store);
        }
      }
    }

    /*
     * Changes to phi nodes used for indirect branches may have been taken place.
     * This method updates the incoming and outgoing sets for Blocks based on those changes.
     */
    public boolean updatePhiBranches() {
      boolean changed = false;
      if (lastNode instanceof TACBranch) {
        TACBranch branch = (TACBranch) lastNode;
        if (branch.isIndirect()) {
          TACPhi phi = branch.getPhi();
          // indirect phi branches are fewer than currently believed to be outgoing!
          if (phi.getPreviousStores().size() < outgoing.size()) {
            changed = true;

            // find all possible destinations
            Set<TACLabel> destinations = new HashSet<>();
            for (TACOperand address : phi.getPreviousStores().values())
              destinations.add(((TACLabelAddress) address).getLabel());

            // find blocks that are no longer visited
            Set<Block> notVisited = new HashSet<>();
            for (Block block : outgoing)
              if (!destinations.contains(block.label)) notVisited.add(block);

            // remove such blocks from outgoing
            // and remove ourself from their incoming
            for (Block block : notVisited) {
              outgoing.remove(block);
              block.incoming.remove(this);
            }
          }
        }
      }

      return changed;
    }

    public Set<Block> getOutgoing() {
      return outgoing;
    }

    /*
     * Removes the TAC nodes from the current block
     * as part of pruning the current block away.
     */
    public boolean removeNodes() {
      boolean edgesUpdated = false;

      for (Block block : outgoing) if (block.removePhiInput(this)) edgesUpdated = true;

      TACNode node = label;

      // Don't use iterator here since removal is involved
      if (node != null && lastNode != null) {
        while (node != lastNode) {
          TACNode temp = node.getNext();
          removeNode(node);
          node = temp;
        }
        // Now firstNode == lastNode, but it still needs to be removed
        removeNode(lastNode);
        lastNode = null;
      }

      return edgesUpdated;
    }

    /*
     * Removes the given block as an input to any phi nodes
     * present in the current block.
     */
    private boolean removePhiInput(Block block) {
      TACCallFinallyFunction function = null;

      Set<TACPhi> stores = new HashSet<>();
      for (TACNode node : this) {
        if (node instanceof TACPhi) {
          TACPhi store = (TACPhi) node;
          if (store.removePreviousStore(block.getLabel())) stores.add(store);
        } else if (node instanceof TACCallFinallyFunction) {
          function = (TACCallFinallyFunction) node;
        }
      }

      // after updating phi input, update branches
      Set<Block> oldBranches = outgoing;
      outgoing = new HashSet<>();
      addEdges(this);

      // If updated branches is smaller, we're going to have to continue
      boolean changed = outgoing.size() < oldBranches.size();

      // Updating phi nodes can affect finally functions that branch back using the phi
      if (function != null && function.getNext() instanceof TACBranch) {
        TACBranch branch = (TACBranch) function.getNext();
        if (branch.isIndirect() && stores.contains(branch.getPhi())) {
          TACReturn return_ = function.getFinallyFunction().getReturn();
          TACNode node = return_.getPrevious();
          while (!(node instanceof TACLabel)) node = node.getPrevious();
          TACLabel label = (TACLabel) node;

          Block finallyReturnBlock = nodeBlocks.get(label);
          oldBranches = finallyReturnBlock.outgoing;

          finallyReturnBlock.outgoing = new HashSet<>();
          addEdges(finallyReturnBlock);

          changed = changed || finallyReturnBlock.outgoing.size() < oldBranches.size();
        }
      }

      return changed;
    }

    /*
     * Returns the label that starts the block.
     * Every block starts with a label.
     */
    public TACLabel getLabel() {
      return label;
    }

    /*
     * Returns the node that ends the block.
     */
    public TACNode getLast() {
      return lastNode;
    }

    /*
     * Removes a node from the TAC listing.
     * If it's not merely control flow, it adds an error to the error list
     * stating that the code is unreachable. Generating the TAC sometimes
     * creates "junk" branches and labels that can't be reached.
     */
    private void removeNode(TACNode node) {
      /* Labels and label addresses are not counted as errors
       * Any node without a context is structural stuff added to form legal LLVM
       * Code inside a cleanup can be removed because finally blocks sometimes have
       * two copies: the normal (cleanup) copy and the unwind copy.  As long as at least
       * one of the two is reachable, the original Shadow is legal.
       */
      if (!(node instanceof TACLabel)
          && !(node instanceof TACLabelAddress)
          && node.getContext() != null
          && !node.getBlock().isInsideCleanup())
        addError(node.getContext(), Error.UNREACHABLE_CODE, "Code cannot be reached");

      node.remove();
    }

    /*
     * Adds a branch from the current block to a target block.
     */
    public void addBranch(Block target) {
      if (target != null) {
        outgoing.add(target);
        target.incoming.add(this);
      }
    }

    /*
     * Adds a node to the current block.
     * Since a block only keeps track of the first node (a label)
     * and the last node in the block, this method only updates the
     * last node.  If a node is added out of order, an
     * IllegalArgumentException will be thrown.
     */
    public void addNode(TACNode node) {
      if (lastNode != null && lastNode.getNext() != node)
        throw new IllegalArgumentException("Cannot add TAC nodes out of order");

      lastNode = node;
    }

    /*
     * Flags this block as one that returns directly.
     */
    public void flagReturns() {
      returns = true;
    }

    /*
     * Flags this block as one that unwinds the stack by throwing an
     * uncaught exception
     */
    public void flagUnwinds() {
      unwinds = true;
    }

    public boolean returnsDirectly() {
      return returns;
    }

    public boolean unwinds() {
      return unwinds;
    }

    public int branches() {
      return outgoing.size();
    }

    private class NodeIterator implements Iterator<TACNode> {
      private boolean done = false;
      private TACNode current = label;

      @Override
      public boolean hasNext() {
        return !done;
      }

      @Override
      public TACNode next() {
        if (done) throw new NoSuchElementException();

        TACNode node = current;
        if (current == lastNode) done = true;
        else current = current.getNext();
        return node;
      }

      @Override
      public void remove() {
        if (done) throw new NoSuchElementException();

        TACNode node = current;

        if (current == lastNode) {
          lastNode = current.getPrevious();
          done = true;
        } else current = current.getNext();

        node.remove();
      }
    }

    @Override
    public Iterator<TACNode> iterator() {
      return new NodeIterator();
    }

    @SuppressWarnings("unused")
    public void addCallEdges(CallGraph calls, Type type) {
      for (TACNode node : this) {
        if (node instanceof TACCall) {
          TACCall call = (TACCall) node;
          if (call.getMethodRef() instanceof TACMethodName) {
            MethodSignature signature = ((TACMethodName) call.getMethodRef()).getSignature();
            // if the call is one present in the graph, add an edge
            // edges are from callee -> caller
            if (calls.contains(signature)) calls.addEdge(signature, method.getSignature());
          }
        }

        // TODO: Add something for when method references are stored?
      }
    }
  }

  /* Allows strings to be sorted based on given indexes */
  private static class IndexedString implements Comparable<IndexedString> {
    private final String string;
    private final int index;

    public IndexedString(int index, String string) {
      this.index = index;
      this.string = string;
    }

    @Override
    public int compareTo(IndexedString other) {
      if (other != null) return Integer.compare(index, other.index);
      else return -1;
    }

    public String getString() {
      return string;
    }
  }

  public static class StorageData {
    @SuppressWarnings("unchecked")
    private final Set<String>[] sets = new Set[3];

    public StorageData() {
      for (int i = 0; i < sets.length; ++i) sets[i] = new HashSet<>();
    }

    public Set<String> getLoadsBeforeStores() {
      return sets[0];
    }

    public Set<String> getLoads() {
      return sets[1];
    }

    public Set<String> getThisStores() {
      return sets[2];
    }

    public boolean addAll(StorageData other) {
      boolean changed = false;
      int size;

      for (int i = 0; i < sets.length; ++i) {
        size = sets[i].size();
        sets[i].addAll(other.sets[i]);

        if (size != sets[i].size()) changed = true;
      }

      return changed;
    }
  }
}
