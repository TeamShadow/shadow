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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTConditionalExpression;
import shadow.parser.javacc.ASTCreateBlock;
import shadow.parser.javacc.ASTCreateDeclaration;
import shadow.parser.javacc.ASTCreateDeclarator;
import shadow.parser.javacc.ASTDestroyDeclaration;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFormalParameter;
import shadow.parser.javacc.ASTFormalParameters;
import shadow.parser.javacc.ASTFunctionType;
import shadow.parser.javacc.ASTIsList;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTMethodDeclarator;
import shadow.parser.javacc.ASTPrimitiveType;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTResultType;
import shadow.parser.javacc.ASTResultTypes;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.ASTTypeArguments;
import shadow.parser.javacc.ASTTypeParameter;
import shadow.parser.javacc.ASTTypeParameters;
import shadow.parser.javacc.ASTUnqualifiedName;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SignatureNode;
import shadow.typecheck.DirectedGraph.CycleFoundException;
import shadow.typecheck.TypeCheckException.Error;
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
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.UninstantiatedClassType;
import shadow.typecheck.type.UninstantiatedInterfaceType;
import shadow.typecheck.type.UninstantiatedType;

/**
 * The <code>TypeUpdater</code> class updates types after they have been collected by
 * the <code>TypeCollector</code>.  This class adds fields and methods, instantiates type
 * parameters, adds parent types and interfaces, and checks method overrides.  It fills in
 * the general shape of all of the types but does not check the actual statements of
 * executable code.
 * 
 * @author Barry Wittman 
 */
public class TypeUpdater extends BaseChecker {
	
	private boolean isMeta = false;	
	
	/**
	 * Creates a new <code>TypeUpdater</code> with the given tree of packages.
	 * @param packageTree	root of all packages
	 */
	public TypeUpdater( Package packageTree ) {
		super( packageTree );		
	}
	
	
	/**
	 * Updates all of the types in the given node table and returns and updated table.
	 * This central method of the class performs all of its functions.
	 * @param typeTable			map from types to nodes
	 * @return
	 * @throws ShadowException
	 * @throws TypeCheckException
	 */
	public Map<Type,Node> update( Map<Type,Node> typeTable )
			throws ShadowException, TypeCheckException {
		
		/* Add fields and methods. */
		ASTWalker walker = new ASTWalker( this );
		for(Node declarationNode : typeTable.values() ) {
			isMeta = declarationNode.getFile().getPath().endsWith(".meta");
			// Only walk outer types since inner ones will be walked automatically.
			if( !declarationNode.getType().hasOuter() )
				walker.walk(declarationNode);
		}
		
		/* Add default creates and make methods for properties. */
		if( errorList.isEmpty() )
			addCreatesAndProperties();

		/* Instantiate type parameters where necessary, returning a new type table
		 * The old type table can be invalidated when type names change as a result
		 * of instantiating type parameters. */
		if( errorList.isEmpty() )
			typeTable = instantiateTypeParameters( typeTable );
		
		/* Topologically sort classes by their is lists: parent types and interfaces.
		 * This sorting will also check for circular inheritance or interfaces.
		 */
		List<Node> nodeList = sortOnIsLists( typeTable );		
		
		/* Update is lists based on updated type parameters. */		
		if( errorList.isEmpty() )
			updateIsLists( nodeList );
		
		/* Update fields and methods. Must happen after is lists are updated, since
		 * generic parameters in the methods could depend on correct parents and interfaces. */
		if( errorList.isEmpty() )
			updateFieldsAndMethods( nodeList );		
		
		/* After updating all the types, make sure that method overrides are all legal. */
		if( errorList.isEmpty() )
			checkOverrides( nodeList );
		
		/* Finally, update which types are referenced by other types. */
		if( errorList.isEmpty() )
			updateTypeReferences( nodeList );

		if( errorList.size() > 0 ) {
			printErrors();
			printWarnings();
			throw errorList.get(0);
		}
		
		printWarnings();
		
		return typeTable;
	}	
		
	private void updateFieldsAndMethods( List<Node> nodeList ) {
		//update fields and methods			
		for(Node declarationNode : nodeList ) {	
			try {			
				Type type = declarationNode.getType();				
				type.updateFieldsAndMethods();				
			}
			catch(InstantiationException e) {							
				addError(declarationNode, Error.INVALID_TYPE_ARGUMENTS, e.getMessage() );
			}
		}		
	}
	
	/* 
	 * Add necessary referenced types to all types. 
	 */
	private void updateTypeReferences( List<Node> nodeList ) {
		for(Node declarationNode : nodeList ) {
			Type type = declarationNode.getType();
			
			/* The following types must be added because they can appear in generated code
			 * without appearing inside the Shadow source at all. */
			
			// Address map for deep copies
			type.addUsedType(Type.ADDRESS_MAP);
			
			// Class management
			type.addUsedType(Type.CLASS);
			type.addUsedType(Type.CLASS_SET);
			type.addUsedType(Type.GENERIC_CLASS);
			
			// Array wrapper classes
			type.addUsedType(Type.ARRAY);
			type.addUsedType(Type.ARRAY_NULLABLE);
			
			// Iterators for foreach loops
			type.addUsedType(Type.ITERATOR);
			type.addUsedType(Type.ITERATOR_NULLABLE);
			
			// Exceptions
			type.addUsedType(Type.EXCEPTION);
			type.addUsedType(Type.CAST_EXCEPTION);
			type.addUsedType(Type.INDEX_OUT_OF_BOUNDS_EXCEPTION);
			type.addUsedType(Type.INTERFACE_CREATE_EXCEPTION);
			type.addUsedType(Type.UNEXPECTED_NULL_EXCEPTION);			
			
			// String
			type.addUsedType(Type.STRING);
			
			// Adding the self adds parents and interfaces and methods
			type.addUsedType(type); 
			
			/* Update imports for meta files, since they won't have statement checking.
			 * Note that imports in meta files have been optimized to include only the
			 * originally imported classes that are referenced. */
			if( declarationNode.getFile().getPath().endsWith(".meta") ) {
				for(Object item : type.getImportedItems())
					if( item instanceof Type ) {
						Type importType = (Type) item;
						type.addUsedType(importType);
					}
			}
		}
	}
	
