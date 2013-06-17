package shadow.typecheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCreateBlock;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTResultType;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InstantiationException;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.UninstantiatedClassType;
import shadow.typecheck.type.UninstantiatedInterfaceType;
import shadow.typecheck.type.UninstantiatedType;

public class TypeInstantiater extends BaseChecker {

	public TypeInstantiater(boolean debug,
			HashMap<Package, HashMap<String, Type>> hashMap,
			List<String> importList, Package packageTree) {
		super(debug, hashMap, importList, packageTree);		
	}
	
	public void instantiateTypes(Map<Type,Node> nodeTable) throws ShadowException
	{	
		DirectedGraph<Node> graph = new DirectedGraph<Node>();
		Map<Type, Node> uninstantiatedNodes = new HashMap<Type,Node>();		
		
		for(Node declarationNode : nodeTable.values() )
		{
			graph.addNode( declarationNode );
			Type typeWithoutArguments = declarationNode.getType().getTypeWithoutTypeArguments(); 
			uninstantiatedNodes.put(typeWithoutArguments, declarationNode);
		}
		
		for(Node declarationNode : nodeTable.values() )
			for( Type dependency : declarationNode.getType().getTypeParameterDependencies()  )
			{
				Node dependencyNode = uninstantiatedNodes.get(dependency.getTypeWithoutTypeArguments());
				if( dependencyNode == null )
					addError(declarationNode, "Dependency not found");
				else
					graph.addEdge(dependencyNode, declarationNode);				
			}
		
		List<Node> nodeList;
		
		try
		{					
			nodeList = graph.topologicalSort();
			//update type parameters
			for(Node declarationNode : nodeList )
			{	
				Type type = declarationNode.getType();				
				updateTypeParameters( type, declarationNode );
			}
			
			//update fields and methods			
			for(Node declarationNode : nodeList )
			{	
				Type type = declarationNode.getType();				
					
				//update fields					
				Map<String,Node> fields = type.getFields();					
				for( Node node : fields.values() )
				{
					 Type nodeType = node.getType();
					 if( nodeType instanceof UninstantiatedType )
					 {
						 try 
						 {
							node.setType( ((UninstantiatedType)nodeType).instantiate() );
						 }
						 catch (InstantiationException e)
						 {							
							 addError(declarationNode, Error.INVL_TYP, e.getMessage() );
						 }							 
					 }
				}
				
				//update methods					
				Map<String, List<MethodSignature>> methodTable = type.getMethodMap();					
				for( String name : methodTable.keySet() )
				{	
					for( MethodSignature signature: methodTable.get(name) )
					{
						MethodType methodType = signature.getMethodType();
						updateTypeParameters(methodType, signature.getNode());
						
						for( String parameter : methodType.getParameterNames() )
						{
							ModifiedType parameterType = methodType.getParameterType(parameter);
							if( parameterType.getType() instanceof UninstantiatedType )
							{
								try
								{
									parameterType.setType( ((UninstantiatedType)parameterType.getType()).instantiate() );
								} 
								catch (InstantiationException e) 
								{									
									addError(signature.getNode(), Error.INVL_TYP, e.getMessage() );
								}
							}
						}
						
						for( ModifiedType returnType : methodType.getReturnTypes() )
						{
							if( returnType.getType() instanceof UninstantiatedType )
							{
								try
								{
									returnType.setType( ((UninstantiatedType)returnType.getType()).instantiate() );
								} 
								catch (InstantiationException e) 
								{									
									addError(signature.getNode(), Error.INVL_TYP, e.getMessage() );
								}
							}
						}						
					}
				}
			}
			
		}
		catch( CycleFoundException e )
		{
			Type type = (Type) e.getCycleCause();			
			addError(nodeTable.get(type), Error.INVL_TYP, "Type " + type + " contains a circular type parameter definition");
		}
				
			
		//now do dependencies based on extends and implements
		graph = new DirectedGraph<Node>();
		
		for(Node declarationNode : nodeTable.values() )
			graph.addNode( declarationNode );
		
		for(Node declarationNode : nodeTable.values() )
		{
			Type type = declarationNode.getType();
			
			if( type instanceof ClassType )
			{
				ClassType classType = (ClassType) type;
				
				if( classType.getExtendType() != null )
				{
					Node dependencyNode = uninstantiatedNodes.get(classType.getExtendType().getTypeWithoutTypeArguments());
					if( dependencyNode == null )
						addError(declarationNode, "Dependency not found");
					else
						graph.addEdge(dependencyNode, declarationNode);
				}
			}
				
			for( Type dependency : type.getInterfaces() )
			{
				Node dependencyNode = uninstantiatedNodes.get(dependency.getTypeWithoutTypeArguments());
				if( dependencyNode == null )
					addError(declarationNode, "Dependency not found");
				else
					graph.addEdge(dependencyNode, declarationNode);
			}
		}
		
		Type hashSet = null;
		Type arrayList = null;
		
		try
		{
			nodeList = graph.topologicalSort();
			
			for(Node declarationNode : nodeList )
			{	
				Type type = declarationNode.getType();
				if( type instanceof ClassType )
				{
					ClassType classType = (ClassType) type;					
					ClassType parent = classType.getExtendType();
					if( classType.getTypeName().endsWith("HashSet") )
						hashSet = classType;
					else if( classType.getTypeName().endsWith("ArrayList") )
						arrayList = classType;
					
					//update parent
					if( parent != null && parent instanceof UninstantiatedClassType )
					{
						try 
						{
							parent = ((UninstantiatedClassType)parent).instantiate();
							classType.setExtendType(parent);
						} 
						catch (InstantiationException e) 
						{
							addError(declarationNode, Error.INVL_TYP, e.getMessage() );
						}
					}
					
					//update interfaces
					ArrayList<InterfaceType> interfaces = classType.getInterfaces() ;					
					for( int i = 0; i < interfaces.size(); i++ )
					{ 
						InterfaceType interfaceType = interfaces.get(i);
						if( interfaceType instanceof UninstantiatedInterfaceType  )
						{
							try 
							{
								interfaceType =  ((UninstantiatedInterfaceType)interfaceType).instantiate();
								interfaces.set(i, interfaceType);
							} 
							catch (InstantiationException e) 
							{
								addError(declarationNode, Error.INVL_TYP, e.getMessage() );
							}						
						}
					}
				}
				else if( type instanceof InterfaceType )
				{	
					//update interfaces
					ArrayList<InterfaceType> interfaces = ((InterfaceType)type).getInterfaces();;					
					for( int i = 0; i < interfaces.size(); i++ )
					{ 
						InterfaceType interfaceType = interfaces.get(i);
						if( interfaceType instanceof UninstantiatedInterfaceType  )
						{
							try 
							{
								interfaceType =  ((UninstantiatedInterfaceType)interfaceType).instantiate();
								interfaces.set(i, interfaceType);
							} 
							catch (InstantiationException e) 
							{
								addError(declarationNode, Error.INVL_TYP, e.getMessage() );
							}						
						}
					}
				}			
			}
		}
		catch( CycleFoundException e )
		{
			Type type = (Type) e.getCycleCause();			
			addError(nodeTable.get(type), Error.INVL_TYP, "Type " + type + " contains a circular extends or implements definition");
		}
			
			/*
			ASTWalker walker = new ASTWalker(this);			
			
			//no need to walk?  seems like stuff is already updated
			//walk over all the files (updates nodes in case they need the right values)
			for( Node node : nodeTable.values() )								
				walker.walk(node);
			*/
		
		
		
		for( Node declarationNode : nodeTable.values() )	
		{			
			if( declarationNode.getType() instanceof ClassType )
			{
				
				ClassType classType = (ClassType)declarationNode.getType();
				ClassType parent = classType.getExtendType();			
		
				/* Check overridden methods to make sure:
				 * 1. All overrides match exactly  (if it matches everything but return type.... trouble!)
				 * 2. No final methods have been overridden
				 * 3. Immutable methods cannot be overridden by mutable methods
				 * 4. No overridden methods have been narrowed in access 
				 */			
			
				if( parent != null)
				{
					for( List<MethodSignature> signatures : classType.getMethodMap().values() )					
						for( MethodSignature signature : signatures )
						{
							if( parent.recursivelyContainsIndistinguishableMethod(signature) && !signature.isCreate() )
							{
								MethodSignature parentSignature = parent.recursivelyGetIndistinguishableMethod(signature);
								Node parentNode = parentSignature.getNode();
								Node node = signature.getNode();
								Modifiers parentModifiers;
								Modifiers modifiers;
								
								if( parentNode == null )
									parentModifiers = new Modifiers();
								else
									parentModifiers = parentNode.getModifiers();
								
								if( node == null )
									modifiers = new Modifiers();
								else
									modifiers = node.getModifiers();									
								
								if( !parentSignature.getReturnTypes().canAccept(signature.getReturnTypes()) )
									addError( parentNode, "Overriding method " + signature + " differs only by return type from " + parentSignature );
								//else if( parentModifiers.isFinal() )
								//	addError( parentNode, "Method " + signature + " cannot override final method" );
								//else if( parentModifiers.isImmutable() )
								//	addError( parentNode, "Method " + signature + " cannot override immutable method" );
								else if( !modifiers.isReadonly() && parentModifiers.isReadonly()  )
									addError( parentNode, "Non-readonly method " + signature + " cannot override readonly method" );
								else if( parentModifiers.isPublic() && (modifiers.isPrivate() || modifiers.isProtected()) )
									addError( parentNode, "Overriding method " + signature + " cannot reduce visibility of public method " + parentSignature );
								else if( parentModifiers.isProtected() && modifiers.isPrivate()  )
									addError( parentNode, "Overriding method " + signature + " cannot reduce visibility of protected method " + parentSignature );									
							}
						}
				}
				
				//check to see if all interfaces are satisfied				
				for( InterfaceType interfaceType : classType.getInterfaces() )
				{	
					ArrayList<String> reasons = new ArrayList<String>();
					
					if( !classType.satisfiesInterface(interfaceType, reasons) )
					{
						String message = "Type " + classType + " does not implement interface " + interfaceType;
						for( String reason : reasons )
							message += "\n\t" + reason;
						addError(Error.INVL_TYP, message );
					}
				}
			}
		}
	}
	
