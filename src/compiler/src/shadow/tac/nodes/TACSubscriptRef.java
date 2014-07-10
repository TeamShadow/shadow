package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.IndexType;
import shadow.typecheck.type.ModifiedType;

/** 
 * TAC representation of a non-array subscript
 * Needed because a subscript could be a store as well as a load
 * Example: [x]
 * @author Barry Wittman
 */

public class TACSubscriptRef extends TACReference
{
	private TACBlock blockRef;
	private TACOperand prefix;
	private TACOperand index;
	private IndexType type;
	
	
	public TACSubscriptRef(TACBlock block, TACOperand indexPrefix,
			TACOperand indexSubscript, IndexType indexType)
	{
		this(null, block, indexPrefix, indexSubscript, indexType);
	}
	public TACSubscriptRef(TACNode node, TACBlock block, TACOperand indexPrefix,
			TACOperand indexSubscript, IndexType indexType)
	{
		super(node);
		blockRef = block;
		prefix = check(indexPrefix, indexPrefix);		
		type = indexType;
		index = check( indexSubscript, indexSubscript );
		
		/*
		if (!(prefix.getType() instanceof ClassType))
			throw new IllegalArgumentException("propertyPrefix is not a " +
					"class type");
		*/
	}

	public TACBlock getBlock()
	{
		return blockRef;
	}
	public TACOperand getPrefix()
	{
		return prefix;
	}
	public TACOperand getIndex()
	{
		return index;
	}

	@Override
	public IndexType getType()
	{
		return type;
	}
	@Override
	public ModifiedType getGetType()
	{
		return type.getReadType();
	}
	@Override
	public ModifiedType getSetType()
	{
		return type.getStoreType();
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
			return prefix;
		else if( num == 1 )
			return index;
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
		return prefix.toString() + "[" + index.toString() + "]";
	}
}



