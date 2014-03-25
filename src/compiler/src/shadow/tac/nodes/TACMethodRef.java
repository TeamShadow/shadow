package shadow.tac.nodes;

import java.util.Collections;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACMethodRef extends TACOperand
{
	private TACOperand prefix;
	private MethodType type;
	private String name;
	private TACMethodRef wrapped;
	public TACMethodRef(MethodSignature sig)
	{
		this(null, null, sig.getMethodType(), sig.getSymbol(),
				sig.getWrapped());
	}
	public TACMethodRef(MethodType methodType, String methodName)
	{
		this(null, null, methodType, methodName, (MethodSignature)null);
	}
	public TACMethodRef(TACOperand prefixNode, MethodSignature sig)
	{
		this(null, prefixNode, sig.getMethodType(), sig.getSymbol(),
				sig.getWrapped());
	}
	public TACMethodRef(TACOperand prefixNode, MethodType methodType,
			String methodName)
	{
		this(null, prefixNode, methodType, methodName, (MethodSignature)null);
	}
	public TACMethodRef(TACNode node, MethodSignature sig)
	{
		this(node, null, sig.getMethodType(), sig.getSymbol(),
				sig.getWrapped());
	}
	public TACMethodRef(TACNode node, MethodType methodType, String methodName)
	{
		this(node, null, methodType, methodName, (MethodSignature)null);
	}
	public TACMethodRef(TACNode node, TACOperand prefixNode,
			MethodSignature sig)
	{
		this(node, prefixNode, sig.getMethodType(), sig.getSymbol(),
				sig.isWrapper() ? sig.getWrapped() : null);
	}
	private TACMethodRef(TACNode node, TACOperand prefixNode,
			MethodType methodType, String methodName,
			MethodSignature wrappedSignature)
	{
		super(node);
		if (prefixNode != null)
			prefix = check(prefixNode,
					new SimpleModifiedType(methodType.getOuter()));
		type = methodType;
		name = methodName;
		if (wrappedSignature != null)
			wrapped = new TACMethodRef((TACNode)this, wrappedSignature);
	}
	public TACMethodRef(TACMethodRef other)
	{
		this(null, null, other.getType(), other.getName(), other.getWrapped());
	}
	public TACMethodRef(TACNode node, TACMethodRef other)
	{
		this(node, null, other.getType(), other.getName(), other.getWrapped());
	}
	public TACMethodRef(TACNode node, TACOperand prefixNode,
			MethodType methodType, String methodName)
	{
		this(node, prefixNode, methodType, methodName, (TACMethodRef)null);
	}
	private TACMethodRef(TACNode node, TACOperand prefixNode,
			MethodType methodType, String methodName,
			TACMethodRef otherWrapped)
	{
		super(node);
		if (prefixNode != null)
		{
			/*if( methodType.getOuter() instanceof InterfaceType )
				prefix = check(prefixNode,
						new SimpleModifiedType(Type.OBJECT));
			else*/
				prefix = check(prefixNode,
						new SimpleModifiedType(methodType.getOuter()));
		}
		type = methodType;
		name = methodName;
		if (otherWrapped != null)
			wrapped = new TACMethodRef((TACNode)this, otherWrapped);
	}

	public Type getOuterType()
	{
		return type.getOuter();
	}
	public boolean hasPrefix()
	{
		return prefix != null;
	}
	public TACOperand getPrefix()
	{
		return prefix;
	}
	public int getIndex()
	{
		int index = type.getOuter().getMethodIndex(
				new MethodSignature(type, name, null));
		if (index == -1 || prefix == null)
			throw new UnsupportedOperationException();
		return index;
	}
	@Override
	public MethodType getType()
	{
		return type;
	}
	public String getName()
	{
		return name;
	}
	public boolean isWrapper()
	{
		return wrapped != null;
	}
	public TACMethodRef getWrapped()
	{
		return wrapped;
	}

	public SequenceType getParameterTypes()
	{
		SequenceType paramTypes = new SequenceType();
		Type outerType = getOuterType();
		if (isCreate() || outerType instanceof InterfaceType ) //since actual object is unknown, assume Object for all interface methods
			paramTypes.add(new SimpleModifiedType(Type.OBJECT));
		else
			paramTypes.add(new SimpleModifiedType(outerType)); // this
		if (isCreate() && getOuterType().hasOuter())
			paramTypes.add(new SimpleModifiedType(getOuterType().getOuter()));
		Type parameterizedType = isCreate() ? getOuterType() : getType();
		if (parameterizedType.isParameterized())
			for (int i = parameterizedType.getTypeParameters().size(); i > 0;
					i--)
				paramTypes.add(new SimpleModifiedType(Type.CLASS));
		
		MethodType methodType = getType();
		if( isWrapper() )
			methodType = methodType.getTypeWithoutTypeArguments();	
				
		for (ModifiedType parameterType : methodType.getParameterTypes())
			paramTypes.add(parameterType);	
			
		return paramTypes;
	}
	public ModifiedType getParameterType(int index)
	{
		return getParameterTypes().get(index);
	}
	public int getParameterCount()
	{
		return getParameterTypes().size();
	}
	public boolean hasParameters()
	{
		return !getParameterTypes().isEmpty();
	}

	public SequenceType getExplicitParameterTypes()
	{
		return getType().getParameterTypes();
	}
	public int getExplicitParameterCount()
	{
		return getExplicitParameterTypes().size();
	}
	public boolean hasExplicitParameters()
	{
		return !getExplicitParameterTypes().isEmpty();
	}

	public SequenceType getReturnTypes()
	{
		if (isCreate())
			return new SequenceType(Collections.<ModifiedType>singletonList(
					new SimpleModifiedType(getOuterType())));
		
		MethodType methodType = getType();
		if( isWrapper() )
			methodType = methodType.getTypeWithoutTypeArguments();
		
		return methodType.getReturnTypes();
	}
	public int getReturnCount()
	{
		return getReturnTypes().size();
	}
	public boolean isVoid()
	{
		return getReturnCount() == 0;
	}
	public Type getVoidReturnType()
	{
		if (!isVoid())
			throw new IllegalStateException();
		return null;
	}
	public boolean isSingle()
	{
		return getReturnCount() == 1;
	}
	public ModifiedType getSingleReturnType()
	{
		if (!isSingle())
			throw new IllegalStateException();
		return getReturnTypes().get(0);
	}
	public boolean isSequence()
	{
		return getReturnCount() > 1;
	}
	public SequenceType getSequenceReturnTypes()
	{
		if (!isSequence())
			throw new IllegalStateException();
		return getReturnTypes();
	}

	public Type getReturnType()
	{
		if (isVoid())
			return getVoidReturnType();
		if (isSingle())
			return getSingleReturnType().getType();
		if (isSequence())
			return getSequenceReturnTypes();
		throw new IllegalStateException();
	}
	public ModifiedType getReturnType(int index)
	{
		return getReturnTypes().get(index);
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
		return name.equals("destroy") && !hasExplicitParameters();
	}
	public boolean isGetClass()
	{
		return name.equals("getClass") && !hasExplicitParameters();
	}
	public boolean isGet()
	{
		return type.getModifiers().isGet() && !hasExplicitParameters();
	}
	public boolean isSet()
	{
		return type.getModifiers().isSet() && getExplicitParameterCount() == 1;
	}

	@Override
	public int getNumOperands()
	{
		return hasPrefix() ? 1 : 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (hasPrefix() && num == 0)
			return getPrefix();
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return name + type;
	}
}
