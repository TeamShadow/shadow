package shadow.TAC;

import java.util.List;

import shadow.TAC.nodes.TACNode;
import shadow.typecheck.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public class TACMethod {
	private TACNode entry;
	private TACNode exit;
	private MethodSignature signature;
	
	/**
	 * Create a TACMethod given an entry and exit for that method
	 * @param name The name of the method
	 * @param entry The entry into the method
	 * @param exit The exit from the method
	 */
	public TACMethod(MethodSignature signature, TACNode entry, TACNode exit) {
		this.signature = signature;
		this.entry = entry;
		this.exit = exit;
	}
	
	public TACNode getEntry() {
		return entry;
	}
	
	public TACNode getExit() {
		return exit;
	}
	
	public String getName() {
		return signature.getSymbol();
	}
	
	public String getMangledName() {
		return signature.getMangledName();
	}
	
	public MethodSignature getSignature() {
		return signature;
	}

	public List<String> getParamNames() {
		return signature.getMethodType().getParameterNames();
	}
	
	public List<ModifiedType> getParamTypes() {
		return signature.getMethodType().getParameterTypes();
	}
	
	public List<Type> getReturnTypes() {
		return signature.getMethodType().getReturnTypes();
	}
}
