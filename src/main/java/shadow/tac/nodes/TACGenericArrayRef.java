package shadow.tac.nodes;

import java.util.Arrays;
import java.util.Collection;

import shadow.typecheck.type.ArrayType;

public class TACGenericArrayRef extends TACArrayRef {
	
	private TACClass _class;	
	
	public TACGenericArrayRef(TACNode node, TACOperand reference, TACOperand... ops) {
		this(node, reference, Arrays.asList(ops), true);
	}	

	public TACGenericArrayRef(TACNode node, TACOperand reference, TACOperand op, boolean check) {
		this(node, reference, Arrays.asList(op), check);
	}
	
	public TACGenericArrayRef(TACNode node, TACOperand reference,
			Collection<TACOperand> ops) {
		this(node, reference, ops, true);
	}
	
	public TACGenericArrayRef(TACNode node, TACOperand reference,
			Collection<TACOperand> ops, boolean check) {
		super(node, reference, ops, check);
		
		ArrayType arrayType = (ArrayType) reference.getType();		
		_class = new TACClass(this, arrayType.getBaseType());		
		new TACNodeRef(this, getTotal());
	}
	
	public TACClass getClassData() {		
		return _class;
	}
}
