package shadow.typecheck.type;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import shadow.parser.javacc.Node;
import shadow.parser.javacc.SimpleNode;

public class ClassType extends ClassInterfaceBaseType {
	private ClassType extendType;
	private ArrayList<InterfaceType> implementTypes = new ArrayList<InterfaceType>();
	
	public ClassType(String typeName, ClassType parent ) {
		this( typeName, new Modifiers(), null );
		setExtendType( parent );
	}
	
	public ClassType(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer ) {
		super( typeName, modifiers, outer );
	}
	
	public void setExtendType(ClassType extendType) {
		this.extendType = extendType;
	}
	
	public ClassType getExtendType() {
		return extendType;
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
				if( existing.matchesInterface(signature) && existing.getMethodType().getModifiers().isPublic( ) ) 
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
		
		if( getExtendType() != null && !methodName.equals("create") )
			return getExtendType().recursivelyGetMethods(methodName, list);
		else
			return list;
	}
	
	private Map<String, Integer> fieldIndexCache;
	public int getFieldIndex( String fieldName )
	{
		// Lazily load cache
		if ( fieldIndexCache == null )
		{
			int start = recursivelyCountParentFields();
			Map<String, Integer> cache = new HashMap<String, Integer>();
			for (Map.Entry<String, ModifiedType> field : getSortedFields().
					entrySet())
				cache.put(field.getKey(), start++);
			fieldIndexCache = cache;
		}
		
		Integer index = fieldIndexCache.get(fieldName);
		return index == null ? -1 : index;
	}
	private int recursivelyCountParentFields()
	{
		if ( getExtendType() == null )
			return 0;
		return getExtendType().recursivelyCountParentFields() +
				getExtendType().getFields().size();
	}

	public List<Map.Entry<String, ModifiedType>> getFieldList()
	{
		List<Map.Entry<String, ModifiedType>> fieldList = new ArrayList<Map.Entry<String, ModifiedType>>();
		
		recursivelyUpdateFieldList(fieldList);
		
		return fieldList;
	}
	private void recursivelyUpdateFieldList( List<Map.Entry<String, ModifiedType>> fieldList )
	{
		if ( getExtendType() != null )
			getExtendType().recursivelyUpdateFieldList(fieldList);
		
		fieldList.addAll(getSortedFields().entrySet());
	}
	public Map<String, ModifiedType> getSortedFields()
	{
		// TODO: also sort by size
		return new TreeMap<String, ModifiedType>(getFields());
	}

	private Map<MethodSignature, Integer> methodIndexCache;
	public int getMethodIndex( MethodSignature method )
	{
		// Lazily load cache
		if ( methodIndexCache == null )
		{
			Map<MethodSignature, Integer> cache =
					new HashMap<MethodSignature, Integer>();
			List<MethodSignature> methods = getOrderedMethods();
			for ( int i = 0; i < methods.size(); i++ )
				cache.put(methods.get(i), i);
			methodIndexCache = cache;
		}

		Integer index = methodIndexCache.get(method);
		return index == null ? -1 : index;
	}

	public List<MethodSignature> getOrderedMethods()
	{
		List<MethodSignature> methodList = new ArrayList<MethodSignature>();
		
		recursivelyUpdateOrderedMethods(methodList);
		
		return methodList;
	}
	private void recursivelyUpdateOrderedMethods( List<MethodSignature> methodList )
	{
		if ( getExtendType() != null )
			getExtendType().recursivelyUpdateOrderedMethods(methodList);
		
		TreeMap<String, List<MethodSignature>> sortedMethods =
				new TreeMap<String, List<MethodSignature>>(methodTable);
		sortedMethods.remove("create"); // skip creates

		for ( List<MethodSignature> methods : sortedMethods.values() )
			for ( MethodSignature method : methods )				
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
	public ClassType replace(SequenceType values, SequenceType replacements )
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
			
			Map<String, ClassInterfaceBaseType> inners = getInnerClasses();
			
			for( String name : inners.keySet() )		
				replaced.addInnerClass(name, inners.get(name).replace(values, replacements));
			
			//replaced.setTypeArguments( new SequenceType(replacements) );
			
			for( ModifiedType modifiedParameter : getTypeParameters() )	
			{
				Type parameter = modifiedParameter.getType();
				replaced.addTypeParameter( new SimpleModifiedType(parameter.replace(values, replacements), modifiedParameter.getModifiers()) );
			}
			
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
	
	public Set<Type> getAllReferencedTypes()
	{
		Set<Type> types = new HashSet<Type>(getReferencedTypes());
		ClassType current = getExtendType();
		while (current != null)
		{
			types.add(current);
			current = current.getExtendType();
		}
		return types;
	}
	
	public boolean isRecursivelyParameterized()
	{
		if( isParameterized() )
			return true;
		
		if( extendType == null )
			return false;
		
		return extendType.isRecursivelyParameterized();
	}
	

	public void printMetaFile(PrintWriter out, String linePrefix )
	{
		printMetaFile(out, linePrefix, "class");	
	}
	
	protected void printMetaFile(PrintWriter out, String linePrefix, String kind )
	{
		//imports
		if( getOuter() == null )
			for( Type importType : getAllReferencedTypes() )			
				if( !recursivelyContainsInnerClass(importType) )
					out.println(linePrefix + "import " + importType.getFullName() + ";");
		
		//modifiers
		out.print("\n" + linePrefix + getModifiers());		
		out.print(kind + " ");
		
		//type name
		if( getOuter() == null ) //outermost class		
			out.print(getFullName());
		else
			out.print(getTypeName().substring(getTypeName().lastIndexOf(':') + 1));
			
		
		//type parameters
		if( isParameterized() )
			out.print(getTypeParameters().toString("<", ">"));
		
		//extend type
		Type extendType = getExtendType();
		if( extendType != null )
			out.print(" extends " + extendType.getFullName() );
		
		//interfaces implemented
		List<InterfaceType> interfaces = getInterfaces();
		boolean first = true;
		if( interfaces.size() > 0 )
		{
			out.print(" implements ");
			for( InterfaceType _interface : interfaces )
			{
				if(!first)
					out.print(", ");
				else
					first = false;
				out.print(_interface.getFullName());				
			}			
		}
		
		out.println("\n" + linePrefix + "{");
		
		String indent = linePrefix + "\t";		
		boolean newLine;
		
		//constants are the only public fields
		newLine = false;
		for( Map.Entry<String, ModifiedType> field : getSortedFields().entrySet() )
			if( field.getValue().getModifiers().isConstant() ) 
			{
				out.println(indent + field.getValue().getModifiers() + field.getValue().getType() + " " + field.getKey());
				newLine = true;
			}		
		if( newLine )
			out.println();		

		//methods
		newLine = false;
		for( List<MethodSignature> list: getMethodMap().values() )		
			for( MethodSignature signature : list )
			{
				Modifiers modifiers = signature.getModifiers();
				if( modifiers.isPublic() || modifiers.isProtected() )
				{				
					out.println(indent + signature + ";");
					newLine = true;
				}
			}
		if( newLine )
			out.println();
		
		//inner classes
		for( ClassInterfaceBaseType _class : getInnerClasses().values() )
		{
			_class.printMetaFile(out, indent);		
		}
		
		out.println(linePrefix + "}\n");	
	}
}