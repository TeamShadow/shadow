package shadow.typecheck;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import shadow.TypeCheckException;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Type;

public class TypeChecker {

	private File currentFile;
	protected boolean debug;
	
	private Package packageTree = null;
	private TypeCollector collector = null;
	
	public TypeChecker(boolean debug) {	
		this.debug = debug;
	}
	
	/**
	 * Given the root node of an AST, type-checks the AST.
	 * @param node The root node of the AST
	 * @return True if the type-check is OK, false otherwise (errors are printed)
	 * @throws ShadowException
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public Node typeCheck(File file) throws ShadowException, ParseException, TypeCheckException, IOException
	{	
		currentFile = file;
		HashMap<Package, HashMap<String, Type>> typeTable = new HashMap<Package, HashMap<String, Type>>();
		packageTree = new Package(typeTable);
		ArrayList<String> importList = new ArrayList<String>();
		
		//collector looks over all files and creates types for everything needed
		collector = new TypeCollector(debug, typeTable, importList, packageTree, this);
		//return value is the top node for the class we are compiling		
		Node node = collector.collectTypes( file );	
		
		//Updates types, adding:
		//Fields and methods
		//Type parameters (including necessary instantiations)
		//All types with type parameters (except for declarations) are UninitializedTypes
		//Extends and implements lists
		TypeUpdater updater = new TypeUpdater(debug, typeTable, importList, packageTree);
		Map<Type, Node> nodeTable = collector.getNodeTable();
		nodeTable = updater.update( nodeTable );
				
		collector.setNodeTable( nodeTable );
		
		//As an optimization, print .meta files for .shadow files with no .meta files or with out of date ones
		printMetaFiles( collector.getFiles() );		
		
		//The "real" typechecking happens here as each statement is checked for type safety and other features
		StatementChecker checker = new StatementChecker(debug, typeTable, importList, packageTree );
		checker.check(node);		
	
		return node;
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
	
	public Set<String> getFiles()
	{
		return collector.getFiles().keySet();		
	}
	
	public void addFileDependencies(Type mainType, Set<String> files, Set<String> checkedFiles)
	{		
		try
		{			
			Map<Type, Node> nodeTable = collector.getNodeTable();			
						
			for( Type type : mainType.getReferencedTypes() )
			{				
				Node node = nodeTable.get(type.getTypeWithoutTypeArguments());
				File file = node.getFile();
				String fileName = BaseChecker.stripExtension(file.getCanonicalPath());
				if( !checkedFiles.contains(fileName) )
						files.add(fileName);
			}
		}
		catch( IOException e )
		{
			System.err.println(e.getLocalizedMessage());
		}
		
		/*
		//this was too complicated, got all the files at once
		//now, we get the references needed for each class
		TreeSet<String> files = new TreeSet<String>();
		Map<Type, Node> nodeTable = collector.getNodeTable();
		
		TreeSet<Type> types = new TreeSet<Type>();
		types.add(mainType);
				
		TreeSet<Type> checkedTypes = new TreeSet<Type>();
		
		Type type = null;
		try
		{			
			while( !types.isEmpty() )
			{
				type = types.first().getTypeWithoutTypeArguments();
				types.remove(type);
				Node node = nodeTable.get(type);
				File file = node.getFile();
				files.add( BaseChecker.stripExtension(file.getCanonicalPath()) );
				
				checkedTypes.add(type);
				
				for( Type referencedType : type.getReferencedTypes() )
					if( !checkedTypes.contains(referencedType.getTypeWithoutTypeArguments()) )
						types.add(referencedType.getTypeWithoutTypeArguments());
			}
		}
		catch (IOException e)
		{
			System.err.println("No file found for type " + type );
		}
		
		return files;
		*/
				
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
