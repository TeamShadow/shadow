package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.typecheck.MethodSignature;

public class ClassType extends ClassInterfaceBaseType {
	protected ClassType extendType;
	protected ArrayList<InterfaceType> implementTypes = new ArrayList<InterfaceType>();
	
	public ClassType(String typeName) {
		this( typeName, 0 );
	}
	
	public ClassType(String typeName, int modifiers) {
		this( typeName, modifiers, null );
	}
	
	public ClassType(String typeName, int modifiers, Type outer ) {
		this( typeName, modifiers, outer, Kind.CLASS );
	}	
	
	public ClassType(String typeName, int modifiers, Type outer, Kind kind ) {
		super( typeName, modifiers, outer, kind);
	}	
	
	public void setExtendType(ClassType extendType) {
		this.extendType = extendType;
	}
	
	public ClassType getExtendType() {
		return extendType;
	}
	
	public boolean isDescendentOf(Type type)
	{
		ClassType parent = extendType;
		while( parent != null )
		{
			if( parent.equals(type))
				return true;
			parent = parent.getExtendType();			
		}
		return false;
	}
	
	public boolean hasInterface(Type type)
	{	
		ClassType current = this;
		while( current != null )
		{
			if( current.implementTypes.contains(type) )
				return true;
			current = current.getExtendType();			
		}
		return false;
	}
	
	public void addInterface(InterfaceType implementType) {
		implementTypes.add(implementType);
	}
	
	public ArrayList<InterfaceType> getInterfaces()
	{
		return implementTypes;
	}	
	
	
	
	public boolean satisfiesInterface( InterfaceType _interface  )
	{
		Map<String, List<MethodSignature> > methodMap =  _interface.getMethodMap();
		
		for( List<MethodSignature> signatures : methodMap.values() )
		{
			for( MethodSignature signature : signatures )
				if( !containsMethod( signature, ModifierSet.PUBLIC  ) )
					return false;						
		}	
		
		for( InterfaceType parentInterface : _interface.getExtendTypes()  )
			if( !satisfiesInterface( parentInterface ) )
				return false;
		
		return true;
	}

	public boolean recursivelyContainsMethod(MethodSignature signature, int modifiers ) //must have certain modifiers (usually public)
	{
		if( containsMethod(signature, modifiers))
			return true;

		if( extendType == null )
			return false;					
		
		//recursively check parents
		return extendType.recursivelyContainsMethod(signature, modifiers );
	}
	
	public MethodSignature recursivelyGetIndistinguishableMethod(MethodSignature signature)
	{		
		if( containsIndistinguishableMethod(signature) )
			return super.getIndistinguishableMethod(signature);
		

		if( extendType == null )
			return null;

		//recursively check parents
		return extendType.recursivelyGetIndistinguishableMethod(signature);
	}
	
	public boolean recursivelyContainsIndistinguishableMethod(MethodSignature signature) //not identical, but indistinguishable at call time
	{
		if( containsIndistinguishableMethod(signature) )
			return true;
		
		//recursively check parents
		if( extendType != null )
			return extendType.recursivelyContainsIndistinguishableMethod(signature);	
		
		return false;
	}
	
	public boolean recursivelyContainsField(String fieldName) {
		if( containsField(fieldName) )
			return true;
		
		if( extendType == null )
			return false;
		
		return extendType.recursivelyContainsField(fieldName);
	}	
	
	public boolean recursivelyContainsMethod(String symbol)
	{
		if( containsMethod(symbol) )
			return true;
		
		if( extendType == null )
			return false;
		
		return extendType.recursivelyContainsMethod(symbol);
	}
	
	public boolean recursivelyContainsInnerClass(String className)
	{
		if( containsInnerClass(className))
			return true;
		
		if( extendType == null )
			return false;		
		
		return extendType.recursivelyContainsInnerClass(className);
	}
	
	public Type recursivelyGetInnerClass(String className) {
		if( containsInnerClass(className) )
			return getInnerClass(className);
		
		if( extendType == null )
			return null;
				
		return extendType.recursivelyGetInnerClass(className);
	}

	public Node recursivelyGetField(String fieldName)
	{
		if( containsField(fieldName) )
			return getField(fieldName);
		
		if( extendType == null )
			return null;
				
		return extendType.recursivelyGetField(fieldName);
	}
	
	public List<MethodSignature> getMethods(String methodName) {
		List<MethodSignature> list = new ArrayList<MethodSignature>();			
		
		return recursivelyGetMethods(methodName, list);
	}
	
	public List<MethodSignature> recursivelyGetMethods( String methodName, List<MethodSignature> list  )
	{
		List<MethodSignature> currentSignatures = methodTable.get(methodName);
		
		if( currentSignatures != null )
			for( MethodSignature signature : currentSignatures )
				if( !list.contains( signature ) )
					list.add(signature);
		
		if( extendType != null )
			return extendType.recursivelyGetMethods(methodName, list);
		else
			return list;
	}
	
}
