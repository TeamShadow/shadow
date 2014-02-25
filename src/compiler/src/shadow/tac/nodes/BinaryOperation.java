package shadow.tac.nodes;

import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class BinaryOperation
{		
	// CONCATENATION(Type.STRING, '#');
	
	public enum Comparison { NONE, LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN_OR_EQUAL };

	private MethodSignature method;
	private ModifiedType result;		
	private String string;
	private Comparison comparison;
	private ModifiedType first;
	private ModifiedType second;
		
	public BinaryOperation( ModifiedType firstType, ModifiedType secondType, char op )
	{	
		first = firstType;
		second = secondType;
		
		if (first.getType() instanceof PropertyType)
			first = ((PropertyType)first.getType()).getGetType();
		if (second.getType() instanceof PropertyType)
			second = ((PropertyType)second.getType()).getGetType();
	
		String operation = "";
		string = String.valueOf(op);
		comparison = Comparison.NONE;
		switch( op )
		{
		case '+': operation = "add"; break;
		case '-': operation = "subtract"; break;
		case '*': operation = "multiply"; break;
		case '/': operation = "divide"; break;
		case '%': operation = "modulus"; break;

		case '|': operation = "bitOr"; break;
		case '^': operation = "bitXor"; break;
		case '&': operation = "bitAnd"; break;
		case 'l': operation = "bitShiftLeft"; string = "<<"; break;
		case 'r': operation = "bitShiftRight"; string = ">>";  break;
		case 'L': operation = "bitRotateLeft"; string = "<<<"; break;
		case 'R': operation = "bitRotateLeft"; string = ">>>"; break;

		case '=': operation = "equal"; string = "=="; break;
		case '!': operation = "equals"; string = "!="; break;
		
		case '<': operation = "compare"; comparison = Comparison.LESS_THAN; break;
		case '>': operation = "compare"; comparison = Comparison.GREATER_THAN; break;
		case '{': operation = "compare"; comparison = Comparison.LESS_THAN_OR_EQUAL; string = "<="; break;
		case '}': operation = "compare"; comparison = Comparison.GREATER_THAN_OR_EQUAL; string = ">="; break;
		
		//no methods for these
		case 'o': string = "or"; break;
		case 'x': string = "xor"; break;
		case 'a': string = "and"; break;
		
		default:
			throw new InternalError("Unknown operator: " + op);
		}
				
		if( operation.isEmpty() ) //boolean operations never have methods
			result = new SimpleModifiedType(Type.BOOLEAN);
		else
		{
			Type methodClass = firstType.getType();
			method = methodClass.getMatchingMethod(operation, new SequenceType(secondType));
			result = method.getReturnTypes().get(0);
			
			//but there isn't actually a method for (most) primitive type operators
			//the "native" ones aren't really there
			if( methodClass.isPrimitive() && method.getMethodType().getModifiers().isNative() )
				method = null;				
		}			
	}	
			
	public boolean hasMethod()
	{
		return method != null;
	}
	
	public ModifiedType getResultType()
	{
		return result;
	}	
	
	@Override
	public String toString()
	{
		return string;
	}
	
	public MethodSignature getMethod()
	{
		return method;
	}
	
	public Comparison getComparison()
	{
		return comparison;
	}
	
	public ModifiedType getFirst()
	{
		return first;
	}
	
	public ModifiedType getSecond()
	{
		return second;
	}
}
