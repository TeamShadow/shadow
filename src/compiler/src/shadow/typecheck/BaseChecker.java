package shadow.typecheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;

import shadow.Loggers;
import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker.WalkType;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.ASTClassOrInterfaceBody;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTUnqualifiedName;
import shadow.parser.javacc.Literal;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SignatureNode;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.ClassChecker.SubstitutionType;
import shadow.typecheck.Package.PackageException;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.UninstantiatedClassType;
import shadow.typecheck.type.UninstantiatedInterfaceType;

public abstract class BaseChecker extends AbstractASTVisitor {
	
	private static final Log logger = Loggers.TYPE_CHECKER;

	protected ArrayList<TypeCheckException> errorList = new ArrayList<TypeCheckException>();
	protected HashMap<Package, HashMap<String, Type>> typeTable; /** Holds all of the types we know about */
	protected List<String> importList; /** Holds all of the imports we know about */
	protected Package packageTree;	
	protected Package currentPackage;
	protected LinkedList<SignatureNode> currentMethod = new LinkedList<SignatureNode>();  /** Current method is a stack since Shadow allows methods to be defined inside of methods */
		
	protected Type currentType = null;
	protected Type declarationType = null;
	protected boolean debug;	
	
	// these are constants for our error messages to keep things consistent
	// TODO: Add more of these
	/*
	public static enum Error {
		INVL_TYP		{ public String toString()  { return "INVALID TYPE"; } },
		MULT_SYM		{ public String toString()  { return "MULTIPLY DEFINED SYMBOL"; } },
		MULT_MTH		{ public String toString()  { return "MULTIPLY DEFINED METHODS"; } },
		UNDEC_VAR		{ public String toString()  { return "UNDECLARED VARIABLE"; } },
		UNDEF_TYP		{ public String toString()  { return "UNDEFINED TYPE"; } },
		TYPE_MIS		{ public String toString()  { return "TYPE MISMATCH"; } },
		INVL_MOD		{ public String toString() { return "INVALID MODIFIER"; } };
	}
	*/
	
	public static enum Error
	{		
		ILLEGAL_ACCESS("Illegal access", "Class, member, method, or property not accessible from this context"),
		INVALID_ARGUMENTS("Invalid arguments", "Supplied method arguments do not match parameters"),
		INVALID_ASSIGNMENT("Invalid assignment", "Right hand side cannot be assigned to left hand side"),
		INVALID_CAST("Invalid cast", "Result type cannot be cast to specified type"),
		INVALID_CREATE("Invalid create", "Target cannot be created"),
		INVALID_DEPENDENCY("Invalid dependency", "Type is dependent on an invalid or unavailable type"),
		INVALID_DEREFERENCE("Invalid dereference", "Nullable reference cannot be dereferenced"),
		INVALID_DESTROY("Invalid destroy", "Target cannot be destroyed"),
		INVALID_EXTEND("Invalid extend", "Type cannot extend given type"),
		INVALID_HIERARCHY("Invalid hierarchy", "Type contains a circular extends or implements definition"),
		INVALID_IMPLEMENT("Invalid implement", "Type cannot implement given type"),
		INVALID_IMPORT("Invalid import", "Type or package cannot be imported"),
		INVALID_INSTANCE("Invalid instance", "Target cannot be instanced"),
		INVALID_METHOD("Invalid method", "Method with specified signature is not defined in this context"),
		INVALID_MODIFIER("Invalid modifier", "Modifier cannot be applied to this declaration"),
		INVALID_OVERRIDE("Invalid override", "Method cannot override parent method"),
		INVALID_PACKAGE("Invalid package", "Package cannot be defined"),
		INVALID_PARAMETERS("Invalid parameters", "Method cannot be declared with the given parameters"),
		INVALID_RETURNS("Invalid returns", "Supplied return values do not match method signature"),
		INVALID_SELF_REFERENCE("Invalid self reference", "Self reference is invalid"),
		INVALID_SINGLETON_CREATE("Invalid singleton create", "Singleton type can only specify a default create"),
		INVALID_STRUCTURE("Invalid structure", "Language construct cannot be used in this way"),
		INVALID_SUBSCRIPT("Invalid subscript", "Subscript is of wrong type or number"),
		INVALID_TRY("Invalid try", "Try statement incorrectly constructed"),
		INVALID_TYPE("Invalid type", "Supplied type cannot be used with this language construct"),
		INVALID_TYPE_ARGUMENTS("Invalid type arguments", "Supplied type arguments do not match type parameters"),
		INVALID_TYPE_PARAMETERS("Invalid type parameters", "Type cannot be parameterized"),
		MISMATCHED_TYPE("Mismatched type", "Supplied type does not match another type supplied to this language construct"),
		MISSING_CREATE("Missing create invocation", "Explicit create invocation is missing, and parent class does not implement the default create"),
		MISSING_INTERFACE("Missing interface", "Type does not implement a required interface"),
		MISSING_TYPE_ARGUMENTS("Missing type arguments", "Type arguments not supplied for parameterized type"),
		MULTIPLY_DEFINED_SYMBOL("Multiply defined symbol", "Symbol cannot be redefined in this context"),
		NO_ACTION("No action", "Statement does not perform an action"),
		NOT_OBJECT("Not object", "Object reference must be used"),
		NOT_TYPE("Not type", "Type name must be used"),
		UNDEFINED_PACKAGE("Undefined package", "Package not defined in this context"),
		UNDEFINED_SYMBOL("Undefined symbol", "Symbol has not been defined in this context"),
		UNDEFINED_TYPE("Undefined type", "Type not defined in this context"),
		UNNECESSARY_TYPE_ARGUMENTS("Unnecessary type arguments", "Type arguments supplied for non-parameterized type"),
		UNREACHABLE_CODE("Unreachable code", "Code cannot be reached");
		
