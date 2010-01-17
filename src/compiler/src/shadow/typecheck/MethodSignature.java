package shadow.typecheck;

import java.util.LinkedList;

public class MethodSignature {
	protected LinkedList<String> parameters;
	protected LinkedList<String> returns;
	protected int modifiers;
	protected String symbol;
	protected int line;	/** the line where it's declared */
	
	public MethodSignature(String symbol, int modifiers, int line) {
		parameters = new LinkedList<String>();
		returns = new LinkedList<String>();
		this.modifiers = modifiers;
		this.symbol = symbol;
		this.line = line;
	}
	
	public void addParameter(String param) {
		parameters.add(param);
	}
	
	public void addReturn(String ret) {
		returns.add(ret);
	}
	
	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getLineNumber() {
		return line;
	}
	
	/**
	 * Need to override equals as we're doing special things
	 * @param ms The method signature to compare to
	 * @return True if the methods can co-exist, False otherwise
	 */
	public boolean equals(Object o) {
		MethodSignature ms = (MethodSignature)o;
		
		// if the symbols are different, then the signatures are different
		if(!symbol.equals(ms.symbol))
			return false;
		
		// if they don't have the same number of params or returns, then different
		if(parameters.size() != ms.parameters.size() || returns.size() != ms.returns.size())
			return false;

		// check the parameters
		for(int i=0; i < parameters.size(); ++i) {
			if(!parameters.get(i).equals(ms.parameters.get(i)))
				return false;
		}
		
		// check the returns
		for(int i=0; i < returns.size(); ++i) {
			if(!returns.get(i).equals(ms.returns.get(i)))
				return false;
		}
		
		// if we made it to here, they are equal
		return true;
	}
	
	// TODO: This is terrible... but not sure how to do a better job
	public int hashCode() {
		return 0;
	}
}
