package shadow.doctool;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.cli.ParseException;

import shadow.Arguments;
import shadow.Configuration;
import shadow.ConfigurationException;
import shadow.Job;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.type.Type;

public class DocumentationTool 
{
	public static void main(String[] args) throws ConfigurationException, IOException, ParseException, ShadowException, shadow.parser.javacc.ParseException, TypeCheckException 
	{
		// Detect and establish the current settings and arguments
		Arguments compilerArgs = new Arguments(args);
		Configuration.buildConfiguration(compilerArgs, false);
		Job currentJob = new Job(compilerArgs);
		
		Type.clearTypes();
		DocumentationTypeChecker checker = new DocumentationTypeChecker();
		
		Path mainFile = currentJob.getMainFile();

		List<Node> nodes;

		try {
			nodes = checker.typeCheck(mainFile.toFile(), currentJob);
		}
		catch( TypeCheckException e ) {
			//logger.error(mainFile + " FAILED TO TYPE CHECK");
			throw e;
		}
	}
}
