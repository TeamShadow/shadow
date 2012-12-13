package shadow.output.llvm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.output.AbstractOutput;
import shadow.output.TabbedLineWriter;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLength;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACPropertyRef;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceRef;
import shadow.tac.nodes.TACSingletonRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariableRef;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public class LLVMOutput extends AbstractOutput
{
	private Process process = null;
	private int tempCounter = 0, labelCounter = 0;
	private List<String> stringLiterals = new LinkedList<String>();
	public LLVMOutput(File file) throws ShadowException
	{
		super(new File(file.getParent(),
				file.getName().replace(".shadow", ".ll")));
	}
	public LLVMOutput(boolean mode) throws ShadowException
	{
		if (!mode)
		{
			try
			{
				process = new ProcessBuilder("opt", "-S", "-O3").start();
			}
			catch (IOException ex)
			{
				throw new ShadowException(ex.getLocalizedMessage());
			}
			writer = new TabbedLineWriter(process.getOutputStream());
		}
		writer.setLineNumbers(mode);
	}

	private String temp(int offset)
	{
		return '%' + Integer.toString(tempCounter - offset - 1);
	}
	private String nextTemp()
	{
		return '%' + Integer.toString(tempCounter++);
	}
	private String nextTemp(TACOperand node)
	{
		String name = nextTemp();
		node.setName(name);
		return name;
	}

	private String nextLabel()
	{
		return "_label" + labelCounter++;
	}
	private String nextLabel(TACOperand node)
	{
		String name = nextLabel();
		node.setName(name);
		return name;
	}

	private String nextString(String value)
	{
		String name = "@_string" + stringLiterals.size();
		stringLiterals.add(value);
		return name;
	}
	private String nextString(TACOperand node, String value)
	{
		String name = nextString(value);
		node.setName(name);
		return name;
	}

	@Override
	public void startFile(TACModule module) throws ShadowException
	{
		writer.write("; " + module.getQualifiedName());
		writer.write();

		writer.write("%boolean = type i1");
		writer.write("%byte = type i8");
		writer.write("%ubyte = type i8");
		writer.write("%short = type i16");
		writer.write("%ushort = type i16");
		writer.write("%int = type i32");
		writer.write("%uint = type i32");
		writer.write("%code = type i32");
		writer.write("%long = type i64");
		writer.write("%ulong = type i64");
		writer.write("%float = type float");
		writer.write("%double = type double");
		writer.write();

		writer.write("declare noalias i8* @malloc(i32)");
		writer.write("declare noalias i8* @calloc(i32, i32)");
		writer.write();

		final Type INTERFACE = Type.CLASS.getPackage().getType("Interface");

		Type interfaceArray = new ArrayType(INTERFACE);

		if (module.getType() instanceof ClassType)
		{
			ClassType classType = module.getClassType();
			writer.write('%' + raw(classType, "_Mclass") + " = type { %" +
					raw(Type.CLASS) + methodList(classType,
					classType.getOrderedMethods(), false) + " }");
			StringBuilder sb = new StringBuilder().append('%').
					append(raw(classType)).append(" = type { %").
					append(raw(classType, "_Mclass")).append('*');
			for (Map.Entry<String, ModifiedType> field :
					classType.getFieldList())
				sb.append(", ").append(type(field.getValue()));
			writer.write(sb.append(" }").toString());
		}
		for (ClassInterfaceBaseType type : module.getReferences())
			if (type instanceof ClassType && !(type instanceof ArrayType) &&
					!type.equals(module.getType()))
		{
			writer.write('%' + raw(type, "_Mclass") + " = type { %" +
					raw(Type.CLASS) + methodList(type,
					((ClassType)type).getOrderedMethods(), false) + " }");
			StringBuilder sb = new StringBuilder().append('%').
					append(raw(type)).append(" = type { %").
					append(raw(type, "_Mclass")).append('*');
			if (type.equals(Type.CLASS))
				sb.append(", ").append(type(interfaceArray)).append(", ").
						append(type(Type.STRING)).append(", ").
						append(type(Type.CLASS)).append(", ").
						append(type(Type.INT)).append(", ").
						append(type(Type.INT));
			else if (type.equals(Type.STRING))
				sb.append(", ").append(type(new ArrayType(Type.UBYTE)));
			writer.write(sb.append(" }").toString());
		}
		writer.write();

		if (module.getType() instanceof ClassType)
		{
			ClassType type = module.getClassType();
			List<MethodSignature> methods = type.getOrderedMethods();
			int typeSize = type.getWidth();
			Object size = typeSize, parent = null;
			Type parentType = type.getExtendType();
			if (parentType != null)
				parent = "getelementptr inbounds (%" + raw(parentType,
						"_Mclass") + "* @" + raw(parentType, "_Mclass") +
						", i32 0, i32 0)";
			if (typeSize == -1)
				typeSize = Integer.parseInt(
						System.getProperty("sun.arch.data.model", "64")) >> 3;
			if (!type.isPrimitive())
				size = sizeof(type(type));
			writer.write('@' + raw(type, "_Mclass") +
					" = global %" + raw(type, "_Mclass") + " { %" +
					raw(Type.CLASS) + " { %" + raw(Type.CLASS, "_Mclass") +
					"* @" + raw(Type.CLASS, "_Mclass") + ", " +
					type(interfaceArray) + " zeroinitializer, " +
					type(Type.STRING) + ' ' +
					nextString(type.getQualifiedName()) + ", " +
					type(Type.CLASS) + ' ' + parent + ", " +
					type(Type.INT) + ' ' + size + ", " +
					type(Type.INT) + ' ' + typeSize + " }" +
					methodList(type, methods, true) + " }");
			if (type instanceof SingletonType)
				writer.write('@' + raw(type, "_Minstance") + " = global " +
						type(type) + " null");
		}

		for (ClassInterfaceBaseType type : module.getReferences())
		{
			if (type instanceof ClassType && !(type instanceof ArrayType) &&
					!type.equals(module.getType()))
			{
				writer.write('@' + raw(type, "_Mclass") +
						" = external global %" + raw(type, "_Mclass"));
				if (type instanceof SingletonType)
					writer.write('@' + raw(type, "_Minstance") +
							" = external global " + type(type));
			}
		}
		writer.write();

//		for (Type type : module.getClassType().getAllReferencedTypes())
//			if (type instanceof ClassInterfaceBaseType && !type.isPrimitive())
//				defineClass((ClassInterfaceBaseType)type, false);
//		defineClass(module.getType(), true);

//		for (ClassInterfaceBaseType type : module.getReferences())
//			if (!type.isPrimitive())
//				declareType(type, type.equals(module.getType()));
//		writer.write();

//		for (ClassInterfaceBaseType type : module.getReferences())
//			if (!type.isPrimitive())
//				declareClass(type, type.equals(module.getType()));
//		writer.write();

//		for (ClassInterfaceBaseType type : module.getReferences())
//			if (!type.isPrimitive())
//				defineClass(type, type.equals(module.getType()));
//		writer.write();

//		writer.write("define " + type(Type.CLASS) + " @\"" +
//				module.getType().getMangledName() + "$$getClass\"(" +
//				type(module.getType()) + ") {");
//		writer.indent();
//		writer.write("ret " + type(Type.CLASS) + " @\"" +
//				module.getType().getMangledName() + "_Mclass\"");
//		writer.outdent();
//		writer.write('}');
//		writer.write();

//		writer.write("define " + type(module.getType()) + " @\"" +
//				module.getType().getMangledName() + "_Mcreate\"() {");
//		writer.indent();
//		writer.write("%1 = call i8* @malloc(i64 " +
//				sizeof(type(module.getType())) + ')');
//		writer.write("%2 = bitcast i8* %1 to " + type(module.getType()));
//		writer.write("ret " + type(module.getType()) + " %2");
//		writer.outdent();
//		writer.write('}');
//		writer.write();
	}

//	private void declareClass(ClassInterfaceBaseType type, boolean current)
//			throws ShadowException
//	{
//		if (type instanceof ArrayType)
//			return;
//		Type INTERFACE = Type.CLASS.getPackage().getType("Interface");
//		String typeName = type.getMangledName();
//		String methodsType = '\"' + typeName + "_Imethods\"";
//		StringBuilder sb = new StringBuilder("%\"").append(typeName)
//				.append("\" = type { %").append(methodsType).append('*');
//		if (type instanceof SingletonType)
//			if (current)
//				writer.write("@\"" + typeName + "_Minstance\" = global " +
//						type(type) + " null");
//			else
//				writer.write("@\"" + typeName + "_Minstance\" = external " +
//						"global " + type(type));
//		if (type instanceof ClassType)
//		{
//			List<MethodSignature> methods =
//					((ClassType)type).getOrderedMethods();
//			writer.write("%\"" + typeName + "_Imethods\" = type " +
//					methodList(type, methods, false));
//			if (current)
//				writer.write("@\"" + typeName + "_Imethods\" = constant %\"" +
//						typeName + "_Imethods\" " +
//						methodList(type, methods, true));
//			else
//				writer.write("@\"" + typeName + "_Imethods\" = external " +
//						"constant %\"" + typeName + "_Imethods\"");
//
//			if (type == Type.CLASS)
//				sb.append(", ").append(type(new ArrayType(INTERFACE,
//						Collections.singletonList(1)))).append(", ").
//						append(type(Type.STRING)).append(", ").
//						append(type(Type.CLASS)).append(", ").
//						append(type(Type.INT)).append(", ").
//						append(type(Type.INT));
//			else if (type == Type.STRING)
//				sb.append(", ").append(type(new ArrayType(
//						Type.UBYTE, Collections.singletonList(1))));
//			else
//				for (Map.Entry<String, ModifiedType> field :
//						((ClassType)type).getFieldList())
//					sb.append(", ").append(type(field.getValue()));
//		}
//
//		writer.write(sb.append(" }").toString());
//		if (!current)
//			for (List<MethodSignature> methodList : type.getMethodMap().
//					values())
//				for (MethodSignature method : methodList)
//					writer.write("declare " +
//							methodToString(new TACMethod(method)));
//		writer.write();
//	}
//
//	private void defineClass(ClassInterfaceBaseType type, boolean current)
//			throws ShadowException
//	{
//		if (type instanceof ArrayType)
//			return;
//		Type INTERFACE = Type.CLASS.getPackage().getType("Interface");
//		String typeName = type.getMangledName();
//		if (current)
//		{
//			Object parent = null, typeSize = 1, size = 1;
//			if (type instanceof ClassType)
//			{
//				ClassType parentType = ((ClassType)type).getExtendType();
//				if (parentType != null)
//					parent = "@\"" + parentType.getMangledName() + "_Mclass\"";
//			}
//			writer.write("@\"" + typeName + "_Mclass\" = constant %\"" +
//					Type.CLASS.getMangledName() + "\" { %\"" +
//					Type.CLASS.getMangledName() + "_Imethods\"* @\"" +
//					Type.CLASS.getMangledName() + "_Imethods\" " + ", { " +
//					type(INTERFACE) + "*, [1 x %int] } { " + type(INTERFACE) +
//					"* null, [1 x %int] [%int 0] }, " + type(Type.STRING) +
//					' ' + nextString(type.getQualifiedName()) + ", " +
//					type(Type.CLASS) + ' ' + parent + ", %int " + size +
//					", %int " + typeSize + " }");
//		}
//		else
//			writer.write("@\"" + typeName + "_Mclass\" = external constant " +
//					rawtype(Type.CLASS));
//	}

	private String methodList(Type type, Iterable<MethodSignature> methods,
			boolean mode) throws ShadowException
	{
		StringBuilder sb = new StringBuilder();
		for (MethodSignature methodSignature : methods)
		{
			TACMethod method = new TACMethod(methodSignature);
			sb.append(", ").append(type(method, true));
			if (mode)
				sb.append(' ').append(name(method));
		}
		return sb.toString();
	}

	@Override
	public void endFile(TACModule module) throws ShadowException
	{
		for (ClassInterfaceBaseType type : module.getReferences())
			if (type instanceof ClassType && !(type instanceof ArrayType) &&
					!type.equals(module.getType()) && !type.isPrimitive())
		{
			for (List<MethodSignature> methodList : type.getMethodMap().
					values())
				for (MethodSignature method : methodList)
					writer.write("declare " +
							methodToString(new TACMethod(method)));
			writer.write();
		}
		int stringIndex = 0;
		for (String literal : stringLiterals)
		{
			byte[] data = null;
			try
			{
				data = literal.getBytes("UTF-8");
			}
			catch (UnsupportedEncodingException ex)
			{
				System.err.println(ex.getLocalizedMessage());
				System.exit(1);
			}
			StringBuilder sb = new StringBuilder(data.length);
			for (byte b : data)
				if (b != '\"' && b >= 0x20 && b < 0x7f)
					sb.appendCodePoint(b);
				else
					sb.append('\\').append(Character.forDigit(b >>> 4, 16)).
							append(Character.forDigit(b & 0xf, 16));
			writer.write("@_array" + stringIndex + " = private unnamed_addr " +
					"constant [" + data.length + " x " + type(Type.UBYTE) +
					"] c\"" + sb + '\"');
			writer.write("@_string" + stringIndex + " = private unnamed_addr " +
					"constant %" + raw(Type.STRING) + " { %" + raw(Type.STRING,
					"_Mclass") + "* @" + raw(Type.STRING, "_Mclass") + ", " +
					type(new ArrayType(Type.UBYTE)) + " { " + type(Type.UBYTE) +
					"* getelementptr inbounds ([" +
					data.length + " x " + type(Type.UBYTE) + "]* @_array" +
					stringIndex + ", i32 0, i32 0), [1 x %int] [%int " +
					data.length + "] } }");
			stringIndex++;
		}

		if (process != null)
			try
			{
				String line;
				BufferedReader reader;
				process.getOutputStream().close();

				reader = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				while ((line = reader.readLine()) != null)
					System.out.println(line);
				reader.close();

				reader = new BufferedReader(new InputStreamReader(
						process.getErrorStream()));
				while ((line = reader.readLine()) != null)
					System.err.println(line);
				reader.close();

				try {
					process.waitFor();
				} catch (InterruptedException ex) { }
				int exit = process.exitValue();
				if (exit != 0)
					System.exit(exit);
			}
			catch (IOException ex)
			{
				throw new ShadowException(ex.getLocalizedMessage());
			}
	}

	@Override
	public void startMethod(TACMethod method) throws ShadowException
	{
		if (method.isNative())
		{
			writer.write("declare " + methodToString(method));
			writer.indent();
		}
		else
		{
			writer.write("define " + methodToString(method) + " {");
			writer.indent();
			for (TACVariable local : method.getLocals())
				writer.write('%' + name(local) + " = alloca " + type(local));
			int paramIndex = 0;
			for (TACVariable param : method.getParameters())
				writer.write("store " + type(param) + " %" + paramIndex++ +
						", " + type(param) + "* %" + name(param));
			tempCounter = ++paramIndex;
			if (method.isCreate() && !method.getPrefixType().isPrimitive())
			{
				ClassInterfaceBaseType type = method.getPrefixType();
				writer.write(nextTemp() + " = load " + type(type) + "* %this");
				writer.write(nextTemp() + " = getelementptr inbounds " +
						type(type) + ' ' + temp(1) + ", i32 0, i32 0");
				writer.write("store %" + raw(type, "_Mclass") + "* @" +
						raw(type, "_Mclass") + ", %" + raw(type, "_Mclass") +
						"** " + temp(0));
			}
		}
	}

	@Override
	public void endMethod(TACMethod method) throws ShadowException
	{
		writer.outdent();
		if (!method.isNative())
			writer.write('}');
		writer.write();
	}

	@Override
	public void visit(TACVariableRef node) throws ShadowException
	{
		node.setName('%' + name(node.getVariable()));
	}

	@Override
	public void visit(TACSingletonRef node) throws ShadowException
	{
		node.setName("@\"" + node.getType().getMangledName() + "_Minstance\"");
	}

	@Override
	public void visit(TACArrayRef node) throws ShadowException
	{
		writer.write(nextTemp() + " = extractvalue " + typeName(node.getArray()) +
				", 0");
		writer.write(nextTemp(node) + " = getelementptr inbounds " +
				type(node) + "* " + temp(1) + ", " + typeName(node.getTotal()));
	}

	@Override
	public void visit(TACFieldRef node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = getelementptr inbounds " +
				typeName(node.getPrefix()) + ", i32 0, i32 " +
				(node.getIndex() + 1));
	}

	@Override
	public void visit(TACLabelRef node) throws ShadowException
	{
		nextLabel(node);
	}

