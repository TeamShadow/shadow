package shadow.typecheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTClassOrInterfaceBody;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCreateDeclaration;
import shadow.parser.javacc.ASTDestroyDeclaration;
import shadow.parser.javacc.ASTExtendsList;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFunctionType;
import shadow.parser.javacc.ASTImplementsList;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTPrimitiveType;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTResultTypes;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.ASTTypeArguments;
import shadow.parser.javacc.ASTTypeBound;
import shadow.parser.javacc.ASTTypeParameter;
import shadow.parser.javacc.ASTTypeParameters;
import shadow.parser.javacc.ASTUnqualifiedName;
import shadow.parser.javacc.ASTViewDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ErrorType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.UninstantiatedClassType;
import shadow.typecheck.type.UninstantiatedInterfaceType;

public class TypeUpdater extends BaseChecker
{	
	private ClassInterfaceBaseType declarationType = null;	
	
	public TypeUpdater(boolean debug,
			HashMap<Package, HashMap<String, ClassInterfaceBaseType>> typeTable,
			List<String> importList, Package packageTree) {
		super(debug, typeTable, importList, packageTree);
	}
	
	public void updateTypes(Map<String, Node> files) throws ShadowException
	{		
		for(Node declarationNode : files.values() )
		{
			ASTWalker walker = new ASTWalker( this );		
			walker.walk(declarationNode);			
		}		
	}
	
	
	//Visitors below this point
	
	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit ) //leaving a type
			currentType = currentType.getOuter();
		else //entering a type
		{					
			currentType = (ClassInterfaceBaseType)node.jjtGetParent().getType();
			currentPackage = currentType.getPackage();				
		}
			
		return WalkType.POST_CHILDREN;
	}
	
	
	//declaration type differs from current type before the body is entered (i.e. for type parameters, extends lists, and implements lists)
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException
	{			
		if( secondVisit )
		{
			if( declarationType.getOuter() != null )			
				declarationType.addTypeParameterDependency(declarationType.getOuter());
			
			declarationType = declarationType.getOuter();		
		}
		else		
			declarationType = (ClassInterfaceBaseType)node.getType();
			
		return WalkType.POST_CHILDREN;
	}	
	
	//next five skip methods, fields, and views
	@Override
	public Object visit(ASTCreateDeclaration node, Boolean secondVisit) throws ShadowException
	{		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTDestroyDeclaration node, Boolean secondVisit) throws ShadowException
	{		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException
	{		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException
	{		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTViewDeclaration node, Boolean secondVisit) throws ShadowException
	{		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTExtendsList node, Boolean secondVisit) throws ShadowException { 
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTImplementsList node, Boolean secondVisit) throws ShadowException { 
		return WalkType.NO_CHILDREN;
	}
	
	
	
	//type parameters will only be visited here on type declarations 
	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit) throws ShadowException
	{		
		if( !secondVisit )
		{	
			if( declarationType instanceof SingletonType )
			{
				addError(node, Error.INVL_TYP, "Cannot declare singleton type " + declarationType + " with type parameters");
				return WalkType.NO_CHILDREN;
			}
			else if( declarationType instanceof ExceptionType )
			{
				addError(node, Error.INVL_TYP, "Cannot declare exception type " + declarationType + " with type parameters");
				return WalkType.NO_CHILDREN;
			}
			else if( declarationType instanceof ErrorType )
			{
				addError(node, Error.INVL_TYP, "Cannot declare error type " + declarationType + " with type parameters");
				return WalkType.NO_CHILDREN;
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{	
			TypeParameter typeParameter = new TypeParameter(node.getImage());
			
			if( node.jjtGetNumChildren() > 0 )
			{
				ASTTypeBound bound = (ASTTypeBound)(node.jjtGetChild(0));				
				for( int i = 0; i < bound.jjtGetNumChildren(); i++ )								
					typeParameter.addBound((ClassInterfaceBaseType)(bound.jjtGetChild(i).getType()));				
			}			
			
			if( declarationType.isParameterized() )
				for( ModifiedType existing : declarationType.getTypeParameters() )
					if( existing.getType().getTypeName().equals( typeParameter.getTypeName() ) )
						addError( node, Error.MULT_SYM, "Multiply defined type parameter " + typeParameter.getTypeName() );
			
			node.setType(typeParameter);
			declarationType.addTypeParameter(node);
		}
		
		return WalkType.POST_CHILDREN;
	}
	
		
	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{	
			if( node.getType() == null )
			{					
				Node child = node.jjtGetChild(0); 
				String typeName = child.getImage();
				int start = 1;
				
				if( child instanceof ASTUnqualifiedName )
				{					
					child = node.jjtGetChild(start);
					typeName += "@" + child.getImage();
					start++;					
				}
				
				ClassInterfaceBaseType type = lookupType(typeName);
				ClassInterfaceBaseType instantiatedType;
				
				if(type == null)
				{
					addError(node, Error.UNDEF_TYP, typeName);			
					node.setType(Type.UNKNOWN);					
				}
				else
				{						
					if( child.jjtGetNumChildren() == 1 ) //contains arguments
					{
						SequenceType arguments = (SequenceType) child.jjtGetChild(0).getType();
						if( type instanceof ClassType )						
							instantiatedType = new UninstantiatedClassType((ClassType)type, arguments);							
						else
							instantiatedType = new UninstantiatedInterfaceType((InterfaceType)type, arguments);
						//the only dependencies that matter for type parameters are classes which themselves have
						currentType.addTypeParameterDependency(type);						
					}
					else if( type.isParameterized() ) //parameterized but no parameters!	
					{
						addError(node, Error.INVL_TYP, child.getImage() + " requires type parameters but none were supplied");
						instantiatedType = Type.UNKNOWN;
					}
					else
						instantiatedType = type;
					
					
					//Container<T, List<String>, String, Thing<K>>:Stuff<U>
					for( int i = start; i < node.jjtGetNumChildren(); i++ )
					{
						child = node.jjtGetChild(i);
						type = type.getInnerClass(child.getImage());
						
						if( type == null )
						{
							addError(node, Error.UNDEF_TYP, child.getImage());
							node.setType(Type.UNKNOWN);
							return WalkType.POST_CHILDREN;
						}
						
						if( child.jjtGetNumChildren() == 1 ) //contains arguments
						{
							SequenceType arguments = (SequenceType) child.jjtGetChild(0).getType();
							if( type instanceof ClassType )						
								instantiatedType = new UninstantiatedClassType((ClassType)type, arguments, instantiatedType);							
							else
								instantiatedType = new UninstantiatedInterfaceType((InterfaceType)type, arguments, instantiatedType);
							
							//the only dependencies that matter for type parameters are classes which themselves have
							currentType.addTypeParameterDependency(type);
						}
						else if( type.isParameterized() ) //parameterized but no parameters!
						{
							addError(node, Error.INVL_TYP, child.getImage() + " requires type parameters but none were supplied");
							instantiatedType = Type.UNKNOWN;
						}
						else
							instantiatedType = type;
					}
					
					//set the type now that it has type parameters 
					node.setType(instantiatedType);	
				}
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTTypeArguments node, Boolean secondVisit) throws ShadowException
	{	
		if( secondVisit )
		{
			SequenceType arguments = new SequenceType();
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				arguments.add(node.jjtGetChild(i));	
			
			node.setType(arguments);	
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException
	{	
		return pushUpType(node, secondVisit);
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
	
	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit) throws ShadowException {		
		node.setType(nameToPrimitiveType(node.getImage()));		
		return WalkType.NO_CHILDREN;			
	}
	
	public Object visit(ASTFunctionType node, Boolean secondVisit) throws ShadowException
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		MethodType methodType = new MethodType(); // it has no name
		
		// add all the parameters to this method
		int i;
		for(i=0; i < node.jjtGetNumChildren(); ++i) {
			Node child = node.jjtGetChild(i);
			
			// check to see if we've moved on to the result types
			if( child instanceof ASTResultTypes )
				break;
			
			if( child.getType() == null ) 
			{
				addError(child, Error.UNDEF_TYP, child.getImage());
				node.setType(Type.UNKNOWN);
				return WalkType.POST_CHILDREN;
			}
				
			methodType.addParameter(child);	// add the type as the parameter
		}
		
		// check to see if we have result types
		if(i < node.jjtGetNumChildren())
		{
			Node resultsNode = node.jjtGetChild(i);
			
			for(int j = 0; j < resultsNode.jjtGetNumChildren(); ++j)
			{
				Node child = resultsNode.jjtGetChild(j);
				Type type = child.getType();
				
				if(type == null)
				{
					addError(child.jjtGetChild(0), Error.UNDEF_TYP, child.jjtGetChild(0).getImage());
					node.setType(Type.UNKNOWN);
					return WalkType.POST_CHILDREN;
				}
					
				methodType.addReturn(child);
			}
		}
		
		node.setType(methodType);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException
	{
		return WalkType.NO_CHILDREN; //skip all blocks
	}
}
