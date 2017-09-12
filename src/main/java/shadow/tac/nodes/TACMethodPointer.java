package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACMethodPointer extends TACOperand implements TACMethodRef {
	
	private TACOperand pointer;
	private MethodType methodType;
	private String name;
	
	public TACMethodPointer(TACNode node, TACOperand pointer, String name, MethodType methodType) {
		super(node);
		this.pointer = pointer;
		this.methodType = methodType;		
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public TACOperand getPointer() {
		return pointer;
	}

	@Override
	public SequenceType getParameterTypes() {
		SequenceType paramTypes = new SequenceType();
		paramTypes.add(new SimpleModifiedType(Type.OBJECT));
		paramTypes.addAll(methodType.getParameterTypes());		
		return paramTypes;
	}
	
	@Override
	public SequenceType getUninstantiatedParameterTypes() {		
		SequenceType paramTypes = new SequenceType();
		paramTypes.add(new SimpleModifiedType(Type.OBJECT));
		paramTypes.addAll(methodType.getTypeWithoutTypeArguments().getParameterTypes());		
		return paramTypes;
	}
	
	@Override
	public SequenceType getUninstantiatedReturnTypes() {		
		return methodType.getTypeWithoutTypeArguments().getReturnTypes();
	}

	@Override
	public Type getReturnType() {		
		return methodType.getReturnTypes().get(0).getType();
	}

	@Override
	public SequenceType getReturnTypes() {		
		return methodType.getReturnTypes();
	}

	@Override
	public Type getType() {		
		return methodType;
	}
	
	@Override
	public int getNumOperands() {	
		return 1;
	}
	
	@Override
	public TACOperand getOperand(int num) {
		if( num == 0 )
			return pointer;
		
		throw new IndexOutOfBoundsException("" + num);
	}


	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);		
	}

	@Override
	public SequenceType getFullReturnTypes() {		
		return getReturnTypes();
	}
}
