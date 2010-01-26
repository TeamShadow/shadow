package shadow.typecheck;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class MethodSignature {
	protected LinkedHashMap<String, Type> parameters; /** symbols & types for each parameter */
	protected LinkedList<Type> returns; /** List of return types */
	protected int modifiers;
	protected String symbol;
	protected int line;	/** the line where it's declared */
	
	public MethodSignature(String symbol, int modifiers, int line) {
		parameters = new LinkedHashMap<String, Type>();
		returns = new LinkedList<Type>();
		this.modifiers = modifiers;
		this.symbol = symbol;
		this.line = line;
	}
	
	public void addParameter(String name, Type type) {
		parameters.put(name, type);
	}
	
	public Type getParameterType(String paramName) {
		return parameters.get(paramName);
	}
	
	public boolean containsParam(String paramName) {
		return parameters.containsKey(paramName);
	}
	
	public void addReturn(Type ret) {
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

		// check the parameters, we care about types only
		Iterator<Type> it1 = parameters.values().iterator();
		Iterator<Type> it2 = ms.parameters.values().iterator();
		
		while(it1.hasNext()) {
			if(!it1.next().equals(it2.next()))
				return false;
		}
		
		// check the returns
		if(!returns.equals(ms.returns))
			return false;
		
		// if we made it to here, they are equal
		return true;
	}
	
	// TODO: This is terrible... but not sure how to do a better job
	// Use the default (based on virtual address) before doing that
	//public int hashCode() {
//		return 0;
	//}
}
