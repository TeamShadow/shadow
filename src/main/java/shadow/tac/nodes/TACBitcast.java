package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of a low-level cast, which casts a reference type
 * to another reference type or a primitive type to another primitive type.
 * TACBitcast differs from TACCast in that it contains no conversions
 * between the different kinds of types: primitive, reference, array, 
 * sequences.
 * Example: cast<int>(5.7)
 * @author Barry Wittman
 */

public class TACBitcast extends TACOperand
{
	private Type type;
	private Modifiers modifiers;
	private TACOperand operand;	
	
	public TACBitcast(TACNode node, ModifiedType destination, TACOperand op)
	{
		super(node);
		if (destination.getType() == Type.NULL)
			destination = new SimpleModifiedType(Type.OBJECT,
					new Modifiers(Modifiers.NULLABLE));
		
		operand = check(op, op); //gets rid of references		
		type = destination.getType();
		modifiers = new Modifiers(destination.getModifiers());		
	}

	public TACOperand getOperand()
	{
		return operand;
	}

	@Override
	public Modifiers getModifiers()
	{
		return modifiers;
	}
	@Override
	public Type getType()
	{
		return type;
	}
	@Override
	public void setType(Type newType)
	{
		type = newType;
	}
	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if( num == 0 )
			return operand;
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "cast<" + getModifiers() + getType() + '>' + getOperand();
	}
}
