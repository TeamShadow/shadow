package shadow.parse;

import java.io.IOException;
import java.nio.file.Path;

import org.antlr.v4.runtime.ANTLRFileStream;

public class PathStream extends ANTLRFileStream {	
	private final Path path;	
	
	public PathStream(Path path) throws IOException 
	{
		this(path, "UTF-8");
	}
	
	public PathStream(Path path, String encoding) throws IOException 
	{
		super(path.toAbsolutePath().toString(), encoding);
		this.path = path.toAbsolutePath();
	}
	
	public Path getPath() 
	{
		return path;		
	}
}
