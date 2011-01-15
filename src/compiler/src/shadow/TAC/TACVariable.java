package shadow.TAC;

import shadow.typecheck.type.Type;

public class TACVariable {
	private Type type;		/** The type of the variable */
	private String symbol;	/** The symbol of the variable */
	private int size;		/** The size of the var in memory in bytes */
	private boolean isRef;	/** Indicates if we have a reference or not */
	private boolean isLiteral;	/** symbol holds the value of the literal */
	
	/**
	 * Returns the default value of a given type.
	 */
	static public TACVariable getDefault(Type type) {
		if(type.isPrimitive())
			return new TACVariable("0", type, true);
		else if(type.isString())
			return new TACVariable("\"\"", type, true);
		else
			return new TACVariable("null", type, true);
		
	}
	
	static public TACVariable getBooleanLiteral(boolean value) {
		Type t = new Type("boolean");
		
		if(value)
			return new TACVariable("true", t, true);
		else
			return new TACVariable("false", t, true);
	}
	
	public TACVariable(String symbol, Type type, boolean isLiteral) {
		this(symbol, type);
		this.isLiteral = isLiteral;
	}
	
	public TACVariable(String symbol, Type type) {
		this.type = type;
		this.symbol = symbol;
		this.isLiteral = false;
		
		if(type.isPrimitive()) {
			isRef = false;
			
			// TODO: Figure out the size of this variable on disk
			if(type == Type.BOOLEAN || type == Type.BYTE) size = 1;
			else if(type == Type.CODE || type == Type.INT) size = 4;
			else if(type == Type.DOUBLE || type == Type.LONG) size = 8;
		}
	}
	
	public String toString() {
		return symbol + "(" + type + (isLiteral ? " literal" : "") + (isRef ? " ref" : "") + ")"; 
	}
	
	public int getSize() {
		return size;
	}
}
