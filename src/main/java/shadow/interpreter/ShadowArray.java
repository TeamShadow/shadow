package shadow.interpreter;

import shadow.ShadowException;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class ShadowArray extends ShadowValue
{
	private ArrayType type;
	private ShadowReference[] data;	
	public ShadowArray(ArrayType type, int length) throws ShadowException
	{
		ShadowReference[] data = new ShadowReference[length];
		ModifiedType baseType = new SimpleModifiedType(type.getBaseType());
		for (int i = 0; i < data.length; i++)
			data[i] = new ShadowReference(baseType);
		this.type = type;
		this.data = data;
	}
	@Override
	public ArrayType getType()
	{
		return this.type;
	}
	
	public ShadowReference get(int index) {
		return data[index];
	}
	
	public void set(int index, ShadowReference reference) {
		data[index] = reference;
	}

	public int getLength()
	{
		return data.length;
	}

	@Override
	public ShadowValue copy() throws ShadowException
	{
		ShadowArray copy = new ShadowArray(getType(), data.length);
		for ( int i = 0; i < data.length; ++i )
			copy.data[i] = data[i].copy();
		return copy;
	}

	@Override
	public String toLiteral() {
		throw new UnsupportedOperationException("Cannot currently create array literals");
	}

	@Override
	public ShadowValue cast(Type type) throws ShadowException
	{
		return this;
	}
}
