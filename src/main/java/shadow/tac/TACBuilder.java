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
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowValue;
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
import shadow.tac.nodes.TACDestination;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACGenericArrayRef;
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
	private boolean explicitSuper, implicitCreate;
	private TACVariable identifier;
	private TACBlock block;
	private Deque<List<TACOperand>> indexStack;
	private Deque<TACModule> moduleStack = new ArrayDeque<TACModule>();
	
	public TACModule build(Node node) throws ShadowException {		
		tree = new TACTree();				
		method = null;
		prefix = null;
		explicitSuper = false;
		implicitCreate = false;
		identifier = null;
		block = null;
		indexStack = new ArrayDeque<List<TACOperand>>();
		TACNode.setBuilder(this);  //ugly, non-typesafe approach
		walk(node);
		return moduleStack.pop();
	}
	
	public TACMethod getMethod() {
		return method;
	}
	
	public TACBlock getBlock() {
		return block;
	}
	
	public void walk(Node node) throws ShadowException
	{
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
		return node.jjtAccept(this, secondVisit);
	}
	
	@Override
	public Object visit(SimpleNode node, Boolean secondVisit)
			throws ShadowException {
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
			Boolean secondVisit) throws ShadowException
	{
		Type type = node.getType();
		TACModule newModule = new TACModule(type);
		
		if( !moduleStack.isEmpty() )
			moduleStack.peek().addInnerClass(newModule);
		moduleStack.push(newModule);
		
		//dummy method and block for constant building 
		method = new TACMethod( new MethodSignature(new MethodType(), "", type, null));
		block = new TACBlock(tree, block);
		for (Node constant : type.getFields().values())
			if (constant.getModifiers().isConstant())
				visitConstant(new TACConstant(type,
						constant.getImage()), constant);
		block = block.getParent();
		
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
	public Object visit(ASTExtendsList node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTImplementsList node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTVersion node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumBody node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumConstant node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeBound node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBodyDeclaration node,
			Boolean secondVisit) throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTVariableDeclarator node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit && !(node.getType() instanceof SingletonType)) { //no records are needed for Singletons
			String name = node.getImage();
			TACReference ref;
			if (node.isField())
				ref = new TACFieldRef(tree,
						new TACVariableRef(tree, method.getThis()), name);
			else
				ref = new TACVariableRef(tree, method.addLocal(node, name));
			if (node.jjtGetNumChildren() == 0)
				new TACStore(tree, ref, getDefaultValue(node));
			else
				new TACStore(tree, ref, tree.appendChild(0));
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTArrayInitializer node, Boolean secondVisit)
			throws ShadowException {
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
			TACLabelRef initLabel = new TACLabelRef(tree),
					doneLabel = new TACLabelRef(tree);
			TACReference instance = new TACSingletonRef(tree, type);
			new TACBranch(tree, new TACSame(tree, instance, new TACLiteral(tree, ShadowValue.NULL)), initLabel, doneLabel);
			initLabel.new TACLabel(tree);
			
			TACMethodRef methodRef = new TACMethodRef(tree, type.getMethods("create").get(0));
			TACOperand object = new TACNewObject(tree, type);
			TACCall call = new TACCall(tree, block, methodRef, object );			
			new TACStore(tree, instance, call ); 				
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
		}
	}
	
	@Override
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {		
			TACNode last = tree.appendAllChildren();
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
		}
		else //first visit					
			initializeSingletons(node);
		
		return POST_CHILDREN;
	}
	@Override
	public Object visit(ASTMethodDeclarator node, Boolean secondVisit)
			throws ShadowException {
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCreateDeclaration node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			TACNode last = tree.appendAllChildren();
			if (last instanceof TACLabel &&
					last.getPrevious() instanceof TACReturn)
				last.remove();
			else
				new TACReturn(tree, method.getMethod().getFullReturnTypes(),
						new TACVariableRef(tree, method.getThis()));
		}
		else
			initializeSingletons(node);
		return POST_CHILDREN;
	}
	@Override
	public Object visit(ASTCreateDeclarator node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTDestroyDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit) {
			TACNode last = tree.appendAllChildren();
			if (last instanceof TACLabel &&
					last.getPrevious() instanceof TACReturn)
				last.remove();
			else
				new TACReturn(tree, method.getMethod().getFullReturnTypes());
		}
		else
			initializeSingletons(node);
		
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTExplicitCreateInvocation node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			implicitCreate = false;
			boolean isSuper = node.getImage().equals("super");
			ClassType thisType = (ClassType)method.getMethod().getOuter();
			List<TACOperand> params = new ArrayList<TACOperand>();
			params.add(new TACVariableRef(tree, method.getThis()));
			
			if( (!isSuper && thisType.hasOuter()) || (isSuper && thisType.getExtendType().hasOuter()) )
			{
				//Type outerType = thisType.getOuter();
				TACVariable outer = method.getParameter("_outer");
				params.add(new TACVariableRef(tree, outer));
			}
			
			
			//TODO: test explicit constructor invocations
			//shouldn't need any special generic stuff, right?
			/*
			if( isSuper )
			{
			
				
			}
			else if( thisType.isParameterized()  )
			{
				//Collection<TACVariable> parameters = method.getParameters();
				TACClass _class = new TACClass(tree, thisType, method);
				params.add(_class.getClassData());
				params.add(_class.getMethodTable());
				//params.add(new TACVariableRef(tree, (TACVariable)(parameters.toArray()[1])));
			}
			*/
			for (int i = 0; i < tree.getNumChildren(); i++)
			{
				TACOperand param = tree.appendChild(i); 
				params.add(param);
				//type.addParameter(param);
			}
			new TACCall(tree, block, new TACMethodRef(tree, node.getMethodSignature()),
					params);		
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTFormalParameters node, Boolean secondVisit)
			throws ShadowException
	{
		return NO_CHILDREN;
	}
	@Override
	public Object visit(ASTFormalParameter node, Boolean secondVisit)
			throws ShadowException
	{	
		//ASTFormalParameters are NOT visited
		//Parameters for methods are handled separately
		//The only thing that comes in here are the declarations
		//in catch blocks
		if (secondVisit)
			new TACVariableRef(tree,
					method.addLocal(node, node.getImage()));
		return POST_CHILDREN;
		
		//return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTReferenceType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFunctionType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceTypeSuffix node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeArguments node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTResultType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTResultTypes node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTName node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTUnqualifiedName node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTNameList node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{	
			TACMethodRef methodRef;
			MethodSignature signature;
			
			TACOperand left = tree.appendChild(0);			
			TACOperand right = tree.appendChild(2);
			char operation = node.jjtGetChild(1).getImage().charAt(0);
			
			if( node.jjtGetChild(0).getType() instanceof PropertyType )
			{					
				PropertyType propertyType = (PropertyType) node.jjtGetChild(0).getType();
				List<TACOperand> parameters = new ArrayList<TACOperand>();
				
				parameters.add( left );
				if( propertyType instanceof SubscriptType )
				{					
					parameters.addAll(indexStack.pop()); //there should only be one index in this list (not needed for properties)
				}
				
				if (operation != '=')
				{	
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
					else
					{
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
			else
			{	
				TACReference var = (TACReference)left;	
				signature = node.getOperations().get(0);
				if (operation != '=')
				{	
					if( left.getType().isPrimitive() && signature.getModifiers().isNative() )
						right = new TACBinary(tree, var, signature, operation, right );
					else
					{
						TACVariableRef temp = new TACVariableRef(tree,
								method.addTempLocal(signature.getReturnTypes().get(0)));
						methodRef = new TACMethodRef(tree, left, signature);
						new TACStore(tree, temp, new TACCall(tree, block, methodRef, methodRef.getPrefix(), right));		
						right = new TACLoad(tree, temp);						
					}
				}
				new TACStore(tree, var, right);				
			}
		}			
			
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTAssignmentOperator node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef trueLabel = new TACLabelRef(tree),
					falseLabel = new TACLabelRef(tree),
					doneLabel = new TACLabelRef(tree);
			TACReference var = new TACVariableRef(tree,
					method.addTempLocal(node));
			new TACBranch(tree, tree.appendChild(0), trueLabel, falseLabel);
			trueLabel.new TACLabel(tree);
			new TACStore(tree, var, tree.appendChild(1));
			new TACBranch(tree, doneLabel);
			falseLabel.new TACLabel(tree);
			new TACStore(tree, var, tree.appendChild(2));
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLoad(tree, var);
		}
		return node.jjtGetNumChildren() == 1 ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCoalesceExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef nonnullLabel = new TACLabelRef(tree),
					nullLabel = new TACLabelRef(tree),
					doneLabel = new TACLabelRef(tree);
			TACReference var = new TACVariableRef(tree,
					method.addTempLocal(node));
			TACOperand value = tree.appendChild(0);
			new TACBranch(tree, new TACSame(tree, value, new TACLiteral(tree, ShadowValue.NULL)), nullLabel, nonnullLabel);
			nonnullLabel.new TACLabel(tree);
			new TACStore(tree, var, value);
			new TACBranch(tree, doneLabel);
			nullLabel.new TACLabel(tree);
			new TACStore(tree, var, tree.appendChild(1));
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLoad(tree, var);
		}
		return node.jjtGetNumChildren() == 1 ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalOrExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef doneLabel = new TACLabelRef(tree);
			TACReference var = new TACVariableRef(tree,
					method.addTempLocal(node));
			TACOperand value = tree.appendChild(0);
			new TACStore(tree, var, value);
			for (int i = 1; i < tree.getNumChildren(); i++)
			{
				TACLabelRef nextLabel = new TACLabelRef(tree);
				new TACBranch(tree, value, doneLabel, nextLabel);
				nextLabel.new TACLabel(tree);
				new TACStore(tree, var, value = tree.appendChild(i));
			}
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLoad(tree, var);
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalExclusiveOrExpression node,
			Boolean secondVisit) throws ShadowException
	{
		if (secondVisit)
			visitBooleanOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalAndExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef doneLabel = new TACLabelRef(tree);
			TACReference var = new TACVariableRef(tree,
					method.addTempLocal(node));
			TACOperand value = tree.appendChild(0);
			new TACStore(tree, var, value);
			for (int i = 1; i < tree.getNumChildren(); i++)
			{
				TACLabelRef nextLabel = new TACLabelRef(tree);
				new TACBranch(tree, value, nextLabel, doneLabel);
				nextLabel.new TACLabel(tree);
				new TACStore(tree, var, value = tree.appendChild(i));
			}
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLoad(tree, var);
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseOrExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseExclusiveOrExpression node,
			Boolean secondVisit) throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseAndExpression node, Boolean secondVisit)
			throws ShadowException
	{
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
				/*
				Node current = node.jjtGetChild(index - 1);
				Type currentType = current.getType();
				Node next = node.jjtGetChild(index);
				Type nextType = next.getType();
				*/				
				if (c == '=' || c == '!') { //== or !=									
					if (currentType.isPrimitive() &&
						nextType.isPrimitive() /*&&
						(currentType.isSubtype(nextType) || nextType.isSubtype(currentType) )*/) //if not, methods are needed					
						value = new TACSame(tree, value, other);					
					else {	
						//no nullables allowed
						TACVariableRef var = new TACVariableRef(tree,
								method.addTempLocal(node));
						Type valueType = resolveType(value.getType());
						MethodSignature signature = valueType.getMatchingMethod("equal", new SequenceType(other));												
						new TACStore(tree, var, new TACCall(tree, block,
								new TACMethodRef(tree, value, signature),
								value, other));
						value = new TACLoad(tree, var);
					}						
				}
				else { //=== or !==				
					boolean valueNullable = value.getModifiers().isNullable();
					boolean otherNullable = other.getModifiers().isNullable();
					if( currentType.isPrimitive() && nextType.isPrimitive() && ( valueNullable || otherNullable )) {
						TACReference var = new TACVariableRef(tree,	method.addTempLocal(new SimpleModifiedType(Type.BOOLEAN)));
						TACLabelRef done = new TACLabelRef(tree);
						
						if( valueNullable && otherNullable ) {							
							TACOperand valueNull = new TACSame(tree, value, new TACLiteral(tree, ShadowValue.NULL));
							TACOperand otherNull = new TACSame(tree, other, new TACLiteral(tree, ShadowValue.NULL));							
							TACOperand bothNull = new TACBinary(tree, valueNull, TACBinary.Boolean.AND, otherNull );
							TACOperand eitherNull = new TACBinary(tree, valueNull, TACBinary.Boolean.OR, otherNull );							
							TACLabelRef notBothNull = new TACLabelRef(tree);												
							TACLabelRef noNull = new TACLabelRef(tree);
							
							new TACStore(tree, var, bothNull);
							new TACBranch(tree, bothNull, done, notBothNull); //var will be true (both null)
							
							notBothNull.new TACLabel(tree);
							new TACBranch(tree, eitherNull, done, noNull); //var will be false (one but not both null)
														
							noNull.new TACLabel(tree);
							new TACStore(tree, var, new TACSame(tree,
									new TACCast(tree, new SimpleModifiedType(value.getType()), value),
									new TACCast(tree, new SimpleModifiedType(other.getType()), other)));
							new TACBranch(tree, done);
						}
						else if( valueNullable ) { //only value nullable							
							TACOperand valueNull = new TACSame(tree, value, new TACLiteral(tree, ShadowValue.NULL));							
							TACLabelRef oneNull = new TACLabelRef(tree);												
							TACLabelRef noNull = new TACLabelRef(tree);														
							new TACBranch(tree, valueNull, oneNull, noNull);
							oneNull.new TACLabel(tree);
							new TACStore(tree, var, new TACLiteral(tree, new ShadowBoolean(false)));		
							new TACBranch(tree, done);														
							noNull.new TACLabel(tree);
							new TACStore(tree, var, new TACSame(tree,
									new TACCast(tree, new SimpleModifiedType(value.getType()), value),
									other));
							new TACBranch(tree, done);							
						}
						else { //only other nullable 
							TACOperand otherNull = new TACSame(tree, other, new TACLiteral(tree, ShadowValue.NULL));							
							TACLabelRef oneNull = new TACLabelRef(tree);												
							TACLabelRef noNull = new TACLabelRef(tree);														
							new TACBranch(tree, otherNull, oneNull, noNull);
							oneNull.new TACLabel(tree);
							new TACStore(tree, var, new TACLiteral(tree, new ShadowBoolean(false)));									
							new TACBranch(tree, done);														
							noNull.new TACLabel(tree);
							new TACStore(tree, var, new TACSame(tree,
									value,
									new TACCast(tree, new SimpleModifiedType(other.getType()), other)));
							new TACBranch(tree, done);
						}
						
						done.new TACLabel(tree);
						value = new TACLoad(tree, var);
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
			throws ShadowException
	{		
		if (secondVisit)
		{
			TACOperand value = tree.appendChild(0);						
			Type comparisonType = node.jjtGetChild(1).getType();
			
			if( comparisonType instanceof ArrayType )
			{
				ArrayType arrayType = (ArrayType) comparisonType;
				comparisonType = arrayType.convertToGeneric();
			}
			
			TACOperand comparisonClass = new TACClass(tree, comparisonType).getClassData();

			//get class from object
			TACMethodRef methodRef = new TACMethodRef(tree, value,
					Type.OBJECT.getMatchingMethod("getClass", new SequenceType()));						
			TACOperand valueClass = new TACCall(tree, block, methodRef, methodRef.getPrefix());

			methodRef = new TACMethodRef(tree, valueClass,
					Type.CLASS.getMatchingMethod("isSubtype", new SequenceType(Type.CLASS)));
			
			new TACCall(tree, block, methodRef, methodRef.getPrefix(), comparisonClass);
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTRelationalExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}
	
	
	private TACOperand convertToString( TACOperand operand )
	{
		Type type = resolveType(operand.getType());
		if( type.equals(Type.STRING ) )
			return operand;
		
		if ( operand.getModifiers().isNullable() && !(type instanceof ArrayType) )// || !type.isPrimitive() && !(type instanceof ArrayType))
		{ // TODO: actually check nullable
			TACLabelRef nullLabel = new TACLabelRef(tree),
					nonnullLabel = new TACLabelRef(tree),
					doneLabel = new TACLabelRef(tree);
			TACReference var = new TACVariableRef(tree,
					method.addTempLocal(new SimpleModifiedType(Type.STRING)));
			new TACBranch(tree, new TACSame(tree, operand,
					new TACLiteral(tree, ShadowValue.NULL)), nullLabel,
					nonnullLabel);
			nullLabel.new TACLabel(tree);
			new TACStore(tree, var, new TACLiteral(tree, new ShadowString("null")));
			new TACBranch(tree, doneLabel);
			nonnullLabel.new TACLabel(tree);
			
			if( type.isPrimitive() ) //convert non null primitive wrapper to real primitive			
				operand = new TACCast(tree, new SimpleModifiedType(type), operand);
			
			TACMethodRef methodRef = new TACMethodRef(tree, operand,
					type.getMatchingMethod("toString", new SequenceType())); 
			
			new TACStore(tree, var, new TACCall(tree, block,
					methodRef,
					Collections.singletonList(methodRef.getPrefix())));
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			operand = new TACLoad(tree, var);
		}
		else
		{
			TACMethodRef methodRef = new TACMethodRef(tree, operand,
					type.getMatchingMethod("toString", new SequenceType())); 
			operand = new TACCall(tree, block, methodRef,
					Collections.singletonList(methodRef.getPrefix()));
		}
		
		return operand;
	}

	@Override
	public Object visit(ASTConcatenationExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACOperand last = null;			
			for (int i = 0; i < tree.getNumChildren(); i++)
			{
				TACOperand operand = tree.appendChild(i);
				
				operand = convertToString( operand );				
				
				last = i == 0 ? operand : new TACCall(tree, block,
						new TACMethodRef(tree,
								Type.STRING.getMethods("concatenate").get(0)),
						Arrays.asList(last, operand));
			}
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTShiftExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTRotateExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTAdditiveExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTUnaryExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACOperand operand = tree.appendChild(0);
			String op = node.getImage(); 
			if( op.equals("#")) //string is special because of nulls
			{	
				convertToString( operand );
			}
			else
			{
				Type type = resolveType(operand.getType());					
				if( op.equals("!") )
					new TACUnary(tree, "!", operand);
				else
				{
					MethodSignature signature = node.getOperations().get(0); 
					if( type.isPrimitive() && signature.getModifiers().isNative() )
						new TACUnary(tree, signature, op, operand);				
					else
					{
						TACVariableRef var = new TACVariableRef(tree,
								method.addTempLocal(node));
						new TACStore(tree, var, new TACCall(tree, block, new TACMethodRef(tree, operand, node.getOperations().get(0)), operand));		
						new TACLoad(tree, var);
					}
				}
			}
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTInlineMethodDefinition node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			throw new UnsupportedOperationException();
		return node.jjtGetNumChildren() == 1 ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCastExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			new TACCast(tree, node, tree.appendChild(1), true);
		return POST_CHILDREN;
	}

	//ShadowParserVisitor.visit(ASTCheckExpression, Boolean)
	
	@Override
	public Object visit(ASTCheckExpression node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {	
			TACLabelRef recover;
			
			if( node.hasRecover() )
				recover = block.getRecover();
			else
				recover = new TACLabelRef(tree);				
				
			TACLabelRef continueLabel = new TACLabelRef(tree);
			TACOperand operand = tree.appendChild(0);
			
			//if there's a recover, things will be handled there if null			
			new TACBranch(tree, new TACSame(tree, operand, new TACLiteral(tree,
					ShadowValue.NULL)), recover, continueLabel);
			
			//otherwise, we throw an exception here
			if( !node.hasRecover() ) {
				recover.new TACLabel(tree);
				TACOperand object = new TACNewObject(tree, Type.UNEXPECTED_NULL_EXCEPTION);
				MethodSignature signature = Type.UNEXPECTED_NULL_EXCEPTION.getMatchingMethod("create", new SequenceType());						
				TACCall exception = new TACCall(tree, block, new TACMethodRef(tree, signature), object);
							
				new TACThrow(tree, block, exception);
			}	
			
			continueLabel.new TACLabel(tree);
			prefix = new TACNodeRef(tree, operand);
			
			if( node.getType().isPrimitive() ) //convert from object to primitive form
				prefix = new TACCast(tree, new SimpleModifiedType(node.getType()), prefix );
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimaryExpression node, Boolean secondVisit)
			throws ShadowException {
		TACOperand savePrefix = prefix;
		TACVariable saveIdentifier = identifier;
		prefix = null;
		identifier = null;

		tree = tree.next(node.jjtGetNumChildren());
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
			walk(node.jjtGetChild(i));
		tree = tree.done();

		prefix = savePrefix;
		identifier = saveIdentifier;
		return NO_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			identifier = new TACVariable(node, node.getImage());
			if (node.isImageNull())
				prefix = tree.appendChild(0);
			else if( node.getType() instanceof SingletonType )
				prefix = new TACSingletonRef(tree, (SingletonType)node.getType());			
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
						prefix = new TACVariableRef(tree, local);
					else {
						TACReference thisRef =
								new TACVariableRef(tree, method.getThis());
						while (!thisRef.getType().containsField(name))
							thisRef = new TACFieldRef(tree, thisRef,
									new SimpleModifiedType(thisRef.getType().
											getOuter()), "_outer");
						if (node.getModifiers().isConstant())
							prefix = new TACConstantRef(tree, thisRef.getType(),
									name);
						else
							prefix = new TACFieldRef(tree, thisRef, name);
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
			prefix = new TACVariableRef(tree, method.getThis());
			
			//for outer class method calls
			Type prefixType = prefix.getType().getTypeWithoutTypeArguments();
			Type methodOuter = signature.getOuter().getTypeWithoutTypeArguments();
			
			while( !prefixType.isSubtype(methodOuter) ) {			
				prefix = new TACFieldRef(tree, prefix, new SimpleModifiedType(prefixType.getOuter()),
					"_outer");
				prefixType = prefixType.getOuter();				
			}
		}
		
		TACMethodRef methodRef = new TACMethodRef(tree,
				prefix,
				//methodType.getTypeWithoutTypeArguments(),
				signature);		
		methodRef.setSuper(explicitSuper);
		List<TACOperand> params = new ArrayList<TACOperand>();
				
		//params.add(prefix);
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
		
		prefix = new TACCall(tree, block, methodRef, params);
		MethodType methodType = signature.getMethodType();
		//sometimes a cast is needed when dealing with generic types
		SequenceType requiredReturnTypes = methodType.getReturnTypes();
		SequenceType methodReturnTypes = methodRef.getReturnTypes();		
		
		if( !methodReturnTypes.matches(requiredReturnTypes) ) {
			if( requiredReturnTypes.size() == 1 )			
				prefix = new TACCast(tree, requiredReturnTypes.get(0), prefix);
			else
				prefix = new TACCast(tree, new SimpleModifiedType(requiredReturnTypes), prefix);
		}
		
		explicitSuper = false;		
	}

	@Override
	public Object visit(ASTQualifiedKeyword node, Boolean secondVisit)
			throws ShadowException {
		
		//TODO: fix this?
		/*
		if (secondVisit)
		{
			if (node.getImage().equals("class"))
			{	
				prefix = new TACClass(tree, identifier.getType(), method).getClassData();				
			}
			else // TODO: Make this work
				throw new UnsupportedOperationException();
		}
		
		return POST_CHILDREN;
		*/
		
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTScopeSpecifier node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {			
			if( node.getImage().equals("class")) {
				Type type = identifier.getType();
				if( node.jjtGetNumChildren() > 0 ) {
					ASTTypeArguments arguments = (ASTTypeArguments) node.jjtGetChild(0);
					
					try {
						type = type.replace(type.getTypeParameters(), arguments.getType());
					}
					catch (InstantiationException e) 
					{}					
				}
				
				prefix = new TACClass(tree, type).getClassData();
			}
			else if( node.getModifiers().isConstant() )
				prefix = new TACConstantRef(tree, node.getPrefixType(), node.getImage());
			else if( node.getType() instanceof SingletonType)
				prefix = new TACSingletonRef(tree, (SingletonType)node.getType());
			else if( !node.getModifiers().isTypeName() )  //doesn't do anything at this stage if it's just a type name				
				prefix = new TACFieldRef(tree, prefix, node.getImage());
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTMethod node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
			identifier = new TACVariable(node, node.getImage());
		return POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTSubscript node, Boolean secondVisit)
			throws ShadowException {
		//subscripts index into arrays or subscriptable types
		if (secondVisit) {
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
						index = new TACCast(tree, new SimpleModifiedType(Type.INT, index.getModifiers()), index);
					list.add(index);
				}
				
				if( arrayType.getBaseType() instanceof TypeParameter )
					prefix = new TACGenericArrayRef(tree, prefix, list);
				else
					prefix = new TACArrayRef(tree, prefix, list);
			}				
			else if( node.getType() instanceof SubscriptType ) {					
				SubscriptType subscriptType = (SubscriptType) node.getType();
				//only do the straight loads
				//stores (and +='s) are handled in ASTExpression
				if( subscriptType.isLoad() && !subscriptType.isStore() ) {
					MethodSignature signature = subscriptType.getGetter();
					methodCall(signature, node);					
				}
				else {					
					List<TACOperand> list = new ArrayList<TACOperand>();
					list.add(tree.appendChild(0));
					indexStack.push(list);					
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
				if( propertyType.isLoad() && !propertyType.isStore() ) {
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
			prefix = new TACLiteral(tree, ShadowValue.NULL);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	/*
	@Override
	public Object visit(ASTArguments node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			new TACSequence(tree);
		return node.jjtGetNumChildren() != 0 ? PRE_CHILDREN : POST_CHILDREN;
	}
	*/

	@Override
	public Object visit(ASTArguments node, Boolean secondVisit) //used to be ASTArgumentList
			throws ShadowException {
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
	public Object visit(ASTArrayCreate node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit)
		{
			int start = 0;
			if( node.jjtGetChild(0) instanceof ASTTypeArguments )
				start++; //skip ahead
			
			tree.appendChild(start);			
			ArrayType arrayType = (ArrayType)node.getType();
			Type type = arrayType.getBaseType();
			TACClass baseClass = new TACClass(tree, type);
			
			List<TACOperand> indices = indexStack.pop();
			
			if( node.getMethodSignature() != null )
			{	
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
				 TACReference outer = new TACVariableRef(tree, method.getThis());
				 Type outerType = outer.getType().getTypeWithoutTypeArguments();					 
				 while( !outerType.isSubtype(methodOuter) ) {
					outer = new TACFieldRef(tree, outer, new SimpleModifiedType(outerType.getOuter()),
							"_outer");						
					outerType = outerType.getOuter();				
				 }					 
				 params.add(1, outer); //after object, before args
			}
		}
		

		TACMethodRef methodRef;		
		if( signature.getOuter() instanceof InterfaceType )
			methodRef = new TACMethodRef(tree, new TACCast(tree, new SimpleModifiedType(signature.getOuter()), object), signature);
		else
			methodRef = new TACMethodRef(tree, signature);
				
		prefix = new TACCall(tree, block, methodRef, params);
		
		//sometimes a cast is needed when dealing with generic types
		if( !prefix.getType().equals(prefixType) )
			prefix = new TACCast(tree, new SimpleModifiedType(prefixType), prefix);
		
		return prefix;
	}	

	@Override
	public Object visit(ASTCreate node, Boolean secondVisit)
			throws ShadowException {
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
	public Object visit(ASTAssertStatement node, Boolean secondVisit)
			throws ShadowException
	{		
		if (secondVisit)
		{
			TACLabelRef doneLabel = new TACLabelRef(tree),
					errorLabel = new TACLabelRef(tree);
			
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

			TACCall exception = new TACCall(tree, block, new TACMethodRef(tree, signature), params);
			new TACThrow(tree, block, exception);
			doneLabel.new TACLabel(tree);			
		}
	
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBlock node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			method.exitScope();
		else
			method.enterScope();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCreateBlock node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			method.exitScope();
		else
			method.enterScope();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBlockStatement node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTLocalDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTLocalMethodDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTLocalVariableDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEmptyStatement node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStatementExpression node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSequenceAssignment node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			ASTRightSide rightSide = (ASTRightSide) node.jjtGetChild(1);
			ASTSequenceLeftSide leftSide = (ASTSequenceLeftSide) node.jjtGetChild(0);
			//create splat
			if( !(rightSide.getType() instanceof SequenceType)  )
			{				
				TACReference temporary = new TACVariableRef(tree,
						method.addTempLocal(rightSide));	
				new TACStore(tree, temporary, tree.appendChild(1));  //is that right? child 0 didn't work

				
				List<TACOperand> sequence =
						new ArrayList<TACOperand>(leftSide.getType().size());
				
				for (int index = 0; index < leftSide.getType().size(); index++ )				
						sequence.add(new TACLoad(tree, temporary));
				
				new TACStore(tree, new TACSequenceRef(tree,
						tree.appendChildRemoveSequence(0)), new TACSequence(tree, sequence) );
			}
			else
			{
				new TACStore(tree, new TACSequenceRef(tree,
						tree.appendChildRemoveSequence(0)), tree.appendChild(1));
			}
		}
		
		
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTSwitchStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if( secondVisit )
		{
			TACOperand value = tree.appendChild(0);
			Type type = value.getType();
			TACLabelRef defaultLabel = null;
			TACLabelRef doneLabel = new TACLabelRef(tree);
			if( node.hasDefault() )
				defaultLabel = new TACLabelRef(tree);
			
			if( !( value.getType() instanceof EnumType ) )
			{	
				//first go through and do the conditions
				for( int i = 1; i < node.jjtGetNumChildren(); i += 2 )
				{	
					ASTSwitchLabel label = (ASTSwitchLabel) node.jjtGetChild(i);
										
					if( label.isDefault() )											
						label.setLabel(defaultLabel);
					else
					{	
						tree.appendChild(i); //append label conditions
						
						TACLabelRef matchingCase = new TACLabelRef(tree);
						label.setLabel(matchingCase);
						for( int j = 0; j < label.getValues().size(); j++ )
						{
							TACOperand operand = label.getValues().get(j);
							TACOperand comparison;
							MethodSignature signature = type.getMatchingMethod("equal", new SequenceType(operand));
							
							if( type.isPrimitive() && signature.getModifiers().isNative() )
								comparison = new TACBinary(tree, value, signature, '=', operand, false );
							else								
								comparison = new TACCall(tree, block, new TACMethodRef(tree, value, signature), value, operand);
														
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
								next = new TACLabelRef(tree);
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
	public Object visit(ASTSwitchLabel node, Boolean secondVisit)
			throws ShadowException
	{
		
		if( secondVisit )
		{		
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )		
				node.addValue(tree.appendChild(i));
		}
		
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTIfStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef trueLabel = new TACLabelRef(tree),
					falseLabel = new TACLabelRef(tree),
					endLabel = new TACLabelRef(tree);
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
	public Object visit(ASTWhileStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitLoop(0, 1, 0, false);
		else
			visitLoop();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTDoStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitLoop(1, 0, 1, true);
		else
			visitLoop();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTForeachStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitForeachLoop(node);
		else
			visitLoop();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTForStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
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
	public Object visit(ASTForInit node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStatementExpressionList node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTForUpdate node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBreakOrContinueStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef unreachableLabel = new TACLabelRef(tree);
			TACBlock exitBlock;
			
			if( node.getImage().equals("break") )
			{
				exitBlock = block.getBreakBlock();
				visitCleanup(exitBlock, null, exitBlock.getBreak());
			}
			else
			{
				exitBlock = block.getContinueBlock();
				visitCleanup(exitBlock, null, exitBlock.getContinue());
			}
			
			unreachableLabel.new TACLabel(tree);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTReturnStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef unreachableLabel = new TACLabelRef(tree);
			visitCleanup(null, null);
			new TACReturn(tree, (SequenceType)node.getType(),
					tree.appendChild(0));
			unreachableLabel.new TACLabel(tree);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTThrowStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef unreachableLabel = new TACLabelRef(tree);
			new TACThrow(tree, block, tree.appendChild(0));
			unreachableLabel.new TACLabel(tree);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTFinallyStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			tree.appendChild(0);
			if (node.hasFinally())
			{
				TACPhi phi = (TACPhi)tree.getLast();
				tree.appendChild(1);
				new TACBranch(tree, (TACDestination)phi.getRef());
			}
			block.getDone().new TACLabel(tree);
			block = block.getParent();
		}
		else
			block = new TACBlock(tree, block).addDone();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTRecoverStatement node, Boolean secondVisit)
			throws ShadowException
	{
		ASTFinallyStatement parent = (ASTFinallyStatement)node.jjtGetParent();
		if (secondVisit)
		{
			tree.appendChild(0);
			if (node.hasRecover())
			{
				tree.appendChild(1);
				new TACBranch(tree, block.getDone());
			}
			if (parent.hasFinally())
			{
				block.getDone().new TACLabel(tree);
				method.setHasLandingpad();
				visitCleanup(block.getParent(), block.getDone(),
						block.getParent().getDone());
				block.getLandingpad().new TACLabel(tree);
				new TACLandingpad(tree, block);
				new TACBranch(tree, block.getUnwind());
				block.getUnwind().new TACLabel(tree);
				new TACUnwind(tree, block);
				TACLabelRef continueUnwind = block.getParent().getUnwind();
				if (continueUnwind != null)
					visitCleanup(block, block.getUnwind(), continueUnwind);
				else
				{
					visitCleanup(block, block.getUnwind());
					new TACResume(tree);
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
	public Object visit(ASTCatchStatements node, Boolean secondVisit)
			throws ShadowException
	{
		ASTRecoverStatement parent = (ASTRecoverStatement)node.jjtGetParent();
		if (secondVisit)
		{
			TACOperand typeid = tree.appendChild(0);
			for (int i = 1; i <= node.getCatches(); i++)
			{
				Type type = node.jjtGetChild(i).jjtGetChild(0).getType();
				TACLabelRef catchLabel = block.getCatch(i - 1),
						skip = new TACLabelRef(tree);
				new TACBranch(tree, new TACCall(tree, block, new TACMethodRef(
						tree, Type.INT.getMatchingMethod("equal", new SequenceType(Type.INT))), typeid,
						new TACTypeId(tree, new TACClass(tree, type).getClassData())),
						catchLabel, skip);
				catchLabel.new TACLabel(tree);
				tree.appendChild(i);
				new TACBranch(tree, block.getDone());
				skip.new TACLabel(tree);
				if (i == node.getCatches())
				{
					TACLabelRef continueUnwind = block.getUnwind();
					if (continueUnwind != null)
						new TACBranch(tree, continueUnwind); //try inside of try					
					else
					{	
						//simply break?
						new TACResume(tree);
						//new TACBranch(tree, block.getParent().getDone()); 
						//System.err.println("Trouble!");
						//rethrow if necessary!
						/*
						TACLabelRef unreachableLabel = new TACLabelRef(tree);
						new TACThrow(tree, block, typeid);
						unreachableLabel.new TACLabel(tree);
						*/						
					}
					
				}
			}
			
			/*  //old code
			if (parent.hasRecover())
			{
				block.getRecover().new TACLabel(tree);
				block = block.getParent();
			}
			*/
			
			//new code
			if (parent.hasRecover())			
				block.getRecover().new TACLabel(tree);
			
			block = block.getParent();			
		}
		else 
		{
			block = new TACBlock(tree, block);
			if (parent.hasRecover())
				block.addRecover();
			
			block.addCatches(node.getCatches());
		}
								
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCatchStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			tree.appendChild(0);
			new TACStore(tree, new TACVariableRef(tree, method.getLocal(
					node.jjtGetChild(0).getImage())), new TACCatch(tree,
					(ExceptionType)node.jjtGetChild(0).getType()));
			tree.appendChild(1);
			method.exitScope();
		}
		else
			method.enterScope();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTTryStatement node, Boolean secondVisit)
			throws ShadowException
	{
		ASTCatchStatements parent = (ASTCatchStatements)node.jjtGetParent();
		if (secondVisit)
		{
			tree.appendChild(0);
			new TACBranch(tree, block.getDone());
			if (parent.getCatches() > 0 )
			{
				method.setHasLandingpad();
				block.getLandingpad().new TACLabel(tree);
				new TACLandingpad(tree, block);
				new TACBranch(tree, block.getUnwind());
				block.getUnwind().new TACLabel(tree);
				new TACTypeId(tree, new TACUnwind(tree, block));
				block = block.getParent();
			}
		}
		else if (parent.getCatches() > 0)
		{
			block = new TACBlock(tree, block).addLandingpad().addUnwind();
		}
		return POST_CHILDREN;
	}

	@SuppressWarnings("unused")
	private void visitCleanup(TACBlock lastBlock)
	{
		visitCleanup(lastBlock, null);
	}
	private void visitCleanup(TACBlock lastBlock, TACLabelRef currentLabel)
	{
		if (lastBlock != null)
			lastBlock = lastBlock.getParent();
		TACBlock currentBlock = block.getCleanupBlock(lastBlock);
		if (currentBlock != lastBlock)
		{
			TACLabelRef lastLabel = new TACLabelRef(tree);
			visitCleanup(currentBlock, lastBlock, currentLabel, lastLabel);
			lastLabel.new TACLabel(tree);
		}
	}
	private void visitCleanup(TACBlock lastBlock, TACLabelRef currentLabel,
			TACLabelRef lastLabel)
	{
		if (lastBlock != null)
			lastBlock = lastBlock.getParent();
		TACBlock currentBlock = block.getCleanupBlock(lastBlock);
		if (currentBlock != lastBlock)
			visitCleanup(currentBlock, lastBlock, currentLabel, lastLabel);
		else
			new TACBranch(tree, lastLabel);
	}
	private void visitCleanup(TACBlock currentBlock, TACBlock lastBlock,
			TACLabelRef currentLabel, TACLabelRef lastLabel)
	{
		if (currentLabel == null)
		{
			currentLabel = new TACLabelRef(tree);
			new TACBranch(tree, currentLabel);
			currentLabel.new TACLabel(tree);
		}
		TACBlock nextBlock;
		while ((nextBlock = currentBlock.getNextCleanupBlock(lastBlock)) !=
				lastBlock)
		{
			TACLabelRef nextLabel = new TACLabelRef(tree);
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
			throws ShadowException
	{
		TACTree saveTree = tree;
		tree = new TACTree(1);
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

	
		TACVariableRef[] counters = new TACVariableRef[layers];			
		TACLabelRef[] labels = new TACLabelRef[layers];			
		TACLabelRef done = new TACLabelRef(tree);
		TACOperand returnValue = null;
		
		type = array.getType();		
		TACOperand oldArray = array;
		TACVariableRef previousArray = null;
		
		for( int i = 0; i < layers; i++ )
		{
			ArrayType arrayType = (ArrayType)type;
			type = arrayType.getBaseType();
			TACOperand length = new TACLength(tree, oldArray, 0);
			TACOperand[] dimensions = new TACOperand[arrayType.getDimensions()];
			dimensions[0] = length;
			for (int j = 1; j < arrayType.getDimensions(); j++)
			{
				dimensions[j] = new TACLength(tree, oldArray, j);
				length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', dimensions[j]);
			}
			
			TACClass class_ = new TACClass(tree, type);
			TACVariableRef copiedArray = new TACVariableRef(tree, method.addTempLocal(new SimpleModifiedType(arrayType)));
			new TACStore(tree, copiedArray, new TACNewArray(tree, arrayType, class_.getClassData(), dimensions));
			
			if( i == 0 )
				returnValue = copiedArray;
			else
			{
				//store current array into its spot in the previous array
				TACArrayRef location = new TACArrayRef(tree, previousArray, counters[i - 1], false);
				new TACStore(tree, location, copiedArray);
			}
			
			previousArray = copiedArray;
			
			if( i == layers - 1 ) //last layer is either objects or primitives, not more arrays
			{
				TACLabelRef terminate;					
				if( i == 0 )
					terminate = done;
				else
					terminate = labels[i - 1];
				
				if( type.isPrimitive() )
				{
					TACMethodRef width = new TACMethodRef(tree, Type.CLASS.getMatchingMethod("width", new SequenceType()) );
					TACOperand size = new TACBinary(tree, new TACCall(tree, block, width, class_.getClassData()), Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', length);
					new TACCopyMemory(tree, copiedArray, oldArray, size);					
					new TACBranch(tree, terminate);		
				}
				else
				{					
					counters[i] = new TACVariableRef(tree, method.addTempLocal(new SimpleModifiedType(Type.INT)));
					new TACStore(tree, counters[i], new TACLiteral(tree, new ShadowInteger(-1))); //starting at -1 allows update and check to happen on the same label
					labels[i] = new TACLabelRef(tree);
					
					new TACBranch(tree, labels[i]);
					labels[i].new TACLabel(tree);					
					TACLabelRef body = new TACLabelRef(tree);					

					//increment counters[i] by 1
					new TACStore(tree, counters[i], new TACBinary(tree, counters[i], Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', new TACLiteral(tree, new ShadowInteger(1))));
					TACOperand condition = new TACBinary(tree, counters[i], Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), '<', length, true);
					new TACBranch(tree, condition, body, terminate);					
					body.new TACLabel(tree);
					
					//copy old value into new location
					TACOperand element = new TACArrayRef(tree, oldArray, counters[i], false); //get value at location
					
					if( baseType instanceof InterfaceType )
						element = new TACCast(tree, new SimpleModifiedType(Type.OBJECT), element);
										
					TACLabelRef copyLabel = new TACLabelRef(tree);
					TACOperand nullCondition = new TACSame(tree, element, new TACLiteral(tree, ShadowValue.NULL));
					new TACBranch(tree, nullCondition, labels[i], copyLabel); //if null, skip entirely, since arrays are calloc'ed
					
					copyLabel.new TACLabel(tree);
					
					TACOperand copiedElement = new TACCall(tree, block, copyMethod, element, map);
					
					if( baseType instanceof InterfaceType )
						copiedElement = new TACCast(tree, new SimpleModifiedType(baseType), copiedElement);
					
					TACArrayRef newElement = new TACArrayRef(tree, copiedArray, counters[i], false);
					new TACStore(tree, newElement, copiedElement);

					//go back to update and check condition
					new TACBranch(tree, labels[i]);					
				}
			}
			else
			{
				counters[i] = new TACVariableRef(tree, method.addTempLocal(new SimpleModifiedType(Type.INT)));
				new TACStore(tree, counters[i], new TACLiteral(tree, new ShadowInteger(-1)));//starting at -1 allows update and check to happen on the same label
				labels[i] = new TACLabelRef(tree);
				
				new TACBranch(tree, labels[i]);
				labels[i].new TACLabel(tree);	
				
				TACLabelRef terminate;
				TACLabelRef body = new TACLabelRef(tree);
				if( i == 0 )
					terminate = done;
				else
					terminate = labels[i - 1];

				//increment counters[i] by 1
				new TACStore(tree, counters[i], new TACBinary(tree, counters[i], Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', new TACLiteral(tree, new ShadowInteger(1))));
				TACOperand condition = new TACBinary(tree, counters[i], Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), '<', length, true);
				new TACBranch(tree, condition, body, terminate);					
				body.new TACLabel(tree);
				
				//set up next round
				oldArray = new TACArrayRef(tree, oldArray, counters[i], false); //get value at location
			}
		}
		
		done.new TACLabel(tree);
		return returnValue;
	}
	
	
	private void visitMethod(MethodSignature methodSignature)
			throws ShadowException {
		TACTree saveTree = tree;
		TACMethod method = this.method = new TACMethod(methodSignature);
		implicitCreate = false;
		if (moduleStack.peek().isClass()) {
			block = new TACBlock(tree = new TACTree(1));
			if (!methodSignature.isNative() && methodSignature.isCreate()) {
				implicitCreate = true;
				Type type = methodSignature.getOuter();
				if (type.hasOuter())
					new TACStore(tree,
							new TACFieldRef(tree, new TACVariableRef(tree,
									method.getThis()),
									new SimpleModifiedType(type.getOuter()),
									"_outer"),
							new TACVariableRef(tree,
									method.getParameter("_outer")));			
				for (Node field : type.getFields().values())
					if (!field.getModifiers().isConstant() && !(field.getType() instanceof SingletonType))
						walk(field);
			}
			else if( methodSignature.getSymbol().equals("copy") && !methodSignature.isWrapper() ) {
				ClassType type = (ClassType) methodSignature.getOuter();	
				
				if( type.getModifiers().isImmutable() ) {				
					//for now, just return this
					new TACReturn(tree, methodSignature.getFullReturnTypes(), new TACVariableRef(
							tree, method.getThis()));
				}			
				else {
					method.addParameters(); //address map called "addresses"
					TACOperand this_ = new TACLoad(tree, new TACVariableRef(tree, method.getThis()));
					TACOperand address = new TACPointerToLong(tree, this_);					
					
					TACVariableRef map = new TACVariableRef(tree, method.getParameter("addresses"));
					TACMethodRef indexMethod = new TACMethodRef(tree, Type.ADDRESS_MAP.getMatchingMethod("containsKey", new SequenceType(Type.ULONG)) );
					TACOperand test = new TACCall(tree, block, indexMethod, map, address );
					
					TACLabelRef copyLabel = new TACLabelRef(tree),
							returnLabel = new TACLabelRef(tree);
					
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
					new TACCall(tree, block, indexMethod, map, address, newAddress);					
					
					if( type.getTypeWithoutTypeArguments().equals(Type.ARRAY) || type.getTypeWithoutTypeArguments().equals(Type.ARRAY_NULLABLE) ) {
						Type genericArray = type.getTypeWithoutTypeArguments();
						
						//call private create to allocate space
						TACMethodRef create = new TACMethodRef(tree, genericArray.getMatchingMethod("create", new SequenceType(new SimpleModifiedType( new ArrayType(Type.INT), new Modifiers(Modifiers.IMMUTABLE)))));
						TACFieldRef lengths = new TACFieldRef(tree, this_, "lengths" );
						duplicate = new TACCall(tree, block, create, object, lengths); //performs cast to Array as well
						
						//get size (product of all dimension lengths)
						TACMethodRef sizeMethod = new TACMethodRef(tree, genericArray.getMatchingMethod("size", new SequenceType()));					
						TACOperand size = new TACCall(tree, block, sizeMethod, this_);
						TACLabelRef done = new TACLabelRef(tree);
						TACLabelRef body = new TACLabelRef(tree);
						TACLabelRef condition = new TACLabelRef(tree);
						
						TACVariableRef i = new TACVariableRef(tree, method.addTempLocal(new SimpleModifiedType(Type.INT)));
						new TACStore(tree, i, new TACLiteral(tree, new ShadowInteger(0)));
						new TACBranch(tree, condition);
						
						//start loop
						condition.new TACLabel(tree);
						
						TACOperand loop = new TACBinary(tree, i, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), '<', size, true );
						new TACBranch(tree, loop, body, done);
						body.new TACLabel(tree);
						
						SequenceType indexArguments = new SequenceType();
						indexArguments.add(i);					
						TACMethodRef indexLoad = new TACMethodRef(tree, genericArray.getMatchingMethod("index", indexArguments));
						indexArguments.add(genericArray.getTypeParameters().get(0));
						TACMethodRef indexStore = new TACMethodRef(tree, genericArray.getMatchingMethod("index", indexArguments));
						
						
						TACOperand value = new TACCall(tree, block, indexLoad, this_, i);
						
						TACLabelRef skipLabel = new TACLabelRef(tree);
						TACLabelRef makeCopyLabel = new TACLabelRef(tree);
						TACOperand isNull = new TACSame(tree, value, new TACLiteral(tree, ShadowValue.NULL));
						new TACBranch(tree, isNull, skipLabel, makeCopyLabel);
						
						makeCopyLabel.new TACLabel(tree);
						
						TACMethodRef copy = new TACMethodRef(tree, value, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
						
						value = new TACCall(tree, block, copy, copy.getPrefix(), map);
						new TACCall(tree, block, indexStore, duplicate, i, value);						
						new TACBranch(tree, skipLabel);
						
						skipLabel.new TACLabel(tree);						
						
						new TACStore(tree, i, new TACBinary(tree, i, Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', new TACLiteral(tree, new ShadowInteger(1)), false ));
						new TACBranch(tree, condition);					
						
						done.new TACLabel(tree);
					}
					else {
						//perform a memcopy to sweep up all the primitives and immutable data (and nulls)
						TACOperand size = new TACLoad(tree, new TACFieldRef(tree, object.getClassData(), "size"));
						new TACCopyMemory(tree, object, this_, size);
						
						if( type.equals(Type.OBJECT))
							duplicate = object;
						else
							duplicate = new TACCast(tree, new SimpleModifiedType(type), object); //casts object to type
						
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
								field = new TACFieldRef(tree, this_, entryType, entry.getKey());
								newField = new TACFieldRef(tree, duplicate, entryType, entry.getKey());
								
								TACLabelRef copyField = new TACLabelRef(tree);
								TACLabelRef skipField = new TACLabelRef(tree);
								
								//TODO: add something special for Singletons, for thread safety
								if( entryType.getType() instanceof ArrayType )
									copiedField = copyArray(field, map);								
								else {	
									if( entryType.getType() instanceof InterfaceType ) {
										//cast converts from interface to object
										field = new TACCast(tree, new SimpleModifiedType(Type.OBJECT), field);
										copyMethod = new TACMethodRef(tree, field, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
									}
									else //normal object
										copyMethod = new TACMethodRef(tree, field, entryType.getType().getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
									
									TACOperand nullCondition = new TACSame(tree, field, new TACLiteral(tree, ShadowValue.NULL));
									new TACBranch(tree, nullCondition, skipField, copyField); //if null, skip

									copyField.new TACLabel(tree);
									copiedField = new TACCall(tree, block, copyMethod, field, map);

									if( entryType.getType() instanceof InterfaceType )
										//and then cast back to interface
										copiedField = new TACCast(tree, newField, copiedField);																
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
					TACOperand index = new TACCall(tree, block, indexMethod, map, address );
					TACOperand existingObject = new TACLongToPointer(tree, index, new SimpleModifiedType(type));
					new TACReturn(tree, methodSignature.getFullReturnTypes(), existingObject);					
				}
			}
			
			if (methodSignature.getNode() == null) { //for gets and sets			
				method.addParameters();
				TACFieldRef field = new TACFieldRef(tree, new TACVariableRef(
						tree, method.getThis()), methodSignature.getSymbol());
				if (methodSignature.isGet())
					new TACReturn(tree, methodSignature.getFullReturnTypes(),
							new TACLoad(tree, field));
				else if (methodSignature.isSet()) {
					TACVariable value = null;
					for (TACVariable parameter : method.getParameters())
						value = parameter;
					new TACStore(tree, field, new TACVariableRef(tree, value));
					new TACReturn(tree, methodSignature.getFullReturnTypes());
				}
				else
					new TACReturn(tree, methodSignature.getFullReturnTypes());
			}	
			else if (methodSignature.isNative()) {
				method.addParameters();
				walk(methodSignature.getNode().jjtGetChild(0).jjtGetChild(0));
			}				
			else if (methodSignature.isWrapper()) {
				MethodSignature wrapped = methodSignature.getWrapped();
				SequenceType fromTypes = methodSignature.getFullParameterTypes(),
						toTypes = wrapped.getFullParameterTypes();
				Iterator<TACVariable> fromArguments = method.addParameters(true).
						getParameters().iterator();
				List<TACOperand> toArguments = new ArrayList<TACOperand>(
						toTypes.size());
				for (int i = 0; i < toTypes.size(); i++)
				{
					TACOperand argument =
							new TACVariableRef(tree, fromArguments.next());
					if (!fromTypes.getType(i).isSubtype(toTypes.getType(i)))
						argument = new TACCast(tree, toTypes.get(i), argument);
					toArguments.add(argument);				
				}
				
				TACOperand value = new TACCall(tree, block, new TACMethodRef(tree,
						wrapped), toArguments); 
				
				if( methodSignature.getFullReturnTypes().isEmpty() )
					new TACReturn(tree, methodSignature.getFullReturnTypes(), null);
				else
				{
					fromTypes = wrapped.getFullReturnTypes();
					toTypes = methodSignature.getFullReturnTypes();
					
					if( value.getType() instanceof SequenceType )						
						value = new TACCast(tree, new SimpleModifiedType(toTypes), value);					
					else {
						if( !fromTypes.getType(0).isSubtype(toTypes.getType(0)) )
							value = new TACCast(tree, toTypes.get(0), value);
					}					
					
					new TACReturn(tree, toTypes, value);	
				}
			}
			else {
				method.addParameters();
				walk(methodSignature.getNode());
			}
			
			if (implicitCreate) {
				ClassType thisType = (ClassType)methodSignature.getOuter(),
						superType = thisType.getExtendType();
				if (superType != null)
					new TACCall(method, block, new TACMethodRef(method,
							superType.getMatchingMethod("create", new SequenceType())), new TACVariableRef(method,
							method.getThis()));
			}
			tree.done();
			method.append(tree);
		}
		moduleStack.peek().addMethod(method);
		block = null;
		this.method = null;
		tree = saveTree;
	}
	
	private void visitBooleanOperation(Node node)
	{
		int child = 0;		
		String image = node.getImage();
		TACOperand current = null;
			while( current == null )
				current = tree.appendChild(child++);
		for( int index = 0; index < node.getImage().length(); index++ )
		{
			char op = image.charAt(index);
			TACOperand next = null;
			while( next == null )
				next = tree.appendChild(child++);
			
			TACBinary.Boolean connector = null;
			switch( op )
			{
			case 'a': connector = TACBinary.Boolean.AND; break;
			case 'o': connector = TACBinary.Boolean.OR; break;
			case 'x': connector = TACBinary.Boolean.XOR; break;
			default: throw new IllegalArgumentException("Operator " + op + " is not a valid boolean operator");			
			}
									
			current = new TACBinary(tree, current, connector, next);
		}	
	}
	
	private void visitBinaryOperation(OperationNode node)
	{	
		int child = 0;		
		String image = node.getImage();
		TACOperand current = null;
		while( current == null )
			current = tree.appendChild(child++);
		for( int index = 0; index < node.getOperations().size(); index++ )
		{
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
					TACVariableRef var = new TACVariableRef(tree,
							method.addTempLocal(new SimpleModifiedType(Type.INT)));
					new TACStore(tree, var, new TACCall(tree, block, new TACMethodRef(tree, current, signature), current, next));		
					current = new TACLoad(tree, var);					
					current = new TACBinary(tree, current, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), op, new TACLiteral(tree, new ShadowInteger(0)), true );
				}
				else
				{
					TACVariableRef var = new TACVariableRef(tree,
							method.addTempLocal(node));
					new TACStore(tree, var, new TACCall(tree, block, new TACMethodRef(tree, current, signature), current, next));		
					current = new TACLoad(tree, var);
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
		
		TACLabelRef bodyLabel = new TACLabelRef(tree),
					updateLabel = new TACLabelRef(tree),
					conditionLabel = new TACLabelRef(tree),
					endLabel = new TACLabelRef(tree);
		
		//make iterator (int index)
		TACVariableRef iterator = new TACVariableRef(tree,	method.addTempLocal(new SimpleModifiedType(Type.INT)));
		new TACStore(tree, iterator, new TACLiteral(tree, new ShadowInteger(0)));
		TACOperand length = new TACLength(tree, array, 0);			
		for (int i = 1; i < arrayType.getDimensions(); i++)
			length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', new TACLength(tree, array, i));			
		
		new TACBranch(tree, conditionLabel);  //init is done, jump to condition
		
		bodyLabel.new TACLabel(tree);
		
		//put initialization before main body
		TACArrayRef location = new TACArrayRef(tree, array, new TACLoad(tree, iterator), false);		
				
		if( create != null)
			new TACStore(tree, location, callCreate( create, params, create.getOuter()));
		else
			new TACStore(tree, location, defaultValue);		
		
		new TACBranch(tree, updateLabel);
		updateLabel.new TACLabel(tree);			
		
		//increment iterator							
		TACOperand value = new TACBinary(tree, new TACLoad(tree, iterator), Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', new TACLiteral(tree, new ShadowInteger(1)));
		new TACStore(tree, iterator, value);	
		
		new TACBranch(tree, conditionLabel);
		conditionLabel.new TACLabel(tree);
		
		//check if iterator < array length
		value = new TACLoad(tree, iterator);			
		TACOperand condition = new TACBinary(tree, value, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), '<', length, true );
		
		new TACBranch(tree, condition, bodyLabel, endLabel);		
		endLabel.new TACLabel(tree);
	}
	
	private void visitForeachLoop(ASTForeachStatement node)
	{	
		ASTForeachInit init = (ASTForeachInit) node.jjtGetChild(0);		
		Type type = init.getCollectionType(); 		
		TACOperand collection = tree.appendChild(0); //last thing from init
		TACVariableRef variable = init.getVariable();
		TACVariableRef iterator;
		TACOperand condition;
		TACOperand value;
		TACLabelRef bodyLabel = new TACLabelRef(tree),				
				endLabel = block.getBreak();
		
		//optimization for arrays
		if( type instanceof ArrayType ) {	
			ArrayType arrayType = (ArrayType) type;
			TACLabelRef updateLabel = block.getContinue(),
						conditionLabel = new TACLabelRef(tree);

			//make iterator (int index)
			iterator = new TACVariableRef(tree,	method.addTempLocal(new SimpleModifiedType(Type.INT)));
			new TACStore(tree, iterator, new TACLiteral(tree, new ShadowInteger(0)));
			TACOperand length = new TACLength(tree, collection, 0);			
			for (int i = 1; i < arrayType.getDimensions(); i++)
				length = new TACBinary(tree, length, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', new TACLength(tree, collection, i), false);			
			
			new TACBranch(tree, conditionLabel);  //init is done, jump to condition
			
			bodyLabel.new TACLabel(tree);
			
			//put variable update before main body
			value = new TACArrayRef(tree, collection, new TACLoad(tree, iterator), false);
			new TACStore(tree, variable, value);
			
			tree.appendChild(1); //body
			
			new TACBranch(tree, updateLabel);
			updateLabel.new TACLabel(tree);			
			
			//increment iterator
			value = new TACLoad(tree, iterator);					
			value = new TACBinary(tree, value, Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+', new TACLiteral(tree, new ShadowInteger(1)), false );
			new TACStore(tree, iterator, value);	
			
			new TACBranch(tree, conditionLabel);
			conditionLabel.new TACLabel(tree);
			
			//check if iterator < array length
			value = new TACLoad(tree, iterator);			
			condition = new TACBinary(tree, value, Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)), '<', length, true );
			
			new TACBranch(tree, condition, bodyLabel, endLabel);		
			endLabel.new TACLabel(tree);
		}
		else
		{	
			TACLabelRef conditionLabel = block.getContinue();
			MethodSignature signature;
			
			//get iterator
			signature = type.getMatchingMethod("iterator", new SequenceType());
			ModifiedType iteratorType = signature.getReturnTypes().get(0);
			iterator = new TACVariableRef(tree, method.addTempLocal(iteratorType));
			TACMethodRef getIterator = new TACMethodRef(tree, collection, signature);
			new TACStore(tree, iterator, new TACCall(tree, block, getIterator, getIterator.getPrefix()) );
			
			new TACBranch(tree, conditionLabel);  //init is done, jump to condition
						
			bodyLabel.new TACLabel(tree);		
			
			//put variable update before main body
			signature = iteratorType.getType().getMatchingMethod("next", new SequenceType());
			TACMethodRef next = new TACMethodRef(tree, iterator, signature);
			new TACStore(tree, variable, new TACCall(tree, block, next, iterator) ); //internally updates iterator
			
			tree.appendChild(1); //body
						
			new TACBranch(tree, conditionLabel);
			conditionLabel.new TACLabel(tree);
			
			//check if iterator has next
			signature = iteratorType.getType().getMatchingMethod("hasNext", new SequenceType());
			TACMethodRef hasNext = new TACMethodRef(tree, iterator, signature);
			condition = new TACCall(tree, block, hasNext, iterator);
			
			new TACBranch(tree, condition, bodyLabel, endLabel);		
			endLabel.new TACLabel(tree);
		}	
		

		block = block.getParent();
	}
	
	private void visitLoop(int condition, int body, int update, boolean force)
	{
		TACLabelRef bodyLabel = new TACLabelRef(tree),
				updateLabel = block.getContinue(),
				conditionLabel = condition != update ?
						new TACLabelRef(tree) : updateLabel,
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
			TACReference index = new TACVariableRef(tree,
					method.addTempLocal(new SimpleModifiedType(Type.INT)));
			new TACStore(tree, index, new TACLiteral(tree, new ShadowInteger(0)));
			TACLabelRef bodyLabel = new TACLabelRef(tree),
					condLabel = new TACLabelRef(tree),
					endLabel = new TACLabelRef(tree);
			new TACBranch(tree, condLabel);
			bodyLabel.new TACLabel(tree);
			new TACStore(tree, new TACArrayRef(tree, alloc, index, false),
					visitArrayAllocation((ArrayType)type.getBaseType(), baseClass.getBaseClass(), sizes, create, params, defaultValue));
			new TACStore(tree, index, new TACBinary(tree, index, Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)), '+',
					new TACLiteral(tree, new ShadowInteger(1))));
			new TACBranch(tree, condLabel);
			condLabel.new TACLabel(tree);
			new TACBranch(tree, new TACSame(tree, index, alloc.getTotalSize()),
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
		return new TACCast(tree, type, new TACLiteral(tree, ShadowValue.NULL));
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
			throws ShadowException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTForeachInit node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			String name = node.getImage();
			node.setVariable(new TACVariableRef(tree, method.addLocal(node, name)));
			//append last child (containing the collection)
			tree.appendChild(node.jjtGetNumChildren() - 1);
		}
		return POST_CHILDREN;
	}
	@Override
	public Object visit(ASTInlineResults node, Boolean secondVisit)
			throws ShadowException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTSequenceRightSide node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			List<TACOperand> sequence =
					new ArrayList<TACOperand>(node.getType().size());
			
			for (int index = 0; index < node.getType().size(); index++ )				
					sequence.add(tree.appendChild(index));
			new TACSequence(tree, sequence);	
		}
			
		return POST_CHILDREN; 
	}	
	
	@Override
	public Object visit(ASTSequenceLeftSide node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			List<TACOperand> sequence =
					new ArrayList<TACOperand>(node.getUsedItems().size());
			int index = 0;
			for (boolean used : node.getUsedItems())
				if (used)
					sequence.add(tree.appendChild(index++));
				else
					sequence.add(new TACVariableRef(tree,
							method.addTempLocal(new SimpleModifiedType(
									Type.OBJECT,
									new Modifiers(Modifiers.NULLABLE | Modifiers.READONLY))))); //kind of a hack, but a nullable, readonly object can take on anything
			new TACSequence(tree, sequence);	
		}
			
		return POST_CHILDREN; 		
	}
	@Override
	public Object visit(ASTSequenceVariable node, Boolean secondVisit)
			throws ShadowException {
		if (secondVisit) {
			String name = node.getImage();
			new TACVariableRef(tree, method.addLocal(node, name));
		}
		return POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTRightSide node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCopyExpression node, Boolean secondVisit)
			throws ShadowException
	{
		
		if( secondVisit )
		{
			TACOperand value = tree.appendChild(0);
			prefix = value;
			Type type = node.getType();
			
			if( !type.getModifiers().isImmutable() ) //if immutable, do nothing, the old one is fine
			{				
				TACNewObject object = new TACNewObject(tree, Type.ADDRESS_MAP );
				TACMethodRef create = new TACMethodRef(tree, Type.ADDRESS_MAP.getMatchingMethod("create", new SequenceType()) );
				TACOperand map = new TACCall(tree, block, create, object);
				
				if( type instanceof ArrayType )
				{
					prefix = copyArray(value, map);					
					new TACNodeRef(tree, prefix);
				}
				else
				{
					TACMethodRef copyMethod;
					TACReference result = new TACVariableRef(tree, method.addTempLocal(node));
					TACOperand data = value;					
					
					TACLabelRef nullLabel = new TACLabelRef(tree);
					TACLabelRef doneLabel = new TACLabelRef(tree);
					TACLabelRef copyLabel = new TACLabelRef(tree);
					
					if( type instanceof InterfaceType )				
					{	
						//cast converts from interface to object
						data = new TACCast(tree, new SimpleModifiedType(Type.OBJECT), data);
						TACOperand nullCondition = new TACSame(tree, data, new TACLiteral(tree, ShadowValue.NULL));
						new TACBranch(tree, nullCondition, nullLabel, copyLabel);
						copyLabel.new TACLabel(tree);
						copyMethod = new TACMethodRef(tree, data, Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));						
					}
					else
					{
						TACOperand nullCondition = new TACSame(tree, data, new TACLiteral(tree, ShadowValue.NULL));						
						new TACBranch(tree, nullCondition, nullLabel, copyLabel);
						copyLabel.new TACLabel(tree);
						copyMethod  = new TACMethodRef(tree, data, type.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));						
					}
					
					TACOperand copy = new TACCall(tree, block, copyMethod, data, map);

					if( type instanceof InterfaceType )
						//and then a cast back to interface
						copy = new TACCast(tree, node, copy);
					
					new TACStore(tree, result, copy);					
					new TACBranch(tree, doneLabel);	
					
					nullLabel.new TACLabel(tree);
					
					new TACStore(tree, result, value);
					new TACBranch(tree, doneLabel);
					
					doneLabel.new TACLabel(tree);
					prefix = new TACLoad(tree, result);
				}
			}			
		}	
		
		
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTGenericDeclaration node, Boolean data)
			throws ShadowException {
		//should never appear in .shadow file (only .meta file)
		//never needs to be compiled
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTArrayDimensions node, Boolean secondVisit)
			throws ShadowException {
		
		if( secondVisit ) {
			List<TACOperand> list = new LinkedList<TACOperand>();
			for (int i = 0; i < tree.getNumChildren(); i++)
				list.add(tree.appendChild(i));
			indexStack.push(list);
		}	
		
		return  POST_CHILDREN;
	}
}
