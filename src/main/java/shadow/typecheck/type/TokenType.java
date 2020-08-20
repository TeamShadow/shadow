package shadow.typecheck.type;

import java.util.List;

public class TokenType extends Type {

	public TokenType() {
		super("Token Type");
	}

	@Override
	public boolean isSubtype(Type other) {
		if( other instanceof TokenType )
			return true;
		return false;
	}

	@Override
	public Type replace(List<ModifiedType> values, List<ModifiedType> replacements) throws InstantiationException {
		return this;
	}

	@Override
	public Type partiallyReplace(List<ModifiedType> values, List<ModifiedType> replacements) {
		return this;
	}

	@Override
	public void updateFieldsAndMethods() throws InstantiationException {

	}
}
