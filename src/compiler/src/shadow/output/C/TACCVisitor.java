/**
 * 
 */
package shadow.output.C;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import shadow.TAC.TACClass;
import shadow.TAC.TACMethod;
import shadow.TAC.TACVariable;
import shadow.TAC.nodes.TACAllocation;
import shadow.TAC.nodes.TACAssign;
import shadow.TAC.nodes.TACBinaryOperation;
import shadow.TAC.nodes.TACBranch;
import shadow.TAC.nodes.TACJoin;
import shadow.TAC.nodes.TACLoop;
import shadow.TAC.nodes.TACMethodCall;
import shadow.TAC.nodes.TACNoOp;
import shadow.TAC.nodes.TACNode;
import shadow.TAC.nodes.TACReturn;
import shadow.TAC.nodes.TACUnaryOperation;
import shadow.output.AbstractTACLinearVisitor;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.typecheck.MethodSignature;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

/**
 * @author wspeirs
 *
 */
public class TACCVisitor extends AbstractTACLinearVisitor {
	private int stringAllocNumber = 0;

	private CPrettyPrinter cWriter;
	private CPrettyPrinter metaWriter;
	private CPrettyPrinter curWriter;
	
	private String cFileName = null;
	private String metaFileName = null;
	
	/**
	 * @param root
	 */
	public TACCVisitor(TACClass theClass) {
		super(theClass);
		cWriter = new CPrettyPrinter(new PrintWriter(System.out), "C: ");
		metaWriter = new CPrettyPrinter(new PrintWriter(System.out), "M: ");
	}
	
	public TACCVisitor(TACClass theClass, File shadowFile) throws ShadowException {
		super(theClass);
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
			cWriter = new CPrettyPrinter(new PrintWriter(new FileOutputStream(cFile, false)));
			
			// open the meta file appending data always
			metaWriter = new CPrettyPrinter(new PrintWriter(new FileWriter(metaFile, /*true*/false)));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ShadowException(e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ShadowException(e.getLocalizedMessage());
		}
	}
	

	@Override
	public void startFile() {
		// write out the stuff for the C file
		cWriter.print("/* AUTO-GENERATED FILE, DO NOT EDIT! */");
		cWriter.print("#include \"" + getClassType().getPath() + ".meta\"");	// include the header
		cWriter.print("");
		
		String className = lit2lit('\"' + getClassType().getFullName() + '\"');
		cWriter.print("struct " + Type.CLASS.getMangledName() + ' ' +
				getClassType().getMangledName() + "_Iclass = {");
		cWriter.indent();
		cWriter.print('&' + Type.CLASS.getMangledName() + "_Imethods, " + className);
		cWriter.outdent();
		cWriter.print("};");
		cWriter.print("");
		
		String metaShortName = metaFileName == null ? "" : metaFileName.substring(metaFileName.lastIndexOf(File.separatorChar)+1, metaFileName.length());
		metaShortName = metaShortName.replace('.', '_');
		
		// write out the stuff for the meta file
		metaWriter.print("/* AUTO-GENERATED FILE, DO NOT EDIT! */");
		metaWriter.print("#ifndef " + metaShortName.toUpperCase());
		metaWriter.print("#define " + metaShortName.toUpperCase());
		metaWriter.print("");
		metaWriter.print("#include \"types.h\"");
		metaWriter.print("#include \"stdlib.h\"");
		
		ClassType classType = (ClassType)getTheClass().getType();
		Type extendsType = classType.getExtendType();
		if (extendsType != null)
		{
			metaWriter.print("#include \"" + extendsType.getPath() + ".meta\"");
			metaWriter.print("");
		}

		getClassType().addReferencedType(Type.CLASS);
		for (Type type : getClassType().getReferenceTypes())
			metaWriter.print("struct " + type.getMangledName() + ';');
		metaWriter.print("");
		
		// create the method structure
		metaWriter.print("struct " + Type.CLASS.getMangledName() + ' ' + getClassType().getMangledName() + "_Iclass;");
		metaWriter.print("struct " + getClassType().getMangledName() + "_Itable;");
		metaWriter.print("struct " + getClassType().getMangledName() + "_Itable " + getClassType().getMangledName() + "_Imethods;");
		
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
//			metaWriter.print(sb.toString());
//		}
		
	}

