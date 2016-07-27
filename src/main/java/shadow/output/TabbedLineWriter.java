package shadow.output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;

import shadow.ShadowException;

public class TabbedLineWriter
{
	private boolean lineNumbers;
	private int indent, line;
	private Writer out;
	private boolean atLineStart = true;
	public TabbedLineWriter(BufferedWriter writer) throws ShadowException
	{
		out = writer;
	}
	public TabbedLineWriter(Writer writer) throws ShadowException
	{
		out = new BufferedWriter(writer);
	}
	public TabbedLineWriter(OutputStream output) throws ShadowException
	{
		out = new OutputStreamWriter(output);
	}
	public TabbedLineWriter(String file) throws ShadowException
	{
		try
		{
			out = new FileWriter(file);
		}
		catch (IOException ex)
		{
			throw new OutputException(ex.getLocalizedMessage());
		}
	}
	public TabbedLineWriter(Path file) throws ShadowException
	{
		try
		{
			out = new FileWriter(file.toFile());
		}
		catch (IOException ex)
		{
			throw new OutputException(ex.getLocalizedMessage());
		}
	}

	public void setLineNumbers(boolean value)
	{
		lineNumbers = value;
	}

	public void indent()
	{
		++indent;
	}
	public void indent(int amount)
	{
		indent += amount;
	}
	public void outdent()
	{
		if (--indent < 0)
			indent = 0;
	}
	public void outdent(int amount)
	{
		if ((indent -= amount) < 0)
			indent = 0;
	}
	private static final int TAB_SIZE = 4, INDENT_BUFFER_SIZE = 8 * TAB_SIZE;
	private static char[] indentBuffer;
	private void writeIndent(int amount) throws IOException
	{
		if (amount < 0)
			return;
		char[] b = indentBuffer;
		if (b == null)
		{
			indentBuffer = b = new char[INDENT_BUFFER_SIZE];
			for (int i = 0; i < b.length; i++)
				b[i] = ' ';
		}
		while (amount > 0)
		{
			int current = Math.min(amount, INDENT_BUFFER_SIZE);
			out.write(b, 0, amount);
			amount -= current;
		}
	}
	private void writeIndent() throws IOException
	{
		writeIndent(indent * TAB_SIZE);
	}
	private void writeLine() throws IOException
	{
		if (lineNumbers)
		{
			String lineString = String.valueOf(++line);
			writeIndent(TAB_SIZE - lineString.length());
			out.write(lineString);
			out.write(": ");
		}
	}
	private static String newline;
	private void writeNewline() throws IOException
	{
		String nl = newline;
		if (nl == null)
			newline = nl = System.getProperty("line.separator");
		out.write(nl);
		out.flush();
		
		atLineStart = true;
	}
	public void write() throws ShadowException
	{
		try
		{
			writeLine();
			writeNewline();
		}
		catch (IOException ex)
		{
			throw new OutputException(ex.getLocalizedMessage());
		}
	}
	public void write(String string) throws ShadowException
	{
		try
		{
			writeLine();
			writeIndent();
			out.write(string);
			writeNewline();
		}
		catch (IOException ex)
		{
			throw new OutputException(ex.getLocalizedMessage());
		}
	}
	public void writeLeft(String string) throws ShadowException
	{
		try
		{
			writeLine();
			out.write(string);
			writeNewline();
		}
		catch (IOException ex)
		{
			throw new OutputException(ex.getLocalizedMessage());
		}
	}
	public void write(char c) throws ShadowException
	{
		try
		{
			writeLine();
			writeIndent();
			out.write(c);
			writeNewline();
		}
		catch (IOException ex)
		{
			throw new OutputException(ex.getLocalizedMessage());
		}
	}
	public void writeNoLine(String string) throws ShadowException
	{
		try
		{
			if (atLineStart) {
				writeLine();
				writeIndent();
				atLineStart = false;
			}
			
			out.write(string);
		}
		catch (IOException ex)
		{
			throw new OutputException(ex.getLocalizedMessage());
		}
	}
	public void writeNoLine(char c) throws ShadowException
	{
		try
		{
			if (atLineStart) {
				writeLine();
				writeIndent();
				atLineStart = false;
			}
			
			out.write(c);
		}
		catch (IOException ex)
		{
			throw new OutputException(ex.getLocalizedMessage());
		}
	}
	
	public void close() throws IOException
	{
		if( out != null ) {
			out.flush();
			out.close();
		}
	}
}
