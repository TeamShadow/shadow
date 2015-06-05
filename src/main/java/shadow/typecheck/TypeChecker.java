package shadow.typecheck;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import shadow.ConfigurationException;
import shadow.Job;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.Type;

public class TypeChecker {
	/**
	 * Given the main file to compile, checks it
	 * @param file the file to compile
	 * @param currentJob configuration information about the current compilation
	 * @return nodes corresponding to all the AST nodes for each class
	 * @throws ShadowException
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws ConfigurationException 
	 */
	public List<Node> typeCheck(File file, Job currentJob) throws ShadowException, ParseException, TypeCheckException, IOException, ConfigurationException
	{		
		HashMap<Package, HashMap<String, Type>> typeTable = new HashMap<Package, HashMap<String, Type>>();
		Package packageTree = new Package(typeTable);
		ArrayList<String> importList = new ArrayList<String>();
		
		//collector looks over all files and creates types for everything needed
		TypeCollector collector = new TypeCollector(typeTable, importList, packageTree, currentJob);
		//return value is the top node for the class we are compiling		
		Map<Type, Node> nodeTable = collector.collectTypes( file );
		Type mainType = collector.getMainType();
		
		//Updates types, adding:
		//Fields and methods
		//Type parameters (including necessary instantiations)
		//All types with type parameters (except for declarations) are UninitializedTypes
		//Extends and implements lists
				
		TypeUpdater updater = new TypeUpdater(typeTable, importList, packageTree);
		nodeTable = updater.update( nodeTable );
		
		
		StatementChecker checker = new StatementChecker(typeTable, importList, packageTree );		
				
		List<Node> allNodes = new ArrayList<Node>();
		
		//only add nodes for outer types
		for( Node node : nodeTable.values())
			if( !node.getType().hasOuter() )
				allNodes.add(node);
		
		// do real typechecking, which updates referenced types
		for(Node node: allNodes) {	
			File nodeFile = node.getFile();
			if( !nodeFile.getPath().endsWith(".meta")) {
				//The "real" typechecking happens here as each statement is checked for type safety and other features
				checker.check(node);				
				//As an optimization, print .meta file for the .shadow file being checked
				printMetaFile( node, BaseChecker.stripExtension(nodeFile.getCanonicalPath()));
			}
		}
		
		//now that all the nodes are typechecked, we know which types are referenced by the main type (even indirectly)
		TreeSet<Type> referencedTypes = new TreeSet<Type>();
		referencedTypes.add(mainType); // almost everything gets figured out from there
		addStandardTypes(referencedTypes);
		
		Set<Type> neededTypes = new HashSet<Type>();
		
		
		while( !referencedTypes.isEmpty() ) {			
			Type next = referencedTypes.first();
			Type simplified = next.getTypeWithoutTypeArguments();
			
			if( !(simplified instanceof ArrayType) && !simplified.hasOuter() && neededTypes.add(simplified) )				
				referencedTypes.addAll(simplified.getReferencedTypes());
					
			referencedTypes.remove(next);
		}
		
		//return only those nodes corresponding to needed types
		List<Node> neededNodes = new ArrayList<Node>();
		
		for( Node node : allNodes )
			if( neededTypes.contains(node.getType()) )
				neededNodes.add(node);
		
		return neededNodes;
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
	
	protected void addStandardTypes(Set<Type> types) {
		Package standard = Type.OBJECT.getPackage();
		types.addAll(standard.getTypes());		
		
		// a few io classes are absolutely necessary for a console program
		Package io = standard.getParent().getChild("io");
		types.add(io.getType("File"));
		types.add(io.getType("IOException"));
		types.add(io.getType("Path"));
	}
}
