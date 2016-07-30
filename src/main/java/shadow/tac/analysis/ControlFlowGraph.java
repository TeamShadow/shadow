package shadow.tac.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import shadow.Loggers;
import shadow.interpreter.ShadowBoolean;
import shadow.parse.Context;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
import shadow.tac.TACMethod;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLabelAddress;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACLocalLoad;
import shadow.tac.nodes.TACLocalStorage;
import shadow.tac.nodes.TACLocalStore;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACParameter;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACUpdate;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

/**
 * Represents the various paths which code execution can take within a method.
 * Useful for determining whether or not a method returns in all cases.
 * 
 * @author Brian Stottler
 * @author Barry Wittman
 */
public class ControlFlowGraph extends ErrorReporter implements Iterable<ControlFlowGraph.Block>
{
	private Block root;	
	private String cachedString; // Saves toString() from re-walking the graph
	private Map<TACLabel, Block> nodeBlocks = new HashMap<TACLabel, Block>();
	private TACMethod method;
	//field names can be repeated in inner classes
	private Map<Type, Set<String>> usedFields = new HashMap<Type, Set<String>>();
	//Method signatures include information that identifies class
	private Set<MethodSignature> usedPrivateMethods = new HashSet<MethodSignature>();
	
	/**
	 * Create a control flow graph for a method.
	 */
	public ControlFlowGraph(TACMethod method)
	{		
		super(Loggers.TYPE_CHECKER);
		this.method = method;
		
		//a method can only directly use fields in its class and outer classes
		Type type = method.getSignature().getOuter();
		while( type != null ) {
			usedFields.put(type, new HashSet<String>());
			type = type.getOuter();
		}
		
		createBlocks(method);
		addEdges();
	}

	/**
	 * Get the method associated with this graph.
	 * 
	 * @return method
	 */
	public TACMethod getMethod()
	{
		return method;
	}
	
	/*
	 * Adds edges between all blocks.
	 */
	private void addEdges()
	{		
		for( Block block : nodeBlocks.values() )
			addEdges(block);
	}	

	@Override
	public Iterator<ControlFlowGraph.Block> iterator() {
		return nodeBlocks.values().iterator();
	}
	
	public Map<Type, Set<String>>  getUsedFields() {
		return usedFields;
	}
	
	public Set<MethodSignature>  getUsedPrivateMethods() {
		return usedPrivateMethods;
	}

	/*
	 * Adds edges from a block to all blocks it branches to.
	 */
	private void addEdges(Block block)
	{
		TACNode node = block.getLabel();
		boolean done = false;		
		
		while( !done ) {
			if( node instanceof TACBranch ) {			
				TACBranch branch = (TACBranch) node;			
				if (branch.isConditional()) {					
					TACOperand condition = branch.getCondition();
					//Hack that allows things like if(true) or while(false) to be handled
					if( condition instanceof TACLiteral && ((TACLiteral)condition).getValue() instanceof ShadowBoolean ) {
						ShadowBoolean value = (ShadowBoolean) ((TACLiteral)condition).getValue();
						if( value.getValue() ) {
							block.addBranch(nodeBlocks.get(branch.getTrueLabel()));
							branch.convertToDirect(branch.getTrueLabel());
						}
						else {
							block.addBranch(nodeBlocks.get(branch.getFalseLabel()));
							branch.convertToDirect(branch.getFalseLabel());
						}
					}
					else {					
						block.addBranch(nodeBlocks.get(branch.getTrueLabel()));
						block.addBranch(nodeBlocks.get(branch.getFalseLabel()));
					}
				}
				else if (branch.isDirect())
					block.addBranch(nodeBlocks.get(branch.getLabel()));				
				else if (branch.isIndirect()) {
					// Branch to every possible destination
					TACPhi phi = branch.getPhi();
					Map<TACLabel, TACOperand> destinations = phi.getPreviousStores();
					if( destinations.size() == 1 ) {
						TACLabelAddress address = (TACLabelAddress) destinations.values().iterator().next();
						TACLabel label = address.getLabel();
						branch.convertToDirect(label);
						block.addBranch(nodeBlocks.get(label));
					}
					else
						for( TACOperand destination : destinations.values() ) {
							TACLabelAddress address = (TACLabelAddress) destination;
							block.addBranch(nodeBlocks.get(address.getLabel()));
						}
				}
			}
			//handles cases where a method call can cause a catchable exception
			else if( node instanceof TACCall ) {
				TACCall call = (TACCall) node;
				if( call.getBlock().hasLandingpad() ) {
					block.addBranch(nodeBlocks.get(call.getBlock().getLandingpad()));					
					block.addBranch(nodeBlocks.get(call.getNoExceptionLabel()));
				}
			}
			else if( node instanceof TACThrow ) {
				TACThrow throw_ = (TACThrow)node;
				if( throw_.getBlock().hasLandingpad() )
					block.addBranch(nodeBlocks.get(throw_.getBlock().getLandingpad()));
			}
			
			if( node == block.getLast() )
				done = true;
			else
				node = node.getNext();
		}
	}

