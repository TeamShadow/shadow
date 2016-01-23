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
 * predictable) cases.
 * 
 * @author Brian Stottler
 */
public class ControlFlowGraph
{
	private final Block root;
	private final String printable;
	
	public ControlFlowGraph(TACMethod method)
	{
		Map<TACNode, Block> association = new HashMap<TACNode, Block>();
		
		Block.resetCount();
		root = new Block();
				
		buildGraph(method, method.getNext(), root, association);
		
		printable = generateString();
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
	
	private boolean returns(Block current, Set<Block> visited)
	{
		if (visited.contains(current))
			return true;
		
		visited.add(current);
		
		// A block should either branch or return
		if (current.branches() > 0) {
			// Ensure everything we can branch to returns
			boolean returns = true;
			
			for (Block child : current)
				if (!returns(child, visited))
					returns = false;
			
			return returns;
		} else {
			// If we don't branch, returning is dependent on us
			return current.returnsDirectly();
		}
	}
	
	private String generateString()
	{
		Set<Block> visited = new HashSet<Block>();
		List<IndexedString> strings = new ArrayList<IndexedString>();
		
		generateString(root, visited, strings);
		
		// Order and combine the strings for each block
		Collections.sort(strings);
		StringBuilder builder = new StringBuilder();
		for (IndexedString string : strings)
			builder.append(string.getString() + "\n");
		
		return builder.toString();
	}
	
	/** 
	 * Traverses the graph to build a nice print-out of it. Very useful for
	 * debugging
	 * */
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
		
		String output = Integer.toString(current.getID()) + ": ";
		
		Collections.sort(children);
		for (int i = 0; i < children.size(); ++i) {
			if (i != 0)
				output += ", ";
			output += Integer.toString(children.get(i)) + " ";
		}
		
		if (current.returnsDirectly())
			output += "(RETURNS)";
			
		strings.add(new IndexedString(current.getID(), output));
			
	}
	
	/** Generates a control flow graph for the given method.
	 * 
	 * @param first			The TACMethod itself, used to detect the end of
	 * 						the method.
	 * @param currentNode	The current node within the method's TAC sequence.
	 * 						Should begin as the TACNode after "first".
	 * @param currentBlock	Represents a linear sequence of instructions in the
	 * 						TAC sequence. Should begin as a childless Block
	 * 						which is also kept as the root of the graph.
	 * @param association	A map linking TAC instructions to their
	 * 						corresponding Blocks. Used to detect loops and
	 * 						to subsequently branches to the proper Blocks.
	 */
	private void buildGraph(TACNode first, TACNode currentNode,
			Block currentBlock, Map<TACNode, Block> association)
	{
		// If we reach the first node, we've looped around the end of the
		// method. Note that we should never arrive here by branching.
		if (currentNode == first)
			return;
		
		// If this node is already within another block, we must have branched
		// to get there
		if (association.containsKey(currentNode)) {
			currentBlock.addBranch(association.get(currentNode));
			return;
		}
	
		// "Add" the current node into the current block
		association.put(currentNode, currentBlock);
		
		// If this is a return statement, we should flag it and return
		if (currentNode instanceof TACReturn) {
			currentBlock.flagReturns();
			return;
		}
		
		if (currentNode instanceof TACBranch) {
			TACBranch branch = (TACBranch) currentNode;
			
			if (branch.isConditional()) {
				// For a conditional, follow the two possible labels and start
				// new blocks as branches from the current one
				buildGraph(first, branch.getTrueLabel().getLabel(),
						new Block(currentBlock), association);
				buildGraph(first, branch.getFalseLabel().getLabel(),
						new Block(currentBlock), association);
			} else if (branch.isDirect()) {
				// Same as conditional, except only one branch
				buildGraph(first, branch.getTrueLabel().getLabel(),
						new Block(currentBlock), association);
			} else if (branch.isIndirect()) {
				// Branch to every possible destination
				TACDestination destination = branch.getDestination();
				for (int i = 0; i < destination.getNumPossibilities(); ++i) {
					buildGraph(first, destination.getPossibility(i).getLabel(),
							new Block(currentBlock), association);
				}
			} else {
				throw new UnsupportedOperationException("Encountered a TACBranch that was neither conditional, direct, or indirect. What?");
			}
		} else {
			// If we don't branch, keep writing to the current block, starting
			// with the next node in the TAC sequence
			buildGraph(first, currentNode.getNext(), currentBlock, association);
		}
	}
	
	@Override
	public String toString()
	{
		return printable;
	}
	
	/**
	 * Represents a contiguous sequence of TAC instructions, unbroken by branch
	 * statements.
	 */
	private static class Block implements Iterable<Block>
	{
		private static int count = 0;
		private final int uniqueID;
		
		private final Set<Block> branches = new HashSet<Block>();
		private boolean returns = false;

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
		
		public void flagReturns()
		{
			returns = true;
		}
		
		public boolean returnsDirectly()
		{
			return returns;
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
		
		public static void resetCount()
		{
			count = 0;
		}
	}

	/** Allows strings to be sorted based on their IDs */
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
