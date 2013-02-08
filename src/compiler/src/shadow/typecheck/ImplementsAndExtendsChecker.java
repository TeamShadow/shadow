package shadow.typecheck;

import java.util.HashMap;
import java.util.HashSet;
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
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public class ImplementsAndExtendsChecker extends BaseChecker {

	private ClassInterfaceBaseType declarationType = null;
	
	public ImplementsAndExtendsChecker(boolean debug,
			HashMap<Package, HashMap<String, ClassInterfaceBaseType>> hashMap,
			List<String> importList, Package packageTree) {
		super(debug, hashMap, importList, packageTree);
		// TODO Auto-generated constructor stub
	}
	
	public void checkImplementsAndExtends(Map<String, Node> files) throws ShadowException
	{
		ASTWalker walker = new ASTWalker(this);
		
		for(Node node : files.values() )
			walker.walk(node);
		
		for(Node declarationNode : files.values() )
		{			
			if( declarationNode.getType() instanceof ClassType )
			{
				
				ClassType classType = (ClassType) declarationNode.getType();
				
				//check for circular class hierarchy				
				ClassType parent = classType.getExtendType();
				HashSet<Type> hierarchy = new HashSet<Type>();
				hierarchy.add(classType.getTypeWithoutTypeArguments());
				
				boolean circular = false;
				
				while( parent != null && !circular )
				{
					if( hierarchy.contains(parent.getTypeWithoutTypeArguments()) )	
					{
						addError(Error.INVL_TYP, "Circular type hierarchy for class " + classType );
						circular = true;
					}
					else
						hierarchy.add(parent.getTypeWithoutTypeArguments());
					parent = parent.getExtendType();
				}
				
				//check for circular interface issues
				for( InterfaceType interfaceType : classType.getInterfaces() )
				{
					if( interfaceType.isCircular() )
					{
						addError(Error.INVL_TYP, "Interface " + interfaceType + " has a circular extends hierarchy" );
						circular = true;
					}
				}				
			}			
		}
	}
	
	@Override
	public ClassInterfaceBaseType lookupType( String name ) //only addition to base checker is resolving type parameters
	{		
		if( declarationType != null &&  currentType != declarationType ) //in declaration header, check type parameters of current class declaration
		{
			if( declarationType.isParameterized() )
			{
				for( ModifiedType modifiedParameter : declarationType.getTypeParameters() )
				{
					
					Type parameter = modifiedParameter.getType();
					
					if( parameter instanceof TypeParameter )
					{
						TypeParameter typeParameter = (TypeParameter) parameter;
						if( typeParameter.getTypeName().equals(name) )
							return typeParameter;
					}					
				}				
			}
		}		
		
		return super.lookupType(name);
	}
	
	//visitors
	
	//Important!  Set the current type on entering the body, not the declaration, otherwise extends and imports are improperly checked with the wrong outer class
		public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException {		
			if( secondVisit )
				currentType = currentType.getOuter();		
			else
			{
				currentType = declarationType; //get type from declaration
				currentPackage = currentType.getPackage();	
			}
				
			return WalkType.POST_CHILDREN;
		}
		
		//But we still need to know what type we're in to sort out type parameters in the extends list
		//declarationType will differ from current type only before the body (extends list, implements list)
		//if no extends added, fix those too	
		@Override
		public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException
		{			
			if( secondVisit )
			{
				if( declarationType instanceof ClassType )
				{
					ClassType classType = (ClassType) declarationType;
					
					if( classType.getExtendType() == null )
					{					
						if( declarationType instanceof EnumType )
							classType.setExtendType(Type.ENUM);
						else if( declarationType instanceof ErrorType )
						{
							if( declarationType == Type.ERROR )
								classType.setExtendType(Type.EXCEPTION);
							else
								classType.setExtendType(Type.ERROR);
						}
						else if( declarationType instanceof ExceptionType )
						{
							if( declarationType == Type.EXCEPTION )
								classType.setExtendType(Type.OBJECT);
							else
								classType.setExtendType(Type.EXCEPTION);
						}
						else if( declarationType instanceof ArrayType )													
							classType.setExtendType(Type.ARRAY);		
						else if( classType != Type.OBJECT )
							classType.setExtendType(Type.OBJECT);
					}
					else
					{
						if( declarationType == Type.ERROR )
							classType.setExtendType(Type.EXCEPTION);
						else if ( declarationType == Type.EXCEPTION )
							classType.setExtendType(Type.OBJECT);
					}
					
				}	
				
				declarationType = declarationType.getOuter();
			}
			else
				declarationType = (ClassInterfaceBaseType)node.getType();
				
			return WalkType.POST_CHILDREN;
		}
		
		@Override
		public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException {
			return deferredTypeResolution(node, secondVisit);	
		}
		
		
		//add extends list
		public Object visit(ASTExtendsList node, Boolean secondVisit) throws ShadowException
		{
			if(secondVisit)
			{
				if( declarationType instanceof ClassType ) //includes error, exception, and enum (for now)
				{		
					ClassType classType = (ClassType)declarationType;			
					Node child = node.jjtGetChild(0); //only one thing in extends lists for classes
					Type extendType = child.getType();
					if( declarationType.getClass() == ClassType.class )
					{						
						if( extendType.getClass() == ClassType.class )
							classType.setExtendType((ClassType) extendType);
						else
							addError(child.jjtGetChild(0), Error.INVL_TYP, "Cannot create class type " + declarationType + " by extending non-class type " + extendType);
					}
					else if( declarationType.getClass() == ExceptionType.class )
					{
						if( extendType.getClass() == ExceptionType.class )
							classType.setExtendType((ClassType) extendType);
						else
							addError(child.jjtGetChild(0), Error.INVL_TYP, "Cannot create exception type " + declarationType + " by extending non-exception type " + extendType);
					}
				}
				else if( declarationType instanceof InterfaceType ) 
				{
					InterfaceType interfaceType = (InterfaceType)declarationType;
					for( int i = 0; i < node.jjtGetNumChildren(); i++ )
					{					
						Type type = node.jjtGetChild(i).getType();
						if( type instanceof InterfaceType )
							interfaceType.addExtendType((InterfaceType)type);
						else				
							addError( node, Error.INVL_TYP, type + "is not an interface type");
					}					
				}
			}
			
			return WalkType.POST_CHILDREN;
		}	
		
		//add implements list
		public Object visit(ASTImplementsList node, Boolean secondVisit) throws ShadowException
		{
			if(secondVisit)
			{
				ClassType classType = (ClassType)declarationType;
				for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				{
					Type type = node.jjtGetChild(i).getType();
					if( type instanceof InterfaceType )
						classType.addInterface((InterfaceType)type);
					else				
						addError( node, Error.INVL_TYP, type + "is not an interface type");
				}				
			}
			
			return WalkType.POST_CHILDREN;
			
		}
		
		public Object visit(ASTTypeArguments node, Boolean secondVisit) throws ShadowException
		{
			if( secondVisit )
			{
				SequenceType sequenceType = new SequenceType();
				for( int i = 0; i < node.jjtGetNumChildren(); i++ )
					sequenceType.add(node.jjtGetChild(i));
				
				node.setType(sequenceType);			
			}
			
			return WalkType.POST_CHILDREN;
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
		
		public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { 
			return pushUpType(node, secondVisit); 
		}
		
		//next five skip methods, fields, views, and blocks
		@Override
		public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException
		{
			return WalkType.NO_CHILDREN; //skip all blocks
		}
	
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
}
