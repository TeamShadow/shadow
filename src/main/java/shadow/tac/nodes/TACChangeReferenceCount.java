package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;

public class TACChangeReferenceCount extends TACNode {

	private TACVariable variable;
	private TACFieldRef field;
	private boolean increment;
	private TACOperand classData; //only used for array decrements
	
	public TACChangeReferenceCount(TACNode node, TACVariable variable, boolean increment) {
		super(node);
		this.variable = variable;
		this.increment = increment;
		if( !increment && variable.getType() instanceof ArrayType ) {
			ArrayType arrayType = (ArrayType) variable.getType();
			classData = new TACClass(this, arrayType.getBaseType() );
		}
	}
	
	public TACChangeReferenceCount(TACNode node, TACFieldRef field, boolean increment) {
		super(node);
		this.field = field;
		this.increment = increment;
		if( !increment && field.getType() instanceof ArrayType ) {
			ArrayType arrayType = (ArrayType) field.getType();
			classData = new TACClass(this, arrayType.getBaseType() );
		}
	}	
	
	public boolean isField() {
		return field != null;
	}
	
	public boolean isIncrement()
	{
		return increment;
	}

	@Override
	public int getNumOperands() 
	{
		return 0;
	}

	public TACVariable getVariable()
	{
		return variable;
	}

	@Override
	public TACOperand getOperand(int num) 
	{
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException 
	{
		visitor.visit(this);
	}

	public TACOperand getClassData()
	{
		return classData;
	}

	public TACFieldRef getField() {
		return field;
	}	
}
