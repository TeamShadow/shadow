package shadow.doctool.output;

import java.io.IOException;
import java.nio.file.Path;

import shadow.doctool.DocumentationException;
import shadow.parser.javacc.ShadowException;

public interface DocumentationTemplate 
{
	/** Outputs the formatted documentation files to the desired directory */
	public void write(Path outputDirectory) throws IOException, ShadowException, DocumentationException;
}
