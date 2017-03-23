package shadow.tac;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowInteger;
import shadow.interpreter.ShadowNull;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowUndefined;
import shadow.parse.Context;
import shadow.parse.ShadowBaseVisitor;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.ArrayCreateCallContext;
import shadow.parse.ShadowParser.ArrayDefaultContext;
import shadow.parse.ShadowParser.CatchStatementsContext;
import shadow.parse.ShadowParser.ExpressionContext;
import shadow.parse.ShadowParser.FinallyStatementContext;
import shadow.parse.ShadowParser.PrimaryExpressionContext;
import shadow.parse.ShadowParser.PrimarySuffixContext;
import shadow.parse.ShadowParser.RecoverStatementContext;
import shadow.parse.ShadowParser.SendStatementContext;
import shadow.parse.ShadowParser.ThrowOrConditionalExpressionContext;
import shadow.tac.analysis.ControlFlowGraph;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACChangeReferenceCount;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACConstantRef;
import shadow.tac.nodes.TACCopyMemory;
import shadow.tac.nodes.TACDummyNode;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACGenericArrayRef;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLabelAddress;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLength;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACLocalLoad;
import shadow.tac.nodes.TACLocalStore;
import shadow.tac.nodes.TACLongToPointer;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACPointerToLong;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceElement;
import shadow.tac.nodes.TACSingletonRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACTypeId;
import shadow.tac.nodes.TACUnary;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InstantiationException;
import shadow.typecheck.type.InterfaceType;
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

public class TACBuilder extends ShadowBaseVisitor<Void> {
	private TACNode anchor;	
	private TACMethod method;
	private TACOperand prefix;
	private boolean explicitSuper;	
	private TACBlock block;	
	private Deque<TACModule> moduleStack = new ArrayDeque<TACModule>();	 	
	
	public TACModule build(Context node) {		
		//anchor = new TACTree(); //no block				
		method = null;
		prefix = null;
		explicitSuper = false;
		block = null;
		visit(node);
		return moduleStack.pop();
	}
	
	@Override
	public Void visit(ParseTree node)
	{
		Context context = (Context) node;
		TACNode saveList = anchor;
		anchor = new TACDummyNode(context, block);
		context.accept(this);		
		//take out dummy node and save the resulting list in the context
		context.setList(anchor.remove());
		anchor = saveList; 
		
		return null;
	}
	
	@Override
	public Void visitChildren(RuleNode node) {
		TACNode saveList = anchor;
		
		for( int i = 0; i < node.getChildCount(); i++ ) {			
			ParseTree child = node.getChild(i);
			
			if( child instanceof Context) {
				Context context = (Context) child;			
				anchor = new TACDummyNode(context, block);			
				context.accept(this);			
				//take out dummy node and save the resulting list in the context
				context.setList(anchor.remove());
			}
			else
				child.accept(this);
		}
		
		anchor = saveList;

		return null;
	}	
		
	private static Type resolveType(Type type) {
		if( type instanceof PropertyType )
			return ((PropertyType)type).getGetType().getType();
		else
			return type;
	}
	
	@Override public Void visitClassOrInterfaceDeclaration(ShadowParser.ClassOrInterfaceDeclarationContext ctx)
	{ 
		Type type = ctx.getType();
		TACModule newModule = new TACModule(type);
		
		if( !moduleStack.isEmpty() )
			moduleStack.peek().addInnerClass(newModule);
		moduleStack.push(newModule);
		
		TACBlock oldBlock = block;
		
		//dummy method and block for constant building
		method = new TACMethod( new MethodSignature(new MethodType(), "", type, null));
		block = new TACBlock(method);
		
		for (ShadowParser.VariableDeclaratorContext constant : type.getFields().values())
			if (constant.getModifiers().isConstant())
				visitConstant(new TACConstant(type,
						constant.generalIdentifier().getText()), constant);
		block = oldBlock;
		
		for (List<MethodSignature> methods : type.getMethodMap().values())
			for (MethodSignature method : methods)
				if (method.isCreate() || method.getModifiers().isPrivate())
					visitMethod(method);
		if (newModule.isClass())
			for (InterfaceType interfaceType : type.getAllInterfaces())
				for (MethodSignature method : interfaceType.orderMethods(moduleStack.peek().
						getClassType()))
					if (method.isWrapper())
						visitMethod(method);		
						
		for (MethodSignature method : type.orderMethods())
			visitMethod(method);

		//TACTree saveTree = tree; //already saved by visitor		
		ShadowParser.ClassOrInterfaceBodyContext body = ctx.classOrInterfaceBody();
		for( ShadowParser.ClassOrInterfaceBodyDeclarationContext declaration : body.classOrInterfaceBodyDeclaration() ) {
			if( declaration.classOrInterfaceDeclaration() != null )					
				build(declaration.classOrInterfaceDeclaration());
		}
		//tree = saveTree;
		
		return null; //no children
	}	

	
	@Override public Void visitVariableDeclarator(ShadowParser.VariableDeclaratorContext ctx)
	{ 
		visitChildren(ctx);
	
		if( !(ctx.getType() instanceof SingletonType) ) { 
			String name = ctx.generalIdentifier().getText();			
			if( ctx.getModifiers().isField() ) {
				TACReference ref = new TACFieldRef(new TACLocalLoad(anchor, method.getThis()), name);
				
				if( ctx.conditionalExpression() != null )
					new TACStore(anchor, ref, ctx.conditionalExpression().appendBefore(anchor));
				//Note that default values are now removed for regular types
				//Defaults for nullables and ArrayTypes are unnecessary, since calloc is used to allocate objects
				//else if( ctx.getModifiers().isNullable() || ctx.getType() instanceof ArrayType )
					//new TACStore(anchor, ref, getDefaultValue(ctx));
			}
			else {
				TACVariable var = method.addLocal(ctx, name);
				if( ctx.conditionalExpression() != null )
					new TACLocalStore(anchor, var, ctx.conditionalExpression().appendBefore(anchor));
				else
					new TACLocalStore(anchor, var, new TACLiteral(anchor, new ShadowUndefined(ctx.getType())));
			}			
		}
		
		return null;
	}
	
	@Override public Void visitArrayInitializer(ShadowParser.ArrayInitializerContext ctx)
	{ 
		visitChildren(ctx);
		
		List<TACOperand> sizes = new ArrayList<TACOperand>();					
		//either the list of initializers or conditional expressions will be empty
		sizes.add(new TACLiteral(anchor, new ShadowInteger(ctx.arrayInitializer().size() + ctx.conditionalExpression().size())));
		ArrayType arrayType = (ArrayType)ctx.getType();
		TACClass baseClass = new TACClass(anchor, arrayType.getBaseType());
		//allocate array
		prefix = visitArrayAllocation(arrayType, baseClass, sizes);
		
		List<? extends Context> list;		
		if( !ctx.arrayInitializer().isEmpty() )
			list = ctx.arrayInitializer();
		else
			list = ctx.conditionalExpression();
		
		//store each element in initializer into the array
		for( int i = 0; i < list.size(); ++i ) {
			// last parameter of false means no array bounds checking needed
			TACArrayRef ref = new TACArrayRef(anchor, prefix, new TACLiteral(anchor, new ShadowInteger(i)), false); 
			new TACStore(anchor, ref, list.get(i).appendBefore(anchor));				
		}		

		ctx.setOperand(prefix);
		
		return null;
	}
	
	private void initializeSingletons(MethodSignature signature)
	{
		for( SingletonType type :  signature.getSingletons() ) {		
			TACLabel initLabel = new TACLabel(method),
					doneLabel = new TACLabel(method);
			TACSingletonRef reference = new TACSingletonRef(type);
			TACOperand instance = new TACLoad(anchor, reference);
			new TACBranch(anchor, new TACBinary(anchor, instance, new TACLiteral(anchor, new ShadowNull(instance.getType()))), initLabel, doneLabel);
			initLabel.insertBefore(anchor);
			
			TACMethodRef methodRef = new TACMethodRef(anchor, type.getMethods("create").get(0));
			TACOperand object = new TACNewObject(anchor, type);
			TACCall call = new TACCall(anchor, methodRef, object );			
			new TACStore(anchor, reference, call ); 				
			new TACBranch(anchor, doneLabel);
			doneLabel.insertBefore(anchor);
		}
	}
	
	private static boolean isTerminator(TACNode node)
	{
		return( node instanceof TACBranch ||
				node instanceof TACResume ||
				node instanceof TACReturn ||
				node instanceof TACThrow );
	}
	
	@Override public Void visitMethodDeclaration(ShadowParser.MethodDeclarationContext ctx)
	{ 
		initializeSingletons(ctx.getSignature());		
		visitChildren(ctx);
		
		ctx.block().appendBefore(anchor);
		
		TACNode last = anchor.getPrevious();		
		//A non-void method should always have explicit TACReturns
		//A void one might need one inserted at the end			
		if( method.getSignature().isVoid() && !isTerminator(last) ) {
			
			//turn context off to avoid dead code removal errors
			Context context = anchor.getContext();
			anchor.setContext(null);			

			//do the cleanup, de-referencing variables
			visitCleanup(null, null);
			
			TACReturn explicitReturn = new TACReturn(anchor, new SequenceType() );
			
			anchor.setContext(context);
			
			new TACLabel(method).insertBefore(anchor); //unreachable label
		}
		
		return null;
	}
	
	@Override public Void visitCreateDeclaration(ShadowParser.CreateDeclarationContext ctx)
	{ 
		initializeSingletons(ctx.getSignature());
		visitChildren(ctx); 
		
		if( ctx.createBlock() != null ) //possible because we still walk dummy nodes created for default creates
			ctx.createBlock().appendBefore(anchor);
		
		TACNode last = anchor.getPrevious();
		
		if (last instanceof TACLabel && last.getPrevious() instanceof TACReturn)
			last.remove();
		else {
			anchor.setContext(null);			
			//do the cleanup, de-referencing variables
			visitCleanup(null, null);			
			//turn context back on
			anchor.setContext(ctx);

			new TACReturn(anchor, method.getSignature().getFullReturnTypes(),
					new TACLocalLoad(anchor, method.getLocal("this")));			
			new TACLabel(method).insertBefore(anchor); //unreachable label
		}
		
		return null;
	}
	
	@Override public Void visitDestroyDeclaration(ShadowParser.DestroyDeclarationContext ctx)
	{ 
		initializeSingletons(ctx.getSignature());
		visitChildren(ctx);
		if( ctx.block() != null ) //possible because we still walk dummy nodes created for default destroys
			ctx.block().appendBefore(anchor);
		
		return null;
	}
	
	//here
	
	@Override public Void visitExplicitCreateInvocation(ShadowParser.ExplicitCreateInvocationContext ctx)
	{ 
		visitChildren(ctx);
	
		boolean isSuper = ctx.getChild(0).getText().equals("super");
		ClassType thisType = (ClassType)method.getSignature().getOuter();
		List<TACOperand> params = new ArrayList<TACOperand>();
		params.add(new TACLocalLoad(anchor, method.getThis()));
		
		if( (!isSuper && thisType.hasOuter()) || (isSuper && thisType.getExtendType().hasOuter()) ) {
			TACVariable outer = method.getParameter("_outer");
			params.add(new TACLocalLoad(anchor, outer));
		}
		
		for( ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression())			 
			params.add(child.appendBefore(anchor));
		
		TACCall call = new TACCall(anchor, new TACMethodRef(anchor, ctx.getSignature()), params);
		call.setDelegatedCreate(true);
		
		// If a super create, walk the fields.
		// Walking the fields is unnecessary if there is a this create,
		// since it will be taken care of by the this create, either explicitly or implicitly.
		if( isSuper ) {
			// Walk fields in *exactly* the order they were declared since
			// some fields depend on prior fields.
			// This is accomplished by using a LinkedHashMap.
			for( Context field : thisType.getFields().values() ) 
				if (!field.getModifiers().isConstant() && !(field.getType() instanceof SingletonType)) {
					visit(field);
					field.appendBefore(anchor);
				}
		}
		
		return null;
	}

	
	@Override public Void visitFormalParameters(ShadowParser.FormalParametersContext ctx)
	{
		return null; //no children
	}
	
	@Override public Void visitFormalParameter(ShadowParser.FormalParameterContext ctx)
	{ 
		visitChildren(ctx);
		
		//The FormalParameters rule is NOT visited
		//Parameters for methods are handled separately
		//The only thing that comes in here are the declarations
		//in catch blocks
		method.addLocal(ctx, ctx.Identifier().getText());
		
		return null;
	}
		
	private void doAssignment(TACOperand left, Context leftAST, TACOperand right, String operation, Context node )
	{
		TACMethodRef methodRef;
		MethodSignature signature;
		Type leftType = leftAST.getType();
		
		operation = operation.substring(0, operation.length() - 1); //clip off last character (=)

		if( leftType instanceof PropertyType ) {					
			PropertyType propertyType = (PropertyType) leftType;
			List<TACOperand> parameters = new ArrayList<TACOperand>();
			
			parameters.add( left );
			if( propertyType instanceof SubscriptType )	{
				//must be a primary expression with a subscript at the end in order to have SubscriptType
				ShadowParser.PrimaryExpressionContext expression = (PrimaryExpressionContext) leftAST;				
				ShadowParser.SubscriptContext subscript = expression.primarySuffix(expression.primarySuffix().size() - 1).subscript(); //subscript on last suffix
				parameters.add(subscript.conditionalExpression(0).getOperand()); //there should only be one index in this list (not needed for properties)
			}
						
			if (!operation.isEmpty() ) {				
				signature = propertyType.getGetter();	
				methodRef = new TACMethodRef(anchor,
						left, //prefix
						signature);
				//TODO: Might need signature without type arguments?
				parameters.set(0, methodRef.getPrefix()); //replacing left with the method prefix can prevent duplicate code (if there were casts)					
				TACOperand result = new TACCall(anchor, methodRef, parameters);					
				
				//signature for other operation
				signature = node.getOperations().get(0);
				
				if( left.getType().isPrimitive() && signature.getModifiers().isNative() && signature.getModifiers().isExtern() )
					right = new TACBinary(anchor, result, signature, operation, right);
				else {
					TACVariable temp = method.addTempLocal(signature.getReturnTypes().get(0));
					methodRef = new TACMethodRef(anchor, result, signature);
					new TACLocalStore(anchor, temp, new TACCall(anchor, methodRef, methodRef.getPrefix(), right));		
					right = new TACLocalLoad(anchor, temp);						
				}		
				
				parameters = new ArrayList<TACOperand>( parameters );					
			}				
			
			parameters.add(right); //value to store (possibly updated by code above)
			
			signature = propertyType.getSetter();	
			methodRef = new TACMethodRef(anchor,
					left, //prefix
					signature);
			//TODO: Might need signature without type arguments?
			parameters.set(0, methodRef.getPrefix()); //replacing left with the method prefix can prevent duplicate code (if there were casts)
			new TACCall(anchor, methodRef, parameters);
		}			
		else if( left instanceof TACLoad ){ //memory operation: field, array, etc.	
			TACReference var = ((TACLoad)left).getReference();
			if (!operation.isEmpty()) {	
				signature = node.getOperations().get(0);
				if( left.getType().isPrimitive() && signature.getModifiers().isNative() )
					right = new TACBinary(anchor, left, signature, operation, right );
				else {
					TACVariable temp = method.addTempLocal(signature.getReturnTypes().get(0));
					methodRef = new TACMethodRef(anchor, left, signature);
					new TACLocalStore(anchor, temp, new TACCall(anchor, methodRef, methodRef.getPrefix(), right));		
					right = new TACLocalLoad(anchor, temp);						
				}
			}
			else {
				//in straight assignment, loading the left is unnecessary because we're just going to store there
				//we had to build in the load so that we had something to get, but now we don't need it
				left.remove();
			}
			
			new TACStore(anchor, var, right);				
		}
		else if( left instanceof TACLocalLoad || left instanceof TACLocalStore ) { //local variable operation			
			TACVariable var;
			if( left instanceof TACLocalLoad )
				var = ((TACLocalLoad)left).getVariable();
			else
				var = ((TACLocalStore)left).getVariable();
			if (!operation.isEmpty()) {
				signature = node.getOperations().get(0);
				if( left.getType().isPrimitive() && signature.getModifiers().isNative() )
					right = new TACBinary(anchor, left, signature, operation, right );
				else {
					TACVariable temp = method.addTempLocal(signature.getReturnTypes().get(0));
					methodRef = new TACMethodRef(anchor, left, signature);
					new TACLocalStore(anchor, temp, new TACCall(anchor, methodRef, methodRef.getPrefix(), right));		
					right = new TACLocalLoad(anchor, temp);						
				}
			}
			else if( left instanceof TACLocalLoad )
				left.remove(); //once we know the var, we can remove the local load
			
			new TACLocalStore(anchor, var, right);	
		}
		else
			throw new UnsupportedOperationException();
	}
	
