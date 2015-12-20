package shadow.doctool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shadow.ConfigurationException;
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
	public static Set<Type> typeCheck(List<File> files) throws ShadowException, TypeCheckException, ParseException, IOException, ConfigurationException
	{			
		Package packageTree = new Package();		
		
		// The collector looks over all files and creates types for everything needed.
		// It returns the root node for the class being compiled
		TypeCollector collector = new TypeCollector(packageTree, true);
		Map<Type, Node> nodeTable = collector.collectTypes(files);
		
		//Updates types, adding:
		//Fields and methods
		//Type parameters (including necessary instantiations)
		//All types with type parameters (except for declarations) are UninitializedTypes
		//Extends and implements lists
		TypeUpdater updater = new TypeUpdater(packageTree);
		nodeTable = updater.update(nodeTable);
		
		return collector.getInitialFileTypes();
	}
}
