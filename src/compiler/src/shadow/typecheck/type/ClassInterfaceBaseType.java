package shadow.typecheck.type;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import shadow.parser.javacc.Node;

public abstract class ClassInterfaceBaseType extends Type
{

	private Map<String, Node> fieldTable;
	protected HashMap<String, List<MethodSignature> > methodTable; // TODO: change this to private
	private HashMap<String, ClassInterfaceBaseType> innerClasses;
	private Set<Type> referencedTypes = new HashSet<Type>();

	public Map<String, ClassInterfaceBaseType> getInnerClasses()
	{
		return innerClasses;
	}
	
	protected void addInnerClass(String name, ClassInterfaceBaseType innerClass)
	{
		innerClasses.put( name, innerClass );
	}
	
	public ClassInterfaceBaseType(String typeName) {
		this( typeName, new Modifiers() );
	}
	
	public ClassInterfaceBaseType(String typeName, Modifiers modifiers)
	{
		this( typeName, modifiers, null );
	}	

	public ClassInterfaceBaseType(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer ) {
		super( typeName, modifiers, outer );
		fieldTable = new HashMap<String, Node>();
		methodTable = new HashMap<String, List<MethodSignature>>();
		innerClasses = new HashMap<String, ClassInterfaceBaseType>();
				
		if( outer != null )
		{		
			typeName = typeName.substring(typeName.lastIndexOf(':') + 1); //works even if name doesn't contain a :				
			outer.innerClasses.put(typeName, this);
		}
	}
	
	public boolean containsField(String fieldName) {
		return fieldTable.containsKey(fieldName);
	}
	
	public boolean containsInnerClass(String className) {
		return innerClasses.containsKey(className);
	}
	
	public boolean containsInnerClass(Type type)
	{
		return innerClasses.containsValue(type);		
	}
	
	public boolean recursivelyContainsInnerClass(Type type)
	{
		if( innerClasses.containsValue(type) )
			return true;
		
		for( ClassInterfaceBaseType innerClass : innerClasses.values() )
			if( innerClass.recursivelyContainsInnerClass(type) )
				return true;
		
		return false;
	}
	
	public ClassInterfaceBaseType getInnerClass(String className) {
		return innerClasses.get(className);
	}
	
	public void addField(String fieldName, Node node) {
		fieldTable.put(fieldName, node);
	}
	
	public Node getField(String fieldName) {
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
		return containsMethod( signature, Modifiers.NO_MODIFIERS );		
	}
	
	
	public boolean containsMethod(MethodSignature signature, Modifiers modifiers ) //must have certain modifiers (usually public)
	{
		List<MethodSignature> list = methodTable.get(signature.getSymbol());
		
		if( list != null )
			for(MethodSignature existing : list )
				if( existing.equals(signature) && (existing.getMethodType().getModifiers().hasModifier(modifiers) )) 
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

	public List<MethodSignature> getMethods(String methodName)
	{
		return methodTable.get(methodName);
	}

	public List<MethodSignature> getAllMethods()
	{
		List<MethodSignature> methodList = new ArrayList<MethodSignature>();

		recursivelyGetAllMethods(methodList);

		return methodList;
	}
	protected abstract void recursivelyGetAllMethods( List<MethodSignature> methodList );

	private Map<MethodSignature, Integer> methodIndexCache;
	public int getMethodIndex( MethodSignature method )
	{
		// Lazily load cache
		if ( methodIndexCache == null )
		{
			Map<MethodSignature, Integer> cache =
					new HashMap<MethodSignature, Integer>();
			List<MethodSignature> methods = orderAllMethods();
			for ( int i = 0; i < methods.size(); i++ )
				cache.put(methods.get(i), i);
			methodIndexCache = cache;
		}

		Integer index = methodIndexCache.get(method);
		return index == null ? -1 : index;
	}

	public List<MethodSignature> orderAllMethods()
	{
		List<MethodSignature> methodList = new ArrayList<MethodSignature>();

		recursivelyOrderAllMethods(methodList);

		return methodList;
	}
	protected abstract void recursivelyOrderAllMethods( List<MethodSignature> methodList );
	protected void orderMethods( List<MethodSignature> methodList )
	{
		TreeMap<String, List<MethodSignature>> sortedMethods =
				new TreeMap<String, List<MethodSignature>>(methodTable);

		for ( List<MethodSignature> methods : sortedMethods.values() )
			for ( MethodSignature method : methods )
				if ( method.getModifiers().isPublic() )
		{
			int index;
			for ( index = 0; index < methodList.size(); index++ )
				if ( methodList.get(index).isIndistinguishable(method) )
			{
				methodList.set(index, method);
				break;
			}
			if ( index == methodList.size() )
				methodList.add(method);
		}
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
	
	public boolean encloses(Type type) {
		if( equals(this) )
			return true;
		
		ClassInterfaceBaseType outer = type.getOuter();
		if( outer == null )
			return false;		
		
		return encloses(outer);
	}
	
	public void addReferencedType(Type type)
	{
		if (!equals(type) && !(type instanceof TypeParameter ) && !referencedTypes.contains(type) && !isDescendentOf(type))
			referencedTypes.add(type);
	}
	public Set<Type> getReferencedTypes()
	{
		return referencedTypes;
	}

	public abstract boolean hasInterface(InterfaceType type);
	public abstract boolean isRecursivelyParameterized();	
	public abstract ClassInterfaceBaseType replace(SequenceType values, SequenceType replacements );	
	public abstract void printMetaFile(PrintWriter out, String linePrefix );
	public abstract boolean isDescendentOf(Type type);
}
