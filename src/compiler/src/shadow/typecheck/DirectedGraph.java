package shadow.typecheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectedGraph<T> implements Iterable<T> {
	
	private class Node
	{
		public T value;
		
		public Set<Node> incoming = new HashSet<Node>();
		public Set<Node> outgoing = new HashSet<Node>();				
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

	public List<T> topologicalSort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<T> iterator() {		
		return nodes.keySet().iterator();
	}	
}
