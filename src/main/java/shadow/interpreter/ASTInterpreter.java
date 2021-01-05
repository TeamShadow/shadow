package shadow.interpreter;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

import shadow.ShadowException;
import shadow.interpreter.InterpreterException.Error;
import shadow.parse.Context;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.PrimaryExpressionContext;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLocalLoad;
import shadow.tac.nodes.TACLocalStore;
import shadow.tac.nodes.TACMethodName;
import shadow.tac.nodes.TACOperand;
import shadow.typecheck.BaseChecker;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.Package;
import shadow.typecheck.StatementChecker;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InstantiationException;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodReferenceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.SubscriptType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.UnboundMethodType;

/**
 * Interpreter that walks AST nodes in order to determine the values of expressions.
 *
 * Currently, this is only used by {@link ConstantFieldInterpreter} to determine the values of
 * compile-time constant fields.
 */
public class ASTInterpreter extends BaseChecker {

	/* Stack for current prefix (needed for arbitrarily long chains of expressions). */
	private LinkedList<Context> curPrefix = new LinkedList<>();

	public ASTInterpreter(Package packageTree, ErrorReporter reporter) {
		super(packageTree, reporter);
	}

	// Converts a Context object to a single "token" containing the same text.
	// Useful for passing some .*Context types to code that operates on tokens.
	private static Token toToken(Context ctx) {
		return new CommonToken(-1, ctx.getText());
	}

	/**
	 * Given a primarySuffix, finds the primarySuffix or primaryPrefix immediately preceding it.
	 *
	 * This method might be useful in other contexts outside of {@link ASTInterpreter}, e.g.
	 * TACBuilder.getPrefix() serves the same purposes. TODO: Find a better home.
	 */
	/*
    public static Context getPreviousSuffix(ShadowParser.PrimarySuffixContext ctx) {
        ShadowParser.PrimaryExpressionContext primaryExpression =
                (ShadowParser.PrimaryExpressionContext) ctx.getParent();

        int index = primaryExpression.primarySuffix().indexOf(ctx);
        return index == 0 ? primaryExpression.primaryPrefix() : primaryExpression.primarySuffix(index - 1);
    }*/

	/** Finds the nearest ancestor type containing a field with the requested name */
	/*
	public static Type findNearestTypeContainingConstant(Type current, String fieldName) {
		if(current.containsConstant(fieldName))
			return current;

		// Check outer types
		Type outer = current.getOuter();
		while(outer != null) {
			if(outer.containsConstant(fieldName))
				return outer;
			outer = outer.getOuter();
		}

		// Check parents
		if(current instanceof ClassType) {
			ClassType classType = (ClassType) current;
			if(classType.getExtendType() != null ) {
				Type nearest = findNearestTypeContainingConstant(classType.getExtendType(), fieldName);
				if(nearest != null)
					return nearest; 
			}
		}

		// Check interfaces
		for(InterfaceType interface_ : current.getInterfaces()) {
			Type nearest = findNearestTypeContainingConstant(interface_, fieldName);
			if(nearest != null)
				return nearest;
		}		

		return null;
	}
	 */


