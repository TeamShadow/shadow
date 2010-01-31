package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MethodType extends Type {
	protected ArrayList<String> paramNames;
	protected ArrayList<Type> paramTypes;
	protected LinkedList<Type> returns; /** List of return types */

	public MethodType(String typeName) {
		super(typeName);
		paramNames = new ArrayList<String>();
		paramTypes = new ArrayList<Type>();
		returns = new LinkedList<Type>();
	}

	public MethodType(String typeName, int modifiers) {
		super(typeName, modifiers);
		paramNames = new ArrayList<String>();
		paramTypes = new ArrayList<Type>();
		returns = new LinkedList<Type>();
	}
	
	public void addParameter(String name, Type type) {
		paramNames.add(name);
		paramTypes.add(type);
	}
	
	public void addParameter(Type type) {
		paramNames.add(null); // have to add to keep in synch
		paramTypes.add(type);
	}
	
	public Type getParameterType(String paramName) {
		for(int i=0; i < paramNames.size(); ++i) {
			if(paramNames.get(i).equals(paramName))
				return paramTypes.get(i);
		}
			
		return null;
	}
	
	public boolean containsParam(String paramName) {
		for(int i=0; i < paramNames.size(); ++i) {
			if(paramNames.get(i).equals(paramName))
				return true;
		}
			
		return false;
	}
	
	public void addReturn(Type ret) {
		returns.add(ret);
	}
	
	/**
	 * Need to override equals as we're doing special things
	 * @param ms The method signature to compare to
	 * @return True if the methods can co-exist, False otherwise
	 */
	public boolean equals(Object o) {
		MethodType mt = (MethodType)o;
		
		// if they don't have the same number of params or returns, then different
		if(paramNames.size() != mt.paramNames.size() || returns.size() != mt.returns.size())
			return false;

		// check the parameters, we care about types only
		for(int i=0; i < paramTypes.size(); ++i) {
			if(!paramTypes.get(i).equals(mt.paramTypes.get(i)))
				return false;
		}
		
		// check the returns
		if(!returns.equals(mt.returns))
			return false;
		
		// if we made it to here, they are equal
		return true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		
		for(Type p:paramTypes) {
			if(p.typeName == null) // method type
				sb.append(p.toString());
			else
				sb.append(p.typeName);
			
			sb.append(",");
		}

		if(paramTypes.size() == 0)
			sb.append(" ) => (");
		else {
			sb.setCharAt(sb.lastIndexOf(","), ')');
			sb.append(" => (");
		}
		
		for(Type r:returns) {
			if(r.typeName == null)
				sb.append(r.toString());
			else
				sb.append(r.typeName);
			sb.append(",");
		}

		if(returns.size() == 0)
			sb.append(" )");
		else
			sb.setCharAt(sb.lastIndexOf(","), ')');
		
		return sb.toString();
	}

}
