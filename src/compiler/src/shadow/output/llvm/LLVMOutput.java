package shadow.output.llvm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowByte;
import shadow.interpreter.ShadowCode;
import shadow.interpreter.ShadowDouble;
import shadow.interpreter.ShadowFloat;
import shadow.interpreter.ShadowInt;
import shadow.interpreter.ShadowInterpreter;
import shadow.interpreter.ShadowLong;
import shadow.interpreter.ShadowShort;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowUByte;
import shadow.interpreter.ShadowUInt;
import shadow.interpreter.ShadowULong;
import shadow.interpreter.ShadowUShort;
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
import shadow.tac.nodes.TACDestination;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLength;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNot;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACPhiRef;
import shadow.tac.nodes.TACPhiRef.TACPhi;
import shadow.tac.nodes.TACPlaceholder;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSame;
import shadow.tac.nodes.TACSequence;
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
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.UnboundMethodType;

public class LLVMOutput extends AbstractOutput
{
	private Process process = null;
	private int tempCounter = 0, labelCounter = 0;
	private List<String> stringLiterals = new LinkedList<String>();
	private HashSet<Generic> generics = new HashSet<Generic>();
	private HashSet<Type> unparameterizedGenerics = new HashSet<Type>();
	private TACModule module;
	private TACMethod method;
	
	public LLVMOutput(File file) throws ShadowException
	{		
		super(new File(file.getParent(),
				file.getName().replace(".shadow", ".ll")));
	}
	
