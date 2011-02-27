package shadow.typecheck;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker.WalkType;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.type.PackageType;
import shadow.typecheck.type.Type;

public abstract class BaseChecker extends AbstractASTVisitor {

	protected ArrayList<String> errorList;
	protected Map<String, Type> typeTable; /** Holds all of the types we know about */
	protected List<File> importList; /** Holds all of the imports we know about */
	protected PackageType packageTree;
	

	/** Holds the package tree structure (for name lookups) */
	protected Type currentType = null;
	protected boolean debug;	
	
	// these are constants for our error messages to keep things consistent
	public static enum Error {
		INVL_TYP		{ public String toString()  { return "INVALID TYPE"; } },
		MULT_SYM		{ public String toString()  { return "MULTIPLY DEFINED SYMBOL"; } },
		MULT_MTH		{ public String toString()  { return "MULTIPLY DEFINED METHODS"; } },
		UNDEC_VAR		{ public String toString()  { return "UNDECLARED VARIABLE"; } },
		UNDEF_TYP		{ public String toString()  { return "UNDEFINED TYPE"; } },
		TYPE_MIS		{ public String toString()  { return "TYPE MISMATCH"; } },
		INVL_MOD		{ public String toString() { return "INVALID MODIFIER"; } };
		
		//abstract String getStr();
	}
	
	public final Map<String, Type> getTypeTable()
	{
		return typeTable;
	}
	
	public void addType( String name, Type type  )
	{
		typeTable.put(name, type);		
	}
	
	public final List<File> getImportList()
	{
		return importList;
	}
	
	public BaseChecker(boolean debug, Map<String, Type> typeTable, List<File> importList, PackageType packageTree  ) {
		errorList = new ArrayList<String>();
		this.debug = debug;
		this.typeTable = typeTable;
		this.importList = importList;
		this.packageTree = packageTree;
	}
	
	/*
	protected Object pushUpType(Node node, Boolean secondVisit, int child) {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		// simply push the type up the tree
		node.setType(node.jjtGetChild(child).getType());
		
		return WalkType.POST_CHILDREN;
	}

	protected Object pushUpType(Node node, Boolean secondVisit) {
		return pushUpType(node, secondVisit, 0);
	}*/
	
	protected Object pushUpType(SimpleNode node, Boolean secondVisit) {
		return pushUpType(node, secondVisit, 0);
	}
	
	protected Object pushUpType(SimpleNode node, Boolean secondVisit, int child) {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		if( node.jjtGetNumChildren() > child )
		{			
			// simply push the type up the tree
			Node childNode = node.jjtGetChild(child); 
			node.setType(childNode.getType());
			node.setModifiers( childNode.getModifiers());
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	protected void pushUpModifiers( SimpleNode node ) //only pushes up modifiers if there is a single child
	{
		if( node.jjtGetNumChildren() == 1 )
		{
			Node child = node.jjtGetChild(0);
			node.setModifiers( child.getModifiers() );
		}
	}
	
	
	

	/**
	 * Adds an error message to the list errors we keep until the end.
	 * @param node The node where the error occurred. This will be printed in the standard format.
	 * @param msg The message to communicate to the user.
	 */
	protected void addError(Node node, String msg) {
		addError( node, null, msg );
	}
	
	/**
	 * Adds an error messages to the list of errors.
	 * @param node The node where the error occurred. This will be printed in standard format.
	 * @param type One of the pre-defined types of errors.
	 * @param msg The message associated with the error.
	 */
	protected void addError(Node node, Error type, String msg) {
		String error = "";
		
		if( node != null )
		{
			if( node.getFile() != null )
				error += "(" + node.getFile().getName() + ")";
			error += "[" + ASTUtils.getLineCol(node) + "] ";
		}
		
		if( type != null )
			error += type;
		
		if( msg != null && msg.length() > 0 )
			error += ": " + msg; 
		
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
		addError( node, type, "" );
	}
	
	protected void addError(Error type, String message) {
		addError( null, type, message );
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
	
	public PackageType getPackageTree()
	{
		return packageTree;
	}
}
