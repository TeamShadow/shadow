package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACSequenceRef extends TACReference
{
	private SequenceType type;
	private List<TACReference> sequence;
	public TACSequenceRef(TACSequence seq)
	{
		this(null, seq);
	}
	public TACSequenceRef(TACNode node, TACSequence seq)
	{
		super(node);
		type = seq.getType();
		sequence = new ArrayList<TACReference>(seq.size());
		for (int i = 0; i < seq.size(); i++)
			sequence.add((TACReference)seq.get(i));
	}

	public int size()
	{
		return sequence.size();
	}
	public TACReference get(int index)
	{
		return sequence.get(index);
	}

	@Override
	public Type getType()
	{
		return type;
	}
	@Override
	public int getNumOperands()
	{
		return size();
	}
	public TACOperand getOperand(int index)
	{
		return get(index);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder().append('(');
		for (TACReference node : sequence)
			sb.append(node).append(", ");
		return sb.delete(sb.length() - 2, sb.length()).toString();
	}
}
