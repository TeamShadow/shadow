package shadow.output.llvm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.output.TabbedLineWriter;
import shadow.parser.javacc.ShadowException;
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
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariable;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACLLVMOutput extends AbstractTACVisitor {
	private Map<String, Integer> loadCounter;
	private TabbedLineWriter llvmWriter;
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
			llvmWriter = new TabbedLineWriter(new FileWriter(llvmFile));
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
		int stringCounter = 0;
		for (String string : getModule().getStrings())
			llvmWriter.writeLine("@string" + stringCounter++ + " = constant [" +
					string.length() + " x i8] c\"" + string + '\"');
		llvmWriter.writeLine();
	}
	@Override
	public void endFile() throws IOException
	{
		llvmWriter.close();
	}

	@Override
	public void startFields() throws IOException
	{
		
	}
	@Override
	public void endFields() throws IOException
	{
		
	}

	@Override
	public void startMethod(TACMethod method) throws IOException
	{
		StringBuilder sb = new StringBuilder("define ");

		List<ModifiedType> retTypes = method.getReturnTypes();
		if (retTypes.isEmpty())
			sb.append("void ");
		else if (retTypes.size() == 1)
			sb.append(typeToString(retTypes.get(0).getType())).append(' ');
		else
		{
			sb.append('{');
			for (ModifiedType type : retTypes)
				sb.append(typeToString(type.getType())).append(", ");
			sb.replace(sb.length() - 2, sb.length(), "} ");
		}
		sb.append("@\"").append(getModule().getType().getFullName()).
				append('%').append(method.getName()).append("\"(");
		for (ModifiedType param : method.getParamTypes())
			sb.append(typeToString(param.getType())).append(", ");
		if (!method.getParamTypes().isEmpty())
			sb.delete(sb.length() - 2, sb.length());
		llvmWriter.writeLine(sb.append(") {").toString());
		//llvmWriter.writeLeftLine("entry:");
		llvmWriter.indent();
	}

	@Override
	public void endMethod(TACMethod method) throws IOException
	{
		llvmWriter.outdent();
		llvmWriter.writeLine('}');
		llvmWriter.writeLine();
	}

	@Override
	public void visit(TACVariable node) throws IOException
	{
	}

	@Override
	public void visit(TACAllocation node) throws IOException
	{
		if (node.isOnHeap())
			throw new UnsupportedOperationException();
		else
			llvmWriter.writeLine('%' + node.getSymbol() + " = alloca " +
					typeToString(node));
	}

	@Override
	public void visit(TACCast node) throws IOException
	{
		StringBuilder sb = new StringBuilder('%' + node.getSymbol() + " = ");
		int operandSize = node.getOperand().getType().getSize(),
				resultSize = node.getType().getSize();
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

	@Override
	public void visit(TACAssign node) throws IOException
	{
		llvmWriter.writeLine("store " +
				typeToString(node.getSecondOperand()) + ' ' +
				nodeToString(node.getSecondOperand()) + ", " +
				typeToString(node.getFirstOperand()) + "* %" +
				node.getFirstOperand().getSymbol());
	}

	@Override
	public void visit(TACUnary node) throws IOException
	{
		llvmWriter.writeLine('%' + node.getSymbol() + " = " +
				operatorToString(node.getOperator()) + typeToString(node) +
				(node.getOperator().isNot() ? " -1, " : " 0, ") +
				nodeToString(node.getOperand()));
	}

	@Override
	public void visit(TACBinary node) throws IOException
	{
		llvmWriter.writeLine('%' + node.getSymbol() + " = " +
				operatorToString(node.getOperator(),
						node.getType().isSigned()) +
				typeToString(node) + ' ' +
				nodeToString(node.getFirstOperand()) + ", " +
				nodeToString(node.getSecondOperand()));
	}

	@Override
	public void visit(TACComparison node) throws IOException
	{
		llvmWriter.writeLine('%' + node.getSymbol() + " = " +
				operatorToString(node.getOperator(),
						node.getType().isSigned()) +
				typeToString(node.getFirstOperand()) + ' ' +
				nodeToString(node.getFirstOperand()) + ", " +
				nodeToString(node.getSecondOperand()));
	}

	@Override
	public void visit(TACLabel node) throws IOException
	{
		llvmWriter.writeLeftLine("; %" + node.getSymbol() + ':');
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
		sb.append("@\"").append(node.getMethodType().getOuter().getFullName()).
				append('%').append(node.getMethodName()).append("\"(");
		for (TACNode param : node.getParameters())
			sb.append(nodeToString(param)).append(", ");
		llvmWriter.writeLine(sb.delete(sb.length() - 2, sb.length())
				.append(')').toString());
	}

	@Override
	public void visit(TACReturn node) throws IOException
	{
		StringBuilder sb = new StringBuilder("ret ");
		List<TACNode> retValues = node.getReturnValue().getNodes();
		if (retValues.isEmpty())
			sb.append("void");
		else if (retValues.size() == 1)
			sb.append(typeToString(retValues.get(0))).append(' ').
					append(nodeToString(retValues.get(0)));
		else
		{
			sb.append('{');
			for (TACNode ret : retValues)
				sb.append(nodeToString(ret)).append(", ");
			sb.delete(sb.length() - 2, sb.length()).append('}');
		}
		llvmWriter.writeLine(sb.toString());
	}

	@Override
	public void visit(TACGetLength node) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACIndexed node) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACLiteral node) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACReference node) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TACSequence node) throws IOException {
		// TODO Auto-generated method stub
		
	}

	private String operatorToString(TACUnary.Operator operator)
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
	private String operatorToString(TACBinary.Operator operator,
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
	private String operatorToString(TACComparison.Operator operator,
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

	private static String typeToString(TACNode node)
	{
		return typeToString(node.getType());
	}
	@SuppressWarnings("unused")
	private static String typeToString(TACNode node, boolean ref)
	{
		return typeToString(node.getType(), ref);
	}
	private static String typeToString(Type type) {
		return typeToString(type, true);
	}
	private static String typeToString(Type type, boolean ref) {
		if (type instanceof ArrayType)
		{
			StringBuilder sb = new StringBuilder("{ ");
			ArrayType array = (ArrayType)type;
			sb.append(typeToString(array.getBaseType())).append('*');
			for (int i = 0; i < array.getDimensions(); i++)
				sb.append(", %int");
			return sb.append(" }").toString();
		}
		else
		{
			if (type.isPrimitive())
				return '%' + type.getTypeName();
			else
				return "%\"" + type.getFullName() + '\"';
		}
	}
	
	private String nodeToString(TACNode node) throws IOException
	{
		if (node instanceof TACVariable) {
			Integer counter = loadCounter.get(node.getSymbol());
			if (counter == null) counter = 0;
			loadCounter.put(node.getSymbol(), counter + 1);
			String name = '%' + node.getSymbol() + '.' + counter;
			llvmWriter.writeLine(name + " = load " + typeToString(node) +
					"* %" + node.getSymbol());
			return name;
		}
		if (node instanceof TACLiteral)
			return literalToString(node.getSymbol());
		return '%' + node.getSymbol();
	}

	private String literalToString(String shadowLiteral) throws IOException {
		if (shadowLiteral.endsWith("uy"))
			return shadowLiteral.substring(0, shadowLiteral.length() - 2);
		if (shadowLiteral.endsWith("y"))
			return shadowLiteral.substring(0, shadowLiteral.length() - 1);
		if (shadowLiteral.endsWith("us"))
			return shadowLiteral.substring(0, shadowLiteral.length() - 2);
		if (shadowLiteral.endsWith("s"))
			return shadowLiteral.substring(0, shadowLiteral.length() - 1);
		if (shadowLiteral.endsWith("ui"))
			return shadowLiteral.substring(0, shadowLiteral.length() - 2);
		if (shadowLiteral.endsWith("i"))
			return shadowLiteral.substring(0, shadowLiteral.length() - 1);
		if (shadowLiteral.endsWith("ul"))
			return shadowLiteral.substring(0, shadowLiteral.length() - 2);
		if (shadowLiteral.endsWith("l"))
			return shadowLiteral.substring(0, shadowLiteral.length() - 1);
		return shadowLiteral;
	}
}
