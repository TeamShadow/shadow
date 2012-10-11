package shadow.typecheck.type;

import java.util.List;

import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.parser.javacc.ShadowParser.ModifierSet;

public class PropertyType extends Type {
	
	private MethodType getter;
	private MethodType setter;
	
	public PropertyType(MethodType getter, MethodType setter) {	
		super(null);			
		this.getter = getter;
		this.setter = setter;	
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
	
	@Override
	public boolean acceptsAssignment( Type rightType, ASTAssignmentOperator.AssignmentType assignmentType )
	{
		if( !isSettable() )
			return false;
		
		Type leftType = getSetType().getType();
		
		if( assignmentType == ASTAssignmentOperator.AssignmentType.EQUAL )
			return rightType.isSubtype(leftType);
		
		/*	
		a->x = b;			store
		a->x += 3;			read and store
		c->y = a->x += 4;	read and store and give back property
		*/
		
		if( !isGettable() )
			return false;
		
		if( !ModifierSet.isNullable(getSetType().getModifiers()) && ModifierSet.isNullable(getGetType().getModifiers()) )
			return false;			
			
		Type getType = getGetType().getType();
		
		switch( assignmentType  )
		{
		case PLUSASSIGN:			
		case MINUSASSIGN:
		case STARASSIGN:
		case SLASHASSIGN:
			return leftType.isNumerical() && getType.isSubtype(leftType) && rightType.isSubtype(leftType);			

		case ANDASSIGN:
		case ORASSIGN:
		case XORASSIGN:
		case MODASSIGN:
		case LEFTSHIFTASSIGN:
		case RIGHTSHIFTASSIGN:
		case RIGHTROTATEASSIGN:
		case LEFTROTATEASSIGN:
			return leftType.isIntegral() && getType.isSubtype(leftType) && rightType.isSubtype(leftType);

		case CATASSIGN:
			return leftType.isString() && getType.isString();
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
	public PropertyType replace(List<TypeParameter> values,
			List<ModifiedType> replacements) {
		
		MethodType replacedGetter = null;
		MethodType replacedSetter = null;
		if( getter != null )
			replacedGetter = getter.replace(values, replacements);
		if( setter != null )
			replacedSetter = setter.replace(values, replacements);		
		
		return new PropertyType( replacedGetter, replacedSetter );
	}

}
