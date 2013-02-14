package shadow.typecheck;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		//collector looks over all files and creates types for everything needed
		TypeCollector collector = new TypeCollector(debug, typeTable, importList, packageTree, this);
		collector.collectTypes( file, node );	
		
		// see how many errors we found
		if(collector.getErrorCount() > 0)
		{
			collector.printErrors();
			return false;
		}		
		
		//add type parameters for types, making them UninitializedTypes 
		TypeUpdater updater = new TypeUpdater(debug, typeTable, importList, packageTree);
		updater.updateTypes( collector.getFiles() );
		//done!
		

		// see how many errors we found
		if(updater.getErrorCount() > 0)
		{
			updater.printErrors();
			return false;
		}
		
		//we must add fields and methods with uninstantiated types
		//otherwise parameterized types won't have fields and methods added to them at all
		//then we add fields and methods
		FieldAndMethodChecker builder = new FieldAndMethodChecker(debug, typeTable, importList, packageTree, collector.getNodeTable() );
		builder.buildTypes( collector.getFiles() );		
		//not done!		
		
		// see how many errors we found
		if(builder.getErrorCount() > 0)
		{
			builder.printErrors();
			return false;
		}
		
		logger.debug("TYPE BUILDING DONE");
		
		
		//then we add extends and implements and check those, and check duplicate methods
		ImplementsAndExtendsChecker extendAdder = new ImplementsAndExtendsChecker(debug, typeTable, importList, packageTree);
		extendAdder.checkImplementsAndExtends(collector.getFiles());
		//done
		
		
		
		// see how many errors we found
		if(extendAdder.getErrorCount() > 0)
		{
			extendAdder.printErrors();
			return false;
		}

		
		
		//then we instantiate all the types after checking to make sure that we can
		//One reason we have to do this is to catch incomplete generic types:
		//class A<T is A<T>>
		//Another is that we can't instantiate before fields and methods are added
		TypeInstantiater instantiater = new TypeInstantiater( debug, typeTable, importList, packageTree );
		instantiater.instantiateTypes( collector.getNodeTable() );
		//maybe done!			

		// see how many errors we found
		if(instantiater.getErrorCount() > 0)
		{
			instantiater.printErrors();
			return false;
		}	
				
		printMetaFiles( collector.getFiles() );		
		
		ClassChecker checker = new ClassChecker(debug, typeTable, importList, packageTree );
		ASTWalker walker = new ASTWalker(checker);
		
		// now go through and check the whole class
		walker.walk(node);	

		// see how many errors we found
		if(checker.getErrorCount() > 0) {
			checker.printErrors();
			return false;
		}
		
		return true;
	}
	
	protected void printMetaFiles( Map<String, Node> files )
	{
		//produce meta files	
		for( Map.Entry<String, Node> entry : files.entrySet() )
		{
			Node node = entry.getValue();
			String file = entry.getKey();
			try
			{					
				File shadowVersion = new File( file + ".shadow");
				File metaVersion = new File( file + ".meta");
				//add meta file if one doesn't exist
				if( !metaVersion.exists() || (shadowVersion.exists() && shadowVersion.lastModified() >= metaVersion.lastModified()) )  
				{	
					PrintWriter out = new PrintWriter(metaVersion);
					ClassInterfaceBaseType type = (ClassInterfaceBaseType) node.getType();
					type.printMetaFile(out, "");
					out.close();						
				}
			}
			catch(IOException e)
			{
				System.err.println("Failed to create meta file for " + node.getType() );					
			}
		}
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
