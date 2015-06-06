package shadow.doctool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.ConfigurationException;
import shadow.AST.ASTWalker;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeCollector;
import shadow.typecheck.TypeUpdater;
import shadow.typecheck.type.Type;

/** A basic type-checker used to gather enough data for documentation */
public class DocumentationTypeChecker
{
	public List<Node> typeCheck(File file) throws ShadowException, ParseException, TypeCheckException, IOException, ConfigurationException
	{
		HashMap<Package, HashMap<String, Type>> typeTable = new HashMap<Package, HashMap<String, Type>>();
		Package packageTree = new Package(typeTable);
		ArrayList<String> importList = new ArrayList<String>();
		
		// The collector looks over all files and creates types for everything needed.
		// It returns the root node for the class being compiled
		TypeCollector collector = new TypeCollector(typeTable, importList, packageTree, true);
		Map<Type, Node> nodeTable = collector.collectTypes(file);
		Type mainType = collector.getMainType();
		
		//Updates types, adding:
		//Fields and methods
		//Type parameters (including necessary instantiations)
		//All types with type parameters (except for declarations) are UninitializedTypes
		//Extends and implements lists
		TypeUpdater updater = new TypeUpdater(typeTable, importList, packageTree);
		nodeTable = updater.update(nodeTable);
		Node mainNode = nodeTable.get(mainType);
		
		DocumentationVisitor docVisitor = new DocumentationVisitor();
		ASTWalker docWalker = new ASTWalker(docVisitor);
		docWalker.walk(mainNode);
		
		return null;
	}
}
