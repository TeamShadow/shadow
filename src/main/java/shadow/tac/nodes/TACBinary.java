package shadow.tac.nodes;

import java.util.Set;

import shadow.ShadowException;
import shadow.interpreter.TACInterpreter;
import shadow.interpreter.ShadowValue;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of binary operator
 * Example: x + y 
 * @author Jacob Young
 * @author Barry Wittman
 */
public class TACBinary extends TACUpdate
{	
	private String operation;
	private TACOperand first, second;
	private ModifiedType result;	
	
	public enum Boolean { 
		AND("and"),
		OR("or"),
		XOR("xor");
		
		private final String name;
		
		Boolean(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}		
	}
	
	//the one with no operation is "reference" equality, ===
	public TACBinary(TACNode node, TACOperand firstOperand, TACOperand secondOperand) {
		this(node, firstOperand, firstOperand, "===", secondOperand, secondOperand, new SimpleModifiedType(Type.BOOLEAN));
	}	
	
	public TACBinary(TACNode node, TACOperand firstOperand, Boolean op,
			TACOperand secondOperand) {
		this(node, firstOperand, new SimpleModifiedType(Type.BOOLEAN), op.getName(), secondOperand, new SimpleModifiedType(Type.BOOLEAN), new SimpleModifiedType(Type.BOOLEAN));
	}	
	
	public TACBinary(TACNode node, TACOperand firstOperand, MethodSignature signature, String op,
			TACOperand secondOperand) {
		this( node, firstOperand, signature, op, secondOperand, false);
	}
	
	public TACBinary(TACNode node, TACOperand firstOperand, MethodSignature signature, String op,
			TACOperand secondOperand, boolean isCompare) {		
		this( node, firstOperand, new SimpleModifiedType(signature.getOuter()), op, secondOperand, signature.getParameterTypes().get(0), isCompare ? new SimpleModifiedType(Type.BOOLEAN) : signature.getReturnTypes().get(0));	
	}
	
	private TACBinary(TACNode node, TACOperand firstOperand, ModifiedType firstType, String op,
			TACOperand secondOperand, ModifiedType secondType, ModifiedType resultType) {
		super(node);
		
		if (firstType.getType() instanceof PropertyType)
			firstType = ((PropertyType)firstType.getType()).getGetType();
		if (secondType.getType() instanceof PropertyType)
			secondType = ((PropertyType)secondType.getType()).getGetType();
		
		operation = op;		
		first = check(firstOperand, firstType);
		
		//reference comparison requires exactly the same types
		if( op.equals("===") ) {
			second = secondOperand;			
			if (firstType.getType().isSubtype(secondType.getType()))
				first = check(first, second);
			else if(secondType.getType().isSubtype(firstType.getType()) )
				second = check(second, first);		
			//primitive exceptions, since they can be equal to each other with no clear subtype relation
			else if( firstType.getType().isPrimitive() && secondType.getType().isPrimitive() ) {				
				if( firstType.getType().getWidth() >= secondType.getType().getWidth() )
					second = TACCast.cast(this, first, second);
				else
					first = TACCast.cast(this, second, first);			
			}
			else
				throw new UnsupportedOperationException();		
		}
		//shifts and rotates have weird issues
		//LLVM insists that you rotate a byte with a byte
		//so we have to throw in explicit casts
		else if( (op.equals("<<") || op.equals(">>") || op.equals("<<<") || op.equals(">>>")) && !firstType.getType().equals(secondType.getType()))		
			second = TACCast.cast(this, firstType, secondOperand);
		else
			second = check(secondOperand, secondType);
		result = resultType;
	}
	
	public TACOperand getFirst() {
		return first;
	}
	public String getOperation() {
		return operation;
	}	
	public TACOperand getSecond() {
		return second;
	}

	@Override
	public Type getType() {
		return result.getType();
	}
	@Override
	public int getNumOperands() {
		return 2;
	}
	@Override
	public TACOperand getOperand(int num) {
		if (num == 0)
			return first;
		if (num == 1)
			return second;
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}

	private static boolean paren = false;
	@Override
	public String toString() {
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
	
	@Override
	public Modifiers getModifiers() {
		return result.getModifiers();
	}

	@Override
	public boolean update(Set<TACUpdate> currentlyUpdating) {
		if( currentlyUpdating.contains(this) )
			return false;
		
		currentlyUpdating.add(this);
		boolean changed = false;
		TACOperand firstValue = first;
		TACOperand secondValue = second;
		
		if( first instanceof TACUpdate ) {
			TACUpdate update = (TACUpdate) first;
			if( update.update(currentlyUpdating) )
				changed = true;			
			firstValue = update.getValue();
		}		

		if( second instanceof TACUpdate ) {
			TACUpdate update = (TACUpdate) second;
			if( update.update(currentlyUpdating) )
				changed = true;			
			secondValue = update.getValue();
		}
		
		if( (changed || getUpdatedValue() == null) && firstValue instanceof TACLiteral && secondValue instanceof TACLiteral ) {
			try {
				ShadowValue result = TACInterpreter.evaluate(this);
				setUpdatedValue(new TACLiteral(this, result));
				changed = true;
			}
			catch(ShadowException e)
			{} //do nothing, failed to evaluate
		}
		
		currentlyUpdating.remove(this);
		return changed;	
	}
	
	@Override
	public TACOperand getValue()
	{
		if( getUpdatedValue() == null )
			return this;
		else
			return getUpdatedValue();
	}
}
