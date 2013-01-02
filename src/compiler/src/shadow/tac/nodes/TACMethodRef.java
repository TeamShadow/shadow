package shadow.tac.nodes;

import java.util.Collections;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassInterfaceBaseType;
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
	public TACMethodRef(MethodSignature sig)
	{
		this(null, null, sig.getMethodType(), sig.getSymbol());
	}
	public TACMethodRef(MethodType methodType, String methodName)
	{
		this(null, null, methodType, methodName);
	}
	public TACMethodRef(TACOperand prefixNode, MethodSignature sig)
	{
		this(null, prefixNode, sig.getMethodType(), sig.getSymbol());
	}
	public TACMethodRef(TACOperand prefixNode, MethodType methodType,
			String methodName)
	{
		this(null, prefixNode, methodType, methodName);
	}
	public TACMethodRef(TACNode node, MethodSignature sig)
	{
		this(node, null, sig.getMethodType(), sig.getSymbol());
	}
	public TACMethodRef(TACNode node, MethodType methodType, String methodName)
	{
		this(node, null, methodType, methodName);
	}
	public TACMethodRef(TACNode node, TACOperand prefixNode,
			MethodSignature sig)
	{
		this(node, prefixNode, sig.getMethodType(), sig.getSymbol());
	}
	public TACMethodRef(TACNode node, TACOperand prefixNode,
			MethodType methodType, String methodName)
	{
		super(node);
		if (prefixNode != null)
			prefix = check(prefixNode, methodType.getOuter());
		type = methodType;
		name = methodName;
	}

	public ClassInterfaceBaseType getPrefixType()
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
	public MethodType getType()
	{
		return type;
	}
	public String getName()
	{
		return name;
	}

	public SequenceType getParameterTypes()
	{
		SequenceType paramTypes = new SequenceType();
		paramTypes.add(new SimpleModifiedType(getPrefixType())); // this
		if (isCreate() && getPrefixType().hasOuter())
			paramTypes.add(new SimpleModifiedType(getPrefixType().getOuter()));
		Type parameterizedType = isCreate() ? getPrefixType() : getType();
		if (parameterizedType.isParameterized())
			for (int i = parameterizedType.getTypeParameters().size(); i > 0;
					i--)
				paramTypes.add(new SimpleModifiedType(Type.CLASS));
		for (String parameterName : getType().getParameterNames())
			paramTypes.add(getType().getParameterType(parameterName));
		return paramTypes;
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
					new SimpleModifiedType(getPrefixType())));
		return getType().getReturnTypes();
	}
	public int getReturnCount()
	{
		return getReturnTypes().size();
	}
	public boolean hasReturn()
	{
		return !getReturnTypes().isEmpty();
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

	@Override
	public int getNumOperands()
	{
		return prefix == null ? 0 : 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (prefix != null && num == 0)
			return prefix;
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
