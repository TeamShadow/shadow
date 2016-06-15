package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACMethodRef extends TACOperand
{
	private TACOperand prefix;
	private TACMethodRef wrapped;
	private MethodSignature signature;
	private boolean isSuper = false;

	public TACMethodRef(TACNode node, MethodSignature sig) {
		this(node, null, sig);
	}	
	
	public TACMethodRef(TACNode node, TACOperand prefixNode,
			MethodSignature sig) {
		super(node);
		initialize(prefixNode, sig);		
		if (sig.isWrapper())
			wrapped = new TACMethodRef((TACNode)this, sig.getWrapped());		
	}
	
	private void initialize(TACOperand prefixNode, MethodSignature sig) {
		if (prefixNode != null) {	
			prefix = prefixNode;

			if( prefix.getType() instanceof ArrayType ) {
				ArrayType arrayType = (ArrayType) prefix.getType();
				Type genericArray = arrayType.convertToGeneric();
				prefix = check(prefixNode, new SimpleModifiedType(genericArray));
			}
			else {
				//inner class issues
				while(  prefixNode.getType() instanceof ClassType &&
						!prefixNode.getType().isSubtype(sig.getOuter()) && 
						 prefixNode.getType().hasOuter()	) //not here, look in outer classes					
					prefixNode = new TACLoad(this, new TACFieldRef(prefixNode, new SimpleModifiedType(prefixNode.getType().getOuter()), "_outer"));
								
				prefix = check(prefixNode,
						new SimpleModifiedType(sig.getOuter()));
			}
		}
		signature = sig;
	}
	
	public TACMethodRef(TACNode node, TACMethodRef other) {
		this(node, null, other.signature);
	}	

	private TACMethodRef(TACNode node, TACOperand prefixNode,
			MethodSignature sig,
			TACMethodRef otherWrapped) {
		super(node);
		initialize( prefixNode, sig);
		if (otherWrapped != null)
			wrapped = new TACMethodRef((TACNode)this, otherWrapped);
	}

	public Type getOuterType() {
		return signature.getOuter();
	}
	public boolean hasPrefix() {
		return prefix != null;
	}
	
	public TACOperand getPrefix() {
		return prefix;
	}
	public int getIndex() {
		int index = signature.getOuter().getMethodIndex(signature);
		if (index == -1 || prefix == null)
			throw new UnsupportedOperationException();
		return index;
	}
	@Override
	public MethodType getType() {		
		if( isWrapper() || getOuterType() instanceof InterfaceType )
			return signature.getSignatureWithoutTypeArguments().getMethodType();
		else
			return signature.getMethodType();
	}
	public String getName() {
		return signature.getSymbol();
	}
	public boolean isWrapper() {
		return wrapped != null;
	}
	public TACMethodRef getWrapped() {
		return wrapped;
	}

	public SequenceType getParameterTypes() {		
		return signature.getFullParameterTypes();
	}

	public SequenceType getReturnTypes() {		
		return signature.getFullReturnTypes();
	}
	public int getReturnCount() {
		return getReturnTypes().size();
	}
	public boolean isVoid() {
		return getReturnCount() == 0;
	}
	public Type getReturnType() {
		SequenceType returnTypes = signature.getFullReturnTypes();
		if( returnTypes.isEmpty() )
			return null;
		else if( returnTypes.size() == 1 )
			return returnTypes.get(0).getType();
		else
			return returnTypes;		
	}
	@Override
	public int getNumOperands() {
		return hasPrefix() ? 1 : 0;
	}
	@Override
	public TACOperand getOperand(int num) {
		if (hasPrefix() && num == 0)
			return getPrefix();
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return getName() + getType();
	}
	
	public void setSuper(boolean value) {
		isSuper = value;
	}
	
	public boolean isSuper() {
		return isSuper;
	}
	
	public MethodSignature getSignature() {
		return signature;
	}
}
