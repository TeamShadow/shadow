package shadow.tac.analysis;

import java.util.HashSet;
import java.util.Set;

import shadow.Loggers;
import shadow.tac.TACMethod;
import shadow.tac.nodes.TACNode;
import shadow.typecheck.ErrorReporter;

public class DeadCodeRemover extends ErrorReporter {

	public DeadCodeRemover() 
	{
		super(Loggers.TYPE_CHECKER);
	}
	
	public void removeDeadCode(TACMethod method)
	{
		TACNode node = method.getNext();
		Set<TACNode> noIncomingEdges = new HashSet<TACNode>();
		
		do
		{
						
			
			
			
			
			
		} 
		while( noIncomingEdges.size() > 0 );
		
		
		
		
	}

}