	private void addCreatesAndProperties() {		
		for( Type type : packageTree ) { // Iterate over all the types in the package tree.
			if( type instanceof ClassType ) {
				ClassType classType = (ClassType)type;
				/* If no creates are present, add the default one. */
				if (classType.getMethods("create").isEmpty()) {
					ASTCreateDeclaration createNode = new ASTCreateDeclaration(-1);
					createNode.setModifiers(Modifiers.PUBLIC);
					MethodSignature createSignature = new MethodSignature(classType, "create",
							createNode.getModifiers(), createNode.getDocumentation(), createNode);
					createNode.setMethodSignature(createSignature);
					classType.addMethod(createSignature);
					// Note: the node is null for the default create, since nothing was made.
				}
				
				/* Add copy method with a set to hold addresses. */
				ASTMethodDeclaration copyNode = new ASTMethodDeclaration(-1);
				copyNode.setModifiers(Modifiers.PUBLIC | Modifiers.READONLY);
				MethodSignature copySignature = new MethodSignature(classType, "copy", copyNode.getModifiers(), copyNode.getDocumentation(), copyNode);
				copySignature.addParameter("addresses", new SimpleModifiedType(Type.ADDRESS_MAP));
				copySignature.addReturn(new SimpleModifiedType(classType));					
				copyNode.setMethodSignature(copySignature);
				classType.addMethod(copySignature);
				
				/* Add default getters and setters. */
				for( Map.Entry<String, Node> field : classType.getFields().entrySet() ) {
					Node node = field.getValue();
					Modifiers fieldModifiers = node.getModifiers();
					
					if( fieldModifiers.isGet() || fieldModifiers.isSet() )	{	
						List<MethodSignature> methods = classType.getMethods(field.getKey());
						int getterCount = 0;
						int setterCount = 0;
						int getterCollision = 0;
						int setterCollision = 0;
						
						for( MethodSignature signature : methods) {
							if( signature.getModifiers().isGet() )
								getterCount++;
							else if( signature.getModifiers().isSet() )
								setterCount++;
							else if( signature.getParameterTypes().isEmpty() )
								getterCollision++;
							else if( signature.getParameterTypes().size() == 1 && field.getValue().getType().equals(signature.getParameterTypes().get(0).getType())  )
								setterCollision++;
						}
						
						if( fieldModifiers.isGet() && getterCount == 0 ) {
							if( getterCollision > 0 )								
								addError(node, Error.INVALID_MODIFIER,
										"Default get property " + field.getKey() +
										" cannot replace a non-get method with an indistinguishable signature" );
							else {
								ASTMethodDeclaration methodNode = new ASTMethodDeclaration(-1);
								// Default get is readonly.
								methodNode.setModifiers(Modifiers.PUBLIC | Modifiers.GET  | Modifiers.READONLY);														
								methodNode.setImage(field.getKey());								
								MethodType methodType = new MethodType(classType, methodNode.getModifiers(), methodNode.getDocumentation());
								methodNode.setType(methodType);
								Modifiers modifiers = new Modifiers(fieldModifiers);
								modifiers.removeModifier(Modifiers.GET);
								modifiers.removeModifier(Modifiers.FIELD);									
								if( modifiers.isImmutable() )
									modifiers.addModifier(Modifiers.IMMUTABLE);																		
								if( modifiers.isSet() )
									modifiers.removeModifier(Modifiers.SET);
								SimpleModifiedType modifiedType = new SimpleModifiedType(field.getValue().getType(), modifiers); 
								methodType.addReturn(modifiedType);
								MethodSignature signature = new MethodSignature(methodType, field.getKey(), classType, methodNode);								
								checkParameterAndReturnVisibility(signature, classType); // Make sure field type is visible
								classType.addMethod(signature);
							}								
						}
						
						if( fieldModifiers.isSet() && setterCount == 0 ) {
							if( setterCollision > 0 )								
								addError(node, Error.INVALID_MODIFIER, "Default set property " +  field.getKey() + " cannot replace a non-set method with an indistinguishable signature" );
							else {
								ASTMethodDeclaration methodNode = new ASTMethodDeclaration(-1);
								methodNode.setModifiers(Modifiers.PUBLIC | Modifiers.SET );
								methodNode.setImage(field.getKey());								
								MethodType methodType = new MethodType(classType, methodNode.getModifiers(), methodNode.getDocumentation());
								methodNode.setType(methodType);
								Modifiers modifiers = new Modifiers(fieldModifiers);
								//is it even possible to have an immutable or readonly set?
								if( modifiers.isImmutable() )
									addError(node, Error.INVALID_MODIFIER, "Default set property " +  field.getKey() + " cannot be created for an immutable field" );										
								modifiers.removeModifier(Modifiers.SET);
								modifiers.removeModifier(Modifiers.FIELD);									
								modifiers.addModifier(Modifiers.ASSIGNABLE);
								if( modifiers.isGet() )
									modifiers.removeModifier(Modifiers.GET);
								if( modifiers.isWeak() )
									modifiers.removeModifier(Modifiers.WEAK);
								SimpleModifiedType modifiedType = new SimpleModifiedType(field.getValue().getType(), modifiers);									
								methodType.addParameter("value", modifiedType );									
								MethodSignature signature = new MethodSignature(methodType, field.getKey(), classType, methodNode);
								checkParameterAndReturnVisibility(signature, classType); // Make sure field type is visible
								classType.addMethod(signature);
							}								
						}
					}						
				}
			}
		}		
	}	
	
