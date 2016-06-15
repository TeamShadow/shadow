package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACCall extends TACOperand
{	
	private TACMethodRef methodRef;
	private List<TACOperand> parameters;
	private TACLabel noExceptionLabel;	

	public TACLabel getNoExceptionLabel() {
		return noExceptionLabel;
	}

	public TACCall(TACNode node, TACMethodRef methodRef, TACOperand... params) {
		this(node, methodRef, Arrays.asList(params));
	}
	
	public TACCall(TACNode node, TACMethodRef methodRef,
			Collection<? extends TACOperand> params) {
		super(node);		
		this.methodRef = methodRef;
		SequenceType types = methodRef.getParameterTypes();
		if (params.size() != types.size())
			throw new IllegalArgumentException("Wrong # args");
		Iterator<? extends TACOperand> paramIter = params.iterator();
		Iterator<ModifiedType> typeIter = types.iterator();
		parameters = new ArrayList<TACOperand>(params.size());
		while (paramIter.hasNext())
			parameters.add(check(paramIter.next(), typeIter.next()));
		
		if( getBlock().hasLandingpad() ) {
			noExceptionLabel = new TACLabel(getMethod());
			noExceptionLabel.insertBefore(node); //before the node but after the call
			new TACNodeRef(node, this);
		}
	}
	public TACMethodRef getMethodRef() {
		return methodRef;
	}
	public TACOperand getPrefix() {
		return parameters.get(0);
	}
	public int getNumParameters() {
		return parameters.size();
	}
	public TACOperand getParameter(int index) {
		return parameters.get(index);
	}
	public List<TACOperand> getParameters() {
		return new ArrayList<TACOperand>(parameters);
	}

	@Override
	public Type getType() {
		return methodRef.getReturnType();
	}
	
	@Override
	public Modifiers getModifiers() {
		SequenceType returns = methodRef.getReturnTypes();
		if( returns.size() == 1)
			return returns.getModifiers(0);
		
		return super.getModifiers();
	}
	
	@Override
	public int getNumOperands() {
		return 1 + parameters.size();
	}
	@Override
	public TACOperand getOperand(int num) {
		if (num == 0)			
			return methodRef;
		return parameters.get(num - 1);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		TACMethodRef method = getMethodRef();
		sb.append(method.getOuterType()).append('.').append(method.getName()).
				append('(');
		for (TACOperand parameter : getParameters())
			sb.append(parameter).append(", ");
		if (getNumParameters() != 0)
			sb.delete(sb.length() - 2, sb.length());
		return sb.append(')').toString();
	}
}
