package shadow;

import shadow.parse.Context;

public interface ShadowExceptionFactory {
	
	ShadowException generateException(String message, Context context);
	String getName();
}
