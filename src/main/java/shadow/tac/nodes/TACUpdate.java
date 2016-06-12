package shadow.tac.nodes;

import java.util.Set;

public interface TACUpdate {	
	TACOperand getValue();
	boolean update(Set<TACUpdate> currentlyUpdating);
}
