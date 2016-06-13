package shadow.tac.nodes;

import shadow.interpreter.ShadowInteger;
import shadow.output.llvm.LLVMOutput;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

/** 
 * TAC representation of object allocation
 * Example: Object:create()
 * @author Jacob Young
 */

public class TACNewObject extends TACOperand
{
	private Type type;	
	private TACOperand classData;
	private TACOperand methodTable;	

	public TACNewObject(TACNode node, Type type) {
		super(node);		
		this.type = type;

		//class needs real type
		TACClass _class = new TACClass(this, type);
		classData = _class.getClassData();
		if( !classData.getType().equals(Type.CLASS) )
			classData = TACCast.cast(this, new SimpleModifiedType(Type.CLASS), classData);
		methodTable = _class.getMethodTable();
		
		//there's a chance that it could be an interface, which isn't allowed
		if( type instanceof TypeParameter ) {
			TACOperand flags = new TACLoad(this, new TACFieldRef(classData, Type.CLASS.getField("flags"), "flags" ));
			TACLiteral interfaceFlag = new TACLiteral(this, new ShadowInteger(LLVMOutput.INTERFACE) );			
			TACOperand value = new TACBinary(this, flags, Type.INT.getMatchingMethod("bitAnd", new SequenceType(interfaceFlag)), '&', interfaceFlag);
			MethodSignature signature = Type.INT.getMatchingMethod("equal", new SequenceType(value));
			TACOperand test = new TACBinary(this, value, signature, '=', new TACLiteral(this, new ShadowInteger(0)));
			
			TACMethod method = getMethod();
			TACLabelRef throwLabel = new TACLabelRef(method);
			TACLabelRef doneLabel = new TACLabelRef(method);			
			new TACBranch(this, test, doneLabel, throwLabel);			
			
			throwLabel.new TACLabel(this);					
			TACOperand object = new TACNewObject(this, Type.INTERFACE_CREATE_EXCEPTION);
			TACOperand name = new TACLoad(this, new TACFieldRef(classData, Type.CLASS.getField("name"), "name"));
			signature = Type.INTERFACE_CREATE_EXCEPTION.getMatchingMethod("create", new SequenceType(name));
			
			TACCall exception = new TACCall(this, new TACMethodRef(this, signature), object, name);
			new TACThrow(this, exception);
			
			doneLabel.new TACLabel(this);
		}		
	}
	
	public TACOperand getClassData()
	{
		return classData;
	}
	
	public TACOperand getMethodTable()
	{
		return methodTable;
	}
	
	public Type getClassType()
	{
		return type;
	}
		

	@Override
	public ClassType getType()
	{
		return Type.OBJECT;
	}	

	@Override
	public int getNumOperands()
	{
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return type + ":create()";
	}
}
