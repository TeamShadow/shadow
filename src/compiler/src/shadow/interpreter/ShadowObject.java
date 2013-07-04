package shadow.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public class ShadowObject extends ShadowValue
{
	private ClassType type;
	private Map<String, ShadowReference> fields;
	public ShadowObject(ClassType type) throws ShadowException
	{
		if (type.isPrimitive())
			throw new ShadowException("Cannot create an object with a " +
					"primitive type");
		Map<String, ShadowReference> fields =
				new HashMap<String, ShadowReference>(type.getFields().size());
		for (Entry<String, ? extends ModifiedType> field :
				type.getFields().entrySet())
			fields.put(field.getKey(), new ShadowReference(field.getValue()));
		this.type = type;
		this.fields = fields;
	}
	@Override
	public ClassType getType()
	{
		return type;
	}

	public ShadowReference field(String name) throws ShadowException
	{
		ShadowReference field = fields.get(name);
		if (field != null)
			return field;
		throw new ShadowException("type " + getType() + " does not contain " +
				"field " + name);
	}

	@Override
	public ShadowValue copy() throws ShadowException
	{
		ShadowObject copy = new ShadowObject(getType());
		for (String field : getType().getFields().keySet())
			copy.field(field).setValue(field(field).getValue());
		return copy;
	}
	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		return this;
	}
}
