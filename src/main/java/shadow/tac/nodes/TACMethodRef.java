package shadow.tac.nodes;

import java.util.Collections;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
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
	private boolean isSuper = false;

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
	
	private void initialize(TACOperand prefixNode, MethodType methodType, String methodName)
	{
		if (prefixNode != null)
		{	
			prefix = prefixNode;
			
			/*
			if( prefix.getType() instanceof InterfaceType )
				_this = check(prefixNode,
						new SimpleModifiedType(Type.OBJECT));
			else*/
			if( prefix.getType() instanceof ArrayType )
			{
				ArrayType arrayType = (ArrayType) prefix.getType();
				Type genericArray = arrayType.convertToGeneric();
				prefix = check(prefixNode, new SimpleModifiedType(genericArray));
			}
			else
			{
				//inner class issues
				while(  prefixNode.getType() instanceof ClassType &&
						!prefixNode.getType().isSubtype(methodType.getOuter()) && 
						 prefixNode.getType().hasOuter()	) //not here, look in outer classes
				{	
					prefixNode = new TACFieldRef(this, prefixNode, new SimpleModifiedType(prefixNode.getType().getOuter()), "_outer");
				}
				
				prefix = check(prefixNode,
						new SimpleModifiedType(methodType.getOuter()));
			}
		}
		type = methodType;
		name = methodName;		
	}
	
	private TACMethodRef(TACNode node, TACOperand prefixNode,
			MethodType methodType, String methodName,
			MethodSignature wrappedSignature)
	{
		super(node);
		initialize( prefixNode, methodType, methodName);
		
		if (wrappedSignature != null)
			wrapped = new TACMethodRef((TACNode)this, wrappedSignature);		
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
		initialize( prefixNode, methodType, methodName);		
		
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
		if( isWrapper() || getOuterType() instanceof InterfaceType )
			return type.getTypeWithoutTypeArguments();
		else
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
		Type outerType = getOuterType().getTypeWithoutTypeArguments();
		if (isCreate() || outerType instanceof InterfaceType ) //since actual object is unknown, assume Object for all interface methods
			paramTypes.add(new SimpleModifiedType(Type.OBJECT));
		else
			paramTypes.add(new SimpleModifiedType(outerType)); // this
		
		
		//creates don't need class and methods because they are already inited
		//paramTypes.add( new SimpleModifiedType(Type.CLASS));
		//paramTypes.add( new SimpleModifiedType(Type.OBJECT)); //method table	
			
		if( isCreate() && getOuterType().hasOuter() )
				paramTypes.add(new SimpleModifiedType(getOuterType().getOuter()));			
			
		//type parameters no longer passed to method for generic objects, only for purely parameterized methods
		//Type parameterizedType = isCreate() ? getOuterType() : getType();
		Type parameterizedType = getType();
		if (parameterizedType.isParameterized())
			for (int i = parameterizedType.getTypeParameters().size(); i > 0; i--)
				paramTypes.add(new SimpleModifiedType(Type.CLASS));
		//TODO: add twice as many?  class type + method table?
		
		MethodType methodType = getType().getTypeWithoutTypeArguments();
				
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
					new SimpleModifiedType(getOuterType().getTypeWithoutTypeArguments())));
			
		return getType().getTypeWithoutTypeArguments().getReturnTypes();
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
	
	public void setSuper(boolean value)
	{
		isSuper = value;
	}
	
	public boolean isSuper()
	{
		return isSuper;
	}
}
