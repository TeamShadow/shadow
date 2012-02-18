package shadow.TAC.nodes;

import java.util.LinkedList;
import java.util.List;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.Type;

/**
 * Represents a method call in the TAC.
 */
public class TACMethodCall extends TACNode {
	private Type methodType;
	private List<TACVariable> returns;
	private List<TACVariable> parameters;
	private String methodName;

	public TACMethodCall(Node astNode) {
		super(astNode, "CALL", null);
		methodType = astNode.getType();
		returns = new LinkedList<TACVariable>();
		parameters = new LinkedList<TACVariable>();
		methodName = astNode.getImage();
	}

	public TACMethodCall(Node astNode, String name, TACNode parent, TACNode next) {
		super(astNode, "CALL", parent, next);
		methodType = astNode.getType();
		returns = new LinkedList<TACVariable>();
		parameters = new LinkedList<TACVariable>();
		methodName = name;
	}
	
	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}
	
	public void addReturn(TACVariable var) {
		returns.add(var);
	}
	
	public boolean hasReturn() {
		return !returns.isEmpty();
	}
	
	public int getReturnCount() {
		return returns.size();
	}

	public TACVariable getReturn(int index) {
		return returns.get(index);
	}
	
	public void addParameter(TACVariable var) {
		parameters.add(var);
	}
	
	public int getParamCount() {
		return parameters.size();
	}
	
	public TACVariable getVariable() {
		return hasReturn() ? returns.get(0) : null;
	}

	public TACVariable getParameter(int index) {
		return parameters.get(index);
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodName() {
		return methodName;
	}
	public String getMangledName() {
		return getMethodType().getOuter().getMangledName() +
				"_M" + getMethodName() + getMethodType().getMangledName();
	}
	
	public MethodType getMethodType() {
		return (MethodType)getAstNode().getType();
	}
	
	public TACVariable[] getParameters() {
		TACVariable[] ret = new TACVariable[parameters.size()];
		
		return parameters.toArray(ret);
	}
	
	public TACVariable[] getReturns() {
		TACVariable[] ret = new TACVariable[returns.size()];
		
		return returns.toArray(ret);
	}
}
