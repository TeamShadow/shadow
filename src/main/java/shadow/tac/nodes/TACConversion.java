package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public class TACConversion extends TACOperand
{	
	Type type;
	Modifiers modifiers;
	Kind kind;	
	List<TACOperand> operands = new ArrayList<TACOperand>();
	
	public enum Kind
	{
		INTERFACE_TO_OBJECT,
		OBJECT_TO_INTERFACE,
		PRIMITIVE_TO_OBJECT,
		OBJECT_TO_PRIMITIVE,
		ARRAY_TO_OBJECT,
		OBJECT_TO_ARRAY,
		SEQUENCE_TO_SEQUENCE,
		SEQUENCE_TO_OBJECT,
		OBJECT_TO_SEQUENCE,
		NULL_TO_ARRAY,
		NULL_TO_INTERFACE
	}	

	public TACConversion(TACNode node, TACOperand source, Type destination, Kind kind)
	{
		this(node, source, destination, kind, false);
	}
	
	public TACConversion(TACNode node, TACOperand source, Type destination, Kind kind, boolean check)
	{
		super(node);
		type = destination;	
		modifiers = new Modifiers(source.getModifiers());
		operands.add(source);
		this.kind = kind;
		
		if( kind.equals(Kind.OBJECT_TO_INTERFACE) )
		{			
			TACOperand srcClass;			
			Type sourceType = source.getType();
			
			if( sourceType instanceof ClassType )
			{
				ClassType classType = (ClassType) source.getType();
			
				TACMethodRef getClass = new TACMethodRef(this, source,
						classType.getMatchingMethod("getClass", new SequenceType()));
				
				srcClass = new TACCall(this, getBuilder().getBlock(), getClass, getClass.getPrefix());
			}
			else if( sourceType instanceof TypeParameter )
			{
				srcClass = new TACClass(this, sourceType);				
			}
			else
				throw new IllegalArgumentException("Unknown source type: " + sourceType);
					
			TACMethodRef methodRef = new TACMethodRef(this, srcClass,
					Type.CLASS.getMethods("interfaceData").get(0));
			TACClass destClass = new TACClass(this, destination);					
			operands.add(new TACCall(this, getBuilder().getBlock(), methodRef, methodRef.getPrefix(), destClass));
		}
		else if( kind.equals(Kind.ARRAY_TO_OBJECT))
		{
			ArrayType arrayType = (ArrayType) source.getType();			
			operands.add(new TACClass(this, arrayType.convertToGeneric()));
		}
		else if( kind.equals(Kind.SEQUENCE_TO_SEQUENCE))
		{
			SequenceType destinationSequence = (SequenceType)type;			
			
			int index = 0;
			for (ModifiedType destType : destinationSequence)
			{
				TACOperand element = new TACSequenceElement(this, source, index);				
				operands.add(new TACCast(this, destType, element, check));			
				index++;
			}
		}
		else if( kind.equals(Kind.SEQUENCE_TO_OBJECT))
		{
			//method returning a sequence with a single thing in it
			TACSequenceElement element = new TACSequenceElement(this, source, 0);
			operands.add(new TACCast(this, new SimpleModifiedType(type), element, check));
		}
		else if( kind.equals(Kind.OBJECT_TO_SEQUENCE))
		{
			SequenceType destinationSequence = (SequenceType)type;			
			operands.add(new TACCast(this, destinationSequence.get(0), source, check));
		}
		else if( kind.equals(Kind.PRIMITIVE_TO_OBJECT))
		{
			modifiers.addModifier(Modifiers.NULLABLE); //marks the difference between int and shadow:standard@int
		}
		else if( kind.equals(Kind.OBJECT_TO_PRIMITIVE))
		{
			modifiers.removeModifier(Modifiers.NULLABLE);
		}
	}
	
	public Kind getKind()
	{
		return kind;
	}
	
	@Override
	public Modifiers getModifiers()
	{
		return modifiers;
	}

	@Override
	public Type getType() {		
		return type;
	}

	@Override
	public int getNumOperands() {		
		return operands.size();
	}

	@Override
	public TACOperand getOperand(int num) {
		return operands.get(num);		
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}

}
