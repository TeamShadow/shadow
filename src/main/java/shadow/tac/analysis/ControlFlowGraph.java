package shadow.tac.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
	/** Provides a helpful way of IDing Blocks. Useful for debugging */
	private int count = 0;
	private String cachedString; // Saves toString() from re-walking the graph
	private Map<TACLabel, Block> nodeBlocks = new HashMap<TACLabel, Block>(); 
	
	public ControlFlowGraph(TACMethod method)
	{		
		super(Loggers.TYPE_CHECKER);			
		createBlocks(method);
		addEdges();
	}
	
	private void addEdges()
	{		
		for( Block block : nodeBlocks.values() )
			addEdges(block);
	}

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

	private void createBlocks(TACNode first)
	{		
		TACNode node = first.getNext();
		boolean starting = true;
		Block block = null;
		
		// Loop through circular linked-list
		while( node != first ) {
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
			
			node = node.getNext();
		}
	}
	
	public void removeUnreachableCode()
	{
		boolean edgesUpdated;
		
		do {
			edgesUpdated = false;
			Set<Block> reachable = new HashSet<Block>();
			findReachable(root, reachable);
			
			Set<Block> unreachable = new HashSet<Block>(nodeBlocks.values());
			unreachable.removeAll(reachable);
			
			//If edges are updated in the process, new nodes might become unreachable
			for( Block block : unreachable ) {
				if( block.deleteTACNodes() )
					edgesUpdated = true;
				nodeBlocks.remove(block.getLabel());
				block.removeEdges();
			}
			
		} while( edgesUpdated );
		
		cachedString = null; //reset cachedString
	}
	
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
	
	/** 
	 * Traverses the graph to build a readable print-out of it. Very useful for
	 * debugging
	 */
	private static void generateString(Block current, Set<Block> visited, 
			List<IndexedString> strings)
	{
		if (visited.contains(current))
			return;
		
		visited.add(current);
		
		List<Integer> children = new ArrayList<Integer>();
		for (Block child : current) {
			children.add(child.getID());
			generateString(child, visited, strings);
		}
		
		// Lead with the current block's ID
		String output = Integer.toString(current.getID()) + ": ";
		
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
			
		strings.add(new IndexedString(current.getID(), output));			
	}
	
	
	/** 
	 * Updates TACLocalLoad nodes to have correct phi information.
	 * This method *must* be called in order for TACLocalLoad to be
	 * translated into appropriate phi nodes. 
	 */
	public void updatePhiNodes() {
		Map<Block,Map<TACVariable,TACLocalStorage>> lastDefinitions = new HashMap<Block,Map<TACVariable, TACLocalStorage>>();
		
		Set<Block> blocks = new LinkedHashSet<Block>(nodeBlocks.values());				
		
		//find the last definition of every variable in every block
		for( Block block : blocks )
			lastDefinitions.put(block, block.getLastDefinitions());		
		
		//use those definitions when constructing phi nodes
		for( Block block : blocks )
			block.updatePhiNodes(lastDefinitions);
		
		//propagate constants and other values (not optional if we want to produce legal IR)
		boolean changed = true;
		Set<TACLocalLoad> undefinedLoads = new HashSet<TACLocalLoad>();
		while( changed ) {
			changed = false;
			undefinedLoads.clear();
			for( Block block : blocks )
				if( block.propagateValues(undefinedLoads) )
					changed = true;
		}
		
		for( TACLocalLoad undefined : undefinedLoads )
			//_exception is a special variable used only for exception handling
			//indirect breaks for finally make its value tricky, but it's guaranteed to never *really* be undefined
			if( !undefined.getVariable().getOriginalName().equals("_exception") )
				addError(undefined.getASTNode(), Error.UNDEFINED_VARIABLE, "Variable " + undefined.getVariable().getOriginalName() + " may not have been defined before use");
		
	}


	@Override
	public String toString()
	{
		if( cachedString == null )
			cachedString = generateString();
		
		return cachedString;
	}
	
	/**
	 * Represents a contiguous sequence of TAC instructions, unbroken by branch
	 * statements.
	 */
	private class Block implements Iterable<Block>
	{		
		private final int uniqueID;
		
		private Set<Block> outgoing = new HashSet<Block>();
		private Set<Block> incoming = new HashSet<Block>();
		private final TACLabel label;
		private boolean returns = false;
		private boolean unwinds = false;
		private TACNode lastNode = null;  //last TAC node in this block
		
		@Override
		public String toString() {
			return "Block " + uniqueID;
		}

		public boolean propagateValues(Set<TACLocalLoad> undefinedLoads) {
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
			uniqueID = count;
			count++;
			this.label = label;
		}
		
		public Map<TACVariable, TACLocalStorage> getLastDefinitions() {
			Map<TACVariable,TACLocalStorage> definitions = new HashMap<TACVariable, TACLocalStorage>();
			TACNode node = label.getNext();
			boolean done = false;
			while( !done ) {				
				if( node instanceof TACLocalStorage ) {
					TACLocalStorage store = (TACLocalStorage)node;
					definitions.put(store.getVariable(), store);					 
				}
				
				if( node == lastNode )
					done = true;
				else
					node = node.getNext();
			}	
			
			return definitions;
		}
		
		/*
		private void addPreviousStore(TACLocalLoad load, Block block, Map<Block, Map<TACVariable, TACLocalStore>> lastDefinitions, Set<Block> visited) {
			if( !visited.contains(block) ) {
				visited.add(block);
				 Map<TACVariable, TACLocalStore> definitions = lastDefinitions.get(block);
				 TACLocalStore store = definitions.get(load.getVariable());
				 //defined by block
				if( store != null )
					load.addPreviousStore(block.getLabel(), store);				
				else //otherwise check parents
					for( Block parent : block.incoming )
						addPreviousStore(load, parent, lastDefinitions, visited);								
			}
		}
		*/
		
		
		
		private TACLocalStorage getPreviousStore(TACVariable variable, Map<Block, Map<TACVariable, TACLocalStorage>> lastDefinitions ) {
			Map<TACVariable, TACLocalStorage> definitions = lastDefinitions.get(this);
			TACLocalStorage store = definitions.get(variable);
			 //defined by block
			if( store != null )
				return store;

			//else insert phi node after label
			TACPhiStore phi = new TACPhiStore(label.getNext(), variable);
			definitions.put(variable, phi);
			for( Block block : incoming )
				phi.addPreviousStore(block.getLabel(), block.getPreviousStore(variable, lastDefinitions));
			return phi;
		}
		
		
/*
		//Only for indirect branching nodes
		//not all of their incoming edges are relevant
		private TACOperand getPreviousStore(TACVariable variable,
				Map<Block, Map<TACVariable, TACLocalStorage>> lastDefinitions, TACLabel label) {
			Map<TACVariable, TACLocalStorage> definitions = lastDefinitions.get(this);
			TACLocalStorage store = definitions.get(variable);
			 //defined by block
			if( store != null )
				return store;			
						
			TACPhiStore phi = new TACPhiStore(label.getNext(), variable);
			definitions.put(variable, phi);
			
			if( branchesIndirectly() ) {
				TACBranch branch = (TACBranch) lastNode;
				TACPhiRef phiRef = branch.getDestination();
				//how many branches go indirectly to the label in question			
				for( int i = 0; i < phiRef.getSize(); ++i )
					if( phiRef.getValue(i).getLabel().equals(label)) {
						TACLabel priorLabel = phiRef.getLabel(i).getLabel(); 
						phi.addPreviousStore(priorLabel, nodeBlocks.get(priorLabel).getPreviousStore(variable, lastDefinitions, getLabel()) );
					}
			}
			else
				for( Block block : incoming )
					phi.addPreviousStore(block.getLabel(), block.getPreviousStore(variable, lastDefinitions, getLabel()));
				
			
			return phi;
		}
*/

		public void updatePhiNodes(Map<Block, Map<TACVariable, TACLocalStorage>> lastDefinitions) {
			Map<TACVariable,TACLocalStorage> predecessors = new HashMap<TACVariable, TACLocalStorage>();
			Map<TACVariable, TACLocalStorage> definitions = lastDefinitions.get(this);
			
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
							phi.addPreviousStore(block.getLabel(), block.getPreviousStore(variable, lastDefinitions));						
						
						predecessors.put(variable, phi);	
						if( !definitions.containsKey(variable) )
							definitions.put(variable, phi);
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

		/*
		private boolean branchesIndirectly() {
			return lastNode != null && lastNode instanceof TACBranch && ((TACBranch)lastNode).isIndirect();
		}
		*/

		public Set<Block> getOutgoing()
		{
			return outgoing;
		}

		public boolean deleteTACNodes()
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

		private TACLabel getLabel() {
			return label;
		}

		private void removeNode(TACNode node)
		{
			//Real operations: not just junk leftover from TAC construction
			//Weakness: a break or continue that isn't reachable won't be flagged
			if( !isControlFlow(node)  )
				addError(node.getASTNode(), Error.UNREACHABLE_CODE, "Code cannot be reached");
			
			node.remove();
		}
		
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
				node instanceof TACLocalLoad; //a load doesn't *do* anything unless the value is used			
		}

		public TACNode getLast()
		{
			return lastNode;			
		}
		
		public void addBranch(Block target)
		{
			if( target != null ) {
				outgoing.add(target);
				target.incoming.add(this);
			}
		}
		
		
		public void addNode(TACNode node)
		{	
			if( lastNode != null && lastNode.getNext() != node )
				throw new IllegalArgumentException("Cannot add TAC nodes out of order");
			
			lastNode = node;
		}
		
		/** Indicate that this block returns directly */
		public void flagReturns()
		{
			returns = true;
		}
		
		/** Indicate that this block unwinds the stack by throwing an uncaught exception */
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
		
		public int getID()
		{
			return uniqueID;
		}
	}

	/** Allows strings to be sorted based on given indexes */
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
