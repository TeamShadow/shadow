package shadow.parser.javacc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Barry Wittman
 * Subclass of ShadowParser that adds file-tracking support for more meaningful error messages. 
 * This approach is cleaner than adding methods to the generated ShadowParser because existing methods
 * (like generateParseException()) cannot be changed.  
 */

public class ShadowFileParser extends ShadowParser
{
	private File file;
	
	public ShadowFileParser(File file) throws FileNotFoundException
	{
		super(new FileInputStream(file));
		this.file = file;
	}
	
	public File getFile()
	{
		return file;
	}
	
	@Override
	public ParseException generateParseException()
	{
		ParseException exception = super.generateParseException();
		return new ParseException(exception, file);
	}
	
}