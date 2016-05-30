package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;

public class TACLocalStore extends TACSimpleNode {

	private TACVariableRef reference;
	private TACOperand value;
	private int number;
	
	public TACLocalStore(TACVariableRef ref, TACOperand op, TACMethod method)
	{
		this(null, ref, op, method);
	}
	public TACLocalStore(TACNode node, TACVariableRef ref, TACOperand op, TACMethod method)
	{
		super(node);
		reference = ref;
		number = method.incrementVariableCounter();
		value = check(op, ref.getSetType());		
		new TACNodeRef(node, value);
	}

	public TACVariableRef getReference()
	{
		return reference;
	}
	public TACOperand getValue()
	{
		return value;
	}

	@Override
	public int getNumOperands()
	{
		return 2;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return reference;
		if (num == 1)
			return value;
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return reference + " = " + value;
	}
	
	public int getNumber()
	{
		return number;
	}
}
