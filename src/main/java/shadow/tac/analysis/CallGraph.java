package shadow.tac.analysis;

import shadow.typecheck.DirectedGraph;
import shadow.typecheck.type.MethodSignature;

public class CallGraph extends DirectedGraph<MethodSignature>
{	
	
	private class CallNode extends GraphNode {
		
		private ControlFlowGraph graph;
		

		public CallNode(MethodSignature value, ControlFlowGraph graph) {
			super(value);
			this.graph = graph;
		}
		
		public ControlFlowGraph getGraph() {
			return graph;
		}
	}
	
	@Override
	public void addNode(MethodSignature value) {
		throw new UnsupportedOperationException();
	}
	
	public void addNode(MethodSignature value, ControlFlowGraph graph) {
		if( !nodes.containsKey(value) ) {
			GraphNode node = new CallNode(value, graph);			
			nodes.put(value, node);
		}
	}
	
	public ControlFlowGraph getControlFlowGraph(MethodSignature method) {
		CallNode node = (CallNode) nodes.get(method);
		return node.getGraph();
	}
}
