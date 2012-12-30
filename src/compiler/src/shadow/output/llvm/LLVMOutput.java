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
import java.util.Map.Entry;

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
import shadow.tac.nodes.TACMethodRef;
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
import shadow.typecheck.type.InterfaceType;
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
	private ClassInterfaceBaseType moduleType;
	private boolean inCreate;
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
		node.setSymbol(name);
		return name;
	}

	private String nextLabel()
	{
		return "_label" + labelCounter++;
	}
	private String nextLabel(TACOperand node)
	{
		String name = nextLabel();
		node.setSymbol(name);
		return name;
	}

	private String nextString(String value)
	{
		int index = stringLiterals.indexOf(value);
		if (index == -1)
		{
			index = stringLiterals.size();
			stringLiterals.add(value);
		}
		return "@_string" + index;
	}
	private String nextString(TACOperand node, String value)
	{
		String name = nextString(value);
		node.setSymbol(name);
		return name;
	}

	// Class type flags
	@SuppressWarnings("unused")
	private static final int INTERFACE = 1, PRIMITIVE = 2;

	@Override
	public void startFile(TACModule module) throws ShadowException
	{
		moduleType = module.getType();

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

		if (module.getType() instanceof ClassType)
		{
			ClassType type = module.getClassType();
			writer.write('%' + raw(type, "_Mclass") + " = type { %" +
					raw(Type.CLASS) + methodList(type, type.orderAllMethods(),
					false) + " }");
			StringBuilder sb = new StringBuilder().append('%').
					append(raw(type)).append(" = type { %").
					append(raw(type, "_Mclass")).append('*');
			if (type.isPrimitive())
				sb.append(", ").append(type(type));
			else
				for (Entry<String, ? extends ModifiedType> field :
						type.getAllFields())
					sb.append(", ").append(type(field.getValue()));
			writer.write(sb.append(" }").toString());
		}
		for (ClassInterfaceBaseType type : module.getReferences())
			if (type instanceof ClassType && !(type instanceof ArrayType) &&
					!type.equals(module.getType()))
		{
			writer.write('%' + raw(type, "_Mclass") + " = type { %" +
					raw(Type.CLASS) + methodList(type, ((ClassType)type).
					orderAllMethods(), false) + " }");
			StringBuilder sb = new StringBuilder().append('%').
					append(raw(type)).append(" = type { %").
					append(raw(type, "_Mclass")).append('*');
			if (type.equals(Type.CLASS))
				sb.append(", ").
						append(type(new ArrayType(Type.CLASS))).append(", ").
						append(type(Type.OBJECT)).append(", ").
						append(type(Type.STRING)).append(", ").
						append(type(Type.CLASS)).append(", ").
						append(type(Type.INT)).append(", ").
						append(type(Type.INT)).append(", ").
						append(type(Type.INT));
			else if (type.equals(Type.ARRAY))
				sb.append(", ").append(type(new ArrayType(Type.INT))).
						append(", ").append(type(Type.CLASS)).append(", ").
						append(type(Type.OBJECT));
			else if (type.equals(Type.STRING))
				sb.append(", ").append(type(new ArrayType(Type.UBYTE))).
						append(", ").append(type(Type.BOOLEAN));
			else if (type.isPrimitive())
				sb.append(", ").append(type(type));
			writer.write(sb.append(" }").toString());
		}
		writer.write();

		if (module.getType() instanceof ClassType)
		{
			ClassType type = module.getClassType();
			List<MethodSignature> methods = type.orderAllMethods();
			int flags = 0;
			if (type.isPrimitive())
				flags |= PRIMITIVE;
			String width = sizeof(type(type) + '*'),
					size = sizeof('%' + raw(type) + '*'),
					parent = null;
			Type parentType = type.getExtendType();
			if (parentType != null)
				parent = "getelementptr inbounds (%" + raw(parentType,
						"_Mclass") + "* @" + raw(parentType, "_Mclass") +
						", i32 0, i32 0)";
			writer.write('@' + raw(type, "_Mclass") + " = global %" + raw(type,
					"_Mclass") + " { %" + raw(Type.CLASS) + " { %" +
					raw(Type.CLASS, "_Mclass") + "* @" + raw(Type.CLASS,
					"_Mclass") + ", " + type(new ArrayType(Type.CLASS)) +
					" zeroinitializer, " + type(Type.OBJECT) + " null, " +
					type(Type.STRING) + ' ' + nextString(type.
					getQualifiedName()) + ", " + type(Type.CLASS) + ' ' +
					parent + ", " + type(Type.INT) + ' ' + flags + ", " +
					type(Type.INT) + ' ' + size + ", " +
					type(Type.INT) + ' ' + width + " }" +
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
			TACMethodRef method = new TACMethodRef(methodSignature);
			sb.append(", ").append(methodType(method, true));
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
					!type.equals(module.getType()))
		{
			if (type.equals(Type.CLASS))
			{
				writer.write("declare noalias " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_Mallocate") + '(' +
						type(Type.CLASS) + ')');
				writer.write("declare noalias " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_Mallocate_Pshadow_Pstandard_Cint") +
						'(' + type(Type.CLASS) + ", " + type(Type.INT) + ')');
			}
			if (type.equals(Type.ARRAY))
				writer.write("declare " + type(Type.ARRAY) + " @" +
						raw(Type.ARRAY, "_Mcreate" + new ArrayType(Type.INT).
						getMangledName() + Type.OBJECT.getMangledName()) + '(' +
						type(Type.ARRAY) + ", " + type(Type.CLASS) + ", " +
						type(new ArrayType(Type.INT)) + ", " +
						type(Type.OBJECT) + ')');
			for (List<MethodSignature> methodList : type.getMethodMap().
					values())
				for (MethodSignature method : methodList)
					if (method.getModifiers().isPublic())
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
			boolean ascii = true;
			for (byte b : data)
			{
				if (b < 0)
					ascii = false;
				if (b != '\"' && b >= 0x20 && b < 0x7f)
					sb.appendCodePoint(b);
				else
					sb.append('\\').
							append(Character.forDigit((b & 0xff) >>> 4, 16)).
							append(Character.forDigit(b & 0xf, 16));
			}
			writer.write("@_array" + stringIndex + " = private unnamed_addr " +
					"constant [" + data.length + " x " + type(Type.UBYTE) +
					"] c\"" + sb + '\"');
			writer.write("@_string" + stringIndex + " = private unnamed_addr " +
					"constant %" + raw(Type.STRING) + " { %" + raw(Type.STRING,
					"_Mclass") + "* @" + raw(Type.STRING, "_Mclass") + ", " +
					type(new ArrayType(Type.UBYTE)) + " { " + type(Type.UBYTE) +
					"* getelementptr inbounds ([" +
					data.length + " x " + type(Type.UBYTE) + "]* @_array" +
					stringIndex + ", i32 0, i32 0), [1 x " + type(Type.INT) +
					"] [" + type(Type.INT) + ' ' + data.length + "] }, " +
					type(Type.BOOLEAN) + ' ' + ascii + " }");
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
		inCreate = method.isCreate();
		tempCounter = method.getParameterCount() + 1;
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
			if (method.isCreate())
			{
				ClassInterfaceBaseType type = method.getPrefixType();
				writer.write(nextTemp() + " = getelementptr inbounds %" +
						raw(type) + "* %0, i32 0, i32 0");
				writer.write("store %" + raw(type, "_Mclass") + "* @" +
						raw(type, "_Mclass") + ", %" + raw(type, "_Mclass") +
						"** " + temp(0));
			}
			boolean first = method.getPrefixType().isPrimitive();
			int paramIndex = 0;
			for (TACVariable param : method.getParameters())
				if (first)
				{
					first = false;
					writer.write(nextTemp() + " = getelementptr inbounds %" +
							raw(param.getType()) + "* %" + paramIndex++ +
							", i32 0, i32 1");
					writer.write(nextTemp() + " = load " +
							type(param) + "* " + temp(1));
					writer.write("store " + type(param) + ' ' + temp(0) +
							", " + typeName(param));
				}
				else
					writer.write("store " + type(param) + " %" + paramIndex++ +
							", " + typeName(param));
		}
	}

	@Override
	public void endMethod(TACMethod method) throws ShadowException
	{
		writer.outdent();
		if (!method.isNative())
			writer.write('}');
		writer.write();
		inCreate = false;
	}

	@Override
	public void visit(TACVariableRef node) throws ShadowException
	{
		node.setSymbol('%' + name(node.getVariable()));
	}

	@Override
	public void visit(TACSingletonRef node) throws ShadowException
	{
		node.setSymbol("@\"" + node.getType().getMangledName() + "_Minstance\"");
	}

	@Override
	public void visit(TACArrayRef node) throws ShadowException
	{
		writer.write(nextTemp() + " = extractvalue " + typeSymbol(node.getArray()) +
				", 0");
		writer.write(nextTemp(node) + " = getelementptr inbounds " +
				type(node) + "* " + temp(1) + ", " + typeSymbol(node.getTotal()));
	}

	@Override
	public void visit(TACFieldRef node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = getelementptr inbounds " +
				typeSymbol(node.getPrefix()) + ", i32 0, i32 " +
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
			node.setSymbol(Integer.toString((int)(Character)node.getValue()));
		else if (node.getValue() instanceof String)
			nextString(node, (String)node.getValue());
	}

	@Override
	public void visit(TACClass node) throws ShadowException
	{
		node.setSymbol("getelementptr (%" + raw(node.getClassType(), "_Mclass") +
				"* @" + raw(node.getClassType(), "_Mclass") +
				", i32 0, i32 0)");
	}

	@Override
	public void visit(TACSequence node) throws ShadowException
	{
		String type = type(node), last = "undef";
		for (int i = 0; i < node.size(); i++, last = temp(0))
			writer.write(nextTemp(node) + " = insertvalue " + type + ' ' + last +
					", " + typeSymbol(node.get(i)) + ", " + i);
	}

	@Override
	public void visit(TACCast node) throws ShadowException
	{
		String srcName = node.getOperand().getSymbol();
		Type srcType = node.getOperand().getType(), destType = node.getType();
		if (srcType == Type.NULL)
		{
			node.setSymbol("null");
			return;
		}
		if (srcType.isPrimitive() != destType.isPrimitive())
		{
			if (srcType.isPrimitive())
			{
				writer.write(nextTemp(node) + " = call noalias " +
						type(Type.OBJECT) + " @" + raw(Type.CLASS,
						"_Mallocate") + '(' + type(Type.CLASS) +
						" getelementptr inbounds (%" + raw(srcType, "_Mclass") +
						"* @" + raw(srcType, "_Mclass") + ", i32 0, i32 0))");
				writer.write(nextTemp() + " = bitcast " + type(
						Type.OBJECT) + temp(1) + " to %" + raw(srcType) + '*');
				writer.write(nextTemp() + " = getelementptr inbounds %" +
						raw(srcType) + '*' + temp(1) + ", i32 0, i32 0");
				writer.write("store %" + raw(srcType, "_Mclass") + "* @" +
						raw(srcType, "_Mclass") + ", %" +
						raw(srcType, "_Mclass") + "** " + temp(0));
				writer.write(nextTemp() + " = getelementptr inbounds %" +
						raw(srcType) + "* " + temp(2) + ", i32 0, i32 1");
				writer.write("store " + type(srcType) + ' ' + srcName + ", " +
						type(srcType) + "* " + temp(0));
				srcName = temp(3);
				srcType = Type.OBJECT;
			}
			else
			{
				throw new UnsupportedOperationException();
			}
		}
		if (srcType instanceof ArrayType != destType instanceof ArrayType)
		{
			if (srcType instanceof ArrayType)
			{
				ArrayType arrayType = (ArrayType)srcType;
	//			int dimensions = arrayType.getDimensions();
	//			String lengthsType = "[" + dimensions + " x %int]";
	//			writer.write(nextTemp() + " = call i8* @malloc(i32 " +
	//					sizeof(type(Type.ARRAY)) + ')');
	//			writer.write(nextTemp() + " = bitcast i8* " + temp(1) + " to " +
	//					type(Type.ARRAY));
	//			writer.write(nextTemp() + " = extractvalue " + typeName(operand) +
	//					", 0");
	//			writer.write(nextTemp() + " = ptrtoint " + type(arrayType.
	//					getBaseType()) + "* " + temp(1) + " to %long");
	//			writer.write(nextTemp() + " = extractvalue " + typeName(operand) +
	//					", 1");
	//			writer.write(nextTemp() + " = call i8* @malloc(i32 " +
	//					sizeof(lengthsType + '*') + ')');
	//			writer.write(nextTemp() + " = bitcast i8* " + temp(1) + " to " +
	//					lengthsType + '*');
	//			writer.write("store " + lengthsType + ' ' + temp(2) + ", " +
	//					lengthsType + "* " + temp(0));
	//			writer.write(nextTemp() + " = getelementptr inbounds " +
	//					lengthsType + "* " + temp(1) + ", i32 0, i32 0");
	//			writer.write(nextTemp() + " = insertvalue { %int*, [1 x %int] } " +
	//					"{ %int* null, [1 x %int] [%int " + dimensions + "] " +
	//					"}, %int* " + temp(1) + ", 0");
	//			writer.write(nextTemp(node) + " = call " + type(Type.ARRAY) +
	//					" @\"" + Type.ARRAY.getMangledName() +
	//					"_Mcreate_CT_Pshadow_Pstandard_Cint_A1\"(" +
	//					type(Type.ARRAY) + ' ' + temp(8) + ", " + type(Type.CLASS) +
	//					" getelementptr inbounds (%" + raw(arrayType.getBaseType(),
	//					"_Mclass") + "* @" + raw(arrayType.getBaseType(),
	//					"_Mclass") + ", i32 0, i32 0), %long " + temp(6) + ", " +
	//					type(new ArrayType(Type.INT)) + ' ' + temp(1) + ')');
	//			writer.writeLeft("; convert " + srcType + " to " + destType);
				ArrayType intArray = new ArrayType(Type.INT);
				String dimsType = " [" + arrayType.getDimensions() + " x " +
						type(Type.INT) + ']';
				writer.write(nextTemp() + " = extractvalue " + type(srcType) +
						' ' + srcName + ", 1");
				writer.write(nextTemp() + " = call " + type(Type.OBJECT) +
						" @" + raw(Type.CLASS,
						"_Mallocate_Pshadow_Pstandard_Cint") + '(' +
						type(Type.CLASS) + " getelementptr inbounds (%" +
						raw(Type.INT, "_Mclass") + "* @" + raw(Type.INT,
						"_Mclass") + ", i32 0, i32 0)" + ", " + type(Type.INT) +
						' ' + arrayType.getDimensions() + ')');
				writer.write(nextTemp() + " = bitcast " + type(Type.OBJECT) +
						' ' + temp(1) + " to" + dimsType + '*');
				writer.write("store" + dimsType + ' ' + temp(2) + ',' +
						dimsType + "* " + temp(0));
				writer.write(nextTemp() + " = getelementptr inbounds" +
						dimsType + "* " + temp(1) + ", i32 0, i32 0");
				writer.write(nextTemp() + " = insertvalue " + type(intArray) +
						" { " + type(Type.INT) + "* null, [1 x " +
						type(Type.INT) + "] [" + type(Type.INT) + ' ' +
						arrayType.getDimensions() + "] }, " + type(Type.INT) +
						"* " + temp(1) + ", 0");
				writer.write(nextTemp() + " = extractvalue " + type(srcType) +
						' ' + srcName + ", 0");
				writer.write(nextTemp() + " = bitcast " +
						type(arrayType.getBaseType()) + "* " + temp(1) +
						" to " + type(Type.OBJECT));
				writer.write(nextTemp() + " = call noalias " +
						type(Type.OBJECT) + " @" + raw(Type.CLASS,
						"_Mallocate") + '(' + type(Type.CLASS) +
						" getelementptr inbounds (%" + raw(Type.ARRAY,
						"_Mclass") + "* @" + raw(Type.ARRAY, "_Mclass") +
						", i32 0, i32 0))");
				writer.write(nextTemp() + " = bitcast " + type(Type.OBJECT) +
						' ' + temp(1) + " to " + type(Type.ARRAY));
				writer.write(nextTemp() + " = call " + type(Type.ARRAY) + " @" +
						raw(Type.ARRAY, "_Mcreate" + new ArrayType(Type.INT).
						getMangledName() + Type.OBJECT.getMangledName()) + '(' +
						type(Type.ARRAY) + ' ' + temp(1) + ", " +
						type(Type.CLASS) + " getelementptr inbounds (%" +
						raw(arrayType.getBaseType(), "_Mclass") + "* @" +
						raw(arrayType.getBaseType(), "_Mclass") +
						", i32 0, i32 0), " + type(new ArrayType(Type.INT)) +
						' ' + temp(5) + ", " + type(Type.OBJECT) + ' ' +
						temp(3) + ')');
				srcType = Type.ARRAY;
				srcName = temp(0);
			}
			else
			{
				if (!srcType.equals(Type.ARRAY))
				{
					writer.write(nextTemp() + " = bitcast " + type(srcType) +
							' ' + srcName + " to " + type(Type.ARRAY));
					srcType = Type.ARRAY;
					srcName = temp(0);
				}
				ArrayType arrayType = (ArrayType)destType;
				String baseType = type(arrayType.getBaseType()) + '*',
						dimsType = " [" + arrayType.getDimensions() + " x " +
								type(Type.INT) + ']';
				writer.write(nextTemp() + " = getelementptr inbounds " +
								type(srcType) + ' ' + srcName +
								", i32 0, i32 3");
				writer.write(nextTemp() + " = load " + type(Type.OBJECT) +
						"* " + temp(1));
				writer.write(nextTemp() + " = bitcast " + type(Type.OBJECT) +
						' ' + temp(1) + " to " + baseType);
				writer.write(nextTemp() + " = insertvalue " + type(destType) +
						" undef, " + baseType + ' ' + temp(1) + ", 0");
				writer.write(nextTemp() + " = getelementptr inbounds " +
						type(srcType) + ' ' + srcName +
						", i32 0, i32 1, i32 0");
				writer.write(nextTemp() + " = load " + type(Type.INT) + "** " +
						temp(1));
				writer.write(nextTemp() + " = bitcast " + type(Type.INT) +
						"* " + temp(1) + " to" + dimsType + '*');
				writer.write(nextTemp() + " = load" + dimsType + "* " +
						temp(1));
				writer.write(nextTemp() + " = insertvalue " + type(destType) +
						' ' + temp(5) + ',' + dimsType + ' ' + temp(1) + ", 1");
				srcType = destType;
				srcName = temp(0);
			}
		}
		if (destType.equals(srcType))
		{
			node.setSymbol(srcName);
			return;
		}
		if (srcType.isSigned() && destType.isFloating())
		{
			writer.write(nextTemp(node) + " = sitofp " + type(srcType) + ' ' +
					srcName + " to " + type(destType));
			return;
		}
		if (srcType.isUnsigned() && destType.isFloating())
		{
			writer.write(nextTemp(node) + " = uitofp " + type(srcType) + ' ' +
					srcName + " to " + type(destType));
			return;
		}
		if (srcType.isFloating() && destType.isSigned())
		{
			writer.write(nextTemp(node) + " = fptosi " + type(srcType) + ' ' +
					srcName + " to " + type(destType));
			return;
		}
		if (srcType.isFloating() && destType.isUnsigned())
		{
			writer.write(nextTemp(node) + " = fptoui " + type(srcType) + ' ' +
					srcName + " to " + type(destType));
			return;
		}
		int srcWidth = srcType.getWidth(), destWidth = destType.getWidth();
		String instruction;
		if (destWidth > srcWidth)
			instruction = destType.isSigned() ? "sext" :
					destType.isUnsigned() ? "zext" : "fext";
		else if (destWidth < srcWidth)
			instruction = "trunc";
		else
			instruction = "bitcast";
		writer.write(nextTemp(node) + " = " + instruction + ' ' +
				type(srcType) + ' ' + srcName + " to " + type(destType));
	}

	@Override
	public void visit(TACNewObject node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = call noalias " + type(Type.OBJECT) +
				" @" + raw(Type.CLASS, "_Mallocate") + '(' +
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
		Type type = node.getType(), baseType = node.getType().getBaseType();
		writer.write(nextTemp() + " = call noalias " + type(Type.OBJECT) +
				" @" + raw(Type.CLASS, "_Mallocate_Pshadow_Pstandard_Cint") +
				'(' + type(Type.CLASS) + " getelementptr inbounds (%" +
				raw(baseType, "_Mclass") + "* @" + raw(baseType, "_Mclass") +
				", i32 0, i32 0), " + typeSymbol(node.getTotalSize()) + ')');
		writer.write(nextTemp() + " = bitcast " + type(Type.OBJECT) + ' ' +
				temp(1) + " to " + type(baseType) + '*');
		writer.write(nextTemp() + " = insertvalue " + type(node.getType()) +
				" undef, " + type(baseType) + "* " + temp(1) + ", 0");
		for (int i = 0; i < node.getDimensions(); i++)
			writer.write(nextTemp(node) + " = insertvalue " + type(type) +
					' ' + temp(1) + ", " + typeSymbol(node.getDimension(i)) +
					", 1, " + i);
	}

	@Override
	public void visit(TACLength node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = extractvalue " +
				typeSymbol(node.getArray()) + ", 1, " + node.getDimension());
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
				' ' + first + ", " + symbol(node.getOperand()));
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
				append(' ').append(symbol(node.getFirst())).append(", ").
				append(symbol(node.getSecond())).toString());
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
				first = symbol(node.getFirst()),
				second = symbol(node.getSecond());
		writer.write(nextTemp() + " = " + dir + _type_ + first + ", " + second);
		writer.write(nextTemp() + " = sub" + _type_ + "mul (" + type +
				" ptrtoint (" + type + "* getelementptr (" + type +
				"* null, i32 1) to " + type + ")," + _type_ + "8), " + second);
		writer.write(nextTemp() + " = " + otherDir + _type_ + first + ", " +
				temp(1));
		writer.write(nextTemp(node) + " = or" + _type_ + temp(1) + ", " +
				temp(3));
	}

	@Override
	public void visit(TACLoad node) throws ShadowException
	{
		TACReference reference = node.getReference();
		if (reference instanceof TACPropertyRef)
		{
			TACPropertyRef property = (TACPropertyRef)reference;
			TACMethodRef method = new TACMethodRef(property.getName(),
					property.getType().getGetter());
			TACCall call = new TACCall(method, method,
					Collections.singletonList(property.getPrefix()));
			walkTo(call);
			node.setSymbol(call.getSymbol());
		}
		else
			writer.write(nextTemp(node) + " = load " +
					typeSymbol(reference, true, false));
	}
	@Override
	public void visit(TACStore node) throws ShadowException
	{
		TACReference reference = node.getReference();
		if (reference instanceof TACSequenceRef)
		{
			TACSequenceRef seq = (TACSequenceRef)reference;
			String name = typeSymbol(node.getValue());
			for (int i = 0; i < seq.size(); i++)
			{
				writer.write(nextTemp() + " = extractvalue " + name + ", " + i);
				writer.write("store " + type(seq.get(i)) + ' ' + temp(0) +
						", " + typeSymbol(seq.get(i), true));
			}
		}
		else if (reference instanceof TACPropertyRef)
		{
			TACPropertyRef property = (TACPropertyRef)reference;
			TACMethodRef method = new TACMethodRef(property.getName(),
					property.getType().getSetter());
			walkTo(new TACCall(method, method,
					Arrays.asList(property.getPrefix(), node.getValue())));
		}
		else
			writer.write("store " + typeSymbol(node.getValue()) + ", " +
					typeSymbol(reference, true, true));
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
			writer.write("br " + typeSymbol(node.getCondition()) + ", label %" +
					symbol(node.getTrueLabel()) + ", label %" +
					symbol(node.getFalseLabel()));
		else
			writer.write("br label %" + symbol(node.getLabel()));
	}

	@Override
	public void visit(TACCall node) throws ShadowException
	{
		String methodName;
		TACMethodRef method = node.getMethod();
		if (method.getIndex() != -1 && !method.isCreate())
		{
			writer.write(nextTemp() + " = getelementptr " +
					typeSymbol(node.getPrefix()) + ", i32 0, i32 0");
			writer.write(nextTemp() + " = load %" +
					raw(method.getPrefixType(), "_Mclass") + "** " + temp(1));
			writer.write(nextTemp() + " = getelementptr %" +
					raw(method.getPrefixType(), "_Mclass") + "* " +
					temp(1) + ", i32 0, i32 " + (method.getIndex() + 1));
			methodName = nextTemp();
			writer.write(methodName + " = load " + methodType(method) + "* " +
					temp(1));
		}
		else
			methodName = name(method);
		StringBuilder sb = new StringBuilder();
		sb.append("call ").append(methodType(method, false)).append(' ').
				append(methodName).append('(');
		boolean first = true;
		for (TACOperand param : node.getParameters())
			if (first)
			{
				first = false;
				Type paramType = param.getType();
				if (paramType.isPrimitive())
				{
					writer.write(nextTemp(node) + " = call noalias " +
							type(Type.OBJECT) + " @" + raw(Type.CLASS,
							"_Mallocate") + '(' + type(Type.CLASS) +
							" getelementptr (%" + raw(paramType, "_Mclass") +
							"* @" + raw(paramType, "_Mclass") +
							", i32 0, i32 0))");
					writer.write(nextTemp() + " = bitcast " +
							type(Type.OBJECT) + temp(1) + " to %" +
							raw(paramType) + '*');
					writer.write(nextTemp() + " = getelementptr inbounds %" +
							raw(paramType) + '*' + temp(1) + ", i32 0, i32 1");
					writer.write("store " + typeSymbol(param) + ", " +
							type(paramType) + "* " + temp(0));
					sb.append('%').append(raw(paramType)).append("* ").
							append(temp(1));
				}
				else
					sb.append(typeSymbol(param));
			}
			else
				sb.append(", ").append(typeSymbol(param));
		if (method.getReturnCount() != 0 || method.isCreate())
			sb.insert(0, nextTemp(node) + " = ");
		writer.write(sb.append(')').toString());
	}

	@Override
	public void visit(TACReturn node) throws ShadowException
	{
		if (node.hasReturnValue())
			writer.write("ret " + typeSymbol(node.getReturnValue()));
		else if (inCreate)
			writer.write("ret %" + raw(moduleType) + "* %0");
		else
			writer.write("ret void");
		nextTemp();
	}

	@SuppressWarnings("unused")
	private static String methodType(TACMethodRef method)
	{
		return methodType(method, true);
	}
	private static String methodType(TACMethodRef method, boolean parameters)
	{
		StringBuilder sb = new StringBuilder();
		if (method.getPrefixType().isPrimitive() && method.isCreate())
			sb.append('%').append(raw(method.getPrefixType())).append('*');
		else
			sb.append(type(method.getReturnTypes()));
		if (parameters)
		{
			sb.append(" (");
			boolean first = true;
			for (ModifiedType paramType : method.getParameterTypes())
				if (first)
				{
					first = false;
					if (method.getPrefixType().isPrimitive())
						sb.append('%').append(raw(paramType.getType())).
								append('*');
					else
						sb.append(type(paramType.getType()));
				}
				else
					sb.append(", ").append(type(paramType.getType()));
			sb.append(")*");
		}
		return sb.toString();
	}
	private static String methodToString(TACMethod method)
	{
		StringBuilder sb = new StringBuilder();
		if (method.getPrefixType().isPrimitive() && method.isCreate())
			sb.append('%').append(raw(method.getPrefixType())).append('*');
		else
			sb.append(type(method.getReturnTypes()));
		sb.append(' ').append(name(method)).append('(');
		boolean first = true;
		for (TACVariable param : method.getParameters())
			if (first)
			{
				first = false;
				if (method.getPrefixType().isPrimitive())
					sb.append('%').append(raw(param.getType())).append('*');
				else
					sb.append(type(param));
			}
			else
				sb.append(", ").append(type(param));
		return sb.append(')').toString();
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
//		if (!type.getModifiers().isTypeName() &&
//				type.getType() instanceof TypeParameter)
//			return type(Type.CLASS);
		return type(type.getType());
	}
	private static String type(TACOperand node, boolean store)
	{
		return type(node.getType(), store);
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
		if (type instanceof InterfaceType)
			return type((InterfaceType)type);
		if (type instanceof TypeParameter)
			return type((TypeParameter)type);
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
	private static String type(InterfaceType type)
	{
		return type(Type.OBJECT);
	}
	private static String type(TypeParameter type)
	{
		return type(Type.OBJECT);
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
	private static String name(TACLabel node)
	{
		String name = node.getName();
		if (name == null)
			throw new NullPointerException();
		return name;
	}
	private static String name(TACMethodRef method)
	{
		StringBuilder sb = new StringBuilder("@\"").
				append(method.getPrefixType().getMangledName()).append("_M").
				append(method.getName());
		for (ModifiedType paramType : method.getType().getParameterTypes())
			sb.append(paramType.getType().getMangledName());
		return sb.append('\"').toString();
	}
	private static String name(TACMethod method)
	{
		StringBuilder sb = new StringBuilder("@\"").
				append(method.getPrefixType().getMangledName()).append("_M").
				append(method.getName());
		for (ModifiedType paramType : method.getType().getParameterTypes())
			sb.append(paramType.getType().getMangledName());
		return sb.append('\"').toString();
	}

	@SuppressWarnings("unused")
	private static String typeName(TACMethodRef method, boolean params)
	{
		return combine(methodType(method, params), name(method), false);
	}
	private static String typeName(TACVariable variable)
	{
		return combine(type(variable), '%' + name(variable), true);
	}

	private static String symbol(TACOperand node)
	{
		String name = node.getSymbol();
		if (name == null)
			throw new NullPointerException();
		return name;
	}
	private static String typeSymbol(TACOperand node)
	{
		return typeSymbol(node, false);
	}
	private static String typeSymbol(TACOperand node, boolean reference)
	{
		return combine(type(node), symbol(node), reference);
	}
	private static String typeSymbol(TACOperand node, boolean reference,
			boolean store)
	{
		return combine(type(node, store), symbol(node), reference);
	}

	private static String combine(String type, String name, boolean reference)
	{
		StringBuilder sb = new StringBuilder(type);
		if (reference)
			sb.append('*');
		return sb.append(' ').append(name).toString();
	}
}