	/*
	 * Divides the code in a method into blocks.
	 */
	private void createBlocks(TACMethod method)
	{	
		TACNode node = method.getNode(); //junk tree thing?
		boolean starting = true;
		boolean done = false;
		Block block = null;
		
		Set<TACVariable> usedLocalVariables = new HashSet<TACVariable>();
		
		// Loop through circular linked-list
		while( !done ) {
			if( node instanceof TACLabel ) {
				TACLabel label = (TACLabel)node;
				block = new Block(label);
				if( starting == true ) {
					root = block;
					starting = false;
				}				
				nodeBlocks.put(label, block);
			}
			else if( node instanceof TACReturn )
				block.flagReturns();
			else if( node instanceof TACThrow || node instanceof TACResume )
				block.flagUnwinds();
			//record field usage, for warnings
			else if( node instanceof TACLoad ) {
				TACLoad load = (TACLoad) node;
				TACReference ref = load.getReference();
				if( ref instanceof TACFieldRef ) {
					TACFieldRef field = (TACFieldRef) ref;
					Set<String> fields = usedFields.get(field.getPrefixType());
					if( fields != null )
						fields.add(field.getName());
				}
			}
			//record private method usage, for warnings
			else if( node instanceof TACCall ) {
				TACCall call = (TACCall) node;
				TACMethodRef methodRef = call.getMethodRef();
				MethodSignature signature = methodRef.getSignature().getSignatureWithoutTypeArguments();
				if( signature.getModifiers().isPrivate() && signature.getOuter().encloses(method.getThis().getType()) )
					usedPrivateMethods.add(signature);
			}
			//record local variable usage, for warnings
			else if( node instanceof TACLocalLoad ) {
				TACLocalLoad load = (TACLocalLoad) node;
				usedLocalVariables.add(load.getVariable());
			}
			
			block.addNode(node);
						
			node = node.getNext();
			if( node == method.getNode() )
				done = true;
		}
		
		//give warnings for unused local variables
		//skip all copy methods, since they're automatically generated (and often don't use the "addresses" variable)
		if( !method.getSignature().getSymbol().equals("copy"))
			for( TACVariable variable : method.getLocals() ) {
				String name = variable.getName(); 
				//special compiler-created variables (_temp, _exception, etc.) start with _
				//no need to check on "this"
				
				if( !name.startsWith("_") && !name.equals("this") ) { 
					if( !usedLocalVariables.contains(variable) ) {
						ModifiedType type = variable.getModifiedType();					
						//kind of a hack to get the declaration
						if( type instanceof Context ) {						
							Context declaration = (Context) type;
							//we don't add warnings for formal parameters: method parameters and catch parameters
							if( !(declaration instanceof ShadowParser.FormalParameterContext) )
								addWarning( declaration, Error.UNUSED_VARIABLE, "Local variable " + variable.getOriginalName() + " is never used");
						}
						else
							addWarning( method.getSignature().getNode(), Error.UNUSED_VARIABLE, "Local variable " + variable.getOriginalName() + " is never used");
					}
				}
			}
		
	}
	
	/**
	 * Removes blocks of code that are unreachable.
	 *  
	 * @return true if at least one block was removed
	 */
	public boolean removeUnreachableCode()
	{
		boolean edgesUpdated;
		boolean changed = false;
		
		do {
			edgesUpdated = false;
			Set<Block> reachable = new HashSet<Block>();
			findReachable(root, reachable);
			
			Set<Block> unreachable = new HashSet<Block>(nodeBlocks.values());
			unreachable.removeAll(reachable);
			
			if( unreachable.size() > 0 )
				changed = true;
			
			//If edges are updated in the process, new nodes might become unreachable
			for( Block block : unreachable ) {
				if( block.removeNodes() )
					edgesUpdated = true;
				nodeBlocks.remove(block.getLabel());
				block.removeEdges();
			}
			
		} while( edgesUpdated );
		
		cachedString = null; //reset cachedString		
		return changed;
	}
	
	/*
	 * Find all blocks reachable from the starting block. 
	 */
	private static void findReachable(Block block, Set<Block> visited)
	{
		if( !visited.contains(block) ) {
			visited.add(block);
			for( Block branch : block.getOutgoing() )
				findReachable(branch, visited);
		}
	}	

	/** 
	 * Traverses the graph to determine if the corresponding method returns in
	 * all cases.
	 */
	public boolean returns()
	{
		Set<Block> visited = new HashSet<Block>();
		
		return returns(root, visited);
	}
	
	/*
	 * Recursive helper to see if all blocks starting at block return.
	 */
	private static boolean returns(Block block, Set<Block> visited)
	{
		if (visited.contains(block))
			return true;
		
		visited.add(block);
		
		// A block should either branch or return
		if (block.branches() > 0) {
			// Ensure everything we can branch to returns
			for (Block child : block.getOutgoing())
				if (!returns(child, visited))
					return false;
			
			return true;
		} else {
			// If we don't branch, this block should return directly or unwind by throwing an uncaught exception
			return block.returnsDirectly() || block.unwinds();
		}
	}
	
