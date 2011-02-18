package shadow.typecheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

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
		// Here is where we'd walk the import statements, and collect the types for those files
		// Right now we are only collecting the types from the current file
	 
		 
		TypeCollector collector = new TypeCollector(debug);
		collector.collectTypes( file, node );
		//ASTWalker walker = new ASTWalker( collector );		
		//walker.walk(node);
				
		//collector.addOutsideTypes(file);
		Map<String, Type> typeTable = collector.getTypeTable();
		List<String> importList = collector.getImportList();
		
		// see how many errors we found
		if(collector.getErrorCount() > 0)
		{
			collector.printErrors(System.out);
			return false;
		}
		
		FieldAndMethodChecker builder = new FieldAndMethodChecker(debug, typeTable, importList );
		builder.buildTypes( collector.getFiles() );
		 //walker = new ASTWalker(builder);
		
		// walk the tree building types
		//walker.walk(node);
		
		if(debug)
			System.out.println("DEBUG: TYPE BUILDING DONE");
		
		// print out the type table as it stands now
/*		System.out.println("TYPE TABLE:");
		for(String tn:typeTable.keySet())
			System.out.println(tn + ": " + typeTable.get(tn));
*/		
		// see how many errors we found
		if(builder.getErrorCount() > 0) {
			builder.printErrors(System.out);
			return false;
		}
		
		ClassChecker checker = new ClassChecker(debug, typeTable, importList );
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
