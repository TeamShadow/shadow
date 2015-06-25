package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import shadow.interpreter.ShadowInteger;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
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
		super(node);
		array = check(reference, reference);
		Iterator<TACOperand> iter = ops.iterator();
		indices = new ArrayList<TACOperand>(ops.size());
		
		if( check )
		{	
			TACLabelRef throwLabel = new TACLabelRef(this);
			
			for( int i = 0; i < ops.size(); i++ )
			{				
				TACOperand length = check(iter.next(), new SimpleModifiedType(Type.INT));
				//by casting to uint (essentially free in LLVM), we don't need to do a negative check
				TACOperand unsignedLength = new TACCast(this, new SimpleModifiedType(Type.UINT), length);
				indices.add(length);
							
				TACOperand bound = new TACLength(this, array, i);
				TACOperand unsignedBound = new TACCast(this, new SimpleModifiedType(Type.UINT), bound); 
				TACOperand condition = new TACBinary(this, unsignedLength, Type.UINT.getMatchingMethod("compare", new SequenceType(Type.UINT)), '<', unsignedBound, true);
				
				TACLabelRef computeOffset = new TACLabelRef(this);
				new TACBranch(this, condition, computeOffset, throwLabel);
				computeOffset.new TACLabel(this);				
				
				if( i > 0 )
				{	
					total = new TACBinary(this, total, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', bound);
					total = new TACBinary(this, total, Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', length);
				}
				else									
					total = length;					
			}
			
			TACLabelRef done = new TACLabelRef(this);			
			new TACBranch(this, done);
			
			throwLabel.new TACLabel(this);
			
			TACOperand object = new TACNewObject(this, Type.INDEX_OUT_OF_BOUNDS_EXCEPTION);
			List<TACOperand> params = new ArrayList<TACOperand>();
			params.add(object);			
			MethodSignature signature;			
			
			//TODO: move to method to reduce code bloat?
			if( ops.size() > 1 )
			{			
				ArrayType intArray = new ArrayType(Type.INT);
				TACClass intClass = new TACClass(this, Type.INT);
				TACOperand bounds  = new TACNewArray(this, intArray, intClass.getClassData(), new TACLiteral(this, new ShadowInteger(ops.size())));				iter = ops.iterator();
				
				//fill the array with the bad indices
				for( int i = 0; i < ops.size(); i++ )
				{
					TACArrayRef arrayRef = new TACArrayRef(this, bounds, new TACLiteral(this, new ShadowInteger(i)), false);
					new TACStore(this, arrayRef, iter.next());
				}
				
				signature = Type.INDEX_OUT_OF_BOUNDS_EXCEPTION.getMatchingMethod("create", new SequenceType(intArray));
				params.add( bounds );				
			}
			else
			{
				signature = Type.INDEX_OUT_OF_BOUNDS_EXCEPTION.getMatchingMethod("create", new SequenceType(Type.INT));
				params.add(ops.iterator().next());				
			}
						
			TACMethodRef methodRef = new TACMethodRef(this, signature);
			TACBlock block = getBuilder().getBlock();
			TACCall exception = new TACCall(this, block, methodRef, params);
						
			new TACThrow(this, block, exception);						
			
			done.new TACLabel(this);	//done label	
			new TACNodeRef(this, total);
		}
		else
		{		
			total = check(iter.next(),
					new SimpleModifiedType(Type.INT));		
			indices.add(total);
			
			while (iter.hasNext())
			{
				total = new TACBinary(this, total, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', new TACLength(this,
						array, indices.size()));
				TACOperand next = check(iter.next(),
						new SimpleModifiedType(Type.INT));
				indices.add(next);
				total = new TACBinary(this, total, Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', next);
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
	public Modifiers getModifiers() {
		if( ((ArrayType)array.getType()).isNullable() )
			return new Modifiers(Modifiers.NULLABLE);
		return super.getModifiers();
	}

	@Override
	public Type getType()
	{
		return ((ArrayType)array.getType()).getBaseType();
	}
	@Override
	public int getNumOperands()
	{
		return 2 + indices.size();
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return array;
		if (num == 1)
			return total;
		return indices.get(num - 2);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(array.toString()).append('[');
		for (TACOperand node : indices)
			sb.append(node).append(", ");
		return sb.delete(sb.length() - 2, sb.length()).append(']').toString();
	}
}
