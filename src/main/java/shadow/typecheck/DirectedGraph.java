package shadow.typecheck;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectedGraph<T> implements Iterable<T> {
	
	
	private class GraphNode implements Comparable<GraphNode>
	{
		public T value;
		
		public Set<GraphNode> incoming = new HashSet<GraphNode>();
		public Set<GraphNode> outgoing = new HashSet<GraphNode>();
		@Override
		public int compareTo(GraphNode o) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public String toString()
		{
			return value.toString() + " in: " + incoming.size() + " out: " + outgoing.size();			
		}
	}	
	
	private Map<T, GraphNode> nodes = new HashMap<T, GraphNode>();	
	
	void addNode(T value)
	{
		if( !nodes.containsKey(value) )
		{
			GraphNode node = new GraphNode();
			node.value = value;
			
			nodes.put(value, node);
		}
	}	
	
	boolean addEdge(T start, T end)
	{
		GraphNode startNode = nodes.get(start);
		GraphNode endNode = nodes.get(end);
		
		if( startNode == null || endNode == null )
			return false;
		
		
		startNode.outgoing.add(endNode);
		endNode.incoming.add(startNode);
		
		return true;
	}
	
	boolean hasEdge(T start, T end)
	{
		GraphNode startNode = nodes.get(start);
		GraphNode endNode = nodes.get(end);
		
		if( startNode == null || endNode == null )
			return false;
		
		return startNode.outgoing.contains(endNode) && endNode.incoming.contains(startNode);		
	}

	public List<T> topologicalSort() throws CycleFoundException
	{	
		ArrayDeque<GraphNode> S = new ArrayDeque<GraphNode>();
		List<T> list = new ArrayList<T>();
		
		HashMap<GraphNode, Integer> inDegrees = new HashMap<GraphNode, Integer>();
		
		for( GraphNode node : nodes.values() )
		{
			if( node.incoming.isEmpty())
				S.add(node);
			else
				inDegrees.put(node, node.incoming.size());
		}
		
		while( !S.isEmpty() )
		{
			GraphNode node = S.removeFirst();
			list.add(node.value);
			
			for( GraphNode child : node.outgoing )
			{
				int in = inDegrees.get(child) - 1;				
				if( in <= 0 )
				{
					inDegrees.remove(child);
					S.add(child);
				}
				else
					inDegrees.put(child, in);				
			}
		}
		
		if( !inDegrees.isEmpty() )			
		{				
			throw new CycleFoundException( inDegrees.keySet().iterator().next().value );
		}
		
		return list;
	}

	@Override
	public Iterator<T> iterator() {		
		return nodes.keySet().iterator();
	}	
}