	private Map<Type,Node> instantiateTypeParameters( Map<Type,Node> nodeTable ) {		
		/* Build graph of type parameter dependencies. */
		DirectedGraph<Node> graph = new DirectedGraph<Node>();		
		Map<Type, Node> uninstantiatedNodes = new HashMap<Type,Node>();
		
		for(Node declarationNode : nodeTable.values() ) {
			graph.addNode( declarationNode );
			Type typeWithoutArguments = declarationNode.getType().getTypeWithoutTypeArguments(); 
			uninstantiatedNodes.put(typeWithoutArguments, declarationNode);
		}
		
		for(Node declarationNode : nodeTable.values() )
			for( Type dependency : declarationNode.getType().getTypeParameterDependencies()  ) {
				Node dependencyNode = uninstantiatedNodes.get( dependency.getTypeWithoutTypeArguments() );
				if( dependencyNode == null )
					addError(declarationNode, Error.INVALID_DEPENDENCY, "Type " +
							declarationNode.getType() + " is dependent on unavailable type " +
							dependency, dependency);
				else
					graph.addEdge(dependencyNode, declarationNode);				
			}
		
		/* Update parameters based on topological sort of type parameter dependencies. */
		// Need a new table because types have changed and will hash to different locations.		
		Map<Type, Node> updatedNodeTable = new HashMap<Type,Node>();
		try {					
			List<Node> nodeList = graph.topologicalSort();
			/* Update type parameters. */
			for(Node declarationNode : nodeList ) {	
				Type type = declarationNode.getType();				
				updateTypeParameters( type, declarationNode );
				
				Type cleanType = declarationNode.getType().getTypeWithoutTypeArguments();				
				updatedNodeTable.put(cleanType, declarationNode);
			}
		}
		catch( CycleFoundException e ) {
			Type type = (Type) e.getCycleCause();			
			addError(nodeTable.get(type), Error.INVALID_TYPE_PARAMETERS, "Type " + type + " contains a circular type parameter definition", type);
		}
		
		uninstantiatedNodes.clear();		
		return updatedNodeTable;
	}
	
	
	private List<Node> sortOnIsLists(Map<Type,Node> nodeTable) {
		//now make dependency graph based on extends and implements
		DirectedGraph<Node> graph = new DirectedGraph<Node>();
		
		for(Node declarationNode : nodeTable.values() )
			graph.addNode( declarationNode );
		
		for(Node declarationNode : nodeTable.values() ) {
			Type type = declarationNode.getType();
			
			if( type instanceof ClassType ) {
				ClassType classType = (ClassType) type;
				
				if( classType.getExtendType() != null ) {
					Node dependencyNode = nodeTable.get(classType.getExtendType().getTypeWithoutTypeArguments());
					if( dependencyNode == null )
						addError(declarationNode, Error.INVALID_DEPENDENCY, "Dependency not found");
					else
						graph.addEdge(dependencyNode, declarationNode);
				}
			}
				
			for( Type dependency : type.getInterfaces() ) {				
				dependency = dependency.getTypeWithoutTypeArguments();
				Node dependencyNode = nodeTable.get(dependency);
				if( dependencyNode == null )
					addError(declarationNode, Error.INVALID_DEPENDENCY, "Dependency not found");
				else
					graph.addEdge(dependencyNode, declarationNode);
			}
		}
		
		List<Node> nodeList = null;
		
		try {	
			 nodeList = graph.topologicalSort();
		}	
		catch( CycleFoundException e ) {
			Node node = (Node) e.getCycleCause();			
			addError(node, Error.INVALID_HIERARCHY, "Type " + node.getType() + " contains a circular is definition", node.getType());
		}
		
		return nodeList;
	}
	
	
	private void updateIsLists(List<Node> nodeList) {	
		for(Node declarationNode : nodeList ) {	
			Type type = declarationNode.getType();
			if( type instanceof ClassType ) {
				ClassType classType = (ClassType) type;					
				ClassType parent = classType.getExtendType();

				/* Update parent */
				if( parent != null && parent instanceof UninstantiatedClassType ) {
					try {
						parent = ((UninstantiatedClassType)parent).partiallyInstantiate();
						classType.setExtendType( parent );										
					} 
					catch (InstantiationException e) {
						addError( declarationNode, Error.INVALID_TYPE_ARGUMENTS, e.getMessage() );
					}
				}
				
				if( parent != null && parent.hasOuter() && (!type.hasOuter() || !type.getOuter().isSubtype(parent.getOuter() ) ) )
					addError( declarationNode, Error.INVALID_PARENT, "Type " + type + " cannot have type " + parent + " as a parent since they have incompatible outer classes" );				
			}			
			
			/* Update interfaces */
			ArrayList<InterfaceType> interfaces = type.getInterfaces();
								
			for( int i = 0; i < interfaces.size(); i++ ) { 
				InterfaceType interfaceType = interfaces.get(i);
				if( interfaceType instanceof UninstantiatedInterfaceType  ) {
					try {
						interfaceType = ((UninstantiatedInterfaceType)interfaceType).partiallyInstantiate();
						interfaces.set( i, interfaceType );							
					} 
					catch (InstantiationException e)  {
						addError(declarationNode, Error.INVALID_TYPE_ARGUMENTS, e.getMessage() );
					}						
				}					
			}			
		}
	}
		
