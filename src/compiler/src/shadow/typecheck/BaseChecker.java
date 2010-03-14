package shadow.typecheck;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTUtils;
import shadow.AST.AbstractASTVisitor;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.Node;
import shadow.typecheck.type.Type;

public abstract class BaseChecker extends AbstractASTVisitor {

	protected ArrayList<String> errorList;
	private Map<String, Type> typeTable; /** Holds all of the types we know about */
	protected List<String> importList; /** Holds all of the types we know about */
	protected Type currentType = null;
	protected boolean debug;	
	
	// these are constants for our error messages to keep things consistent
	public static enum Error {
		INVL_TYP		{ String getStr() { return "INVALID TYPE"; } },
		MULT_SYM		{ String getStr() { return "MULTIPLY DEFINED SYMBOL"; } },
		MULT_MTH		{ String getStr() { return "MULTIPLY DEFINED METHODS"; } },
		UNDEC_VAR		{ String getStr() { return "UNDECLARED VARIABLE"; } },
		UNDEF_TYP		{ String getStr() { return "UNDEFINED TYPE"; } },
		TYPE_MIS		{ String getStr() { return "TYPE MISMATCH"; } };
		
		abstract String getStr();
	}
	
	public final Map<String, Type> getTypeTable()
	{
		return typeTable;
	}
	
	public void addType( String name, Type type  )
	{
		typeTable.put(name, type);		
	}
	
	public final List<String> getImportList()
	{
		return importList;
	}
	
	public BaseChecker(boolean debug, Map<String, Type> typeTable, List<String> importList  ) {
		errorList = new ArrayList<String>();
		this.debug = debug;
		this.typeTable = typeTable;
		this.importList = importList;
	}
	
	protected Object pushUpType(Node node, Boolean secondVisit, int child) {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		// simply push the type up the tree
		node.setType(node.jjtGetChild(child).getType());
		
		return WalkType.POST_CHILDREN;
	}

	protected Object pushUpType(Node node, Boolean secondVisit) {
		return pushUpType(node, secondVisit, 0);
	}

	/**
	 * Adds an error message to the list errors we keep until the end.
	 * @param node The node where the error occurred. This will be printed in the standard format.
	 * @param msg The message to communicate to the user.
	 */
	protected void addError(Node node, String msg) {
		String error = "[" + ASTUtils.getLineCol(node) + "] : " + msg;
		
		if(debug)
			errorList.add(ASTUtils.getFileAndLine(3) + error);
		else
			errorList.add(error);
	}
	
	/**
	 * Adds an error messages to the list of errors.
	 * @param node The node where the error occurred. This will be printed in standard format.
	 * @param type One of the pre-defined types of errors.
	 * @param msg The message associated with the error.
	 */
	protected void addError(Node node, Error type, String msg) {
		String error = "[" + ASTUtils.getLineCol(node) + "] " + type.getStr() + ": " + msg; 
		
		if(debug)
			errorList.add(ASTUtils.getFileAndLine(3) + error);
		else
			errorList.add(error);
	}
	
	/**
	 * Adds an error messages to the list of errors.
	 * @param node The node where the error occurred. This will be printed in standard format.
	 * @param type One of the pre-defined types of errors.
	 */
	protected void addError(Node node, Error type) {
		String error = "[" + ASTUtils.getLineCol(node) + "] " + type.getStr() + ": "; 
		
		if(debug)
			errorList.add(ASTUtils.getFileAndLine(3) + error);
		else
			errorList.add(error);
	}
	
	/**
	 * Print out the list of errors to standard error.
	 */
	public void printErrors() {
		printErrors(System.err);
	}
	
	/**
	 * Print out the list of errors to the given stream.
	 * @param stream The stream to print the errors to.
	 */
	public void printErrors(PrintStream stream) {
		for(String msg:errorList) {
			stream.println(msg);
		}
	}
	
	
	public final Type lookupType( String name )
	{
				return lookupType( name, currentType );
	}
	
	public final Type lookupType( String name, Type outerClass )
	{	
		if( outerClass == null )
		{
			//check imports here, eventually
			return typeTable.get( name );
		}
		else
		{
			Type type = typeTable.get( outerClass + "." + name);
			if( type != null )
				return type;
			else
				//recursive calls
				return lookupType( name, outerClass.getOuter() );
		}	 
	}
	
	public int getErrorCount() {
		return errorList.size();
	}
}
