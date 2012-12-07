package shadow.output;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.TACVisitor;

public interface Output extends TACVisitor
{
	public abstract void startFile(TACModule module) throws ShadowException;
	public abstract void endFile(TACModule module) throws ShadowException;
	public abstract void startMethod(TACMethod method) throws ShadowException;
	public abstract void endMethod(TACMethod method) throws ShadowException;
}
