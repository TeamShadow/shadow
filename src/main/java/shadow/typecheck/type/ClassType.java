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

import shadow.doctool.Documentation;
import shadow.parse.Context;
import shadow.parse.ShadowParser;

public class ClassType extends Type {
	private ClassType extendType;	
	private HashMap<String, ClassType> innerClasses;
	
	public ClassType(String typeName, ClassType parent) {
		this(typeName, new Modifiers(), null, null);
		setExtendType(parent);
	}
	
	public ClassType(String typeName, Modifiers modifiers, 
			Documentation documentation, Type outer) 
	{		
		super(typeName, modifiers, documentation, outer);
		
		innerClasses = new HashMap<String, ClassType>();
	}
	
	public void setExtendType(ClassType extendType) {
		this.extendType = extendType;
	}
	
	public ClassType getExtendType() {
		return extendType;
	}
	
	public boolean isDescendentOf(Type type) {
		ClassType parent = getExtendType();
		while( parent != null ) {
			if( parent.equals(type))
				return true;
			parent = parent.getExtendType();			
		}
		return false;
	}
		
	public boolean satisfiesInterface( InterfaceType _interface, List<String> reasons ) {
		Map<String, List<MethodSignature> > methodMap =  _interface.getMethodMap();
		
		for( List<MethodSignature> signatures : methodMap.values() ) {
			for( MethodSignature signature : signatures )
				if( !recursivelyContainsInterfaceMethod( signature  ) ) {
					reasons.add("Does not contain method " + signature);
					return false;
				}
		}	
		
		for( InterfaceType parentInterface : _interface.getInterfaces()  )
			if( !satisfiesInterface( parentInterface, reasons ) )
				return false;
		
		return true;
	}

