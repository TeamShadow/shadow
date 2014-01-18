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

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowByte;
import shadow.interpreter.ShadowCode;
import shadow.interpreter.ShadowDouble;
import shadow.interpreter.ShadowFloat;
import shadow.interpreter.ShadowInt;
import shadow.interpreter.ShadowInterpreter;
import shadow.interpreter.ShadowLong;
import shadow.interpreter.ShadowShort;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowUByte;
import shadow.interpreter.ShadowUInt;
import shadow.interpreter.ShadowULong;
import shadow.interpreter.ShadowUShort;
import shadow.interpreter.ShadowValue;
import shadow.output.AbstractOutput;
import shadow.output.Cleanup;
import shadow.output.TabbedLineWriter;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACConstant;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBlock;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACConstantRef;
import shadow.tac.nodes.TACDestination;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACInit;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLength;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNot;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACPhiRef;
import shadow.tac.nodes.TACPhiRef.TACPhi;
import shadow.tac.nodes.TACPlaceholder;
import shadow.tac.nodes.TACPropertyRef;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSame;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceRef;
import shadow.tac.nodes.TACSingletonRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACTypeId;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACUnwind;
import shadow.tac.nodes.TACVariableRef;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.UnboundMethodType;

public class LLVMOutput extends AbstractOutput
{
	private Process process = null;
	private int tempCounter = 0, labelCounter = 0;
	private List<String> stringLiterals = new LinkedList<String>();
	private TACModule module;
	private TACMethod method;
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
		node.data = name;
		return name;
	}

	private String nextLabel()
	{
		return "%_label" + labelCounter++;
	}
	private String nextLabel(TACOperand node)
	{
		String name = nextLabel();
		node.data = name;
		return name;
	}

	// Class type flags
	private static final int INTERFACE = 1, PRIMITIVE = 2;

	@Override
	public void startFile(TACModule module) throws ShadowException
	{
		this.module = module;
		Type moduleType = module.getType();

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

		// Methods for exception handling
		writer.write("declare i32 @__shadow_personality_v0(...)");
		writer.write("declare void @__shadow_throw(" + type(Type.OBJECT) + ") noreturn");
		writer.write("declare " + type(Type.EXCEPTION) + " @__shadow_catch(i8* nocapture) nounwind");
		writer.write("declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone");
		writer.write();

		StringBuilder sb = new StringBuilder().append('%').
				append(raw(moduleType, "_Mclass")).append(" = type { ");
		if (moduleType instanceof ClassType)
			sb.append('%').append(raw(Type.CLASS)).append(", ");
		writer.write(sb.append(methodList(moduleType.orderAllMethods(), false)).
				append(" }").toString());
		if (moduleType instanceof ClassType)
		{
			sb.setLength(0);
			sb.append('%').append(raw(moduleType)).append(" = type { %").
					append(raw(moduleType, "_Mclass")).append('*');
			if (moduleType.isPrimitive())
				sb.append(", ").append(type(moduleType));
			else for (Type fieldType : module.getFieldTypes())
				sb.append(", ").append(type(fieldType));
			writer.write(sb.append(" }").toString());
		}
		for (TACConstant constant : module.getConstants())
		{
			ShadowInterpreter interpreter = new ShadowInterpreter();
			interpreter.walk(constant);
			Object result = constant.getValue().data;
			if (!(result instanceof ShadowValue))
				throw new UnsupportedOperationException(
						"Could not interpret constant initializer");
			writer.write(name(constant) + " = constant " +
					typeLiteral((ShadowValue)result));
			Cleanup.getInstance().walk(constant);
		}
		for (Type type : module.getReferences())
			if (type != null && !(type instanceof ArrayType) &&
					!(type instanceof UnboundMethodType) &&
					!type.equals(module.getType()))
		{
			sb.setLength(0);
			sb.append('%').append(raw(type, "_Mclass")).append(" = type { ");
			if (type instanceof ClassType)
				sb.append('%').append(raw(Type.CLASS)).append(", ");
			writer.write(sb.append(methodList(type.orderAllMethods(), false)).
					append(" }").toString());
			if (type instanceof ClassType)
			{
				if (type.getTypeName().equals("compare"))
					throw new Error(type.getClass().toString());
				sb.setLength(0);
				sb.append('%').append(raw(type)).append(" = type { %").
						append(raw(type, "_Mclass")).append('*');
				if (type.equals(Type.CLASS))
					sb.append(", ").append(type(new ArrayType(Type.OBJECT))).
							append(", ").
							append(type(new ArrayType(Type.CLASS))).
							append(", ").append(type(Type.STRING)).append(", ").
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
				else for (Entry<String, ? extends ModifiedType> field :
						((ClassType)type).orderAllFields())
					sb.append(", ").append(type(field.getValue()));
				writer.write(sb.append(" }").toString());
			}
		}
		writer.write();

		List<InterfaceType> interfaces = moduleType.getAllInterfaces();
		int interfaceCount = interfaces.size();
		StringBuilder interfaceData = new StringBuilder(
				"@_interfaceData = private unnamed_addr constant [").
				append(interfaceCount).append(" x ").
				append(type(Type.OBJECT)).append("] [");
		StringBuilder interfaceClasses = new StringBuilder(
				"@_interfaces = private unnamed_addr constant [").
				append(interfaceCount).append(" x ").
				append(type(Type.CLASS)).append("] [");
		for (int i = 0; i < interfaceCount; i++)
		{
			InterfaceType type = interfaces.get(i);
			if (module.isClass())
			{
				List<MethodSignature> methods = type.orderAllMethods(module.
						getClassType());
				String methodsType = methodList(methods, false);
				writer.write("@_class" + i +
						" = private unnamed_addr constant { " + methodsType +
						" } { " + methodList(methods, true) + " }");
				interfaceData.append(type(Type.OBJECT)).append(" bitcast ({ ").
						append(methodsType).append(" }* @_class").append(i).
						append(" to ").append(type(Type.OBJECT)).append("), ");
			}
			interfaceClasses.append(typeText(Type.CLASS, classOf(type))).
					append(", ");
		}
		if (interfaceCount != 0)
		{
			interfaceData.delete(interfaceData.length() - 2,
					interfaceData.length());
			interfaceClasses.delete(interfaceClasses.length() - 2,
					interfaceClasses.length());
		}
		if (moduleType instanceof ClassType)
			writer.write(interfaceData.append(']').toString());
		writer.write(interfaceClasses.append(']').toString());

		List<MethodSignature> methods = moduleType.orderAllMethods();
		int flags = 0;
		if (moduleType.isPrimitive())
			flags |= PRIMITIVE;
		if (moduleType instanceof ClassType)
		{
			ClassType parentType = ((ClassType)moduleType).getExtendType();
			writer.write('@' + raw(moduleType, "_Mclass") + " = constant %" +
					raw(moduleType, "_Mclass") + " { %" + raw(Type.CLASS) +
					" { %" + raw(Type.CLASS, "_Mclass") + "* @" +
					raw(Type.CLASS, "_Mclass") + ", " +
					type(new ArrayType(Type.OBJECT)) + " { " +
					type(Type.OBJECT) + "* getelementptr inbounds ([" +
					interfaceCount + " x " + type(Type.OBJECT) +
					"]* @_interfaceData, i32 0, i32 0), [1 x " +
					type(Type.INT) + "] [" + typeLiteral(interfaceCount) +
					"] }, " + type(new ArrayType(Type.CLASS)) + " { " +
					type(Type.CLASS) + "* getelementptr inbounds ([" +
					interfaceCount + " x " + type(Type.CLASS) +
					"]* @_interfaces, i32 0, i32 0), [1 x " + type(Type.INT) +
					"] [" + typeLiteral(interfaceCount) + "] }, " +
					typeLiteral(moduleType.getQualifiedName()) + ", " +
					typeText(Type.CLASS, parentType != null ?
					classOf(parentType) : null) + ", " + typeLiteral(flags) +
					", " + typeText(Type.INT, sizeof('%' + raw(moduleType) +
					'*')) + ", " + typeText(Type.INT, sizeof(type(moduleType) +
					'*')) + " }, " + methodList(methods, true) + " }");
		}
		else
		{
			flags |= INTERFACE;
			writer.write('@' + raw(moduleType, "_Mclass") + " = constant %" +
					raw(Type.CLASS) + " { %" + raw(Type.CLASS, "_Mclass") +
					"* @" + raw(Type.CLASS, "_Mclass") + ", " +
					type(new ArrayType(Type.OBJECT)) + " zeroinitializer, " +
					type(new ArrayType(Type.CLASS)) + " { " + type(Type.CLASS) +
					"* getelementptr inbounds ([" + interfaceCount + " x " +
					type(Type.CLASS) + "]* @_interfaces, i32 0, i32 0), [1 x " +
					type(Type.INT) + "] [" + typeLiteral(interfaceCount) +
					"] }, " + typeLiteral(moduleType.getQualifiedName()) +
					", " + type(Type.CLASS) + " null, " + typeLiteral(flags) +
					", " + typeLiteral(-1) + ", " + typeText(Type.INT,
					sizeof(type(moduleType) + '*')) + " }");
		}
		if (moduleType instanceof SingletonType)
			writer.write('@' + raw(moduleType, "_Minstance") + " = global " +
					type(moduleType) + " null");

		for (Type type : module.getReferences())
		{
			if (type != null && !(type instanceof ArrayType) &&
					!(type instanceof UnboundMethodType) &&
					!type.equals(module.getType()))
			{
				if (type instanceof InterfaceType)
					writer.write('@' + raw(type, "_Mclass") +
							" = external constant %" + raw(Type.CLASS));
				if (type instanceof ClassType)
					writer.write('@' + raw(type, "_Mclass") +
							" = external constant %" + raw(type, "_Mclass"));
				if (type instanceof SingletonType)
					writer.write('@' + raw(type, "_Minstance") +
							" = external global " + type(type));
			}
		}
		writer.write();

//		for (Type type : module.getClassType().getAllReferencedTypes())
//			if ( !type.isPrimitive())
//				defineClass(type, false);
//		defineClass(module.getType(), true);

//		for (Type type : module.getReferences())
//			if (!type.isPrimitive())
//				declareType(type, type.equals(module.getType()));
//		writer.write();

//		for (Type type : module.getReferences())
//			if (!type.isPrimitive())
//				declareClass(type, type.equals(module.getType()));
//		writer.write();

//		for (Type type : module.getReferences())
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

//	private void declareClass(Type type, boolean current)
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
//	private void defineClass(Type type, boolean current)
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

	private String methodList(Iterable<MethodSignature> methods, boolean name)
			throws ShadowException
	{
		StringBuilder sb = new StringBuilder();
		for (MethodSignature method : methods)
		{
			TACMethodRef methodRef = new TACMethodRef(method);
			sb.append(", ").append(methodType(methodRef));
			if (name)
				sb.append(' ').append(name(methodRef));
		}
		return sb.substring(2);
	}

	@Override
	public void endFile(TACModule module) throws ShadowException
	{
		for (Type type : module.getReferences())
			if (type instanceof ClassType && !(type instanceof ArrayType) &&
					!(type instanceof UnboundMethodType) &&
					!type.equals(module.getType()))
		{
			if (type.equals(Type.CLASS))
			{
				writer.write("declare noalias " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_Mallocate") + '(' +
						type(Type.CLASS) + ')');
				writer.write("declare noalias " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_Mallocate" +
						Type.INT.getMangledName()) + '(' + type(Type.CLASS) +
						", " + type(Type.INT) + ')');
				writer.write("declare " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_MinterfaceData" +
						Type.CLASS.getMangledName()) + '(' + type(Type.CLASS) +
						", " + type(Type.CLASS) + ')');
			}
			if (type.equals(Type.ARRAY))
				writer.write("declare " + type(Type.ARRAY) + " @" +
						raw(Type.ARRAY, "_Mcreate" + new ArrayType(Type.INT).
						getMangledName() + Type.OBJECT.getMangledName()) + '(' +
						type(Type.OBJECT) + ", " + type(Type.CLASS) + ", " +
						type(new ArrayType(Type.INT)) + ", " +
						type(Type.OBJECT) + ')');
			for (List<MethodSignature> methodList :
					type.getMethodMap().values())
				for (MethodSignature method : methodList)
					if (method.getModifiers().isPublic())
						writer.write("declare " + methodToString(
								new TACMethod(method).addParameters()));
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
					type(new ArrayType(Type.BYTE)) + " { " + type(Type.BYTE) +
					"* getelementptr inbounds ([" +
					data.length + " x " + type(Type.BYTE) + "]* @_array" +
					stringIndex + ", i32 0, i32 0), [1 x " + type(Type.INT) +
					"] [" + typeLiteral(data.length) + "] }, " +
					typeLiteral(ascii) + " }");
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

				try
				{
					Thread.sleep(11);
				} catch (InterruptedException ex)
				{
				}

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
		this.method = method;
		if (module.isInterface())
			return;
		TACMethodRef methodRef = method.getMethod();
		tempCounter = methodRef.getParameterCount() + 1;
		if (methodRef.isNative())
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
			if (method.hasLandingpad())
				writer.write("%_exception = alloca { i8*, i32 }");
			boolean primitiveCreate = !method.getMethod().getPrefixType().
					isSimpleReference() && methodRef.isCreate(), first = true;
			int paramIndex = 0;
			for (TACVariable param : method.getParameters())
			{
				String symbol = '%' + Integer.toString(paramIndex++);
				if (first)
				{
					first = false;
					if (methodRef.isCreate())
					{
						writer.write(nextTemp() + " = bitcast " +
								typeText(Type.OBJECT, symbol) + " to %" +
								raw(param.getType()) + '*');
						symbol = temp(0);
					}
					if (primitiveCreate)
					{
						writer.write(nextTemp() +
								" = getelementptr inbounds %" +
								raw(param.getType()) + "* " + symbol +
								", i32 0, i32 1");
						writer.write(nextTemp() + " = load " +
								type(param) + "* " + temp(1));
						symbol = temp(0);
					}
				}
				writer.write("store " + typeText(param, symbol) + ", " +
						typeName(param));
			}
		}
	}

	@Override
	public void endMethod(TACMethod method) throws ShadowException
	{
		writer.outdent();
		if (!method.getMethod().isNative() && !module.isInterface())
			writer.write('}');
		writer.write();
		method = null;
	}

	@Override
	public void visit(TACVariableRef node) throws ShadowException
	{
		node.data = '%' + name(node.getVariable());
	}

	@Override
	public void visit(TACSingletonRef node) throws ShadowException
	{
		node.data = '@' + raw(node.getType(), "_Minstance");
	}

	@Override
	public void visit(TACArrayRef node) throws ShadowException
	{
		writer.write(nextTemp() + " = extractvalue " +
				typeSymbol(node.getArray()) + ", 0");
		writer.write(nextTemp(node) + " = getelementptr inbounds " +
				type(node) + "* " + temp(1) + ", " +
				typeSymbol(node.getTotal()));
	}

	@Override
	public void visit(TACConstantRef node) throws ShadowException
	{
		node.data = name(node);
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

	@Override
	public void visit(TACMethodRef node) throws ShadowException
	{
		if (node.getPrefixType() instanceof InterfaceType)
		{
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getPrefix()) + ", 0");
			writer.write(nextTemp() + " = getelementptr %" +
					raw(node.getPrefixType(), "_Mclass") + "* " +
					temp(1) + ", i32 0, i32 " + node.getIndex());
			writer.write(nextTemp(node) + " = load " + methodType(node) +
					"* " + temp(1));
		}
		else if (node.hasPrefix() &&
				!node.getPrefixType().getModifiers().isImmutable() &&
				//!node.getType().getModifiers().isFinal() && //replace with Readonly?
				!node.getType().getModifiers().isPrivate())
		{
			writer.write(nextTemp() + " = getelementptr " +
					typeSymbol(node.getPrefix()) + ", i32 0, i32 0");
			writer.write(nextTemp() + " = load %" +
					raw(node.getPrefixType(), "_Mclass") + "** " + temp(1));
			writer.write(nextTemp() + " = getelementptr %" +
					raw(node.getPrefixType(), "_Mclass") + "* " +
					temp(1) + ", i32 0, i32 " + (node.getIndex() + 1));
			writer.write(nextTemp(node) + " = load " + methodType(node) +
					"* " + temp(1));
		}
		else
			node.data = name(node);
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
		node.data = literal(node.getValue());
	}

	@Override
	public void visit(TACClass node) throws ShadowException
	{
		if (node.hasOperand())
			node.data = symbol(node.getOperand());
		else
			node.data = classOf(node.getClassType());
	}

	@Override
	public void visit(TACSequence node) throws ShadowException
	{
		String current = "undef";
		for (int i = 0; i < node.size(); i++)
		{
			writer.write(nextTemp() + " = insertvalue " + typeText(node,
					current) + ", " + typeSymbol(node.get(i)) + ", " + i);
			current = temp(0);
		}
		node.data = current;
	}

	@Override
	public void visit(TACCast node) throws ShadowException
	{ 
		String srcName = symbol(node.getOperand());
		ModifiedType srcType = node.getOperand(), destType = node;
		if (srcType.getType() instanceof SequenceType ||
				destType.getType() instanceof SequenceType)
		{
			if (srcType.getType() instanceof SequenceType &&
					destType.getType() instanceof SequenceType)
			{
				String current = "undef";
				int index = 0;
				for (ModifiedType type : (SequenceType)destType.getType()) {
					TACOperand value = new TACPlaceholder(
							((SequenceType)srcType.getType()).get(index));
					writer.write(nextTemp(value) + " = extractvalue " +
							typeSymbol(node.getOperand()) + ", " + index);
					TACCast cast = new TACCast(value, type, value);
					walk(value);
					writer.write(nextTemp() + " = insertvalue " + typeText(node,
							current) + ", " + typeSymbol(cast) + ", " + index);
					current = temp(0);
					index++;
				}
				node.data = current;
			}
			else if (srcType.getType() instanceof SequenceType)
			{
				srcType = ((SequenceType)srcType.getType()).get(0);
				TACOperand value = new TACPlaceholder(srcType);
				writer.write(nextTemp(value) + " = extractvalue " +
						typeSymbol(node.getOperand()) + ", 0");
				TACCast cast = new TACCast(value, destType, value);
				walk(cast);
				node.data = symbol(cast);
			}
			else
			{
				destType = ((SequenceType)destType.getType()).get(0);
				TACCast cast = new TACCast(destType, node.getOperand());
				walk(cast);
				writer.write(nextTemp() + " = insertvalue " + type(node)
						+ " undef, " + typeSymbol(cast) + ", 0");
				node.data = temp(0);
			}
			return;
		}
		if (srcType.getType() == Type.NULL)
		{
			node.data = "null";
			return;
		}
		if (destType.getType() == Type.NULL)
		{
			node.data = srcName;
			return;
		}
		if (srcType.getType() instanceof InterfaceType)
		{
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getOperand()) + ", 1");
			srcName = temp(0);
			srcType = new SimpleModifiedType(Type.OBJECT);
		}
		if (destType.getType() instanceof InterfaceType)
		{
			TACMethodRef methodRef = new TACMethodRef(
					Type.CLASS.getMethods("interfaceData").get(0));
			TACClass destClass = new TACClass(methodRef, destType.getType(),
					method);
			TACClass srcClass = new TACClass(destClass, srcType.getType(),
					method);
			TACCall call = new TACCall(srcClass, new TACBlock(), methodRef,
					srcClass, destClass);
			walk(call);
			writer.write(nextTemp() + " = bitcast " + typeSymbol(call) +
					" to %" + raw(destType, "_Mclass") + '*');
			writer.write(nextTemp() + " = insertvalue " + type(destType) +
					" undef, %" + raw(destType, "_Mclass") + "* " + temp(1) +
					", 0");
			writer.write(nextTemp() + " = bitcast " +
					typeSymbol(node.getOperand()) + " to " + type(Type.OBJECT));
			writer.write(nextTemp(node) + " = insertvalue " + typeText(destType,
					temp(2)) + ", " + typeTemp(Type.OBJECT, 1) + ", 1");
			return;
		}
		if (srcType.getType().isPrimitive() != destType.getType().isPrimitive())
		{
			if (srcType.getType().isPrimitive())
			{
				writer.write(nextTemp(node) + " = call noalias " +
						type(Type.OBJECT) + " @" + raw(Type.CLASS,
						"_Mallocate") + '(' + type(Type.CLASS) +
						" getelementptr inbounds (%" + raw(srcType, "_Mclass") +
						"* @" + raw(srcType, "_Mclass") + ", i32 0, i32 0))");
				writer.write(nextTemp() + " = bitcast " + typeText(Type.OBJECT,
						temp(1)) + " to %" + raw(srcType) + '*');
				writer.write(nextTemp() + " = getelementptr inbounds %" +
						raw(srcType) + "* " + temp(1) + ", i32 0, i32 0");
				writer.write("store %" + raw(srcType, "_Mclass") + "* @" +
						raw(srcType, "_Mclass") + ", %" +
						raw(srcType, "_Mclass") + "** " + temp(0));
				writer.write(nextTemp() + " = getelementptr inbounds %" +
						raw(srcType) + "* " + temp(2) + ", i32 0, i32 1");
				writer.write("store " + typeText(srcType, srcName) + ", " +
						typeText(srcType, temp(0), true));
				srcName = temp(3);
				srcType = new SimpleModifiedType(Type.OBJECT);
			}
			else
			{	// TODO: FIXME: !!!
				writer.write(nextTemp() + " = bitcast " + typeText(srcType,
						srcName) + " to %" + raw(destType) + '*');
				writer.write(nextTemp() + " = getelementptr inbounds %" +
						raw(destType) + "* " + temp(1) + ", i32 0, i32 1");
				writer.write(nextTemp(node) + " = load " + typeTemp(destType,
						1, true));
				return;
			}
		}
		if (srcType.getType() instanceof ArrayType !=
				destType.getType() instanceof ArrayType)
		{
			if (srcType.getType() instanceof ArrayType)
			{
				ArrayType arrayType = (ArrayType)srcType.getType();
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
				TACClass baseClass = new TACClass(arrayType.getBaseType(),
						method);
				walk(baseClass);
				ArrayType intArray = new ArrayType(Type.INT);
				String dimsType = " [" + arrayType.getDimensions() + " x " +
						type(Type.INT) + ']';
				writer.write(nextTemp() + " = extractvalue " + typeText(srcType,
						srcName) + ", 1");
				writer.write(nextTemp() + " = call " + type(Type.OBJECT) +
						" @" + raw(Type.CLASS,
						"_Mallocate_Pshadow_Pstandard_Cint") + '(' +
						typeText(Type.CLASS, classOf(Type.INT)) + ", " +
						typeLiteral(arrayType.getDimensions()) + ')');
				writer.write(nextTemp() + " = bitcast " + typeText(Type.OBJECT,
						temp(1)) + " to" + dimsType + '*');
				writer.write("store" + dimsType + ' ' + temp(2) + ',' +
						dimsType + "* " + temp(0));
				writer.write(nextTemp() + " = getelementptr inbounds" +
						dimsType + "* " + temp(1) + ", i32 0, i32 0");
				writer.write(nextTemp() + " = insertvalue " + type(intArray) +
						" { " + type(Type.INT) + "* null, [1 x " +
						type(Type.INT) + "] [" +
						typeLiteral(arrayType.getDimensions()) + "] }, " +
						typeTemp(Type.INT, 1, true) + ", 0");
				writer.write(nextTemp() + " = extractvalue " + typeText(srcType,
						srcName) + ", 0");
				writer.write(nextTemp() + " = bitcast " +
						typeTemp(arrayType.getBaseType(), 1, true) + " to " +
						type(Type.OBJECT));
				writer.write(nextTemp() + " = call noalias " +
						type(Type.OBJECT) + " @" + raw(Type.CLASS,
						"_Mallocate") + '(' + typeText(Type.CLASS,
						classOf(Type.ARRAY)) + ')');
				writer.write(nextTemp() + " = call " + type(Type.ARRAY) + " @" +
						raw(Type.ARRAY, "_Mcreate" + new ArrayType(Type.INT).
						getMangledName() + Type.OBJECT.getMangledName()) + '(' +
						typeTemp(Type.OBJECT, 1) + ", " +
						typeSymbol(baseClass) + ", " +
						typeTemp(new ArrayType(Type.INT), 4) + ", " +
						typeTemp(Type.OBJECT, 2) + ')');
				srcType = new SimpleModifiedType(Type.ARRAY);
				srcName = temp(0);

//				TACClass baseClass = new TACClass(arrayType.getBaseType(),
//						method);
//				TACMethodRef createMethod = new TACMethodRef(baseClass,
//						Type.ARRAY.getMethod("create", 2));
//				TACCall create = new TACCall(createMethod
//						new TACMethodRef(Type.ARRAY.getMethod("create", 2)),
//						);
			}
			else
			{
				if (!srcType.getType().equals(Type.ARRAY))
				{
					writer.write(nextTemp() + " = bitcast " + typeText(srcType,
							srcName) + " to " + type(Type.ARRAY));
					srcType = new SimpleModifiedType(Type.ARRAY);
					srcName = temp(0);
				}
				ArrayType arrayType = (ArrayType)destType.getType();
				String baseType = type(arrayType.getBaseType()) + '*',
						dimsType = " [" + arrayType.getDimensions() + " x " +
								type(Type.INT) + ']';
				writer.write(nextTemp() + " = getelementptr inbounds " +
								typeText(srcType, srcName) + ", i32 0, i32 3");
				writer.write(nextTemp() + " = load " + type(Type.OBJECT) +
						"* " + temp(1));
				writer.write(nextTemp() + " = bitcast " + typeTemp(Type.OBJECT,
						1) + " to " + baseType);
				writer.write(nextTemp() + " = insertvalue " + type(destType) +
						" undef, " + baseType + ' ' + temp(1) + ", 0");
				writer.write(nextTemp() + " = getelementptr inbounds " +
						typeText(srcType, srcName) + ", i32 0, i32 1, i32 0");
				writer.write(nextTemp() + " = load " + type(Type.INT) + "** " +
						temp(1));
				writer.write(nextTemp() + " = bitcast " + type(Type.INT) +
						"* " + temp(1) + " to" + dimsType + '*');
				writer.write(nextTemp() + " = load" + dimsType + "* " +
						temp(1));
				writer.write(nextTemp() + " = insertvalue " + typeTemp(destType,
						5) + ',' + dimsType + ' ' + temp(1) + ", 1");
				srcType = destType;
				srcName = temp(0);
			}
		}
		if (destType.getType().equals(srcType.getType()))
		{
			node.data = srcName;
			return;
		}
		if (srcType.getType().isSigned() && destType.getType().isFloating())
		{
			writer.write(nextTemp(node) + " = sitofp " + typeText(srcType,
					srcName) + ' ' + " to " + type(destType));
			return;
		}
		if (srcType.getType().isUnsigned() && destType.getType().isFloating())
		{
			writer.write(nextTemp(node) + " = uitofp " + typeText(srcType,
					srcName) + ' ' + " to " + type(destType));
			return;
		}
		if (srcType.getType().isFloating() && destType.getType().isSigned())
		{
			writer.write(nextTemp(node) + " = fptosi " + typeText(srcType,
					srcName) + ' ' + " to " + type(destType));
			return;
		}
		if (srcType.getType().isFloating() && destType.getType().isUnsigned())
		{
			writer.write(nextTemp(node) + " = fptoui" + typeText(srcType,
					srcName) + ' ' + " to " + type(destType));
			return;
		}
		int srcWidth = Type.getWidth(srcType),
				destWidth = Type.getWidth(destType);
		String instruction;
		if (destWidth > srcWidth)
			instruction = destType.getType().isSigned() ? "sext" :
					destType.getType().isUnsigned() ? "zext" : "fpext";
		else if (destWidth < srcWidth)
			instruction = "trunc";
		else
			instruction = "bitcast";
		writer.write(nextTemp(node) + " = " + instruction + ' ' +
				typeText(srcType, srcName) + " to " + type(destType));
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
				'(' + typeSymbol(node.getBaseClass()) + ", " +
				typeSymbol(node.getTotalSize()) + ')');
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
		writer.write(nextTemp(node) + " = " + instruction + ' ' + typeText(node,
				first) + ", " + symbol(node.getOperand()));
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
		writer.write(sb.append(instruction).append(' ').append(typeSymbol(type,
				node.getFirst())).append(", ").append(symbol(node.getSecond())).
				toString());
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
	public void visit(TACNot node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = xor " +
				typeSymbol(node.getOperand()) + ", true");
	}
	@Override
	public void visit(TACSame node) throws ShadowException
	{
		Type type = node.getOperand(0).getType();
		String op = type.isIntegral() || !type.isPrimitive() ?
				"icmp eq " : "fcmp oeq ";
		writer.write(nextTemp(node) + " = " + op + typeSymbol(
				node.getOperand(0)) + ", " + symbol(node.getOperand(1)));
	}

	@Override
	public void visit(TACLoad node) throws ShadowException
	{
		TACReference reference = node.getReference();
		if (reference instanceof TACPropertyRef)
		{
			TACPropertyRef property = (TACPropertyRef)reference;
			TACMethodRef method = new TACMethodRef(property.getPrefix(),
					property.getType().getGetter().getMethodType(), property.getName());
			TACCall call = new TACCall(method.getNext(), property.getBlock(),
					method, Collections.singletonList(property.getPrefix()));
			walk(call);
			node.data = symbol(call);
		}
		else
			writer.write(nextTemp(node) + " = load " +
					typeSymbol(reference.getGetType(), reference, true));
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
			TACMethodRef method = new TACMethodRef(property.getPrefix(),
					property.getType().getSetter().getMethodType(), property.getName());
			walk(new TACCall(method.getNext(), property.getBlock(), method,
					Arrays.asList(property.getPrefix(), node.getValue())));
		}
		else
			writer.write("store " + typeSymbol(node.getValue()) + ", " +
					typeSymbol(reference.getSetType(), reference, true));
	}

	@Override
	public void visit(TACPhi node) throws ShadowException
	{
		TACPhiRef phi = node.getRef();
		StringBuilder sb = new StringBuilder(nextTemp(phi)).
				append(" = phi i8*");
		for (int i = 0; i < phi.getSize(); i++)
			sb.append(" [ blockaddress(").append(name(method)).append(", ").
					append(symbol(phi.getValue(i))).append("), ").
					append(symbol(phi.getLabel(i))).append(" ],");
		writer.write(sb.deleteCharAt(sb.length() - 1).toString());
	}

	@Override
	public void visit(TACBranch node) throws ShadowException
	{
		if (node.isConditional())
			writer.write("br " + typeSymbol(node.getCondition()) + ", label " +
					symbol(node.getTrueLabel()) + ", label " +
					symbol(node.getFalseLabel()));
		else if (node.isDirect())
			writer.write("br label " + symbol(node.getLabel()));
		else if (node.isIndirect())
		{
			StringBuilder sb = new StringBuilder("indirectbr i8* ").
					append(symbol(node.getOperand())).append(", [ ");
			TACDestination dest = node.getDestination();
			for (int i = 0; i < dest.getNumPossibilities(); i++)
				sb.append("label ").append(symbol(dest.getPossibility(i))).
						append(", ");
			writer.write(sb.delete(sb.length() - 2, sb.length()).append(" ]").
					toString());
		}
	}

	@Override
	public void visit(TACLabel node) throws ShadowException
	{
		writer.writeLeft(name(node) + ':');
	}

	@Override
	public void visit(TACCall node) throws ShadowException
	{
		TACMethodRef method = node.getMethod();
		StringBuilder sb = new StringBuilder(node.getBlock().hasLandingpad() ?
				"invoke" : "call").append(' ').
				append(methodType(method)).append(' ').
				append(symbol(method)).append('(');
		boolean first = true;
		for (TACOperand param : node.getParameters())
			if (first)
			{
				first = false;
				/*Type paramType = param.getType();
				if (paramType instanceof InterfaceType)
				{
					writer.write(nextTemp() + " = extractvalue " +
							typeSymbol(param) + ", 1");
					sb.append(type(Type.OBJECT) + ' ' + temp(0));
				}
				else if (paramType.isPrimitive())
				{
					writer.write(nextTemp() + " = call noalias " +
							type(Type.OBJECT) + " @" + raw(Type.CLASS,
							"_Mallocate") + '(' + type(Type.CLASS) + ' ' +
							classOf(paramType) + ')');
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
				else*/
					sb.append(typeSymbol(param));
			}
			else
				sb.append(", ").append(typeSymbol(param));
		if (!method.getReturnTypes().isEmpty())
			sb.insert(0, nextTemp(node) + " = ");
		writer.write(sb.append(')').toString());
		if (node.getBlock().hasLandingpad())
		{
			TACLabelRef label = new TACLabelRef();
			visit(label);
			writer.indent(2);
			writer.write(" to label " + symbol(label) + " unwind label " +
					symbol(node.getBlock().getLandingpad()));
			visit(label.new TACLabel());
			writer.outdent(2);
		}
	}

	@Override
	public void visit(TACInit node) throws ShadowException
	{
		String className = raw(node.getThisType(), "_Mclass"),
				objectClassName = raw(Type.OBJECT, "_Mclass");
		writer.write(nextTemp() + " = getelementptr inbounds " +
				type(Type.OBJECT) + " %0, i32 0, i32 0");
		writer.write("store %" + objectClassName + "* bitcast (%" + className +
				"* @" + className + " to %" + objectClassName + "*), %" +
				objectClassName + "** " + temp(0));
	}

	@Override
	public void visit(TACReturn node) throws ShadowException
	{
		if (method.getMethod().isCreate())
		{	// FIXME: hack
			writer.write(nextTemp() + " = bitcast " + typeText(Type.OBJECT,
					"%0") + " to %" + raw(node.getReturnValue().getType()) +
					'*');
			writer.write("ret %" + raw(node.getReturnValue().getType()) +
					"* " + temp(0)); // FIXME: hack
		}
		else if (node.hasReturnValue())
			writer.write("ret " + typeSymbol(node.getReturnValue()));
		else
			writer.write("ret void");
	}

	@Override
	public void visit(TACThrow node) throws ShadowException
	{
		writer.write((node.getBlock().hasLandingpad() ?
				"invoke" : "call") + " void @__shadow_throw(" +
				typeSymbol(node.getException()) + ") noreturn");
		if (node.getBlock().hasLandingpad())
		{
			TACLabelRef label = new TACLabelRef();
			visit(label);
			writer.indent(2);
			writer.write(" to label " + symbol(label) + " unwind label " +
					symbol(node.getBlock().getLandingpad()));
			visit(label.new TACLabel());
			writer.outdent(2);
		}
		writer.write("unreachable");
	}

	@Override
	public void visit(TACTypeId node) throws ShadowException
	{
		if (node.getOperand() instanceof TACUnwind)
			writer.write(nextTemp(node) + " = extractvalue { i8*, i32 } " +
					symbol(node.getOperand()) + ", 1");
		else
		{
			writer.write(nextTemp() + " = bitcast " +
					typeSymbol(node.getOperand()) + " to i8*");
			writer.write(nextTemp(node) + " = tail call i32 " +
					"@llvm.eh.typeid.for(i8* " + temp(1) + ") nounwind");
		}
	}

	@Override
	public void visit(TACLandingpad node) throws ShadowException
	{
		writer.write(nextTemp() + " = landingpad { i8*, i32 } " +
				"personality i32 (...)* @__shadow_personality_v0");
		writer.indent(2);
		if (node.getBlock().hasCleanup())
			writer.write("cleanup");
		for (TACBlock block = node.getBlock(); block != null;
				block = block.getParent())
			for (int i = 0; i < block.getNumCatches(); i++)
				writer.write("catch " + type(Type.CLASS) + ' ' +
						classOf(block.getCatchNode(i).getType()));
		writer.outdent(2);
		writer.write("store { i8*, i32 } " + temp(0) +
				", { i8*, i32 }* %_exception");
	}

	@Override
	public void visit(TACUnwind node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = load { i8*, i32 }* %_exception");
		String type = nextTemp();
		writer.write(type + " = extractvalue { i8*, i32 } " + temp(1) + ", 0");
		TACLabelRef label = node.getBlock().getLandingpad();
		for (int i = 0; i < node.getBlock().getNumCatches(); i++)
		{
			TACCatch catchNode = node.getBlock().getCatchNode(i);
			writer.write(nextTemp() + " = tail call i32 @llvm.eh.typeid.for(" +
					"i8* bitcast (" + type(Type.CLASS) + ' ' +
					classOf(catchNode.getType()) + " to i8*)) nounwind");
			writer.write(nextTemp() + " = icmp ne i32 " + type + ", " +
					temp(1));
			label = new TACLabelRef();
			visit(label);
			writer.write("br i1 " + temp(0) + ", label " + symbol(label) +
					", label " + symbol(node.getBlock().getCatch(i)));
			visit(label.new TACLabel());
		}
		// TODO: cleanup???
	}

	@Override
	public void visit(TACCatch node) throws ShadowException
	{
		writer.write(nextTemp() + " = getelementptr inbounds { i8*, i32 }* " +
				"%_exception, i32 0, i32 0");
		writer.write(nextTemp() + " = load i8** " + temp(1));
		writer.write(nextTemp() + " = call " + type(Type.EXCEPTION) +
				" @__shadow_catch(i8* " + temp(1) + ") nounwind");
		writer.write(nextTemp(node) + " = bitcast " + type(Type.EXCEPTION) +
				' ' + temp(1) + " to " + type(node.getType()));
	}
	
	@Override
	public void visit(TACResume node) throws ShadowException
	{
		writer.write(nextTemp() + " = load { i8*, i32 }* %_exception");
		writer.write("resume { i8*, i32 } " + temp(0));
	}

	private static String classOf(Type type)
	{
		if (type instanceof InterfaceType)
			return '@' + raw(type, "_Mclass");
		return "getelementptr inbounds (%" + raw(type, "_Mclass") + "* @" +
				raw(type, "_Mclass") + ", i32 0, i32 0)";
	}

	private static String methodType(TACMethodRef method)
	{
		return methodToString(method, false, true) + '*';
	}
	private static String methodToString(TACMethod method)
	{
		return methodToString(method.getMethod(), true, true);
	}
	private static String methodToString(TACMethodRef method, boolean name,
			boolean parameters)
	{
		StringBuilder sb = new StringBuilder();
		if (name && (method.getModifiers().isPrivate() || method.isWrapper()))
			sb.append("private ");
		boolean primitiveCreate = !method.getPrefixType().isSimpleReference() &&
				method.isCreate();
		if (primitiveCreate)
			sb.append('%').append(raw(method.getPrefixType())).append('*');
		else
		{
			if (method.isVoid())
				sb.append("void");
			else if (method.isSingle())
				sb.append(type(method.getSingleReturnType()));
			else
				sb.append(type(method.getSequenceReturnTypes()));
		}
		sb.append(' ');
		if (name)
			sb.append(name(method));
		if (parameters)
		{
			sb.append('(');
			boolean first = true;
			for (int i = 0; i < method.getParameterCount(); i++)
			{
				if (first)
				{
					first = false;
					if (primitiveCreate)
						sb.append('%').append(raw(method.getParameterType(0))).
								append('*');
					else
						sb.append(type(method.getParameterType(i)));
					if (name && method.isCreate())
						sb.append(" returned");
				}
				else
					sb.append(", ").append(type(method.getParameterType(i)));
			}
			sb.append(')');
		}
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
//		if (!type.getModifiers().isTypeName() &&
//				type.getType() instanceof TypeParameter)
//			return type(Type.CLASS);
		return type(type.getType());
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
		return "{ %" + raw(type, "_Mclass") + "*, " + type(Type.OBJECT) + " }";
	}
	private static String type(TypeParameter type)
	{
		return type(Type.OBJECT);
	}

	private static String raw(ModifiedType type)
	{
		return raw(type.getType(), "");
	}
	private static String raw(ModifiedType type, String extra)
	{
		return raw(type.getType(), extra);
	}
	private static String raw(Type type)
	{
		return raw(type, "");
	}
	private static String raw(Type type, String extra)
	{
		return '\"' + type.getMangledName() + extra + '\"';
	}

	/*private static String wrapType(ModifiedType type, ModifiedType wrappedType)
	{
		if (Type.getWidth(type) == Type.getWidth(wrappedType))
			return type(type);
		return '%' + raw(type) + '*';
	}
	private static String wrapType(SequenceType type, SequenceType wrappedType)
	{
		if (type.size() != wrappedType.size())
			throw new IllegalArgumentException();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < type.size(); i++)
			sb.append(", ").append(wrapType(type.get(i), wrappedType.get(i)));
		return sb.replace(0, 2, "{ ").append(" }").toString();
	}*/

	private static String name(TACLabel label)
	{
		Object name = label.getRef().data;
		if (name instanceof String)
			return ((String)name).substring(1);
		throw new NullPointerException();
	}
	private static String name(TACVariable var)
	{
		String name = var.getName();
		if (name == null)
			throw new NullPointerException();
		return name;
	}
	private static String name(TACConstantRef constant)
	{
		return Type.mangle(new StringBuilder("@\"").
				append(constant.getPrefixType().getMangledName()).append("_M"),
				constant.getName()).append('\"').toString();
	}
	private static String name(TACConstant constant)
	{
		return Type.mangle(new StringBuilder("@\"").
				append(constant.getPrefixType().getMangledName()).append("_M"),
				constant.getName()).append('\"').toString();
	}
	private static String name(TACMethodRef method)
	{
		StringBuilder sb = Type.mangle(new StringBuilder("@\"").
				append(method.getPrefixType().getMangledName()).append("_M"),
				method.getName());
		for (ModifiedType paramType : method.getType().getParameterTypes())
			sb.append(paramType.getType().getMangledName());
		if (method.isWrapper())
			sb.append("_W");
		return sb.append('\"').toString();
	}
	private static String name(TACMethod method)
	{
		return name(method.getMethod());
	}

	private static String typeName(TACVariable variable)
	{
		return typeText(variable, '%' + name(variable), true);
	}

	private static String symbol(TACOperand node)
	{
		Object symbol = node.data;
		if (symbol instanceof String)
			return (String)symbol;
		throw new NullPointerException();
	}
	private static String typeSymbol(TACOperand node)
	{
		return typeSymbol(node, node);
	}
	private static String typeSymbol(TACOperand node, boolean reference)
	{
		return typeSymbol(node, node, reference);
	}
	private static String typeSymbol(Type type, TACOperand node)
	{
		return typeText(type, symbol(node));
	}
	private static String typeSymbol(ModifiedType type, TACOperand node)
	{
		return typeText(type, symbol(node));
	}
	private static String typeSymbol(ModifiedType type, TACOperand node,
			boolean reference)
	{
		return typeText(type, symbol(node), reference);
	}

	private String literal(ShadowValue value)
	{
		if (value == ShadowValue.NULL)
			return "null";
		if (value instanceof ShadowBoolean)
			return literal((boolean)((ShadowBoolean)value).getValue());
		if (value instanceof ShadowByte)
			return literal((long)((ShadowByte)value).getValue());
		if (value instanceof ShadowShort)
			return literal((long)((ShadowShort)value).getValue());
		if (value instanceof ShadowInt)
			return literal((long)((ShadowInt)value).getValue());
		if (value instanceof ShadowLong)
			return literal((long)((ShadowLong)value).getValue());
		if (value instanceof ShadowUByte)
			return literal((long)((ShadowUByte)value).getValue());
		if (value instanceof ShadowUShort)
			return literal((long)((ShadowUShort)value).getValue());
		if (value instanceof ShadowUInt)
			return literal((long)((ShadowUInt)value).getValue());
		if (value instanceof ShadowULong)
			return literal((long)((ShadowULong)value).getValue());
		if (value instanceof ShadowCode)
			return literal((long)((ShadowCode)value).getValue());
		if (value instanceof ShadowFloat)
			return literal((double)((ShadowFloat)value).getValue());
		if (value instanceof ShadowDouble)
			return literal((double)((ShadowDouble)value).getValue());
		if (value instanceof ShadowString)
			return literal((String)((ShadowString)value).getValue());
		throw new UnsupportedOperationException(value.getClass().toString());
	}
	private String literal(boolean value)
	{
		return Boolean.toString(value);
	}
	private String literal(long value)
	{
		return Long.toString(value);
	}
	private String literal(double value)
	{
		long raw = Double.doubleToRawLongBits(value);
		StringBuilder sb = new StringBuilder("0x");
		for (int i = 0; i < 16; i++, raw <<= 4)
			sb.append(Character.forDigit((int)(raw >> 60) & 15, 16));
		return sb.toString();
	}
	private String literal(String value)
	{
		int index = stringLiterals.indexOf(value);
		if (index == -1)
		{
			index = stringLiterals.size();
			stringLiterals.add(value);
		}
		return "@_string" + index;
	}
	private String typeLiteral(boolean value)
	{
		return typeText(Type.BOOLEAN, literal(value));
	}
	private String typeLiteral(int value)
	{
		return typeText(Type.INT, literal(value));
	}
	private String typeLiteral(String value)
	{
		return typeText(Type.STRING, literal(value));
	}
	private String typeLiteral(ShadowValue value)
	{
		return typeText(value, literal(value));
	}

	private String typeTemp(Type type, int offset)
	{
		return typeText(type, temp(offset));
	}
	private String typeTemp(Type type, int offset, boolean reference)
	{
		return typeText(type, temp(offset), reference);
	}
	private String typeTemp(ModifiedType type, int offset)
	{
		return typeText(type, temp(offset));
	}
	private String typeTemp(ModifiedType type, int offset, boolean reference)
	{
		return typeText(type, temp(offset), reference);
	}

	private static String typeText(Type type, String name)
	{
		return combine(type(type), name, false);
	}
	private static String typeText(Type type, String name, boolean reference)
	{
		return combine(type(type), name, reference);
	}
	private static String typeText(ModifiedType type, String name)
	{
		return combine(type(type), name, false);
	}
	private static String typeText(ModifiedType type, String name,
			boolean reference)
	{
		return combine(type(type), name, reference);
	}

	private static String combine(String type, String name, boolean reference)
	{
		StringBuilder sb = new StringBuilder(type);
		if (reference)
			sb.append('*');
		return sb.append(' ').append(name).toString();
	}
}
