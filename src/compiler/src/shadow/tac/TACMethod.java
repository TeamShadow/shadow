package shadow.tac;

import java.util.List;
import java.util.Map;

import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.tac.nodes.TACNode;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACMethod extends TACDeclaration
{
	private Map<String, Type> allocs;
	private MethodSignature sig;
	public TACMethod(MethodSignature ms) {
		sig = ms;
	}
	public TACMethod(MethodSignature ms, TACDeclaration body) {
		super(body);
		sig = ms;
	}
	public TACMethod(MethodSignature ms, TACNode entryNode, TACNode exitNode) {
		super(entryNode, exitNode);
		sig = ms;
	}

	public String getName()
	{
		return sig.getSymbol();
	}
	
	public String getMangledName()
	{
		return sig.getMangledName();
	}
	
	public MethodType getMethodType()
	{
		return sig.getMethodType();
	}

	public List<String> getParameterNames()
	{
		return sig.getMethodType().getParameterNames();
	}
	
	public SequenceType getParameterTypes()
	{
		return sig.getMethodType().getParameterTypes();
	}
	
	public SequenceType getReturnTypes()
	{
		return sig.getMethodType().getReturnTypes();
	}
	
	public boolean isStatic()
	{
		return ModifierSet.isStatic(sig.getModifiers());
	}
	
	public MethodSignature getSignature()
	{
		return sig;
	}

	public void setAllocations(Map<String, Type> nodes)
	{
		allocs = nodes;
	}
	public Map<String, Type> getAllocations()
	{
		return allocs;
	}
}
