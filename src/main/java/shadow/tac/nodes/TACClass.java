package shadow.tac.nodes;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowInteger;
import shadow.interpreter.ShadowNull;
import shadow.interpreter.ShadowString;
import shadow.parser.javacc.ShadowException;
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
			if( type.isParameterizedIncludingOuterClasses() && !isRaw )
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
				return Type.OBJECT;
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
	}
	
	public TACClass(TACNode node, Type classType ) {
		this(node, classType, false);
	}

	public TACClass(TACNode node, Type classType, boolean raw) {
		super(node);
		type = classType;
		isRaw = raw;
		TACMethod method = getMethod();
		if (type instanceof TypeParameter)
		{	
			//no support for generic methods, only generic classes
			//generic methods are being removed from Shadow			
			Type outer = classType.getOuter();	
			int index = 0;				
			for( ModifiedType parameter : outer.getTypeParametersIncludingOuterClasses()  ) {
				if( parameter.getType().getTypeName().equals(classType.getTypeName()) )
					break;
				index++;
			}
			
			if( index == outer.getTypeParametersIncludingOuterClasses().size() )
				throw new IllegalArgumentException("Index " + index + " equal to type parameter size " + outer.getTypeParametersIncludingOuterClasses().size());
			
			//get class from this
			//cast class to GenericClass
			//get generics field from GenericClass
			//get arrayref to index location
			
			TACVariable _this = method.getThis();				
			TACLoad classValue = new TACLoad(this, new TACFieldRef(new TACLocalLoad(this, _this), new SimpleModifiedType(Type.CLASS, new Modifiers(Modifiers.IMMUTABLE)), "class")); 
			TACOperand genericClass = TACCast.cast(this, new SimpleModifiedType(Type.GENERIC_CLASS), classValue);
			TACOperand generics = new TACLoad(this, new TACFieldRef(genericClass, "parameters"));
			TACOperand parameter = new TACLoad(this, new TACArrayRef(this, generics, new TACLiteral( this, new ShadowInteger(2*index)), false));
			classData = TACCast.cast(this, new SimpleModifiedType(Type.CLASS), parameter );
		
			methodTable = new TACLoad(this, new TACArrayRef(this, generics, new TACLiteral( this, new ShadowInteger(2*index + 1)), false ));
		}		
		else
		{	
			MethodSignature signature = method.getSignature();
			if( signature.isWrapper())
				signature = signature.getWrapped();
			Type outer = signature.getOuter();
			
			
			if( (!type.isParameterizedIncludingOuterClasses() && !(type instanceof ArrayType) ) || //non-generics 
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
				if( type.encloses(outer) ) { //we're currently inside this type and can get it from class values					
					TACOperand prefix = new TACLocalLoad(this, method.getThis());					
					while( !type.equals(outer)) {
						prefix = new TACLoad(this, new TACFieldRef(prefix, prefix, "_outer"));
						outer = outer.getOuter();						
					}					
					classData = new TACLoad(this, new TACFieldRef(prefix, new SimpleModifiedType(Type.CLASS, new Modifiers(Modifiers.IMMUTABLE)), "class"));
				}
				//construct otherwise absent array type
				else if( type instanceof ArrayType )
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
		
		//if( type.hasOuter() )
		//	return isGenericArray(type.getOuter());
		
		return false;	
	}
	
	private TACOperand buildArrayClass(ArrayType type) {
		
		TACOperand dimensions = new TACLiteral(this, new ShadowInteger(type.getDimensions()));
		TACOperand base = new TACClass(this, type.getBaseType());							
		TACOperand name;
		
		if( type.isFullyInstantiated() )
			name = new TACLiteral(this, new ShadowString(type.toString()));
		else {
			TACMethodRef getName = new TACMethodRef(this, Type.CLASS.getMatchingMethod("toString", new SequenceType()));					
				TACOperand baseName = new TACCall(this, getName, base);
				StringBuilder builder = new StringBuilder("[");
				for( int i = 1; i < type.getDimensions(); ++i )
					builder.append(",");
				builder.append("]");
				TACOperand brackets = new TACLiteral(this, new ShadowString(builder.toString()));
				name = new TACCall(this, new TACMethodRef(this, Type.STRING.getMethods("concatenate").get(0)), baseName, brackets);
		}

		TACMethod method = getMethod();
		TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.CLASS));		
		
		TACGlobal classSet = new TACGlobal(this, Type.CLASS_SET, "@_arraySet");
		
		SequenceType arguments = new SequenceType();
		arguments.add(name);
		arguments.add(base);
		arguments.add(dimensions);
		
		TACMethodRef findArray = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("findArray", arguments));
		
		TACCall class_ = new TACCall(this, findArray, classSet, name, base, dimensions);
		TACOperand isNull = new TACSame(this, class_, new TACLiteral(this, new ShadowNull(class_.getType())));
		TACLabelRef nullCase = new TACLabelRef(method);
		TACLabelRef notNullCase = new TACLabelRef(method);
		TACLabelRef done = new TACLabelRef(method);
		new TACBranch(this, isNull, nullCase, notNullCase);
		nullCase.new TACLabel(this);
		TACMethodRef addArray = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("addArray", arguments));
		TACCall addedClass = new TACCall(this, addArray, classSet, name, base, dimensions);
		new TACLocalStore(this, var, addedClass);
		new TACBranch(this, done);
		notNullCase.new TACLabel(this);
		new TACLocalStore(this, var, class_);
		new TACBranch(this, done);
		done.new TACLabel(this);		 	
		
		return new TACLocalLoad(this, var);
	}
	
	private TACOperand makeGenericName(Type type, TACNewArray parameterArray) {
		TACOperand name;			
		
		if( type.isFullyInstantiated() )
			name = new TACLiteral(this, new ShadowString(type.toString(Type.PACKAGES | Type.TYPE_PARAMETERS)));
		else {
			//put all outer types into a stack, with the outermost at the top
			//the goal is to avoid creating outer classes 
			Deque<Type> types = new ArrayDeque<Type>();
			types.push(type);
			
			while( types.peek().hasOuter() )
				types.push(types.peek().getOuter() );			
			
			SequenceType parameters;
			TACOperand baseName;
			TACMethodRef makeName;			
			Type current = types.pop();
			int start = 0;
			int end = current.getTypeParametersIncludingOuterClasses().size() * 2;
			TACOperand startValue = new TACLiteral(this, new ShadowInteger(start));
			TACOperand endValue = new TACLiteral(this, new ShadowInteger(end));
			
			//first get outermost type
			if( current.isFullyInstantiated() )
				name = new TACLiteral(this, new ShadowString(current.toString(Type.PACKAGES | Type.TYPE_PARAMETERS)));
			else {				
				baseName = new TACLiteral(this, new ShadowString(current.toString(Type.PACKAGES) ));
				
				parameters = new SequenceType();
				parameters.add(baseName);
				parameters.add(parameterArray);
				parameters.add(startValue);
				parameters.add(endValue);
				makeName = new TACMethodRef(this, Type.CLASS.getMatchingMethod("makeName", parameters));
				name = new TACCall(this, makeName, new TACLiteral(this, new ShadowNull(Type.CLASS)), baseName, parameterArray, startValue, endValue);
			}		
			
			while( !types.isEmpty() ) {
				current = types.pop();
				baseName = new TACLiteral(this, new ShadowString(":" + current.getTypeName()));
				
				if( current.isParameterized() ) {
					end = start + current.getTypeParameters().size() * 2;
					startValue = new TACLiteral(this, new ShadowInteger(start));
					endValue = new TACLiteral(this, new ShadowInteger(end));
					
					parameters = new SequenceType();
					parameters.add(baseName);
					parameters.add(parameterArray);
					parameters.add(startValue);
					parameters.add(endValue);
					makeName = new TACMethodRef(this, Type.CLASS.getMatchingMethod("makeName", parameters));
					name = new TACCall(this, new TACMethodRef(this, Type.STRING.getMethods("concatenate").get(0)), name, new TACCall(this, makeName, new TACLiteral(this, new ShadowNull(Type.CLASS)), baseName, parameterArray, startValue, endValue));
				}
				else {
					end = start;
					name = new TACCall(this, new TACMethodRef(this, Type.STRING.getMethods("concatenate").get(0)), name, baseName);
				}
				start = end;
			}	
		}
		
		return name;
	}
	
	private TACOperand buildGenericClass(Type type) {
		TACMethod method = getMethod();		
		
		TACNewArray parameterArray = new TACNewArray(this, new ArrayType(Type.OBJECT), new TACClass(this, Type.OBJECT), new TACLiteral(this, new ShadowInteger(type.getTypeParametersIncludingOuterClasses().size()*2)));
		
		int i = 0;
		for( ModifiedType argument : type.getTypeParametersIncludingOuterClasses() ) {	
			TACClass class_ = new TACClass(this, argument.getType()); 
			new TACStore(this, new TACArrayRef(this, parameterArray, new TACLiteral(this, new ShadowInteger(i)), false), class_.getClassData());
			i++;
			TACOperand methodTable = class_.getMethodTable();
			if( methodTable == null )
				methodTable = new TACLiteral(this, new ShadowNull(Type.NULL));
			new TACStore(this, new TACArrayRef(this, parameterArray, new TACLiteral(this, new ShadowInteger(i)), false), methodTable);
			i++;
		}
		
		TACOperand name = makeGenericName(type, parameterArray);
		
		//after making the name, array parameters must be changed to their generic versions
		i = 0;
		for( ModifiedType argument : type.getTypeParametersIncludingOuterClasses() ) {
			if( argument.getType() instanceof ArrayType ) {
				ArrayType arrayType = (ArrayType) argument.getType();
				TACClass class_ = new TACClass(this, arrayType); 
				new TACStore(this, new TACArrayRef(this, parameterArray, new TACLiteral(this, new ShadowInteger(i)), false), class_.getClassData());
				i++;
				TACOperand methodTable = class_.getMethodTable();
				if( methodTable == null )
					methodTable = new TACLiteral(this, new ShadowNull(Type.NULL));
				new TACStore(this, new TACArrayRef(this, parameterArray, new TACLiteral(this, new ShadowInteger(i)), false), methodTable);
				i++;
			}
			else
				i += 2;
		}
		
		TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.CLASS));		
		
		TACGlobal classSet = new TACGlobal(this, Type.CLASS_SET, "@_genericSet");		
		
		SequenceType arguments = new SequenceType();
		arguments.add(name);		
		arguments.add(new SimpleModifiedType(parameterArray.getType(), new Modifiers(Modifiers.IMMUTABLE)));
		
		TACMethodRef findGeneric = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("findGeneric", arguments));
		
		TACCall class_ = new TACCall(this, findGeneric, classSet, name, parameterArray);
		TACOperand isNull = new TACSame(this, class_, new TACLiteral(this, new ShadowNull(class_.getType())));
		TACLabelRef nullCase = new TACLabelRef(method);
		TACLabelRef notNullCase = new TACLabelRef(method);
		TACLabelRef done = new TACLabelRef(method);
		new TACBranch(this, isNull, nullCase, notNullCase);
		nullCase.new TACLabel(this);
		
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

		TACMethodRef addGeneric = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("addGeneric", arguments));
		TACCall addedClass = new TACCall(this, addGeneric, classSet, base, name, parent, interfaceArray, parameterArray);
		new TACLocalStore(this, var, addedClass);
		new TACBranch(this, done);
		notNullCase.new TACLabel(this);
		new TACLocalStore(this, var, class_);
		new TACBranch(this, done);
		done.new TACLabel(this);
		
		return new TACLocalLoad(this, var);
	}
	
	private TACOperand buildGenericArrayClass(Type type) {		
		TACNewArray parameterArray = new TACNewArray(this, new ArrayType(Type.OBJECT), new TACClass(this, Type.OBJECT), new TACLiteral(this, new ShadowInteger(type.getTypeParametersIncludingOuterClasses().size()*2)));
		
		//store type parameters
		Type base = type.getTypeParametersIncludingOuterClasses().get(0).getType();	
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
			methodTable = new TACLiteral(this, new ShadowNull(Type.NULL));
		new TACStore(this, new TACArrayRef(this, parameterArray, new TACLiteral(this, new ShadowInteger(1)), false), methodTable);
		
		
		TACGlobal classSet = new TACGlobal(this, Type.CLASS_SET, "@_genericSet");
		TACOperand name = makeGenericName(type, parameterArray);
		
		TACOperand isNull;
		if( Type.ARRAY_NULLABLE.encloses(type) )
			isNull = new TACLiteral(this, new ShadowBoolean(true));
		else
			isNull = new TACLiteral(this, new ShadowBoolean(false));
		
		SequenceType arguments = new SequenceType();
		arguments.add(name);		
		arguments.add(new SimpleModifiedType(parameterArray.getType(), new Modifiers(Modifiers.IMMUTABLE)));
		arguments.add(isNull);		
		
		TACMethodRef getGenericArray = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("getGenericArray", arguments));
		
		return new TACCall(this, getGenericArray, classSet, name, parameterArray, isNull);
	}

	public TACOperand getClassData()
	{
		return classData;
	}
	
	public boolean isRaw() {
		return isRaw;
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
