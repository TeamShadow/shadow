package shadow.doctool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import shadow.ConfigurationException;
import shadow.Loggers;
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
	private static final Logger logger = Loggers.DOC_TOOL;
	
	public static Type typeCheck(File file) throws ShadowException, TypeCheckException, ParseException, IOException, ConfigurationException
	{
		long startTime = System.currentTimeMillis(); // Time the type checking
		
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
		
		logger.info("Successfully type-checked " + file.toString() + " in "
				+ (System.currentTimeMillis() - startTime) + "ms");
		
		return mainNode.getType();
	}
}
