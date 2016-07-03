package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class ShadowArray extends ShadowValue
{
	private ArrayType type;
	private ShadowReference[] data;
	private int[] lengths;
	private int offset;
	public ShadowArray(ArrayType type, int... lengths) throws ShadowException
	{
		if (type.getDimensions() != lengths.length)
			throw new ShadowException("Cannot create array with " +
					lengths.length + " dimensions from " + lengths.length +
					" lengths");
		int length = 1;
		for (int i = 0; i < lengths.length; i++)
			length *= lengths[i];
		ShadowReference[] data = new ShadowReference[length];
		ModifiedType baseType = new SimpleModifiedType(type.getBaseType());
		for (int i = 0; i < data.length; i++)
			data[i] = new ShadowReference(baseType);
		this.type = type;
		this.data = data;
		this.lengths = lengths;
		this.offset = 0;
	}
	@Override
	public ArrayType getType()
	{
		return this.type;
	}

	public int getLength(int dimension)
	{
		return this.lengths[dimension];
	}
	private ShadowReference index(int... indices) throws ShadowException
	{
		if (indices.length != this.lengths.length)
			throw new ShadowException("Cannot index into array with " +
					lengths.length + " dimensions with " + indices.length +
					" indices");
		int index = 0;
		for (int i = 0; i < indices.length; i++)
		{
			if (indices[i] < 0 || indices[i] >= this.lengths[i])
				throw new ShadowException("Indices out of bounds");
			index = index * lengths[i] + indices[i];
		}
		return data[index + this.offset];
	}

	@Override
	public ShadowValue copy() throws ShadowException
	{
		ShadowArray copy = new ShadowArray(getType());
		int[] indices = new int[getType().getDimensions()];
		while (indices[0] != getLength(0))
		{
			copy.index(indices).setValue(index(indices).getValue());
			int i;
			for (i = indices.length - 1; i > 0 &&
					indices[i - 1] != getLength(i - 1); i--)
				indices[i] = 0;
			indices[i]++;
		}
		return copy;
	}
	@Override
	public ShadowValue cast(Type type) throws ShadowException
	{
		return this;
	}
}
