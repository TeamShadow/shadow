package shadow.output;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.TACVisitor;

public interface Output extends TACVisitor
{
	void startFile(TACModule module) throws ShadowException;
	void endFile(TACModule module) throws ShadowException;
	void startMethod(TACMethod method) throws ShadowException;
	void endMethod(TACMethod method) throws ShadowException;
}
