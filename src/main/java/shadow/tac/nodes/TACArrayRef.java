package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import shadow.interpreter.ShadowInteger;
import shadow.tac.TACMethod;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of array subscript
 * Example: [x, y, z] 
 * @author Jacob Young
 * @author Barry Wittman
 */
public class TACArrayRef extends TACReference
{
	private TACOperand array, total;
	private List<TACOperand> indices;	
	
	public TACArrayRef(TACNode node, TACOperand reference, TACOperand... ops)
	{
		this(node, reference, Arrays.asList(ops), true);
	}
	
	//single op, can specify not to check for optimization
	public TACArrayRef(TACNode node, TACOperand reference, TACOperand op, boolean check)
	{
		this(node, reference, Arrays.asList(op), check);
	}
	
	public TACArrayRef(TACNode node, TACOperand reference,
			Collection<TACOperand> ops)
	{
		this(node, reference, ops, true);
	}
	
	public TACArrayRef(TACNode node, TACOperand reference,
			Collection<TACOperand> ops, boolean check)
	{
		array = reference;
		Iterator<TACOperand> iter = ops.iterator();
		indices = new ArrayList<TACOperand>(ops.size());
		
		if( check ) {
			TACMethod method = node.getMethod();
			TACLabelRef throwLabel = new TACLabelRef(method);
			
			for( int i = 0; i < ops.size(); i++ ) {
				TACOperand length = iter.next();
				//by casting to uint (essentially free in LLVM), we don't need to do a negative check
				TACOperand unsignedLength = TACCast.cast(node, new SimpleModifiedType(Type.UINT), length);
				indices.add(length);
							
				TACOperand bound = new TACLength(node, array, i);
				TACOperand unsignedBound = TACCast.cast(node, new SimpleModifiedType(Type.UINT), bound); 
				TACOperand condition = new TACBinary(node, unsignedLength, Type.UINT.getMatchingMethod("compare", new SequenceType(Type.UINT)), '<', unsignedBound, true);
				
				TACLabelRef computeOffset = new TACLabelRef(method);
				new TACBranch(node, condition, computeOffset, throwLabel);
				computeOffset.new TACLabel(node);				
				
				if( i > 0 ) {	
					total = new TACBinary(node, total, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', bound);
					total = new TACBinary(node, total, Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', length);
				}
				else									
					total = length;					
			}
			
			TACLabelRef done = new TACLabelRef(method);			
			new TACBranch(node, done);
			
			throwLabel.new TACLabel(node);
			
			TACOperand object = new TACNewObject(node, Type.INDEX_OUT_OF_BOUNDS_EXCEPTION);
			List<TACOperand> params = new ArrayList<TACOperand>();
			params.add(object);			
			MethodSignature signature;			
			
			//TODO: move to method to reduce code bloat?
			if( ops.size() > 1 ) {			
				ArrayType intArray = new ArrayType(Type.INT);
				TACClass intClass = new TACClass(node, Type.INT);
				TACOperand bounds  = new TACNewArray(node, intArray, intClass.getClassData(), new TACLiteral(node, new ShadowInteger(ops.size())));
				iter = ops.iterator();
				
				//fill the array with the bad indices
				for( int i = 0; i < ops.size(); i++ )
				{
					TACArrayRef arrayRef = new TACArrayRef(node, bounds, new TACLiteral(node, new ShadowInteger(i)), false);
					new TACStore(node, arrayRef, iter.next());
				}
				
				signature = Type.INDEX_OUT_OF_BOUNDS_EXCEPTION.getMatchingMethod("create", new SequenceType(intArray));
				params.add( bounds );				
			}
			else {
				signature = Type.INDEX_OUT_OF_BOUNDS_EXCEPTION.getMatchingMethod("create", new SequenceType(Type.INT));
				params.add(ops.iterator().next());				
			}						
						
			TACCall exception = new TACCall(node, new TACMethodRef(node, signature), params);						
			new TACThrow(node, exception);						
			
			done.new TACLabel(node);	//done label	
			new TACNodeRef(node, total);
		}
		else {		
			total = iter.next();
			indices.add(total);
			
			while (iter.hasNext()) {
				total = new TACBinary(node, total, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', new TACLength(node,
						array, indices.size()));
				TACOperand next = iter.next();
				indices.add(next);
				total = new TACBinary(node, total, Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', next);
			}
		}
	}

	public TACOperand getArray()
	{
		return array;
	}
	public int getNumIndices()
	{
		return indices.size();
	}
	public TACOperand getIndex(int index)
	{
		return indices.get(index);
	}
	public TACOperand getTotal()
	{
		return total;
	}
	
	@Override
	public Modifiers getModifiers()
	{
		if( ((ArrayType)array.getType()).isNullable() )
			return new Modifiers(Modifiers.NULLABLE);
		return new Modifiers();
	}

	@Override
	public Type getType()
	{
		return ((ArrayType)array.getType()).getBaseType();
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(array.toString()).append('[');
		for (TACOperand node : indices)
			sb.append(node).append(", ");
		return sb.delete(sb.length() - 2, sb.length()).append(']').toString();
	}

	@Override
	public void setType(Type type)
	{
		throw new UnsupportedOperationException();		
	}
}
