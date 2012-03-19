package shadow.tac.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACPhi extends TACNode implements Iterable<TACPhiBranch>
{
	private Type type;
	private TACLabel label;
	private List<TACPhiBranch> branches;
	public TACPhi(Type nodeType, TACLabel phiLabel)
	{
		type = nodeType;
		label = phiLabel;
		branches = new ArrayList<TACPhiBranch>();
	}
	
	@Override
	public Type getType()
	{
		return type;
	}
	public TACLabel getLabel()
	{
		return label;
	}
	public List<TACPhiBranch> getBranches()
	{
		return branches;
	}
	@Override
	public Iterator<TACPhiBranch> iterator()
	{
		return branches.iterator();
	}
	
	protected void addBranch(TACPhiBranch branchNode)
	{
		if (!branchNode.getType().isSubtype(type))
			throw new IllegalArgumentException("Can not branch to a phi node with an incompatible type.");
		branches.add(branchNode);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		for (TACPhiBranch node : branches)
			sb.append(node.getValue()).append(" : ");
		return sb.replace(sb.length() - 3, sb.length(), ")").toString();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
