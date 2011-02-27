package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.HashSet;

import shadow.typecheck.MethodSignature;

public class InterfaceType extends ClassInterfaceBaseType {
	protected ArrayList<InterfaceType> extendTypes = new ArrayList<InterfaceType>();

	public InterfaceType(String typeName) {
		this( typeName, 0 );
	}
	
	public InterfaceType(String typeName, int modifiers) {
		this( typeName, modifiers, null );
	}
	
	public InterfaceType(String typeName, int modifiers, Type outer ) {
		this( typeName, modifiers, outer, Kind.INTERFACE );
	}	
		
	public InterfaceType(String typeName, int modifiers, Type outer, Kind kind ) {
		super( typeName, modifiers, outer, kind );
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
	
}
