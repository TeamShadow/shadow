package shadow.typecheck;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.Configuration;
import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTClassOrInterfaceBody;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPrimaryPrefix;
import shadow.parser.javacc.ASTPrimitiveType;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTTypeArguments;
import shadow.parser.javacc.ASTTypeDeclaration;
import shadow.parser.javacc.ASTTypeParameters;
import shadow.parser.javacc.ASTUnqualifiedName;
import shadow.parser.javacc.ASTViewDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.ShadowParser.TypeKind;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.Package.PackageException;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ErrorType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.ViewType;

public class TypeCollector extends BaseChecker
{	
	private Map<Type,Node> nodeTable = new HashMap<Type,Node>(); //for errors and also resolving type parameters	
	private String currentName = "";
	private Map<String, Node> files = new HashMap<String, Node>();	
	private TypeChecker typeChecker;
	
	protected LinkedList<Object> importedItems = new LinkedList<Object>();	
	
	public TypeCollector(boolean debug,HashMap< Package, HashMap<String, Type>> typeTable, ArrayList<String> importList, Package p, TypeChecker typeChecker )
	{		
		super(debug, typeTable, importList, p );		
		this.typeChecker = typeChecker;	
	}
	
	public Map<Type,Node> getNodeTable()
	{
		return nodeTable;
	}				
		
	public Node collectTypes(File input) throws ParseException, ShadowException, TypeCheckException, IOException
	{			
		//Create walker
		ASTWalker walker = new ASTWalker( this );
		Node resultNode = null;		
		List<String> fileList = new ArrayList<String>();
		
		//add file to be checked to list
		fileList.add(stripExtension(input.getCanonicalPath()));
		
		//add standard imports		
		File standardDirectory = new File( Configuration.getInstance().getSystemImport(), "shadow" + File.separator + "standard" );
		File[] standardImports = standardDirectory.listFiles( new FilenameFilter()
				{
					public boolean accept(File dir, String name)
					{
						return name.endsWith(".shadow");
					}
				}
		);
				
		for( File file :  standardImports )			
			fileList.add(stripExtension(file.getCanonicalPath()));
						
		for(int i = 0; i < fileList.size(); i++ )
		{			
			String canonical = fileList.get(i);
								
			if( !files.containsKey(canonical) ) //don't double add
			{
				
				File canonicalFile = new File(canonical + ".shadow");
				if( !canonicalFile.equals(input.getCanonicalFile()) ) //always read the shadow file for the input file
				{
					if( canonicalFile.exists() )  
					{											
						File meta = new File( canonical + ".meta" );
						if( meta.exists() && meta.lastModified() >= canonicalFile.lastModified() ) //check for more recent .meta file
							canonicalFile = meta;
					}
					else
					{
						canonicalFile  = new File(canonical + ".meta");
					}
				}
				
				ShadowParser parser = new ShadowParser(new FileInputStream(canonicalFile));
				typeChecker.setCurrentFile(canonicalFile);
			    Node node = parser.CompilationUnit();
			    
			    //this node is needed for full type checking and compilation
			    if( i == 0 )			    	
			    	resultNode = node;
			    
			    HashMap<Package, HashMap<String, Type>> otherTypes = new HashMap<Package, HashMap<String, Type>> ();			    
				TypeCollector collector = new TypeCollector(debug, otherTypes, new ArrayList<String>(), new Package(otherTypes), typeChecker);
				walker = new ASTWalker( collector );		
				walker.walk(node);				
		
				files.put(canonical, node);				
 
				
				//copy other types into our package tree				
				for( Package p : otherTypes.keySet() )
				{
					//if package already exists, it won't be recreated
					Package newPackage = packageTree.addQualifiedPackage(p.getQualifiedName(), typeTable);
					try
					{	
						newPackage.addTypes( otherTypes.get(p) );
					}
					catch(PackageException e)
					{
						addError( node, Error.INVALID_PACKAGE, e.getMessage() );				
					}
				}
				
				//copy any errors into our error list
				if( collector.getErrorCount() > 0 )
					errorList.addAll(collector.errorList);
				
				for( String _import : collector.getImportList() )
				{
					if( !fileList.contains(_import) )
						fileList.add(_import);					
				}
				
				//Add files in directory after imports (order matters in case of duplicates)
				File[] directoryFiles = canonicalFile.getParentFile().listFiles( new FilenameFilter()
						{
							public boolean accept(File dir, String name)
							{
								return name.endsWith(".shadow");
							}
						}
				);		

				for( File file :  directoryFiles )
					fileList.add(stripExtension(file.getCanonicalPath()));	
				
				//copy tables from other file into our central table
				Map<Type,Node> otherNodeTable = collector.nodeTable;
				for( Type type : otherNodeTable.keySet() )
					if( !nodeTable.containsKey(type) )
						nodeTable.put(type, otherNodeTable.get(type));				
			}
		}		
		
		if( errorList.size() > 0 )
		{
			printErrors();
			throw errorList.get(0);
		}		
		
		//return the node corresponding to the file being compiled
		return resultNode;
	}


