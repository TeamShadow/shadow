package shadow.tac.nodes;

public interface TACDestination
{
	public abstract int getNumPossibilities();
	public abstract TACLabelRef getPossibility(int num);
}
