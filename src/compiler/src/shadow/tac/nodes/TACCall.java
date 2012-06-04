package shadow.tac.nodes;

import java.io.IOException;

import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACCall extends TACPrefixed
{
	private String name;
	private MethodType type;
	private TACSequence parameters;
	private String[] returnSymbols;
	public TACCall(String methodName, MethodType methodType,
			TACSequence parameterValues)
	{
		this(null, methodName, methodType, parameterValues);
	}
	public TACCall(TACNode prefixNode, String methodName, MethodType methodType,
			TACSequence parameterValues)
	{
		super(prefixNode);
		name = methodName;
		type = methodType;
		parameters = parameterValues;
		SequenceType argumentTypes = methodType.getParameterTypes();
		SequenceType parameterTypes = parameterValues.getType();
		if (argumentTypes.size() != parameterTypes.size())
			throw new IllegalArgumentException("Wrong number of arguments.");
		for (int i = 0; i < argumentTypes.size(); i++)
		{
			Type expectedType = argumentTypes.get(i).getType(),
					actualType = parameterTypes.get(i).getType();
			if (!actualType.equals(expectedType))
					throw new IllegalArgumentException("Invalid types passed " +
							"to method call.");
		}
		int returnCount = type.getReturnTypes().size();
		if (returnCount == 0)
			returnSymbols = null;
		else
			returnSymbols = new String[returnCount - 1];
	}

	@Override
	public boolean expectsPrefix()
	{
		return !ModifierSet.isStatic(type.getModifiers());
	}
	@Override
	public Type expectedPrefixType()
	{
		return type.getOuter();
	}
	@Override
	public Type getType() {
		SequenceType retTypes = type.getReturnTypes();
		if (retTypes.size() == 0)
			return null;
		if (retTypes.size() == 1)
			return retTypes.get(0).getType();
		return retTypes;
	}
	public SequenceType getSequenceType()
	{
		return type.getReturnTypes();
	}
	public String getMethodName()
	{
		return name;
	}
	public MethodType getMethodType()
	{
		return type;
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
		return name + parameters;
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
