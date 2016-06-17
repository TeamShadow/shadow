package shadow.tac.analysis;

import shadow.tac.TACMethod;
import shadow.typecheck.DirectedGraph;

public class CreateGraph extends DirectedGraph<TACMethod>
{	
	
	private class CreateNode extends GraphNode {
		
		private ControlFlowGraph graph;
		

		public CreateNode(TACMethod value, ControlFlowGraph graph) {
			super(value);
			this.graph = graph;
		}
		
		public ControlFlowGraph getGraph() {
			return graph;
		}
	}
	
	@Override
	public void addNode(TACMethod value) {
		throw new UnsupportedOperationException();
	}
	
	public void addNode(TACMethod value, ControlFlowGraph graph) {
		if( !nodes.containsKey(value) ) {
			GraphNode node = new CreateNode(value, graph);			
			nodes.put(value, node);
		}
	}
	
	public ControlFlowGraph getControlFlowGraph(TACMethod method) {
		CreateNode node = (CreateNode) nodes.get(method);
		return node.getGraph();
	}
}