	public Map<String, Node> getFiles()
	{
		return files;
	}
	
	private void createType( SimpleNode node, Modifiers modifiers, TypeKind kind ) throws ShadowException
	{		 
		String typeName;
		
		if( node.jjtGetNumChildren() > 0 && (node.jjtGetChild(0) instanceof ASTUnqualifiedName) )
		{
			
			if( currentType == null )
			{
				String name = node.jjtGetChild(0).getImage();									
				currentPackage = packageTree.addQualifiedPackage(name, typeTable);
			}
			else
				addError( node, Error.INVALID_PACKAGE, "Package can only be defined by outermost classes" );			
		}
		
		String image = node.getImage();		
		if( currentPackage.getQualifiedName().equals("shadow.standard"))
		{
			if( image.equals("Boolean") ||
				image.equals("Byte") ||
				image.equals("Code") ||
				image.equals("Short") ||
				image.equals("Int") ||
				image.equals("Long") ||
				image.equals("Float") ||
				image.equals("Double") ||
				image.equals("UByte") ||
				image.equals("UInt") ||
				image.equals("ULong") ||
				image.equals("UShort") )
			{
				image = image.toLowerCase();				
			}
		}		
			
		if( currentType == null )
			typeName = currentName + image; //package name is separate
		else
			typeName = currentName + ":" + image;
		
		if( lookupType(typeName) != null )
		{
			addError( node, Error.MULTIPLY_DEFINED_SYMBOL, "Type " + typeName + " already defined" );
			node.setType(Type.UNKNOWN);
		}
		else
		{			
			Type type = null;	
			
			switch( kind )
			{			
			case CLASS:
				type = new ClassType(typeName, modifiers, currentType );
				break;
			case ENUM:				
				type = new EnumType(typeName, modifiers, currentType );
				break;
			case ERROR:
				type = new ErrorType(typeName, modifiers, currentType );
				break;
			case EXCEPTION:
				type = new ExceptionType(typeName, modifiers, currentType );
				break;
			case INTERFACE:
				type = new InterfaceType(typeName, modifiers);
				break;
			case SINGLETON:
				type = new SingletonType(typeName, modifiers, currentType );
				break;
			case VIEW:
				type = new ViewType(typeName, modifiers );
				break;
			default:
				throw new ShadowException("Unsupported type!" );
			}			
			
			//Special case for system types			
			if( currentPackage.getQualifiedName().equals("shadow.standard"))
			{	
				switch( typeName )
				{
				case "Array":			Type.ARRAY = (ClassType) type; break;			
				case "CanAdd":			Type.CAN_ADD = (InterfaceType)type; break;
				case "CanCompare":		Type.CAN_COMPARE = (InterfaceType) type; break;
				case "CanDivide":		Type.CAN_DIVIDE = (InterfaceType)type; break;
				case "CanEqual":		Type.CAN_EQUAL = (InterfaceType) type; break;	
				case "CanModulus":		Type.CAN_MODULUS = (InterfaceType)type; break;
				case "CanMultiply":		Type.CAN_MULTIPLY = (InterfaceType)type; break;
				case "CanNegate":		Type.CAN_NEGATE = (InterfaceType)type; break;
				case "CanSubtract":		Type.CAN_SUBTRACT = (InterfaceType)type; break;
				case "CanIndex":		Type.CAN_INDEX = (InterfaceType) type; break;
				case "CanIterate":		Type.CAN_ITERATE = (InterfaceType) type; break;
				case "Class":			Type.CLASS = (ClassType) type; break;
				case "boolean":			Type.BOOLEAN = (ClassType)type; break;
				case "byte":			Type.BYTE = (ClassType)type; break;
				case "code":			Type.CODE = (ClassType)type; break;
				case "double":			Type.DOUBLE = (ClassType)type; break;
				case "Enum":			Type.ENUM = (ClassType) type; break;//the base class for enum is not an enum
				case "Error":			Type.ERROR = (ErrorType) type; break;				
				case "Exception":		Type.EXCEPTION = (ExceptionType) type; break;
				case "float":			Type.FLOAT = (ClassType)type; break;
				case "int":				Type.INT = (ClassType) type; break;
				case "Integer":			Type.INTEGER = (InterfaceType) type; break;
				case "long":			Type.LONG = (ClassType)type; break;
				case "Method":			Type.METHOD = (ClassType)type; break;
				case "Number":			Type.NUMBER = (InterfaceType) type; break;
				case "Object":			Type.OBJECT = (ClassType) type; break;				
				case "short":			Type.SHORT = (ClassType)type; break;
				case "String":			Type.STRING = (ClassType) type; break;
				case "ubyte":			Type.UBYTE = (ClassType)type; break;
				case "uint":			Type.UINT = (ClassType)type; break;
				case "ulong":			Type.ULONG = (ClassType)type; break;
				case "UnboundMethod":	Type.UNBOUND_METHOD = (ClassType)type; break;
				case "ushort":			Type.USHORT = (ClassType)type; break;
				}
			}
			
			type.addImportedItems( importedItems );
			
			try
			{			
				addType( type, currentPackage );
			}
			catch(PackageException e)
			{
				addError( node, Error.INVALID_PACKAGE, e.getMessage() );				
			}
			
			node.setType(type);	
			declarationType = type;
		}
	}
	