	//used to do an LLVM check pass
	public LLVMOutput(boolean mode) throws ShadowException
	{
		if (!mode)
		{
			try
			{
				process = new ProcessBuilder("opt", "-S", "-O3").start();
			}
			catch (IOException ex)
			{
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
	private static final int INTERFACE = 1, PRIMITIVE = 2, GENERIC = 4, ARRAY = 8, METHOD = 16;
	
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
	
	
	private void writeTypeDeclaration(Type type) throws ShadowException
	{
		StringBuilder sb = new StringBuilder();
		
		if( type instanceof InterfaceType )
		{
			if( type.isFullyInstantiated() )
				sb.append('%').append(withGenerics(type, "_methods")).append(" = type { ");
			else if( type.isUninstantiated() )
				sb.append('%').append(raw(type, "_methods")).append(" = type { ");
			
			writer.write(sb.append(methodList(type.orderAllMethods(), false)).
					append(" }").toString());
		}
		else if (type instanceof ClassType)
		{	
			if( type.isUninstantiated() )
			{
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
	
	private void writeTypeConstants(Type type) throws ShadowException
	{	
		if( type.isParameterizedIncludingOuterClasses() )
		{
			unparameterizedGenerics.add(type.getTypeWithoutTypeArguments());
		
			if( type.isFullyInstantiated() )
			{	
				writer.write('@' + withGenerics(type, "_class") +
						" = external constant %" + raw(Type.CLASS));				
							
				generics.add(new Generic(type));
			}
			else if( type instanceof ClassType && type.isUninstantiated() )
			{
				//methods recorded for classes with no type parameters and for the raw class of those that do
				writer.write('@' + raw(type, "_methods") +
					" = external constant %" + raw(type, "_methods"));
			}
		}
		else
		{
			writer.write('@' + raw(type, "_class") +
					" = external constant %" + raw(Type.CLASS));
			
			if( type instanceof ClassType )
			{
				//methods recorded for classes with no type parameters and for the raw class of those that do
				writer.write('@' + raw(type, "_methods") +
					" = external constant %" + raw(type, "_methods"));
			}	
			
			
			if (type instanceof SingletonType) //never parameterized
				writer.write('@' + raw(type, "_instance") +
						" = external global " + type(type));
		}	
	}
	

	@Override
	public void startFile(TACModule module) throws ShadowException
	{
		this.module = module;
		Type moduleType = module.getType();

		writer.write("; " + module.getQualifiedName());
		writer.write();

		writePrimitiveTypes();
		
		// Methods for exception handling
		writer.write("declare i32 @__shadow_personality_v0(...)");
		writer.write("declare void @__shadow_throw(" + type(Type.OBJECT) + ") noreturn");
		writer.write("declare " + type(Type.EXCEPTION) + " @__shadow_catch(i8* nocapture) nounwind");
		writer.write("declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone");
		//memcopy
		writer.write("declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)");
		writer.write("declare noalias void @free(i8*) nounwind");
		writer.write();
		
		
		//Class fields (since the order is all changed)
		if( moduleType instanceof ClassType )
		{
			ClassType classType = (ClassType) moduleType;
			writer.write("; 0: class (Class)");
			writer.write("; 1: _methods");
			int counter = 2;			
			for (Entry<String, ? extends ModifiedType> field :
				(classType).orderAllFields())
			{	
				
				writer.write("; " + counter + ": " + field.getKey() + " (" + field.getValue().getType() + ")" );
				++counter;				
			}
			
			writer.write();
		}
		
		writeTypeDeclaration(moduleType); //replace the stuff above?
		
		for (TACConstant constant : module.getConstants())
		{
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
		
		HashSet<Type> definedGenerics = new HashSet<Type>();
		for (Type type : module.getReferences())
		{
			if(type != null && !type.equals(module.getType()))		
			{	
				if( !type.isParameterized() )
					writeTypeDeclaration(type);	
				else
				{
					Type unparameterizedType = type.getTypeWithoutTypeArguments();
					
					//write all parameterized interfaces
					if( type.isFullyInstantiated() && type instanceof InterfaceType ) 
						writeTypeDeclaration(type);
					//if unparameterized version has not been declared yet, do it
					if( definedGenerics.add(unparameterizedType) &&
						//keep as mangles the same as?
						!unparameterizedType.equals(module.getType()))
						writeTypeDeclaration(unparameterizedType);
				}
			}
		}
		writer.write();
		
		
		HashSet<InterfaceType> interfaces = moduleType.getAllInterfaces();
		int interfaceCount = interfaces.size();
		
		if( module.isClass() )
		//no need to write interface data for interfaces
		{		
			StringBuilder interfaceData;
			
			//generic classes don't list interfaces (because their parameterized versions have those)
			//but they do share interfaceData (the actual methods)
			if( moduleType.isParameterizedIncludingOuterClasses() )
				interfaceData = new StringBuilder( "@_interfaceData" + moduleType.getMangledName() + " = " );
			else
				interfaceData = new StringBuilder( "@_interfaceData = private " );
					
			interfaceData.append("unnamed_addr constant [").
					append(interfaceCount).append(" x ").
					append(type(Type.OBJECT)).append("] [");
			StringBuilder interfaceClasses = new StringBuilder("@_interfaces = private ");
		
			
			interfaceClasses.append("unnamed_addr constant [").
				append(interfaceCount).append(" x ").
				append(type(Type.CLASS)).append("] [");
				
			int i = 0;
			for(InterfaceType type : interfaces)
			{			
				if (module.isClass())
				{
					List<MethodSignature> methods = type.orderAllMethods(module.
							getClassType());
					String methodsType = methodList(methods, false);
					writer.write("@_class" + i +
							" = private unnamed_addr constant { " + methodsType +
							" } { " + methodList(methods, true) + " }");
					interfaceData.append(type(Type.OBJECT)).append(" bitcast ({ ").
							append(methodsType).append(" }* @_class").append(i).
							append(" to ").append(type(Type.OBJECT)).append("), ");
				}				
				interfaceClasses.append(typeText(Type.CLASS, classOf(type))).
						append(", ");
				i++;
			}
			if (interfaceCount > 0)
			{
				interfaceData.delete(interfaceData.length() - 2,
						interfaceData.length());
				interfaceClasses.delete(interfaceClasses.length() - 2,
						interfaceClasses.length());
			}
			
			writer.write(interfaceData.append(']').toString());
			
			if( !moduleType.isParameterizedIncludingOuterClasses() )	
				writer.write(interfaceClasses.append(']').toString());
		}

		List<MethodSignature> methods = moduleType.orderAllMethods();
		int flags = 0;
		if (moduleType.isPrimitive())
			flags |= PRIMITIVE;
		if (module.isClass())
		{			
			ClassType parentType = ((ClassType)moduleType).getExtendType();
			writer.write('@' + raw(moduleType, "_methods") + " = constant %" +
					raw(moduleType, "_methods") + " { " +					
					methodList(methods, true) + " }");
			
			//nothing will ever be the raw, unparameterized class
			if( !moduleType.isParameterizedIncludingOuterClasses() )
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
					"]* @_interfaceData, i32 0, i32 0), [1 x " + 
					type(Type.INT) + "] [" + typeLiteral(interfaceCount) +
					"] }, " + 
					type(new ArrayType(Type.CLASS)) + " { " + //interfaces
					type(Type.CLASS) + "* getelementptr inbounds ([" +
					interfaceCount + " x " + type(Type.CLASS) +
					"]* @_interfaces, i32 0, i32 0), [1 x " + type(Type.INT) +
					"] [" + typeLiteral(interfaceCount) + "] }, " +
					
					typeLiteral(flags) + ", " +			//flags
					typeText(Type.INT, sizeof('%' + raw(moduleType) + '*')) + //size 
					" }" );
		}
		else
		{
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
					
					/*
					" { " + type(Type.CLASS) + //interfaces
					"* getelementptr inbounds ([" + interfaceCount + " x " +					
					type(Type.CLASS) + "]* @_interfaces" + (moduleType.isParameterized() ? moduleType.getMangledName() : "" ) + 
					", i32 0, i32 0), [1 x " +
					type(Type.INT) + "] [" + typeLiteral(interfaceCount) +
					"] }, " + 
					*/
					
					typeLiteral(flags) + ", " +
					typeLiteral(-1) + //size (unknown for interfaces) 
					" }");
		}
		if (moduleType instanceof SingletonType)
			writer.write('@' + raw(moduleType, "_instance") + " = global " +
					type(moduleType) + " null");
		
		writer.write();

		//HashSet<InterfaceType> referencedInterfaces = new HashSet<InterfaceType>();
		//if( moduleType instanceof InterfaceType && moduleType.isParameterized() )
		//	referencedInterfaces.add((InterfaceType)(moduleType.getTypeWithoutTypeArguments()));
		HashSet<String> recordedTypes = new HashSet<String>();
		Set<Type> references = module.getReferences(); 
		
		for (Type type : references )
		{
			if (type != null && !(type instanceof ArrayType) &&
					!(type instanceof UnboundMethodType) &&
					!type.equals(module.getType()))
			{				
				if( (type.isFullyInstantiated() && recordedTypes.add(type.getMangledNameWithGenerics())) ||  
					(type.isUninstantiated() && recordedTypes.add(type.getMangledName()))	)				
					writeTypeConstants(type);
			}
		}
		writer.write();
	}


	private String methodList(Iterable<MethodSignature> methods, boolean name)
			throws ShadowException
	{
		StringBuilder sb = new StringBuilder();
		for (MethodSignature method : methods)
		{
			TACMethodRef methodRef = new TACMethodRef(method);
			sb.append(", ").append(methodType(methodRef));
			if (name)
				sb.append(' ').append(name(methodRef));
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
	public void endFile(TACModule module) throws ShadowException
	{
		HashSet<String> recordedClasses = new HashSet<String>();
		
		for (Type type : module.getReferences())
			if (type instanceof ClassType && !(type instanceof ArrayType) &&
					!(type instanceof UnboundMethodType) &&
					!type.equals(module.getType()))
		{
			if (type.equals(Type.CLASS))  //add in private methods 
			{
				writer.write("declare noalias " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_Mallocate") + '(' +
						type(Type.CLASS) + ')');
				writer.write("declare noalias " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_Mallocate" +
						Type.INT.getMangledName()) + '(' + type(Type.CLASS) +
						", " + type(Type.INT) + ')');
				writer.write("declare " + type(Type.OBJECT) + " @" +
						raw(Type.CLASS, "_MinterfaceData" +
						Type.CLASS.getMangledName()) + '(' + type(Type.CLASS) +
						", " + type(Type.CLASS) + ')');
			}
			
			if( type.equals(Type.GENERIC_CLASS))
			{
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
			
			if (type.equals(Type.ARRAY)) //add in private method
				writer.write("declare " + type(Type.ARRAY) + " @" +
						raw(Type.ARRAY, "_Mcreate" + new ArrayType(Type.INT).
						getMangledName() + Type.OBJECT.getMangledName()) + '(' +
						type(Type.OBJECT) + ", " + 
						//type(Type.CLASS) + ", " +
						type(new ArrayType(Type.INT)) + ", " +
						type(Type.OBJECT) + ')');
			
			if( type.isUninstantiated() && recordedClasses.add(type.getMangledName()) )
				for (List<MethodSignature> methodList :
						type.getMethodMap().values())
					for (MethodSignature method : methodList)
						if (method.getModifiers().isPublic() ||
							(module.getType().isSubtype(type)  && method.getModifiers().isProtected()))
							writer.write("declare " + methodToString(
									new TACMethod(method).addParameters()));
			writer.write();
		}
		
		/*
		Set<String> usedGenerics = new HashSet<String>();
		Type genericClassArray = new ArrayType(Type.GENERIC_CLASS);
		for( Generic generic : getGenerics() )
		{
			if( usedGenerics.add(generic.getMangledGeneric()) )
				writer.write("@_generics" + generic.getMangledGeneric() + 
						" = external constant " + type(genericClassArray));			
		}
		*/
		
		//print generic possibilities
		Type genericClassArray = new ArrayType(Type.GENERIC_CLASS);
		for( Type type : unparameterizedGenerics )
		{			
			writer.write("@_generics" + type.getMangledName() + 
					" = external constant " + type(genericClassArray));			
		}		
		
		writer.write();

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
					Thread.sleep(11);
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
	public void startMethod(TACMethod method) throws ShadowException
	{
		this.method = method;
		if (module.isInterface())
			return;
		TACMethodRef methodRef = method.getMethod();
		tempCounter = methodRef.getParameterCount() + 1;
		if (methodRef.isNative())
		{
			writer.write("declare " + methodToString(method));
			writer.indent();
		}
		else
		{
			writer.write("define " + methodToString(method) + " {");
			writer.indent();
			for (TACVariable local : method.getLocals())
				writer.write('%' + name(local) + " = alloca " + type(local));
			if (method.hasLandingpad())
				writer.write("%_exception = alloca { i8*, i32 }");
			boolean primitiveCreate = !method.getMethod().getOuterType().
					isSimpleReference() && methodRef.isCreate(), first = true;
			int paramIndex = 0;
			for (TACVariable param : method.getParameters())
			{
				String symbol = '%' + Integer.toString(paramIndex++);
				if (first)
				{
					first = false;
					if (methodRef.isCreate())
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
	public void endMethod(TACMethod method) throws ShadowException
	{
		writer.outdent();
		if (!method.getMethod().isNative() && !module.isInterface())
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

	@Override
	public void visit(TACArrayRef node) throws ShadowException
	{
		writer.write(nextTemp() + " = extractvalue " +
				typeSymbol(node.getArray()) + ", 0");
		writer.write(nextTemp(node) + " = getelementptr inbounds " +
				type(node) + "* " + temp(1) + ", " +
				typeSymbol(node.getTotal()));
	}

	@Override
	public void visit(TACConstantRef node) throws ShadowException
	{
		node.setData(name(node));
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
	public void visit(TACMethodRef node) throws ShadowException
	{
		if (node.getOuterType() instanceof InterfaceType)
		{
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
				//TODO: Change to locked (devirtualization)
				!node.getOuterType().isPrimitive() &&
				//!node.getOuterType().getModifiers().isImmutable() &&
				//!node.getType().getModifiers().isFinal() && //replace with Readonly?
				!node.getType().getModifiers().isPrivate() &&
				!node.isSuper() )
		{	
			writer.write(nextTemp() + " = getelementptr " +
					typeSymbol(node.getPrefix()) + ", i32 0, i32 1");
			
			writer.write(nextTemp() + " = load %" +
					raw(node.getPrefix().getType(), "_methods") + "** " + temp(1));
			
			writer.write(nextTemp() + " = getelementptr %" +
					raw(node.getPrefix().getType(), "_methods") + "* " +
					temp(1) + ", i32 0, i32 " + node.getIndex()); //may need to + 1 to the node.getIndex() if a parent method table is added	
			
			writer.write(nextTemp(node) + " = load " + methodType(node) +
					"* " + temp(1));
		}
		else
			node.setData(name(node));
	}

//	@Override
//	public void visit(TACSequenceRef node) throws ShadowException
//	{
//		TACSequenceRef seq = (TACSequenceRef)var;
//		for (int i = 0; i < seq.size(); i++)
//		{
//			String temp = nextTemp();
//			writer.write(temp + " = extractvalue " + typeName(
//					node.getValue()) + ", " + i);
//			writer.write("store " + type(seq.get(i)) + ' ' + temp + ", " +
//					typeName(seq.get(i), true));
//		}
//	}

	@Override
	public void visit(TACLiteral node) throws ShadowException
	{
		node.setData(literal(node.getValue()));
	}

	@Override
	public void visit(TACClass node) throws ShadowException
	{
		
		node.setData(node.getClassData().getData());
		
		/*
		if (node.hasOperand())
			node.setData(symbol(node.getOperand()));
		else
			node.setData(classOf(node.getClassType()));
		*/
	}
	
	@Override
	public void visit(TACClass.TACClassData node) throws ShadowException
	{
		Type type = node.getClassType();		
		node.setData(classOf(type));
	}
	
	@Override
	public void visit(TACClass.TACMethodTable node) throws ShadowException
	{
		node.setData("@" + raw(node.getClassType().getTypeWithoutTypeArguments(), "_methods"));
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
	
	private String allocatePrimitive(TACOperand node, Type srcType, String srcName) throws ShadowException
	{
		writer.write(nextTemp(node) + " = call noalias " +
				type(Type.OBJECT) + " @" + raw(Type.CLASS,
				"_Mallocate") + '(' + type(Type.CLASS) +
				" @" + raw(srcType, "_class") + ")");
		writer.write(nextTemp() + " = bitcast " + typeText(Type.OBJECT,
				temp(1)) + " to %" + raw(srcType) + "*");
		writer.write(nextTemp() + " = getelementptr inbounds %" +
				raw(srcType) + "* " + temp(1) + ", i32 0, i32 0");
		writer.write("store " + type(Type.CLASS) + " @" + raw(srcType, "_class") + ", " +
				type(Type.CLASS) + "* " + temp(0));
		
		writer.write(nextTemp() + " = getelementptr inbounds %" +
				raw(srcType) + "* " + temp(2) + ", i32 0, i32 1");
		writer.write("store %" + raw(srcType, "_methods") + "* @" +	raw(srcType, "_methods") + ", %" +
				raw(srcType, "_methods") + "** " + temp(0));				
		
		writer.write(nextTemp() + " = getelementptr inbounds %" +
				raw(srcType) + "* " + temp(3) + ", i32 0, i32 2");
		writer.write("store " + typeText(srcType, srcName) + ", " +
				typeText(srcType, temp(0), true));
		return temp(4);
	}

	@Override
	public void visit(TACCast node) throws ShadowException
	{ 
		String srcName = symbol(node.getOperand());
		ModifiedType srcType = node.getOperand(), destType = node;
		if (srcType.getType() instanceof SequenceType ||
				destType.getType() instanceof SequenceType)
		{
			if (srcType.getType() instanceof SequenceType &&
					destType.getType() instanceof SequenceType)
			{
				String current = "undef";
				int index = 0;
				for (ModifiedType type : (SequenceType)destType.getType()) {
					TACOperand value = new TACPlaceholder(
							((SequenceType)srcType.getType()).get(index));
					writer.write(nextTemp(value) + " = extractvalue " +
							typeSymbol(node.getOperand()) + ", " + index);
					TACCast cast = new TACCast(value, type, value);
					walk(value);
					writer.write(nextTemp() + " = insertvalue " + typeText(node,
							current) + ", " + typeSymbol(cast) + ", " + index);
					current = temp(0);
					index++;
				}
				node.setData(current);
			}
			else if (srcType.getType() instanceof SequenceType)
			{
				srcType = ((SequenceType)srcType.getType()).get(0);
				TACOperand value = new TACPlaceholder(srcType);
				writer.write(nextTemp(value) + " = extractvalue " +
						typeSymbol(node.getOperand()) + ", 0");
				TACCast cast = new TACCast(value, destType, value);
				walk(cast);
				node.setData(symbol(cast));
			}
			else
			{
				destType = ((SequenceType)destType.getType()).get(0);
				TACCast cast = new TACCast(destType, node.getOperand());
				walk(cast);
				writer.write(nextTemp() + " = insertvalue " + type(node)
						+ " undef, " + typeSymbol(cast) + ", 0");
				node.setData( temp(0));
			}
			return;
		}
		if (srcType.getType() == Type.NULL)
		{
			if( destType.getType() instanceof ArrayType)
				node.setData("undef");  //Shadow arrays are not pointers, they're structs with pointers and lengths
			else
				node.setData("null");
			return;
		}
		if (destType.getType() == Type.NULL)
		{
			node.setData(srcName);
			return;
		}
		if (srcType.getType() instanceof InterfaceType)
		{
			writer.write(nextTemp() + " = extractvalue " +
					typeSymbol(node.getOperand()) + ", 1");
			srcName = temp(0);
			srcType = new SimpleModifiedType(Type.OBJECT);
		}
		if (destType.getType() instanceof InterfaceType)
		{			
			Type interfaceClass = srcType.getType(); //which class's interface table gets searched			
			if (srcType.getType().isPrimitive())
			{	
				srcName = allocatePrimitive(node, srcType.getType(), srcName);
				srcType = new SimpleModifiedType(Type.OBJECT);				
			}			
			/*else
				srcName = typeSymbol(node.getOperand()); //old thing for below
			*/
			
			TACMethodRef methodRef = new TACMethodRef(
					Type.CLASS.getMethods("interfaceData").get(0));
			TACClass destClass = new TACClass(methodRef, destType.getType(),
					method);
			TACClass srcClass = new TACClass(destClass, interfaceClass,
					method);
			walk(srcClass); //good idea?
			TACCall call = new TACCall(srcClass, new TACBlock(), methodRef,
					srcClass, destClass);
			//walk(destClass); //picks up other stuff? FIX IF NEEDED
			walk(call);
			writer.write(nextTemp() + " = bitcast " + typeSymbol(call) +
					" to %" + raw(destType, "_methods") + '*');
			writer.write(nextTemp() + " = insertvalue " + type(destType) +
					" undef, %" + raw(destType, "_methods") + "* " + temp(1) +
					", 0");
			writer.write(nextTemp() + " = bitcast " +
					typeText(srcType, srcName) + " to " + type(Type.OBJECT));
			/*      ^ used to be typeSymbol(node.getOperand()) */
			writer.write(nextTemp(node) + " = insertvalue " + typeText(destType,
					temp(2)) + ", " + typeTemp(Type.OBJECT, 1) + ", 1");
			return;
		}
		if (srcType.getType().isPrimitive() != destType.getType().isPrimitive())
		{
			if (srcType.getType().isPrimitive())
			{
				srcName = allocatePrimitive(node, srcType.getType(), srcName);
				srcType = new SimpleModifiedType(Type.OBJECT);
			}
			else
			{	// TODO: FIXME: !!!
				writer.write(nextTemp() + " = bitcast " + typeText(srcType,
						srcName) + " to %" + raw(destType) +  "*");
				writer.write(nextTemp() + " = getelementptr inbounds %" +
						raw(destType) + "* " + temp(1) + ", i32 0, i32 2");
				writer.write(nextTemp(node) + " = load " + typeTemp(destType,
						1, true));
				return;
			}
		}
		if (srcType.getType() instanceof ArrayType !=
				destType.getType() instanceof ArrayType)
		{
			if (srcType.getType() instanceof ArrayType)
			{
				ArrayType arrayType = (ArrayType)srcType.getType();
				ClassType arrayWrapper = Type.ARRAY.replace(Type.ARRAY.getTypeParameters(), new SequenceType(arrayType.getBaseType()));
				TACClass arrayClass = new TACClass(arrayWrapper, method);
				walk(arrayClass);
				
				ArrayType intArray = new ArrayType(Type.INT);
				String dimsType = " [" + arrayType.getDimensions() + " x " +
						type(Type.INT) + ']';
				writer.write(nextTemp() + " = extractvalue " + typeText(srcType,
						srcName) + ", 1");				
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
				writer.write(nextTemp() + " = extractvalue " + typeText(srcType,
						srcName) + ", 0");
				writer.write(nextTemp() + " = bitcast " +
						typeTemp(arrayType.getBaseType(), 1, true) + " to " +
						type(Type.OBJECT));
				writer.write(nextTemp() + " = call noalias " +
						type(Type.OBJECT) + " @" + raw(Type.CLASS,
						"_Mallocate") + '(' + typeText(Type.CLASS,
						symbol(arrayClass.getClassData())) + ')');
								
				//copy class
				writer.write(nextTemp() + " = getelementptr inbounds " +
						type(Type.OBJECT) + " " + temp(1) + ", i32 0, i32 0");
				writer.write("store " + type(Type.CLASS) + " " + symbol(arrayClass.getClassData()) + ", " +
						type(Type.CLASS) + "* " + temp(0));
				
				//copy methodtable
				writer.write(nextTemp() + " = getelementptr inbounds " +
						type(Type.OBJECT) + " " + temp(2) + ", i32 0, i32 1");
				writer.write("store %" + raw(Type.OBJECT, "_methods") + "* bitcast(%" + raw(Type.ARRAY, "_methods") + "* " +  symbol(arrayClass.getMethodTable()) + " to %" + raw(Type.OBJECT, "_methods") + "*), " +
						"%" + raw(Type.OBJECT, "_methods") + "** " + temp(0));	
								
				writer.write(nextTemp() + " = call " + type(Type.ARRAY) + " @" +
						raw(Type.ARRAY, "_Mcreate" + new ArrayType(Type.INT).
						getMangledName() + Type.OBJECT.getMangledName()) + '(' +
						typeTemp(Type.OBJECT, 3) + ", " +						
						typeTemp(new ArrayType(Type.INT), 6) + ", " +
						typeTemp(Type.OBJECT, 4) + ')');
				srcType = new SimpleModifiedType(Type.ARRAY);
				srcName = temp(0);
			}
			else
			{
				if (!srcType.getType().equals(Type.ARRAY))
				{
					writer.write(nextTemp() + " = bitcast " + typeText(srcType,
							srcName) + " to " + type(Type.ARRAY));
					srcType = new SimpleModifiedType(Type.ARRAY);
					srcName = temp(0);
				}
				ArrayType arrayType = (ArrayType)destType.getType();
				String baseType = type(arrayType.getBaseType()) + '*',
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
				writer.write(nextTemp() + " = insertvalue " + typeTemp(destType,
						5) + ',' + dimsType + ' ' + temp(1) + ", 1");
				srcType = destType;
				srcName = temp(0);
			}
		}
		if (destType.getType().equals(srcType.getType()))
		{
			node.setData(srcName);
			return;
		}
		
		String instruction;
		int srcWidth = Type.getWidth(srcType),
				destWidth = Type.getWidth(destType);
		
		if( srcType.getType().isFloating() )
		{
			if( destType.getType().isFloating() )
				instruction = srcWidth > destWidth ? "fptrunc" : (srcWidth < destWidth ? "fpext" : "bitcast");
			else if( destType.getType().isSigned() )
				instruction = "fptosi";
			else
				instruction = "fptoui";
		}
		else if( srcType.getType().isSigned() )
		{
			if( destType.getType().isFloating() )
				instruction = "sitofp";
			else if( destType.getType().isSigned() )
				instruction = srcWidth > destWidth ? "trunc" : (srcWidth < destWidth ? "sext" : "bitcast");
			else
				instruction = srcWidth > destWidth ? "trunc" : (srcWidth < destWidth ? "zext" : "bitcast");
		}
		else if( srcType.getType().isUnsigned() )
		{
			if( destType.getType().isFloating() )
				instruction = "uitofp";
			else
				instruction = srcWidth > destWidth ? "trunc" : (srcWidth < destWidth ? "zext" : "bitcast");
		}
		else
			instruction = "bitcast";		
		
		writer.write(nextTemp(node) + " = " + instruction + ' ' +
				typeText(srcType, srcName) + " to " + type(destType));
	}

	
//	@Override
//	public void visit(TACGenericClass node) throws ShadowException
//	{
//		String genericClass = node.getClassObject().getData().toString();		
//		String rawName  = raw(node.getClassType(), "_Mclass");
//		String rawClass = nextTemp();
//		
//		//copy fields from raw class
//		writer.write(rawClass + " = getelementptr %" + rawName + "* @" + rawName + ", i32 0, i32 0");
//		//writer.write(nextTemp() + " = " + ); //get size
//		writer.write(nextTemp() + " = bitcast " + type(Type.CLASS) + " " + rawClass + " to i8*");
//		writer.write(nextTemp() + " = bitcast " + type(Type.GENERIC_CLASS) + " " + genericClass + " to i8*");
//		writer.write("call void @llvm.memcpy.p0i8.p0i8.i32(i8* " + temp(0) + ", i8* " + temp(1) + ", i32 " + sizeof(type(Type.CLASS)) + ", i32 4, i1 0)");
//		
//		//fix flags
//		//flags (int)
//		writer.write(nextTemp() + " = getelementptr " + type(Type.GENERIC_CLASS) + " " + genericClass + ", i32 0, i32 1");
//		writer.write("store " + typeText(Type.INT, "8") + ", " + type(Type.INT) + "* " + temp(0)); 
//				
//		/*		
//		//size (int)
//		writer.write(nextTemp() + " = getelementptr " + type(Type.CLASS) + " " + rawClass + ", i32 0, i32 2");
//		
//		writer.write(nextTemp() + " = getelementptr " + type(Type.GENERIC_CLASS) + " " + genericClass + ", i32 0, i32 2");
//		writer.write("store " + type(Type.INT) + " " + temp(1) + ", " + type(Type.INT) + "* " + temp(0));
//
//		//data (Object[])
//		arrayType = new ArrayType(Type.OBJECT);
//		writer.write(nextTemp() + " = getelementptr " + type(Type.CLASS) + " " + rawClass +
//				", i32 0, i32 3");
//		writer.write(nextTemp() + " = getelementptr " + type(Type.GENERIC_CLASS) + " " + genericClass +
//				", i32 0, i32 3");
//		writer.write("store " + type(arrayType) + " " + temp(1) + ", " + type(arrayType) + "* " + temp(0));
//		
//		//interfaces (Class[])  (going to require additional work for generic interfaces)
//		arrayType = new ArrayType(Type.CLASS);
//		writer.write(nextTemp() + " = getelementptr " + type(Type.CLASS) + " " + rawClass +
//				", i32 0, i32 4");
//		writer.write(nextTemp() + " = getelementptr " + type(Type.GENERIC_CLASS) + " " + genericClass +
//				", i32 0, i32 4");
//		writer.write("store " + type(arrayType) + " " + temp(1) + ", " + type(arrayType) + "* " + temp(0));
//		
//		//name (String) 
//		writer.write(nextTemp() + " = getelementptr " + type(Type.CLASS) + " " + rawClass +
//				", i32 0, i32 5");
//		writer.write(nextTemp() + " = getelementptr " + type(Type.GENERIC_CLASS) + " " + genericClass +
//				", i32 0, i32 5");
//		writer.write("store" + type(Type.STRING) + " " + temp(1) + ", " + type(Type.STRING) + "* " + temp(0));
//		
//		//parent (Class) (also needs additional work for generic parent)
//		writer.write(nextTemp() + " = getelementptr " + type(Type.CLASS) + " " + rawClass +
//				", i32 0, i32 6");
//		writer.write(nextTemp() + " = getelementptr " + type(Type.GENERIC_CLASS) + " " + genericClass +
//				", i32 0, i32 6");
//		writer.write("store " + type(Type.CLASS) + " " + temp(1) + ", " + type(Type.CLASS) + "* " + temp(0));
//		*/
//		
//		String parameters = node.getParameters().getData().toString();
//		
//		//parameters (AbstractClass[])
//		ArrayType arrayType = new ArrayType(Type.CLASS);
//		writer.write(nextTemp() + " = getelementptr " + type(Type.GENERIC_CLASS) + " " + genericClass +
//				", i32 0, i32 7");
//		writer.write("store " + type(arrayType) + " " + parameters + ", " + type(arrayType) + "* " + temp(0));		
//		
//		//rawClass (Class)
//		writer.write(nextTemp() + " = getelementptr " + type(Type.GENERIC_CLASS) + " " + genericClass +
//				", i32 0, i32 8");		
//		writer.write("store " + type(Type.CLASS) + " " + rawClass + ", " + type(Type.CLASS) + "* " + temp(0));	
//	}

	@Override
	public void visit(TACNewObject node) throws ShadowException
	{				
		ClassType type = node.getClassType();	
		
		TACOperand _class = node.getClassData();
		TACOperand methods = node.getMethodTable();	
		
		
		//add something special in for type parameters
		/*if( type.isParameterized() )
		{
			TACGenericClass genericClass = (TACGenericClass) node.getPrevious();
			
			writer.write(nextTemp() + " = bitcast " + type(Type.GENERIC_CLASS) + " " + genericClass.getClassObject().getData() + " to " + type(Type.CLASS) );
			writer.write(nextTemp(node) + " = call noalias " + type(Type.OBJECT) +
					" @" + raw(Type.CLASS, "_Mallocate") + '(' +
					type(Type.CLASS) + " " + temp(1) + ")");			
		}
		else
		{
		*/
		//	writer.write(nextTemp() + " = getelementptr %" + raw(node.getType(),
		//			"_Mclass") + "* @" + raw(node.getType(), "_Mclass") +
		//			", i32 0, i32 0");
		//	writer.write(nextTemp() + " = bitcast " + type(Type.CLASS) + " " + temp(1) + " to " + type(Type.CLASS) );
			writer.write(nextTemp(node) + " = call noalias " + type(Type.OBJECT) +
					" @" + raw(Type.CLASS, "_Mallocate") + '(' + type(Type.CLASS) +
					" " + symbol(_class) + " )");
		//}
		

		//copy class
		writer.write(nextTemp() + " = getelementptr inbounds " +
				type(Type.OBJECT) + " " + symbol(node) + ", i32 0, i32 0");
		writer.write("store " + type(Type.CLASS) + " " +  symbol(_class) + ", " +
				type(Type.CLASS) + "* " + temp(0));
		
		//copy methodtable
		writer.write(nextTemp() + " = getelementptr inbounds " +
				type(Type.OBJECT) + " " + symbol(node) + ", i32 0, i32 1");
		writer.write("store %" + raw(Type.OBJECT, "_methods") + "* bitcast(%" + raw(type.getTypeWithoutTypeArguments(), "_methods") + "* " +  symbol(methods) + " to %" + raw(Type.OBJECT, "_methods") + "*), " +
				"%" + raw(Type.OBJECT, "_methods") + "** " + temp(0));	
		
		/*
		if (!type.equals(Type.OBJECT))
			writer.write(nextTemp(node) + " = bitcast " + type(Type.OBJECT) +
					' ' + temp(3) + " to " + type(type));
		*/
	}
	 

	@Override
	public void visit(TACNewArray node) throws ShadowException
	{
		Type type = node.getType(), baseType = node.getType().getBaseType();
		
		String allocationClass;
		
		//typeText(type, symbol(node));				
		if( baseType instanceof ArrayType )
		{		
			allocationClass  = typeSymbol(node.getBaseClass());
			//allocationClass = typeText(Type.CLASS, "null"); 
			//can probably simplify this to be the same thing either way with manipulation of baseType
			//writer.write(nextTemp() + " = " + sizeof(type(baseType)));
		}
		else
		{			
			allocationClass  = typeSymbol(node.getBaseClass());			
		}
		
		writer.write(nextTemp() + " = call noalias " + type(Type.OBJECT) +
				" @" + raw(Type.CLASS, "_Mallocate" + Type.INT.getMangledName()) +
			 	'(' + allocationClass + ", " +
				typeSymbol(node.getTotalSize()) + ')');
		writer.write(nextTemp() + " = bitcast " + type(Type.OBJECT) + ' ' +
				temp(1) + " to " + type(baseType) + '*');
		writer.write(nextTemp() + " = insertvalue " + type(node.getType()) +
				" undef, " + type(baseType) + "* " + temp(1) + ", 0");
		for (int i = 0; i < node.getDimensions(); i++)
			writer.write(nextTemp(node) + " = insertvalue " + type(type) +
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
		
		String op = type.isFloating() ?
				"fcmp oeq " : "icmp eq " ;

		writer.write(nextTemp(node) + " = " + op + typeSymbol(
				node.getOperand(0)) + ", " + symbol(node.getOperand(1)));
	}

	@Override
	public void visit(TACLoad node) throws ShadowException
	{
		TACReference reference = node.getReference();
		
		writer.write(nextTemp(node) + " = load " +
			typeSymbol(reference.getGetType(), reference, true));
	}
	
	@Override
	public void visit(TACStore node) throws ShadowException
	{
		TACReference reference = node.getReference();
		
		if (reference instanceof TACSequenceRef)
		{
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
		{		
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
	public void visit(TACCall node) throws ShadowException
	{
		TACMethodRef method = node.getMethod();
		StringBuilder sb = new StringBuilder(node.getBlock().hasLandingpad() ?
				"invoke" : "call").append(' ').
				append(methodType(method)).append(' ').
				append(symbol(method)).append('(');
		boolean first = true;
		for (TACOperand param : node.getParameters())
			if (first)
			{
				first = false;			
				sb.append(typeSymbol(param));
			}
			else
				sb.append(", ").append(typeSymbol(param));
		if (!method.getReturnTypes().isEmpty())
			sb.insert(0, nextTemp(node) + " = ");
		writer.write(sb.append(')').toString());
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

	private static String classOf(Type type)
	{		
		
		//if (type instanceof InterfaceType)
		if( type.isFullyInstantiated() )
			return '@' + withGenerics(type, "_class");
		else
			return '@' + raw(type, "_class");
		//return "getelementptr inbounds (%" + raw(type, "_Mclass") + "* @" +
			//	raw(type, "_Mclass") + ", i32 0, i32 0)";
	}
	
	private static String methodTableOf(Type type)
	{
		return '@' + raw(type, "_methods");
	}
	
	

	private static String methodType(TACMethodRef method)
	{
		return methodToString(method, false, true) + '*';
	}
	private static String methodToString(TACMethod method)
	{
		return methodToString(method.getMethod(), true, true);
	}
	private static String methodToString(TACMethodRef method, boolean name,
			boolean parameters)
	{
		StringBuilder sb = new StringBuilder();
		if (name && (method.getModifiers().isPrivate() || method.isWrapper()))
			sb.append("private ");
		boolean primitiveCreate = !method.getOuterType().isSimpleReference() &&
				method.isCreate();
		if (primitiveCreate)
			sb.append('%').append(raw(method.getOuterType())).append('*');
		else
		{
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
					if (primitiveCreate)
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
	}

	private static String sizeof(String type)
	{
		return "ptrtoint (" + type + " getelementptr (" + type +
				" null, i32 1) to i32)";
	}

	private static String type(TACVariable var)
	{
		return type(var.getType());
	}
	private static String type(TACOperand node)
	{
		return type(node.getType());
	}
	private static String type(ModifiedType type)
	{
//		if (!type.getModifiers().isTypeName() &&
//				type.getType() instanceof TypeParameter)
//			return type(Type.CLASS);
		return type(type.getType());
	}
	private static String type(Type type)
	{
		if (type == null)
			throw new NullPointerException();
		if (type instanceof ArrayType)
			return type((ArrayType)type);
		if (type instanceof SequenceType)
			return type((SequenceType)type);
		if (type instanceof ClassType)
			return type((ClassType)type);
		if (type instanceof InterfaceType)
			return type((InterfaceType)type);
		if (type instanceof TypeParameter)
			return type((TypeParameter)type);
		throw new IllegalArgumentException("Unknown type.");
	}
	private static String type(ArrayType type)
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
	private static String type(ClassType type)
	{
		if (type.isPrimitive())
			return '%' + type.getTypeName();
		return "%\"" + type.getMangledName() + "\"*";
	}
	private static String type(InterfaceType type)
	{
		return "{ %" + raw(type, "_methods") + "*, " + type(Type.OBJECT) + " }";
	}
	private static String type(TypeParameter type)
	{
		return type(Type.OBJECT);
	}

	private static String raw(ModifiedType type)
	{
		return raw(type.getType(), "");
	}
	private static String raw(ModifiedType type, String extra)
	{
		return raw(type.getType(), extra);
	}
	private static String raw(Type type)
	{
		return raw(type, "");
	}
	private static String raw(Type type, String extra)
	{
		return '\"' + type.getMangledName() + extra + '\"';
	}
	
	private static String withGenerics(ModifiedType type)
	{
		return withGenerics(type.getType(), "");
	}
	private static String withGenerics(ModifiedType type, String extra)
	{
		return withGenerics(type.getType(), extra);
	}
	private static String withGenerics(Type type)
	{
		return withGenerics(type, "");
	}
	private static String withGenerics(Type type, String extra)
	{
		return '\"' + type.getMangledNameWithGenerics() + extra + '\"';
	}

	/*private static String wrapType(ModifiedType type, ModifiedType wrappedType)
	{
		if (Type.getWidth(type) == Type.getWidth(wrappedType))
			return type(type);
		return '%' + raw(type) + '*';
	}
	private static String wrapType(SequenceType type, SequenceType wrappedType)
	{
		if (type.size() != wrappedType.size())
			throw new IllegalArgumentException();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < type.size(); i++)
			sb.append(", ").append(wrapType(type.get(i), wrappedType.get(i)));
		return sb.replace(0, 2, "{ ").append(" }").toString();
	}*/

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
		for (ModifiedType paramType : method.getType().getParameterTypes())
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
		return typeText(type, symbol(node));
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
		if (value instanceof ShadowByte)
			return literal((long)((ShadowByte)value).getValue());
		if (value instanceof ShadowShort)
			return literal((long)((ShadowShort)value).getValue());
		if (value instanceof ShadowInt)
			return literal((long)((ShadowInt)value).getValue());
		if (value instanceof ShadowLong)
			return literal((long)((ShadowLong)value).getValue());
		if (value instanceof ShadowUByte)
			return literal((long)((ShadowUByte)value).getValue());
		if (value instanceof ShadowUShort)
			return literal((long)((ShadowUShort)value).getValue());
		if (value instanceof ShadowUInt)
			return literal((long)((ShadowUInt)value).getValue());
		if (value instanceof ShadowULong)
			return literal((long)((ShadowULong)value).getValue());
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
	private static String literal(double value)
	{
		long raw = Double.doubleToRawLongBits(value);
		StringBuilder sb = new StringBuilder("0x");
		for (int i = 0; i < 16; i++, raw <<= 4)
			sb.append(Character.forDigit((int)(raw >> 60) & 15, 16));
		return sb.toString();
	}
	private String literal(String value)
	{
		int index = stringLiterals.indexOf(value);
		if (index == -1)
		{
			index = stringLiterals.size();
			stringLiterals.add(value);
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
	private String typeTemp(ModifiedType type, int offset)
	{
		return typeText(type, temp(offset));
	}
	private String typeTemp(ModifiedType type, int offset, boolean reference)
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
	
	public HashSet<Generic> getGenerics()
	{
		return generics;
	}	
	
	public void setGenerics(HashSet<Generic> generics)
	{
		this.generics = generics;
	}
	
	
	public void buildGenerics() throws ShadowException
	{
		writer.write("; Generics");
		writer.write();

		writePrimitiveTypes();
		
		writeTypeDeclaration(Type.OBJECT);		
		writeTypeDeclaration(Type.CLASS);		
		//writeTypeDeclaration(Type.ARRAY_CLASS);
		writeTypeDeclaration(Type.GENERIC_CLASS);
		//writeTypeDeclaration(Type.METHOD_CLASS);
		writeTypeDeclaration(Type.ITERATOR);
		writeTypeDeclaration(Type.STRING);
		
		writeTypeConstants(Type.OBJECT);		
		writeTypeConstants(Type.CLASS);		
		//writeTypeDeclaration(Type.ARRAY_CLASS);
		writeTypeConstants(Type.GENERIC_CLASS);
		//writeTypeDeclaration(Type.METHOD_CLASS);
		writeTypeConstants(Type.ITERATOR);
		writeTypeConstants(Type.STRING);
		
		//contains generics, organized in lists with the unparameterized generic as the key
		Map<String, Set<String>> allGenerics = new HashMap<String, Set<String>>();
		
		Set<String> parameterNames = new HashSet<String>();
		Set<String> types = new HashSet<String>();
		
		types.add(Type.OBJECT.getMangledNameWithGenerics());
		types.add(Type.CLASS.getMangledNameWithGenerics());
		types.add(Type.GENERIC_CLASS.getMangledNameWithGenerics());
		types.add(Type.ITERATOR.getMangledNameWithGenerics());
		types.add(Type.STRING.getMangledNameWithGenerics());
				
		for( Generic generic : generics )
		{				
			if( types.add(generic.getMangledName()) )
			{	
				StringBuilder sb; 
				boolean first;
				
				if( !generic.isInterface() )
				{
					if( !allGenerics.containsKey(generic.getMangledGeneric()) )					
					{
						allGenerics.put(generic.getMangledGeneric(), new HashSet<String>());
						writer.write("@_interfaceData" + generic.getMangledGeneric() + " = external constant [" + generic.getInterfaces().size() + " x " + type(Type.OBJECT) + "]");
						writer.write(generic.getTypeLayout());
					}
					
					sb = new StringBuilder("[");
					first = true;
					
					for(String _interface : generic.getInterfaces() )
					{		
						if( first )
							first = false;
						else
							sb.append(", ");

						sb.append(type(Type.CLASS)).append(" ");

						if( _interface.contains("_L_"))	
							sb.append("bitcast (" + type(Type.GENERIC_CLASS) + " @\"" + _interface + "_class\" to " + type(Type.CLASS) + ")");
						else
							sb.append("@\"" + _interface + "_class\"");							
					}
					
					sb.append("]");					
					
					writer.write("@_interfaces" + generic.getMangledName() +
							" = private constant [" + generic.getInterfaces().size() + " x " + type(Type.CLASS) + "] " + sb.toString());
				}
				else
				{
					if( !allGenerics.containsKey(generic.getMangledGeneric()) )
						allGenerics.put(generic.getMangledGeneric(), new HashSet<String>());					
				}
				
				allGenerics.get(generic.getMangledGeneric()).add(generic.getMangledName());
				
				
				//write type parameters
				sb = new StringBuilder("[");					
				first = true;
				
				for(String parameter : generic.getParameters() )
				{		
					if( first )
						first = false;
					else
						sb.append(", ");
											
					if( parameter.contains("_L_"))
					{
						String trimmed = parameter.substring(0,  parameter.indexOf("_L_"));
						sb.append(type(Type.OBJECT)).append(" bitcast (" + type(Type.GENERIC_CLASS) + " @\"" + parameter + "_class\" to " + type(Type.OBJECT) + "), " );
						sb.append(type(Type.OBJECT)).append(" bitcast (%\"" + trimmed + "_methods\"* @\"" + trimmed + "_methods\" to " + type(Type.OBJECT) + ")" );
						parameterNames.add(trimmed);
					}						
					else
					{
						sb.append(type(Type.OBJECT)).append(" bitcast (" + type(Type.CLASS) + " @\"" + parameter + "_class\" to " + type(Type.OBJECT) + "), " );
						sb.append(type(Type.OBJECT)).append(" bitcast (%\"" + parameter + "_methods\"* @\"" + parameter + "_methods\" to " + type(Type.OBJECT) + ")" );
						parameterNames.add(parameter);
					}
					
				}
				
				sb.append("]");	
				
				writer.write("@_parameters" + generic.getMangledName() +
						" = private constant [" + generic.getParameters().size()*2 + " x " + type(Type.OBJECT) + "] " + sb.toString());
									
				String interfaceData;
				String interfaces;
				String size;
				
				if( generic.isInterface() )
				{
					interfaceData = interfaces = " zeroinitializer, ";
					size = typeLiteral(-1) + ", ";
				}
				else
				{
					interfaceData = " { " + type(Type.OBJECT) + "* getelementptr ([" + generic.getInterfaces().size() + " x " + type(Type.OBJECT) + "]* @_interfaceData" + generic.getMangledGeneric() + ", i32 0, i32 0), [1 x " +
							type(Type.INT) + "] [" + typeLiteral(generic.getInterfaces().size()) +	"] }, ";
					interfaces = " { " + type(Type.CLASS) + "* getelementptr inbounds ([" + generic.getInterfaces().size() + " x " +
							type(Type.CLASS) + "]* @_interfaces" + generic.getMangledName() + ", i32 0, i32 0), [1 x " +
							type(Type.INT) + "] [" + typeLiteral(generic.getInterfaces().size()) + "] }, ";
					size = typeText(Type.INT, sizeof('%' + generic.getMangledGeneric() + '*')) + ", ";
				}	
				
				
				writer.write("@\"" + generic.getMangledName() + "_class\"" + " = constant %" +
						raw(Type.GENERIC_CLASS) + " { " + 		
						
						type(Type.CLASS) + " @" + raw(Type.GENERIC_CLASS, "_class") + ", " + //class
						"%" + raw(Type.GENERIC_CLASS, "_methods") + "* @" + raw(Type.GENERIC_CLASS, "_methods") + ", " + //methods
						
						typeLiteral(generic.getName()) + ", " + //name 
						type(Type.CLASS) +  " " + generic.getParent() + ", "  +//parent 					
						
						type(new ArrayType(Type.OBJECT)) + 
						interfaceData + //data
													
						type(new ArrayType(Type.CLASS)) + 
						interfaces + //interfaces
					
						typeLiteral(generic.getMangledGeneric().equals(Type.ARRAY.getMangledName()) ? GENERIC | ARRAY : GENERIC) + ", " + //flags							
						size + //size
						
						type(new ArrayType(Type.OBJECT)) + 
						" { " + type(Type.OBJECT) + "* getelementptr inbounds ([" + generic.getParameters().size()*2 + " x " +
						type(Type.OBJECT) + "]* @_parameters" + generic.getMangledName() + ", i32 0, i32 0), [1 x " +
						type(Type.INT) + "] [" + typeLiteral(generic.getParameters().size()*2) + "] }" + //parameters
						" }");
			}
		}		
		
		writer.write();
		
		
		for( Entry<String, Set<String>> entry : allGenerics.entrySet() )
		{
			int size = entry.getValue().size();
			if( size > 0 )
			{
				StringBuilder sb = new StringBuilder("@_data" + entry.getKey() +
						" = private constant [" + size + " x " + type(Type.GENERIC_CLASS) + "] [");
				
				boolean first = true;
				
				for( String parameterized : entry.getValue() )
				{
					if( first )
						first = false;
					else
						sb.append(", ");
					
					sb.append(type(Type.GENERIC_CLASS));
					sb.append(" @\"" + parameterized + "_class\"");				
				}
				
				sb.append("]");						
						
				
				writer.write(sb.toString());
				writer.write("@_generics" + entry.getKey() +
						" = constant " + type(new ArrayType(Type.GENERIC_CLASS)) +
						" { " + type(Type.GENERIC_CLASS) +  "* getelementptr inbounds ([" + size + " x "  + type(Type.GENERIC_CLASS) + "]* " +
						"@_data" + entry.getKey() + ", i32 0, i32 0), [1 x " + type(Type.INT) + "] [" + type(Type.INT) + " " + size + "] }");
			}
		}
		
		writer.write();		
		
		for( String parameter : parameterNames)
		{
			if( !types.contains(parameter) )
			{
				writer.write("@\"" + parameter + "_class\" = external constant %" + raw(Type.CLASS));
				writer.write("%\"" + parameter + "_methods\" = type opaque");				
				writer.write("@\"" + parameter + "_methods\" = external constant %\"" + parameter + "_methods\"");
			}
		}
		
		writer.write();
		
		writeStringLiterals();		
	}	
}
