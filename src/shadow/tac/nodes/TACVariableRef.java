package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACVariableRef extends TACReference
{
	private TACVariable variable;
	public TACVariableRef(TACVariable var)
	{
		this(null, var);
	}
	public TACVariableRef(TACNode node, TACVariable var)
	{
		super(node);
		if (var == null)
			throw new NullPointerException();
		variable = var;
	}

	public TACVariable getVariable()
	{
		return variable;
	}
	public String getName()
	{
		return variable.getName();
	}

	@Override
	public Modifiers getModifiers()
	{
		return variable.getModifiers();
	}
	@Override
	public Type getType()
	{
		return variable.getType();
	}
	@Override
	public void setType(Type newType)
	{
		variable.setType(newType);
	}
	@Override
	public int getNumOperands()
	{
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		throw new IndexOutOfBoundsException(""+num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return variable.getName();
	}
}
