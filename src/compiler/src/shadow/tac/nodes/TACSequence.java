package shadow.tac.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.SequenceType;

public class TACSequence extends TACNode implements Iterable<TACNode>
{
	private SequenceType type;
	private List<TACNode> nodes;
	public TACSequence()
	{
		type = new SequenceType();
		nodes = new ArrayList<TACNode>();
	}
	public TACSequence(TACNode node)
	{
		type = new SequenceType();
		type.add(node);
		nodes = Collections.singletonList(node);
	}
	public TACSequence(List<TACNode> nodeList)
	{
		type = new SequenceType();
		for (TACNode node : nodeList)
			type.add(node);
		nodes = nodeList;
	}
	
	public void addNode(TACNode node)
	{
		type.add(node);
		nodes.add(node);
	}
	
	@Override
	public SequenceType getType()
	{
		return type;
	}
	public List<TACNode> getNodes()
	{
		return nodes;
	}
	public boolean isEmpty()
	{
		return nodes.isEmpty();
	}
	@Override
	public Iterator<TACNode> iterator()
	{
		return nodes.iterator();
	}
	
	@Override
	public String toString()
	{
		if (nodes.isEmpty())
			return "( )";
		StringBuilder sb = new StringBuilder();
		sb.append("( ");
		for (TACNode node : nodes)
			sb.append(node).append(", ");
		sb.replace(sb.length() - 2, sb.length(), " )");
		return sb.toString();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
