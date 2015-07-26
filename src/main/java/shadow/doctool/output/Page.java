package shadow.doctool.output;

import java.io.IOException;
import java.nio.file.Path;

import shadow.doctool.DocumentationException;
import shadow.parser.javacc.ShadowException;

public interface Page 
{
	public Path getRelativePath();
	public void write(Path root) throws IOException, ShadowException, DocumentationException;
}
