package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class InterfaceType extends ClassInterfaceBaseType {
	protected ArrayList<InterfaceType> extendTypes = new ArrayList<InterfaceType>();

	public InterfaceType(String typeName) {
		this( typeName, 0 );
	}
	
	public InterfaceType(String typeName, int modifiers) {
		this( typeName, modifiers, null );
	}	
		
	public InterfaceType(String typeName, int modifiers, ClassInterfaceBaseType outer ) {
		super( typeName, modifiers, outer );
	}

	public void addExtendType(InterfaceType extendType) {
		this.extendTypes.add(extendType);
	}
	
	public boolean isDescendentOf(Type type)
	{			
		for( InterfaceType parent : extendTypes )
		{
			if( parent.equals(type) || parent.isDescendentOf( type ))
				return true;			
		}
		return false;
	}
	
	public boolean recursivelyContainsMethod(MethodSignature signature, int modifiers ) //must have certain modifiers (usually public)
	{
		if( containsMethod(signature, modifiers))
			return true;		
		
		//check extends		
		for( InterfaceType parent : extendTypes )
			if( parent.recursivelyContainsMethod(signature, modifiers ) )
					return true;			 		
		
		return false;
	}
	
	public boolean recursivelyContainsMethod( String symbol )
	{
		if( containsMethod(symbol))
			return true;		
		
		//check extends		
		for( InterfaceType parent : extendTypes )
			if( parent.recursivelyContainsMethod(symbol ) )
					return true;			 		
		
		return false;
	}

	public ArrayList<InterfaceType> getExtendTypes()
	{
		return extendTypes;
	}

	//see if interface has circular extends hierarchy
	public boolean isCircular()
	{
		HashSet<InterfaceType> descendants = new HashSet<InterfaceType>();			
		
		return recursiveIsCircular( descendants );
	}
	
	//recursively look at parents, adding yourself to the list of interfaces as you go
	//if you find yourself, it's circular
	//if you don't, remove yourself afterwards
	private boolean recursiveIsCircular( HashSet<InterfaceType> descendants )
	{
		if( descendants.contains(this) )
				return true;
		
		descendants.add(this);
		
		for( InterfaceType _interface : extendTypes )
			if( _interface.recursiveIsCircular(descendants) )
				return true;
		
		descendants.remove(this);
		
		return false;
	}
	
	@Override
	public InterfaceType replace(List<TypeParameter> values, List<ModifiedType> replacements )
	{		
		if( isRecursivelyParameterized() )
		{		
			InterfaceType replaced = new InterfaceType( getTypeName(), getModifiers(), getOuter() );
			
			for( InterfaceType _interface : extendTypes )
				replaced.addExtendType(_interface.replace(values, replacements));		
			
			//should have no fields in an interface
			
			/*
			Map<String, ModifiedType> fields = getFields(); 
			
			for( String name : fields.keySet() )
			{
				ModifiedType field = fields.get(name);			
				replaced.addField(name, new SimpleModifiedType( field.getType().replace(values, replacements), field.getModifiers() ) );
			}
			*/
			
			Map<String, List<MethodSignature> > methods = getMethodMap();
			
			for( String name : methods.keySet() )
			{
				List<MethodSignature> signatures = methods.get(name);
				
				for( MethodSignature signature : signatures )			
					replaced.addMethod(name, signature.replace(values, replacements));				
			}
			
			//should have no inner interfaces in an interface		
			/*
			Map<String, Type> inners = getInnerClasses();
			
			for( String name : inners.keySet() )		
				replaced.addInnerClass(name, inners.get(name).replace(values, replacements));
			*/
			return replaced;
		}
		
		
		return this;
	}
	
	@Override
	public boolean isRecursivelyParameterized()
	{
		if( isParameterized() )
			return true;
		
		for( InterfaceType parent : extendTypes )
			if( parent.isRecursivelyParameterized() )
				return true;
		
		return false;
	}
	
	public boolean isSubtype(Type t)
	{
		if( t == UNKNOWN )
			return false;
	
		if( equals(t) || t == Type.OBJECT )
			return true;		
		
		if( t instanceof InterfaceType )			
			return isDescendentOf(t);
		else
			return false;	
	}	
}
