package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

/** 
 * TAC representation of class constant
 * Example: Object:class
 * @author Jacob Young and Barry Wittman
 */

public class TACClass extends TACOperand
{
	private Type type;
	private TACOperand classData;
	private TACOperand methodTable;
	
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
	
	
	public TACClass(Type classType, TACMethod method)
	{
		this(null, classType, method);
	}
	public TACClass(TACNode node, Type classType, TACMethod method)
	{
		super(node);
		type = classType;
		if (classType instanceof TypeParameter)
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
				
				
				TACClass classValue = new TACClass(this, outer, method);
				TACOperand genericClass = new TACCast(this, new SimpleModifiedType(Type.GENERIC_CLASS), classValue.getClassData() );
				TACOperand generics = new TACFieldRef(this, genericClass, "parameters" );
				TACOperand parameter = new TACArrayRef(this, generics, new TACLiteral( this, "" + (2*index)) );
				
				methodTable = new TACArrayRef(this, generics, new TACLiteral( this, "" + (2*index + 1)) );
				classData = new TACCast(this, new SimpleModifiedType(Type.CLASS), parameter );
						
				//check( parameter, new SimpleModifiedType(Type.CLASS));			
			}
		}
		else
		{			
			methodTable = new TACMethodTable(this);
			classData = new TACClassData(this);
		}
	}

	public boolean hasOperands()
	{
		return classData != null && methodTable != null;
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
		return 2;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if( hasOperands() )
		{
			if( num == 0 )
				return classData;
			else if( num == 1 )
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