	private void updateTypeParameters(Type type, Node declarationNode)
	{
		if( type.isParameterized() )
		{	
			for( ModifiedType modifiedType : type.getTypeParameters() )
			{						
				TypeParameter typeParameter = (TypeParameter) modifiedType.getType();
				Set<Type> bounds = typeParameter.getBounds();						
				
				if( !bounds.isEmpty() )
				{	
					Set<Type> updatedBounds = new HashSet<Type>();
					
					for( Type boundType : bounds )
					{	
						if( boundType instanceof UninstantiatedType )
						{
							try
							{
								boundType = ((UninstantiatedType)boundType).instantiate();
							}
							catch( InstantiationException e )
							{
								addError(declarationNode, Error.INVL_TYP, e.getMessage() );
							}								
						}
						
						updatedBounds.add(boundType);
					}
					
					typeParameter.setBounds(updatedBounds);
				}
			}
		}
	}
	
	
	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{	
		if( node.getType() != null && node.getType() instanceof UninstantiatedType )
		{	
			
			UninstantiatedType type = (UninstantiatedType) node.getType();
			try 
			{
				node.setType(type.instantiate());
			} 
			catch (InstantiationException e) 
			{
				addError(node, Error.INVL_TYP, e.getMessage() );
			}
		}		
		
		return WalkType.NO_CHILDREN;
	}
	
	
	public Object visit(ASTReferenceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			Node child = node.jjtGetChild(0);
			Type type = child.getType();
			node.setModifiers(new Modifiers(child.getModifiers()));
			
			List<Integer> dimensions = node.getArrayDimensions();
			
			if( dimensions.size() == 0 )
				node.setType(type);
			else
				node.setType(new ArrayType(type, dimensions));
		}
	
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { 
		return pushUpType(node, secondVisit); 
	}
	
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )			
		{
			Type type = node.jjtGetChild(1).getType(); //child 0 is always Modifiers 
			node.setType(type);
			
			if( node.getModifiers().isNullable() && type.isPrimitive() )
				addError(node, Error.TYPE_MIS, "Cannot mark primitive type " + type + " as nullable");				
		}
		
		return WalkType.POST_CHILDREN;	
	}

	
	
		//next five skip methods, fields, views, and blocks
		//can't!
	
	
		@Override
		public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException
		{
			return WalkType.NO_CHILDREN; //skip all blocks
		}
		
		@Override
		public Object visit(ASTCreateBlock node, Boolean secondVisit) throws ShadowException
		{
			return WalkType.NO_CHILDREN; //skip all blocks
		}
}