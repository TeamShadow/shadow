package shadow.output.llvm;

import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class GenericArray extends Generic {
	
	private String internalParameter;
	
	public GenericArray(Type type)
	{
		super(fixType(type));
		
		name = type.toString();
		mangledName = type.getMangledNameWithGenerics();
		mangledGeneric = type.getMangledName();
		
		internalParameter = type.getTypeParameters().get(0).getType().getMangledNameWithGenerics();		
	}
	
	public String getInternalParameter()
	{
		return internalParameter;
	}
	
	private static Type fixType(Type type)
	{
		if( type.getTypeWithoutTypeArguments().equals(Type.ARRAY))
		{
			ModifiedType modifiedType = type.getTypeParameters().get(0);
			if( modifiedType.getType() instanceof ArrayType )
			{
				ArrayType arrayType = (ArrayType) modifiedType.getType();
				Type result = Type.ARRAY.replace(Type.ARRAY.getTypeParameters(), new SequenceType(arrayType.convertToGeneric()));
				return result;				
			}
			
			return type;
		}
		else
			throw new UnsupportedOperationException();		
	}
	

}
