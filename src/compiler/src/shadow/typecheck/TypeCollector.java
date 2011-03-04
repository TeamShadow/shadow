package shadow.typecheck;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import shadow.Configuration;
import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTClassOrInterfaceBody;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTExtendsList;
import shadow.parser.javacc.ASTImplementsList;
import shadow.parser.javacc.ASTImportDeclaration;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPackageDeclaration;
import shadow.parser.javacc.ASTUnqualifiedName;
import shadow.parser.javacc.ASTViewDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ErrorType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.Type.Kind;

public class TypeCollector extends BaseChecker
{	
	protected Map<Type,List<String>> extendsTable = new HashMap<Type,List<String>>();
	protected Map<Type,Node> nodeTable = new HashMap<Type,Node>(); //for errors only
	protected Map<Type,List<String>> implementsTable = new HashMap<Type,List<String>>();	
	protected String currentName = "";
	protected Map<File, Node> files = new HashMap<File, Node>();
	
	public TypeCollector(boolean debug)
	{		
		super(debug, new HashMap< Package, HashMap<String, Type>>(), new LinkedList<File>(), new Package() );
		// put all of our built-in types into the TypeTable
		// change this step eventually to pull from the shadow.standard package
		
		addType(Type.OBJECT.getTypeName(),	Type.OBJECT);
		addType(Type.BOOLEAN.getTypeName(),	Type.BOOLEAN);
		addType(Type.BYTE.getTypeName(),		Type.BYTE);
		addType(Type.CODE.getTypeName(),		Type.CODE);
		addType(Type.SHORT.getTypeName(),		Type.SHORT);
		addType(Type.INT.getTypeName(),		Type.INT);
		addType(Type.LONG.getTypeName(),		Type.LONG);
		addType(Type.FLOAT.getTypeName(),		Type.FLOAT);
		addType(Type.DOUBLE.getTypeName(),	Type.DOUBLE);
		addType(Type.STRING.getTypeName(),	Type.STRING);
		addType(Type.UBYTE.getTypeName(),		Type.UBYTE);
		addType(Type.UINT.getTypeName(),		Type.UINT);
		addType(Type.ULONG.getTypeName(),		Type.ULONG);
		addType(Type.USHORT.getTypeName(),	Type.USHORT);
		addType(Type.NULL.getTypeName(),		Type.NULL);	
		
		addType(Type.ENUM.getTypeName(), Type.ENUM);
		addType(Type.ERROR.getTypeName(), Type.ENUM);	
		addType(Type.EXCEPTION.getTypeName(), Type.ENUM);
	}
	
	
	private void updateMissingTypes()
	{
		List<String> list;
		
		for( Package p : getTypeTable().keySet() )
		{
			for( Type type : getTypeTable().get(p).values() ) //look through all types, updating their extends and implements
			{	
				TreeSet<String> missingTypes = new TreeSet<String>();
				
				if( type instanceof ClassType ) //includes error, exception, and enum (for now)
				{				
					if( !type.isBuiltIn() )
					{
						ClassType classType = (ClassType)type;
						if( extendsTable.containsKey(type))
						{
							list = extendsTable.get(type);
							ClassType parent = (ClassType)lookupTypeStartingAt(list.get(0), classType.getOuter()); //only one thing in extends lists for classes
							if( parent == null )						
								missingTypes.add(list.get(0));
							else							
								classType.setExtendType(parent);
						}
						else if( type.getKind() == Kind.CLASS )
							classType.setExtendType(Type.OBJECT);
						else if( type.getKind() == Kind.ENUM )
							classType.setExtendType(Type.ENUM);
						else if( type.getKind() == Kind.ERROR )
							classType.setExtendType(Type.ERROR);
						else if( type.getKind() == Kind.EXCEPTION )
							classType.setExtendType(Type.EXCEPTION);
						
						if( implementsTable.containsKey(type))
						{
							list = implementsTable.get(type);			
							for( String name : list )
							{
								InterfaceType _interface = (InterfaceType)lookupTypeStartingAt(name, classType.getOuter());
								if( _interface == null )							
									missingTypes.add(name);
								else							
									classType.addInterface(_interface);
							}
						}
					}
				}
				else if( type instanceof InterfaceType ) 
				{
					InterfaceType interfaceType = (InterfaceType)type;
					if( extendsTable.containsKey(type))
					{
						list = extendsTable.get(type);
						for( String name : list )
						{
							InterfaceType _interface = (InterfaceType)lookupTypeStartingAt(name, interfaceType.getOuter());
							if( _interface == null )						
								missingTypes.add(name);
							else							
								interfaceType.addExtendType(_interface);
						}
					}				
				}
				
				if( missingTypes.size() > 0 )	
					addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot define type " + type + " because it depends on the following undefined types " + missingTypes);			
			}
		}
	}
	
	
	public void collectTypes(File input, Node node) throws FileNotFoundException, ParseException, ShadowException
	//includes files in the same directory
	{			
		//Walk over file being checked
		ASTWalker walker = new ASTWalker( this );		
		walker.walk(node);
		files.put( input, node );
		
		//add files in directory BEFORE imports
		File[] directoryFiles = input.getParentFile().listFiles( new FilenameFilter()
				{
					public boolean accept(File dir, String name)
					{
						return name.endsWith(".shadow");
					}
				}
		);		
		List<File> fileList = new ArrayList<File>();
		fileList.addAll(Arrays.asList(directoryFiles));	
		
		//Add import list
		fileList.addAll(getImportList());
		
		for( File other : fileList )
		{			
			if( !files.containsKey(other) ) //don't double add
			{
				ShadowParser parser = new ShadowParser(new FileInputStream(other));
			    SimpleNode otherNode = parser.CompilationUnit();
			    
				TypeCollector collector = new TypeCollector(debug);
				walker = new ASTWalker( collector );		
				walker.walk(otherNode);							
				
				HashMap<Package, HashMap<String, Type>> outsideTypes = collector.getTypeTable();
				files.put(other, otherNode);
				
				
				//by adding in order, we preserve proper name hiding (might happen?)
				for( Package p : outsideTypes.keySet() )
				{	
					if( typeTable.containsKey(p))
					{
						HashMap<String,Type> packageTypes = typeTable.get(p);
						packageTypes.putAll(outsideTypes.get(p));						
					}
					else
						typeTable.put(p, outsideTypes.get(p));
				}
				
				//add in the imports from the other file as long as they aren't in the list already
				//NO!  This isn't C.  We only need imports for type information.  We don't need imports of imports for anything.				
				/*
				List<File> otherImports = collector.getImportList();
				for( File otherImport : otherImports )
					if( !fileList.contains(otherImport) )
						fileList.add(otherImport);
				*/	
			}
		}	
		
		updateMissingTypes();					
	}
	