		private final String name;
		private final String message;		
		
		Error( String name, String message )
		{
			this.name = name;
			this.message = message;
		}
		
		public String getName()
		{
			return name;
		}
		
		public String getMessage()
		{
			return message;			
		}
	}	
	
	
	public final HashMap<Package, HashMap<String, Type>> getTypeTable() {
		return typeTable;
	}
	
	public void addType( Type type, Package p  ) throws PackageException {
		p.addType(type); //automatically adds to typeTable and sets type's package				
	}
	
	public final List<String> getImportList() {
		return importList;
	}
	
	public BaseChecker(boolean debug, HashMap<Package, HashMap<String, Type>> hashMap, List<String> importList, Package packageTree  ) {
		this.debug = debug;
		this.typeTable = hashMap;
		this.importList = importList;
		this.packageTree = packageTree;
	}
	
	protected Object pushUpType(SimpleNode node, Boolean secondVisit)
	{
		return pushUpType(node, secondVisit, 0);
	}
	
	protected Object pushUpType(SimpleNode node, Boolean secondVisit, int child) 
	{
		if(secondVisit)
		{
			if( node.jjtGetNumChildren() > child )
			{			
				// push the type up the tree
				Node childNode = node.jjtGetChild(child); 
				node.setType(childNode.getType());
				node.setModifiers( childNode.getModifiers());
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	protected void pushUpModifiers( SimpleNode node ) //only pushes up modifiers if there is a single child
	{
		if( node.jjtGetNumChildren() == 1 )
		{
			Node child = node.jjtGetChild(0);
			node.setModifiers( child.getModifiers() );
		}
	}
	
	
	

	/**
	 * Adds an error message to the list errors we keep until the end.
	 * @param node The node where the error occurred. This will be printed in the standard format.
	 * @param msg The message to communicate to the user.
	 */
	protected void addError(Node node, String msg) {
		addError( node, null, msg );
	}
	
	protected void addErrors(Node node, List<String> messages)
	{		
		if( messages != null )
			for( String message : messages )
				addError( node, message );
	}
	
	/**
	 * Adds an error messages to the list of errors.
	 * @param node The node where the error occurred. This will be printed in standard format.
	 * @param type One of the pre-defined types of errors.
	 * @param msg The message associated with the error.
	 */
	protected void addError(Node node, Error type, String msg) {
		String error = "";
		
		if( node != null )
		{
			if( node.getFile() != null )
				error += "(" + node.getFile().getName() + ")";
			error += "[" + ASTUtils.getLineCol(node) + "] ";
		}
		
		if( type != null )
			error += type.getName();
		
		if( msg != null && msg.length() > 0 )
			error += ": " + msg;
		else
			error += ": " + type.getMessage();
		
		errorList.add(new TypeCheckException(type, error));
	}
	
	/**
	 * Adds an error messages to the list of errors.
	 * @param node The node where the error occurred. This will be printed in standard format.
	 * @param type One of the pre-defined types of errors.
	 */
	protected void addError(Node node, Error type) {
		addError( node, type, "" );
	}
	
	protected void addError(Error type, String message) {
		addError( null, type, message );
	}
	
	
	/**
	 * Print out the list of errors to the given stream.
	 * @param stream The stream to print the errors to.
	 */
	public void printErrors() 
	{
		for(TypeCheckException exception : errorList)
			logger.error(exception.getMessage());		
	}
	
	
	protected final Type lookupTypeFromCurrentMethod( String name )
	{		
		for( Node method : currentMethod )
		{
			MethodType methodType = (MethodType)(method.getType());
			if( methodType.isParameterized() )
			{
				for( ModifiedType modifiedType : methodType.getTypeParameters() )
				{
					Type type = modifiedType.getType();
					if( type instanceof TypeParameter )
					{
						TypeParameter typeParameter = (TypeParameter) type;
						
						if( typeParameter.getTypeName().equals(name))
							return typeParameter;
					}
				}
			}			
		}
		
		/* if( currentType != null )
			return lookupTypeStartingAt( name, currentType );
		else */
		return lookupTypeStartingAt( name, declarationType );
	}
	
	//outer class is just a guess, not a sure thing
	//this method is used when starting from a specific point (as in when looking up extends lists), rather than from the current type
	protected final Type lookupTypeStartingAt( String name, Type outer )
	{	
		Type type = null;
		
		if( name.contains("@"))
		{
			int atSign = name.indexOf('@');
			return lookupType( name.substring(atSign + 1 ), name.substring(0, atSign) );
		}
		else if( outer != null ) 		//try starting points
		{
			type = null;
			Type current = outer;
			
			while( current != null)
			{			
				//check type parameters of outer class
				if( current.isParameterized() )
					for( ModifiedType modifiedParameter : current.getTypeParameters() )
					{
						Type parameter = modifiedParameter.getType();
						
						if( parameter instanceof TypeParameter )
						{
							TypeParameter typeParameter = (TypeParameter) parameter;
							if( typeParameter.getTypeName().equals(name) )
								return typeParameter;
						}
					}				

				if( current instanceof ClassType )
					type = ((ClassType)current).getInnerClass(name);
				
				if( type != null )
					return type;
					
				current = current.getOuter();
			}
		
			//walk up packages from there
			Package p = outer.getPackage();		
			while( p != null)
			{
				type = lookupType( name, p  );
				if( type != null )
					return type;			
				
				p = p.getParent();
			}			
		}
		
		//still not found, try all the packages and types that the outer class imported
		if( outer != null )
			for( Object item : outer.getImportedItems() )
			{
				type = null;
				
				if( item instanceof Package )
				{
					Package importedPackage = (Package) item;
					type = lookupType( name, importedPackage );	
				}
				else if( item instanceof Type )
				{
					Type importedType = (Type) item;
					if( importedType.getTypeWithoutTypeArguments().getTypeName().equals( name ) )
						type = importedType;				
				}
				
				if( type != null )
					return type;			
			}
		
		return null;	
	}
	
	//nothing known, start with current method (looking for type parameters)
	protected Type lookupType( String name )
	{
		if( name.contains("@"))
		{
			int atSign = name.indexOf('@');
			return lookupType( name.substring(atSign + 1 ), name.substring(0, atSign) );
		}
		else
			return lookupTypeFromCurrentMethod( name );
	}		

	//get type from specific package
	protected final Type lookupType( String name, Package p )
	{		
		return typeTable.get(p).get(name);
	}
	
	private final Type lookupType( String name, String packageName )
	{			
		Package p;
		
		if( packageName.equals("default") )
			p = packageTree;
		else
			p = packageTree.getChild(packageName);
		
		if( p == null )
		{
			addError(Error.INVALID_IMPORT, "Package " + packageName + " not defined");
			return null;
		}
		
		HashMap<String, Type> map = typeTable.get(p); 
		return map.get(name);
	}
	
	public int getErrorCount()
	{
		return errorList.size();
	}
	
	public Package getPackageTree()
	{
		return packageTree;
	}
	
	public static ClassType literalToType( Literal literal )
	{
		ClassType type = null;
		switch( literal )
		{
		case BYTE: 		type = Type.BYTE; break;
		case CODE: 		type = Type.CODE; break;
		case SHORT: 	type = Type.SHORT; break;
		case INT: 		type = Type.INT; break;
		case LONG:		type = Type.LONG; break;
		case FLOAT: 	type = Type.FLOAT; break;
		case DOUBLE: 	type = Type.DOUBLE; break;
		case STRING:	type = Type.STRING; break;
		case UBYTE: 	type = Type.UBYTE; break;
		case USHORT: 	type = Type.USHORT; break;
		case UINT: 		type = Type.UINT; break;
		case ULONG: 	type = Type.ULONG; break;
		case BOOLEAN: 	type = Type.BOOLEAN; break;
		case NULL: 		type = Type.NULL; break;
		}			
		return type;
	}
	
	public static ClassType nameToPrimitiveType(String name)
	{
		if( name.equals("boolean") )
			return Type.BOOLEAN;
		else if( name.equals("byte") )
			return Type.BYTE;
		else if( name.equals("code") )
			return Type.CODE;				
		else if( name.equals("double"))
			return  Type.DOUBLE;
		else if( name.equals("float") )
			return  Type.FLOAT;
		else if( name.equals("int") )
			return Type.INT;
		else if( name.equals("long") )
			return  Type.LONG;
		else if( name.equals("short") )
			return  Type.SHORT; 
		else if( name.equals("ubyte") )
			return  Type.UBYTE;
		else if( name.equals("uint") )
			return  Type.UINT;
		else if( name.equals("ulong") )
			return Type.ULONG;
		else if( name.equals("ushort") )
			return Type.USHORT;
		else
			return null;
	}
	

	protected static String stripExtension(String file)
	{
		return file.substring(0, file.lastIndexOf("."));
	}
	
	protected Object typeResolution(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{	
		if( node.getType() != null ) //optimization if type already determined
			return WalkType.NO_CHILDREN;		
		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Node child = node.jjtGetChild(0); 
		String typeName = child.getImage();
		int start = 1;
		
		if( child instanceof ASTUnqualifiedName )
		{					
			child = node.jjtGetChild(start);
			typeName += "@" + child.getImage();
			start++;					
		}
		
		Type type = lookupType(typeName);				
		
		if(type == null)
		{
			addError(node, Error.UNDEFINED_TYPE, "Type " + typeName + " not defined in this context");			
			node.setType(Type.UNKNOWN);					
		}
		else
		{			
			if (currentType instanceof ClassType)
				((ClassType)currentType).addReferencedType(type);
		
			if( !classIsAccessible( type, currentType  ) )		
				addError(node, Error.ILLEGAL_ACCESS, "Class " + type + " not accessible from current context");
			
			if( child.jjtGetNumChildren() == 1 ) //contains arguments
			{
				SequenceType arguments = (SequenceType) child.jjtGetChild(0).getType();						
				if( type.isParameterized() ) 
				{		
					SequenceType parameters = type.getTypeParameters();
					if( parameters.canAccept(arguments, SubstitutionType.TYPE_PARAMETER) )					
						type = type.replace(parameters, arguments);
					else
					{						
						addError( child, Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + arguments.toString(true) + " do not match type parameters " + parameters.toString(true) );
						type = Type.UNKNOWN;
					}
				}
				else
				{
					addError( child, Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments supplied for non-parameterized type " + type);
					type = Type.UNKNOWN;
				}										
			}
			else if( type.isParameterized() ) //parameterized but no parameters!	
			{
				addError(child, Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for parameterized type " + child.getImage());
				type = Type.UNKNOWN;
			}
			
			
			//Container<T, List<String>, String, Thing<K>>:Stuff<U>
			for( int i = start; i < node.jjtGetNumChildren() && type != Type.UNKNOWN; i++ )
			{
				Type outer = type;
				child = node.jjtGetChild(i);
				type = null;
				if( outer instanceof ClassType )
					type = ((ClassType)outer).getInnerClass(child.getImage());
				
				if(type == null)
				{
					addError(node, Error.UNDEFINED_TYPE, "Type " + child.getImage() + " not defined in this context");			
					type = Type.UNKNOWN;					
				}
				else
				{
					if (currentType instanceof ClassType)
						((ClassType)currentType).addReferencedType(type);
				
					if( !classIsAccessible( type, currentType  ) )		
						addError(node, Error.ILLEGAL_ACCESS, "Type " + type + " not accessible from this context");
					
					if( child.jjtGetNumChildren() == 1 ) //contains arguments
					{
						SequenceType arguments = (SequenceType) child.jjtGetChild(0).getType();						
						if( type.isParameterized() ) 
						{		
							SequenceType parameters = type.getTypeParameters();
							if( parameters.canAccept(arguments, SubstitutionType.TYPE_PARAMETER ) )					
								type = type.replace(parameters, arguments);
							else
							{						
								addError( child, Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + arguments.toString(true) + " do not match type parameters " + parameters.toString(true) );
								type = Type.UNKNOWN;
							}
						}
						else
						{
							addError( child, Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments supplied for non-parameterized type " + type);
							type = Type.UNKNOWN;
						}										
					}
					else if( type.isParameterized() ) //parameterized but no parameters!	
					{
						addError(child, Error.MISSING_TYPE_ARGUMENTS, "Type arguments are not supplised for parameterized type " + child.getImage());
						type = Type.UNKNOWN;
					}
				}
			}
			//set the type now that it has type parameters 
			node.setType(type);	
		}	
		
		return WalkType.POST_CHILDREN;
	}
	
	protected Object deferredTypeResolution(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{	
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Node child = node.jjtGetChild(0); 
		String typeName = child.getImage();
		int start = 1;
		
		if( child instanceof ASTUnqualifiedName )
		{					
			child = node.jjtGetChild(start);
			typeName += "@" + child.getImage();
			start++;					
		}
		
		Type type = lookupType(typeName);				
		
		if(type == null)
		{
			addError(node, Error.UNDEFINED_TYPE, "Type " + typeName + " not defined in this context");			
			node.setType(Type.UNKNOWN);					
		}
		else
		{			
			if (currentType instanceof ClassType)
				((ClassType)currentType).addReferencedType(type);
		
			if( !classIsAccessible( type, currentType  ) )		
				addError(node, Error.ILLEGAL_ACCESS, "Type " + type + " not accessible from this context");
			
			if( child.jjtGetNumChildren() == 1 ) //contains arguments
			{
				SequenceType arguments = (SequenceType) child.jjtGetChild(0).getType();						
				if( type.isParameterized() ) 
				{					
					if( type instanceof ClassType )						
						type = new UninstantiatedClassType( (ClassType)type, arguments);
					else if( type instanceof InterfaceType )
						type = new UninstantiatedInterfaceType( (InterfaceType)type, arguments);
				}
				else
				{
					addError( child, Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments supplied for non-parameterized type " + type);
					type = Type.UNKNOWN;
				}										
			}
			else if( type.isParameterized() ) //parameterized but no parameters!	
			{
				addError(child, Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for parameterized type " + child.getImage());
				type = Type.UNKNOWN;
			}			
			
			//Container<T, List<String>, String, Thing<K>>:Stuff<U>
			for( int i = start; i < node.jjtGetNumChildren() && type != Type.UNKNOWN; i++ )
			{
				Type outer = type;
				child = node.jjtGetChild(i);
				type = null;
				if( outer instanceof ClassType )
					type = ((ClassType)outer).getInnerClass(child.getImage());
				
				if(type == null)
				{
					addError(node, Error.UNDEFINED_TYPE, "Type " + child.getImage() + " not defined in current context");			
					type = Type.UNKNOWN;					
				}
				else
				{
					if (currentType instanceof ClassType)
						((ClassType)currentType).addReferencedType(type);
				
					if( !classIsAccessible( type, currentType  ) )		
						addError(node, Error.ILLEGAL_ACCESS, "Type " + type + " not accessible from current context");
					
					if( child.jjtGetNumChildren() == 1 ) //contains arguments
					{
						SequenceType arguments = (SequenceType) child.jjtGetChild(0).getType();						
						if( type.isParameterized() ) 
						{		
							
							if( type instanceof ClassType )						
								type = new UninstantiatedClassType( (ClassType)type, arguments);
							else if( type instanceof InterfaceType )
								type = new UninstantiatedInterfaceType( (InterfaceType)type, arguments);
						}
						else
						{
							addError( child, Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments supplied for non-parameterized type " + type);
							type = Type.UNKNOWN;
						}										
					}
					else if( type.isParameterized() ) //parameterized but no parameters!	
					{
						addError(child, Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for parameterized type " + child.getImage());
						type = Type.UNKNOWN;
					}
				}
			}
			//set the type now that it has type parameters 
			node.setType(type);	
		}	
		
		return WalkType.POST_CHILDREN;
	}
	
	protected static boolean classIsAccessible( Type classType, Type type )
	{
		if( classType.getModifiers().isPublic() || classType.getOuter() == null || classType.getOuter().equals(type)  )
			return true;
		
		Type outer = type.getOuter();
		
		while( outer != null )
		{
			if( outer == classType.getOuter() )
				return true;
			
			outer = outer.getOuter();		
		}
		
		
		if( type instanceof ClassType )
		{
			ClassType parent = ((ClassType)type).getExtendType();
			
			while( parent != null )
			{
				if( classType.getOuter() == parent )
				{
					if( classType.getModifiers().isPrivate())
						return false;
					else
						return true;
				}
				
				outer = parent.getOuter();
				
				while( outer != null )
				{
					if( outer == classType.getOuter() )
						return true;
					
					outer = outer.getOuter();		
				}
				
				parent = parent.getExtendType();
			}
		}
		
		return false;
	}
	
	protected static boolean methodIsAccessible( MethodSignature signature, Type type )
	{		
		Node node = signature.getNode();
		if( node.getEnclosingType() == type || node.getModifiers().isPublic() ) 
			return true;		
		
		if( type instanceof ClassType )
		{
			ClassType parent = ((ClassType)type).getExtendType();
			
			while( parent != null )
			{
				if( node.getEnclosingType() == parent )
				{
					if( node.getModifiers().isPrivate())
						return false;
					else
						return true;
				}
				
				parent = parent.getExtendType();
			}
		}

		return false;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit ) //leaving a type
			currentType = currentType.getOuter();
		else //entering a type
		{					
			currentType = declarationType;
			currentPackage = currentType.getPackage();				
		}
			
		return WalkType.POST_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException
	{			
		if( secondVisit )			
			declarationType = declarationType.getOuter();		
		else
		{
			declarationType = node.getType();
			currentPackage = declarationType.getPackage();
		}
			
		return WalkType.POST_CHILDREN;
	}	
	
	// TypeCollector overrides, because it does something different
	// All other checkers use this
	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit) throws ShadowException 
	{	
		if( !secondVisit )		
			currentPackage = packageTree;
		
		return WalkType.POST_CHILDREN;
	}
}
