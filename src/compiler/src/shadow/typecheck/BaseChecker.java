package shadow.typecheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import shadow.Loggers;
import shadow.TypeCheckException;
import shadow.TypeCheckException.Error;
import shadow.AST.ASTWalker.WalkType;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.ASTAssignmentOperator.AssignmentType;
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
import shadow.typecheck.Package.PackageException;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.UninstantiatedClassType;
import shadow.typecheck.type.UninstantiatedInterfaceType;

public abstract class BaseChecker extends AbstractASTVisitor
{	
	public enum SubstitutionType
	{
		ASSIGNMENT, BINDING, TYPE_PARAMETER, INITIALIZATION;
	}
	
	private static final Logger logger = Loggers.TYPE_CHECKER;

	protected ArrayList<TypeCheckException> errorList = new ArrayList<TypeCheckException>();
	protected HashMap<Package, HashMap<String, Type>> typeTable; /** Holds all of the types we know about */
	protected List<String> importList; /** Holds all of the imports we know about */
	protected Package packageTree;	
	protected Package currentPackage;
	protected LinkedList<SignatureNode> currentMethod = new LinkedList<SignatureNode>();  /** Current method is a stack since Shadow allows methods to be defined inside of methods */
		
	protected Type currentType = null;
	protected Type declarationType = null;
	protected boolean debug;	
	
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
				node.setModifiers(childNode.getModifiers()); //copies current and return modifiers
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	protected void pushUpModifiers( SimpleNode node ) //only pushes up modifiers if there is a single child
	{
		if( node.jjtGetNumChildren() == 1 )
		{
			Node child = node.jjtGetChild(0);
			node.setModifiers(child.getModifiers()); //copies current and return modifiers
		}
	}
	
	public static void addError(List<TypeCheckException> errors, Error type, String reason)
	{
		if( errors != null )
			errors.add(new TypeCheckException(type, reason));		
	}	
	