	private void checkOverrides(List<Node> nodeList) {	
		for( Node declarationNode : nodeList ) {	
			setFile( declarationNode.getFile() ); // Used for error messages.
			if( declarationNode.getType() instanceof ClassType ) {					
				ClassType classType = (ClassType)declarationNode.getType();
				ClassType parent = classType.getExtendType();			
			
				if( parent != null) {
					/* Enforce immutability: any mutable parent method must be overridden. */ 
					if( classType.getModifiers().isImmutable() ) {
						List<MethodSignature> list = classType.orderAllMethods();
						for( MethodSignature signature : list )
							if( !signature.isCreate() && !signature.getModifiers().isReadonly() )
								addError(signature.getNode(), Error.INVALID_METHOD, "Mutable parent method " + signature + " must be overridden by readonly method" );
					}
					
					/* Check overridden methods to make sure:
					 * 1. No locked method has been overridden
					 * 2. All overrides match exactly  (if it matches everything but return type...ambiguous!)
					 * 3. Readonly methods cannot be overridden by mutable methods
					 * 4. No overridden methods have been narrowed in access. */					
					for( List<MethodSignature> signatures : classType.getMethodMap().values() )					
						for( MethodSignature signature : signatures ) {
							if( parent.recursivelyContainsIndistinguishableMethod(signature) && !signature.isCreate() ) {
								MethodSignature parentSignature = parent.recursivelyGetIndistinguishableMethod(signature);
								Node node = signature.getNode();
								Modifiers parentModifiers = parentSignature.getModifiers();
								Modifiers modifiers = signature.getModifiers();
								
								if( parentModifiers.isLocked() )
									addError( node, Error.INVALID_OVERRIDE, "Method " + signature + " cannot override locked method " + parentSignature );
								else if( !parentSignature.getReturnTypes().canAccept(signature.getReturnTypes()) )
									addError( node, Error.INVALID_OVERRIDE, "Overriding method " + signature + " differs only by return type from " + parentSignature );
								else if( !modifiers.isReadonly() && parentModifiers.isReadonly()  )
									addError( node, Error.INVALID_OVERRIDE, "Mutable method " + signature + " cannot override readonly method" );
								else if( parentModifiers.isPublic() && (modifiers.isPrivate() || modifiers.isProtected()) )
									addError( node, Error.INVALID_OVERRIDE, "Overriding method " + signature + " cannot reduce visibility of public method " + parentSignature );
								else if( parentModifiers.isProtected() && modifiers.isPrivate()  )
									addError( node, Error.INVALID_OVERRIDE, "Overriding method " + signature + " cannot reduce visibility of protected method " + parentSignature );									
							}
						}
				}
				
				/* Check to see if all interfaces are satisfied. */				
				for( InterfaceType interfaceType : classType.getInterfaces() )  {	
					ArrayList<String> reasons = new ArrayList<String>();
					
					if( !classType.satisfiesInterface(interfaceType, reasons) ) {
						String message = "Type " + classType + " does not implement interface " + interfaceType;
						for( String reason : reasons )
							message += "\n\t" + reason;
						addError(Error.MISSING_INTERFACE, message );
					}
					
					if( classType.hasOuter() && interfaceType.recursivelyContainsMethod("create") )
						addError(Error.INVALID_INTERFACE, "Inner class " + classType + " cannot implement interface " + interfaceType + " because it contains a create method");
				}
			}
		}				
	}	

	/* Ensure a method's parameters and return types are equally or more
	 * visible than the method */
	private void checkParameterAndReturnVisibility(MethodSignature method, ClassType parent) {
		// The return types of private methods will never be less visible
		if (method.getModifiers().hasModifier(Modifiers.PRIVATE))
			return;
		
		// If this is false, the method must be protected
		boolean isPublic = method.getModifiers().hasModifier(Modifiers.PUBLIC);
		
		// Test each of its return types (within the sequence type)
		for (ModifiedType modifiedType : method.getReturnTypes()) {
			Type type = modifiedType.getType();
			
			// Only bother to test inner classes (relative to the method's containing class)
			if (parent.recursivelyContainsInnerClass(type)) {
				Type outer = type;
				
				// Climb through nested inner types looking for any lesser visibility
				while (outer != parent) {
					// If (PRIVATE || (!isPublic && PROTECTED)
					if (outer.getModifiers().hasModifier(Modifiers.PRIVATE)
							|| (isPublic && outer.getModifiers().hasModifier(Modifiers.PROTECTED))) {
						addError(method.getNode(), Error.ILLEGAL_ACCESS, "Method " + method + " is more visible than return type " + type);
						break; // No need to keep climbing
					}
						
					outer = outer.getOuter();
				}
			}
		}
		
		// Test each of its parameter types (within the sequence type)
		for (ModifiedType modifiedType : method.getParameterTypes()) {
			Type type = modifiedType.getType();
			
			// Only bother to test inner classes (relative to the method's containing class)
			if (parent.recursivelyContainsInnerClass(type)) {
				Type outer = type;
				
				// Climb through nested inner types looking for any lesser visibility
				while (outer != parent) {
					// If (PRIVATE || (!isPublic && PROTECTED)
					if (outer.getModifiers().hasModifier(Modifiers.PRIVATE)
							|| (isPublic && outer.getModifiers().hasModifier(Modifiers.PROTECTED))) {
						addError(method.getNode(), Error.ILLEGAL_ACCESS, "Method " + method + " is more visible than parameter type " + type);
						break; // No need to keep climbing
					}
						
					outer = outer.getOuter();
				}
			}
		}
	}
	
	private void updateTypeParameters( Type type, Node declarationNode ) {
		if( type.isParameterized() ) {	
			for( ModifiedType modifiedType : type.getTypeParameters() ) {						
				TypeParameter typeParameter = (TypeParameter) modifiedType.getType();
			
				try {
					typeParameter.updateFieldsAndMethods();
				}
				catch( InstantiationException e ) {
					addError( declarationNode, Error.INVALID_TYPE_ARGUMENTS, e.getMessage() );
				}
			}
		}
	}
	
	
	/**
	 * Tries to find a type without any other information.
	 * This method starts by looking in the current method, which will
	 * eventually look through outer types and then imported types.
	 * This method overrides the <code>BaseChecker</code> method and
	 * adds the ability to resolve type parameters.
	 * @param name		name of the type
	 * @return			type found or <code>null</code> if not found
	 */
	@Override
	public Type lookupType( String name ) {		
		// In declaration header, check type parameters of current class declaration.
		if( declarationType != null &&  currentType != declarationType ) {
			if( declarationType.isParameterized() ) {
				for( ModifiedType modifiedParameter : declarationType.getTypeParameters() ) {					
					Type parameter = modifiedParameter.getType();					
					if( parameter instanceof TypeParameter ) {
						TypeParameter typeParameter = (TypeParameter) parameter;
						if( typeParameter.getTypeName().equals(name) )
							return typeParameter;
					}					
				}				
			}
		}
		return super.lookupType(name);
	}
		
