package shadow.output.llvm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowCode;
import shadow.interpreter.ShadowDouble;
import shadow.interpreter.ShadowFloat;
import shadow.interpreter.ShadowInteger;
import shadow.interpreter.ShadowInterpreter;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowValue;
import shadow.output.AbstractOutput;
import shadow.output.Cleanup;
import shadow.output.TabbedLineWriter;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACConstant;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBlock;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACConstantRef;
import shadow.tac.nodes.TACConstructGeneric;
import shadow.tac.nodes.TACConversion;
import shadow.tac.nodes.TACCopyMemory;
import shadow.tac.nodes.TACDestination;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACGenericArrayRef;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLength;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACLongToPointer;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNot;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACPhiRef;
import shadow.tac.nodes.TACPhiRef.TACPhi;
import shadow.tac.nodes.TACPointerToLong;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSame;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceElement;
import shadow.tac.nodes.TACSequenceRef;
import shadow.tac.nodes.TACSingletonRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACTypeId;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACUnwind;
import shadow.tac.nodes.TACVariableRef;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InstantiationException;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public class LLVMOutput extends AbstractOutput {
	private Process process = null;
	private int tempCounter = 0, labelCounter = 0;
	private List<String> stringLiterals = new LinkedList<String>();
	private HashSet<Type> generics = new HashSet<Type>();	
	private HashSet<ArrayType> arrays = new HashSet<ArrayType>();
	private HashSet<Type> unparameterizedGenerics = new HashSet<Type>();	
	private TACMethod method;
	private int classCounter = 0;
	private HashSet<MethodSignature> usedSignatures = new HashSet<MethodSignature>();
	private HashSet<TACConstantRef> usedConstants = new HashSet<TACConstantRef>();
	private HashSet<Type> usedMethodTables = new HashSet<Type>();
	private HashSet<Type> usedClasses = new HashSet<Type>();
	
	private TACModule module;
	
	public LLVMOutput(File file) throws ShadowException {		
		super(new File(file.getParent(),
				file.getName().replace(".shadow", ".ll")));
	}
	
	//used to do an LLVM check pass for debugging
	public LLVMOutput(boolean mode) throws ShadowException {
		if (!mode) {
			try {
				process = new ProcessBuilder("opt", "-S", "-O3").start();
			}
			catch (IOException ex) {
				throw new ShadowException(ex.getLocalizedMessage());
			}
			writer = new TabbedLineWriter(process.getOutputStream());
		}
		writer.setLineNumbers(mode);
	}

	private String temp(int offset)
	{
		return '%' + Integer.toString(tempCounter - offset - 1);
	}
	private String nextTemp()
	{
		return '%' + Integer.toString(tempCounter++);
	}
	private String nextTemp(TACOperand node)
	{
		String name = nextTemp();
		node.setData(name);
		return name;
	}

	private String nextLabel()
	{
		return "%_label" + labelCounter++;
	}
	private String nextLabel(TACOperand node)
	{
		String name = nextLabel();
		node.setData(name);
		return name;
	}

	// Class type flags
	public static final int INTERFACE = 1, PRIMITIVE = 2, GENERIC = 4, ARRAY = 8, SINGLETON = 16, METHOD = 32;
	
	private void writePrimitiveTypes() throws ShadowException
	{
		writer.write("%boolean = type i1");
		writer.write("%byte = type i8");
		writer.write("%ubyte = type i8");
		writer.write("%short = type i16");
		writer.write("%ushort = type i16");
		writer.write("%int = type i32");
		writer.write("%uint = type i32");
		writer.write("%code = type i32");
		writer.write("%long = type i64");
		writer.write("%ulong = type i64");
		writer.write("%float = type float");
		writer.write("%double = type double");
		writer.write();	
	}
	
	private void writeTypeOpaque(Type type) throws ShadowException
	{
		StringBuilder sb = new StringBuilder();
		
		if( type instanceof InterfaceType )
		{
			if( type.isFullyInstantiated() )
				sb.append('%').append(withGenerics(type, "_methods")).append(" = type opaque");
			else if( type.isUninstantiated() )
				sb.append('%').append(raw(type, "_methods")).append(" = type opaque");
			
			writer.write(sb.toString());
		}
		else if (type instanceof ClassType)
		{	
			if( type.isUninstantiated() )
			{
				sb.append('%').append(raw(type, "_methods")).append(" = type opaque");
								
				sb.setLength(0);
				//first thing in every object is the class			
				sb.append('%').append(raw(type)).append(" = type opaque");
		
				writer.write(sb.toString());
			}
		}	
	}
	
	
	private void writeTypeDeclaration(Type type) throws ShadowException
	{
		StringBuilder sb = new StringBuilder();
		
		if( type instanceof InterfaceType ) {
			if( type.isFullyInstantiated() )
				sb.append('%').append(withGenerics(type, "_methods")).append(" = type { ");
			else if( type.isUninstantiated() )
				sb.append('%').append(raw(type, "_methods")).append(" = type { ");
			
			writer.write(sb.append(methodList(type.orderAllMethods(), false)).
					append(" }").toString());
		}
		else if (type instanceof ClassType) {	
			if( type.isUninstantiated() ) {
				sb.append('%').append(raw(type, "_methods")).append(" = type { ");
				
				writer.write(sb.append(methodList(type.orderAllMethods(), false)).
						append(" }").toString());
				
				sb.setLength(0);
				//first thing in every object is the class			
				sb.append('%').append(raw(type)).append(" = type { ");
				
				sb.append(type(Type.CLASS)).append(", ");
				
				//then the method table
				sb.append('%').append(raw(type, "_methods")).append("* ");
										
				if (type.isPrimitive())
					sb.append(", ").append(type(type));
				else			
				 for (Entry<String, ? extends ModifiedType> field :
						((ClassType)type).orderAllFields())
					sb.append(", ").append(type(field.getValue()));
				writer.write(sb.append(" }").toString());
			}
		}	
	}
	
	private void writeTypeConstants(Type type) throws ShadowException {	
		if( type.isParameterizedIncludingOuterClasses() ) {
			//unparameterizedGenerics.add(type.getTypeWithoutTypeArguments());
		
			if( type.isFullyInstantiated() ) {				
				writer.write(classOf(type) + " = external constant %" + raw(Type.CLASS));				
				
				/*
				if( type.getTypeWithoutTypeArguments().equals(Type.ARRAY) || type.getTypeWithoutTypeArguments().equals(Type.ARRAY_NULLABLE) )
					generics.add(new GenericArray(type));
				else
					generics.add(new Generic(type));
				*/
			}
			else if( type instanceof ClassType && type.isUninstantiated() ) {
				//methods recorded for classes with no type parameters and for the raw class of those that do
				writer.write('@' + raw(type, "_methods") +
					" = external constant %" + raw(type, "_methods"));
			}
		}
		else {
			writer.write(classOf(type) +
					" = external constant %" + raw(Type.CLASS));
			
			if( type instanceof ClassType ) {
				//methods recorded for classes with no type parameters and for the raw class of those that do
				writer.write('@' + raw(type, "_methods") +
					" = external constant %" + raw(type, "_methods"));
			}	
			
			
			if (type instanceof SingletonType) //never parameterized
				writer.write('@' + raw(type, "_instance") +
						" = external global " + type(type));
		}	
	}
	
	
	protected void writeModuleDefinition(TACModule module) throws ShadowException {		
		Type moduleType = module.getType();
		writer.write("; " + module.getQualifiedName());
		writer.write();
		
		//Class fields (since the order is all changed)
		//used for debugging
		if( moduleType instanceof ClassType ) {
			ClassType classType = (ClassType) moduleType;
			writer.write("; 0: class (Class)");
			writer.write("; 1: _methods");
			int counter = 2;			
			for (Entry<String, ? extends ModifiedType> field :
				(classType).orderAllFields()) {	
				
				writer.write("; " + counter + ": " + field.getKey() + " (" + field.getValue().getType() + ")" );
				++counter;				
			}
			
			writer.write();
		}
		
		//fields
		writeTypeDeclaration(moduleType);
		
		//constants
		for (TACConstant constant : module.getConstants()) {
			ShadowInterpreter interpreter = new ShadowInterpreter();
			interpreter.walk(constant);
			Object result = constant.getValue().getData();
			if (!(result instanceof ShadowValue))
				throw new UnsupportedOperationException(
						"Could not interpret constant initializer");
			writer.write(name(constant) + " = constant " +
					typeLiteral((ShadowValue)result));
			Cleanup.getInstance().walk(constant);
		}
		
		//interfaces implemented (because a special object is used to map the methods correctly)
		ArrayList<InterfaceType> interfaces = moduleType.getAllInterfaces();
		int interfaceCount = interfaces.size();
		
		if( module.isClass() ) {
		//no need to write interface data for interfaces				
			StringBuilder interfaceData = new StringBuilder( "@_interfaceData" + moduleType.getMangledName() + " = ");			

			if( !moduleType.isParameterizedIncludingOuterClasses() )
				interfaceData.append("private " );			
					
			//generic classes don't list interfaces (because their parameterized versions have those)
			//but they do share interfaceData (the actual methods)
			interfaceData.append("unnamed_addr constant [").
					append(interfaceCount).append(" x ").
					append(type(Type.OBJECT)).append("] [");
			StringBuilder interfaceClasses = new StringBuilder("@_interfaces" + moduleType.getMangledName() + " = private ");
			
			interfaceClasses.append("unnamed_addr constant [").
				append(interfaceCount).append(" x ").
				append(type(Type.CLASS)).append("] [");
			
			boolean firstData = true;
			boolean firstClass = true;
			
			for(InterfaceType type : interfaces) {			
				if( module.isClass() ) {
					List<MethodSignature> methods = type.orderAllMethods(module.
							getClassType());
					
					String methodsType = methodList(methods, false);
					writer.write("@_class" + classCounter +
							" = private unnamed_addr constant { " + methodsType +
							" } { " + methodList(methods, true) + " }");
					if( firstData )
						firstData = false;
					else
						interfaceData.append(", ");
					interfaceData.append(type(Type.OBJECT)).append(" bitcast ({ ").
							append(methodsType).append(" }* @_class").append(classCounter).
							append(" to ").append(type(Type.OBJECT)).append(")");
				}				
				
				if( firstClass )
					firstClass = false;
				else
					interfaceClasses.append(", ");				
				interfaceClasses.append(typeText(Type.CLASS, classOf(type)));				
				usedClasses.add(type);
				classCounter++;
			}
			writer.write(interfaceData.append(']').toString());
			
			if( !moduleType.isParameterizedIncludingOuterClasses() )	
				writer.write(interfaceClasses.append(']').toString());
		}

		//method declarations (but not definitions)
		List<MethodSignature> methods = moduleType.orderAllMethods();
		
		int flags = 0;
		if (moduleType.isPrimitive())
			flags |= PRIMITIVE;
		if( moduleType instanceof SingletonType )
			flags |= SINGLETON;
		if (module.isClass()) {
			//add in methods that are inherited from parent classes
			for( MethodSignature signature : methods)			
				if( (signature.getOuter() instanceof ClassType) && !module.getType().encloses(signature.getOuter()) && !signature.isWrapper() )
					usedSignatures.add(signature);
			
			ClassType parentType = ((ClassType)moduleType).getExtendType();
			writer.write('@' + raw(moduleType, "_methods") + " = constant %" +
					raw(moduleType, "_methods") + " { " +					
					methodList(methods, true) + " }");
			
			//nothing will ever be the raw, unparameterized class
			if( !moduleType.isParameterizedIncludingOuterClasses() ) {
				writer.write('@' + raw(moduleType, "_class") + " = constant %" +
					raw(Type.CLASS) + " { " + 		
					
					type(Type.CLASS) + " @" + raw(Type.CLASS, "_class") + ", " + //class
					"%" + raw(Type.CLASS, "_methods") + "* @" + raw(Type.CLASS, "_methods") + ", " + //methods
					typeLiteral(moduleType.getQualifiedName()) + ", " +  //name
					
					typeText(Type.CLASS, parentType != null ?  //parent class
							classOf(parentType) : null) + ", " +
					
					type(new ArrayType(Type.OBJECT)) + " { " +   //data
					type(Type.OBJECT) + "* getelementptr inbounds ([" +
					interfaceCount + " x " + type(Type.OBJECT) +
					"]* @_interfaceData" + moduleType.getMangledName() + ", i32 0, i32 0), [1 x " + 
					type(Type.INT) + "] [" + typeLiteral(interfaceCount) +
					"] }, " + 
					type(new ArrayType(Type.CLASS)) + " { " + //interfaces
					type(Type.CLASS) + "* getelementptr inbounds ([" +
					interfaceCount + " x " + type(Type.CLASS) +
					"]* @_interfaces" + moduleType.getMangledName() + ", i32 0, i32 0), [1 x " + type(Type.INT) +
					"] [" + typeLiteral(interfaceCount) + "] }, " +
					
					typeLiteral(flags) + ", " +			//flags
					typeText(Type.INT, sizeof('%' + raw(moduleType) + '*')) + //size 
					" }" );
				
				//class definition above includes parent Class
				if( parentType != null )
					usedClasses.add(parentType);
			}
		}
		else {
			flags |= INTERFACE;
			
			//nothing will ever be the raw, unparameterized class
			if( !moduleType.isParameterizedIncludingOuterClasses() )
				writer.write('@' + raw(moduleType, "_class") + " = constant %" +
					raw(Type.CLASS) + " { " + 		
					
					type(Type.CLASS) + " @" + raw(Type.CLASS, "_class") + ", " + //class
					"%" + raw(Type.CLASS, "_methods") + "* @" + raw(Type.CLASS, "_methods") + ", " + //methods
					
					typeLiteral(moduleType.getQualifiedName()) + ", " + //name 
					type(Type.CLASS) + " null, " + //parent 					
					
					type(new ArrayType(Type.OBJECT)) + " zeroinitializer, " + //data
					type(new ArrayType(Type.CLASS)) +  " zeroinitializer, " + //interfaces
					
					typeLiteral(flags) + ", " +
					typeLiteral(-1) + //size (unknown for interfaces) 
					" }");
		}
		
		//all module class definitions need Class class and methods
		if( !module.getType().encloses(Type.CLASS)) {
			usedClasses.add(Type.CLASS);
			usedMethodTables.add(Type.CLASS);
		}
		
		if (moduleType instanceof SingletonType)
			writer.write('@' + raw(moduleType, "_instance") + " = global " +
					type(moduleType) + " null");
		
		writer.write();
		
		if( moduleType.isParameterizedIncludingOuterClasses() )
			unparameterizedGenerics.add(moduleType.getTypeWithoutTypeArguments());		
		
		//recursively do inner classes
		for( TACModule innerClass : module.getInnerClasses() )
			writeModuleDefinition(innerClass);
	}
	

	@Override
	public void startFile(TACModule module) throws ShadowException {		
		this.module = module;		
		Type moduleType = module.getType();
			
		//boiler plate for all types
		writePrimitiveTypes();
		
		// Methods for exception handling
		writer.write("declare i32 @__shadow_personality_v0(...)");
		writer.write("declare void @__shadow_throw(" + type(Type.OBJECT) + ") noreturn");
		writer.write("declare " + type(Type.EXCEPTION) + " @__shadow_catch(i8* nocapture) nounwind");
		writer.write("declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone");
		//memcopy
		writer.write("declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)");
		writer.write("declare void @__arrayStore(%\"_Pshadow_Pstandard_CObject\"**, i32, %\"_Pshadow_Pstandard_CObject\"*, %\"_Pshadow_Pstandard_CClass\"*)");
		writer.write("declare %\"_Pshadow_Pstandard_CObject\"* @__arrayLoad(%\"_Pshadow_Pstandard_CObject\"**, i32, %\"_Pshadow_Pstandard_CClass\"*, %\"_Pshadow_Pstandard_CObject\"*)");
		writer.write("declare noalias void @free(i8*) nounwind");
		writer.write();
		
		//type references
		HashSet<Type> definedGenerics = new HashSet<Type>();
		for (Type type : module.getReferences()) {			
			//write type declarations
			if(type != null && !moduleType.encloses(type)) {	
				if( !type.isParameterized() )
					writeTypeDeclaration(type);	
				else {
					//if unparameterized version has not been declared yet, do it
					Type unparameterizedType = type.getTypeWithoutTypeArguments();
					if( definedGenerics.add(unparameterizedType) &&		
						!unparameterizedType.equals(module.getType()))
						writeTypeDeclaration(unparameterizedType);
				}
			}
			
			//record generics
			if( type instanceof ArrayType )							
				arrays.add((ArrayType)type);
			else if( !moduleType.encloses(type) ) {				
				if( type.isFullyInstantiated() ) {//&& recordedTypes.add(type.getMangledNameWithGenerics())) ){ //||  
					//(type.isUninstantiated() && recordedTypes.add(type.getMangledName()))	)				
					//writeTypeConstants(type);
					/*
					if( type.getTypeWithoutTypeArguments().equals(Type.ARRAY) || type.getTypeWithoutTypeArguments().equals(Type.ARRAY_NULLABLE) )
						generics.add(new GenericArray(type));
					else
						generics.add(new Generic(type));
					*/
					
					addInstantiations(type);
					//generics.add(type);
				}
				
				if( type.isParameterizedIncludingOuterClasses() )
					unparameterizedGenerics.add(type.getTypeWithoutTypeArguments());
			}	
		}
		writer.write();
		
		//defines class and recursively defines inner classes  
		writeModuleDefinition(module);		
	}


	private void addInstantiations(Type type) {
		if( generics.add(type) ) {
			Type noArguments = type.getTypeWithoutTypeArguments();			
			for(Type partiallyInstantiated : noArguments.getPartiallyInstantiatedGenerics() ) {				
				try {
					Type instantiated = partiallyInstantiated.replace(noArguments.getTypeParametersIncludingOuterClasses(), type.getTypeParametersIncludingOuterClasses());
					if( instantiated.isFullyInstantiated() )
						addInstantiations(instantiated);
				} catch (InstantiationException e) {}				
			}			
		}
	}

	private String methodList(Iterable<MethodSignature> methods, boolean name)
			throws ShadowException {
		StringBuilder sb = new StringBuilder();
		for (MethodSignature method : methods) {
			//TACMethodRef methodRef = new TACMethodRef(method);
			sb.append(", ").append(methodType(method));
			if (name)
				sb.append(' ').append(name(method));
		}
		
		if( sb.length() > 0 )
			return sb.substring(2);
		else
			return sb.toString();  //no methods!
	}
	
	public void writeStringLiterals() throws ShadowException
	{
		int stringIndex = 0;
		for (String literal : stringLiterals)
		{
			byte[] data = null;
			try
			{
				data = literal.getBytes("UTF-8");
			}
			catch (UnsupportedEncodingException ex)
			{
				System.err.println(ex.getLocalizedMessage());
				System.exit(1);
			}
			StringBuilder sb = new StringBuilder(data.length);
			boolean ascii = true;
			for (byte b : data)
			{
				if (b < 0)
					ascii = false;
				if (b != '\"' && b >= 0x20 && b < 0x7f)
					sb.appendCodePoint(b);
				else
					sb.append('\\').
							append(Character.forDigit((b & 0xff) >>> 4, 16)).
							append(Character.forDigit(b & 0xf, 16));
			}
			writer.write("@_array" + stringIndex + " = private unnamed_addr " +
					"constant [" + data.length + " x " + type(Type.UBYTE) +
					"] c\"" + sb + '\"');
			writer.write("@_string" + stringIndex + " = private unnamed_addr " +
					"constant %" + raw(Type.STRING) + " { " + type(Type.CLASS) + 
					" @" + raw(Type.STRING, "_class") + ", " +
					"%" + raw(Type.STRING, "_methods") + "* @" + raw(Type.STRING, "_methods") + ", " +					
					type(new ArrayType(Type.BYTE)) + " { " + type(Type.BYTE) +
					"* getelementptr inbounds ([" +
					data.length + " x " + type(Type.BYTE) + "]* @_array" +
					stringIndex + ", i32 0, i32 0), [1 x " + type(Type.INT) +
					"] [" + typeLiteral(data.length) + "] }, " +
					typeLiteral(ascii) + " }");
			stringIndex++;
		}		
	}

	@Override
	public void endFile(TACModule module) throws ShadowException {
		Type moduleType = module.getType();
		
		//declare in a few strange methods that are not called through normal channels  
		if (moduleType instanceof ClassType ) {
			if (!moduleType.encloses(Type.CLASS))  {
				writer.write("declare noalias " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_Mallocate") + '(' +
						type(Type.CLASS) + ", %" + raw(Type.OBJECT, "_methods") +  "*)");				
				writer.write("declare noalias " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_Mallocate" +
						Type.INT.getMangledName()) + '(' + type(Type.CLASS) +
						", " + type(Type.INT) + ')');
			}
			
			if( !moduleType.encloses(Type.GENERIC_CLASS)){
				ArrayType genericClassArray = new ArrayType(Type.GENERIC_CLASS);
				ArrayType classArray = new ArrayType(Type.CLASS);				
				writer.write("declare " + type(Type.CLASS) + " @" +
						raw(Type.GENERIC_CLASS, "_MfindClass" + 
						genericClassArray.getMangledName() +						
						classArray.getMangledName()) + '(' + 
						type(Type.GENERIC_CLASS) + ", " +
						type(genericClassArray) + ", " +
						type(classArray) + ')');				
			}
			
			if( !moduleType.encloses(Type.ARRAY_CLASS)) {
				ArrayType genericClassArray = new ArrayType(Type.GENERIC_CLASS);
				writer.write("declare " + type(Type.CLASS) + " @" +
						raw(Type.ARRAY_CLASS, "_MfindClass" + 
						genericClassArray.getMangledName() +						
						Type.CLASS.getMangledName() + Type.CLASS.getMangledName()) + '(' + 
						type(Type.GENERIC_CLASS) + ", " +
						type(genericClassArray) + ", " +
						type(Type.CLASS) + ", " +
						type(Type.CLASS) +')');
			}			
			
			if( !moduleType.encloses(Type.ARRAY) )
				writer.write("declare " + type(Type.ARRAY) + " @" +
						raw(Type.ARRAY, "_Mcreate" + new ArrayType(Type.INT).
						getMangledName() + Type.OBJECT.getMangledName()) + '(' +
						type(Type.OBJECT) + ", " +
						type(new ArrayType(Type.INT)) + ", " +
						type(Type.OBJECT) + ')');
			

			if( !moduleType.encloses(Type.ARRAY_NULLABLE) )
				writer.write("declare " + type(Type.ARRAY_NULLABLE) + " @" +
						raw(Type.ARRAY_NULLABLE, "_Mcreate" + new ArrayType(Type.INT).
						getMangledName() + Type.OBJECT.getMangledName()) + '(' +
						type(Type.OBJECT) + ", " +
						type(new ArrayType(Type.INT)) + ", " +
						type(Type.OBJECT) + ')');
		}
		
		//print the method tables that are used		
		for( Type type : usedMethodTables )
			if( type instanceof ClassType && type.isUninstantiated() )				
				writer.write('@' + raw(type, "_methods") +
					" = external constant %" + raw(type, "_methods"));
		writer.write();
		
		
		//print the class constants that are used		
		for( Type type : usedClasses ) {
			if( type.isFullyInstantiated() || type.isUninstantiated() )
				writer.write(classOf(type) +
						" = external constant %" + raw(Type.CLASS)); //a lie, sometimes it's a GenericClass, but that doesn't matter
			
			if (type instanceof SingletonType) //never parameterized
				writer.write('@' + raw(type, "_instance") +
						" = external global " + type(type));
		}
		writer.write();
		
		//print only the constant fields that are used
		for( TACConstantRef constant : usedConstants )
			writer.write("@\"" + 				
				constant.getPrefixType().getMangledName() + "_M" + 
				constant.getName() + "\" = external constant " + type(constant.getType()));
		writer.write();
		
		//print only the (mostly private) methods that are called directly
		for( MethodSignature method : usedSignatures )
			writer.write("declare " + methodToString(method));
		writer.write();
		
		//one special-case hack for the Class class
		//this method is not listed as a native method in the class
		//because its second parameter has no shadow type
		if( moduleType.equals(Type.CLASS))
			writer.write("declare noalias " + type(Type.OBJECT) + " @" +
					raw(Type.CLASS, "_Mallocate") + '(' +
					type(Type.CLASS) + ", %" + raw(Type.OBJECT, "_methods") +  "*)");
		
		//print generic possibilities
		Type genericClassArray = new ArrayType(Type.GENERIC_CLASS);
		for( Type type : unparameterizedGenerics )
			writer.write("@_generics" + type.getMangledName() +	" = external constant " + type(genericClassArray));			
		writer.write();
		
		/* //seem to be covered by usedClasses
		//print array type possibilities (only those arrays that can show up as generic parameters)
		for( Array array : arrays )		
			writer.write("@\"" + array.getMangledName() + "_class\" = external constant %" + raw(Type.CLASS));
		writer.write();
		*/

		writeStringLiterals();

		if (process != null)
			try
			{
				String line;
				BufferedReader reader;
				process.getOutputStream().close();

				reader = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				while ((line = reader.readLine()) != null)
					System.out.println(line);
				reader.close();

				try
				{
					Thread.sleep(11); //why is this 11?
				} catch (InterruptedException ex)
				{
				}

				reader = new BufferedReader(new InputStreamReader(
						process.getErrorStream()));
				while ((line = reader.readLine()) != null)
					System.err.println(line);
				reader.close();

				try {
					process.waitFor();
				} catch (InterruptedException ex) { }
				int exit = process.exitValue();
				if (exit != 0)
					System.exit(exit);
			}
			catch (IOException ex)
			{
				throw new ShadowException(ex.getLocalizedMessage());
			}
	}

	@Override
	public void startMethod(TACMethod method, TACModule module) throws ShadowException
	{
		this.method = method;		
		MethodSignature signature = method.getMethod();
		if (module.getType() instanceof InterfaceType)
			return;
		SequenceType parameters = signature.getFullParameterTypes();
		tempCounter = parameters.size() + 1;
		if (signature.isNative()) {
			writer.write("declare " + methodToString(method));
			writer.indent();
		}
		else {
			writer.write("define " + methodToString(method) + " {");
			writer.indent();
			for (TACVariable local : method.getLocals())
				writer.write('%' + name(local) + " = alloca " + type(local));
			if (method.hasLandingpad())
				writer.write("%_exception = alloca { i8*, i32 }");
			boolean primitiveCreate = !signature.getOuter().
					isSimpleReference() && signature.isCreate(), first = true;
			int paramIndex = 0;
			for (TACVariable param : method.getParameters())
			{
				String symbol = '%' + Integer.toString(paramIndex++);
				if (first)
				{
					first = false;
					if (signature.isCreate())
					{
						writer.write(nextTemp() + " = bitcast " +
								typeText(Type.OBJECT, symbol) + " to %" +
								raw(param.getType()) + '*');
						symbol = temp(0);
					}
					if (primitiveCreate)
					{
						writer.write(nextTemp() +
								" = getelementptr inbounds %" +
								raw(param.getType()) + "* " + symbol +
								", i32 0, i32 2");
						writer.write(nextTemp() + " = load " +
								type(param) + "* " + temp(1));
						symbol = temp(0);
					}
				}
				writer.write("store " + typeText(param, symbol) + ", " +
						typeName(param));
			}
		}
	}

	@Override
	public void endMethod(TACMethod method, TACModule module) throws ShadowException {
		writer.outdent();
		MethodSignature signature = method.getMethod();
		if (!signature.isNative() && !(module.getType() instanceof InterfaceType))
			writer.write('}');
		writer.write();
		method = null;
	}

	@Override
	public void visit(TACVariableRef node) throws ShadowException
	{
		node.setData( '%' + name(node.getVariable()));
	}

	@Override
	public void visit(TACSingletonRef node) throws ShadowException
	{
		node.setData( '@' + raw(node.getType(), "_instance"));
	}

	//visitor pattern doesn't play nicely with inheritance
	//we have to handle TACGenericArrayRef inside TACArrayRef
	@Override
	public void visit(TACArrayRef node) throws ShadowException {
		if( node instanceof TACGenericArrayRef ) {
			writer.write(nextTemp(node) + " = extractvalue " +
				typeSymbol(node.getArray()) + ", 0");
		}
		else {					
			writer.write(nextTemp() + " = extractvalue " +
				typeSymbol(node.getArray()) + ", 0");
			writer.write(nextTemp(node) + " = getelementptr inbounds " +
				type(node.getType()) + "* " + temp(1) + ", " +
				typeSymbol(node.getTotal()));
		}
	}

	@Override
	public void visit(TACConstantRef node) throws ShadowException {
		node.setData(name(node));
		if( !module.getType().encloses(node.getPrefixType()) )
			usedConstants.add(node);			
	}

	@Override
	public void visit(TACFieldRef node) throws ShadowException
	{		
		writer.write(nextTemp(node) + " = getelementptr inbounds " +
				typeSymbol(node.getPrefix()) + ", i32 0, i32 " +
				(node.getIndex())); 
	}

	@Override
	public void visit(TACLabelRef node) throws ShadowException
	{
		nextLabel(node);
	}
	
	@Override
	public void visit(TACLongToPointer node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = inttoptr " + type(Type.ULONG) + " " +
				symbol(node.getOperand(0)) + " to " + type(node.getType())); 
	}
	
	@Override
	public void visit(TACPointerToLong node) throws ShadowException
	{		
		writer.write(nextTemp(node) + " = ptrtoint " + typeSymbol(node.getOperand(0)) +
				" to " + type(Type.ULONG));
	}
	
	@Override
	public void visit(TACCopyMemory node) throws ShadowException
	{
		TACOperand destinationNode = node.getDestination();
		TACOperand sourceNode = node.getSource();
		TACOperand size = node.getSize();
		
		String destination = typeSymbol(destinationNode);
		String source = typeSymbol(sourceNode);
		
		if( destinationNode.getType() instanceof ArrayType )
		{
			ArrayType arrayType = (ArrayType) destinationNode.getType();
			writer.write(nextTemp() + " = extractvalue " +
					destination + ", 0");
			destination = typeText(arrayType.getBaseType(), temp(0), true);			
		}
		
		if( sourceNode.getType() instanceof ArrayType )
		{
			ArrayType arrayType = (ArrayType) sourceNode.getType();
			writer.write(nextTemp() + " = extractvalue " +
					source + ", 0");
			source = typeText(arrayType.getBaseType(), temp(0), true);			
		}
		
		writer.write(nextTemp() + " = bitcast " + destination + " to i8*");
		writer.write(nextTemp() + " = bitcast " + source + " to i8*");
		writer.write("call void @llvm.memcpy.p0i8.p0i8.i32(i8* " + temp(1) + ", i8* " + temp(0) + ", i32 " + symbol(size) + ", i32 1, i1 0)");
	}

	@Override
	public void visit(TACMethodRef node) throws ShadowException
	{
		if (node.getOuterType() instanceof InterfaceType) {
			writer.write(nextTemp() + " = extractvalue " +
					type(node.getOuterType()) + " " + symbol(node.getPrefix()) + ", 0");
			writer.write(nextTemp() + " = getelementptr %" +
					raw(node.getOuterType(), "_methods") + "* " +
					//raw(node.getPrefix(), "_methods") + "* " +
					temp(1) + ", i32 0, i32 " + node.getIndex());
			writer.write(nextTemp(node) + " = load " + methodType(node) +
					"* " + temp(1));
		}
		else if (node.hasPrefix() && 
				//Devirtualization conditions below
				!node.getOuterType().isPrimitive() &&
				!node.getOuterType().getModifiers().isLocked() &&
				!node.getType().getModifiers().isLocked() &&
				!(node.getOuterType() instanceof SingletonType) &&
				!node.getType().getModifiers().isPrivate() &&
				!node.isSuper() ) {	
			writer.write(nextTemp() + " = getelementptr " +
					typeSymbol(node.getPrefix()) + ", i32 0, i32 1");
			
			writer.write(nextTemp() + " = load %" +
					raw(node.getPrefix().getType(), "_methods") + "** " + temp(1));
			if( !module.getType().encloses(node.getPrefix().getType()) )
				usedMethodTables.add(node.getPrefix().getType());
			
			writer.write(nextTemp() + " = getelementptr %" +
					raw(node.getPrefix().getType(), "_methods") + "* " +
					temp(1) + ", i32 0, i32 " + node.getIndex()); //may need to + 1 to the node.getIndex() if a parent method table is added	
			
			writer.write(nextTemp(node) + " = load " + methodType(node) +
					"* " + temp(1));
		}
		else {			
			node.setData(name(node));
			MethodSignature signature = node.getSignature();
			if( !module.getType().encloses(signature.getOuter()) && !signature.isWrapper()  )
				usedSignatures.add(signature.getSignatureWithoutTypeArguments());
		}
	}

	@Override
	public void visit(TACLiteral node) throws ShadowException
	{
		node.setData(literal(node.getValue()));
	}

	@Override
	public void visit(TACClass node) throws ShadowException {		
		node.setData(node.getClassData().getData());
	}
	
	@Override
	public void visit(TACClass.TACClassData node) throws ShadowException {
		Type type = node.getClassType();		
		node.setData(classOf(type));
	}
	
	@Override
	public void visit(TACClass.TACMethodTable node) throws ShadowException {
		Type type = node.getClassType().getTypeWithoutTypeArguments();
		node.setData("@" + raw(type, "_methods"));
		if( !module.getType().encloses(type))
			usedMethodTables.add(type);
	}	

	@Override
	public void visit(TACSequence node) throws ShadowException
	{
		String current = "undef";
		for (int i = 0; i < node.size(); i++)
		{
			writer.write(nextTemp() + " = insertvalue " + typeText(node,
					current) + ", " + typeSymbol(node.get(i)) + ", " + i);
			current = temp(0);
		}
		node.setData(current);
	}
	
	@Override
	public void visit(TACConversion node) throws ShadowException
	{
		TACOperand source = node.getOperand(0);		
		Type destType = node.getType();
		Type srcType = source.getType();
		ArrayType arrayType;
		String dimsType;
		Type genericArray;
		String baseType;
		
		switch( node.getKind()  )
		{
		case INTERFACE_TO_OBJECT:
			writer.write(nextTemp(node) + " = extractvalue " +
					typeSymbol(source) + ", 1");			
			break;
			
		case OBJECT_TO_INTERFACE:
			//operand 1 is the previously processed methods
			writer.write(nextTemp() + " = bitcast " + typeSymbol(node.getOperand(1)) +
					" to %" + raw(destType, "_methods") + '*');
			writer.write(nextTemp() + " = insertvalue " + type(destType) +
					" undef, %" + raw(destType, "_methods") + "* " + temp(1) +
					", 0");
			writer.write(nextTemp() + " = bitcast " +
					typeSymbol(source) + " to " + type(Type.OBJECT));
			
			writer.write(nextTemp(node) + " = insertvalue " + typeText(destType,
					temp(2)) + ", " + typeTemp(Type.OBJECT, 1) + ", 1");
			break;
			
		case PRIMITIVE_TO_OBJECT:
			writer.write(nextTemp() + " = call noalias " +
					type(Type.OBJECT) + " @" + raw(Type.CLASS,
					"_Mallocate") + '(' + type(Type.CLASS) +
					" " + classOf(srcType) + ", %" + 
					raw(Type.OBJECT, "_methods") + "* bitcast(%" +
					raw(srcType, "_methods") + "* @" +	raw(srcType, "_methods") +
					" to %" + raw(Type.OBJECT, "_methods") + "*)" +
					")");
			
			if( !module.getType().encloses(srcType) )
				usedMethodTables.add(srcType);
			
			writer.write(nextTemp(node) + " = bitcast " + typeText(Type.OBJECT,
					temp(1)) + " to %" + raw(srcType) + "*");		
			
			writer.write(nextTemp() + " = getelementptr inbounds %" +
					raw(srcType) + "* " + temp(1) + ", i32 0, i32 2");
			writer.write("store " + typeSymbol(source) + ", " +
					typeText(srcType, temp(0), true));
			break;
			
		case OBJECT_TO_PRIMITIVE:
			writer.write(nextTemp() + " = bitcast " + typeSymbol(source) + " to %" + raw(destType) +  "*");
			writer.write(nextTemp() + " = getelementptr inbounds %" +
					raw(destType) + "* " + temp(1) + ", i32 0, i32 2");
			writer.write(nextTemp(node) + " = load " + typeTemp(destType, 1, true));			
			break;
			
		case ARRAY_TO_OBJECT:
			arrayType = (ArrayType) srcType;
			if( arrayType.isNullable() ) {
				genericArray = Type.ARRAY_NULLABLE;				
				baseType = type(arrayType.getBaseType(), true);
			}
			else {
				genericArray = Type.ARRAY;
				baseType = type(arrayType.getBaseType());
			}
			TACClass arrayClass = (TACClass)node.getOperand(1);
			
			ArrayType intArray = new ArrayType(Type.INT, false);
			dimsType = " [" + arrayType.getDimensions() + " x " +
					type(Type.INT) + ']';
			writer.write(nextTemp() + " = extractvalue " + typeSymbol(source) + ", 1");				
			writer.write(nextTemp() + " = call " + type(Type.OBJECT) +
					" @" + raw(Type.CLASS,
					"_Mallocate" + Type.INT.getMangledName()) + '(' +
					typeText(Type.CLASS, classOf(Type.INT)) + ", " +
					typeLiteral(arrayType.getDimensions()) + ')');
			writer.write(nextTemp() + " = bitcast " + typeText(Type.OBJECT,
					temp(1)) + " to" + dimsType + '*');
			writer.write("store" + dimsType + ' ' + temp(2) + ',' +
					dimsType + "* " + temp(0));
			writer.write(nextTemp() + " = getelementptr inbounds" +
					dimsType + "* " + temp(1) + ", i32 0, i32 0");
			writer.write(nextTemp() + " = insertvalue " + type(intArray) +
					" { " + type(Type.INT) + "* null, [1 x " +
					type(Type.INT) + "] [" +
					typeLiteral(arrayType.getDimensions()) + "] }, " +
					typeTemp(Type.INT, 1, true) + ", 0");
			writer.write(nextTemp() + " = extractvalue " + typeSymbol(source) + ", 0");
			writer.write(nextTemp() + " = bitcast " +					
					combine(baseType, temp(1), true) + " to " +
					type(Type.OBJECT));
			writer.write(nextTemp() + " = call noalias " +
					type(Type.OBJECT) + " @" + raw(Type.CLASS,
					"_Mallocate") + '(' + typeText(Type.CLASS,
					symbol(arrayClass.getClassData())) + ", %" +
					raw(Type.OBJECT, "_methods") + "* bitcast(%" + raw(genericArray, "_methods") + "* " +  symbol(arrayClass.getMethodTable()) + " to %" + raw(Type.OBJECT, "_methods") + "*)" + 
					')');
							
			writer.write(nextTemp(node) + " = call " + type(genericArray) + " @" +
					raw(genericArray, "_Mcreate" + new ArrayType(Type.INT).
					getMangledName() + Type.OBJECT.getMangledName()) + '(' +
					typeTemp(Type.OBJECT, 1) + ", " +						
					typeTemp(new ArrayType(Type.INT), 4) + ", " +
					typeTemp(Type.OBJECT, 2) + ')');
			break;
			
		case OBJECT_TO_ARRAY:
			String srcName = symbol(source);
			arrayType = (ArrayType)destType;
			
			if( arrayType.isNullable()  ) {
				genericArray = Type.ARRAY_NULLABLE;			
				baseType = type(arrayType.getBaseType(), true) + '*';
			}
			else {			
				genericArray = Type.ARRAY;
				baseType = type(arrayType.getBaseType()) + '*';
			}
			
			if (!srcType.equals(genericArray))
			{
				writer.write(nextTemp() + " = bitcast " + typeSymbol(source) + " to " + type(genericArray));
				srcType = genericArray;
				srcName = temp(0);
			}			
			
			dimsType = " [" + arrayType.getDimensions() + " x " +
							type(Type.INT) + ']';
			writer.write(nextTemp() + " = getelementptr inbounds " +
							typeText(srcType, srcName) + ", i32 0, i32 2");
			writer.write(nextTemp() + " = load " + type(Type.OBJECT) +
					"* " + temp(1));
			writer.write(nextTemp() + " = bitcast " + typeTemp(Type.OBJECT,
					1) + " to " + baseType);
			writer.write(nextTemp() + " = insertvalue " + type(destType) +
					" undef, " + baseType + ' ' + temp(1) + ", 0");
			writer.write(nextTemp() + " = getelementptr inbounds " +
					typeText(srcType, srcName) + ", i32 0, i32 3, i32 0");
			writer.write(nextTemp() + " = load " + type(Type.INT) + "** " +
					temp(1));
			writer.write(nextTemp() + " = bitcast " + type(Type.INT) +
					"* " + temp(1) + " to" + dimsType + '*');
			writer.write(nextTemp() + " = load" + dimsType + "* " +
					temp(1));
			writer.write(nextTemp(node) + " = insertvalue " + typeTemp(destType,
					5) + ',' + dimsType + ' ' + temp(1) + ", 1");
			break;
		case SEQUENCE_TO_SEQUENCE:			
			String current = "undef";
			int index = 0;			
			for (int i = 1; i < node.getNumOperands(); i ++) //first operand is source
			{	
				writer.write(nextTemp() + " = insertvalue " + typeText(node,current) + ", " +
						typeSymbol(node.getOperand(i)) + ", " + index);
				current = temp(0);
				index++;
			}
			node.setData(current);			
			break;
		case SEQUENCE_TO_OBJECT:
			node.setData(symbol(node.getOperand(1)));
			break;
		case OBJECT_TO_SEQUENCE:			
			writer.write(nextTemp(node) + " = insertvalue " + type(node)
					+ " undef, " + typeSymbol(node.getOperand(1)) + ", 0");						
			break;
		case NULL_TO_ARRAY:
			writer.write(nextTemp(node) + " = insertvalue " + type(node)
					+ " zeroinitializer, " + type(((ArrayType)node.getType()).getBaseType()) + "* null, 0");
			break;
		case NULL_TO_INTERFACE:
			writer.write(nextTemp(node) + " = insertvalue " + type(node)
					+ " zeroinitializer, " + type(Type.OBJECT) + "* null, 1");
			break;
		}
	}
	
	@Override
	public void visit(TACSequenceElement node ) throws ShadowException
	{	
		writer.write(nextTemp(node) + " = extractvalue " +
				typeSymbol(node.getOperand(0)) + ", " + node.getIndex());		
	}
	
	@Override
	public void visit(TACCast node) throws ShadowException
	{ 
		Type srcType = node.getOperand().getType(), destType = node.getType();
		
		if (destType.equals(srcType) || destType == Type.NULL )
		{
			node.setData(symbol(node.getOperand()));
			return;
		}
		else if( srcType == Type.NULL )
		{
			if( destType instanceof ArrayType || destType instanceof InterfaceType )
				node.setData("zeroinitializer");  //Shadow arrays are not pointers, they're structs with pointers and lengths
										//change back to undef?
			else
				node.setData("null");
			
			return;
		}	
		
		//all substantive conversions have been taken care of with TACConversion
		//but conversions of primitive types and pointer casts are swept up here 
		
		String instruction;
		int srcWidth = Type.getWidth(node.getOperand()),
				destWidth = Type.getWidth(node);
		
		String srcTypeName = type(srcType);
		String destTypeName = type(destType);
		
		if( srcType.isPrimitive() && destType.isPrimitive() )
		{
			if( srcType.isFloating() )
			{
				if( destType.isFloating() )
					instruction = srcWidth > destWidth ? "fptrunc" : (srcWidth < destWidth ? "fpext" : "bitcast");
				else if( destType.isSigned() )
					instruction = "fptosi";
				else
					instruction = "fptoui";
			}
			else if( srcType.isSigned() )
			{
				if( destType.isFloating() )
					instruction = "sitofp";
				else if( destType.isSigned() )
					instruction = srcWidth > destWidth ? "trunc" : (srcWidth < destWidth ? "sext" : "bitcast");
				else
					instruction = srcWidth > destWidth ? "trunc" : (srcWidth < destWidth ? "zext" : "bitcast");
			}
			else if( srcType.isUnsigned() )
			{
				if( destType.isFloating() )
					instruction = "uitofp";
				else
					instruction = srcWidth > destWidth ? "trunc" : (srcWidth < destWidth ? "zext" : "bitcast");
			}
			else
				instruction = "bitcast"; //should never happen
		}
		else
		{
			instruction = "bitcast";
			
			//primitive types are in their object forms
			if( srcType.isPrimitive() )
				srcTypeName = "%\"" + srcType.getMangledName() + "\"*";
			
			if( destType.isPrimitive() )
				destTypeName = "%\"" + destType.getMangledName() + "\"*";			
		}
		
		writer.write(nextTemp(node) + " = " + instruction + ' ' +
				srcTypeName + ' ' + symbol(node.getOperand()) + " to " + destTypeName);
	}	

	@Override
	public void visit(TACNewObject node) throws ShadowException
	{				
		Type type = node.getClassType();	
		
		TACOperand _class = node.getClassData();
		TACOperand methods = node.getMethodTable();	
		
		//this is pretty ugly, but if we retrieved a generic type parameter's method table, it'll be stored as an Object*, not an Object_methods*
		if( type instanceof TypeParameter )
			writer.write(nextTemp() + " = bitcast " + type(Type.OBJECT) + " " +  symbol(methods) +  " to %"  + raw(Type.OBJECT, "_methods") + "*");
		//any other method table will be of the correct type but needs cast to Object_methods* for compatibility with allocate()
		else 
			writer.write(nextTemp() + " = bitcast %" + raw(type.getTypeWithoutTypeArguments(), "_methods") + "* " +  symbol(methods) +  " to %"  + raw(Type.OBJECT, "_methods") + "*");
		writer.write(nextTemp(node) + " = call noalias " + type(Type.OBJECT) +
				" @" + raw(Type.CLASS, "_Mallocate") + '(' + type(Type.CLASS) +
				" " + symbol(_class) + ", %" + raw(Type.OBJECT, "_methods") + "* " + temp(1) +
				" )");
	}
	 

	@Override
	public void visit(TACNewArray node) throws ShadowException
	{
		Type baseType = node.getType().getBaseType();		
		String allocationClass = typeSymbol(node.getBaseClass());		
		String allocationMethod = "_Mallocate";	
		
		writer.write(nextTemp() + " = call noalias " + type(Type.OBJECT) +
				" @" + raw(Type.CLASS, allocationMethod + Type.INT.getMangledName()) +
			 	'(' + allocationClass + ", " +
				typeSymbol(node.getTotalSize()) + ')');
		writer.write(nextTemp() + " = bitcast " + type(Type.OBJECT) + ' ' +
				temp(1) + " to " + type(baseType) + '*');
		writer.write(nextTemp() + " = insertvalue " + type(node) +
				" undef, " + type(baseType) + "* " + temp(1) + ", 0");
		for (int i = 0; i < node.getDimensions(); i++)
			writer.write(nextTemp(node) + " = insertvalue " + type(node) +
					' ' + temp(1) + ", " + typeSymbol(node.getDimension(i)) +
					", 1, " + i);
	}

	@Override
	public void visit(TACLength node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = extractvalue " +
				typeSymbol(node.getArray()) + ", 1, " + node.getDimension());
	}

	@Override
	public void visit(TACUnary node) throws ShadowException
	{
		switch (node.getOperation())
		{
			case "-":
				if( node.getType().isFloating() )
					visitUnary(node, "fsub", "-0.0");
				else
					visitUnary(node, "sub", "0");
				break;
			case "~":
			case "!":
				visitUnary(node, "xor", "-1");
				break;
		}
	}
	private void visitUnary(TACUnary node, String instruction, String first)
			throws ShadowException
	{
		writer.write(nextTemp(node) + " = " + instruction + ' ' + typeText(node,
				first) + ", " + symbol(node.getOperand()));
	}	
	
	@Override
	public void visit(TACBinary node) throws ShadowException
	{	
		switch (node.getOperation().toString())
		{
			case "+":
				visitUnsignedOperation(node, "add");
				break;
			case "-":
				visitUnsignedOperation(node, "sub");
				break;
			case "*":
				visitUnsignedOperation(node, "mul");
				break;
			case "/":
				visitSignedOperation(node, "div");
				break;
			case "%":
				visitSignedOperation(node, "rem");
				break;

			case "or":
			case "|":
				visitNormalOperation(node, "or");
				break;
			case "xor":
			case "^":
				visitNormalOperation(node, "xor");
				break;
			case "and":
			case "&":
				visitNormalOperation(node, "and");
				break;

			case "<<":
				visitNormalOperation(node, "shl");
				break;
			case ">>":
				visitShiftOperation(node, "shr");
				break;
			case "<<<":
				visitRotateLeft(node);
				break;
			case ">>>":
				visitRotateRight(node);
				break;

			case "==":
				visitEqualityOperation(node, "eq");
				break;
			case "!=":
				visitEqualityOperation(node, "ne");
				break;
			case "<":
				visitRelationalOperation(node, "lt");
				break;
			case ">":
				visitRelationalOperation(node, "gt");
				break;
			case "<=":
				visitRelationalOperation(node, "le");
				break;
			case ">=":
				visitRelationalOperation(node, "ge");
				break;
			default:
				throw new UnsupportedOperationException(
						"Binary operation " + node.getOperation() + " not supported on current types" );
		}		
	}
	private void visitUnsignedOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "", "", "f", instruction);
	}
	private void visitSignedOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "s", "u", "f", instruction);
	}
	private void visitNormalOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "", "", "", instruction);
	}
	private void visitShiftOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "a", "l", "", instruction);
	}
	private void visitEqualityOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "icmp ", "icmp ", "fcmp o", instruction);
	}
	private void visitRelationalOperation(TACBinary node, String instruction)
			throws ShadowException
	{
		visitOperation(node, "icmp s", "icmp u", "fcmp o", instruction);
	}
	private void visitOperation(TACBinary node, String signed,
			String unsigned, String floatingPoint, String instruction)
			throws ShadowException
	{
		StringBuilder sb = new StringBuilder(nextTemp(node)).append(" = ");
		Type type = node.getFirst().getType();
		if (type.isIntegral() || !type.isPrimitive())
			if (type.isSigned())
				sb.append(signed);
			else
				sb.append(unsigned);
		else
			sb.append(floatingPoint);
		writer.write(sb.append(instruction).append(' ').append(typeSymbol(type,
				node.getFirst())).append(", ").append(symbol(node.getSecond())).
				toString());
	}
	private void visitRotateLeft(TACBinary node) throws ShadowException
	{
		visitRotate(node, "shl", "lshr");
	}
	private void visitRotateRight(TACBinary node) throws ShadowException
	{
		visitRotate(node, "lshr", "shl");
	}
	private void visitRotate(TACBinary node, String dir, String otherDir)
			throws ShadowException
	{
		String type = type(node), _type_ = ' ' + type + ' ',
				first = symbol(node.getFirst()),
				second = symbol(node.getSecond());
		writer.write(nextTemp() + " = " + dir + _type_ + first + ", " + second);
		writer.write(nextTemp() + " = sub" + _type_ + "mul (" + type +
				" ptrtoint (" + type + "* getelementptr (" + type +
				"* null, i32 1) to " + type + ")," + _type_ + "8), " + second);
		writer.write(nextTemp() + " = " + otherDir + _type_ + first + ", " +
				temp(1));
		writer.write(nextTemp(node) + " = or" + _type_ + temp(1) + ", " +
				temp(3));
	}
	
	@Override
	public void visit(TACConstructGeneric node) throws ShadowException
	{ 
		int size = node.getNumOperands();
		Type genericClassArray = new ArrayType(Type.GENERIC_CLASS);
		Type classArray = new ArrayType(Type.CLASS);
				
		writer.write(nextTemp() + " = call " + 
				type(Type.OBJECT) + " @" +
				raw(Type.CLASS, "_Mallocate" + 
				Type.INT.getMangledName()) + '(' +
				type(Type.CLASS) + " " + classOf(Type.CLASS) + ", " +
				type(Type.INT) + " " + size + ')');
		
		String start = nextTemp();
		writer.write(start + " = bitcast " + 
				type(Type.OBJECT) + " " + temp(1) + " to " +
				type(Type.CLASS) + "*");
				
		
		for( int i = 0; i < size; i++ )
		{
			writer.write(nextTemp() + " = getelementptr " + type(Type.CLASS) + "* " + start + ", i32 " + i);  			
			writer.write("store " + type(Type.CLASS) + " " + symbol(node.getOperand(i)) + ", " + type(Type.CLASS) + "* " + temp(0));			
		}		
		
		writer.write(nextTemp() + " = insertvalue " + type(new ArrayType(Type.CLASS)) + " undef, " + type(Type.CLASS) + "* " + start + ", 0");
		writer.write(nextTemp() + " = insertvalue " + type(new ArrayType(Type.CLASS)) + " " + temp(1) + ", " + typeLiteral(size) + ", 1, 0" );
		writer.write(nextTemp() + " = load " + type(genericClassArray) + "*  @_generics" + node.getClassType().getMangledName());
		writer.write(nextTemp(node) + " = call " + 
				type(Type.CLASS) + " @" +
				raw(Type.GENERIC_CLASS, "_MfindClass" + 
				genericClassArray.getMangledName() + 
				classArray.getMangledName()) + '(' +
				type(Type.GENERIC_CLASS) + " null, " +
				type(genericClassArray) + " " +  temp(1) + ", " +
				type(classArray) + " " + temp(2) + ')');
		writer.write(nextTemp() + " = bitcast " + 
				type(Type.CLASS) + "* " + start + " to i8*");
		writer.write("call void @free(i8* " + temp(0) + ")");		
	}

	@Override
	public void visit(TACNot node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = xor " +
				typeSymbol(node.getOperand()) + ", true");
	}
	
	@Override
	public void visit(TACSame node) throws ShadowException
	{
		Type type = node.getOperand(0).getType();
		//String op = type.isIntegral() || !type.isPrimitive() ?
		//		"icmp eq " : "fcmp oeq ";
		
		if( type instanceof ArrayType )
		{
			ArrayType arrayType = (ArrayType)type;
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getOperand(0)) + ", 0");
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getOperand(1)) + ", 0");
			writer.write(nextTemp(node) + " = icmp eq" + type(arrayType.getBaseType())
					+ "* " + temp(2) + ", " + temp(1));			
		}
		else if( type instanceof InterfaceType )
		{			
			
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getOperand(0)) + ", 1");
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getOperand(0)) + ", 1");				
			writer.write(nextTemp(node) + " = icmp eq" + type(Type.OBJECT)
					+ temp(2) + ", " + temp(1));		
		}
		else
		{		
			String op = type.isFloating() ?
					"fcmp oeq " : "icmp eq " ;
	
			writer.write(nextTemp(node) + " = " + op + typeSymbol(
					node.getOperand(0)) + ", " + symbol(node.getOperand(1)));
		}
	}

	@Override
	public void visit(TACLoad node) throws ShadowException
	{
		TACReference reference = node.getReference();		
		
		if( reference instanceof TACGenericArrayRef ) {
			TACGenericArrayRef genericRef = (TACGenericArrayRef) reference;			
			writer.write(nextTemp(node) + " = call " + type(Type.OBJECT) + " @__arrayLoad(" + typeSymbol(Type.OBJECT, genericRef, true) + ", " + 
					typeSymbol(genericRef.getTotal()) + ", " +
					typeSymbol(genericRef.getClassData().getClassData()) + ", " +
					typeSymbol(Type.OBJECT, genericRef.getClassData().getMethodTable()) + ")");
		}
		else
			writer.write(nextTemp(node) + " = load " +
					typeSymbol(reference.getGetType(), reference, true));
	}
	
	@Override
	public void visit(TACStore node) throws ShadowException {
		TACReference reference = node.getReference();
		
		if (reference instanceof TACSequenceRef) {
			//TODO: What about a sequence filled with TACGenericArrayRefs?
			TACSequenceRef seq = (TACSequenceRef)reference;
			String name = typeSymbol(node.getValue());
			for (int i = 0; i < seq.size(); i++)
			{
				writer.write(nextTemp() + " = extractvalue " + name + ", " + i);
				writer.write("store " + type(seq.get(i)) + ' ' + temp(0) +
						", " + typeSymbol(seq.get(i), true));
			}
		}
		else if( reference instanceof TACGenericArrayRef ) {
			TACGenericArrayRef genericRef = (TACGenericArrayRef) reference;			
						
			writer.write("call void @__arrayStore(" + typeSymbol(Type.OBJECT, genericRef, true) + ", " + 
					typeSymbol(genericRef.getTotal()) + ", " + typeSymbol(node.getValue()) + ", " +
					typeSymbol(genericRef.getClassData().getClassData()) + ")");
			
			//this code is based on Array.index(int, Object)
			//labels have an extra % at the front
			/*
			
			String checkArrayLabel = nextLabel().substring(1);
			String objectLabel = nextLabel().substring(1);
			String primitiveLabel = nextLabel().substring(1);
			String arrayLabel = nextLabel().substring(1);
			String doneLabel = nextLabel().substring(1);
			
			String arrayAsObj = nextTemp();
			writer.write(arrayAsObj + " = extractvalue " +
					typeSymbol(genericRef.getArray()) + ", 0");
		
			writer.write(nextTemp() + " = and i32 " + flag + ", " + PRIMITIVE); //primitive flag (2)
			writer.write(nextTemp() + " = icmp eq i32 " + temp(1) + ", 0"); //not primitive
			writer.write("br i1 " + temp(0) + ", label %" + checkArrayLabel + ", label %" + primitiveLabel);
			
			writer.writeLeft(checkArrayLabel + ":");			
			writer.write(nextTemp() + " = and i32 " + flag + ", " + ARRAY); //array flag (8)
			writer.write(nextTemp() + " = icmp eq i32 " + temp(1) + ", 0"); //not array
			writer.write("br i1 " + temp(0) + ", label %" + objectLabel + ", label %" + arrayLabel);
			
			writer.writeLeft(objectLabel + ":");			
			writer.write(nextTemp() + " = getelementptr inbounds " +
					type(genericRef.getType()) + "* " + arrayAsObj + ", " +
					typeSymbol(genericRef.getTotal()));
			writer.write("store " + typeSymbol(node.getValue()) + ", " +
					typeText(genericRef.getSetType(), temp(0), true));
			writer.write("br label %" + doneLabel);
			
			writer.writeLeft(primitiveLabel + ":");
			writer.write(nextTemp() + " = call i32 " +
					name(Type.CLASS.getMatchingMethod("width", new SequenceType())) + 
					'(' + typeSymbol(genericRef.getClassData()) + ')');
			writer.write(nextTemp() + " = mul " + typeSymbol(genericRef.getTotal()) + ", " + temp(1));			
			writer.write(nextTemp() + " = bitcast " + typeText(Type.OBJECT, arrayAsObj, true) + " to i8*");
			writer.write(nextTemp() + " = getelementptr inbounds i8* " + temp(1) + ", i32 " + temp(2));
			writer.write(nextTemp() + " = getelementptr inbounds " + typeSymbol(node.getValue()) + ", i32 1");
			writer.write(nextTemp() + " = bitcast " + typeTemp(Type.OBJECT, 1) + " to i8*");
			writer.write("call void @llvm.memcpy.p0i8.p0i8.i32(i8* " + temp(2) + ", i8* " + temp(0) + ", i32 " + temp(5) + ", i32 1, i1 0)");
			writer.write("br label %" + doneLabel);
				
			writer.writeLeft(arrayLabel + ":");
			writer.write(nextTemp() + " = call i32 " +
					name(Type.CLASS.getMatchingMethod("width", new SequenceType())) + 
					'(' + typeSymbol(genericRef.getClassData()) + ')');
			writer.write(nextTemp() + " = mul " + typeSymbol(genericRef.getTotal()) + ", " + temp(1));
			writer.write(nextTemp() + " = bitcast " + typeText(Type.OBJECT, arrayAsObj, true) + " to i8*");
			writer.write(nextTemp() + " = getelementptr inbounds i8* " + temp(1) + ", i32 " + temp(2));			
			writer.write(nextTemp() + " = bitcast " + typeSymbol(node.getValue()) + " to " + type(Type.ARRAY));
			writer.write(nextTemp() + " = getelementptr inbounds " + typeTemp(Type.ARRAY, 1) + ", i32 0, i32 2");
			writer.write(nextTemp() + " = load " + typeTemp(Type.OBJECT, 1, true));			
			writer.write(nextTemp() + " = bitcast i8* " + temp(4) + " to " + type(Type.OBJECT) + "*");
			writer.write("store " + typeTemp(Type.OBJECT, 1) + ", " + typeTemp(Type.OBJECT, 0, true));			
			writer.write(nextTemp() + " = getelementptr inbounds " + typeTemp(Type.ARRAY, 4) + ", i32 0, i32 3, i32 1, i32 0");
			writer.write(nextTemp() + " = load i32* " + temp(1));
			writer.write(nextTemp() + " = getelementptr inbounds " + typeTemp(Type.ARRAY, 6) + ", i32 0, i32 3, i32 0");
			writer.write(nextTemp() + " = load i32** " + temp(1));			
			writer.write(nextTemp() + " = getelementptr inbounds " + typeTemp(Type.OBJECT, 5, true) + ", i32 1");
			writer.write(nextTemp() + " = bitcast " + typeTemp(Type.OBJECT, 1, true) + " to i32*");			
			writer.write("call void @llvm.memcpy.p0i32.p0i32.i32(i32* " + temp(0) + ", i32* " + temp(2) + ", i32 " + temp(4) + ", i32 0, i1 0)");
			writer.write("br label %" + doneLabel);
			
			writer.writeLeft(doneLabel + ":");	
			
							*/
		}
		else {		
			writer.write("store " + typeSymbol(node.getValue()) + ", " +
				typeSymbol(reference.getSetType(), reference, true));
		}
	}

	@Override
	public void visit(TACPhi node) throws ShadowException
	{
		TACPhiRef phi = node.getRef();
		StringBuilder sb = new StringBuilder(nextTemp(phi)).
				append(" = phi i8*");
		for (int i = 0; i < phi.getSize(); i++)
			sb.append(" [ blockaddress(").append(name(method)).append(", ").
					append(symbol(phi.getValue(i))).append("), ").
					append(symbol(phi.getLabel(i))).append(" ],");
		writer.write(sb.deleteCharAt(sb.length() - 1).toString());
	}

	@Override
	public void visit(TACBranch node) throws ShadowException
	{
		if (node.isConditional())
			writer.write("br " + typeSymbol(node.getCondition()) + ", label " +
					symbol(node.getTrueLabel()) + ", label " +
					symbol(node.getFalseLabel()));
		else if (node.isDirect())
			writer.write("br label " + symbol(node.getLabel()));
		else if (node.isIndirect())
		{
			StringBuilder sb = new StringBuilder("indirectbr i8* ").
					append(symbol(node.getOperand())).append(", [ ");
			TACDestination dest = node.getDestination();
			for (int i = 0; i < dest.getNumPossibilities(); i++)
				sb.append("label ").append(symbol(dest.getPossibility(i))).
						append(", ");
			writer.write(sb.delete(sb.length() - 2, sb.length()).append(" ]").
					toString());
		}
	}

	@Override
	public void visit(TACLabel node) throws ShadowException
	{
		writer.writeLeft(name(node) + ':');
	}

	@Override
	public void visit(TACCall node) throws ShadowException {
		TACMethodRef method = node.getMethod();
		StringBuilder sb = new StringBuilder(node.getBlock().hasLandingpad() ?
				"invoke" : "call").append(' ').
				append(methodType(method)).append(' ').
				append(symbol(method)).append('(');
		boolean first = true;
		for (TACOperand param : node.getParameters())
			if (first) {
				first = false;			
				sb.append(typeSymbol(param));
			}
			else
				sb.append(", ").append(typeSymbol(param));
		if (!method.getReturnTypes().isEmpty())
			sb.insert(0, nextTemp(node) + " = ");
		writer.write(sb.append(')').toString());
		if (node.getBlock().hasLandingpad()) {
			TACLabelRef label = new TACLabelRef();
			visit(label);
			writer.indent(2);
			writer.write(" to label " + symbol(label) + " unwind label " +
					symbol(node.getBlock().getLandingpad()));
			visit(label.new TACLabel());
			writer.outdent(2);
		}
	}

	@Override
	public void visit(TACReturn node) throws ShadowException
	{
		if (method.getMethod().isCreate())
		{	// FIXME: hack
			writer.write(nextTemp() + " = bitcast " + typeText(Type.OBJECT,
					"%0") + " to %" + raw(node.getReturnValue().getType()) +
					'*');
			writer.write("ret %" + raw(node.getReturnValue().getType()) +
					"* " + temp(0)); // FIXME: hack
		}
		else if (node.hasReturnValue())
			writer.write("ret " + typeSymbol(node.getReturnValue()));
		else
			writer.write("ret void");
	}

	@Override
	public void visit(TACThrow node) throws ShadowException
	{
		writer.write((node.getBlock().hasLandingpad() ?
				"invoke" : "call") + " void @__shadow_throw(" +
				typeSymbol(node.getException()) + ") noreturn");
		if (node.getBlock().hasLandingpad())
		{
			TACLabelRef label = new TACLabelRef();
			visit(label);
			writer.indent(2);
			writer.write(" to label " + symbol(label) + " unwind label " +
					symbol(node.getBlock().getLandingpad()));
			visit(label.new TACLabel());
			writer.outdent(2);
		}
		writer.write("unreachable");
	}	
	
	@Override
	public void visit(TACTypeId node) throws ShadowException
	{
		if (node.getOperand() instanceof TACUnwind)
			writer.write(nextTemp(node) + " = extractvalue { i8*, i32 } " +
					symbol(node.getOperand()) + ", 1");
		else
		{
			writer.write(nextTemp() + " = bitcast " +
					typeSymbol(node.getOperand()) + " to i8*");
			writer.write(nextTemp(node) + " = tail call i32 " +
					"@llvm.eh.typeid.for(i8* " + temp(1) + ") nounwind");
		}
	}	

	@Override
	public void visit(TACLandingpad node) throws ShadowException
	{
		writer.write(nextTemp() + " = landingpad { i8*, i32 } " +
				"personality i32 (...)* @__shadow_personality_v0");
		writer.indent(2);
		if (node.getBlock().hasCleanup())
			writer.write("cleanup");
		for (TACBlock block = node.getBlock(); block != null;
				block = block.getParent())
			for (int i = 0; i < block.getNumCatches(); i++)
				writer.write("catch " + type(Type.CLASS) + ' ' +
						classOf(block.getCatchNode(i).getType()));
		writer.outdent(2);
		writer.write("store { i8*, i32 } " + temp(0) +
				", { i8*, i32 }* %_exception");
	}

	@Override
	public void visit(TACUnwind node) throws ShadowException
	{
		writer.write(nextTemp(node) + " = load { i8*, i32 }* %_exception");
		String type = nextTemp();
		writer.write(type + " = extractvalue { i8*, i32 } " + temp(1) + ", 0");
		TACLabelRef label = node.getBlock().getLandingpad();
		for (int i = 0; i < node.getBlock().getNumCatches(); i++)
		{
			TACCatch catchNode = node.getBlock().getCatchNode(i);
			writer.write(nextTemp() + " = tail call i32 @llvm.eh.typeid.for(" +
					"i8* bitcast (" + type(Type.CLASS) + ' ' +
					classOf(catchNode.getType()) + " to i8*)) nounwind");
			writer.write(nextTemp() + " = icmp ne i32 " + type + ", " +
					temp(1));
			label = new TACLabelRef();
			visit(label);
			writer.write("br i1 " + temp(0) + ", label " + symbol(label) +
					", label " + symbol(node.getBlock().getCatch(i)));
			visit(label.new TACLabel());
		}
		// TODO: cleanup???
	}

	@Override
	public void visit(TACCatch node) throws ShadowException
	{
		writer.write(nextTemp() + " = getelementptr inbounds { i8*, i32 }* " +
				"%_exception, i32 0, i32 0");
		writer.write(nextTemp() + " = load i8** " + temp(1));
		writer.write(nextTemp() + " = call " + type(Type.EXCEPTION) +
				" @__shadow_catch(i8* " + temp(1) + ") nounwind");
		writer.write(nextTemp(node) + " = bitcast " + type(Type.EXCEPTION) +
				' ' + temp(1) + " to " + type(node.getType()));
	}
	
	@Override
	public void visit(TACResume node) throws ShadowException
	{
		writer.write(nextTemp() + " = load { i8*, i32 }* %_exception");
		writer.write("resume { i8*, i32 } " + temp(0));
	}

	private String classOf(Type type) {
		//module is null when building generics
		if( module != null && !module.getType().encloses(type) )
			usedClasses.add(type);		
		
		//may need something for ArrayType, but maybe not
		if( type.isFullyInstantiated() )
			return '@' + withGenerics(type, "_class");
		else
			return '@' + raw(type, "_class");
	}
	
	private static String methodType(TACMethodRef method)
	{
		return methodToString(method, false, true) + '*';
	}
	
	private static String methodType(MethodSignature method)
	{
		return methodToString(method, false, true) + '*';
	}
	
	private static String methodToString(TACMethod method)
	{
		return methodToString(method.getMethod(), true, true);
	}
	
	private static String methodToString(MethodSignature signature)
	{
		return methodToString(signature, true, true);
	}
	
	private static String methodToString(MethodSignature signature, boolean name, boolean parameters)
	{
		StringBuilder sb = new StringBuilder();
		if (name && (/*signature.getModifiers().isPrivate() ||*/ signature.isWrapper()))
			sb.append("private ");
		boolean primitiveOrInterfaceCreate = !signature.getOuter().isSimpleReference() &&
				signature.isCreate();
		
		if( primitiveOrInterfaceCreate ) {			
			if( signature.getOuter() instanceof InterfaceType ) //in situations were an interface contains a create
				sb.append(type(Type.OBJECT));
			else
				sb.append(type(signature.getOuter(), true)); //the nullable looks odd, but it gets the Object version of the primitive
		}		
		/*if (primitiveCreate)
			sb.append('%').append(raw(signature.getOuter())).append('*');
		*/
		else
		{
			SequenceType returnTypes = signature.getFullReturnTypes();
			if( returnTypes.size() == 0 )
				sb.append("void");
			else if (returnTypes.size() == 1 )			
				sb.append(type(returnTypes.get(0)));
			else
				sb.append(type(returnTypes));
		}
		sb.append(' ');
		if (name)
			sb.append(name(signature));
		if (parameters)
		{
			sb.append('(');
			boolean first = true;
			SequenceType parameterTypes = signature.getFullParameterTypes();
			
			for (int i = 0; i < parameterTypes.size(); i++) {
				if (first) {
					first = false;
					sb.append(type(parameterTypes.get(i)));
					if (name && signature.isCreate())
						sb.append(" returned");
				}
				else
					sb.append(", ").append(type(parameterTypes.get(i)));
			}
			sb.append(')');
		}
		return sb.toString();
		
	}
	
	private static String methodToString(TACMethodRef method, boolean name,
			boolean parameters) {
		
		return methodToString(method.getSignature(), name, parameters);
		/*
		StringBuilder sb = new StringBuilder();
		if (name && (method.getModifiers().isPrivate() || method.isWrapper()))
			sb.append("private ");
		boolean primitiveOrInterfaceCreate = !method.getOuterType().isSimpleReference() &&
				method.isCreate();
		if( primitiveOrInterfaceCreate ) {			
			if( method.getOuterType() instanceof InterfaceType ) //in situations were an interface contains a create
				sb.append(type(Type.OBJECT));
			else
				sb.append(type(method.getOuterType(), true)); //the nullable looks odd, but it gets the Object version of the primitive
		}
		else {			
			if (method.isVoid())
				sb.append("void");
			else if (method.isSingle())
				sb.append(type(method.getSingleReturnType()));
			else
				sb.append(type(method.getSequenceReturnTypes()));
		}
		sb.append(' ');
		if (name)
			sb.append(name(method));
		if (parameters)
		{
			sb.append('(');
			boolean first = true;
			for (int i = 0; i < method.getParameterCount(); i++)
			{
				if (first)
				{
					first = false;
					if (primitiveOrInterfaceCreate)
						sb.append('%').append(raw(method.getParameterType(0))).
								append('*');
					else
						sb.append(type(method.getParameterType(i)));
					if (name && method.isCreate())
						sb.append(" returned");
				}
				else
					sb.append(", ").append(type(method.getParameterType(i)));
			}
			sb.append(')');
		}
		return sb.toString();
		*/
	}

	private static String sizeof(String type)
	{
		return "ptrtoint (" + type + " getelementptr (" + type +
				" null, i32 1) to i32)";
	}

	private static String type(ModifiedType type)
	{
		return type(type.getType(), type.getModifiers().isNullable());
	}	
	
	protected static String type(Type type) {
		return type(type, false);
	}
	
	protected static String type(Type type, boolean nullable) {
		if (type == null)
			throw new NullPointerException();
		if (type instanceof ArrayType)
			return type((ArrayType)type);
		if (type instanceof SequenceType)
			return type((SequenceType)type);
		if (type instanceof ClassType)
			return type((ClassType)type, nullable);
		if (type instanceof InterfaceType)
			return type((InterfaceType)type);
		if (type instanceof TypeParameter)
			return type((TypeParameter)type);
		throw new IllegalArgumentException("Unknown type.");
	}
	protected static String type(ArrayType type)
	{
		return "{ " + type(type.getBaseType()) + "*, [" + type.getDimensions() +
				" x " + type(Type.INT) + "] }";
	}
	private static String type(SequenceType type)
	{
		if (type.isEmpty())
			return "void";
		if (type.size() == 1)
			return type(type.get(0));
		StringBuilder sb = new StringBuilder("{ ");
		for (ModifiedType each : type)
			sb.append(type(each)).append(", ");
		return sb.replace(sb.length() - 2, sb.length(), " }").toString();
	}
	private static String type(ClassType type, boolean nullable)
	{		
		if (type.isPrimitive() && !nullable)
			return '%' + type.getTypeName();
		return "%\"" + type.getMangledName() + "\"*";
	}
	private static String type(InterfaceType type)
	{
		return "{ %" + raw(type, "_methods") + "*, " + type(Type.OBJECT) + " }";
	}
	private static String type(TypeParameter type)
	{
		return type(type.getClassBound());
	}
	private static String raw(Type type)
	{
		return raw(type, "");
	}
	private static String raw(Type type, String extra)
	{
		return '\"' + type.getMangledName() + extra + '\"';
	}	

	private static String withGenerics(Type type, String extra)
	{
		return '\"' + type.getMangledNameWithGenerics() + extra + '\"';
	}


	private static String name(TACLabel label)
	{
		Object name = label.getRef().getData();
		if (name instanceof String)
			return ((String)name).substring(1);
		throw new NullPointerException();
	}
	private static String name(TACVariable var)
	{
		String name = var.getName();
		if (name == null)
			throw new NullPointerException();
		return name;
	}
	private static String name(TACConstantRef constant)
	{
		return Type.mangle(new StringBuilder("@\"").
				append(constant.getPrefixType().getMangledName()).append("_M"),
				constant.getName()).append('\"').toString();
	}
	private static String name(TACConstant constant)
	{
		return Type.mangle(new StringBuilder("@\"").
				append(constant.getPrefixType().getMangledName()).append("_M"),
				constant.getName()).append('\"').toString();
	}
	
	//Is it only the wrapped ones that correspond to interface methods?
	//If so, those are the ones that need special generic attention
	private static String name(TACMethodRef method)
	{
		StringBuilder sb = new StringBuilder("@\"");
		
		if( method.isWrapper() )		
			sb.append(method.getOuterType().getMangledNameWithGenerics());
		else
			sb.append(method.getOuterType().getMangledName());	
		
		sb = Type.mangle( sb.append("_M"), method.getName());
		
		for (ModifiedType paramType : method.getType().getTypeWithoutTypeArguments().getParameterTypes())
		{
			Type type = paramType.getType();
			//if( type instanceof ArrayType )
			//	type = ((ArrayType)type).convertToGeneric();
			if( type instanceof TypeParameter )
				type = ((TypeParameter)type).getClassBound();
			
			sb.append(type.getMangledNameWithGenerics());
		}
		if (method.isWrapper())
			sb.append("_W");
		return sb.append('\"').toString();
	}
	
	private static String name(MethodSignature method)
	{
		StringBuilder sb = new StringBuilder("@\"");
		
		if( method.isWrapper() )		
			sb.append(method.getOuter().getMangledNameWithGenerics());
		else
			sb.append(method.getOuter().getMangledName());
		
		SequenceType parameters;
		if( method.isWrapper() || method.getOuter() instanceof InterfaceType )
			parameters =  method.getMethodType().getTypeWithoutTypeArguments().getParameterTypes();
		else
			parameters = method.getMethodType().getParameterTypes();
		
		sb = Type.mangle( sb.append("_M"), method.getSymbol());
		for (ModifiedType paramType : parameters)
		{
			Type type = paramType.getType();
			//if( type instanceof ArrayType )
			//	type = ((ArrayType)type).convertToGeneric();
			if( type instanceof TypeParameter )
				type = ((TypeParameter)type).getClassBound();
			
			sb.append(type.getMangledNameWithGenerics());
		}
		if (method.isWrapper())
			sb.append("_W");
		return sb.append('\"').toString();
	}
	
	
	
	/*//old
	 private static String name(TACMethodRef method)
	{
		StringBuilder sb = Type.mangle(new StringBuilder("@\"").
				append(method.getPrefixType().getMangledName()).append("_M"),
				method.getName());
		for (ModifiedType paramType : method.getType().getParameterTypes())
			sb.append(paramType.getType().getMangledName());
		if (method.isWrapper())
			sb.append("_W");
		return sb.append('\"').toString();
	}
	*/
	//with interfaces, generic types are also needed to avoid collisions with
	//methods implementing a different generic version of the same interface
	/*
	private static String interfaceName(TACMethodRef method)
	{
		StringBuilder sb = Type.mangle(new StringBuilder("@\"").
				append(method.getPrefixType().getMangledNameWithGenerics()).append("_M"),
				method.getName());
		for (ModifiedType paramType : method.getType().getParameterTypes())
			sb.append(paramType.getType().getMangledName());
		if (method.isWrapper())
			sb.append("_W");
		return sb.append('\"').toString();
	}
	*/
	private static String name(TACMethod method)
	{
		return name(method.getMethod());
	}

	private static String typeName(TACVariable variable)
	{
		return typeText(variable, '%' + name(variable), true);
	}

	private static String symbol(TACOperand node)
	{
		Object symbol = node.getData();
		if (symbol instanceof String)
			return (String)symbol;
		throw new NullPointerException();
	}
	private static String typeSymbol(TACOperand node)
	{
		return typeSymbol(node, node);
	}
	private static String typeSymbol(TACOperand node, boolean reference)
	{
		return typeSymbol(node, node, reference);
	}
	private static String typeSymbol(Type type, TACOperand node)
	{
		return typeSymbol(type, node, false);
	}
	private static String typeSymbol(Type type, TACOperand node, boolean reference)
	{
		return typeText(type, symbol(node), reference);
	}
	private static String typeSymbol(ModifiedType type, TACOperand node)
	{
		return typeText(type, symbol(node));
	}
	private static String typeSymbol(ModifiedType type, TACOperand node,
			boolean reference)
	{
		return typeText(type, symbol(node), reference);
	}

	private String literal(ShadowValue value)
	{
		if (value == ShadowValue.NULL)
			return "null";
		if (value instanceof ShadowBoolean)
			return literal((boolean)((ShadowBoolean)value).getValue());
		if (value instanceof ShadowInteger)
			return literal(((ShadowInteger)value).getValue());
		if (value instanceof ShadowCode)
			return literal((long)((ShadowCode)value).getValue());
		if (value instanceof ShadowFloat)
			return literal((double)((ShadowFloat)value).getValue());
		if (value instanceof ShadowDouble)
			return literal((double)((ShadowDouble)value).getValue());
		if (value instanceof ShadowString)
			return literal((String)((ShadowString)value).getValue());
		throw new UnsupportedOperationException(value.getClass().toString());
	}
	private static String literal(boolean value)
	{
		return Boolean.toString(value);
	}
	private static String literal(long value)
	{
		return Long.toString(value);
	}
	
	private static String literal(BigInteger value)
	{
		return value.toString();
	}
	
	private static String literal(double value)
	{
		long raw = Double.doubleToRawLongBits(value);
		StringBuilder sb = new StringBuilder("0x");
		for (int i = 0; i < 16; i++, raw <<= 4)
			sb.append(Character.forDigit((int)(raw >> 60) & 15, 16));
		return sb.toString();
	}
	private String literal(String value) {
		int index = stringLiterals.indexOf(value);
		if (index == -1) {
			index = stringLiterals.size();
			stringLiterals.add(value);
			
			//module is null when generics are being built
			if( module != null && !module.getType().encloses(Type.STRING)) {
				usedClasses.add(Type.STRING);
				usedMethodTables.add(Type.STRING);
			}
		}
		return "@_string" + index;
	}
	private String typeLiteral(boolean value)
	{
		return typeText(Type.BOOLEAN, literal(value));
	}
	private static String typeLiteral(int value)
	{
		return typeText(Type.INT, literal(value));
	}
	private String typeLiteral(String value)
	{
		return typeText(Type.STRING, literal(value));
	}
	private String typeLiteral(ShadowValue value)
	{
		return typeText(value, literal(value));
	}

	private String typeTemp(Type type, int offset)
	{
		return typeText(type, temp(offset));
	}
	private String typeTemp(Type type, int offset, boolean reference)
	{
		return typeText(type, temp(offset), reference);
	}

	private static String typeText(Type type, String name)
	{
		return combine(type(type), name, false);
	}
	private static String typeText(Type type, String name, boolean reference)
	{
		return combine(type(type), name, reference);
	}
	private static String typeText(ModifiedType type, String name)
	{
		return combine(type(type), name, false);
	}
	private static String typeText(ModifiedType type, String name,
			boolean reference)
	{
		return combine(type(type), name, reference);
	}

	private static String combine(String type, String name, boolean reference)
	{
		StringBuilder sb = new StringBuilder(type);
		if (reference)
			sb.append('*');
		return sb.append(' ').append(name).toString();
	}
	
	public HashSet<Type> getGenerics()
	{
		return generics;
	}
	
	public HashSet<ArrayType> getArrays()
	{
		return arrays;
	}

	public void setGenerics(HashSet<Type> generics, HashSet<ArrayType> arrays)
	{
		this.generics = generics;
		this.arrays = arrays;
	}
	
	
	public void buildGenerics() throws ShadowException
	{
		writer.write("; Generics");
		writer.write();

		writePrimitiveTypes();
		
		writeTypeDeclaration(Type.OBJECT);		
		writeTypeDeclaration(Type.CLASS);
		writeTypeDeclaration(Type.GENERIC_CLASS);
		writeTypeDeclaration(Type.ARRAY_CLASS);
		//writeTypeDeclaration(Type.METHOD_CLASS);
		//TODO: potentially remove Iterator references?
		//probably not, since they're in String
		writeTypeDeclaration(Type.ITERATOR);
		writeTypeDeclaration(Type.STRING);
		writeTypeOpaque(Type.ADDRESS_MAP);
		
		writeTypeConstants(Type.OBJECT);		
		writeTypeConstants(Type.CLASS);
		writeTypeConstants(Type.GENERIC_CLASS);
		writeTypeConstants(Type.ARRAY_CLASS);
		writeTypeConstants(Type.ITERATOR);
		writeTypeConstants(Type.STRING);
		
		//contains generics, organized in lists with the unparameterized generic as the key
		Map<Type, Set<Type>> allGenerics = new HashMap<Type, Set<Type>>();
		
		Set<Type> parameterNames = new HashSet<Type>();
		Set<Type> types = new HashSet<Type>();	
		
		types.add(Type.OBJECT);
		types.add(Type.CLASS);
		types.add(Type.GENERIC_CLASS);
		types.add(Type.ARRAY_CLASS);
		types.add(Type.ITERATOR);
		types.add(Type.STRING);	
				
		for( Type generic : generics )
		{				
			if( types.add(generic) )
			{	
				StringBuilder sb; 
				boolean first;
				Type noArguments = generic.getTypeWithoutTypeArguments();
				
				if( !(generic instanceof InterfaceType) )
				{	
					if( !allGenerics.containsKey(noArguments) ) {
						allGenerics.put(noArguments, new HashSet<Type>());
						writer.write("@_interfaceData" + noArguments.getMangledName() + " = external constant [" + generic.getInterfaces().size() + " x " + type(Type.OBJECT) + "]");						
						
						//this is only really necessary to get size
						//gettable from original class?
						writer.write(typeLayout(noArguments));						
					}
					
					sb = new StringBuilder("[");
					first = true;
					
					for(InterfaceType _interface : generic.getInterfaces() ) {		
						if( first )
							first = false;
						else
							sb.append(", ");
						if( _interface.isFullyInstantiated() )
							sb.append(type(Type.CLASS) + " bitcast (" + typeText(Type.GENERIC_CLASS, classOf(_interface) + " to " + type(Type.CLASS) + ")"));
						else
							sb.append(typeText(Type.CLASS, classOf(_interface)));							
					}
					
					sb.append("]");					
					
					writer.write("@_interfaces" + generic.getMangledNameWithGenerics() +
							" = private constant [" + generic.getInterfaces().size() + " x " + type(Type.CLASS) + "] " + sb.toString());
				}
				else
				{
					if( !allGenerics.containsKey(noArguments) )
						allGenerics.put(noArguments, new HashSet<Type>());					
				}
				
				allGenerics.get(noArguments).add(generic);				
				
				//write definitions of type parameters
				sb = new StringBuilder("[");					
				first = true;					
				
				List<ModifiedType> parameterList = generic.getTypeParametersIncludingOuterClasses(); 
				for( ModifiedType parameter : parameterList ) {		
					if( first )
						first = false;
					else
						sb.append(", ");
					
					Type parameterType = parameter.getType();
					Type parameterWithoutArguments = parameterType.getTypeWithoutTypeArguments();
					
					//arrays need special treatment
					//use an array type inside of anything except an array, use the generic version
					//inside array (or inner classes of array or iterator), leave it as an array
					if( parameterType instanceof ArrayType &&
						!parameterWithoutArguments.equals(Type.ITERATOR) &&
						!parameterWithoutArguments.equals(Type.ITERATOR_NULLABLE) &&
						!(parameterWithoutArguments.equals(Type.ARRAY) || parameterWithoutArguments.equals(Type.ARRAY_NULLABLE) || (parameterWithoutArguments.hasOuter() && parameterWithoutArguments.getOuter().equals(Type.ARRAY)) || (parameterWithoutArguments.hasOuter() && parameterWithoutArguments.getOuter().equals(Type.ARRAY_NULLABLE))) )
					{
						parameterType = ((ArrayType)parameterType).convertToGeneric();
						parameterWithoutArguments = parameterType.getTypeWithoutTypeArguments();
					}
					
					
					//first put in the class
					sb.append(type(Type.OBJECT)).append(" bitcast (");
					if( parameterWithoutArguments.equals(Type.ARRAY) ||  parameterWithoutArguments.equals(Type.ARRAY_NULLABLE) )
						sb.append(type(Type.ARRAY_CLASS));
					else if( parameterType.isFullyInstantiated() )
						sb.append(type(Type.GENERIC_CLASS));
					else
						sb.append(type(Type.CLASS));
					
					sb.append(" ").append(classOf(parameterType)).append(" to ").append(type(Type.OBJECT)).append("), ");
					
					
					//then the method table
					if( parameterType instanceof InterfaceType || parameter instanceof ArrayType )
						sb.append(type(Type.OBJECT)).append(" null"); //no method table for interfaces
					else
						sb.append(type(Type.OBJECT)).append(" bitcast (%\"" + parameterWithoutArguments.getMangledName() + "_methods\"* @\"" + parameterWithoutArguments.getMangledName() + "_methods\" to " + type(Type.OBJECT) + ")" );
					
					parameterNames.add(parameterWithoutArguments);
				}
				
				sb.append("]");	
				
				writer.write("@_parameters" + generic.getMangledNameWithGenerics() +
						" = private constant [" + parameterList.size()*2 + " x " + type(Type.OBJECT) + "] " + sb.toString());
									
				String interfaceData;
				String interfaces;
				String size;
				
				if( generic instanceof InterfaceType )
				{
					interfaceData = interfaces = " zeroinitializer, ";
					size = typeLiteral(-1) + ", ";
				}
				else
				{
					interfaceData = " { " + type(Type.OBJECT) + "* getelementptr ([" + generic.getInterfaces().size() + " x " + type(Type.OBJECT) + "]* @_interfaceData" + noArguments.getMangledName() + ", i32 0, i32 0), [1 x " +
							type(Type.INT) + "] [" + typeLiteral(generic.getInterfaces().size()) +	"] }, ";
					interfaces = " { " + type(Type.CLASS) + "* getelementptr inbounds ([" + generic.getInterfaces().size() + " x " +
							type(Type.CLASS) + "]* @_interfaces" + generic.getMangledNameWithGenerics() + ", i32 0, i32 0), [1 x " +
							type(Type.INT) + "] [" + typeLiteral(generic.getInterfaces().size()) + "] }, ";
					size = typeText(Type.INT, sizeof('%' + noArguments.getMangledName() + '*')) + ", ";
				}	
				
				//get parent class
				String parentClass;
				if( generic instanceof ClassType  ) {
					ClassType parent = ((ClassType) generic).getExtendType();					
					if( parent.isFullyInstantiated() )
						parentClass = type(Type.CLASS) + " (" + type(Type.GENERIC_CLASS) + " @\"" + parent.getMangledNameWithGenerics() + "_class\" to " + type(Type.CLASS) + ")";
					else
						parentClass = type(Type.CLASS) + " @\"" + parent.getMangledName() + "_class\"";
				}
				else				
					parentClass = type(Type.CLASS) +  " null";				
				
				if( noArguments.equals(Type.ARRAY) || noArguments.equals(Type.ARRAY_NULLABLE) ) {
					Type parameter = generic.getTypeParameters().get(0).getType();
					String parameterClass;
												
					if( parameter.isFullyInstantiated() ) //parameters that are themselves generic
					{						
						parameterNames.add(parameter.getTypeWithoutTypeArguments());						
						parameterClass = " bitcast (" + type(Type.GENERIC_CLASS) + " @\"" + parameter.getMangledNameWithGenerics() + "_class\" to " + type(Type.CLASS) + ")";
					}	
					else
					{
						parameterNames.add(parameter);
						parameterClass = " @\"" + parameter.getMangledName() + "_class\"";
					}
					
					writer.write("@\"" + generic.getMangledNameWithGenerics() + "_class\"" + " = constant %" +
							raw(Type.ARRAY_CLASS) + " { " + 		
							
							type(Type.CLASS) + " @" + raw(Type.ARRAY_CLASS, "_class") + ", " + //class
							"%" + raw(Type.ARRAY_CLASS, "_methods") + "* @" + raw(Type.ARRAY_CLASS, "_methods") + ", " + //methods
							
							typeLiteral(generic.toString()) + ", " + //name 
							parentClass + ", "  +//parent 					
							
							type(new ArrayType(Type.OBJECT)) + 
							interfaceData + //data
														
							type(new ArrayType(Type.CLASS)) + 
							interfaces + //interfaces
						
							typeLiteral(GENERIC) + ", " + //flags							
							size + //size
							
							type(new ArrayType(Type.OBJECT)) + 
							" { " + type(Type.OBJECT) + "* getelementptr inbounds ([" + generic.getTypeParameters().size()*2 + " x " +
							type(Type.OBJECT) + "]* @_parameters" + generic.getMangledNameWithGenerics() + ", i32 0, i32 0), [1 x " +
							type(Type.INT) + "] [" + typeLiteral(generic.getTypeParameters().size()*2) + "] }" + ", " + //parameters 
							
							type(Type.CLASS) + parameterClass + //internal class
							
							" }");
				}
				else //regular generic
				{
				
					writer.write("@\"" + generic.getMangledNameWithGenerics() + "_class\"" + " = constant %" +
							raw(Type.GENERIC_CLASS) + " { " + 		
							
							type(Type.CLASS) + " @" + raw(Type.GENERIC_CLASS, "_class") + ", " + //class
							"%" + raw(Type.GENERIC_CLASS, "_methods") + "* @" + raw(Type.GENERIC_CLASS, "_methods") + ", " + //methods
							
							typeLiteral(generic.toString()) + ", " + //name 
							parentClass + ", "  +//parent 					
							
							type(new ArrayType(Type.OBJECT)) + 
							interfaceData + //data
														
							type(new ArrayType(Type.CLASS)) + 
							interfaces + //interfaces
						
							typeLiteral(GENERIC) + ", " + //flags							
							size + //size
							
							type(new ArrayType(Type.OBJECT)) + 
							" { " + type(Type.OBJECT) + "* getelementptr inbounds ([" + parameterList.size()*2 + " x " +
							type(Type.OBJECT) + "]* @_parameters" + generic.getMangledNameWithGenerics() + ", i32 0, i32 0), [1 x " +
							type(Type.INT) + "] [" + typeLiteral(parameterList.size()*2) + "] }" + //parameters
							
							" }");
				}
			}
		}		
		
		writer.write();
		
		
		for( Entry<Type, Set<Type>> entry : allGenerics.entrySet() )
		{
			int size = entry.getValue().size();
			if( size > 0 )
			{
				StringBuilder sb = new StringBuilder("@_data" + entry.getKey().getMangledName() +
						" = private constant [" + size + " x " + type(Type.GENERIC_CLASS) + "] [");
				
				boolean first = true;
				
				for( Type parameterized : entry.getValue() )
				{
					if( first )
						first = false;
					else
						sb.append(", ");
					
					sb.append(type(Type.GENERIC_CLASS));
					
					if( entry.getKey().equals(Type.ARRAY) || entry.getKey().equals(Type.ARRAY_NULLABLE) )
					{
						sb.append(" bitcast( " + type(Type.ARRAY_CLASS) + " ");						
						sb.append(classOf(parameterized));
						sb.append(" to " + type(Type.GENERIC_CLASS) + ")");
					}
					else
						sb.append(classOf(parameterized));				
				}
				
				sb.append("]");						
						
				
				writer.write(sb.toString());
				writer.write("@_generics" + entry.getKey().getMangledName() +
						" = constant " + type(new ArrayType(Type.GENERIC_CLASS)) +
						" { " + type(Type.GENERIC_CLASS) +  "* getelementptr inbounds ([" + size + " x "  + type(Type.GENERIC_CLASS) + "]* " +
						"@_data" + entry.getKey().getMangledName() + ", i32 0, i32 0), [1 x " + type(Type.INT) + "] [" + type(Type.INT) + " " + size + "] }");
			}
		}
		
		if( arrays.size() > 0 ) {
			writer.write();
			writer.write("; Arrays");
		}
		
		for( ArrayType array : arrays )
		{			
			writer.write(classOf(array) + " = constant %" + raw(Type.CLASS) + " {" +
			typeText(Type.CLASS, classOf(Type.CLASS)) + ", " + 
			"%" + raw(Type.CLASS, "_methods") + "* @" + raw(Type.CLASS, "_methods") + ", " + 
			typeLiteral(array.toString()) + ", " + 
			typeText(Type.CLASS, classOf(array.getBaseType())) + ", " + 
			type(new ArrayType(Type.OBJECT)) + " zeroinitializer, " +
			type(new ArrayType(Type.CLASS)) + " zeroinitializer, " +
			typeLiteral(ARRAY) + ", " +			
			typeLiteral(array.getDimensions()) + " }");
			
			types.add(array);
		}
		
		writer.write();		
		
		for( Type parameter : parameterNames) {			
			if( !types.contains(parameter) ) {
				//no class object for the unparameterized class
				if( !parameter.isParameterized() )
					writer.write(classOf(parameter) + " = external constant %" + raw(Type.CLASS));
				writer.write("%" + raw(parameter, "_methods") + " = type opaque");				
				if( !(parameter instanceof InterfaceType) )
					writer.write("@" + raw(parameter, "_methods") + " = external constant %" + raw(parameter, "_methods"));							
			}
		}		
		
		writer.write();	
		
		
		writeStringLiterals();		
	}
	
	private static String typeLayout(Type type) {
		StringBuilder sb = new StringBuilder("%" + type.getMangledName() + " = type { ");
		
		sb.append(simplify(Type.CLASS)).append(", ");
	
		//then the method table
		sb.append(simplify(Type.OBJECT));

		if (type.isPrimitive())
			sb.append(", ").append(simplify(type));
		else			
			for (Entry<String, ? extends ModifiedType> field :
				((ClassType)type).orderAllFields())
				sb.append(", ").append(simplify(field.getValue().getType()));
		return sb.append(" }").toString();
	}
	
	
	//needed so that complex type information is not needed when describing types
	private static String simplify(Type type)
	{
		if( type.isPrimitive() )		
			return '%' + type.getTypeName();		
		else if( type instanceof InterfaceType )		
			return "{ " + simplify(Type.OBJECT) + ", " + simplify(Type.OBJECT) + " }";			
		else if( type instanceof ArrayType )		
			return "{ " + simplify(((ArrayType)type).getBaseType()) + "*, [" + ((ArrayType)type).getDimensions() + " x " + simplify(Type.INT) + "] }";			
		else if( type instanceof MethodType )
			throw new UnsupportedOperationException("Method types not yet supported");
		else
			return "%\"" + Type.OBJECT.getMangledName() + "\"*";
	}
	
}
