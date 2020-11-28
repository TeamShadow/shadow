package shadow.tac;

import java.io.StringWriter;

import shadow.interpreter.ConstantFieldInterpreter.FieldKey;
import shadow.interpreter.ShadowValue;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
import shadow.typecheck.type.Type;

/**
 * A simple node representing a compile-time constant field and containing its
 * {@link shadow.interpreter.ShadowValue} (as computed by
 * {@link shadow.interpreter.ASTInterpreter}).
 */
public class TACConstant
{
	private final FieldKey fieldKey;
	private final VariableDeclaratorContext context;
	
	public TACConstant(FieldKey fieldKey, VariableDeclaratorContext context)
	{
		this.fieldKey = fieldKey;
		this.context = context;
	}

	public FieldKey getFieldKey() {
		return fieldKey;
	}

	public ShadowValue getInterpretedValue() {
		return context.getInterpretedValue();
	}

	@Override
	public String toString()
	{
		StringWriter writer = new StringWriter();
		writer.write(context.getModifiers().toString());
		writer.write(context.getType().toString(Type.TYPE_PARAMETERS));
		writer.write(' ');
		writer.write(fieldKey.fieldName);
		writer.write(" = ");
		writer.write(getInterpretedValue().toLiteral());
		return writer.toString();		
	}

}