	// Assumes that operands has at least one element, and that operators has
	// operators.size() - 1 elements.
	private ShadowValue evaluateBinaryOperation(
			Context parent, List<? extends Context> operands, List<Token> operators, boolean cast) {

		try {
			ShadowValue current = operands.get(0).getInterpretedValue();
			for( int i = 0; i < parent.getOperations().size(); i++ ) {
				String op = operators.get(i).getText();
				BinaryOperator operator = BinaryOperator.fromString(op);
				ShadowValue next = operands.get(i + 1).getInterpretedValue();
				MethodSignature signature = parent.getOperations().get(i);
				boolean isCompare = ( op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">=") || op.equals("==") || op.equals("===") );
				Type currentType = current.getType();
				if( currentType.isPrimitive() && signature.isImport() ) { //operation based on method
					if(!isCompare)
						current = current.cast(signature.getReturnTypes().get(0).getType());
					next = next.cast(signature.getParameterTypes().get(0).getType());
					current = current.apply(operator, next);     
				}
				else {				
					next = next.cast(signature.getParameterTypes().get(0).getType());
					current = current.callMethod(signature.getSymbol(), next);				
				}
			}

			return current;
		}
		catch(ShadowException e) {
			addError(e.setContext(parent));
			return ShadowValue.INVALID;
		}
		/*

		// exp ( '<operator>' exp )*
		Iterator<? extends Context> operandIterator = operands.iterator();
		ShadowValue left = operandIterator.next().getInterpretedValue();

		for (Token operatorToken : operators) {
			BinaryOperator operator = BinaryOperator.fromString(operatorToken.getText());
			ShadowValue right = operandIterator.next().getInterpretedValue();
			try {            
				if(cast) {            	
					if(left.isStrictSubtype(right))					
						left = left.cast(right.getType());					
					else if(right.isStrictSubtype(left))
						right = right.cast(left.getType());
					//TODO: numerical madness: uint + int -> long

				}	            
				left = left.apply(operator, right);            
			} 
			catch (ShadowException e) {
				addError(e.setContext(parent));
				return ShadowValue.INVALID;
			}
		}

		return left;
		 */
	}

	private ModifiedType resolveType( ModifiedType node ) { //dereferences into PropertyType or IndexType for getter, if needed	
		Type type = node.getType();		
		if( type instanceof PropertyType ) { //includes SubscriptType as well		
			PropertyType getSetType = (PropertyType) type;
			if( getSetType.isGettable() )						
				return getSetType.getGetType();			
			else {
				String kind = (type instanceof SubscriptType) ? "Subscript " : "Property ";
				if( node instanceof Context)
					addError((Context)node, Error.ILLEGAL_ACCESS, kind + node + " does not have appropriate get access");
				else
					addError(new InterpreterException(Error.ILLEGAL_ACCESS, kind + node + " does not have appropriate get access"));
				return new SimpleModifiedType( Type.UNKNOWN );
			}				
		}
		else
			return node;
	}

	// This should be the entry point for all interpretation. Assumes the variable
	// declarator includes a definition.
	@Override
	public Void visitVariableDeclarator(ShadowParser.VariableDeclaratorContext ctx) {
		// Normally BaseChecker would do this for us if we weren't using VariableDeclarator
		// as an entry point
		Type oldType = currentType;
		currentType = ctx.getEnclosingType();

		visitChildren(ctx);

		// This cast seems wacky, but if we don't do it, then constant long X = 5; stores 5 into X, not 5L
		try {
			ctx.setInterpretedValue(ctx.conditionalExpression().getInterpretedValue().cast(ctx.getType()));
		}
		catch(ShadowException e) {
			addError(e.setContext(ctx));
		}

		currentType = oldType;
		return null;
	}

	@Override
	public Void visitConditionalExpression(ShadowParser.ConditionalExpressionContext ctx) {
		// coalesceExpression ('?' throwOrConditionalExpression ',' throwOrConditionalExpression)?
		visitChildren(ctx);
		ShadowValue value = ctx.coalesceExpression().getInterpretedValue();

		if (ctx.getChildCount() > 1) {
			if(value instanceof ShadowBoolean) {
				boolean condition = ((ShadowBoolean) value).getValue();
				value = ctx.throwOrConditionalExpression(condition ? 0 : 1).getInterpretedValue();
			}
			else
				addError(ctx.coalesceExpression(), Error.INVALID_TYPE, "Supplied type " + value.getType() + " cannot be used in the condition of a ternary operator, boolean type required", value.getType());
		}

		ctx.setInterpretedValue(value);
		return null;
	}

	@Override
	public Void visitThrowOrConditionalExpression(ShadowParser.ThrowOrConditionalExpressionContext ctx) {
		visitChildren(ctx);

		if(ctx.throwStatement() != null) {
			addError(ctx.throwStatement(), Error.INVALID_STRUCTURE, "Throw statement is not allowed in a compile-time constant");
			ctx.setInterpretedValue(ShadowValue.INVALID);
		}
		else
			ctx.setInterpretedValue(ctx.conditionalExpression().getInterpretedValue());

		return null;
	}

	@Override
	public Void visitBrackets(ShadowParser.BracketsContext ctx) { 
		visitChildren(ctx);

		ModifiedType prefixNode = curPrefix.getFirst();
		prefixNode = resolveType( prefixNode );
		Type prefixType = prefixNode.getType();

		if( prefixNode.getModifiers().isTypeName() && !(prefixType instanceof ArrayType) ) {			
			ctx.setType(new ArrayType( prefixType,  getDimensions(ctx), false) );
			ctx.addModifiers(Modifiers.TYPE_NAME);
		}
		else {				
			addError(curPrefix.getFirst(), Error.NOT_TYPE, "Can only apply brackets to a type name");
			ctx.setType(Type.UNKNOWN);
		}

		return null;		
	}

	@Override
	public Void visitCastExpression(ShadowParser.CastExpressionContext ctx) { 
		visitChildren(ctx);

		Type type = ctx.type().getType();

		if( ctx.getChild(2).getText().equals("nullable") ) {
			if( type instanceof ArrayType ) {
				type = ((ArrayType)type).convertToNullable();
				ctx.addModifiers(Modifiers.NULLABLE);
			}
			else
				addError(ctx, Error.INVALID_CAST, "Can only specify nullable for array types in casts");
		}

		ShadowValue value = ctx.conditionalExpression().getInterpretedValue();
		try {
			value = value.cast(type);
		}
		catch(ShadowException e) {
			addError(e.setContext(ctx));
		}

		ctx.setInterpretedValue(value);   	

		return null;
	}

	@Override public Void visitCheckExpression(ShadowParser.CheckExpressionContext ctx) { 
		visitChildren(ctx);

		ShadowValue value = ctx.conditionalExpression().getInterpretedValue();

		if( value instanceof ShadowNull )
			addError(ctx.conditionalExpression(), Error.INVALID_TYPE, "Null value not permitted in a check statement in a compile-time constant");

		ctx.setInterpretedValue(value);
		ctx.setType(value.getType());

		return null;
	}

	@Override public Void visitCopyExpression(ShadowParser.CopyExpressionContext ctx) { 
		visitChildren(ctx);

		ShadowValue value = ctx.conditionalExpression().getInterpretedValue();

		// Useful?
		if( ctx.getChild(0).getText().equals("freeze") )
			ctx.addModifiers(Modifiers.IMMUTABLE);

		ctx.setInterpretedValue(value);
		ctx.setType(value.getType());

		return null;
	}

	@Override
	public Void visitClassSpecifier(ShadowParser.ClassSpecifierContext ctx) {
		visitChildren(ctx);

		//always part of a suffix, thus always has a prefix			
		ModifiedType prefixNode = curPrefix.getFirst();
		boolean isTypeName = prefixNode.getModifiers().isTypeName();
		Type prefixType = prefixNode.getType();

		if( isTypeName ) {
			if( ctx.typeArguments() != null ) { // has type arguments											
				ShadowParser.TypeArgumentsContext arguments = ctx.typeArguments();
				SequenceType parameterTypes = prefixType.getTypeParameters();
				SequenceType argumentTypes = (SequenceType) arguments.getType();

				if( !prefixType.isParameterized() )
					addError(ctx.typeArguments(), Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments " + argumentTypes + " supplied for non-parameterized type " + prefixType, prefixType);
				else if( !parameterTypes.canAccept(argumentTypes, SubstitutionKind.TYPE_PARAMETER) )
					addError(ctx.typeArguments(), Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + argumentTypes + " do not match type parameters " + parameterTypes );
				else {
					try {
						Type replacedType = prefixType.replace(parameterTypes, argumentTypes);

						currentType.addUsedType(replacedType);
						currentType.addPartiallyInstantiatedClass(replacedType);

						try {
							ctx.setInterpretedValue(new ShadowClass(replacedType));
						} catch (ShadowException e) {
							addError(e.setContext(ctx));
						}
					}
					catch(InstantiationException e) {
						addError(ctx.typeArguments(), Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + argumentTypes + " do not match type parameters " + parameterTypes );
					}
				}
			}
			else if( prefixType.isParameterized() )				
				addError(ctx, Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for parameterized type " + prefixType);
			else {				
				try {
					ctx.setInterpretedValue(new ShadowClass(prefixType));
				} catch (ShadowException e) {
					addError(e.setContext(ctx));
				}
			}
		}
		else
			addError(ctx, Error.NOT_TYPE, "Class specifier requires type name for access" );



		ctx.setType( Type.CLASS );
		ctx.addModifiers(Modifiers.IMMUTABLE);	

		return null;
	}

	@Override
	public Void visitCoalesceExpression(ShadowParser.CoalesceExpressionContext ctx) {
		// conditionalOrExpression ('??' conditionalOrExpression)* ('??' throwStatement)?
		visitChildren(ctx);

		// We assume there won't be a throw statement in a constant expression
		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.conditionalOrExpression(), ctx.operators, false));

		return null;
	}

	@Override
	public Void visitConditionalOrExpression(ShadowParser.ConditionalOrExpressionContext ctx) {
		visitChildren(ctx);

		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.conditionalExclusiveOrExpression(), ctx.operators, true));

		return null;
	}

	@Override
	public Void visitConditionalExclusiveOrExpression(ShadowParser.ConditionalExclusiveOrExpressionContext ctx) {
		visitChildren(ctx);

		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.conditionalAndExpression(), ctx.operators, true));

		return null;
	}

	@Override
	public Void visitConditionalAndExpression(ShadowParser.ConditionalAndExpressionContext ctx) {
		visitChildren(ctx);

		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.bitwiseOrExpression(), ctx.operators, true));

		return null;
	}

	protected MethodSignature setCreateType( Context node, Type prefixType, SequenceType arguments) {			
		return setMethodType( node, prefixType, "create", arguments, null);		
	}

	protected MethodSignature setMethodType( Context node, Type type, String method, SequenceType arguments) {		
		return setMethodType( node, type, method, arguments, null );
	}

	protected boolean setTypeFromName( Context node, String name )  {	
		// check to see if it's a field or a method			
		if( setTypeFromContext( node, name, currentType ) )
			return true;

		//is it a type?
		Type type = lookupType( node, name );		

		if(type != null) {
			currentType.addUsedType(type);			
			node.setType(type);
			node.addModifiers(Modifiers.TYPE_NAME);
			return true;
		}

		return false;
	}



	protected boolean setTypeFromContext( Context node, String name, Type context ) {
		if( context instanceof TypeParameter ) {
			TypeParameter typeParameter = (TypeParameter) context;
			for( Type type : typeParameter.getBounds() )
				if( setTypeFromContext( node, name, type ) )
					return true;

			return setTypeFromContext( node, name, typeParameter.getClassBound());			
		}		
		else {			
			Modifiers methodModifiers = Modifiers.NO_MODIFIERS;
			if(!currentMethod.isEmpty())
				methodModifiers = currentMethod.getFirst().getModifiers();

			// Check fields first
			if(context.containsField(name)) {
				Context field = context.getField(name);
				node.setInterpretedValue(ShadowValue.INVALID);
				node.addModifiers(field.getModifiers());

				if( !StatementChecker.fieldIsAccessible( field, currentType ) )
					addError(field, Error.ILLEGAL_ACCESS, "Field " + name + " not accessible from this context");
				else
					addError(field, Error.ILLEGAL_ACCESS, "Field " + name + " cannot be referenced in a compile-time constant because it is not constant");

				return true;			
			}

			// Next check methods
			if(context.recursivelyContainsMethod(name)) {
				node.setType( new UnboundMethodType( name, context ) );	
				if(methodModifiers != null && methodModifiers.isImmutable() )
					node.addModifiers(Modifiers.IMMUTABLE);
				else if(methodModifiers != null && methodModifiers.isReadonly() )
					node.addModifiers(Modifiers.READONLY);
				return true;
			}

			// Finally check constants
			if(context.recursivelyContainsConstant(name)) {
				Context field = context.recursivelyGetConstant(name);
				node.setType(field.getType());
				node.addModifiers(field.getModifiers());

				if(!StatementChecker.fieldIsAccessible(field, currentType))
					addError(field, Error.ILLEGAL_ACCESS, "Constant " + name + " not accessible from this context");

				return true;
			}
		}			

		return false;
	}


	protected MethodSignature setMethodType( Context node, Type type, String method, SequenceType arguments, SequenceType typeArguments ) {	
		List<ShadowException> errors = new ArrayList<>();
		MethodSignature signature = type.getMatchingMethod(method, arguments, typeArguments, errors);

		if( signature == null )
			addErrors(node, errors);
		else {
			// TODO: Decorator needs to be fixed
			if(!signature.getOuter().hasInterface(Type.DECORATOR) && !methodIsAccessible( signature, currentType ))
				addError(node, Error.ILLEGAL_ACCESS, signature.getSymbol() + signature.getMethodType() + " is not accessible from this context");						

			//if any arguments have an UnboundMethodType, note down what their true MethodSignature must be
			for( int i = 0; i < arguments.size(); ++i ) {
				ModifiedType argument = arguments.get(i);				
				if( argument.getType() instanceof UnboundMethodType ) {
					//since it matches, the parameter of the method must be a MethodType
					MethodType parameterType = (MethodType) signature.getParameterTypes().get(i).getType();					
					UnboundMethodType unboundType = (UnboundMethodType) argument.getType();
					MethodSignature boundSignature = unboundType.getOuter().getMatchingMethod(unboundType.getTypeName(), parameterType.getParameterTypes());
					unboundType.setKnownSignature(boundSignature);					
				}
			}		
			node.setType(signature.getMethodType());		
		}		

		return signature;
	}

	@Override
	public Void visitCreate(ShadowParser.CreateContext ctx) { 
		visitChildren(ctx);

		Context prefixNode = curPrefix.getFirst();
		Type prefixType = prefixNode.getType();
		ShadowValue value = ShadowValue.INVALID;
		Context parent = (Context) ctx.getParent();

		if( prefixType instanceof InterfaceType )			
			addError(curPrefix.getFirst(), Error.INVALID_CREATE, "Interfaces cannot be created");						
		else if( prefixType instanceof SingletonType )			
			addError(curPrefix.getFirst(), Error.INVALID_CREATE, "Singletons cannot be created");
		else if(!( prefixType instanceof ClassType) && !(prefixType instanceof TypeParameter && !prefixType.getAllMethods("create").isEmpty()))
			addError(curPrefix.getFirst(), Error.INVALID_CREATE, "Type " + prefixType + " cannot be created", prefixType);				
		else if( !prefixNode.getModifiers().isTypeName() )
			addError(curPrefix.getFirst(), Error.INVALID_CREATE, "Only a type can be created");
		else if(prefixType.equals(Type.STRING)){

			if( ctx.typeArguments() != null ) {
				SequenceType typeArguments = (SequenceType) ctx.typeArguments().getType();
				addError(ctx.typeArguments(), Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + typeArguments + " do match type parameters " + prefixType.getTypeParameters());
			}
			else if(ctx.conditionalExpression().size() == 0)
				value = new ShadowString("");
			else if(ctx.conditionalExpression().size() == 1 && ctx.conditionalExpression(0).getInterpretedValue() instanceof ShadowString)
				value = ctx.conditionalExpression(0).getInterpretedValue();
			else {
				StringBuilder builder = new StringBuilder("(");
				boolean first = true;
				for(Context context : ctx.conditionalExpression()) {
					if(first)
						first = false;
					else
						builder.append(", ");
					builder.append(context.getInterpretedValue());
				}
				builder.append(")");				
				addError(curPrefix.getFirst(), Error.INVALID_CREATE, "Create for the String class with arguments " + builder.toString() + " is not allowed in a compile-time constant");
			}
		}
		else
			addError(curPrefix.getFirst(), Error.INVALID_CREATE, "Create is only allowed for the String class in a compile-time constant");

		parent.setInterpretedValue(value);		
		return null;
	}

	@Override
	public Void visitBitwiseOrExpression(ShadowParser.BitwiseOrExpressionContext ctx) {
		visitChildren(ctx);

		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.bitwiseExclusiveOrExpression(), ctx.operators, true));

		return null;
	}

	@Override
	public Void visitBitwiseExclusiveOrExpression(ShadowParser.BitwiseExclusiveOrExpressionContext ctx) {
		visitChildren(ctx);

		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.bitwiseAndExpression(), ctx.operators, true));

		return null;
	}

	@Override
	public Void visitArrayCreate(ShadowParser.ArrayCreateContext ctx)	{ 
		visitChildren(ctx);

		addError(ctx, Error.UNSUPPORTED_OPERATION, "Array cannot be created in a compile-time constant");
		ctx.setInterpretedValue(ShadowValue.INVALID);
		((Context)ctx.getParent()).setInterpretedValue(ShadowValue.INVALID);

		return null;
	}

	@Override public Void visitArrayInitializer(ShadowParser.ArrayInitializerContext ctx) { 
		visitChildren(ctx);

		//TODO: One day, make some arrays allowed?
		addError(ctx, Error.UNSUPPORTED_OPERATION, "Array initializer is not permitted in a compile-time constant");
		ctx.setInterpretedValue(ShadowValue.INVALID);

		return null;		
	}

	@Override
	public Void visitBitwiseAndExpression(ShadowParser.BitwiseAndExpressionContext ctx) {
		visitChildren(ctx);

		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.equalityExpression(), ctx.operators, true));

		return null;
	}

	@Override
	public Void visitEqualityExpression(ShadowParser.EqualityExpressionContext ctx) {
		visitChildren(ctx);

		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.isExpression(), ctx.operators, true));

		return null;
	}

	@Override
	public Void visitIsExpression(ShadowParser.IsExpressionContext ctx) {
		visitChildren(ctx);

		if (ctx.type() != null) {
			// TODO: Complete this!        	
			addError(Error.UNSUPPORTED_OPERATION.getException(ctx));
			ctx.setInterpretedValue(ShadowValue.INVALID);
		} else {
			ctx.setInterpretedValue(ctx.relationalExpression().getInterpretedValue());
		}

		return null;
	}

	@Override public Void visitFunctionType(ShadowParser.FunctionTypeContext ctx) { 
		visitChildren(ctx);

		MethodType methodType = new MethodType();		
		ShadowParser.ResultTypesContext parameters = ctx.resultTypes(0);
		SequenceType parameterTypes = (SequenceType) parameters.getType();

		ShadowParser.ResultTypesContext returns = ctx.resultTypes(1);
		SequenceType returnTypes = (SequenceType) returns.getType();

		for( ModifiedType parameter : parameterTypes  )
			methodType.addParameter(parameter);			

		for( ModifiedType type : returnTypes )
			methodType.addReturn(type);

		ctx.setType(new MethodReferenceType(methodType));
		ctx.addModifiers(Modifiers.TYPE_NAME);

		return null;
	}

	@Override
	public Void visitRelationalExpression(ShadowParser.RelationalExpressionContext ctx) {
		visitChildren(ctx);

		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.concatenationExpression(), ctx.operators, true));

		return null;
	}

	@Override
	public Void visitConcatenationExpression(ShadowParser.ConcatenationExpressionContext ctx) {
		visitChildren(ctx);
		
		
		//Handled differently from other binary operators since it's allowed between almost any two types
		ShadowValue value = ShadowValue.INVALID;

		if(ctx.operators.size() > 0) {
			String string = ctx.shiftExpression(0).getInterpretedValue().toString();
			for(int i = 0; i < ctx.operators.size(); ++i)
				string += ctx.shiftExpression(i + 1).getInterpretedValue().toString();
			value = new ShadowString(string);
		}
		else
			value = ctx.shiftExpression(0).getInterpretedValue();
		
		ctx.setInterpretedValue(value);
		return null;
	}

	@Override public Void visitScopeSpecifier(ShadowParser.ScopeSpecifierContext ctx) { 
		visitChildren(ctx);

		// Always part of a suffix, thus always has a prefix			
		ModifiedType prefixNode = curPrefix.getFirst();
		prefixNode = resolveType( prefixNode );
		Type prefixType = prefixNode.getType();
		boolean isTypeName = prefixNode.getModifiers().isTypeName();
		String name =  ctx.Identifier().getText();
		// Check field first
		if( prefixType.containsField( name ) ) {
			Context field = prefixType.getField(name);			
			if( !StatementChecker.fieldIsAccessible( field, currentType ))
				addError(ctx, Error.ILLEGAL_ACCESS, "Field " + name + " not accessible from this context");
			else
				addError(ctx, Error.ILLEGAL_ACCESS, "Field " + name + " cannot be referenced in a compile-time constant because it is not constant");

			ctx.setInterpretedValue(ShadowValue.INVALID);
		}
		else if(prefixType.recursivelyContainsConstant(name)) {
			Context constant = prefixType.recursivelyGetConstant(name);

			if(!isTypeName )
				addError(ctx, Error.ILLEGAL_ACCESS, "Constant " + name + " requires type name for access");

			ctx.setInterpretedValue(dereferenceField(constant.getEnclosingType(), name, ctx));
		}
		else if( prefixType.containsInnerType(name) ) {
			Type innerType = prefixType.getInnerType(name);

			if( !currentType.canSee(innerType) ) {
				addError(ctx, Error.ILLEGAL_ACCESS, "Type " + innerType + " is not accessible from this context", innerType);
				ctx.setInterpretedValue(ShadowValue.INVALID);
			}
			else {
				ctx.setType(innerType);						
				ctx.addModifiers(Modifiers.TYPE_NAME);					
			}					
		}
		else {
			addError(ctx, Error.UNDEFINED_SYMBOL, "Symbol " + name + " not defined in this context");
			ctx.setInterpretedValue(ShadowValue.INVALID);
		}

		return null;
	}

	@Override
	public Void visitShiftExpression(ShadowParser.ShiftExpressionContext ctx) {
		visitChildren(ctx);

		// The shift operator is a parser rule instead of a literal token (for disambiguation
		// reasons), so we create a proxy token containing its text
		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx,
						ctx.rotateExpression(),
						ctx.operators.stream().map(ASTInterpreter::toToken).collect(toList()), false));

		return null;
	}

	@Override public Void visitSpawnExpression(ShadowParser.SpawnExpressionContext ctx)
	{
		visitChildren(ctx);

		addError(ctx, Error.INVALID_STRUCTURE, "Spawn expression cannot occur in a compile-time constant");
		ctx.setInterpretedValue(ShadowValue.INVALID);

		return null;
	}

	@Override
	public Void visitRotateExpression(ShadowParser.RotateExpressionContext ctx) {
		visitChildren(ctx);

		// The rotate operator is a parser rule instead of a literal token (for disambiguation
		// reasons), so we create a proxy token containing its text
		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx,
						ctx.additiveExpression(),
						ctx.operators.stream().map(ASTInterpreter::toToken).collect(toList()), false));

		return null;
	}

	@Override
	public Void visitAdditiveExpression(ShadowParser.AdditiveExpressionContext ctx) {
		visitChildren(ctx);

		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.multiplicativeExpression(), ctx.operators, true));

		return null;
	}

	@Override public Void visitLiteral(ShadowParser.LiteralContext ctx) { 
		//no children visited
		Type type = literalToType(ctx);
		if(type != Type.NULL)
			currentType.addUsedType(type);

		ctx.setInterpretedValue(StatementChecker.processLiteral(ctx, getErrorReporter()));
		return null;		
	}

	@Override
	public Void visitMethod(ShadowParser.MethodContext ctx) { 
		visitChildren(ctx);

		//always part of a suffix, thus always has a prefix
		Context prefixNode = curPrefix.getFirst();
		ShadowValue prefixValue = prefixNode.getInterpretedValue();
		ModifiedType resolvedType = resolveType(prefixNode);
		Type prefixType = resolvedType.getType();
		String methodName = ctx.Identifier().getText();
		ShadowValue value = ShadowValue.INVALID;

		if(prefixNode.getModifiers().isTypeName()) {
			if(prefixType instanceof SingletonType)
				addError(curPrefix.getFirst(), Error.INVALID_TYPE, "Methods cannot be called from singleton types in a compile-time constant");
			else
				addError(curPrefix.getFirst(), Error.NOT_OBJECT, "Type name cannot be used to call method");				
		}
		else if( prefixType instanceof SequenceType ) {
			addError(curPrefix.getFirst(), Error.INVALID_TYPE, "Method cannot be called on a sequence result");
		}
		else if(prefixType != null) {				
			List<MethodSignature> methods = prefixType.getAllMethods(methodName);

			//unbound method (it gets bound when you supply arguments)
			if( methods != null && methods.size() > 0 )
				value = new ShadowUnboundMethod(prefixValue, new UnboundMethodType(methodName, prefixType)); 
			else
				addError(ctx, Error.UNDEFINED_SYMBOL, "Method " + methodName + " not defined in this context");
		}			

		ctx.setInterpretedValue(value);

		// These push the immutable or readonly modifier to the prefix of the call
		if( resolvedType.getModifiers().isImmutable() )
			ctx.addModifiers(Modifiers.IMMUTABLE);			
		else if( resolvedType.getModifiers().isReadonly() )
			ctx.addModifiers(Modifiers.READONLY);
		else if( resolvedType.getModifiers().isTemporaryReadonly() )
			ctx.addModifiers(Modifiers.TEMPORARY_READONLY);	

		return null;
	}

	@Override 
	public Void visitMethodCall(ShadowParser.MethodCallContext ctx) { 
		visitChildren(ctx);

		//always part of a suffix, thus always has a prefix
		Context prefixNode = curPrefix.getFirst();
		ShadowValue prefixValue = prefixNode.getInterpretedValue();
		Type prefixType = resolveType(prefixNode).getType();
		ShadowValue value = ShadowValue.INVALID;



		if(prefixValue != null && prefixValue instanceof ShadowUnboundMethod) {
			try {

				ShadowValue[] arguments = new ShadowValue[ctx.conditionalExpression().size()];
				MethodSignature signature = ctx.getSignature();

				for(int i = 0; i < ctx.conditionalExpression().size(); ++i) {
					arguments[i] = ctx.conditionalExpression(i).getInterpretedValue();
					Type parameterType = signature.getMethodType().getParameterTypes().get(i).getType();
					if(arguments[i].getType().isStrictSubtype(parameterType))
						arguments[i] = arguments[i].cast(parameterType);

				}			

				ShadowUnboundMethod unboundMethod = (ShadowUnboundMethod)(prefixValue);				
				ShadowValue object = unboundMethod.getObject();


				value = object.callMethod(unboundMethod.getType().getTypeName(), arguments);
			}
			catch(InterpreterException e) {
				addError(e.setContext(ctx));
			}
			catch(ShadowException e) {
				addError(e.setContext(ctx));
			}
		}			
		else if(prefixType instanceof MethodReferenceType) // only happens with method pointers and local methods		
			addError(ctx, Error.INVALID_STRUCTURE, "Method reference cannot be called inside a compile-time constant");			
		else
			addError(ctx, Error.INVALID_TYPE, "Cannot apply arguments to non-method type " + prefixType, prefixType);		

		ctx.setInterpretedValue(value);

		return null;
	}

	@Override
	public Void visitMultiplicativeExpression(ShadowParser.MultiplicativeExpressionContext ctx) {
		visitChildren(ctx);

		ctx.setInterpretedValue(
				evaluateBinaryOperation(
						ctx, ctx.unaryExpression(), ctx.operators, true));

		return null;
	}

	@Override
	public Void visitUnaryExpression(ShadowParser.UnaryExpressionContext ctx) {
		visitChildren(ctx);

		ShadowValue value = ShadowValue.INVALID;

		if (ctx.operator != null) {
			UnaryOperator operator = UnaryOperator.fromString(ctx.operator.getText());
			try {
				value = ctx.unaryExpression().getInterpretedValue().apply(operator);
			} catch (ShadowException e) {
				addError(e.setContext(ctx));
			}
		} else if (ctx.primaryExpression() != null) {
			value = ctx.primaryExpression().getInterpretedValue();
		} else {
			// At the time of writing, only inlineMethodDefinition falls in this case
			addError(Error.UNSUPPORTED_OPERATION.getException(ctx));
		}

		ctx.setInterpretedValue(value);

		return null;
	}

	@Override public Void visitPrimaryExpression(ShadowParser.PrimaryExpressionContext ctx) { 
		curPrefix.addFirst(null);
		visitChildren(ctx);

		if( !ctx.primarySuffix().isEmpty() ) { 	//has suffixes, pull type from last suffix
			ShadowParser.PrimarySuffixContext last = ctx.primarySuffix(ctx.primarySuffix().size() - 1);

			ModifiedType modifiedType = resolveType( last ); //otherwise, strip away the property
			ctx.addModifiers(modifiedType.getModifiers());
			ctx.setInterpretedValue(last.getInterpretedValue());
		}
		else {							//just prefix		
			ShadowParser.PrimaryPrefixContext child = ctx.primaryPrefix();			
			ctx.addModifiers(child.getModifiers());
			ctx.setInterpretedValue(child.getInterpretedValue());


			if(child.spawnExpression() != null) { // Probably shouldn't be possible in compile-time constants
				ctx.action = true;
				currentType.addUsedType(Type.THREAD);
			}
		}		

		curPrefix.removeFirst();  //pop prefix type off stack

		return null;
	}

	@Override public Void visitPrimaryPrefix(ShadowParser.PrimaryPrefixContext ctx) { 
		visitChildren(ctx);

		String image = ctx.getText();
		ShadowValue value = ShadowValue.INVALID;

		if(!ctx.getModifiers().isTypeName()) {
			if( image.equals("this") || image.equals("super") )
				addError(ctx, Error.INVALID_SELF_REFERENCE, "Reference " + image + " invalid for compile-time constants");
			else if(ctx.spawnExpression() != null )
				addError(ctx, Error.INVALID_STRUCTURE, "Cannot spawn a thread in a compile-time constant");
			else if (ctx.generalIdentifier() != null) {
				if (ctx.unqualifiedName() == null) {
					String fieldName = ctx.generalIdentifier().getText();
					value = dereferenceField(currentType.recursivelyGetConstant(fieldName).getEnclosingType(), fieldName, ctx);
				}
			}
			else if( ctx.conditionalExpression() != null ) {
				value = ctx.conditionalExpression().getInterpretedValue();
				ctx.setType(ctx.conditionalExpression().getType());
				ctx.addModifiers(ctx.conditionalExpression().getModifiers());
			}


			// check expression, copy expression, cast expression,
			// primitive and function types, and array initializer
			else {			
				Context child = (Context) ctx.getChild(0);
				value = child.getInterpretedValue();
				ctx.addModifiers(child.getModifiers());
			}
		}

		ctx.setInterpretedValue(value);
		curPrefix.set(0, ctx); //so that the suffix can figure out where it's at

		return null;
	}


	@Override
	public Void visitPrimarySuffix(ShadowParser.PrimarySuffixContext ctx) { 
		visitChildren(ctx);

		Context prefixNode = curPrefix.getFirst();		

		if( prefixNode.getModifiers().isNullable() && !(prefixNode.getType() instanceof ArrayType) )
			addError(prefixNode, Error.INVALID_DEREFERENCE, "Nullable reference cannot be dereferenced");


		if( ctx.property() != null || ctx.methodCall() != null ||  ctx.allocation() != null ) {
			ShadowParser.PrimaryExpressionContext parent = (PrimaryExpressionContext) ctx.getParent();
			parent.action = true;
		}

		Context child = (Context) ctx.getChild(0);
		ctx.addModifiers(child.getModifiers());
		ctx.setType(child.getType());
		ctx.setInterpretedValue(child.getInterpretedValue()); // might override type above

		curPrefix.set(0, ctx); //so that a future suffix can figure out where it's at

		return null;
	}

	@Override public Void visitPrimitiveType(ShadowParser.PrimitiveTypeContext ctx) { 
		// No children
		ctx.setType(nameToPrimitiveType(ctx.getText()));
		currentType.addUsedType(ctx.getType());
		ctx.addModifiers(Modifiers.TYPE_NAME);

		return null;
	}

	@Override
	public Void visitProperty(ShadowParser.PropertyContext ctx)
	{ 
		visitChildren(ctx);

		// Always part of a suffix, thus always has a prefix
		Context prefixNode = curPrefix.getFirst();
		ModifiedType resolvedType = resolveType(prefixNode);
		Type prefixType = resolvedType.getType();
		String propertyName = ctx.Identifier().getText();
		ShadowValue value = ShadowValue.INVALID;
		ShadowValue prefixValue = prefixNode.getInterpretedValue();

		if(prefixNode.getModifiers().isTypeName()) {
			if(prefixType instanceof SingletonType)
				addError(curPrefix.getFirst(), Error.INVALID_TYPE, "Properties cannot be called from singleton types in a compile-time constant");
			else
				addError(curPrefix.getFirst(), Error.NOT_OBJECT, "Object reference must be used to access property " + propertyName);				
		}
		else {
			List<MethodSignature> methods = prefixType.getAllMethods(propertyName);	
			if( resolvedType.getModifiers().isImmutable() )
				ctx.addModifiers(Modifiers.IMMUTABLE);
			else if( resolvedType.getModifiers().isReadonly() )
				ctx.addModifiers(Modifiers.READONLY);
			else if( resolvedType.getModifiers().isTemporaryReadonly() )
				ctx.addModifiers(Modifiers.TEMPORARY_READONLY);				

			if( methods != null && methods.size() > 0 ) {
				MethodSignature getter = null;

				// Only getters for compile-time constants
				for( MethodSignature signature : methods ) {
					if( signature.getModifiers().isGet() )
						getter = signature;
				}					

				boolean mutableProblem = false;
				boolean accessibleProblem = false;

				if( getter != null && !methodIsAccessible(getter, currentType)) {
					accessibleProblem = true;
					getter = null;
				}

				if( getter != null && !prefixNode.getModifiers().isMutable() && getter.getModifiers().isMutable()  ) {
					mutableProblem = true;
					getter = null;
				}

				if( mutableProblem  )
					addError(ctx, Error.ILLEGAL_ACCESS, "Mutable property " + propertyName + " cannot be called from " + (prefixNode.getModifiers().isImmutable() ? "immutable" : "readonly") + " context");
				else if( accessibleProblem )
					addError(ctx, Error.ILLEGAL_ACCESS, "Property " + propertyName + " is not accessible in this context");
				else if( getter == null )
					addError(ctx, Error.INVALID_PROPERTY, "Property " + propertyName + " is not defined in this context");
				else if(prefixValue != null) { // Only case where it works out						
					try {
						value = prefixValue.callMethod(propertyName);
					}
					catch(InterpreterException e) {
						addError(e.setContext(ctx));
					}
				}				
			}
			else
				addError(ctx, Error.INVALID_PROPERTY, "Property " + propertyName + " not defined in this context");
		}

		ctx.setInterpretedValue(value);

		return null;
	}	


	/*
    private ShadowValue visitStandalonePrimaryPrefix(ShadowParser.PrimaryPrefixContext ctx) {
        ShadowValue value = ShadowValue.INVALID;

        if (ctx.literal() != null) {
            // Interpreted values for literals are set by StatementChecker#visitLiteral()
            value = ctx.literal().getInterpretedValue();

            // This happens because .meta files containing literals aren't processed by
            // StatementChecker. TODO: Find a better way to deal with them.
            if (value == null) {
                value = StatementChecker.processLiteral(ctx.literal(), getErrorReporter());
            }
        } else if (ctx.generalIdentifier() != null) {
            if (ctx.unqualifiedName() != null) {
                // This would be something of the form "package@Class" with no scope specifier
                // (which will probably have been caught during type-checking)
                addError(Error.UNSUPPORTED_OPERATION.getException(ctx));
            } else {
                String fieldName = ctx.generalIdentifier().getText();
                value = dereferenceField(findNearestTypeContainingField(currentType, fieldName), fieldName, ctx);
            }
        }
        else if(ctx.conditionalExpression() != null)
        	value = ctx.conditionalExpression().getInterpretedValue();
        else if(ctx.castExpression() != null)
        	value = ctx.castExpression().getInterpretedValue();
        else
        	// Don't know what it is, but we don't like it
        	addError(Error.UNSUPPORTED_OPERATION.getException(ctx));

        return value;
    }
	 */

	@Override
	public Void visitSubscript(ShadowParser.SubscriptContext ctx) { 
		visitChildren(ctx);

		Context prefixNode = curPrefix.getFirst();
		ModifiedType resolvedType = resolveType( prefixNode );
		Type prefixType = resolvedType.getType();
		ShadowValue prefixValue = prefixNode.getInterpretedValue();
		ShadowValue value = ShadowValue.INVALID;

		if( prefixType instanceof ArrayType  && !(((ArrayType)prefixType).getBaseType() instanceof TypeParameter) ) {
			ArrayType arrayType = (ArrayType)prefixType;

			ShadowParser.ConditionalExpressionContext child = ctx.conditionalExpression();
			Type childType = child.getType();

			if( !childType.isSubtype(Type.LONG) )
				addError(child, Error.INVALID_SUBSCRIPT, "Subscript type " + childType + " is invalid, must be subtype of " + Type.LONG, childType);

			if( resolvedType.getModifiers().isImmutable() )
				ctx.addModifiers(Modifiers.IMMUTABLE);
			else if( resolvedType.getModifiers().isReadonly() )
				ctx.addModifiers(Modifiers.READONLY);
			else if( resolvedType.getModifiers().isTemporaryReadonly() )
				ctx.addModifiers(Modifiers.TEMPORARY_READONLY);
			else
				ctx.addModifiers(Modifiers.ASSIGNABLE);

			//backdoor for creates
			//immutable and readonly array should only be assignable in creates
			if( prefixNode.getModifiers().isAssignable() ) 
				ctx.addModifiers(Modifiers.ASSIGNABLE);

			//primitive arrays are initialized to default values
			//non-primitive array elements could be null
			//however, arrays of arrays are not-null
			//instead, they will be filled with default values, including a length of zero
			//thus, we don't need to check nullability, but we do need a range check				

			//nullable array means what you get out is nullable, not the array itself
			if( arrayType.isNullable() ) 
				ctx.addModifiers(Modifiers.NULLABLE);	


			try {
				ShadowArray array = (ShadowArray) prefixValue;
				int index = ((ShadowInteger)prefixValue.cast(Type.INT)).getValue().intValue();
				if(index < 0 || index >= array.getLength())
					addError(child, Error.INVALID_SUBSCRIPT, "Subscript value " + index + " is out of bounds");
				else
					value = array.get(index).getValue();
			}
			catch(ShadowException e)
			{}

		}						
		else if( prefixType.hasUninstantiatedInterface(Type.CAN_INDEX) || prefixType.hasUninstantiatedInterface(Type.CAN_INDEX_NULLABLE) ||
				prefixType.hasUninstantiatedInterface(Type.CAN_INDEX_STORE) || prefixType.hasUninstantiatedInterface(Type.CAN_INDEX_STORE_NULLABLE)) {

			SequenceType arguments = new SequenceType();
			ShadowParser.ConditionalExpressionContext child = ctx.conditionalExpression();
			arguments.add(child);

			MethodSignature signature = setMethodType( ctx, prefixType, "index", arguments);

			if( signature != null && (prefixNode.getModifiers().isReadonly() || prefixNode.getModifiers().isTemporaryReadonly() || prefixNode.getModifiers().isImmutable()) && signature.getModifiers().isMutable() ) {
				addError(ctx, Error.INVALID_SUBSCRIPT, "Cannot apply mutable subscript to immutable or readonly prefix");						
			}
			else {
				//if signature is null, then it is not a load
				SubscriptType subscriptType = new SubscriptType(signature, child, new UnboundMethodType("index", prefixType), prefixNode, currentType);
				if( signature != null ) {
					ctx.addModifiers(subscriptType.getGetType().getModifiers());

					try {
						value = prefixValue.callMethod("index", child.getInterpretedValue());
					} 
					catch (InterpreterException e) {
						addError(e.setContext(ctx));
					}					
				}

				if( resolvedType.getModifiers().isImmutable() )
					ctx.addModifiers(Modifiers.IMMUTABLE);
				else if( resolvedType.getModifiers().isReadonly() )
					ctx.addModifiers(Modifiers.READONLY);
				else if( resolvedType.getModifiers().isTemporaryReadonly() )
					ctx.addModifiers(Modifiers.TEMPORARY_READONLY);
				else if( prefixType.hasUninstantiatedInterface(Type.CAN_INDEX_STORE) || prefixType.hasUninstantiatedInterface(Type.CAN_INDEX_STORE_NULLABLE)  ) 
					ctx.addModifiers(Modifiers.ASSIGNABLE);
			}							
		}			
		else {
			addError(ctx, Error.INVALID_SUBSCRIPT, "Subscript is not permitted for type " + prefixType +
					" because it does not implement " + Type.CAN_INDEX + ", " + Type.CAN_INDEX_STORE +
					", " + Type.CAN_INDEX_NULLABLE + ", or " + Type.CAN_INDEX_STORE_NULLABLE, prefixType);
		}		

		ctx.setInterpretedValue(value);
		return null;
	}	

	/**
	 * Determines the value associated with a given field, keyed by its parent type and name.
	 *
	 * {@link ASTInterpreter} does not support this by default - it should be overriden by classes
	 * like {@link ConstantFieldInterpreter} that track field values.
	 */
	protected ShadowValue dereferenceField(Type parentType, String fieldName, Context referenceCtx) {
		throw new UnsupportedOperationException(
				"ASTInterpreter cannot dereference fields. Did you mean to use ConstantFieldInterpreter?");
	}
}
