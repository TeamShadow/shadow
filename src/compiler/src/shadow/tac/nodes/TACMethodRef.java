package shadow.tac.nodes;

import java.util.Collections;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACMethodRef extends TACOperand
{
	private int index;
	private String name;
	private MethodType type;
	public TACMethodRef(MethodSignature sig)
	{
		this(null, sig.getSymbol(), sig.getMethodType());
	}
	public TACMethodRef(String methodName, MethodType methodType)
	{
		this(null, methodName, methodType);
	}
	public TACMethodRef(TACNode node, MethodSignature sig)
	{
		this(node, sig.getSymbol(), sig.getMethodType());
	}
	public TACMethodRef(TACNode node, String methodName, MethodType methodType)
	{
		super(node);
		ClassInterfaceBaseType thisType = methodType.getOuter();
		index = ((ClassType)thisType).getMethodIndex(
				new MethodSignature(methodType, methodName, null));
		name = methodName;
		type = methodType;
	}

	public ClassInterfaceBaseType getPrefixType()
	{
		return type.getOuter();
	}

	public void makeDirect()
	{
		index = -1;
	}
	public int getIndex()
	{
		return index;
	}
	public String getName()
	{
		return name;
	}
	public MethodType getType()
	{
		return type;
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
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
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
