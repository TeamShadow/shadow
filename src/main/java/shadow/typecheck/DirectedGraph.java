/*
 * Copyright 2015 Team Shadow
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 	
 * 	    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package shadow.typecheck;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The <code>DirectedGraph</code> class represents a directed graph containing 
 * any type of value in each node.  All operations are done in terms of the type
 * <code>T</code> used to represent the value of each node.  Values added to the
 * graph must be unique, and <code>T</code> must have a reasonable implementation
 * of <code>hashCode()</code>.
 * 
 * @author Barry Wittman
 */
public class DirectedGraph<T> implements Iterable<T> {	
	/*
	 * Node class for the graph, which tracks incoming and outgoing edges.
	 */
	protected class GraphNode {
		public final T value;		
		public final Set<GraphNode> incoming = new HashSet<GraphNode>();
		public final Set<GraphNode> outgoing = new HashSet<GraphNode>();
		
		public GraphNode(T value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value.toString() + " in: " + incoming.size() + " out: " + outgoing.size();			
		}
	}
	
	/**
	 * The <code>CycleFoundException</code> exception is thrown when a cycle is found
	 * when doing a topological sort.
	 */
	public static class CycleFoundException extends Exception {
		private static final long serialVersionUID = -1938318109952799264L;
		private Object cause;	
		
		/**
		 * Creates the exception, noting the particular cause.
		 * @param cause		an object explaining the cause of the cycle 
		 */
		public CycleFoundException( Object cause ) {
			this.cause = cause;
		}
		
		/**
		 * Gets the cause of the exception
		 * @return			the object explaining the source of the cycle
		 */
		public Object getCycleCause() {
			return cause;		
		}
	}
	
	// Graph internals are managed by a map from values to nodes.
	protected Map<T, GraphNode> nodes = new HashMap<T, GraphNode>();	
	
	
	/**
	 * Adds a new node to graph with given value, unless that value is already present.
	 * @param value		the value to be added
	 */
	public void addNode(T value) {
		if( !nodes.containsKey(value) ) {
			GraphNode node = new GraphNode(value);			
			nodes.put(value, node);
		}
	}	
	
	/**
	 * Adds a new directed edge between existing nodes with given values.	
	 * @param start		value of the starting node
	 * @param end		value of the ending node
	 * @return			<code>true</code> if adding the edge was successful
	 */
	public boolean addEdge(T start, T end) {
		GraphNode startNode = nodes.get(start);
		GraphNode endNode = nodes.get(end);
		
		if( startNode == null || endNode == null )
			return false;	
		
		startNode.outgoing.add(endNode);
		endNode.incoming.add(startNode);		
		return true;
	}
	
	/**
	 * Checks to see if there is an edge between the nodes with given values.	
	 * @param start		value of the starting node
	 * @param end		value of the ending node
	 * @return			<code>true</code> if the edge exists
	 */
	boolean hasEdge(T start, T end) {
		GraphNode startNode = nodes.get(start);
		GraphNode endNode = nodes.get(end);
		
		if( startNode == null || endNode == null )
			return false;
		
		return startNode.outgoing.contains(endNode) &&
				endNode.incoming.contains(startNode);		
	}

	
	/**
	 * Performs a topological sort of the directed graph if the graph is acyclic,
	 * using Kahn's algorithm. If a cycle is found, the method will throw a
	 * <code>CycleFoundException</code>. Otherwise, it will return an ordered list of nodes.
	 * @return	list of values corresponding to a topological sort 
	 * @throws 	CycleFoundException
	 */
	public List<T> topologicalSort() throws CycleFoundException {	
		// S contains a queue of nodes which no longer have incoming edges
		ArrayDeque<GraphNode> S = new ArrayDeque<GraphNode>();
		List<T> list = new ArrayList<T>();
		
		/* inDegrees is a separate map of nodes to a count of incoming edges.
		 * By using a separate map, the graph is not changed by doing a topological sort.
		 */		
		HashMap<GraphNode, Integer> inDegrees = new HashMap<GraphNode, Integer>();
		
		for( GraphNode node : nodes.values() )
			if( node.incoming.isEmpty())
				S.add(node);
			else
				inDegrees.put(node, node.incoming.size());		
		
		while( !S.isEmpty() ) {
			GraphNode node = S.removeFirst();
			list.add(node.value);
			
			// Decrease in-degree of all neighbors of node
			for( GraphNode child : node.outgoing ) {
				int in = inDegrees.get( child ) - 1;				
				if( in <= 0 ) {
					inDegrees.remove( child );
					S.add(child);
				}
				else
					inDegrees.put( child, in );				
			}
		}
		
		// Cycle found if there are still elements with non-zero in-degree 
		if( !inDegrees.isEmpty() )
			throw new CycleFoundException( inDegrees.keySet().iterator().next().value );
				
		return list;
	}

	@Override
	public Iterator<T> iterator() {		
		return nodes.keySet().iterator();
	}	
}
