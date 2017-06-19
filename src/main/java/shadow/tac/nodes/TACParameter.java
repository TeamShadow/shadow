package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACParameter extends TACOperand {
	
	private ModifiedType type;
	private final int number;
	private boolean increment = false;

	public TACParameter(TACNode node, ModifiedType type, int number) {
		super(node);	
		this.type = type;
		this.number = number;		
	}
	
	public boolean isIncrement() {
		return increment;
	}
	
	public void setIncrement(boolean value) {
		increment = value;
	}
	
	public int getNumber() {
		return number;
	}

	@Override
	public Type getType() {
		return type.getType();
	}
	
	@Override
	public Object getData() {
		return "%" + number;
	}
	
	@Override
	public Modifiers getModifiers() {
		return type.getModifiers();
	}

	@Override
	public int getNumOperands() {
		return 0;
	}

	@Override
	public TACOperand getOperand(int num) {
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}
	
	@Override
	public boolean equals(Object object) {
		if( object == null || !(object instanceof TACParameter))
			return false;
		
		return ((TACParameter)object).number == number;
	}
	
	@Override
	public boolean canPropagate() {
		return true;
	}
	
	@Override
	public String toString() {		
		if( number == 0 )
			return "this";		
		MethodSignature signature = getMethod().getSignature();		
		return signature.getParameterNames().get(number - 1);
	}
}
