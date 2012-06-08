package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.parser.javacc.SimpleNode;

public class ClassType extends ClassInterfaceBaseType {
	private ArrayList<InterfaceType> implementTypes = new ArrayList<InterfaceType>();
	private Set<Type> referencedTypes = new HashSet<Type>();
	
	public ClassType(String typeName, ClassType parent ) {
		this( typeName, 0 );
		setExtendType( parent );
	}
	
	public ClassType(String typeName) {
		this( typeName, 0 );
	}
	
	public ClassType(String typeName, int modifiers) {
		this( typeName, modifiers, null );
	}	
	
	public ClassType(String typeName, int modifiers, ClassInterfaceBaseType outer ) {
		super( typeName, modifiers, outer );
	}	
	
	public boolean isDescendentOf(Type type)
	{
		ClassType parent = getExtendType();
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
				if( !recursivelyContainsInterfaceMethod( signature  ) )
					return false;						
		}	
		
		for( InterfaceType parentInterface : _interface.getExtendTypes()  )
			if( !satisfiesInterface( parentInterface ) )
				return false;
		
		return true;
	}

	private boolean containsInterfaceMethod(MethodSignature signature) {
		List<MethodSignature> list = methodTable.get(signature.getSymbol());
		
		if( list != null )
			for(MethodSignature existing : list )
				if( existing.matchesInterface(signature) && ModifierSet.isPublic(existing.getMethodType().getModifiers() ) ) 
					return true;
		
		return false;
	}
	
	private boolean recursivelyContainsInterfaceMethod(MethodSignature signature) {
		if( containsInterfaceMethod(signature))
			return true;

		if( getExtendType() == null )
			return false;					
		
		//recursively check parents
		return getExtendType().recursivelyContainsInterfaceMethod(signature);
	}

	/*
	public boolean recursivelyContainsMethod(MethodSignature signature, int modifiers ) //must have certain modifiers (usually public)
	{
		if( containsMethod(signature, modifiers))
			return true;

		if( getExtendType() == null )
			return false;					
		
		//recursively check parents
		return getExtendType().recursivelyContainsMethod(signature, modifiers );
	}
	*/
	
	public MethodSignature recursivelyGetIndistinguishableMethod(MethodSignature signature)
	{		
		if( containsIndistinguishableMethod(signature) )
			return super.getIndistinguishableMethod(signature);
		

		if( getExtendType() == null )
			return null;

		//recursively check parents
		return getExtendType().recursivelyGetIndistinguishableMethod(signature);
	}
	
	public boolean recursivelyContainsIndistinguishableMethod(MethodSignature signature) //not identical, but indistinguishable at call time
	{
		if( containsIndistinguishableMethod(signature) )
			return true;
		
		//recursively check parents
		if( getExtendType() != null )
			return getExtendType().recursivelyContainsIndistinguishableMethod(signature);	
		
		return false;
	}
	
	public boolean recursivelyContainsField(String fieldName) {
		if( containsField(fieldName) )
			return true;
		
		if( getExtendType() == null )
			return false;
		
		return getExtendType().recursivelyContainsField(fieldName);
	}	
	
	public boolean recursivelyContainsMethod(String symbol)
	{
		if( containsMethod(symbol) )
			return true;
		
		if( getExtendType() == null )
			return false;
		
		return getExtendType().recursivelyContainsMethod(symbol);
	}
	
	public boolean recursivelyContainsInnerClass(String className)
	{
		if( containsInnerClass(className))
			return true;
		
		if( getExtendType() == null )
			return false;		
		
		return getExtendType().recursivelyContainsInnerClass(className);
	}
	
	public Type recursivelyGetInnerClass(String className) {
		if( containsInnerClass(className) )
			return getInnerClass(className);
		
		if( getExtendType() == null )
			return null;
				
		return getExtendType().recursivelyGetInnerClass(className);
	}

	public Node recursivelyGetField(String fieldName)
	{
		if( containsField(fieldName) )
			return getField(fieldName);
		
		if( getExtendType() == null )
			return null;
				
		return getExtendType().recursivelyGetField(fieldName);
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
		
		if( getExtendType() != null )
			return getExtendType().recursivelyGetMethods(methodName, list);
		else
			return list;
	}
	
	public List<Node> getFieldList()
	{
		List<Node> fieldList = new ArrayList<Node>();
		
		recursivelyUpdateFieldList(fieldList);
		
		return fieldList;
	}
	public void recursivelyUpdateFieldList( List<Node> fieldList )
	{
		if ( getExtendType() != null )
			getExtendType().recursivelyUpdateFieldList(fieldList);
		
		fieldList.addAll(getFields().values());
	}
	
	public List<MethodSignature> getMethodList()
	{
		List<MethodSignature> methodsList = new ArrayList<MethodSignature>();
		
		recursivelyUpdateMethodList(methodsList);
		
		return methodsList;
	}
	public void recursivelyUpdateMethodList( List<MethodSignature> methodList )
	{
		if ( getExtendType() != null )
			getExtendType().recursivelyUpdateMethodList(methodList);
		
		for ( List<MethodSignature> methods : methodTable.values() )
		{
			for ( MethodSignature method : methods )
			{
				boolean replaced = false;
				for ( int i = 0; i < methodList.size() && !replaced; i++ )
				{
					if ( methodList.get(i).isIndistinguishable(method) )
					{
						methodList.set(i, method);
						replaced = true;
					}
				}
				if ( !replaced )
					methodList.add(method);
			}
		}
	}
	
	public void addReferencedType(Type type)
	{
		if (!equals(type) && !referencedTypes.contains(type) && !isDescendentOf(type))
			referencedTypes.add(type);
	}
	public Set<Type> getReferencedTypes()
	{
		return referencedTypes;
	}
	public Set<Type> getAllReferencedTypes()
	{
		Set<Type> types = new HashSet<Type>(referencedTypes);
		ClassType current = getExtendType();
		while (current != null)
		{
			types.add(current);
			current = current.getExtendType();
		}
		return types;
	}
	
	@Override
	public String getMangledName()
	{
		StringBuilder sb = new StringBuilder();
		if (!isPrimitive())
			sb.append(getPackage().getMangledName()).append("_C");
		mangle(getTypeName(), sb);
		return sb.toString();
	}

	
	@Override
	public ClassType replace(List<TypeParameter> values, List<ModifiedType> replacements )
	{	
		if( isRecursivelyParameterized() )
		{		
			ClassType replaced = new ClassType( getTypeName(), getModifiers(), getOuter() );			
								
			replaced.setExtendType(getExtendType().replace(values, replacements));			
			
			Map<String, Node> fields = getFields(); 
			
			for( String name : fields.keySet() )
			{
				SimpleNode field = (SimpleNode)(fields.get(name));
				field = field.clone();
				field.setType(field.getType().replace(values, replacements));			
				replaced.addField(name, field );
			}
			
			Map<String, List<MethodSignature> > methods = getMethodMap();
			
			for( String name : methods.keySet() )
			{
				List<MethodSignature> signatures = methods.get(name);
				
				for( MethodSignature signature : signatures )			
					replaced.addMethod(name, signature.replace(values, replacements));				
			}
			
			Map<String, Type> inners = getInnerClasses();
			
			for( String name : inners.keySet() )		
				replaced.addInnerClass(name, inners.get(name).replace(values, replacements));
			
			return replaced;
		}
		
		return this;
	}
	
	public boolean isSubtype(Type t)
	{
		if( t == UNKNOWN || this == UNKNOWN )
			return false;
	
		if( this == NULL || equals(t) || t == Type.OBJECT )
			return true;		
		
		if( t.isNumerical() && isNumerical() )
			return isNumericalSubtype(t);
		else if( t instanceof ClassType )			
			return isDescendentOf(t);
		else if( t instanceof InterfaceType )
			return hasInterface(t);
		else
			return false;
	}
}
