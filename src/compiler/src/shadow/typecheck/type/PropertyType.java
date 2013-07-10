package shadow.typecheck.type;

import java.util.List;

import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.typecheck.BaseChecker.Error;
import shadow.typecheck.ClassChecker;
import shadow.typecheck.ClassChecker.SubstitutionType;

public class PropertyType extends Type {
	
	private MethodSignature getter;
	private MethodSignature setter;
	
	public PropertyType(MethodSignature getter, MethodSignature setter) {	
		super(null);			
		this.getter = getter;
		this.setter = setter;	
	}	

	public MethodSignature getGetter()
	{
		return getter;
	}
	
	public MethodSignature getSetter()
	{
		return setter;
	}
	
	public ModifiedType getGetType()
	{
		if( getter == null )
			return null;
		
		return getter.getReturnTypes().get(0);
	}
	
	public ModifiedType getSetType()
	{
		if( setter == null )
			return null;
		
		return setter.getParameterTypes().get(0);
	}
	
	public boolean isGettable()
	{
		return getter != null;		
	}
	
	public boolean isSettable()
	{
		return setter != null;
	}	
	
	
	//fix this after add/subtract/mod/multiply/divide stuff is done
	public boolean acceptsAssignment( ModifiedType right, ASTAssignmentOperator.AssignmentType assignmentType, SubstitutionType substitutionType, List<String> reasons )
	{
		if( !isSettable() )
		{
			ClassChecker.addReason(reasons, Error.INVALID_ASSIGNMENT, "Non-settable property type " + this + " cannot be assigned to");
			return false;
		}
		
		ModifiedType left = getSetType();
		
		if( assignmentType == ASTAssignmentOperator.AssignmentType.EQUAL )
			return ClassChecker.checkAssignment(left, right, assignmentType, substitutionType, reasons);
	
		/*	
		a->x = b;			store
		a->x += 3;			read and store
		c->y = a->x += 4;	read and store and give back property
		*/
		
		if( !isGettable() )
		{
			ClassChecker.addReason(reasons, Error.INVALID_ASSIGNMENT, "Non-gettable property type " + this + " cannot use assignment other than =");
			return false;
		}
			
		
		if( !ClassChecker.checkAssignment(left, right, assignmentType, substitutionType, reasons) )
			return false; //checks modifiers			
			
		Type getType = getGetType().getType();
		Type leftType = left.getType();
		Type rightType = right.getType();
		
		boolean success = true;
		
		switch( assignmentType  )
		{
		case PLUS:			
		case MINUS:
		case STAR:
		case SLASH:
			success = leftType.isNumerical() && getType.isSubtype(leftType) && rightType.isSubtype(leftType);
			if( !success )
				ClassChecker.addReason(reasons, Error.INVALID_ASSIGNMENT, "Non-numerical result cannot be assigned");
			
			return success;

		case AND:
		case OR:
		case XOR:
		case MOD:
		case LEFT_SHIFT:
		case RIGHT_SHIFT:
		case RIGHT_ROTATE:
		case LEFT_ROTATE:
			success = leftType.isIntegral() && getType.isSubtype(leftType) && rightType.isSubtype(leftType);
			if( !success )
				ClassChecker.addReason(reasons, Error.INVALID_ASSIGNMENT, "Non-integral result cannot be assigned");
			
			return success;
			

		case CAT:			
			success = leftType.isString() && getType.isString();
			if( !success )
				ClassChecker.addReason(reasons, Error.INVALID_ASSIGNMENT, "Non-String result cannot be concatenated");
			
			return success;
		}
		
		return false;
	}	
	
	
	@Override
	public boolean isSubtype(Type other) {

		if( other instanceof PropertyType )
		{
			PropertyType otherProperty = (PropertyType)other;
			if( otherProperty.getter != null )
			{
				if( getter == null )
					return false;
				//covariant on get
				if( !getGetType().getType().isSubtype(otherProperty.getGetType().getType()) )
					return false;
			}
			
			if( otherProperty.setter != null )
			{
				if( setter == null )
					return false;
				//contravariant on set
				if( !otherProperty.getGetType().getType().isSubtype(getGetType().getType()) )
					return false;
			}			
			
			return true;
		}
		
		return false;
	}

	@Override
	public PropertyType replace(SequenceType values,
			SequenceType replacements) {
		
		MethodSignature replacedGetter = null;
		MethodSignature replacedSetter = null;
		if( getter != null )
			replacedGetter = getter.replace(values, replacements);
		if( setter != null )
			replacedSetter = setter.replace(values, replacements);	
			
		return new PropertyType( replacedGetter, replacedSetter );
	}
	
	@Override
	public String toString()
	{
		return toString(false);
	}
	
	@Override
	public String toString(boolean withBounds)
	{
		StringBuilder sb = new StringBuilder("[");
		
		if( isGettable() )
		{
			sb.append("get: ");
			sb.append(getGetType().getType().toString(withBounds));
		}
		
		if( isGettable() && isSettable() )
		{
			sb.append(", ");
		}
		
		if( isSettable() )
		{
			sb.append("set: ");
			sb.append(getSetType().getType().toString(withBounds));
		}
		sb.append("]");
		return sb.toString();		
	}
}
