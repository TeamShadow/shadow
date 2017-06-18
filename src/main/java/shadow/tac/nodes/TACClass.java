package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.interpreter.ShadowInteger;
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
			if( type instanceof ArrayType || type.isParameterized() )
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
				return new MethodTableType(((ArrayType)type).convertToGeneric());
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

	public TACClass(TACNode node, Type classType) {
		super(node);
		type = classType;		
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
			classData = new TACLoad(this, new TACArrayRef(this, generics, new TACLiteral( this, new ShadowInteger((long)index)), false));
		
			methodTable = new TACLoad(this, new TACArrayRef(this, methodTables, new TACLiteral( this, new ShadowInteger((long)index)), false ));
		}		
		else {	
			MethodSignature signature = method.getSignature();
			if( signature.isWrapper())
				signature = signature.getWrapped();
			Type outer = signature.getOuter();
			
			if( (!type.isParameterized() && !(type instanceof ArrayType)) || //non-generics
				(type instanceof ArrayType && !((ArrayType)type).containsUnboundTypeParameters()) || //fully instantiated arrays
				type.isFullyInstantiated() ) { //fully parameterized generics
				classData = new TACClassData(this);
				canPropagate = true; //basically a literal, other classes require loads				
			}
			else if( type.equals(outer) ) { // just get the current class!
				TACVariable _this = getMethod().getThis();
				classData = new TACLoad(this, new TACFieldRef(new TACLocalLoad(this, _this), new SimpleModifiedType(Type.CLASS, new Modifiers(Modifiers.IMMUTABLE)), "class")); 
			}
			else  //type has uninstantiated generics within it				
				classData = lookUpClass(type, outer);
			
			//predictable method table in all cases
			methodTable = new TACMethodTable(this);
		}
	}
	
	private TACOperand lookUpClass(Type type, Type outer) {		
		if( outer instanceof ClassType ) {
			Type check = type;
			if( type instanceof ArrayType )
				check = ((ArrayType)type).convertToGeneric();
			
			ClassType outerClass = (ClassType) outer;
			SequenceType dependencies = outerClass.getDependencyList();
			int size = dependencies != null ? dependencies.size() : 0; 
			
			int i;
			for( i = 0; i < size && !dependencies.getType(i).equals(check); ++i );
			
			if( i < size ) {				
				TACVariable _this = getMethod().getThis();
				TACLoad classValue = new TACLoad(this, new TACFieldRef(new TACLocalLoad(this, _this), new SimpleModifiedType(Type.CLASS, new Modifiers(Modifiers.IMMUTABLE)), "class")); 
				TACOperand genericClass = TACCast.cast(this, new SimpleModifiedType(Type.GENERIC_CLASS), classValue);
				TACOperand generics = new TACLoad(this, new TACFieldRef(genericClass, "parameters"));
				TACOperand classData = new TACLoad(this, new TACArrayRef(this, generics, new TACLiteral( this, new ShadowInteger((long)(i + outerClass.getTypeParameters().size()))), false));
				
				//these extra classes are always GenericClasses, so we have to throw in a cast
				return TACCast.cast(this, new SimpleModifiedType(Type.GENERIC_CLASS), classData); 
			}
			else
				throw new IllegalArgumentException("Type " + type + " not found in dependency list for class " + outer);
			
		}
		else
			throw new IllegalArgumentException("Cannot look up type parameter in non-class type");		
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
