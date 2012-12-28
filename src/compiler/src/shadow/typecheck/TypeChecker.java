package shadow.typecheck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;

import shadow.Loggers;
import shadow.AST.ASTWalker;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.ClassInterfaceBaseType;

public class TypeChecker {
	private static final Log logger = Loggers.TYPE_CHECKER;
	
	
	private File currentFile;
	private File mainFile;
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
	 * @throws IOException 
	 */
	public boolean typeCheck(Node node, File file) throws ShadowException, ParseException, IOException
	{	 
		mainFile = file;
		HashMap<Package, HashMap<String, ClassInterfaceBaseType>> typeTable = new HashMap<Package, HashMap<String, ClassInterfaceBaseType>>();
		Package packageTree = new Package(typeTable);
		ArrayList<String> importList = new ArrayList<String>();
		TypeCollector collector = new TypeCollector(debug, typeTable, importList, packageTree, this);
		collector.collectTypes( file, node );	
		
		// see how many errors we found
		if(collector.getErrorCount() > 0)
		{
			collector.printErrors();
			return false;
		}
		
		FieldAndMethodChecker builder = new FieldAndMethodChecker(debug, typeTable, importList, packageTree, collector.getNodeTable() );
		builder.buildTypes( collector.getFiles() );

		logger.debug("TYPE BUILDING DONE");
		
	
		// see how many errors we found
		if(builder.getErrorCount() > 0)
		{
			builder.printErrors();
			return false;
		}
		
		ClassChecker checker = new ClassChecker(debug, typeTable, importList, packageTree );
		ASTWalker walker = new ASTWalker(checker);
		
		// now go through and check the whole class
		walker.postorderWalk(node);	// visit each node after its children

		// see how many errors we found
		if(checker.getErrorCount() > 0) {
			checker.printErrors();
			return false;
		}
		
		return true;
	}
	
	public File getCurrentFile()
	{
		return currentFile;
	}
	
	public void setCurrentFile(File file )
	{
		currentFile = file;
	}
	
	public File getMainFile()
	{
		return mainFile;
	}
	
	public void setMainFile(File file )
	{
		mainFile = file;
	}
}
