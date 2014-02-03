package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

/** 
 * TAC representation of class constant
 * Example: Object:class
 * @author Jacob Young
 */

public class TACClass extends TACOperand
{
	private Type type;
	private TACOperand op;
	public TACClass(Type classType, TACMethod method)
	{
		this(null, classType, method);
	}
	public TACClass(TACNode node, Type classType, TACMethod method)
	{
		super(node);
		type = classType;
		if (classType instanceof TypeParameter)
		{
			TACVariable var = method.getParameter(classType.getTypeName());
			if (var != null)
				op = check(new TACVariableRef(this, var), this);
			else
				op = check(new TACFieldRef(this, new TACVariableRef(this,
						method.getParameter("this")), this,
						classType.getTypeName()), this);
		}
	}

	public Type getClassType()
	{
		return type;
	}
	public boolean hasOperand()
	{
		return op != null;
	}
	public TACOperand getOperand()
	{
		return op;
	}

	@Override
	public Type getType()
	{
		return Type.CLASS;
	}
	@Override
	public int getNumOperands()
	{
		return op == null ? 0 : 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0 && op != null)
			return op;
		throw new IndexOutOfBoundsException("num");
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return type + ":class";
	}
}
