package shadow.tac;

import shadow.parser.javacc.ShadowException;
import shadow.tac.nodes.TACCast;

/**
 * TACCastConverter is used to turn all high-level casts
 * (TACCast nodes) into conversion code and low-level casts
 * (TACBitcast nodes).
 * 
 * @author Barry Wittman
 */

public class TACCastConverter extends TACAbstractVisitor {

	public void visit(TACCast node) throws ShadowException
	{
		//TODO: Replace all TACCast nodes with conversions and TACBitcast nodes 
	}	
}
