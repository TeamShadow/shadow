package shadow.interpreter;

import shadow.typecheck.type.Type;

public abstract class ShadowNumber extends ShadowValue {
	
	public ShadowNumber() {
		super();
	}
	
	public ShadowNumber(int modifiers) {
		super(modifiers);
	}
	
	@Override
    public ShadowValue callMethod(String method, ShadowValue ... arguments) throws InterpreterException {
		if(arguments.length == 0) {
			switch(method) {
			case "toByte": return toByte();
			case "toUByte": return toUByte();
			case "toShort": return toShort();
			case "toUShort": return toUShort();
			case "toInt": return toInt();
			case "toLong": return toLong();
			case "toULong": return toULong();
			case "toCode": return toCode();
			case "toFloat": return toFloat();
			case "toDouble": return toDouble();
			}
		}
		else if(arguments.length == 1 && arguments[0] instanceof ShadowNumber) {
			ShadowNumber number = (ShadowNumber)(arguments[0].cast(getType()));
			switch(method) {
			case "max": return max(number);
			case "min": return min(number);
			}
		}
		
		return super.callMethod(method, arguments);
	}
	

	public ShadowNumber toByte() throws InterpreterException {
		return (ShadowNumber) cast(Type.BYTE);
	}

	public ShadowNumber toUByte() throws InterpreterException {
		return (ShadowNumber) cast(Type.UBYTE);
	}

	public ShadowNumber toShort() throws InterpreterException {
		return (ShadowNumber) cast(Type.SHORT);
	}

	public ShadowNumber toUShort() throws InterpreterException {
		return (ShadowNumber) cast(Type.USHORT);
	}

	public ShadowNumber toInt() throws InterpreterException {
		return (ShadowNumber) cast(Type.INT);
	}
	
	public ShadowNumber toUInt() throws InterpreterException {
		return (ShadowNumber) cast(Type.UINT);
	}

	public ShadowNumber toLong() throws InterpreterException {
		return (ShadowNumber) cast(Type.LONG);
	}

	public ShadowNumber toULong() throws InterpreterException {
		return (ShadowNumber) cast(Type.ULONG);
	}

	public ShadowNumber toCode() throws InterpreterException {
		return (ShadowNumber) cast(Type.CODE);
	}

	public ShadowNumber toFloat() throws InterpreterException {
		return (ShadowNumber) cast(Type.FLOAT);
	}

	public ShadowNumber toDouble() throws InterpreterException {
		return (ShadowNumber) cast(Type.DOUBLE);
	}
	
	
	public ShadowNumber max(ShadowNumber number) throws InterpreterException {
		ShadowNumber first = this;
		ShadowNumber second = number;
        if (first.isStrictSubtype(second))
            first = (ShadowNumber) first.cast(second.getType());
        else if (second.isStrictSubtype(first))
            second = (ShadowNumber) second.cast(first.getType());
		
		if(first.compareTo(second) >= 0)
			return first;
		else
			return second;
	}
	
	public ShadowNumber min(ShadowNumber number) throws InterpreterException {
		ShadowNumber first = this;
		ShadowNumber second = number;
        if (first.isStrictSubtype(second))
            first = (ShadowNumber) first.cast(second.getType());
        else if (second.isStrictSubtype(first))
            second = (ShadowNumber) second.cast(first.getType());
		
		if(first.compareTo(second) <= 0)
			return first;
		else
			return second;
	}
}
