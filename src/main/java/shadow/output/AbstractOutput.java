package shadow.output;

import java.io.File;
import java.io.Writer;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;

public abstract class AbstractOutput extends TACAbstractVisitor
		implements Output
{
	protected TabbedLineWriter writer;
	protected File file;
	public AbstractOutput(TabbedLineWriter out)
	{
		writer = out;
	}
	public AbstractOutput() throws ShadowException
	{
		this(new TabbedLineWriter(System.out));
	}
	public AbstractOutput(Writer out) throws ShadowException
	{
		this(new TabbedLineWriter(out));
	}
	public AbstractOutput(File out) throws ShadowException
	{
		this(new TabbedLineWriter(out));
		file = out;
	}
	
	public File getFile()
	{
		return file;
	}

	public void build(TACModule module) throws ShadowException
	{
		startFile(module);
		for (TACMethod method : module.getMethods())
			build(method, module);
		endFile(module);
	}
	public void build(TACMethod method, TACModule module) throws ShadowException
	{
		startMethod(method, module);
		walk(method);
		endMethod(method, module);
	}

	@Override
	public void startFile(TACModule module) throws ShadowException { }
	@Override
	public void endFile(TACModule module) throws ShadowException { }
	@Override
	public void startMethod(TACMethod method, TACModule module) throws ShadowException { }
	@Override
	public void endMethod(TACMethod method, TACModule module) throws ShadowException { }
}
