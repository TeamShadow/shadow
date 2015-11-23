package shadow.typecheck.type;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypeParameter extends Type {
	
	private ClassType classBound;
	
	public TypeParameter(String typeName, Type outer) {
		super(typeName, new Modifiers(), null, outer);
		classBound = Type.OBJECT;
	}
	
	public void addBound(Type type) {		
		//only one class at a time
		if( type instanceof ClassType )		
			classBound = ((ClassType)type);
		else
			addInterface((InterfaceType)type);
		invalidateHashName();
	}
	
	
	public ClassType getClassBound() {
		return classBound;
	}
	
	public Set<Type> getBounds() {
		HashSet<Type> bounds = new HashSet<Type>(getInterfaces());
		bounds.add(classBound);		
		return bounds;
	}
	
	public boolean acceptsSubstitution(Type type) {
		if( equals(type) )
			return true;
		
		SequenceType values = new SequenceType(this);	
		SequenceType replacements = new SequenceType(type);

		try {
			for( InterfaceType bound : getInterfaces() ) {
				bound = bound.replace(values, replacements);
				if( !type.isSubtype(bound))
					return false;
			}
			
			ClassType replacedClass = classBound.replace(values, replacements);
			if( !type.isSubtype(replacedClass))
				return false;
		}
		catch(InstantiationException e)	{}
		
		return true;
	}
	
	public boolean isSubtype(Type type) {		
		if( equals(type) || type == Type.OBJECT )
			return true;
		
		//one type parameter is a subtype of another only if they're identical
		if( type instanceof TypeParameter )
			return false;
		
		if( classBound.isSubtype(type) )
			return true;
		
		//but it can still stand in for another type, with the correct bounds
		for( InterfaceType bound : getInterfaces() )
			if( bound.isSubtype(type) )
				return true;		
		
		return false;
	}
	
	
	
	@Override
	public boolean equals(Type o) {
		if( o != null && o instanceof TypeParameter ) {				
			if( o == this )
				return true;
			
			TypeParameter type = (TypeParameter) o;				
			return type.getTypeName().equals(getTypeName()) && getOuter() == type.getOuter();
		}
		else
			return false;	
	}
	
	@Override
	public Type replace(List<ModifiedType> values, List<ModifiedType> replacements ) {
		for( int i = 0; i < values.size(); i++ )		
			if( values.get(i).getType().getTypeName().equals(getTypeName()))
				return replacements.get(i).getType();		
		
		return this;
	}
	
	@Override
	public Type partiallyReplace(List<ModifiedType> values, List<ModifiedType> replacements ) {
		return replace( values, replacements );
	}
	
	
	@Override
	public void updateFieldsAndMethods() throws InstantiationException {
		Set<InterfaceType> toRemove = new HashSet<InterfaceType>();
		Set<InterfaceType> toAdd = new HashSet<InterfaceType>();
		List<InterfaceType> interfaces = getInterfaces();
		
		for( InterfaceType type : interfaces ) {
			if( type instanceof UninstantiatedInterfaceType ) {			
				toRemove.add(type);
				toAdd.add(((UninstantiatedInterfaceType)type).instantiate());
			}
		}
		
		interfaces.removeAll(toRemove);
		interfaces.addAll(toAdd);
		
		if( classBound instanceof UninstantiatedClassType )
			classBound = ((UninstantiatedClassType)classBound).instantiate();
		
		invalidateHashName();
	}
	
	@Override
	public String toString(int options) {
		StringBuilder builder = new StringBuilder();
		
		if((options & MANGLE) != 0 ) {
			builder.append(classBound.toString(options & ~PARAMETER_BOUNDS)); 
		}		
		else if((options & PARAMETER_BOUNDS) != 0 ) {
			builder.append(getTypeName());
			boolean first = true;		
			List<InterfaceType> interfaces = getInterfaces();
			if( classBound != Type.OBJECT || interfaces.size() > 0 ) {			 			
				builder.append(" is ");
				
				if( classBound != Type.OBJECT ) {
					builder.append(classBound.toString(options & ~PARAMETER_BOUNDS));
					first = false;
				}
			
				for(InterfaceType bound : interfaces ) {
					if( first )
						first = false;
					else
						builder.append(" and ");
					builder.append(bound.toString(options & ~PARAMETER_BOUNDS));
				}
			}
		}
		
		return builder.toString();
	}	
	
	public List<MethodSignature> getAllMethods(String methodName) {
		return getMethods(methodName);
	}
	
	public List<MethodSignature> getMethods(String methodName) {
		Set<MethodSignature> signatures = new HashSet<MethodSignature>();
		
		if( !methodName.equals("create") )
			signatures.addAll(classBound.getAllMethods(methodName));
		
		for(InterfaceType bound : getInterfaces() )					
			signatures.addAll(bound.getAllMethods(methodName));
		
		return new ArrayList<MethodSignature>(signatures);
	}

	@Override
	public boolean isRecursivelyParameterized() {
		return isParameterized();
	}

	@Override
	public void printMetaFile(PrintWriter out, String linePrefix) {
		// should never get called
	}

	@Override
	public boolean isDescendentOf(Type type) {		
		// should never get called
		return false;
	}
	
	@Override
	public boolean hasUninstantiatedInterface(InterfaceType type) {
		
		type = type.getTypeWithoutTypeArguments();
		for( InterfaceType interfaceType : getInterfaces() )			
			if( interfaceType.hasUninstantiatedInterface(type) )
				return true;
		
		ClassType current = classBound;		
		while( current != null ) {
			for( InterfaceType interfaceType : current.getInterfaces() )			
				if( interfaceType.hasUninstantiatedInterface(type) )
					return true;
			
			current = current.getExtendType();			
		}
		return false;
	}
	
	@Override
	public boolean hasInterface(InterfaceType type) {
		for( InterfaceType interfaceType : getInterfaces() )			
			if( interfaceType.hasInterface(type) )
				return true;
		
		ClassType current = classBound;
		while( current != null ) {
			for( InterfaceType interfaceType : current.getInterfaces() )			
				if( interfaceType.hasInterface(type) )
					return true;
			
			current = current.getExtendType();			
		}
		return false;
	}
}
