package shadow.tac.nodes;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACPhi extends TACNode
{
	private Type type;
	private TACNode[] nodes;
	public TACPhi(Type nodeType, TACNode... nodeList)
	{
		for (int i = 0; i < nodeList.length; i++)
		{
			if (!nodeList[i].getType().equals(nodeType))
			{
				if (nodeList[i].getType().isSubtype(nodeType));
// FIXME:			nodeList[i] = new TACCast(nodeType, nodeList[i]);
				else
					throw new IllegalArgumentException("Incompatible option in phi.");
			}
		}
		type = nodeType;
		nodes = nodeList;
	}
	
	@Override
	public Type getType()
	{
		return type;
	}
	public TACNode[] getNodes()
	{
		return nodes;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		for (TACNode node : nodes)
			sb.append(node).append(" : ");
		return sb.replace(sb.length() - 3, sb.length(), ")").toString();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
