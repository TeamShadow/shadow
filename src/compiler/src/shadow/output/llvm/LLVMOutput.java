package shadow.output.llvm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.output.TabbedLineWriter;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACAbstractVisitor;
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
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

public class LLVMOutput extends TACAbstractVisitor
{
	private Process process;
	private TabbedLineWriter llvm;
	private int tempCounter, labelCounter;
	private List<String> stringLiterals = new LinkedList<String>();
	public LLVMOutput(File file) throws ShadowException
	{
		try
		{
			llvm = new TabbedLineWriter(new File(file.getParentFile(), file.
					getName().replace(".shadow", ".ll")));
		}
		catch (IOException ex)
		{
			throw new ShadowException(ex.getLocalizedMessage());
		}
	}
	public LLVMOutput(boolean mode) throws ShadowException
	{
		if (mode)
			llvm = new TabbedLineWriter(System.out);
		else
		{
			try
			{
				process = new ProcessBuilder("opt", "-S", "-O3").start();
			}
			catch (IOException ex)
			{
				throw new ShadowException(ex.getLocalizedMessage());
			}
			llvm = new TabbedLineWriter(process.getOutputStream());
		}
		llvm.setLineNumbers(mode);
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
		return ".label" + labelCounter++;
	}
	private String nextLabel(TACOperand node)
	{
		String name = nextLabel();
		node.setName(name);
		return name;
	}

	private String nextString(String value)
	{
		String name = "@.str" + stringLiterals.size();
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
		llvm.write("; " + module.getFullName());
		llvm.write();

		llvm.write("%boolean = type i1");
		llvm.write("%byte = type i8");
		llvm.write("%ubyte = type i8");
		llvm.write("%short = type i16");
		llvm.write("%ushort = type i16");
		llvm.write("%int = type i32");
		llvm.write("%uint = type i32");
		llvm.write("%code = type i32");
		llvm.write("%long = type i64");
		llvm.write("%ulong = type i64");
		llvm.write("%float = type float");
		llvm.write("%double = type double");
		llvm.write();

		llvm.write("declare noalias i8* @malloc(i64)");
		llvm.write("declare noalias i8* @calloc(i64, i64)");
		llvm.write();

//		for (Type type : module.getClassType().getAllReferencedTypes())
//			if (type instanceof ClassInterfaceBaseType && !type.isPrimitive())
//				defineClass((ClassInterfaceBaseType)type, false);
//		defineClass(module.getType(), true);

		for (ClassInterfaceBaseType type : module.getReferences())
			if (!type.isPrimitive())
				declareClass(type, type.equals(module.getType()));

		for (ClassInterfaceBaseType type : module.getReferences())
			if (!type.isPrimitive())
				defineClass(type, type.equals(module.getType()));

		llvm.write();

		llvm.write("define " + type(Type.CLASS) + " @\"" + module.getType() +
				"$$getClass\"(" + type(module.getType()) + ") {");
		llvm.indent();
		llvm.write("ret " + type(Type.CLASS) + " @\"" + module.getType() +
				"$class\"");
		llvm.outdent();
		llvm.write('}');
		llvm.write();
	}
	private void declareClass(ClassInterfaceBaseType type, boolean current)
			throws ShadowException
	{
		if (type instanceof ArrayType)
			return;
		String typeName = type.toString();
		String methodsType = '\"' + typeName + "$methods\"";
		StringBuilder sb = new StringBuilder("%\"").append(type)
				.append("\" = type { %").append(methodsType).append('*');
		if (type instanceof SingletonType)
			llvm.write("@\"" + type + "$instance\" = global " + type(type) +
					" null");
		if (type instanceof ClassType)
		{
			List<MethodSignature> methods =
					((ClassType)type).getOrderedMethods();
			llvm.write("%\"" + type + "$methods\" = type " +
					methodList(type, methods, false));
			if (current)
				llvm.write("@\"" + type + "$methods\" = constant %\"" + type +
						"$methods\" " + methodList(type, methods, true));
			else
				llvm.write("@\"" + type + "$methods\" = external constant %\"" +
						type + "$methods\"");

			for (Map.Entry<String, ModifiedType> field : ((ClassType)type).
					getFieldList())
				sb.append(", ").append(type(field.getValue()));
		}
		llvm.write(sb.append(" }").toString());
		if (!current)
			for (List<MethodSignature> methodList : type.getMethodMap().
					values())
				for (MethodSignature method : methodList)
					llvm.write("declare " + methodToString(new TACMethod(
							method)));
		llvm.write();
	}

