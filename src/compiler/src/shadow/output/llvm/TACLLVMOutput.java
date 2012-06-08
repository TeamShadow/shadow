package shadow.output.llvm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.output.TabbedLineWriter;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.tac.AbstractTACVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACComparison;
import shadow.tac.nodes.TACGetLength;
import shadow.tac.nodes.TACIndexed;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACPhiBranch;
import shadow.tac.nodes.TACPrefixed;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariable;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACLLVMOutput extends AbstractTACVisitor {
	private int tempCounter, returnCounter;
	private Map<String, Integer> loadCounter;
	private TabbedLineWriter llvmWriter, curWriter;
	private String llvmFileName;
	public TACLLVMOutput(TACModule module, File shadowFile)
			throws ShadowException
	{
		super(module);
		loadCounter = new HashMap<String, Integer>();
		try {
			llvmFileName = shadowFile.getAbsolutePath().
					replace(".shadow", ".ll");

			File llvmFile = new File(llvmFileName);
			curWriter = llvmWriter = new TabbedLineWriter(new FileWriter(llvmFile));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			throw new ShadowException(ex.getLocalizedMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new ShadowException(ex.getLocalizedMessage());
		}
	}

	@Override
	public void startFile() throws IOException
	{
		llvmWriter.writeLine("; AUTO-GENERATED FILE, DO NOT EDIT!");
		llvmWriter.writeLine("%boolean = type i1");
		llvmWriter.writeLine("%code = type i32");
		llvmWriter.writeLine("%ubyte = type i8");
		llvmWriter.writeLine("%byte = type i8");
		llvmWriter.writeLine("%ushort = type i16");
		llvmWriter.writeLine("%short = type i16");
		llvmWriter.writeLine("%uint = type i32");
		llvmWriter.writeLine("%int = type i32");
		llvmWriter.writeLine("%ulong = type i64");
		llvmWriter.writeLine("%long = type i64");
		llvmWriter.writeLine("%float = type float");
		llvmWriter.writeLine("%double = type double");
		llvmWriter.writeLine();

		llvmWriter.writeLine("declare noalias i8* @malloc(%int) nounwind");
		llvmWriter.writeLine();

		getType().addReferencedType(Type.CLASS);
		getType().addReferencedType(Type.STRING);
		getType().addReferencedType(Type.ARRAY);
		for (Type type : getType().getAllReferencedTypes())
			if (type instanceof ClassType)
			{
				ClassType classType = (ClassType)type;
				String typeName = type.getFullName();
				StringBuilder sb = new StringBuilder("%\"").append(typeName).
						append("!!methods\" = type { ");
				for (MethodSignature method : classType.getMethodList())
					if (!ModifierSet.isStatic(method.getModifiers()))
						sb.append(typeToString(method.getMethodType())).
								append(", ");
				llvmWriter.writeLine("@\"" + typeName + "!!class\" = " +
						"external constant %\"shadow.standard@Class\"");
				llvmWriter.writeLine(sb.delete(sb.length() - 2, sb.length()).
						append(" }").toString());
				llvmWriter.writeLine("@\"" + typeName +
						"!!methods\" = external constant %\"" + typeName +
						"!!methods\"");
				sb.setLength(0);
				sb.append("%\"").append(typeName).append("\" = type { %\"").
						append(typeName).append("!!methods\"*");
				for (Node node : classType.getFieldList())
					sb.append(", ").append(typeToString(node.getType()));
				llvmWriter.writeLine(sb.append(" }").toString());
				for (List<MethodSignature> methods : classType.getMethodMap().
						values())
					for (MethodSignature method : methods)
						llvmWriter.writeLine("declare " +
								methodSignatureToString(method));
				llvmWriter.writeLine();
			}

		StringBuilder sbType = new StringBuilder("%\"").
				append(getType().getFullName()).
				append("!!methods\" = type { ");
		StringBuilder sbValue = new StringBuilder("@\"").
				append(getType().getFullName()).
				append("!!methods\" = constant %\"").
				append(getType().getFullName()).append("!!methods\" { ");
		for (MethodSignature method : getType().getMethodList())
			if (!ModifierSet.isStatic(method.getModifiers()))
			{
				String typeString = typeToString(method.getMethodType());
				sbType.append(typeString).append(", ");
				sbValue.append(typeString).append(' ').
						append(methodNameToString(method)).append(", ");
			}
		String extendsType = getType().getExtendType() == null ? "null" :
				"@\"" + getType().getExtendType().getFullName() + "!!class\"";
		llvmWriter.writeLine("@\"" + getType().getFullName() + "!!class\" = " +
				"constant %\"shadow.standard@Class\" { " +
				"%\"shadow.standard@Class!!methods\"* " +
				"@\"shadow.standard@Class!!methods\", " +
				"%\"shadow.standard@Class\"* " + extendsType +
				", %\"shadow.standard@String\"* @.str }");
		llvmWriter.writeLine(sbType.delete(sbType.length() - 2,
				sbType.length()).append(" }").toString());
		llvmWriter.writeLine(sbValue.delete(sbValue.length() - 2,
				sbValue.length()).append(" }").toString());
		StringBuilder sb = new StringBuilder("%\"").append(getType().
				getFullName()).append("\" = type { %\"").append(getType().
				getFullName()).append("!!methods\"*");
		for (Node node : getType().getFieldList())
			sb.append(", ").append(typeToString(node.getType()));
		llvmWriter.writeLine(sb.append(" }").toString());
		llvmWriter.writeLine();

		int stringCounter = 0;
		String stringNumber = "";
		for (String string : getModule().getStrings())
		{
			llvmWriter.writeLine("@.data" + stringNumber + " = private " +
					"unnamed_addr constant [" + string.length() + " x i8] c\"" +
					string + '\"');
			llvmWriter.writeLine("@.str" + stringNumber + " = private " +
					"unnamed_addr constant %\"shadow.standard@String\" { " +
					"%\"shadow.standard@String!!methods\"* " +
					"@\"shadow.standard@String!!methods\", %boolean true, " +
					"{ %ubyte*, [1 x %int] } { %ubyte* getelementptr " +
					"inbounds ([" + string.length() + " x i8]* @.data" +
					stringNumber + ", i32 0, i32 0), [1 x %int] [%int " +
					string.length() + "] } }");
			stringNumber = Integer.toString(++stringCounter);
		}
		llvmWriter.writeLine();

		llvmWriter.writeLine("define %\"shadow.standard@Class\"* @\"" +
				getType().getFullName() + "!getClass!" +
				getType().getFullName() + "\"(%\"" +
				getType().getFullName() + "\"*) {");
		llvmWriter.indent();
		llvmWriter.writeLine("ret %\"shadow.standard@Class\"* @\"" +
				getType().getFullName() + "!!class\"");
		llvmWriter.outdent();
		llvmWriter.writeLine('}');
		//llvmWriter.writeLine();
	}
	@Override
	public void endFile() throws IOException
	{
		String llvmNativeFileName = llvmFileName.replace(".ll", ".native.ll");
		File llvmNativeFile = new File(llvmNativeFileName);
		if (llvmNativeFile.exists())
		{
			llvmWriter.writeLine();
			FileReader llvmNativeReader = new FileReader(llvmNativeFile);
			char[] buffer = new char[1024];
			int read; while ((read = llvmNativeReader.read(buffer)) != -1)
				llvmWriter.write(buffer, 0, read);
		}
		llvmWriter.close();
	}

	@Override
	public void startFields() throws IOException
	{
		llvmWriter = new TabbedLineWriter(new Writer() {
			@Override public void write(int c) throws IOException { }
			@Override public void write(char[] cbuf, int off, int len)
					throws IOException { }
			@Override public void flush() throws IOException { }
			@Override public void close() throws IOException { }
		});
	}
	@Override
	public void endFields() throws IOException
	{
		llvmWriter = curWriter;
	}

	@Override
	public void startMethod(TACMethod method) throws IOException
	{
		llvmWriter.writeLine();

		tempCounter = method.getParameterTypes().size() + (method.isStatic() ?
				1 : 2);
		returnCounter = 0;
		loadCounter.clear();

		llvmWriter.writeLine("define " + methodSignatureToString(method) +
				" {");
		//llvmWriter.writeLeftLine(".entry:");
		llvmWriter.indent();

		int parameterCounter = 0;
		if (!method.isStatic())
		{
			String type = typeToString(method.getMethodType().getOuter());
			llvmWriter.writeLine("%this = alloca " + type);
			llvmWriter.writeLine("store " + type + " %" + parameterCounter++ +
					", " + type + "* %this");
		}
		for (String parameter : method.getParameterNames())
		{
			String type = typeToString(method.getSignature().
					getParameterType(parameter).getType());
			llvmWriter.writeLine('%' + parameter + " = alloca " + type);
			llvmWriter.writeLine("store " + type + " %" + parameterCounter++ +
					", " + type + "* %" + parameter);
		}
		//llvmWriter.writeLine();
	}

	@Override
	public void endMethod(TACMethod method) throws IOException
	{
		llvmWriter.outdent();
		llvmWriter.writeLine('}');
	}

	@Override
	public void visit(TACVariable node) throws IOException
	{
		throw new UnsupportedOperationException("TACVariable is being removed from TAC");
	}

	@Override
	public void visit(TACReference node) throws IOException
	{
		if (node.getReference() instanceof TACVariable)
		{
			node.setSymbol("%" + tempCounter++);
			llvmWriter.writeLine(node.getSymbol() + " = load " +
					typeToString(node) + "* %" + node.getReference().
					getSymbol());
		}
		else
			node.setSymbol(node.getReference().getSymbol());
	}

	@Override
	public void visit(TACAllocation node) throws IOException
	{
		if (node.isOnHeap()) {
			//llvmWriter.writeLine();
			//llvmWriter.writeLine("; " + node);

			/*ArrayType arrayType = null;
			int dims;
			if (node.isArray())
			{
				arrayType = (ArrayType)node.getType();
				dims = arrayType.getDimensions();// - 1;
			}
			else
				dims = 0;*/
			/*llvmWriter.writeLine('%' + node.getSymbol() + ".0 = " +
					"insertvalue " + typeToString(arrayType) + " { " +
					typeToString(arrayType.getBaseType()) + "* null, " +
					" }, ");*/
			/*StringBuilder sb = new StringBuilder("%.tmp").
					append(node.getSymbol()).
					append(" = call i8* @malloc(i64 ptrtoint (").
					append(typeToString(arrayType)).
					append("* getelementptr (").
					append(typeToString(arrayType)).
					append("* null, i32 1) to i64))");
			llvmWriter.writeLine(sb.toString());
			llvmWriter.writeLine(nodeToString(node) + " = bitcast i8* %tmp" +
					node.getSymbol() + " to " + typeToString(arrayType) + '*');*/
			if (!node.isArray())
			{
				String alloc = "%" + tempCounter++;
				node.setSymbol("%" + tempCounter++);
				llvmWriter.writeLine(alloc + " = call i8* @malloc(%int " +
						"ptrtoint (" + typeToString(node) + " getelementptr " +
						"inbounds (" + typeToString(node) + " null, i32 1) " +
						"to %int))");
				llvmWriter.writeLine(node.getSymbol() + " = bitcast i8* " +
						alloc + " to " + typeToString(node));
			}
			else
			{
				ArrayType array = (ArrayType)node.getType();
				int dims = array.getDimensions();
				llvmWriter.writeLine("%.size" + node.getSymbol() + " = mul " +
						typeNodeToString(node.getSize()) + ", " +
						sizeToString(array.getBaseType()));
				llvmWriter.writeLine("%.alloc" + node.getSymbol() + " = call " +
						"i8* @malloc(" + typeToString(node.getSize()) +
						" %.size" + node.getSymbol() + ')');
				llvmWriter.writeLine("%.cast" + node.getSymbol() + " = " +
						"bitcast i8* %.alloc" + node.getSymbol() + " to " +
						typeToString(array.getBaseType()) + '*');
				llvmWriter.writeLine("%.val" + node.getSymbol() + ".0 = " +
						"insertvalue " + typeToString(node) + " undef, " +
						typeToString(array.getBaseType()) + "* %.cast" +
						node.getSymbol() + ", 0");
				for (int i = 0; i < dims; i++)
					llvmWriter.writeLine("%.val" + node.getSymbol() + '.' +
							(i + 1) + " = insertvalue [" + dims + " x %int] " +
							(i == 0 ? "undef" : "%.val" + node.getSymbol() +
							'.' + i) + ", " +
							typeNodeToString(node.getSizes(i)) + ", " + i);
				llvmWriter.writeLine('%' + node.getSymbol() + " = " +
						"insertvalue " + typeToString(node) + " %.val" +
						node.getSymbol() + ".0, [" + dims + " x %int] %.val" +
						node.getSymbol() + '.' + dims + ", 1");
			}
			/*sb.append(nodeToString(node));
			sb.append(" = ");
			sb.append("malloc(sizeof(");
			sb.append(typeToString(node, false).trim());
			sb.append(')');
			if (dims != 0)
				sb.append(" + ").append(dims).append(" * sizeof(").append(typeToString(Type.INT)).append(')');
			sb.append(");");
			llvmWriter.writeLine(sb.toString());
			if (node.isArray())
			{
				llvmWriter.writeLine(nodeToString(node) + "->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;");
				llvmWriter.writeLine(nodeToString(node) + "->_Iarray = calloc(" + nodeToString(node.getSize()) +
						", sizeof(" + typeToString(arrayType.getBaseType()).trim() + "));");
				llvmWriter.writeLine(nodeToString(node) + "->_Idims = " + (dims + 1) + ';');
				for (int i = 0; i <= dims; i++)
					llvmWriter.writeLine(nodeToString(node) + "->_Ilengths[" + i + "] = " + nodeToString(node.getSizes(i)) + ';');
			}*/

			//llvmWriter.writeLine();
		}
		else
			llvmWriter.writeLine('%' + node.getSymbol() + " = alloca " +
					typeToString(node));
	}

	@Override
	public void visit(TACIndexed node) throws IOException
	{
		ArrayType array = (ArrayType)node.getPrefix().getType();
		/*llvmWriter.writeLine();
		llvmWriter.writeLine("; " + node);*/
		String arrayPtr = "%" + tempCounter++;
		String ptr = "%" + tempCounter++;
		node.setSymbol("%" + tempCounter++);
		llvmWriter.writeLine(arrayPtr + " = extractvalue " +
				typeNodeToString(node.getPrefix()) + ", 0");
		llvmWriter.writeLine(ptr + " = getelementptr inbounds " +
				typeToString(array.getBaseType()) + "* " + arrayPtr + ", " +
				typeNodeToString(node.getIndex()));
		llvmWriter.writeLine(node.getSymbol() + " = load " +
				typeToString(node) + ' ' + ptr);
		/*llvmWriter.writeLine("%." + node.getSymbol() + ".ptr0 = extractvalue " +
				typeToString(node.getPrefix()) + ' ' +
				nodeToString(node.getPrefix()) + ", 0");
		llvmWriter.writeLine("%." + node.getSymbol() + ".ptr1 = " +
				"getelementptr " + typeToString(array.getBaseType()) + "* %." +
				node.getSymbol() + ".ptr0, " + typeToString(node.getIndex()) +
				' ' + nodeToString(node.getIndex()));
		llvmWriter.writeLine('%' + node.getSymbol() + " = load " +
				typeToString(array.getBaseType()) + "* %." + node.getSymbol() +
				".ptr1");
		llvmWriter.writeLine();*/
	}

	@Override
	public void visit(TACGetLength node) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void visit(TACCast node) throws IOException
	{
		String symbol = node.getSymbol();
		TACNode operand = node.getOperand();
		if (operand.getType() instanceof ArrayType)
		{
			ArrayType array = (ArrayType)operand.getType();
			int dims = array.getDimensions();
			llvmWriter.writeLine();
			llvmWriter.writeLine("; " + node);
			String operandString = typeNodeToString(operand);
			llvmWriter.writeLine("%." + symbol + ".0 = call i8* @malloc(%int add (%int " + sizeToString(Type.ARRAY) + ", %int " + dims * 4 + "))");
			llvmWriter.writeLine("%." + symbol + ".1 = bitcast i8* %." + symbol + ".0 to %\"shadow.standard@Array\"*");
			llvmWriter.writeLine("%." + symbol + ".2 = extractvalue " + operandString + ", 0");
			llvmWriter.writeLine("%." + symbol + ".3 = ptrtoint " + typeToString(array.getBaseType()) + "* %." + symbol + ".2 to %long");
			llvmWriter.writeLine("%." + symbol + ".4 = insertvalue %\"shadow.standard@Array\" { %\"shadow.standard@Array!!methods\"* @\"shadow.standard@Array!!methods\", %\"shadow.standard@Class\"* null, %long undef, { %int*, [1 x %int] } { %int* undef, [1 x %int] [%int " + dims + "] } }, %long %." + symbol + ".3, 2");
			llvmWriter.writeLine("%." + symbol + ".5 = extractvalue " + operandString + ", 1");
			llvmWriter.writeLine("%." + symbol + ".6 = getelementptr inbounds %\"shadow.standard@Array\"* %." + symbol + ".1, i32 1");
			llvmWriter.writeLine("%." + symbol + ".7 = bitcast %\"shadow.standard@Array\"* %." + symbol + ".6 to [" + dims + " x %int]*");
			llvmWriter.writeLine("store [" + dims + " x %int] %." + symbol + ".5, [" + dims + " x %int]* %." + symbol + ".7");
			llvmWriter.writeLine("%." + symbol + ".8 = getelementptr inbounds [" + dims + " x %int]* %." + symbol + ".7, i32 0, i32 0");
			llvmWriter.writeLine("%." + symbol + ".9 = insertvalue %\"shadow.standard@Array\" %." + symbol + ".4, %int* %." + symbol + ".8, 3, 0");
			llvmWriter.writeLine("store %\"shadow.standard@Array\" %." + symbol + ".9, %\"shadow.standard@Array\"* %." + symbol + ".1");
			llvmWriter.writeLine('%' + symbol + " = bitcast %\"shadow.standard@Array\"* %." + symbol + ".1 to " + typeToString(node));

			/*llvmWriter.writeLine("%." + node.getSymbol() + ".alloc = call " +
					"i8* @malloc(%int add (%int ptrtoint (%\"shadow.standard@Array\"* " +
					"getelementptr inbounds (%\"shadow.standard@Array\"* " +
					"null, i32 1) to %int), %int " +
					Type.INT.getSize() * array.getDimensions() + "))");
			llvmWriter.writeLine("%." + node.getSymbol() + ".ptr0 = bitcast " +
					"i8* %." + node.getSymbol() + ".alloc to " +
					"%\"shadow.standard@Array\"*");
			llvmWriter.writeLine("%." + node.getSymbol() + ".ptr1 = " +
					"getelementptr inbounds %\"shadow.standard@Array\"* %." +
					node.getSymbol() + ".ptr0, i32 1");
			llvmWriter.writeLine("%." + node.getSymbol() + ".ptr2 = bitcast " +
					"%\"shadow.standard@Array\"* %." + node.getSymbol() +
					".ptr1 to [" + array.getDimensions() + " x %int]*");
			llvmWriter.writeLine("%." + node.getSymbol() + ".val2 = " +
					"extractvalue " + typeToString(operand) + ' ' +
					nodeToString(operand) + ", 1");
			llvmWriter.writeLine("store [" + dims + " x %int] %." +
					node.getSymbol() + ".val2, [" + dims + " x %int]* %." +
					node.getSymbol() + ".ptr2");
			llvmWriter.writeLine("%." + node.getSymbol() + ".val3 = " +
					"getelementptr inbounds [" + dims + " x %int]* %." +
					node.getSymbol() + ".ptr2, i32 0, i32 0");
			llvmWriter.writeLine("%." + node.getSymbol() + ".val4 = " +
					"insertvalue " + lengthsValue + ", %int* %." +
					node.getSymbol() + ".val3, 0");
			llvmWriter.writeLine("%." + node.getSymbol() + ".val0 = " +
					"insertvalue %\"shadow.standard@Array\" { " +
					"%\"shadow.standard@Array!!methods\"* " +
					"@\"shadow.standard@Array!!methods\", " +
					"%\"shadow.standard@Class\"* null, " + lengthsType +
					" undef }, " + lengthsType + " %." + node.getSymbol() +
					".val4, 2");
			llvmWriter.writeLine("store %\"shadow.standard@Array\" %." +
					node.getSymbol() + ".val0, %\"shadow.standard@Array\"* %." +
					node.getSymbol() + ".ptr0");
			llvmWriter.writeLine('%' + node.getSymbol() + " = bitcast " +
					"%\"shadow.standard@Array\"* %." + node.getSymbol() +
					".ptr0 to " + typeToString(node));*/
			llvmWriter.writeLine();
		}
		else if (node.getType() instanceof ArrayType)
		{
			ArrayType array = (ArrayType)node.getType();
			int dims = array.getDimensions();
			llvmWriter.writeLine();
			llvmWriter.writeLine("; " + node);
			llvmWriter.writeLine("%." + symbol + ".0 = bitcast " + typeNodeToString(operand) + " to %\"shadow.standard@Array\"*");
			llvmWriter.writeLine("%." + symbol + ".1 = getelementptr inbounds %\"shadow.standard@Array\"* %." + symbol + ".0, i32 0, i32 2");
			llvmWriter.writeLine("%." + symbol + ".2 = load %long* %." + symbol + ".1");
			llvmWriter.writeLine("%." + symbol + ".3 = inttoptr %long %." + symbol + ".2 to " + typeToString(array.getBaseType()) + '*');
			llvmWriter.writeLine("%." + symbol + ".4 = getelementptr inbounds %\"shadow.standard@Array\"* %." + symbol + ".0, i32 0, i32 3, i32 0");
			llvmWriter.writeLine("%." + symbol + ".5 = load %int** %." + symbol + ".4");
			llvmWriter.writeLine("%." + symbol + ".6 = bitcast %int* %." + symbol + ".5 to [" + dims + " x %int]*");
			llvmWriter.writeLine("%." + symbol + ".7 = load [" + dims + " x %int]* %." + symbol + ".6");
			llvmWriter.writeLine("%." + symbol + ".8 = insertvalue " + typeToString(array) + " undef, " + typeToString(array.getBaseType()) + "* %." + symbol + ".3, 0");
			llvmWriter.writeLine('%' + symbol + " = insertvalue " + typeToString(array) + " %." + symbol + ".8, [" + dims + " x %int] %." + symbol + ".7, 1");
			llvmWriter.writeLine();
		}
		else
		{
			StringBuilder sb = new StringBuilder().append('%').append(symbol).
					append(" = ");
			int operandSize = operand.getType().getSize(), resultSize =
					node.getType().getSize();
			if (resultSize < operandSize)
				sb.append("trunc ");
			else if (resultSize > operandSize)
				sb.append(node.getType().isSigned() ? "sext " : "zext ");
			else
				sb.append("bitcast ");
			sb.append(typeToString(node.getOperand())).append(' ').
					append(nodeToString(node.getOperand())).append(" to ").
					append(typeToString(node));
			llvmWriter.writeLine(sb.toString());
		}
	}

	@Override
	public void visit(TACAssign node) throws IOException
	{
		TACNode variable = node.getFirstOperand(), value = node.
				getSecondOperand();
		if (variable instanceof TACPrefixed && ((TACPrefixed)variable).
				isPrefixed())
		{
			TACNode prefix = ((TACPrefixed)variable).getPrefix();
			// FIXME: This could be more efficient
			int index = getType().getFieldList().indexOf(getType().
					getField(variable.getSymbol())) + 1;
			Integer counter = loadCounter.get(variable.getSymbol());
			if (counter == null) counter = 0;
			loadCounter.put(variable.getSymbol(), counter + 1);
			llvmWriter.writeLine("%.store" + counter + '.' +
					variable.getSymbol() + " = getelementptr inbounds " +
					typeToString(prefix) + ' ' + nodeToString(prefix) +
					", i32 0, i32 " + index);
			llvmWriter.writeLine("store " + typeToString(value) + ' ' +
					nodeToString(value) + ", " + typeToString(variable) +
					"* %.store" + counter + '.' + variable.getSymbol());
		} else
			llvmWriter.writeLine("store " + typeNodeToString(value) + ", " +
					typeToString(variable) + "* " + variable.getSymbol());
	}

	@Override
	public void visit(TACUnary node) throws IOException
	{
		node.setSymbol("%" + tempCounter++);
		llvmWriter.writeLine(node.getSymbol() + " = " +
				operatorToString(node.getOperator()) + typeToString(node) +
				(node.getOperator().isNot() ? " -1, " : " 0, ") +
				nodeToString(node.getOperand()));
	}

	@Override
	public void visit(TACBinary node) throws IOException
	{
		node.setSymbol("%" + tempCounter++);
		llvmWriter.writeLine(node.getSymbol() + " = " +
				operatorToString(node.getOperator(),
						node.getType().isSigned()) + typeToString(node) + ' ' +
				nodeToString(node.getFirstOperand()) + ", " +
				nodeToString(node.getSecondOperand()));
	}

	@Override
	public void visit(TACComparison node) throws IOException
	{
		if (node.getFirstOperand().getType() instanceof ArrayType)
		{
			ArrayType array = (ArrayType)node.getFirstOperand().getType();
			if (node.getSecondOperand().getSymbol().equals("null"))
			{
				llvmWriter.writeLine("%.ptr" + node.getSymbol() + " = " +
						"extractvalue " + typeNodeToString(
						node.getFirstOperand()) + ", 0");
				llvmWriter.writeLine('%' + node.getSymbol() + " = " +
						operatorToString(node.getOperator()) +
						typeToString(array.getBaseType()) + "* %.ptr" +
						node.getSymbol() + ", null");
			}
			else
				throw new UnsupportedOperationException("(array comparison)");
		}
		else
			llvmWriter.writeLine('%' + node.getSymbol() + " = " +
					operatorToString(node.getOperator(),
							node.getType().isSigned()) +
					typeNodeToString(node.getFirstOperand()) + ", " +
					nodeToString(node.getSecondOperand()));
	}

	@Override
	public void visit(TACLabel node) throws IOException
	{
		llvmWriter.writeLeftLine(node.getSymbol() + ':');
	}

	@Override
	public void visit(TACBranch node) throws IOException
	{
		if (node.isConditional())
			llvmWriter.writeLine("br " + typeToString(node.getCondition()) +
					' ' + nodeToString(node.getCondition()) + ", label " +
					nodeToString(node.getTrueBranch()) + ", label " +
					nodeToString(node.getFalseBranch()));
		else
			llvmWriter.writeLine("br label " + nodeToString(node.getBranch()));
	}

	@Override
	public void visit(TACPhiBranch node) throws IOException
	{
		visit((TACBranch)node);
	}

	@Override
	public void visit(TACPhi node) throws IOException
	{
		StringBuilder sb = new StringBuilder().append('%').
				append(node.getSymbol()).append(" = phi ").
				append(typeToString(node));
		for (TACPhiBranch branch : node)
			sb.append(" [ ").append(nodeToString(branch.getValue())).
					append(", %").append(branch.getFromLabel().getSymbol()).
					append(" ],");
		llvmWriter.writeLine(sb.deleteCharAt(sb.length() - 1).toString());
	}

	@Override
	public void visit(TACCall node) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		Type type = node.getType();
		if (type == null)
			sb.append("call void ");
		else
		{
			sb.append('%').append(node.getSymbol()).append(" = call ");
			if (!(type instanceof SequenceType))
				sb.append(typeToString(type)).append(' ');
			else
			{
				sb.append("{ ");
				for (ModifiedType retType : (SequenceType)type)
					sb.append(typeToString(retType.getType())).append(", ");
				sb.replace(sb.length() - 2, sb.length(), " } ");
			}
		}
		sb.append(methodNameToString(node)).append('(');
		if (!ModifierSet.isStatic(node.getMethodType().getModifiers()))
			sb.append(typeToString(node.getPrefix())).append(' ').
					append(nodeToString(node.getPrefix())).append(", ");
		for (TACNode param : node.getParameters())
			sb.append(typeToString(param)).append(' ').
					append(nodeToString(param)).append(", ");
		if (!node.getParameters().isEmpty() ||
				!ModifierSet.isStatic(node.getMethodType().getModifiers()))
			sb.delete(sb.length() - 2, sb.length());
		llvmWriter.writeLine(sb.append(')').toString());
	}

	@Override
	public void visit(TACReturn node) throws IOException
	{
		List<TACNode> returnValues = node.getReturnValue().getNodes();
		if (returnValues.isEmpty())
			llvmWriter.writeLine("ret void");
		else if (returnValues.size() == 1)
			llvmWriter.writeLine("ret " + typeToString(returnValues.get(0)) +
					' ' + nodeToString(returnValues.get(0)));
		else
		{
			String type = typeToString(node.getReturnValue().getType());
			for (TACNode returnValue : returnValues)
				llvmWriter.writeLine("%.ret" + returnCounter +
						" = insertvalue " + type + ' ' + (returnCounter == 0 ?
						"undef" : "%.ret" + (returnCounter - 1)) + ", " +
						typeToString(returnValue) + ' ' +
						nodeToString(returnValue) + ", " + returnCounter++);
			llvmWriter.writeLine("ret " + type + " %.ret" +
						(returnCounter - 1));
		}
	}

	@Override
	public void visit(TACLiteral node) throws IOException
	{
		node.setSymbol(String.valueOf(node.getValue()));
	}

	@Override
	public void visit(TACSequence node) throws IOException { }

	private static String operatorToString(TACUnary.Operator operator)
	{
		switch (operator)
		{
			case PLUS:
				return "add ";
			case MINUS:
				return "sub ";

			case LOGICAL_NOT:
			case BITWISE_NOT:
				return "xor ";

			default:
				throw new UnsupportedOperationException();
		}
	}
	private static String operatorToString(TACBinary.Operator operator,
			boolean signed)
	{
		switch (operator)
		{
			case ADD:
				return "add ";
			case SUBTRACT:
				return "sub ";
			case MULTIPLY:
				return "mul ";
			case DIVIDE:
				return signed ? "sdiv " : "udiv ";
			case MODULUS:
				return signed ? "srem " : "urem ";

			case LOGICAL_OR:
			case BITWISE_OR:
				return "or ";
			case BITWISE_XOR:
			case LOGICAL_XOR:
				return "xor ";
			case LOGICAL_AND:
			case BITWISE_AND:
				return "and ";
			
			case LEFT_SHIFT:
				return "shl ";
			case RIGHT_SHIFT:
				return signed ? "ashr " : "lshr ";
			
			case LEFT_ROTATE:
			case RIGHT_ROTATE:
			default:
				throw new UnsupportedOperationException();
		}
	}
	private static String operatorToString(TACComparison.Operator operator)
	{
		switch (operator)
		{
			case EQUAL:
				return "icmp eq ";
			case NOT_EQUAL:
				return "icmp ne ";
			default:
				throw new UnsupportedOperationException();
		}
		
	}
	private static String operatorToString(TACComparison.Operator operator,
			boolean signed)
	{
		switch (operator)
		{
			case EQUAL:
				return "icmp eq ";
			case NOT_EQUAL:
				return "icmp ne ";
			case GREATER_THAN:
				return signed ? "icmp sgt " : "icmp ugt ";
			case GREATER_THAN_OR_EQUAL:
				return signed ? "icmp sge " : "icmp uge ";
			case LESS_THAN:
				return signed ? "icmp slt " : "icmp ult ";
			case LESS_THAN_OR_EQUAL:
				return signed ? "icmp sle " : "icmp ule ";
			default:
				throw new UnsupportedOperationException();
		}
	}

	private static String typeNodeToString(TACNode node) throws IOException
	{
		return typeToString(node) + ' ' + nodeToString(node);
	}

	private static String typeToString(TACNode node)
	{
		return typeToString(node.getType());
	}
	@SuppressWarnings("unused")
	private static String typeToString(TACNode node, boolean ref)
	{
		return typeToString(node.getType(), ref);
	}
	private static String typeToString(Type type)
	{
		return typeToString(type, true);
	}
	private static String typeToString(Type type, boolean ref)
	{
		if (type instanceof ArrayType)
		{
			ArrayType array = (ArrayType)type;
			return "{ " + typeToString(array.getBaseType()) + "*, [" +
					array.getDimensions() + " x %int] }";
		}
		else if (type instanceof SequenceType)
		{
			SequenceType sequence = (SequenceType)type;
			StringBuilder sb = new StringBuilder();
			if (sequence.isEmpty())
				sb.append("void");
			else if (sequence.size() == 1)
				sb.append(typeToString(sequence.getType(0)));
			else
			{
				sb.append("{ ");
				for (ModifiedType valueType : sequence)
					sb.append(typeToString(valueType.getType())).append(", ");
				sb.replace(sb.length() - 2, sb.length(), " }");
			}
			return sb.toString();
		}
		else if (type instanceof MethodType)
		{
			MethodType method = (MethodType)type;
			StringBuilder sb = new StringBuilder(typeToString(method.
					getReturnTypes())).append(" (");
			SequenceType parameterTypes = method.getParameterTypes();
			if (!ModifierSet.isStatic(method.getModifiers()))
			{
				sb.append(typeToString(method.getOuter()));
				for (ModifiedType parameterType : parameterTypes)
					sb.append(", ").append(typeToString(parameterType.
							getType()));
			}
			else if (!parameterTypes.isEmpty())
			{
				for (ModifiedType parameterType : parameterTypes)
					sb.append(typeToString(parameterType.getType())).
							append(", ");
				sb.delete(sb.length() - 2, sb.length());
			}
			return sb.append(")*").toString();
		}
		else
		{
			if (type == Type.NULL)
				return "%\"shadow.standard@Object\"*";
			if (type.isPrimitive())
				return '%' + type.getTypeName();
			else
				return "%\"" + type.getFullName() + "\"*";
		}
	}

	private static String nodeToString(TACNode node) throws IOException
	{
		/*while (node instanceof TACReference)
			node = ((TACReference)node).getReference();
		if (node instanceof TACPrefixed && !(node instanceof TACCall)) {
			TACPrefixed variable = (TACPrefixed)node;
			if ("class".equals(node.getSymbol()))
				return "@\"" + variable.getPrefix().getType().getFullName() +
						"!!class\"";
			Integer counter = loadCounter.get(node.getSymbol());
			if (counter == null) counter = 0;
			loadCounter.put(node.getSymbol(), counter + 1);
			String name = "%.load" + counter + '.' + node.getSymbol();
			if (variable.isPrefixed() && !(node instanceof TACIndexed))
			{
				llvmWriter.flush();
				// FIXME: This could be more efficient
				int index = getType().getFieldList().indexOf(getType().
						getField(variable.getSymbol())) + 1;
				llvmWriter.writeLine("%.ptr" + counter + '.' +
						node.getSymbol() + " = getelementptr inbounds " +
						typeToString(variable.getPrefix()) + ' ' +
						nodeToString(variable.getPrefix()) + ", i32 0, i32 " +
						index);
				llvmWriter.writeLine(name + " = load " + typeToString(node) +
						"* %.ptr" + counter + '.' + node.getSymbol());
			}
			else
				llvmWriter.writeLine(name + " = load " + typeToString(node) +
						"* %" + node.getSymbol());
			return name;
		}*/
		/*if (node instanceof TACLiteral)
			return String.valueOf(((TACLiteral)node).getValue());
			//return literalToString(node.getSymbol());
		return '%' + node.getSymbol();*/
		return node.getSymbol();
	}

	@SuppressWarnings("unused")
	private String literalToString(String shadowLiteral) throws IOException
	{
		if (shadowLiteral.equals("true") || shadowLiteral.equals("false") ||
				shadowLiteral.equals("null"))
			return shadowLiteral;
		if (shadowLiteral.endsWith("uy") || shadowLiteral.endsWith("us") ||
				shadowLiteral.endsWith("ui") || shadowLiteral.endsWith("ul"))
			return shadowLiteral.substring(0, shadowLiteral.length() - 2);
		if (shadowLiteral.endsWith("u") || shadowLiteral.endsWith("y") ||
				shadowLiteral.endsWith("s") || shadowLiteral.endsWith("i") ||
				shadowLiteral.endsWith("l"))
			return shadowLiteral.substring(0, shadowLiteral.length() - 1);
		return shadowLiteral;
	}

	private static String methodSignatureToString(TACMethod method)
			throws IOException
	{
		return methodSignatureToString(method.getSignature());
	}
	private static String methodSignatureToString(MethodSignature method)
			throws IOException
	{
		StringBuilder sb = new StringBuilder();

		SequenceType retTypes = method.getReturnTypes();
		if (retTypes.isEmpty())
			sb.append("void");
		else if (retTypes.size() == 1)
			sb.append(typeToString(retTypes.getType(0)));
		else
		{
			sb.append("{ ");
			for (ModifiedType type : retTypes)
				sb.append(typeToString(type.getType())).append(", ");
			sb.replace(sb.length() - 2, sb.length(), " }");
		}
		sb.append(' ').append(methodNameToString(method)).append('(');
		if (!ModifierSet.isStatic(method.getModifiers()))
		{
			sb.append(typeToString(method.getMethodType().getOuter()));
			for (ModifiedType param : method.getParameterTypes())
				sb.append(", ").append(typeToString(param.getType()));
		}
		else if (!method.getParameterTypes().isEmpty())
		{
			for (ModifiedType param : method.getParameterTypes())
				sb.append(typeToString(param.getType())).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		return sb.append(')').toString();
	}

	@SuppressWarnings("unused")
	private static String methodNameToString(TACMethod method)
			throws IOException
	{
		return methodNameToString(method.getName(), method.getMethodType());
	}
	private static String methodNameToString(MethodSignature method)
			throws IOException
	{
		return methodNameToString(method.getSymbol(), method.getMethodType());
	}
	private static String methodNameToString(TACCall call) throws IOException
	{
		return methodNameToString(call.getMethodName(), call.getMethodType());
	}
	private static String methodNameToString(String methodName,
			MethodType methodType) throws IOException
	{
		StringBuilder sb = new StringBuilder("@\"").append(methodType.getOuter().
				getFullName()).append('!').append(methodName);
		if (!ModifierSet.isStatic(methodType.getModifiers()))
			sb.append('!').append(methodType.getOuter().getFullName());
		for (ModifiedType param : methodType.getParameterTypes())
			sb.append('!').append(param.getType().getFullName());
		return sb.append('\"').toString();
	}

	private static String sizeToString(Type type)
	{
		String typeString = typeToString(type);
		return "ptrtoint (" + typeString + "* getelementptr inbounds (" +
				typeString + "* null, i32 1) to %int)";
	}
}