	/* Checks method and field modifiers to see if they are legal. 
	 * The kind is "constant" or "method", for error reporting purposes.
	 */
	private boolean checkModifiers( Node node, String kind ) {
		int visibilityModifiers = 0;
		Modifiers modifiers = node.getModifiers();
		boolean success = true;
		
		/* Count up the visibility modifiers. */ 
		if( modifiers.isPublic())
			visibilityModifiers++;
		if( modifiers.isPrivate())
			visibilityModifiers++;
		if( modifiers.isProtected())
			visibilityModifiers++;
		
		if( visibilityModifiers > 1 ) {
			addError(Error.INVALID_MODIFIER, "Only one public, private, or protected modifier can be used at once" );
			success = false;
		}
		
		if( currentType instanceof InterfaceType ) {
			if( visibilityModifiers > 0 ) {			
				addError(Error.INVALID_MODIFIER, "Interface " + kind + "s cannot be marked public, private, or protected since they are all public by definition" );
				success = false;
			}			
			
			node.addModifier(Modifiers.PUBLIC);
			node.getType().addModifier(Modifiers.PUBLIC);
		}
		else if( visibilityModifiers == 0 ) {			
			addError(Error.INVALID_MODIFIER, "Every class " + kind + " must be specified as public, private, or protected" );
			success = false;
		}
		
		return success;
	}
	
	/* Visitor methods and their helpers below this point. */	
	
	
	/* Helper method called by several different visitor methods. */
	private Object visitMethod( String name, SignatureNode node, Boolean secondVisit ) {	
		MethodSignature signature;
		
		if( secondVisit ) {
			signature = node.getMethodSignature();			
			
			/* Make sure we don't already have an indistinguishable method. */
			if( currentType.containsIndistinguishableMethod(signature) ) {				
				// Get the first indistinguishable signature for error reporting. 
				MethodSignature method = currentType.getIndistinguishableMethod(signature);				
				addError(Error.MULTIPLY_DEFINED_SYMBOL,
						"Indistinguishable method already declared on line " +
						method.getNode().getLineStart());
				return WalkType.NO_CHILDREN;
			}
			
			/* Check to see if the method has a body or not (and if it should). */
			if( currentType instanceof ClassType ) {			
				if( isMeta && node.hasBlock() )
					addError(node, Error.INVALID_STRUCTURE, "Method " + signature + " must not define a body in a meta file");
				
				if( !isMeta ) {					
					if( !node.hasBlock() && !signature.getModifiers().isAbstract() && !signature.getModifiers().isNative() )
						addError(node, Error.INVALID_STRUCTURE, "Method " + signature + " must define a body");
					
					if( node.hasBlock() && (signature.getModifiers().isAbstract() || signature.getModifiers().isNative() ) )
						addError(node, Error.INVALID_STRUCTURE, (signature.getModifiers().isAbstract() ? "Abstract" : "Native") + " method " + signature + " must not define a body");
					
					/* Check to see if the method's parameters and return types are the
					 * correct level of visibility, e.g., a public method shouldn't
					 * return a private inner class.
					 */
					checkParameterAndReturnVisibility(signature, (ClassType)currentType);
				}
			}
			else if( currentType instanceof InterfaceType ) {
				if( node.hasBlock() )
					addError(node, Error.INVALID_STRUCTURE, "Interface method " + signature + " must not define a body");
			}
			
			// Add the method to the current type.
			currentType.addMethod(signature);
			// Since we're leaving the method, take it off the method stack.
			currentMethod.removeFirst();
		}
		else {
			/* Create the method signature and add this method to the method stack. */
			signature = new MethodSignature( currentType, name, node.getModifiers(),
					node.getDocumentation(), node );
			node.setMethodSignature(signature);
			MethodType methodType = signature.getMethodType();
			node.setType( methodType );
			node.setEnclosingType(currentType);
			checkModifiers( node, "method" );
			currentMethod.addFirst(node);
		}		
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTResultTypes node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {	
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				node.addType(node.jjtGetChild(i));
		}
		
		return WalkType.POST_CHILDREN;			
	}
	
	public Object visit(ASTCreateDeclarator node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {
			ASTCreateDeclaration parent = (ASTCreateDeclaration) node.jjtGetParent();
			MethodSignature signature = parent.getMethodSignature();
			visitDeclarator( node, signature  );			
			
			if( (currentType instanceof SingletonType) && signature.getParameterTypes().size() > 0 )
					addError(Error.INVALID_SINGLETON_CREATE, "Singleton type " + currentType + " can only specify a default create");
		}		
		
		return WalkType.POST_CHILDREN;	
	}
	
	public Object visit(ASTMethodDeclarator node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {
			ASTMethodDeclaration parent = (ASTMethodDeclaration) node.jjtGetParent();
			visitDeclarator( node, parent.getMethodSignature() );			
		}		
		
		return WalkType.POST_CHILDREN;
	}
	
	private void visitDeclarator( Node node, MethodSignature signature ) {	
		// ASTCreateDeclarator will never have type parameters. 
		int index = 0;		
		if( node.jjtGetChild(index) instanceof ASTTypeParameters ) {
			Type methodType = signature.getMethodType();
			methodType.setParameterized(true);
			index++;
		}
		
		/* Add parameters to the signature */
		ASTFormalParameters parameters = (ASTFormalParameters) node.jjtGetChild(index);
		List<String> parameterNames = parameters.getParameterNames();
		SequenceType parameterTypes = parameters.getType();		
		
		for( int i = 0; i < parameterNames.size(); ++i )				
			signature.addParameter(parameterNames.get(i), parameterTypes.get(i));
		
		if( signature.getModifiers().isSet() ) {				
			if( parameterTypes.size() != 1 )
				addError(Error.INVALID_MODIFIER,
						"Methods marked with set must have exactly one parameter");
			else
				signature.getParameterTypes().get(0).getModifiers().addModifier(Modifiers.ASSIGNABLE);
		}			
		else if( signature.getModifiers().isGet() ) {
			if( parameterTypes.size() != 0 )
				addError(Error.INVALID_MODIFIER,
						"Methods marked with get cannot have any parameters");
		}		

		/* Add return types */
		index++;
		if( node.jjtGetNumChildren() > index ) { // Creates have no results.		
			ASTResultTypes results = (ASTResultTypes) node.jjtGetChild(index);
					
			for( ModifiedType modifiedType : results.getType() ) 
				signature.addReturn(modifiedType);
		
			if( signature.getModifiers().isSet() ) {				
				if( signature.getReturnTypes().size() != 0 )
					addError(Error.INVALID_MODIFIER,
							"Methods marked with set cannot have return values");				
			}			
			else if( signature.getModifiers().isGet() ) {
				if( signature.getReturnTypes().size() != 1 )
					addError(Error.INVALID_MODIFIER,
							"Methods marked with get must have exactly one return value");
			}
		}		
	}
	