	private void defineClass(ClassInterfaceBaseType type, boolean current)
			throws ShadowException
	{
		if (type instanceof ArrayType)
			return;
		String typeName = type.toString();
		if (current)
		{
			llvm.write("@\"" + typeName + "$class\" = constant %\"" +
					Type.CLASS + "\" { %\"" + Type.CLASS + "$methods\"* @\"" +
					Type.CLASS + "$methods\", %\"" + Type.STRING + "\"* " +
					nextString(typeName) + ", { " + type(Type.CLASS) +
					"*, [1 x " + type(Type.LONG) + "] } { " + type(Type.CLASS) +
					"* null, [1 x " + type(Type.LONG) + "] [" + type(Type.LONG) +
					" 0] }, " + type(Type.CLASS) + ' ' +
					(!(type instanceof ClassType) || ((ClassType)type).getExtendType() == null ? null :
					"@\"" + ((ClassType) type).getExtendType() + "$class\"") + " }");
		}
		else
			llvm.write("@\"" + typeName + "$class\" = external constant " +
					"%\"shadow.standard@Class\"");
	}

	private String methodList(Type type, List<MethodSignature> methods,
			boolean mode) throws ShadowException
	{
		StringBuilder sb = new StringBuilder("{ ");
		for (MethodSignature methodSignature : methods)
		{
			TACMethod method = new TACMethod(methodSignature);
			if (method.isGetClass())
			{
				sb.append(type(Type.CLASS)).append(" (").append(type(type)).
						append(")*");
				if (mode)
					sb.append(" @\"").append(type).append("$$getClass\"");
			}
			else
			{
				sb.append(type(method, true));
				if (mode)
					sb.append(' ').append(name(method));
			}
			sb.append(", ");
		}
		if (!methods.isEmpty())
			sb.deleteCharAt(sb.length() - 2);
		return sb.append('}').toString();
	}

	@Override
	public void endFile(TACModule module) throws ShadowException
	{
		llvm.write();
		int stringIndex = 0;
		for (String literal : stringLiterals)
		{
			int length = literal.length();
			llvm.write("@.array" + stringIndex + " = private unnamed_addr " +
					"constant [" + length + " x %ubyte] c\"" + literal + '\"');
			llvm.write("@.str" + stringIndex + " = private unnamed_addr " +
					"constant %\"shadow.standard@String\" { " +
					"%\"" + Type.STRING + "$methods\"* " +
					"@\"" + Type.STRING + "$methods\", { %ubyte*, [1 x " +
					"%long] } { %ubyte* getelementptr inbounds ([" + length +
					" x %ubyte]* @.array" + stringIndex + ", i32 0, i32 0), " +
					"[1 x %long] [%long " + length + "] } }");
			stringIndex++;
		}

		if (process != null)
			try
		{
			String line;
			BufferedReader reader;
			process.getOutputStream().close();

			reader = new BufferedReader(new InputStreamReader(process.
					getInputStream()));
			while ((line = reader.readLine()) != null)
				System.out.println(line);
			reader.close();

			reader = new BufferedReader(new InputStreamReader(process.
					getErrorStream()));
			while ((line = reader.readLine()) != null)
				System.err.println(line);
			reader.close();
		}
			catch (IOException ex)
		{
			throw new ShadowException(ex.getLocalizedMessage());
		}
	}

	@Override
	public void startMethod(TACMethod method) throws ShadowException
	{
		if (method.isNative() && !method.isGetClass())
		{
			llvm.write("declare " + methodToString(method));
			llvm.indent();
			return;
		}
		llvm.write("define " + methodToString(method) + " {");
		llvm.indent();
		for (TACVariable local : method.getLocals())
			llvm.write('%' + name(local) + " = alloca " + type(local));
		int paramIndex = 0;
		for (TACVariable param : method.getParameters())
			llvm.write("store " + type(param) + " %" + paramIndex++ + ", " +
					type(param) + "* %" + name(param));
		tempCounter = ++paramIndex;
		if (method.isConstructor())
		{
			ClassInterfaceBaseType type = method.getPrefixType();
			String methodsType = '\"' + type.toString() + "$methods\"";
			llvm.write(nextTemp() + " = load " + type(type) + "* %this");
			llvm.write(nextTemp() + " = getelementptr " + type(type) + ' ' +
					temp(1) + ", i32 0, i32 0");
			llvm.write("store %" + methodsType + "* @" + methodsType + ", %" +
					methodsType + "** " + temp(0));
		}
	}