	/* 
	 * Generates a String representation of the graph.
	 */
	private String generateString()
	{
		Set<Block> visited = new HashSet<Block>();
		List<IndexedString> strings = new ArrayList<IndexedString>();
		
		generateString(root, visited, strings);
		
		// Order the strings for each block and combine them
		Collections.sort(strings);
		StringBuilder builder = new StringBuilder();
		for (IndexedString string : strings)
			builder.append(string.getString() + "\n");
		
		return builder.toString();
	}
	
	/* 
	 * Traverses the graph to build a readable print-out of it. Useful for
	 * debugging.
	 */
	private static void generateString(Block current, Set<Block> visited, 
			List<IndexedString> strings)
	{
		if (visited.contains(current))
			return;
		
		visited.add(current);
		
		List<Integer> children = new ArrayList<Integer>();
		for (Block child : current.getOutgoing()) {
			children.add(child.getNumber());
			generateString(child, visited, strings);
		}
		
		// Lead with the current block's ID
		String output = Integer.toString(current.getNumber()) + ": ";
		
		// List the blocks it can branch to
		Collections.sort(children);
		for (int i = 0; i < children.size(); ++i) {
			if (i != 0)
				output += ", ";
			output += Integer.toString(children.get(i)) + " ";
		}
		
		// Indicate whether or not it returns directly
		if (current.returnsDirectly())
			output += "(RETURNS)";
		else if( current.unwinds() )
			output += "(UNWINDS)";
			
		strings.add(new IndexedString(current.getNumber(), output));			
	}
	
	
	/** 
	 * Adds phi nodes to blocks where needed.
	 * This method *must* be called in order to create the phi nodes
	 * needed for constant propagation, checking for undefined variables,
	 * and proper LLVM IR generation. 
	 */
	public void addPhiNodes() {
		Map<Block,Map<TACVariable,TACLocalStorage>> lastDefinitions = new HashMap<Block,Map<TACVariable, TACLocalStorage>>();
		
		Collection<Block> blocks = nodeBlocks.values();				
		
		//find the last definition of every variable in every block
		for( Block block : blocks )
			lastDefinitions.put(block, block.getLastStores());		
		
		//use those definitions when constructing phi nodes
		for( Block block : blocks )
			block.addPhiNodes(lastDefinitions);
	}
	

	/**
	 * Propagate constants and other values through the SSA representation.
	 * This method is not optional if we want to produce legal LLVM IR.
	 * Propagating constants will also find undefined local variables,
	 * adding appropriate error messages to the error list.
	 * 
	 * @return true if constants propagated more deeply than before 
	 */	
	public boolean propagateConstants()
	{		
		List<Block> blocks = getReversePostorder();
		
		boolean done = false;
		boolean changed = false;
		Set<TACLocalLoad> undefinedLoads = new HashSet<TACLocalLoad>();
		
		while( !done ) {
			done = true;
			undefinedLoads.clear();
			for( Block block : blocks )
				if( block.propagateValues(undefinedLoads) ) {
					done = false;
					changed = true;
				}
		}
		
		for( TACLocalLoad undefined : undefinedLoads )
			//_exception is a special variable used only for exception handling
			//indirect breaks for finally make its value tricky, but it's guaranteed to never *really* be undefined
			if( !undefined.getVariable().getOriginalName().equals("_exception") )
				addError(undefined.getContext(), Error.UNDEFINED_VARIABLE, "Variable " + undefined.getVariable().getOriginalName() + " may not have been defined before use");
		
		return changed;
	}

	@Override
	public String toString()
	{
		if( cachedString == null )
			cachedString = generateString();
		
		return cachedString;
	}
	
	/**
	 * Returns a list giving the graph in reverse postorder.
	 * Reverse postorder is considered to be an easy ordering
	 * of a rooted, directed graph that reduces the number of
	 * children visited before their parents are visited. Cycles
	 * in the graph, of course, make the notion of children and parents
	 * somewhat fuzzy.  This ordering reduces the running time for
	 * the data flow equations to reach equilibrium.
	 * @return list of blocks in reverse postorder with respect to the root 
	 */
	public List<Block> getReversePostorder() {
		List<Block> list = new ArrayList<Block>(nodeBlocks.size());
		Set<Block> visited = new HashSet<Block>(nodeBlocks.size() * 2);		
		postorder(root, list, visited);
		Collections.reverse(list); //reverse list
		return list;
	}
	
