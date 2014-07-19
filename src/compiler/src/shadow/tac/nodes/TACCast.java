package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.tac.nodes.TACConversion.Kind;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACCast extends TACOperand
{
	private Type type;
	private Modifiers modifiers;
	private TACOperand operand;	

	public TACCast(TACNode node, ModifiedType destination, TACOperand op)
	{
		super(node);
		if (destination.getType() == Type.NULL)
			destination = new SimpleModifiedType(Type.OBJECT,
					new Modifiers(Modifiers.NULLABLE));
		
		op = check(op, op); //gets rid of references
		
		type = destination.getType();
		modifiers = new Modifiers(destination.getModifiers());
		
		if( type instanceof SequenceType || op.getType() instanceof SequenceType )
		{
			if( type instanceof SequenceType && op.getType() instanceof SequenceType )							
				operand = new TACConversion(this, op, type, Kind.SEQUENCE_TO_SEQUENCE);
			else if( type instanceof SequenceType )			
				operand = new TACConversion(this, op, type, Kind.OBJECT_TO_SEQUENCE);
			else
				operand = new TACConversion(this, op, type, Kind.SEQUENCE_TO_OBJECT);
		}			
		else if( op.getType().equals(Type.NULL) || destination.equals(Type.NULL) ) //does that second condition ever happen?
			operand = op;		
		else
		{		
			if( op.getType() instanceof InterfaceType )			
				op = new TACConversion(this, op, Type.OBJECT, Kind.INTERFACE_TO_OBJECT);
						
			if( type instanceof InterfaceType )
			{				
				if( op.getType().isPrimitive() )				
					op = new TACConversion(this, op, op.getType(), Kind.PRIMITIVE_TO_OBJECT);
								
				operand = new TACConversion(this, op, type, Kind.OBJECT_TO_INTERFACE);
				return;
			}			
			
			if( op.getType().isPrimitive() != type.isPrimitive())
			{
				if( op.getType().isPrimitive() )				
					op = new TACConversion(this, op, op.getType(), Kind.PRIMITIVE_TO_OBJECT);
				else
				{
					operand = new TACConversion(this, op, type, Kind.OBJECT_TO_PRIMITIVE);
					return;
				}				
			}
			
			if( (op.getType() instanceof ArrayType) != (type instanceof ArrayType) )
			{
				if( op.getType() instanceof ArrayType )
				{
					ArrayType arrayType = (ArrayType) op.getType();
					op = new TACConversion(this, op, arrayType.convertToGeneric(), Kind.ARRAY_TO_OBJECT);					
				}
				else
				{ 
					operand = new TACConversion(this, op, type, Kind.OBJECT_TO_ARRAY); 
					return;					
				}
			}
			
			operand = op;
		}		
	}
	
	
	private void sequenceCast(ModifiedType newType, TACOperand op)
	{
		//if destination is sequence, source must be as well (even if it only has a single value)
		if( newType.getType() instanceof SequenceType )
		{
			SequenceType destType = (SequenceType) newType.getType();			
			//this is wrong, extract values!
			List<TACOperand> operands = new ArrayList<TACOperand>(op.getNumOperands());
			for( int i = 0; i < op.getNumOperands(); i++ )
				operands.add( check(op.getOperand(i), destType.get(i)));
			
			operand = new TACSequence(this, operands);			
		}
		//source is a sequence, but destination is not
		//get first thing from the source
		else
		{
			SequenceType srcType = (SequenceType) op.getType();			 
			operand = check( op.getOperand(0), srcType.get(0));
		}
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
