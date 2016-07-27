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

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowCode;
import shadow.interpreter.ShadowDouble;
import shadow.interpreter.ShadowFloat;
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
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
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
	private TACNode tree;	
	private TACMethod method;
	private TACOperand prefix;
	private boolean explicitSuper;	
	private TACBlock block;	
	private Deque<TACModule> moduleStack = new ArrayDeque<TACModule>();	 	
	
	public TACModule build(Context node) {		
		//tree = new TACTree(); //no block				
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
		TACNode saveList = tree;
		tree = new TACDummyNode(context, block);
		//tree.setASTNode((Context)node);
		context.accept(this);		
		//take out dummy node and save the resulting list in the context
		context.setList(tree.remove());
		tree = saveList; 
		
		return null;
	}
	/*
	public void walk(Node node)  {
		tree = tree.next(node.jjtGetNumChildren());
		Object type = visit(node, false);
		if (type != NO_CHILDREN) {
			for (int i = 0; i < node.jjtGetNumChildren(); i++)
				walk(node.jjtGetChild(i));
			if (type == POST_CHILDREN)
				visit(node, true);
		}
		tree = tree.done();
	}

	public Object visit(Node node, Boolean secondVisit) throws ShadowException {
		tree.setASTNode(node);
		return node.jjtAccept(this, secondVisit);
	}
	
	@Override
	public Object visit(SimpleNode node, Boolean secondVisit)
			throws ShadowException {
		tree.setASTNode(node);
		return node.jjtAccept(this, secondVisit);
	}
	*/
	
	/* Empty visitors */
		
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
						constant.Identifier().getText()), constant);
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
			String name = ctx.Identifier().getText();			
			if( ctx.getModifiers().isField() ) {
				TACReference ref = new TACFieldRef(new TACLocalLoad(tree, method.getThis()), name);
				
				if( ctx.conditionalExpression() != null )
					new TACStore(tree, ref, ctx.conditionalExpression().appendBefore(tree));
				//Note that default values are now removed for regular types					
				else if( ctx.getModifiers().isNullable() || ctx.getType() instanceof ArrayType )
					new TACStore(tree, ref, getDefaultValue(ctx));
			}
			else {
				TACVariable var = method.addLocal(ctx, name);
				if( ctx.conditionalExpression() != null )
					new TACLocalStore(tree, var, ctx.conditionalExpression().appendBefore(tree));
				else
					new TACLocalStore(tree, var, new TACLiteral(tree, new ShadowUndefined(ctx.getType())));
			}			
		}
		
		return null;
	}
	
	@Override public Void visitArrayInitializer(ShadowParser.ArrayInitializerContext ctx)
	{ 
		visitChildren(ctx);
		
		List<TACOperand> sizes = new ArrayList<TACOperand>();					
		//either the list of initializers or conditional expressions will be empty
		sizes.add(new TACLiteral(tree, new ShadowInteger(ctx.arrayInitializer().size() + ctx.conditionalExpression().size())));
		ArrayType arrayType = (ArrayType)ctx.getType();
		TACClass baseClass = new TACClass(tree, arrayType.getBaseType());
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
			TACArrayRef ref = new TACArrayRef(tree, prefix, new TACLiteral(tree, new ShadowInteger(i)), false); 
			new TACStore(tree, ref, list.get(i).appendBefore(tree));				
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
			TACOperand instance = new TACLoad(tree, reference);
			new TACBranch(tree, new TACBinary(tree, instance, new TACLiteral(tree, new ShadowNull(instance.getType()))), initLabel, doneLabel);
			initLabel.insertBefore(tree);
			
			TACMethodRef methodRef = new TACMethodRef(tree, type.getMethods("create").get(0));
			TACOperand object = new TACNewObject(tree, type);
			TACCall call = new TACCall(tree, methodRef, object );			
			new TACStore(tree, reference, call ); 				
			new TACBranch(tree, doneLabel);
			doneLabel.insertBefore(tree);
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
		
		ctx.block().appendBefore(tree);
		
		TACNode last = tree.getPrevious();		
		//A non-void method should always have explicit TACReturns
		//A void one might need one inserted at the end			
		if( method.getSignature().isVoid() && !isTerminator(last) ) {
			TACReturn explicitReturn = new TACReturn(tree, new SequenceType() );
			//prevents an error from being recorded if this return is later removed as dead code
			explicitReturn.setASTNode(null); 
		}
		
		return null;
	}
	
	@Override public Void visitCreateDeclaration(ShadowParser.CreateDeclarationContext ctx)
	{ 
		initializeSingletons(ctx.getSignature());
		visitChildren(ctx); 
		
		if( ctx.createBlock() != null ) //possible because we still walk dummy nodes created for default creates
			ctx.createBlock().appendBefore(tree);
		
		TACNode last = tree.getPrevious();
		
		if (last instanceof TACLabel && last.getPrevious() instanceof TACReturn)
			last.remove();
		else		
			new TACReturn(tree, method.getSignature().getFullReturnTypes(),
					new TACLocalLoad(tree, method.getThis()));
		
		return null;
	}
	
	@Override public Void visitDestroyDeclaration(ShadowParser.DestroyDeclarationContext ctx)
	{ 
		initializeSingletons(ctx.getSignature());
		visitChildren(ctx);
		ctx.block().appendBefore(tree);	
		
		TACNode last = tree.getPrevious();		
		if( !isTerminator(last) ) {
			TACReturn explicitReturn = new TACReturn(tree, new SequenceType() );
			//prevents an error from being recorded if this return is later removed as dead code
			explicitReturn.setASTNode(null); 
		}
		
		return null;
	}
	
	//here
	
	@Override public Void visitExplicitCreateInvocation(ShadowParser.ExplicitCreateInvocationContext ctx)
	{ 
		visitChildren(ctx);
	
		boolean isSuper = ctx.getChild(0).getText().equals("super");
		ClassType thisType = (ClassType)method.getSignature().getOuter();
		List<TACOperand> params = new ArrayList<TACOperand>();
		params.add(new TACLocalLoad(tree, method.getThis()));
		
		if( (!isSuper && thisType.hasOuter()) || (isSuper && thisType.getExtendType().hasOuter()) ) {
			TACVariable outer = method.getParameter("_outer");
			params.add(new TACLocalLoad(tree, outer));
		}
		
		for( ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression())			 
			params.add(child.appendBefore(tree));
		
		new TACCall(tree, new TACMethodRef(tree, ctx.getSignature()), params);
		
		// If a super create, walk the fields.
		// Walking the fields is unnecessary if there is a this create,
		// since it will be taken care of by the this create, either explicitly or implicity.
		if( isSuper ) {
			// Walk fields in *exactly* the order they were declared since
			// some fields depend on prior fields.
			// This is accomplished by using a LinkedHashMap.
			for( Context field : thisType.getFields().values() ) 
				if (!field.getModifiers().isConstant() && !(field.getType() instanceof SingletonType)) {
					visit(field);
					field.appendBefore(tree);
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
				methodRef = new TACMethodRef(tree,
						left, //prefix
						signature);
				//TODO: Might need signature without type arguments?
				parameters.set(0, methodRef.getPrefix()); //replacing left with the method prefix can prevent duplicate code (if there were casts)					
				TACOperand result = new TACCall(tree, methodRef, parameters);					
				
				//signature for other operation
				signature = node.getOperations().get(0);
				
				if( left.getType().isPrimitive() && signature.getModifiers().isNative() )
					right = new TACBinary(tree, result, signature, operation, right);
				else {
					TACVariable temp = method.addTempLocal(signature.getReturnTypes().get(0));
					methodRef = new TACMethodRef(tree, result, signature);
					new TACLocalStore(tree, temp, new TACCall(tree, methodRef, methodRef.getPrefix(), right));		
					right = new TACLocalLoad(tree, temp);						
				}		
				
				parameters = new ArrayList<TACOperand>( parameters );					
			}				
			
			parameters.add(right); //value to store (possibly updated by code above)
			
			signature = propertyType.getSetter();	
			methodRef = new TACMethodRef(tree,
					left, //prefix
					signature);
			//TODO: Might need signature without type arguments?
			parameters.set(0, methodRef.getPrefix()); //replacing left with the method prefix can prevent duplicate code (if there were casts)
			new TACCall(tree, methodRef, parameters);
		}			
		else if( left instanceof TACLoad ){ //memory operation: field, array, etc.	
			TACReference var = ((TACLoad)left).getReference();
			if (!operation.isEmpty()) {	
				signature = node.getOperations().get(0);
				if( left.getType().isPrimitive() && signature.getModifiers().isNative() )
					right = new TACBinary(tree, left, signature, operation, right );
				else {
					TACVariable temp = method.addTempLocal(signature.getReturnTypes().get(0));
					methodRef = new TACMethodRef(tree, left, signature);
					new TACLocalStore(tree, temp, new TACCall(tree, methodRef, methodRef.getPrefix(), right));		
					right = new TACLocalLoad(tree, temp);						
				}
			}
			else {
				//in straight assignment, loading the left is unnecessary because we're just going to store there
				//we had to build in the load so that we had something to get, but now we don't need it
				left.remove();
			}
			
			new TACStore(tree, var, right);				
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
					right = new TACBinary(tree, left, signature, operation, right );
				else {
					TACVariable temp = method.addTempLocal(signature.getReturnTypes().get(0));
					methodRef = new TACMethodRef(tree, left, signature);
					new TACLocalStore(tree, temp, new TACCall(tree, methodRef, methodRef.getPrefix(), right));		
					right = new TACLocalLoad(tree, temp);						
				}
			}
			else if( left instanceof TACLocalLoad )
				left.remove(); //once we know the var, we can remove the local load
			
			new TACLocalStore(tree, var, right);	
		}
		else
			throw new UnsupportedOperationException();
	}
	
	@Override public Void visitExpression(ShadowParser.ExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand left = ctx.primaryExpression().appendBefore(tree);
		
		if( ctx.assignmentOperator() != null ) {					
			TACOperand right = ctx.conditionalExpression().appendBefore(tree);							
			String operation = ctx.assignmentOperator().getText();			
			doAssignment(left, ctx.primaryExpression(), right, operation, ctx);
		}		
		
		return null;
	}
	
	@Override public Void visitConditionalExpression(ShadowParser.ConditionalExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand condition = ctx.coalesceExpression().appendBefore(tree);
		ctx.setOperand(condition);
		
		if( ctx.conditionalExpression().size() > 0 ) {
			TACLabel trueLabel = new TACLabel(method),
					falseLabel = new TACLabel(method),
					doneLabel = new TACLabel(method);
			TACVariable var = method.addTempLocal(ctx);
			new TACBranch(tree, condition, trueLabel, falseLabel);
			trueLabel.insertBefore(tree);
			new TACLocalStore(tree, var, ctx.conditionalExpression(0).appendBefore(tree));
			new TACBranch(tree, doneLabel);
			falseLabel.insertBefore(tree);
			new TACLocalStore(tree, var, ctx.conditionalExpression(1).appendBefore(tree));
			new TACBranch(tree, doneLabel);
			doneLabel.insertBefore(tree);
			ctx.setOperand(new TACLocalLoad(tree, var));
		}
		
		return null;
	}
	
	@Override public Void visitCoalesceExpression(ShadowParser.CoalesceExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand value = ctx.conditionalOrExpression(0).appendBefore(tree);
		ctx.setOperand(value);
		
		if( ctx.conditionalOrExpression().size() > 1 ) {
			TACLabel doneLabel = new TACLabel(method);
			TACVariable var = method.addTempLocal(ctx);
			
			for( int i = 1; i < ctx.conditionalOrExpression().size(); ++i ) {
				TACLabel nullLabel = new TACLabel(method);
				TACLabel nonNullLabel = new TACLabel(method);
				new TACBranch(tree, new TACBinary(tree, value, new TACLiteral(tree, new ShadowNull(value.getType()))), nullLabel, nonNullLabel);
				nonNullLabel.insertBefore(tree);
				new TACLocalStore(tree, var, value);
				new TACBranch(tree, doneLabel);
				nullLabel.insertBefore(tree);
				value = ctx.conditionalOrExpression(i).appendBefore(tree);
			}			

			//whatever the final thing is, we're stuck with it if we got that far
			new TACLocalStore(tree, var, value);
			new TACBranch(tree, doneLabel);			
			doneLabel.insertBefore(tree);			
			ctx.setOperand(new TACLocalLoad(tree, var));
		}
		
		return null;
	}
	
	@Override public Void visitConditionalOrExpression(ShadowParser.ConditionalOrExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand value = ctx.conditionalExclusiveOrExpression(0).appendBefore(tree);
		ctx.setOperand(value);
	
		if( ctx.conditionalExclusiveOrExpression().size() > 1 ) {
			TACLabel doneLabel = new TACLabel(method);
			TACVariable var = method.addTempLocal(ctx);			
			new TACLocalStore(tree, var, value);
			for (int i = 1; i < ctx.conditionalExclusiveOrExpression().size(); i++) {
				TACLabel nextLabel = new TACLabel(method);
				new TACBranch(tree, value, doneLabel, nextLabel);
				nextLabel.insertBefore(tree);
				value = ctx.conditionalExclusiveOrExpression(i).appendBefore(tree);
				new TACLocalStore(tree, var, value);
			}
			new TACBranch(tree, doneLabel);
			doneLabel.insertBefore(tree);
			ctx.setOperand(new TACLocalLoad(tree, var));
		}
		
		return null;
	}
	
	@Override public Void visitConditionalExclusiveOrExpression(ShadowParser.ConditionalExclusiveOrExpressionContext ctx) 
	{
		visitChildren(ctx); 		
		TACOperand value = ctx.conditionalAndExpression(0).appendBefore(tree);
		for( int i = 1; i < ctx.conditionalAndExpression().size(); i++ ) {
			TACOperand next = ctx.conditionalAndExpression(i).appendBefore(tree);										
			value = new TACBinary(tree, value, TACBinary.Boolean.XOR, next);
		}		
		
		ctx.setOperand(value);
		
		return null;
	}
	
	@Override public Void visitConditionalAndExpression(ShadowParser.ConditionalAndExpressionContext ctx)
	{ 
		visitChildren(ctx);
		TACOperand value = ctx.bitwiseOrExpression(0).appendBefore(tree);
		ctx.setOperand(value);
	
		if( ctx.bitwiseOrExpression().size() > 1 ) {
			TACLabel doneLabel = new TACLabel(method);
			TACVariable var = method.addTempLocal(ctx);			
			new TACLocalStore(tree, var, value);
			for (int i = 1; i < ctx.bitwiseOrExpression().size(); i++) {
				TACLabel nextLabel = new TACLabel(method);
				new TACBranch(tree, value, nextLabel, doneLabel);
				nextLabel.insertBefore(tree);
				value = ctx.bitwiseOrExpression(i).appendBefore(tree);
				new TACLocalStore(tree, var, value);
			}
			new TACBranch(tree, doneLabel);
			doneLabel.insertBefore(tree);
			ctx.setOperand(new TACLocalLoad(tree, var));
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
		
		TACOperand current = ctx.isExpression(0).appendBefore(tree);		
		for( int i = 1; i < ctx.isExpression().size(); i++ ) {
			String op = ctx.getChild(2*i - 1).getText();  //the operations are every other child
			TACOperand next = ctx.isExpression(i).appendBefore(tree);
			Type currentType = current.getType();
			Type nextType = next.getType();
							
			if( op.equals("==") || op.equals("!=") ) { //== or !=									
				if (currentType.isPrimitive() &&
					nextType.isPrimitive() ) //if not, methods are needed					
					current = new TACBinary(tree, current, next);					
				else {	
					//no nullables allowed
					TACVariable var = method.addTempLocal(ctx);
					Type valueType = resolveType(current.getType());
					MethodSignature signature = valueType.getMatchingMethod("equal", new SequenceType(next));												
					new TACLocalStore(tree, var, new TACCall(tree,
							new TACMethodRef(tree, current, signature),
							current, next));
					current = new TACLocalLoad(tree, var);
				}						
			}
			else { //=== or !==				
				boolean currentNullable = current.getModifiers().isNullable();
				boolean nextNullable = next.getModifiers().isNullable();
				if( currentType.isPrimitive() && nextType.isPrimitive() && ( currentNullable || nextNullable )) {
					TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.BOOLEAN));
					TACLabel done = new TACLabel(method);
					
					if( currentNullable && nextNullable ) {							
						TACOperand valueNull = new TACBinary(tree, current, new TACLiteral(tree, new ShadowNull(current.getType())));
						TACOperand otherNull = new TACBinary(tree, next, new TACLiteral(tree, new ShadowNull(next.getType())));							
						TACOperand bothNull = new TACBinary(tree, valueNull, TACBinary.Boolean.AND, otherNull );
						TACOperand eitherNull = new TACBinary(tree, valueNull, TACBinary.Boolean.OR, otherNull );							
						TACLabel notBothNull = new TACLabel(method);												
						TACLabel noNull = new TACLabel(method);
						
						new TACLocalStore(tree, var, bothNull);
						new TACBranch(tree, bothNull, done, notBothNull); //var will be true (both null)
						
						notBothNull.insertBefore(tree);
						new TACBranch(tree, eitherNull, done, noNull); //var will be false (one but not both null)
													
						noNull.insertBefore(tree);
						new TACLocalStore(tree, var, new TACBinary(tree,
								TACCast.cast(tree, new SimpleModifiedType(current.getType()), current),
								TACCast.cast(tree, new SimpleModifiedType(next.getType()), next)));
						new TACBranch(tree, done);
					}
					else if( currentNullable ) { //only current nullable							
						TACOperand currentNull = new TACBinary(tree, current, new TACLiteral(tree, new ShadowNull(current.getType())));							
						TACLabel oneNull = new TACLabel(method);												
						TACLabel noNull = new TACLabel(method);														
						new TACBranch(tree, currentNull, oneNull, noNull);
						oneNull.insertBefore(tree);
						new TACLocalStore(tree, var, new TACLiteral(tree, new ShadowBoolean(false)));		
						new TACBranch(tree, done);														
						noNull.insertBefore(tree);
						new TACLocalStore(tree, var, new TACBinary(tree,
								TACCast.cast(tree, new SimpleModifiedType(current.getType()), current),
								next));
						new TACBranch(tree, done);							
					}
					else { //only next nullable 
						TACOperand nextNull = new TACBinary(tree, next, new TACLiteral(tree, new ShadowNull(next.getType())));							
						TACLabel oneNull = new TACLabel(method);												
						TACLabel noNull = new TACLabel(method);														
						new TACBranch(tree, nextNull, oneNull, noNull);
						oneNull.insertBefore(tree);
						new TACLocalStore(tree, var, new TACLiteral(tree, new ShadowBoolean(false)));									
						new TACBranch(tree, done);														
						noNull.insertBefore(tree);
						new TACLocalStore(tree, var, new TACBinary(tree,
								current,
								TACCast.cast(tree, new SimpleModifiedType(next.getType()), next)));
						new TACBranch(tree, done);
					}
					
					done.insertBefore(tree);
					current = new TACLocalLoad(tree, var);
				}
				else //both non-nullable primitives or both references, no problem		
					current = new TACBinary(tree, current, next);
			}
			if( op.startsWith("!") )
				current = new TACUnary(tree, "!", current);
		}		
		ctx.setOperand(current);
		
		return null;
	}
	
	@Override public Void visitIsExpression(ShadowParser.IsExpressionContext ctx)
	{ 
		visitChildren(ctx);
		TACOperand value = ctx.relationalExpression().appendBefore(tree);
		ctx.setOperand(value);
		
		if( ctx.type() != null ) {						
			Type comparisonType = ctx.type().getType();
			
			if( comparisonType instanceof ArrayType ) {
				ArrayType arrayType = (ArrayType) comparisonType;
				comparisonType = arrayType.convertToGeneric();
			}
			
			TACOperand comparisonClass = new TACClass(tree, comparisonType).getClassData();

			//get class from object
			TACMethodRef methodRef = new TACMethodRef(tree, value,
					Type.OBJECT.getMatchingMethod("getClass", new SequenceType()));						
			TACOperand valueClass = new TACCall(tree, methodRef, methodRef.getPrefix());

			methodRef = new TACMethodRef(tree, valueClass,
					Type.CLASS.getMatchingMethod("isSubtype", new SequenceType(Type.CLASS)));
			
			ctx.setOperand(new TACCall(tree, methodRef, methodRef.getPrefix(), comparisonClass));
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
				new TACBranch(tree, new TACBinary(tree, operand,
						new TACLiteral(tree, new ShadowNull(operand.getType()))), nullLabel,
						nonnullLabel);
				nullLabel.insertBefore(tree);
				new TACLocalStore(tree, var, new TACLiteral(tree, new ShadowString("null")));
				new TACBranch(tree, doneLabel);
				nonnullLabel.insertBefore(tree);
				
				if( type.isPrimitive() ) //convert non null primitive wrapper to real primitive			
					operand = TACCast.cast(tree, new SimpleModifiedType(type), operand);
				
				TACMethodRef methodRef = new TACMethodRef(tree, operand,
						type.getMatchingMethod("toString", new SequenceType())); 
				
				new TACLocalStore(tree, var, new TACCall(tree, methodRef, methodRef.getPrefix()));
				new TACBranch(tree, doneLabel);
				doneLabel.insertBefore(tree);
				operand = new TACLocalLoad(tree, var);
			}
			else {
				TACMethodRef methodRef = new TACMethodRef(tree, operand,
						type.getMatchingMethod("toString", new SequenceType())); 
				operand = new TACCall(tree, methodRef,
						Collections.singletonList(methodRef.getPrefix()));
			}
		}
				
		return operand;
	}
	
	@Override public Void visitConcatenationExpression(ShadowParser.ConcatenationExpressionContext ctx)
	{ 
		visitChildren(ctx);
		TACOperand last = ctx.shiftExpression(0).appendBefore(tree);
				
		for( int i = 1; i < ctx.shiftExpression().size(); ++i  ) {
			if( i == 1 )
				last = convertToString(last);
			
			TACOperand next = convertToString(ctx.shiftExpression(i).appendBefore(tree));
			last = new TACCall(tree, new TACMethodRef(tree, Type.STRING.getMethods("concatenate").get(0)),
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
			ctx.setOperand(ctx.primaryExpression().appendBefore(tree));
		else if( ctx.inlineMethodDefinition() != null )
			ctx.setOperand(ctx.inlineMethodDefinition().appendBefore(tree));
		else { //unary is happening
			TACOperand operand = ctx.unaryExpression().appendBefore(tree);
			String op = ctx.getChild(0).getText(); 
			if( op.equals("#")) //string is special because of nulls
				ctx.setOperand(convertToString( operand ));			
			else {
				Type type = resolveType(operand.getType());					
				if( op.equals("!") )
					ctx.setOperand(new TACUnary(tree, "!", operand));
				else {
					MethodSignature signature = ctx.getOperations().get(0); 
					if( type.isPrimitive() && signature.getModifiers().isNative() )
						ctx.setOperand(new TACUnary(tree, signature, op, operand));				
					else 
						ctx.setOperand(new TACCall(tree, new TACMethodRef(tree, operand, ctx.getOperations().get(0)), operand));					
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
		ctx.setOperand(TACCast.cast(tree, ctx, ctx.conditionalExpression().appendBefore(tree), true));
		
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
		TACOperand operand = ctx.conditionalExpression().appendBefore(tree);
		
		//if there's a recover, things will be handled there if null			
		new TACBranch(tree, new TACBinary(tree, operand, new TACLiteral(tree,
				new ShadowNull(operand.getType()))), recover, continueLabel);
		
		//otherwise, we throw an exception here
		if( !block.hasRecover() ) {
			recover.insertBefore(tree);
			TACOperand object = new TACNewObject(tree, Type.UNEXPECTED_NULL_EXCEPTION);
			MethodSignature signature = Type.UNEXPECTED_NULL_EXCEPTION.getMatchingMethod("create", new SequenceType());						
			TACCall exception = new TACCall(tree, new TACMethodRef(tree, signature), object);
						
			new TACThrow(tree, exception);
		}	
		
		continueLabel.insertBefore(tree);
		
		//add in cast to remove nullable?
		prefix = TACCast.cast(tree, ctx, operand);
		ctx.setOperand(prefix);
		
		return null;
	}
	
	@Override public Void visitPrimaryExpression(ShadowParser.PrimaryExpressionContext ctx)
	{ 		
		TACOperand savePrefix = prefix;
		prefix = null;		
		visitChildren(ctx);
		
		TACOperand operand = ctx.primaryPrefix().appendBefore(tree);
		for( ShadowParser.PrimarySuffixContext suffix : ctx.primarySuffix() )
			operand = suffix.appendBefore(tree);
		
		ctx.setOperand(operand);
		prefix = savePrefix;
		
		return null;
	}
	
	@Override public Void visitPrimarySuffix(ShadowParser.PrimarySuffixContext ctx)
	{ 
		visitChildren(ctx);
		
		Context child = (Context)ctx.getChild(0);
		ctx.setOperand(child.appendBefore(tree));
		
		return null;
	}
	
	@Override public Void visitAllocation(ShadowParser.AllocationContext ctx)
	{
		visitChildren(ctx);
		
		Context child = (Context)ctx.getChild(0);
		ctx.setOperand(child.appendBefore(tree));
		
		return null;		
	}

	@Override public Void visitPrimaryPrefix(ShadowParser.PrimaryPrefixContext ctx)
	{ 
		visitChildren(ctx);
		
		//TODO: remove prefix, since the value can be gotten from the Context node?		
		if( ctx.literal() != null )
			prefix = ctx.literal().appendBefore(tree);
		else if( ctx.checkExpression() != null ) 
			prefix = ctx.checkExpression().appendBefore(tree);
		else if( ctx.copyExpression() != null )
			prefix = ctx.copyExpression().appendBefore(tree);
		else if( ctx.castExpression() != null )
			prefix = ctx.castExpression().appendBefore(tree);
		else if( ctx.conditionalExpression() != null )
			prefix = ctx.conditionalExpression().appendBefore(tree);
		else if( ctx.primitiveType() != null )
			prefix = ctx.primitiveType().appendBefore(tree);
		else if( ctx.functionType() != null )
			prefix = ctx.functionType().appendBefore(tree);
		else if( ctx.arrayInitializer() != null )
			prefix = ctx.arrayInitializer().appendBefore(tree);
		else if( ctx.getType() instanceof SingletonType )
			prefix = new TACLoad(tree, new TACSingletonRef((SingletonType)ctx.getType()));
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
					prefix = new TACLocalLoad(tree, local);
				else {
					if (ctx.getModifiers().isConstant()) { //constant
						Type thisType = method.getSignature().getOuter();
						//figure out type that defines constant							
						while( !thisType.containsField(name))
							thisType = thisType.getOuter();
						prefix = new TACLoad(tree, new TACConstantRef(thisType, name));
					}
					else { //field					
						ModifiedType thisRef = method.getThis();
						TACOperand op = new TACLocalLoad(tree, (TACVariable)thisRef);
						//make chain of this:_outer references until field is found
						while (!op.getType().containsField(name)) {								
							op = new TACLoad(tree, new TACFieldRef(op,
									new SimpleModifiedType(op.getType().
											getOuter()), "_outer"));
						}							
						prefix = new TACLoad(tree, new TACFieldRef(op, name));
					}
				}
			}				
		}			
		ctx.setOperand(prefix);
			
		return null;
	}

	private void methodCall(MethodSignature signature, Context node, List<? extends Context> list) {
		if (prefix == null) {			
			prefix = new TACLocalLoad(tree, method.getThis());
			
			//for outer class method calls
			Type prefixType = prefix.getType().getTypeWithoutTypeArguments();
			Type methodOuter = signature.getOuter().getTypeWithoutTypeArguments();
			
			while( !prefixType.isSubtype(methodOuter) ) {			
				prefix = new TACLoad(tree, new TACFieldRef(prefix, new SimpleModifiedType(prefixType.getOuter()),
					"_outer"));
				prefixType = prefixType.getOuter();				
			}
		}
		
		TACMethodRef methodRef = new TACMethodRef(tree,
				prefix,				
				signature);		
		methodRef.setSuper(explicitSuper);
		List<TACOperand> params = new ArrayList<TACOperand>();

		params.add(methodRef.getPrefix());
		for( Context child : list )	//potentially empty list				
			params.add(child.appendBefore(tree));
		
		prefix = new TACCall(tree, methodRef, params);
		MethodType methodType = signature.getMethodType();
		//sometimes a cast is needed when dealing with generic types
		SequenceType requiredReturnTypes = methodType.getReturnTypes();
		SequenceType methodReturnTypes = methodRef.getReturnTypes();		
		
		if( !methodReturnTypes.matches(requiredReturnTypes) ) {
			if( requiredReturnTypes.size() == 1 )			
				prefix = TACCast.cast(tree, requiredReturnTypes.get(0), prefix);
			else
				prefix = TACCast.cast(tree, new SimpleModifiedType(requiredReturnTypes), prefix);
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
			
			prefix = new TACClass(tree, type, raw).getClassData();
		}
		else if( ctx.getModifiers().isConstant() )
			prefix = new TACLoad(tree, new TACConstantRef(getPrefix(ctx).getType(), ctx.Identifier().getText()));
		else if( ctx.getType() instanceof SingletonType)
			prefix = new TACLoad(tree, new TACSingletonRef((SingletonType)ctx.getType()));
		else if( !ctx.getModifiers().isTypeName() )  //doesn't do anything at this stage if it's just a type name				
			prefix = new TACLoad(tree, new TACFieldRef(prefix, ctx.Identifier().getText()));
		
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
				TACOperand index = child.appendBefore(tree);
				//uints, longs, and ulongs can be used as indexes
				//however, ints are required
				Type indexType = index.getType();
				if( indexType.isIntegral() && !indexType.isSubtype(Type.INT))
					index = TACCast.cast(tree, new SimpleModifiedType(Type.INT, index.getModifiers()), index);
				list.add(index);
			}
			
			if( arrayType.getBaseType() instanceof TypeParameter )
				prefix = new TACLoad(tree, new TACGenericArrayRef(tree, prefix, list));
			else
				prefix = new TACLoad(tree, new TACArrayRef(tree, prefix, list));
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
				ctx.conditionalExpression(0).appendBefore(tree);
				
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
			TACOperand length = new TACLength(tree, prefix, 0);
			for (int i = 1; i < arrayType.getDimensions(); i++)
				length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), "*", new TACLength(tree, prefix, i));
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
		prefix = ctx.setOperand(new TACLiteral(tree, ctx.value));
		
		return null;
	}
	
	@Override public Void visitArguments(ShadowParser.ArgumentsContext ctx)
	{ 
		visitChildren(ctx);		
		for( ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression() ) 
			child.appendBefore(tree);		
		//no operand saved because the operands are in the children
		
		return null;
	}
	
	@Override public Void visitArrayCreate(ShadowParser.ArrayCreateContext ctx)
	{ 
		visitChildren(ctx);
		
		ShadowParser.ArrayDimensionsContext dimensions = ctx.arrayDimensions();
		dimensions.appendBefore(tree);		
			
		ArrayType arrayType = (ArrayType)ctx.getType();
		Type type = arrayType.getBaseType();
		TACClass baseClass = new TACClass(tree, type);		
		List<TACOperand> indices = new ArrayList<TACOperand>(dimensions.conditionalExpression().size());
		for( ShadowParser.ConditionalExpressionContext dimension : dimensions.conditionalExpression() )
			indices.add(dimension.getOperand());
		
		if( ctx.arrayCreateCall() != null ) {	
			MethodSignature create = ctx.getSignature();
			ctx.arrayCreateCall().appendBefore(tree);
			List<TACOperand> arguments = new ArrayList<TACOperand>(ctx.arrayCreateCall().conditionalExpression().size());
			for( ShadowParser.ConditionalExpressionContext child : ctx.arrayCreateCall().conditionalExpression() )
				arguments.add(child.getOperand());
			prefix = visitArrayAllocation(arrayType, baseClass, indices, create, arguments);
		}
		else if( ctx.arrayDefault() != null ) {
			TACOperand value = ctx.arrayDefault().appendBefore(tree);
			prefix = visitArrayAllocation(arrayType, baseClass, indices, value);
		}
		else //nullable array only
			prefix = visitArrayAllocation(arrayType, baseClass, indices);
		
		ctx.setOperand(prefix);
		
		return null;
	}
	
	private TACOperand callCreate(MethodSignature signature, List<TACOperand> params, Type prefixType) {
		TACOperand object = new TACNewObject(tree, prefixType);					
				
		params.add(0, object); //put object in front of other things
		
		//have to pass a reference to outer classes into constructor
		if( !(prefixType instanceof TypeParameter) && prefixType.hasOuter() ) {				
			Type methodOuter = prefixType.getOuter().getTypeWithoutTypeArguments();
			
			if( prefix != null && prefix.getType().isSubtype(methodOuter) )
				params.add(1, prefix); //after object, before args
			else {					
				 TACOperand outer = new TACLocalLoad(tree, method.getThis());
				 Type outerType = outer.getType().getTypeWithoutTypeArguments();					 
				 while( !outerType.isSubtype(methodOuter) ) {
					outer = new TACLoad(tree, new TACFieldRef(outer, new SimpleModifiedType(outerType.getOuter()),
							"_outer"));						
					outerType = outerType.getOuter();				
				 }					 
				 params.add(1, outer); //after object, before args
			}
		}		

		TACMethodRef methodRef;		
		if( signature.getOuter() instanceof InterfaceType )
			methodRef = new TACMethodRef(tree, TACCast.cast(tree, new SimpleModifiedType(signature.getOuter()), object), signature);
		else
			methodRef = new TACMethodRef(tree, signature);
				
		prefix = new TACCall(tree, methodRef, params);
				
		//sometimes a cast is needed when dealing with generic types
		if( !prefix.getType().equals(prefixType) )
			prefix = TACCast.cast(tree, new SimpleModifiedType(prefixType), prefix);
		
		return prefix;
	}
	
	@Override public Void visitCreate(ShadowParser.CreateContext ctx)
	{ 
		visitChildren(ctx);		
		
		List<TACOperand> params = new ArrayList<TACOperand>(ctx.conditionalExpression().size() + 1);		
		for( ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression())
				params.add(child.appendBefore(tree));			
		
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
		context.appendBefore(tree);
		
		return null;
	}
	
	@Override public Void visitAssertStatement(ShadowParser.AssertStatementContext ctx)
	{ 
		visitChildren(ctx);
		
		TACLabel doneLabel = new TACLabel(method),
				errorLabel = new TACLabel(method);
		
		TACOperand condition = ctx.conditionalExpression(0).appendBefore(tree); 
		
		new TACBranch(tree, condition, doneLabel, errorLabel);			
		errorLabel.insertBefore(tree);
		
		TACOperand object = new TACNewObject(tree, Type.ASSERT_EXCEPTION);
		List<TACOperand> params = new ArrayList<TACOperand>();
		params.add(object);			
		MethodSignature signature;			
		
		if( ctx.conditionalExpression().size() > 1 ) { // has message
			TACOperand message = convertToString( ctx.conditionalExpression(1).appendBefore(tree) );
			signature = Type.ASSERT_EXCEPTION.getMatchingMethod("create", new SequenceType(message));
			params.add( message );				
		}
		else
			signature = Type.ASSERT_EXCEPTION.getMatchingMethod("create", new SequenceType());

		TACCall exception = new TACCall(tree, new TACMethodRef(tree, signature), params);
		new TACThrow(tree, exception);
		doneLabel.insertBefore(tree);		
		
		return null;
	}
	
	@Override public Void visitBlock(ShadowParser.BlockContext ctx)
	{ 
		method.enterScope();
		visitChildren(ctx); 
		
		for( ShadowParser.BlockStatementContext statement : ctx.blockStatement() )
			statement.appendBefore(tree);
		
		method.exitScope();		
		return null;
	}
	
	@Override public Void visitCreateBlock(ShadowParser.CreateBlockContext ctx)
	{ 
		method.enterScope();
		visitChildren(ctx); 
		
		if( ctx.explicitCreateInvocation() != null )
			ctx.explicitCreateInvocation().appendBefore(tree);
		for( ShadowParser.BlockStatementContext statement : ctx.blockStatement() )
			statement.appendBefore(tree);
		
		method.exitScope();		
		return null;
	}
	
	@Override public Void visitBlockStatement(ShadowParser.BlockStatementContext ctx)
	{ 
		visitChildren(ctx);
		if( ctx.localDeclaration() != null )
			ctx.localDeclaration().appendBefore(tree);
		else
			ctx.statement().appendBefore(tree);
		
		return null;
	}
	
	@Override public Void visitLocalDeclaration(ShadowParser.LocalDeclarationContext ctx)
	{ 
		visitChildren(ctx);
		
		if( ctx.localMethodDeclaration() != null )
			ctx.localMethodDeclaration().appendBefore(tree);
		else
			ctx.localVariableDeclaration().appendBefore(tree);
		
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
			child.appendBefore(tree);
		
		return null;
	}
	
	@Override public Void visitStatementExpression(ShadowParser.StatementExpressionContext ctx)
	{ 
		visitChildren(ctx);
	
		if( ctx.sequenceAssignment() != null )
			ctx.sequenceAssignment().appendBefore(tree);
		else
			ctx.expression().appendBefore(tree);
		
		return null;
	}
	
	//private void doAssignment(TACOperand left, TACOperand right, char operation, OperationNode node ) {
	@Override public Void visitSequenceAssignment(ShadowParser.SequenceAssignmentContext ctx)
	{ 
		visitChildren(ctx);
	
		ShadowParser.RightSideContext rightSide = ctx.rightSide();
		ShadowParser.SequenceLeftSideContext leftSide = ctx.sequenceLeftSide();
		
		//add computation of all left values
		leftSide.appendBefore(tree);
		//add computation of all right values
		rightSide.appendBefore(tree);		
		
		List<TACOperand> left = new ArrayList<TACOperand>();
		for( ParseTree child : leftSide.children)
			if( child instanceof Context )
				left.add(((Context) child).getOperand());	
		
		SequenceType sequence = (SequenceType)leftSide.getType();								
		//create splat
		if( !(rightSide.getType() instanceof SequenceType)  ) {				
			TACOperand right = rightSide.getOperand();
			TACVariable temporary = method.addTempLocal(rightSide);	
			new TACLocalStore(tree, temporary, right);

			int index = 0;
			for( int i = 0; i < sequence.size(); ++i )
				if( sequence.get(i) != null ) {									
					doAssignment(left.get(index), (Context)sequence.get(i), new TACLocalLoad(tree, temporary), "=", null);
					index++;
				}				
		}
		else if( rightSide.sequenceRightSide() != null ){ //sequence on the right			
			int index = 0;			
			for( int i = 0; i < sequence.size(); ++i ) //sequence size must match right hand size
				if( sequence.get(i) != null ) {										
					doAssignment(left.get(index), (Context)sequence.get(i),
							rightSide.sequenceRightSide().conditionalExpression(i).getOperand(), "=", null);
					index++;
				}

		}
		else { //method call on the right whose output must be broken into parts			
			int index = 0;
			for( int i = 0; i < sequence.size(); ++i )
				if( sequence.get(i) != null ) {								
					doAssignment(left.get(index), (Context)sequence.get(i), new TACSequenceElement(tree, rightSide.getOperand(), i), "=", null);
					index++;
				}		
		}
		
		return null;
	}
	
	@Override public Void visitSwitchStatement(ShadowParser.SwitchStatementContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand value = ctx.conditionalExpression().appendBefore(tree);
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
					label.appendBefore(tree); //append (all) label conditions
					TACLabel matchingCase = new TACLabel(method);
					labels.add(matchingCase);
					
					for( int j = 0; j < label.primaryExpression().size(); ++j ) {
						TACOperand operand = label.primaryExpression(j).getOperand();
						TACOperand comparison;
						MethodSignature signature = type.getMatchingMethod("equal", new SequenceType(operand));
						
						if( type.isPrimitive() && signature.getModifiers().isNative() )
							comparison = new TACBinary(tree, value, operand); //equivalent to ===
						else								
							comparison = new TACCall(tree, new TACMethodRef(tree, value, signature), value, operand);
													
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
						
						new TACBranch(tree, comparison, matchingCase, next);
						
						if( moreConditions )
							next.insertBefore(tree);							
					}
				}
			}
			
			if( ctx.hasDefault && ctx.switchLabel().size() == 1 ) //only default exists, needs a direct jump
				new TACBranch(tree, defaultLabel);
			
			//then go through and add the executable blocks of code to jump to
			for( int i = 0; i < ctx.statement().size(); ++i ) {				
				labels.get(i).insertBefore(tree); //mark start of code				
				ctx.statement(i).appendBefore(tree); //add block of code (the child after each label)
				new TACBranch(tree, doneLabel);	
			}
			
			doneLabel.insertBefore(tree);
		}
		else
			throw new UnsupportedOperationException();	
		
		return null;
	}
	
	@Override public Void visitSwitchLabel(ShadowParser.SwitchLabelContext ctx)	
	{ 
		visitChildren(ctx);
		
		for(ShadowParser.PrimaryExpressionContext child : ctx.primaryExpression() )
			child.appendBefore(tree);
		
		return null;
	}
	
	@Override public Void visitIfStatement(ShadowParser.IfStatementContext ctx)
	{ 
		visitChildren(ctx);
		
		TACLabel trueLabel = new TACLabel(method),
				falseLabel = new TACLabel(method),
				endLabel = new TACLabel(method);
		
		new TACBranch(tree, ctx.conditionalExpression().appendBefore(tree), trueLabel, falseLabel);
		trueLabel.insertBefore(tree);
		ctx.statement(0).appendBefore(tree);
		new TACBranch(tree, endLabel);
		falseLabel.insertBefore(tree);		
		
		if( ctx.statement().size() > 1 ) //else case			
			ctx.statement(1).appendBefore(tree);
		
		new TACBranch(tree, endLabel);		
		endLabel.insertBefore(tree);
		
		return null;
	}

	@Override public Void visitWhileStatement(ShadowParser.WhileStatementContext ctx)
	{
		block = new TACBlock(tree, block).addBreak().addContinue();
		visitChildren(ctx);
		
		TACLabel bodyLabel = new TACLabel(method),
				conditionLabel = block.getContinue(),
				endLabel = block.getBreak();
		new TACBranch(tree, conditionLabel);
		
		//body
		bodyLabel.insertBefore(tree);
		ctx.statement().appendBefore(tree);
		new TACBranch(tree, conditionLabel);
		
		//condition
		conditionLabel.insertBefore(tree);
		TACOperand condition = ctx.conditionalExpression().appendBefore(tree);		
		new TACBranch(tree, condition, bodyLabel, endLabel);
		
		endLabel.insertBefore(tree);
		block = block.getParent();	
		
		return null;
	}
	
	@Override public Void visitDoStatement(ShadowParser.DoStatementContext ctx)
	{ 
		block = new TACBlock(tree, block).addBreak().addContinue();
		visitChildren(ctx);
		
		TACLabel bodyLabel = new TACLabel(method),
				conditionLabel = block.getContinue(),
				endLabel = block.getBreak();
		//only difference from while is this jump to body instead of condition
		new TACBranch(tree, bodyLabel); 
		
		//body
		bodyLabel.insertBefore(tree);
		ctx.statement().appendBefore(tree);
		new TACBranch(tree, conditionLabel);
		
		//condition
		conditionLabel.insertBefore(tree);
		TACOperand condition = ctx.conditionalExpression().appendBefore(tree);		
		new TACBranch(tree, condition, bodyLabel, endLabel);
		
		endLabel.insertBefore(tree);
		block = block.getParent();	
		
		return null;
	}
	
	@Override public Void visitForeachStatement(ShadowParser.ForeachStatementContext ctx)
	{ 
		block = new TACBlock(tree, block).addBreak().addContinue();
		method.enterScope(); //needed because a variable is declared in a foreach
		visitChildren(ctx);
		
		ShadowParser.ForeachInitContext init = ctx.foreachInit();		
		Type type = init.conditionalExpression().getType(); 		
		TACOperand collection = init.appendBefore(tree);
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
			new TACLocalStore(tree, iterator, new TACLiteral(tree, new ShadowInteger(0)));
			TACOperand length = new TACLength(tree, collection, 0);			
			for (int i = 1; i < arrayType.getDimensions(); i++)
				length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), "*", new TACLength(tree, collection, i), false);			
			
			new TACBranch(tree, conditionLabel);  //init is done, jump to condition
			
			//body
			bodyLabel.insertBefore(tree);
			
			//store array element into local variable before executing body
			value = new TACLoad(tree, new TACArrayRef(tree, collection, new TACLocalLoad(tree, iterator), false));
			new TACLocalStore(tree, variable, value);
			
			ctx.statement().appendBefore(tree); //body
			
			new TACBranch(tree, updateLabel);
			updateLabel.insertBefore(tree);			
			
			//increment iterator
			value = new TACLocalLoad(tree, iterator);					
			value = new TACBinary(tree, value, Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+", new TACLiteral(tree, new ShadowInteger(1)), false );
			new TACLocalStore(tree, iterator, value);	
			
			new TACBranch(tree, conditionLabel);
			conditionLabel.insertBefore(tree);
			
			//check if iterator < array length
			value = new TACLocalLoad(tree, iterator);			
			condition = new TACBinary(tree, value, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), "<", length, true );
			
			new TACBranch(tree, condition, bodyLabel, endLabel);		
			endLabel.insertBefore(tree);
		}
		else {	
			TACLabel conditionLabel = block.getContinue();
			MethodSignature signature;
			
			//get iterator
			signature = type.getMatchingMethod("iterator", new SequenceType());
			ModifiedType iteratorType = signature.getReturnTypes().get(0);
			iterator = method.addTempLocal(iteratorType);
			TACMethodRef getIterator = new TACMethodRef(tree, collection, signature);
			new TACLocalStore(tree, iterator, new TACCall(tree, getIterator, getIterator.getPrefix()));
			
			new TACBranch(tree, conditionLabel);  //init is done, jump to condition
						
			bodyLabel.insertBefore(tree);		
			
			//put variable update before main body
			signature = iteratorType.getType().getMatchingMethod("next", new SequenceType());
			TACMethodRef next = new TACMethodRef(tree, new TACLocalLoad(tree, iterator), signature);
			new TACLocalStore(tree, variable, new TACCall(tree, next, new TACLocalLoad(tree, iterator))); //internally updates iterator
			
			ctx.statement().appendBefore(tree); //body
						
			new TACBranch(tree, conditionLabel);
			conditionLabel.insertBefore(tree);
			
			//check if iterator has next
			signature = iteratorType.getType().getMatchingMethod("hasNext", new SequenceType());
			TACMethodRef hasNext = new TACMethodRef(tree, new TACLocalLoad(tree, iterator), signature);
			condition = new TACCall(tree, hasNext, new TACLocalLoad(tree, iterator));
			
			new TACBranch(tree, condition, bodyLabel, endLabel);		
			endLabel.insertBefore(tree);
		}			

		method.exitScope();
		block = block.getParent();	
		
		return null;		
	}
	
	@Override public Void visitForStatement(ShadowParser.ForStatementContext ctx)
	{ 
		block = new TACBlock(tree, block).addBreak().addContinue();
		method.enterScope(); //needed because a variable can be declared in a for
		visitChildren(ctx);		
		
		if( ctx.forInit() != null )
			ctx.forInit().appendBefore(tree);
		
		TACLabel bodyLabel = new TACLabel(method),
				 conditionLabel = block.getContinue(),
				 updateLabel = (ctx.forUpdate() != null) ?
						new TACLabel(method) : null,
				 endLabel = block.getBreak();						
		//branch to condition
		new TACBranch(tree, conditionLabel);
		
		//body
		bodyLabel.insertBefore(tree);
		ctx.statement().appendBefore(tree);
		
		//update (if exists)
		if( ctx.forUpdate() != null ) {
			new TACBranch(tree, updateLabel);
			updateLabel.insertBefore(tree);
			ctx.forUpdate().appendBefore(tree);
		}
		
		//condition
		new TACBranch(tree, conditionLabel);
		conditionLabel.insertBefore(tree);
		new TACBranch(tree, ctx.conditionalExpression().appendBefore(tree), bodyLabel, endLabel);
		
		endLabel.insertBefore(tree);
		method.exitScope();
		block = block.getParent();	
		
		return null;		
	}
	
	@Override public Void visitForInit(ShadowParser.ForInitContext ctx)
	{ 
		visitChildren(ctx);
		
		if( ctx.localVariableDeclaration() != null )
			ctx.localVariableDeclaration().appendBefore(tree);
		else
			ctx.statementExpressionList().appendBefore(tree);
		
		return null;
	}
	
	@Override public Void visitStatementExpressionList(ShadowParser.StatementExpressionListContext ctx)
	{ 
		visitChildren(ctx);
		
		for( ShadowParser.StatementExpressionContext child : ctx.statementExpression() )
			child.appendBefore(tree);
		
		return null;	
	}
	
	@Override public Void visitForUpdate(ShadowParser.ForUpdateContext ctx)
	{ 
		visitChildren(ctx);
		ctx.statementExpressionList().appendBefore(tree);
		
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
		
		unreachableLabel.insertBefore(tree);
		
		return null;	
	}
	
	@Override public Void visitReturnStatement(ShadowParser.ReturnStatementContext ctx)
	{ 
		visitChildren(ctx);
		TACLabel unreachableLabel = new TACLabel(method);
		visitCleanup(null, null);
		
		if( method.getSignature().isCreate() ) {				
			new TACReturn(tree, method.getSignature().getFullReturnTypes(),
					new TACLocalLoad(tree, method.getThis()));
			unreachableLabel.insertBefore(tree);
		}
		else {			
			if( ctx.rightSide() != null )
				new TACReturn(tree, (SequenceType)ctx.getType(), ctx.rightSide().appendBefore(tree));
			else
				new TACReturn(tree, new SequenceType());
			unreachableLabel.insertBefore(tree);
		}
		
		return null;
	}
	
	@Override public Void visitThrowStatement(ShadowParser.ThrowStatementContext ctx)
	{ 
		visitChildren(ctx);
		TACLabel unreachableLabel = new TACLabel(method);
		new TACThrow(tree, ctx.conditionalExpression().appendBefore(tree));
		unreachableLabel.insertBefore(tree);
		
		return null;
	}
	
	@Override public Void visitFinallyStatement(ShadowParser.FinallyStatementContext ctx)
	{ 
		block = new TACBlock(tree, block).addDone();
		visitChildren(ctx);
		
		ctx.recoverStatement().appendBefore(tree);
		if( ctx.block() != null ) {
			TACPhi phi = (TACPhi)tree.getPrevious(); //last thing before the anchor is a phi
			ctx.block().appendBefore(tree);
			new TACBranch(tree, phi);				
		}
		block.getDone().insertBefore(tree);
		block = block.getParent();	
		
		return null;	
	}
	
	@Override public Void visitRecoverStatement(ShadowParser.RecoverStatementContext ctx)
	{
		ShadowParser.FinallyStatementContext parent = (FinallyStatementContext) ctx.getParent();
		if( parent.block() != null ) //parent has finally
			block = new TACBlock(tree, block).addLandingpad().addUnwind().
			addCleanup().addDone();
		
		visitChildren(ctx);
		
		ctx.catchStatements().appendBefore(tree);
		if( ctx.block() != null ) { //has recover
			ctx.block().appendBefore(tree);
			new TACBranch(tree, block.getDone());
		}
		if ( parent.block() != null ) { //parent has finally
			block.getDone().insertBefore(tree);
			method.setHasLandingpad();
			visitCleanup( block.getParent(), block.getDone(),
					block.getParent().getDone() );
			block.getLandingpad().insertBefore(tree);
			TACVariable exception = method.getLocal("_exception");
			new TACLocalStore(tree, exception, new TACLandingpad(tree, block));
			new TACBranch(tree, block.getUnwind());
			block.getUnwind().insertBefore(tree);
			TACLabel continueUnwind = block.getParent().getUnwind();
			if (continueUnwind != null)
				visitCleanup(block, block.getUnwind(), continueUnwind);
			else {
				visitCleanup(block, block.getUnwind());
				new TACResume(tree, new TACLocalLoad(tree, exception));
			}
			block.getCleanup().insertBefore(tree);
			block.getCleanupPhi().insertBefore(tree);
			block = block.getParent();				
		}
		
		return null;
	}

	
	@Override public Void visitCatchStatements(ShadowParser.CatchStatementsContext ctx)
	{ 
		ShadowParser.RecoverStatementContext parent = (RecoverStatementContext) ctx.getParent();
		
		block = new TACBlock(tree, block);
		if( parent.block() != null ) //parent has recover
			block.addRecover();
		
		block.addCatches(ctx.catchStatement().size());
		
		visitChildren(ctx);
		
		ctx.tryStatement().appendBefore(tree); //appends try block			
		if( ctx.catchStatement().size() > 0 ) {			
			TACOperand typeid = new TACSequenceElement(tree, new TACLocalLoad(tree, method.getLocal("_exception")), 1 );
			for( int i = 0; i < ctx.catchStatement().size(); ++i ) {
				ShadowParser.CatchStatementContext child = ctx.catchStatement(i);
				Type type = child.formalParameter().getType();
				TACLabel catchLabel = block.getCatch(i),
						skip = new TACLabel(method);

				new TACBranch(tree, new TACBinary(tree, typeid, new TACTypeId(tree, new TACClass(tree, type).getClassData())),
						catchLabel, skip);
						
				catchLabel.insertBefore(tree);
				child.appendBefore(tree); //append catch i
				new TACBranch(tree, block.getDone());
				skip.insertBefore(tree);
			}
			
			TACLabel continueUnwind = block.getUnwind();
			if (continueUnwind != null)
				new TACBranch(tree, continueUnwind); //try inside of try					
			else
				new TACResume(tree, new TACLocalLoad(tree, method.getLocal("_exception")));
		}			

		if( parent.block() != null ) //parent has recover			
			block.getRecover().insertBefore(tree);
		
		block = block.getParent();	
		
		return null;
	}
	
	@Override public Void visitCatchStatement(ShadowParser.CatchStatementContext ctx)	
	{ 
		method.enterScope();
		visitChildren(ctx); 
		
		ShadowParser.FormalParameterContext parameter = ctx.formalParameter();
		
		parameter.appendBefore(tree);
							
		new TACLocalStore(tree, method.getLocal(
				parameter.Identifier().getText()), new TACCatch(tree,
				(ExceptionType)parameter.getType(), new TACLocalLoad(tree, method.getLocal("_exception"))));
		ctx.block().appendBefore(tree);
		method.exitScope();
		
		return null;
	}
	
	@Override public Void visitTryStatement(ShadowParser.TryStatementContext ctx)
	{ 
		ShadowParser.CatchStatementsContext parent = (CatchStatementsContext) ctx.getParent();
		if( parent.catchStatement().size() > 0 )
			block = new TACBlock(tree, block).addLandingpad().addUnwind();
		
		visitChildren(ctx); 
		
		ctx.block().appendBefore(tree);
		new TACBranch(tree, block.getDone());
		if( parent.catchStatement().size() > 0 ) {
			method.setHasLandingpad();
			block.getLandingpad().insertBefore(tree);
			TACVariable exception = method.getLocal("_exception");			
			new TACLocalStore(tree, exception, new TACLandingpad(tree, block));
			new TACBranch(tree, block.getUnwind());
			block.getUnwind().insertBefore(tree);
			block = block.getParent();				
		}
		
		return null;
	}	

	private void visitCleanup(TACBlock lastBlock, TACLabel currentLabel) {
		if (lastBlock != null)
			lastBlock = lastBlock.getParent();
		TACBlock currentBlock = block.getCleanupBlock(lastBlock);
		if (currentBlock != lastBlock) {
			TACLabel lastLabel = new TACLabel(method);
			visitCleanup(currentBlock, lastBlock, currentLabel, lastLabel);
			lastLabel.insertBefore(tree);
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
			new TACBranch(tree, lastLabel);
	}
	
	private void visitCleanup(TACBlock currentBlock, TACBlock lastBlock,
			TACLabel currentLabel, TACLabel lastLabel) {
		if (currentLabel == null) {
			currentLabel = new TACLabel(method);
			new TACBranch(tree, currentLabel);
			currentLabel.insertBefore(tree);
		}
		TACBlock nextBlock;
		while ((nextBlock = currentBlock.getNextCleanupBlock(lastBlock)) !=
				lastBlock) {
			TACLabel nextLabel = new TACLabel(method);
			new TACBranch(tree, currentBlock.getCleanup());
			//currentBlock.getCleanupPhi().addEdge(nextLabel, currentLabel);
			currentBlock.getCleanupPhi().addPreviousStore(currentLabel, new TACLabelAddress(tree, nextLabel, method) );
			nextLabel.insertBefore(tree);
			currentBlock = nextBlock;
			currentLabel = nextLabel;
		}
		new TACBranch(tree, currentBlock.getCleanup());
		//currentBlock.getCleanupPhi().addEdge(lastLabel, currentLabel);
		currentBlock.getCleanupPhi().addPreviousStore(currentLabel, new TACLabelAddress(tree, lastLabel, method) );
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
			copyMethod = new TACMethodRef(tree, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
		else
			copyMethod = new TACMethodRef(tree, baseType.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)) );
	
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
			TACOperand length = new TACLength(tree, oldArray, 0);
			TACOperand[] dimensions = new TACOperand[arrayType.getDimensions()];
			dimensions[0] = length;
			for (int j = 1; j < arrayType.getDimensions(); j++) {
				dimensions[j] = new TACLength(tree, oldArray, j);
				length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), "*", dimensions[j]);
			}
			
			TACClass class_ = new TACClass(tree, type);
			TACVariable copiedArray = method.addTempLocal(new SimpleModifiedType(arrayType));
			new TACLocalStore(tree, copiedArray, new TACNewArray(tree, arrayType, class_.getClassData(), dimensions));
			
			if( i == 0 )
				returnValue = new TACLocalLoad(tree, copiedArray);
			else {
				//store current array into its spot in the previous array
				TACArrayRef location = new TACArrayRef(tree, new TACLocalLoad(tree, previousArray), new TACLocalLoad(tree, counters[i - 1]), false);
				new TACStore(tree, location, new TACLocalLoad(tree, copiedArray));
			}
			
			previousArray = copiedArray;
			
			if( i == layers - 1 ) { //last layer is either objects or primitives, not more arrays
				TACLabel terminate;					
				if( i == 0 )
					terminate = done;
				else
					terminate = labels[i - 1];
				
				if( type.isPrimitive() ) {
					TACMethodRef width = new TACMethodRef(tree, Type.CLASS.getMatchingMethod("width", new SequenceType()) );
					TACOperand size = new TACBinary(tree, new TACCall(tree, width, class_.getClassData()), Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), "*", length);
					new TACCopyMemory(tree, new TACLocalLoad(tree, copiedArray), oldArray, size);					
					new TACBranch(tree, terminate);		
				}
				else {					
					counters[i] = method.addTempLocal(new SimpleModifiedType(Type.INT));
					new TACLocalStore(tree, counters[i], new TACLiteral(tree, new ShadowInteger(-1))); //starting at -1 allows update and check to happen on the same label
					labels[i] = new TACLabel(method);
					
					new TACBranch(tree, labels[i]);
					labels[i].insertBefore(tree);					
					TACLabel body = new TACLabel(method);					

					//increment counters[i] by 1
					new TACLocalStore(tree, counters[i], new TACBinary(tree, new TACLocalLoad(tree, counters[i]), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+", new TACLiteral(tree, new ShadowInteger(1))));
					TACOperand condition = new TACBinary(tree, new TACLocalLoad(tree, counters[i]), Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), "<", length, true);
					new TACBranch(tree, condition, body, terminate);					
					body.insertBefore(tree);
					
					//copy old value into new location
					TACOperand element = new TACLoad(tree, new TACArrayRef(tree, oldArray, new TACLocalLoad(tree, counters[i]), false)); //get value at location
					
					if( baseType instanceof InterfaceType )
						element = TACCast.cast(tree, new SimpleModifiedType(Type.OBJECT), element);
										
					TACLabel copyLabel = new TACLabel(method);
					TACOperand nullCondition = new TACBinary(tree, element, new TACLiteral(tree, new ShadowNull(element.getType())));
					new TACBranch(tree, nullCondition, labels[i], copyLabel); //if null, skip entirely, since arrays are calloc'ed
					
					copyLabel.insertBefore(tree);
					
					TACOperand copiedElement = new TACCall(tree, copyMethod, element, map);
					
					if( baseType instanceof InterfaceType )
						copiedElement = TACCast.cast(tree, new SimpleModifiedType(baseType), copiedElement);
					
					TACArrayRef newElement = new TACArrayRef(tree, new TACLocalLoad(tree, copiedArray), new TACLocalLoad(tree, counters[i]), false);
					new TACStore(tree, newElement, copiedElement);

					//go back to update and check condition
					new TACBranch(tree, labels[i]);					
				}
			}
			else {
				counters[i] = method.addTempLocal(new SimpleModifiedType(Type.INT));
				new TACLocalStore(tree, counters[i], new TACLiteral(tree, new ShadowInteger(-1)));//starting at -1 allows update and check to happen on the same label
				labels[i] = new TACLabel(method);
				
				new TACBranch(tree, labels[i]);
				labels[i].insertBefore(tree);	
				
				TACLabel terminate;
				TACLabel body = new TACLabel(method);
				if( i == 0 )
					terminate = done;
				else
					terminate = labels[i - 1];

				//increment counters[i] by 1
				new TACLocalStore(tree, counters[i], new TACBinary(tree, new TACLocalLoad(tree, counters[i]), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+", new TACLiteral(tree, new ShadowInteger(1))));
				TACOperand condition = new TACBinary(tree, new TACLocalLoad(tree, counters[i]), Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), "<", length, true);
				new TACBranch(tree, condition, body, terminate);					
				body.insertBefore(tree);
				
				//set up next round
				oldArray = new TACLoad(tree, new TACArrayRef(tree, oldArray, new TACLocalLoad(tree, counters[i]), false)); //get value at location
			}
		}
		
		done.insertBefore(tree);		
		return returnValue;
	}

	private void visitMethod(MethodSignature methodSignature) {
		TACNode saveTree = tree;
		TACMethod method = this.method = new TACMethod(methodSignature);
		boolean implicitCreate = false;
		if( moduleStack.peek().isClass() && !methodSignature.isNative() ) {
			
			
			/*if (methodSignature.isNative()) {
				method.addParameters(tree);
				
				//MethodDeclarationContext
				//CreateDeclarationContext
				//DestroyDeclarationContext
				//LocalMethodDeclarationContext 
				//InlineMethodDefinitionContext
				//walk(methodSignature.getNode().jjtGetChild(0).jjtGetChild(0));
				//Does this do nothing?
			}*/ //do nothing if native?			
		
			block = new TACBlock(method);
			//tree = new TACTree(1, block);
			tree = new TACDummyNode(null, block);
			
			if( methodSignature.getSymbol().equals("copy") && !methodSignature.isWrapper() ) {
				ClassType type = (ClassType) methodSignature.getOuter();
				method.addParameters(tree); //address map called "addresses"				
				
				if( type.getModifiers().isImmutable() ) {				
					//for now, just return this
					new TACReturn(tree, methodSignature.getFullReturnTypes(), new TACLocalLoad(
							tree, method.getThis()));
				}			
				else {					
					TACOperand this_ = new TACLocalLoad(tree, method.getThis());
					TACOperand address = new TACPointerToLong(tree, this_);					
					
					TACOperand map = new TACLocalLoad(tree, method.getParameter("addresses"));
					TACMethodRef indexMethod = new TACMethodRef(tree, Type.ADDRESS_MAP.getMatchingMethod("containsKey", new SequenceType(Type.ULONG)) );
					TACOperand test = new TACCall(tree, indexMethod, map, address );
					
					TACLabel copyLabel = new TACLabel(method),
							returnLabel = new TACLabel(method);
					
					new TACBranch(tree, test, returnLabel, copyLabel);
					copyLabel.insertBefore(tree);
					
					//allocate a new object
					TACNewObject object = new TACNewObject(tree, type);
					TACOperand duplicate;					
					
					//add it to the map of addresses
					SequenceType arguments = new SequenceType();
					arguments.add(new SimpleModifiedType(Type.ULONG));  //key
					arguments.add(new SimpleModifiedType(Type.ULONG));  //value
					indexMethod = new TACMethodRef(tree, Type.ADDRESS_MAP.getMatchingMethod("index", arguments) );
					TACOperand newAddress = new TACPointerToLong(tree, object);					
					new TACCall(tree, indexMethod, map, address, newAddress);					
					
					if( type.getTypeWithoutTypeArguments().equals(Type.ARRAY) || type.getTypeWithoutTypeArguments().equals(Type.ARRAY_NULLABLE) ) {
						Type genericArray = type.getTypeWithoutTypeArguments();
						
						//call private create to allocate space
						TACMethodRef create = new TACMethodRef(tree, genericArray.getMatchingMethod("create", new SequenceType(new SimpleModifiedType( new ArrayType(Type.INT), new Modifiers(Modifiers.IMMUTABLE)))));
						TACOperand lengths = new TACLoad(tree, new TACFieldRef(this_, "lengths" ));
						duplicate = new TACCall(tree, create, object, lengths); //performs cast to Array as well
						
						//get size (product of all dimension lengths)
						TACMethodRef sizeMethod = new TACMethodRef(tree, genericArray.getMatchingMethod("size", new SequenceType()));					
						TACOperand size = new TACCall(tree, sizeMethod, this_);
						TACLabel done = new TACLabel(method);
						TACLabel body = new TACLabel(method);
						TACLabel condition = new TACLabel(method);
						
						TACVariable i = method.addTempLocal(new SimpleModifiedType(Type.INT));
						new TACLocalStore(tree, i, new TACLiteral(tree, new ShadowInteger(0)));
						new TACBranch(tree, condition);
						
						//start loop
						condition.insertBefore(tree);
						
						TACOperand loop = new TACBinary(tree, new TACLocalLoad(tree, i), Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), "<", size, true );
						new TACBranch(tree, loop, body, done);
						body.insertBefore(tree);
						
						SequenceType indexArguments = new SequenceType();
						indexArguments.add(i);					
						TACMethodRef indexLoad = new TACMethodRef(tree, genericArray.getMatchingMethod("index", indexArguments));
						indexArguments.add(genericArray.getTypeParameters().get(0));
						TACMethodRef indexStore = new TACMethodRef(tree, genericArray.getMatchingMethod("index", indexArguments));
						
						
						TACOperand value = new TACCall(tree, indexLoad, this_, new TACLocalLoad(tree, i));
						
						TACLabel skipLabel = new TACLabel(method);
						TACLabel makeCopyLabel = new TACLabel(method);
						TACOperand isNull = new TACBinary(tree, value, new TACLiteral(tree, new ShadowNull(value.getType())));
						new TACBranch(tree, isNull, skipLabel, makeCopyLabel);
						
						makeCopyLabel.insertBefore(tree);
						
						TACMethodRef copy = new TACMethodRef(tree, value, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
						
						value = new TACCall(tree, copy, copy.getPrefix(), map);
						new TACCall(tree, indexStore, duplicate, new TACLocalLoad(tree, i), value);						
						new TACBranch(tree, skipLabel);
						
						skipLabel.insertBefore(tree);						
						
						new TACLocalStore(tree, i, new TACBinary(tree, new TACLocalLoad(tree, i), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+", new TACLiteral(tree, new ShadowInteger(1)), false ));
						new TACBranch(tree, condition);					
						
						done.insertBefore(tree);
					}
					else {
						//perform a memcopy to sweep up all the primitives and immutable data (and nulls)
						TACOperand size = new TACLoad(tree, new TACFieldRef(object.getClassData(), "size"));
						new TACCopyMemory(tree, object, this_, size);
						
						if( type.equals(Type.OBJECT))
							duplicate = object;
						else
							duplicate = TACCast.cast(tree, new SimpleModifiedType(type), object); //casts object to type
						
						//copy other fields in using their copy methods
						//arrays need special attention
						TACMethodRef copyMethod;
						TACOperand field;
						TACFieldRef newField;
						TACOperand copiedField;
						for( Entry<String, ? extends ModifiedType> entry : type.orderAllFields() ) {
							ModifiedType entryType = entry.getValue();
							//only copy mutable, non-primitive types and non-singletons
							if( !entryType.getModifiers().isImmutable() &&
								!entryType.getType().getModifiers().isImmutable() &&
								!entryType.getType().isPrimitive() &&
								!(entryType.getType() instanceof SingletonType)) {							
								//get field references
								field = new TACLoad(tree, new TACFieldRef(this_, entryType, entry.getKey()));
								newField = new TACFieldRef(duplicate, entryType, entry.getKey());
								
								TACLabel copyField = new TACLabel(method);
								TACLabel skipField = new TACLabel(method);
								
								//TODO: add something special for Singletons, for thread safety
								if( entryType.getType() instanceof ArrayType )
									copiedField = copyArray(field, map);								
								else {	
									if( entryType.getType() instanceof InterfaceType ) {
										//cast converts from interface to object
										field = TACCast.cast(tree, new SimpleModifiedType(Type.OBJECT), field);
										copyMethod = new TACMethodRef(tree, field, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
									}
									else //normal object
										copyMethod = new TACMethodRef(tree, field, entryType.getType().getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
									
									TACOperand nullCondition = new TACBinary(tree, field, new TACLiteral(tree, new ShadowNull(field.getType())));
									new TACBranch(tree, nullCondition, skipField, copyField); //if null, skip

									copyField.insertBefore(tree);
									copiedField = new TACCall(tree, copyMethod, field, map);

									if( entryType.getType() instanceof InterfaceType )
										//and then cast back to interface
										copiedField = TACCast.cast(tree, newField, copiedField);																
								}
								
								//store copied value
								new TACStore(tree, newField, copiedField);		
								new TACBranch(tree, skipField);								
								
								skipField.insertBefore(tree);
							}
						}
					}
					
					new TACReturn(tree, methodSignature.getFullReturnTypes(), duplicate);
					
					returnLabel.insertBefore(tree);
					
					indexMethod = new TACMethodRef(tree, Type.ADDRESS_MAP.getMatchingMethod("index", new SequenceType(Type.ULONG)) );
					TACOperand index = new TACCall(tree, indexMethod, map, address );
					TACOperand existingObject = new TACLongToPointer(tree, index, new SimpleModifiedType(type));
					new TACReturn(tree, methodSignature.getFullReturnTypes(), existingObject);					
				}
			}
			//Gets and sets that were created by default (that's why they have a null parent)
			else if( methodSignature.getNode().getParent() == null && (methodSignature.isGet() || methodSignature.isSet() )) { 			
				method.addParameters(tree);
				TACFieldRef field = new TACFieldRef(new TACLocalLoad(
						tree, method.getThis()), methodSignature.getSymbol());
				if (methodSignature.isGet())
					new TACReturn(tree, methodSignature.getFullReturnTypes(),
							new TACLoad(tree, field));
				else if (methodSignature.isSet()) {
					TACVariable value = null;
					for (TACVariable parameter : method.getParameters())
						value = parameter;
					new TACStore(tree, field, new TACLocalLoad(tree, value));
					new TACReturn(tree, methodSignature.getFullReturnTypes());
				}
				else
					new TACReturn(tree, methodSignature.getFullReturnTypes());
			}		
			else if (methodSignature.isWrapper()) {
				MethodSignature wrapped = methodSignature.getWrapped();
				SequenceType fromTypes = methodSignature.getFullParameterTypes(),
						toTypes = wrapped.getFullParameterTypes();
				Iterator<TACVariable> fromArguments = method.addParameters(tree, true).
						getParameters().iterator();
				List<TACOperand> toArguments = new ArrayList<TACOperand>(
						toTypes.size());
				for (int i = 0; i < toTypes.size(); i++) {
					TACOperand argument =
							new TACLocalLoad(tree, fromArguments.next());
					if (!fromTypes.getType(i).isSubtype(toTypes.getType(i)))
						argument = TACCast.cast(tree, toTypes.get(i), argument);
					toArguments.add(argument);				
				}
				
				TACOperand value = new TACCall(tree, new TACMethodRef(tree,
						wrapped), toArguments); 
				
				if( methodSignature.getFullReturnTypes().isEmpty() )
					new TACReturn(tree, methodSignature.getFullReturnTypes(), null);
				else {
					fromTypes = wrapped.getFullReturnTypes();
					toTypes = methodSignature.getFullReturnTypes();
					
					if( value.getType() instanceof SequenceType )						
						value = TACCast.cast(tree, new SimpleModifiedType(toTypes), value);					
					else {
						if( !fromTypes.getType(0).isSubtype(toTypes.getType(0)) )
							value = TACCast.cast(tree, toTypes.get(0), value);
					}					
					
					new TACReturn(tree, toTypes, value);	
				}
			}
			else { // Regular method or create (includes empty creates)
				method.addParameters(tree);
				
				if( methodSignature.isCreate()) {
					ShadowParser.CreateDeclarationContext declaration = (ShadowParser.CreateDeclarationContext) methodSignature.getNode();
					implicitCreate = declaration.createBlock() == null || declaration.createBlock().explicitCreateInvocation() == null;
					Type type = methodSignature.getOuter();
					if (type.hasOuter())
						new TACStore(tree,
								new TACFieldRef(new TACLocalLoad(tree,
										method.getThis()),
										new SimpleModifiedType(type.getOuter()),
										"_outer"),
								new TACLocalLoad(tree,
										method.getParameter("_outer")));
				}
				
				// Call parent create if implicit create.
				if( implicitCreate ) {
					ClassType thisType = (ClassType)methodSignature.getOuter(),
							superType = thisType.getExtendType();
					if (superType != null) {
						if( superType.hasOuter() )
							new TACCall(tree, new TACMethodRef(tree,
									superType.getMatchingMethod("create", new SequenceType())), new TACLocalLoad(tree,
									method.getThis()), new TACLocalLoad(tree, method.getParameter("_outer")));
						else
							new TACCall(tree, new TACMethodRef(tree,
									superType.getMatchingMethod("create", new SequenceType())), new TACLocalLoad(tree,
									method.getThis()));
					}
					
					// Walk fields in *exactly* the order they were declared since
					// some fields depend on prior fields.
					// This is accomplished by using a LinkedHashMap.
					for( ShadowParser.VariableDeclaratorContext field : ((ClassType)(methodSignature.getOuter())).getFields().values() ) 
						if (!field.getModifiers().isConstant() && !(field.getType() instanceof SingletonType)) {
							visit(field);
							field.appendBefore(tree);
						}
				}				
				
				visit(methodSignature.getNode());
				methodSignature.getNode().appendBefore(tree);				
			}			
		
			tree = tree.remove(); //gets node before anchor (and removes dummy)
			method.setNode(tree.getNext()); //the node after last node is, strangely, the first node			
		}
		moduleStack.peek().addMethod(method);
		block = null;
		this.method = null;
		tree = saveTree;
	}	
	
	private void visitBinaryOperation(Context node, List<? extends Context> list) {
		TACOperand current = list.get(0).appendBefore(tree);		
		
		for( int i = 1; i < list.size(); i++ ) {
			String op = node.getChild(2*i - 1).getText();  //the operations are every other child
			TACOperand next = list.get(i).appendBefore(tree);
			MethodSignature signature = node.getOperations().get(i - 1);
			boolean isCompare = ( op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">=") );
			Type currentType = resolveType(current.getType());
			if( currentType.isPrimitive() && signature.getModifiers().isNative() ) //operation based on method
				current = new TACBinary(tree, current, signature, op, next, isCompare );
			else {	
				//comparisons will always give positive, negative or zero integer
				//must be compared to 0 with regular int comparison to work
				if( isCompare ) {
					TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.INT));
					new TACLocalStore(tree, var, new TACCall(tree, new TACMethodRef(tree, current, signature), current, next));		
					current = new TACLocalLoad(tree, var);					
					current = new TACBinary(tree, current, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), op, new TACLiteral(tree, new ShadowInteger(0)), true );
				}
				else
					current = new TACCall(tree, new TACMethodRef(tree, current, signature), current, next);				
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
		new TACLocalStore(tree, iterator, new TACLiteral(tree, new ShadowInteger(0)));
		TACOperand length = new TACLength(tree, array, 0);			
		for (int i = 1; i < arrayType.getDimensions(); i++)
			length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), "*", new TACLength(tree, array, i));			
		
		new TACBranch(tree, conditionLabel);  //init is done, jump to condition
		
		bodyLabel.insertBefore(tree);
		
		//put initialization before main body
		TACArrayRef location = new TACArrayRef(tree, array, new TACLocalLoad(tree, iterator), false);		
				
		if( create != null)
			new TACStore(tree, location, callCreate( create, params, create.getOuter()));
		else
			new TACStore(tree, location, defaultValue);		
		
		new TACBranch(tree, updateLabel);
		updateLabel.insertBefore(tree);			
		
		//increment iterator							
		TACOperand value = new TACBinary(tree, new TACLocalLoad(tree, iterator), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+", new TACLiteral(tree, new ShadowInteger(1)));
		new TACLocalStore(tree, iterator, value);	
		
		new TACBranch(tree, conditionLabel);
		conditionLabel.insertBefore(tree);
		
		//check if iterator < array length
		value = new TACLocalLoad(tree, iterator);			
		TACOperand condition = new TACBinary(tree, value, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), "<", length, true );
		
		new TACBranch(tree, condition, bodyLabel, endLabel);		
		endLabel.insertBefore(tree);
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
		TACNewArray alloc = new TACNewArray(tree, type, baseClassData,
				sizes.subList(0, type.getDimensions()));
		int start = type.getDimensions();
		int end = sizes.size();
		sizes = sizes.subList(start, end);
		if (!sizes.isEmpty())
		{
			TACVariable index = method.addTempLocal(new SimpleModifiedType(Type.INT));
			new TACLocalStore(tree, index, new TACLiteral(tree, new ShadowInteger(0)));
			TACLabel bodyLabel = new TACLabel(method),
					condLabel = new TACLabel(method),
					endLabel = new TACLabel(method);
			new TACBranch(tree, condLabel);
			bodyLabel.insertBefore(tree);
			new TACStore(tree, new TACArrayRef(tree, alloc, new TACLocalLoad(tree, index), false),
					visitArrayAllocation((ArrayType)type.getBaseType(), new TACClass(tree, (ArrayType)type.getBaseType()), sizes, create, params, defaultValue));
			new TACLocalStore(tree, index, new TACBinary(tree, new TACLocalLoad(tree, index), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), "+",
					new TACLiteral(tree, new ShadowInteger(1))));
			new TACBranch(tree, condLabel);
			condLabel.insertBefore(tree);
			new TACBranch(tree, new TACBinary(tree, new TACLocalLoad(tree, index), alloc.getTotalSize()),
					endLabel, bodyLabel);
			endLabel.insertBefore(tree);
		}
		else {
			//fill with values			
			initializeArray(alloc, create, params, defaultValue);			
		}
		
		return alloc;		
	}
	
	private TACOperand getDefaultValue(Context type)
	{
		TACOperand op;
		
		if (type.getType().equals(Type.BOOLEAN))
			op = new TACLiteral(tree, new ShadowBoolean(false));
		else if (type.getType().equals(Type.CODE))
			op =  new TACLiteral(tree, ShadowCode.parseCode("'\0'"));
		else if (type.getType().equals(Type.UBYTE))
			op =  new TACLiteral(tree, ShadowInteger.parseNumber("0uy"));
		else if (type.getType().equals(Type.BYTE))
			op =  new TACLiteral(tree, ShadowInteger.parseNumber("0y"));
		else if (type.getType().equals(Type.USHORT))
			op =  new TACLiteral(tree, ShadowInteger.parseNumber("0us"));
		else if (type.getType().equals(Type.SHORT))
			op =  new TACLiteral(tree, ShadowInteger.parseNumber("0s"));
		else if (type.getType().equals(Type.UINT))
			op =  new TACLiteral(tree, ShadowInteger.parseNumber("0ui"));
		else if (type.getType().equals(Type.INT))
			op =  new TACLiteral(tree, ShadowInteger.parseNumber("0i"));
		else if (type.getType().equals(Type.ULONG))
			op =  new TACLiteral(tree, ShadowInteger.parseNumber("0ul"));
		else if (type.getType().equals(Type.LONG))
			op =  new TACLiteral(tree, ShadowInteger.parseNumber("0l"));
		else if( type.getType().equals(Type.DOUBLE))
			op =  new TACLiteral(tree, ShadowDouble.parseDouble("0.0"));
		else if( type.getType().equals(Type.FLOAT))
			op =  new TACLiteral(tree, ShadowFloat.parseFloat("0.0f"));
		else if( type.getType() instanceof ClassType )
			op = new TACLiteral(tree, new ShadowNull(type.getType()));
		else
			op = TACCast.cast(tree, type, new TACLiteral(tree, new ShadowNull(Type.NULL)));
		
		return op;
	}
	
	
	@Override public Void visitDestroy(ShadowParser.DestroyContext ctx)
	{ 
		throw new UnsupportedOperationException();		
	}
		
	@Override public Void visitForeachInit(ShadowParser.ForeachInitContext ctx)
	{ 
		visitChildren(ctx);	
		
		method.addLocal(ctx, ctx.Identifier().getText());		
		//append collection
		ctx.setOperand(ctx.conditionalExpression().appendBefore(tree));
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
			child.appendBefore(tree);
		
		return null;		
	}	

	@Override public Void visitSequenceLeftSide(ShadowParser.SequenceLeftSideContext ctx)
	{ 
		visitChildren(ctx);
		
		//Note: their instructions are appended, but the operands for each sequence element are retrieved later and not saved here
		for( ParseTree child : ctx.children )
			if( child instanceof Context )
				((Context)child).appendBefore(tree);
		
		return null;
	}
	
	@Override public Void visitSequenceVariable(ShadowParser.SequenceVariableContext ctx)
	{ 
		visitChildren(ctx);
				
		TACVariable var = method.addLocal(ctx, ctx.Identifier().getText());
		ctx.setOperand(new TACLocalStore(tree, var, new TACLiteral(tree, new ShadowUndefined(ctx.getType()))));
		
		return null;		
	}
	
	@Override public Void visitRightSide(ShadowParser.RightSideContext ctx)
	{ 
		visitChildren(ctx);
		
		if( ctx.sequenceRightSide() != null ) {
			ctx.sequenceRightSide().appendBefore(tree);
			List<TACOperand> sequence =
					new ArrayList<TACOperand>(ctx.sequenceRightSide().conditionalExpression().size());	
			for( ShadowParser.ConditionalExpressionContext child : ctx.sequenceRightSide().conditionalExpression() )
				sequence.add(child.getOperand());
			ctx.setOperand(new TACSequence(tree, sequence));			
		}
		else
			ctx.setOperand(ctx.conditionalExpression().appendBefore(tree));
		
		return null;
	}
	
	@Override public Void visitCopyExpression(ShadowParser.CopyExpressionContext ctx)
	{ 
		visitChildren(ctx);
		
		TACOperand value = ctx.conditionalExpression().appendBefore(tree);
		prefix = value;
		Type type = ctx.getType();
		
		if( !type.getModifiers().isImmutable() ) { //if immutable, do nothing, the old one is fine
			TACNewObject object = new TACNewObject(tree, Type.ADDRESS_MAP );
			TACMethodRef create = new TACMethodRef(tree, Type.ADDRESS_MAP.getMatchingMethod("create", new SequenceType()) );
			TACOperand map = new TACCall(tree, create, object);
			
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
					data = TACCast.cast(tree, new SimpleModifiedType(Type.OBJECT), data);
					TACOperand nullCondition = new TACBinary(tree, data, new TACLiteral(tree, new ShadowNull(data.getType())));
					new TACBranch(tree, nullCondition, nullLabel, copyLabel);
					copyLabel.insertBefore(tree);
					copyMethod = new TACMethodRef(tree, data, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));						
				}
				else {
					TACOperand nullCondition = new TACBinary(tree, data, new TACLiteral(tree, new ShadowNull(data.getType())));						
					new TACBranch(tree, nullCondition, nullLabel, copyLabel);
					copyLabel.insertBefore(tree);
					copyMethod  = new TACMethodRef(tree, data, type.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));						
				}
				
				TACOperand copy = new TACCall(tree, copyMethod, data, map);

				if( type instanceof InterfaceType )
					//and then a cast back to interface
					copy = TACCast.cast(tree, ctx, copy);
				
				new TACLocalStore(tree, result, copy);					
				new TACBranch(tree, doneLabel);	
				
				nullLabel.insertBefore(tree);
				
				new TACLocalStore(tree, result, value);
				new TACBranch(tree, doneLabel);
				
				doneLabel.insertBefore(tree);
				prefix = new TACLocalLoad(tree, result);
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
			child.appendBefore(tree);
		
		return null;	
	}	

	@Override
	public Void visitArrayCreateCall(ArrayCreateCallContext ctx)
	{		
		visitChildren(ctx);		
		for( ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression() )
			child.appendBefore(tree);		
		
		return null;
	}

	@Override
	public Void visitArrayDefault(ArrayDefaultContext ctx) 
	{
		visitChildren(ctx);		
		ctx.setOperand(ctx.conditionalExpression().appendBefore(tree));
		
		return null;
	}
}