	private static void postorder(Block block, List<Block> list, Set<Block> visited) {
		if( !visited.contains(block) ) {			
			visited.add(block);			
			for( Block child : block.outgoing )
				postorder(child, list, visited);			
			list.add(block);
		}
	}
	
	
	public Set<String> getInitializedFields(Set<String> alreadyInitialized, Set<String> thisStores, Map<MethodSignature, StorageData> methodData, Set<String> fieldsNeedingInitialization)
	{	
		Map<Block,Set<String>> initialLoadsBeforeStores = new HashMap<Block,Set<String>>();
		Map<Block,Set<String>> initialStores = new HashMap<Block,Set<String>>();
		Map<Block,Set<String>> allLoadsBeforeStores = new HashMap<Block,Set<String>>();
		Map<Block,Set<String>> allStores = new HashMap<Block,Set<String>>();
		
		Set<String> loads = new TreeSet<String>();
		List<Block> blocks = getReversePostorder(); //converges faster since many blocks will be visited only after their predecessors
		
		for(Block block : blocks ) {
			Set<String> loadsBeforeStores = new TreeSet<String>();			
			Set<String> stores = new TreeSet<String>();			
				
			block.recordLoadsAndStoresInCreates(method.getThis().getType(), loadsBeforeStores, stores, loads, thisStores, methodData);			
			
			initialLoadsBeforeStores.put(block, loadsBeforeStores);
			initialStores.put(block, stores);			
			
			if( block == root ) {
				Set<String> set = new TreeSet<String>(loadsBeforeStores);
				set.removeAll(alreadyInitialized);				
				allLoadsBeforeStores.put(block, set);
				
				set = new TreeSet<String>(stores);
				set.addAll(alreadyInitialized);				
				allStores.put(block, set);
			}
			else {
				allLoadsBeforeStores.put(block, new TreeSet<String>());
				allStores.put(block, new TreeSet<String>(fieldsNeedingInitialization)); //for data flow equations, assume everything is stored initially 
			}
		}		
		
		boolean changed = true;		
		Map<Block, Set<String>> allPreviousStores = new HashMap<Block, Set<String>>();
		
		//keep going as long as changes to the stores are being detected
		while( changed ) {
			changed = false;
			
			for(Block block : blocks ) {
				if( block != root ) {					
					//find previous stores, the intersection of the stores of previous blocks
					Set<String> previousStores = null;				
					
					for( Block parent : block.incoming ) {	
						if( previousStores == null )
							previousStores = allStores.get(parent);
						else
							previousStores = intersect( previousStores, allStores.get(parent));
					}
					allPreviousStores.put(block, previousStores); //keep record for later error reporting					
					
					//get current state of stores and loads before stores
					Set<String> stores = allStores.get(block);
					Set<String> loadsBeforeStores = allLoadsBeforeStores.get(block);
					
					//record size, to detect changes
					int storesSize = stores.size();
					int loadsBeforeSize = loadsBeforeStores.size();						
										
					//update stores
					stores.clear();
					stores.addAll(initialStores.get(block));
					stores.addAll(previousStores);					
					
					//update loads before stores
					loadsBeforeStores.addAll(initialLoadsBeforeStores.get(block));
					loadsBeforeStores.removeAll(previousStores);									
					
					if( storesSize != stores.size() || loadsBeforeSize != loadsBeforeStores.size() )
						changed = true;
				}
			}
		}
		
		//root has no previous stores
		allPreviousStores.put(root, new TreeSet<String>());		
		
		//find blocks that return and record errors for fields that are used before they are initialized
		List<Block> returningBlocks = new ArrayList<Block>();
		for(Block block : nodeBlocks.values() ) {
			if( block.returnsDirectly() )
				returningBlocks.add(block);
			Set<String> loadsBeforeStores = allLoadsBeforeStores.get(block);
			if( loadsBeforeStores.size() > 0 )
				block.addErrorsForUninitializedFields(allPreviousStores.get(block), methodData);
		}
		
		//check all loads of fields that might contain a "this"		
		for( String field : thisStores ) {
			//field might contain "this" and is therefore illegal to load
			if( loads.contains(field) )
				addError(method.getSignature().getNode(), Error.READ_OF_THIS_IN_CREATE, "Field " + field + " that might contain a reference to \"this\" cannot be read in a create or methods called by a create" );
		}
		
		//intersect all of the fields initialized by the time any returning block is reached
		Set<String> initializedFields = null;
		for( Block block : returningBlocks )
			if( initializedFields == null )
				initializedFields = allStores.get(block);
			else
				initializedFields = intersect(initializedFields, allStores.get(block));	
		
		return initializedFields;		
	}
	
	public StorageData getLoadsBeforeStoresInMethods(Type type, CallGraph callGraph) {
		StorageData data = new StorageData();
		for(Block block : nodeBlocks.values() )
			block.recordLoadsBeforeStoresInMethods(type, data, callGraph);
		
		return data;
	}
	
	private Set<String> intersect(Set<String> a, Set<String> b) {
		Set<String> result = new TreeSet<String>();
	
		//always iterate over smaller set
		if( b.size() < a.size() ) {
			Set<String> temp = a;
			a = b;
			b = temp;
		}
		
		for( String string : a )
			if( b.contains(string) )
				result.add(string);
		
		return result;
	}
	
	//constants are already taken care of
	//nullable fields are initialized to null
	//primitive types are initialized to reasonable defaults
	//arrays (surprisingly) are value types, initialized with lengths of zero
	public static boolean needsInitialization(ModifiedType modifiedType) {
		Modifiers modifiers = modifiedType.getModifiers();
		Type type = modifiedType.getType();
		
		return !modifiers.isConstant() && !modifiers.isNullable() && !type.isPrimitive() && !(type instanceof ArrayType)  && !(type instanceof SingletonType);
	}
	
	public void addCallEdges(CallGraph calls, Type type) {
		for(Block block : this)
			block.addCallEdges(calls, type);		
	}
	
	/*
	 * Represents a contiguous sequence of TAC nodes, unbroken by branch
	 * statements.
	 */
	public class Block implements Iterable<TACNode>
	{		
		private Set<Block> outgoing = new HashSet<Block>();
		private Set<Block> incoming = new HashSet<Block>();
		private final TACLabel label;
		private boolean returns = false;
		private boolean unwinds = false;
		private TACNode lastNode = null;  //last TAC node in this block
		