	public Map<File, Node> getFiles()
	{
		return files;
	}
	
/*	public void linkTypeTable()
	{
		//this is supposed to find the parents for everything
		List<String> list;
		for( Type type : getTypeTable().values() )
		{	
			if( type instanceof ClassType ) //includes error, exception, and enum (for now)
			{
				if( !type.isBuiltIn() )
				{
					ClassType classType = (ClassType)type;
					if( extendsTable.containsKey(type))
					{
						list = extendsTable.get(type);
						ClassType parent = (ClassType)lookupType(list.get(0), classType.getOuter()); //only one thing in extends lists for classes
						if( parent == null )
							addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot extend undefined class " + list.get(0));
						else
							classType.setExtendType(parent);
					}
					else if( type.getKind() == Kind.CLASS )
						classType.setExtendType(Type.OBJECT);
					else if( type.getKind() == Kind.ENUM )
						classType.setExtendType(Type.ENUM);
					else if( type.getKind() == Kind.ERROR )
						classType.setExtendType(Type.ERROR);
					else if( type.getKind() == Kind.EXCEPTION )
						classType.setExtendType(Type.EXCEPTION);
					
					if( implementsTable.containsKey(type))
					{
						list = implementsTable.get(type);			
						for( String name : list )
						{
							InterfaceType _interface = (InterfaceType)lookupType(name, classType.getOuter());
							if( _interface == null )
								addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot implement undefined interface " + name);
							else							
								classType.addImplementType(_interface);
						}
					}
				}
			}
			else if( type instanceof InterfaceType ) 
			{
				InterfaceType interfaceType = (InterfaceType)type;
				if( extendsTable.containsKey(type))
				{
					list = extendsTable.get(type);
					for( String name : list )
					{
						InterfaceType _interface = (InterfaceType)lookupType(name, interfaceType.getOuter());
						if( _interface == null )
							addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot extend undefined interface " + name);
						else							
							interfaceType.addExtendType(_interface);
					}
				}				
			}
		}	
	}
*/
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )		
			exitType( node );		
		else
			enterType( node, node.getModifiers(), node.getKind() );
			
		return WalkType.POST_CHILDREN;
	}	
	
	
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
			currentType = node.getEnclosingType();
		else
			currentType = node.jjtGetParent().getType();
			
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			if( node.jjtGetNumChildren() > 0 ) 
			{
				Node child = node.jjtGetChild(0); 
				if( child instanceof ASTUnqualifiedName )
					node.setImage(child.getImage() + "@" + node.getImage());
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	

	
	private void enterType( SimpleNode node, int modifiers, Kind kind ) throws ShadowException
	{		 
		if( !currentName.isEmpty() )
			currentName += ".";
		
		currentName += node.getImage();	
		if( lookupType(currentName) != null )
			addError( node, Error.MULT_SYM, "Type " + currentName + " already defined" );
		else
		{			
			Type type = null;
			
			switch( kind )
			{
			case CLASS:
				type = new ClassType(currentName, modifiers, currentType );
				break;
			case ENUM:
				//enum may need some fine tuning
				type = new EnumType(currentName, modifiers, currentType );
				break;
			case ERROR:
				type = new ErrorType(currentName, modifiers, currentType );
				break;
			case EXCEPTION:
				type = new ExceptionType(currentName, modifiers, currentType );
				break;
			case INTERFACE:
				type = new InterfaceType(currentName, modifiers, currentType );
				break;			
			case VIEW:
				//add support for views eventually
				break;
			default:
				throw new ShadowException("Unsupported type!" );
			}
			
			addType( currentName, type  );
			node.setType(type);
			
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				if( node.jjtGetChild(i).getClass() == ASTExtendsList.class )
					addExtends( (ASTExtendsList)node.jjtGetChild(i), type );
				else if( node.jjtGetChild(i).getClass() == ASTImplementsList.class )
					addImplements( (ASTImplementsList)node.jjtGetChild(i), type );
		}
	}
	
	private void addExtends( ASTExtendsList node, Type type )
	{
		List<String> list = new LinkedList<String>();
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			list.add( node.jjtGetChild(i).getImage() );
		
		extendsTable.put(type, list);
		nodeTable.put(type, node.jjtGetParent() );
	}
	
	public void addImplements( ASTImplementsList node, Type type )
	{
		List<String> list = new LinkedList<String>();
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			list.add( node.jjtGetChild(i).getImage() );
		
		implementsTable.put(type, list);
		nodeTable.put(type, node.jjtGetParent() );
	}
	
	private void exitType( SimpleNode node )
	{
		//	remove innermost class		
		int index = currentName.lastIndexOf('.'); 
		if( index == -1 )
			currentName = "";
		else
			currentName = currentName.substring(0, index);
		
//		if( currentType != null )
//			currentType = currentType.getOuter();
	}
	

	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )		
			exitType( node );		
		else
			enterType( node, node.getModifiers(), Type.Kind.ENUM );
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTViewDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )		

			exitType( node );		
		else
			enterType( node, node.getModifiers(), Type.Kind.VIEW );
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTPackageDeclaration node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			Node name = node.jjtGetChild(0);			
			currentName = name.getImage();	//name of all types start in this package			
			currentPackage = packageTree.addPackagePath(currentName); 
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			if( node.jjtGetNumChildren() > 0 )
			{
				Node child = node.jjtGetChild(0);
				node.setImage( child.getImage() + "@" + node.getImage() );
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	
	@Override
	public Object visit(ASTImportDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )
		{
			Node name = node.jjtGetChild(0);
			String separator = File.separator; //platform independence, we hope 
			if( separator.equals("\\"))
				separator = "\\\\";
			String path = name.getImage().replaceAll("\\.", separator);
			List<File> importPaths = Configuration.getInstance().getImportPaths();
			boolean success = false;			
				
			
			if( importPaths != null )
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
							
							for( File file : matchingShadow )
							{
								String prefix = file.getName().substring(0, file.getName().lastIndexOf(".shadow"));
								File metaVersion = new File( file.getParent(), prefix + ".meta"  );
								if( metaVersion.exists() && metaVersion.lastModified() >= file.lastModified() )
									importList.add(metaVersion);
								else
									importList.add(file);
							}
							
							for( File file : matchingMeta )
								if( !importList.contains(file))
									importList.add(file);
							
							node.setPackage(packageTree.addPackagePath(path));
							
							success = true;						
						}
					}
					else
					{
						String directory = path.substring(0, path.indexOf('@'));
						path = path.replaceAll("@", separator);
						File shadowVersion = new File( importPath, path + ".shadow" );
						File metaVersion = new File( importPath, path + ".meta" );
						if( shadowVersion.exists() )
						{
							if( metaVersion.exists() && metaVersion.lastModified() >= shadowVersion.lastModified() )												
								importList.add(metaVersion);
							else
								importList.add(shadowVersion);	
							
							node.setPackage(packageTree.addPackagePath(directory));
							success = true;						
						}
						else if( metaVersion.exists() )
						{
							importList.add(metaVersion);
							node.setPackage(packageTree.addPackagePath(directory));
							success = true;						
						}
					}
					
					if( success )
						break;
				}			
				
				if( !success )
					addError(node, "Import " + name.getImage() + " not found");
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
}
