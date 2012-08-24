package shadow.tac;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACMethod extends TACNodeList
{
	private String name;
	private MethodType type;
	private Map<String, TACVariable> locals =
			new LinkedHashMap<String, TACVariable>();
	private Deque<Map<String, TACVariable>> scopes =
			new LinkedList<Map<String, TACVariable>>();
	public TACMethod(MethodSignature sig)
	{
		this(sig.getSymbol(), sig.getMethodType());
	}
	public TACMethod(String methodName, MethodType methodType)
	{
		name = methodName;
		type = methodType;
		scopes.push(new LinkedHashMap<String, TACVariable>(
				getParameterCount() * 2));
		if (!isStatic())
			addLocal(type.getOuter(), "this");
		for (String parameterName : methodType.getParameterNames())
			addLocal(methodType.getParameterType(parameterName).getType(),
					parameterName);
		enterScope();
	}

	public ClassInterfaceBaseType getPrefixType()
	{
		return type.getOuter();
	}

	public String getName()
	{
		return name;
	}
	public MethodType getType()
	{
		return type;
	}

	public Collection<TACVariable> getLocals()
	{
		return locals.values();
	}
	public Collection<TACVariable> getParameters()
	{
		return scopes.peekLast().values();
	}
	public int getParameterCount()
	{
		return type.getParameterTypes().size() + (isStatic() ? 0 : 1);
	}

	public SequenceType getReturnTypes()
	{
		return type.getReturnTypes();
	}
	public Type getReturnType()
	{
		SequenceType retTypes = type.getReturnTypes();
		if (retTypes.size() == 0)
			return null;
		if (retTypes.size() == 1)
			return retTypes.getType(0);
		return retTypes;
	}
	public int getReturnCount()
	{
		return type.getReturnTypes().size();
	}

	public boolean isStatic()
	{
		return ModifierSet.isStatic(type.getModifiers());
	}
	public boolean isNative()
	{
		return ModifierSet.isNative(type.getModifiers());
	}

	public boolean isConstructor()
	{
		return name.equals("constructor");
	}
	public boolean isDestructor()
	{
		return name.equals("destructor");
	}
	public boolean isGetClass()
	{
		return name.equals("getClass") && type.getParameterTypes().isEmpty();
	}

	public void enterScope()
	{
		scopes.push(new HashMap<String, TACVariable>());
	}
	public TACVariable addLocal(Type type, String name)
	{
		TACVariable variable = new TACVariable(type, name);
		while (locals.containsKey(variable.getName()))
			variable.rename();
		locals.put(variable.getName(), variable);
		scopes.peek().put(name, variable);
		return variable;
	}
	public TACVariable addTempLocal(Type type)
	{
		return addLocal(type, "temp");
	}
	public TACVariable getLocal(String name)
	{
		TACVariable result = null;
		Iterator<Map<String, TACVariable>> iterator = scopes.iterator();
		while (result == null && iterator.hasNext())
			result = iterator.next().get(name);
		return result;
	}
	public void exitScope()
	{
		scopes.pop();
		if (scopes.isEmpty())
			throw new NoSuchElementException();
	}

	@Override
	public String toString()
	{
		final String newline = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder(getName()).append('(');
		for (TACVariable param : getParameters())
			sb.append(param).append(", ");
		if (getParameterCount() > 0)
			sb.delete(sb.length() - 2, sb.length());
		sb.append(") => (");
		for (ModifiedType retType : getReturnTypes())
			sb.append(retType.getType()).append(", ");
		if (getReturnCount() > 0)
			sb.delete(sb.length() - 2, sb.length());
		sb.append(") {").append(newline);
		++indent;
		sb.append(super.toString());
		--indent;
		return sb.append('}').append(newline).toString();
	}
}
