package shadow.TAC;

import java.util.List;

import shadow.typecheck.type.Type;

public class TACVariable {
	private Type type;			/** The type of the variable */
	private String symbol;		/** The symbol of the variable */
	private int size;			/** The size of the var in memory in bytes */
	private boolean isRef;		/** Indicates if we have a reference or not */
	private boolean isLiteral;	/** symbol holds the value of the literal */
	private boolean isField;	/** Indicates if this variable is a field of the method or not */
	private List<Integer> arrayDim;	/** Records the array dimension, null if not an array */
	
	/**
	 * Returns the default value of a given type.
	 * @param type The type of TACVariable to return.
	 * @return A TACVariable literal of that type with its default value.
	 */
	static public TACVariable getDefault(Type type) {
		if(type.isPrimitive())
			return new TACVariable("0", type, true);
		else if(type.isString())
			return new TACVariable("\"\"", type, true);
		else
			return new TACVariable("null", type, true);
	}
	
	/**
	 * Returns a boolean literal TACVariable.
	 * @param value True or false.
	 * @return TACVariable representing the literal.
	 */
	static public TACVariable getBooleanLiteral(boolean value) {
		Type t = new Type("boolean");
		
		if(value)
			return new TACVariable("true", t, true);
		else
			return new TACVariable("false", t, true);
	}
	
	/**
	 * Returns an int literal TACVariable.
	 * @param integral value.
	 * @return TACVariable representing the literal.
	 */
	static public TACVariable getIntLiteral(int value) {
		return new TACVariable(Integer.toString(value), new Type("int"), true);
	}
	
	public TACVariable(String symbol, Type type) {
		this.type = type;
		this.symbol = symbol;
		this.isLiteral = false;
		this.arrayDim = null;
		
		if(type.isPrimitive()) {
			isRef = false;
			
			// TODO: Figure out the size of this variable on disk
			if(type == Type.BOOLEAN || type == Type.BYTE) size = 1;
			else if(type == Type.CODE || type == Type.INT) size = 4;
			else if(type == Type.DOUBLE || type == Type.LONG) size = 8;
		} else {
			/* I'm just guessing here */
			isRef = true;
			size = 8;
		}
	}
	
	public TACVariable(String symbol, Type type, boolean isLiteral) {
		this(symbol, type);
		this.isLiteral = isLiteral;
		this.arrayDim = null;
	}
	
	public TACVariable(String symbol, Type type, List<Integer> arrayDim) {
		this(symbol, type);
		this.isLiteral = false;
		this.arrayDim = arrayDim;
	}
	
	public String toString() {
		return symbol + "(" + type + (isLiteral ? " literal" : "") + (isRef ? " ref" : "") + (isArray() ? arrayDim : "") + ")"; 
	}
	
	public int getSize() {
		return size;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public boolean isLiteral() {
		return isLiteral;
	}

	/**
	 * Returns true if this variable is a field.
	 * @return
	 */
	public boolean isField() {
		return isField;
	}

	public void setField(boolean isField) {
		this.isField = isField;
	}

	/**
	 * Returns true if this variable is a reference.
	 * @return
	 */
	public boolean isRef() {
		return isRef;
	}

	/**
	 * Returns true if this variable is an array.
	 * @return
	 */
	public boolean isArray() {
		return arrayDim != null;
	}
	
	public List<Integer> getArrayDim() {
		return arrayDim;
	}
}
