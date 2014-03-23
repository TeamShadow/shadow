package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACCall extends TACOperand
{
	private TACBlock blockRef;
	private TACMethodRef methodRef;
	private List<TACOperand> parameters;
	public TACCall(TACBlock block, TACMethodRef method, TACOperand... params)
	{
		this(null, block, method, Arrays.asList(params));
	}
	public TACCall(TACBlock block, TACMethodRef method,
			Collection<? extends TACOperand> params)
	{
		this(null, block, method, params);
	}
	public TACCall(TACNode node, TACBlock block, TACMethodRef method,
			TACOperand... params)
	{
		this(node, block, method, Arrays.asList(params));
	}
	public TACCall(TACNode node, TACBlock block, TACMethodRef method,
			Collection<? extends TACOperand> params)
	{
		super(node);
		blockRef = block;
		methodRef = method;
		SequenceType types = method.getParameterTypes();
		if (params.size() != types.size())
			throw new IllegalArgumentException("Wrong # args");
		Iterator<? extends TACOperand> paramIter = params.iterator();
		Iterator<ModifiedType> typeIter = types.iterator();
		parameters = new ArrayList<TACOperand>(params.size());
		while (paramIter.hasNext())
			parameters.add(check(paramIter.next(), typeIter.next()));
	}

	public TACBlock getBlock()
	{
		return blockRef;
	}
	public TACMethodRef getMethod()
	{
		return methodRef;
	}
	public TACOperand getPrefix()
	{
		return parameters.get(0);
	}
	public int getNumParameters()
	{
		return parameters.size();
	}
	public TACOperand getParameter(int index)
	{
		return parameters.get(index);
	}
	public List<TACOperand> getParameters()
	{
		return new ArrayList<TACOperand>(parameters);
	}

	@Override
	public Type getType()
	{
		return methodRef.getReturnType();
	}
	@Override
	public int getNumOperands()
	{
		return 2 + parameters.size();
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return blockRef;
		if (num == 1)
			return methodRef;
		return parameters.get(num - 2);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		TACMethodRef method = getMethod();
		sb.append(method.getOuterType()).append('.').append(method.getName()).
				append('(');
		for (TACOperand parameter : getParameters())
			sb.append(parameter).append(", ");
		if (getNumParameters() != 0)
			sb.delete(sb.length() - 2, sb.length());
		return sb.append(')').toString();
	}
}
