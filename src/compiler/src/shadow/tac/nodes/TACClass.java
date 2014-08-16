package shadow.tac.nodes;

import shadow.output.llvm.LLVMOutput;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

/** 
 * TAC representation of class constant
 * Example: Object:class
 * @author Jacob Young 
 * @author Barry Wittman
 */

public class TACClass extends TACOperand
{
	private Type type;
	private TACOperand classData;
	private TACOperand methodTable;
	private TACClass baseClass = null;
	
	public class TACClassData extends TACOperand
	{		
		public TACClassData(TACNode node)
		{
			super(node);
		}
		
		public Type getClassType()
		{
			return type;
		}

		@Override
		public Type getType() 
		{
			return Type.CLASS;
		}

		@Override
		public int getNumOperands()
		{		
			return 0;
		}

		@Override
		public TACOperand getOperand(int num) 
		{		
			throw new IndexOutOfBoundsException("" + num);
		}

		@Override
		public void accept(TACVisitor visitor) throws ShadowException 
		{
			visitor.visit(this);			
		}
	}
	
	public class TACMethodTable extends TACOperand
	{	
		
		public TACMethodTable(TACNode node)
		{
			super(node);
		}
		
		public Type getClassType()
		{
			return type;
		}

		@Override
		public Type getType() 
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
			throw new IndexOutOfBoundsException("" + num);
		}

		@Override
		public void accept(TACVisitor visitor) throws ShadowException 
		{
			visitor.visit(this);			
		}
	}		

	public TACClass(TACNode node, Type classType)
	{
		super(node);
		type = classType;
		TACMethod method = getBuilder().getMethod();
		if (type instanceof TypeParameter)
		{	
			TACVariable var = method.getParameter(classType.getTypeName());
			if (var != null)
			{
				//TODO: Add in support for generic methods
				//op = check(new TACVariableRef(this, var), this);
			}
			else
			{
				Type outer = classType.getOuter();
				int index = 0;				
				for( ModifiedType parameter : outer.getTypeParameters()  )
				{
					if( parameter.getType().getTypeName().equals(classType.getTypeName()) )
						break;
					index++;
				}
				
				if( index == outer.getTypeParameters().size() )
					throw new IllegalArgumentException();
				
				//get class from this
				//cast class to GenericClass
				//get generics field from GenericClass
				//get arrayref to index location
				
				TACVariableRef _this = new TACVariableRef(this, method.getThis());				
				TACLoad classValue = new TACLoad(this, new TACFieldRef(this, _this, new SimpleModifiedType(Type.CLASS, new Modifiers(Modifiers.IMMUTABLE)), "class")); 
				TACOperand genericClass = new TACCast(this, new SimpleModifiedType(Type.GENERIC_CLASS), classValue);
				TACOperand generics = new TACFieldRef(this, genericClass, "parameters");
				TACOperand parameter = new TACArrayRef(this, generics, new TACLiteral( this, "" + (2*index)));
				
				methodTable = new TACArrayRef(this, generics, new TACLiteral( this, "" + (2*index + 1)) );
				classData = new TACCast(this, new SimpleModifiedType(Type.CLASS), parameter );
			}
		}		
		else
		{	
			if( !type.isParameterizedIncludingOuterClasses() || type.isFullyInstantiated())
			{	
				classData = new TACClassData(this);
				
				if( type instanceof ArrayType )
				{
					ArrayType arrayType = (ArrayType) type;
					methodTable = null;
					baseClass = new TACClass(this, arrayType.getBaseType());
				}
				else
					methodTable = new TACMethodTable(this);
			}
			else //type is partially instantiated
			{	
				Type outer = method.getMethod().getOuter();
				
				if( type instanceof ArrayType )	
				{
					TACBlock block = getBuilder().getBlock();
					//type = ((ArrayType)type).convertToGeneric(); //old stuff
					ArrayType arrayType = (ArrayType) type;
					
					baseClass = new TACClass(this, arrayType.getBaseType());
					TACOperand baseClassData = baseClass.getClassData();
					
					TACOperand flags = new TACLiteral(this, "" + LLVMOutput.ARRAY);
					TACOperand size = new TACLiteral(this, "" + arrayType.getDimensions());
					TACOperand name = new TACFieldRef(this, baseClassData, "name");
					TACOperand arrayClass = new TACNewObject(this, Type.CLASS);
					
					SequenceType arguments = new SequenceType();				
					arguments.add(flags);
					arguments.add(size);
					arguments.add(name);
					arguments.add(baseClassData);
					arguments.add(new SimpleModifiedType(new ArrayType(Type.CLASS), new Modifiers(Modifiers.IMMUTABLE)));
					arguments.add(new SimpleModifiedType(new ArrayType(Type.OBJECT), new Modifiers(Modifiers.IMMUTABLE)));
					
					TACMethodRef create = new TACMethodRef(this, Type.CLASS.getMatchingMethod("create", arguments));
					
					classData = new TACCall(this, block, create, arrayClass, flags, size, name, baseClassData, new TACLiteral(this, "null"), new TACLiteral(this, "null"));												
					methodTable = null; //not needed for arrays				
				}			
				else if( type.encloses(outer) ) //we're currently inside this type and can get it from class values
				{					
					TACOperand prefix = new TACVariableRef(this, method.getThis());
					
					while( !type.equals(outer))
					{
						prefix = new TACFieldRef(this, prefix, prefix, "_outer");
						outer = outer.getOuter();						
					}
					
					classData = new TACLoad(this, new TACFieldRef(this, prefix, new SimpleModifiedType(Type.CLASS, new Modifiers(Modifiers.IMMUTABLE)), "class"));
					methodTable = new TACMethodTable(this);
				}
				else
				{					
					TACOperand[] arguments = new TACOperand[type.getTypeParametersIncludingOuterClasses().size()];
					int i = 0;
					
					for( ModifiedType argument : type.getTypeParametersIncludingOuterClasses() )
					{					
						arguments[i] = new TACClass(this, argument.getType()).getClassData();					
						i++;
					}
					
					classData = new TACConstructGeneric(this, arguments, type.getTypeWithoutTypeArguments());
					methodTable = new TACMethodTable(this);
				}
			}
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
	
	
	public TACClass getBaseClass()
	{
		return baseClass; //non-null for array classes
	}

	@Override
	public Type getType()
	{
		/*
		if( type instanceof ArrayType )			
			return Type.ARRAY_CLASS;
		if( type instanceof MethodType )
			return Type.METHOD_CLASS;
		if( type.isParameterized() && !type.equals(type.getTypeWithoutTypeArguments()) )
			return Type.GENERIC_CLASS;
		
		return Type.CLASS;
		*/
		return Type.CLASS;
		//return type;
	}
	@Override
	public int getNumOperands()
	{
		if( classData == null )
			return 0;
		else if( methodTable == null)
			return 1;
		
		return 2;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if( num < getNumOperands() )
		{
			if( num == 0 )
				return classData;
			else if( num == 1)
				return methodTable;			
		}
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
		return type + ":class";
	}
}