	@Override public Void visitExpression(ShadowParser.ExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand left = ctx.primaryExpression().appendBefore(anchor);
		
		if( ctx.assignmentOperator() != null ) {					
			TACOperand right = ctx.conditionalExpression().appendBefore(anchor);							
			String operation = ctx.assignmentOperator().getText();			
			doAssignment(left, ctx.primaryExpression(), right, operation, ctx);
		}		
		
		return null;
	}
	
	@Override public Void visitConditionalExpression(ShadowParser.ConditionalExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand condition = ctx.coalesceExpression().appendBefore(anchor);
		ctx.setOperand(condition);
		
		if( ctx.throwOrConditionalExpression().size() > 0 ) {
			ThrowOrConditionalExpressionContext first = ctx.throwOrConditionalExpression(0);
			ThrowOrConditionalExpressionContext second = ctx.throwOrConditionalExpression(1);
			
			TACLabel trueLabel = new TACLabel(method),
					falseLabel = new TACLabel(method),
					doneLabel = new TACLabel(method);
			TACVariable var = method.addTempLocal(ctx);
			new TACBranch(anchor, condition, trueLabel, falseLabel);
			
			trueLabel.insertBefore(anchor);
			if(first.throwStatement() != null) {
				first.throwStatement().getList().appendBefore(anchor);
			} else {
				new TACLocalStore(anchor, var, first.appendBefore(anchor));
				new TACBranch(anchor, doneLabel);
			}
			
			falseLabel.insertBefore(anchor);
			if(second.throwStatement() != null) {
				second.throwStatement().getList().appendBefore(anchor);				
			} else {
				new TACLocalStore(anchor, var, second.appendBefore(anchor));
				new TACBranch(anchor, doneLabel);
			}
			
			doneLabel.insertBefore(anchor);
			
			ctx.setOperand(new TACLocalLoad(anchor, var));
		}
		
		return null;
	}
	
	@Override public Void visitThrowOrConditionalExpression(ThrowOrConditionalExpressionContext ctx) 
	{
		visitChildren(ctx);
		
		if(ctx.conditionalExpression() != null) {
			ctx.setOperand(ctx.conditionalExpression().appendBefore(anchor));
		}
		
		return null;
	}
	
	@Override public Void visitCoalesceExpression(ShadowParser.CoalesceExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand value = ctx.conditionalOrExpression(0).appendBefore(anchor);
		ctx.setOperand(value);
		
		if( ctx.conditionalOrExpression().size() > 1 ) {
			TACLabel doneLabel = new TACLabel(method);
			TACVariable var = method.addTempLocal(ctx);
			
			for( int i = 1; i < ctx.conditionalOrExpression().size(); ++i ) {
				TACLabel nullLabel = new TACLabel(method);
				TACLabel nonNullLabel = new TACLabel(method);
				new TACBranch(anchor, new TACBinary(anchor, value, new TACLiteral(anchor, new ShadowNull(value.getType()))), nullLabel, nonNullLabel);
				nonNullLabel.insertBefore(anchor);
				new TACLocalStore(anchor, var, value);
				new TACBranch(anchor, doneLabel);
				nullLabel.insertBefore(anchor);
				value = ctx.conditionalOrExpression(i).appendBefore(anchor);
			}			

			//whatever the final thing is, we're stuck with it if we got that far
			new TACLocalStore(anchor, var, value);
			new TACBranch(anchor, doneLabel);			
			doneLabel.insertBefore(anchor);			
			ctx.setOperand(new TACLocalLoad(anchor, var));
		}
		
		return null;
	}
	
	@Override public Void visitConditionalOrExpression(ShadowParser.ConditionalOrExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand value = ctx.conditionalExclusiveOrExpression(0).appendBefore(anchor);
		ctx.setOperand(value);
	
		if( ctx.conditionalExclusiveOrExpression().size() > 1 ) {
			TACLabel doneLabel = new TACLabel(method);
			TACVariable var = method.addTempLocal(ctx);			
			new TACLocalStore(anchor, var, value);
			for (int i = 1; i < ctx.conditionalExclusiveOrExpression().size(); i++) {
				TACLabel nextLabel = new TACLabel(method);
				new TACBranch(anchor, value, doneLabel, nextLabel);
				nextLabel.insertBefore(anchor);
				value = ctx.conditionalExclusiveOrExpression(i).appendBefore(anchor);
				new TACLocalStore(anchor, var, value);
			}
			new TACBranch(anchor, doneLabel);
			doneLabel.insertBefore(anchor);
			ctx.setOperand(new TACLocalLoad(anchor, var));
		}
		
		return null;
	}
	
	@Override public Void visitConditionalExclusiveOrExpression(ShadowParser.ConditionalExclusiveOrExpressionContext ctx) 
	{
		visitChildren(ctx); 		
		TACOperand value = ctx.conditionalAndExpression(0).appendBefore(anchor);
		for( int i = 1; i < ctx.conditionalAndExpression().size(); i++ ) {
			TACOperand next = ctx.conditionalAndExpression(i).appendBefore(anchor);										
			value = new TACBinary(anchor, value, TACBinary.Boolean.XOR, next);
		}		
		
		ctx.setOperand(value);
		
		return null;
	}
	
	@Override public Void visitConditionalAndExpression(ShadowParser.ConditionalAndExpressionContext ctx)
	{ 
		visitChildren(ctx);
		TACOperand value = ctx.bitwiseOrExpression(0).appendBefore(anchor);
		ctx.setOperand(value);
	
		if( ctx.bitwiseOrExpression().size() > 1 ) {
			TACLabel doneLabel = new TACLabel(method);
			TACVariable var = method.addTempLocal(ctx);			
			new TACLocalStore(anchor, var, value);
			for (int i = 1; i < ctx.bitwiseOrExpression().size(); i++) {
				TACLabel nextLabel = new TACLabel(method);
				new TACBranch(anchor, value, nextLabel, doneLabel);
				nextLabel.insertBefore(anchor);
				value = ctx.bitwiseOrExpression(i).appendBefore(anchor);
				new TACLocalStore(anchor, var, value);
			}
			new TACBranch(anchor, doneLabel);
			doneLabel.insertBefore(anchor);
			ctx.setOperand(new TACLocalLoad(anchor, var));
		}
		
		return null;	
	}
	
	
	@Override public Void visitBitwiseOrExpression(ShadowParser.BitwiseOrExpressionContext ctx)
	{ 
		visitChildren(ctx);
		visitBinaryOperation(ctx, ctx.bitwiseExclusiveOrExpression());
		
		return null;
	}

	@Override public Void visitBitwiseExclusiveOrExpression(ShadowParser.BitwiseExclusiveOrExpressionContext ctx)
	{ 
		visitChildren(ctx);
		visitBinaryOperation(ctx, ctx.bitwiseAndExpression());
		
		return null;
	}
	
	@Override public Void visitBitwiseAndExpression(ShadowParser.BitwiseAndExpressionContext ctx)
	{ 
		visitChildren(ctx);
		visitBinaryOperation(ctx, ctx.equalityExpression());
		
		return null;
	}	

