package shadow.tac.nodes;

import java.util.Arrays;
import java.util.Collection;

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowInteger;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowValue;
import shadow.output.llvm.LLVMOutput;
import shadow.tac.TACMethod;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACGenericArrayRef extends TACArrayRef {
	
	//in most situations, these two are the same
	//it's possible to have different values in the case of an array
	//e.g. if T is Wombat[], then T[] is Wombat[][]
	//internalParameter would be Wombat[]
	//genericParameter would be Array<Wombat>
	//private TACOperand internalParameter;
	private TACClass genericParameter;
	private boolean isNullable;

	public TACGenericArrayRef(TACNode node, TACOperand reference,
			Collection<TACOperand> ops) {
		this(node, reference, ops, true);
	}
	
	public TACGenericArrayRef(TACNode node, TACOperand reference,
			Collection<TACOperand> ops, boolean check) {
		super(node, reference, ops, check);
		
		ArrayType arrayType = (ArrayType) reference.getType();
		Type baseType = arrayType.getBaseType();
		genericParameter = new TACClass(this, baseType);		
		isNullable =  reference.getModifiers().isNullable(); 
		
		/*
		
		if( baseType instanceof ArrayType ) {
			ArrayType arrayBase = (ArrayType)baseType;
			if( reference.getModifiers().isNullable() )
				arrayBase = arrayBase.convertToNullable();			
			genericParameter = new TACClass(this, arrayBase.convertToGeneric() ).getClassData();
		}
		else {
			TACOperand flags = new TACFieldRef(this, internalClass, "flags");			
			TACLiteral arrayFlag = new TACLiteral(this, new ShadowInteger(LLVMOutput.ARRAY) );			
			TACOperand value = new TACBinary(this, flags, Type.INT.getMatchingMethod("bitAnd", new SequenceType(arrayFlag)), '&', arrayFlag);
			MethodSignature signature = Type.INT.getMatchingMethod("equal", new SequenceType(value));
			TACOperand test = new TACBinary(this, value, signature, '=', new TACLiteral(this, new ShadowInteger(0)));
			
			TACLabelRef convertLabel = new TACLabelRef(this);
			TACLabelRef noChangeLabel = new TACLabelRef(this);
			TACLabelRef doneLabel = new TACLabelRef(this);
			TACMethod method = getBuilder().getMethod();
			TACVariableRef var = new TACVariableRef(this,
					method.addTempLocal(new SimpleModifiedType(Type.CLASS)));
			
			new TACBranch(this, test, noChangeLabel, convertLabel);			
			
			convertLabel.new TACLabel(this);			
			TACOperand baseClass = new TACFieldRef(this, internalClass, "parent");			
			TACBlock block = getBuilder().getBlock();
			TACGlobal classSet = new TACGlobal(this, Type.CLASS_SET, "@_genericSet");
			
			SequenceType parameters = new SequenceType();
			TACOperand arrayName;
			TACOperand isNull;
			if( reference.getModifiers().isNullable() ) {			
				arrayName = new TACLiteral(this, new ShadowString(Type.ARRAY_NULLABLE.toString(Type.PACKAGES)));
				isNull = new TACLiteral(this, new ShadowBoolean(true));
			}
			else {
				arrayName = new TACLiteral(this, new ShadowString(Type.ARRAY.toString(Type.PACKAGES)));
				isNull = new TACLiteral(this, new ShadowBoolean(false));
			}
			parameters.add(arrayName);
			parameters.add(new SimpleModifiedType(baseClass.getType())); //removes nullable modifier
			
			TACMethodRef makeName = new TACMethodRef(this, Type.CLASS.getMatchingMethod("makeName", parameters));
			TACOperand name = new TACCall(this, block, makeName, new TACLiteral(this, ShadowValue.NULL), arrayName, baseClass);			
			
			SequenceType arguments = new SequenceType();
			arguments.add(name);		
			arguments.add(new SimpleModifiedType(baseClass.getType())); //removes nullable modifier
			arguments.add(internalParameter.getMethodTable());
			arguments.add(isNull);			

			TACMethodRef getGenericArray = new TACMethodRef(this, classSet, Type.CLASS_SET.getMatchingMethod("getGenericArray", arguments));			
			TACOperand genericArray = new TACCall(this, block, getGenericArray, classSet, name, baseClass, internalParameter.getMethodTable(), isNull);
			
			new TACStore(this, var, genericArray );
			new TACBranch(this, doneLabel);
			noChangeLabel.new TACLabel(this);
			new TACStore(this, var, internalClass );
			new TACBranch(this, doneLabel);
			doneLabel.new TACLabel(this);
			
			genericParameter = new TACLoad(this, var);
		}
		
		new TACNodeRef(this, getTotal());
		*/
	}

	
	public TACClass getGenericParameter() {
		return genericParameter;
	}
	
	public boolean isNullable() {
		return isNullable;
	}
	
}
