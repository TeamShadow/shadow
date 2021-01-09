package shadow.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import shadow.ShadowException;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;
import shadow.interpreter.InterpreterException.Error;

public class ShadowObject extends ShadowValue {
	private Type type;
	private Map<String, ShadowReference> fields;
	public ShadowObject(Type type) throws ShadowException {
		if (type.isPrimitive())
			throw new InterpreterException(Error.INVALID_TYPE, "Cannot create an object with a " +
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
	public Type getType() {
		return type;
	}

	public ShadowReference field(String name) throws ShadowException {
		ShadowReference field = fields.get(name);
		if (field != null)
			return field;
		throw new InterpreterException(Error.UNDEFINED_SYMBOL, "Type " + getType() + " does not contain " +
				"field " + name);
	}

	@Override
	public ShadowValue copy() throws ShadowException {
		ShadowObject copy = new ShadowObject(getType());
		for (String field : getType().getFields().keySet())
			copy.field(field).setValue(field(field).getValue());
		return copy;
	}

	@Override
	public String toLiteral() {
		throw new UnsupportedOperationException("Cannot convert an arbitrary object to a literal");
	}

	@Override
	public ShadowValue cast(Type type) throws ShadowException {
		return new ShadowObject((ClassType)type);
	}
}
