package shadow.doctool.output;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import shadow.doctool.DocumentationException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.type.Type;

public abstract class DocumentationTemplate 
{
	protected Map<String, String> arguments;
	protected Set<Type> typesToDocument;
	protected Set<Package> packagesToDocument;
	
	public DocumentationTemplate(Map<String, String> arguments,
			Set<Type> typesToDocument, Set<shadow.typecheck.Package> packagesToDocument)
	{
		this.arguments = arguments;
		this.typesToDocument = typesToDocument;
		this.packagesToDocument = packagesToDocument;
	}
	
	/** Outputs the formatted documentation files to the desired directory */
	public abstract void write(Path outputDirectory) throws IOException, ShadowException, DocumentationException;
}