	public static boolean checkAssignment( ModifiedType left, ModifiedType right, AssignmentType assignmentType, SubstitutionType substitutionType, List<TypeCheckException> errors )
	{
		Type leftType = left.getType();		 
		Type rightType = right.getType();		
		
		//process property on right first
		if( rightType instanceof PropertyType )
		{					
			PropertyType propertyType = (PropertyType)rightType;					
			
			if( propertyType.isGettable() )
			{
				right = propertyType.getGetType();
				rightType = right.getType();
			}
			else
			{
				addError(errors, Error.INVALID_ASSIGNMENT, "Property " + propertyType + " is not gettable");
				return false;				
			}
		}
		
		//property on left			
		if( leftType instanceof PropertyType )  
		{					
			PropertyType propertyType = (PropertyType)leftType;					
			
			if( propertyType.isSettable() )
				return checkAssignment( propertyType.getSetType(), right, assignmentType, substitutionType, errors );
			else
			{
				addError(errors, Error.INVALID_ASSIGNMENT, "Property " + propertyType + " is not settable");
				return false;				
			}
		}	
	
		
		//sequence on left
		if( leftType instanceof SequenceType )
		{
			SequenceType sequenceLeft = (SequenceType) leftType;
			
			if( !assignmentType.equals(AssignmentType.EQUAL))
			{
				addError(errors, Error.INVALID_ASSIGNMENT, "Sequence type " + sequenceLeft + " cannot be assigned with any operator other than =");
				return false;
			}			
			
			//if( !sequenceLeft.canAccept(right, substitutionType, errors) )
			//	return false;
			
			//should this return either way?
			
			//Trying this:
			return sequenceLeft.canAccept(right, substitutionType, errors);
		}
		
		
		//type parameter binding follows different rules
		if( substitutionType.equals(SubstitutionType.TYPE_PARAMETER))
		{			
			if( leftType instanceof TypeParameter )
			{
				TypeParameter typeParameter = (TypeParameter) leftType;
				if( !typeParameter.acceptsSubstitution(rightType) )
				{
					addError(errors, Error.INVALID_TYPE_ARGUMENTS, "Cannot substitute type argument " + rightType + " for type argument " + leftType);
					return false;					
				}					
			}
			else
			{
				//will this ever happen?
				addError(errors, Error.INVALID_TYPE_ARGUMENTS, "Cannot substitute type argument " + rightType + " for type " + leftType + " which is not a type parameter");
				return false;				
			}			
		}
		//normal types
		else if( !leftType.canAccept(rightType, assignmentType, errors )  )
			return false;

		
		//check modifiers after types
		Modifiers rightModifiers = right.getModifiers();			
		Modifiers leftModifiers = left.getModifiers();
	
		
		//immutability
		if( leftModifiers.isImmutable() )
		{			
			if( !rightModifiers.isImmutable() && !rightType.getModifiers().isImmutable() && !leftType.getModifiers().isImmutable() )
			//never a problem if either type is immutable (though the left could never be if the right isn't)
			{
				addError(errors, Error.INVALID_ASSIGNMENT, "Right hand side with non-immutable value cannot be assigned to immutable left hand side");
				return false;
			}
			
			if( leftModifiers.isField() )
			{} //do something!  what about readonly fields?
			
			
			/*
			if( leftModifiers.isField() || (!currentMethod.isEmpty() && !currentMethod.getFirst().getMethodSignature().isCreate()))   ))
			
			addError(errorNode, Error.INVL_TYP, "Cannot assign a value to field marked immutable except in a create");
			return false;
			*/
		}
		else
		{
			if( rightModifiers.isImmutable() && !leftModifiers.isReadonly() && !leftType.getModifiers().isImmutable() && !rightType.getModifiers().isImmutable() )
			//never a problem if either type is immutable
			{
				addError(errors, Error.INVALID_ASSIGNMENT, "Right hand side with immutable value cannot be assigned to non-immutable and non-readonly left hand side");
				return false;
			}
			
			//readonly issues
			if( !leftModifiers.isReadonly() ) //and of course not immutable			
			{
				if( rightModifiers.isReadonly() && !rightType.getModifiers().isImmutable() && !rightType.getModifiers().isReadonly() && !leftType.getModifiers().isReadonly() && !leftType.getModifiers().isImmutable() && !rightType.getModifiers().isImmutable() )
				//never a problem if either type is immutable or readonly
				{
					addError(errors, Error.INVALID_ASSIGNMENT, "Right hand side with readonly value cannot be assigned to non-readonly left hand side");
					return false;
				}				
			}
		}
		
		//nullability
		if( !leftModifiers.isNullable() && rightModifiers.isNullable() )
		{
			addError(errors, Error.INVALID_ASSIGNMENT, "Right hand side with nullable value cannot be assigned to non-nullable left hand side");			
			return false;
		}		

		if( substitutionType.equals(SubstitutionType.ASSIGNMENT) ) //only differences between initializations and assignments
		{
			
			if( leftModifiers.isConstant() )
			{
				addError(errors, Error.INVALID_ASSIGNMENT, "Right hand side cannot be assigned to variable marked constant");
				return false;			
			}
			else if( !leftModifiers.isAssignable() )
			{
				//might be non-assignable due to immutable or readonly references
				if( leftModifiers.isImmutable() )
					addError(errors, Error.INVALID_ASSIGNMENT, "Right hand side cannot be assigned in immutable context of expression " + left);
				else if( leftModifiers.isReadonly() )
					addError(errors, Error.INVALID_ASSIGNMENT, "Right hand side cannot be assigned in readonly context of expression " + left);
				else				
					addError(errors, Error.INVALID_ASSIGNMENT, "Right hand side cannot be assigned to non-assignable expression " + left);
				return false;
			}
		}
				
		return true;
	}
	
	protected List<TypeCheckException> isValidInitialization( ModifiedType left, ModifiedType right )
	{		
		List<TypeCheckException> errors = new ArrayList<TypeCheckException>();
		checkAssignment( left, right, AssignmentType.EQUAL, SubstitutionType.INITIALIZATION, errors );
		return errors;
	}
	
	protected List<TypeCheckException> isValidAssignment( ModifiedType left, ModifiedType right, AssignmentType assignmentType)
	{
		List<TypeCheckException> errors = new ArrayList<TypeCheckException>();
		checkAssignment( left, right, assignmentType, SubstitutionType.ASSIGNMENT, errors );
		return errors;		
	}
	
	protected List<TypeCheckException> isValidBinding( ModifiedType left, ModifiedType right )
	{
		List<TypeCheckException> errors = new ArrayList<TypeCheckException>();
		checkAssignment( left, right, AssignmentType.EQUAL, SubstitutionType.BINDING, errors );
		return errors;
	}

	/**
	 * Adds an error message to the list errors we keep until the end.
	 * @param node The node where the error occurred. This will be printed in the standard format.
	 * @param msg The message to communicate to the user.
	 */
	
	protected void addErrors(List<TypeCheckException> errors)
	{		
		if( errors != null )
			for( TypeCheckException error : errors )
				addError( error.getError(), error.getMessage() );
	}
	
	protected void addErrors(Node node, List<TypeCheckException> errors )
	{		
		if( errors != null )
			for( TypeCheckException error : errors )
				addError( node, error.getError(), error.getMessage() );
	}
	
