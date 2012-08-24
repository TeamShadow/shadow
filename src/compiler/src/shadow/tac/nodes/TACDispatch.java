package shadow.tac.nodes;

import java.util.Collection;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Type;

public class TACDispatch extends TACCall
{
	private TACOperand prefix;
	private int index;
	public TACDispatch(TACOperand thisRef, TACMethod method,
			Collection<TACOperand> params)
	{
		this(null, thisRef, method, params);
	}
	public TACDispatch(TACNode node, TACOperand thisRef, TACMethod method,
			Collection<TACOperand> params)
	{
		super(node, method, params);
		Type type = thisRef.getType();
		if (type instanceof ArrayType)
			type = Type.ARRAY;
		prefix = check(thisRef, type);
		index = ((ClassType)method.getPrefixType()).getMethodIndex(
				new MethodSignature(method.getType(), method.getName(), null));
	}

	public TACOperand getPrefix()
	{
		return prefix;
	}
	public int getIndex()
	{
		return index;
	}

	@Override
	public int getNumOperands()
	{
		return 1 + super.getNumOperands();
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return prefix;
		return super.getOperand(num - 1);
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
		sb.append(getPrefix()).append('.').append(getMethod().getName()).
				append('(');
		for (TACOperand parameter : getParameters())
			sb.append(parameter).append(", ");
		if (getNumParameters() != 0)
			sb.delete(sb.length() - 2, sb.length());
		return sb.append(')').toString();
	}
}
