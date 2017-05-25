package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.List;

import shadow.ShadowException;
import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowInteger;
import shadow.interpreter.ShadowNull;
import shadow.interpreter.ShadowString;
import shadow.output.llvm.LLVMOutput;
import shadow.tac.TACMethod;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodTableType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

/** 
 * TAC representation of class constant
 * Example: Object:class
 * @author Jacob Young 
 * @author Barry Wittman
 */

public class TACClass extends TACOperand
{
	private Type type;
	private TACOperand classData;
	private TACOperand methodTable;	
	private boolean isRaw;
	private boolean canPropagate = false;
	
	
	public class TACClassData extends TACOperand {		
		public TACClassData(TACNode node) {
			super(node);
		}
		
		public Type getClassType() {
			return type;
		}

		@Override
		public Type getType() {
			if( type.isParameterized() && !isRaw )
					return Type.GENERIC_CLASS;
			
			return Type.CLASS;
		}

		@Override
		public int getNumOperands() {		
			return 0;
		}

		@Override
		public TACOperand getOperand(int num) {		
			throw new IndexOutOfBoundsException("" + num);
		}

		@Override
		public void accept(TACVisitor visitor) throws ShadowException 
		{
			visitor.visit(this);			
		}
		
		@Override
		public Object getData()
		{
			return LLVMOutput.classOf(type);
		}
		
		@Override
		public boolean canPropagate()
		{
			return true;
		}
	}
	
	public class TACMethodTable extends TACOperand
	{	
		
		public TACMethodTable(TACNode node)
		{
			super(node);
		}
		
		public Type getClassType() {
			return type;
		}

		@Override
		public Type getType() {
			if( type instanceof InterfaceType )
				return Type.METHOD_TABLE;
			else if( type instanceof ArrayType )
				return new MethodTableType(((ArrayType)type).recursivelyGetBaseType());
			else
				return new MethodTableType(type);
		}

		@Override
		public int getNumOperands()
		{		
			return 0;
		}

		@Override
		public TACOperand getOperand(int num) 
		{		
			throw new IndexOutOfBoundsException("" + num);
		}

		@Override
		public void accept(TACVisitor visitor) throws ShadowException {
			visitor.visit(this);			
		}
		
		@Override
		public Object getData() {
			Type withoutArguments = type.getTypeWithoutTypeArguments();
			if( withoutArguments instanceof ArrayType )
				withoutArguments = ((ArrayType)withoutArguments).recursivelyGetBaseType();
				
			if( withoutArguments instanceof InterfaceType )
				return "null";
			else			
				return LLVMOutput.methodTable(withoutArguments);
		}
		
		@Override
		public boolean canPropagate()
		{
			return true;
		}
	}
	
	public TACClass(TACNode node, Type classType ) {
		this(node, classType, false);
	}

