package shadow.typecheck;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DirectedGraph<T> implements Iterable<T> {
	
	
	private class Node implements Comparable<Node>
	{
		public T value;
		
		public Set<Node> incoming = new HashSet<Node>();
		public Set<Node> outgoing = new HashSet<Node>();
		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return 0;
		}				
	}	
	
	private Map<T, Node> nodes = new HashMap<T, Node>();	
	
	void addNode(T value)
	{
		if( !nodes.containsKey(value) )
		{
			Node node = new Node();
			node.value = value;
			
			nodes.put(value, node);
		}
	}	
	
	boolean addEdge(T start, T end)
	{
		Node startNode = nodes.get(start);
		Node endNode = nodes.get(end);
		
		if( startNode == null || endNode == null )
			return false;
		
		
		startNode.outgoing.add(endNode);
		endNode.incoming.add(startNode);
		
		return true;
	}
	
	boolean hasEdge(T start, T end)
	{
		Node startNode = nodes.get(start);
		Node endNode = nodes.get(end);
		
		if( startNode == null || endNode == null )
			return false;
		
		return startNode.outgoing.contains(endNode) && endNode.incoming.contains(startNode);		
	}

	public List<T> topologicalSort() throws CycleFoundException
	{	
		ArrayDeque<Node> S = new ArrayDeque<Node>();
		List<T> list = new ArrayList<T>();
		
		HashMap<Node, Integer> inDegrees = new HashMap<Node, Integer>();
		
		for( Node node : nodes.values() )
		{
			if( node.incoming.isEmpty())
				S.add(node);
			else
				inDegrees.put(node, node.incoming.size());
		}
		
		while( !S.isEmpty() )
		{
			Node node = S.removeFirst();
			list.add(node.value);
			
			for( Node child : node.outgoing )
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
			throw new CycleFoundException( inDegrees.keySet().toArray()[0] );
		
		return list;
	}

	@Override
	public Iterator<T> iterator() {		
		return nodes.keySet().iterator();
	}	
}
