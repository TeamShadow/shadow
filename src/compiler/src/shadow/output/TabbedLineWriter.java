package shadow.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class TabbedLineWriter extends FilterWriter
{
	private int indent;
	private final String lineSeparator;
	public TabbedLineWriter(Writer out)
	{
		super(out);
		indent = 0;
		lineSeparator = System.getProperty("line.separator");
	}
	
	public void writeLine() throws IOException
	{
		out.write(lineSeparator);
	}
	public void writeTabs() throws IOException
	{
		for (int i = 0; i < indent; i++)
			out.write('\t');
	}
	
	public void writeLeftLine(int c) throws IOException
	{
		out.write(c);
		writeLine();
	}
	public void writeLeftLine(String str) throws IOException
	{
		out.write(str, 0, str.length());
		writeLine();
	}
	
	public void writeLine(int c) throws IOException
	{
		writeTabs();
		out.write(c);
		writeLine();
	}
	public void writeLine(String str) throws IOException
	{
		writeTabs();
		out.write(str, 0, str.length());
		writeLine();
	}

	public void indent()
	{
		indent++;
	}
	public void outdent()
	{
		indent--;
	}
}
