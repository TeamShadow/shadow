package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.tac.nodes.BinaryOperation.Comparison;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of binary operator
 * Example: x + y 
 * @author Jacob Young
 * @author Barry Wittman
 */

public class TACBinary extends TACOperand
{	
	private String operation;
	private TACOperand first, second;
	private ModifiedType result;
	
	public enum Boolean 
	{ 
		AND("and"),
		OR("or"),
		XOR("xor");
		
		private final String name;
		
		Boolean(String name)
		{
			this.name = name;
		}
		
		public String getName()
		{
			return name;
		}		
	}
	
	public TACBinary(TACNode node, TACOperand firstOperand, Boolean op,
			TACOperand secondOperand)
	{
		this(node, firstOperand, new SimpleModifiedType(Type.BOOLEAN), op.getName(), secondOperand, new SimpleModifiedType(Type.BOOLEAN), new SimpleModifiedType(Type.BOOLEAN));
	}	
	
	public TACBinary(TACNode node, TACOperand firstOperand, MethodSignature signature, char op,
			TACOperand secondOperand)
	{
		this( node, firstOperand, signature, op, secondOperand, false );
	}
	
	public TACBinary(TACNode node, TACOperand firstOperand, MethodSignature signature, char op,
			TACOperand secondOperand, boolean isCompare)
	{
		this( node, firstOperand, new SimpleModifiedType(signature.getOuter()), stringVersion(op), secondOperand, signature.getParameterTypes().get(0), isCompare ? new SimpleModifiedType(Type.BOOLEAN) : signature.getReturnTypes().get(0) );
	}
	
	private TACBinary(TACNode node, TACOperand firstOperand, ModifiedType firstType, String op,
			TACOperand secondOperand, ModifiedType secondType, ModifiedType resultType )
	{
		super(node);
		
		if (firstType.getType() instanceof PropertyType)
			firstType = ((PropertyType)firstType.getType()).getGetType();
		if (secondType.getType() instanceof PropertyType)
			secondType = ((PropertyType)secondType.getType()).getGetType();
		
		operation = op;
		first = check(firstOperand, firstType);
		second = check(secondOperand, secondType);
		result = resultType;
	}
	
	private static String stringVersion(char op)
	{
		String version = String.valueOf(op);
		switch( op )
		{		
		case 'l': version = "<<"; break;
		case 'r': version = ">>";  break;
		case 'L': version  = "<<<"; break;
		case 'R': version = ">>>"; break;
		case '=': version = "=="; break; //probably don't see this one
		case '!': version = "!="; break; //probably don't see this one		
		case '{': version = "<="; break;
		case '}': version = ">="; break;	
		case 'o': version = "or"; break;
		case 'x': version = "xor"; break;
		case 'a': version = "and"; break;
		}
		
		return version;
	}
	
	
	public TACOperand getFirst()
	{
		return first;
	}
	public String getOperation()
	{
		return operation;
	}	
	public TACOperand getSecond()
	{
		return second;
	}

	@Override
	public Type getType()
	{
		return result.getType();
	}
	@Override
	public int getNumOperands()
	{
		return 2;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return first;
		if (num == 1)
			return second;
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	private static boolean paren = false;
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		boolean isParen = paren;
		if (isParen)
			sb.append('(');
		paren = true;
		sb.append(getFirst()).append(' ').append(getOperation()).append(' ');
		paren = true;
		sb.append(getSecond());
		if (isParen)
			sb.append(')');
		paren = false;
		return sb.toString();
	}
}