	@Override
	public void endFile() {

		boolean /*foundConstructor = false,*/ foundMain = false, foundNative = false;
		metaWriter.print(typeToString(Type.CLASS) + ' ' + getClassType().getMangledName() + "_MgetClass(" + typeToString(getClassType()) + " this);");
		for ( List<MethodSignature> methods : getClassType().getMethodMap().values() )
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
				List<Type> retTypes = method.getMethodType().getReturnTypes();
				List<ModifiedType> paramTypes = method.getMethodType().getParameterTypes();
				
				// loop through the ret types
				
				if(retTypes.size() != 0)
					sb.append(typeToString(retTypes.get(0)));
				else
					sb.append("void");
				
				sb.append(' ');
				sb.append(method.getMethodType().getOuter().getMangledName());
				sb.append(method.getMangledName());
				sb.append('(');
				
				if (!ModifierSet.isStatic(method.getASTNode().getModifiers()))
				{
					if ( method.getSymbol().equals("getClass") && method.canAccept(Collections.<Type>emptyList()) )
						sb.append(typeToString(getClassType()));
					else
						sb.append(typeToString(method.getMethodType().getOuter()));
					sb.append(", ");
				}
				
				for ( ModifiedType param : paramTypes )
					sb.append(typeToString(param.getType())).append(", ");
				
				for ( int i = 1; i < retTypes.size(); i++ )
					sb.append(typeToString(retTypes.get(i))).append("*, ");
				
				if (paramTypes.size() == 0 && ModifierSet.isStatic(method.getASTNode().getModifiers()))
					sb.append("void);");
				else
					sb.replace(sb.length() - 2, sb.length(), ");");
				
				metaWriter.print(sb.toString());
			}
		}
		metaWriter.print("");
		
		metaWriter.print("struct " + getTheClass().getMangledName() + "_Itable {");
		metaWriter.indent();
		for ( MethodSignature method : getClassType().getMethodList() )
		{
			if ( !method.getSymbol().equals("constructor") )
			{
				StringBuffer sb = new StringBuffer();
				List<Type> retTypes = method.getMethodType().getReturnTypes();
				List<ModifiedType> paramTypes = method.getMethodType().getParameterTypes();
				
				// loop through the ret types
				
				if(retTypes.size() != 0)
					sb.append(typeToString(retTypes.get(0)));
				else
					sb.append("void");
				
				sb.append(" ");
				sb.append("(*");
				sb.append(method.getMangledName());
				sb.append(")(");
				
				if (!ModifierSet.isStatic(method.getASTNode().getModifiers()))
				{
					if ( method.getSymbol().equals("getClass") && method.canAccept(Collections.<Type>emptyList()) )
						sb.append(typeToString(getClassType()));
					else
						sb.append(typeToString(method.getMethodType().getOuter()));
					sb.append(", ");
//					sb.append(" this, ");
				}
				
				for ( ModifiedType param : paramTypes )
					sb.append(typeToString(param.getType())).append(", ");
				
				for ( int i = 1; i < retTypes.size(); i++ )
					sb.append(typeToString(retTypes.get(i))).append("*, ");
				
				if (paramTypes.size() == 0 && ModifierSet.isStatic(method.getASTNode().getModifiers()))
					sb.append("void);");
				else
					sb.replace(sb.length() - 2, sb.length(), ");");
				
				metaWriter.print(sb.toString());
			}
		}
		metaWriter.outdent();
		metaWriter.print("};");
		metaWriter.print("");
		
		cWriter.print(typeToString(Type.CLASS) + ' ' + getClassType().getMangledName() + "_MgetClass(" + typeToString(getClassType()) + " this) {");
		cWriter.indent();
		cWriter.print("return &" + getClassType().getMangledName() + "_Iclass;");
		cWriter.outdent();
		cWriter.print("}");
		cWriter.print("");
		
		cWriter.print("struct " + getTheClass().getMangledName() + "_Itable " + getTheClass().getMangledName() + "_Imethods = {");
		cWriter.indent();
		for ( MethodSignature method : getClassType().getMethodList() )
		{
			/*if ( method.getSymbol().equals("getClass") &&
					method.canAccept(Collections.<Type>emptyList()) )
			{
				StringBuffer sb = new StringBuffer();
				
				sb.append(' ');
				sb.append(method.getMethodType().getOuter().getMangledName());
				sb.append(method.getMangledName());
				sb.append(',');
				
				metaWriter.print(sb.toString());
			}
			else*/ if ( !method.getSymbol().equals("constructor") )
			{
				StringBuffer sb = new StringBuffer();
				
				if ( method.getSymbol().equals("getClass") && method.canAccept(Collections.<Type>emptyList()) )
					sb.append(getClassType().getMangledName());
				else
					sb.append(method.getMethodType().getOuter().getMangledName());
				sb.append(method.getMangledName());
				sb.append(',');
				
				cWriter.print(sb.toString());
			}
		}
		
		cWriter.outdent();
		cWriter.print("};");
		cWriter.print("");
		
		if (foundMain)
		{
			/* Here's our main method */
			cWriter.print("int main(int argc, char **argv) {");
			cWriter.indent();
			cWriter.print(getTheClass().getType().getMangledName() + "_Mmain_R_Pshadow_Pstandard_CString_A1((struct _Pshadow_Pstandard_CString **)0);");
			cWriter.print("return 0;");
			cWriter.outdent();
			cWriter.print("}");
			cWriter.print("");
		}
		if ( foundNative )
		{
			cWriter.print("#include \"" + getClassType().getPath() + ".h\"");
			cWriter.print("");
		}

		for (Type type : getClassType().getReferenceTypes())
			metaWriter.print("#include \"" + type.getPath() + ".meta\"");
		metaWriter.print("");
		metaWriter.print("#endif");
	}

	@Override
	public void startFields() {
		TACClass theClass = this.getTheClass();
		
		metaWriter.print("struct " + theClass.getType().getMangledName() + " {");
		metaWriter.indent();
		
//		ClassType type = (ClassType)theClass.getType();
//		if (type.getExtendType() != null)
//			metaWriter.print("struct " + type.getExtendType().getMangledName() + " _Isuper;");
		
		// add the super class (TODO: what do we do for interfaces???)
		/*if(theClass.getExtendClassName() != null)
			metaWriter.print("struct " + theClass.getExtendClassName() + " _super;"); later*/
		
//		List<String> imps = theClass.getImplementsClassNames();
		
//		metaWriter.print("");
//		metaWriter.print("/* Interfaces */");
		
//		// add pointers to all the implementing classes
//		for(String s:imps) {
//			metaWriter.print("struct " + s + " *_" + s.toLowerCase() + ";");
//		}
		
		// add a pointer to the methods for this class
		metaWriter.print("struct " + theClass.getMangledName() + "_Itable *_Imethods;");
		
//		// add in a var to keep track of the reference counts to this object
//		metaWriter.print("");
//		metaWriter.print("unsigned int _ref_count;");	// we might want to move this to Object ONLY
		metaWriter.print("");
		
		metaWriter.print("/* Fields */");
		
		curWriter = metaWriter;
	}

	@Override
	public void endFields() {
		metaWriter.outdent();
		metaWriter.print("};");
//		metaWriter.print("");
		curWriter = cWriter;
	}

	@Override
	public void startMethod(TACMethod method) {
		StringBuilder sb = new StringBuilder();
		
		int modifiers = method.getSignature().getASTNode().getModifiers();
		
		List<Type> retTypes = method.getReturnTypes();
		
		// right now we punt on returning more than one thing
		if(retTypes.isEmpty())
			sb.append("void");
		else
			sb.append(typeToString(method.getReturnTypes().get(0)));
		
		sb.append(' ');
		sb.append(getClassType().getMangledName());
		sb.append(method.getMangledName());
		sb.append('(');
		
		List<String> paramNames = method.getParamNames();
		List<ModifiedType> paramTypes = method.getParamTypes();

		// first param is always a reference to the class, unless it's static
		if(!ModifierSet.isStatic(modifiers))
			sb.append(typeToString(getTheClass().getType())).append(" this, ");

		for (int i = 0; i < paramNames.size(); i++)
		{
			sb.append(typeToString(paramTypes.get(i).getType()));
			sb.append(' ');
			sb.append(paramNames.get(i));
			sb.append(", ");
		}

		for (int i = 1; i < retTypes.size(); i++)
		{
			sb.append(typeToString(retTypes.get(i)));
			sb.append("* _Ireturn").append(i).append(", ");
		}
		
		if (ModifierSet.isStatic(modifiers) && paramTypes.isEmpty() && retTypes.size() <= 1)
			sb.append(") ");
		else
			sb.setCharAt(sb.length()-2, ')');
		
		// methods that are NOT static should be scoped to ONLY this file
		/*if(!ModifierSet.isStatic(modifiers))
			cWriter.print("static "); not sure what this does in c... I'll look it up later */
		
		// print to the C file
		cWriter.print(sb.toString() + '{');
		cWriter.indent();
		
		if ( method.getName().equals("constructor") )
		{
			cWriter.print("this->_Imethods = &" + getClassType().getMangledName() + "_Imethods;");
		}
		
		// print to the meta file
//		sb.setCharAt(sb.length()-1, ';');
//		metaWriter.print(sb.toString());
	}

	@Override
	public void endMethod(TACMethod method) {
		cWriter.outdent();
		cWriter.print("}");
		cWriter.print("");
		
		metaWriter.print("");
	}
	
	public void visit(TACNode node) {
		cWriter.print(node.toString(), node);
	}
	
	private String typeToString(Type type) {
		if (type instanceof ArrayType)
			return typeToString(((ArrayType)type).getBaseType()) + '*';
		else
		{
			if (type.isPrimitive())
				return type.getTypeName() + "_t";
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

	private String varToString(TACVariable var) {
		StringBuilder sb = new StringBuilder();

		if (var.isField())
			sb.append("this->");
		if (var.isLiteral())
			sb.append(lit2lit(var.getSymbol()));
		else
			sb.append(var.getSymbol());
		
		return sb.toString();
	}
	
	/**
	 * Converts Shadow literals to C literals.
	 * @param shadowLiteral
	 * @return
	 */
	private String lit2lit(String shadowLiteral) {
		if (shadowLiteral.startsWith("\""))
		{
			String var = "_Istring" + stringAllocNumber++;
			cWriter.print("static struct " + Type.STRING.getMangledName() + ' ' + var + " = {");
			cWriter.indent();
			cWriter.print('&' + Type.STRING.getMangledName() + "_Imethods,");
			cWriter.print("(boolean_t)1, (ubyte_t *)" + shadowLiteral);
			cWriter.outdent();
			cWriter.print("};");
			return '&' + var;
		}
		if (shadowLiteral.equals("true"))
			return "1";
		else if (shadowLiteral.equals("false"))
			return "0";
		else if (shadowLiteral.equals("null"))
			return "((void *)0)";
		if (shadowLiteral.endsWith("ub"))
			shadowLiteral = "(ubyte_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 2);
		else if (shadowLiteral.endsWith("b"))
			shadowLiteral = "(byte_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 1);
		if (shadowLiteral.endsWith("us"))
			shadowLiteral = "(ushort_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 2);
		else if (shadowLiteral.endsWith("s"))
			shadowLiteral = "(short_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 1);
		if (shadowLiteral.endsWith("ui"))
			shadowLiteral = "(uint_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 2);
		else if (shadowLiteral.endsWith("i"))
			shadowLiteral = "(int_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 1);
		if (shadowLiteral.endsWith("ul"))
			shadowLiteral = "(ulong_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 2);
		else if (shadowLiteral.endsWith("l"))
			shadowLiteral = "(long_t)" + shadowLiteral.substring(0, shadowLiteral.length() - 1);
		return shadowLiteral;
	}
	
	public void visit(TACAllocation node) {
		TACVariable var = node.getVariable();
		
		if (!var.isField() || curWriter == metaWriter)
		{
			StringBuilder sb = new StringBuilder();
	
			sb.append(typeToString(var.getType()));
			sb.append(' ');
			sb.append(var.getSymbol());
			sb.append(';');
			
			// print to the current writer as these can show up anywhere
			curWriter.print(sb.toString(), node);
	
			if (node.isOnHeap()) {
				sb.setLength(0);
				sb.append(varToString(var));
				sb.append(" = "/*("*/);
				/*sb.append(typeToString(var.getType()));*/
				sb.append(/*")*/"calloc(");
				sb.append(node.getSize().getSymbol());
				sb.append(", sizeof(struct ");
				sb.append(var.getType().getMangledName());
				sb.append("));");
				curWriter.print(sb.toString(), node);
			}
		}
	}
	
	public void visit(TACAssign node) {
		if (!node.getLHS().isField() || curWriter == cWriter)
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append(varToString(node.getLHS()));
			sb.append(" = ");
			sb.append(varToString(node.getRHS()));
			sb.append(';');
			
			cWriter.print(sb.toString(), node);
		}
	}
	
	public void visit(TACBinaryOperation node) {
		StringBuilder sb = new StringBuilder();
		
		if(node.getLHS().isField())
			sb.append("this->");
		sb.append(varToString(node.getLHS()));
		sb.append(" = ");
		sb.append(varToString(node.getRHS()));
		
		switch(node.getOperation()) {
		case ADDITION: sb.append(" + "); break;
		case AND: sb.append(" & "); break;
		case DIVISION: sb.append(" / "); break;
		case LROTATE: sb.append(" SOME MACRO "); break;
		case LSHIFT: sb.append(" << "); break;
		case MOD: sb.append(" % "); break;
		case MULTIPLICATION: sb.append(" * "); break;
		case OR: sb.append(" | "); break;
		case RROTATE: sb.append(" SOME MACRO "); break;
		case RSHIFT: sb.append(">>"); break;
		case SUBTRACTION: sb.append(" - "); break;
		case XOR: sb.append("^"); break;
		}
		
		sb.append(varToString(node.getOperand2()));
		sb.append(';');
		
		cWriter.print(sb.toString(), node);
	}
	
	public void visit(TACBranch node) {
		StringBuilder sb = new StringBuilder("if ( ");
		
		sb.append(varToString(node.getLHS()));
		switch(node.getComparision()) {
			case EQUAL: sb.append(" == "); break;
			case GREATER: sb.append(" > "); break;
			case GREATER_EQUAL: sb.append(" >= "); break;
			case IS: sb.append(" ???? "); break;
			case LESS: sb.append(" < "); break;
			case LESS_EQUAL: sb.append(" <= "); break;
			case NOT_EQUAL: sb.append(" != "); break;
		}
		sb.append(varToString(node.getRHS()));
		sb.append(" ) { ");
		
		cWriter.print("");
		cWriter.print(sb.toString(), node);
		cWriter.indent();
	}
	
	public void visitElse() {
		cWriter.print("else {");
		cWriter.indent();
	}
	
	public void visit(TACLoop node) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("while ( ");
		sb.append(varToString(node.getLHS()));
		switch(node.getComparision()) {
			case EQUAL: sb.append(" == "); break;
			case GREATER: sb.append(" > "); break;
			case GREATER_EQUAL: sb.append(" >= "); break;
			case IS: sb.append(" ???? "); break;
			case LESS: sb.append(" < "); break;
			case LESS_EQUAL: sb.append(" <= "); break;
			case NOT_EQUAL: sb.append(" != "); break;
		}
		sb.append(varToString(node.getRHS()));
		sb.append(" ) {");
		cWriter.print(sb.toString(), node);
		cWriter.indent();
	}
	
	public void visit(TACJoin node) {
	}
	
	public void visitJoin(TACJoin node) {
		cWriter.outdent();
		cWriter.print("} ", node);
	}
	
	public void visit(TACNoOp node) {
	}

	public void visit(TACUnaryOperation node) {
		StringBuilder sb = new StringBuilder();
		
		if(node.getLHS().isField())
			sb.append("this->");
		sb.append(node.getLHS());
		
		sb.append(" = ");
		
		sb.append(node.getOperation());
		
		if(node.getRHS().isField())
			sb.append("(*this)");
		sb.append(node.getRHS());
		
		sb.append(";");
		
		cWriter.print(sb.toString(), node);
	}

	public void visit(TACMethodCall node) {
		StringBuilder sb = new StringBuilder();
		
		/*if ( node.getMangledName().equals("print_PString") )
		{
			sb.append("printf(\"%s\", ");
		}
		else if ( node.getMangledName().equals("print_Pint") )
		{
			sb.append("printf(\"%d\", ");
		}
		else if ( node.getMangledName().equals("printLine") )
		{
			sb.append("printf(\"\\n\");");
		}
		else
		{
			sb.append(node.getMangledName());
			sb.append('(');
		} moved to shadow.h for now */
		
		if (node.hasReturn()) {
			TACVariable ret = node.getReturn(0);
			sb.append(ret.getSymbol());
			sb.append(" = ");
		}
		
		if (!ModifierSet.isStatic(node.getMethodType().getModifiers()) &&
				!node.getMethodName().equals("constructor"))
		{
			sb.append(varToString(node.getParameter(0)));
			sb.append("->_Imethods->");
			sb.append(node.getMangledMethodName());
		}
		else
			sb.append(node.getMangledName());
		sb.append('(');
		
		/*for(TACVariable param:node.getParameters()) {

			if(param.isField())
				sb.append("this->");
			
			sb.append(param.getSymbol());
			sb.append(", ");
		}*/
		int paramIndex = 0;
		MethodType type = node.getMethodType();
		for (int i = 0; i < node.getParamCount(); i++)
		{
			Type paramType;
			if (i == 0 && !ModifierSet.isStatic(type.getModifiers()))
				paramType = type.getOuter();
			else
				paramType = type.getParameterTypes().get(paramIndex++).getType();
			TACVariable param = node.getParameter(i);
			if (!paramType.equals(param.getType()))
				sb.append('(').append(typeToString(paramType)).append(')');
			sb.append(varToString(param)).append(", ");
		}
		for (int i = 1; i < node.getReturnCount(); i++)
			sb.append('&' + varToString(node.getReturn(i)) + ", ");
		
		if(node.getParamCount() > 0 || node.getReturnCount() > 1)
			sb.replace(sb.length() - 2, sb.length(), ");");
		else
			sb.append(");");
		
		cWriter.print(sb.toString(), node);
	}
	
	public void visit(TACReturn node) {
		List<TACVariable> retVars = node.getReturns();
		
		for ( int i = 1; i < retVars.size(); i++ )
			cWriter.print("(*_Ireturn" + i + ") = " + varToString(retVars.get(i)) + ';', node);
		
		cWriter.print("return " + varToString(retVars.get(0)) + ';', node);
	}
}
