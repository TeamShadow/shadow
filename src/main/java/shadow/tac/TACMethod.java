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

import shadow.interpreter.ShadowUndefined;
import shadow.output.text.TextOutput;
import shadow.parser.javacc.ShadowException;
import shadow.tac.nodes.TACBlock;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLocalStore;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACParameter;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACMethod extends TACNodeList
{
	private final MethodSignature signature;
	private final Map<String, TACVariable> locals;
	private final Deque<Map<String, TACVariable>> scopes;
	private boolean landingpad;
	private int labelCounter = 0;		//counter to keep label numbering unique
	private int variableCounter = 0;	//counter to keep variable number unique
	
	public TACMethod(MethodSignature methodSignature)
	{
		signature = methodSignature;
		locals = new LinkedHashMap<String, TACVariable>();
		scopes = new LinkedList<Map<String, TACVariable>>();
		landingpad = false;
		new TACBlock(this);		
		enterScope();		
	}
	
	@Override
	public TACMethod getMethod()
	{
		return this;
	}	

	public TACMethod addParameters(TACNode node, boolean isWrapped)
	{		
		//method starts with a label, always
		new TACLabelRef(this).new TACLabel(node);
		
		Type prefixType = signature.getOuter();		
		int parameter = 0;		
		
		if( prefixType instanceof InterfaceType )
			prefixType = Type.OBJECT;
		
		ModifiedType modifiedType = new SimpleModifiedType(prefixType);
		//we mark the primitive "this" as nullable, to show that it's the object version of the primitive
		if( prefixType.isPrimitive() && (signature.isCreate() || isWrapped ) )
			modifiedType.getModifiers().addModifier(Modifiers.NULLABLE);
		
		if (signature.isCreate() ) {
			new TACLocalStore(node, addLocal(modifiedType, "this"), TACCast.cast(node, modifiedType, new TACParameter(node, new SimpleModifiedType(Type.OBJECT), parameter++)));
			if( prefixType.hasOuter()) {
				modifiedType = new SimpleModifiedType(prefixType.getOuter());
				if( prefixType.getOuter().isPrimitive() && (signature.isCreate() || isWrapped ) )
					modifiedType.getModifiers().addModifier(Modifiers.NULLABLE);
				
				new TACLocalStore(node, addLocal(modifiedType, "_outer"), new TACParameter(node, modifiedType, parameter++));				
			}
		}
		else
			new TACLocalStore(node, addLocal(modifiedType, "this"), new TACParameter(node, modifiedType, parameter++));
		
		//methods are no longer parameterized in Shadow
		/*
		MethodType methodType = method.getMethodType();
		if (methodType.isParameterized())
			for (ModifiedType typeParam : methodType.getTypeParameters())
				addLocal(new SimpleModifiedType(Type.CLASS),
						typeParam.getType().getTypeName());
		*/
		
		MethodType type = signature.getMethodType();		
		if( isWrapped )
			type = signature.getSignatureWithoutTypeArguments().getMethodType();
		
		for (String name : type.getParameterNames()) {
			ModifiedType parameterType = type.getParameterType(name);
			new TACLocalStore(node, addLocal(parameterType, name), new TACParameter(node, parameterType, parameter++));
		}
		
		//It's critical that these things happen before this next enterScope(),
		//since parameters are all expected to be in the first scope.
		enterScope();
		
		//variable to hold low level exception data
		//note that variables cannot start with underscore (_) in Shadow, so no collision is possible
		new TACLocalStore(node, addLocal(new SimpleModifiedType(Type.getExceptionType()), "_exception"), new TACLiteral(node, new ShadowUndefined(Type.getExceptionType())));
		
		return this;
	}
	
	public TACMethod addParameters(TACNode node)
	{
		return addParameters(node, false);
	}

	public MethodSignature getSignature()
	{
		return signature;
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
	
	public int incrementLabelCounter()
	{
		return labelCounter++;
	}
	
	public int incrementVariableCounter()
	{
		return variableCounter++;
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