	public Object visit(ASTFormalParameter node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {
			Type type = node.jjtGetChild(1).getType(); // Child 0 is modifiers.			
			node.setType( type );
		
			if( type instanceof SingletonType )
				addError(Error.INVALID_TYPE, "Parameter cannot be defined with singleton type "
						+ type);			
		}		
	
		return WalkType.POST_CHILDREN;
	}	
	
	public Object visit(ASTFormalParameters node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {		
			/* Iterate through formal parameters. */
			for( int i = 0; i < node.jjtGetNumChildren(); ++i ) {
				Node parameter = node.jjtGetChild(i);
				String parameterName = parameter.getImage();
				if( node.getParameterNames().contains( parameterName ) )
					addError( Error.MULTIPLY_DEFINED_SYMBOL, "Symbol " + parameterName +
							" already defined as a parameter name" );
				if( parameter.getType() instanceof SingletonType )
					addError( parameter, Error.INVALID_PARAMETERS,
							"Cannot define method with singleton parameter" );				
				node.addParameter(parameterName, parameter);
			}	
		}
		
		return WalkType.POST_CHILDREN;			
	}	
	

	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )		
			currentPackage = packageTree;		
				
		return WalkType.POST_CHILDREN;			
	}
	
	private void updateImports( Node node ) {
		/* Changing these items updates the imports inside the type. */
		List<Object> importedItems = node.getType().getImportedItems();
		for( int i = 0; i < importedItems.size(); i++ ) {
			Object thing = importedItems.get(i);
			String item = (String) thing;
			if( item.contains("@") ) { // Single class.			
				Type type = lookupType(item);				
				//shouldn't fail
				if( type == null )
					addError(Error.INVALID_IMPORT, "Undefined type " + item + " cannot be imported");
				else 
					importedItems.set(i, type);
			}
			else {	// Whole package.			
				Package p = packageTree.getChild(item);
				if( p == null )
					addError(Error.INVALID_IMPORT, "Undefined package " + item + " cannot be imported");
				else
					importedItems.set(i, p);					
			}			
		}
	}
	
	@Override
	public Object visit(ASTLiteral node, Boolean secondVisit) throws ShadowException {							
		node.setType( literalToType( node.getLiteral() ) );
		return WalkType.NO_CHILDREN;			
	}
	
	@Override
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {
			Node child = node.jjtGetChild(1); // Child 0 is modifiers. 
			node.setType(child.getType());			
		}
		
		return WalkType.POST_CHILDREN;	
	}
		
	private Object visitDeclaration( Node node, Boolean secondVisit ) throws ShadowException {
		if( secondVisit ){
			ASTIsList list = null;			
			for( int i = 0; i < node.jjtGetNumChildren() && list == null; ++i )
				if( node.jjtGetChild(i) instanceof ASTIsList )
					list = (ASTIsList)(node.jjtGetChild(i));
						
			if( declarationType instanceof InterfaceType || declarationType instanceof EnumType ) {
				String kind;
				if( declarationType instanceof EnumType ) {							
					kind = "Enum type ";
					EnumType enumType = (EnumType) declarationType;
					enumType.setExtendType(Type.ENUM);
				}
				else
					kind = "Interface type ";
				
				if( list != null )					
					for( int i = 0; i < list.jjtGetNumChildren(); i++ ) {					
						Type type = list.jjtGetChild(i).getType();
						if( type instanceof InterfaceType )
							declarationType.addInterface((InterfaceType)type);
						else				
							addError(Error.INVALID_INTERFACE, kind + declarationType +
									" cannot implement non-interface type " + type, type);
					}
			}	
			// Should only be classes, singletons, and exceptions.
			else if( declarationType instanceof ClassType ) { 					
				ClassType classType = (ClassType)declarationType;
				String kind;
				if( classType.getClass() == ExceptionType.class )
					kind = "Exception type ";
				else if( classType.getClass() == SingletonType.class )
					kind = "Singleton type ";
				else
					kind = "Class type ";
				
				if( list != null )				
					for( int i = 0; i < list.jjtGetNumChildren(); ++i ) {
						Type type = list.jjtGetChild(i).getType();
						String otherKind;
						if( type.getClass() == ExceptionType.class )
							otherKind = "exception type ";
						else if( type.getClass() == SingletonType.class )
							otherKind = "singleton type ";
						else
							otherKind = "class type ";
						
						if( type instanceof ClassType ) {
							// Exceptions are the only things that can extend exceptions.
							if(((declarationType.getClass() == ExceptionType.class) !=
									(type.getClass() == ExceptionType.class)) ||
									type.getClass() == SingletonType.class )
								 // Nothing can extend a singleton.
								addError(Error.INVALID_PARENT, kind + declarationType +
										" cannot be child of " + otherKind + type);														
							else if( type.getModifiers().isLocked() )
								addError(Error.INVALID_PARENT, kind + declarationType +
										" cannot be child of locked " + otherKind + type);
							else if( i != 0 )
								addError(Error.INVALID_PARENT, kind + declarationType +
										" can only be child of " + otherKind + type + " if " + type + " is listed first in the is list");
							else
								classType.setExtendType((ClassType)type);
						}
						else if( type instanceof InterfaceType )
							classType.addInterface((InterfaceType)type);
						else				
							addError(Error.INVALID_TYPE, kind + classType +
									" cannot extend or implement type " + type, classType, type);
					}
				
				/* Use defaults if no extend type set. */
				if( classType.getExtendType() == null ) {
					if( classType instanceof ExceptionType ) {
						// Special case only for the root of all exceptions.
						if( classType == Type.EXCEPTION ) 
							classType.setExtendType( Type.OBJECT );
						else
							classType.setExtendType( Type.EXCEPTION );
					}
					// The Object class is the only class with a null parent.
					else if( classType != Type.OBJECT ) 
						classType.setExtendType( Type.OBJECT );
				}
			}		
			
			if( declarationType.getOuter() != null )			
				declarationType.addTypeParameterDependency( declarationType.getOuter() );
			// Outermost type
			else { 			
				// Set compilation unit type.
				Node parent = node.jjtGetParent();
				parent.setType(declarationType);								
			}
			
			declarationType = declarationType.getOuter();
		}
		else {
			declarationType = node.getType();
			currentPackage = declarationType.getPackage();
			updateImports( node );
		}
			
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit( ASTClassOrInterfaceDeclaration node, Boolean secondVisit )
			throws ShadowException {				
		return visitDeclaration( node, secondVisit );
	}
	
	@Override
	public Object visit( ASTEnumDeclaration node, Boolean secondVisit ) throws ShadowException {				
		return visitDeclaration( node, secondVisit );
	}
	
	@Override
	public Object visit( ASTCreateDeclaration node, Boolean secondVisit ) throws ShadowException {
		Node methodDeclarator = node.jjtGetChild(0);
		return visitMethod( methodDeclarator.getImage(), node, secondVisit );
	}
	
	@Override
	public Object visit( ASTDestroyDeclaration node, Boolean secondVisit ) throws ShadowException {			
		return visitMethod( node.getImage(), node, secondVisit );		
	}

	@Override
	public Object visit( ASTMethodDeclaration node, Boolean secondVisit ) throws ShadowException {		
		Node methodDeclarator = node.jjtGetChild(0);
		
		// Creates are not marked immutable or readonly since they change fields
		if( !secondVisit && currentType != null && !node.getImage().equals("create") ) {
			if( currentType.getModifiers().isImmutable() ) {
				node.addModifier(Modifiers.READONLY);
				methodDeclarator.addModifier(Modifiers.READONLY);
			}
		}
		return visitMethod( methodDeclarator.getImage(), node, secondVisit );
	}
	
	@Override
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		// A field declaration has a type followed by an identifier (or a sequence of them).	
		Type type = node.jjtGetChild(0).getType();
						
		node.setType(type);
		node.setEnclosingType(currentType);
		node.addModifier(Modifiers.FIELD);
		
		// Constants must be public, private, or protected,
		// unlike regular fields which are implicitly private.
		if( node.getModifiers().isConstant() )
			checkModifiers( node, "constant" );
		
		if( currentType.getModifiers().isImmutable() )
			node.addModifier( Modifiers.IMMUTABLE );
		
		// All interface fields are implicitly public and constant.
		if( currentType instanceof InterfaceType ) {
			node.addModifier( Modifiers.CONSTANT ); 
			node.addModifier( Modifiers.PUBLIC );
		}
		
		if( type instanceof UninstantiatedType && node.getModifiers().isConstant() ) {
			UninstantiatedType uninstantiatedType = (UninstantiatedType) type;
			for( ModifiedType argument : uninstantiatedType.getTypeArguments() ) {
				if( argument.getType() instanceof TypeParameter ) {					
					addError( Error.INVALID_TYPE_PARAMETERS,
							"Field marked constant cannot have a type with a type parameter" );
					break;
				}				
			}
		}	
						
		/* Add all the identifiers. */
		for( int i = 1; i < node.jjtGetNumChildren(); ++i ) {
			Node declarator = node.jjtGetChild(i);
			declarator.setType(type);
			declarator.setModifiers(node.getModifiers());
			declarator.setDocumentation(node.getDocumentation());
			declarator.setEnclosingType(currentType);
			
			String symbol = declarator.getImage();
			
			/* Make sure we don't already have this symbol.
			 * Methods and fields can have the same name since they can be
			 * disambiguated by colon or dot. */			
			if( currentType.containsField( symbol ) )						
				addError( Error.MULTIPLY_DEFINED_SYMBOL, "Field name " + symbol +
						" already declared on line " +
						currentType.getField(symbol).getLineStart() );
			else if( currentType instanceof ClassType &&
					((ClassType)currentType).containsInnerClass( symbol ))
				addError(Error.MULTIPLY_DEFINED_SYMBOL, "Field name " + symbol +
						" already declared as inner class");
			else
				currentType.addField(symbol, declarator);
			
			if( type instanceof SingletonType ) {
				if( node.getModifiers().isGet() )
					addError(node, Error.INVALID_MODIFIER,
							"Modifier get cannot be applied to field " + symbol +
							" with singleton type");
				if( node.getModifiers().isImmutable() )
					addError(node, Error.INVALID_MODIFIER,
							"Modifier immutable cannot be applied to field " + symbol +
							" with singleton type");
				if( node.getModifiers().isNullable() )
					addError(node, Error.INVALID_MODIFIER,
							"Modifier nullable cannot be applied to field " + symbol +
							" with singleton type");
				if( node.getModifiers().isReadonly() )
					addError(node, Error.INVALID_MODIFIER,
							"Modifier readonly cannot be applied to field " + symbol +
							" with singleton type");
				if( node.getModifiers().isSet() )
					addError(node, Error.INVALID_MODIFIER,
							"Modifier set cannot be applied to field " + symbol +
							" with singleton type");
				if( node.getModifiers().isWeak() )
					addError(node, Error.INVALID_MODIFIER,
							"Modifier weak cannot be applied to field " + symbol +
							" with singleton type");				
			}			
		}		
			
		return WalkType.POST_CHILDREN;
	}
		
	/* Type parameters will only be visited here on type declarations and class methods. */ 
	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit ) {			
			SequenceType typeParameters = node.getType();			
			for( int i = 0; i < node.jjtGetNumChildren(); ++i ) {
				Node typeParameter = node.jjtGetChild(i); 
				typeParameters.add(typeParameter);
				TypeParameter newParameter = (TypeParameter) typeParameter.getType();
				
				for( int j = 0; j < i; ++j ) {
					TypeParameter existingParameter =
							(TypeParameter) typeParameters.get(j).getType();
					if( newParameter.getTypeName().equals(existingParameter.getTypeName()) )
						addError(Error.MULTIPLY_DEFINED_SYMBOL, "Type parameter " +
								newParameter.getTypeName() + " has already been defined" );
				}
			}
		}	
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit ) {	
			TypeParameter typeParameter = (TypeParameter) node.getType();
			
			if( node.jjtGetNumChildren() > 0 ) {
				ASTIsList bounds = (ASTIsList)(node.jjtGetChild(0));				
				for( int i = 0; i < bounds.jjtGetNumChildren(); i++ )								
					typeParameter.addBound(bounds.jjtGetChild(i).getType());				
			}
		}
		else {	
			/* Type parameters are created on the first visit so that bounds dependent
			 * on them can look up the right type. */
			String symbol = node.getImage();
			TypeParameter typeParameter = new TypeParameter(symbol, declarationType);
			Type type = declarationType; //add parameters to current class
					
			if( type instanceof SingletonType )			
				addError(Error.INVALID_TYPE_PARAMETERS, "Singleton type " + declarationType.getTypeName() + " cannot be parameterized");
			else if( type instanceof ExceptionType )			
				addError(Error.INVALID_TYPE_PARAMETERS, "Exception type " + declarationType.getTypeName() + " cannot be parameterized");
			
			node.setType(typeParameter);
			type.addTypeParameter(node);			
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException {	
		if(!secondVisit)
			return WalkType.POST_CHILDREN;		
		
		Type type = null;
			
		// Type can be complex: package@Container<T, List<String>, String, Thing<K>>:Stuff<U>
		for( int i = 0; i < node.jjtGetNumChildren() && type != Type.UNKNOWN; i++ ) {	
			Node child = node.jjtGetChild(i);
			String typeName = child.getImage();
			if( i == 0 ) { // Special case for first pass, might include package.				
				if( child instanceof ASTUnqualifiedName ) {
					i++;
					child = node.jjtGetChild(i);
					typeName += "@" + child.getImage();											
				}					
				type = lookupType(typeName);
			}
			else { // On later passes, get inner class from previous.
				if( type instanceof UninstantiatedType )
					type = ((UninstantiatedType)type).getType();
				
				if( type instanceof ClassType ) 
					type = ((ClassType)type).getInnerClass(typeName);
				else
					type = null;
			}			
		
			if(type == null) {
				addError(Error.UNDEFINED_TYPE, "Type " + typeName +
						" not defined in current context");			
				type = Type.UNKNOWN;					
			}
			else {
				if( !isMeta && !classIsAccessible( type, declarationType  ) )		
					addError(Error.ILLEGAL_ACCESS, "Type " + type +
							" not accessible from current context");
				
				if( child.jjtGetNumChildren() == 1 ) { // Contains type arguments.				
					SequenceType arguments = (SequenceType) child.jjtGetChild(0).getType();						
					if( type.isParameterized() ) {
						if( type instanceof ClassType )						
							type = new UninstantiatedClassType( (ClassType)type, arguments );
						else if( type instanceof InterfaceType )
							type = new UninstantiatedInterfaceType( (InterfaceType)type, arguments );
					}
					else {
						addError(Error.UNNECESSARY_TYPE_ARGUMENTS,
								"Type arguments supplied for non-parameterized type " + type);
						type = Type.UNKNOWN;
					}										
				}
				else if( type.isParameterized() ) { // Parameterized but no type arguments.
					addError(Error.MISSING_TYPE_ARGUMENTS,
							"Type arguments not supplied for parameterized type " + typeName);
					type = Type.UNKNOWN;
				}
			}
		}
		// Set the type now that it has type parameters. 
		node.setType(type);
		
		return WalkType.POST_CHILDREN;
	}	
		
	public Object visit(ASTTypeArguments node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {			
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				node.addType(node.jjtGetChild(i));
		}
		
		return WalkType.POST_CHILDREN;
	}

	
	public Object visit(ASTReferenceType node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit ) {
			Node child = node.jjtGetChild(0);
			Type type = child.getType();
			node.setModifiers(new Modifiers(child.getModifiers()));
			
			List<Integer> dimensions = node.getArrayDimensions();			
			if( dimensions.size() == 0 )
				node.setType(type);
			else {				
				ArrayType arrayType = new ArrayType(type, dimensions, node.getModifiers().isNullable());
				node.setType(arrayType);
			}
		}
	
		return WalkType.POST_CHILDREN;
	}
	

	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit) throws ShadowException {
		node.setType(nameToPrimitiveType(node.getImage()));		
		return WalkType.NO_CHILDREN;			
	}	
	
	@Override
	public Object visit(ASTFunctionType node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {		
			MethodType methodType = new MethodType();		
			ASTResultTypes parameters = (ASTResultTypes) node.jjtGetChild(0);
			SequenceType parameterTypes = parameters.getType();
			
			ASTResultTypes returns = (ASTResultTypes) node.jjtGetChild(1);
			SequenceType returnTypes = returns.getType();
							
			for( ModifiedType parameter : parameterTypes  )
				methodType.addParameter(parameter);			
				
			for( ModifiedType type : returnTypes )
				methodType.addReturn(type);
			
			node.setType(methodType);
		}
		
		return WalkType.POST_CHILDREN;		
	}
	
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { 
		if( secondVisit ) {
			boolean isNullable = node.getModifiers().isNullable();
			
			Node child = node.jjtGetChild(0);			
			node.setModifiers(child.getModifiers());
			
			Type type = child.getType();
			
			if( isNullable && type instanceof ArrayType ) {
				ArrayType arrayType = (ArrayType) type;
				type = arrayType.convertToNullable();
			}
			
			node.setType(type);
		}		
		
		return WalkType.POST_CHILDREN;
	}	
	
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		return WalkType.NO_CHILDREN; // Skip all blocks.
	}
	
	public Object visit(ASTCreateBlock node, Boolean secondVisit) throws ShadowException {
		return WalkType.NO_CHILDREN; // Skip all blocks.
	}
	
	public Object visit(ASTConditionalExpression node, Boolean secondVisit) throws ShadowException {
		return WalkType.NO_CHILDREN; // Skip all field initializations.
	}
}
