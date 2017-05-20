/*
 * Copyright 2016 Team Shadow
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

import shadow.ShadowException;
import shadow.parse.Context;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.MethodDeclaratorContext;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
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
	public TypeUpdater( Package packageTree, ErrorReporter reporter )
	{
		super( packageTree, reporter );	
	}
	
	
	/**
	 * Updates all of the types in the given node table and returns and updated table.
	 * This central method of the class performs all of its functions.
	 * @param typeTable			map from types to nodes
	 * @return
	 * @throws ShadowException
	 * @throws TypeCheckException
	 */
	public Map<Type,Context> update( Map<Type,Context> typeTable )
			throws ShadowException {
		
		/* Add fields and methods. */		
		for(Context declarationNode : typeTable.values() ) {			
			isMeta = declarationNode.getPath().toString().endsWith(".meta");
			// Only walk outer types since inner ones will be walked automatically.
			if( !declarationNode.getType().hasOuter() )
				visit(declarationNode);
		}
		
		/* Add default creates and make methods for properties 
		 * Must happen after all fields and methods are added.
		 * Otherwise, an inner type might get a default create added
		 * when it didn't need one.  
		 */		
		for(Context declarationNode : typeTable.values() )
			addCreatesAndProperties(declarationNode);

		/* Instantiate type parameters where necessary, returning a new type table
		 * The old type table can be invalidated when type names change as a result
		 * of instantiating type parameters. */
		if( getErrorReporter().getErrorList().isEmpty() )
			typeTable = instantiateTypeParameters( typeTable );
		
		/* Topologically sort classes by their is lists: parent types and interfaces.
		 * This sorting will also check for circular inheritance or interfaces.
		 */
		List<Context> nodeList = sortOnIsLists( typeTable );		
		
		/* Update is lists based on updated type parameters. */		
		if( getErrorReporter().getErrorList().isEmpty() )
			updateIsLists( nodeList );
		
		/* Update fields and methods. Must happen after is lists are updated, since
		 * generic parameters in the methods could depend on correct parents and interfaces. */
		if( getErrorReporter().getErrorList().isEmpty() )
			updateFieldsAndMethods( nodeList );		
		
		/* After updating all the types, make sure that method overrides are all legal. */
		if( getErrorReporter().getErrorList().isEmpty() )
			checkOverrides( nodeList );
		
		/* Finally, update which types are referenced by other types. */
		if( getErrorReporter().getErrorList().isEmpty() )
			updateTypeReferences( nodeList );
		
		printAndReportErrors();
		
		return typeTable;
	}	
		
	private void updateFieldsAndMethods( List<Context> nodeList ) {
		//update fields and methods			
		for(Context declarationNode : nodeList ) {	
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
	private void updateTypeReferences( List<Context> nodeList ) {
		for(Context declarationNode : nodeList ) {
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
			
			type.addUsedType(Type.SHADOW_POINTER);
			
			// Adding the self adds parents and interfaces and methods
			type.addUsedType(type); 
			
			/* Update imports for meta files, since they won't have statement checking.
			 * Note that imports in meta files have been optimized to include only the
			 * originally imported classes that are referenced. */
			if( declarationNode.getPath().toString().endsWith(".meta") ) {
				for(Object item : type.getImportedItems())
					if( item instanceof Type ) {
						Type importType = (Type) item;
						type.addUsedType(importType);
					}
			}
		}
	}
	
	//We want a token from the same file, for error reporting purposes
	private static Token makeDummyToken(Context node)
	{		
		CommonToken token = new CommonToken(node.getStart());
		//since the token is for a made-up create or property, it has no location in the file
		token.setLine(-1);
		token.setCharPositionInLine(-1);
		
		return token;		
	}
	
	private void addCreatesAndProperties( Context declaration ) {
		Type type = declaration.getType();		
		if( type instanceof ClassType ) {
			ClassType classType = (ClassType)type;
			/* If no creates are present, add the default one. */
			if (classType.getMethods("create").isEmpty()) {
				//might be a terrible idea to create a dummy node
				ShadowParser.CreateDeclarationContext createNode = new ShadowParser.CreateDeclarationContext(null, -1);
				createNode.start = createNode.stop = makeDummyToken(declaration);
				createNode.addModifiers(Modifiers.PUBLIC);
				MethodSignature createSignature = new MethodSignature(classType, "create",
						createNode.getModifiers(), createNode.getDocumentation(), createNode);
				createNode.setSignature(createSignature);
				classType.addMethod(createSignature);
			}
			
			/* If no destroy is present, add the default one. */
			if (classType.getMethods("destroy").isEmpty()) {
				//might be a terrible idea to create a dummy node
				ShadowParser.DestroyDeclarationContext destroyNode = new ShadowParser.DestroyDeclarationContext(null, -1);
				destroyNode.start = destroyNode.stop = makeDummyToken(declaration);
				destroyNode.addModifiers(Modifiers.PUBLIC);
				MethodSignature destroySignature = new MethodSignature(classType, "destroy",
						destroyNode.getModifiers(), destroyNode.getDocumentation(), destroyNode);
				destroyNode.setSignature(destroySignature);
				classType.addMethod(destroySignature);
			}			
			
			/* Add copy method with a set to hold addresses. */
			ShadowParser.MethodDeclarationContext copyNode = new ShadowParser.MethodDeclarationContext(null, -1);
			copyNode.start = copyNode.stop = makeDummyToken(declaration);
			copyNode.addModifiers(Modifiers.PUBLIC | Modifiers.READONLY);				
			MethodSignature copySignature = new MethodSignature(classType, "copy", copyNode.getModifiers(), copyNode.getDocumentation(), copyNode);
			copySignature.addParameter("addresses", new SimpleModifiedType(Type.ADDRESS_MAP));
			copySignature.addReturn(new SimpleModifiedType(classType));					
			copyNode.setSignature(copySignature);
			classType.addMethod(copySignature);
			
			/* Add default getters and setters. */
			for( Entry<String, VariableDeclaratorContext> field : classType.getFields().entrySet() ) {
				Context node = field.getValue();
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
							ShadowParser.MethodDeclarationContext methodNode = new ShadowParser.MethodDeclarationContext(null, -1);
							methodNode.start = methodNode.stop = makeDummyToken(declaration);
							// Default get is readonly.
							methodNode.addModifiers(Modifiers.PUBLIC | Modifiers.GET  | Modifiers.READONLY);
							if( classType.getModifiers().isLocked() )
								methodNode.getModifiers().addModifier(Modifiers.LOCKED);
							MethodType methodType = new MethodType(classType, methodNode.getModifiers(), methodNode.getDocumentation());
							methodNode.setType(methodType);
							Modifiers modifiers = new Modifiers(fieldModifiers);
							modifiers.removeModifier(Modifiers.GET);
							modifiers.removeModifier(Modifiers.FIELD);
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
							ShadowParser.MethodDeclarationContext methodNode = new ShadowParser.MethodDeclarationContext(null, -1);
							methodNode.start = methodNode.stop = makeDummyToken(declaration);
							methodNode.addModifiers(Modifiers.PUBLIC | Modifiers.SET);
							if( classType.getModifiers().isLocked() )
								methodNode.getModifiers().addModifier(Modifiers.LOCKED);
							//methodNode.setImage(field.getKey());								
							MethodType methodType = new MethodType(classType, methodNode.getModifiers(), methodNode.getDocumentation());
							methodNode.setType(methodType);
							Modifiers modifiers = new Modifiers(fieldModifiers);
							//is it even possible to have an immutable or readonly set?
							if( modifiers.isImmutable() )
								addError(node, Error.INVALID_MODIFIER, "Default set property " +  field.getKey() + " cannot be created for an immutable field" );										
							if( modifiers.isReadonly() )
								addError(node, Error.INVALID_MODIFIER, "Default set property " +  field.getKey() + " cannot be created for a readonly field" );
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
	
	private Map<Type,Context> instantiateTypeParameters( Map<Type,Context> nodeTable ) {		
		/* Build graph of type parameter dependencies. */
		DirectedGraph<Context> graph = new DirectedGraph<Context>();		
		Map<Type, Context> uninstantiatedNodes = new HashMap<Type,Context>();
		
		for(Context declarationNode : nodeTable.values() ) {
			graph.addNode( declarationNode );
			Type typeWithoutArguments = declarationNode.getType().getTypeWithoutTypeArguments(); 
			uninstantiatedNodes.put(typeWithoutArguments, declarationNode);
		}
		
		for(Context declarationNode : nodeTable.values() )
			for( Type dependency : declarationNode.getType().getTypeParameterDependencies()  ) {
				Context dependencyNode = uninstantiatedNodes.get( dependency.getTypeWithoutTypeArguments() );
				if( dependencyNode != null )
					graph.addEdge(dependencyNode, declarationNode);
				/*
				if( dependencyNode == null )
					addError(declarationNode, Error.INVALID_DEPENDENCY, "Type " +
							declarationNode.getType() + " is dependent on unavailable type " +
							dependency, dependency);
				else
					graph.addEdge(dependencyNode, declarationNode);
				*/
			}
		
		/* Update parameters based on topological sort of type parameter dependencies. */
		// Need a new table because types have changed and will hash to different locations.		
		Map<Type, Context> updatedNodeTable = new HashMap<Type,Context>();
		try {					
			List<Context> nodeList = graph.topologicalSort();
			/* Update type parameters. */
			for(Context declarationNode : nodeList ) {	
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
	
	
	private List<Context> sortOnIsLists(Map<Type,Context> nodeTable) {
		//now make dependency graph based on extends and implements
		DirectedGraph<Context> graph = new DirectedGraph<Context>();
		
		for(Context declarationNode : nodeTable.values() )
			graph.addNode( declarationNode );
		
		for(Context declarationNode : nodeTable.values() ) {
			Type type = declarationNode.getType();
			
			if( type instanceof ClassType ) {
				ClassType classType = (ClassType) type;
				
				if( classType.getExtendType() != null ) {
					Context dependencyNode = nodeTable.get(classType.getExtendType().getTypeWithoutTypeArguments());
					if( dependencyNode != null )
						graph.addEdge(dependencyNode, declarationNode);
					
					/*if( dependencyNode == null )
						addError(declarationNode, Error.INVALID_DEPENDENCY, "Dependency not found");
					else
						graph.addEdge(dependencyNode, declarationNode);
					*/
				}
			}
				
			for( Type dependency : type.getInterfaces() ) {				
				dependency = dependency.getTypeWithoutTypeArguments();
				Context dependencyNode = nodeTable.get(dependency);
				if( dependencyNode != null )
					graph.addEdge(dependencyNode, declarationNode);
				/*				
				if( dependencyNode == null )
					addError(declarationNode, Error.INVALID_DEPENDENCY, "Dependency not found");
				else
					graph.addEdge(dependencyNode, declarationNode);
				*/
			}
		}
		
		List<Context> nodeList = null;
		
		try {	
			 nodeList = graph.topologicalSort();
		}	
		catch( CycleFoundException e ) {
			Context node = (Context) e.getCycleCause();			
			addError(node, Error.INVALID_HIERARCHY, "Type " + node.getType() + " contains a circular is definition", node.getType());
		}
		
		return nodeList;
	}
	
	
	private void updateIsLists(List<Context> nodeList) {	
		for(Context declarationNode : nodeList ) {	
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
		
	private void checkOverrides(List<Context> nodeList) {	
		for( Context declarationNode : nodeList ) {
			if( declarationNode.getType() instanceof ClassType ) {					
				ClassType classType = (ClassType)declarationNode.getType();
				ClassType parent = classType.getExtendType();			
			
				if( parent != null) {
					/* Enforce immutability: any mutable parent method must be overridden by a readonly method. */ 
					if( classType.getModifiers().isImmutable() ) {
						List<MethodSignature> list = classType.orderAllMethods();
						for( MethodSignature signature : list )
							if( !signature.isCreate() && !signature.isDestroy() && !signature.getModifiers().isReadonly() )
								addError(signature.getNode(), Error.INVALID_METHOD, "Mutable parent method " + signature.getSymbol() + signature.getMethodType() + " must be overridden by readonly method" );
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
								Context node = signature.getNode();
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
						addError(declarationNode, Error.MISSING_INTERFACE, message );
					}
					
					if( classType.hasOuter() && interfaceType.recursivelyContainsMethod("create") )
						addError(declarationNode, Error.INVALID_INTERFACE, "Inner class " + classType + " cannot implement interface " + interfaceType + " because it contains a create method");
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
						addError(method.getNode(), Error.ILLEGAL_ACCESS, "Method " + method.getSymbol() + method.getMethodType() + " is more visible than return type " + type);
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
	
	private void updateTypeParameters( Type type, Context declarationNode ) {
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
	public Type lookupType( Context ctx, String name ) {		
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
		return super.lookupType(ctx, name);
	}
		
	/* Checks method and field modifiers to see if they are legal. 
	 * The kind is "constant" or "method", for error reporting purposes.
	 */
	private boolean checkModifiers( Context node, String kind ) {
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
			addError(node, Error.INVALID_MODIFIER, "Only one public, private, or protected modifier can be used at once" );
			success = false;
		}
		
		if( currentType instanceof InterfaceType ) {
			if( visibilityModifiers > 0 ) {			
				addError(node, Error.INVALID_MODIFIER, "Interface " + kind + "s cannot be marked public, private, or protected since they are all public by definition" );
				success = false;
			}			
			
			node.getModifiers().addModifier(Modifiers.PUBLIC);
			node.getType().addModifier(Modifiers.PUBLIC);
		}
		else if( visibilityModifiers == 0 ) {			
			addError(node, Error.INVALID_MODIFIER, "Every class " + kind + " must be specified as public, private, or protected" );
			success = false;
		}
		
		return success;
	}
	
	/* Visitor methods and their helpers below this point. */	
	
	
	/* Helper method called by several different visitor methods. */
	private void visitMethodPre( String name, Context node) {
		/* Create the method signature and add this method to the method stack. */
		MethodSignature signature = new MethodSignature( currentType, name, node.getModifiers(),
				node.getDocumentation(), node );
		node.setSignature(signature);
		MethodType methodType = signature.getMethodType();
		node.setType( methodType );		
		checkModifiers( node, "method" );
		currentMethod.addFirst(node);
	}
	
	private void visitMethodPost( Context node) {
		MethodSignature signature = node.getSignature();			
		
		/* Make sure we don't already have an indistinguishable method. */
		if( currentType.containsIndistinguishableMethod(signature) ) {				
			// Get the first indistinguishable signature for error reporting. 
			MethodSignature method = currentType.getIndistinguishableMethod(signature);
			String message = "Indistinguishable method " + method + " already declared";
			if( method.getNode().getStart() != null )
				message += " on line " + method.getNode().getStart().getLine();
			addError(node, Error.MULTIPLY_DEFINED_SYMBOL, message);
			return;
		}
		
		boolean hasBlock = signature.hasBlock();
		
		/* Check to see if the method has a body or not (and if it should). */
		if( currentType instanceof ClassType ) {			
			if( isMeta && hasBlock )
				addError(node, Error.INVALID_STRUCTURE, "Method " + signature + " must not define a body in a meta file");
			
			if( !isMeta ) {					
				if( !hasBlock && !signature.isAbstract() && !signature.isNative() && !signature.isExternWithoutBlock() )
					addError(node, Error.INVALID_STRUCTURE, "Method " + signature + " must define a body");
				
				if( hasBlock && (signature.isAbstract() || signature.isNative() ) )
					addError(node, Error.INVALID_STRUCTURE, (signature.isAbstract() ? "Abstract" : "Native") + " method " + signature + " must not define a body");
				
				/* Check to see if the method's parameters and return types are the
				 * correct level of visibility, e.g., a public method shouldn't
				 * return a private inner class.
				 */
				checkParameterAndReturnVisibility(signature, (ClassType)currentType);
			}
		}
		else if( currentType instanceof InterfaceType ) {
			if( hasBlock )
				addError(node, Error.INVALID_STRUCTURE, "Interface method " + signature + " must not define a body");
		}
		
		// Add the method to the current type.
		currentType.addMethod(signature);
		// Since we're leaving the method, take it off the method stack.
		currentMethod.removeFirst();
	}
	
	
	@Override public Void visitResultTypes(ShadowParser.ResultTypesContext ctx)
	{
		visitChildren(ctx);
		
		SequenceType type = new SequenceType();		
		for( ShadowParser.ResultTypeContext child : ctx.resultType() )
			type.add(child);		
		ctx.setType(type);	
		
		return null;
	}
	
	@Override public Void visitCreateDeclarator(ShadowParser.CreateDeclaratorContext ctx)
	{ 
		visitChildren(ctx); 
		
		ShadowParser.CreateDeclarationContext parent = (ShadowParser.CreateDeclarationContext) ctx.getParent();
		MethodSignature signature = parent.getSignature();
		visitDeclarator( ctx, ctx.formalParameters(), signature );			
		
		if( (currentType instanceof SingletonType) && signature.getParameterTypes().size() > 0 )
				addError(ctx, Error.INVALID_SINGLETON_CREATE, "Singleton type " + currentType + " can only specify a default create");
		
		return null;
	}	
	
	@Override public Void visitMethodDeclarator(ShadowParser.MethodDeclaratorContext ctx)
	{
		visitChildren(ctx);
		
		ShadowParser.MethodDeclarationContext parent = (ShadowParser.MethodDeclarationContext) ctx.getParent();
		visitDeclarator( ctx, ctx.formalParameters(), parent.getSignature() );
		
		return null;		
	}	
	
	private void visitDeclarator( Context node, ShadowParser.FormalParametersContext parameters, MethodSignature signature ) {
		// Add parameters to the signature		
		for( ShadowParser.FormalParameterContext parameter : parameters.formalParameter() )				
			signature.addParameter(parameter.Identifier().getText(), parameter);
		
		if( signature.getModifiers().isSet() ) {				
			if( parameters.formalParameter().size() != 1 )
				addError(node, Error.INVALID_MODIFIER,
						"Methods marked with set must have exactly one parameter");
			else
				signature.getParameterTypes().get(0).getModifiers().addModifier(Modifiers.ASSIGNABLE);
		}			
		else if( signature.getModifiers().isGet() ) {
			if( parameters.formalParameter().size() != 0 )
				addError(node, Error.INVALID_MODIFIER,
						"Methods marked with get cannot have any parameters");
		} 
		
		if(!signature.isExtern() && signature.getSymbol().startsWith("_")) {
  			addError(node, Error.INVALID_METHODIDENTIFIER, Error.INVALID_METHODIDENTIFIER.getMessage());
  		}
		
		if(signature.isExtern() && signature.getSymbol().startsWith("$")) {
			if(signature.getParameterTypes().size() == 0) {
				addError(node, Error.INVALID_ARGUMENTS, "To extern a method from another class, the first parameter of this method should be the class which contains the method being externed.");
			} else if(!signature.getModifiers().isPrivate()) {
				addError(node, Error.INVALID_MODIFIER, "An externed method from another class should be private.");
			}
		}

		// Add return types
		if( node instanceof ShadowParser.MethodDeclaratorContext ) {
			ShadowParser.MethodDeclaratorContext declarator = (MethodDeclaratorContext) node;
			for( ShadowParser.ResultTypeContext result : declarator.resultTypes().resultType() )
				signature.addReturn(result);
			
			if( signature.getModifiers().isSet() ) {				
				if( signature.getReturnTypes().size() != 0 )
					addError(node, Error.INVALID_MODIFIER,
							"Methods marked with set cannot have return values");				
			}			
			else if( signature.getModifiers().isGet() ) {
				if( signature.getReturnTypes().size() != 1 )
					addError(node, Error.INVALID_MODIFIER,
							"Methods marked with get must have exactly one return value");
			}			
		}	
	}
	
	@Override public Void visitFormalParameter(ShadowParser.FormalParameterContext ctx)
	{ 
		visitChildren(ctx);
		
		Type type = ctx.type().getType();
		ctx.setType(type);
		if( type instanceof SingletonType )
			addError(ctx, Error.INVALID_TYPE, "Parameter cannot be defined with singleton type "
					+ type);
		
		return null;
	}
	
	@Override public Void visitFormalParameters(ShadowParser.FormalParametersContext ctx)
	{ 
		visitChildren(ctx);
		
		SequenceType type = new SequenceType();
		Set<String> names = new HashSet<String>();
		for( ShadowParser.FormalParameterContext parameter : ctx.formalParameter() ) {
			String name = parameter.Identifier().getText();
			if( names.contains( name ) )
				addError( parameter, Error.MULTIPLY_DEFINED_SYMBOL, "Symbol " + name +
						" already defined as a parameter name" );
			else
				names.add(name);
			
			if( parameter.getType() instanceof SingletonType )
				addError( parameter, Error.INVALID_PARAMETERS,
						"Cannot define method with singleton parameter" );				
			type.add(parameter);			
		}
		
		ctx.setType(type);
		return null;	
	}	
		
	@Override public Void visitCompilationUnit(ShadowParser.CompilationUnitContext ctx) 
	{ 
		currentPackage = packageTree;		
		return visitChildren(ctx); 
	}
	
	private void updateImports( Context node ) {
		/* Changing these items updates the imports inside the type. */
		List<Object> importedItems = node.getType().getImportedItems();
		for( int i = 0; i < importedItems.size(); i++ ) {
			Object thing = importedItems.get(i);
			String item = (String) thing;
			if( item.contains("@") ) { // Single class.			
				Type type = lookupType(node, item);				
				//shouldn't fail
				if( type == null )
					addError(node, Error.INVALID_IMPORT, "Undefined type " + item + " cannot be imported");
				else 
					importedItems.set(i, type);
			}
			else {	// Whole package.			
				Package p = packageTree.getChild(item);
				if( p == null )
					addError(node, Error.INVALID_IMPORT, "Undefined package " + item + " cannot be imported");
				else
					importedItems.set(i, p);					
			}			
		}
	}
	
	@Override public Void visitLiteral(ShadowParser.LiteralContext ctx) 
	{ 		
		ctx.setType(literalToType(ctx));
		return null; //no children
	}
	
	@Override public Void visitResultType(ShadowParser.ResultTypeContext ctx)
	{
		visitChildren(ctx);		
		ctx.setType(ctx.type().getType());
		ctx.addModifiers(ctx.modifiers().getModifiers());
		return null;
	}
		
	private void visitDeclaration( Context node, ShadowParser.IsListContext list) 
	{		
		declarationType = node.getType();
		currentPackage = declarationType.getPackage();
		updateImports( node );
		
		visitChildren(node);
		
		//after visiting children					
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
				for( ShadowParser.ClassOrInterfaceTypeContext child : list.classOrInterfaceType() ) {					
					Type type = child.getType();
					if( type instanceof InterfaceType )
						declarationType.addInterface((InterfaceType)type);
					else				
						addError(child, Error.INVALID_INTERFACE, kind + declarationType +
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
			
			if( list != null ) {
				boolean first = true;
				for( ShadowParser.ClassOrInterfaceTypeContext child : list.classOrInterfaceType()  ) {
					Type type = child.getType();
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
							addError(child, Error.INVALID_PARENT, kind + declarationType +
									" cannot be child of " + otherKind + type);														
						else if( type.getModifiers().isLocked() )
							addError(child, Error.INVALID_PARENT, kind + declarationType +
									" cannot be child of locked " + otherKind + type);
						else if( !first )
							addError(child, Error.INVALID_PARENT, kind + declarationType +
									" can only be child of " + otherKind + type + " if " + type + " is listed first in the is list");
						else
							classType.setExtendType((ClassType)type);
					}
					else if( type instanceof InterfaceType )
						classType.addInterface((InterfaceType)type);
					else				
						addError(child, Error.INVALID_TYPE, kind + classType +
								" cannot extend or implement type " + type, classType, type);
					
					first = false;
				}
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
			Context parent = (Context) node.getParent();
			parent.setType(declarationType);								
		}
		
		declarationType = declarationType.getOuter();
		
	}
	
	@Override public Void visitClassOrInterfaceDeclaration(ShadowParser.ClassOrInterfaceDeclarationContext ctx) 
	{ 	
		visitDeclaration(ctx, ctx.isList());	
		return null; 
	}
	
	@Override public Void visitEnumDeclaration(ShadowParser.EnumDeclarationContext ctx)
	{
		visitDeclaration(ctx, ctx.isList());	
		return null; 
	}
	
	@Override public Void visitCreateDeclaration(ShadowParser.CreateDeclarationContext ctx)
	{
		visitMethodPre( "create", ctx);		
		visitChildren(ctx);
		visitMethodPost(ctx);
		
		return null;
	}
	
	@Override public Void visitDestroyDeclaration(ShadowParser.DestroyDeclarationContext ctx)
	{
		visitMethodPre( "destroy", ctx);		
		visitChildren(ctx);
		visitMethodPost(ctx);
		
		return null;
	}
	
	@Override public Void visitMethodDeclaration(ShadowParser.MethodDeclarationContext ctx)
	{	
		if( currentType != null  ) {
			if( currentType.getModifiers().isImmutable() ) {
				ctx.getModifiers().addModifier(Modifiers.READONLY);
				ctx.methodDeclarator().getModifiers().addModifier(Modifiers.READONLY);
			}
			
			if( currentType.getModifiers().isLocked() || (currentType instanceof SingletonType) ) {
				ctx.getModifiers().addModifier(Modifiers.LOCKED);
				ctx.methodDeclarator().getModifiers().addModifier(Modifiers.LOCKED);
			}
		}
		
		visitMethodPre( ctx.methodDeclarator().generalIdentifier().getText(), ctx);
		visitChildren(ctx); 
		visitMethodPost(ctx);
		
		return null;
	}
	
	@Override public Void visitFieldDeclaration(ShadowParser.FieldDeclarationContext ctx)
	{ 
		visitChildren(ctx);
		
		// A field declaration has a type followed by an identifier (or a sequence of them).	
		Type type = ctx.type().getType();	
						
		ctx.setType(type);		
		ctx.getModifiers().addModifier(Modifiers.FIELD);
		
		// Constants must be public, private, or protected,
		// unlike regular fields which are implicitly private.
		if( ctx.getModifiers().isConstant() )
			checkModifiers( ctx, "constant" );
		
		if( currentType.getModifiers().isImmutable() )
			ctx.getModifiers().addModifier( Modifiers.IMMUTABLE );
		
		// All interface fields are implicitly public and constant.
		if( currentType instanceof InterfaceType ) {
			ctx.getModifiers().addModifier( Modifiers.CONSTANT ); 
			ctx.getModifiers().addModifier( Modifiers.PUBLIC );
		}
		
		if( type instanceof UninstantiatedType && ctx.getModifiers().isConstant() ) {
			UninstantiatedType uninstantiatedType = (UninstantiatedType) type;
			for( ModifiedType argument : uninstantiatedType.getTypeArguments() ) {
				if( argument.getType() instanceof TypeParameter ) {					
					addError( ctx, Error.INVALID_TYPE_PARAMETERS,
							"Field marked constant cannot have a type with a type parameter" );
					break;
				}				
			}
		}	
						
		/* Add all the identifiers. */
		for( ShadowParser.VariableDeclaratorContext declarator : ctx.variableDeclarator() ) {			
			declarator.setType(type);
			declarator.addModifiers(ctx.getModifiers());
			declarator.setDocumentation(ctx.getDocumentation());			
			
			String symbol = declarator.generalIdentifier().getText();
			
			/* Make sure we don't already have this symbol.
			 * Methods and fields can have the same name since they can be
			 * disambiguated by colon or dot. */			
			if( currentType.containsField( symbol ) )						
				addError( declarator, Error.MULTIPLY_DEFINED_SYMBOL, "Field name " + symbol +
						" already declared on line " +
						currentType.getField(symbol).getStart().getLine() );
			else if( currentType instanceof ClassType &&
					((ClassType)currentType).containsInnerClass( symbol ))
				addError(declarator, Error.MULTIPLY_DEFINED_SYMBOL, "Field name " + symbol +
						" already declared as inner class");
			else
				currentType.addField(symbol, declarator);
			
			if( type instanceof SingletonType ) {
				if( ctx.getModifiers().isGet() )
					addError(declarator, Error.INVALID_MODIFIER,
							"Modifier get cannot be applied to field " + symbol +
							" with singleton type");
				if( ctx.getModifiers().isImmutable() )
					addError(declarator, Error.INVALID_MODIFIER,
							"Modifier immutable cannot be applied to field " + symbol +
							" with singleton type");
				if( ctx.getModifiers().isNullable() )
					addError(declarator, Error.INVALID_MODIFIER,
							"Modifier nullable cannot be applied to field " + symbol +
							" with singleton type");
				if( ctx.getModifiers().isReadonly() )
					addError(declarator, Error.INVALID_MODIFIER,
							"Modifier readonly cannot be applied to field " + symbol +
							" with singleton type");
				if( ctx.getModifiers().isSet() )
					addError(declarator, Error.INVALID_MODIFIER,
							"Modifier set cannot be applied to field " + symbol +
							" with singleton type");
				if( ctx.getModifiers().isWeak() )
					addError(declarator, Error.INVALID_MODIFIER,
							"Modifier weak cannot be applied to field " + symbol +
							" with singleton type");				
			}			
		}
		
		return null;
	}
	
	@Override public Void visitTypeParameters(ShadowParser.TypeParametersContext ctx)
	{ 
		visitChildren(ctx);
		SequenceType sequence = new SequenceType();
		Set<String> parameterNames = new HashSet<String>();
		for( ShadowParser.TypeParameterContext typeParameter : ctx.typeParameter() ) {			
			sequence.add(typeParameter);			
			String name = typeParameter.Identifier().getText();
			if( parameterNames.contains(name) )
				addError(typeParameter, Error.MULTIPLY_DEFINED_SYMBOL, "Type parameter " +
						name + " has already been defined" );
			parameterNames.add(name);
		}
		ctx.setType(sequence);		
		
		return null;		
	}
	
	@Override public Void visitTypeParameter(ShadowParser.TypeParameterContext ctx)
	{ 
		/* Type parameters are created before visiting children so that bounds dependent
		 * on them can look up the right type. */
		String symbol = ctx.Identifier().getText();
		TypeParameter typeParameter = new TypeParameter(symbol, declarationType);
		Type type = declarationType; //add parameters to current class
				
		if( type instanceof SingletonType )			
			addError(ctx, Error.INVALID_TYPE_PARAMETERS, "Singleton type " + declarationType.getTypeName() + " cannot be parameterized");
		else if( type instanceof ExceptionType )			
			addError(ctx, Error.INVALID_TYPE_PARAMETERS, "Exception type " + declarationType.getTypeName() + " cannot be parameterized");
		
		ctx.setType(typeParameter);
		type.addTypeParameter(ctx);	
		
		visitChildren(ctx);
		
		if( ctx.isList() != null ) {							
			for( ShadowParser.ClassOrInterfaceTypeContext bound : ctx.isList().classOrInterfaceType() )								
				typeParameter.addBound(bound.getType());				
		}	
		
		return null;
	}
	
	@Override public Void visitClassOrInterfaceType(ShadowParser.ClassOrInterfaceTypeContext ctx)
	{ 
		visitChildren(ctx);
		

		
		//(unqualifiedName '@')? Identifier ( ':' Identifier )* typeArguments?
		// Type can be complex: package@Container:Stuff<T, List<String>, String, Thing<K>>
		// Note that all type arguments are on the last class
		String typeName = ctx.Identifier(0).getText();
		if( ctx.unqualifiedName() != null )
			typeName = ctx.unqualifiedName().getText() + "@" + typeName;
		Type type = lookupType(ctx, typeName);
		
		for( int i = 1; type != null && i < ctx.Identifier().size(); ++i ) {
			typeName = ctx.Identifier(i).getText();

			if( type instanceof ClassType ) 
				type = ((ClassType)type).getInnerClass(typeName);
			else
				type = null;
		}		

		if(type == null) {
			addError(ctx, Error.UNDEFINED_TYPE, "Type " + typeName +
					" not defined in current context");			
			type = Type.UNKNOWN;					
		}
		else {
			if( !isMeta && !classIsAccessible( type, declarationType  ) )		
				addError(ctx, Error.ILLEGAL_ACCESS, "Type " + type +
						" not accessible from current context");
			
			if( ctx.typeArguments() != null ) { // Contains type arguments.				
				SequenceType arguments = (SequenceType) ctx.typeArguments().getType();						
				if( type.isParameterized() ) {
					if( type instanceof ClassType )						
						type = new UninstantiatedClassType( (ClassType)type, arguments );
					else if( type instanceof InterfaceType )
						type = new UninstantiatedInterfaceType( (InterfaceType)type, arguments );
				}
				else {
					addError(ctx, Error.UNNECESSARY_TYPE_ARGUMENTS,
							"Type arguments supplied for non-parameterized type " + type);
					type = Type.UNKNOWN;
				}										
			}
			else if( type.isParameterized() ) { // Parameterized but no type arguments.
				addError(ctx, Error.MISSING_TYPE_ARGUMENTS,
						"Type arguments not supplied for parameterized type " + typeName);
				type = Type.UNKNOWN;
			}
		}
		
		// Set the type now that it has type parameters. 
		ctx.setType(type);
		
		return null;
	}
	
	@Override public Void visitTypeArguments(ShadowParser.TypeArgumentsContext ctx)
	{
		visitChildren(ctx);
		
		SequenceType sequence = new SequenceType();
		for( ShadowParser.TypeContext type : ctx.type() )
			sequence.add(type);		
		ctx.setType(sequence);
		
		return null;		
	}
	
	@Override public Void visitReferenceType(ShadowParser.ReferenceTypeContext ctx)
	{
		visitChildren(ctx);
		
		Context child = (Context)ctx.getChild(0);  //either primitive type or class type
		Type type = child.getType();		
		
		int dimensions = getDimensions( ctx );		
		if( dimensions != 0 )
			type = new ArrayType(type, dimensions, false);		
		ctx.setType(type);
		
		ctx.addModifiers(Modifiers.TYPE_NAME);		
		return null;
	}
	
	
	@Override public Void visitPrimitiveType(ShadowParser.PrimitiveTypeContext ctx)
	{ 
		ctx.setType(nameToPrimitiveType(ctx.getText()));
		return null; //no children 
	}
	
	@Override public Void visitFunctionType(ShadowParser.FunctionTypeContext ctx)
	{
		visitChildren(ctx);
		
		MethodType methodType = new MethodType();
		SequenceType parameterTypes = (SequenceType) ctx.resultTypes(0).getType();
		SequenceType returnTypes = (SequenceType) ctx.resultTypes(1).getType();
						
		for( ModifiedType parameter : parameterTypes  )
			methodType.addParameter(parameter);			
			
		for( ModifiedType type : returnTypes )
			methodType.addReturn(type);
		
		ctx.setType(methodType);
		
		return null;
	}
	
	@Override public Void visitType(ShadowParser.TypeContext ctx)
	{
		visitChildren(ctx);
		boolean isNullable = ctx.getModifiers().isNullable();
		
		Context child = (Context) ctx.getChild(0);
		Type type = child.getType();
		
		if( isNullable && type instanceof ArrayType ) {
			ArrayType arrayType = (ArrayType) type;
			type = arrayType.convertToNullable();
		}
		
		ctx.setType(type);
		
		return null;
	}
	
	@Override public Void visitBlock(ShadowParser.BlockContext ctx) { /* no children */ return null; }
	
	@Override public Void visitCreateBlock(ShadowParser.CreateBlockContext ctx) { /* no children */ return null; }
	
	// Skip all field initializations.
	@Override public Void visitConditionalExpression(ShadowParser.ConditionalExpressionContext ctx) { /* no children */ return null; }	
}
