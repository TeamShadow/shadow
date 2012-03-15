package shadow.output.c;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import shadow.output.TabbedLineWriter;
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
import shadow.typecheck.MethodSignature;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

/**
 * @author wspeirs
 *
 */
public class TACCVisitor extends AbstractTACVisitor {
	private TabbedLineWriter cWriter, metaWriter, curWriter;
	private String cFileName, metaFileName;
	
	public TACCVisitor(TACModule module, File shadowFile) throws ShadowException
	{
		super(module);
		try {
			cFileName = shadowFile.getAbsolutePath().replace(".shadow", ".c");
			metaFileName = shadowFile.getAbsolutePath().replace(".shadow", ".meta");
			
			File cFile = new File(cFileName);
			File metaFile = new File(metaFileName);
			
			cFile.delete();	// always delete the c file
	
			// make sure the meta file exits
//			if(!metaFile.exists())
//				throw new ShadowException("Meta file does not exist!");

			// create the cWriter always with a new file
			cWriter = new TabbedLineWriter(new FileWriter(cFile, false));
			
			// open the meta file appending data always
			metaWriter = new TabbedLineWriter(new FileWriter(metaFile, false));
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			throw new ShadowException(ex.getLocalizedMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new ShadowException(ex.getLocalizedMessage());
		}
	}
	

	@Override
	public void startFile() {
		// write out the stuff for the C file
		cWriter.writeLine("/* AUTO-GENERATED FILE, DO NOT EDIT! */");
		cWriter.writeLine("#include \"" + getType().getPath() + ".meta\"");	// include the header
		cWriter.writeLine("");
		
		String className = lit2lit('\"' + getType().getFullName() + '\"');
		cWriter.writeLine("struct " + Type.CLASS.getMangledName() + ' ' +
				getType().getMangledName() + "_Iclass = {");
		cWriter.indent();
		cWriter.writeLine('&' + Type.CLASS.getMangledName() + "_Imethods, " + className);
		cWriter.outdent();
		cWriter.writeLine("};");
		cWriter.writeLine("");
		
		String metaShortName = metaFileName == null ? "" : metaFileName.substring(metaFileName.lastIndexOf(File.separatorChar)+1, metaFileName.length());
		metaShortName = metaShortName.replace('.', '_');
		
		// write out the stuff for the meta file
		metaWriter.writeLine("/* AUTO-GENERATED FILE, DO NOT EDIT! */");
		metaWriter.writeLine("#ifndef " + metaShortName.toUpperCase());
		metaWriter.writeLine("#define " + metaShortName.toUpperCase());
		metaWriter.writeLine("");
		metaWriter.writeLine("#include \"types.h\"");
		metaWriter.writeLine("#include \"stdlib.h\"");
		
		ClassType classType = (ClassType)getModule().getClassType();
		Type extendsType = classType.getExtendType();
		if (extendsType != null)
		{
			metaWriter.writeLine("#include \"" + extendsType.getPath() + ".meta\"");
			metaWriter.writeLine("");
		}

		getModule().getClassType().addReferencedType(Type.CLASS);
		for (Type type : getModule().getClassType().getReferenceTypes())
			metaWriter.writeLine("struct " + type.getMangledName() + ';');
		metaWriter.writeLine("");
		
		// create the method structure
		metaWriter.writeLine("struct " + Type.CLASS.getMangledName() + ' ' + getType().getMangledName() + "_Iclass;");
		metaWriter.writeLine("struct " + getType().getMangledName() + "_Itable;");
		metaWriter.writeLine("struct " + getType().getMangledName() + "_Itable " + getType().getMangledName() + "_Imethods;");
		
//		List<TACMethod> methods = getTheClass().getMethods();
//		for(TACMethod method:methods) {
//			// don't need the field init method included
//			if(method.getName().equals("constructor")) {
//				foundConstructor = true;
//				continue;
//			}
//			else if (method.getName().equals("main"))
//			{
//				List<ModifiedType> paramTypes = method.getParamTypes();
//				if (paramTypes.size() == 1)
//				{
//					Type firstArg = paramTypes.get(0).getType();
//					if (firstArg instanceof ArrayType &&
//							((ArrayType)firstArg).getBaseType().equals(Type.STRING))
//					{
//						foundMain = true;
//					}
//				}
//			}
//			
//			StringBuffer sb = new StringBuffer();
//			List<Type> retTypes = method.getReturnTypes();
//			List<ModifiedType> paramTypes = method.getParamTypes();
//			
//			// loop through the ret types
//			
//			if(retTypes.size() != 0)
//				sb.append(typeToString(retTypes.get(0)));
//			else
//				sb.append("void");
//			
//			sb.append(" ");
//			sb.append("(*");
//			sb.append(getTheClass().getType().getMangledName());
//			sb.append(method.getMangledName());
//			sb.append(")(");
//			
//			if (!ModifierSet.isStatic(method.getSignature().getASTNode().getModifiers()))
//			{
//				sb.append(typeToString(getTheClass().getType()));
//				sb.append(" this, ");
//			}
//			
//			for(ModifiedType param:paramTypes) {
//				sb.append(typeToString(param.getType()));
//				sb.append(", ");
//			}
//			
//			if (paramTypes.size() == 0 && ModifierSet.isStatic(method.getSignature().getASTNode().getModifiers())) {
//				sb.append("void  ");
//			}
//			
//			sb.setCharAt(sb.length()-2, ')');
//			sb.setCharAt(sb.length()-1, ';');
//			
//			metaWriter.writeLine(sb.toString());
//		}
		
	}

	@Override
	public void endFile() {

		boolean /*foundConstructor = false,*/ foundMain = false, foundNative = false;
		metaWriter.writeLine(typeToString(Type.CLASS) + ' ' + getType().getMangledName() + "_MgetClass(" + typeToString(getType()) + " this);");
		for ( List<MethodSignature> methods : getType().getMethodMap().values() )
		{
			for ( MethodSignature method : methods )
			{
				/*if ( method.getSymbol().equals("constructor") )
					foundConstructor = true;
				else*/ if ( !(method.getSymbol().equals("getClass") &&
						method.canAccept(Collections.<Type>emptyList())) &&
						ModifierSet.isNative(method.getASTNode().getModifiers()) )
					foundNative = true;
				if ( method.getSymbol().equals("main") &&
						method.canAccept(Arrays.<Type>asList(new ArrayType(Type.STRING, Arrays.<Integer>asList(1)))) )
					foundMain = true;
				
				StringBuffer sb = new StringBuffer();
				List<ModifiedType> retTypes = method.getMethodType().getReturnTypes();
				List<ModifiedType> paramTypes = method.getMethodType().getParameterTypes();
				
				// loop through the ret types
				
				if(retTypes.size() != 0)
					sb.append(typeToString(retTypes.get(0).getType()));
				else
					sb.append("void");
				
				sb.append(' ');
				sb.append(method.getMethodType().getOuter().getMangledName());
				sb.append(method.getMangledName());
				sb.append('(');
				
				if (!ModifierSet.isStatic(method.getASTNode().getModifiers()))
				{
					if ( method.getSymbol().equals("getClass") && method.canAccept(Collections.<Type>emptyList()) )
						sb.append(typeToString(getType()));
					else
						sb.append(typeToString(method.getMethodType().getOuter()));
					sb.append(", ");
				}
				
				for ( ModifiedType param : paramTypes )
					sb.append(typeToString(param.getType())).append(", ");
				
				for ( int i = 1; i < retTypes.size(); i++ )
					sb.append(typeToString(retTypes.get(i).getType())).append("*, ");
				
				if (paramTypes.size() == 0 && ModifierSet.isStatic(method.getASTNode().getModifiers()))
					sb.append("void);");
				else
					sb.replace(sb.length() - 2, sb.length(), ");");
				
				metaWriter.writeLine(sb.toString());
			}
		}
		metaWriter.writeLine("");
		
		metaWriter.writeLine("struct " + getModule().getMangledName() + "_Itable {");
		metaWriter.indent();
		for ( MethodSignature method : /*FIXME?*/((ClassType)getType()).getMethodList() )
		{
			if ( !method.getSymbol().equals("constructor") )
			{
				StringBuffer sb = new StringBuffer();
				List<ModifiedType> retTypes = method.getMethodType().getReturnTypes();
				List<ModifiedType> paramTypes = method.getMethodType().getParameterTypes();
				
				// loop through the ret types
				
				if(retTypes.size() != 0)
					sb.append(typeToString(retTypes.get(0).getType()));
				else
					sb.append("void");
				
				sb.append(" ");
				sb.append("(*");
				sb.append(method.getMangledName());
				sb.append(")(");
				
				if (!ModifierSet.isStatic(method.getASTNode().getModifiers()))
				{
					if ( method.getSymbol().equals("getClass") && method.canAccept(Collections.<Type>emptyList()) )
						sb.append(typeToString(getType()));
					else
						sb.append(typeToString(method.getMethodType().getOuter()));
					sb.append(", ");
//					sb.append(" this, ");
				}
				
				for ( ModifiedType param : paramTypes )
					sb.append(typeToString(param.getType())).append(", ");
				
				for ( int i = 1; i < retTypes.size(); i++ )
					sb.append(typeToString(retTypes.get(i).getType())).append("*, ");
				
				if (paramTypes.size() == 0 && ModifierSet.isStatic(method.getASTNode().getModifiers()))
					sb.append("void);");
				else
					sb.replace(sb.length() - 2, sb.length(), ");");
				
				metaWriter.writeLine(sb.toString());
			}
		}
		metaWriter.outdent();
		metaWriter.writeLine("};");
		metaWriter.writeLine("");
		
		cWriter.writeLine(typeToString(Type.CLASS) + ' ' + getType().getMangledName() + "_MgetClass(" + typeToString(getType()) + " this) {");
		cWriter.indent();
		cWriter.writeLine("return &" + getType().getMangledName() + "_Iclass;");
		cWriter.outdent();
		cWriter.writeLine("}");
		cWriter.writeLine("");
		
		if ( foundNative )
		{
			cWriter.writeLine("#include \"" + getType().getPath() + ".h\"");
			cWriter.writeLine("");
		}
		
		cWriter.writeLine("struct " + getModule().getMangledName() + "_Itable " + getModule().getMangledName() + "_Imethods = {");
		cWriter.indent();
		for ( MethodSignature method : getType().getMethodList() )
		{
			/*if ( method.getSymbol().equals("getClass") &&
					method.canAccept(Collections.<Type>emptyList()) )
			{
				StringBuffer sb = new StringBuffer();
				
				sb.append(' ');
				sb.append(method.getMethodType().getOuter().getMangledName());
				sb.append(method.getMangledName());
				sb.append(',');
				
				metaWriter.writeLine(sb.toString());
			}
			else*/ if ( !method.getSymbol().equals("constructor") )
			{
				StringBuffer sb = new StringBuffer();
				
				if ( method.getSymbol().equals("getClass") && method.canAccept(Collections.<Type>emptyList()) )
					sb.append(getType().getMangledName());
				else
					sb.append(method.getMethodType().getOuter().getMangledName());
				sb.append(method.getMangledName());
				sb.append(',');
				
				cWriter.writeLine(sb.toString());
			}
		}
		
		cWriter.outdent();
		cWriter.writeLine("};");
		cWriter.writeLine("");
		
		if (foundMain)
		{
			/* Here's our main method */
			cWriter.writeLine("int main(int argc, char **argv) {");
			cWriter.indent();
//			cWriter.writeLine("");
//			cWriter.writeLine("for (int i = 0; i < argc; i++) {");
//			cWriter.indent();
//			cWriter.outdent();
//			cWriter.writeLine("}");
			cWriter.writeLine(getModule().getClassType().getMangledName() + "_Mmain_R_Pshadow_Pstandard_CString_A1((struct _Pshadow_Pstandard_CString **)0);");
			cWriter.writeLine("return 0;");
			cWriter.outdent();
			cWriter.writeLine("}");
			cWriter.writeLine("");
		}

		for (Type type : getType().getReferenceTypes())
			metaWriter.writeLine("#include \"" + type.getPath() + ".meta\"");
		metaWriter.writeLine("");
		metaWriter.writeLine("#endif");
	}

	@Override
	public void startFields() {
		TACModule theClass = this.getModule();
		
		metaWriter.writeLine("struct " + theClass.getClassType().getMangledName() + " {");
		metaWriter.indent();
		
//		ClassType type = (ClassType)theClass.getType();
//		if (type.getExtendType() != null)
//			metaWriter.writeLine("struct " + type.getExtendType().getMangledName() + " _Isuper;");
		
		// add the super class (TODO: what do we do for interfaces???)
		/*if(theClass.getExtendClassName() != null)
			metaWriter.writeLine("struct " + theClass.getExtendClassName() + " _super;"); later*/
		
//		List<String> imps = theClass.getImplementsClassNames();
		
//		metaWriter.writeLine("");
//		metaWriter.writeLine("/* Interfaces */");
		
//		// add pointers to all the implementing classes
//		for(String s:imps) {
//			metaWriter.writeLine("struct " + s + " *_" + s.toLowerCase() + ";");
//		}
		
		// add a pointer to the methods for this class
		metaWriter.writeLine("struct " + theClass.getMangledName() + "_Itable *_Imethods;");
		
//		// add in a var to keep track of the reference counts to this object
//		metaWriter.writeLine("");
//		metaWriter.writeLine("unsigned int _ref_count;");	// we might want to move this to Object ONLY
		metaWriter.writeLine("");
		
		metaWriter.writeLine("/* Fields */");
		
		curWriter = metaWriter;
	}

	@Override
	public void endFields() {
		metaWriter.outdent();
		metaWriter.writeLine("};");
//		metaWriter.writeLine("");
		curWriter = cWriter;
	}

	@Override
	public void startMethod(TACMethod method) {
		StringBuilder sb = new StringBuilder();
		
		int modifiers = method.getSignature().getASTNode().getModifiers();
		
		List<ModifiedType> retTypes = method.getReturnTypes();
		
		// right now we punt on returning more than one thing
		if(retTypes.isEmpty())
			sb.append("void");
		else
			sb.append(typeToString(method.getReturnTypes().get(0).getType()));
		
		sb.append(' ');
		sb.append(getType().getMangledName());
		sb.append(method.getMangledName());
		sb.append('(');
		
		List<String> paramNames = method.getParamNames();
		List<ModifiedType> paramTypes = method.getParamTypes();

		// first param is always a reference to the class, unless it's static
		if(!ModifierSet.isStatic(modifiers))
			sb.append(typeToString(getModule().getClassType())).append(" this, ");

		for (int i = 0; i < paramNames.size(); i++)
		{
			sb.append(typeToString(paramTypes.get(i).getType()));
			sb.append(' ');
			sb.append(paramNames.get(i));
			sb.append(", ");
		}

		for (int i = 1; i < retTypes.size(); i++)
		{
			sb.append(typeToString(retTypes.get(i).getType()));
			sb.append("* _Ireturn").append(i).append(", ");
		}
		
		if (ModifierSet.isStatic(modifiers) && paramTypes.isEmpty() && retTypes.size() <= 1)
			sb.append(") ");
		else
			sb.setCharAt(sb.length()-2, ')');
		
		// methods that are NOT static should be scoped to ONLY this file
		/*if(!ModifierSet.isStatic(modifiers))
			cWriter.writeLine("static "); not sure what this does in c... I'll look it up later */
		
		// writeLine to the C file
		cWriter.writeLine(sb.toString() + '{');
		cWriter.indent();
		
		if ( method.getName().equals("constructor") )
		{
			cWriter.writeLine("this->_Imethods = &" + getType().getMangledName() + "_Imethods;");
		}
		
		// writeLine to the meta file
//		sb.setCharAt(sb.length()-1, ';');
//		metaWriter.writeLine(sb.toString());
		
		TreeSet<String> vars = new TreeSet<String>(new Comparator<String>()
			{
				@Override
				public int compare(String str1, String str2)
				{
					boolean str1IsTemp = str1.startsWith("_Itemp"), str2IsTemp = str2.startsWith("_Itemp");
					if (str1IsTemp != str2IsTemp)
						return str1IsTemp ? 1 : -1;
					return str1IsTemp ? Integer.valueOf(str1.substring(6)).compareTo(Integer.valueOf(
							str2.substring(6))) : str1.compareTo(str2);
				}
			});
		vars.addAll(method.getAllocations().keySet());
		for (String var : vars)
			cWriter.writeLine(typeToString(method.getAllocations().get(var)) + ' ' + var + ';');
	}

	@Override
	public void endMethod(TACMethod method) {
		cWriter.outdent();
		cWriter.writeLine("}");
		cWriter.writeLine("");
		
		metaWriter.writeLine("");
	}
	
	private char operatorToString(TACUnary.Operator operator)
	{
		return operator.getSymbol();
	}
	private String operatorToString(TACBinary.Operator operator)
	{
		switch (operator)
		{
			case ADD:
				return " + ";
			case SUBTRACT:
				return " - ";
			case MULTIPLY:
				return " * ";
			case DIVIDE:
				return " / ";
			case MODULUS:
				return " % ";
			
			case LOGICAL_OR:
				return " || ";
			case LOGICAL_XOR:
				return " != ";
			case LOGICAL_AND:
				return " && ";
			
			case BITWISE_OR:
				return " | ";
			case BITWISE_XOR:
				return " ^ ";
			case BITWISE_AND:
				return " & ";
			
			case LEFT_SHIFT:
				return " << ";
			case RIGHT_SHIFT:
				return " >> ";
			
			case LEFT_ROTATE:
			case RIGHT_ROTATE:
			default:
				throw new UnsupportedOperationException();
		}
	}
	private String operatorToString(TACComparison.Operator operator)
	{
		switch (operator)
		{
			case EQUAL:
				return " == ";
			case NOT_EQUAL:
				return " != ";
			
			case LESS_THAN:
				return " < ";
			case LESS_THAN_OR_EQUAL:
				return " <= ";
			
			case GREATER_THAN:
				return " > ";
			case GREATER_THAN_OR_EQUAL:
				return " >= ";
			
			case IS:
			default:
				throw new UnsupportedOperationException();
		}
	}
	
	/*public void visit(TACNode node) {
		cWriter.writeLine(node.toString()/*, node*//*);
	}*/
	
	private static String typeToString(TACNode node)
	{
		return typeToString(node.getType());
	}
	private static String typeToString(Type type) {
		if (type instanceof ArrayType)
			return typeToString(((ArrayType)type).getBaseType()) + '*';
		else
		{
			if (type.isPrimitive())
				return type.getTypeName() + "_shadow_t";
			else
			{
				StringBuilder sb = new StringBuilder();
				sb.append("struct ");
				sb.append(type.getMangledName());
				sb.append('*');
				return sb.toString();
			}
		}
	}
	
	private String nodeToString(TACNode node)
	{
		if (node instanceof TACLiteral)
			return nodeToString((TACLiteral)node);
		if (node instanceof TACPrefixed)
			return nodeToString((TACPrefixed)node);
		return node.getSymbol();
	}
	
	private String nodeToString(TACPrefixed node)
	{
		if (node instanceof TACCall)
			return node.getSymbol();
		if (node.isPrefixed())
			return nodeToString(node.getPrefix()) + "->" + node.getSymbol();
		return node.getSymbol();
	}
	
	@Override
	public void visit(TACNode node)
	{
		if (node.getLabel() != null)
		{
			curWriter.outdent();
			 // TODO: to remove (void)0; move block variable declarations somewhere else.
			curWriter.writeLine(node.getLabel() + ": (void)0;");
			curWriter.indent();
		}
		super.visit(node);
	}
	
	@Override
	public void visit(TACAllocation node)
	{
		if (node.isOnHeap()) {
			StringBuilder sb = new StringBuilder();
			sb.append(nodeToString(node));
			sb.append(" = "/*("*/);
			/*sb.append(typeToString(var));*/
			sb.append(/*")*/"calloc(");
			if (node.getSize() == null)
				sb.append('1');
			else
				sb.append(nodeToString(node.getSize()));
			sb.append(", sizeof(struct ");
			sb.append(node.getType().getMangledName());
			sb.append("));");
			curWriter.writeLine(sb.toString(), node);
		}
		else
			curWriter.writeLine(typeToString(node) + ' ' + nodeToString(node) + ';');
		if (node.isArray())
		{
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public void visit(TACAssign node)
	{
		if (node.getFirstOperand() instanceof TACSequence)
		{
			TACSequence first = (TACSequence)node.getFirstOperand();
			List<TACNode> firstNodes = first.getNodes();
			if (node.getSecondOperand() instanceof TACSequence)
			{
				TACSequence second = (TACSequence)node.getSecondOperand();
				for (int i = 0; i < firstNodes.size(); i++)
					curWriter.writeLine(nodeToString(firstNodes.get(i)) + " = " +
							nodeToString(second.getNodes().get(i)) + ';');
			}
			else if (node.getSecondOperand() instanceof TACCall)
			{
				TACCall second = (TACCall)node.getSecondOperand();
				for (int i = 0; i < firstNodes.size(); i++)
					curWriter.writeLine(nodeToString(firstNodes.get(i)) + " = " +
							second.getSymbol(i) + ';');
			}
			else
				throw new UnsupportedOperationException();
		}
		else
			curWriter.writeLine(nodeToString(node.getFirstOperand()) + " = " +
					nodeToString(node.getSecondOperand()) + ';');
	}
	
	@Override
	public void visit(TACUnary node)
	{
		curWriter.writeLine(nodeToString(node) + " = " + operatorToString(node.getOperator()) +
				nodeToString(node.getOperand()) + ';');
	}
	
	@Override
	public void visit(TACBinary node)
	{
		curWriter.writeLine(nodeToString(node) + " = " + nodeToString(node.getFirstOperand()) +
				operatorToString(node.getOperator()) + nodeToString(node.getSecondOperand()) + ';');
	}
	
	@Override
	public void visit(TACComparison node)
	{
		curWriter.writeLine(nodeToString(node) + " = " + nodeToString(node.getFirstOperand()) +
				operatorToString(node.getOperator()) + nodeToString(node.getSecondOperand()) + ';');
	}
	
	@Override
	public void visit(TACBranch node)
	{
		if (node.isConditional())
		{
			curWriter.writeLine("if ( " + nodeToString(node.getCondition()) + " )");
			curWriter.indent();
			curWriter.writeLine("goto " + node.getTrueBranch().getLabel() + ';');
			curWriter.outdent();
			if (node.getFalseBranch() != null)
			{
				curWriter.writeLine("else");
				curWriter.indent();
				curWriter.writeLine("goto " + node.getFalseBranch().getLabel() + ';');
				curWriter.outdent();	
			}
		}
		else
			curWriter.writeLine("goto " + node.getBranch().getLabel() + ';');
	}

	@Override
	public void visit(TACLabel node)
	{
		TACNode trueBranch = node.getTrueBranch(),
				falseBranch = node.getFalseBranch();
		if (trueBranch != null)
			if (trueBranch.getLabel() == null)
				trueBranch.setLabel("label" + labelCounter++);
		if (falseBranch != null)
			if (falseBranch.getLabel() == null)
				falseBranch.setLabel("label" + labelCounter++);
	}
	
	@Override
	public void visit(TACCall node)
	{
		MethodSignature signature = node.getSignature();
		StringBuilder sb = new StringBuilder();
		if (node.getSymbolCount() != 0)
			sb.append(nodeToString(node)).append(" = ");
		boolean isStatic = ModifierSet.isStatic(signature.getMethodType().getModifiers());
		if (!isStatic && !signature.getSymbol().equals("constructor"))
			sb.append(nodeToString(node.getPrefix())).append("->_Imethods->");
		else
			sb.append(signature.getMethodType().getOuter().getMangledName());
		sb.append(signature.getMangledName()).append('(');
		if (!isStatic)
			sb.append(nodeToString(node.getPrefix())).append(", ");
		for (TACNode argNode : node.getParameters())
			sb.append(nodeToString(argNode)).append(", ");
		for (int i = 1; i < node.getSymbolCount(); i++)
			sb.append('&').append(node.getSymbol(i)).append(", ");
		if (isStatic && node.getParameters().getNodes().isEmpty() &&
				node.getSymbolCount() <= 1)
			sb.append(");");
		else
			sb.replace(sb.length() - 2, sb.length(), ");");
		curWriter.writeLine(sb.toString());
	}
	
	@Override
	public void visit(TACReturn node)
	{
		if (node.hasReturnValue())
		{
			List<TACNode> retValues = node.getReturnValue().getNodes();
			for (int i = 1; i < retValues.size(); i++)
				curWriter.writeLine("*_Ireturn" + i + " = " + nodeToString(retValues.get(i)) + ';');
			curWriter.writeLine("return " + nodeToString(retValues.get(0)) + ';');
		}
		else
			curWriter.writeLine("return;");
	}
	
	@Override
	public void visit(TACCast node)
	{
		curWriter.writeLine(nodeToString(node) + " = (" + typeToString(node) + ')' +
				nodeToString(node.getOperand()) + ';');
	}
	
	@Override
	public void visit(TACLiteral node)
	{
	}
	
	@Override
	public void visit(TACPhi node)
	{
	}
	
	@Override
	public void visit(TACPhiBranch node)
	{
		curWriter.writeLine(nodeToString(node.getPhi()) + " = " +
				nodeToString(node.getValue()) + ';');
		curWriter.writeLine("goto " + node.getPhi().getLabel() + ';');
	}
	
	@Override
	public void visit(TACReference node)
	{
	}
	
	@Override
	public void visit(TACVariable node)
	{
	}
	
	@Override
	public void visit(TACSequence node)
	{
	}
	
//	private String varToString(TACValue var) {
//		StringBuilder sb = new StringBuilder();
//
//		if (var.isField())
//			sb.append("this->");
//		if (var.isLiteral())
//			sb.append(lit2lit(var.getSymbol()));
//		else
//			sb.append(var.getSymbol());
//		
//		return sb.toString();
//	}

	private String lit2lit(String shadowLiteral) {
		return literalToString(shadowLiteral);
	}
	
	private String nodeToString(TACLiteral node)
	{
		return literalToString(node.getSymbol());
	}
	
	/**
	 * Converts Shadow literals to C literals.
	 * @param shadowLiteral
	 * @return
	 */
	private String literalToString(String shadowLiteral) {
		if (shadowLiteral.startsWith("\""))
		{
			String var = "_Istring" + stringAllocNumber++;
			cWriter.writeLine("static struct " + Type.STRING.getMangledName() + ' ' + var + " = {");
			cWriter.indent();
			cWriter.writeLine('&' + Type.STRING.getMangledName() + "_Imethods,");
			cWriter.writeLine("(boolean_shadow_t)1, (ubyte_shadow_t *)" + shadowLiteral);
			cWriter.outdent();
			cWriter.writeLine("};");
			return '&' + var;
		}
		if (shadowLiteral.equals("true"))
			return "((boolean_shadow_t)1)";
		else if (shadowLiteral.equals("false"))
			return "((boolean_shadow_t)0)";
		else if (shadowLiteral.equals("null"))
			return "((void *)0)";
		if (shadowLiteral.endsWith("ub"))
			shadowLiteral = "((ubyte_shadow_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 2) + ')';
		else if (shadowLiteral.endsWith("b"))
			shadowLiteral = "((byte_shadow_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 1) + ')';
		if (shadowLiteral.endsWith("us"))
			shadowLiteral = "((ushort_shadow_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 2) + ')';
		else if (shadowLiteral.endsWith("s"))
			shadowLiteral = "((short_shadow_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 1) + ')';
		if (shadowLiteral.endsWith("ui"))
			shadowLiteral = "((uint_shadow_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 2) + ')';
		else if (shadowLiteral.endsWith("i"))
			shadowLiteral = "((int_shadow_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 1) + ')';
		if (shadowLiteral.endsWith("ul"))
			shadowLiteral = "((ulong_shadow_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 2) + "ull)";
		else if (shadowLiteral.endsWith("l"))
			shadowLiteral = "((long_shadow_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 1) + "ll)";
		return shadowLiteral;
	}
	
//	@Override
//	public void visit(TACAllocation node) {
//		for (TACVariable var : node.getValue())
//		{
//			curWriter.writeLine("" + var.getType() + ' ' + var.getSymbol() + ';');
//		}
//		
//		/*if (!var.isField() || curWriter == metaWriter)
//		{
//			StringBuilder sb = new StringBuilder();
//	
//			sb.append(typeToString(var);
//			sb.append(' ');
//			sb.append(var.getSymbol());
//			sb.append(';');
//			
//			// writeLine to the current writer as these can show up anywhere
//			curWriter.writeLine(sb.toString(), node);
//	
//			if (node.isOnHeap()) {
//				sb.setLength(0);
//				sb.append(varToString(var));
//				sb.append(" = "/*("*//*);
//				/*sb.append(typeToString(var));*//*
//				sb.append(/*")*//*"calloc(");
//				sb.append(node.getSize().getSymbol());
//				sb.append(", sizeof(struct ");
//				sb.append(var.getType().getMangledName());
//				sb.append("));");
//				curWriter.writeLine(sb.toString(), node);
//			}
//		}*/
//	}
//	
//	@Override
//	public void visit(TACOperator node)
//	{
//		if (node.isNoOp())
//			return;
//		List<TACVariable> value = node.getValue() == null ? null : node.getValue().getVariables(),
//				op1 = node.getFirstOperand() == null ? null : node.getFirstOperand().getVariables(),
//				op2 = node.getSecondOperand() == null ? null : node.getSecondOperand().getVariables();
//		if (value != null)
//			for (int i = 0; i < value.size(); i++)
//			{
//				StringBuilder sb = new StringBuilder(value.get(i).getSymbol());
//				sb.append(" = ");
//
//				if (node.isAssign())
//					sb.append(op1.get(i).getSymbol());
//				if (node.isUnary())
//				{
//					switch (node.getOperator())
//					{
//						case NEGATE:
//							sb.append('-');
//							break;
//						case LOGICAL_NOT:
//							sb.append('!');
//							break;
//						case BITWISE_NOT:
//							sb.append('~');
//							break;
//					}
//					sb.append(op1.get(i).getSymbol());
//				}
//				else if (node.isBinary())
//				{
//					sb.append(op1.get(i).getSymbol()).append(' ');
//					switch (node.getOperator())
//					{
//						case ADD:
//							sb.append('+');
//							break;
//						case SUBTRACT:
//							sb.append('-');
//							break;
//						case MULTIPLY:
//							sb.append('*');
//							break;
//						case DIVIDE:
//							sb.append('/');
//							break;
//					}
//					sb.append(' ').append(op2.get(i).getSymbol());
//				}
//				sb.append(';');
//				
//				curWriter.writeLine(sb.toString());
//			}
//	}
//	
//	public void visit(TACAssign node) {
//		if (!node.getLHS().isField() || curWriter == cWriter)
//		{
//			StringBuilder sb = new StringBuilder();
//			
//			sb.append(varToString(node.getLHS()));
//			sb.append(" = ");
//			sb.append(varToString(node.getRHS()));
//			sb.append(';');
//			
//			cWriter.writeLine(sb.toString(), node);
//		}
//	}
//	
//	public void visit(TACBinary node) {
//		StringBuilder sb = new StringBuilder();
//		
//		if(node.getLHS().isField())
//			sb.append("this->");
//		sb.append(varToString(node.getLHS()));
//		sb.append(" = ");
//		sb.append(varToString(node.getRHS()));
//		
//		switch(node.getOperator()) {
//		case ADDITION: sb.append(" + "); break;
//		case AND: sb.append(" & "); break;
//		case DIVISION: sb.append(" / "); break;
//		case LROTATE: sb.append(" SOME MACRO "); break;
//		case LSHIFT: sb.append(" << "); break;
//		case MOD: sb.append(" % "); break;
//		case MULTIPLICATION: sb.append(" * "); break;
//		case OR: sb.append(" | "); break;
//		case RROTATE: sb.append(" SOME MACRO "); break;
//		case RSHIFT: sb.append(">>"); break;
//		case SUBTRACTION: sb.append(" - "); break;
//		case XOR: sb.append("^"); break;
//		}
//		
//		sb.append(varToString(node.getOperand2()));
//		sb.append(';');
//		
//		cWriter.writeLine(sb.toString(), node);
//	}
//	
//	public void visit(TACBranch node) {
//		StringBuilder sb = new StringBuilder("if ( ");
//		
//		sb.append(varToString(node.getLHS()));
//		switch(node.getComparision()) {
//			case EQUAL: sb.append(" == "); break;
//			case GREATER: sb.append(" > "); break;
//			case GREATER_EQUAL: sb.append(" >= "); break;
//			case IS: sb.append(" ???? "); break;
//			case LESS: sb.append(" < "); break;
//			case LESS_EQUAL: sb.append(" <= "); break;
//			case NOT_EQUAL: sb.append(" != "); break;
//		}
//		sb.append(varToString(node.getRHS()));
//		sb.append(" ) { ");
//		
//		cWriter.writeLine("");
//		cWriter.writeLine(sb.toString(), node);
//		cWriter.indent();
//	}
//	
//	public void visitElse() {
//		cWriter.writeLine("else {");
//		cWriter.indent();
//	}
//	
//	public void visit(TACLoop node) {
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append("while ( ");
//		sb.append(varToString(node.getLHS()));
//		switch(node.getComparision()) {
//			case EQUAL: sb.append(" == "); break;
//			case GREATER: sb.append(" > "); break;
//			case GREATER_EQUAL: sb.append(" >= "); break;
//			case IS: sb.append(" ???? "); break;
//			case LESS: sb.append(" < "); break;
//			case LESS_EQUAL: sb.append(" <= "); break;
//			case NOT_EQUAL: sb.append(" != "); break;
//		}
//		sb.append(varToString(node.getRHS()));
//		sb.append(" ) {");
//		cWriter.writeLine(sb.toString(), node);
//		cWriter.indent();
//	}
//	
//	public void visit(TACJoin node) {
//	}
//	
//	public void visitJoin(TACJoin node) {
//		cWriter.outdent();
//		cWriter.writeLine("} ", node);
//	}
//	
//	public void visit(TACNoOp node) {
//	}
//
//	public void visit(TACUnary node) {
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append(varToString(node.getLHS()));
//		sb.append(" = ");
//		switch(node.getOperator()) {
//			case PLUS: sb.append('+'); break;
//			case MINUS: sb.append('-'); break;
//			case COMPLEMENT: sb.append('~'); break;
//		}
//		sb.append(varToString(node.getRHS()));
//		sb.append(';');
//		
//		cWriter.writeLine(sb.toString(), node);
//	}
//
//	public void visit(TACMethodCall node) {
//		StringBuilder sb = new StringBuilder();
//		
//		/*if ( node.getMangledName().equals("writeLine_PString") )
//		{
//			sb.append("writeLinef(\"%s\", ");
//		}
//		else if ( node.getMangledName().equals("writeLine_Pint") )
//		{
//			sb.append("writeLinef(\"%d\", ");
//		}
//		else if ( node.getMangledName().equals("writeLineLine") )
//		{
//			sb.append("writeLinef(\"\\n\");");
//		}
//		else
//		{
//			sb.append(node.getMangledName());
//			sb.append('(');
//		} moved to shadow.h for now */
//		
//		if (node.hasReturn()) {
//			TACValue ret = node.getReturn(0);
//			sb.append(ret.getSymbol());
//			sb.append(" = ");
//		}
//		
//		if (!ModifierSet.isStatic(node.getMethodType().getModifiers()) &&
//				!node.getMethodName().equals("constructor"))
//		{
//			sb.append(varToString(node.getParameter(0)));
//			sb.append("->_Imethods->");
//			sb.append(node.getMangledMethodName());
//		}
//		else
//			sb.append(node.getMangledName());
//		sb.append('(');
//		
//		/*for(TACValue param:node.getParameters()) {
//
//			if(param.isField())
//				sb.append("this->");
//			
//			sb.append(param.getSymbol());
//			sb.append(", ");
//		}*/
//		int paramIndex = 0;
//		MethodType type = node.getMethodType();
//		for (int i = 0; i < node.getParamCount(); i++)
//		{
//			Type paramType;
//			if (i == 0 && !ModifierSet.isStatic(type.getModifiers()))
//				paramType = type.getOuter();
//			else
//				paramType = type.getParameterTypes().get(paramIndex++).getType();
//			TACValue param = node.getParameter(i);
//			if (!paramType.equals(param.getType()))
//				sb.append('(').append(typeToString(paramType)).append(')');
//			sb.append(varToString(param)).append(", ");
//		}
//		for (int i = 1; i < node.getReturnCount(); i++)
//			sb.append('&' + varToString(node.getReturn(i)) + ", ");
//		
//		if(node.getParamCount() > 0 || node.getReturnCount() > 1)
//			sb.replace(sb.length() - 2, sb.length(), ");");
//		else
//			sb.append(");");
//		
//		cWriter.writeLine(sb.toString(), node);
//	}
//	
//	public void visit(TACReturn node) {
//		List<TACValue> retVars = node.getReturns();
//		
//		for ( int i = 1; i < retVars.size(); i++ )
//			cWriter.writeLine("(*_Ireturn" + i + ") = " + varToString(retVars.get(i)) + ';', node);
//		
//		cWriter.writeLine("return " + varToString(retVars.get(0)) + ';', node);
//	}
}
