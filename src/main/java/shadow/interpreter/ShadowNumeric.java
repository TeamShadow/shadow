package shadow.interpreter;

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
		
		return super.callMethod(method, arguments);
	}
	public abstract ShadowNumber abs() throws InterpreterException;
	public abstract ShadowNumber cos() throws InterpreterException;
	public abstract ShadowNumber sin() throws InterpreterException;
	public abstract ShadowNumber power(ShadowNumber number) throws InterpreterException;
	public abstract ShadowNumber squareRoot() throws InterpreterException;
	public abstract ShadowNumber logBase10() throws InterpreterException;
	public abstract ShadowNumber logBase2() throws InterpreterException;
	public abstract ShadowNumber logBaseE() throws InterpreterException;
}