	private boolean containsInterfaceMethod(MethodSignature signature) {
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
	
	public MethodSignature recursivelyGetIndistinguishableMethod(MethodSignature signature) {		
		if( containsIndistinguishableMethod(signature) )
			return super.getIndistinguishableMethod(signature);		

		if( getExtendType() == null )
			return null;

		//recursively check parents
		return getExtendType().recursivelyGetIndistinguishableMethod(signature);
	}
	
	public boolean recursivelyContainsIndistinguishableMethod(MethodSignature signature) { //not identical, but indistinguishable at call time
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
	
	public boolean recursivelyContainsMethod(String symbol) {
		if( containsMethod(symbol) )
			return true;

		if( getExtendType() == null )
			return false;		
		
		return getExtendType().recursivelyContainsMethod(symbol);
	}
	
	public boolean recursivelyContainsInnerClass(String className) {
		if( containsInnerClass(className) )
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

	public Context recursivelyGetField(String fieldName) {
		if( containsField(fieldName) )
			return getField(fieldName);

		if( getExtendType() == null )
			return null;
				
		return getExtendType().recursivelyGetField(fieldName);
	}
	
	//get methods from all visible sources, adds outer classes too
	public List<MethodSignature> getAllMethods(String methodName) {
		List<MethodSignature> list = new ArrayList<MethodSignature>(getMethods(methodName));
				
		if( !methodName.equals("create")  ) {	
			//first the parents
			ClassType parent = extendType;
			while( parent != null ) {
				parent.includeMethods(methodName, list);
				parent = parent.extendType;
			}
			
			//outer classes of this and parents
			ClassType current = this;
			while( current != null ) {	
				//then outer classes
				Type outer = current.getOuter();
				while( outer != null && outer instanceof ClassType) {
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
	
	//returns number of fields, including parent fields
	private int buildFieldIndexCache() {		
		int extendFieldSize = getExtendType() == null ? 0 : getExtendType().buildFieldIndexCache();
		
		if ( fieldIndexCache == null ) {
			int next = extendFieldSize;
			Map<String, Integer> cache = new HashMap<String, Integer>();
			for (Map.Entry<String, ? extends ModifiedType> field :
				sortFields())
				cache.put(field.getKey(), next++);
			fieldIndexCache = cache;
			return next;
		}
		else
			return fieldIndexCache.size() + extendFieldSize;		
	}
	
	
	public int getFieldIndex( String fieldName ) {
		// Lazily load cache
		buildFieldIndexCache();		
		
		Integer index = fieldIndexCache.get(fieldName);
		return index == null ? ( getExtendType() == null ? -1 : getExtendType().getFieldIndex(fieldName)) : index;
	}

	public List<Entry<String, ? extends ModifiedType>> orderAllFields() {
		List<Entry<String, ? extends ModifiedType>> fieldList = new ArrayList<Entry<String, ? extends ModifiedType>>();
		
		recursivelyOrderAllFields(fieldList);		
		return fieldList;
	}
	
	private void recursivelyOrderAllFields( List<Entry<String, ? extends ModifiedType>> fieldList ) {
		if ( getExtendType() != null )
			getExtendType().recursivelyOrderAllFields(fieldList);		
		fieldList.addAll(sortFields());
	}
	
	public Set<Entry<String, ? extends ModifiedType>> sortFields() {
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
				public String getKey() {
					return "_outer";
				}
				@Override
				public ModifiedType getValue() {
					return new SimpleModifiedType(getOuter());
				}
				@Override
				public ModifiedType setValue(ModifiedType value){
					throw new UnsupportedOperationException();
				}
			});
		
		//constants live in the class
		//singletons don't need references stored
		for (Entry<String, ? extends ModifiedType> field : getFields().entrySet())
			if (!field.getValue().getModifiers().isConstant() && !(field.getValue().getType() instanceof SingletonType))
				set.add(field);
		
		return set;
	}

	@Override
	protected List<MethodSignature> recursivelyOrderMethods( List<MethodSignature> methodList ) {
		if ( getExtendType() != null )
			getExtendType().recursivelyOrderAllMethods(methodList);
		return orderMethods(methodList, false);
	}

	@Override
	protected List<MethodSignature> recursivelyOrderAllMethods( List<MethodSignature> methodList ) {
		if ( getExtendType() != null )
			getExtendType().recursivelyOrderAllMethods(methodList);
		return orderMethods(methodList, true);
	}
	
	@Override
	public ClassType replace(List<ModifiedType> values, List<ModifiedType> replacements ) throws InstantiationException {	
		if( isRecursivelyParameterized() ) {	
			Type cached = typeWithoutTypeArguments.getInstantiation(this, values, replacements);
			if( cached != null )
				return (ClassType)cached;
			
			ClassType replaced = new ClassType(getTypeName(), getModifiers(), 
					getDocumentation(), (ClassType)getOuter());
			replaced.setPackage(getPackage());
			replaced.typeWithoutTypeArguments = typeWithoutTypeArguments;
			
			typeWithoutTypeArguments.addInstantiation(this, values, replacements, replaced);

			replaced.setExtendType(getExtendType().replace(values, replacements));			
			
			for( InterfaceType _interface : getInterfaces() )
				replaced.addInterface(_interface.replace(values, replacements));
			
			
			Map<String, ShadowParser.VariableDeclaratorContext> fields = getFields();
			for( String name : fields.keySet() ) {
				ShadowParser.VariableDeclaratorContext field = fields.get(name);
				field = Context.copy(field);	
				field.setType(field.getType().replace(values, replacements));			
				replaced.addField(name, field );
			}
			
			for( List<MethodSignature> signatures : getMethodMap().values() )
				for( MethodSignature signature : signatures ) {
					MethodSignature replacedSignature = signature.replace(values, replacements);
					replaced.addMethod(replacedSignature);					
				}
			
			if( isParameterized() )
				for( ModifiedType modifiedParameter : getTypeParameters() )	{
					Type parameter = modifiedParameter.getType();
					replaced.addTypeParameter( new SimpleModifiedType(parameter.replace(values, replacements), modifiedParameter.getModifiers()) );
				}
			
			return replaced;
		}
		
		return this;
	}
	
	@Override
	public ClassType partiallyReplace(List<ModifiedType> values, List<ModifiedType> replacements ) {	
		if( isRecursivelyParameterized() ) {	
			Type cached = typeWithoutTypeArguments.getInstantiation(this, values, replacements);
			if( cached != null )
				return (ClassType)cached;
			
			ClassType replaced = new ClassType(getTypeName(), getModifiers(),
					getDocumentation(), (ClassType)getOuter() );
			replaced.setPackage(getPackage());
			replaced.typeWithoutTypeArguments = typeWithoutTypeArguments;
			
			typeWithoutTypeArguments.addInstantiation(this, values, replacements, replaced);
			
			replaced.setExtendType(getExtendType().partiallyReplace(values, replacements));			
			
			for( InterfaceType _interface : getInterfaces() )
				replaced.addInterface(_interface.partiallyReplace(values, replacements));
			
			Map<String, ShadowParser.VariableDeclaratorContext> fields = getFields(); 
			
			for( String name : fields.keySet() ) {
				ShadowParser.VariableDeclaratorContext field = fields.get(name);
				if( field.getType().isParameterized() ) {
					field = Context.copy(field);					
					SequenceType typeArguments = new SequenceType();
					for( ModifiedType typeParameter : field.getType().getTypeParameters() ) {
						Type type = typeParameter.getType();
						
						if( type instanceof TypeParameter || type.isParameterized() )
							typeArguments.add( new SimpleModifiedType( type.partiallyReplace(values, replacements), typeParameter.getModifiers() ) );
						else
							typeArguments.add(typeParameter);
					}
					
					if( field.getType() instanceof InterfaceType )
						field.setType( new UninstantiatedInterfaceType( (InterfaceType)field.getType(), typeArguments ));
					else
						field.setType( new UninstantiatedClassType( (ClassType)field.getType(), typeArguments ));
				}
				replaced.addField(name, field );
			}
			
			for( List<MethodSignature> signatures : getMethodMap().values() )
				for( MethodSignature signature : signatures ) {	
					MethodSignature replacedSignature = signature.partiallyReplace(values, replacements);
					replaced.addMethod(replacedSignature);
					signature.getNode().setSignature(replacedSignature);
				}
			
			if( isParameterized() )
				for( ModifiedType modifiedParameter : getTypeParameters() )	{
					Type parameter = modifiedParameter.getType();
					replaced.addTypeParameter( new SimpleModifiedType(parameter.partiallyReplace(values, replacements), modifiedParameter.getModifiers()) );
				}
			
			return replaced;
		}
		
		return this;
	}
	
	@Override
	public void updateFieldsAndMethods() throws InstantiationException {	
		ClassType parent = getExtendType();
		
		if( parent != null )
			parent.updateFieldsAndMethods();			
		
		for( InterfaceType _interface : getInterfaces() )
			_interface.updateFieldsAndMethods();
		
		Map<String, ShadowParser.VariableDeclaratorContext> fields = getFields(); 
		
		for( String name : fields.keySet() ) {
			ShadowParser.VariableDeclaratorContext field = fields.get(name);
			Type type = field.getType();
			if( type instanceof UninstantiatedType )
				field.setType( ((UninstantiatedType)type).instantiate() );
			else if( type instanceof ArrayType )
				field.setType( ((ArrayType)type).instantiate() );
		}	
		
		for( List<MethodSignature> signatures : getMethodMap().values() )
			for( MethodSignature signature : signatures ) {			
				signature.updateFieldsAndMethods();
				Context node = signature.getNode(); 
				if( node != null )
					node.setType(signature.getMethodType());
			}

		for( ClassType inner : getInnerClasses().values() )		
			inner.updateFieldsAndMethods();
		
		if( isParameterized() )
			getTypeParameters().updateFieldsAndMethods();
		
		invalidateHashName();
	}
	
	//necessary?
	/*
	@Override
	public boolean equals(Type type) {		
		return super.equals(type);
	}
	*/	
	
	@Override
	public boolean isSubtype(Type t) {
		if( t == UNKNOWN || this == UNKNOWN )
			return false;
	
		if( this == NULL || t == Type.OBJECT || t == Type.VAR || equals(t) )
			return true;
		
		if( t instanceof MethodTableType && this == METHOD_TABLE )
			return true;
		
		if( t instanceof TypeParameter )
			return isSubtype(((TypeParameter)t).getClassBound());
		
		if( t instanceof ArrayType ) {
			ArrayType arrayType = (ArrayType) t;
			return isSubtype(arrayType.convertToGeneric());
		}
		
		if( t.isNumerical() && isNumerical() )
			return isNumericalSubtype(t);
		else if( t instanceof ClassType )			
			return isDescendentOf(t);
		else if( t instanceof InterfaceType )
			return hasInterface((InterfaceType)t);
		else
			return false;		
		//note that a ClassType is never the subtype of a TypeParameter
		//also, Object[] is a subtype of Array<Object>, but Array<Object> is not a subtype of Object[]
	}
	
	public Set<Type> getAllReferencedTypes() {
		Set<Type> types = new HashSet<Type>(getUsedTypes());
		ClassType current = getExtendType();
		while (current != null){
			types.add(current);
			current = current.getExtendType();
		}
		return types;
	}
	
	public boolean isRecursivelyParameterized() {
		if( isParameterized() )
			return true;
		
		if( extendType == null )
			return false;
		
		return extendType.isRecursivelyParameterized();
	}
	
	@Override
	public boolean hasUninstantiatedInterface(InterfaceType type) {
		ClassType current = this;
		
		type = type.getTypeWithoutTypeArguments();
		
		while( current != null ) {
			for( InterfaceType interfaceType : current.getInterfaces() )			
				if( interfaceType.hasUninstantiatedInterface(type) )
					return true;
			
			current = current.getExtendType();			
		}
		return false;
	}
	
	public boolean hasInterface(InterfaceType type) {	
		ClassType current = this;
		while( current != null ) {
			for( InterfaceType interfaceType : current.getInterfaces() ) {
				if( interfaceType.hasInterface(type) )
					return true;
			}
			
			current = current.getExtendType();			
		}
		return false;
	}
	
	public Map<String, ClassType> getInnerClasses() {
		return innerClasses;
	}
	
	public void addInnerClass(String name, ClassType innerClass) {
		innerClasses.put( name, innerClass );
		innerClass.setOuter(this);
	}
	
	public boolean containsInnerClass(String className) {
		return innerClasses.containsKey(className);
	}
	
	public boolean containsInnerClass(Type type) {
		return innerClasses.containsValue(type);		
	}
	
	public boolean recursivelyContainsInnerClass(Type type) {
		if( innerClasses.containsValue(type) )
			return true;
		
		for( ClassType innerClass : innerClasses.values() )
			if( innerClass.recursivelyContainsInnerClass(type) )
				return true;
		
		return false;
	}
	
	public ClassType getInnerClass(String className) {
		if( className.contains(":")) {
			int colon = className.indexOf(':');
			String prefix = className.substring(0, colon);
			ClassType inner = innerClasses.get(prefix);
			if( inner != null )
				return inner.getInnerClass(className.substring(colon + 1));
			else
				return null;
		}			
		
		return innerClasses.get(className);
	}
	
	@Override
	public ClassType getTypeWithoutTypeArguments() {
		return (ClassType)super.getTypeWithoutTypeArguments();
	}
	

	public void printMetaFile(PrintWriter out, String linePrefix ) {
		printMetaFile(out, linePrefix, "class");	
	}
	
	protected void printMetaFile(PrintWriter out, String linePrefix, String kind ) {
		printImports(out, linePrefix);
		
		//modifiers
		out.print(linePrefix + getModifiers());		
		out.print(kind + " ");
		
		//type name
		String name;
		if( isPrimitive() ) {//hack for capitalization purposes									
			if( getTypeName().startsWith("u") )
				name = getTypeName().substring(0,2).toUpperCase() + getTypeName().substring(2);
			else
				name = getTypeName().substring(0,1).toUpperCase() + getTypeName().substring(1);
			out.print("shadow:standard@" + name);
		}
		else if( !hasOuter() ) //outermost class		
			out.print(toString(PACKAGES | TYPE_PARAMETERS | PARAMETER_BOUNDS ));
		else {	
			name = toString(TYPE_PARAMETERS | PARAMETER_BOUNDS);
			out.print(name.substring(name.lastIndexOf(':') + 1));
		}
		
		//extend type
		Type extendType = getExtendType();
		boolean isStarted = false;
		if( extendType != null && !this.equals(Type.EXCEPTION)  ) {
			out.print(" is " + extendType.toString(PACKAGES | TYPE_PARAMETERS) );
			isStarted = true;
		}
		
		//interfaces implemented
		List<InterfaceType> interfaces = getInterfaces();
		boolean first = true;
		if( interfaces.size() > 0 ) {
			if( isStarted )
				first = false;
			else
				out.print(" is ");
			for( InterfaceType _interface : interfaces ) {
				if(!first)
					out.print(" and ");
				else
					first = false;
				out.print(_interface.toString(PACKAGES | TYPE_PARAMETERS));				
			}			
		}
		
		out.println(System.lineSeparator() + linePrefix + "{");
		
		String indent = linePrefix + "\t";		
		boolean newLine;
		
		//constants		
		newLine = false;
		for( Map.Entry<String, ? extends ModifiedType> field : getFields().entrySet() ) {
			Modifiers modifiers = field.getValue().getModifiers(); 
			if( modifiers.isConstant() && (modifiers.isPublic() || modifiers.isProtected())) {
				String visibility = modifiers.isPublic() ? "public" : "protected";
				out.println(indent + visibility + " constant " + field.getValue().getType() + " " + field.getKey() + ";");
				newLine = true;				
			}
		}
		if( newLine )
			out.println();		
		
		//now all fields have to be around, just so that generics can figure out how big they need to be
		//necessary?  perhaps code can be written to compute the size
		//TODO: try to take this back to constants only				
		newLine = false;
		for( Map.Entry<String, ? extends ModifiedType> field : sortFields() )		
			if( !field.getKey().equals("_outer")  ) {
				out.println(indent + field.getValue().getModifiers() + field.getValue().getType() + " " + field.getKey() + ";");
				newLine = true;
			}
		
		if( newLine )
			out.println();		

		//methods
		newLine = false;
		for( List<MethodSignature> list: getMethodMap().values() )		
			for( MethodSignature signature : list ) {
				Modifiers modifiers = signature.getModifiers();
				if( (modifiers.isPublic() || modifiers.isProtected() || signature.isCreate()) && !signature.isCopy() )
				{				
					out.println(indent + signature + ";");
					newLine = true;
				}
			}
		if( newLine && getInnerClasses().size() > 0 )
			out.println();
		
		//inner classes
		for( Type _class : getInnerClasses().values() )
				_class.printMetaFile(out, indent);		
		
		//if( !hasOuter() )
			//printGenerics( out, indent );				
		out.println(linePrefix + "}");	
	}
	
	@Override
	public String toString(int options) {
		if( ((options & MANGLE) != 0) && ((options & CONVERT_ARRAYS) != 0) &&
			Type.ARRAY != null && Type.ARRAY_NULLABLE != null && //not a typical situation, but causes problems when types are still being collected
			(Type.ARRAY.encloses(this) || Type.ARRAY_NULLABLE.encloses(this)) )
			return super.toString(options & ~CONVERT_ARRAYS);
		
		return super.toString(options);		
	}	
}