	@Override
	public void endMethod(TACMethod method) throws ShadowException
	{
		llvm.outdent();
		if (!method.isNative() || method.isGetClass())
			llvm.write('}');
	}

	@Override
	public void visit(TACVariableRef node) throws ShadowException
	{
		node.setName('%' + name(node.getVariable()));
	}

	@Override
	public void visit(TACSingletonRef node) throws ShadowException
	{
		node.setName("@\"" + node.getType() + "$instance\"");
	}

	@Override
	public void visit(TACArrayRef node) throws ShadowException
	{
		llvm.write(nextTemp() + " = extractvalue " + typeName(node.getArray()) +
				", 0");
		llvm.write(nextTemp(node) + " = getelementptr " + type(node) + "* " +
				temp(1) + ", " + typeName(node.getTotal()));
	}

	@Override
	public void visit(TACFieldRef node) throws ShadowException
	{
		llvm.write(nextTemp(node) + " = getelementptr inbounds " +
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
//			llvm.write(temp + " = extractvalue " + typeName(
//					node.getValue()) + ", " + i);
//			llvm.write("store " + type(seq.get(i)) + ' ' + temp + ", " +
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
		node.setName("@\"" + node.getClassType() + "$class\"");
	}

	@Override
	public void visit(TACSequence node) throws ShadowException
	{
		String type = type(node), last = "undef";
		for (int i = 0; i < node.size(); i++, last = temp(0))
			llvm.write(nextTemp(node) + " = insertvalue " + type + ' ' + last +
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
			String lengthsType = "[" + dimensions + " x %long]";
			llvm.write(nextTemp() + " = call i8* @malloc(i64 " +
					sizeof(type(Type.ARRAY)) + ')');
			llvm.write(nextTemp(node) + " = bitcast i8* " + temp(1) + " to " +
					type(Type.ARRAY));
			llvm.write(nextTemp() + " = extractvalue " + typeName(operand) +
					", 0");
			llvm.write(nextTemp() + " = ptrtoint " + type(arrayType.
					getBaseType()) + "* " + temp(1) + " to %long");
			llvm.write(nextTemp() + " = extractvalue " + typeName(operand) +
					", 1");
			llvm.write(nextTemp() + " = call i8* @malloc(i64 " +
					sizeof(lengthsType + '*') + ')');
			llvm.write(nextTemp() + " = bitcast i8* " + temp(1) + " to " +
					lengthsType + '*');
			llvm.write("store " + lengthsType + ' ' + temp(2) + ", " +
					lengthsType + "* " + temp(0));
			llvm.write(nextTemp() + " = getelementptr " + lengthsType + "* " +
					temp(1) + ", i32 0, i32 0");
			llvm.write(nextTemp() + " = insertvalue { %long*, [1 x %long] } " +
					"{ %long* null, [1 x %long] [%long " + dimensions + "] " +
					"}, %long* " + temp(1) + ", 0");
			llvm.write("call void @\"" + Type.ARRAY +
					"$$constructor$long$long[]\"(" + type(Type.ARRAY) + ' ' +
					temp(7) + ", %long " + temp(5) + ", { %long*, [1 x " +
					"%long] } " + temp(0) + ')');
			if (destType.equals(Type.ARRAY))
				return;
			srcType = Type.ARRAY;
		}
		int srcWidth = srcType.getWidth(), destWidth = destType.getWidth();
		String instruction;
		if (destWidth > srcWidth)
			instruction = destType.isSigned() ? "sext" : "zext";
		else if (destWidth < srcWidth)
			instruction = "trunc";
		else
			instruction = "bitcast";
		llvm.write(nextTemp(node) + " = " + instruction + ' ' +
				typeName(operand) + " to " + type(destType));
	}

	@Override
	public void visit(TACNewObject node) throws ShadowException
	{
		llvm.write(nextTemp() + " = call noalias i8* @malloc(i64 " +
				sizeof(type(node.getType())) + ')');
		llvm.write(nextTemp(node) + " = bitcast i8* " + temp(1) + " to " +
				type(node.getType()));
	}

	@Override
	public void visit(TACNewArray node) throws ShadowException
	{
		String type = type(node.getType()),
				base = type(node.getType().getBaseType()) + '*';
		llvm.write(nextTemp() + " = call noalias i8* @calloc(i64 " + name(node.
				getTotalSize()) + ", i64 " + sizeof(base) + ')');
		llvm.write(nextTemp() + " = bitcast i8* " + temp(1) + " to " + base);
		llvm.write(nextTemp() + " = insertvalue " + type + " undef, " + base +
				' ' + temp(1) + ", 0");
		for (int i = 0; i < node.getDimensions(); i++)
			llvm.write(nextTemp(node) + " = insertvalue " + type + ' ' + temp(
					1) + ", " + typeName(node.getDimension(i)) + ", 1, " + i);
	}

	@Override
	public void visit(TACLength node) throws ShadowException
	{
		llvm.write(nextTemp(node) + " = extractvalue " +
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
		llvm.write(nextTemp(node) + " = " + instruction + ' ' + type(node) +
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
		llvm.write(sb.append(instruction).append(' ').append(type(type)).
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
		llvm.write(nextTemp() + " = " + dir + _type_ + first + ", " + second);
		llvm.write(nextTemp() + " = sub" + _type_ + "mul (" + type +
				" ptrtoint (" + type + "* getelementptr (" + type +
				"* null, i32 1) to " + type + ")," + _type_ + "8), " + second);
		llvm.write(nextTemp() + " = " + otherDir + _type_ + first + ", " +
				temp(1));
		llvm.write(nextTemp(node) + " = or" + _type_ + temp(1) + ", " +
				temp(3));
	}
	private void visitConcatenation(TACBinary node) throws ShadowException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(TACLoad node) throws ShadowException
	{
		llvm.write(nextTemp(node) + " = load " +
				typeName(node.getReference(), true));
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
				llvm.write(nextTemp() + " = extractvalue " + name + ", " + i);
				llvm.write("store " + type(seq.get(i)) + ' ' + temp(0) + ", " +
						typeName(seq.get(i), true));
			}
		}
		else
			llvm.write("store " + typeName(node.getValue()) + ", " +
					typeName(reference, true));
	}

	@Override
	public void visit(TACLabel node) throws ShadowException
	{
		llvm.writeLeft(name(node) + ':');
	}

	@Override
	public void visit(TACBranch node) throws ShadowException
	{
		if (node.isConditional())
			llvm.write("br " + typeName(node.getCondition()) + ", label %" +
					name(node.getTrueLabel()) + ", label %" +
					name(node.getFalseLabel()));
		else
			llvm.write("br label %" + name(node.getLabel()));
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
			llvm.write(sb.append(')').toString());
		}
		else
		{
			String methodsType = "%\"" + method.getPrefixType() + "$methods\"*";
			llvm.write(nextTemp() + " = getelementptr " +
					typeName(node.getPrefix()) + ", i32 0, i32 0");
			llvm.write(nextTemp() + " = load " + methodsType + "* " + temp(1));
			llvm.write(nextTemp() + " = getelementptr " + methodsType + ' ' +
					temp(1) + ", i32 0, i32 " + node.getMethod().getIndex());
			String methodTemp = nextTemp();
			llvm.write(methodTemp + " = load " + type(method) + "* " + temp(1));
			StringBuilder sb = new StringBuilder();
			if (method.getReturnCount() != 0)
				sb.append(nextTemp(node)).append(" = ");
			sb.append("call ").append(type(method, false)).append(' ').
					append(methodTemp).append('(');
			for (TACOperand param : node.getParameters())
				sb.append(typeName(param)).append(", ");
			if (method.getParameterCount() > 0)
				sb.delete(sb.length() - 2, sb.length());
			llvm.write(sb.append(')').toString());
		}
	}

	@Override
	public void visit(TACReturn node) throws ShadowException
	{
		if (node.hasReturnValue())
			llvm.write("ret " + typeName(node.getReturnValue()));
		else
			llvm.write("ret void");
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
		return "ptrtoint (" + type + " getelementptr(" + type + " null, i32 " +
				"1) to i64)";
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
		throw new IllegalArgumentException("Unknown type.");
	}
	private static String type(ArrayType type)
	{
		return "{ " + type(type.getBaseType()) + "*, [" + type.
				getDimensions() + " x %long] }";
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
		return "%\"" + type.getFullName() + "\"*";
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
			System.out.print("");//throw new NullPointerException();
		return name;
	}
	private static String name(TACMethod method)
	{
		StringBuilder sb = new StringBuilder("@\"").
				append(method.getPrefixType()).append('$');
		sb.append('$').append(method.getName());
		for (ModifiedType paramType : method.getType().getParameterTypes())
			sb.append('$').append(paramType.getType());
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
	private static String typeName(String type, String name,
			boolean reference)
	{
		StringBuilder sb = new StringBuilder(type);
		if (reference)
			sb.append('*');
		return sb.append(' ').append(name).toString();
	}
}