		@Override
		public String toString()
		{
			return "Block " + label.getNumber();
		}		
		
		public void addErrorsForUninitializedFields(Set<String> previousStores, Map<MethodSignature, StorageData> methodData)
		{
			Set<String> stores = new HashSet<String>(previousStores);
			Type type = method.getThis().getType();			
			
			for( TACNode node : this ) {				
				if( node instanceof TACStore ) {
					TACStore store = (TACStore) node;
					if( store.getReference() instanceof TACFieldRef ) {
						TACFieldRef field = (TACFieldRef) store.getReference();
						//we don't care about nullables or primitives
						if( operandIsThis(field.getPrefix(), type) && needsInitialization(field) )							
							stores.add(field.getName());
						else if( operandIsThis(store.getValue(), type) )						
							for( Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet()  )
								if( !stores.contains(entry.getKey()) && needsInitialization(entry.getValue()) )
									addError(node.getContext(), Error.UNINITIALIZED_FIELD, "Current object cannot be stored in a field or array before field " + entry.getKey() + " is initialized" );
					}					
					else if( operandIsThis(store.getValue(), type) ) { //store of "this" somewhere
						for( Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet()  )
							if( !stores.contains(entry.getKey()) && needsInitialization(entry.getValue()) )
								addError(node.getContext(), Error.UNINITIALIZED_FIELD, "Current object cannot be stored in a field or array before field " + entry.getKey() + " is initialized" );
					}
				}									
				else if( node instanceof TACLoad ) {
					TACLoad load = (TACLoad)node;
					if( load.getReference() instanceof TACFieldRef ) {
						TACFieldRef field = (TACFieldRef) load.getReference();
						//we don't care about nullables or primitives
						//class and _outer are special cases, guaranteed to be initialized
						if( operandIsThis(field.getPrefix(), type) && !stores.contains(field.getName()) && !field.getName().equals("class") && !field.getName().equals("_outer") && needsInitialization(field)  )
							addError(node.getContext(), Error.UNINITIALIZED_FIELD, "Field " + field.getName() + " may have been used without being initialized" );
					}
				}
				else if( node instanceof TACCall ) {
					TACCall call = (TACCall)node;
					MethodSignature signature = call.getMethodRef().getSignature();
					if( operandIsThis( call.getPrefix(), type ) ) {						 
						//see if it's in the list of methods whose field usage we track
						//otherwise, it would have already caused an error
						if( methodData.containsKey(signature) ) {
							Set<String> fields = methodData.get(signature).getLoadsBeforeStores();							
							for( String field : fields  )
								if( !stores.contains(field) )
									addError(node.getContext(), Error.UNINITIALIZED_FIELD, "Method call is not permitted before field " + field + " is initialized");
						}
					}
				}					
			}
		}
		
		public void recordLoadsBeforeStoresInMethods(Type type, StorageData data, CallGraph callGraph)
		{	
			Set<String> stores = new HashSet<String>();
			
			for( TACNode node : this ) {				
				if( node instanceof TACCall ) {
					TACCall call = (TACCall)node;
					//freak out if we're calling a method that takes "this"
					//as a parameter and it's not one of the methods we're tracking
					//all possible fields could be affected
					if( !callGraph.contains(call.getMethod().getSignature()) ) { 
						for( int i = 0; i < call.getNumParameters(); ++i )
							if( operandIsThis(call.getParameter(i), type) ) { 
								for( Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet()  )
									if( !stores.contains(entry.getKey()) && needsInitialization(entry.getValue()) )
										data.getLoadsBeforeStores().add(entry.getKey());
								break;
							}
					}
				}
				else
					recordLoadsAndStores(node, type, data.getLoadsBeforeStores(), stores, data.getLoads(), data.getThisStores());
			}			
		}	
		
		public void recordLoadsAndStoresInCreates(Type type, Set<String> loadsBeforeStores, Set<String> stores, Set<String> loads, Set<String> thisStores, Map<MethodSignature, StorageData> methodData )
		{	
			for( TACNode node : this ) {				
				if( node instanceof TACCall ) {
					TACCall call = (TACCall)node;
					MethodSignature signature = call.getMethodRef().getSignature();
					if( operandIsThis( call.getPrefix(), type ) ) {
						//creates are handled separately, and we have to assume that native code works
						if( !signature.isCreate() && !signature.isNative() ) {							
							//if we've recorded the fields a method uses, add those to the loads before stores
							if( methodData.containsKey(signature) ) {							
								loadsBeforeStores.addAll(methodData.get(signature).getLoadsBeforeStores());
								loads.addAll(methodData.get(signature).getLoads());
								thisStores.addAll(methodData.get(signature).getThisStores());
							}
							//otherwise, it's not a legal method to call, probably because it's unlocked
							else
								addError(node.getContext(), Error.ILLEGAL_ACCESS, "Cannot call unlocked method " + signature.getSymbol() + signature.getMethodType() + " from a create" );
						}
					}
					//an inner class call, perhaps even a create
					else if( methodData.containsKey(signature) ) {
						loadsBeforeStores.addAll(methodData.get(signature).getLoadsBeforeStores());
						loads.addAll(methodData.get(signature).getLoads());
						thisStores.addAll(methodData.get(signature).getThisStores());
					}
					//not a call inside the current file
					else {						
						for( int i = 1; i < call.getNumParameters(); ++i ) //always an extra parameter for "this"
							if( operandIsThis(call.getParameter(i), type)  ) {
								addError(node.getContext(), Error.ILLEGAL_ACCESS, "Current object cannot be passed as a parameter inside of its create" );
								break;
							}						
					}
				}
				else
					recordLoadsAndStores(node, type, loadsBeforeStores, stores, loads, thisStores);
			}			
		}
		
