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
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACReturn;

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
	private final String cachedString; // Saves toString() from re-walking the graph
	
	public ControlFlowGraph(TACMethod method)
	{
		Map<TACNode, Block> nodeBlocks = new HashMap<TACNode, Block>();
		root = new Block();
				
		buildGraph(method, method.getNext(), root, nodeBlocks);
		
		cachedString = generateString();
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
	
	private boolean returns(Block block, Set<Block> visited)
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
	
	@Override
	public String toString()
	{
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

		public Block()
		{
			uniqueID = count;
			count++;
		}
		
		public Block(Block parent)
		{
			this();
			
			parent.addBranch(this);
		}
		
		public void addBranch(Block target)
		{
			branches.add(target);
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
