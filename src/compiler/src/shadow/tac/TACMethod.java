package shadow.tac;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

import shadow.output.text.TextOutput;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
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
				type.getParameterTypes().size()));
		addLocal(getPrefixType(), "this");
		if (isCreate() && getPrefixType().hasOuter())
			addLocal(getPrefixType().getOuter(), "outer");
		Type parameterizedType = isCreate() ? getPrefixType() : methodType;
		if (parameterizedType.isParameterized())
			for (ModifiedType typeParam : parameterizedType.getTypeParameters())
				addLocal(Type.CLASS, typeParam.getType().getTypeName());
		for (String parameterName : methodType.getParameterNames())
			addLocal(methodType.getParameterType(parameterName).getType(),
					parameterName);
		enterScope();
	}

	public Type getPrefixType()
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
		return scopes.peekLast().size();
	}
	public boolean hasParameters()
	{
		return !scopes.peekLast().isEmpty();
	}
	public boolean hasParametersExceptThis()
	{
		return !type.getParameterTypes().isEmpty();
	}
	public TACVariable getParameter(String name)
	{
		return scopes.peekLast().get(name);
	}
	public TACVariable getParameter(int index)
	{
		if (index < 0)
			throw new NoSuchElementException();
		Iterator<TACVariable> iter = scopes.peekLast().values().iterator();
		while (index-- != 0)
			iter.next();
		return iter.next();
	}

	public SequenceType getReturnTypes()
	{
		if (isCreate())
			return new SequenceType(Collections.<ModifiedType>singletonList(
					new SimpleModifiedType(getPrefixType())));
		return type.getReturnTypes();
	}
	public Type getReturnType()
	{
		SequenceType retTypes = getReturnTypes();
		if (retTypes.isEmpty())
			return null;
		if (retTypes.size() == 1)
			return retTypes.getType(0);
		return retTypes;
	}
	public int getReturnCount()
	{
		return getReturnTypes().size();
	}
	public boolean hasReturn()
	{
		return !getReturnTypes().isEmpty();
	}

	public boolean isNative()
	{
		return type.getModifiers().isNative();
	}

	public boolean isCreate()
	{
		return name.equals("create");
	}
	public boolean isDestroy()
	{
		return name.equals("destroy") && !hasParametersExceptThis();
	}
	public boolean isGetClass()
	{
		return name.equals("getClass") && !hasParametersExceptThis();
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
		return addLocal(type, "_temp");
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
		StringWriter writer = new StringWriter();
		try
		{
			new TextOutput(writer).build(this);
		}
		catch (ShadowException ex)
		{
			return "Error";
		}
		return writer.toString();
	}

//	@Override
//	public String toString()
//	{
//		final String newline = System.getProperty("line.separator");
//		StringBuilder sb = new StringBuilder();
//		sb.append(type.getModifiers()).append(getName()).append('(');
//		for (TACVariable param : getParameters())
//			sb.append(param).append(", ");
//		if (getParameterCount() > 0)
//			sb.delete(sb.length() - 2, sb.length());
//		sb.append(") => (");
//		for (ModifiedType retType : getReturnTypes())
//			sb.append(retType.getType()).append(", ");
//		if (getReturnCount() > 0)
//			sb.delete(sb.length() - 2, sb.length());
//		sb.append(") {").append(newline);
//		++indent;
//		for (TACVariable local : locals.values())
//			indent(sb).append(local).append(';').append(newline);
//		sb.append(super.toString());
//		--indent;
//		return sb.append('}').append(newline).toString();
//	}
}
