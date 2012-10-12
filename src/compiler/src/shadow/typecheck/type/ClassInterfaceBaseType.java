package shadow.typecheck.type;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.parser.javacc.Node;

public abstract class ClassInterfaceBaseType extends Type
{
	private ClassType extendType;
	private Map<String, Node> fieldTable;
	protected HashMap<String, List<MethodSignature> > methodTable; // TODO: change this to private
	private HashMap<String, Type> innerClasses;

	public Map<String, Type> getInnerClasses()
	{
		return innerClasses;
	}
	
	protected void addInnerClass(String name, Type innerClass)
	{
		innerClasses.put( name, innerClass );
	}
	
	public ClassInterfaceBaseType(String typeName) {
		this( typeName, 0 );
	}
	
	public ClassInterfaceBaseType(String typeName, int modifiers)
	{
		this( typeName, modifiers, null );
	}	

	public ClassInterfaceBaseType(String typeName, int modifiers, ClassInterfaceBaseType outer ) {
		super( typeName, modifiers, outer );
		fieldTable = new HashMap<String, Node>();
		methodTable = new HashMap<String, List<MethodSignature>>();
		innerClasses = new HashMap<String, Type>();
		
		if( outer != null && outer instanceof ClassInterfaceBaseType)
		{
			ClassInterfaceBaseType outerClass = (ClassInterfaceBaseType)outer;
			outerClass.innerClasses.put(typeName, this);
		}
	}	
	
	public void setExtendType(ClassType extendType) {
		this.extendType = extendType;
	}
	
	public ClassType getExtendType() {
		return extendType;
	}
	
	public boolean containsField(String fieldName) {
		return fieldTable.containsKey(fieldName);
	}
	
	public boolean containsInnerClass(String className) {
		return innerClasses.containsKey(className);
	}
	
	public Type getInnerClass(String className) {
		return innerClasses.get(className);
	}
	
	public void addField(String fieldName, Node node) {
		fieldTable.put(fieldName, node);
	}
	
	public Node getField(String fieldName) {
		return fieldTable.get(fieldName);
	}
	
	public ModifiedType getFieldType (String fieldName) {
		return fieldTable.get(fieldName);
	}
	
	public Map<String, Node> getFields() {
		return fieldTable;
	}	
	
	public boolean containsMethod(String symbol)
	{
		return methodTable.get(symbol) != null;		
	}	
	
	public boolean containsMethod(MethodSignature signature)
	{
		return containsMethod( signature, 0 );		
	}
	
	
	public boolean containsMethod(MethodSignature signature, int modifiers ) //must have certain modifiers (usually public)
	{
		List<MethodSignature> list = methodTable.get(signature.getSymbol());
		
		if( list != null )
			for(MethodSignature existing : list )
				if( existing.equals(signature) && (existing.getMethodType().getModifiers() & modifiers ) == modifiers ) 
					return true;
		
		return false;
	}	
	
	public boolean containsIndistinguishableMethod(MethodSignature signature) //not identical, but indistinguishable at call time
	{
		List<MethodSignature> list = methodTable.get(signature.getSymbol());
		
		if( list != null )
			for(MethodSignature existing : list )
				if( existing.isIndistinguishable(signature))
					return true;
		
		return false;
	}
	
	public void addMethod(String name, MethodSignature signature) {
		if( methodTable.containsKey(name) )		
			methodTable.get(name).add(signature);
		else
		{
			List<MethodSignature> list = new LinkedList<MethodSignature>();
			list.add(signature);
			methodTable.put(name, list);
		}
	}
	
	public Map<String, List<MethodSignature>> getMethodMap() {
		return methodTable;
	}
	
	public List<MethodSignature> getMethods(String methodName) {
		return methodTable.get(methodName);
	}
		
	/**
	 * This function is only used for error reporting as it finds an indistinguishable signature.
	 * @param signature
	 * @return
	 */
	public MethodSignature getIndistinguishableMethod(MethodSignature signature)
	{		
		for(MethodSignature ms : methodTable.get(signature.getSymbol()))
		{
			if(ms.isIndistinguishable(signature))
				return ms;			
		}
		
		return null;
	}
	
	protected Type findType(String[] names, int i)
	{
		Type type;
		for( String name : innerClasses.keySet() )
		{
			if( name.equals( names[i]) )
			{
				if( i == names.length - 1)
					return innerClasses.get(name);
				
				type = innerClasses.get(name).findType(names, i + 1);
				if( type != null )
					return type;
			}			
		}
		
		for( String name : fieldTable.keySet() )
		{
			if( name.equals( names[i]) )
			{
				if( i == names.length - 1)
					return fieldTable.get(name).getType();
				
				type = fieldTable.get(name).getType().findType(names, i + 1);
				if( type != null )
					return type;
			}			
		}
		
		for( String name : methodTable.keySet() )
		{
			if( name.equals( names[i]) )
			{
				UnboundMethodType methodType = new UnboundMethodType(name, this ); 
				if( i == names.length - 1)
					return methodType;
				
				type = methodType.findType(names, i + 1);
				if( type != null )
					return type;
			}			
		}
		
		return null;
	}
	
	public boolean isRecursivelyParameterized()
	{
		if( isParameterized() )
			return true;
		
		if( extendType == null )
			return false;
		
		return extendType.isRecursivelyParameterized();
	}
	
	public abstract ClassInterfaceBaseType replace(List<TypeParameter> values, List<ModifiedType> replacements );

	public boolean encloses(Type type) {
		if( equals(this) )
			return true;
		
		ClassInterfaceBaseType outer = type.getOuter();
		if( outer == null )
			return false;		
		
		return encloses(outer);
	}
}
