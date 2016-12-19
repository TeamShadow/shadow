package shadow.tac.nodes;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.PointerType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

public abstract class TACReference implements ModifiedType {	
	 public boolean needsGarbageCollection() {
		Type type = getType();	
		
		//Class objects don't need GC because they're all constants made at compile time
		//Generic Class objects won't benefit from GC because they're stored in the hash tables of generic classes until the program ends
		
		return 	!(type instanceof PointerType) &&
				!(type instanceof SingletonType) &&				
				!type.equals(Type.CLASS) &&
				!type.equals(Type.GENERIC_CLASS) &&
				!type.equals(Type.METHOD_TABLE) &&
				(!type.isPrimitive() || type.getModifiers().isNullable());
	}
}
