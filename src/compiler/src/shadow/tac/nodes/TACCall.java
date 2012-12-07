package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACCall extends TACOperand
{
	private TACMethod method;
	private List<TACOperand> parameters;
	public TACCall(TACMethod methodRef, Collection<TACOperand> params)
	{
		this(null, methodRef, params);
	}
	public TACCall(TACNode node, TACMethod methodRef,
			Collection<? extends TACOperand> params)
	{
		super(node);
		method = methodRef;
		SequenceType types = methodRef.getType().getParameterTypes();
		if (params.size() - 1 != types.size())
			throw new IllegalArgumentException("Wrong # args");
		Iterator<? extends TACOperand> paramIter = params.iterator();
		Iterator<ModifiedType> typeIter = types.iterator();
		parameters = new ArrayList<TACOperand>(params.size());
		parameters.add(check(paramIter.next(), methodRef.getPrefixType()));
		while (paramIter.hasNext())
			parameters.add(check(paramIter.next(), typeIter.next().getType()));
	}

	public TACMethod getMethod()
	{
		return method;
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
		return parameters;
	}

	@Override
	public Type getType()
	{
		return method.getReturnType();
	}
	@Override
	public int getNumOperands()
	{
		return parameters.size();
	}
	@Override
	public TACOperand getOperand(int num)
	{
		return parameters.get(num);
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
		TACMethod method = getMethod();
		sb.append(method.getPrefixType()).append('.').append(method.getName()).
				append('(');
		for (TACOperand parameter : getParameters())
			sb.append(parameter).append(", ");
		if (getNumParameters() != 0)
			sb.delete(sb.length() - 2, sb.length());
		return sb.append(')').toString();
	}
}
