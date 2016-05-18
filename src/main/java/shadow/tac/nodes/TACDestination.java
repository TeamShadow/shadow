package shadow.tac.nodes;

public interface TACDestination
{
	public int getNumPossibilities();
	public TACLabelRef getPossibility(int num);
	public void addIncoming(TACBranch branch);
	public void removeIncoming(TACBranch branch);	
	public boolean hasIncoming(TACBranch branch);
	public int incomingCount();	
}
