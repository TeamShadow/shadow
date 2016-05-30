package shadow.output.llvm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

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
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACConstant;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.TACNodeList;
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
import shadow.tac.nodes.TACConversion;
import shadow.tac.nodes.TACCopyMemory;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACGenericArrayRef;
import shadow.tac.nodes.TACGlobal;
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
import shadow.tac.nodes.TACSingletonRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACTypeId;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACUnwind;
import shadow.tac.nodes.TACVariableRef;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodTableType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public class LLVMOutput extends AbstractOutput {
	private Process process = null;
	private int tempCounter = 0;
	private List<String> stringLiterals = new LinkedList<String>();	
	private HashSet<Type> unparameterizedGenerics = new HashSet<Type>();	
	private TACMethod method;
	private int classCounter = 0;
	private HashSet<MethodSignature> usedSignatures = new HashSet<MethodSignature>();
	private HashSet<TACConstantRef> usedConstants = new HashSet<TACConstantRef>();
	
	
	private Set<String> genericClasses = new TreeSet<String>();
	private Set<String> arrayClasses = new TreeSet<String>();
	
	private TACModule module;
	private boolean skipMethod = false;
	
	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	public LLVMOutput(File file) throws ShadowException {
		super(file);
		//super(new File(file.getParent(),
			//	file.getName().replace(".shadow", ".ll")));
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

	// Class type flags
	public static final int INTERFACE = 1, PRIMITIVE = 2, GENERIC = 4, ARRAY = 8, SINGLETON = 16, METHOD = 32;
	
	private void writePrimitiveTypes() throws ShadowException {
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
	}
	private void writeTypes() throws ShadowException {
		writePrimitiveTypes();
		
		Type moduleType = module.getType();
		
		//type references
		HashSet<Type> definedGenerics = new HashSet<Type>();
		for (Type type : moduleType.getUsedTypes()) {			
			//write type and method table declarations (even for current types!)
			if(type != null && !(type instanceof ArrayType)  ) {	
				if( !type.isParameterizedIncludingOuterClasses() ) {
					writeTypeDefinition(type);
					
					//external stuff for types outside of this file
					if( !moduleType.encloses(type) ) {					
						writer.write(classOf(type) +
								" = external constant %" + raw(Type.CLASS));					
						if( type instanceof ClassType )				
							writer.write(methodTable(type) +
								" = external constant " + methodTableType(type));					
						if (type instanceof SingletonType) //never parameterized
							writer.write('@' + raw(type, "_instance") +
								" = external global " + type(type));
					}
				}
				else {
					Type unparameterizedType = type.getTypeWithoutTypeArguments();
					//if unparameterized version has not been declared yet, do it
					if( definedGenerics.add(unparameterizedType) ) {				
						writeTypeDefinition(unparameterizedType);						
						
						if( !moduleType.encloses(unparameterizedType) ) {
							writer.write(classOf(unparameterizedType) +
									" = external constant %" + raw(Type.CLASS));
							if(  type instanceof ClassType ) {
								writer.write( interfaceData(unparameterizedType) +
										" = external constant [" + type.getAllInterfaces().size() + " x " + type(Type.OBJECT) + "]");
								writer.write(methodTable(unparameterizedType) +
										" = external constant " + methodTableType(unparameterizedType));
							}						
						}						
					}
				
					if( type.isFullyInstantiated()  )
						writeGenericClassSupportingMaterial(type); //junk that all generic classes need
				}
			}
		}
		
		for (Type type : moduleType.getMentionedTypes()) {
			if(type != null && !(type instanceof ArrayType) && !moduleType.getUsedTypes().contains(type)  ) {	
				if( !type.isParameterizedIncludingOuterClasses() ) {
					writeTypeDeclaration(type);
					
					//external stuff for types outside of this file
					if( !moduleType.encloses(type) )
						writer.write(classOf(type) +
								" = external constant %" + raw(Type.CLASS));					
				}
				else {
					Type unparameterizedType = type.getTypeWithoutTypeArguments();
					//if unparameterized version has not been declared yet, do it
					if( definedGenerics.add(unparameterizedType) ) {				
						writeTypeDeclaration(unparameterizedType);						
						
						if( !moduleType.encloses(unparameterizedType) ) {
							writer.write(classOf(unparameterizedType) +
									" = external constant %" + raw(Type.CLASS));											
						}						
					}				
				}
			}
		}
		
		writer.write();
	}
	
	private void writeTypeDeclaration(Type type) throws ShadowException {
		StringBuilder sb = new StringBuilder();		
		if( type instanceof InterfaceType ) {
			sb.append(methodTableType(type)).append(" = type opaque");			
			writer.write(sb.toString());
		}
		else if (type instanceof ClassType) {	
			if( type.isUninstantiated() ) {
				sb.append('%' + raw(type)).append(" = type opaque");
				writer.write(sb.toString());
			}
		}	
	}
	
	private void writeTypeDefinition(Type type) throws ShadowException {
		StringBuilder sb = new StringBuilder();
		
		if( type instanceof InterfaceType ) {
			sb.append(methodTableType(type)).append(" = type { ");			
			writer.write(sb.append(methodList(type.orderAllMethods(), false)).
					append(" }").toString());
		}
		else if (type instanceof ClassType) {	
			if( type.isUninstantiated() ) {
				sb.append(methodTableType(type)).append(" = type { ");				
				writer.write(sb.append(methodList(type.orderAllMethods(), false)).
						append(" }").toString());
				
				sb.setLength(0);
								
				//first thing in every object is the class			
				sb.append('%' + raw(type)).append(" = type { ");
				
				sb.append(type(Type.CLASS)).append(", ");
				
				//then the method table
				sb.append(methodTableType(type)).append("* ");
				

				if (type.isPrimitive()) //put wrapped value in for primitives
					sb.append(", ").append(type(type));
				else {			
					for (Entry<String, ? extends ModifiedType> field :
							((ClassType)type).orderAllFields())					
							sb.append(", ").append(type(field.getValue()));					
				}
				writer.write(sb.append(" }").toString());
			}
		}	
	}
	
	protected void writeModuleDefinition(TACModule module) throws ShadowException {
		Map<String, ShadowValue> constants = new HashMap<String, ShadowValue>();		
		writeModuleDefinition(module, constants);		
	}
	
	private void writeModuleDefinition(TACModule module, Map<String, ShadowValue> constants) throws ShadowException {		
		Type moduleType = module.getType();
		writer.write("; " + moduleType.toString(Type.PACKAGES | Type.TYPE_PARAMETERS | Type.PARAMETER_BOUNDS));
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
		
		
		Set<String> localConstants = new HashSet<String>();		
		
		//constants
		for (TACConstant constant : module.getConstants()) {
			ShadowInterpreter interpreter = new ShadowInterpreter(constants);
			String name = constant.getName();
			Node node = module.getType().getField(name);
			try {
				interpreter.walk(constant);
				Object result = constant.getValue().getData();
				if (!(result instanceof ShadowValue))
					throw new ShadowException(
							TypeCheckException.makeMessage(null, "Could not initialize constant " + name, node.getFile(), node.getLineStart(), node.getLineEnd(), node.getColumnStart(), node.getColumnEnd() ));
				
				String fullName = constant.getPrefixType().toString() + ":" + name; 
				constants.put(fullName, (ShadowValue)result);				
				localConstants.add(fullName);
				writer.write(name(constant) + " = constant " +
					typeLiteral((ShadowValue)result));
			}
			catch(ShadowException e) {
				String message = TypeCheckException.makeMessage(null, "Could not initialize constant " + name + ": " + e.getMessage(), node.getFile(), node.getLineStart(), node.getLineEnd(), node.getColumnStart(), node.getColumnEnd() );
				throw new ShadowException(message);
			}
			
			Cleanup.getInstance().walk(constant);
		}
		
		//interfaces implemented (because a special object is used to map the methods correctly)
		ArrayList<InterfaceType> interfaces = moduleType.getAllInterfaces();
		int interfaceCount = interfaces.size();
		
		if( module.isClass() ) {
		//no need to write interface data for interfaces				
			StringBuilder interfaceData = new StringBuilder( interfaceData(moduleType) + " = ");			

			//if( !moduleType.isParameterizedIncludingOuterClasses() )
				//interfaceData.append("private " );			
					
			//generic classes don't list interfaces (because their parameterized versions have those)
			//but they do share interfaceData (the actual methods)
			interfaceData.append("unnamed_addr constant [").
					append(interfaceCount).append(" x ").
					append(type(Type.OBJECT)).append("] [");
			StringBuilder interfaceClasses = new StringBuilder( interfaces(moduleType) + " = private ");
			
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
				if( type.isFullyInstantiated() )
					interfaceClasses.append(typeText(Type.CLASS, "bitcast (" + type(Type.GENERIC_CLASS) + " " + classOf(type) + " to " + type(Type.CLASS) + ")"));
				else
					interfaceClasses.append(typeText(Type.CLASS, classOf(type)));
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
			writer.write(methodTable(moduleType) + " = constant " +
					methodTableType(moduleType) + " { " +					
					methodList(methods, true) + " }");
					
			//nothing will ever be the raw, unparameterized class ...now they will!
			//if( !moduleType.isParameterizedIncludingOuterClasses() ) {
				writer.write(classOf(moduleType) + " = constant %" +
					raw(Type.CLASS) + " { " + 		
					
					type(Type.CLASS) + " " + classOf(Type.CLASS) + ", " + //class
					methodTableType(Type.CLASS) + "* " + methodTable(Type.CLASS) + ", " + //methods
					typeLiteral(moduleType.toString(moduleType.hasOuter() ? Type.NO_OPTIONS : Type.PACKAGES )) + ", " +  //name
					
					typeText(Type.CLASS, parentType != null ?  //parent class
							classOf(parentType) : null) + ", " +
					
					type(new ArrayType(Type.OBJECT)) + " { " +   //data
					type(Type.OBJECT) + "* getelementptr inbounds ([" +
					interfaceCount + " x " + type(Type.OBJECT) +
					"], [" +
					interfaceCount + " x " + type(Type.OBJECT) +
					"]* " + interfaceData(moduleType) + ", i32 0, i32 0), [1 x " + 
					type(Type.INT) + "] [" + typeLiteral(interfaceCount) +
					"] }, " + 
					type(new ArrayType(Type.CLASS)) + //interfaces 
					
					( moduleType.isParameterizedIncludingOuterClasses() ?							
					" zeroinitializer, " :		
							
					" { " + 
					type(Type.CLASS) + "* getelementptr inbounds ([" +
					interfaceCount + " x " + type(Type.CLASS) +
					"], [" +
					interfaceCount + " x " + type(Type.CLASS) +
					"]* " + interfaces(moduleType) + ", i32 0, i32 0), [1 x " + type(Type.INT) +
					"] [" + typeLiteral(interfaceCount) + "] }, " )	+
					
					typeLiteral(flags) + ", " +			//flags
					typeText(Type.INT, sizeof(type(moduleType, true))) + //size 
					" }" );
			//}
		}
		else {
			flags |= INTERFACE;
			
			//nothing will ever be the raw, unparameterized class...NOW THEY Will!
			//if( !moduleType.isParameterizedIncludingOuterClasses() )
				writer.write(classOf(moduleType) + " = constant %" +
					raw(Type.CLASS) + " { " + 		
					
					type(Type.CLASS) + " " + classOf(Type.CLASS) + ", " + //class
					methodTableType(Type.CLASS) + "* " + methodTable(Type.CLASS) + ", " + //methods
					
					typeLiteral(moduleType.toString(moduleType.hasOuter() ? Type.NO_OPTIONS :  Type.PACKAGES)) + ", " + //name 
					type(Type.CLASS) + " null, " + //parent 					
					
					type(new ArrayType(Type.OBJECT)) + " zeroinitializer, " + //data
					type(new ArrayType(Type.CLASS)) +  " zeroinitializer, " + //interfaces
					
					typeLiteral(flags) + ", " +
					typeLiteral(-1) + //size (unknown for interfaces) 
					" }");
		}
		
		if (moduleType instanceof SingletonType)
			writer.write('@' + raw(moduleType, "_instance") + " = global " +
					type(moduleType) + " null");
		
		writer.write();
		
		if( moduleType.isParameterizedIncludingOuterClasses() )
			unparameterizedGenerics.add(moduleType.getTypeWithoutTypeArguments());		
		
		//recursively do inner classes
		for( TACModule innerClass : module.getInnerClasses() )
			writeModuleDefinition(innerClass, constants);		
		
		//remove constants defined in current class
		//inner classes can use outer constants but not the converse
		for( String name : localConstants )
			constants.remove(name);
	}
	
	private void writeGenericClasses() throws ShadowException {		
		for (Type type : module.getType().getUsedTypes() ) {
			if( type.isFullyInstantiated() && !(type instanceof ArrayType) )
				writeGenericClass(type);	
		}
		
		writer.write();
	}
	private void writeArrayClasses() throws ShadowException {
		for (Type type : module.getType().getUsedTypes() ) {
			if( type instanceof ArrayType && !((ArrayType)type).containsUnboundTypeParameters()  )
				writeArrayClass((ArrayType)type);	
		}
		
		writer.write();		
	}
	
	public static void readGenericAndArrayClasses(File llvmFile, Set<String> generics, Set<String> arrays ) throws IOException {
		BufferedReader llvm = Files.newBufferedReader(llvmFile.toPath(), UTF8);
		String line;
		
		//first skip all of the type declarations
		while( !(line = llvm.readLine()).isEmpty() );

		//first read in all the generic classes
		while( !(line = llvm.readLine()).isEmpty() )
			generics.add(line.split("\\s+")[0]);
		
		//then read in all the array classes
		while( !(line = llvm.readLine()).isEmpty() )
			arrays.add(line.split("\\s+")[0]);
		
		llvm.close();
	}


	@Override
	public void startFile(TACModule module) throws ShadowException {		
		this.module = module;
		
		//write all regular types including class objects and method tables
		writeTypes();		
				
		writeGenericClasses();		
		writeArrayClasses();
		//writeClasses();
		
		// Methods for exception handling
		writer.write("declare i32 @__shadow_personality_v0(...)");
		writer.write("declare void @__shadow_throw(" + type(Type.OBJECT) + ") noreturn");
		writer.write("declare " + type(Type.EXCEPTION) + " @__shadow_catch(i8* nocapture) nounwind");
		writer.write("declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone");
		//memcopy
		writer.write("declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)");
		writer.write("declare void @__arrayStore(" + type(Type.OBJECT) + "*, %int, " + type(Type.OBJECT) + ", " + type(Type.CLASS) + ")");
		writer.write("declare " + type(Type.OBJECT) + " @__arrayLoad(" + type(Type.OBJECT) + "*, %int, " + type(Type.CLASS) + ", " +  type(Type.OBJECT) + ", %boolean)");
		writer.write("declare void @free(i8*) nounwind");
		writer.write();
		
		//defines class and recursively defines inner classes  
		writeModuleDefinition(module);		
	}

	private String methodList(Iterable<MethodSignature> methods, boolean name)
			throws ShadowException {
		StringBuilder sb = new StringBuilder();
		for (MethodSignature method : methods) {			
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
					" " + classOf(Type.STRING) + ", " +
					methodTableType(Type.STRING) + "* " + methodTable(Type.STRING) + ", " +					
					type(new ArrayType(Type.BYTE)) + " { " + type(Type.BYTE) +
					"* getelementptr inbounds ([" +
					data.length + " x " + type(Type.BYTE) + "], [" +
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
		
		//declare a few strange methods that are not called through normal channels  
		if (moduleType instanceof ClassType ) {
			if (!moduleType.encloses(Type.CLASS))  {
				writer.write("declare noalias " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_Mallocate") + '(' +
						type(Type.CLASS) + ", " + methodTableType(Type.OBJECT) +  "*)");				
				
				writer.write("declare noalias " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_Mallocate_" +
						Type.INT.toString()) + '(' + type(Type.CLASS) +
						", " + type(Type.INT) + ')');
			}
	
			if( !moduleType.encloses(Type.ARRAY) )
				writer.write("declare " + type(Type.ARRAY) + " @" +
						raw(Type.ARRAY, "_Mcreate_" + new ArrayType(Type.INT).
						toString(Type.MANGLE) + "_" + Type.OBJECT.toString(Type.MANGLE)) + '(' +
						type(Type.OBJECT) + ", " +
						type(new ArrayType(Type.INT)) + ", " +
						type(Type.OBJECT) + ')');
			

			if( !moduleType.encloses(Type.ARRAY_NULLABLE) )
				writer.write("declare " + type(Type.ARRAY_NULLABLE) + " @" +
						raw(Type.ARRAY_NULLABLE, "_Mcreate_" + new ArrayType(Type.INT).
						toString(Type.MANGLE) + "_" + Type.OBJECT.toString(Type.MANGLE)) + '(' +
						type(Type.OBJECT) + ", " +
						type(new ArrayType(Type.INT)) + ", " +
						type(Type.OBJECT) + ')');
		}
		
		
		//declare the two sets used to keep track of all generic classes and array classes
		writer.write();
		writer.write("@_genericSet = external global " + type(Type.CLASS_SET));
		writer.write("@_arraySet = external global " + type(Type.CLASS_SET));
		writer.write();
		
		//print only the constant fields that are used
		for( TACConstantRef constant : usedConstants )
			writer.write("@" + 				
				constant.getPrefixType().toString(Type.MANGLE) + "_C" + //c for constant 
				constant.getName() + " = external constant " + type(constant.getType()));
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
					type(Type.CLASS) + ", " + methodTableType(Type.OBJECT) +  "*)");
	

		writeStringLiterals();

		if (process != null)
			try {
				String line;
				BufferedReader reader;
				process.getOutputStream().close();

				reader = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				while ((line = reader.readLine()) != null)
					System.out.println(line);
				reader.close();

				try {
					Thread.sleep(11); //why is this 11?
				}
				catch (InterruptedException ex)	{}

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
			catch (IOException ex) {
				throw new ShadowException( ex.getLocalizedMessage() );
			}
	}


	@Override
	public void startMethod(TACMethod method, TACModule module) throws ShadowException {
		this.method = method;		
		MethodSignature signature = method.getMethod();
		if (module.getType() instanceof InterfaceType ) {
			skipMethod = true;			
		}		
		else if (signature.isNative()) {
			writer.write("declare " + methodToString(method));
			writer.write();
			skipMethod = true;
		}
		else {
			SequenceType parameters = signature.getFullParameterTypes();
			tempCounter = parameters.size() + 1;
			writer.write("define " + methodToString(method) +
					(signature.isWrapper() ? " unnamed_addr" : "" ) +
					(method.hasLandingpad() ? " personality i32 (...)* @__shadow_personality_v0 {" : " {"  ));
			writer.indent();
			for (TACVariable local : method.getLocals())
				writer.write('%' + name(local) + " = alloca " + type(local));
			if (method.hasLandingpad())
				writer.write("%_exception = alloca { i8*, i32 }");			
			
			boolean primitiveCreate = !signature.getOuter().
					isSimpleReference() && signature.isCreate(), first = true;
			int paramIndex = 0;
			for (TACVariable param : method.getParameters()) {
				String symbol = '%' + Integer.toString(paramIndex++);
				if (first) {
					first = false;
					if (signature.isCreate()) {
						writer.write(nextTemp() + " = bitcast " +
								typeText(Type.OBJECT, symbol) + " to %" +
								raw(param.getType()) + '*');
						symbol = temp(0);
					}
					if (primitiveCreate) {
						writer.write(nextTemp() +
								" = getelementptr inbounds %" +
								raw(param.getType()) + ", %" +
								raw(param.getType()) + "* " + symbol +
								", i32 0, i32 2");
						writer.write(nextTemp() + " = load " + type(param) + ", " +
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
	public void walk(TACNodeList nodes) throws ShadowException {
		if( !skipMethod )
			super.walk(nodes);
	}

	@Override
	public void endMethod(TACMethod method, TACModule module) throws ShadowException {
				
		if( !skipMethod ) {
			writer.outdent();
			writer.write('}');
			writer.write();
		}
		skipMethod = false;
		method = null;
	}

	@Override
	public void visit(TACVariableRef node) throws ShadowException {
		node.setData( '%' + name(node.getVariable()));
	}

	@Override
	public void visit(TACSingletonRef node) throws ShadowException {
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
				type(node.getType()) + ", " +
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
	public void visit(TACFieldRef node) throws ShadowException {		
		writer.write(nextTemp(node) + " = getelementptr inbounds " +
				"%" + raw(node.getPrefix().getType()) + ", " + 
				typeSymbol(node.getPrefix()) + ", i32 0, i32 " +
				(node.getIndex())); 
	}

	/*
	@Override
	public void visit(TACLabelRef node) throws ShadowException {
		nextLabel(node);
	}
	*/
	
	@Override
	public void visit(TACLongToPointer node) throws ShadowException {
		writer.write(nextTemp(node) + " = inttoptr " + type(Type.ULONG) + " " +
				symbol(node.getOperand(0)) + " to " + type(node.getType())); 
	}
	
	@Override
	public void visit(TACPointerToLong node) throws ShadowException {		
		writer.write(nextTemp(node) + " = ptrtoint " + typeSymbol(node.getOperand(0)) +
				" to " + type(Type.ULONG));
	}
	
	@Override
	public void visit(TACCopyMemory node) throws ShadowException {
		TACOperand destinationNode = node.getDestination();
		TACOperand sourceNode = node.getSource();
		TACOperand size = node.getSize();
		
		String destination = typeSymbol(destinationNode);
		String source = typeSymbol(sourceNode);
		
		if( destinationNode.getType() instanceof ArrayType ) {
			ArrayType arrayType = (ArrayType) destinationNode.getType();
			writer.write(nextTemp() + " = extractvalue " +
					destination + ", 0");
			destination = typeText(arrayType.getBaseType(), temp(0), true);			
		}
		
		if( sourceNode.getType() instanceof ArrayType ) {
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
	public void visit(TACMethodRef node) throws ShadowException {
		if (node.getOuterType() instanceof InterfaceType) {
			writer.write(nextTemp() + " = extractvalue " +
					type(node.getOuterType()) + " " + symbol(node.getPrefix()) + ", 0");
			writer.write(nextTemp() + " = getelementptr " +
					methodTableType(node.getOuterType()) +
					", " + methodTableType(node.getOuterType()) + "* " +
					temp(1) + ", i32 0, i32 " + node.getIndex());
			writer.write(nextTemp(node) + " = load " + methodType(node) + ", " + methodType(node) +
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
			writer.write(nextTemp() + " = getelementptr inbounds %" +
					raw(node.getPrefix().getType()) + ", " + 					
					typeSymbol(node.getPrefix()) + ", i32 0, i32 1");
			
			writer.write(nextTemp() + " = load " + methodTableType(node.getPrefix().getType()) + "*, " +
					methodTableType(node.getPrefix().getType()) + "** " + temp(1));
			
			writer.write(nextTemp() + " = getelementptr inbounds " +
					methodTableType(node.getPrefix().getType()) + ", " + 
					methodTableType(node.getPrefix().getType()) + "* " +
					temp(1) + ", i32 0, i32 " + node.getIndex()); //may need to + 1 to the node.getIndex() if a parent method table is added	
			
			writer.write(nextTemp(node) + " = load " + methodType(node) + ", " + methodType(node) +
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
	public void visit(TACLiteral node) throws ShadowException {
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
		if( type instanceof ArrayType )
			type = ((ArrayType)type).recursivelyGetBaseType();
			
		if( type instanceof InterfaceType )
			node.setData("null");
		else			
			node.setData(methodTable(type));
	}	

	@Override
	public void visit(TACSequence node) throws ShadowException {
		String current = "undef";
		for (int i = 0; i < node.size(); i++) {
			writer.write(nextTemp() + " = insertvalue " + typeText(node,
					current) + ", " + typeSymbol(node.get(i)) + ", " + i);
			current = temp(0);
		}
		node.setData(current);
	}
	
	@Override
	public void visit(TACConversion node) throws ShadowException {
		TACOperand source = node.getOperand(0);		
		Type destType = node.getType();
		Type srcType = source.getType();
		ArrayType arrayType;
		String dimsType;
		Type genericArray;
		String baseType;
		
		switch( node.getKind()  ) {
		case INTERFACE_TO_OBJECT:
			writer.write(nextTemp(node) + " = extractvalue " +
					typeSymbol(source) + ", 1");			
			break;
			
		case OBJECT_TO_INTERFACE:
			//operand 1 is the previously processed methods
			writer.write(nextTemp() + " = bitcast " + typeSymbol(node.getOperand(1)) +
					" to " + methodTableType(destType) + '*');
			writer.write(nextTemp() + " = insertvalue " + type(destType) +
					" undef, " + methodTableType(destType) + "* " + temp(1) +
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
					" " + classOf(srcType) + ", " + 
					methodTableType(Type.OBJECT) + "* bitcast(" +
					methodTableType(srcType) + "* " + methodTable(srcType) +
					" to " + methodTableType(Type.OBJECT) + "*)" +
					")");
			
			writer.write(nextTemp(node) + " = bitcast " + typeText(Type.OBJECT,
					temp(1)) + " to %" + raw(srcType) + "*");		
			
			writer.write(nextTemp() + " = getelementptr inbounds %" +
					raw(srcType) + ", %" + raw(srcType) + "* " + temp(1) + ", i32 0, i32 2");
			writer.write("store " + typeSymbol(source) + ", " +
					typeText(srcType, temp(0), true));
			break;
			
		case OBJECT_TO_PRIMITIVE:
			writer.write(nextTemp() + " = bitcast " + typeSymbol(source) + " to %" + raw(destType) +  "*");
			writer.write(nextTemp() + " = getelementptr inbounds %" +
					raw(destType) + ", %" + 
					raw(destType) + "* " + temp(1) + ", i32 0, i32 2");
			writer.write(nextTemp(node) + " = load " +  type(destType) + ", " + typeTemp(destType, 1, true));			
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
					"_Mallocate_" + Type.INT.toString()) + "(" +
					typeText(Type.CLASS, classOf(Type.INT)) + ", " +
					typeLiteral(arrayType.getDimensions()) + ')');
			writer.write(nextTemp() + " = bitcast " + typeText(Type.OBJECT,
					temp(1)) + " to" + dimsType + '*');
			//cleverly copies all the dimensions at once
			writer.write("store" + dimsType + ' ' + temp(2) + ',' +
					dimsType + "* " + temp(0));
			writer.write(nextTemp() + " = getelementptr inbounds" + dimsType + ", " + 
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
			
			writer.write(nextTemp() + " = bitcast " + typeText(Type.GENERIC_CLASS, symbol(arrayClass.getClassData())) + " to " + type(Type.CLASS));
			
			writer.write(nextTemp() + " = call noalias " +
					type(Type.OBJECT) + " @" + raw(Type.CLASS,
					"_Mallocate") + '(' + typeTemp(Type.CLASS,1) + ", " +
					methodTableType(Type.OBJECT) + "* bitcast(" + methodTableType(genericArray) + "* " +  symbol(arrayClass.getMethodTable()) + " to " + methodTableType(Type.OBJECT) + "*)" + 
					')');
							
			SequenceType arguments = new SequenceType();
			arguments.add(new SimpleModifiedType(new ArrayType(Type.INT), new Modifiers(Modifiers.IMMUTABLE)));
			arguments.add(new SimpleModifiedType(Type.OBJECT));
			MethodSignature arrayCreate = genericArray.getMatchingMethod("create", arguments);
						
			writer.write(nextTemp(node) + " = call " + type(genericArray) + " " +
					name(arrayCreate) + '(' +
					typeTemp(Type.OBJECT, 1) + ", " +						
					typeTemp(new ArrayType(Type.INT), 5) + ", " +
					typeTemp(Type.OBJECT, 3) + ')');
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
			
			if (!srcType.equals(genericArray)) {
				writer.write(nextTemp() + " = bitcast " + typeSymbol(source) + " to " + type(genericArray));
				srcType = genericArray;
				srcName = temp(0);
			}			
			
			dimsType = " [" + arrayType.getDimensions() + " x " +
							type(Type.INT) + ']';
			writer.write(nextTemp() + " = getelementptr inbounds %" + raw(srcType) + ", " +
							typeText(srcType, srcName) + ", i32 0, i32 2");
			writer.write(nextTemp() + " = load " +  type(Type.OBJECT) + ", " + type(Type.OBJECT) +
					"* " + temp(1));
			writer.write(nextTemp() + " = bitcast " + typeTemp(Type.OBJECT,
					1) + " to " + baseType);
			writer.write(nextTemp() + " = insertvalue " + type(destType) +
					" undef, " + baseType + ' ' + temp(1) + ", 0");
			writer.write(nextTemp() + " = getelementptr inbounds %" + raw(srcType) + ", " + 
					typeText(srcType, srcName) + ", i32 0, i32 3, i32 0");
			writer.write(nextTemp() + " = load " + type(Type.INT) + "*, " + type(Type.INT) + "** " +
					temp(1));
			writer.write(nextTemp() + " = bitcast " + type(Type.INT) +
					"* " + temp(1) + " to" + dimsType + '*');
			writer.write(nextTemp() + " = load" + dimsType + ", " + dimsType + "* " +
					temp(1));
			writer.write(nextTemp(node) + " = insertvalue " + typeTemp(destType,
					5) + ',' + dimsType + ' ' + temp(1) + ", 1");
			break;
		case SEQUENCE_TO_SEQUENCE:			
			String current = "undef";
			int index = 0;			
			for (int i = 1; i < node.getNumOperands(); i ++) { //first operand is source
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
	public void visit(TACSequenceElement node ) throws ShadowException {	
		writer.write(nextTemp(node) + " = extractvalue " +
				typeSymbol(node.getOperand(0)) + ", " + node.getIndex());		
	}
	
	@Override
	public void visit(TACCast node) throws ShadowException { 
		Type srcType = node.getOperand().getType(), destType = node.getType();
		
		if (destType.equals(srcType) || destType == Type.NULL ) {
			node.setData(symbol(node.getOperand()));
			return;
		}
		else if( srcType == Type.NULL ) {
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
		
		if( srcType.isPrimitive() && destType.isPrimitive() ) {
			if( srcType.isFloating() ) {
				if( destType.isFloating() )
					instruction = srcWidth > destWidth ? "fptrunc" : (srcWidth < destWidth ? "fpext" : "bitcast");
				else if( destType.isSigned() )
					instruction = "fptosi";
				else
					instruction = "fptoui";
			}
			else if( srcType.isSigned() ) {
				if( destType.isFloating() )
					instruction = "sitofp";
				else if( destType.isSigned() )
					instruction = srcWidth > destWidth ? "trunc" : (srcWidth < destWidth ? "sext" : "bitcast");
				else
					instruction = srcWidth > destWidth ? "trunc" : (srcWidth < destWidth ? "zext" : "bitcast");
			}
			else if( srcType.isUnsigned() ) {
				if( destType.isFloating() )
					instruction = "uitofp";
				else
					instruction = srcWidth > destWidth ? "trunc" : (srcWidth < destWidth ? "zext" : "bitcast");
			}
			else
				instruction = "bitcast"; //should never happen
		}
		else {
			instruction = "bitcast";
			
			//primitive types are in their object forms
			if( srcType.isPrimitive() )
				srcTypeName = type(srcType, true);
			
			if( destType.isPrimitive() )
				destTypeName = type(destType, true);			
		}
		
		writer.write(nextTemp(node) + " = " + instruction + ' ' +
				srcTypeName + ' ' + symbol(node.getOperand()) + " to " + destTypeName);
	}	

	@Override
	public void visit(TACNewObject node) throws ShadowException {				
		Type type = node.getClassType();	
		
		TACOperand _class = node.getClassData();
		TACOperand methods = node.getMethodTable();	
		
		//this is pretty ugly, but if we retrieved a generic type parameter's method table, it'll be stored as an Object*, not an Object_methods*
		if( type instanceof TypeParameter )
			writer.write(nextTemp() + " = bitcast " + type(Type.OBJECT) + " " +  symbol(methods) +  " to "  + methodTableType(Type.OBJECT) + "*");
		//any other method table will be of the correct type but needs cast to Object_methods* for compatibility with allocate()
		else 
			writer.write(nextTemp() + " = bitcast " + methodTableType(type.getTypeWithoutTypeArguments()) + "* " +  symbol(methods) +  " to "  + methodTableType(Type.OBJECT) + "*");
		writer.write(nextTemp(node) + " = call noalias " + type(Type.OBJECT) +
				" @" + raw(Type.CLASS, "_Mallocate") + '(' + type(Type.CLASS) +
				" " + symbol(_class) + ", " + methodTableType(Type.OBJECT) + "* " + temp(1) +
				" )");
	}
	 

	@Override
	public void visit(TACNewArray node) throws ShadowException {
		Type baseType = node.getType().getBaseType();		
		String allocationClass = typeSymbol(node.getBaseClass());
		
		writer.write(nextTemp() + " = call noalias " + type(Type.OBJECT) +
				" @" + raw(Type.CLASS, "_Mallocate_" + Type.INT.toString()) +
			 	'(' + allocationClass + ", " +
				typeSymbol(node.getTotalSize()) + ')');
		writer.write(nextTemp() + " = bitcast " + type(Type.OBJECT) + ' ' +
				temp(1) + " to " + type(baseType) + '*');
		writer.write(nextTemp() + " = insertvalue " + type(node) +
				" zeroinitializer, " + type(baseType) + "* " + temp(1) + ", 0");
		for (int i = 0; i < node.getDimensions(); i++)
			writer.write(nextTemp(node) + " = insertvalue " + type(node) +
					' ' + temp(1) + ", " + typeSymbol(node.getDimension(i)) +
					", 1, " + i);
	}

	@Override
	public void visit(TACLength node) throws ShadowException {
		writer.write(nextTemp(node) + " = extractvalue " +
				typeSymbol(node.getArray()) + ", 1, " + node.getDimension());
	}

	@Override
	public void visit(TACUnary node) throws ShadowException {
		switch (node.getOperation()) {
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
			throws ShadowException {
		writer.write(nextTemp(node) + " = " + instruction + ' ' + typeText(node,
				first) + ", " + symbol(node.getOperand()));
	}	
	
	@Override
	public void visit(TACBinary node) throws ShadowException {	
		switch (node.getOperation().toString()) {
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
			throws ShadowException {
		visitOperation(node, "", "", "f", instruction);
	}
	private void visitSignedOperation(TACBinary node, String instruction)
			throws ShadowException {
		visitOperation(node, "s", "u", "f", instruction);
	}
	private void visitNormalOperation(TACBinary node, String instruction)
			throws ShadowException {
		visitOperation(node, "", "", "", instruction);
	}
	private void visitShiftOperation(TACBinary node, String instruction)
			throws ShadowException {
		visitOperation(node, "a", "l", "", instruction);
	}
	private void visitEqualityOperation(TACBinary node, String instruction)
			throws ShadowException {
		visitOperation(node, "icmp ", "icmp ", "fcmp o", instruction);
	}
	private void visitRelationalOperation(TACBinary node, String instruction)
			throws ShadowException {
		visitOperation(node, "icmp s", "icmp u", "fcmp o", instruction);
	}
	private void visitOperation(TACBinary node, String signed,
			String unsigned, String floatingPoint, String instruction)
			throws ShadowException {
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
	private void visitRotateLeft(TACBinary node) throws ShadowException {
		visitRotate(node, "shl", "lshr");
	}
	private void visitRotateRight(TACBinary node) throws ShadowException {
		visitRotate(node, "lshr", "shl");
	}
	private void visitRotate(TACBinary node, String dir, String otherDir)
			throws ShadowException {
		String type = type(node), _type_ = ' ' + type + ' ',
				first = symbol(node.getFirst()),
				second = symbol(node.getSecond());
		writer.write(nextTemp() + " = " + dir + _type_ + first + ", " + second);
		writer.write(nextTemp() + " = sub" + _type_ + "mul (" + type +
				" ptrtoint (" + type + "* getelementptr (" + type + ", " + type +
				"* null, i32 1) to " + type + ")," + _type_ + "8), " + second);
		writer.write(nextTemp() + " = " + otherDir + _type_ + first + ", " +
				temp(1));
		writer.write(nextTemp(node) + " = or" + _type_ + temp(1) + ", " +
				temp(3));
	}
	
	@Override
	public void visit(TACGlobal node) throws ShadowException {
		writer.write(nextTemp(node) + " = load " + type(node) + ", " + type(node) + "* " + node.getName());
	}
	
	@Override
	public void visit(TACNot node) throws ShadowException {
		writer.write(nextTemp(node) + " = xor " +
				typeSymbol(node.getOperand()) + ", true");
	}
	
	@Override
	public void visit(TACSame node) throws ShadowException {
		Type type = node.getOperand(0).getType();
		
		if( type instanceof ArrayType ) {
			ArrayType arrayType = (ArrayType)type;
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getOperand(0)) + ", 0");
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getOperand(1)) + ", 0");
			writer.write(nextTemp(node) + " = icmp eq" + type(arrayType.getBaseType())
					+ "* " + temp(2) + ", " + temp(1));			
		}
		else if( type instanceof InterfaceType ) {			
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getOperand(0)) + ", 1");
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getOperand(0)) + ", 1");				
			writer.write(nextTemp(node) + " = icmp eq" + type(Type.OBJECT)
					+ temp(2) + ", " + temp(1));		
		}
		else {		
			String op = type.isFloating() ? "fcmp oeq " : "icmp eq " ;
			writer.write(nextTemp(node) + " = " + op + typeSymbol(
					node.getOperand(0)) + ", " + symbol(node.getOperand(1)));
		}
	}

	@Override
	public void visit(TACLoad node) throws ShadowException {
		TACReference reference = node.getReference();		
		
		if( reference instanceof TACGenericArrayRef ) {
			TACGenericArrayRef genericRef = (TACGenericArrayRef) reference;			
			writer.write(nextTemp(node) + " = call " + type(Type.OBJECT) + " @__arrayLoad(" + typeSymbol(Type.OBJECT, genericRef, true) + ", " + 
					typeSymbol(genericRef.getTotal()) + ", " +
					typeSymbol(genericRef.getGenericParameter().getClassData()) + ", " +
					typeSymbol(Type.OBJECT, genericRef.getGenericParameter().getMethodTable()) + ", " +
					typeLiteral(genericRef.isNullable()) + ")");
		}
		else
			writer.write(nextTemp(node) + " = load " + type(reference.getGetType()) + ", " +
					typeSymbol(reference.getGetType(), reference, true));
	}
	
	@Override
	public void visit(TACStore node) throws ShadowException {
		TACReference reference = node.getReference();
		/*
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
		else 
		*/	
		if( reference instanceof TACGenericArrayRef ) {
			TACGenericArrayRef genericRef = (TACGenericArrayRef) reference;			
						
			writer.write("call void @__arrayStore(" + typeSymbol(Type.OBJECT, genericRef, true) + ", " + 
					typeSymbol(genericRef.getTotal()) + ", " + typeSymbol(node.getValue()) + ", " +
					typeSymbol(genericRef.getGenericParameter().getClassData()) + ")");
		}
		else {		
			writer.write("store " + typeSymbol(node.getValue()) + ", " +
				typeSymbol(reference.getSetType(), reference, true));
		}
	}

	@Override
	public void visit(TACPhi node) throws ShadowException {
		TACPhiRef phi = node.getRef();
		StringBuilder sb = new StringBuilder(nextTemp(node)).
				append(" = phi i8*");
		for (int i = 0; i < phi.getSize(); i++)
			sb.append(" [ blockaddress(").append(name(method)).append(", ").
					append(symbol(phi.getValue(i))).append("), ").
					append(symbol(phi.getLabel(i))).append(" ],");
		writer.write(sb.deleteCharAt(sb.length() - 1).toString());
	}

	@Override
	public void visit(TACBranch node) throws ShadowException {
		if (node.isConditional())
			writer.write("br " + typeSymbol(node.getCondition()) + ", label " +
					symbol(node.getTrueLabel()) + ", label " +
					symbol(node.getFalseLabel()));
		else if (node.isDirect())
			writer.write("br label " + symbol(node.getLabel()));
		else if (node.isIndirect()) {
			StringBuilder sb = new StringBuilder("indirectbr i8* ").
					append(symbol(node.getOperand())).append(", [ ");
			TACPhiRef dest = node.getDestination();
			for (int i = 0; i < dest.getSize(); i++)
				sb.append("label ").append(symbol(dest.getValue(i))).
						append(", ");
			writer.write(sb.delete(sb.length() - 2, sb.length()).append(" ]").
					toString());
		}
	}

	@Override
	public void visit(TACLabel node) throws ShadowException {
		writer.writeLeft(name(node) + ':');
	}

	@Override
	public void visit(TACCall node) throws ShadowException {
		TACMethodRef method = node.getMethod();
		StringBuilder sb = new StringBuilder(node.getBlock().hasLandingpad() ?
				"invoke" : "call").append(' ').
				append(methodToString(method, false, false)).append(symbol(method)).append('(');
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
			TACLabelRef label = new TACLabelRef(this.method);			
			writer.indent(2);
			writer.write(" to label " + symbol(label) + " unwind label " +
					symbol(node.getBlock().getLandingpad()));
			visit(label.new TACLabel());
			writer.outdent(2);
		}
	}

	@Override
	public void visit(TACReturn node) throws ShadowException {
		if( method.getMethod().isCreate() ) {
			writer.write(nextTemp() + " = bitcast " + typeText(Type.OBJECT,
					"%0") + " to %" + raw(node.getReturnValue().getType()) + '*');
			writer.write("ret %" + raw(node.getReturnValue().getType()) + "* " + temp(0));
		}
		else if (node.hasReturnValue())
			writer.write("ret " + typeSymbol(node.getReturnValue()));
		else
			writer.write("ret void");
	}

	@Override
	public void visit(TACThrow node) throws ShadowException {
		writer.write((node.getBlock().hasLandingpad() ?
				"invoke" : "call") + " void @__shadow_throw(" +
				typeSymbol(node.getException()) + ") noreturn");
		if (node.getBlock().hasLandingpad()) {
			TACLabelRef label = new TACLabelRef(method);			
			writer.indent(2);
			writer.write(" to label " + symbol(label) + " unwind label " +
					symbol(node.getBlock().getLandingpad()));
			visit(label.new TACLabel());
			writer.outdent(2);
		}
		writer.write("unreachable");
	}	
	
	@Override
	public void visit(TACTypeId node) throws ShadowException {
		if (node.getOperand() instanceof TACUnwind)
			writer.write(nextTemp(node) + " = extractvalue { i8*, i32 } " +
					symbol(node.getOperand()) + ", 1");
		else {
			writer.write(nextTemp() + " = bitcast " +
					typeSymbol(node.getOperand()) + " to i8*");
			writer.write(nextTemp(node) + " = tail call i32 " +
					"@llvm.eh.typeid.for(i8* " + temp(1) + ") nounwind");
		}
	}	

	@Override
	public void visit(TACLandingpad node) throws ShadowException {
		writer.write(nextTemp() + " = landingpad { i8*, i32 }");
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
	public void visit(TACUnwind node) throws ShadowException {
		writer.write(nextTemp(node) + " = load { i8*, i32 }, { i8*, i32 }* %_exception");
		String type = nextTemp();
		writer.write(type + " = extractvalue { i8*, i32 } " + temp(1) + ", 0");
		TACLabelRef label = node.getBlock().getLandingpad();
		for (int i = 0; i < node.getBlock().getNumCatches(); i++) {
			TACCatch catchNode = node.getBlock().getCatchNode(i);
			writer.write(nextTemp() + " = tail call i32 @llvm.eh.typeid.for(" +
					"i8* bitcast (" + type(Type.CLASS) + ' ' +
					classOf(catchNode.getType()) + " to i8*)) nounwind");
			writer.write(nextTemp() + " = icmp ne i32 " + type + ", " +
					temp(1));
			label = new TACLabelRef(method);			
			writer.write("br i1 " + temp(0) + ", label " + symbol(label) +
					", label " + symbol(node.getBlock().getCatch(i)));
			visit(label.new TACLabel());
		}
		// TODO: cleanup???
	}

	@Override
	public void visit(TACCatch node) throws ShadowException {
		writer.write(nextTemp() + " = getelementptr inbounds { i8*, i32 }, { i8*, i32 }* " +
				"%_exception, i32 0, i32 0");
		writer.write(nextTemp() + " = load i8*, i8** " + temp(1));
		writer.write(nextTemp() + " = call " + type(Type.EXCEPTION) +
				" @__shadow_catch(i8* " + temp(1) + ") nounwind");
		writer.write(nextTemp(node) + " = bitcast " + type(Type.EXCEPTION) +
				' ' + temp(1) + " to " + type(node.getType()));
	}
	
	@Override
	public void visit(TACResume node) throws ShadowException {
		writer.write(nextTemp() + " = load { i8*, i32 }, { i8*, i32 }* %_exception");
		writer.write("resume { i8*, i32 } " + temp(0));
	}
	
	private static String interfaceData(Type type) {
		return "@_interfaceData" + type.toString(Type.MANGLE);
	}
	
	private static String interfaces(Type type) {
		return "@_interfaces" + type.toString(Type.MANGLE);
	}
	
	private static String genericInterfaces(Type type) {
		return "@_interfaces" + type.toString(Type.MANGLE | Type.TYPE_PARAMETERS | Type.CONVERT_ARRAYS);
	}

	private String classOf(Type type) {
		if( type.isFullyInstantiated() )
			return '@' + withGenerics(type, "_class");
		else
			return '@' + raw(type, "_class");
	}
	
	private static String methodTable(Type type) {
		if( type instanceof InterfaceType && type.isFullyInstantiated())
			return "@" + withGenerics(type, "_methods");		
		return "@" + raw(type, "_methods");
	}
	
	private static String methodTableType(Type type) {
		return "%" + raw(type, "_methods");
	}
	
	private static String methodType(TACMethodRef method) {
		return methodToString(method, false, true) + '*';
	}
	
	private static String methodType(MethodSignature method) {
		return methodToString(method, false, true) + '*';
	}
	
	private static String methodToString(TACMethod method) {
		return methodToString(method.getMethod(), true, true);
	}
	
	private static String methodToString(MethodSignature signature) {
		return methodToString(signature, true, true);
	}
	
	private static String methodToString(MethodSignature signature, boolean name,
			boolean parameters) {
		StringBuilder sb = new StringBuilder();
		if (name && (/*signature.getModifiers().isPrivate() ||*/ signature.isWrapper()))
			sb.append("private ");
		boolean primitiveOrInterfaceCreate = !signature.getOuter().isSimpleReference() &&
				signature.isCreate();
		
		if( primitiveOrInterfaceCreate ) {			
			if( signature.getOuter() instanceof InterfaceType ) //in situations where an interface contains a create
				sb.append(type(Type.OBJECT));
			else
				sb.append(type(signature.getOuter(), true)); //the nullable looks odd, but it gets the Object version of the primitive
		}
		else {
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
		if (parameters) {
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
	
	private static String methodToString( TACMethodRef method, boolean name,
			boolean parameters ) {
		
		return methodToString( method.getSignature(), name, parameters );
	}

	private static String sizeof( String type ) {
		return "ptrtoint (" + type + " getelementptr (" + type.substring(0, type.length() - 1) + ", " + type +
				" null, i32 1) to i32)";
	}

	private static String type( ModifiedType type ) {
		return type(type.getType(), type.getModifiers().isNullable());
	}
	
	protected static String simplifiedType( ModifiedType type ) {
		return simplifiedType(type.getType(), type.getModifiers().isNullable());
	}
	
	protected static String type( Type type ) {
		return type(type, false);
	}
	
	protected static String simplifiedType( Type type ) {
		return simplifiedType(type, false);
	}
	
	protected static String simplifiedType(Type type, boolean nullable) {
		if (type == null)
			throw new NullPointerException();
		if (type instanceof ArrayType)
			return simplifiedType((ArrayType)type);
		if( type.isPrimitive() )
			if( nullable )
				return type(Type.OBJECT);
			else
				return type(type, false);		
		if (type instanceof ClassType)
			return type(Type.OBJECT);
		if (type instanceof InterfaceType)
			return simplifiedType((InterfaceType)type);
		if (type instanceof TypeParameter)
			return type(Type.OBJECT);		
		throw new IllegalArgumentException("Unknown type.");
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
		if( type instanceof MethodTableType )
			return type((MethodTableType)type);
		throw new IllegalArgumentException("Unknown type.");
	}
	
	protected static String type(ArrayType type) {
		return "{ " + type(type.getBaseType()) + "*, [" + type.getDimensions() +
				" x " + type(Type.INT) + "] }";
	}
	
	protected static String simplifiedType(ArrayType type) {
		return "{ " + simplifiedType(type.getBaseType()) + "*, [" + type.getDimensions() +
				" x " + type(Type.INT) + "] }";
	}
	
	private static String type(SequenceType type) {
		if (type.isEmpty())
			return "void";
		if (type.size() == 1)
			return type(type.get(0));
		StringBuilder sb = new StringBuilder("{ ");
		for (ModifiedType each : type)
			sb.append(type(each)).append(", ");
		return sb.replace(sb.length() - 2, sb.length(), " }").toString();
	}
	
	private static String type(ClassType type, boolean nullable) {		
		if (type.isPrimitive() && !nullable)
			return '%' + type.getTypeName();
		return '%' + type.toString(Type.MANGLE) + '*';
	}
	
	private static String type(InterfaceType type) {
		return "{ " + methodTableType(type) + "*, " + type(Type.OBJECT) + " }";
	}
	
	private static String simplifiedType(InterfaceType type) {
		return "{ " + methodTableType(Type.OBJECT) + "*, " + type(Type.OBJECT) + " }";
	}
	
	private static String type(TypeParameter type) {
		return type(type.getClassBound());
	}
	
	private static String type(MethodTableType type) {
		return '%' + type.toString(Type.MANGLE) + '*';
	}
	
	private static String raw(Type type) {
		return raw(type, "");
	}
	
	private static String raw(Type type, String extra) {
		return type.toString(Type.MANGLE) + extra;
	}	

	private static String withGenerics(Type type, String extra) {
		return type.toString(Type.MANGLE | Type.TYPE_PARAMETERS | Type.CONVERT_ARRAYS) + extra;
	}
	
	public static String declareGeneric(String class_) {
			return class_ + " = external constant %" + raw(Type.GENERIC_CLASS) + System.lineSeparator();
	}
	
	public static String declareArray(String class_) {
		return class_ + " = external constant %" + raw(Type.CLASS) + System.lineSeparator();		
	}
	
	public static String declareGeneric(Type type) {
		if( type instanceof ArrayType )			
			return '@' + withGenerics(type, "_class") + " = external constant %" + raw(Type.CLASS) + System.lineSeparator();
		else
			return '@' + withGenerics(type, "_class") + " = external constant %" + raw(Type.GENERIC_CLASS) + System.lineSeparator();
	}	
	
	public static void addGenerics(String set, Set<? extends String> generics, boolean areArrays, OutputStream out) throws IOException {
		MethodSignature signature = Type.CLASS_SET.getMatchingMethod("add", new SequenceType(Type.CLASS));
		String prefix = "call " + type(signature.getSingleReturnType()) + " " + name(signature) + "(" + typeText(Type.CLASS_SET, set) + ", "; 
		for( String generic : generics ) {
			StringBuilder sb = new StringBuilder("\t" + prefix);
			if( areArrays )
				sb.append(type(Type.CLASS)).append(" ").append(generic).append(")");
			else
				sb.append(type(Type.CLASS)).append(" bitcast (").append(typeText(Type.GENERIC_CLASS, generic)).append(" to ").append(type(Type.CLASS) + "))");		
			
			sb.append(System.lineSeparator());
			
			out.write(sb.toString().getBytes());			
		}		
	}

	private String name(TACLabel label) {
		return symbol(label.getRef()).substring(1);
	}
	
	private static String name(TACVariable var) {
		String name = var.getName();
		if (name == null)
			throw new NullPointerException();
		return name;
	}
	
	private static String name(TACConstantRef constant) {
		return new StringBuilder("@").
				append(raw(constant.getPrefixType(), "_C" + constant.getName())).toString();
	}
	
	private static String name(TACConstant constant) {
		return new StringBuilder("@").
				append(raw(constant.getPrefixType(), "_C" + constant.getName())).toString();
	}

	private static String name(TACMethodRef method) {		
		return name(method.getSignature());	
	}
	
	private static String name(MethodSignature method) {
		return '@' + method.getMangledName();
	}
	
	private static String name(TACMethod method) {
		return name(method.getMethod());
	}

	private static String typeName(TACVariable variable) {
		return typeText(variable, '%' + name(variable), true);
	}

	private static String symbol(TACOperand node) {
		Object symbol = node.getData();
		if (symbol instanceof String)
			return (String)symbol;
		throw new NullPointerException();
	}
	
	private static String typeSymbol(TACOperand node) {
		return typeSymbol(node, node);
	}

	private static String typeSymbol(Type type, TACOperand node) {
		return typeSymbol(type, node, false);
	}
	
	private static String typeSymbol(Type type, TACOperand node, boolean reference) {
		return typeText(type, symbol(node), reference);
	}
	
	private static String typeSymbol(ModifiedType type, TACOperand node) {
		return typeText(type, symbol(node));
	}
	
	private static String typeSymbol(ModifiedType type, TACOperand node,
			boolean reference) {
		return typeText(type, symbol(node), reference);
	}

	private String literal(ShadowValue value) {
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
	
	private static String literal(boolean value) {
		return Boolean.toString(value);
	}
	
	private static String literal(long value) {
		return Long.toString(value);
	}
	
	private static String literal(BigInteger value) {
		return value.toString();
	}
	
	private static String literal(double value) {
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
		}
		return "@_string" + index;
	}
	
	private String typeLiteral(boolean value) {
		return typeText(Type.BOOLEAN, literal(value));
	}
	
	private static String typeLiteral(int value) {
		return typeText(Type.INT, literal(value));
	}
	
	private String typeLiteral(String value) {
		return typeText(Type.STRING, literal(value));
	}
	
	private String typeLiteral(ShadowValue value) {
		return typeText(value, literal(value));
	}

	private String typeTemp(Type type, int offset) {
		return typeText(type, temp(offset));
	}
	
	private String typeTemp(Type type, int offset, boolean reference) {
		return typeText(type, temp(offset), reference);
	}

	private static String typeText(Type type, String name) {
		return combine(type(type), name, false);
	}
	
	private static String typeText(Type type, String name, boolean reference) {
		return combine(type(type), name, reference);
	}
	
	private static String typeText(ModifiedType type, String name) {
		return combine(type(type), name, false);
	}
	
	private static String typeText(ModifiedType type, String name, boolean reference) {
		return combine(type(type), name, reference);
	}

	private static String combine(String type, String name, boolean reference) {
		StringBuilder sb = new StringBuilder(type);
		if (reference)
			sb.append('*');
		return sb.append(' ').append(name).toString();
	}
	
	private void writeArrayClass( ArrayType type ) throws ShadowException {
		Type baseType = type.getBaseType();
		String baseClass;
		if( baseType.isFullyInstantiated() )
			baseClass = typeText(Type.CLASS, "(" + type(Type.GENERIC_CLASS) + " " + classOf(baseType) + " to " + type(Type.CLASS) + ")");
		else
			baseClass = typeText(Type.CLASS, classOf(baseType));
		
		arrayClasses.add("@" + withGenerics(type,  "_class"));
	
		writer.write("@" + withGenerics(type,  "_class") + " = linkonce unnamed_addr constant  %" +
				raw(Type.CLASS) + " { " + 		
				
				typeText(Type.CLASS, classOf(Type.CLASS)) + ", " + //class
				methodTableType(Type.CLASS) + "* " + methodTable(Type.CLASS) + ", " + //methods
				
				typeLiteral(type.toString()) + ", " + //name 
				baseClass + ", "  +//parent 					
				
				typeText(new ArrayType(Type.OBJECT), "zeroinitializer, ") + //data											
				typeText(new ArrayType(Type.CLASS), "zeroinitializer, ") + //interfaces
			
				typeLiteral(ARRAY) + ", " + //flags							
				typeLiteral(type.getDimensions()) + //size

				" }");
	}

	private void writeGenericClass(Type generic) throws ShadowException {				
		Type noArguments = generic.getTypeWithoutTypeArguments();
		List<ModifiedType> parameterList = generic.getTypeParametersIncludingOuterClasses(); 
					
		String interfaceData;
		String interfaces;
		String size;
		
		if( generic instanceof InterfaceType ) {
			interfaceData = interfaces = " zeroinitializer, ";
			size = typeLiteral(-1) + ", ";
		}
		else {
			ArrayList<InterfaceType> interfaceList = generic.getAllInterfaces();
			interfaceData = "{ " + type(Type.OBJECT) + "* getelementptr ([" + interfaceList.size() + " x " + type(Type.OBJECT) + "], [" + interfaceList.size() + " x " + type(Type.OBJECT) + "]* " + interfaceData(noArguments) + ", i32 0, i32 0), [1 x " +
					type(Type.INT) + "] [" + typeLiteral(interfaceList.size()) +	"] }, ";
			interfaces = "{ " + type(Type.CLASS) + "* getelementptr inbounds ([" + interfaceList.size() + " x " +
					type(Type.CLASS) + "], [" + interfaceList.size() + " x " +
					type(Type.CLASS) + "]* " + genericInterfaces(generic) + ", i32 0, i32 0), [1 x " +
					type(Type.INT) + "] [" + typeLiteral(interfaceList.size()) + "] }, ";
			size = typeText(Type.INT, sizeof(type(noArguments))) + ", ";
		}	
		
		//get parent class
		String parentClass;
		if( generic instanceof ClassType  ) {
			ClassType parent = ((ClassType) generic).getExtendType();					
			if( parent.isFullyInstantiated() )
				parentClass = type(Type.CLASS) + " (" + typeText(Type.GENERIC_CLASS, classOf(parent)) + " to " + type(Type.CLASS) + ")";
			else
				parentClass = typeText(Type.CLASS, classOf(parent));			
		}
		else				
			parentClass = type(Type.CLASS) +  " null";				
		
		
		genericClasses.add(classOf(generic));
		
		writer.write(classOf(generic) + " = linkonce unnamed_addr constant  %" +
				raw(Type.GENERIC_CLASS) + " { " + 		
				
				typeText(Type.CLASS, classOf(Type.GENERIC_CLASS)) + ", " + //class
				methodTableType(Type.GENERIC_CLASS) + "* " + methodTable(Type.GENERIC_CLASS) + ", " + //methods
				
				typeLiteral(generic.toString()) + ", " + //name 
				parentClass + ", "  +//parent 					
				
				typeText(new ArrayType(Type.OBJECT), interfaceData) + //data
											
				typeText(new ArrayType(Type.CLASS), interfaces) + //interfaces
			
				typeLiteral(GENERIC) + ", " + //flags							
				size + //size
				
				type(new ArrayType(Type.OBJECT)) + 
				" { " + type(Type.OBJECT) + "* getelementptr inbounds ([" + parameterList.size()*2 + " x " +
				type(Type.OBJECT) + "], [" + parameterList.size()*2 + " x " +
				type(Type.OBJECT) + "]* @_parameters" + generic.toString(Type.MANGLE | Type.TYPE_PARAMETERS | Type.CONVERT_ARRAYS) + ", i32 0, i32 0), [1 x " +
				type(Type.INT) + "] [" + typeLiteral(parameterList.size()*2) + "] }" + //parameters
				
				" }");
		
	}
	
	private void writeGenericClassSupportingMaterial(Type generic) throws ShadowException {				
		StringBuilder sb; 
		boolean first;
		if( generic instanceof ClassType ) {			
			sb = new StringBuilder("[");
			first = true;
			
			for(InterfaceType _interface : generic.getAllInterfaces() ) {		
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
			
			writer.write(genericInterfaces(generic) +
					" = linkonce unnamed_addr constant [" + generic.getAllInterfaces().size() + " x " + type(Type.CLASS) + "] " + sb.toString());
		}				
		
		//write definitions of type parameters
		sb = new StringBuilder("[");					
		first = true;			
		
		List<ModifiedType> parameterList = generic.getTypeParametersIncludingOuterClasses(); 
		for( ModifiedType parameter : parameterList ) {	
			Type parameterType = parameter.getType();
			Type parameterWithoutArguments = parameterType.getTypeWithoutTypeArguments();

			if( first )
				first = false;
			else
				sb.append(", ");			
			
			//arrays need special treatment
			//use an Array<T> type inside of anything except an array
			//inside array (or inner classes of array or iterator), leave it as an array
			if( parameterType instanceof ArrayType &&
				!parameterWithoutArguments.equals(Type.ITERATOR) &&
				!parameterWithoutArguments.equals(Type.ITERATOR_NULLABLE) &&
				!(isArray(generic.getTypeWithoutTypeArguments())) ) {
				parameterType = ((ArrayType)parameterType).convertToGeneric();
				parameterWithoutArguments = parameterType.getTypeWithoutTypeArguments();
			}		
			
			//first put in the class
			sb.append(type(Type.OBJECT)).append(" bitcast (");
			if( parameterType.isFullyInstantiated() )
				sb.append(type(Type.GENERIC_CLASS));
			else
				sb.append(type(Type.CLASS));
			
			sb.append(" ").append(classOf(parameterType)).append(" to ").append(type(Type.OBJECT)).append("), ");
			
			//then the method table
			if( parameterType instanceof ArrayType )
				sb.append(type(Type.OBJECT)).append(" bitcast (" + methodTableType(((ArrayType)parameterType).recursivelyGetBaseType()) + "* " + methodTable(((ArrayType)parameterType).recursivelyGetBaseType()) + " to " + type(Type.OBJECT) + ")" );
			else if( parameterType instanceof InterfaceType )
				sb.append(type(Type.OBJECT)).append(" null"); //no method table for interfaces			
			else
				sb.append(type(Type.OBJECT)).append(" bitcast (" + methodTableType(parameterWithoutArguments) + "* " + methodTable(parameterWithoutArguments) + " to " + type(Type.OBJECT) + ")" );			
		}
		
		sb.append("]");	
		
		writer.write("@_parameters" + generic.toString(Type.MANGLE | Type.TYPE_PARAMETERS | Type.CONVERT_ARRAYS) +
				" = linkonce unnamed_addr constant [" + parameterList.size()*2 + " x " + type(Type.OBJECT) + "] " + sb.toString());
	}
	
	private static boolean isArray(Type type) {
		return Type.ARRAY.equals(type.getTypeWithoutTypeArguments()) || Type.ARRAY_NULLABLE.equals(type.getTypeWithoutTypeArguments());
	}
	
	public Set<String> getGenericClasses() {
		return genericClasses;
	}
	
	public Set<String> getArrayClasses() {
		return arrayClasses;
	}
	
	public void close() throws IOException {
		writer.close();
	}
}