		private void recordLoadsAndStores(TACNode node, Type type, Set<String> loadsBeforeStores, Set<String> stores, Set<String> loads, Set<String> thisStores) 
		{
			if( node instanceof TACStore ) {
				TACStore store = (TACStore) node;
				if( store.getReference() instanceof TACFieldRef ) {
					TACFieldRef field = (TACFieldRef) store.getReference();					
					if( operandIsThis(field.getPrefix(), type) && needsInitialization(field)    ) {							
						stores.add(field.getName());
						if( operandIsThis(store.getValue(), type) ) //store of "this" inside current object
							thisStores.add(field.getName());							
					}
					else if( operandIsThis(store.getValue(), type) )
						//might leak all data						
						for( Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet()  )
							if( !stores.contains(entry.getKey()) && needsInitialization(entry.getValue()) )
								loadsBeforeStores.add(entry.getKey());
				}					
				else if( operandIsThis(store.getValue(), type) ) { //store of "this", but not in a field in the current object
					//might leak all data						
					for( Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet()  )
						if( !stores.contains(entry.getKey()) && needsInitialization(entry.getValue()) )
							loadsBeforeStores.add(entry.getKey());
				}
			}									
			else if( node instanceof TACLoad ) {
				TACLoad load = (TACLoad)node;
				if( load.getReference() instanceof TACFieldRef ) {
					TACFieldRef field = (TACFieldRef) load.getReference();				
					//class and _outer are special cases, guaranteed to be initialized
					if( operandIsThis(field.getPrefix(), type) && !field.getName().equals("class") && !field.getName().equals("_outer") ) {
						if( needsInitialization(field) ) {
							if( !stores.contains(field.getName()) )							
								loadsBeforeStores.add(field.getName());							
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
				while( op instanceof TACCast ) {
					TACCast cast = (TACCast)op;
					op = cast.getOperand(0);				
				}			
				if( op instanceof TACUpdate )
					op = ((TACUpdate)op).getValue();				
			} while( start != op );
			
			return op;
		}
		
		private boolean operandIsThis(TACOperand op, Type type) {
			Set<TACPhi> visitedPhis = new HashSet<TACPhi>();
			return operandIsThis(op, type, visitedPhis);
		}
		
		//as long as the data flow analysis has already happened, it should be hard to sneak
		//in a variable that stores "this" without us detecting it
		private boolean operandIsThis(TACOperand op, Type type, Set<TACPhi> visitedPhis) {
			op = getValue(op);
			
			//current type
			if( method.getThis().getType().equals(type) ) {
				if( op instanceof TACParameter ) {
					TACParameter parameter = (TACParameter)op;
					return parameter.getNumber() == 0; //first parameter is always "this"
				}			
				else if( op instanceof TACLocalLoad ) {
					TACLocalLoad load = (TACLocalLoad) op;
					if( load.getVariable().equals(method.getThis()))
						return true;				
				}
				else if( op instanceof TACLocalStore ) {
					TACLocalStore store = (TACLocalStore)op;
					if( store.getVariable().equals(method.getThis()))
						return true;
				}			
				else if( op instanceof TACPhi ) {
					TACPhi phi = (TACPhi)op;
					if( visitedPhis.contains(phi) )
						return false;
					
					visitedPhis.add(phi);				
					for( TACOperand value : phi.getPreviousStores().values() ) {					
						if( operandIsThis(value, type, visitedPhis) )
							return true;
					}
				}
			}
			else { //an outer type				
				if( op instanceof TACLoad ) {
					TACLoad load = (TACLoad) op;
					if( load.getReference() instanceof TACFieldRef ) {
						TACFieldRef field = (TACFieldRef) load.getReference();
						return field.getName().equals("_outer") && field.getType().equals(type);
					}
				}				
			}
			
			return false;
		}
		
		

		@Override
		public boolean equals(Object other)
		{
			if( other == null || !(other instanceof Block) )
				return false;
			
			if( this == other )
				return true;
			
			Block block = (Block) other;
			return toString().equals(block.toString());
		}
		
		@Override
		public int hashCode()
		{
			return toString().hashCode();			
		}
		
		/*
		 * Gets the number of the label the block starts with.
		 */
		public int getNumber()
		{
			return label.getNumber();
		}

		/*
		 * Propagates constants through updatable nodes.
		 * If a variable is found to be undefined where it's loaded,
		 * adds the load to a list of undefined loads.  
		 */
		public boolean propagateValues(Set<TACLocalLoad> undefinedLoads)
		{
			boolean changed = false;			
			Set<TACUpdate> currentlyUpdating = new HashSet<TACUpdate>();
			for( TACNode node : this ) {				
				if( node instanceof TACUpdate )
					if( ((TACUpdate)node).update(currentlyUpdating) )
						changed = true;
				
				if( node instanceof TACLocalLoad ) {
					TACLocalLoad load = (TACLocalLoad)node;
					if( load.isUndefined() )
						undefinedLoads.add(load);
				}
				
				if( node instanceof TACBranch ) {
					TACBranch branch = (TACBranch) node;
					if( branch.isConditional() && branch.getCondition() instanceof TACUpdate  ) {
						TACUpdate update = (TACUpdate) branch.getCondition();
						if( update.update(currentlyUpdating) )
							changed = true;
						
						//simplifying branch
						if( update.getValue() instanceof TACLiteral ) {
							TACLiteral literal = (TACLiteral) update.getValue();
							boolean value = ((ShadowBoolean)literal.getValue()).getValue();
							TACLabel trueLabel = branch.getTrueLabel();
							TACLabel falseLabel = branch.getFalseLabel();							
							if( value ) {
								branch.convertToDirect(trueLabel);
								Block falseBlock = nodeBlocks.get(falseLabel);
								outgoing.remove(falseBlock);
								falseBlock.incoming.remove(this);
							}
							else {								
								branch.convertToDirect(falseLabel);
								Block trueBlock = nodeBlocks.get(trueLabel);
								outgoing.remove(trueBlock);
								trueBlock.incoming.remove(this);								
							}
							changed = true;
						}
					}
				}
			}
			
			return changed;
		}

		public void removeEdges() {
			for( Block block : incoming )
				block.outgoing.remove(this);
			for( Block block : outgoing )
				block.incoming.remove(this);
		}

		public Block(TACLabel label)
		{
			this.label = label;
		}
		
		/*
		 * Finds the last time that each variable in a block is stored to. 
		 */
		public Map<TACVariable, TACLocalStorage> getLastStores() {
			Map<TACVariable,TACLocalStorage> stores = new HashMap<TACVariable, TACLocalStorage>();
			for( TACNode node : this ) {				
				if( node instanceof TACLocalStorage ) {
					TACLocalStorage store = (TACLocalStorage)node;
					stores.put(store.getVariable(), store);					 
				}				
			}
			return stores;
		}	
		
		/*
		 * Finds the previous time that a variable has been stored to.
		 * If none, a phi node is inserted. 
		 */
		private TACLocalStorage getPreviousStore(TACVariable variable, Map<Block, Map<TACVariable, TACLocalStorage>> lastStores ) {
			Map<TACVariable, TACLocalStorage> stores = lastStores.get(this);
			TACLocalStorage store = stores.get(variable);
			 //defined by block
			if( store != null )
				return store;

			//else insert phi node after label
			TACPhi phi = new TACPhi(label.getNext(), variable);
			stores.put(variable, phi);
			for( Block block : incoming )
				phi.addPreviousStore(block.getLabel(), block.getPreviousStore(variable, lastStores));
			return phi;
		}	

		/*
		 * Adds phi nodes as needed to the current block based on the last
		 * definitions of variables in other blocks.
		 */
		public void addPhiNodes(Map<Block, Map<TACVariable, TACLocalStorage>> lastStores) {
			Map<TACVariable, TACLocalStorage> stores = lastStores.get(this);
			Map<TACVariable,TACLocalStorage> predecessors = new HashMap<TACVariable, TACLocalStorage>();
			
			for( TACNode node : this )	{		
				if( node instanceof TACLocalStorage ) {  //both TACLocalStore and TACPhiStore
					TACLocalStorage store = (TACLocalStorage)node;
					predecessors.put(store.getVariable(), store);	
				}
				else if( node instanceof TACLocalLoad ) {
					TACLocalLoad load = (TACLocalLoad)node;					
					TACVariable variable = load.getVariable();
					TACOperand store = predecessors.get(variable); 
					if( store == null ) {
						//add phi
						TACPhi phi = new TACPhi(label.getNext(), variable);
						if( !stores.containsKey(variable) )
							stores.put(variable, phi);
						predecessors.put(variable, phi);
						for( Block block : incoming )
							phi.addPreviousStore(block.getLabel(), block.getPreviousStore(variable, lastStores));							
						load.setPreviousStore(phi);
					}
					else
						load.setPreviousStore(store);
				}
			}					
		}

		public Set<Block> getOutgoing()
		{
			return outgoing;
		}
		
		/*
		 * Removes the TAC nodes from the current block
		 * as part of pruning the current block away. 
		 */
		public boolean removeNodes()
		{
			boolean edgesUpdated = false;
			
			for( Block block : outgoing )
				if( block.removePhiInput(this) )
					edgesUpdated = true;
			
			TACNode node = label;
			
			//might be wise not to use iterator here since removal is involved
			if( node != null && lastNode != null ) {
				while( node != lastNode ) {
					TACNode temp = node.getNext();
					removeNode(node);
					node = temp;
				}
				//Now firstNode == lastNode, but it still needs to be removed
				removeNode(lastNode);				
				lastNode = null;				
			}
			
			return edgesUpdated;
		}
		
		/*
		 * Removes the given block as an input to any phi nodes
		 * present in the current block. 
		 */
		private boolean removePhiInput(Block block)
		{	
			for( TACNode node : this ) {
				/*
				if( node instanceof TACPhi ) {
					TACPhiRef phiRef = ((TACPhi)node).getRef();
					phiRef.removeLabel(block.getLabel());
				}
				else*/
				if( node instanceof TACPhi ) {
					TACPhi store = (TACPhi)node;
					store.removePreviousStore(block.getLabel());					
				}
			}

			//after updating phi input, update branches			
			Set<Block> oldBranches = outgoing;
			outgoing = new HashSet<Block>();
			addEdges(this);
			
			//if updated branches is smaller, we're going to have to continue
			return outgoing.size() < oldBranches.size();
		}

		/*
		 * Returns the label that starts the block.
		 * Every block starts with a label.
		 */
		public TACLabel getLabel() 
		{
			return label;
		}
		
		/*
		 * Returns the node that ends the block.
		 */
		public TACNode getLast()
		{
			return lastNode;			
		}
		

		/*
		 * Removes a node from the TAC listing.
		 * If it's not merely control flow, it adds an error to the error list
		 * stating that the code is unreachable. Generating the TAC sometimes
		 * creates "junk" branches and labels that can't be reached. 
		 */
		private void removeNode(TACNode node)
		{
			//Labels and label addresses are not counted as errors
			//Any node without a context is structural stuff added to form legal LLVM
			if( !(node instanceof TACLabel) && !(node instanceof TACLabelAddress) &&
				node.getContext() != null ) 
				addError(node.getContext(), Error.UNREACHABLE_CODE, "Code cannot be reached");
			
			node.remove();
		}


		/*
		 * Adds a branch from the current block to a target block.
		 */
		public void addBranch(Block target)
		{
			if( target != null ) {
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
		public void addNode(TACNode node)
		{	
			if( lastNode != null && lastNode.getNext() != node )
				throw new IllegalArgumentException("Cannot add TAC nodes out of order");
			
			lastNode = node;
		}
		
		/*
		 * Flags this block as one that returns directly.
		 */
		public void flagReturns()
		{
			returns = true;
		}
		
		/*
		 * Flags this block as one that unwinds the stack by throwing an
		 * uncaught exception
		 */
		public void flagUnwinds()
		{
			unwinds = true;
		}
		
				
		public boolean returnsDirectly()
		{
			return returns;
		}
		
		public boolean unwinds()
		{
			return unwinds;
		}	
		
		public int branches()
		{
			return outgoing.size();
		}
		
		private class NodeIterator implements Iterator<TACNode>
		{
			private boolean done = false;
			private TACNode current = label;

			@Override
			public boolean hasNext() {
				return !done;
			}

			@Override
			public TACNode next() {
				if( done )
					throw new NoSuchElementException();
				
				TACNode node = current;
				if( current == lastNode )
					done = true;
				else
					current = current.getNext();
				return node;				
			}

			@Override
			public void remove() {
				if( done )
					throw new NoSuchElementException();
				
				TACNode node = current;
				
				if( current == lastNode ) {
					lastNode = current.getPrevious();
					done = true;
				}
				else
					current = current.getNext();
				
				node.remove();
			}
		}
		
		@Override
		public Iterator<TACNode> iterator() {
			return new NodeIterator();
		}


		public void addCallEdges(CallGraph calls, Type type)
		{
			for( TACNode node : this ) {
				if( node instanceof TACCall ) {
					TACCall call = (TACCall)node;
					MethodSignature signature = call.getMethodRef().getSignature();
					//if the call is one present in the graph, add an edge
					//edges are from callee -> caller
					if( calls.contains(signature) )
						calls.addEdge(signature, method.getSignature());
				}				
			}		
		}
	}

	/* Allows strings to be sorted based on given indexes */
	private static class IndexedString implements Comparable<IndexedString>
	{
		private final String string;
		private final int index;
		
		public IndexedString(int index, String string)
		{
			this.index = index;
			this.string = string;
		}
		
		@Override
		public int compareTo(IndexedString other)
		{
			if (other != null)
				return Integer.compare(index, other.index);
			else
				return -1;
		}
		
		public String getString()
		{
			return string;
		}
	}	
	
	public static class StorageData {
		@SuppressWarnings("unchecked")
		private Set<String>[] sets = new Set[3];
		
		public StorageData() {
			for( int i = 0; i < sets.length; ++i )
				sets[i] = new HashSet<String>();
		}		
		
		public Set<String> getLoadsBeforeStores() 
		{
			return sets[0];		
		}
		
		public Set<String> getLoads() 
		{
			return sets[1];
		}
		
		public Set<String> getThisStores() 
		{			
			return sets[2];				
		}
		
		public boolean addAll(StorageData other) {
			boolean changed = false;
			int size;
			
			for( int i = 0; i < sets.length; ++i ) {
				size = sets[i].size();
				sets[i].addAll(other.sets[i]);
				
				if( size != sets[i].size() )
					changed = true;
			}
			
			return changed;
		}
		
	}
}