//	@Override
//	public void visit(TACSequenceRef node) throws ShadowException
//	{
//		TACSequenceRef seq = (TACSequenceRef)var;
//		for (int i = 0; i < seq.size(); i++)
//		{
//			String temp = nextTemp();
//			writer.write(temp + " = extractvalue " + typeName(
//					node.getValue()) + ", " + i);
//			writer.write("store " + type(seq.get(i)) + ' ' + temp + ", " +
//					typeName(seq.get(i), true));
//		}
//	}

	@Override
	public void visit(TACLiteral node) throws ShadowException
	{
		if (node.getValue() instanceof Character)
			node.setName(Integer.toString((int)(Character)node.getValue()));
		else if (node.getValue() instanceof String)
			nextString(node, (String)node.getValue());
	}

	@Override
	public void visit(TACClass node) throws ShadowException
	{
		node.setName("getelementptr (%" + raw(node.getClassType(), "_Mclass") +
				"* @" + raw(node.getClassType(), "_Mclass") +
				", i32 0, i32 0)");
	}

	@Override
	public void visit(TACSequence node) throws ShadowException
	{
		String type = type(node), last = "undef";
		for (int i = 0; i < node.size(); i++, last = temp(0))
			writer.write(nextTemp(node) + " = insertvalue " + type + ' ' + last +
					", " + typeName(node.get(i)) + ", " + i);
	}

	@Override
	public void visit(TACCast node) throws ShadowException
	{
		TACOperand operand = node.getOperand();
		Type srcType = operand.getType(), destType = node.getType();
		if (srcType == Type.NULL)
		{
			node.setName(name(node.getOperand()));
			return;
		}
		if (srcType instanceof ArrayType)
		{
			ArrayType arrayType = (ArrayType)srcType;
			int dimensions = arrayType.getDimensions();
			String lengthsType = "[" + dimensions + " x %int]";
			writer.write(nextTemp() + " = call i8* @malloc(i32 " +
					sizeof(type(Type.ARRAY)) + ')');
			writer.write(nextTemp() + " = bitcast i8* " + temp(1) + " to " +
					type(Type.ARRAY));
			writer.write(nextTemp() + " = extractvalue " + typeName(operand) +
					", 0");
			writer.write(nextTemp() + " = ptrtoint " + type(arrayType.
					getBaseType()) + "* " + temp(1) + " to %long");
			writer.write(nextTemp() + " = extractvalue " + typeName(operand) +
					", 1");
			writer.write(nextTemp() + " = call i8* @malloc(i32 " +
					sizeof(lengthsType + '*') + ')');
			writer.write(nextTemp() + " = bitcast i8* " + temp(1) + " to " +
					lengthsType + '*');
			writer.write("store " + lengthsType + ' ' + temp(2) + ", " +
					lengthsType + "* " + temp(0));
			writer.write(nextTemp() + " = getelementptr inbounds " +
					lengthsType + "* " + temp(1) + ", i32 0, i32 0");
			writer.write(nextTemp() + " = insertvalue { %int*, [1 x %int] } " +
					"{ %int* null, [1 x %int] [%int " + dimensions + "] " +
					"}, %int* " + temp(1) + ", 0");
			writer.write(nextTemp(node) + " = call " + type(Type.ARRAY) +
					" @\"" + Type.ARRAY.getMangledName() + "_Mcreate_R" +
					"_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1\"(" +
					type(Type.ARRAY) + ' ' + temp(8) + ", " + type(Type.CLASS) +
					" getelementptr inbounds (%" + raw(arrayType.getBaseType(),
					"_Mclass") + "* @" + raw(arrayType.getBaseType(),
					"_Mclass") + ", i32 0, i32 0), %long " + temp(6) + ", " +
					type(new ArrayType(Type.INT)) + ' ' + temp(1) + ')');
			srcType = Type.ARRAY;
		}
		if (destType.equals(srcType))
			return;
		int srcWidth = srcType.getWidth(), destWidth = destType.getWidth();
		String instruction;
		if (destWidth > srcWidth)
			instruction = destType.isSigned() ? "sext" : "zext";
		else if (destWidth < srcWidth)
			instruction = "trunc";
		else
			instruction = "bitcast";
		writer.write(nextTemp(node) + " = " + instruction + ' ' +
				typeName(operand) + " to " + type(destType));
	}

	@Override
	public void visit(TACNewObject node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = call noalias " + type(Type.OBJECT) +
				" @" + raw(Type.CLASS, "_McreateObject") + '(' +
				type(Type.CLASS) + " getelementptr (%" + raw(node.getType(),
				"_Mclass") + "* @" + raw(node.getType(), "_Mclass") +
				", i32 0, i32 0))");
		if (!node.getType().equals(Type.OBJECT))
			writer.write(nextTemp(node) + " = bitcast " + type(Type.OBJECT) +
					' ' + temp(1) + " to " + type(node.getType()));
	}

	@Override
	public void visit(TACNewArray node) throws ShadowException
	{
		String type = type(node.getType()),
				base = type(node.getType().getBaseType()) + '*';
		writer.write(nextTemp() + " = call noalias i8* @calloc(i32 " + name(node.
				getTotalSize()) + ", i32 " + sizeof(base) + ')');
		writer.write(nextTemp() + " = bitcast i8* " + temp(1) + " to " + base);
		writer.write(nextTemp() + " = insertvalue " + type + " undef, " + base +
				' ' + temp(1) + ", 0");
		for (int i = 0; i < node.getDimensions(); i++)
			writer.write(nextTemp(node) + " = insertvalue " + type + ' ' + temp(
					1) + ", " + typeName(node.getDimension(i)) + ", 1, " + i);
	}

	@Override
	public void visit(TACLength node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = extractvalue " +
				typeName(node.getArray()) + ", 1, " + node.getDimension());
	}

	@Override
	public void visit(TACUnary node) throws ShadowException
	{
		switch (node.getOperation())
		{
			case PLUS:
				visitUnary(node, "add", "0");
				break;
			case MINUS:
				visitUnary(node, "sub", "0");
				break;
			case COMPLEMENT:
			case NOT:
				visitUnary(node, "xor", "-1");
				break;
		}
	}
	private void visitUnary(TACUnary node, String instruction, String first)
			throws ShadowException
	{
		writer.write(nextTemp(node) + " = " + instruction + ' ' + type(node) +
				' ' + first + ", " + name(node.getOperand()));
	}

	@Override
	public void visit(TACBinary node) throws ShadowException
	{
		switch (node.getOperation())
		{
			case ADD:
				visitUnsignedOperation(node, "add");
				break;
			case SUBTRACT:
				visitUnsignedOperation(node, "sub");
				break;
			case MULTIPLY:
				visitUnsignedOperation(node, "mul");
				break;
			case DIVIDE:
				visitSignedOperation(node, "div");
				break;
			case MODULUS:
				visitSignedOperation(node, "rem");
				break;

			case OR:
			case BITWISE_OR:
				visitNormalOperation(node, "or");
				break;
			case XOR:
			case BITWISE_XOR:
				visitNormalOperation(node, "xor");
				break;
			case AND:
			case BITWISE_AND:
				visitNormalOperation(node, "and");
				break;

			case SHIFT_LEFT:
				visitNormalOperation(node, "shl");
				break;
			case SHIFT_RIGHT:
				visitShiftOperation(node, "shr");
				break;
			case ROTATE_LEFT:
				visitRotateLeft(node);
				break;
			case ROTATE_RIGHT:
				visitRotateRight(node);
				break;

			case EQUAL:
				visitEqualityOperation(node, "eq");
				break;
			case NOT_EQUAL:
				visitEqualityOperation(node, "ne");
				break;
			case LESS_THAN:
				visitRelationalOperation(node, "lt");
				break;
			case GREATER_THAN:
				visitRelationalOperation(node, "gt");
				break;
			case LESS_OR_EQUAL:
				visitRelationalOperation(node, "le");
				break;
			case GREATER_OR_EQUAL:
				visitRelationalOperation(node, "ge");
				break;

			case CONCATENATION:
				visitConcatenation(node);
				break;
		}
	}
	private void visitUnsignedOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "", "", "f", instruction);
	}
	private void visitSignedOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "s", "u", "f", instruction);
	}
	private void visitNormalOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "", "", "", instruction);
	}
	private void visitShiftOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "a", "l", "", instruction);
	}
	private void visitEqualityOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "icmp ", "icmp ", "fcmp o", instruction);
	}
	private void visitRelationalOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "icmp s", "icmp u", "fcmp o", instruction);
	}
	private void visitOperation(TACBinary node, String signed,
			String unsigned, String floatingPoint, String instruction)
			throws ShadowException
	{
		StringBuilder sb = new StringBuilder(nextTemp(node)).append(" = ");
		Type type = node.getFirst().getType();
		if (type.isIntegral() || !type.isPrimitive())
			if (type.isSigned())
				sb.append(signed);
			else
				sb.append(unsigned);
		else
			sb.append(floatingPoint);
		writer.write(sb.append(instruction).append(' ').append(type(type)).
				append(' ').append(name(node.getFirst())).append(", ").
				append(name(node.getSecond())).toString());
	}
	private void visitRotateLeft(TACBinary node) throws ShadowException
	{
		visitRotate(node, "shl", "lshr");
	}
	private void visitRotateRight(TACBinary node) throws ShadowException
	{
		visitRotate(node, "lshr", "shl");
	}
	private void visitRotate(TACBinary node, String dir, String otherDir)
			throws ShadowException
	{
		String type = type(node), _type_ = ' ' + type + ' ',
				first = name(node.getFirst()),
				second = name(node.getSecond());
		writer.write(nextTemp() + " = " + dir + _type_ + first + ", " + second);
		writer.write(nextTemp() + " = sub" + _type_ + "mul (" + type +
				" ptrtoint (" + type + "* getelementptr (" + type +
				"* null, i32 1) to " + type + ")," + _type_ + "8), " + second);
		writer.write(nextTemp() + " = " + otherDir + _type_ + first + ", " +
				temp(1));
		writer.write(nextTemp(node) + " = or" + _type_ + temp(1) + ", " +
				temp(3));
	}
	private void visitConcatenation(TACBinary node) throws ShadowException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(TACLoad node) throws ShadowException
	{
		TACReference reference = node.getReference();
		if (reference instanceof TACPropertyRef)
		{
			TACPropertyRef property = (TACPropertyRef)reference;
			TACMethod method = new TACMethod(property.getReferenceName(),
					property.getType().getGetter());
			TACCall call = new TACCall(method,
					Collections.singletonList(property.getPrefix()));
			walkTo(call);
			node.setName(call.getName());
		}
		else
			writer.write(nextTemp(node) + " = load " +
					typeName(node.getReference(), true, false));
	}
	@Override
	public void visit(TACStore node) throws ShadowException
	{
		TACReference reference = node.getReference();
		if (reference instanceof TACSequenceRef)
		{
			TACSequenceRef seq = (TACSequenceRef)reference;
			String name = typeName(node.getValue());
			for (int i = 0; i < seq.size(); i++)
			{
				writer.write(nextTemp() + " = extractvalue " + name + ", " + i);
				writer.write("store " + type(seq.get(i)) + ' ' + temp(0) +
						", " + typeName(seq.get(i), true));
			}
		}
		else if (reference instanceof TACPropertyRef)
		{
			TACPropertyRef property = (TACPropertyRef)reference;
			TACMethod method = new TACMethod(property.getReferenceName(),
					property.getType().getSetter());
			walkTo(new TACCall(method,
					Arrays.asList(property.getPrefix(), node.getValue())));
		}
		else
			writer.write("store " + typeName(node.getValue()) + ", " +
					typeName(reference, true, true));
	}

	@Override
	public void visit(TACLabel node) throws ShadowException
	{
		writer.writeLeft(name(node) + ':');
	}

	@Override
	public void visit(TACBranch node) throws ShadowException
	{
		if (node.isConditional())
			writer.write("br " + typeName(node.getCondition()) + ", label %" +
					name(node.getTrueLabel()) + ", label %" +
					name(node.getFalseLabel()));
		else
			writer.write("br label %" + name(node.getLabel()));
	}

	@Override
	public void visit(TACCall node) throws ShadowException
	{
		TACMethod method = node.getMethod();
		if (method.getIndex() == -1)
		{
			StringBuilder sb = new StringBuilder();
			if (method.getReturnCount() != 0)
				sb.append(nextTemp(node)).append(" = ");
			sb.append("call ").append(typeName(method, false)).append('(');
			for (TACOperand param : node.getParameters())
				sb.append(typeName(param)).append(", ");
			if (method.getParameterCount() > 0)
				sb.delete(sb.length() - 2, sb.length());
			writer.write(sb.append(')').toString());
		}
		else
		{
			writer.write(nextTemp() + " = getelementptr " +
					typeName(node.getPrefix()) + ", i32 0, i32 0");
			writer.write(nextTemp() + " = load %" +
					raw(method.getPrefixType(), "_Mclass") + "** " + temp(1));
			writer.write(nextTemp() + " = getelementptr %" +
					raw(method.getPrefixType(), "_Mclass") + "* " +
					temp(1) + ", i32 0, i32 " + (method.getIndex() + 1));
			String methodTemp = nextTemp();
			writer.write(methodTemp + " = load " + type(method) + "* " +
					temp(1));
			StringBuilder sb = new StringBuilder();
			if (method.getReturnCount() != 0)
				sb.append(nextTemp(node)).append(" = ");
			sb.append("call ").append(type(method, false)).append(' ').
					append(methodTemp).append('(');
			for (TACOperand param : node.getParameters())
				sb.append(typeName(param)).append(", ");
			if (method.getParameterCount() > 0)
				sb.delete(sb.length() - 2, sb.length());
			writer.write(sb.append(')').toString());
		}
	}

	@Override
	public void visit(TACReturn node) throws ShadowException
	{
		if (node.hasReturnValue())
			writer.write("ret " + typeName(node.getReturnValue()));
		else
			writer.write("ret void");
		nextTemp();
	}

	private static String methodToString(TACMethod method)
	{
		StringBuilder sb = new StringBuilder(type(method.getReturnTypes())).
				append(' ').append(name(method)).append('(');
		for (TACVariable param : method.getParameters())
			sb.append(type(param)).append(", ");
		if (method.getParameterCount() > 0)
			sb.delete(sb.length() - 2, sb.length());
		sb.append(')');
		return sb.toString();
	}

	private static String sizeof(String type)
	{
		return "ptrtoint (" + type + " getelementptr (" + type +
				" null, i32 1) to i32)";
	}

	private static String type(TACVariable var)
	{
		return type(var.getType());
	}
	private static String type(TACOperand node)
	{
		return type(node.getType());
	}
	private static String type(ModifiedType type)
	{
		return type(type.getType());
	}
	private static String type(TACOperand node, boolean store)
	{
		return type(node.getType(), store);
	}
	private static String type(TACMethod method)
	{
		return type(method, true);
	}
	private static String type(TACMethod method, boolean parameters)
	{
		StringBuilder sb = new StringBuilder(type(method.getReturnTypes()));
		if (parameters)
		{
			sb.append(" (");
			for (TACVariable param : method.getParameters())
				sb.append(type(param)).append(", ");
			if (!method.getParameters().isEmpty())
				sb.delete(sb.length() - 2, sb.length());
			sb.append(")*");
		}
		return sb.toString();
	}
	private static String type(Type type, boolean store)
	{
		if (type instanceof PropertyType)
			if (store)
				type = ((PropertyType)type).getSetType().getType();
			else
				type = ((PropertyType)type).getGetType().getType();
		return type(type);
	}
	private static String type(Type type)
	{
		if (type == null)
			throw new NullPointerException();
		if (type instanceof ArrayType)
			return type((ArrayType)type);
		if (type instanceof SequenceType)
			return type((SequenceType)type);
		if (type instanceof ClassType)
			return type((ClassType)type);
		if (type instanceof TypeParameter)
			return type(Type.CLASS);
		throw new IllegalArgumentException("Unknown type.");
	}
	private static String type(ArrayType type)
	{
		return "{ " + type(type.getBaseType()) + "*, [" + type.getDimensions() +
				" x " + type(Type.INT) + "] }";
	}
	private static String type(SequenceType type)
	{
		if (type.isEmpty())
			return "void";
		if (type.size() == 1)
			return type(type.get(0));
		StringBuilder sb = new StringBuilder("{ ");
		for (ModifiedType each : type)
			sb.append(type(each)).append(", ");
		return sb.replace(sb.length() - 2, sb.length(), " }").toString();
	}
	private static String type(ClassType type)
	{
		if (type.isPrimitive())
			return '%' + type.getTypeName();
		return "%\"" + type.getMangledName() + "\"*";
	}

	private static String raw(Type type)
	{
		return raw(type, "");
	}
	private static String raw(Type type, String extra)
	{
		return '\"' + type.getMangledName() + extra + '\"';
	}

	private static String name(TACVariable var)
	{
		String name = var.getName();
		if (name == null)
			throw new NullPointerException();
		return name;
	}
	private static String name(TACOperand node)
	{
		String name = node.getName();
		if (name == null)
			throw new NullPointerException();
		return name;
	}
	private static String name(TACLabel node)
	{
		String name = node.getName();
		if (name == null)
			throw new NullPointerException();
		return name;
	}
	private static String name(TACMethod method)
	{
		StringBuilder sb = new StringBuilder("@\"").
				append(method.getPrefixType().getMangledName()).append("_M").
				append(method.getName());
		for (ModifiedType paramType : method.getType().getParameterTypes())
			sb.append("_R").append(paramType.getType().getMangledName());
		return sb.append('\"').toString();
	}

	private static String typeName(TACOperand node)
	{
		return typeName(node, false);
	}
	private static String typeName(TACMethod method, boolean params)
	{
		return typeName(type(method, params), name(method), false);
	}
	private static String typeName(TACOperand node, boolean reference)
	{
		return typeName(type(node), name(node), reference);
	}
	private static String typeName(TACOperand node, boolean reference,
			boolean store)
	{
		return typeName(type(node, store), name(node), reference);
	}
	private static String typeName(String type, String name, boolean reference)
	{
		StringBuilder sb = new StringBuilder(type);
		if (reference)
			sb.append('*');
		return sb.append(' ').append(name).toString();
	}
}
