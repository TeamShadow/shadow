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
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser.ModifierSet;
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
		cWriter.print("#include \"" + metaFileName + "\"");	// include the header
		cWriter.print("");
		
		String metaShortName = metaFileName == null ? "" : metaFileName.substring(metaFileName.lastIndexOf(File.separatorChar)+1, metaFileName.length());
		metaShortName = metaShortName.replace('.', '_');
		
		// write out the stuff for the meta file
		metaWriter.print("/* AUTO-GENERATED FILE, DO NOT EDIT! */");
		metaWriter.print("#include \"shadow.h\"");
		metaWriter.print("#ifndef " + metaShortName.toUpperCase());
		metaWriter.print("#define " + metaShortName.toUpperCase());
		metaWriter.print("");
		
		// create the method structure
		metaWriter.print("struct " + getTheClass().getMangledName() + "_methods {");
		metaWriter.indent();
		
		List<TACMethod> methods = getTheClass().getMethods();
		boolean foundConstructor = false, foundMain = false;
		for(TACMethod method:methods) {
			// don't need the field init method included
			if(method.getName().equals("constructor")) {
				foundConstructor = true;
				continue;
			}
			else if (method.getName().equals("main"))
			{
				List<ModifiedType> paramTypes = method.getParamTypes();
				if (paramTypes.size() == 1)
				{
					Type firstArg = paramTypes.get(0).getType();
					if (firstArg instanceof ArrayType &&
							((ArrayType)firstArg).getBaseType().equals(Type.STRING))
					{
						foundMain = true;
					}
				}
			}
			
			StringBuffer sb = new StringBuffer();
			List<Type> retTypes = method.getReturnTypes();
			List<ModifiedType> paramTypes = method.getParamTypes();
			
			// loop through the ret types
			
			if(retTypes.size() != 0)
				sb.append(typeToString(retTypes.get(0)));
			else
				sb.append("void");
			
			sb.append(" ");
			sb.append("(*");
			sb.append(getTheClass().getType().getMangledName());
			sb.append(method.getMangledName());
			sb.append(")(");
			
			if (!ModifierSet.isStatic(method.getSignature().getASTNode().getModifiers()))
			{
				sb.append(typeToString(getTheClass().getType()));
				sb.append(" this, ");
			}
			
			for(ModifiedType param:paramTypes) {
				sb.append(typeToString(param.getType()));
				sb.append(", ");
			}
			
			if (paramTypes.size() == 0 && ModifierSet.isStatic(method.getSignature().getASTNode().getModifiers())) {
				sb.append("void  ");
			}
			
			sb.setCharAt(sb.length()-2, ')');
			sb.setCharAt(sb.length()-1, ';');
			
			metaWriter.print(sb.toString());
		}
		
		if (foundMain)
		{
			/* Here's our main method */
			cWriter.print("int main(int argc, char **argv) {");
			cWriter.indent();
			cWriter.print(getTheClass().getType().getMangledName() + "_Mmain_Pshadow_Pstandard_CString_A1(argv);");
			cWriter.print("return 0;");
			cWriter.outdent();
			cWriter.print("}");
			cWriter.print("");
		}
		
		metaWriter.outdent();
		metaWriter.print("};");
		metaWriter.print("");
		
	}

	@Override
	public void endFile() {
		metaWriter.print("#endif");
	}

	@Override
	public void startFields() {
		TACClass theClass = this.getTheClass();
		
		metaWriter.print("struct " + theClass.getType().getMangledName() + " {");
		metaWriter.indent();
		
		ClassType type = (ClassType)theClass.getType();
		if (type.getExtendType() != null)
			metaWriter.print(type.getExtendType().getMangledName() + " super;");
		
		// add the super class (TODO: what do we do for interfaces???)
		/*if(theClass.getExtendClassName() != null)
			metaWriter.print("struct " + theClass.getExtendClassName() + " _super;"); later*/
		
		List<String> imps = theClass.getImplementsClassNames();
		
//		metaWriter.print("");
//		metaWriter.print("/* Interfaces */");
		
		// add pointers to all the implementing classes
		for(String s:imps) {
			metaWriter.print("struct " + s + " *_" + s.toLowerCase() + ";");
		}
		
		// add a pointer to the methods for this class
		metaWriter.print("struct " + theClass.getMangledName() + "_methods *_methods;");
		
		// add in a var to keep track of the reference counts to this object
		metaWriter.print("");
//		metaWriter.print("unsigned int _ref_count;");	// we might want to move this to Object ONLY
//		metaWriter.print("");
		
		metaWriter.print("/* Fields */");
		
		curWriter = metaWriter;
	}

	@Override
	public void endFields() {
		metaWriter.outdent();
		metaWriter.print("};");
		metaWriter.print("");
		curWriter = cWriter;
	}

	@Override
	public void startMethod(TACMethod method) {
		StringBuffer sb = new StringBuffer();
		
		int modifiers = method.getSignature().getASTNode().getModifiers();
		
		// right now we punt on returning more than one thing
		if(method.getReturnTypes().size() == 0) {
			sb.append("void");
		} else {
			sb.append(typeToString(method.getReturnTypes().get(0)));
		}
		
		sb.append(' ');
		sb.append(getTheClass().getType().getMangledName());
		sb.append(method.getMangledName());
		sb.append('(');
		
		List<String> paramNames = method.getParamNames();
		List<ModifiedType> paramTypes = method.getParamTypes();

		// first param is always a reference to the class, unless it's static
		if(!ModifierSet.isStatic(modifiers))
			sb.append(typeToString(getTheClass().getType())).append(" this, ");

		for(int i=0; i < paramNames.size(); ++i) {
			sb.append(typeToString(paramTypes.get(i).getType()));
			sb.append(" ");
			sb.append(paramNames.get(i));
			sb.append(", ");
		}
		
		if (paramNames.size() > 0 || !ModifierSet.isStatic(modifiers))
			sb.setCharAt(sb.length()-2, ')');
		else
			sb.append(") ");
		
		// methods that are NOT static should be scoped to ONLY this file
		/*if(!ModifierSet.isStatic(modifiers))
			cWriter.print("static "); not sure what this does in c... I'll look it up later */
		
		// print to the C file
		cWriter.print(sb.toString() + '{');
		cWriter.indent();
		
		// print to the meta file
		sb.setCharAt(sb.length()-1, ';');
		metaWriter.print(sb.toString());
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
		StringBuilder sb = new StringBuilder();

		if (!type.isPrimitive())
			sb.append("struct ");
		sb.append(type.getMangledName());
		if (!type.isPrimitive())
			sb.append('*');
		
		return sb.toString();
	}

	private String varToString(TACVariable var) {
		StringBuilder sb = new StringBuilder();

		if (var.isField())
			sb.append("(*this).");
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
		if (shadowLiteral.equals("true"))
			return "1";
		else if (shadowLiteral.equals("false"))
			return "0";
		else if (shadowLiteral.equals("null"))
			return "NULL";
		if (shadowLiteral.endsWith("u"))
			shadowLiteral = shadowLiteral.substring(0, shadowLiteral.length() - 1);
		return shadowLiteral;
	}
	
	public void visit(TACAllocation node) {
		TACVariable var = node.getVariable();
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
			sb.append(" = (");
			sb.append(typeToString(var.getType()));
			sb.append(")calloc(");
			sb.append(node.getSize().getSymbol());
			sb.append(", sizeof(struct ");
			sb.append(var.getType().getMangledName());
			sb.append("));");
			curWriter.print(sb.toString(), node);
		}
	}
	
	public void visit(TACAssign node) {
		StringBuilder sb = new StringBuilder();
		
		if(node.getLHS().isField())
			sb.append("(*this).");
		sb.append(node.getLHS().getSymbol());
		
		sb.append(" = ");
		
		if(node.getRHS().isField())
			sb.append("(*this).");
		sb.append(lit2lit(node.getRHS().getSymbol()));
		
		sb.append(';');
		
		cWriter.print(sb.toString(), node);
	}
	
	public void visit(TACBinaryOperation node) {
		StringBuilder sb = new StringBuilder();
		
		if(node.getLHS().isField())
			sb.append("(*this).");
		sb.append(node.getLHS().getSymbol());
		sb.append(" = ");
		sb.append(node.getRHS().getSymbol());
		
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
		
		sb.append(node.getOperand2().getSymbol());
		sb.append(";");
		
		cWriter.print(sb.toString(), node);
	}
	
	public void visit(TACBranch node) {
		StringBuilder sb = new StringBuilder("if ( ");
		
		if(node.getLHS().isField())
			sb.append("(*this).");
		sb.append(lit2lit(node.getLHS().getSymbol()));
		
		switch(node.getComparision()) {
			case EQUAL: sb.append(" == "); break;
			case GREATER: sb.append(" > "); break;
			case GREATER_EQUAL: sb.append(" >= "); break;
			case IS: sb.append(" ???? "); break;
			case LESS: sb.append(" < "); break;
			case LESS_EQUAL: sb.append(" <= "); break;
			case NOT_EQUAL: sb.append(" != "); break;
		}
		
		if(node.getRHS().isField())
			sb.append("(*this).");
		sb.append(lit2lit(node.getRHS().getSymbol()));
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
		sb.append(lit2lit(node.getLHS().getSymbol()));
		switch(node.getComparision()) {
			case EQUAL: sb.append(" == "); break;
			case GREATER: sb.append(" > "); break;
			case GREATER_EQUAL: sb.append(" >= "); break;
			case IS: sb.append(" ???? "); break;
			case LESS: sb.append(" < "); break;
			case LESS_EQUAL: sb.append(" <= "); break;
			case NOT_EQUAL: sb.append(" != "); break;
		}
		sb.append(lit2lit(node.getRHS().getSymbol()));
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
			sb.append("(*this).");
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
		
		sb.append(node.getMangledName());
		sb.append('(');
		
		/*for(TACVariable param:node.getParameters()) {

			if(param.isField())
				sb.append("(*this).");
			
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
			if(param.isField())
				sb.append("(*this).");
			sb.append(param.getSymbol());
			sb.append(", ");
		}
		for (int i = 1; i < node.getReturnCount(); i++)
		{
			TACVariable ret = node.getReturn(i);
			sb.append('&');
			if(ret.isField())
				sb.append("(*this).");
			sb.append(ret.getSymbol());
			sb.append(", ");
		}
		
		if(node.getParamCount() > 0 || node.getReturnCount() > 1) {
			sb.setCharAt(sb.length()-2, ')');
			sb.setCharAt(sb.length()-1, ';');
		} else {
			sb.append(");");
		}
		
		cWriter.print(sb.toString(), node);
	}
	
	public void visit(TACReturn node) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("return ");
		sb.append(varToString(node.getReturns().get(0)));
		sb.append(';');
		
		cWriter.print(sb.toString(), node);
	}
}
