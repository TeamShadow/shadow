package doctool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.ConfigurationException;
import shadow.Job;
import shadow.AST.ASTWalker;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeChecker;
import shadow.typecheck.TypeCollector;
import shadow.typecheck.TypeUpdater;
import shadow.typecheck.type.Type;

/** A type checker geared towards generating documentation */
public class DocToolTypeChecker extends TypeChecker
{
	@Override
	public List<Node> typeCheck(File file, Job currentJob) throws ShadowException, ParseException, TypeCheckException, IOException, ConfigurationException
	{
		currentFile = file;
		HashMap<Package, HashMap<String, Type>> typeTable = new HashMap<Package, HashMap<String, Type>>();
		packageTree = new Package(typeTable);
		ArrayList<String> importList = new ArrayList<String>();
		
		// The collector looks over all files and creates types for everything needed.
		// It returns the root node for the class being compiled
		collector = new TypeCollector(typeTable, importList, packageTree, this, currentJob);
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
		
		DocToolVisitor docVisitor = new DocToolVisitor();
		ASTWalker docWalker = new ASTWalker(docVisitor);
		docWalker.walk(mainNode);
		
		return null;
	}
}
