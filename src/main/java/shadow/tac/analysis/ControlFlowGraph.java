package shadow.tac.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shadow.tac.TACMethod;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACDestination;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACThrow;

/**
 * Represents the various paths which code execution can take within a method.
 * Useful for determining whether or not a method returns in all (reasonably
 * predictable) cases. Probably does not handle try/catch correctly.
 * 
 * @author Brian Stottler
 */
public class ControlFlowGraph
{
	private final Block root;
	/** Provides a helpful way of IDing Blocks. Useful for debugging */
	private int count = 0;
	private String cachedString; // Saves toString() from re-walking the graph
	private Map<TACNode, Block> nodeBlocks = new HashMap<TACNode, Block>(); 
	
	public ControlFlowGraph(TACMethod method)
	{		
		root = new Block();		
		createBlocks(method);
		addEdges();
				
		//buildGraph(method, method.getNext(), root, nodeBlocks);
		
		//cachedString = generateString();
	}
	
	private void addEdges() {
		addEdges(root);
		
		for( Block block : nodeBlocks.values() )
			addEdges(block);
	}

	private void addEdges(Block block) {
		TACNode node = block.getFirst();
		boolean done = false;
		
		while( !done )
		{
			if( node instanceof TACBranch )
			{			
				TACBranch branch = (TACBranch) node;			
				if (branch.isConditional()) {
					block.addBranch(nodeBlocks.get(branch.getTrueLabel()));
					block.addBranch(nodeBlocks.get(branch.getFalseLabel()));
				}
				else if (branch.isDirect())
					block.addBranch(nodeBlocks.get(branch.getLabel()));				
				else if (branch.isIndirect()) {
					// Branch to every possible destination
					TACDestination destination = branch.getDestination();
					for (int i = 0; i < destination.getNumPossibilities(); ++i)
						block.addBranch(nodeBlocks.get(destination.getPossibility(i).getLabel()));					
				}
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
				block = new Block();
				nodeBlocks.put(node, block);
			}
			else if( node instanceof TACReturn )
				block.flagReturns();
			else if( node instanceof TACThrow )
				block.flagUnwinds();
			
			block.addNode(node);
			
			node = node.getNext();
		}
	}
	
	public void removeDeadCode()
	{
		Set<Block> reachable = new HashSet<Block>();
		findReachable(root, reachable);
		
		Set<Block> unreachable = new HashSet<Block>(nodeBlocks.values());
		unreachable.removeAll(reachable);
		
		for( Block block : unreachable )
			block.deleteTACNodes();		
		
		Set<TACNode> keysToRemove = new HashSet<TACNode>();
		for( Map.Entry<TACNode, Block> entry : nodeBlocks.entrySet() )
			if( unreachable.contains(entry.getValue()) )
				keysToRemove.add(entry.getKey());
		
		for(TACNode node : keysToRemove )
			nodeBlocks.remove(node);		
		
		cachedString = null; //reset cachedString
	}
	
	private static void findReachable(Block block, Set<Block> visited)
	{
		if( !visited.contains(block) ) {
			visited.add(block);
			for( Block branch : block.branches )
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
	
	/** Generates a control flow graph for the given method.
	 * 
	 * @param first			The TACMethod itself (used to detect the end of
	 * 						the method on wrap-around).
	 * @param node			The current node within the method's TAC sequence.
	 * 						Should initially be the TACNode after "first".
	 * @param block			Represents a linear sequence of instructions (no
	 * 						branches) in the TAC sequence. Should initially be
	 * 						a childless Block - this will be the root of the
	 * 						graph on completion.
	 * @param nodeBlocks	A map linking TAC instructions to their
	 * 						corresponding Blocks. Used to detect loops and
	 * 						to subsequently branches to the proper Blocks.
	 */
	
	/*
	private void buildGraph(TACNode first, TACNode node, Block block,
			Map<TACNode, Block> nodeBlocks)
	{
		// If we reach the first node, we've gone past the end of the
		// method. Note that we should never arrive here by branching.
		if (node == first)
			return;
		
		// If this node is already within another block, we must have branched
		// to get there. We want to
		// 		- Make note that our current block branches to that block
		//		- Stop traversing this branch
		if (nodeBlocks.containsKey(node)) {
			block.addBranch(nodeBlocks.get(node));
			return;
		}
	
		// "Add" the current node into the current block
		nodeBlocks.put(node, block);
		block.addNode(node);
		
		// If this is a return statement, we should flag it as such
		if (node instanceof TACReturn) {
			block.flagReturns();
			return;
		}
		
		// Deal with the different branch circumstances
		if (node instanceof TACBranch) {
			TACBranch branch = (TACBranch) node;
			
			if (branch.isConditional()) {
				// For a conditional, follow the two possible labels and create
				// corresponding blocks (branching from the current one)
				buildGraph(first, branch.getTrueLabel().getLabel(),
						new Block(block), nodeBlocks);
				buildGraph(first, branch.getFalseLabel().getLabel(),
						new Block(block), nodeBlocks);
			} else if (branch.isDirect()) {
				// Same as conditional, except only one branch
				buildGraph(first, branch.getLabel().getLabel(),
						new Block(block), nodeBlocks);
			} else if (branch.isIndirect()) {
				// Branch to every possible destination
				TACDestination destination = branch.getDestination();
				for (int i = 0; i < destination.getNumPossibilities(); ++i) {
					buildGraph(first, destination.getPossibility(i).getLabel(),
							new Block(block), nodeBlocks);
				}
			} else { // Unfortunately this is possible, although it should never happen
				throw new UnsupportedOperationException("Encountered a TACBranch that was neither conditional, direct, or indirect. What?");
			}
		} else {
			// If we don't branch, keep writing to the current block, starting
			// with the next node in the TAC sequence
			buildGraph(first, node.getNext(), block, nodeBlocks);
		}
	}	
	*/
	
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
		
		private final Set<Block> branches = new HashSet<Block>();
		private boolean returns = false;
		private boolean unwinds = false;
	
		private TACNode firstNode = null; //first TAC node in this block
		private TACNode lastNode = null;  //last TAC node in this block

		public Block()
		{
			uniqueID = count;
			count++;
		}
		
		public void deleteTACNodes() {
			if( firstNode != null && lastNode != null ) {				
				while( firstNode != lastNode ) {
					TACNode temp = firstNode.getNext();
					firstNode.remove();
					firstNode = temp;
				}
				//Now firstNode == lastNode, but it still needs to be removed
				lastNode.remove();
				firstNode = lastNode = null;				
			}
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
