package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.tac.nodes.TACConversion.Kind;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
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
		this( node, destination, op, false);
	}
	

	public TACCast(TACNode node, ModifiedType destination, TACOperand op, boolean check)
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
				operand = new TACConversion(this, op, type, Kind.SEQUENCE_TO_SEQUENCE, check);
			else if( type instanceof SequenceType )			
				operand = new TACConversion(this, op, type, Kind.OBJECT_TO_SEQUENCE, check);
			else
				operand = new TACConversion(this, op, type, Kind.SEQUENCE_TO_OBJECT, check);
		}			
		else if( op.getType().equals(Type.NULL) )//|| destination.equals(Type.NULL) ) //does that second condition ever happen?
		{			
			if( type instanceof ArrayType )			
				operand = new TACConversion(this, op, type, Kind.NULL_TO_ARRAY);
			else if( type instanceof InterfaceType )
				operand = new TACConversion(this, op, type, Kind.NULL_TO_INTERFACE);
			else
				operand = op;
		}
		else
		{		
			if( op.getType() instanceof InterfaceType )
			{
				op = new TACConversion(this, op, Type.OBJECT, Kind.INTERFACE_TO_OBJECT);
			}
						
			if( type instanceof InterfaceType )
			{				
				if( op.getType().isPrimitive() && !op.getModifiers().isNullable() )				
					op = new TACConversion(this, op, op.getType(), Kind.PRIMITIVE_TO_OBJECT);
				else if( op.getType() instanceof ArrayType )
					op = new TACConversion(this, op, ((ArrayType)op.getType()).convertToGeneric(), Kind.ARRAY_TO_OBJECT);
								
				operand = new TACConversion(this, op, type, Kind.OBJECT_TO_INTERFACE, check);
				return;
			}			
			
			if( op.getType().isPrimitive() != type.isPrimitive())
			{
				if( op.getType().isPrimitive() )	
				{
					//nullable primitives are already objects
					if( !op.getModifiers().isNullable() )
						op = new TACConversion(this, op, op.getType(), Kind.PRIMITIVE_TO_OBJECT);
				}
				else
				{
					if( !modifiers.isNullable() )
						operand = new TACConversion(this, op, type, Kind.OBJECT_TO_PRIMITIVE, check);
					else
						operand = op;
					return;
				}				
			}
			
			if( op.getType().isPrimitive() && type.isPrimitive() && (op.getModifiers().isNullable() || modifiers.isNullable()) )
			{
				if( op.getModifiers().isNullable() && modifiers.isNullable() )
				{
					if( !op.getType().equals(type) ) //can't just cast objects if they're different types
					{
						TACLabelRef doneLabel = new TACLabelRef(this);
						TACLabelRef nullLabel = new TACLabelRef(this);
						TACLabelRef convertLabel = new TACLabelRef(this);
						
						TACReference var = new TACVariableRef(this,
								getBuilder().getMethod().addTempLocal(destination));
						
						TACLiteral nullLiteral = new TACLiteral(this, "null");
						TACOperand compare = new TACSame(this, op, nullLiteral);
						new TACBranch(this, compare, nullLabel, convertLabel);
						
						nullLabel.new TACLabel(this);						
						new TACStore(this, var, nullLiteral);
						new TACBranch(this, doneLabel);
						
						convertLabel.new TACLabel(this);
						
						TACOperand converted = new TACConversion(this, op, op.getType(), Kind.OBJECT_TO_PRIMITIVE);
						converted = new TACCast(this, new SimpleModifiedType(type), converted, check);
						converted = new TACConversion(this, converted, type, Kind.PRIMITIVE_TO_OBJECT);
						new TACStore(this, var, converted);
						new TACBranch(this, doneLabel);
						
						doneLabel.new TACLabel(this);
						operand = new TACLoad(this, var);
					}
				}				
				else if( modifiers.isNullable() )				
					operand = new TACConversion(this, op, type, Kind.PRIMITIVE_TO_OBJECT );
				else				
					operand = new TACConversion(this, op, type, Kind.OBJECT_TO_PRIMITIVE);
				
				return;
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
					operand = new TACConversion(this, op, type, Kind.OBJECT_TO_ARRAY, check); 
					return;					
				}
			}
			
			operand = op;
		}
		
		
		if( check && !operand.getType().isSubtype(type) && !(operand.getType().isPrimitive() && type.isPrimitive()) )
			//subtypes should be safe
			//primitive types are freely convertible (except for booleans?)
		{			
			TACBlock block = getBuilder().getBlock();
			
			//get class from object
			TACMethodRef methodRef = new TACMethodRef(this, operand,
					Type.OBJECT.getMatchingMethod("getClass", new SequenceType()));						
			
			TACOperand operandClass = new TACCall(this, block, methodRef, methodRef.getPrefix());
			TACOperand destinationClass = new TACClass(this, type).getClassData();
			
			methodRef = new TACMethodRef(this, operandClass,
					Type.CLASS.getMatchingMethod("isSubtype", new SequenceType(Type.CLASS)));
			
			
			TACOperand result = new TACCall(this, block, methodRef, methodRef.getPrefix(), destinationClass);
			TACLabelRef throwLabel = new TACLabelRef(this);
			TACLabelRef doneLabel = new TACLabelRef(this);
			
			new TACBranch(this, result, doneLabel, throwLabel);
			
			throwLabel.new TACLabel(this);
			
			TACOperand object = new TACNewObject(this, Type.CAST_EXCEPTION);
			SequenceType params = new SequenceType();			
			params.add(operandClass);
			params.add(destinationClass);
			
			MethodSignature signature;
			signature = Type.CAST_EXCEPTION.getMatchingMethod("create", params);
						
			methodRef = new TACMethodRef(this, signature);			
			TACCall exception = new TACCall(this, block, methodRef, object, operandClass, destinationClass);
						
			new TACThrow(this, block, exception);						
			
			doneLabel.new TACLabel(this);	//done label	
			new TACNodeRef(this, operand);
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
