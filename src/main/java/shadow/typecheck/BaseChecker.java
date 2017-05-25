/*
 * Copyright 2015 Team Shadow
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 	
 * 	    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package shadow.typecheck;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import shadow.ShadowException;
import shadow.parse.Context;
import shadow.parse.Context.AssignmentKind;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowVisitorErrorReporter;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.SubscriptType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

/**
 * The <code>BaseChecker</code> class is an abstract class that other type-checking
 * classes can inherit from. It stores a tree of all packages containing all types;
 * references to the current package, the current type, and the current method;
 * and lists of errors and warnings.
 * 
 * @author Barry Wittman
 * @author William R. Speirs
 */
public abstract class BaseChecker extends ShadowVisitorErrorReporter {
	
	/** 
	 * Kinds of substitution possible.
	 * Each has its own type-checking rules.
	 */
	public enum SubstitutionKind {
		/** Substitution when assigning a value to a variable. */
		ASSIGNMENT,
		/** Substitution when binding an argument to a method parameter. */
		BINDING,
		/** Substitution of a type argument to a type parameter. */
		TYPE_PARAMETER,
		/**
		 * Substitution when initializing a variable.
		 * Similar to assignment but with somewhat relaxed rules. */
		INITIALIZATION;
	}
	
	protected final Package packageTree;
	// Current method is a stack since Shadow allows methods to be defined inside of methods.
	protected final LinkedList<Context> currentMethod = new LinkedList<Context>();

	protected Package currentPackage;
	protected Type currentType = null;	
	// Declaration type differs from current type in the header (before the type's body is entered).
	protected Type declarationType = null;

	/**
	 * Creates a new <code>BaseChecker</code> with the given tree of packages.
	 * @param packageTree root of all packages
	 */
	public BaseChecker( Package packageTree, ErrorReporter reporter ) {		
		super( reporter );
		this.packageTree = packageTree;
	}
	
	/**
	 * Clears out the data structures within the checker,
	 * returning it to a state similar to just after construction.
	 * Child classes should call this method via <code>super.clear()</code>
	 * when overriding. 
	 */
	public void clear() {		
		getErrorReporter().clearErrors();
		currentPackage = null;
		currentMethod.clear();
		currentType = null;
		declarationType = null;
		packageTree.clear();
	}	
	
