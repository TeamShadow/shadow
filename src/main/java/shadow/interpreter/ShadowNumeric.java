package shadow.interpreter;

import shadow.ShadowException;

/**
 * ShadowNumeric doesn't map to a class or interface in Shadow, but it's a convenient layer of
 * abstraction.
 * @author Barry Wittman
 */
public abstract class ShadowNumeric extends ShadowNumber {

	
	protected ShadowNumeric() {
    }

    protected ShadowNumeric(int modifiers) {
        super(modifiers);
    }
	
	@Override
    public ShadowValue callMethod(String method, ShadowValue ... arguments) throws InterpreterException {
		try {
		
			if(arguments.length == 0) {
				switch(method) {
				case "abs": return abs();
				case "cos": return cos();
				case "sin": return sin();
				case "squareRoot": return squareRoot();
				case "logBase10": return logBase10();
				case "logBase2": return logBase2();
				case "logBaseE": return logBaseE();
				}				
			}
			else if(arguments.length == 1 && arguments[0] instanceof ShadowNumber) {
				ShadowNumber number = (ShadowNumber)arguments[0];
				switch(method) {
				case "power": return power(number);
				case "max": return max(number);
				case "min": return min(number);
				}
			}
		}
		catch(ShadowException e) {
		}
		
		return super.callMethod(method, arguments);
	}
	public abstract ShadowNumber abs() throws ShadowException;
	public abstract ShadowNumber cos() throws ShadowException;
	public abstract ShadowNumber sin() throws ShadowException;
	public abstract ShadowNumber power(ShadowNumber number) throws ShadowException;
	public abstract ShadowNumber squareRoot() throws ShadowException;
	public abstract ShadowNumber logBase10() throws ShadowException;
	public abstract ShadowNumber logBase2() throws ShadowException;
	public abstract ShadowNumber logBaseE() throws ShadowException;
}
