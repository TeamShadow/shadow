package shadow.tac.nodes;

import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public interface TACMethodRef {	
	
	SequenceType getParameterTypes();
	SequenceType getUninstantiatedParameterTypes();
	Type getReturnType();
	SequenceType getReturnTypes();
	SequenceType getFullReturnTypes();
	SequenceType getUninstantiatedReturnTypes();
}