	/**
	 * Determines whether a a right-hand type can be substituted into a left-hand type.
	 * This method is the central checker for all assignments, bindings, and initializations.
	 * @param left				type of left-hand side, including modifiers
	 * @param right				type of right-hand side, including modifiers
	 * @param assignmentKind	kind of assignment: =, +=, #=, etc.
	 * @param substitutionKind	kind of substitution: assignment, binding, type parameter, etc.
	 * @param errors			list of errors to add to if assignment is not legal
	 * @return					if assignment is legal
	 */
	public static boolean checkSubstitution( ModifiedType left, ModifiedType right, AssignmentKind assignmentKind, SubstitutionKind substitutionKind, List<ShadowException> errors ) {
		Type leftType = left.getType();		 
		Type rightType = right.getType();		
		
		// If necessary, process the property or subscript on right type.
		if( rightType instanceof PropertyType ) {					
			PropertyType getSetType = (PropertyType)rightType;					
			
			if( getSetType.isGettable() ) { // "Get" from the type, if allowed			
				right = getSetType.getGetType();
				rightType = right.getType();
			}
			else { // Fail otherwise			
				String kind = (rightType instanceof SubscriptType) ? "Subscript " : "Property ";				
				ErrorReporter.addError( errors, Error.INVALID_ASSIGNMENT, kind + getSetType +
						" cannot be loaded", rightType );
				return false;				
			}
		}
	
		// If the left type is a property or subscript, try to apply the right into it.			
		if( leftType instanceof PropertyType )  {			
			PropertyType propertyType = (PropertyType)leftType;
			List<ShadowException> errorList = propertyType.applyInput( right );
						
			if( errorList.isEmpty() )
				return checkSubstitution( propertyType.getSetType(), right, assignmentKind, substitutionKind, errors );
			else {
				errors.addAll( errorList );
				return false;
			}		
		}

		// Sequence on left
		if( leftType instanceof SequenceType ) {
			SequenceType sequenceLeft = (SequenceType)leftType;	
			// Compound assignments not allowed for sequences
			if( !assignmentKind.equals( AssignmentKind.EQUAL ) ) { 
				ErrorReporter.addError( errors, Error.INVALID_ASSIGNMENT, "Sequence type " + sequenceLeft +
						" cannot be assigned with any operator other than =" );
				return false;
			}			

			return sequenceLeft.canAccept( right, substitutionKind, errors );
		}
		
		// Do not allow assignment to singleton references
		if( leftType instanceof SingletonType ) {
			ErrorReporter.addError( errors, Error.INVALID_ASSIGNMENT,
					"Singleton reference cannot be assigned to" );
			return false;
		}
		
		
		// Type parameter binding follows its own rules
		if( substitutionKind.equals( SubstitutionKind.TYPE_PARAMETER ) ) {			
			if( leftType instanceof TypeParameter ) {
				TypeParameter typeParameter = (TypeParameter) leftType;
				if( !typeParameter.acceptsSubstitution(rightType) ) {
					ErrorReporter.addError(errors, Error.INVALID_TYPE_ARGUMENTS,
							"Cannot substitute type argument " + rightType +
							" for type argument " + leftType, rightType);
					return false;					
				}					
			}
			else {
				// Should never happen
				ErrorReporter.addError( errors, Error.INVALID_TYPE_ARGUMENTS,
						"Cannot substitute type argument " + rightType +	" for type " +
						leftType + " which is not a type parameter", rightType, leftType );
				return false;				
			}			
		}
		// Normal types
		else if( !leftType.canAccept( rightType, assignmentKind, errors )  )
			return false;
		
		// Check modifiers after types
		Modifiers rightModifiers = right.getModifiers();			
		Modifiers leftModifiers = left.getModifiers();	
		
		// Immutability
		if( leftModifiers.isImmutable() ) {			
			if( !rightModifiers.isImmutable() && !rightType.getModifiers().isImmutable() &&
					!leftType.getModifiers().isImmutable() ) {
				// Never a problem if either type is immutable
				ErrorReporter.addError( errors, Error.INVALID_ASSIGNMENT,
						"Right hand side with non-immutable value cannot be assigned to immutable left hand side",
						rightType, leftType);
				return false;
			}
		}
		else {
			if( rightModifiers.isImmutable() && !leftModifiers.isReadonly() &&
					!leftType.getModifiers().isImmutable() &&
					!rightType.getModifiers().isImmutable() ) {
				// Never a problem if either type is immutable			
				ErrorReporter.addError( errors, Error.INVALID_ASSIGNMENT,
						"Right hand side with immutable value cannot be assigned to non-immutable and non-readonly left hand side",
						rightType, leftType);
				return false;
			}
			
			// Readonly issues
			if( !leftModifiers.isReadonly() ) { // and of course not immutable
				if( rightModifiers.isReadonly() && !rightType.getModifiers().isImmutable() &&
						!rightType.getModifiers().isReadonly() &&
						!leftType.getModifiers().isReadonly() &&
						!leftType.getModifiers().isImmutable() &&
						!rightType.getModifiers().isImmutable() ) {
				// Never a problem if either type is immutable or readonly				
					ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
							"Right hand side with readonly value cannot be assigned to non-readonly left hand side",
							rightType, leftType);
					return false;
				}				
			}
		}

		// Nullability
		boolean leftArray = leftType instanceof ArrayType;
		boolean rightArray = rightType instanceof ArrayType;
		