	protected void addError(Node node, Error type, String msg)
	{
		String error = "";

		error += "(" + node.getFile().getName() + ")";
		error += "[" + node.getLine() + ":" + node.getColumn() + "] ";
		
		if( type != null )
			error += type.getName() + ": ";		
		
		error += msg;		
		
		errorList.add(new TypeCheckException(type, error));
	}
	
	/**
	 * Adds an error messages to the list of errors.
	 * @param node The node where the error occurred. This will be printed in standard format.
	 * @param type One of the pre-defined types of errors.
	 * @param msg The message associated with the error.
	 */
	protected void addError(Error type, String msg)
	{
		String error = "";
		
		if( getFile() != null )
		{
			error += "(" + getFile().getName() + ")";
			error += "[" + getLine() + ":" + getColumn() + "] ";
		}
		
		if( type != null )
			error += type.getName() + ": ";		
		
		error += msg;		
		
		errorList.add(new TypeCheckException(type, error));
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
		switch(name)
		{
		case "boolean": return Type.BOOLEAN;
		case "byte":	return Type.BYTE;
		case "code":	return Type.CODE;				
		case "double":	return  Type.DOUBLE;
		case "float":	return  Type.FLOAT;
		case "int":		return Type.INT;
		case "long":	return  Type.LONG;
		case "short":	return  Type.SHORT; 
		case "ubyte":	return  Type.UBYTE;
		case "uint":	return  Type.UINT;
		case "ulong":	return Type.ULONG;
		case "ushort":	return Type.USHORT;
		default: 		return null;
		}
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
			addError(Error.UNDEFINED_TYPE, "Type " + typeName + " not defined in this context");			
			node.setType(Type.UNKNOWN);					
		}
		else
		{			
			if (currentType instanceof ClassType)
				((ClassType)currentType).addReferencedType(type);
		
			if( !classIsAccessible( type, currentType  ) )		
				addError(Error.ILLEGAL_ACCESS, "Class " + type + " not accessible from current context");
			
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
						addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + arguments.toString(true) + " do not match type parameters " + parameters.toString(true) );
						type = Type.UNKNOWN;
					}
				}
				else
				{
					addError(Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments supplied for non-parameterized type " + type);
					type = Type.UNKNOWN;
				}										
			}
			else if( type.isParameterized() ) //parameterized but no parameters!	
			{
				addError(Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for parameterized type " + child.getImage());
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
					addError(Error.UNDEFINED_TYPE, "Type " + child.getImage() + " not defined in this context");			
					type = Type.UNKNOWN;					
				}
				else
				{
					if (currentType instanceof ClassType)
						((ClassType)currentType).addReferencedType(type);
				
					if( !classIsAccessible( type, currentType  ) )		
						addError(Error.ILLEGAL_ACCESS, "Type " + type + " not accessible from this context");
					
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
								addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + arguments.toString(true) + " do not match type parameters " + parameters.toString(true) );
								type = Type.UNKNOWN;
							}
						}
						else
						{
							addError(Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments supplied for non-parameterized type " + type);
							type = Type.UNKNOWN;
						}										
					}
					else if( type.isParameterized() ) //parameterized but no parameters!	
					{
						addError(Error.MISSING_TYPE_ARGUMENTS, "Type arguments are not supplised for parameterized type " + child.getImage());
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
			addError(Error.UNDEFINED_TYPE, "Type " + typeName + " not defined in this context");			
			node.setType(Type.UNKNOWN);					
		}
		else
		{			
			if (currentType instanceof ClassType)
				((ClassType)currentType).addReferencedType(type);
		
			if( !classIsAccessible( type, currentType  ) )		
				addError(Error.ILLEGAL_ACCESS, "Type " + type + " not accessible from this context");
			
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
					addError(Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments supplied for non-parameterized type " + type);
					type = Type.UNKNOWN;
				}										
			}
			else if( type.isParameterized() ) //parameterized but no parameters!	
			{
				addError(Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for parameterized type " + child.getImage());
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
					addError(Error.UNDEFINED_TYPE, "Type " + child.getImage() + " not defined in current context");			
					type = Type.UNKNOWN;					
				}
				else
				{
					if (currentType instanceof ClassType)
						((ClassType)currentType).addReferencedType(type);
				
					if( !classIsAccessible( type, currentType  ) )		
						addError(Error.ILLEGAL_ACCESS, "Type " + type + " not accessible from current context");
					
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
							addError(Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments supplied for non-parameterized type " + type);
							type = Type.UNKNOWN;
						}										
					}
					else if( type.isParameterized() ) //parameterized but no parameters!	
					{
						addError(Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for parameterized type " + child.getImage());
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
