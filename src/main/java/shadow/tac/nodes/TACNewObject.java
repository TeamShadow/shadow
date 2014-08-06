package shadow.tac.nodes;

import shadow.parser.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of object allocation
 * Example: Object:create()
 * @author Jacob Young
 */

public class TACNewObject extends TACOperand
{
	private ClassType type;	
	private TACOperand classData;
	private TACOperand methodTable;	

	public TACNewObject(TACNode node, ClassType type)
	{
		super(node);		
		this.type = type;
		//for most intents and purposes, we will treat a type with arguments like the generic 		

		//TODO: do we really need a separate TACClass object? refactor?
		//class needs real type
		TACClass _class = new TACClass(this, type);
		this.classData = _class.getClassData();
		this.methodTable = _class.getMethodTable();
	}
	
	public TACOperand getClassData()
	{
		return classData;
	}
	
	public TACOperand getMethodTable()
	{
		return methodTable;
	}
	
	public ClassType getClassType()
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
