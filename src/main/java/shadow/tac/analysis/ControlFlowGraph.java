package shadow.tac.analysis;

import java.util.ArrayList;
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
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACPhiRef;
import shadow.tac.nodes.TACPhiRef.TACPhi;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACUnwind;
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
	private final Block root;
	/** Provides a helpful way of IDing Blocks. Useful for debugging */
	private int count = 0;
	private String cachedString; // Saves toString() from re-walking the graph
	private Map<TACLabel, Block> nodeBlocks = new HashMap<TACLabel, Block>(); 
	
	public ControlFlowGraph(TACMethod method)
	{		
		super(Loggers.TYPE_CHECKER);
		root = new Block(null);	//only the root has no label	
		createBlocks(method);
		addEdges();
	}
	
	private void addEdges()
	{
		addEdges(root);		
		for( Block block : nodeBlocks.values() )
			addEdges(block);
	}

	private void addEdges(Block block)
	{
		TACNode node = block.getFirst();
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
				if( call.getBlock().hasLandingpad() )
					block.addBranch(nodeBlocks.get(call.getBlock().getLandingpad().getLabel()));
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
		Block block = root;
		
		// Loop through circular linked-list
		while( node != first ) {
			if( node instanceof TACLabel ) {
				TACLabel label = (TACLabel)node;
				block = new Block(label);
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
			}
			
		} while( edgesUpdated );
		
		cachedString = null; //reset cachedString
	}
	
	private static void findReachable(Block block, Set<Block> visited)
	{
		if( !visited.contains(block) ) {
			visited.add(block);
			for( Block branch : block.getBranches() )
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
	private void generateString(Block current, Set<Block> visited, 
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
		
		private Set<Block> branches = new HashSet<Block>();
		private final TACLabel label;
		private boolean returns = false;
		private boolean unwinds = false;
	
		private TACNode firstNode = null; //first TAC node in this block
		private TACNode lastNode = null;  //last TAC node in this block

		public Block(TACLabel label)
		{
			uniqueID = count;
			count++;
			this.label = label;
		}
		
		public Set<Block> getBranches()
		{
			return branches;
		}
		
		public boolean deleteTACNodes()
		{
			boolean edgesUpdated = false;
			
			for( Block block : branches )
				if( block.removePhiInput(this) )
					edgesUpdated = true;
			
			if( firstNode != null && lastNode != null ) {
				while( firstNode != lastNode ) {
					TACNode temp = firstNode.getNext();
					removeNode(firstNode);
					firstNode = temp;
				}
				//Now firstNode == lastNode, but it still needs to be removed
				removeNode(lastNode);
				firstNode = lastNode = null;				
			}
			
			return edgesUpdated;
		}
		
		private boolean removePhiInput(Block block)
		{
			TACLabel label = block.getLabel();
			
			TACNode node = firstNode;
			boolean done = false;
			while( !done ) {				
				if( node instanceof TACPhi ) {
					TACPhiRef phiRef = ((TACPhi)node).getRef();
					phiRef.removeLabel(label.getRef());
				}
				
				if( node == lastNode )
					done = true;
				else
					node = node.getNext();
			}

			//after updating phi input, update branches			
			Set<Block> oldBranches = branches;
			branches = new HashSet<Block>();
			addEdges(this);
			
			//if updated branches is smaller, we're going to have to continue
			return branches.size() < oldBranches.size();
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
			return node instanceof TACLabel ||
				node instanceof TACBranch ||
				node instanceof TACLandingpad ||
				node instanceof TACUnwind ||
				node instanceof TACResume;			
		}

		public TACNode getFirst()
		{
			return firstNode;
		}
		
		public TACNode getLast()
		{
			return lastNode;			
		}
		
		public void addBranch(Block target)
		{
			if( target != null )
				branches.add(target);
		}
		
		
		public void addNode(TACNode node)
		{
			if( firstNode == null )
				firstNode = node;
			
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
			return branches.iterator();
		}
		
		public int branches()
		{
			return branches.size();
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