	public boolean addImport( String name )
	{
		String separator = File.separator; //platform independence, we hope 
		if( separator.equals("\\"))
			separator = "\\\\";
		String path = name.replaceAll("\\.", separator);
		List<File> importPaths = Configuration.getInstance().getImports();
		boolean success = false;				
		
		if( importPaths != null && importPaths.size() > 0 )
		{
			for( File importPath : importPaths )
			{	
				if( !path.contains("@"))  //no @, must be a whole package import
				{		
					File fullPath = new File( importPath, path );
					if( fullPath.isDirectory() )
					{
						File[] matchingShadow = fullPath.listFiles( new FilenameFilter(){							
							@Override
							public boolean accept(File dir, String name)
							{
								return name.endsWith(".shadow");
							}    }   );
						

						File[] matchingMeta = fullPath.listFiles( new FilenameFilter(){							
							@Override
							public boolean accept(File dir, String name)
							{
								return name.endsWith(".meta");
							}    }   );
						
						try 
						{						
							for( File file : matchingShadow )							
									importList.add(stripExtension(file.getCanonicalPath()));
															
							for( File file : matchingMeta )
							{
								String canonicalPath = stripExtension(file.getCanonicalPath());
								if( !importList.contains(canonicalPath))
									importList.add(canonicalPath);
							}
							
							success = true;
						}
						catch (IOException e) 
						{}												
					}
				}
				else
				{
					File shadowVersion;
					File metaVersion;
					if( path.startsWith("default"))
					{
						path = path.replaceFirst("default@", "");
						shadowVersion = new File( typeChecker.getCurrentFile().getParent(),  path + ".shadow");
						metaVersion = new File( typeChecker.getCurrentFile().getParent(),  path + ".meta");
					}
					else
					{
						path = path.replaceAll("@", separator);
						shadowVersion = new File( importPath, path + ".shadow" );
						metaVersion = new File( importPath, path + ".meta" );
					}
					
					try
					{						
						if( shadowVersion.exists() )
						{							
							importList.add(stripExtension(shadowVersion.getCanonicalPath()));							
							success = true;						
						}
						else if( metaVersion.exists() )
						{
							importList.add(stripExtension(metaVersion.getCanonicalPath()));							
							success = true;						
						}
					} 
					catch (IOException e)
					{						
					}
				}
				
				if( success )
					return true;
			}	
			
		}
		else
			addError(Error.INVALID_IMPORT, "No import paths specified, cannot import " + name);
		
		return false;
	}
	
	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
		{
			currentPackage = packageTree;
			importedItems.clear();			
			currentName = "";
		}
		
