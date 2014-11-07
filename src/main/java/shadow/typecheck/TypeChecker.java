package shadow.typecheck;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import shadow.ConfigurationException;
import shadow.TypeCheckException;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.Type;

public class TypeChecker {

	private File currentFile;	
	private Package packageTree = null;
	private TypeCollector collector = null;
		
	/**
	 * Given the root node of an AST, type-checks the AST.
	 * @param node The root node of the AST
	 * @return True if the type-check is OK, false otherwise (errors are printed)
	 * @throws ShadowException
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws ConfigurationException 
	 */
	public Node typeCheck(File file) throws ShadowException, ParseException, TypeCheckException, IOException, ConfigurationException
	{	
		currentFile = file;
		HashMap<Package, HashMap<String, Type>> typeTable = new HashMap<Package, HashMap<String, Type>>();
		packageTree = new Package(typeTable);
		ArrayList<String> importList = new ArrayList<String>();
		
		//collector looks over all files and creates types for everything needed
		collector = new TypeCollector(typeTable, importList, packageTree, this);
		//return value is the top node for the class we are compiling		
		Node node = collector.collectTypes( file );	
		
		//Updates types, adding:
		//Fields and methods
		//Type parameters (including necessary instantiations)
		//All types with type parameters (except for declarations) are UninitializedTypes
		//Extends and implements lists
		TypeUpdater updater = new TypeUpdater(typeTable, importList, packageTree);
		Map<Type, Node> nodeTable = collector.getNodeTable();
		nodeTable = updater.update( nodeTable );
				
		collector.setNodeTable( nodeTable );
		
		//As an optimization, print .meta files for .shadow files with no .meta files or with out of date ones
		//printMetaFiles( collector.getFiles() );		
		
		//meta files have already been type checked
		if( !file.getPath().endsWith(".meta")) {
			//The "real" typechecking happens here as each statement is checked for type safety and other features
			StatementChecker checker = new StatementChecker(typeTable, importList, packageTree );
			checker.check(node);
			
			//As an optimization, print .meta file for the .shadow file being checked
			printMetaFile( node, BaseChecker.stripExtension(file.getCanonicalPath()));		
		}
		return node;
	}
	
	protected void printMetaFile( Node node, String file ) {
		try {					
			File shadowVersion = new File( file + ".shadow");
			File metaVersion = new File( file + ".meta");
			//add meta file if one doesn't exist
			if( !metaVersion.exists() || (shadowVersion.exists() && shadowVersion.lastModified() >= metaVersion.lastModified()) ) {	
				PrintWriter out = new PrintWriter(metaVersion);
				node.getType().printMetaFile(out, "");
				out.close();						
			}
		}
		catch(IOException e) {
			System.err.println("Failed to create meta file for " + node.getType() );					
		}		
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
					node.getType().printMetaFile(out, "");
					out.close();						
				}
			}
			catch(IOException e)
			{
				System.err.println("Failed to create meta file for " + node.getType() );					
			}
		}
	}
	
	public Package getPackageTree()
	{
		return packageTree;		
	}
	
	public void addFileDependencies(Type mainType, Set<String> files, Set<String> checkedFiles)
	{		
		try
		{			
			Map<Type, Node> nodeTable = collector.getNodeTable();			
						
			for( Type type : mainType.getReferencedTypes() )
			{	
				if( !type.hasOuter() && !(type instanceof ArrayType) )
				{				
					Node node = nodeTable.get(type.getTypeWithoutTypeArguments());
					File file = node.getFile();
					String fileName = BaseChecker.stripExtension(file.getCanonicalPath());
					if( !checkedFiles.contains(fileName) )
							files.add(fileName);
				}
			}
		}
		catch( IOException e )
		{
			System.err.println(e.getLocalizedMessage());
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
	
	/*
	
	public File getMainFile()
	{
		return mainFile;
	}
	
	public void setMainFile(File file )
	{
		mainFile = file;
	}
	*/
}
