package shadow.typecheck.type;

import java.util.List;

public class MethodTableType extends Type {
	
	Type type;
	
	public MethodTableType(Type type) {
		super("MethodTable(" + type + ")");		
		this.type = type.getTypeWithoutTypeArguments();
	}
	
	@Override
	public Type replace(List<ModifiedType> values,
			List<ModifiedType> replacements) throws InstantiationException {
		return this;
	}

	@Override
	public Type partiallyReplace(List<ModifiedType> values,
			List<ModifiedType> replacements) {
		return this;
	}

	@Override
	public void updateFieldsAndMethods() throws InstantiationException { }

	@Override
	public boolean isSubtype(Type other) {
		if( other == OBJECT )
			return true;

		return equals(other);
	}
	
	@Override
	public boolean equals(Type other) {
		if( other == null )
			return false;
		
		if( other instanceof MethodTableType ) {
			MethodTableType otherTable = (MethodTableType) other;
			return otherTable.type.equals(type);
		}
		
		return false;
	}
	
	@Override
	public String toString(int options)	{
		return type.toString(options) + ":_methods";
	}
}