		return WalkType.POST_CHILDREN;			
	}
	
	@Override
	public Object visit(ASTTypeDeclaration node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )		
			importedItems.add("shadow.standard"); //add standard imports after explicit ones
				
		return WalkType.POST_CHILDREN;			
	}
	
	
	@Override
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			String name = node.getImage();
			if( node.jjtGetNumChildren() > 0 ) //has @ sign
				name = node.jjtGetChild(0).getImage() + "@" + name;
			
			if( addImport( name ) )
				importedItems.add(name);
			else
				addError(node, "No file found for import " + name);		
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	//Visitors below this point
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
			nodeTable.put(node.getType(), node );
		else
			createType( node, node.getModifiers(), node.getKind() );
			
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
			nodeTable.put(node.getType(), node );
		else
			createType( node, node.getModifiers(), TypeKind.ENUM );

		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTViewDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )
			nodeTable.put(node.getType(), node );
		else
			createType( node, node.getModifiers(), TypeKind.VIEW );
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit ) //leaving a type
		{
			currentType = currentType.getOuter();
			if( currentType == null )
				currentName = currentPackage.getQualifiedName();
			else
				currentName = currentType.getTypeName();
		}
		else //entering a type
		{					
			currentType = node.jjtGetParent().getType();
			currentName = currentType.getTypeName();				
		}
			
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit) throws ShadowException
	{	
		declarationType.setParameterized(true);	
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{			
			if ( node.jjtGetNumChildren() > 0)
			{
				boolean colon = true;
				Node first = node.jjtGetChild(0);
				String name = first.getImage();
				if( first instanceof ASTUnqualifiedName )
				{
					name += "@";
					colon = false;
				}
				
				for( int i = 1; i < node.jjtGetNumChildren(); i++ ) 
				{	
					if( colon )
						name += ":";
					else
						colon = true;
					
					name += node.jjtGetChild(i).getImage();					
				}
				
				node.setImage(name);
				
				if( first instanceof ASTUnqualifiedName )
				{
					String importName = name;					
					if( importName.contains(":"))
						importName = importName.substring(0, importName.indexOf(':'));
					if( !addImport( importName ) )
						addError(node, "No file found for import " + importName);
				}
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			//triggers an import (adding a file to the compilation process) 
			//since there's an ASTUnqualifiedName@
			if( node.jjtGetNumChildren() > 0 ) 
			{
				Node child = node.jjtGetChild(0);				
				
				if( child instanceof ASTUnqualifiedName )
				{
					String name = child.getImage() + "@" +  node.getImage();
					if( !addImport( name ) )
						addError(node, "No file found for import " + name);
				}
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTTypeArguments node, Boolean secondVisit) throws ShadowException
	{	
		if( secondVisit )
		{
			StringBuilder builder = new StringBuilder();
			builder.append("<");
			for( int i = 0; i < node.jjtGetNumChildren(); i++ ) 
			{
				Node child = node.jjtGetChild(i);
				if( i > 0 )
					builder.append(", ");
				builder.append(child.getImage());
			}
			builder.append(">");
			node.setImage(builder.toString());
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTReferenceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			StringBuilder builder = new StringBuilder(node.jjtGetChild(0).getImage());
			List<Integer> dimensions = node.getArrayDimensions();
			
			for( int i = 0; i < dimensions.size(); i++ )
			{
				
				builder.append("[");
				
				for( int j = 1; j < dimensions.get(i); j++ )
					builder.append(",");				
				
				builder.append("[");
			}					
			
			node.setImage(builder.toString());
		}
	
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit) throws ShadowException {		
		node.setType(nameToPrimitiveType(node.getImage()));		
		return WalkType.NO_CHILDREN;			
	}
	
}