	public TACClass(TACNode node, Type classType, boolean raw) {
		super(node);
		type = classType;
		isRaw = raw;
		TACMethod method = getMethod();
		if (type instanceof TypeParameter) {	
			//no support for generic methods, only generic classes
			//generic methods are being removed from Shadow			
			Type outer = classType.getOuter();	
			int index = 0;				
			for( ModifiedType parameter : outer.getTypeParameters()  ) {
				if( parameter.getType().getTypeName().equals(classType.getTypeName()) )
					break;
				index++;
			}
			
			if( index == outer.getTypeParameters().size() )
				throw new IllegalArgumentException("Type parameter " + classType.getTypeName() + " not present in class " + outer );
			
			//get class from this
			//cast class to GenericClass
			//get generics field from GenericClass
			//get arrayref to index location
			
			TACVariable _this = method.getThis();
			TACLoad classValue = new TACLoad(this, new TACFieldRef(new TACLocalLoad(this, _this), new SimpleModifiedType(Type.CLASS, new Modifiers(Modifiers.IMMUTABLE)), "class")); 
			TACOperand genericClass = TACCast.cast(this, new SimpleModifiedType(Type.GENERIC_CLASS), classValue);
			TACOperand generics = new TACLoad(this, new TACFieldRef(genericClass, "parameters"));
			TACOperand methodTables = new TACLoad(this, new TACFieldRef(genericClass, "tables"));
			TACOperand parameter = new TACLoad(this, new TACArrayRef(this, generics, new TACLiteral( this, new ShadowInteger(index)), false));
			classData = TACCast.cast(this, new SimpleModifiedType(Type.CLASS), parameter );
		
			methodTable = new TACLoad(this, new TACArrayRef(this, methodTables, new TACLiteral( this, new ShadowInteger(index)), false ));
		}		
		else {	
			MethodSignature signature = method.getSignature();
			if( signature.isWrapper())
				signature = signature.getWrapped();
			Type outer = signature.getOuter();
			
			
			if( (!type.isParameterized() && !(type instanceof ArrayType) ) || //non-generics
				(!type.isParameterized() && outer.getUsedTypes().contains(type) ) || //must in this situation be an array type
				(outer.getUsedTypes().contains(type) && type.isFullyInstantiated()) || //fully parameterized generics defined in the file
				raw ) {
				
				classData = new TACClassData(this);
				canPropagate = true; //basically a literal, other classes require loads
				
				if( type instanceof ArrayType )										
					methodTable = null;
				else 				
					methodTable = new TACMethodTable(this);
			}
			else { //type has generics and is not defined in this file				
				//construct otherwise absent array type
				if( type instanceof ArrayType )
					classData = buildArrayClass((ArrayType)type);
				//generic array classes are different from regular generics
				else if( isGenericArray(type) )		
					classData = buildGenericArrayClass(type);
				//regular generics
				else					
					classData = buildGenericClass(type);
				
				methodTable = new TACMethodTable(this);
			}
		}
	}
	
	private static boolean isGenericArray(Type type) {
		if( type.getTypeWithoutTypeArguments().equals(Type.ARRAY) || type.getTypeWithoutTypeArguments().equals(Type.ARRAY_NULLABLE) )
			return true;

		return false;	
	}
	
