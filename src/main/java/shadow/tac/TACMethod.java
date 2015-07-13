package shadow.tac;

import java.io.StringWriter;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

import shadow.output.text.TextOutput;
import shadow.parser.javacc.ShadowException;
import shadow.tac.nodes.TACMethodRef;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACMethod extends TACNodeList
{
	private MethodSignature method;
	private Map<String, TACVariable> locals;
	private Deque<Map<String, TACVariable>> scopes;
	private boolean landingpad;
	public TACMethod(MethodSignature methodSignature)
	{	
		method = methodSignature;
		locals = new LinkedHashMap<String, TACVariable>();
		scopes = new LinkedList<Map<String, TACVariable>>();
		landingpad = false;
		enterScope();
		Type prefixType = methodSignature.getOuter();		
		if( prefixType instanceof InterfaceType )
			prefixType = Type.OBJECT;
		
		addLocal(new SimpleModifiedType(prefixType), "this");		
		if (methodSignature.isCreate() )
		{	
			if( prefixType.hasOuter())
				addLocal(new SimpleModifiedType(prefixType.getOuter()), "_outer");
		}
		
		MethodType methodType = methodSignature.getMethodType();
		if (methodType.isParameterized())
			for (ModifiedType typeParam : methodType.getTypeParameters())
				addLocal(new SimpleModifiedType(Type.CLASS),
						typeParam.getType().getTypeName());
		
		//for( int i = 0; i < methodType.getParameterNames().size(); ++i )		
		//	addLocal(methodType.getParameterTypes().get(i), methodType.getParameterNames().get(i));
		
	}
	
	public TACMethod addParameters(boolean isWrapped)
	{
		MethodType type = method.getMethodType();		
		if( isWrapped )
			type = method.getSignatureWithoutTypeArguments().getMethodType();
		
		for (String name : type.getParameterNames())
			addLocal(type.getParameterType(name), name);
		enterScope();
		return this;
	}
	
	public TACMethod addParameters()
	{
		return addParameters(false);
	}

	public MethodSignature getMethod()
	{
		return method;
	}

	public Collection<TACVariable> getLocals()
	{
		return locals.values();
	}
	public Collection<TACVariable> getParameters()
	{
		return new Parameters();
	}
	public TACVariable getThis()
	{
		return locals.values().iterator().next();
	}
	public boolean hasParameter(String name)
	{
		return scopes.peekLast().containsKey(name);
	}
	public TACVariable getParameter(String name)
	{
		return scopes.peekLast().get(name);
	}
	private class Parameters extends AbstractCollection<TACVariable>
	{
		@Override
		public int size()
		{
			return scopes.peekLast().size();
		}
		@Override
		public Iterator<TACVariable> iterator()
		{
			return new LimitedIterator<TACVariable>(locals.values().iterator(),
					size());
		}
	}
	private static class LimitedIterator<T> implements Iterator<T>
	{
		private Iterator<T> iterator;
		private int remaining;
		public LimitedIterator(Iterator<T> iter, int size)
		{
			iterator = iter;
			remaining = size;
		}
		@Override
		public boolean hasNext()
		{
			return remaining != 0 && iterator.hasNext();
		}
		@Override
		public T next()
		{
			if (remaining == 0)
				throw new NoSuchElementException();
			remaining--;
			return iterator.next();
		}
		@Override
		public void remove()
		{
			iterator.remove();
		}
	}

	public void enterScope()
	{
		scopes.push(new HashMap<String, TACVariable>());
	}
	public TACVariable addLocal(ModifiedType type, String name)
	{
		TACVariable variable = new TACVariable(type, name);
		while (locals.containsKey(variable.getName()))
			variable.rename();
		locals.put(variable.getName(), variable);
		scopes.peek().put(name, variable);
		return variable;
	}
	public TACVariable addTempLocal(ModifiedType type)
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

	public void setHasLandingpad()
	{
		landingpad = true;
	}
	public boolean hasLandingpad()
	{
		return landingpad;
	}

	@Override
	public String toString()
	{
		StringWriter writer = new StringWriter();
		try
		{
			new TextOutput(writer).build(this, null);
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
