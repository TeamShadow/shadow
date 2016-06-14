package shadow.tac.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shadow.Loggers;
import shadow.interpreter.ShadowBoolean;
import shadow.tac.TACMethod;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLocalLoad;
import shadow.tac.nodes.TACLocalStorage;
import shadow.tac.nodes.TACLocalStore;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACPhiRef;
import shadow.tac.nodes.TACPhiRef.TACPhi;
import shadow.tac.nodes.TACPhiStore;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACUnwind;
import shadow.tac.nodes.TACUpdate;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.TypeCheckException.Error;

/**
 * Represents the various paths which code execution can take within a method.
 * Useful for determining whether or not a method returns in all cases.
 * 
 * @author Brian Stottler
 * @author Barry Wittman
 */
public class ControlFlowGraph extends ErrorReporter
{
	private Block root;	
	private String cachedString; // Saves toString() from re-walking the graph
	private Map<TACLabel, Block> nodeBlocks = new HashMap<TACLabel, Block>(); 
	
	/**
	 * Create a control flow graph for a method.
	 */
	public ControlFlowGraph(TACMethod method)
	{		
		super(Loggers.TYPE_CHECKER);			
		createBlocks(method);
		addEdges();
	}
	
	/*
	 * Adds edges between all blocks.
	 */
	private void addEdges()
	{		
		for( Block block : nodeBlocks.values() )
			addEdges(block);
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
							block.addBranch(nodeBlocks.get(branch.getTrueLabel().getLabel()));
							branch.convertToDirect(branch.getTrueLabel());
						}
						else {
							block.addBranch(nodeBlocks.get(branch.getFalseLabel().getLabel()));
							branch.convertToDirect(branch.getFalseLabel());
						}
					}
					else {					
						block.addBranch(nodeBlocks.get(branch.getTrueLabel().getLabel()));
						block.addBranch(nodeBlocks.get(branch.getFalseLabel().getLabel()));
					}
				}
				else if (branch.isDirect())
					block.addBranch(nodeBlocks.get(branch.getLabel().getLabel()));				
				else if (branch.isIndirect()) {
					// Branch to every possible destination
					TACPhiRef destination = branch.getDestination();
					if( destination.getSize() == 1 ) {						
						TACLabelRef labelRef = destination.getValue(0);
						branch.convertToDirect(labelRef);
						block.addBranch(nodeBlocks.get(labelRef.getLabel()));
					}
					else
						for (int i = 0; i < destination.getSize(); ++i)
							block.addBranch(nodeBlocks.get(destination.getValue(i).getLabel()));					
				}
			}
			//handles cases where a method call can cause a catchable exception
			else if( node instanceof TACCall ) {
				TACCall call = (TACCall) node;
				if( call.getBlock().hasLandingpad() ) {
					block.addBranch(nodeBlocks.get(call.getBlock().getLandingpad().getLabel()));					
					block.addBranch(nodeBlocks.get(call.getNoExceptionLabel().getLabel()));
				}
			}
			else if( node instanceof TACThrow ) {
				TACThrow throw_ = (TACThrow)node;
				if( throw_.getBlock().hasLandingpad() )
					block.addBranch(nodeBlocks.get(throw_.getBlock().getLandingpad().getLabel()));
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
		TACNode node = method.getNode();
		TACNode last = node.getPrevious();
		boolean done = false;
		boolean starting = true;
		Block block = null;
		
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
			
			block.addNode(node);
			
			if( node == last )
				done = true;
			else
				node = node.getNext();
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
			for (Block child : block)
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
		for (Block child : current) {
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
	public boolean propagateConstants() {		
		Collection<Block> blocks = nodeBlocks.values();
		
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
				addError(undefined.getASTNode(), Error.UNDEFINED_VARIABLE, "Variable " + undefined.getVariable().getOriginalName() + " may not have been defined before use");
		
		return changed;
	}

	@Override
	public String toString()
	{
		if( cachedString == null )
			cachedString = generateString();
		
		return cachedString;
	}
	
	/*
	 * Represents a contiguous sequence of TAC nodes, unbroken by branch
	 * statements.
	 */
	private class Block implements Iterable<Block>
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
			return "Block " + label.getRef().getNumber();
		}
		
		/*
		 * Gets the number of the label the block starts with.
		 */
		public int getNumber()
		{
			return label.getRef().getNumber();
		}

		/*
		 * Propagates constants through updatable nodes.
		 * If a variable is found to be undefined where it's loaded,
		 * adds the load to a list of undefined loads.  
		 */
		public boolean propagateValues(Set<TACLocalLoad> undefinedLoads)
		{
			boolean changed = false;
			
			TACNode node = label.getNext();
			boolean done = false;
			Set<TACUpdate> currentlyUpdating = new HashSet<TACUpdate>();
			while( !done ) {				
				if( node instanceof TACUpdate )
					if( ((TACUpdate)node).update(currentlyUpdating) )
						changed = true;
				
				if( node instanceof TACLocalLoad ) {
					TACLocalLoad load = (TACLocalLoad)node;
					if( load.isUndefined() )
						undefinedLoads.add(load);
				}
				
				if( node == lastNode )
					done = true;
				else
					node = node.getNext();
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
			TACNode node = label.getNext();
			boolean done = false;
			while( !done ) {				
				if( node instanceof TACLocalStorage ) {
					TACLocalStorage store = (TACLocalStorage)node;
					stores.put(store.getVariable(), store);					 
				}
				
				if( node == lastNode )
					done = true;
				else
					node = node.getNext();
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
			TACPhiStore phi = new TACPhiStore(label.getNext(), variable);
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
			Map<TACVariable,TACLocalStorage> predecessors = new HashMap<TACVariable, TACLocalStorage>();
			Map<TACVariable, TACLocalStorage> stores = lastStores.get(this);
			
			TACNode node = label.getNext();
			boolean done = false;
			while( !done ) {				
				if( node instanceof TACLocalStore ) {
					TACLocalStore store = (TACLocalStore)node;
					predecessors.put(store.getVariable(), store);	
				}
				else if( node instanceof TACLocalLoad ) {
					TACLocalLoad load = (TACLocalLoad)node;					
					TACVariable variable = load.getVariable();
					TACOperand store = predecessors.get(variable); 
					if( store == null ) {
						//add phi
						TACPhiStore phi = new TACPhiStore(label.getNext(), variable);
						for( Block block : incoming )
							phi.addPreviousStore(block.getLabel(), block.getPreviousStore(variable, lastStores));						
						
						predecessors.put(variable, phi);	
						if( !stores.containsKey(variable) )
							stores.put(variable, phi);
						load.setPreviousStore(phi);
					}
					else
						load.setPreviousStore(store);
				}
				
				if( node == lastNode )
					done = true;
				else
					node = node.getNext();
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
			TACNode node = label;
			boolean done = false;
			while( !done ) {				
				if( node instanceof TACPhi ) {
					TACPhiRef phiRef = ((TACPhi)node).getRef();
					phiRef.removeLabel(block.getLabel().getRef());
				}
				else if( node instanceof TACPhiStore ) {
					TACPhiStore store = (TACPhiStore)node;
					store.removePreviousStore(block.getLabel());					
				}
				
				if( node == lastNode )
					done = true;
				else
					node = node.getNext();
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
			//Real operations: not just junk leftover from TAC construction
			//Weakness: a break or continue that isn't reachable won't be flagged
			if( !isControlFlow(node)  )
				addError(node.getASTNode(), Error.UNREACHABLE_CODE, "Code cannot be reached");
			
			node.remove();
		}
		
		/*
		 * Determines if a node is only control flow and
		 * not "real" computation.
		 */		
		private boolean isControlFlow(TACNode node)
		{
			if( node instanceof TACLocalStore ) {
				return isControlFlow(((TACLocalStore)node).getValue());
			}			
			
			return node instanceof TACLabel ||
				node instanceof TACBranch ||
				node instanceof TACLandingpad ||
				node instanceof TACUnwind ||
				node instanceof TACResume ||
				//a load doesn't do anything unless the value is used
				node instanceof TACLocalLoad; 			
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
		
		@Override
		public Iterator<Block> iterator()
		{
			return outgoing.iterator();
		}
		
		public int branches()
		{
			return outgoing.size();
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

}
