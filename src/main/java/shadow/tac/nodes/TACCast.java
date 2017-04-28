package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import shadow.ShadowException;
import shadow.interpreter.ShadowValue;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodTableType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.PointerType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public class TACCast extends TACUpdate
{
	private Type type;
	private Modifiers modifiers;
	private final Kind kind;
	private List<TACOperand> operands = new ArrayList<TACOperand>();
	
	public enum Kind
	{
		ARRAY_TO_OBJECT,
		INTERFACE_TO_OBJECT,
		ITEM_TO_SEQUENCE,
		NULL_TO_ARRAY,
		NULL_TO_INTERFACE,
		OBJECT_TO_ARRAY,
		OBJECT_TO_INTERFACE,
		OBJECT_TO_OBJECT,
		OBJECT_TO_PRIMITIVE,
		PRIMITIVE_TO_OBJECT,
		PRIMITIVE_TO_PRIMITIVE,		
		SEQUENCE_TO_ITEM,
		SEQUENCE_TO_SEQUENCE
	}
	
	public enum Format
	{
		ARRAY,
		INTERFACE,
		NULL,
		OBJECT,
		PRIMITIVE,
		SEQUENCE
	}
	
	public static TACCast cast(TACNode node, ModifiedType destination, TACOperand op)
	{
		return cast(node, destination, op, false);
	}

	
	static int levels = 0;
	
	@SuppressWarnings("incomplete-switch")
	public static TACCast cast(TACNode node, ModifiedType destination, TACOperand op, boolean check)
	{		
		levels++;
		try
		{
			if (destination.getType() == Type.NULL)
				destination = new SimpleModifiedType(Type.OBJECT,
						new Modifiers(Modifiers.NULLABLE));
			
			Format in = typeToFormat(op);
			Type inType = op.getType();
			Type outType = destination.getType();
			Format out = typeToFormat(destination);
			ModifiedType intermediate;
			
			switch( in ) {
			case ARRAY:			
				switch( out ) {
				case INTERFACE:
					intermediate = new SimpleModifiedType(((ArrayType)inType).convertToGeneric());
					return new TACCast(node, destination, new TACCast(node, intermediate, op, Kind.ARRAY_TO_OBJECT, check ), Kind.OBJECT_TO_INTERFACE, check);
				case OBJECT:
					intermediate = new SimpleModifiedType(((ArrayType)inType).convertToGeneric());
					//the "same" types, no object cast needed
					if( needsCast(intermediate, destination)  )
						return new TACCast(node, destination, new TACCast(node, intermediate, op, Kind.ARRAY_TO_OBJECT, check ), Kind.OBJECT_TO_OBJECT, check);
					else
						return new TACCast(node, intermediate, op, Kind.ARRAY_TO_OBJECT, check );
				case SEQUENCE:
					return new TACCast(node, destination, op, Kind.ITEM_TO_SEQUENCE, check);				
				}			
				break;
				
			case INTERFACE:
				switch( out ) {
				case ARRAY:
					intermediate = new SimpleModifiedType(((ArrayType)outType).convertToGeneric());
					return new TACCast(node, destination, new TACCast(node, intermediate, op, Kind.INTERFACE_TO_OBJECT, check ), Kind.OBJECT_TO_ARRAY, check);
				case INTERFACE:
					intermediate = new SimpleModifiedType(Type.OBJECT);
					return new TACCast(node, destination, new TACCast(node, intermediate, op, Kind.INTERFACE_TO_OBJECT, false ), Kind.OBJECT_TO_INTERFACE, check);
				case OBJECT:
					intermediate = new SimpleModifiedType(Type.OBJECT);
					//the "same" types, no object cast needed
					if( needsCast(intermediate, destination)  )				
						return new TACCast(node, destination, new TACCast(node, intermediate, op, Kind.INTERFACE_TO_OBJECT, false ), Kind.OBJECT_TO_OBJECT, check);	
					else
						return new TACCast(node, intermediate, op, Kind.INTERFACE_TO_OBJECT, false );
				case PRIMITIVE:
					intermediate = new SimpleModifiedType(outType, new Modifiers(destination.getModifiers().getModifiers() | Modifiers.NULLABLE));
					return new TACCast(node, destination, new TACCast(node, intermediate, op, Kind.INTERFACE_TO_OBJECT, check ), Kind.OBJECT_TO_PRIMITIVE, check);
				case SEQUENCE:
					return new TACCast(node, destination, op, Kind.ITEM_TO_SEQUENCE, check);
				}			
				break;
				
			case NULL:
				switch( out ) {
				case ARRAY:
					return new TACCast(node, destination, op, Kind.NULL_TO_ARRAY, check);
				case INTERFACE:
					return new TACCast(node, destination, op, Kind.NULL_TO_INTERFACE, check);				
				case OBJECT:
					return new TACCast(node, destination, op, Kind.OBJECT_TO_OBJECT, check);				
				case SEQUENCE:
					return new TACCast(node, destination, op, Kind.ITEM_TO_SEQUENCE, check);				
				}
				break;
				
			case OBJECT:
				switch( out ) {
				case ARRAY:
					return new TACCast(node, destination, op, Kind.OBJECT_TO_ARRAY, check);
				case INTERFACE:
					return new TACCast(node, destination, op, Kind.OBJECT_TO_INTERFACE, check);
				case OBJECT:
					return new TACCast(node, destination, op, Kind.OBJECT_TO_OBJECT, check);
				case PRIMITIVE:
					return new TACCast(node, destination, op, Kind.OBJECT_TO_PRIMITIVE, check);
				case SEQUENCE:
					return new TACCast(node, destination, op, Kind.ITEM_TO_SEQUENCE, check);				
				}
	
				break;
			case PRIMITIVE:
				switch( out ) {
				case INTERFACE:
					intermediate = new SimpleModifiedType(inType, new Modifiers(destination.getModifiers().getModifiers() | Modifiers.NULLABLE)); 
					return new TACCast(node, destination, new TACCast(node, intermediate, op, Kind.PRIMITIVE_TO_OBJECT, false ), Kind.OBJECT_TO_INTERFACE, check);
				case OBJECT:
					intermediate = new SimpleModifiedType(inType, new Modifiers(Modifiers.NULLABLE));
					if( needsCast(intermediate, destination)  )
						return new TACCast(node, destination, new TACCast(node, intermediate, op, Kind.PRIMITIVE_TO_OBJECT, false ), Kind.OBJECT_TO_OBJECT, check);
					else
						return new TACCast(node, intermediate, op, Kind.PRIMITIVE_TO_OBJECT, false );
				case PRIMITIVE:
					return new TACCast(node, destination, op, Kind.PRIMITIVE_TO_PRIMITIVE, false );
				case SEQUENCE:
					break;
				}
	
				break;
			case SEQUENCE:
				switch( out ) {
				case ARRAY:
				case INTERFACE:
				case OBJECT:
				case PRIMITIVE:
					intermediate = ((SequenceType)inType).get(0);
					if( needsCast(intermediate, destination) )				
						return TACCast.cast(node, destination, new TACCast(node, intermediate, op, Kind.SEQUENCE_TO_ITEM, check), check);
					else
						return new TACCast(node, destination, op, Kind.SEQUENCE_TO_ITEM, check);
				case SEQUENCE:
					return new TACCast(node, destination, op, Kind.SEQUENCE_TO_SEQUENCE, check);
				}
	
				break;
			}
			
			throw new IllegalArgumentException();
		}
		finally
		{
			levels--;
		}
	}
	
	
	private static Format typeToFormat(ModifiedType modifiedType) {
		Type type = modifiedType.getType();
		Modifiers modifiers = modifiedType.getModifiers();
		
		if( type == Type.NULL )
			return Format.NULL;
		else if( type instanceof SequenceType )
			return Format.SEQUENCE;
		else if( type instanceof ArrayType )
			return Format.ARRAY;
		else if( type instanceof InterfaceType )
			return Format.INTERFACE;
		else if( type instanceof ClassType || type instanceof TypeParameter || type instanceof MethodTableType || type instanceof PointerType ) {
			if( type.isPrimitive() && !modifiers.isNullable() )
				return Format.PRIMITIVE;			
			return Format.OBJECT;
		}
		
		throw new IllegalArgumentException();
	}
	
	/*
	public TACCast(TACNode node, ModifiedType destination, TACOperand op)
	{
		this( node, destination, op, false);
	}
	*/
	
	public Kind getKind() {
		return kind;
	}
	
	
	@SuppressWarnings("incomplete-switch")
	private TACCast(TACNode node, ModifiedType destination, TACOperand op, Kind kind, boolean check ) {
		super(node);
		
		type = destination.getType();
		modifiers = new Modifiers(destination.getModifiers());
		this.kind = kind;
		
		//big change removing this!
		//op = check(op, op); //gets rid of references
		
		ModifiedType modifiedType;
		SequenceType destinationSequence;
		TACSequenceElement element;
		operands.add(op);
		
		switch( kind ) {
		case ARRAY_TO_OBJECT:
			//only changes to generic form of array
			//additional casts are needed to go to another object type
			ArrayType arrayType = (ArrayType) op.getType();			
			operands.add(new TACClass(this, arrayType.convertToGeneric()));			
			break;
		case ITEM_TO_SEQUENCE:
			destinationSequence = (SequenceType)type;
			modifiedType = destinationSequence.get(0); 
			if( needsCast(modifiedType, op) )
				operands.set(0, cast(this, modifiedType, op, check));			
			break;			
		case OBJECT_TO_ARRAY: //do nothing now?			
			break;
		case OBJECT_TO_INTERFACE:
			operands.add(getInterfaceData(op, type));
			break;
		case OBJECT_TO_OBJECT:
			if( check && !op.getType().isSubtype(type) )
				objectToObjectCheck(op);			
			break;			
		case OBJECT_TO_PRIMITIVE:
			modifiers.removeModifier(Modifiers.NULLABLE); //marks the difference between int and shadow:standard@int
			break;
		case PRIMITIVE_TO_OBJECT:
			modifiers.addModifier(Modifiers.NULLABLE); //marks the difference between int and shadow:standard@int
			break;
		case SEQUENCE_TO_SEQUENCE:
			operands.clear();
			destinationSequence = (SequenceType)type;
			int index = 0;
			for (ModifiedType destType : destinationSequence) {
				element = new TACSequenceElement(this, op, index);
				if( needsCast(element, destType) )				
					operands.add(cast(this, destType, element, check));
				else
					operands.add(element);
				index++;
			}
		}	
	}
	
	private TACOperand getInterfaceData(TACOperand source, Type destination) {
		TACOperand srcClass;			
		Type sourceType = source.getType();
		
		if( sourceType instanceof ClassType ) {
			ClassType classType = (ClassType) source.getType();
		
			TACMethodRef getClass = new TACMethodRef(this, source,
					classType.getMatchingMethod("getClass", new SequenceType()));
			
			srcClass = new TACCall(this, getClass, getClass.getPrefix());
		}
		else if( sourceType instanceof TypeParameter )		
			srcClass = new TACClass(this, sourceType).getClassData();
		else
			throw new IllegalArgumentException("Unknown source type: " + sourceType);
				
		TACMethodRef methodRef = new TACMethodRef(this, srcClass,
				Type.CLASS.getMethods("interfaceData").get(0));
		TACOperand destClass = new TACClass(this, destination).getClassData();					
		return new TACCall(this, methodRef, methodRef.getPrefix(), destClass);
	}
	
	private static boolean needsCast(ModifiedType modifiedType1, ModifiedType modifiedType2 ) {
		Type type1 = modifiedType1.getType();
		Type type2 = modifiedType2.getType();
		
		if( type1.isPrimitive() && type2.isPrimitive() && modifiedType1.getModifiers().isNullable() != modifiedType2.getModifiers().isNullable() )
			return true;
		
		return !type1.isSubtype(type2) || !type2.isSubtype(type1);
	}
	
	private void objectToObjectCheck(TACOperand op) {
		//get class from object
		TACMethodRef methodRef = new TACMethodRef(this, op,
				Type.OBJECT.getMatchingMethod("getClass", new SequenceType()));						
		
		TACOperand operandClass = new TACCall(this, methodRef, methodRef.getPrefix());
		TACOperand destinationClass = new TACClass(this, type).getClassData();
		
		methodRef = new TACMethodRef(this, operandClass,
				Type.CLASS.getMatchingMethod("isSubtype", new SequenceType(Type.CLASS)));
		
		
		TACOperand result = new TACCall(this, methodRef, methodRef.getPrefix(), destinationClass);
		TACLabel throwLabel = new TACLabel(getMethod());
		TACLabel doneLabel = new TACLabel(getMethod());
		
		new TACBranch(this, result, doneLabel, throwLabel);
		
		throwLabel.insertBefore(this);
		
		TACOperand object = new TACNewObject(this, Type.CAST_EXCEPTION);
		SequenceType params = new SequenceType();			
		params.add(operandClass);
		params.add(destinationClass);
		
		MethodSignature signature;
		signature = Type.CAST_EXCEPTION.getMatchingMethod("create", params);
					
		methodRef = new TACMethodRef(this, signature);			
		TACCall exception = new TACCall(this, methodRef, object, operandClass, destinationClass);
					
		new TACThrow(this, exception);						
		
		doneLabel.insertBefore(this);	//done label
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
		return operands.size();
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if( num < operands.size() )
			return operands.get(num);
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
		StringBuffer sb = new StringBuffer("cast<" + getModifiers() + getType() + '>');
		sb.append('(');
		int length = operands.size();
		if( kind == Kind.ARRAY_TO_OBJECT || kind == Kind.OBJECT_TO_INTERFACE ) //extra ops
			length--;		
		for( int i = 0; i < length; ++i ) {
			sb.append(operands.get(i).toString());
			if( i < length - 1)
				sb.append(", ");
		}		
		sb.append(')');		
		return sb.toString();
	}

	@Override
	public TACOperand getValue()
	{
		if( getUpdatedValue() == null )
			return this;
		else
			return getUpdatedValue();
	}

	@Override
	public boolean update(Set<TACUpdate> currentlyUpdating) {
		if( currentlyUpdating.contains(this) )
			return false;
		
		currentlyUpdating.add(this);
		boolean changed = false;
		TACOperand updated = operands.get(0); 
			
		if( operands.get(0) instanceof TACUpdate ) {
			TACUpdate update = (TACUpdate) operands.get(0);
			if( update.update(currentlyUpdating) )
				changed = true;			
			updated = update.getValue();
		}
		
		if( (changed || getUpdatedValue() == null) && updated instanceof TACLiteral && kind == Kind.PRIMITIVE_TO_PRIMITIVE ) {
			try {
				TACLiteral literal = (TACLiteral)updated;
				ShadowValue result = literal.getValue().cast(type);
				setUpdatedValue(new TACLiteral(this, result));
				changed = true;
			}
			catch(ShadowException e)
			{} //do nothing, failed to evaluate
		}
		
		currentlyUpdating.remove(this);
		return changed;	
	}
}
