package shadow.typecheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;

import shadow.AST.ASTWalker;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Type;

public class TypeChecker {

	protected boolean debug;
	
	public TypeChecker(boolean debug) {	
		this.debug = debug;
	}
	
	/**
	 * Given the root node of an AST, type-checks the AST.
	 * @param node The root node of the AST
	 * @return True of the type-check is OK, false otherwise (errors are printed)
	 * @throws ShadowException
	 * @throws ParseException 
	 * @throws FileNotFoundException 
	 */
	public boolean typeCheck(Node node, File file) throws ShadowException, FileNotFoundException, ParseException
	{	 
		HashMap<Package, HashMap<String, Type>> typeTable = new HashMap<Package, HashMap<String, Type>>();
		Package packageTree = new Package(typeTable);
		LinkedList<File> importList = new LinkedList<File>();
		TypeCollector collector = new TypeCollector(debug, typeTable, importList, packageTree);
		collector.collectTypes( file, node );	
		
		// see how many errors we found
		if(collector.getErrorCount() > 0)
		{
			collector.printErrors(System.out);
			return false;
		}
		
		FieldAndMethodChecker builder = new FieldAndMethodChecker(debug, typeTable, importList, packageTree );
		builder.buildTypes( collector.getFiles() );

		if(debug)
			System.out.println("DEBUG: TYPE BUILDING DONE");
		
	
		// see how many errors we found
		if(builder.getErrorCount() > 0)
		{
			builder.printErrors(System.out);
			return false;
		}
		
		ClassChecker checker = new ClassChecker(debug, typeTable, importList, packageTree );
		ASTWalker walker = new ASTWalker(checker);
		
		// now go through and check the whole class
		walker.postorderWalk(node);	// visit each node after its children

		// see how many errors we found
		if(checker.getErrorCount() > 0) {
			checker.printErrors(System.out);
			return false;
		}
		
		return true;
	}
	

}
