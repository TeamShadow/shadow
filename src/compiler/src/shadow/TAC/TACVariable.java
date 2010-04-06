package shadow.TAC;

import shadow.typecheck.type.Type;

public class TACVariable {
	private Type type;
	private String symbol;
	private int size;	/** The size of the var in memory in bytes */
	private boolean isRef;
	private boolean isLiteral;	/** symbol holds the value of the literal */
	
	public TACVariable(String symbol, Type type, boolean isLiteral) {
		this(symbol, type);
		this.isLiteral = isLiteral;
	}
	
	public TACVariable(String symbol, Type type) {
		this.type = type;
		this.symbol = symbol;
		
		if(type.isPrimitive()) {
			isRef = false;
			
			// TODO: Figure out the size of this variable on disk
			if(type == Type.BOOLEAN || type == Type.BYTE) size = 1;
			else if(type == Type.CODE || type == Type.INT) size = 4;
			else if(type == Type.DOUBLE || type == Type.LONG) size = 8;
		}
	}
	
	public String toString() {
		return symbol + "(" + type + ")"; 
	}
	
	public int getSize() {
		return size;
	}
}
