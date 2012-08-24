package shadow.output.c;

import java.util.Map;

import shadow.output.TabbedLineWriter;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACVariableRef;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class COutput extends TACAbstractVisitor
{
	private TabbedLineWriter meta, c;
	private int tempCounter;
	@Override
	public void startFile(TACModule module) throws ShadowException
	{
		meta = new TabbedLineWriter(System.out);
		c = new TabbedLineWriter(System.out);

		meta.write("#ifndef _META");
		meta.write("#define _META");
		meta.write();

		meta.write("typedef unsigned char shadow_boolean_t;");
		meta.write("typedef char shadow_byte_t;");
		meta.write("typedef unsigned char shadow_ubyte_t;");
		meta.write("typedef short shadow_short_t;");
		meta.write("typedef unsigned short shadow_ushort_t;");
		meta.write("typedef int shadow_int_t;");
		meta.write("typedef unsigned int shadow_uint_t;");
		meta.write("typedef int shadow_code_t;");
		meta.write("typedef long long shadow_long_t;");
		meta.write("typedef unsigned long long shadow_ulong_t;");
		meta.write("typedef float shadow_float_t;");
		meta.write("typedef double shadow_double_t;");
		meta.write();

		c.write("/// " + module.getFullName());
		c.write();

		module.getClassType().addReferencedType(Type.CLASS);
		module.getClassType().addReferencedType(Type.OBJECT);
		for (Type type : module.getClassType().getAllReferencedTypes())
			if (type instanceof ClassType)
		{
			c.write("%\"" + type.getFullName() + "!!methods\" = type {}");
			StringBuilder sb = new StringBuilder("%\"");
			sb.append(type.getFullName());
			sb.append("\" = type { %\"");
			sb.append(type.getFullName());
			sb.append("!!methods\"*");
			for (Map.Entry<String, ModifiedType> fields : ((ClassType)type).
					getFieldList())
				sb.append(", ").append(typeToString(fields.getValue().
						getType()));
			c.write(sb.append(" }").toString());
			c.write();
		}
		/*List<MethodSignature> methods = module.getClassType().getMethodList();
		for (MethodSignature method : methods)
			llvm.write("declare " + methodToString(method));*/
		c.write("%\"" + module.getFullName() + "!!methods\" = type {}");
		StringBuilder sb = new StringBuilder("%\"");
		sb.append(module.getFullName());
		sb.append("\" = type { %\"");
		sb.append(module.getFullName());
		sb.append("!!methods\"*");
		for (Map.Entry<String, ModifiedType> fields : module.getClassType().
				getFieldList())
			sb.append(", ").append(typeToString(fields.getValue().getType()));
		c.write(sb.append(" }").toString());
//		llvm.write("%\"" + module.getType().getFullName() + "\" = type { %\"" +
//				module.getType().getFullName() + "!!methods\"* }");
		c.write();
	}

	@Override
	public void endFile(TACModule module) throws ShadowException
	{
		meta.write("#endif");
		meta.write();
	}

	@Override
	public void startMethod(TACMethod method) throws ShadowException
	{
		c.write("define " + methodToString(method) + " {");
		c.indent();
		for (TACVariable local : method.getLocals())
			c.write('%' + local.getName() + " = alloca " +
					typeToString(local.getType()));
		int paramIndex = 0;
		for (TACVariable local : method.getParameters())
			c.write("store " + typeToString(local.getType()) + " %" +
					paramIndex++ + ", " + typeToString(local.getType()) +
					"* %" + local.getName());
		tempCounter = method.getParameterCount() + 1;
	}

	@Override
	public void endMethod(TACMethod method) throws ShadowException
	{
		c.outdent();
		c.write('}');
		c.write();
	}

	@Override
	public void visit(TACVariableRef node) throws ShadowException
	{
		node.setName("%" + node.getName());
	}
	@Override
	public void visit(TACFieldRef node) throws ShadowException
	{
		node.setName("%" + tempCounter++);
		c.write(node.getName() + " = getelementptr inbounds " + typeAndName(
				node.getPrefix()) + ", i64 0, i32 " + (node.getIndex() + 1));
	}

	@Override
	public void visit(TACLoad node) throws ShadowException
	{
		node.setName("%" + tempCounter++);
		c.write(node.getName() + " = load " +
				typeAndName(node.getReference(), true));
	}
	@Override
	public void visit(TACStore node) throws ShadowException
	{
		c.write("store " + typeAndName(node.getValue()) + ", " +
				typeAndName(node.getReference(), true));
	}

	@Override
	public void visit(TACReturn node) throws ShadowException
	{
		if (node.hasReturnValue())
			c.write("ret " + node.getReturnValue().getName());
		else
			c.write("ret void");
	}

	@SuppressWarnings("unused")
	private static String methodToString(MethodSignature method)
	{
		return methodToString(method.getSymbol(), method.getMethodType());
	}
	private static String methodToString(TACMethod method)
	{
		return methodToString(method.getName(), method.getType());
	}
	private static String methodToString(String name, MethodType type)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(typeToString(type.getReturnTypes()));
		sb.append(" @\"");
		sb.append(type.getOuter().getFullName());
		sb.append('!');
		sb.append(name);
		for (ModifiedType paramType : type.getParameterTypes())
			sb.append('%').append(paramType.getType());
		sb.append("\"(");
		if (!ModifierSet.isStatic(type.getModifiers()))
			sb.append(typeToString(type.getOuter())).append(", ");
		for (ModifiedType paramType : type.getParameterTypes())
			sb.append(typeToString(paramType.getType())).append(", ");
		trimComma(sb);
		sb.append(')');
		return sb.toString();
	}

	private static String typeAndName(TACOperand node)
	{
		return typeAndName(node, false);
	}
	private static String typeAndName(TACOperand node, boolean reference)
	{
		StringBuilder builder = new StringBuilder(typeToString(node.getType()));
		if (reference)
			builder.append('*');
		return builder.append(' ').append(node.getName()).toString();
	}
	private static String typeToString(Type type)
	{
		if (type instanceof SequenceType)
		{
			SequenceType sequence = (SequenceType)type;
			switch (sequence.size())
			{
				case 0:
					return "void";
				case 1:
					return typeToString(sequence.getType(0));
				default:
					StringBuilder sb = new StringBuilder("{ ");
					sb.append(typeToString(sequence.getType(0)));
					for (ModifiedType aType : sequence)
						sb.append(typeToString(aType.getType())).append(", ");
					return sb.replace(sb.length() - 2, sb.length(), " }").
							toString();
			}
		}
		if (type.isPrimitive())
			return '%' + type.getTypeName();
		if (type instanceof ClassType)
			return "%\"" + type.getFullName() + "\"*";
		throw new IllegalArgumentException("Unknown type.");
	}

	private static void trimComma(StringBuilder sb)
	{
		int start = sb.length();
		while ((start -= 2) >= 0 && sb.charAt(start) == ',' &&
				sb.charAt(start + 1) == ' ')
			sb.delete(start, start + 2);
	}
}
