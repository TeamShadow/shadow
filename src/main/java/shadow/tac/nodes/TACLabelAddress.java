package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.output.llvm.LLVMOutput;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.PointerType;
import shadow.typecheck.type.Type;

public class TACLabelAddress extends TACOperand {	

	private final TACLabel label;
	private final TACMethod method;
	
	public TACLabelAddress(TACNode node, TACLabel label, TACMethod method)
	{
		super(node);
		this.label = label;
		this.method = method;
	}
	
	public TACLabel getLabel()
	{
		return label;
	}
	
	public TACMethod getMethod()
	{
		return method;
	}

	@Override
	public int getNumOperands()
	{
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		throw new IndexOutOfBoundsException("" + num);
	}
	
	@Override
	public Object getData()
	{
		return "blockaddress(" + LLVMOutput.name(method) + ", " + LLVMOutput.symbol(label) + ")";								
	}

	@Override
	public Type getType()
	{
		return new PointerType();
	}

	@Override
	public String toString()
	{
		return "address(" + label.toString() + ")";
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
