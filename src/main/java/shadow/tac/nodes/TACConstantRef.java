package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACConstantRef extends TACReference {
	private Type prefix;
	private ModifiedType type;
	private String name;
	
	public TACConstantRef(TACNode node, Type prefixType, String constantName) {
		super(node);
		ModifiedType constantType = prefixType.getField(constantName);
		if (constantType == null)
			throw new IllegalArgumentException("Field does not exist");
		if (!constantType.getModifiers().isConstant())
			throw new IllegalArgumentException("Field is not a constant");
		prefix = prefixType;
		type = constantType;
		name = constantName;
	}
	
	@Override
	public boolean equals(Object o) {
		if( o == null )
			return false;
		
		if( o instanceof TACConstantRef ) {
			TACConstantRef other = (TACConstantRef) o;
			return prefix.equals(other.prefix) && name.equals(other.name);
		}
		
		return false;		
	}
	
	@Override
	public String toString() {
		return prefix.getImportName() + ":" + name;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	public Type getPrefixType() {
		return prefix;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Modifiers getModifiers() {
		return type.getModifiers();
	}
	
	@Override
	public Type getType() {
		return type.getType();
	}
	
	@Override
	public void setType(Type newType) {
		type.setType(newType);
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
}