		/* 
		 * Arrays (and their object forms) are tricky.
		 * For example, a non-nullable array cannot be assigned to a nullable array. 
		 */
		if( leftArray && rightArray ) {
			if( leftModifiers.isNullable() && !rightModifiers.isNullable() )
				ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
						"Right hand side with non-nullable array type cannot be assigned to nullable left hand side",
						rightType, leftType);
			else if( !leftModifiers.isNullable() && rightModifiers.isNullable() )
				ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
						"Right hand side with nullable value cannot be assigned to non-nullable left hand side",
						rightType, leftType);
		}
		else if( leftArray ) {
			if( leftModifiers.isNullable() &&
					!rightType.getTypeWithoutTypeArguments().equals( Type.ARRAY_NULLABLE ) )
				ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
						"Right hand side with non-nullable array type cannot be assigned to nullable left hand side",
						rightType, leftType);
			else if( !leftModifiers.isNullable() &&
					rightType.getTypeWithoutTypeArguments().equals( Type.ARRAY_NULLABLE ) )
				ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
						"Right hand side with nullable value cannot be assigned to non-nullable left hand side",
						rightType, leftType);
			else if( !leftModifiers.isNullable() && rightModifiers.isNullable() )
				/* Weird case:
				 * nullable NullableArray<int> x = method();
				 * nullable int[] array = x;
				 */  
				ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
						"Right hand side with nullable object value cannot be assigned to nullable array left hand side without a check",
						rightType, leftType);
		}
		else if( rightArray ) {
			if( leftType.getTypeWithoutTypeArguments().equals( Type.ARRAY_NULLABLE ) &&
					!rightModifiers.isNullable() )
				ErrorReporter.addError( errors, Error.INVALID_ASSIGNMENT,
						"Right hand side with non-nullable array type cannot be assigned to nullable left hand side",
						rightType, leftType);
			else if( !leftType.getTypeWithoutTypeArguments().equals( Type.ARRAY_NULLABLE ) &&
					rightModifiers.isNullable() )
				ErrorReporter.addError( errors, Error.INVALID_ASSIGNMENT,
						"Right hand side with nullable value cannot be assigned to non-nullable left hand side",
						rightType, leftType );
		}
		else { // No arrays (easy case)			
			if( !leftModifiers.isNullable() && rightModifiers.isNullable() ) {
				ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
						"Right hand side with nullable value cannot be assigned to non-nullable left hand side",
						rightType, leftType);			
				return false;
			}			
		}				

		// These cases are the only differences between initializations and assignments.
		if( substitutionKind.equals( SubstitutionKind.ASSIGNMENT ) ) {			
			if( leftModifiers.isConstant() ) {
				ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
						"Right hand side cannot be assigned to variable marked constant",
						rightType, leftType);
				return false;			
			}
			else if( !leftModifiers.isAssignable() ) {
				// Might be non-assignable due to immutable or readonly references
				if( leftModifiers.isImmutable() )
					ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
							"Right hand side cannot be assigned in immutable context of expression "
							+ left, rightType, leftType);
				else if( leftModifiers.isReadonly() )
					ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
							"Right hand side cannot be assigned in readonly context of expression "
							+ left, rightType, leftType);
				else				
					ErrorReporter.addError(errors, Error.INVALID_ASSIGNMENT,
							"Right hand side cannot be assigned to non-assignable expression " +
							left, rightType, leftType);
				return false;
			}
		}
				
		return true;
	}
	
	/**
	 * Determines whether a left-hand type can be initialized with a right-hand type.
	 * @param left				type of left-hand side, including modifiers
	 * @param right				type of right-hand side, including modifiers
	 * @return					list of errors (empty if valid)
	 */
	protected final static List<ShadowException> isValidInitialization( ModifiedType left,
			ModifiedType right ) {		
		List<ShadowException> errors = new ArrayList<ShadowException>();
		checkSubstitution( left, right, AssignmentKind.EQUAL,
				SubstitutionKind.INITIALIZATION, errors );
		return errors;
	}
	
	/**
	 * Determines whether a left-hand type can be assigned with a right-hand type.
	 * @param left				type of left-hand side, including modifiers
	 * @param right				type of right-hand side, including modifiers
	 * @return					list of errors (empty if valid)
	 */	
	protected final static List<ShadowException> isValidAssignment( ModifiedType left,
			ModifiedType right, AssignmentKind assignmentType ) {
		List<ShadowException> errors = new ArrayList<ShadowException>();
		checkSubstitution( left, right, assignmentType, SubstitutionKind.ASSIGNMENT, errors );
		return errors;		
	}

	/**
	 * Tries to find a type within a particular outer type, but the outer type is just a guess.
	 * This method is used when starting from a specific point (as in when looking through
	 * <code>is</code> lists), rather than from the current type.
	 * @param name		name of the type
	 * @param outer		outer type
	 * @return			type found or <code>null</code> if not found
	 */	
	protected final Type lookupTypeStartingAt( Context ctx, String name, Type outer ) {	
		Type type = null;		
		
		if( name.contains("@")) { // Fully qualified type name
			int atSign = name.indexOf('@');
			return lookupType(ctx, name.substring(atSign + 1 ), name.substring(0, atSign) );
		}
		else if( outer != null ) { // Check type parameters of outer type			
				
			// First check type parameters of outer class
			// Note that only the parameters of the current outer class are visible
			if( outer.isParameterized() )
				for( ModifiedType modifiedParameter : outer.getTypeParameters() ) {
					Type parameter = modifiedParameter.getType();
					
					if( parameter instanceof TypeParameter ) {
						TypeParameter typeParameter = (TypeParameter) parameter;
						if( typeParameter.getTypeName().equals(name) )
							return typeParameter;
					}
				}	
	
			Type current = outer;
			while( current != null ) {			
				// Then check inner classes
				if( current instanceof ClassType )
					type = ((ClassType)current).getInnerClass(name);
				
				if( type != null )
					return type;
			
				// Then check outer class itself
				if( current.getTypeName().equals(name) )
					return current;
				
				current = current.getOuter();					
			}		
		
			// Walk up the tree of package from the starting point
			Package p = outer.getPackage();		
			while( p != null ) {
				type = p.getType(name);
				if( type != null )
					return type;
				p = p.getParent();
			}		
		
			// If still not found, try all the packages and types that the outer class imported
			for( Object item : outer.getImportedItems() ) {
				type = null;
				
				if( item instanceof Package ) {
					Package importedPackage = (Package) item;
					type = importedPackage.getType(name);	
				}
				else if( item instanceof Type ) {
					Type importedType = (Type) item;
					if( importedType.getTypeWithoutTypeArguments().getTypeName().equals( name ) )
						type = importedType;				
				}
				
				if( type != null )
					return type;			
			}
		}
		
		return null;	
	}
	
	/**
	 * Tries to find a type without any other information.
	 * This method starts by looking in the current method, which will
	 * eventually look through outer types and then imported types.
	 * @param name		name of the type
	 * @return			type found or <code>null</code> if not found
	 */
	protected Type lookupType( Context ctx, String name )
	{
		if( name.contains("@")) {
			int atSign = name.indexOf('@');
			return lookupType( ctx, name.substring(atSign + 1 ), name.substring(0, atSign) );
		}
		else if( name.contains(":")) {  //this case handles partially qualified names (perhaps leaving off outermost classes)
			int colon = name.indexOf(':');
			Type outer = lookupType(ctx, name.substring(0, colon));
			if( outer != null && outer instanceof ClassType )
				return ((ClassType)outer).getInnerClass(name.substring(colon + 1));
			else
				return null;
		}
		else
			return lookupTypeStartingAt( ctx, name, declarationType );
	}
	
	/**
	 * Tries to find a type inside a package with a specific name.
	 * @param name			name of the type
	 * @param packageName	name of the package
	 * @return				type found or <code>null</code> if not found
	 */
	private final Type lookupType( Context ctx, String name, String packageName ) {			
		Package p;
		
		if( packageName.equals("default") )
			p = packageTree;
		else
			p = packageTree.getChild(packageName);
		
		if( p == null ) {
			addError(ctx, Error.INVALID_IMPORT, "Package " + packageName + " not defined");
			return null;
		}		
		 
		return p.getType(name);
	}

	
	/**
	 * Determines the type of a given literal.
	 * @param literal	kind of literal
	 * @return			its type
	 */
	public static ClassType literalToType( ShadowParser.LiteralContext literal ) {
		if( literal.ByteLiteral() != null )
			return Type.BYTE;
		else if( literal.CodeLiteral() != null )
			return Type.CODE;
		else if( literal.ShortLiteral() != null )
			return Type.SHORT;
		else if( literal.IntLiteral() != null )
			return Type.INT;
		else if( literal.LongLiteral() != null )
			return Type.LONG;
		else if( literal.FloatLiteral() != null )
			return Type.FLOAT;
		else if( literal.DoubleLiteral() != null )
			return Type.DOUBLE;			
		else if( literal.StringLiteral() != null )
			return Type.STRING;
		else if( literal.UByteLiteral() != null )
			return Type.UBYTE;
		else if( literal.UShortLiteral() != null )
			return Type.USHORT;
		else if( literal.UIntLiteral() != null )
			return Type.UINT;
		else if( literal.ULongLiteral() != null )
			return Type.ULONG;
		else if( literal.BooleanLiteral() != null )
			return Type.BOOLEAN;
		else if( literal.NullLiteral() != null )
			return Type.NULL;
		
		return null;	
	}
	
	/**
	 * Determines the primitive type associated with a given type name.
	 * @param 	name	name of the primitive type
	 * @return			its type
	 */
	public static ClassType nameToPrimitiveType(String name) {
		switch(name) {
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
	
	/**
	 * Strips the extension off of a string specifying a file name.
	 * @param file		file name
	 * @return			name without extension
	 */
	public static String stripExtension(String file)
	{
		return file.substring(0, file.lastIndexOf("."));
	}	

	/**
	 * Tests to see if one type is accessible from another.
	 * @param type			type whose accessibility is in question 
	 * @param currentType	type where code is currently executing
	 * @return				<code>true</code> if type is accessible
	 */
	public static boolean classIsAccessible( Type type, Type currentType ) {
		// Public classes and outermost classes are always accessible.
		// So are direct inner classes.
		if( type.getModifiers().isPublic() || !type.hasOuter() ||
				type.getOuter().equals( currentType ) || type instanceof TypeParameter )
			return true;
		
		// If any outer class of type is also an outer class of typeToAccess, it's visible.
		Type outer = currentType.getOuter();		
		while( outer != null ) {
			if( outer == type.getOuter() )
				return true;			
			outer = outer.getOuter();		
		}		
		
		// Repeat the rigamarole for parent classes.
		if( currentType instanceof ClassType ) {
			ClassType parent = ((ClassType)currentType).getExtendType();			
			while( parent != null ) {
				if( type.getOuter() == parent ) {
					if( type.getModifiers().isPrivate())
						return false;
					else
						return true;
				}
				
				outer = parent.getOuter();				
				while( outer != null ) {
					if( outer == type.getOuter() )
						return true;					
					outer = outer.getOuter();		
				}
				
				parent = parent.getExtendType();
			}
		}		
		return false;
	}
	
	/**
	 * Tests to see if a method is accessible from the current type.
	 * @param signature		method signature whose accessibility is in question 
	 * @param currentType	type where code is currently executing
	 * @return				<code>true</code> if the method is accessible
	 */
	public static boolean methodIsAccessible( MethodSignature signature, Type currentType ) {
		if( signature.getMethodType().getModifiers().isPublic() ) 
			return true;
		
		Context node = signature.getNode();
		if( node == null )
			return false;		
		
		Type outer = currentType;		
		while( outer != null ) {
			if( node.getEnclosingType().equals( outer ) )
				return true;
			
			if( outer instanceof ClassType ) {
				ClassType parent = ((ClassType)outer).getExtendType();
				
				while( parent != null ) {
					if( node.getEnclosingType().equals( parent ) ) {
						if( node.getModifiers().isPrivate())
							return false;
						else
							return true;
					}					
					parent = parent.getExtendType();
				}
			}			
			
			outer = outer.getOuter();
		}
		return false;
	}
	
	/*
	 * Visitor methods below this point.
	 */
	
	@Override
	public Void visitClassOrInterfaceBody( ShadowParser.ClassOrInterfaceBodyContext ctx )
	{		
		// Entering a type							
		currentType = declarationType;
		currentPackage = currentType.getPackage();
		
		visitChildren(ctx);
				
		// Leaving a type
		currentType = currentType.getOuter();
					
		return null;
	}

	// Entering declaration, but type has not yet been entered.
	@Override
	public Void visitClassOrInterfaceDeclaration( ShadowParser.ClassOrInterfaceDeclarationContext ctx )
	{
		declarationType = ctx.getType();
		currentPackage = declarationType.getPackage();
		
		visitChildren(ctx);
				
		declarationType = declarationType.getOuter();
		
		return null;
	}	
	
	/*
	 * TypeCollector overrides this method, because it does something different.
	 * All other checkers use this method.
	 */ 
	@Override
	public Void visitCompilationUnit( ShadowParser.CompilationUnitContext ctx)
	{			
		currentPackage = packageTree;		
		return visitChildren(ctx);
	}
	
	public int getDimensions(Context context) {		
		int dimensions = 0;		
		
		for( ParseTree tree : context.children ) {
			if( tree instanceof TerminalNode ) {
				String token = ((TerminalNode)tree).getText();			
				if( token.equals("]"))					
					dimensions++;				
			}
		}
		
		return dimensions;
	}
}