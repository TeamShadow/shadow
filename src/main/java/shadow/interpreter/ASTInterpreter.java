package shadow.interpreter;

import static java.util.stream.Collectors.toList;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

import shadow.interpreter.InterpreterException.Error;
import shadow.parse.Context;
import shadow.parse.ShadowParser;
import shadow.typecheck.BaseChecker;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.Package;
import shadow.typecheck.StatementChecker;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.InstantiationException;
import shadow.typecheck.type.MethodReferenceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
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
		catch(InterpreterException e) {
			addError(e.setContext(parent));
			return ShadowValue.INVALID;
		}
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

		ShadowValue value = ShadowValue.INVALID;
		try {
			// This cast seems wacky, but if we don't do it, then constant long X = 5; stores 5 into X, not 5L
			value = ctx.conditionalExpression().getInterpretedValue().cast(ctx.getType());
		}
		catch(InterpreterException e) {
			addError(e.setContext(ctx));
		}
		ctx.setInterpretedValue(value);

		currentType = oldType;
		return null;
	}

	@Override
	public Void visitConditionalExpression(ShadowParser.ConditionalExpressionContext ctx) {
		// coalesceExpression ('?' throwOrConditionalExpression ',' throwOrConditionalExpression)?
		visitChildren(ctx);
		ShadowValue value = ShadowValue.INVALID;

		try {
			if (ctx.getChildCount() > 1) {
				if(ctx.coalesceExpression().getInterpretedValue() instanceof ShadowBoolean) {
					boolean condition = ((ShadowBoolean) ctx.coalesceExpression().getInterpretedValue()).getValue();
					value = ctx.throwOrConditionalExpression(condition ? 0 : 1).getInterpretedValue().cast(ctx.getType());
				}
				else
					addError(ctx.coalesceExpression(), Error.INVALID_TYPE, "Supplied type " + value.getType() + " cannot be used in the condition of a ternary operator, boolean type required", value.getType());
			}
			else
				value = ctx.coalesceExpression().getInterpretedValue();
		}
		catch(InterpreterException e) {
			addError(e.setContext(ctx));
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
	public Void visitCastExpression(ShadowParser.CastExpressionContext ctx) { 
		visitChildren(ctx);

		Type type = ctx.type().getType();
		ShadowValue value = ctx.conditionalExpression().getInterpretedValue();
		try {
			value = value.cast(type);
		}
		catch(InterpreterException e) {
			addError(e.setContext(ctx));
		}

		ctx.setInterpretedValue(value);
		return null;
	}

	@Override public Void visitCheckExpression(ShadowParser.CheckExpressionContext ctx) { 
		visitChildren(ctx);

		ShadowValue value = ctx.conditionalExpression().getInterpretedValue();

		if(value instanceof ShadowNull)
			addError(ctx.conditionalExpression(), Error.INVALID_TYPE, "Null value not permitted in a check statement in a compile-time constant");

		ctx.setInterpretedValue(value);
		return null;
	}

	@Override public Void visitCopyExpression(ShadowParser.CopyExpressionContext ctx) { 
		visitChildren(ctx);

		ShadowValue value = ctx.conditionalExpression().getInterpretedValue();
		ctx.setInterpretedValue(value);

		return null;
	}

	@Override
	public Void visitClassSpecifier(ShadowParser.ClassSpecifierContext ctx) {
		visitChildren(ctx);

		// Always part of a suffix, thus always has a prefix			
		ModifiedType prefixNode = curPrefix.getFirst();
		Type prefixType = prefixNode.getType();
		ShadowValue value = ShadowValue.INVALID;

		if( ctx.typeArguments() != null ) { // has type arguments											
			ShadowParser.TypeArgumentsContext arguments = ctx.typeArguments();
			SequenceType parameterTypes = prefixType.getTypeParameters();
			SequenceType argumentTypes = (SequenceType) arguments.getType();

			try {
				Type replacedType = prefixType.replace(parameterTypes, argumentTypes);
				currentType.addUsedType(replacedType);
				currentType.addPartiallyInstantiatedClass(replacedType);

				try {
					value = new ShadowClass(replacedType);
				} catch (InterpreterException e) {
					addError(e.setContext(ctx));
				}
			} catch (InstantiationException e) {
				// Should have already been caught in the typechecker
			}
		}
		else {				
			try {
				value = new ShadowClass(prefixType);
			} catch (InterpreterException e) {
				addError(e.setContext(ctx));
			}
		}

		ctx.setInterpretedValue(value);

		return null;
	}

	@Override
	public Void visitCoalesceExpression(ShadowParser.CoalesceExpressionContext ctx) {
		// conditionalOrExpression ('??' conditionalOrExpression)* ('??' throwStatement)?
		visitChildren(ctx);
		if(ctx.throwStatement() != null) {
			addError(ctx.throwStatement(), Error.INVALID_STRUCTURE, "Throw statement is not allowed in a compile-time constant");
			ctx.setInterpretedValue(ShadowValue.INVALID);
		}
		else {
			ShadowValue value = ctx.conditionalOrExpression(0).getInterpretedValue();
			for(int i = 1; i < ctx.conditionalOrExpression().size() && value instanceof ShadowNull; ++i)
				value = ctx.conditionalOrExpression(i).getInterpretedValue();
			
			ctx.setInterpretedValue(value);
		}
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

	@Override
	public Void visitCreate(ShadowParser.CreateContext ctx) { 
		visitChildren(ctx);

		Context prefixNode = curPrefix.getFirst();
		Type prefixType = prefixNode.getType();
		ShadowValue value = ShadowValue.INVALID;
		Context parent = (Context) ctx.getParent();

		if(prefixType.equals(Type.STRING)){
			if(ctx.conditionalExpression().size() == 0)
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
			boolean isType = ctx.relationalExpression().getInterpretedValue().getType().isSubtype(ctx.type().getType());
			ctx.setInterpretedValue(new ShadowBoolean(isType));
		} else {
			ctx.setInterpretedValue(ctx.relationalExpression().getInterpretedValue());
		}

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

		// Handled differently from other binary operators since it's allowed between almost any two types
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
		String name =  ctx.Identifier().getText();

		// Check field first
		if( prefixType.containsField( name ) ) {
			addError(ctx, Error.ILLEGAL_ACCESS, "Field " + name + " cannot be referenced in a compile-time constant because it is not constant");
			ctx.setInterpretedValue(ShadowValue.INVALID);
		}
		else if(prefixType.recursivelyContainsConstant(name)) {
			Context constant = prefixType.recursivelyGetConstant(name);
			ctx.setInterpretedValue(dereferenceField(constant.getEnclosingType(), name, ctx));
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

	@Override public Void visitLiteral(ShadowParser.LiteralContext ctx)
	{ 
	
		ctx.setInterpretedValue(StatementChecker.processLiteral(ctx, getErrorReporter()));
		
		return null;		
	}
	
	@Override
	public Void visitMethod(ShadowParser.MethodContext ctx) { 
		visitChildren(ctx);

		// Always part of a suffix, thus always has a prefix
		Context prefixNode = curPrefix.getFirst();
		ShadowValue prefixValue = prefixNode.getInterpretedValue();
		ShadowValue value = ShadowValue.INVALID;
		Type type = ctx.getType();

		if(type instanceof UnboundMethodType)
			value = new ShadowUnboundMethod(prefixValue, (UnboundMethodType)type); 
		else
			addError(ctx, Error.UNSUPPORTED_OPERATION, "Accessing method references not supported in compile-time constants");

		ctx.setInterpretedValue(value);

		return null;
	}

	@Override 
	public Void visitMethodCall(ShadowParser.MethodCallContext ctx) { 
		visitChildren(ctx);

		// Always part of a suffix, thus always has a prefix
		Context prefixNode = curPrefix.getFirst();
		ShadowValue prefixValue = prefixNode.getInterpretedValue();
		Type prefixType = resolveType(prefixNode).getType();
		ShadowValue value = ShadowValue.INVALID;

		if(prefixValue instanceof ShadowUnboundMethod) {
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
		}			
		else if(prefixType instanceof MethodReferenceType) // only happens with method pointers and local methods		
			addError(ctx, Error.INVALID_STRUCTURE, "Method reference cannot be called inside a compile-time constant");
		else
			addError(Error.UNSUPPORTED_OPERATION.getException(ctx));	
	
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
			} catch (InterpreterException e) {
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
		
		Context child;

		if( !ctx.primarySuffix().isEmpty() )	// Has suffixes, pull value from last suffix
			child = ctx.primarySuffix(ctx.primarySuffix().size() - 1);
		else									// Just prefix		
			child = ctx.primaryPrefix();			
	
		ctx.setInterpretedValue(child.getInterpretedValue());
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
					ShadowParser.VariableDeclaratorContext fieldCtx = currentType.recursivelyGetConstant(fieldName);
					if (fieldCtx == null) {
						// It's either not constant or an unknown reference (the latter probably can't happen)
						addError(ctx,
								currentType.recursivelyGetField(fieldName) == null
										? Error.UNKNOWN_REFERENCE
										: Error.NON_CONSTANT_REFERENCE);
					} else {
						value = dereferenceField(fieldCtx.getEnclosingType(), fieldName, ctx);
					}
				}
			}
			else if( ctx.conditionalExpression() != null )
				value = ctx.conditionalExpression().getInterpretedValue();
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

		Context child = (Context) ctx.getChild(0);
		ctx.setInterpretedValue(child.getInterpretedValue());

		curPrefix.set(0, ctx); //so that a future suffix can figure out where it's at

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
		}
		else {
			 try {
				value = prefixValue.callMethod(propertyName);
			} catch (InterpreterException e) {
				addError(e.setContext(ctx));
			}
		}

		ctx.setInterpretedValue(value);
		return null;
	}	

	@Override
	public Void visitSubscript(ShadowParser.SubscriptContext ctx) { 
		visitChildren(ctx);

		Context prefixNode = curPrefix.getFirst();
		ModifiedType resolvedType = resolveType( prefixNode );
		Type prefixType = resolvedType.getType();
		ShadowValue prefixValue = prefixNode.getInterpretedValue();
		ShadowValue value = ShadowValue.INVALID;

		if( prefixType instanceof ArrayType  && !(((ArrayType)prefixType).getBaseType() instanceof TypeParameter) ) {
			ShadowParser.ConditionalExpressionContext child = ctx.conditionalExpression();

			try {
				ShadowArray array = (ShadowArray) prefixValue;
				int index = ((ShadowInteger)prefixValue.cast(Type.INT)).getValue().intValue();
				if(index < 0 || index >= array.getLength())
					addError(child, Error.INVALID_SUBSCRIPT, "Subscript value " + index + " is out of bounds");
				else
					value = array.get(index).getValue();
			}
			// Shouldn't happen
			catch(InterpreterException e) {
				addError(e.setContext(ctx));
			}
		}						
		else  {
			ShadowParser.ConditionalExpressionContext child = ctx.conditionalExpression();
			try {
				value = prefixValue.callMethod("index", child.getInterpretedValue());
			} 
			catch (InterpreterException e) {
				addError(e.setContext(ctx));
			}					
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
