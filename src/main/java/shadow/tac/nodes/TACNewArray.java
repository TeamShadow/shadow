package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of array allocation
 * Example: int:create[5]
 * @author Jacob Young
 */

//TODO: Add optimization so that initialized arrays don't need to be cleared to all zeroes
public class TACNewArray extends TACOperand
{
	private ArrayType type;
	private TACOperand base;	
	private TACOperand size;

	public TACNewArray(TACNode node, ArrayType arrayType, TACOperand baseClass,
			TACOperand size) {
		super(node);
		type = arrayType;
		if( arrayType.isNullable() )
			getModifiers().addModifier(Modifiers.NULLABLE);
		base = check(baseClass, new SimpleModifiedType(Type.CLASS));
		this.size = check(size, new SimpleModifiedType(Type.LONG));
	}

	public TACOperand getBaseClass() {
		return base;
	}
	public Type getBaseType() {
		return getType().getBaseType();
	}
	@Override
	public ArrayType getType() {
		return type;
	}
	@Override
	public int getNumOperands() {
		return 1;
	}
	
	public TACOperand getSize() {
		return size;
	}
	
	@Override
	public TACOperand getOperand(int num) {
		if( num == 0 )			
			return size;
		
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getType().toString()).append(":create");
		sb.append('[');
		sb.append(size.toString());
		sb.append(']');
		return sb.toString();
	}
}
