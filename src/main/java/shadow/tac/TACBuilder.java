package shadow.tac;

import static shadow.AST.ASTWalker.WalkType.NO_CHILDREN;
import static shadow.AST.ASTWalker.WalkType.POST_CHILDREN;
import static shadow.AST.ASTWalker.WalkType.PRE_CHILDREN;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowCode;
import shadow.interpreter.ShadowDouble;
import shadow.interpreter.ShadowFloat;
import shadow.interpreter.ShadowInteger;
import shadow.interpreter.ShadowNull;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowUndefined;
import shadow.parser.javacc.*;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBlock;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACConstantRef;
import shadow.tac.nodes.TACCopyMemory;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACGenericArrayRef;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
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
import shadow.tac.nodes.TACNodeRef;
import shadow.tac.nodes.TACNot;
import shadow.tac.nodes.TACOperand;
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

public class TACBuilder implements ShadowParserVisitor {
	private TACTree tree;	
	private TACMethod method;
	private TACOperand prefix;
	private boolean explicitSuper;	
	private TACBlock block;	
	private Deque<TACModule> moduleStack = new ArrayDeque<TACModule>();
	
	public TACModule build(Node node) throws ShadowException {		
		tree = new TACTree(); //no block				
		method = null;
		prefix = null;
		explicitSuper = false;
		block = null;
		walk(node);
		return moduleStack.pop();
	}
	
