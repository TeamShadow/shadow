package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/**
 * Instruction used to convert a ulong value to a pointer.
 * Used in the copy() method where a hashmap of addresses are maintained.
 * 
 * @author Barry Wittman
 */

public class TACLongToPointer extends TACOperand {
	
	private TACOperand value;
	private ModifiedType type;

	public TACLongToPointer(TACNode node, TACOperand value, ModifiedType type)
	{
		super(node);
		//check is used in case the ulong is nullable and must be converted from object form 
		this.value = check(value, new SimpleModifiedType(Type.ULONG));
		this.type = type;
	}	

	@Override
	public Type getType() {		
		return type.getType();
	}

	@Override
	public int getNumOperands() {		
		return 1;
	}

	@Override
	public TACOperand getOperand(int num) {
		if( num == 0 )
			return value;
		
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}
}