	@Override public Void visitEqualityExpression(ShadowParser.EqualityExpressionContext ctx)
	{ 
		visitChildren(ctx);		
		
		TACOperand current = ctx.isExpression(0).appendBefore(anchor);		
		for( int i = 1; i < ctx.isExpression().size(); i++ ) {
			String op = ctx.getChild(2*i - 1).getText();  //the operations are every other child
			TACOperand next = ctx.isExpression(i).appendBefore(anchor);
			Type currentType = current.getType();
			Type nextType = next.getType();
							
			if( op.equals("==") || op.equals("!=") ) { //== or !=									
				if (currentType.isPrimitive() &&
					nextType.isPrimitive() ) //if not, methods are needed					
					current = new TACBinary(anchor, current, next);					
				else {	
					//no nullables allowed
					TACVariable var = method.addTempLocal(ctx);
					Type valueType = resolveType(current.getType());
					MethodSignature signature = valueType.getMatchingMethod("equal", new SequenceType(next));												
					new TACLocalStore(anchor, var, new TACCall(anchor,
							new TACMethodRef(anchor, current, signature),
							current, next));
					current = new TACLocalLoad(anchor, var);
				}						
			}
			else { //=== or !==				
				boolean currentNullable = current.getModifiers().isNullable();
				boolean nextNullable = next.getModifiers().isNullable();
				if( currentType.isPrimitive() && nextType.isPrimitive() && ( currentNullable || nextNullable )) {
					TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.BOOLEAN));
					TACLabel done = new TACLabel(method);
					
					if( currentNullable && nextNullable ) {							
						TACOperand valueNull = new TACBinary(anchor, current, new TACLiteral(anchor, new ShadowNull(current.getType())));
						TACOperand otherNull = new TACBinary(anchor, next, new TACLiteral(anchor, new ShadowNull(next.getType())));							
						TACOperand bothNull = new TACBinary(anchor, valueNull, TACBinary.Boolean.AND, otherNull );
						TACOperand eitherNull = new TACBinary(anchor, valueNull, TACBinary.Boolean.OR, otherNull );							
						TACLabel notBothNull = new TACLabel(method);												
						TACLabel noNull = new TACLabel(method);
						
						new TACLocalStore(anchor, var, bothNull);
						new TACBranch(anchor, bothNull, done, notBothNull); //var will be true (both null)
						
						notBothNull.insertBefore(anchor);
						new TACBranch(anchor, eitherNull, done, noNull); //var will be false (one but not both null)
													
						noNull.insertBefore(anchor);
						new TACLocalStore(anchor, var, new TACBinary(anchor,
								TACCast.cast(anchor, new SimpleModifiedType(current.getType()), current),
								TACCast.cast(anchor, new SimpleModifiedType(next.getType()), next)));
						new TACBranch(anchor, done);
					}
					else if( currentNullable ) { //only current nullable							
						TACOperand currentNull = new TACBinary(anchor, current, new TACLiteral(anchor, new ShadowNull(current.getType())));							
						TACLabel oneNull = new TACLabel(method);												
						TACLabel noNull = new TACLabel(method);														
						new TACBranch(anchor, currentNull, oneNull, noNull);
						oneNull.insertBefore(anchor);
						new TACLocalStore(anchor, var, new TACLiteral(anchor, new ShadowBoolean(false)));		
						new TACBranch(anchor, done);														
						noNull.insertBefore(anchor);
						new TACLocalStore(anchor, var, new TACBinary(anchor,
								TACCast.cast(anchor, new SimpleModifiedType(current.getType()), current),
								next));
						new TACBranch(anchor, done);							
					}
					else { //only next nullable 
						TACOperand nextNull = new TACBinary(anchor, next, new TACLiteral(anchor, new ShadowNull(next.getType())));							
						TACLabel oneNull = new TACLabel(method);												
						TACLabel noNull = new TACLabel(method);														
						new TACBranch(anchor, nextNull, oneNull, noNull);
						oneNull.insertBefore(anchor);
						new TACLocalStore(anchor, var, new TACLiteral(anchor, new ShadowBoolean(false)));									
						new TACBranch(anchor, done);														
						noNull.insertBefore(anchor);
						new TACLocalStore(anchor, var, new TACBinary(anchor,
								current,
								TACCast.cast(anchor, new SimpleModifiedType(next.getType()), next)));
						new TACBranch(anchor, done);
					}
					
					done.insertBefore(anchor);
					current = new TACLocalLoad(anchor, var);
				}
				else //both non-nullable primitives or both references, no problem		
					current = new TACBinary(anchor, current, next);
			}
			if( op.startsWith("!") )
				current = new TACUnary(anchor, "!", current);
		}		
		ctx.setOperand(current);
		
		return null;
	}
	
	@Override public Void visitIsExpression(ShadowParser.IsExpressionContext ctx)
	{ 
		visitChildren(ctx);
		TACOperand value = ctx.relationalExpression().appendBefore(anchor);
		ctx.setOperand(value);
		
		if( ctx.type() != null ) {						
			Type comparisonType = ctx.type().getType();
			
			if( comparisonType instanceof ArrayType ) {
				ArrayType arrayType = (ArrayType) comparisonType;
				comparisonType = arrayType.convertToGeneric();
			}
			
			TACOperand comparisonClass = new TACClass(anchor, comparisonType).getClassData();

			//get class from object
			TACMethodRef methodRef = new TACMethodRef(anchor, value,
					Type.OBJECT.getMatchingMethod("getClass", new SequenceType()));						
			TACOperand valueClass = new TACCall(anchor, methodRef, methodRef.getPrefix());

			methodRef = new TACMethodRef(anchor, valueClass,
					Type.CLASS.getMatchingMethod("isSubtype", new SequenceType(Type.CLASS)));
			
			ctx.setOperand(new TACCall(anchor, methodRef, methodRef.getPrefix(), comparisonClass));
		}
		
		return null;
	}
	
	
	@Override public Void visitRelationalExpression(ShadowParser.RelationalExpressionContext ctx)	
	{ 
		visitChildren(ctx);
		visitBinaryOperation(ctx, ctx.concatenationExpression());
		
		return null;
	}	
	
	private TACOperand convertToString( TACOperand operand ) {
		Type type = resolveType(operand.getType());
		if( !type.equals(Type.STRING ) ) {
			if ( operand.getModifiers().isNullable() && !(type instanceof ArrayType) ) { // || !type.isPrimitive() && !(type instanceof ArrayType))
				TACLabel nullLabel = new TACLabel(method),
						nonnullLabel = new TACLabel(method),
						doneLabel = new TACLabel(method);
				TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.STRING));
				new TACBranch(anchor, new TACBinary(anchor, operand,
						new TACLiteral(anchor, new ShadowNull(operand.getType()))), nullLabel,
						nonnullLabel);
				nullLabel.insertBefore(anchor);
				new TACLocalStore(anchor, var, new TACLiteral(anchor, new ShadowString("null")));
				new TACBranch(anchor, doneLabel);
				nonnullLabel.insertBefore(anchor);
				
				if( type.isPrimitive() ) //convert non null primitive wrapper to real primitive			
					operand = TACCast.cast(anchor, new SimpleModifiedType(type), operand);
				
				TACMethodRef methodRef = new TACMethodRef(anchor, operand,
						type.getMatchingMethod("toString", new SequenceType())); 
				
				new TACLocalStore(anchor, var, new TACCall(anchor, methodRef, methodRef.getPrefix()));
				new TACBranch(anchor, doneLabel);
				doneLabel.insertBefore(anchor);
				operand = new TACLocalLoad(anchor, var);
			}
			else {
				TACMethodRef methodRef = new TACMethodRef(anchor, operand,
						type.getMatchingMethod("toString", new SequenceType())); 
				operand = new TACCall(anchor, methodRef,
						Collections.singletonList(methodRef.getPrefix()));
			}
		}
				
		return operand;
	}
	
	@Override public Void visitConcatenationExpression(ShadowParser.ConcatenationExpressionContext ctx)
	{ 
		visitChildren(ctx);
		TACOperand last = ctx.shiftExpression(0).appendBefore(anchor);
				
		for( int i = 1; i < ctx.shiftExpression().size(); ++i  ) {
			if( i == 1 )
				last = convertToString(last);
			
			TACOperand next = convertToString(ctx.shiftExpression(i).appendBefore(anchor));
			last = new TACCall(anchor, new TACMethodRef(anchor, Type.STRING.getMethods("concatenate").get(0)),
					Arrays.asList(last, next));
		}
		
		ctx.setOperand(last);			
		
		return null;
	}
	
	@Override public Void visitShiftExpression(ShadowParser.ShiftExpressionContext ctx)
	{ 
		visitChildren(ctx);		
		visitBinaryOperation(ctx, ctx.rotateExpression());
		
		return null;		
	}
	
	@Override public Void visitRotateExpression(ShadowParser.RotateExpressionContext ctx)
	{ 
		visitChildren(ctx);		
		visitBinaryOperation(ctx, ctx.additiveExpression());
		
		return null;	
	}
	
	@Override public Void visitAdditiveExpression(ShadowParser.AdditiveExpressionContext ctx)
	{ 
		visitChildren(ctx);		
		visitBinaryOperation(ctx, ctx.multiplicativeExpression());
		
		return null;
	}
	
	@Override public Void visitMultiplicativeExpression(ShadowParser.MultiplicativeExpressionContext ctx)
	{ 
		visitChildren(ctx);		
		visitBinaryOperation(ctx, ctx.unaryExpression());
		
		return null;		
	}
	
	@Override public Void visitUnaryExpression(ShadowParser.UnaryExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		if( ctx.primaryExpression() != null )
			ctx.setOperand(ctx.primaryExpression().appendBefore(anchor));
		else if( ctx.inlineMethodDefinition() != null )
			ctx.setOperand(ctx.inlineMethodDefinition().appendBefore(anchor));
		else { //unary is happening
			TACOperand operand = ctx.unaryExpression().appendBefore(anchor);
			String op = ctx.getChild(0).getText(); 
			if( op.equals("#")) //string is special because of nulls
				ctx.setOperand(convertToString( operand ));			
			else {
				Type type = resolveType(operand.getType());					
				if( op.equals("!") )
					ctx.setOperand(new TACUnary(anchor, "!", operand));
				else {
					MethodSignature signature = ctx.getOperations().get(0); 
					if( type.isPrimitive() && signature.getModifiers().isNative() && signature.getModifiers().isExtern() )
						ctx.setOperand(new TACUnary(anchor, signature, op, operand));				
					else 
						ctx.setOperand(new TACCall(anchor, new TACMethodRef(anchor, operand, ctx.getOperations().get(0)), operand));					
				}
			}
		}		
		
		return null;	
	}
	
	@Override public Void visitInlineMethodDefinition(ShadowParser.InlineMethodDefinitionContext ctx)
	{ 		
		throw new UnsupportedOperationException();
	}
	
	@Override public Void visitCastExpression(ShadowParser.CastExpressionContext ctx)
	{ 
		//one day this will be supported
		if( ctx.getType() instanceof MethodType )
			throw new UnsupportedOperationException();
		
		visitChildren(ctx);		
		ctx.setOperand(TACCast.cast(anchor, ctx, ctx.conditionalExpression().appendBefore(anchor), true));
		
		return null;
	}
	
	@Override public Void visitCheckExpression(ShadowParser.CheckExpressionContext ctx)	
	{ 
		visitChildren(ctx);
		TACLabel recover;
		
		if( block.hasRecover() )
			recover = block.getRecover();
		else
			recover = new TACLabel(method);				
			
		TACLabel continueLabel = new TACLabel(method);
		TACOperand operand = ctx.conditionalExpression().appendBefore(anchor);
		
		//if there's a recover, things will be handled there if null			
		new TACBranch(anchor, new TACBinary(anchor, operand, new TACLiteral(anchor,
				new ShadowNull(operand.getType()))), recover, continueLabel);
		
		//otherwise, we throw an exception here
		if( !block.hasRecover() ) {
			recover.insertBefore(anchor);
			TACOperand object = new TACNewObject(anchor, Type.UNEXPECTED_NULL_EXCEPTION);
			MethodSignature signature = Type.UNEXPECTED_NULL_EXCEPTION.getMatchingMethod("create", new SequenceType());						
			TACCall exception = new TACCall(anchor, new TACMethodRef(anchor, signature), object);
						
			new TACThrow(anchor, exception);
		}	
		
		continueLabel.insertBefore(anchor);
		
		//add in cast to remove nullable?
		prefix = TACCast.cast(anchor, ctx, operand);
		ctx.setOperand(prefix);
		
		return null;
	}
	
	@Override public Void visitPrimaryExpression(ShadowParser.PrimaryExpressionContext ctx)
	{ 		
		TACOperand savePrefix = prefix;
		prefix = null;		
		visitChildren(ctx);
		
		TACOperand operand = ctx.primaryPrefix().appendBefore(anchor);
		for( ShadowParser.PrimarySuffixContext suffix : ctx.primarySuffix() )
			operand = suffix.appendBefore(anchor);
		
		ctx.setOperand(operand);
		prefix = savePrefix;
		
		return null;
	}
	
	@Override public Void visitPrimarySuffix(ShadowParser.PrimarySuffixContext ctx)
	{ 
		visitChildren(ctx);
		
		Context child = (Context)ctx.getChild(0);
		ctx.setOperand(child.appendBefore(anchor));
		
		return null;
	}
	
	@Override public Void visitAllocation(ShadowParser.AllocationContext ctx)
	{
		visitChildren(ctx);
		
		Context child = (Context)ctx.getChild(0);
		ctx.setOperand(child.appendBefore(anchor));
		
		return null;		
	}

	@Override public Void visitPrimaryPrefix(ShadowParser.PrimaryPrefixContext ctx)
	{ 
		visitChildren(ctx);
		
		//TODO: remove prefix, since the value can be gotten from the Context node?		
		if( ctx.literal() != null )
			prefix = ctx.literal().appendBefore(anchor);
		else if( ctx.checkExpression() != null ) 
			prefix = ctx.checkExpression().appendBefore(anchor);
		else if( ctx.copyExpression() != null )
			prefix = ctx.copyExpression().appendBefore(anchor);
		else if( ctx.castExpression() != null )
			prefix = ctx.castExpression().appendBefore(anchor);
		else if( ctx.conditionalExpression() != null )
			prefix = ctx.conditionalExpression().appendBefore(anchor);
		else if( ctx.primitiveType() != null )
			prefix = ctx.primitiveType().appendBefore(anchor);
		else if( ctx.functionType() != null )
			prefix = ctx.functionType().appendBefore(anchor);
		else if( ctx.arrayInitializer() != null )
			prefix = ctx.arrayInitializer().appendBefore(anchor);
		else if(ctx.spawnExpression() != null)
			prefix = ctx.spawnExpression().appendBefore(anchor);
		else if( ctx.getType() instanceof SingletonType )
			prefix = new TACLoad(anchor, new TACSingletonRef((SingletonType)ctx.getType()));
		else {
			String name = ctx.getText();
			explicitSuper = name.equals("super");
			if (!(ctx.getModifiers().isTypeName() ||
					ctx.getType() instanceof UnboundMethodType)) {
				TACVariable local;
				if( explicitSuper )
					local = method.getLocal("this");
				else
					local = method.getLocal(name);
				if (local != null)
					prefix = new TACLocalLoad(anchor, local);
				else {
					if (ctx.getModifiers().isConstant()) { //constant
						Type thisType = method.getSignature().getOuter();
						//figure out type that defines constant							
						while( !thisType.containsField(name))
							thisType = thisType.getOuter();
						prefix = new TACLoad(anchor, new TACConstantRef(thisType, name));
					}
					else { //field					
						ModifiedType thisRef = method.getThis();
						TACOperand op = new TACLocalLoad(anchor, (TACVariable)thisRef);
						//make chain of this:_outer references until field is found
						while (!op.getType().containsField(name)) {								
							op = new TACLoad(anchor, new TACFieldRef(op,
									new SimpleModifiedType(op.getType().
											getOuter()), "_outer"));
						}							
						prefix = new TACLoad(anchor, new TACFieldRef(op, name));
					}
				}
			}				
		}			
		ctx.setOperand(prefix);
			
		return null;
	}

	private void methodCall(MethodSignature signature, Context node, List<? extends Context> list) {
		if (prefix == null) {			
			prefix = new TACLocalLoad(anchor, method.getThis());
			
			//for outer class method calls
			Type prefixType = prefix.getType().getTypeWithoutTypeArguments();
			Type methodOuter = signature.getOuter().getTypeWithoutTypeArguments();
			
			while( !prefixType.isSubtype(methodOuter) ) {			
				prefix = new TACLoad(anchor, new TACFieldRef(prefix, new SimpleModifiedType(prefixType.getOuter()),
					"_outer"));
				prefixType = prefixType.getOuter();				
			}
		}
		
		TACMethodRef methodRef = new TACMethodRef(anchor,
				prefix,				
				signature);		
		methodRef.setSuper(explicitSuper);
		List<TACOperand> params = new ArrayList<TACOperand>();

		if(!signature.isExtern()) {
			params.add(methodRef.getPrefix());
		}
		for( Context child : list )	//potentially empty list				
			params.add(child.appendBefore(anchor));
		
		prefix = new TACCall(anchor, methodRef, params);
		MethodType methodType = signature.getMethodType();
		//sometimes a cast is needed when dealing with generic types
		SequenceType requiredReturnTypes = methodType.getReturnTypes();
		SequenceType methodReturnTypes = methodRef.getReturnTypes();		
		
		if( !methodReturnTypes.matches(requiredReturnTypes) ) {
			if( requiredReturnTypes.size() == 1 )			
				prefix = TACCast.cast(anchor, requiredReturnTypes.get(0), prefix);
			else
				prefix = TACCast.cast(anchor, new SimpleModifiedType(requiredReturnTypes), prefix);
		}
		
		node.setOperand(prefix);
		explicitSuper = false;		
	}
	
	
	@Override public Void visitQualifiedKeyword(ShadowParser.QualifiedKeywordContext ctx)
	{ 
		throw new UnsupportedOperationException();		
	}
	
	private static Context getPrefix(ShadowParser.ScopeSpecifierContext ctx)
	{
		ShadowParser.PrimarySuffixContext parent = (PrimarySuffixContext) ctx.getParent();
		ShadowParser.PrimaryExpressionContext expression = (PrimaryExpressionContext) parent.getParent();
		int i = 0;
		for( ; i < expression.primarySuffix().size() && expression.primarySuffix(i) != parent; ++i );
		if( i > 0 )
			return expression.primarySuffix(i - 1);
		else
			return expression.primaryPrefix();
	}
	
	@Override public Void visitScopeSpecifier(ShadowParser.ScopeSpecifierContext ctx)
	{ 
		visitChildren(ctx);
		if( ctx.Identifier() == null) { //this is the :class case
			Type type = getPrefix(ctx).getType();			
			//Type type = prefix.getType(); //TODO: Does this work?
			
			boolean raw = false;
			
			if( ctx.typeArguments() != null  ) {
				ShadowParser.TypeArgumentsContext arguments = ctx.typeArguments();
				
				try {
					type = type.replace(type.getTypeParameters(), (SequenceType)arguments.getType());
				}
				catch (InstantiationException e) {}	//should not happen				
			}
			else if( type.isParameterized() ) //parameterized, but no type arguments given
				raw = true;					
			
			prefix = new TACClass(anchor, type, raw).getClassData();
		}
		else if( ctx.getModifiers().isConstant() )
			prefix = new TACLoad(anchor, new TACConstantRef(getPrefix(ctx).getType(), ctx.Identifier().getText()));
		else if( ctx.getType() instanceof SingletonType)
			prefix = new TACLoad(anchor, new TACSingletonRef((SingletonType)ctx.getType()));
		else if( !ctx.getModifiers().isTypeName() )  //doesn't do anything at this stage if it's just a type name				
			prefix = new TACLoad(anchor, new TACFieldRef(prefix, ctx.Identifier().getText()));
		
		ctx.setOperand(prefix);
		
		return null;
	}
	
	private static Context getSuffix(ShadowParser.PrimaryExpressionContext expression)
	{
		if( expression.primarySuffix().size() > 0 )
			return expression.primarySuffix(expression.primarySuffix().size() - 1);
		else
			return expression.primaryPrefix();		
	}
	
	@Override public Void visitSubscript(ShadowParser.SubscriptContext ctx)
	{ 
		visitChildren(ctx);
		//last suffix and on the LHS			
		ShadowParser.PrimarySuffixContext suffix = (PrimarySuffixContext) ctx.getParent();
		ShadowParser.PrimaryExpressionContext expression = (ShadowParser.PrimaryExpressionContext)suffix.getParent(); 
		boolean isStore = isLHS(expression) && suffix == getSuffix(expression);
		Type prefixType = resolveType(prefix.getType());
		
		if( prefixType instanceof ArrayType ) {
			ArrayType arrayType = (ArrayType) prefixType;
			List<TACOperand> list = new ArrayList<TACOperand>(ctx.conditionalExpression().size());
			for(ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression()) {
				TACOperand index = child.appendBefore(anchor);
				//uints, longs, and ulongs can be used as indexes
				//however, ints are required
				Type indexType = index.getType();
				if( indexType.isIntegral() && !indexType.isSubtype(Type.INT))
					index = TACCast.cast(anchor, new SimpleModifiedType(Type.INT, index.getModifiers()), index);
				list.add(index);
			}
			
			if( arrayType.getBaseType() instanceof TypeParameter )
				prefix = new TACLoad(anchor, new TACGenericArrayRef(anchor, prefix, list));
			else
				prefix = new TACLoad(anchor, new TACArrayRef(anchor, prefix, list));
		}				
		else if( ctx.getType() instanceof SubscriptType ) {					
			SubscriptType subscriptType = (SubscriptType) ctx.getType();
			//only do the straight loads
			//stores (and +='s) are handled in ASTExpression
			//only one conditionalExpression is possible
			if( !isStore ) {
				MethodSignature signature = subscriptType.getGetter();
				methodCall(signature, ctx, ctx.conditionalExpression()); //handles appending					
			}
			else {
				//simply append everything (of which there is only one)
				ctx.conditionalExpression(0).appendBefore(anchor);
				
				/*
				List<TACOperand> list = new ArrayList<TACOperand>();
				TACOperand op = tree.appendChild(0);										
				list.add(op);
				ctx.subscripts = list;
				*/					
				//tree.append(prefix); //append the prefix as well
			}
		}		
		else
			throw new UnsupportedOperationException();
		
		ctx.setOperand(prefix);
		
		return null;
	}	
	
	private static boolean isLHS(shadow.parse.ShadowParser.PrimaryExpressionContext context)
	{
		ParserRuleContext parent = context.getParent(); 
		
		if( parent instanceof shadow.parse.ShadowParser.SequenceLeftSideContext )
			return true;
		
		if( parent instanceof shadow.parse.ShadowParser.ExpressionContext ) {
			shadow.parse.ShadowParser.ExpressionContext expression = (ExpressionContext) parent;
			return expression.assignmentOperator() != null;
		}
		
		return false;
	}
	
	
	@Override public Void visitProperty(ShadowParser.PropertyContext ctx)
	{ 
		visitChildren(ctx);

		//last suffix and on the LHS
		ShadowParser.PrimarySuffixContext suffix = (PrimarySuffixContext) ctx.getParent();
		ShadowParser.PrimaryExpressionContext expression = (ShadowParser.PrimaryExpressionContext)suffix.getParent(); 
		boolean isStore = isLHS(expression) && suffix == getSuffix(expression);
		//prefix should never be null			
		Type prefixType = resolveType(prefix.getType());
		
		if( prefixType instanceof ArrayType && ctx.Identifier().getText().equals("size")) {
			//optimization to avoid creating an Array object			
			ArrayType arrayType = (ArrayType)prefixType;				
			TACOperand length = new TACLength(anchor, prefix, 0);
			for (int i = 1; i < arrayType.getDimensions(); i++)
				length = new TACBinary(anchor, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), "*", new TACLength(anchor, prefix, i));
			prefix = length;
		}
		else {
			PropertyType propertyType = (PropertyType) ctx.getType();
			//only do the straight loads
			//stores (and +='s) are handled in ASTExpression
			if( !isStore ) {
				MethodSignature signature = propertyType.getGetter();
				methodCall(signature, ctx, new ArrayList<Context>()); //no parameters to add					
			}
			//else					//it should already be in the tree, right?
				//tree.append(prefix); //append the prefix for future use				
		}
		
		ctx.setOperand(prefix);
		
		return null;
	}


	@Override public Void visitMethodCall(ShadowParser.MethodCallContext ctx)
	{ 
		visitChildren(ctx);
		methodCall(ctx.getSignature(), ctx, ctx.conditionalExpression() ); //handles appending
		
		return null;
	}
	
	@Override public Void visitLiteral(ShadowParser.LiteralContext ctx)
	{ 
		//no children
		prefix = ctx.setOperand(new TACLiteral(anchor, ctx.value));
		
		return null;
	}
	
	@Override public Void visitArguments(ShadowParser.ArgumentsContext ctx)
	{ 
		visitChildren(ctx);		
		for( ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression() ) 
			child.appendBefore(anchor);		
		//no operand saved because the operands are in the children
		
		return null;
	}
	
	@Override public Void visitArrayCreate(ShadowParser.ArrayCreateContext ctx)
	{ 
		visitChildren(ctx);
		
		ShadowParser.ArrayDimensionsContext dimensions = ctx.arrayDimensions();
		dimensions.appendBefore(anchor);		
			
		ArrayType arrayType = (ArrayType)ctx.getType();
		Type type = arrayType.getBaseType();
		TACClass baseClass = new TACClass(anchor, type);		
		List<TACOperand> indices = new ArrayList<TACOperand>(dimensions.conditionalExpression().size());
		for( ShadowParser.ConditionalExpressionContext dimension : dimensions.conditionalExpression() )
			indices.add(dimension.getOperand());
		
		if( ctx.getSignature() != null ) { //either explicit or implicit create call	
			MethodSignature create = ctx.getSignature();
			List<TACOperand> arguments = new ArrayList<TACOperand>();
			if( ctx.arrayCreateCall() != null ) {
				ctx.arrayCreateCall().appendBefore(anchor);
				for( ShadowParser.ConditionalExpressionContext child : ctx.arrayCreateCall().conditionalExpression() )
					arguments.add(child.getOperand());
			}
			prefix = visitArrayAllocation(arrayType, baseClass, indices, create, arguments);
		}
		else if( ctx.arrayDefault() != null ) {
			TACOperand value = ctx.arrayDefault().appendBefore(anchor);
			prefix = visitArrayAllocation(arrayType, baseClass, indices, value);
		}
		else //nullable array only
			prefix = visitArrayAllocation(arrayType, baseClass, indices);
		
		ctx.setOperand(prefix);
		
		return null;
	}
	
	private TACOperand callCreate(MethodSignature signature, List<TACOperand> params, Type prefixType) {
		TACOperand object = new TACNewObject(anchor, prefixType);					
				
		params.add(0, object); //put object in front of other things
		
		//have to pass a reference to outer classes into constructor
		if( !(prefixType instanceof TypeParameter) && prefixType.hasOuter() ) {				
			Type methodOuter = prefixType.getOuter().getTypeWithoutTypeArguments();
			
			if( prefix != null && prefix.getType().isSubtype(methodOuter) )
				params.add(1, prefix); //after object, before args
			else {					
				 TACOperand outer = new TACLocalLoad(anchor, method.getThis());
				 Type outerType = outer.getType().getTypeWithoutTypeArguments();					 
				 while( !outerType.isSubtype(methodOuter) ) {
					outer = new TACLoad(anchor, new TACFieldRef(outer, new SimpleModifiedType(outerType.getOuter()),
							"_outer"));						
					outerType = outerType.getOuter();				
				 }					 
				 params.add(1, outer); //after object, before args
			}
		}		

		TACMethodRef methodRef;		
		if( signature.getOuter() instanceof InterfaceType )
			methodRef = new TACMethodRef(anchor, TACCast.cast(anchor, new SimpleModifiedType(signature.getOuter()), object), signature);
		else
			methodRef = new TACMethodRef(anchor, signature);
				
		prefix = new TACCall(anchor, methodRef, params);
				
		//sometimes a cast is needed when dealing with generic types
		if( !prefix.getType().equals(prefixType) )
			prefix = TACCast.cast(anchor, new SimpleModifiedType(prefixType), prefix);
		
		return prefix;
	}
	
	@Override public Void visitCreate(ShadowParser.CreateContext ctx)
	{ 
		visitChildren(ctx);		
		
		List<TACOperand> params = new ArrayList<TACOperand>(ctx.conditionalExpression().size() + 1);		
		for( ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression())
				params.add(child.appendBefore(anchor));			
		
		MethodSignature signature = ctx.getSignature();
		TACOperand operand;
		//both set prefix
		if( signature.getOuter() instanceof InterfaceType )
			operand = callCreate(signature, params, ((Context)ctx.getParent()).getType() );
		else
			operand = callCreate(signature, params, signature.getOuter());
		
		ctx.setOperand(operand);
		
		return null;
	}
	
	@Override public Void visitStatement(ShadowParser.StatementContext ctx)
	{ 
		visitChildren(ctx);
		Context context = (Context) ctx.getChild(0);
		context.appendBefore(anchor);
		
		return null;
	}
	
	@Override public Void visitAssertStatement(ShadowParser.AssertStatementContext ctx)
	{ 
		visitChildren(ctx);
		
		TACLabel doneLabel = new TACLabel(method),
				errorLabel = new TACLabel(method);
		
		TACOperand condition = ctx.conditionalExpression(0).appendBefore(anchor); 
		
		new TACBranch(anchor, condition, doneLabel, errorLabel);			
		errorLabel.insertBefore(anchor);
		
		TACOperand object = new TACNewObject(anchor, Type.ASSERT_EXCEPTION);
		List<TACOperand> params = new ArrayList<TACOperand>();
		params.add(object);			
		MethodSignature signature;			
		
		if( ctx.conditionalExpression().size() > 1 ) { // has message
			TACOperand message = convertToString( ctx.conditionalExpression(1).appendBefore(anchor) );
			signature = Type.ASSERT_EXCEPTION.getMatchingMethod("create", new SequenceType(message));
			params.add( message );				
		}
		else
			signature = Type.ASSERT_EXCEPTION.getMatchingMethod("create", new SequenceType());

		TACCall exception = new TACCall(anchor, new TACMethodRef(anchor, signature), params);
		new TACThrow(anchor, exception);
		doneLabel.insertBefore(anchor);		
		
		return null;
	}
	
	@Override public Void visitBlock(ShadowParser.BlockContext ctx)
	{ 
		method.enterScope();
		visitChildren(ctx); 
		
		for( ShadowParser.BlockStatementContext statement : ctx.blockStatement() )
			statement.appendBefore(anchor);
		
		method.exitScope();		
		return null;
	}
	
	@Override public Void visitCreateBlock(ShadowParser.CreateBlockContext ctx)
	{ 
		method.enterScope();
		visitChildren(ctx); 
		
		if( ctx.explicitCreateInvocation() != null )
			ctx.explicitCreateInvocation().appendBefore(anchor);
		for( ShadowParser.BlockStatementContext statement : ctx.blockStatement() )
			statement.appendBefore(anchor);
		
		method.exitScope();		
		return null;
	}
	
	@Override public Void visitBlockStatement(ShadowParser.BlockStatementContext ctx)
	{ 
		visitChildren(ctx);
		if( ctx.localDeclaration() != null )
			ctx.localDeclaration().appendBefore(anchor);
		else
			ctx.statement().appendBefore(anchor);
		
		return null;
	}
	
	@Override public Void visitLocalDeclaration(ShadowParser.LocalDeclarationContext ctx)
	{ 
		visitChildren(ctx);
		
		if( ctx.localMethodDeclaration() != null )
			ctx.localMethodDeclaration().appendBefore(anchor);
		else
			ctx.localVariableDeclaration().appendBefore(anchor);
		
		return null;		
	}
	
	@Override public Void visitLocalMethodDeclaration(ShadowParser.LocalMethodDeclarationContext ctx)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override public Void visitLocalVariableDeclaration(ShadowParser.LocalVariableDeclarationContext ctx)
	{
		visitChildren(ctx);
		
		for( ShadowParser.VariableDeclaratorContext child : ctx.variableDeclarator() )
			child.appendBefore(anchor);
		
		return null;
	}
	
	@Override public Void visitStatementExpression(ShadowParser.StatementExpressionContext ctx)
	{ 
		visitChildren(ctx);
	
		if( ctx.sequenceAssignment() != null )
			ctx.sequenceAssignment().appendBefore(anchor);
		else
			ctx.expression().appendBefore(anchor);
		
		return null;
	}
	
	//private void doAssignment(TACOperand left, TACOperand right, char operation, OperationNode node ) {
	@Override public Void visitSequenceAssignment(ShadowParser.SequenceAssignmentContext ctx)
	{ 
		visitChildren(ctx);
	
		ShadowParser.RightSideContext rightSide = ctx.rightSide();
		ShadowParser.SequenceLeftSideContext leftSide = ctx.sequenceLeftSide();
		
		//add computation of all left values
		leftSide.appendBefore(anchor);
		//add computation of all right values
		rightSide.appendBefore(anchor);		
		
		List<TACOperand> left = new ArrayList<TACOperand>();
		for( ParseTree child : leftSide.children)
			if( child instanceof Context )
				left.add(((Context) child).getOperand());	
		
		SequenceType sequence = (SequenceType)leftSide.getType();								
		//create splat
		if( !(rightSide.getType() instanceof SequenceType)  ) {				
			TACOperand right = rightSide.getOperand();
			TACVariable temporary = method.addTempLocal(rightSide);	
			new TACLocalStore(anchor, temporary, right);

			int index = 0;
			for( int i = 0; i < sequence.size(); ++i )
				if( sequence.get(i) != null ) {									
					doAssignment(left.get(index), (Context)sequence.get(i), new TACLocalLoad(anchor, temporary), "=", null);
					index++;
				}				
		}
		else if( rightSide.sequenceRightSide() != null ){ //sequence on the right			
			
			//store all right hand side things into temporary variables (for garbage collection purposes)
			ArrayList<TACVariable> variables = new ArrayList<TACVariable>();
			for( int i = 0; i < sequence.size(); ++i )
				if( sequence.get(i) != null ) {
					TACOperand rightOperand = rightSide.sequenceRightSide().conditionalExpression(i).getOperand();
					TACVariable variable = method.addTempLocal(rightOperand);
					variables.add(variable);					
					new TACLocalStore(anchor, variable, rightOperand);
				}
			
			//then store all temporary variables into the left
			int index = 0;
			for( int i = 0; i < sequence.size(); ++i ) //sequence size must match right hand size
				if( sequence.get(i) != null ) {										
					doAssignment(left.get(index), (Context)sequence.get(i),
							new TACLocalLoad(anchor, variables.get(index)), "=", null);
					index++;
				}
		}
		else { //method call on the right whose output must be broken into parts			
			int index = 0;
			for( int i = 0; i < sequence.size(); ++i )
				if( sequence.get(i) != null ) {								
					doAssignment(left.get(index), (Context)sequence.get(i), new TACSequenceElement(anchor, rightSide.getOperand(), i), "=", null);
					index++;
				}		
		}
		
		return null;
	}
	
	@Override public Void visitSwitchStatement(ShadowParser.SwitchStatementContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand value = ctx.conditionalExpression().appendBefore(anchor);
		Type type = value.getType();
		TACLabel defaultLabel = null;
		TACLabel doneLabel = new TACLabel(method);
		if( ctx.hasDefault )
			defaultLabel = new TACLabel(method);		
		List<TACLabel> labels = new ArrayList<TACLabel>( ctx.switchLabel().size() );
		
		if( !( value.getType() instanceof EnumType ) ) {	
			//first go through and do the conditions
			for( int i = 0; i < ctx.switchLabel().size(); ++i ) {	
				ShadowParser.SwitchLabelContext label = ctx.switchLabel(i);
									
				if( label.primaryExpression().size() == 0 ) 
					labels.add(defaultLabel);
				else { // not default
					label.appendBefore(anchor); //append (all) label conditions
					TACLabel matchingCase = new TACLabel(method);
					labels.add(matchingCase);
					
					for( int j = 0; j < label.primaryExpression().size(); ++j ) {
						TACOperand operand = label.primaryExpression(j).getOperand();
						TACOperand comparison;
						MethodSignature signature = type.getMatchingMethod("equal", new SequenceType(operand));
						
						if( type.isPrimitive() && signature.getModifiers().isNative() && signature.getModifiers().isExtern() )
							comparison = new TACBinary(anchor, value, operand); //equivalent to ===
						else								
							comparison = new TACCall(anchor, new TACMethodRef(anchor, value, signature), value, operand);
													
						boolean moreConditions = false;
						if( j < label.primaryExpression().size() - 1 ) //more conditions in this label
							moreConditions = true;
						else if( i < ctx.switchLabel().size() - 2) //at least two more labels (of which only one can be default)
							moreConditions = true;
						else if( i < ctx.switchLabel().size() - 1 && ctx.switchLabel(i + 1).primaryExpression().size() > 0 ) //one more label which isn't default
							moreConditions = true;
						else
							moreConditions = false;							
						
						TACLabel next;
						
						if( moreConditions )
							next = new TACLabel(method);
						else if( ctx.hasDefault )
							next = defaultLabel;
						else
							next = doneLabel;
						
						new TACBranch(anchor, comparison, matchingCase, next);
						
						if( moreConditions )
							next.insertBefore(anchor);							
					}
				}
			}
			
			if( ctx.hasDefault && ctx.switchLabel().size() == 1 ) //only default exists, needs a direct jump
				new TACBranch(anchor, defaultLabel);
			
			//then go through and add the executable blocks of code to jump to
			for( int i = 0; i < ctx.statement().size(); ++i ) {				
				labels.get(i).insertBefore(anchor); //mark start of code				
				ctx.statement(i).appendBefore(anchor); //add block of code (the child after each label)
				TACBranch branch = new TACBranch(anchor, doneLabel);
				branch.setContext(null); //prevents dead code removal error
			}
			
			doneLabel.insertBefore(anchor);			
		}
		else
			throw new UnsupportedOperationException();	
		
		return null;
	}
	
	@Override public Void visitSwitchLabel(ShadowParser.SwitchLabelContext ctx)	
	{ 
		visitChildren(ctx);
		
		for(ShadowParser.PrimaryExpressionContext child : ctx.primaryExpression() )
			child.appendBefore(anchor);
		
		return null;
	}
	
	@Override public Void visitIfStatement(ShadowParser.IfStatementContext ctx)
	{ 
		visitChildren(ctx);
		
		TACLabel trueLabel = new TACLabel(method),
				falseLabel = new TACLabel(method),
				endLabel = ctx.statement().size() > 1 ? new TACLabel(method) : falseLabel;
		
		new TACBranch(anchor, ctx.conditionalExpression().appendBefore(anchor), trueLabel, falseLabel);
		trueLabel.insertBefore(anchor);
		ctx.statement(0).appendBefore(anchor);
		TACBranch branch = new TACBranch(anchor, endLabel);
		branch.setContext(null); //won't cause a dead code problem if removed
		
		if( ctx.statement().size() > 1 ) { //else case
			falseLabel.insertBefore(anchor);
			ctx.statement(1).appendBefore(anchor);
		
			branch = new TACBranch(anchor, endLabel);
			branch.setContext(null); //won't cause a dead code problem if removed
		}
		
		endLabel.insertBefore(anchor);		
		
		return null;
	}

	@Override public Void visitWhileStatement(ShadowParser.WhileStatementContext ctx)
	{
		block = new TACBlock(anchor, block).addBreak().addContinue();
		visitChildren(ctx);
		
		TACLabel bodyLabel = new TACLabel(method),
				conditionLabel = block.getContinue(),
				endLabel = block.getBreak();
		new TACBranch(anchor, conditionLabel);
		
		//body
		bodyLabel.insertBefore(anchor);
		ctx.statement().appendBefore(anchor);
		new TACBranch(anchor, conditionLabel).setContext(null); //prevents dead code removal error
		
		//condition
		conditionLabel.insertBefore(anchor);
		TACOperand condition = ctx.conditionalExpression().appendBefore(anchor);		
		new TACBranch(anchor, condition, bodyLabel, endLabel);
		
		endLabel.insertBefore(anchor);		
		block = block.getParent();	
		
		return null;
	}
	
	@Override public Void visitDoStatement(ShadowParser.DoStatementContext ctx)
	{ 
		block = new TACBlock(anchor, block).addBreak().addContinue();
		visitChildren(ctx);
		
		TACLabel bodyLabel = new TACLabel(method),
				conditionLabel = block.getContinue(),
				endLabel = block.getBreak();
		//only difference from while is this jump to body instead of condition
		new TACBranch(anchor, bodyLabel); 
		
		//body
		bodyLabel.insertBefore(anchor);
		ctx.statement().appendBefore(anchor);
		new TACBranch(anchor, conditionLabel);
		
		//condition
		conditionLabel.insertBefore(anchor);
		TACOperand condition = ctx.conditionalExpression().appendBefore(anchor);		
		TACBranch branch = new TACBranch(anchor, condition, bodyLabel, endLabel);
		branch.setContext(null); //avoids dead code removal error 
		
		endLabel.insertBefore(anchor);
		block = block.getParent();	
		
		return null;
	}
	
	@Override public Void visitForeachStatement(ShadowParser.ForeachStatementContext ctx)
	{ 
		block = new TACBlock(anchor, block).addBreak().addContinue();
		method.enterScope(); //needed because a variable is declared in a foreach
		visitChildren(ctx);
		
		ShadowParser.ForeachInitContext init = ctx.foreachInit();		
		Type type = init.conditionalExpression().getType(); 		
		TACOperand collection = init.appendBefore(anchor);
		TACVariable variable = method.getLocal(init.Identifier().getText());			
		TACVariable iterator;
		TACOperand condition;
		TACOperand value;
		TACLabel bodyLabel = new TACLabel(method),				
				endLabel = block.getBreak();
		
		//optimization for arrays
		if( type instanceof ArrayType ) {	
			ArrayType arrayType = (ArrayType) type;
			TACLabel updateLabel = block.getContinue(),
						conditionLabel = new TACLabel(method);

			//make iterator (int index)
			iterator = method.addTempLocal(new SimpleModifiedType(Type.INT));
			new TACLocalStore(anchor, iterator, new TACLiteral(anchor, new ShadowInteger(0)));
			TACOperand length = new TACLength(anchor, collection, 0);			
			for (int i = 1; i < arrayType.getDimensions(); i++)
				length = new TACBinary(anchor, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), "*", new TACLength(anchor, collection, i), false);			
			
			new TACBranch(anchor, conditionLabel);  //init is done, jump to condition
			
			//body
			bodyLabel.insertBefore(anchor);
			
			//store array element into local variable before executing body
			value = new TACLoad(anchor, new TACArrayRef(anchor, collection, new TACLocalLoad(anchor, iterator), false));
			new TACLocalStore(anchor, variable, value);
			
			ctx.statement().appendBefore(anchor); //body
			
			TACBranch branch = new TACBranch(anchor, updateLabel);
			branch.setContext(null); //avoid dead code removal error
			updateLabel.insertBefore(anchor);			
			
			//increment iterator
			value = new TACLocalLoad(anchor, iterator);
			value.setContext(null); //avoid dead code removal error
			TACLiteral one = new TACLiteral(anchor, new ShadowInteger(1));
			one.setContext(null);
			value = new TACBinary(anchor, value, Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+", one, false );
			value.setContext(null);
			new TACLocalStore(anchor, iterator, value).setContext(null);			
			
			new TACBranch(anchor, conditionLabel).setContext(null);
			conditionLabel.insertBefore(anchor);
			
			//check if iterator < array length
			value = new TACLocalLoad(anchor, iterator);			
			condition = new TACBinary(anchor, value, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), "<", length, true );
			
			new TACBranch(anchor, condition, bodyLabel, endLabel);		
			endLabel.insertBefore(anchor);
		}
		else {	
			TACLabel conditionLabel = block.getContinue();
			MethodSignature signature;
			
			//get iterator
			signature = type.getMatchingMethod("iterator", new SequenceType());
			ModifiedType iteratorType = signature.getReturnTypes().get(0);
			iterator = method.addTempLocal(iteratorType);
			TACMethodRef getIterator = new TACMethodRef(anchor, collection, signature);
			new TACLocalStore(anchor, iterator, new TACCall(anchor, getIterator, getIterator.getPrefix()));
			
			new TACBranch(anchor, conditionLabel);  //init is done, jump to condition
						
			bodyLabel.insertBefore(anchor);		
			
			//put variable update before main body
			signature = iteratorType.getType().getMatchingMethod("next", new SequenceType());
			TACMethodRef next = new TACMethodRef(anchor, new TACLocalLoad(anchor, iterator), signature);
			new TACLocalStore(anchor, variable, new TACCall(anchor, next, new TACLocalLoad(anchor, iterator))); //internally updates iterator
			
			ctx.statement().appendBefore(anchor); //body
						
			new TACBranch(anchor, conditionLabel);
			conditionLabel.insertBefore(anchor);
			
			//check if iterator has next
			signature = iteratorType.getType().getMatchingMethod("hasNext", new SequenceType());
			TACMethodRef hasNext = new TACMethodRef(anchor, new TACLocalLoad(anchor, iterator), signature);
			condition = new TACCall(anchor, hasNext, new TACLocalLoad(anchor, iterator));
			
			new TACBranch(anchor, condition, bodyLabel, endLabel);		
			endLabel.insertBefore(anchor);
		}			

		method.exitScope();
		block = block.getParent();	
		
		return null;		
	}
	
	@Override public Void visitForStatement(ShadowParser.ForStatementContext ctx)
	{ 
		block = new TACBlock(anchor, block).addBreak().addContinue();
		method.enterScope(); //needed because a variable can be declared in a for
		visitChildren(ctx);		
		
		if( ctx.forInit() != null )
			ctx.forInit().appendBefore(anchor);
		
		TACLabel bodyLabel = new TACLabel(method),
				 updateLabel = block.getContinue(),
				 conditionLabel = (ctx.forUpdate() != null) ?
							new TACLabel(method) : updateLabel,				 
				 endLabel = block.getBreak();						
		//branch to condition
		new TACBranch(anchor, conditionLabel);
		
		//body
		bodyLabel.insertBefore(anchor);
		ctx.statement().appendBefore(anchor);
		
		//update (if exists)
		if( ctx.forUpdate() != null ) {
			new TACBranch(anchor, updateLabel).setContext(null); //prevents dead code removal error
			updateLabel.insertBefore(anchor);
			ctx.forUpdate().appendBefore(anchor);
		}
		
		//condition
		new TACBranch(anchor, conditionLabel).setContext(null); //prevents dead code removal error
		conditionLabel.insertBefore(anchor);
		new TACBranch(anchor, ctx.conditionalExpression().appendBefore(anchor), bodyLabel, endLabel);
		
		endLabel.insertBefore(anchor);		
		method.exitScope();
		block = block.getParent();	
		
		return null;		
	}
	
	@Override public Void visitForInit(ShadowParser.ForInitContext ctx)
	{ 
		visitChildren(ctx);
		
		if( ctx.localVariableDeclaration() != null )
			ctx.localVariableDeclaration().appendBefore(anchor);
		else
			ctx.statementExpressionList().appendBefore(anchor);
		
		return null;
	}
	
	@Override public Void visitStatementExpressionList(ShadowParser.StatementExpressionListContext ctx)
	{ 
		visitChildren(ctx);
		
		for( ShadowParser.StatementExpressionContext child : ctx.statementExpression() )
			child.appendBefore(anchor);
		
		return null;	
	}
	
	@Override public Void visitForUpdate(ShadowParser.ForUpdateContext ctx)
	{ 
		visitChildren(ctx);
		ctx.statementExpressionList().appendBefore(anchor);
		
		return null;	
	}
	
	@Override public Void visitBreakOrContinueStatement(ShadowParser.BreakOrContinueStatementContext ctx)
	{ 
		//no children
		//An unreachable label is needed in case a programmer writes code after the break or continue.
		//Such code is unreachable, but a branch is a terminator instruction.
		//Thus, the result is badly formed LLVM if a block of instructions follows a terminator
		//without a label first.		
		//Unreachable code will be detected and removed in TAC analysis.
		TACLabel unreachableLabel = new TACLabel(method);
		TACBlock exitBlock;
		
		if( ctx.getChild(0).getText().equals("break") ) {
			exitBlock = block.getBreakBlock();
			visitCleanup(exitBlock, null, exitBlock.getBreak());
		}
		else {
			exitBlock = block.getContinueBlock();
			visitCleanup(exitBlock, null, exitBlock.getContinue());
		}
		
		unreachableLabel.insertBefore(anchor);		
		
		return null;	
	}
	
	@Override public Void visitReturnStatement(ShadowParser.ReturnStatementContext ctx)
	{ 
		visitChildren(ctx);	
		
		TACOperand rightSide = null;
		if( ctx.rightSide() != null )
			rightSide = ctx.rightSide().appendBefore(anchor);
		
		TACLabel unreachableLabel = new TACLabel(method);		
		
		//turn context off to avoid dead code removal errors
		Context context = anchor.getContext();
		anchor.setContext(null);
		
		//store the return value before the cleanup
		if( !method.getSignature().isCreate() ) {			
			if( rightSide != null ) {			
				Type type = rightSide.getType();
				
				if( type instanceof SequenceType ) {
					TACSequence sequence = (TACSequence)rightSide;
					SequenceType sequenceType = (SequenceType) type;
					for( int i = 0; i < sequenceType.size(); ++i )
						new TACLocalStore(anchor, method.getLocal("_return" + i), sequence.get(i));
				}
				else
					new TACLocalStore(anchor, method.getLocal("return"), rightSide);
			}
		}
		
		//do the cleanup, de-referencing variables
		visitCleanup(null, null);
		
		//turn context back on
		anchor.setContext(context);
		
		if( method.getSignature().isCreate() )				
			new TACReturn(anchor, method.getSignature().getFullReturnTypes(),
					new TACLocalLoad(anchor, method.getLocal("this")));		
		else {			
			if( rightSide != null ) {
				Type type = rightSide.getType();
						
				if( type instanceof SequenceType ) {
					SequenceType sequenceType = (SequenceType) type;
					List<TACOperand> operands = new ArrayList<TACOperand>(sequenceType.size());
					for( int i = 0; i < sequenceType.size(); ++i )
						operands.add(new TACLocalLoad(anchor, method.getLocal("_return" + i)));
					new TACReturn(anchor, (SequenceType)ctx.getType(), new TACSequence(anchor, operands));
				}
				else			
					new TACReturn(anchor, (SequenceType)ctx.getType(), new TACLocalLoad(anchor, method.getLocal("return")));
			}
			else
				new TACReturn(anchor, new SequenceType());			
		}
		
		unreachableLabel.insertBefore(anchor);		
		
		return null;
	}
	
	@Override public Void visitThrowStatement(ShadowParser.ThrowStatementContext ctx)
	{ 
		visitChildren(ctx);
		TACLabel unreachableLabel = new TACLabel(method);
		new TACThrow(anchor, ctx.conditionalExpression().appendBefore(anchor));
		unreachableLabel.insertBefore(anchor);		
		
		return null;
	}
	
	@Override public Void visitFinallyStatement(ShadowParser.FinallyStatementContext ctx)
	{ 
		block = new TACBlock(anchor, block).addDone();
		visitChildren(ctx);
		
		ctx.recoverStatement().appendBefore(anchor);
		if( ctx.block() != null ) {
			TACPhi phi = (TACPhi)anchor.getPrevious(); //last thing before the anchor is a phi
			ctx.block().appendBefore(anchor);
			new TACBranch(anchor, phi);				
		}
		block.getDone().insertBefore(anchor);		
		block = block.getParent();	
		
		return null;	
	}
	
	@Override public Void visitRecoverStatement(ShadowParser.RecoverStatementContext ctx)
	{
		ShadowParser.FinallyStatementContext parent = (FinallyStatementContext) ctx.getParent();
		if( parent.block() != null ) //parent has finally
			block = new TACBlock(anchor, block).addLandingpad().addUnwind().
			addCleanup().addDone();
		
		visitChildren(ctx);
		
		ctx.catchStatements().appendBefore(anchor);
		if( ctx.block() != null ) { //has recover
			ctx.block().appendBefore(anchor);
			new TACBranch(anchor, block.getDone()).setContext(null); //prevents dead code removal error
		}
		if ( parent.block() != null ) { //parent has finally
			block.getDone().insertBefore(anchor);
			method.setHasLandingpad();
			
			//turn off context to prevent dead code removal errors
			Context context = anchor.getContext();
			anchor.setContext(null);			
			
			visitCleanup( block.getParent(), block.getDone(),
					block.getParent().getDone() );
			block.getLandingpad().insertBefore(anchor);
			TACVariable exception = method.getLocal("_exception");
			new TACLocalStore(anchor, exception, new TACLandingpad(anchor, block));
			new TACBranch(anchor, block.getUnwind());
			block.getUnwind().insertBefore(anchor);
			TACLabel continueUnwind = block.getParent().getUnwind();
			if (continueUnwind != null)
				visitCleanup(block, block.getUnwind(), continueUnwind);
			else {
				visitCleanup(block, block.getUnwind());								
				new TACResume(anchor, new TACLocalLoad(anchor, exception));
			}
			//turn context back on
			anchor.setContext(context);
			
			block.getCleanup().insertBefore(anchor);
			block.getCleanupPhi().insertBefore(anchor);
			block = block.getParent();				
		}
		
		return null;
	}

	
	@Override public Void visitCatchStatements(ShadowParser.CatchStatementsContext ctx)
	{ 
		ShadowParser.RecoverStatementContext parent = (RecoverStatementContext) ctx.getParent();
		
		block = new TACBlock(anchor, block);
		if( parent.block() != null ) //parent has recover
			block.addRecover();
		
		block.addCatches(ctx.catchStatement().size());
		
		visitChildren(ctx);
		
		ctx.tryStatement().appendBefore(anchor); //appends try block
		
		if( ctx.catchStatement().size() > 0 ) {
			//ignores context for a while, preventing dead code removal errors
			//they'll be caught inside the catch
			//catching them here creates misleading error messages
			Context context = anchor.getContext();
			anchor.setContext(null);
			
			TACOperand typeid = new TACSequenceElement(anchor, new TACLocalLoad(anchor, method.getLocal("_exception")), 1 );
			for( int i = 0; i < ctx.catchStatement().size(); ++i ) {
				ShadowParser.CatchStatementContext child = ctx.catchStatement(i);
				Type type = child.formalParameter().getType();
				TACLabel catchLabel = block.getCatch(i),
						skip = new TACLabel(method);
				
				new TACBranch(anchor, new TACBinary(anchor, typeid, new TACTypeId(anchor, new TACClass(anchor, type).getClassData())),
						catchLabel, skip);
						
				catchLabel.insertBefore(anchor);
				child.appendBefore(anchor); //append catch i
				new TACBranch(anchor, block.getDone()); //prevents dead code removal error
				skip.insertBefore(anchor);
			}
			
			TACLabel continueUnwind = block.getUnwind();
			if (continueUnwind != null)
				new TACBranch(anchor, continueUnwind); //try inside of try					
			else
				new TACResume(anchor, new TACLocalLoad(anchor, method.getLocal("_exception")));
			
			anchor.setContext(context);
		}			

		if( parent.block() != null ) //parent has recover			
			block.getRecover().insertBefore(anchor);
		
		block = block.getParent();	
		
		return null;
	}
	
	@Override public Void visitCatchStatement(ShadowParser.CatchStatementContext ctx)	
	{ 
		method.enterScope();
		visitChildren(ctx); 
		
		ShadowParser.FormalParameterContext parameter = ctx.formalParameter();
		
		parameter.appendBefore(anchor);
							
		new TACLocalStore(anchor, method.getLocal(
				parameter.Identifier().getText()), new TACCatch(anchor,
				(ExceptionType)parameter.getType(), new TACLocalLoad(anchor, method.getLocal("_exception"))));
		ctx.block().appendBefore(anchor);
		method.exitScope();
		
		return null;
	}
	
	@Override public Void visitTryStatement(ShadowParser.TryStatementContext ctx)
	{ 
		ShadowParser.CatchStatementsContext parent = (CatchStatementsContext) ctx.getParent();
		if( parent.catchStatement().size() > 0 )
			block = new TACBlock(anchor, block).addLandingpad().addUnwind();
		
		visitChildren(ctx); 
		
		ctx.block().appendBefore(anchor);
		
		//turn context off to avoid dead code removal errors
		Context context = anchor.getContext();
		anchor.setContext(null);
		
		new TACBranch(anchor, block.getDone());		
		if( parent.catchStatement().size() > 0 ) {
			method.setHasLandingpad();
			block.getLandingpad().insertBefore(anchor);
			TACVariable exception = method.getLocal("_exception");			
			new TACLocalStore(anchor, exception, new TACLandingpad(anchor, block));
			new TACBranch(anchor, block.getUnwind());
			block.getUnwind().insertBefore(anchor);
			block = block.getParent();				
		}
		
		//turn context back on
		anchor.setContext(context);
		
		return null;
	}	

	private void visitCleanup(TACBlock lastBlock, TACLabel currentLabel) {
		if (lastBlock != null)
			lastBlock = lastBlock.getParent();
		TACBlock currentBlock = block.getCleanupBlock(lastBlock);
		if (currentBlock != lastBlock) {
			TACLabel lastLabel = new TACLabel(method);
			visitCleanup(currentBlock, lastBlock, currentLabel, lastLabel);
			lastLabel.insertBefore(anchor);
		}
	}
	private void visitCleanup(TACBlock lastBlock, TACLabel currentLabel,
			TACLabel lastLabel) {
		if (lastBlock != null)
			lastBlock = lastBlock.getParent();
		TACBlock currentBlock = block.getCleanupBlock(lastBlock);
		if (currentBlock != lastBlock)
			visitCleanup(currentBlock, lastBlock, currentLabel, lastLabel);
		else
			new TACBranch(anchor, lastLabel);
	}
	
	private void visitCleanup(TACBlock currentBlock, TACBlock lastBlock,
			TACLabel currentLabel, TACLabel lastLabel) {
		if (currentLabel == null) {
			currentLabel = new TACLabel(method);
			new TACBranch(anchor, currentLabel);
			currentLabel.insertBefore(anchor);
		}
		TACBlock nextBlock;
		while ((nextBlock = currentBlock.getNextCleanupBlock(lastBlock)) !=
				lastBlock) {
			TACLabel nextLabel = new TACLabel(method);
			new TACBranch(anchor, currentBlock.getCleanup());			
			currentBlock.getCleanupPhi().addPreviousStore(currentLabel, new TACLabelAddress(anchor, nextLabel, method) );
			nextLabel.insertBefore(anchor);
			currentBlock = nextBlock;
			currentLabel = nextLabel;
		}
		new TACBranch(anchor, currentBlock.getCleanup());
		currentBlock.getCleanupPhi().addPreviousStore(currentLabel, new TACLabelAddress(anchor, lastLabel, method) );
	}

	private void visitConstant(TACConstant constantRef, ShadowParser.VariableDeclaratorContext constantNode)
	{			
		visit(constantNode.conditionalExpression());
		//normal nodes have the last node in the list, but constants need the first
		//the node after the last is the first
		constantRef.setNode(constantNode.conditionalExpression().getList().getNext());		
		moduleStack.peek().addConstant(constantRef);		
	}
	
	private TACOperand copyArray(TACOperand array, TACOperand map) { //should work even for null arrays
		Type type = array.getType();
		int layers = 0;
		
		while( type instanceof ArrayType ) {
			ArrayType arrayType = (ArrayType) type;		
			type = arrayType.getBaseType();
			layers++;
		}
				
		Type baseType = type;
		TACMethodRef copyMethod;
		
		if( type.isPrimitive() )
			copyMethod = null;
		else if( type instanceof InterfaceType )
			copyMethod = new TACMethodRef(anchor, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
		else
			copyMethod = new TACMethodRef(anchor, baseType.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)) );
	
		TACVariable[] counters = new TACVariable[layers];			
		TACLabel[] labels = new TACLabel[layers];			
		TACLabel done = new TACLabel(method);
		TACOperand returnValue = null;
		
		type = array.getType();		
		TACOperand oldArray = array;
		TACVariable previousArray = null;
		
		for( int i = 0; i < layers; i++ ) {
			ArrayType arrayType = (ArrayType)type;
			type = arrayType.getBaseType();
			TACOperand length = new TACLength(anchor, oldArray, 0);
			TACOperand[] dimensions = new TACOperand[arrayType.getDimensions()];
			dimensions[0] = length;
			for (int j = 1; j < arrayType.getDimensions(); j++) {
				dimensions[j] = new TACLength(anchor, oldArray, j);
				length = new TACBinary(anchor, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), "*", dimensions[j]);
			}
			
			TACClass class_ = new TACClass(anchor, type);
			TACVariable copiedArray = method.addTempLocal(new SimpleModifiedType(arrayType));
			new TACLocalStore(anchor, copiedArray, new TACNewArray(anchor, arrayType, class_.getClassData(), dimensions));
			
			if( i == 0 )
				returnValue = new TACLocalLoad(anchor, copiedArray);
			else {
				//store current array into its spot in the previous array
				TACArrayRef location = new TACArrayRef(anchor, new TACLocalLoad(anchor, previousArray), new TACLocalLoad(anchor, counters[i - 1]), false);
				new TACStore(anchor, location, new TACLocalLoad(anchor, copiedArray));
			}
			
			previousArray = copiedArray;
			
			if( i == layers - 1 ) { //last layer is either objects or primitives, not more arrays
				TACLabel terminate;					
				if( i == 0 )
					terminate = done;
				else
					terminate = labels[i - 1];
				
				if( type.isPrimitive() ) {
					TACMethodRef width = new TACMethodRef(anchor, Type.CLASS.getMatchingMethod("width", new SequenceType()) );
					TACOperand size = new TACBinary(anchor, new TACCall(anchor, width, class_.getClassData()), Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), "*", length);
					new TACCopyMemory(anchor, new TACLocalLoad(anchor, copiedArray), oldArray, size);					
					new TACBranch(anchor, terminate);		
				}
				else {					
					counters[i] = method.addTempLocal(new SimpleModifiedType(Type.INT));
					new TACLocalStore(anchor, counters[i], new TACLiteral(anchor, new ShadowInteger(-1))); //starting at -1 allows update and check to happen on the same label
					labels[i] = new TACLabel(method);
					
					new TACBranch(anchor, labels[i]);
					labels[i].insertBefore(anchor);					
					TACLabel body = new TACLabel(method);					

					//increment counters[i] by 1
					new TACLocalStore(anchor, counters[i], new TACBinary(anchor, new TACLocalLoad(anchor, counters[i]), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+", new TACLiteral(anchor, new ShadowInteger(1))));
					TACOperand condition = new TACBinary(anchor, new TACLocalLoad(anchor, counters[i]), Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), "<", length, true);
					new TACBranch(anchor, condition, body, terminate);					
					body.insertBefore(anchor);
					
					//copy old value into new location
					TACOperand element = new TACLoad(anchor, new TACArrayRef(anchor, oldArray, new TACLocalLoad(anchor, counters[i]), false)); //get value at location
					
					if( baseType instanceof InterfaceType )
						element = TACCast.cast(anchor, new SimpleModifiedType(Type.OBJECT), element);
										
					TACLabel copyLabel = new TACLabel(method);
					TACOperand nullCondition = new TACBinary(anchor, element, new TACLiteral(anchor, new ShadowNull(element.getType())));
					new TACBranch(anchor, nullCondition, labels[i], copyLabel); //if null, skip entirely, since arrays are calloc'ed
					
					copyLabel.insertBefore(anchor);
					
					TACOperand copiedElement = new TACCall(anchor, copyMethod, element, map);
					
					if( baseType instanceof InterfaceType )
						copiedElement = TACCast.cast(anchor, new SimpleModifiedType(baseType), copiedElement);
					
					TACArrayRef newElement = new TACArrayRef(anchor, new TACLocalLoad(anchor, copiedArray), new TACLocalLoad(anchor, counters[i]), false);
					new TACStore(anchor, newElement, copiedElement);

					//go back to update and check condition
					new TACBranch(anchor, labels[i]);					
				}
			}
			else {
				counters[i] = method.addTempLocal(new SimpleModifiedType(Type.INT));
				new TACLocalStore(anchor, counters[i], new TACLiteral(anchor, new ShadowInteger(-1)));//starting at -1 allows update and check to happen on the same label
				labels[i] = new TACLabel(method);
				
				new TACBranch(anchor, labels[i]);
				labels[i].insertBefore(anchor);	
				
				TACLabel terminate;
				TACLabel body = new TACLabel(method);
				if( i == 0 )
					terminate = done;
				else
					terminate = labels[i - 1];

				//increment counters[i] by 1
				new TACLocalStore(anchor, counters[i], new TACBinary(anchor, new TACLocalLoad(anchor, counters[i]), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+", new TACLiteral(anchor, new ShadowInteger(1))));
				TACOperand condition = new TACBinary(anchor, new TACLocalLoad(anchor, counters[i]), Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), "<", length, true);
				new TACBranch(anchor, condition, body, terminate);					
				body.insertBefore(anchor);
				
				//set up next round
				oldArray = new TACLoad(anchor, new TACArrayRef(anchor, oldArray, new TACLocalLoad(anchor, counters[i]), false)); //get value at location
			}
		}
		
		done.insertBefore(anchor);		
		return returnValue;
	}
	
	
	private void setupGCMethod() {
		//begin synthetic finally for gc handling
		
		method.setGarbageCollected(true);		
		block = new TACBlock(method).addDone();
		//block = new TACBlock(method); //no done needed, since nothing should come after?
		anchor = new TACDummyNode(null, block);		
		
		
		//synthetic recoverStatement
		block = new TACBlock(anchor, block).addLandingpad().addUnwind().
				addCleanup().addDone();
		
		//synthetic catchStatements
		block = new TACBlock(anchor, block);	
	}
	
	private void setupNonGCMethod() {
		method.setGarbageCollected(false);
		block = new TACBlock(method);
		anchor = new TACDummyNode(null, block);
	}
	
	private void cleanupGCMethod() {		
		//turn off context to prevent dead code removal errors
		Context context = anchor.getContext();
		anchor.setContext(null);
		
		
		//synthetic try statement
		new TACBranch(anchor, block.getDone()).setContext(null);
		
		//synthetic catchStatements
		block = block.getParent();	
					
		//synthetic recoverStatement			
		block.getDone().insertBefore(anchor);
		//new TACBranch(anchor, block.getCleanup());
		method.setHasLandingpad();					
		
		//is this necessary, since every GC method will have a finally?
		visitCleanup( block.getParent(), block.getDone(),
				block.getParent().getDone() );
		block.getLandingpad().insertBefore(anchor);
		TACVariable exception = method.getLocal("_exception");
		new TACLocalStore(anchor, exception, new TACLandingpad(anchor, block));
		new TACBranch(anchor, block.getUnwind());
		block.getUnwind().insertBefore(anchor);
		visitCleanup(block, block.getUnwind());								
		new TACResume(anchor, new TACLocalLoad(anchor, exception));
		
		block.getCleanup().insertBefore(anchor);
		block.getCleanupPhi().insertBefore(anchor);
		block = block.getParent();	
		
		//synthetic finally
		TACPhi phi = (TACPhi)anchor.getPrevious(); //last thing before the anchor is a phi
		
		//all the decrements go before this indirect branch		
		new TACBranch(anchor, phi);				
	
		block.getDone().insertBefore(anchor); //catches things that should have a return but don't		
		//block = block.getParent();	
				
		//turn context back on
		anchor.setContext(context);
	}
	
	private void cleanupNonGCMethod() {
		block = block.getParent();
	}
	
	

	private void visitMethod(MethodSignature methodSignature) {
		TACNode saveTree = anchor;
		TACMethod method = this.method = new TACMethod(methodSignature);
		boolean implicitCreate = false;
		if( moduleStack.peek().isClass() && !methodSignature.isNative() && !methodSignature.isExtern() ) {				
			
			if( methodSignature.getSymbol().equals("copy") && !methodSignature.isWrapper() ) {
				setupNonGCMethod();				
				ClassType type = (ClassType) methodSignature.getOuter();
				method.addParameters(anchor); //address map called "addresses"				
				
				if( type.getModifiers().isImmutable() ) {				
					//for now, just return this
					//after incrementing reference count
					if( !type.isPrimitive() )
						new TACChangeReferenceCount(anchor, method.getThis(), true);
					new TACReturn(anchor, methodSignature.getFullReturnTypes(), new TACLocalLoad(
							anchor, method.getThis()));
				}			
				else {					
					TACOperand this_ = new TACLocalLoad(anchor, method.getThis());
					TACOperand address = new TACPointerToLong(anchor, this_);					
					
					TACOperand map = new TACLocalLoad(anchor, method.getParameter("addresses"));
					TACMethodRef indexMethod = new TACMethodRef(anchor, Type.ADDRESS_MAP.getMatchingMethod("containsKey", new SequenceType(Type.ULONG)) );
					TACOperand test = new TACCall(anchor, indexMethod, map, address );
					
					TACLabel copyLabel = new TACLabel(method),
							returnLabel = new TACLabel(method);
					
					new TACBranch(anchor, test, returnLabel, copyLabel);
					copyLabel.insertBefore(anchor);
					
					//allocate a new object (which by default gets a ref count of 1)
					TACNewObject object = new TACNewObject(anchor, type);
					TACOperand duplicate;					
					
					//add it to the map of addresses
					SequenceType arguments = new SequenceType();
					arguments.add(new SimpleModifiedType(Type.ULONG));  //key
					arguments.add(new SimpleModifiedType(Type.ULONG));  //value
					indexMethod = new TACMethodRef(anchor, Type.ADDRESS_MAP.getMatchingMethod("index", arguments) );
					TACOperand newAddress = new TACPointerToLong(anchor, object);					
					new TACCall(anchor, indexMethod, map, address, newAddress);					
					
					if( type.getTypeWithoutTypeArguments().equals(Type.ARRAY) || type.getTypeWithoutTypeArguments().equals(Type.ARRAY_NULLABLE) ) {
						Type genericArray = type.getTypeWithoutTypeArguments();
						
						//call private create to allocate space
						TACMethodRef create = new TACMethodRef(anchor, genericArray.getMatchingMethod("create", new SequenceType(new SimpleModifiedType( new ArrayType(Type.INT), new Modifiers(Modifiers.IMMUTABLE)))));
						TACOperand lengths = new TACLoad(anchor, new TACFieldRef(this_, "lengths" ));
						duplicate = new TACCall(anchor, create, object, lengths); //performs cast to Array as well
						
						//get size (product of all dimension lengths)
						TACMethodRef sizeMethod = new TACMethodRef(anchor, genericArray.getMatchingMethod("size", new SequenceType()));					
						TACOperand size = new TACCall(anchor, sizeMethod, this_);
						TACLabel done = new TACLabel(method);
						TACLabel body = new TACLabel(method);
						TACLabel condition = new TACLabel(method);
						
						TACVariable i = method.addTempLocal(new SimpleModifiedType(Type.INT));
						new TACLocalStore(anchor, i, new TACLiteral(anchor, new ShadowInteger(0)));
						new TACBranch(anchor, condition);
						
						//start loop
						condition.insertBefore(anchor);
						
						TACOperand loop = new TACBinary(anchor, new TACLocalLoad(anchor, i), Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), "<", size, true );
						new TACBranch(anchor, loop, body, done);
						body.insertBefore(anchor);
						
						SequenceType indexArguments = new SequenceType();
						indexArguments.add(i);					
						TACMethodRef indexLoad = new TACMethodRef(anchor, genericArray.getMatchingMethod("index", indexArguments));
						indexArguments.add(genericArray.getTypeParameters().get(0));
						TACMethodRef indexStore = new TACMethodRef(anchor, genericArray.getMatchingMethod("index", indexArguments));
						
						
						TACOperand value = new TACCall(anchor, indexLoad, this_, new TACLocalLoad(anchor, i));
						
						TACLabel skipLabel = new TACLabel(method);
						TACLabel makeCopyLabel = new TACLabel(method);
						TACOperand isNull = new TACBinary(anchor, value, new TACLiteral(anchor, new ShadowNull(value.getType())));
						new TACBranch(anchor, isNull, skipLabel, makeCopyLabel);
						
						makeCopyLabel.insertBefore(anchor);
						
						TACMethodRef copy = new TACMethodRef(anchor, value, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
						
						value = new TACCall(anchor, copy, copy.getPrefix(), map);
						new TACCall(anchor, indexStore, duplicate, new TACLocalLoad(anchor, i), value);						
						new TACBranch(anchor, skipLabel);
						
						skipLabel.insertBefore(anchor);						
						
						new TACLocalStore(anchor, i, new TACBinary(anchor, new TACLocalLoad(anchor, i), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+", new TACLiteral(anchor, new ShadowInteger(1)), false ));
						new TACBranch(anchor, condition);					
						
						done.insertBefore(anchor);
					}
					else {
						//perform a memcopy to sweep up all the primitives and immutable data (and nulls)
						TACOperand size = new TACLoad(anchor, new TACFieldRef(object.getClassData(), "size"));
						new TACCopyMemory(anchor, object, this_, size);
						
						if( type.equals(Type.OBJECT))
							duplicate = object;
						else
							duplicate = TACCast.cast(anchor, new SimpleModifiedType(type), object); //casts object to type
						
						//copy other fields in using their copy methods
						//arrays need special attention
						TACMethodRef copyMethod;
						TACOperand field;
						TACFieldRef newField;
						TACOperand copiedField;
						for( Entry<String, ? extends ModifiedType> entry : type.orderAllFields() ) {
							ModifiedType entryType = entry.getValue();
							//only copy non-primitive types and non-singletons
							if( !entryType.getType().isPrimitive() &&
								!(entryType.getType() instanceof SingletonType) ) {
								//increment ref count on immutables (which have already been copied through memcpy)
								if( entryType.getModifiers().isImmutable() )
									new TACChangeReferenceCount(anchor, new TACFieldRef(duplicate, entryType, entry.getKey()), true );
								else {							
									//get field references
									field = new TACLoad(anchor, new TACFieldRef(this_, entryType, entry.getKey()));
									newField = new TACFieldRef(duplicate, entryType, entry.getKey());
									
									TACLabel copyField = new TACLabel(method);
									TACLabel skipField = new TACLabel(method);									

									if( entryType.getType() instanceof ArrayType )
										copiedField = copyArray(field, map);								
									else {	
										if( entryType.getType() instanceof InterfaceType ) {
											//cast converts from interface to object
											field = TACCast.cast(anchor, new SimpleModifiedType(Type.OBJECT), field);
											copyMethod = new TACMethodRef(anchor, field, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
										}
										else //normal object
											copyMethod = new TACMethodRef(anchor, field, entryType.getType().getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
										
										TACOperand nullCondition = new TACBinary(anchor, field, new TACLiteral(anchor, new ShadowNull(field.getType())));
										new TACBranch(anchor, nullCondition, skipField, copyField); //if null, skip
	
										copyField.insertBefore(anchor);
										copiedField = new TACCall(anchor, copyMethod, field, map);
	
										if( entryType.getType() instanceof InterfaceType )
											//and then cast back to interface
											copiedField = TACCast.cast(anchor, newField, copiedField);																
									}
									
									//store copied value
									//should increment ref count
									TACStore store = new TACStore(anchor, newField, copiedField);
									//don't decrement old value, since the old field is sitting there because of memcpy
									store.setDecrementReference(false); 
									new TACBranch(anchor, skipField);								
									
									skipField.insertBefore(anchor);
								}
							}
						}
					}					
					
					
					//No GC for a duplicated object, since it's "born" with a ref count of 1					
					new TACReturn(anchor, methodSignature.getFullReturnTypes(), duplicate);
					
					returnLabel.insertBefore(anchor);
					
					indexMethod = new TACMethodRef(anchor, Type.ADDRESS_MAP.getMatchingMethod("index", new SequenceType(Type.ULONG)) );
					TACOperand index = new TACCall(anchor, indexMethod, map, address );
					TACOperand existingObject = new TACLongToPointer(anchor, index, new SimpleModifiedType(type));
					
					//storage is used to add a reference for GC (since this is a non-GC method, it's never decremented)					
					TACVariable variable = method.addTempLocal(duplicate);
					new TACLocalStore(anchor, variable, existingObject );					
					new TACReturn(anchor, methodSignature.getFullReturnTypes(), existingObject);					
				}
				
				cleanupNonGCMethod();
				
			}
			//Gets and sets that were created by default (that's why they have a null parent)
			else if( methodSignature.getNode().getParent() == null && (methodSignature.isGet() || methodSignature.isSet() )) { 			
				setupNonGCMethod();		
				
				method.addParameters(anchor);
				TACFieldRef field = new TACFieldRef(new TACLocalLoad(
						anchor, method.getThis()), methodSignature.getSymbol());
				if (methodSignature.isGet()) {
					TACLoad load = new TACLoad(anchor, field); 
					//add a reference count since whoever is getting this element expects it to go up by one 									
					if( field.needsGarbageCollection() )
						new TACChangeReferenceCount(anchor, field, true);
					new TACReturn(anchor, methodSignature.getFullReturnTypes(),	load);
				}
				else if (methodSignature.isSet()) {
					TACVariable value = null;
					for (TACVariable parameter : method.getParameters())
						value = parameter;
					new TACStore(anchor, field, new TACLocalLoad(anchor, value));
					new TACReturn(anchor, methodSignature.getFullReturnTypes());
				}				
				
				cleanupNonGCMethod();		
			}		
			else if (methodSignature.isWrapper()) {
				setupGCMethod();				
				
				MethodSignature wrapped = methodSignature.getWrapped();
				SequenceType fromTypes = methodSignature.getFullParameterTypes(),
						toTypes = wrapped.getFullParameterTypes();
				Iterator<TACVariable> fromArguments = method.addParameters(anchor, true).
						getParameters().iterator();
				List<TACOperand> toArguments = new ArrayList<TACOperand>(
						toTypes.size());
				for (int i = 0; i < toTypes.size(); i++) {
					TACOperand argument =
							new TACLocalLoad(anchor, fromArguments.next());
					if (!fromTypes.getType(i).isSubtype(toTypes.getType(i)))
						argument = TACCast.cast(anchor, toTypes.get(i), argument);
					toArguments.add(argument);				
				}
				
				TACOperand value = new TACCall(anchor, new TACMethodRef(anchor,
						wrapped), toArguments); 
				
				TACLabel unreachableLabel = new TACLabel(method);		
				
				//turn context off to avoid dead code removal errors
				Context context = anchor.getContext();
				anchor.setContext(null);				
				
				if( methodSignature.getFullReturnTypes().isEmpty() ) {
					visitCleanup(null, null);	
					
					//turn context back on
					anchor.setContext(context);					
					
					new TACReturn(anchor, methodSignature.getFullReturnTypes(), null);
				}
				else {
					fromTypes = wrapped.getFullReturnTypes();
					toTypes = methodSignature.getFullReturnTypes();
					
					//cast all returns and store them in appropriate variables
					if( value.getType() instanceof SequenceType ) {						
						TACSequence sequence = (TACSequence)value;
						SequenceType sequenceType = (SequenceType) value.getType();
						for( int i = 0; i < sequenceType.size(); ++i )
							new TACLocalStore(anchor, method.getLocal("_return" + i), TACCast.cast(anchor,  toTypes.get(i), sequence.get(i)));
					}
					else {
						if( !fromTypes.getType(0).isSubtype(toTypes.getType(0)) )
							value = TACCast.cast(anchor, toTypes.get(0), value);
						new TACLocalStore(anchor, method.getLocal("return"), value);
					}	
					
					visitCleanup(null, null);
					
					//turn context back on
					anchor.setContext(context);
					
					if( value.getType() instanceof SequenceType ) {
						SequenceType sequenceType = (SequenceType) value.getType();
						List<TACOperand> operands = new ArrayList<TACOperand>(sequenceType.size());
						for( int i = 0; i < sequenceType.size(); ++i )
							operands.add(new TACLocalLoad(anchor, method.getLocal("_return" + i)));
						new TACReturn(anchor, toTypes, new TACSequence(anchor, operands));
					}
					else			
						new TACReturn(anchor, toTypes, new TACLocalLoad(anchor, method.getLocal("return")));	
				}
				
				unreachableLabel.insertBefore(anchor);
				
				cleanupGCMethod();
			}
			else { // Regular method, create, or destroy (includes empty creates and destroys)
				setupGCMethod();
				
				method.addParameters(anchor);
				
				if( methodSignature.isCreate()) {
					ShadowParser.CreateDeclarationContext declaration = (ShadowParser.CreateDeclarationContext) methodSignature.getNode();
					implicitCreate = declaration.createBlock() == null || declaration.createBlock().explicitCreateInvocation() == null;
					Type type = methodSignature.getOuter();
					if (type.hasOuter()) {
						TACStore store = new TACStore(anchor,
								new TACFieldRef(new TACLocalLoad(anchor,
										method.getThis()),
										new SimpleModifiedType(type.getOuter()),
										"_outer"),
								new TACLocalLoad(anchor,
										method.getParameter("_outer")));						
					}
				}
				
				// Call parent create if implicit create.
				if( implicitCreate ) {
					ClassType thisType = (ClassType)methodSignature.getOuter(),
							superType = thisType.getExtendType();
					if (superType != null) {
						TACCall call;
						if( superType.hasOuter() )
							call = new TACCall(anchor, new TACMethodRef(anchor,
									superType.getMatchingMethod("create", new SequenceType())), new TACLocalLoad(anchor,
									method.getThis()), new TACLocalLoad(anchor, method.getParameter("_outer")));
						else
							call = new TACCall(anchor, new TACMethodRef(anchor,
									superType.getMatchingMethod("create", new SequenceType())), new TACLocalLoad(anchor,
									method.getThis()));
						
						call.setDelegatedCreate(true);
					}
					
					// Walk fields in *exactly* the order they were declared since
					// some fields depend on prior fields.
					// This is accomplished by using a LinkedHashMap.
					for( ShadowParser.VariableDeclaratorContext field : ((ClassType)(methodSignature.getOuter())).getFields().values() ) 
						if (!field.getModifiers().isConstant() && !(field.getType() instanceof SingletonType)) {
							visit(field);
							field.appendBefore(anchor);
						}
				}				
				
				visit(methodSignature.getNode());
				methodSignature.getNode().appendBefore(anchor);
				
				//fill in all the fields to destroy
				if( methodSignature.isDestroy() ) {					
					TACOperand this_ = new TACLocalLoad(anchor, method.getThis());					
					
					ClassType classType = (ClassType)(methodSignature.getOuter()); 
					for( Entry<String, ? extends ModifiedType> entry : classType.sortFields() ) {
						if( !entry.getKey().equals("_outer") ) { //TODO: deal with outer class reference count decrements						
							TACFieldRef reference = new TACFieldRef(this_, entry.getKey());
							if( reference.needsGarbageCollection() )
								new TACChangeReferenceCount(anchor, reference, false);
						}
					}					
					
					//the mirror image of a create: calls parent destroy *afterwards*
					ClassType thisType = (ClassType)methodSignature.getOuter(),
							superType = thisType.getExtendType();
					if( superType != null )					
						new TACCall(anchor, new TACMethodRef(anchor,
								superType.getMatchingMethod("destroy", new SequenceType())), new TACLocalLoad(anchor,
								method.getThis()));
					
					//add explicit return					
					TACReturn explicitReturn = new TACReturn(anchor, new SequenceType() ); 
					//prevents an error from being recorded if this return is later removed as dead code
					explicitReturn.setContext(null);
					new TACLabel(method).insertBefore(anchor); //unreachable label
				}
				
				cleanupGCMethod();
			}
			
			//end regular block
			//block = block.getParent();
			
			
			anchor = anchor.remove(); //gets node before anchor (and removes dummy)
			method.setNode(anchor.getNext()); //the node after last node is, strangely, the first node			
		}
		moduleStack.peek().addMethod(method);
		block = null;
		this.method = null;
		anchor = saveTree;
	}	
	
	private void visitBinaryOperation(Context node, List<? extends Context> list) {
		TACOperand current = list.get(0).appendBefore(anchor);		
		
		for( int i = 1; i < list.size(); i++ ) {
			String op = node.getChild(2*i - 1).getText();  //the operations are every other child
			TACOperand next = list.get(i).appendBefore(anchor);
			MethodSignature signature = node.getOperations().get(i - 1);
			boolean isCompare = ( op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">=") );
			Type currentType = resolveType(current.getType());
			if( currentType.isPrimitive() && signature.getModifiers().isNative() && signature.getModifiers().isExtern() ) //operation based on method
				current = new TACBinary(anchor, current, signature, op, next, isCompare );
			else {	
				//comparisons will always give positive, negative or zero integer
				//must be compared to 0 with regular int comparison to work
				if( isCompare ) {
					TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.INT));
					new TACLocalStore(anchor, var, new TACCall(anchor, new TACMethodRef(anchor, current, signature), current, next));		
					current = new TACLocalLoad(anchor, var);					
					current = new TACBinary(anchor, current, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), op, new TACLiteral(anchor, new ShadowInteger(0)), true );
				}
				else
					current = new TACCall(anchor, new TACMethodRef(anchor, current, signature), current, next);				
			}			
		}
		
		node.setOperand(current);
	}

	private void initializeArray(TACOperand array, MethodSignature create, List<TACOperand> params, TACOperand defaultValue)
	{	
		//nothing to do
		if( create == null && defaultValue == null )
			return;
		
		ArrayType arrayType = (ArrayType) array.getType();
		
		TACLabel bodyLabel = new TACLabel(method),
					updateLabel = new TACLabel(method),
					conditionLabel = new TACLabel(method),
					endLabel = new TACLabel(method);
		
		//make iterator (int index)
		TACVariable iterator = method.addTempLocal(new SimpleModifiedType(Type.INT));
		new TACLocalStore(anchor, iterator, new TACLiteral(anchor, new ShadowInteger(0)));
		TACOperand length = new TACLength(anchor, array, 0);			
		for (int i = 1; i < arrayType.getDimensions(); i++)
			length = new TACBinary(anchor, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), "*", new TACLength(anchor, array, i));			
		
		new TACBranch(anchor, conditionLabel);  //init is done, jump to condition
		
		bodyLabel.insertBefore(anchor);
		
		//put initialization before main body
		TACArrayRef location = new TACArrayRef(anchor, array, new TACLocalLoad(anchor, iterator), false);		
				
		if( create != null)
			new TACStore(anchor, location, callCreate( create, params, create.getOuter()));
		else
			new TACStore(anchor, location, defaultValue);		
		
		new TACBranch(anchor, updateLabel);
		updateLabel.insertBefore(anchor);			
		
		//increment iterator							
		TACOperand value = new TACBinary(anchor, new TACLocalLoad(anchor, iterator), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+", new TACLiteral(anchor, new ShadowInteger(1)));
		new TACLocalStore(anchor, iterator, value);	
		
		new TACBranch(anchor, conditionLabel);
		conditionLabel.insertBefore(anchor);
		
		//check if iterator < array length
		value = new TACLocalLoad(anchor, iterator);			
		TACOperand condition = new TACBinary(anchor, value, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), "<", length, true );
		
		new TACBranch(anchor, condition, bodyLabel, endLabel);		
		endLabel.insertBefore(anchor);
	}
	
	private TACOperand visitArrayAllocation(ArrayType type, TACClass baseClass,
			List<TACOperand> sizes, MethodSignature create, List<TACOperand> params)
	{
		return visitArrayAllocation(type, baseClass, sizes, create, params, null);
	}
	
	private TACOperand visitArrayAllocation(ArrayType type, TACClass baseClass,
			List<TACOperand> sizes, TACOperand defaultValue)
	{
		return visitArrayAllocation(type, baseClass, sizes, null, null, defaultValue);
	}
	
	private TACOperand visitArrayAllocation(ArrayType type, TACClass baseClass,
			List<TACOperand> sizes)
	{
		return visitArrayAllocation(type, baseClass, sizes, null, null, null);
	}
	
	private TACOperand visitArrayAllocation(ArrayType type, TACClass baseClass,
			List<TACOperand> sizes, MethodSignature create, List<TACOperand> params, TACOperand defaultValue)
	{
		TACOperand baseClassData = baseClass.getClassData();
		TACNewArray alloc = new TACNewArray(anchor, type, baseClassData,
				sizes.subList(0, type.getDimensions()));
		int start = type.getDimensions();
		int end = sizes.size();
		sizes = sizes.subList(start, end);
		if (!sizes.isEmpty())
		{
			TACVariable index = method.addTempLocal(new SimpleModifiedType(Type.INT));
			new TACLocalStore(anchor, index, new TACLiteral(anchor, new ShadowInteger(0)));
			TACLabel bodyLabel = new TACLabel(method),
					condLabel = new TACLabel(method),
					endLabel = new TACLabel(method);
			new TACBranch(anchor, condLabel);
			bodyLabel.insertBefore(anchor);
			new TACStore(anchor, new TACArrayRef(anchor, alloc, new TACLocalLoad(anchor, index), false),
					visitArrayAllocation((ArrayType)type.getBaseType(), new TACClass(anchor, (ArrayType)type.getBaseType()), sizes, create, params, defaultValue));
			new TACLocalStore(anchor, index, new TACBinary(anchor, new TACLocalLoad(anchor, index), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+",
					new TACLiteral(anchor, new ShadowInteger(1))));
			new TACBranch(anchor, condLabel);
			condLabel.insertBefore(anchor);
			new TACBranch(anchor, new TACBinary(anchor, new TACLocalLoad(anchor, index), alloc.getTotalSize()),
					endLabel, bodyLabel);
			endLabel.insertBefore(anchor);
		}
		else {
			//fill with values			
			initializeArray(alloc, create, params, defaultValue);			
		}
		
		return alloc;		
	}
	
	/*
	private TACOperand getDefaultValue(Context type)
	{
		TACOperand op;
		
		if (type.getType().equals(Type.BOOLEAN))
			op = new TACLiteral(anchor, new ShadowBoolean(false));
		else if (type.getType().equals(Type.CODE))
			op =  new TACLiteral(anchor, ShadowCode.parseCode("'\0'"));
		else if (type.getType().equals(Type.UBYTE))
			op =  new TACLiteral(anchor, ShadowInteger.parseNumber("0uy"));
		else if (type.getType().equals(Type.BYTE))
			op =  new TACLiteral(anchor, ShadowInteger.parseNumber("0y"));
		else if (type.getType().equals(Type.USHORT))
			op =  new TACLiteral(anchor, ShadowInteger.parseNumber("0us"));
		else if (type.getType().equals(Type.SHORT))
			op =  new TACLiteral(anchor, ShadowInteger.parseNumber("0s"));
		else if (type.getType().equals(Type.UINT))
			op =  new TACLiteral(anchor, ShadowInteger.parseNumber("0ui"));
		else if (type.getType().equals(Type.INT))
			op =  new TACLiteral(anchor, ShadowInteger.parseNumber("0i"));
		else if (type.getType().equals(Type.ULONG))
			op =  new TACLiteral(anchor, ShadowInteger.parseNumber("0ul"));
		else if (type.getType().equals(Type.LONG))
			op =  new TACLiteral(anchor, ShadowInteger.parseNumber("0l"));
		else if( type.getType().equals(Type.DOUBLE))
			op =  new TACLiteral(anchor, ShadowDouble.parseDouble("0.0"));
		else if( type.getType().equals(Type.FLOAT))
			op =  new TACLiteral(anchor, ShadowFloat.parseFloat("0.0f"));
		else if( type.getType() instanceof ClassType || type.getType() instanceof InterfaceType )
			op = new TACLiteral(anchor, new ShadowNull(type.getType()));
		else
			op = TACCast.cast(anchor, type, new TACLiteral(anchor, new ShadowNull(Type.NULL)));
		
		return op;
	}
	*/
	
	@Override public Void visitDestroy(ShadowParser.DestroyContext ctx)
	{ 
		throw new UnsupportedOperationException();		
	}
		
	@Override public Void visitForeachInit(ShadowParser.ForeachInitContext ctx)
	{ 
		visitChildren(ctx);	
		
		method.addLocal(ctx, ctx.Identifier().getText());		
		//append collection
		ctx.setOperand(ctx.conditionalExpression().appendBefore(anchor));
		return null;
	}
	
	@Override public Void visitInlineResults(ShadowParser.InlineResultsContext ctx)
	{ 
		throw new UnsupportedOperationException();
	}	
	
	@Override public Void visitSequenceRightSide(ShadowParser.SequenceRightSideContext ctx)	
	{ 
		visitChildren(ctx);
		
		//Note: their instructions are appended, but the operands for each sequence element are retrieved later and not saved here
		for(ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression() )
			child.appendBefore(anchor);
		
		return null;		
	}	

	@Override public Void visitSequenceLeftSide(ShadowParser.SequenceLeftSideContext ctx)
	{ 
		visitChildren(ctx);
		
		//Note: their instructions are appended, but the operands for each sequence element are retrieved later and not saved here
		for( ParseTree child : ctx.children )
			if( child instanceof Context )
				((Context)child).appendBefore(anchor);
		
		return null;
	}
	
	@Override public Void visitSequenceVariable(ShadowParser.SequenceVariableContext ctx)
	{ 
		visitChildren(ctx);
				
		TACVariable var = method.addLocal(ctx, ctx.Identifier().getText());
		
		prefix = new TACLocalLoad(anchor, var);
		ctx.setOperand(prefix);
		
		//ctx.setOperand(new TACLocalStore(anchor, var, new TACLiteral(anchor, new ShadowUndefined(ctx.getType()))));
		
		return null;		
	}
	
	@Override public Void visitRightSide(ShadowParser.RightSideContext ctx)
	{ 
		visitChildren(ctx);
		
		if( ctx.sequenceRightSide() != null ) {
			ctx.sequenceRightSide().appendBefore(anchor);
			List<TACOperand> sequence =
					new ArrayList<TACOperand>(ctx.sequenceRightSide().conditionalExpression().size());	
			for( ShadowParser.ConditionalExpressionContext child : ctx.sequenceRightSide().conditionalExpression() )
				sequence.add(child.getOperand());
			ctx.setOperand(new TACSequence(anchor, sequence));			
		}
		else
			ctx.setOperand(ctx.conditionalExpression().appendBefore(anchor));
		
		return null;
	}
	
	@Override public Void visitCopyExpression(ShadowParser.CopyExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand value = ctx.conditionalExpression().appendBefore(anchor);
		prefix = value;
		Type type = ctx.getType();
		
		if( !type.getModifiers().isImmutable() ) { //if immutable, do nothing, the old one is fine
			TACNewObject object = new TACNewObject(anchor, Type.ADDRESS_MAP );
			TACMethodRef create = new TACMethodRef(anchor, Type.ADDRESS_MAP.getMatchingMethod("create", new SequenceType()) );
			TACOperand map = new TACCall(anchor, create, object);
			
			if( type instanceof ArrayType )
				prefix = copyArray(value, map);			
			else {
				TACMethodRef copyMethod;
				TACVariable result = method.addTempLocal(ctx);
				TACOperand data = value;					
				
				TACLabel nullLabel = new TACLabel(method);
				TACLabel doneLabel = new TACLabel(method);
				TACLabel copyLabel = new TACLabel(method);
				
				if( type instanceof InterfaceType )	{	
					//cast converts from interface to object
					data = TACCast.cast(anchor, new SimpleModifiedType(Type.OBJECT), data);
					TACOperand nullCondition = new TACBinary(anchor, data, new TACLiteral(anchor, new ShadowNull(data.getType())));
					new TACBranch(anchor, nullCondition, nullLabel, copyLabel);
					copyLabel.insertBefore(anchor);
					copyMethod = new TACMethodRef(anchor, data, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));						
				}
				else {
					TACOperand nullCondition = new TACBinary(anchor, data, new TACLiteral(anchor, new ShadowNull(data.getType())));						
					new TACBranch(anchor, nullCondition, nullLabel, copyLabel);
					copyLabel.insertBefore(anchor);
					copyMethod  = new TACMethodRef(anchor, data, type.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));						
				}
				
				TACOperand copy = new TACCall(anchor, copyMethod, data, map);

				if( type instanceof InterfaceType )
					//and then a cast back to interface
					copy = TACCast.cast(anchor, ctx, copy);
				
				new TACLocalStore(anchor, result, copy);					
				new TACBranch(anchor, doneLabel);	
				
				nullLabel.insertBefore(anchor);
				
				new TACLocalStore(anchor, result, value);
				new TACBranch(anchor, doneLabel);
				
				doneLabel.insertBefore(anchor);
				prefix = new TACLocalLoad(anchor, result);
			}
		}	
		
		ctx.setOperand(prefix);
		
		return null;
	}
	
	@Override public Void visitArrayDimensions(ShadowParser.ArrayDimensionsContext ctx)
	{ 
		visitChildren(ctx);
		
		//Note: their instructions are appended, but the operands for each dimension are retrieved later and not saved here
		for( ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression() )
			child.appendBefore(anchor);
		
		return null;	
	}	

	@Override
	public Void visitArrayCreateCall(ArrayCreateCallContext ctx)
	{		
		visitChildren(ctx);		
		for( ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression() )
			child.appendBefore(anchor);		
		
		return null;
	}

	@Override
	public Void visitArrayDefault(ArrayDefaultContext ctx) 
	{
		visitChildren(ctx);		
		ctx.setOperand(ctx.conditionalExpression().appendBefore(anchor));
		
		return null;
	}
	
	@Override public Void visitSpawnExpression(shadow.parse.ShadowParser.SpawnExpressionContext ctx)
	{
		visitChildren(ctx);
		
		Type runnerType = ctx.type().getType();
		
		MethodSignature runnerCreateSignature = ctx.spawnRunnerCreateCall().getSignature();
		List<TACOperand> runnerParams = new ArrayList<TACOperand>(((SequenceType)ctx.spawnRunnerCreateCall().getType()).size() + 1);	

		ctx.spawnRunnerCreateCall().appendBefore(anchor);
		for(ShadowParser.ConditionalExpressionContext child : ctx.spawnRunnerCreateCall().conditionalExpression()) {						
			runnerParams.add(child.getOperand());
		}
		
		TACOperand runnerRef = callCreate(runnerCreateSignature, runnerParams, runnerType);
		
		MethodSignature threadCreateSignature = ctx.getSignature();
		ArrayList<TACOperand> params = new ArrayList<TACOperand>();
		if(ctx.StringLiteral() != null) {
			params.add(new TACLiteral(anchor, ShadowString.parseString(ctx.StringLiteral().getText())));
		}
		params.add(runnerRef);
		
		prefix = callCreate(threadCreateSignature, params, Type.THREAD);
		ctx.setOperand(prefix);
		
		return null;
	}
	
	@Override public Void visitSpawnRunnerCreateCall(shadow.parse.ShadowParser.SpawnRunnerCreateCallContext ctx) 
	{
		visitChildren(ctx);
		
		for(ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression()) {
			child.appendBefore(anchor);
		}
		
		return null;
	}
	
	@Override
	public Void visitSendStatement(SendStatementContext ctx) {
		visitChildren(ctx);

		TACOperand object = ctx.conditionalExpression(0).appendBefore(anchor);
		TACOperand thread = ctx.conditionalExpression(1).appendBefore(anchor);

		TACMethodRef methodRef = new TACMethodRef(anchor, thread, ctx.getSignature());
		List<TACOperand> params = new ArrayList<TACOperand>();
		params.add(methodRef.getPrefix());
		params.add(object);

		new TACCall(anchor, methodRef, params);

		return null;
	}
}
