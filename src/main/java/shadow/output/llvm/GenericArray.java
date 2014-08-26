package shadow.output.llvm;

import shadow.typecheck.type.Type;

public class GenericArray extends Generic {
	
	private String internalParameter;
	
	public GenericArray(Type type)
	{
		super(type);

		internalParameter = type.getTypeParameters().get(0).getType().getMangledNameWithGenerics();		
	}
	
	public String getInternalParameter()
	{
		return internalParameter;
	}
}
