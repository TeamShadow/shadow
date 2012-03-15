package shadow.tac;

import java.util.List;
import java.util.Map;

import shadow.tac.nodes.TACNode;
import shadow.typecheck.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public class TACMethod extends TACDeclaration
{
	private Map<String, Type> allocs;
	private MethodSignature sig;
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

	public List<String> getParamNames()
	{
		return sig.getMethodType().getParameterNames();
	}
	
	public List<ModifiedType> getParamTypes()
	{
		return sig.getMethodType().getParameterTypes();
	}
	
	public List<ModifiedType> getReturnTypes()
	{
		return sig.getMethodType().getReturnTypes();
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