	public void walk(Node node) throws ShadowException {
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

	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit)
			throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTImportDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTModifiers node, Boolean secondVisit)
			throws ShadowException {
		return PRE_CHILDREN;
	}
	
	private static Type resolveType(Type type) {
		if( type instanceof PropertyType )
			return ((PropertyType)type).getGetType().getType();
		else
			return type;
	}

	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node,
			Boolean secondVisit) throws ShadowException {
		Type type = node.getType();
		TACModule newModule = new TACModule(type);
		
		if( !moduleStack.isEmpty() )
			moduleStack.peek().addInnerClass(newModule);
		moduleStack.push(newModule);
		
		TACBlock oldBlock = block;
		
		//dummy method and block for constant building
		method = new TACMethod( new MethodSignature(new MethodType(), "", type, null));
		block = method.getBlock();
		
		for (Node constant : type.getFieldList())
			if (constant.getModifiers().isConstant())
				visitConstant(new TACConstant(type,
						constant.getImage(), block), constant);
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

		TACTree saveTree = tree;		
		Node body = node.jjtGetChild(node.jjtGetNumChildren() - 1);
		for (int i = 0; i < body.jjtGetNumChildren(); i++) {
			SimpleNode child = (SimpleNode)body.jjtGetChild(i);
			if (child.jjtGetNumChildren() > 1 && child.jjtGetChild(1) instanceof
					ASTClassOrInterfaceDeclaration)
				build(child);
		}
		tree = saveTree;
		
		return NO_CHILDREN;
	}

	@Override
	public Object visit(ASTVersion node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumBody node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumConstant node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTIsList node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBodyDeclaration node,
			Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTVariableDeclarator node, Boolean secondVisit) throws ShadowException {
		// No records are needed for singletons.
		if (secondVisit && !(node.getType() instanceof SingletonType)) { 
			String name = node.getImage();			
			if (node.isField()) {
				TACReference ref = new TACFieldRef(new TACLocalLoad(tree, method.getThis()), name);
				
				if (node.jjtGetNumChildren() != 0)
					new TACStore(tree, ref, tree.appendChild(0));
				//Note that default values are now removed for regular types
				else if( node.getModifiers().isNullable() || node.getType() instanceof ArrayType )
					new TACStore(tree, ref, getDefaultValue(node));
			}
			else {
				TACVariable var = method.addLocal(node, name);
				if (node.jjtGetNumChildren() != 0)
					new TACLocalStore(tree, var, tree.appendChild(0));
				else
					new TACLocalStore(tree, var, new TACLiteral(tree, new ShadowUndefined(node.getType())));
			}
			
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTArrayInitializer node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {			
			List<TACOperand> sizes = new ArrayList<TACOperand>();			
			sizes.add(new TACLiteral(tree, new ShadowInteger(node.jjtGetNumChildren())));
			ArrayType arrayType = (ArrayType)node.getType();
			TACClass baseClass = new TACClass(tree, arrayType.getBaseType());
			//allocate array
			prefix = visitArrayAllocation(arrayType, baseClass, sizes);
			
			//store each element in initializer into the array
			for( int i = 0; i < node.jjtGetNumChildren(); ++i ) {
				// last parameter of false means no array bounds checking needed
				TACArrayRef ref = new TACArrayRef(tree, prefix, new TACLiteral(tree, new ShadowInteger(i)), false); 
				new TACStore(tree, ref, tree.appendChild(i));				
			}
			
			new TACNodeRef(tree, prefix);
		}		
		
		return POST_CHILDREN;
	}
	
	private void initializeSingletons(SignatureNode node) {
		for( SingletonType type :  node.getSingletons() ) {		
			TACLabelRef initLabel = new TACLabelRef(method),
					doneLabel = new TACLabelRef(method);
			TACSingletonRef reference = new TACSingletonRef(type);
			TACOperand instance = new TACLoad(tree, reference);
			new TACBranch(tree, new TACSame(tree, instance, new TACLiteral(tree, new ShadowNull(instance.getType()))), initLabel, doneLabel);
			initLabel.new TACLabel(tree);
			
			TACMethodRef methodRef = new TACMethodRef(tree, type.getMethods("create").get(0));
			TACOperand object = new TACNewObject(tree, type);
			TACCall call = new TACCall(tree, methodRef, object );			
			new TACStore(tree, reference, call ); 				
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
		}
	}
	
	private boolean isTerminator(TACNode node)
	{
		return( node instanceof TACBranch ||
				node instanceof TACResume ||
				node instanceof TACReturn ||
				node instanceof TACThrow );
	}
	
	@Override
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {		
			TACNode last = tree.appendAllChildren();
			
			//A non-void method should always have explicit TACReturns
			//A void one might need one inserted at the end			
			if( method.getSignature().isVoid() && !isTerminator(last) ) {
				TACReturn explicitReturn = new TACReturn(tree, new SequenceType() );
				//prevents an error from being recorded if this return is later removed as dead code
				explicitReturn.setASTNode(null); 
			}
			
			/*
			if (last instanceof TACLabel ) {
				if( last.getPrevious() instanceof TACReturn || last.getPrevious() instanceof TACThrow  )
					last.remove();
				//void method might need an explicit return
				else if( method.getMethod().isVoid() ) {
					MethodSignature signature = method.getMethod();
					SequenceType returnTypes = signature.getFullReturnTypes(); 
					new TACReturn(tree, returnTypes );
				}
			}
			*/
			
			//Junk added to guarantee a return, but that should be done by control flow graph checking!
			/*
			if (last instanceof TACLabel &&	last.getPrevious() instanceof TACReturn)
				last.remove();
			else {
				MethodSignature signature = method.getMethod();
				SequenceType returnTypes = signature.getFullReturnTypes(); 
				if (signature.isVoid())
					new TACReturn(tree, returnTypes );
				else if (signature.isSingle())
					new TACReturn(tree, returnTypes,
							getDefaultValue(returnTypes.get(0)));
				else {
					List<TACOperand> seq = new ArrayList<TACOperand>();
					for (ModifiedType type : returnTypes)
						seq.add(getDefaultValue(type));
					new TACReturn(tree, returnTypes,
							new TACSequence(tree, seq));
				}
			}
			*/
		}
		else //first visit					
			initializeSingletons(node);
		
		return POST_CHILDREN;
	}
	@Override
	public Object visit(ASTMethodDeclarator node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCreateDeclaration node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			TACNode last = tree.appendAllChildren();
			if (last instanceof TACLabel &&
					last.getPrevious() instanceof TACReturn)
				last.remove();
			else
				new TACReturn(tree, method.getSignature().getFullReturnTypes(),
						new TACLocalLoad(tree, method.getThis()));
		}
		else
			initializeSingletons(node);
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCreateDeclarator node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTDestroyDeclaration node, Boolean secondVisit) throws ShadowException	{
		if (secondVisit) {
			TACNode last = tree.appendAllChildren();
			if (last instanceof TACLabel &&
					last.getPrevious() instanceof TACReturn)
				last.remove();
			else
				new TACReturn(tree, method.getSignature().getFullReturnTypes());
		}
		else
			initializeSingletons(node);
		
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTExplicitCreateInvocation node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {			
			boolean isSuper = node.getImage().equals("super");
			ClassType thisType = (ClassType)method.getSignature().getOuter();
			List<TACOperand> params = new ArrayList<TACOperand>();
			params.add(new TACLocalLoad(tree, method.getThis()));
			
			if( (!isSuper && thisType.hasOuter()) || (isSuper && thisType.getExtendType().hasOuter()) ) {
				TACVariable outer = method.getParameter("_outer");
				params.add(new TACLocalLoad(tree, outer));
			}
			
			for (int i = 0; i < tree.getNumChildren(); i++) {
				TACOperand param = tree.appendChild(i); 
				params.add(param);
			}
			new TACCall(tree, new TACMethodRef(tree, node.getMethodSignature()),
					params);
			
			// If a super create, walk the fields.
			// Walking the fields is unnecessary if there is a this create,
			// since it will be taken care of by the this create, either explicitly or implicity.
			if( isSuper ) {
				// Walk fields in *exactly* the order they were declared since
				// some fields depend on prior fields.
				for( Node field : thisType.getFieldList() ) 
					if (!field.getModifiers().isConstant() && !(field.getType() instanceof SingletonType))
						walk(field);
			}
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTFormalParameters node, Boolean secondVisit) throws ShadowException {
		return NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTFormalParameter node, Boolean secondVisit) throws ShadowException {	
		//ASTFormalParameters are NOT visited
		//Parameters for methods are handled separately
		//The only thing that comes in here are the declarations
		//in catch blocks
		if (secondVisit)
			method.addLocal(node, node.getImage());
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTType node, Boolean secondVisit)throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTReferenceType node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFunctionType node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceTypeSuffix node, Boolean secondVisit)
			throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeArguments node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTResultTypes node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTUnqualifiedName node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTNameList node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}
	
	private void doAssignment(TACOperand left, Node leftAST, TACOperand right, char operation, OperationNode node ) {
		TACMethodRef methodRef;
		MethodSignature signature;
		Type leftType = leftAST.getType();
		List<TACOperand> indexes = getIndexes(leftAST);
				
		/*
		if( indexes != null ) {
			for( int i = 0; i < indexes.size(); ++i )
				tree.append(indexes.get(i));
		}
		*/		
		
		if( leftType instanceof PropertyType ) {					
			PropertyType propertyType = (PropertyType) leftType;
			List<TACOperand> parameters = new ArrayList<TACOperand>();
			
			parameters.add( left );
			if( propertyType instanceof SubscriptType )
				parameters.addAll(indexes); //there should only be one index in this list (not needed for properties)
			
			if (operation != '=') {	
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
			if (operation != '=') {	
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
			if (operation != '=') {	
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
	
	// Primary expressions can have indexes which are needed in the assignment process
	// This method retrieves them if dealing with a primary expression that has them
	// Very ugly and fragile code that is highly dependent on the grammar not changing!
	private List<TACOperand> getIndexes(Node node) {
		List<TACOperand> indexes = null;
		
		if( node instanceof ASTPrimaryExpression  ) {
			ASTPrimaryExpression expression = (ASTPrimaryExpression) node;			
			Node suffix = expression.getSuffix();
			if( suffix != null && suffix instanceof ASTPrimarySuffix ) {
				ASTPrimarySuffix primarySuffix = (ASTPrimarySuffix) suffix;
				if( primarySuffix.jjtGetNumChildren() > 0 && primarySuffix.jjtGetChild(0) instanceof DimensionNode )
					indexes = ((DimensionNode)primarySuffix.jjtGetChild(0)).getIndexes();			
			}
		}
		
		return indexes;
	}
	

	@Override
	public Object visit(ASTExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			// Shadow standard is to evaluate the LHS of an assignment first
			TACOperand left = tree.appendChild(0);		
			TACOperand right = tree.appendChild(2);							
			char operation = node.jjtGetChild(1).getImage().charAt(0);
			ASTPrimaryExpression expression = (ASTPrimaryExpression) node.jjtGetChild(0);
			doAssignment(left, expression, right, operation, node);
			
			/*

			TACMethodRef methodRef;
			MethodSignature signature;

			if( node.jjtGetChild(0).getType() instanceof PropertyType ) {					
				PropertyType propertyType = (PropertyType) node.jjtGetChild(0).getType();
				List<TACOperand> parameters = new ArrayList<TACOperand>();
				
				parameters.add( left );
				if( propertyType instanceof SubscriptType )
					parameters.addAll(indexStack.pop()); //there should only be one index in this list (not needed for properties)
				
				if (operation != '=') {	
					signature = propertyType.getGetter();	
					methodRef = new TACMethodRef(tree,
							left, //prefix
							signature);
					//TODO: Might need signature without type arguments?
					parameters.set(0, methodRef.getPrefix()); //replacing left with the method prefix can prevent duplicate code (if there were casts)					
					TACOperand result = new TACCall(tree, block, methodRef, parameters);					
					
					//signature for other operation
					signature = node.getOperations().get(0);
					
					if( left.getType().isPrimitive() && signature.getModifiers().isNative() )
						right = new TACBinary(tree, result, signature, operation, right);
					else {
						TACVariableRef temp = new TACVariableRef(tree,
								method.addTempLocal(signature.getReturnTypes().get(0)));
						methodRef = new TACMethodRef(tree, result, signature);
						new TACStore(tree, temp, new TACCall(tree, block, methodRef, methodRef.getPrefix(), right));		
						right = new TACLoad(tree, temp);						
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
				new TACCall(tree, block, methodRef, parameters);
			}			
			else {	
				TACReference var = (TACReference)left;	
				signature = node.getOperations().get(0);
				if (operation != '=') {	
					if( left.getType().isPrimitive() && signature.getModifiers().isNative() )
						right = new TACBinary(tree, var, signature, operation, right );
					else {
						TACVariableRef temp = new TACVariableRef(tree,
								method.addTempLocal(signature.getReturnTypes().get(0)));
						methodRef = new TACMethodRef(tree, left, signature);
						new TACStore(tree, temp, new TACCall(tree, block, methodRef, methodRef.getPrefix(), right));		
						right = new TACLoad(tree, temp);						
					}
				}
				new TACStore(tree, var, right);				
			}
			
			*/
		}			
			
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTAssignmentOperator node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalExpression node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			TACLabelRef trueLabel = new TACLabelRef(method),
					falseLabel = new TACLabelRef(method),
					doneLabel = new TACLabelRef(method);
			TACVariable var = method.addTempLocal(node);
			new TACBranch(tree, tree.appendChild(0), trueLabel, falseLabel);
			trueLabel.new TACLabel(tree);
			new TACLocalStore(tree, var, tree.appendChild(1));
			new TACBranch(tree, doneLabel);
			falseLabel.new TACLabel(tree);
			new TACLocalStore(tree, var, tree.appendChild(2));
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLocalLoad(tree, var);
		}
		return node.jjtGetNumChildren() == 1 ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCoalesceExpression node, Boolean secondVisit) throws ShadowException	{
		if (secondVisit) {
			TACLabelRef nonnullLabel = new TACLabelRef(method),
					nullLabel = new TACLabelRef(method),
					doneLabel = new TACLabelRef(method);
			TACVariable var = method.addTempLocal(node);
			TACOperand value = tree.appendChild(0);
			new TACBranch(tree, new TACSame(tree, value, new TACLiteral(tree, new ShadowNull(value.getType()))), nullLabel, nonnullLabel);
			nonnullLabel.new TACLabel(tree);
			new TACLocalStore(tree, var, value);
			new TACBranch(tree, doneLabel);
			nullLabel.new TACLabel(tree);
			new TACLocalStore(tree, var, tree.appendChild(1));
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLocalLoad(tree, var);
		}
		return node.jjtGetNumChildren() == 1 ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalOrExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			TACLabelRef doneLabel = new TACLabelRef(method);
			TACVariable var = method.addTempLocal(node);
			TACOperand value = tree.appendChild(0);
			new TACLocalStore(tree, var, value);
			for (int i = 1; i < tree.getNumChildren(); i++)
			{
				TACLabelRef nextLabel = new TACLabelRef(method);
				new TACBranch(tree, value, doneLabel, nextLabel);
				nextLabel.new TACLabel(tree);
				new TACLocalStore(tree, var, value = tree.appendChild(i));
			}
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLocalLoad(tree, var);
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalExclusiveOrExpression node,
			Boolean secondVisit) throws ShadowException {
		if (secondVisit)
			visitBooleanOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalAndExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			TACLabelRef doneLabel = new TACLabelRef(method);
			TACVariable var = method.addTempLocal(node);
			TACOperand value = tree.appendChild(0);
			new TACLocalStore(tree, var, value);
			for (int i = 1; i < tree.getNumChildren(); i++) {
				TACLabelRef nextLabel = new TACLabelRef(method);
				new TACBranch(tree, value, nextLabel, doneLabel);
				nextLabel.new TACLabel(tree);
				new TACLocalStore(tree, var, value = tree.appendChild(i));
			}
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLocalLoad(tree, var);
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseOrExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseExclusiveOrExpression node,
			Boolean secondVisit) throws ShadowException {
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseAndExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTEqualityExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			int index = 0;
			String image = node.getImage();
			TACOperand value = tree.appendChild(index);
			while (index < image.length()) {
				char c = image.charAt(index);
				TACOperand other = tree.appendChild(++index);
				Type currentType = value.getType();
				Type nextType = other.getType();
								
				if (c == '=' || c == '!') { //== or !=									
					if (currentType.isPrimitive() &&
						nextType.isPrimitive() /*&&
						(currentType.isSubtype(nextType) || nextType.isSubtype(currentType) )*/) //if not, methods are needed					
						value = new TACSame(tree, value, other);					
					else {	
						//no nullables allowed
						TACVariable var = method.addTempLocal(node);
						Type valueType = resolveType(value.getType());
						MethodSignature signature = valueType.getMatchingMethod("equal", new SequenceType(other));												
						new TACLocalStore(tree, var, new TACCall(tree,
								new TACMethodRef(tree, value, signature),
								value, other));
						value = new TACLocalLoad(tree, var);
					}						
				}
				else { //=== or !==				
					boolean valueNullable = value.getModifiers().isNullable();
					boolean otherNullable = other.getModifiers().isNullable();
					if( currentType.isPrimitive() && nextType.isPrimitive() && ( valueNullable || otherNullable )) {
						TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.BOOLEAN));
						TACLabelRef done = new TACLabelRef(method);
						
						if( valueNullable && otherNullable ) {							
							TACOperand valueNull = new TACSame(tree, value, new TACLiteral(tree, new ShadowNull(value.getType())));
							TACOperand otherNull = new TACSame(tree, other, new TACLiteral(tree, new ShadowNull(other.getType())));							
							TACOperand bothNull = new TACBinary(tree, valueNull, TACBinary.Boolean.AND, otherNull );
							TACOperand eitherNull = new TACBinary(tree, valueNull, TACBinary.Boolean.OR, otherNull );							
							TACLabelRef notBothNull = new TACLabelRef(method);												
							TACLabelRef noNull = new TACLabelRef(method);
							
							new TACLocalStore(tree, var, bothNull);
							new TACBranch(tree, bothNull, done, notBothNull); //var will be true (both null)
							
							notBothNull.new TACLabel(tree);
							new TACBranch(tree, eitherNull, done, noNull); //var will be false (one but not both null)
														
							noNull.new TACLabel(tree);
							new TACLocalStore(tree, var, new TACSame(tree,
									TACCast.cast(tree, new SimpleModifiedType(value.getType()), value),
									TACCast.cast(tree, new SimpleModifiedType(other.getType()), other)));
							new TACBranch(tree, done);
						}
						else if( valueNullable ) { //only value nullable							
							TACOperand valueNull = new TACSame(tree, value, new TACLiteral(tree, new ShadowNull(value.getType())));							
							TACLabelRef oneNull = new TACLabelRef(method);												
							TACLabelRef noNull = new TACLabelRef(method);														
							new TACBranch(tree, valueNull, oneNull, noNull);
							oneNull.new TACLabel(tree);
							new TACLocalStore(tree, var, new TACLiteral(tree, new ShadowBoolean(false)));		
							new TACBranch(tree, done);														
							noNull.new TACLabel(tree);
							new TACLocalStore(tree, var, new TACSame(tree,
									TACCast.cast(tree, new SimpleModifiedType(value.getType()), value),
									other));
							new TACBranch(tree, done);							
						}
						else { //only other nullable 
							TACOperand otherNull = new TACSame(tree, other, new TACLiteral(tree, new ShadowNull(other.getType())));							
							TACLabelRef oneNull = new TACLabelRef(method);												
							TACLabelRef noNull = new TACLabelRef(method);														
							new TACBranch(tree, otherNull, oneNull, noNull);
							oneNull.new TACLabel(tree);
							new TACLocalStore(tree, var, new TACLiteral(tree, new ShadowBoolean(false)));									
							new TACBranch(tree, done);														
							noNull.new TACLabel(tree);
							new TACLocalStore(tree, var, new TACSame(tree,
									value,
									TACCast.cast(tree, new SimpleModifiedType(other.getType()), other)));
							new TACBranch(tree, done);
						}
						
						done.new TACLabel(tree);
						value = new TACLocalLoad(tree, var);
					}
					else //both non-nullable primitives or both references, no problem		
						value = new TACSame(tree, value, other);
				}
				if (c == '!' || c == 'n')
					value = new TACNot(tree, value);
			}
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTIsExpression node, Boolean secondVisit)
			throws ShadowException {		
		if (secondVisit) {
			TACOperand value = tree.appendChild(0);						
			Type comparisonType = node.jjtGetChild(1).getType();
			
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
			
			new TACCall(tree, methodRef, methodRef.getPrefix(), comparisonClass);
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTRelationalExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}
	
	
	private TACOperand convertToString( TACOperand operand ) {
		Type type = resolveType(operand.getType());
		if( type.equals(Type.STRING ) )
			return operand;
		
		if ( operand.getModifiers().isNullable() && !(type instanceof ArrayType) ) { // || !type.isPrimitive() && !(type instanceof ArrayType))
			TACLabelRef nullLabel = new TACLabelRef(method),
					nonnullLabel = new TACLabelRef(method),
					doneLabel = new TACLabelRef(method);
			TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.STRING));
			new TACBranch(tree, new TACSame(tree, operand,
					new TACLiteral(tree, new ShadowNull(operand.getType()))), nullLabel,
					nonnullLabel);
			nullLabel.new TACLabel(tree);
			new TACLocalStore(tree, var, new TACLiteral(tree, new ShadowString("null")));
			new TACBranch(tree, doneLabel);
			nonnullLabel.new TACLabel(tree);
			
			if( type.isPrimitive() ) //convert non null primitive wrapper to real primitive			
				operand = TACCast.cast(tree, new SimpleModifiedType(type), operand);
			
			TACMethodRef methodRef = new TACMethodRef(tree, operand,
					type.getMatchingMethod("toString", new SequenceType())); 
			
			new TACLocalStore(tree, var, new TACCall(tree, methodRef, methodRef.getPrefix()));
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			operand = new TACLocalLoad(tree, var);
		}
		else {
			TACMethodRef methodRef = new TACMethodRef(tree, operand,
					type.getMatchingMethod("toString", new SequenceType())); 
			operand = new TACCall(tree, methodRef,
					Collections.singletonList(methodRef.getPrefix()));
		}
		
		return operand;
	}

	@Override
	public Object visit(ASTConcatenationExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
		{
			TACOperand last = null;			
			for (int i = 0; i < tree.getNumChildren(); i++)
			{
				TACOperand operand = tree.appendChild(i);
				
				operand = convertToString( operand );				
				
				last = i == 0 ? operand : new TACCall(tree,
						new TACMethodRef(tree,
								Type.STRING.getMethods("concatenate").get(0)),
						Arrays.asList(last, operand));
			}
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTShiftExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTRotateExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTAdditiveExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTUnaryExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			TACOperand operand = tree.appendChild(0);
			String op = node.getImage(); 
			if( op.equals("#")) //string is special because of nulls
				convertToString( operand );			
			else {
				Type type = resolveType(operand.getType());					
				if( op.equals("!") )
					new TACUnary(tree, "!", operand);
				else {
					MethodSignature signature = node.getOperations().get(0); 
					if( type.isPrimitive() && signature.getModifiers().isNative() )
						new TACUnary(tree, signature, op, operand);				
					else {
						TACVariable var = method.addTempLocal(node);
						new TACLocalStore(tree, var, new TACCall(tree, new TACMethodRef(tree, operand, node.getOperations().get(0)), operand));		
						new TACLocalLoad(tree, var);
					}
				}
			}
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTInlineMethodDefinition node, Boolean secondVisit)
			throws ShadowException {
		//if (secondVisit)
			throw new UnsupportedOperationException();
		//return node.jjtGetNumChildren() == 1 ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCastExpression node, Boolean secondVisit)
			throws ShadowException {
		
		//one day this will be supported
		if( node.getType() instanceof MethodType )
			throw new UnsupportedOperationException();
		
		if (secondVisit)
			TACCast.cast(tree, node, tree.appendChild(1), true);
		return POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTCheckExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {	
			TACLabelRef recover;
			
			if( node.hasRecover() )
				recover = block.getRecover();
			else
				recover = new TACLabelRef(method);				
				
			TACLabelRef continueLabel = new TACLabelRef(method);
			TACOperand operand = tree.appendChild(0);
			
			//if there's a recover, things will be handled there if null			
			new TACBranch(tree, new TACSame(tree, operand, new TACLiteral(tree,
					new ShadowNull(operand.getType()))), recover, continueLabel);
			
			//otherwise, we throw an exception here
			if( !node.hasRecover() ) {
				recover.new TACLabel(tree);
				TACOperand object = new TACNewObject(tree, Type.UNEXPECTED_NULL_EXCEPTION);
				MethodSignature signature = Type.UNEXPECTED_NULL_EXCEPTION.getMatchingMethod("create", new SequenceType());						
				TACCall exception = new TACCall(tree, new TACMethodRef(tree, signature), object);
							
				new TACThrow(tree, exception);
			}	
			
			continueLabel.new TACLabel(tree);
			prefix = new TACNodeRef(tree, operand, new SimpleModifiedType(operand.getType(), new Modifiers(operand.getModifiers().getModifiers() & ~Modifiers.NULLABLE)));
			
			if( node.getType().isPrimitive() ) //convert from object to primitive form
				prefix = TACCast.cast(tree, new SimpleModifiedType(node.getType()), prefix );
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimaryExpression node, Boolean secondVisit)
			throws ShadowException {
		TACOperand savePrefix = prefix;
		//TACVariable saveIdentifier = identifier;
		prefix = null;
		//identifier = null;

		tree = tree.next(node.jjtGetNumChildren());
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
			walk(node.jjtGetChild(i));
		tree = tree.done();

		prefix = savePrefix;
		//identifier = saveIdentifier;
		return NO_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			//identifier = new TACVariable(node, node.getImage());
			if (node.isImageNull())
				prefix = tree.appendChild(0);
			else if( node.getType() instanceof SingletonType )
				prefix = new TACLoad(tree, new TACSingletonRef((SingletonType)node.getType()));			
			else {
				String name = node.getImage();
				explicitSuper = name.equals("super");
				if (!(/*explicitSuper ||*/ node.getModifiers().isTypeName() ||
						node.getType() instanceof UnboundMethodType))
				{
					TACVariable local;
					if( explicitSuper )
						local = method.getLocal("this");
					else
						local = method.getLocal(name);
					if (local != null)
						prefix = new TACLocalLoad(tree, local); //TODO: this might not work
					else {
						if (node.getModifiers().isConstant()) { //constant
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
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTAllocation node, Boolean secondVisit)
			throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimarySuffix node, Boolean secondVisit)
			throws ShadowException {
		return PRE_CHILDREN;
	}
	
	private void methodCall(MethodSignature signature, Node node) {
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
		for (int i = 0; i < tree.getNumChildren(); i++)
			if (node.jjtGetChild(i) instanceof ASTTypeArguments)
				for (ModifiedType type :
						(SequenceType)node.jjtGetChild(i).getType()) {
					TACClass _class = new TACClass(tree, type.getType()); 
					params.add(_class.getClassData());
					params.add(_class.getMethodTable());
				}
			else			
				params.add(tree.appendChild(i));
		
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
		
		explicitSuper = false;		
	}

	@Override
	public Object visit(ASTQualifiedKeyword node, Boolean secondVisit)
			throws ShadowException {
		
		//TODO: Fix this!
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTScopeSpecifier node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {			
			if( node.getImage().equals("class")) {
				//Type type = identifier.getType();
				Type type = node.getPrefixType();
				
				boolean raw = false;
				
				if( node.jjtGetNumChildren() > 0 ) {
					ASTTypeArguments arguments = (ASTTypeArguments) node.jjtGetChild(0);
					
					try {
						type = type.replace(type.getTypeParameters(), arguments.getType());
					}
					catch (InstantiationException e) {}					
				}
				else if( type.isParameterized() ) //parameterized, but no type arguments given
					raw = true;					
				
				prefix = new TACClass(tree, type, raw).getClassData();
			}
			else if( node.getModifiers().isConstant() )
				prefix = new TACLoad(tree, new TACConstantRef(node.getPrefixType(), node.getImage()));
			else if( node.getType() instanceof SingletonType)
				prefix = new TACLoad(tree, new TACSingletonRef((SingletonType)node.getType()));
			else if( !node.getModifiers().isTypeName() )  //doesn't do anything at this stage if it's just a type name				
				prefix = new TACLoad(tree, new TACFieldRef(prefix, node.getImage()));
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTMethod node, Boolean secondVisit)
			throws ShadowException {
		//if (secondVisit)
			//identifier = new TACVariable(node, node.getImage());
		return POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTSubscript node, Boolean secondVisit)
			throws ShadowException {
		//subscripts index into arrays or subscriptable types		
		if (secondVisit) {
			//last suffix and on the LHS			
			ASTPrimarySuffix suffix = (ASTPrimarySuffix) node.jjtGetParent();
			ASTPrimaryExpression expression = (ASTPrimaryExpression)suffix.jjtGetParent(); 
			boolean isStore = expression.isLHS() && suffix == expression.getSuffix();
			Type prefixType = resolveType(prefix.getType());
			
			if( prefixType instanceof ArrayType ) {
				ArrayType arrayType = (ArrayType) prefixType;
				List<TACOperand> list = new LinkedList<TACOperand>();
				for (int i = 0; i < tree.getNumChildren(); i++) {
					TACOperand index = tree.appendChild(i);
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
			else if( node.getType() instanceof SubscriptType ) {					
				SubscriptType subscriptType = (SubscriptType) node.getType();
				//only do the straight loads
				//stores (and +='s) are handled in ASTExpression
				if( !isStore ) {
					MethodSignature signature = subscriptType.getGetter();
					methodCall(signature, node);					
				}
				else {					
					List<TACOperand> list = new ArrayList<TACOperand>();
					//TODO: Put this in the right places, since it shouldn't be called until the left hand side goes
					
					TACOperand op = tree.appendChild(0);
					//important that the load happens now, rather than in the assignment
					/*
					if( op instanceof TACReference ) {
						TACReference reference = (TACReference) op;
						op = new TACLoad(tree, reference);
					}
					*/					
					list.add(op);
					node.setIndexes(list);					
					tree.append(prefix); //append the prefix as well
				}
			}		
			else
				throw new UnsupportedOperationException();
		}
		return POST_CHILDREN;
	}	

	@Override
	public Object visit(ASTProperty node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			//last suffix and on the LHS
			ASTPrimarySuffix suffix = (ASTPrimarySuffix) node.jjtGetParent();
			ASTPrimaryExpression expression = (ASTPrimaryExpression)suffix.jjtGetParent(); 
			boolean isStore = expression.isLHS() && suffix == expression.getSuffix();
			//prefix should never be null			
			Type prefixType = resolveType(prefix.getType());
			
			if( prefixType instanceof ArrayType && node.getImage().equals("size")) {
				//optimization to avoid creating an Array object			
				ArrayType arrayType = (ArrayType)prefixType;				
				TACOperand length = new TACLength(tree, prefix, 0);
				for (int i = 1; i < arrayType.getDimensions(); i++)
					length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', new TACLength(tree, prefix, i));
				prefix = length;
			}
			else {
				PropertyType propertyType = (PropertyType) node.getType();
				//only do the straight loads
				//stores (and +='s) are handled in ASTExpression
				if( !isStore ) {
					MethodSignature signature = propertyType.getGetter();
					methodCall(signature, node);					
				}
				else					
					tree.append(prefix); //append the prefix for future use				
			}
		}
		return POST_CHILDREN;
	}	
	

	@Override
	public Object visit(ASTMethodCall node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)		
			methodCall(node.getMethodSignature(), node);			
		
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTLiteral node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)		
			prefix = new TACLiteral(tree, node.getValue());
		
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBooleanLiteral node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			prefix = new TACLiteral(tree, new ShadowBoolean(node.isTrue()));		
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTNullLiteral node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			prefix = new TACLiteral(tree, new ShadowNull(Type.NULL));
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTArguments node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			List<TACOperand> params = new ArrayList<TACOperand>();
			for (int i = 0; i < tree.getNumChildren(); i++) {
				TACOperand param = tree.appendChild(i);
				if (param != null)
					params.add(param);
			}
			new TACSequence(tree, params);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTArrayCreate node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			int start = 0;
			if( node.jjtGetChild(0) instanceof ASTTypeArguments )
				start++; //skip ahead
			
			tree.appendChild(start);			
			ArrayType arrayType = (ArrayType)node.getType();
			Type type = arrayType.getBaseType();
			TACClass baseClass = new TACClass(tree, type);
			ASTArrayDimensions dimensions = (ASTArrayDimensions) node.jjtGetChild(start);			
			List<TACOperand> indices = dimensions.getIndexes();
			
			if( node.getMethodSignature() != null ) {	
				MethodSignature create = node.getMethodSignature();
				List<TACOperand> arguments = new ArrayList<TACOperand>();
				for( int i = start + 1; i < node.jjtGetNumChildren(); ++i )
					arguments.add(tree.appendChild(i));
				prefix = visitArrayAllocation(arrayType, baseClass, indices, create, arguments);
			}
			else if( node.hasDefault() ) {
				TACOperand value = tree.appendChild(start + 1);
				prefix = visitArrayAllocation(arrayType, baseClass, indices, value);
			}
			else //nullable array only
				prefix = visitArrayAllocation(arrayType, baseClass, indices);
		}
		return POST_CHILDREN;
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

	@Override
	public Object visit(ASTCreate node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			int start = 0;
			List<TACOperand> params = new ArrayList<TACOperand>();
			if( node.jjtGetNumChildren() > 0 && node.jjtGetChild(0) instanceof ASTTypeArguments)
				start++;
			
			for( int i = start; i < tree.getNumChildren(); i++)
					params.add(tree.appendChild(i));			
			
			MethodSignature signature = node.getMethodSignature();
			if( signature.getOuter() instanceof InterfaceType )
				callCreate(signature, params, node.jjtGetParent().getType() );
			else
				callCreate(signature, params, signature.getOuter());
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTStatement node, Boolean secondVisit)
			throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTAssertStatement node, Boolean secondVisit) throws ShadowException {		
		if (secondVisit) {
			TACLabelRef doneLabel = new TACLabelRef(method),
					errorLabel = new TACLabelRef(method);
			
			new TACBranch(tree, tree.appendChild(0), doneLabel, errorLabel);			
			errorLabel.new TACLabel(tree);
			
			TACOperand object = new TACNewObject(tree, Type.ASSERT_EXCEPTION);
			List<TACOperand> params = new ArrayList<TACOperand>();
			params.add(object);			
			MethodSignature signature;			
			
			if( node.jjtGetNumChildren() > 1 ) { // has message
				TACOperand message = convertToString( tree.appendChild(1) );
				signature = Type.ASSERT_EXCEPTION.getMatchingMethod("create", new SequenceType(message));
				params.add( message );				
			}
			else
				signature = Type.ASSERT_EXCEPTION.getMatchingMethod("create", new SequenceType());

			TACCall exception = new TACCall(tree, new TACMethodRef(tree, signature), params);
			new TACThrow(tree, exception);
			doneLabel.new TACLabel(tree);			
		}
	
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		if (secondVisit)
			method.exitScope();
		else
			method.enterScope();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCreateBlock node, Boolean secondVisit) throws ShadowException {
		if (secondVisit)
			method.exitScope();
		else
			method.enterScope();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBlockStatement node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTLocalDeclaration node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTLocalMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTLocalVariableDeclaration node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEmptyStatement node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStatementExpression node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}
	
	//private void doAssignment(TACOperand left, TACOperand right, char operation, OperationNode node ) {

	@Override
	public Object visit(ASTSequenceAssignment node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			ASTRightSide rightSide = (ASTRightSide) node.jjtGetChild(1);
			ASTSequenceLeftSide leftSide = (ASTSequenceLeftSide) node.jjtGetChild(0);			
			
			TACSequence left = tree.appendChildRemoveSequence(0);
			//for( int i = 0; i < left.size(); ++i )
			//	tree.append(left.get(i));
			//TACSequence left = (TACSequence) tree.appendChild(0);
									
			//create splat
			if( !(rightSide.getType() instanceof SequenceType)  ) {				
				TACOperand right = tree.appendChild(1);
				TACVariable temporary = method.addTempLocal(rightSide);	
				new TACLocalStore(tree, temporary, right);
				

				//fix this!
				//it should be a bunch of separate stores instead of a sequence store
								
				//for (int index = 0; index < leftSide.getType().size(); index++ )				
					//	sequence.add(new TACLoad(tree, temporary));
				
				//new TACStore(tree, new TACSequenceRef(tree,
					//	left), new TACSequence(tree, sequence) );
				
				
				int index = 0;
				for( boolean used : leftSide.getUsedItems() )
					if( used ) {						
						doAssignment(left.get(index), leftSide.jjtGetChild(index), new TACLocalLoad(tree, temporary), '=', null);
						index++;
					}				
			}
			else if( rightSide.jjtGetChild(0) instanceof ASTSequenceRightSide ){ //sequence on the right
				//compute all right side values before assigning to left
				TACSequence right = tree.appendChildRemoveSequence(1);
				
				/*
				List<TACReference> rightValues = new ArrayList<TACReference>(right.size());
				for( int i = 0; i < right.size(); ++i  ) {
					TACReference temporary = new TACVariableRef(tree,
							method.addTempLocal(right.get(i)));
					rightValues.add(temporary);
					new TACStore(tree, temporary, right.get(i));
				}
				*/				
				
				int index = 0;
				for( int i = 0; i < leftSide.getUsedItems().size(); ++i )
					if( leftSide.getUsedItems().get(i) ) {						
						doAssignment(left.get(index), leftSide.jjtGetChild(index), right.get(i), '=', null);
						index++;
					}
				
				//fix this!
				//it should be a bunch of separate stores instead of a sequence store
				//new TACStore(tree, new TACSequenceRef(tree,
					//	left), tree.appendChild(1));
			}
			else { //method call on the right whose output must be broken into parts
				TACOperand right = tree.appendChild(1);	
				
				/*
				TACReference temporary = new TACVariableRef(tree,
						method.addTempLocal(rightSide));
				new TACStore(tree, temporary, right);
				*/				
				
				int index = 0;
				for( int i = 0; i < leftSide.getUsedItems().size(); ++i )
					if( leftSide.getUsedItems().get(i) ) {						
						doAssignment(left.get(index), leftSide.jjtGetChild(index), new TACSequenceElement(tree, right, i), '=', null);
						index++;
					}		
			}
		}
		
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTSwitchStatement node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {
			TACOperand value = tree.appendChild(0);
			Type type = value.getType();
			TACLabelRef defaultLabel = null;
			TACLabelRef doneLabel = new TACLabelRef(method);
			if( node.hasDefault() )
				defaultLabel = new TACLabelRef(method);
			
			if( !( value.getType() instanceof EnumType ) ) {	
				//first go through and do the conditions
				for( int i = 1; i < node.jjtGetNumChildren(); i += 2 ) {	
					ASTSwitchLabel label = (ASTSwitchLabel) node.jjtGetChild(i);
										
					if( label.isDefault() )											
						label.setLabel(defaultLabel);
					else {	
						tree.appendChild(i); //append label conditions
						
						TACLabelRef matchingCase = new TACLabelRef(method);
						label.setLabel(matchingCase);
						for( int j = 0; j < label.getValues().size(); j++ ) {
							TACOperand operand = label.getValues().get(j);
							TACOperand comparison;
							MethodSignature signature = type.getMatchingMethod("equal", new SequenceType(operand));
							
							if( type.isPrimitive() && signature.getModifiers().isNative() )
								comparison = new TACBinary(tree, value, signature, '=', operand, false );
							else								
								comparison = new TACCall(tree, new TACMethodRef(tree, value, signature), value, operand);
														
							boolean moreConditions = false;
							if( j < label.getValues().size() - 1 ) //more conditions in this label
								moreConditions = true;
							else if( i < node.jjtGetNumChildren() - 4 ) //at least two more labels (of which only one can be default)
								moreConditions = true;
							else if( i < node.jjtGetNumChildren() - 2 && !((ASTSwitchLabel)node.jjtGetChild(i + 2)).isDefault() ) //one more label which isn't default
								moreConditions = true;
							else
								moreConditions = false;
								
							
							TACLabelRef next;
							
							if( moreConditions )
								next = new TACLabelRef(method);
							else if( node.hasDefault() )
								next = defaultLabel;
							else
								next = doneLabel;
							
							new TACBranch(tree, comparison, matchingCase, next);
							if( moreConditions )
								next.new TACLabel(tree);							
						}
					}
				}								
				
				//then go through and add the executable blocks of code to jump to
				for( int i = 1; i < node.jjtGetNumChildren(); i += 2 ) {	
					ASTSwitchLabel label = (ASTSwitchLabel) node.jjtGetChild(i);
					label.getLabel().new TACLabel(tree); //mark start of code
					
					tree.appendChild(i + 1); //add block of code (the child after each label)
					new TACBranch(tree, doneLabel);	
				}
				
				doneLabel.new TACLabel(tree);
			}
			else
				throw new UnsupportedOperationException();		
		}		
		
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTSwitchLabel node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )		
				node.addValue(tree.appendChild(i));		
		
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTIfStatement node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			TACLabelRef trueLabel = new TACLabelRef(method),
					falseLabel = new TACLabelRef(method),
					endLabel = new TACLabelRef(method);
			new TACBranch(tree, tree.appendChild(0), trueLabel, falseLabel);
			trueLabel.new TACLabel(tree);
			tree.appendChild(1);
			new TACBranch(tree, endLabel);
			falseLabel.new TACLabel(tree);
			tree.appendChild(2);
			new TACBranch(tree, endLabel);
			endLabel.new TACLabel(tree);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTWhileStatement node, Boolean secondVisit) throws ShadowException {
		if (secondVisit)
			visitLoop(0, 1, 0, false);
		else
			visitLoop();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTDoStatement node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			visitLoop(1, 0, 1, true);
		else
			visitLoop();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTForeachStatement node, Boolean secondVisit) throws ShadowException {
		if (secondVisit)
			visitForeachLoop(node);
		else
			visitLoop();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTForStatement node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			int condition = 0;
			if (node.jjtGetChild(condition) instanceof ASTForInit)
				tree.appendChild(condition++);
			int update = condition;
			if (node.jjtGetChild(update + 1) instanceof ASTForUpdate)
				update++;
			int body = update + 1;
			visitLoop(condition, body, update, false);
		}
		else
			visitLoop();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTForInit node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStatementExpressionList node, Boolean secondVisit)
			throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTForUpdate node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBreakOrContinueStatement node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			TACLabelRef unreachableLabel = new TACLabelRef(method);
			TACBlock exitBlock;
			
			if( node.getImage().equals("break") ) {
				exitBlock = block.getBreakBlock();
				visitCleanup(exitBlock, null, exitBlock.getBreak());
			}
			else {
				exitBlock = block.getContinueBlock();
				visitCleanup(exitBlock, null, exitBlock.getContinue());
			}
			
			unreachableLabel.new TACLabel(tree);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTReturnStatement node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			TACLabelRef unreachableLabel = new TACLabelRef(method);
			visitCleanup(null, null);
			new TACReturn(tree, node.getType(),
				tree.appendChild(0));
			unreachableLabel.new TACLabel(tree);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTThrowStatement node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			TACLabelRef unreachableLabel = new TACLabelRef(method);
			new TACThrow(tree, tree.appendChild(0));
			unreachableLabel.new TACLabel(tree);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTFinallyStatement node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			tree.appendChild(0);
			if (node.hasFinally()) {
				TACPhi phi = (TACPhi)tree.getLast();
				tree.appendChild(1);
				new TACBranch(tree, phi, phi.getRef());				
			}
			block.getDone().new TACLabel(tree);
			block = block.getParent();			
		}
		else
			block = new TACBlock(tree, block).addDone();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTRecoverStatement node, Boolean secondVisit) throws ShadowException {
		ASTFinallyStatement parent = (ASTFinallyStatement)node.jjtGetParent();
		if (secondVisit) {
			tree.appendChild(0);
			if (node.hasRecover()) {
				tree.appendChild(1);
				new TACBranch(tree, block.getDone());
			}
			if (parent.hasFinally()) {
				block.getDone().new TACLabel(tree);
				method.setHasLandingpad();
				visitCleanup(block.getParent(), block.getDone(),
						block.getParent().getDone());
				block.getLandingpad().new TACLabel(tree);
				TACVariable exception = method.getLocal("_exception");
				new TACLocalStore(tree, exception, new TACLandingpad(tree, block));
				new TACBranch(tree, block.getUnwind());
				block.getUnwind().new TACLabel(tree);
				//new TACUnwind(tree, block, new TACLocalLoad(tree, exception));
				TACLabelRef continueUnwind = block.getParent().getUnwind();
				if (continueUnwind != null)
					visitCleanup(block, block.getUnwind(), continueUnwind);
				else
				{
					visitCleanup(block, block.getUnwind());
					new TACResume(tree, new TACLocalLoad(tree, exception));
				}
				block.getCleanup().new TACLabel(tree);
				block.getCleanupPhi().new TACPhi(tree);
				block = block.getParent();				
			}
		}
		else if (parent.hasFinally())
			block = new TACBlock(tree, block).addLandingpad().addUnwind().
					addCleanup().addDone();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCatchStatements node, Boolean secondVisit) throws ShadowException {
		ASTRecoverStatement parent = (ASTRecoverStatement)node.jjtGetParent();
		if (secondVisit) {
			tree.appendChild(0); //appends try block			
			if( node.getCatches() > 0 ) {			
				TACOperand typeid = new TACSequenceElement(tree, new TACLocalLoad(tree, node.getException()), 1 );
				for (int i = 1; i <= node.getCatches(); i++) {
					Type type = node.jjtGetChild(i).jjtGetChild(0).getType();
					TACLabelRef catchLabel = block.getCatch(i - 1),
							skip = new TACLabelRef(method);
					/*
					new TACBranch(tree, new TACCall(tree, block, new TACMethodRef(
							tree, Type.INT.getMatchingMethod("equal", new SequenceType(Type.INT))), typeid,
							new TACTypeId(tree, new TACClass(tree, type).getClassData())),
							catchLabel, skip);*/
					
					new TACBranch(tree, new TACSame(tree, typeid, new TACTypeId(tree, new TACClass(tree, type).getClassData())),
							catchLabel, skip);
							
					catchLabel.new TACLabel(tree);
					tree.appendChild(i); //append catch i
					new TACBranch(tree, block.getDone());
					skip.new TACLabel(tree);
				}
				
				TACLabelRef continueUnwind = block.getUnwind();
				if (continueUnwind != null)
					new TACBranch(tree, continueUnwind); //try inside of try					
				else
					new TACResume(tree, new TACLocalLoad(tree, node.getException()));
			}			

			if (parent.hasRecover())			
				block.getRecover().new TACLabel(tree);
			
			block = block.getParent();			
		}
		else {
			block = new TACBlock(tree, block);
			if (parent.hasRecover())
				block.addRecover();
			
			block.addCatches(node.getCatches());
		}
								
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCatchStatement node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			tree.appendChild(0);
			
			ASTCatchStatements parent = (ASTCatchStatements) node.jjtGetParent();			
			new TACLocalStore(tree, method.getLocal(
					node.jjtGetChild(0).getImage()), new TACCatch(tree,
					(ExceptionType)node.jjtGetChild(0).getType(), new TACLocalLoad(tree, parent.getException())));
			tree.appendChild(1);
			method.exitScope();
		}
		else
			method.enterScope();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTTryStatement node, Boolean secondVisit)
			throws ShadowException {
		ASTCatchStatements parent = (ASTCatchStatements)node.jjtGetParent();
		if (secondVisit) {
			tree.appendChild(0);
			new TACBranch(tree, block.getDone());
			if (parent.getCatches() > 0 ) {
				method.setHasLandingpad();
				block.getLandingpad().new TACLabel(tree);
				TACVariable exception = method.getLocal("_exception");
				parent.setException(exception);
				new TACLocalStore(tree, exception, new TACLandingpad(tree, block));
				new TACBranch(tree, block.getUnwind());
				block.getUnwind().new TACLabel(tree);
				//new TACTypeId(tree, exception);
				block = block.getParent();				
			}
		}
		else if (parent.getCatches() > 0) {
			block = new TACBlock(tree, block).addLandingpad().addUnwind();
		}
		return POST_CHILDREN;
	}

	@SuppressWarnings("unused")
	private void visitCleanup(TACBlock lastBlock) {
		visitCleanup(lastBlock, null);
	}
	private void visitCleanup(TACBlock lastBlock, TACLabelRef currentLabel) {
		if (lastBlock != null)
			lastBlock = lastBlock.getParent();
		TACBlock currentBlock = block.getCleanupBlock(lastBlock);
		if (currentBlock != lastBlock) {
			TACLabelRef lastLabel = new TACLabelRef(method);
			visitCleanup(currentBlock, lastBlock, currentLabel, lastLabel);
			lastLabel.new TACLabel(tree);
		}
	}
	private void visitCleanup(TACBlock lastBlock, TACLabelRef currentLabel,
			TACLabelRef lastLabel) {
		if (lastBlock != null)
			lastBlock = lastBlock.getParent();
		TACBlock currentBlock = block.getCleanupBlock(lastBlock);
		if (currentBlock != lastBlock)
			visitCleanup(currentBlock, lastBlock, currentLabel, lastLabel);
		else
			new TACBranch(tree, lastLabel);
	}
	
	private void visitCleanup(TACBlock currentBlock, TACBlock lastBlock,
			TACLabelRef currentLabel, TACLabelRef lastLabel) {
		if (currentLabel == null) {
			currentLabel = new TACLabelRef(method);
			new TACBranch(tree, currentLabel);
			currentLabel.new TACLabel(tree);
		}
		TACBlock nextBlock;
		while ((nextBlock = currentBlock.getNextCleanupBlock(lastBlock)) !=
				lastBlock) {
			TACLabelRef nextLabel = new TACLabelRef(method);
			new TACBranch(tree, currentBlock.getCleanup());
			currentBlock.getCleanupPhi().addEdge(nextLabel, currentLabel);
			nextLabel.new TACLabel(tree);
			currentBlock = nextBlock;
			currentLabel = nextLabel;
		}
		new TACBranch(tree, currentBlock.getCleanup());
		currentBlock.getCleanupPhi().addEdge(lastLabel, currentLabel);
	}

	private void visitConstant(TACConstant constantRef, Node constantNode)
			throws ShadowException {
		TACTree saveTree = tree;
		tree = new TACTree(1, constantRef.getBlock());		
		walk(constantNode.jjtGetChild(0));
		tree.done();
		constantRef.append(tree);
		moduleStack.peek().addConstant(constantRef);
		tree = saveTree;
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
		TACLabelRef[] labels = new TACLabelRef[layers];			
		TACLabelRef done = new TACLabelRef(method);
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
				length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', dimensions[j]);
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
				TACLabelRef terminate;					
				if( i == 0 )
					terminate = done;
				else
					terminate = labels[i - 1];
				
				if( type.isPrimitive() ) {
					TACMethodRef width = new TACMethodRef(tree, Type.CLASS.getMatchingMethod("width", new SequenceType()) );
					TACOperand size = new TACBinary(tree, new TACCall(tree, width, class_.getClassData()), Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', length);
					new TACCopyMemory(tree, new TACLocalLoad(tree, copiedArray), oldArray, size);					
					new TACBranch(tree, terminate);		
				}
				else {					
					counters[i] = method.addTempLocal(new SimpleModifiedType(Type.INT));
					new TACLocalStore(tree, counters[i], new TACLiteral(tree, new ShadowInteger(-1))); //starting at -1 allows update and check to happen on the same label
					labels[i] = new TACLabelRef(method);
					
					new TACBranch(tree, labels[i]);
					labels[i].new TACLabel(tree);					
					TACLabelRef body = new TACLabelRef(method);					

					//increment counters[i] by 1
					new TACLocalStore(tree, counters[i], new TACBinary(tree, new TACLocalLoad(tree, counters[i]), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', new TACLiteral(tree, new ShadowInteger(1))));
					TACOperand condition = new TACBinary(tree, new TACLocalLoad(tree, counters[i]), Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), '<', length, true);
					new TACBranch(tree, condition, body, terminate);					
					body.new TACLabel(tree);
					
					//copy old value into new location
					TACOperand element = new TACLoad(tree, new TACArrayRef(tree, oldArray, new TACLocalLoad(tree, counters[i]), false)); //get value at location
					
					if( baseType instanceof InterfaceType )
						element = TACCast.cast(tree, new SimpleModifiedType(Type.OBJECT), element);
										
					TACLabelRef copyLabel = new TACLabelRef(method);
					TACOperand nullCondition = new TACSame(tree, element, new TACLiteral(tree, new ShadowNull(element.getType())));
					new TACBranch(tree, nullCondition, labels[i], copyLabel); //if null, skip entirely, since arrays are calloc'ed
					
					copyLabel.new TACLabel(tree);
					
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
				labels[i] = new TACLabelRef(method);
				
				new TACBranch(tree, labels[i]);
				labels[i].new TACLabel(tree);	
				
				TACLabelRef terminate;
				TACLabelRef body = new TACLabelRef(method);
				if( i == 0 )
					terminate = done;
				else
					terminate = labels[i - 1];

				//increment counters[i] by 1
				new TACLocalStore(tree, counters[i], new TACBinary(tree, new TACLocalLoad(tree, counters[i]), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', new TACLiteral(tree, new ShadowInteger(1))));
				TACOperand condition = new TACBinary(tree, new TACLocalLoad(tree, counters[i]), Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), '<', length, true);
				new TACBranch(tree, condition, body, terminate);					
				body.new TACLabel(tree);
				
				//set up next round
				oldArray = new TACLoad(tree, new TACArrayRef(tree, oldArray, new TACLocalLoad(tree, counters[i]), false)); //get value at location
			}
		}
		
		done.new TACLabel(tree);
		return returnValue;
	}

	private void visitMethod(MethodSignature methodSignature) throws ShadowException {
		TACTree saveTree = tree;
		TACMethod method = this.method = new TACMethod(methodSignature);
		boolean implicitCreate = false;
		if (moduleStack.peek().isClass()) {
			block = method.getBlock();
			tree = new TACTree(1, block);		
			
			if (methodSignature.isNative()) {
				method.addParameters(tree);
				walk(methodSignature.getNode().jjtGetChild(0).jjtGetChild(0));
			}		
			else if( methodSignature.getSymbol().equals("copy") && !methodSignature.isWrapper() ) {
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
					
					TACLabelRef copyLabel = new TACLabelRef(method),
							returnLabel = new TACLabelRef(method);
					
					new TACBranch(tree, test, returnLabel, copyLabel);
					copyLabel.new TACLabel(tree);
					
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
						TACLabelRef done = new TACLabelRef(method);
						TACLabelRef body = new TACLabelRef(method);
						TACLabelRef condition = new TACLabelRef(method);
						
						TACVariable i = method.addTempLocal(new SimpleModifiedType(Type.INT));
						new TACLocalStore(tree, i, new TACLiteral(tree, new ShadowInteger(0)));
						new TACBranch(tree, condition);
						
						//start loop
						condition.new TACLabel(tree);
						
						TACOperand loop = new TACBinary(tree, new TACLocalLoad(tree, i), Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), '<', size, true );
						new TACBranch(tree, loop, body, done);
						body.new TACLabel(tree);
						
						SequenceType indexArguments = new SequenceType();
						indexArguments.add(i);					
						TACMethodRef indexLoad = new TACMethodRef(tree, genericArray.getMatchingMethod("index", indexArguments));
						indexArguments.add(genericArray.getTypeParameters().get(0));
						TACMethodRef indexStore = new TACMethodRef(tree, genericArray.getMatchingMethod("index", indexArguments));
						
						
						TACOperand value = new TACCall(tree, indexLoad, this_, new TACLocalLoad(tree, i));
						
						TACLabelRef skipLabel = new TACLabelRef(method);
						TACLabelRef makeCopyLabel = new TACLabelRef(method);
						TACOperand isNull = new TACSame(tree, value, new TACLiteral(tree, new ShadowNull(value.getType())));
						new TACBranch(tree, isNull, skipLabel, makeCopyLabel);
						
						makeCopyLabel.new TACLabel(tree);
						
						TACMethodRef copy = new TACMethodRef(tree, value, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
						
						value = new TACCall(tree, copy, copy.getPrefix(), map);
						new TACCall(tree, indexStore, duplicate, new TACLocalLoad(tree, i), value);						
						new TACBranch(tree, skipLabel);
						
						skipLabel.new TACLabel(tree);						
						
						new TACLocalStore(tree, i, new TACBinary(tree, new TACLocalLoad(tree, i), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', new TACLiteral(tree, new ShadowInteger(1)), false ));
						new TACBranch(tree, condition);					
						
						done.new TACLabel(tree);
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
								
								TACLabelRef copyField = new TACLabelRef(method);
								TACLabelRef skipField = new TACLabelRef(method);
								
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
									
									TACOperand nullCondition = new TACSame(tree, field, new TACLiteral(tree, new ShadowNull(field.getType())));
									new TACBranch(tree, nullCondition, skipField, copyField); //if null, skip

									copyField.new TACLabel(tree);
									copiedField = new TACCall(tree, copyMethod, field, map);

									if( entryType.getType() instanceof InterfaceType )
										//and then cast back to interface
										copiedField = TACCast.cast(tree, newField, copiedField);																
								}
								
								//store copied value
								new TACStore(tree, newField, copiedField);		
								new TACBranch(tree, skipField);								
								
								skipField.new TACLabel(tree);
							}
						}
					}
					
					new TACReturn(tree, methodSignature.getFullReturnTypes(), duplicate);
					
					returnLabel.new TACLabel(tree);
					
					indexMethod = new TACMethodRef(tree, Type.ADDRESS_MAP.getMatchingMethod("index", new SequenceType(Type.ULONG)) );
					TACOperand index = new TACCall(tree, indexMethod, map, address );
					TACOperand existingObject = new TACLongToPointer(tree, index, new SimpleModifiedType(type));
					new TACReturn(tree, methodSignature.getFullReturnTypes(), existingObject);					
				}
			}
			//Gets and sets that were created by default (that's why they have a node with a bogus ID)
			else if( methodSignature.getNode().getID() == -1 && (methodSignature.isGet() || methodSignature.isSet() )) { 			
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
			else { // Regular method or create
				method.addParameters(tree);
				
				if( methodSignature.isCreate()) {
					ASTCreateDeclaration declaration = (ASTCreateDeclaration) methodSignature.getNode();
					implicitCreate = !declaration.hasExplicitCreate();
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
					for( Node field : ((ClassType)(methodSignature.getOuter())).getFieldList() ) 
						if (!field.getModifiers().isConstant() && !(field.getType() instanceof SingletonType))
							walk(field);
				}				
				
				walk(methodSignature.getNode());
			}
			
			
			tree.done();
			method.append(tree);
		}
		moduleStack.peek().addMethod(method);
		block = null;
		this.method = null;
		tree = saveTree;
	}
	
	private void visitBooleanOperation(Node node) {
		int child = 0;		
		String image = node.getImage();
		TACOperand current = null;
		while( current == null )
			current = tree.appendChild(child++);
		
		for( int index = 0; index < node.getImage().length(); index++ ) {
			char op = image.charAt(index);
			TACOperand next = null;
			while( next == null )
				next = tree.appendChild(child++);
			
			TACBinary.Boolean connector = null;
			switch( op ) {
			case 'a': connector = TACBinary.Boolean.AND; break;
			case 'o': connector = TACBinary.Boolean.OR; break;
			case 'x': connector = TACBinary.Boolean.XOR; break;
			default: throw new IllegalArgumentException("Operator " + op + " is not a valid boolean operator");			
			}
									
			current = new TACBinary(tree, current, connector, next);
		}	
	}
	
	private void visitBinaryOperation(OperationNode node) {	
		int child = 0;		
		String image = node.getImage();
		TACOperand current = null;
		while( current == null )
			current = tree.appendChild(child++);
		
		for( int index = 0; index < node.getOperations().size(); index++ ) {
			char op = image.charAt(index);
			TACOperand next = null;
			while( next == null )
				next = tree.appendChild(child++);
			//BinaryOperation operation = new BinaryOperation( current, next, c );
			MethodSignature signature = node.getOperations().get(index);
			boolean isCompare = ( op == '<' || op == '>' || op == '{' || op == '}' );
			Type currentType = resolveType(current.getType());
			if( currentType.isPrimitive() && signature.getModifiers().isNative() ) //operation based on method
				current = new TACBinary(tree, current, signature, op, next, isCompare );
			else
			{	
				//comparisons will always give positive, negative or zero integer
				//must be compared to 0 with regular int comparison to work
				if( isCompare )
				{
					TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.INT));
					new TACLocalStore(tree, var, new TACCall(tree, new TACMethodRef(tree, current, signature), current, next));		
					current = new TACLocalLoad(tree, var);					
					current = new TACBinary(tree, current, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), op, new TACLiteral(tree, new ShadowInteger(0)), true );
				}
				else
				{
					TACVariable var = method.addTempLocal(node);
					new TACLocalStore(tree, var, new TACCall(tree, new TACMethodRef(tree, current, signature), current, next));		
					current = new TACLocalLoad(tree, var);
				}
			}			
		}
	}

	private void visitLoop()
	{
		block = new TACBlock(tree, block).addBreak().addContinue();
	}
	
	private void initializeArray(TACOperand array, MethodSignature create, List<TACOperand> params, TACOperand defaultValue)
	{	
		//nothing to do
		if( create == null && defaultValue == null )
			return;
		
		ArrayType arrayType = (ArrayType) array.getType();
		
		TACLabelRef bodyLabel = new TACLabelRef(method),
					updateLabel = new TACLabelRef(method),
					conditionLabel = new TACLabelRef(method),
					endLabel = new TACLabelRef(method);
		
		//make iterator (int index)
		TACVariable iterator = method.addTempLocal(new SimpleModifiedType(Type.INT));
		new TACLocalStore(tree, iterator, new TACLiteral(tree, new ShadowInteger(0)));
		TACOperand length = new TACLength(tree, array, 0);			
		for (int i = 1; i < arrayType.getDimensions(); i++)
			length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', new TACLength(tree, array, i));			
		
		new TACBranch(tree, conditionLabel);  //init is done, jump to condition
		
		bodyLabel.new TACLabel(tree);
		
		//put initialization before main body
		TACArrayRef location = new TACArrayRef(tree, array, new TACLocalLoad(tree, iterator), false);		
				
		if( create != null)
			new TACStore(tree, location, callCreate( create, params, create.getOuter()));
		else
			new TACStore(tree, location, defaultValue);		
		
		new TACBranch(tree, updateLabel);
		updateLabel.new TACLabel(tree);			
		
		//increment iterator							
		TACOperand value = new TACBinary(tree, new TACLocalLoad(tree, iterator), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', new TACLiteral(tree, new ShadowInteger(1)));
		new TACLocalStore(tree, iterator, value);	
		
		new TACBranch(tree, conditionLabel);
		conditionLabel.new TACLabel(tree);
		
		//check if iterator < array length
		value = new TACLocalLoad(tree, iterator);			
		TACOperand condition = new TACBinary(tree, value, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), '<', length, true );
		
		new TACBranch(tree, condition, bodyLabel, endLabel);		
		endLabel.new TACLabel(tree);
	}
	
	private void visitForeachLoop(ASTForeachStatement node)
	{	
		ASTForeachInit init = (ASTForeachInit) node.jjtGetChild(0);		
		Type type = init.getCollectionType(); 		
		TACOperand collection = tree.appendChild(0); //last thing from init
		TACVariable variable = init.getVariable();
		TACVariable iterator;
		TACOperand condition;
		TACOperand value;
		TACLabelRef bodyLabel = new TACLabelRef(method),				
				endLabel = block.getBreak();
		
		//optimization for arrays
		if( type instanceof ArrayType ) {	
			ArrayType arrayType = (ArrayType) type;
			TACLabelRef updateLabel = block.getContinue(),
						conditionLabel = new TACLabelRef(method);

			//make iterator (int index)
			iterator = method.addTempLocal(new SimpleModifiedType(Type.INT));
			new TACLocalStore(tree, iterator, new TACLiteral(tree, new ShadowInteger(0)));
			TACOperand length = new TACLength(tree, collection, 0);			
			for (int i = 1; i < arrayType.getDimensions(); i++)
				length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', new TACLength(tree, collection, i), false);			
			
			new TACBranch(tree, conditionLabel);  //init is done, jump to condition
			
			bodyLabel.new TACLabel(tree);
			
			//put variable update before main body
			value = new TACLoad(tree, new TACArrayRef(tree, collection, new TACLocalLoad(tree, iterator), false));
			new TACLocalStore(tree, variable, value);
			
			tree.appendChild(1); //body
			
			new TACBranch(tree, updateLabel);
			updateLabel.new TACLabel(tree);			
			
			//increment iterator
			value = new TACLocalLoad(tree, iterator);					
			value = new TACBinary(tree, value, Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', new TACLiteral(tree, new ShadowInteger(1)), false );
			new TACLocalStore(tree, iterator, value);	
			
			new TACBranch(tree, conditionLabel);
			conditionLabel.new TACLabel(tree);
			
			//check if iterator < array length
			value = new TACLocalLoad(tree, iterator);			
			condition = new TACBinary(tree, value, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), '<', length, true );
			
			new TACBranch(tree, condition, bodyLabel, endLabel);		
			endLabel.new TACLabel(tree);
		}
		else {	
			TACLabelRef conditionLabel = block.getContinue();
			MethodSignature signature;
			
			//get iterator
			signature = type.getMatchingMethod("iterator", new SequenceType());
			ModifiedType iteratorType = signature.getReturnTypes().get(0);
			iterator = method.addTempLocal(iteratorType);
			TACMethodRef getIterator = new TACMethodRef(tree, collection, signature);
			new TACLocalStore(tree, iterator, new TACCall(tree, getIterator, getIterator.getPrefix()));
			
			new TACBranch(tree, conditionLabel);  //init is done, jump to condition
						
			bodyLabel.new TACLabel(tree);		
			
			//put variable update before main body
			signature = iteratorType.getType().getMatchingMethod("next", new SequenceType());
			TACMethodRef next = new TACMethodRef(tree, new TACLocalLoad(tree, iterator), signature);
			new TACLocalStore(tree, variable, new TACCall(tree, next, new TACLocalLoad(tree, iterator))); //internally updates iterator
			
			tree.appendChild(1); //body
						
			new TACBranch(tree, conditionLabel);
			conditionLabel.new TACLabel(tree);
			
			//check if iterator has next
			signature = iteratorType.getType().getMatchingMethod("hasNext", new SequenceType());
			TACMethodRef hasNext = new TACMethodRef(tree, new TACLocalLoad(tree, iterator), signature);
			condition = new TACCall(tree, hasNext, new TACLocalLoad(tree, iterator));
			
			new TACBranch(tree, condition, bodyLabel, endLabel);		
			endLabel.new TACLabel(tree);
		}			

		block = block.getParent();		
	}
	
	private void visitLoop(int condition, int body, int update, boolean force)
	{
		TACLabelRef bodyLabel = new TACLabelRef(method),
				updateLabel = block.getContinue(),
				conditionLabel = condition != update ?
						new TACLabelRef(method) : updateLabel,
				endLabel = block.getBreak();
		new TACBranch(tree, force ? bodyLabel : conditionLabel);
		bodyLabel.new TACLabel(tree);
		tree.appendChild(body);
		if (condition != update)
		{
			new TACBranch(tree, updateLabel);
			updateLabel.new TACLabel(tree);
			tree.appendChild(update);
		}
		new TACBranch(tree, conditionLabel);
		conditionLabel.new TACLabel(tree);
		new TACBranch(tree, tree.appendChild(condition), bodyLabel, endLabel);
		endLabel.new TACLabel(tree);
		block = block.getParent();		
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
		//sizes = sizes.subList(type.getDimensions(), sizes.size());
		sizes = sizes.subList(start, end);
		if (!sizes.isEmpty())
		{
			TACVariable index = method.addTempLocal(new SimpleModifiedType(Type.INT));
			new TACLocalStore(tree, index, new TACLiteral(tree, new ShadowInteger(0)));
			TACLabelRef bodyLabel = new TACLabelRef(method),
					condLabel = new TACLabelRef(method),
					endLabel = new TACLabelRef(method);
			new TACBranch(tree, condLabel);
			bodyLabel.new TACLabel(tree);
			new TACStore(tree, new TACArrayRef(tree, alloc, new TACLocalLoad(tree, index), false),
					visitArrayAllocation((ArrayType)type.getBaseType(), new TACClass(tree, (ArrayType)type.getBaseType()), sizes, create, params, defaultValue));
			new TACLocalStore(tree, index, new TACBinary(tree, new TACLocalLoad(tree, index), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+',
					new TACLiteral(tree, new ShadowInteger(1))));
			new TACBranch(tree, condLabel);
			condLabel.new TACLabel(tree);
			new TACBranch(tree, new TACSame(tree, new TACLocalLoad(tree, index), alloc.getTotalSize()),
					endLabel, bodyLabel);
			endLabel.new TACLabel(tree);
		}
		else {
			//fill with values			
			initializeArray(alloc, create, params, defaultValue);			
		}
		
		return new TACNodeRef(tree, alloc);		
	}
	
	private TACOperand getDefaultValue(ModifiedType type)
	{
		if (type.getType().equals(Type.BOOLEAN))
			return new TACLiteral(tree, new ShadowBoolean(false));
		if (type.getType().equals(Type.CODE))
			return new TACLiteral(tree, ShadowCode.parseCode("'\0'"));
		if (type.getType().equals(Type.UBYTE))
			return new TACLiteral(tree, ShadowInteger.parseNumber("0uy"));
		if (type.getType().equals(Type.BYTE))
			return new TACLiteral(tree, ShadowInteger.parseNumber("0y"));
		if (type.getType().equals(Type.USHORT))
			return new TACLiteral(tree, ShadowInteger.parseNumber("0us"));
		if (type.getType().equals(Type.SHORT))
			return new TACLiteral(tree, ShadowInteger.parseNumber("0s"));
		if (type.getType().equals(Type.UINT))
			return new TACLiteral(tree, ShadowInteger.parseNumber("0ui"));
		if (type.getType().equals(Type.INT))
			return new TACLiteral(tree, ShadowInteger.parseNumber("0i"));
		if (type.getType().equals(Type.ULONG))
			return new TACLiteral(tree, ShadowInteger.parseNumber("0ul"));
		if (type.getType().equals(Type.LONG))
			return new TACLiteral(tree, ShadowInteger.parseNumber("0l"));
		if( type.getType().equals(Type.DOUBLE))
			return new TACLiteral(tree, ShadowDouble.parseDouble("0.0"));
		if( type.getType().equals(Type.FLOAT))
			return new TACLiteral(tree, ShadowFloat.parseFloat("0.0f"));		
//		if (!type.getModifiers().isNullable())
//			throw new IllegalArgumentException();
		if( type instanceof ClassType )
			return new TACLiteral(tree, new ShadowNull(type.getType()));
		
		return TACCast.cast(tree, type, new TACLiteral(tree, new ShadowNull(Type.NULL)));
	}

	@Override
	public Object visit(ASTBrackets node, Boolean secondVisit)
			throws ShadowException
	{
		//builds onto a type only, no action required
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTDestroy node, Boolean secondVisit)
			throws ShadowException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTForeachInit node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {
			String name = node.getImage();
			node.setVariable(method.addLocal(node, name));
			//append last child (containing the collection)
			tree.appendChild(node.jjtGetNumChildren() - 1);
		}
		return POST_CHILDREN;
	}
	@Override
	public Object visit(ASTInlineResults node, Boolean secondVisit) throws ShadowException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTSequenceRightSide node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			List<TACOperand> sequence =
					new ArrayList<TACOperand>(node.getType().size());			
			for (int index = 0; index < node.getType().size(); index++ )
				sequence.add(tree.appendChild(index));
			
			new TACSequence(tree, sequence);	
		}
			
		return POST_CHILDREN; 
	}	
	
	@Override
	public Object visit(ASTSequenceLeftSide node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			
			SequenceType sequenceType = node.getType();
			List<TACOperand> sequence =
					new ArrayList<TACOperand>(sequenceType.size());
			int index = 0;
			for (ModifiedType type : sequenceType)
				if( type != null )
					sequence.add(tree.appendChild(index++));
					//sequence.add(tree.deleteChild(index++)); //remove it but put it in the sequence for safe-keeping
			
			/*
				else
					sequence.add(new TACVariableRef(tree,
							method.addTempLocal(new SimpleModifiedType(
									Type.OBJECT,
									new Modifiers(Modifiers.NULLABLE | Modifiers.READONLY))))); //kind of a hack, but a nullable, readonly object can take on anything
			*/
			new TACSequence(tree, sequence);	
		}
			
		return POST_CHILDREN; 		
	}
	@Override
	public Object visit(ASTSequenceVariable node, Boolean secondVisit) throws ShadowException {
		if (secondVisit) {
			String name = node.getImage();
			TACVariable var = method.addLocal(node, name);
			new TACLocalStore(tree, var, new TACLiteral(tree, new ShadowUndefined(node.getType())));
		}
		return POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTRightSide node, Boolean secondVisit) throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCopyExpression node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {
			TACOperand value = tree.appendChild(0);
			prefix = value;
			Type type = node.getType();
			
			if( !type.getModifiers().isImmutable() ) { //if immutable, do nothing, the old one is fine
				TACNewObject object = new TACNewObject(tree, Type.ADDRESS_MAP );
				TACMethodRef create = new TACMethodRef(tree, Type.ADDRESS_MAP.getMatchingMethod("create", new SequenceType()) );
				TACOperand map = new TACCall(tree, create, object);
				
				if( type instanceof ArrayType ) {
					prefix = copyArray(value, map);					
					new TACNodeRef(tree, prefix);
				}
				else {
					TACMethodRef copyMethod;
					TACVariable result = method.addTempLocal(node);
					TACOperand data = value;					
					
					TACLabelRef nullLabel = new TACLabelRef(method);
					TACLabelRef doneLabel = new TACLabelRef(method);
					TACLabelRef copyLabel = new TACLabelRef(method);
					
					if( type instanceof InterfaceType )				
					{	
						//cast converts from interface to object
						data = TACCast.cast(tree, new SimpleModifiedType(Type.OBJECT), data);
						TACOperand nullCondition = new TACSame(tree, data, new TACLiteral(tree, new ShadowNull(data.getType())));
						new TACBranch(tree, nullCondition, nullLabel, copyLabel);
						copyLabel.new TACLabel(tree);
						copyMethod = new TACMethodRef(tree, data, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));						
					}
					else
					{
						TACOperand nullCondition = new TACSame(tree, data, new TACLiteral(tree, new ShadowNull(data.getType())));						
						new TACBranch(tree, nullCondition, nullLabel, copyLabel);
						copyLabel.new TACLabel(tree);
						copyMethod  = new TACMethodRef(tree, data, type.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));						
					}
					
					TACOperand copy = new TACCall(tree, copyMethod, data, map);

					if( type instanceof InterfaceType )
						//and then a cast back to interface
						copy = TACCast.cast(tree, node, copy);
					
					new TACLocalStore(tree, result, copy);					
					new TACBranch(tree, doneLabel);	
					
					nullLabel.new TACLabel(tree);
					
					new TACLocalStore(tree, result, value);
					new TACBranch(tree, doneLabel);
					
					doneLabel.new TACLabel(tree);
					prefix = new TACLocalLoad(tree, result);
				}
			}			
		}	
		
		
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTArrayDimensions node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {
			List<TACOperand> list = new ArrayList<TACOperand>(tree.getNumChildren());
			for (int i = 0; i < tree.getNumChildren(); i++)
				list.add(tree.appendChild(i));
			node.setIndexes(list);
		}	
		
		return  POST_CHILDREN;
	}
}
