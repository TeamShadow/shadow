package shadow.typecheck.type;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import shadow.parser.javacc.Node;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.Package;

public class ClassType extends Type
{
	private ClassType extendType;	
	private HashMap<String, ClassType> innerClasses;
	
	public ClassType(String typeName, ClassType parent ) {
		this( typeName, new Modifiers(), null );
		setExtendType( parent );
	}
	
	public ClassType(String typeName, Modifiers modifiers, Type outer ) {		
		super( typeName, modifiers, outer );
		
		innerClasses = new HashMap<String, ClassType>();
		
		if( outer != null && outer instanceof ClassType && typeName != null )
		{		
			typeName = typeName.substring(typeName.lastIndexOf(':') + 1); //works even if name doesn't contain a :				
			((ClassType)outer).innerClasses.put(typeName, this);
		}
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
		
	public boolean satisfiesInterface( InterfaceType _interface, List<String> reasons  )
	{
		Map<String, List<MethodSignature> > methodMap =  _interface.getMethodMap();
		
		for( List<MethodSignature> signatures : methodMap.values() )
		{
			for( MethodSignature signature : signatures )
				if( !recursivelyContainsInterfaceMethod( signature  ) )
				{
					reasons.add("Does not contain method " + signature);
					return false;
				}
		}	
		
		for( InterfaceType parentInterface : _interface.getInterfaces()  )
			if( !satisfiesInterface( parentInterface, reasons ) )
				return false;
		
		return true;
	}

	private boolean containsInterfaceMethod(MethodSignature signature)
	{
		List<MethodSignature> list = getMethods(signature.getSymbol());
		
		if( list != null )
			for(MethodSignature existing : list )
				if( existing.matchesInterface(signature) && existing.getMethodType().getModifiers().isPublic( ) ) 
					return true;
		
		return false;
	}
	
	private boolean recursivelyContainsInterfaceMethod(MethodSignature signature) {
		if( containsInterfaceMethod(signature))
			return true;

		if( signature.getSymbol().equals("create") || getExtendType() == null )
			return false;					
		
		//recursively check parents
		return getExtendType().recursivelyContainsInterfaceMethod(signature);
	}
	
	public MethodSignature recursivelyGetIndistinguishableMethod(MethodSignature signature)
	{		
		if( containsIndistinguishableMethod(signature) )
			return super.getIndistinguishableMethod(signature);
		
		
		//recursively check outer
		if( getOuter() != null && getOuter() instanceof ClassType && ((ClassType)getOuter()).recursivelyContainsIndistinguishableMethod(signature) )		
			return ((ClassType)getOuter()).recursivelyGetIndistinguishableMethod(signature) ;
		

		if( getExtendType() == null )
			return null;

		//recursively check parents
		return getExtendType().recursivelyGetIndistinguishableMethod(signature);
	}
	
	public boolean recursivelyContainsIndistinguishableMethod(MethodSignature signature) //not identical, but indistinguishable at call time
	{
		if( containsIndistinguishableMethod(signature) )
			return true;
		
		//recursively check outer
		Type outer = getOuter();
		if( outer != null && outer instanceof ClassType && ((ClassType)outer).recursivelyContainsIndistinguishableMethod(signature) )		
			return true;
		
		//recursively check parents
		if( getExtendType() != null )
			return getExtendType().recursivelyContainsIndistinguishableMethod(signature);	
		
		return false;
	}
	
	public boolean recursivelyContainsField(String fieldName) {
		if( containsField(fieldName) )
			return true;
				
		if( getOuter() != null && getOuter() instanceof ClassType && ((ClassType)getOuter()).recursivelyContainsField(fieldName) )		
			return true;
		
		if( getExtendType() == null )
			return false;
		
		return getExtendType().recursivelyContainsField(fieldName);
	}	
	
	public boolean recursivelyContainsMethod(String symbol)
	{
		if( containsMethod(symbol) )
			return true;
				
		if( getOuter() != null && getOuter() instanceof ClassType && ((ClassType)getOuter()).recursivelyContainsMethod(symbol) )		
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
	
	public ClassType recursivelyGetInnerClass(String className) {
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
		
		if( getOuter() != null && getOuter() instanceof ClassType && ((ClassType)getOuter()).recursivelyContainsField(fieldName) )		
			return ((ClassType)getOuter()).recursivelyGetField(fieldName);
		
		if( getExtendType() == null )
			return null;
				
		return getExtendType().recursivelyGetField(fieldName);
	}
	
	//get methods from class and ancestors
	//does not include visible outer class methods
	/*
	public List<MethodSignature> getMethods(String methodName)
	{
		List<MethodSignature> list = new ArrayList<MethodSignature>();
		
		includeMethods( methodName, list );
		
		if( !methodName.equals("create") )
		{		
			ClassType parent = extendType;
			while( parent != null )
			{
				parent.includeMethods(methodName, list);
				parent = parent.extendType;
			}
		}
		
		return list;
	}
	*/
	
	//get methods from all visible sources, adds outer classes too
	public List<MethodSignature> getAllMethods(String methodName)
	{
		List<MethodSignature> list = getMethods(methodName);
				
		if( !methodName.equals("create") )
		{	
			//first the parents
			ClassType parent = extendType;
			while( parent != null )
			{
				parent.includeMethods(methodName, list);
				parent = parent.extendType;
			}
			
			//outer classes of this and parents
			ClassType current = this;
			while( current != null )
			{	
				//then outer classes
				Type outer = current.getOuter();
				while( outer != null && outer instanceof ClassType)
				{
					ClassType outerClass = (ClassType)outer;
					outerClass.includeMethods(methodName, list);
					outer = outerClass.getOuter();
				}
				
				current = current.extendType;
			}		
		}
		
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
			for (Map.Entry<String, ? extends ModifiedType> field :
					sortFields())
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

	public List<Entry<String, ? extends ModifiedType>> orderAllFields()
	{
		List<Entry<String, ? extends ModifiedType>> fieldList = new ArrayList<Entry<String, ? extends ModifiedType>>();
		
		recursivelyOrderAllFields(fieldList);
		
		return fieldList;
	}
	private void recursivelyOrderAllFields( List<Entry<String, ? extends ModifiedType>> fieldList )
	{
		if ( getExtendType() != null )
			getExtendType().recursivelyOrderAllFields(fieldList);
		
		fieldList.addAll(sortFields());
	}
	private Set<Entry<String, ? extends ModifiedType>> sortFields()
	{
		Set<Entry<String, ? extends ModifiedType>> set = new TreeSet<Entry<String, ? extends ModifiedType>>(new Comparator<Entry<String, ? extends ModifiedType>>() {
			@Override
			public int compare(Entry<String, ? extends ModifiedType> first, Entry<String, ? extends ModifiedType> second) {
				int width = getWidth(first.getValue()) - getWidth(second.getValue());
				if (width != 0)
					return -width;
				return first.getKey().compareTo(second.getKey());
			}
		});
		if (getOuter() != null)
			set.add(new Entry<String, ModifiedType>() {
				@Override
				public String getKey()
				{
					return "this";
				}
				@Override
				public ModifiedType getValue()
				{
					return new SimpleModifiedType(getOuter());
				}
				@Override
				public ModifiedType setValue(ModifiedType value)
				{
					throw new UnsupportedOperationException();
				}
			});
		for (Entry<String, ? extends ModifiedType> field : getFields().entrySet())
			if (!field.getValue().getModifiers().isConstant())
				set.add(field);
		if (isParameterized())
			for (final ModifiedType typeParam : getTypeParameters())
				set.add(new Entry<String, ModifiedType>()
				{
					@Override
					public String getKey()
					{
						return typeParam.getType().getTypeName();
					}
					@Override
					public ModifiedType getValue()
					{
						return new SimpleModifiedType(Type.CLASS);
					}
					@Override
					public ModifiedType setValue(ModifiedType value)
					{
						throw new UnsupportedOperationException();
					}
				});
		return set;
	}

	@Override
	protected List<MethodSignature> recursivelyOrderMethods( List<MethodSignature> methodList )
	{
		if ( getExtendType() != null )
			getExtendType().recursivelyOrderAllMethods(methodList);
		return orderMethods(methodList, false);
	}

	@Override
	protected List<MethodSignature> recursivelyOrderAllMethods( List<MethodSignature> methodList )
	{
		if ( getExtendType() != null )
			getExtendType().recursivelyOrderAllMethods(methodList);
		return orderMethods(methodList, true);
	}

	@Override
	public String getMangledName()
	{
		return getPackage().getMangledName() + super.getMangledName();
	}

	
	@Override
	public ClassType replace(SequenceType values, SequenceType replacements )
	{	
		if( isRecursivelyParameterized() )
		{	
			Type cached = typeWithoutTypeArguments.getInstantiation(replacements);
			if( cached != null )
				return (ClassType)cached;
			
			ClassType replaced = new ClassType( getTypeName(), getModifiers(), (ClassType)getOuter() );
			replaced.setPackage(getPackage());
			replaced.typeWithoutTypeArguments = typeWithoutTypeArguments;
			
			typeWithoutTypeArguments.addInstantiation(replacements, replaced);
			
			replaced.setExtendType(getExtendType().replace(values, replacements));			
			
			for( InterfaceType _interface : getInterfaces() )
				replaced.addInterface(_interface.replace(values, replacements));
			
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
			
			Map<String, ClassType> inners = getInnerClasses();
			
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
	
	@Override
	public boolean equals(Object o)
	{		
		if( this.getTypeWithoutTypeArguments() == Type.ARRAY && this.getTypeParameters().size() == 1 && o instanceof ArrayType )
		{
			ArrayType arrayType = (ArrayType)o;
			ModifiedType baseType = this.getTypeParameters().get(0);			
			return baseType != null && arrayType.getBaseType().equals(baseType.getType()) && baseType.getModifiers().getModifiers() == 0;
		}
		
		return super.equals(o);
	}
	
	
	@Override
	public boolean isSubtype(Type t)
	{
		if( t == UNKNOWN || this == UNKNOWN )
			return false;
	
		if( this == NULL || equals(t) || t == Type.OBJECT || t == Type.VAR )
			return true;		
		
		if( t.isNumerical() && isNumerical() )
			return isNumericalSubtype(t);
		else if( t instanceof ClassType )			
			return isDescendentOf(t);
		else if( t instanceof InterfaceType )
			return hasInterface((InterfaceType)t);
		else
			return false;
		
		//note that a ClassType is never the subtype of a TypeParameter
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
	
	public boolean hasInterface(InterfaceType type)
	{	
		ClassType current = this;
		while( current != null )
		{
			for( InterfaceType interfaceType : current.getInterfaces() )
			{
				if( interfaceType.hasInterface(type) )
					return true;
			}
			current = current.getExtendType();			
		}
		return false;
	}
	
	public Map<String, ClassType> getInnerClasses()
	{
		return innerClasses;
	}
	
	protected void addInnerClass(String name, ClassType innerClass)
	{
		innerClasses.put( name, innerClass );
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
		
		for( ClassType innerClass : innerClasses.values() )
			if( innerClass.recursivelyContainsInnerClass(type) )
				return true;
		
		return false;
	}
	
	public ClassType getInnerClass(String className) {
		return innerClasses.get(className);
	}
	

	public void printMetaFile(PrintWriter out, String linePrefix )
	{
		printMetaFile(out, linePrefix, "class");	
	}
	
	protected void printMetaFile(PrintWriter out, String linePrefix, String kind )
	{
		//imports
		if( getOuter() == null )
		{
			HashSet<Type> imports = new HashSet<Type>();
			
			for( Object importItem : getImportedItems() )
			{
				if( importItem instanceof Type )
				{
					Type importType = (Type)importItem;
					if( getAllReferencedTypes().contains(importType))
						imports.add(importType);
						
				}
				else if( importItem instanceof Package )
				{
					Package importPackage = (Package)importItem;
					for( Type referencedType : getAllReferencedTypes() )
						if( referencedType.getPackage().equals( importPackage ) )
							imports.add(referencedType);					
				}
			}
			
			for( Type type : imports )			
				out.println(linePrefix + "import " + type.getImportName() + ";");
		}
			
		/*
			for( Type importType : getAllReferencedTypes() )			
				if( !recursivelyContainsInnerClass(importType) )
					out.println(linePrefix + "import " + importType.getImportName() + ";");					
		*/
		
		//modifiers
		out.print("\n" + linePrefix + getModifiers());		
		out.print(kind + " ");
			
		
		//type name
		String name;
		if( isPrimitive() ) //hack for capitalization purposes
		{							
			if( getTypeName().startsWith("u") )
				name = getTypeName().substring(0,2).toUpperCase() + getTypeName().substring(2);
			else
				name = getTypeName().substring(0,1).toUpperCase() + getTypeName().substring(1);
			out.print("shadow.standard@" + name);
		}
		else if( getOuter() == null ) //outermost class		
			out.print(getQualifiedName(true));
		else
		{	
			name = toString(true);
			out.print(name.substring(name.lastIndexOf(':') + 1));
		}
		
		//extend type
		Type extendType = getExtendType();
		if( extendType != null && !(this instanceof SingletonType) && !(this instanceof ErrorType) && !this.equals(Type.EXCEPTION)  )
			out.print(" extends " + extendType.getQualifiedName() );
		
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
				out.print(_interface.getQualifiedName());				
			}			
		}
		
		out.println("\n" + linePrefix + "{");
		
		String indent = linePrefix + "\t";		
		boolean newLine;
		
		//constants are the only public fields
		newLine = false;
		for( Map.Entry<String, ? extends ModifiedType> field : sortFields() )
			if( field.getValue().getModifiers().isConstant() ) 
			{
				out.println(indent + field.getValue().getModifiers() + field.getValue().getType() + " " + field.getKey() + ";");
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
				if( modifiers.isPublic() || modifiers.isProtected() || signature.isCreate()  )
				{				
					out.println(indent + signature + ";");
					newLine = true;
				}
			}
		if( newLine )
			out.println();
		
		//inner classes
		for( Type _class : getInnerClasses().values() )
		{
			if( !_class.getModifiers().isPrivate() )
				_class.printMetaFile(out, indent);		
		}
		
		out.println(linePrefix + "}");	
	}
}