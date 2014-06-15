package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

/** 
 * Creates generic class on the fly 
 * @author Barry Wittman
 */

public class TACGenericClass extends TACOperand
{
	private Type type;	
	private TACOperand parameters;
	private TACOperand classObject;
		
	public TACGenericClass(Type classType, TACMethod method)
	{
		this(null, classType, method);		
	}
	public TACGenericClass(TACNode node, Type classType, TACMethod method)
	{
		super(node);
		type = classType;
		
		SequenceType typeParameters = classType.getTypeParameters();	
		
		//array 
		parameters = new TACNewArray(this, new ArrayType(Type.CLASS, 1), new TACClass(this, Type.CLASS, method), new TACLiteral(this, "" + typeParameters.size()) );
		TACOperand operand;
		int index = 0;
		List<TACOperand> indices;
		for( ModifiedType parameter : typeParameters )
		{
			//maybe add something for TypeParameters
			if( parameter.getType().isParameterized() )
				operand = new TACGenericClass(this, parameter.getType(), method);
			else
				operand = new TACClass(this, parameter.getType(), method);
						
			indices = new ArrayList<TACOperand>(1);
			indices.add(new TACLiteral(this, "" + index ));
			TACReference reference = new TACArrayRef(this, parameters, indices);
			new TACStore(this, reference, operand);
			index++;			
		}
		
		//class to hold the generic class
		classObject = new TACNewObject(this, Type.GENERIC_CLASS, method);
	}
	
	public TACOperand getClassObject()
	{
		return classObject;
	}
	
	public TACOperand getParameters()
	{
		return parameters;
	}	

	public Type getClassType()
	{
		return type;
	}

	@Override
	public Type getType()
	{
		return Type.GENERIC_CLASS;
	}
	@Override
	public TACOperand getOperand(int num)
	{		
		throw new IndexOutOfBoundsException("num");
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
	@Override
	public int getNumOperands() {
		
		return 0;
	}
}