	private TACOperand buildArrayClass(ArrayType type) {		
		
		TACOperand base = new TACClass(this, type.getBaseType());							
		TACOperand name;
		
		if( type.isFullyInstantiated() )
			name = new TACLiteral(this, new ShadowString(type.toString()));
		else {
			TACMethodRef getName = new TACMethodRef(this, Type.CLASS.getMatchingMethod("toString", new SequenceType()));					
				TACOperand baseName = new TACCall(this, getName, base);				
				TACOperand brackets = new TACLiteral(this, new ShadowString("[]"));
				name = new TACCall(this, new TACMethodRef(this, Type.STRING.getMethods("concatenate").get(0)), baseName, brackets);
		}

		TACMethod method = getMethod();
		TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.CLASS));		
		
		TACLoad classSet = new TACLoad(this, new TACGlobalRef(Type.CLASS_SET, "@_arraySet"));
		
		SequenceType arguments = new SequenceType();
		arguments.add(name);
		arguments.add(base);		
		
		TACMethodRef findArray = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("findArray", arguments));
		
		TACCall class_ = new TACCall(this, findArray, classSet, name, base);
		TACOperand isNull = new TACBinary(this, class_, new TACLiteral(this, new ShadowNull(class_.getType())));
		TACLabel nullCase = new TACLabel(method);
		TACLabel notNullCase = new TACLabel(method);
		TACLabel done = new TACLabel(method);
		new TACBranch(this, isNull, nullCase, notNullCase);
		nullCase.insertBefore(this);
		TACMethodRef addArray = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("addArray", arguments));
		TACCall addedClass = new TACCall(this, addArray, classSet, name, base);
		new TACLocalStore(this, var, addedClass);
		new TACBranch(this, done);
		notNullCase.insertBefore(this);
		new TACLocalStore(this, var, class_);
		new TACBranch(this, done);
		done.insertBefore(this);		 	
		
		return new TACLocalLoad(this, var);
	}	
	
	private TACOperand makeGenericName(Type type, TACNewArray parameterArray) {
		TACOperand name;			
		
		if( type.isFullyInstantiated() )
			name = new TACLiteral(this, new ShadowString(type.toString(Type.PACKAGES | Type.TYPE_PARAMETERS)));
		else {
			SequenceType parameters;
			TACOperand baseName;
			TACMethodRef makeName;			
					
			baseName = new TACLiteral(this, new ShadowString(type.toString(Type.PACKAGES) ));
			
			parameters = new SequenceType();
			parameters.add(baseName);
			parameters.add(parameterArray);
			makeName = new TACMethodRef(this, Type.CLASS.getMatchingMethod("makeName", parameters));
			name = new TACCall(this, makeName, new TACLiteral(this, new ShadowNull(Type.CLASS)), baseName, parameterArray);			
		}
		
		return name;
	}
	
	private TACOperand buildGenericClass(Type type) {
		TACMethod method = getMethod();		
		
		TACNewArray parameterArray = new TACNewArray(this, new ArrayType(Type.CLASS), new TACClass(this, Type.CLASS), new TACLiteral(this, new ShadowInteger(type.getTypeParameters().size())));
		TACNewArray methodArray = new TACNewArray(this, new ArrayType(Type.METHOD_TABLE), new TACClass(this, Type.METHOD_TABLE), new TACLiteral(this, new ShadowInteger(type.getTypeParameters().size())));
		
		int i = 0;
		for( ModifiedType argument : type.getTypeParameters() ) {	
			TACClass class_ = new TACClass(this, argument.getType()); 
			new TACStore(this, new TACArrayRef(this, parameterArray, new TACLiteral(this, new ShadowInteger(i)), false), class_.getClassData());
			
			TACOperand methodTable = class_.getMethodTable();
			if( methodTable == null )
				methodTable = new TACLiteral(this, new ShadowNull(Type.METHOD_TABLE));
			new TACStore(this, new TACArrayRef(this, methodArray, new TACLiteral(this, new ShadowInteger(i)), false), methodTable);
			i++;
		}
		
		TACOperand name = makeGenericName(type, parameterArray);
			
		TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.CLASS));		
		
		TACLoad classSet = new TACLoad(this, new TACGlobalRef(Type.CLASS_SET, "@_genericSet"));		
		
		SequenceType arguments = new SequenceType();
		arguments.add(name);		
		arguments.add(new SimpleModifiedType(parameterArray.getType(), new Modifiers(Modifiers.IMMUTABLE)));
		
		TACMethodRef findGeneric = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("findGeneric", arguments));
		
		TACCall class_ = new TACCall(this, findGeneric, classSet, name, parameterArray);
		TACOperand isNull = new TACBinary(this, class_, new TACLiteral(this, new ShadowNull(class_.getType())));
		TACLabel nullCase = new TACLabel(method);
		TACLabel notNullCase = new TACLabel(method);
		TACLabel done = new TACLabel(method);
		new TACBranch(this, isNull, nullCase, notNullCase);
		nullCase.insertBefore(this);	
		
		TACClass base = new TACClass(this, type.getTypeWithoutTypeArguments(), true);
		
		TACOperand parent;
		if( type instanceof ClassType ) {
			ClassType classType = (ClassType) type;
			if( classType.getExtendType() != null )
				parent = new TACClass(this, classType.getExtendType()).getClassData();
			else
				parent = new TACLiteral(this, new ShadowNull(Type.CLASS));
		}
		else
			parent = new TACLiteral(this, new ShadowNull(Type.CLASS));
		
		List<InterfaceType> interfaces;
		if( type instanceof InterfaceType ) //no interfaces inside of interfaces
			interfaces = new ArrayList<InterfaceType>();
		else		
			interfaces = type.getAllInterfaces();
		TACNewArray interfaceArray = new TACNewArray(this, new ArrayType(Type.CLASS), new TACClass(this, Type.CLASS), new TACLiteral(this, new ShadowInteger(interfaces.size())));			
		for( i = 0; i < interfaces.size(); ++i )
			new TACStore(this, new TACArrayRef(this, interfaceArray, new TACLiteral(this, new ShadowInteger(i)), false), new TACClass(this, interfaces.get(i)).getClassData());

		arguments = new SequenceType();
		arguments.add(base);
		arguments.add(name);
		arguments.add(parent);
		arguments.add(new SimpleModifiedType(interfaceArray.getType(), new Modifiers(Modifiers.IMMUTABLE)));
		arguments.add(new SimpleModifiedType(parameterArray.getType(), new Modifiers(Modifiers.IMMUTABLE)));
		arguments.add(new SimpleModifiedType(methodArray.getType(), new Modifiers(Modifiers.IMMUTABLE)));

		TACMethodRef addGeneric = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("addGeneric", arguments));
		TACCall addedClass = new TACCall(this, addGeneric, classSet, base, name, parent, interfaceArray, parameterArray, methodArray);
		new TACLocalStore(this, var, addedClass);
		new TACBranch(this, done);
		notNullCase.insertBefore(this);
		new TACLocalStore(this, var, class_);
		new TACBranch(this, done);
		done.insertBefore(this);
		
		return new TACLocalLoad(this, var);
	}
	
	private TACOperand buildGenericArrayClass(Type type) {		
		TACNewArray parameterArray = new TACNewArray(this, new ArrayType(Type.CLASS), new TACClass(this, Type.CLASS), new TACLiteral(this, new ShadowInteger(type.getTypeParameters().size())));
		
		//store type parameters
		Type base = type.getTypeParameters().get(0).getType();	
		TACClass class_ = new TACClass(this, base); 
		new TACStore(this, new TACArrayRef(this, parameterArray, new TACLiteral(this, new ShadowInteger(0)), false), class_.getClassData());
		
		//including method table
		TACOperand methodTable;		
		if( base instanceof ArrayType ) {
			ArrayType arrayType = (ArrayType) base;
			class_ = new TACClass(this, arrayType.recursivelyGetBaseType());
		}
		methodTable = class_.getMethodTable();
		if( methodTable == null )
			methodTable = new TACLiteral(this, new ShadowNull(Type.METHOD_TABLE));
		
		TACLoad classSet = new TACLoad(this, new TACGlobalRef(Type.CLASS_SET, "@_genericSet"));
		TACOperand name = makeGenericName(type, parameterArray);
		
		TACOperand isNull;
		if( Type.ARRAY_NULLABLE.encloses(type) )
			isNull = new TACLiteral(this, new ShadowBoolean(true));
		else
			isNull = new TACLiteral(this, new ShadowBoolean(false));
		
		SequenceType arguments = new SequenceType();
		arguments.add(name);		
		arguments.add(new SimpleModifiedType(parameterArray.getType(), new Modifiers(Modifiers.IMMUTABLE)));
		arguments.add(methodTable);
		arguments.add(isNull);		
		
		TACMethodRef getGenericArray = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("getGenericArray", arguments));
		
		return new TACCall(this, getGenericArray, classSet, name, parameterArray, methodTable, isNull);
	}

	public TACOperand getClassData()
	{
		return classData;
	}
	
	public TACOperand getMethodTable()
	{
		return methodTable;
	}
	
	public Type getClassType() {
		return type;
	}


	@Override
	public Type getType() {
		return getClassData().getType();
	}
	@Override
	public int getNumOperands() {
		if( classData == null )
			return 0;
		else if( methodTable == null)
			return 1;		
		return 2;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if( num < getNumOperands() )
		{
			if( num == 0 )
				return classData;
			else if( num == 1)
				return methodTable;			
		}
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return type + ":class";
	}
	
	@Override
	public boolean canPropagate() {
		return canPropagate;
	}	
}
