package shadow.tac.nodes;

import shadow.output.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACVariable extends TACPrefixed
{
	private boolean field;
	private Type type;
	public TACVariable(Type varType, String varSymbol, boolean isField)
	{
		type = varType;
		setSymbol(varSymbol);
	}
	public TACVariable(TACNode prefix, Type varType, String varSymbol, boolean isField)
	{
		super(prefix);
		type = varType;
		setSymbol(varSymbol);
	}
	
	@Override
	public boolean expectsPrefix()
	{
		return field;
	}
	@Override
	public Type getType()
	{
		return type;
	}
	
	@Override
	public String toString()
	{
		return "" + type + ' ' + getSymbol();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
