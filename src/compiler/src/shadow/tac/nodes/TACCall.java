package shadow.tac.nodes;

import java.util.List;

import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.typecheck.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACCall extends TACPrefixed
{
	private MethodSignature signature;
	private TACSequence parameters;
	private String[] returnSymbols;
	public TACCall(MethodSignature methodSignature, TACSequence parameterValues)
	{
		this(null, methodSignature, parameterValues);
	}
	public TACCall(TACNode prefixNode, MethodSignature methodSignature, TACSequence parameterValues)
	{
		super(prefixNode);
		signature = methodSignature;
		parameters = parameterValues;
		List<ModifiedType> argumentTypes = methodSignature.getMethodType().getParameterTypes();
		List<ModifiedType> parameterTypes = parameterValues.getType().getTypes();
		if (argumentTypes.size() != parameterTypes.size())
			throw new IllegalArgumentException("Wrong number of arguments.");
		for (int i = 0; i < argumentTypes.size(); i++)
		{
			Type expectedType = argumentTypes.get(i).getType(),
					actualType = parameterTypes.get(i).getType();
			if (!actualType.equals(expectedType))
			{
				if (actualType.isSubtype(expectedType));
// FIXME:			parameterValues.getNodes().set(i, new TACCast(
//							expectedType, parameterValues.getNodes()
//							.get(i)));
				else
					throw new IllegalArgumentException("Invalid types passed to method call.");
			}
		}
		int returnCount = signature.getMethodType().getReturnTypes().size();
		if (returnCount == 0)
			returnSymbols = null;
		else
			returnSymbols = new String[returnCount - 1];
	}

	@Override
	public boolean expectsPrefix()
	{
		return !ModifierSet.isStatic(signature.getMethodType().getModifiers());
	}
	@Override
	public Type getType() {
		List<ModifiedType> retTypes = signature.getMethodType().getReturnTypes();
		if (retTypes.size() == 0)
			return null;
		if (retTypes.size() == 1)
			return retTypes.get(0).getType();
		return getSequenceType();
	}
	public SequenceType getSequenceType()
	{
		return new SequenceType(signature.getMethodType().getReturnTypes());
	}
	public MethodSignature getSignature()
	{
		return signature;
	}
	public TACSequence getParameters()
	{
		return parameters;
	}

	public int getSymbolCount()
	{
		return returnSymbols == null ? 0 : returnSymbols.length + 1;
	}
	public String getSymbol(int index)
	{
		if (index == 0)
			return getSymbol();
		else
			return returnSymbols[index - 1];
	}
	public void setSymbol(int index, String symbol)
	{
		if (index == 0)
			setSymbol(symbol);
		else
			returnSymbols[index - 1] = symbol;
	}
	
	@Override
	public String toString()
	{
		return signature.getSymbol() + parameters;
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
