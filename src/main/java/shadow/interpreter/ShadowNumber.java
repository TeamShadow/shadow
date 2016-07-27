package shadow.interpreter;

import shadow.ShadowException;

public abstract class ShadowNumber extends ShadowValue {
	
	public ShadowNumber()
	{
		super();
	}
	
	public ShadowNumber(int modifiers)
	{
		super(modifiers);
	}

	public abstract ShadowNumber abs() throws ShadowException;
	public abstract ShadowNumber cos() throws ShadowException;
	public abstract ShadowNumber sin() throws ShadowException;
	public abstract ShadowNumber power(ShadowNumber number) throws ShadowException;
	public abstract ShadowNumber squareRoot() throws ShadowException;
	public abstract ShadowNumber logBase10() throws ShadowException;
	public abstract ShadowNumber logBase2() throws ShadowException;
	public abstract ShadowNumber logBaseE() throws ShadowException;
	public abstract ShadowNumber max(ShadowNumber number) throws ShadowException;
	public abstract ShadowNumber min(ShadowNumber number) throws ShadowException;
}
