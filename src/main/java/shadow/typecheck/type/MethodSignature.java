package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shadow.doctool.Documentation;
import shadow.parse.Context;
import shadow.parse.ShadowParser;

public class MethodSignature implements Comparable<MethodSignature> {
	public static final int IMPORT_NATIVE = 0;
	public static final int IMPORT_ASSEMBLY = 1;
	public static final int IMPORT_METHOD = 2;
	
	public static final int EXPORT_NATIVE = 0;
	public static final int EXPORT_ASSEMBLY = 1;
	public static final int EXPORT_METHOD = 2;
	
	protected final MethodType type;
	protected final String symbol;
	private final Context node;	/** The AST context that corresponds to the branch of the tree for this method */
	private final MethodSignature wrapped;
	private MethodSignature signatureWithoutTypeArguments;
	private Type outer;
	private Set<SingletonType> singletons = new HashSet<>();
	private int importType = -1;
	private int exportType = -1;
	private List<Type> allowedExports;

	private MethodSignature(MethodType type, String symbol, Type outer, Context node, MethodSignature wrapped) 
	{
		this.type = type;
		this.symbol = symbol;
		this.node = node;
		this.wrapped = wrapped;
		this.outer = outer;
		signatureWithoutTypeArguments = this;
		if( node != null )
			node.setEnclosingType(outer);
	}
	public MethodSignature(MethodType type, String symbol, Type outer, Context node) 
	{
		this(type, symbol, outer, node, null);
	}
	
	public MethodSignature(Type enclosingType, String symbol, 
			Modifiers modifiers, Documentation documentation, Context node) 
	{		
		this(new MethodType(enclosingType, modifiers, documentation), symbol, enclosingType, node);
	}
	
	public void addParameter(String name, ModifiedType node) 
	{
		this.type.addParameter(name, node);
	}
	
	public ModifiedType getParameterType(String paramName) 
	{
		return type.getParameterType(paramName);		
	}
	
	public boolean containsParam(String paramName) {
		return type.containsParam(paramName);
	}
	
	public void addReturn(ModifiedType ret) {
		type.addReturn(ret);
	}
	
	public Modifiers getModifiers() {
		return type.getModifiers();
	}
	
	public String getSymbol() {
		return symbol;
	}

	public Context getNode() {
		return node;
	}		

	public boolean canAccept( SequenceType argumentTypes )
	{
		return type.canAccept(argumentTypes);		
	}

	public boolean equals(Object o)
	{
		if( o != null && o instanceof MethodSignature )
		{
			MethodSignature ms = (MethodSignature)o;
			//Is it pretty? No. Should it work?  Probably!  
			return ms.getMangledName().equals(getMangledName());			
		}
		else
			return false;
	}
	
	public boolean isIndistinguishable(MethodSignature signature )
	//isIndistinguishable() differs from equals() in that differences in return types are ignored
	{
		if( signature != null && signature.symbol.equals(symbol) )			
			return type.matchesParams(signature.type );		
		else
			return false;
	}
	
	// These are the true return types that the compiler will use	
	public SequenceType getFullReturnTypes() {	
		Type outerType = getOuter();
		if (isCreate()) {
			SimpleModifiedType modified;				
			if( outerType instanceof InterfaceType )
				modified = new SimpleModifiedType(Type.OBJECT);
			else
				modified = new SimpleModifiedType(outerType);
			
			if( outerType.isPrimitive() )
				modified.getModifiers().addModifier(Modifiers.NULLABLE);
			
			return new SequenceType(Collections.<ModifiedType>singletonList(modified));
		}
		
		//if( isWrapper() || outerType instanceof InterfaceType )		
			//return signatureWithoutTypeArguments.type.getReturnTypes();
		//else
			return type.getReturnTypes();
		
		//return type.getReturnTypes();
	}
	
	// These are the true parameter types that the compiler will use
	public SequenceType getFullParameterTypes() {
		SequenceType paramTypes = new SequenceType();	
		
		MethodType methodType;
		//if( isWrapper() || getOuter() instanceof InterfaceType )
			//methodType = signatureWithoutTypeArguments.type;
		//else
			methodType = type;		

		if(!isImportAssembly()) {
			Type outerType = getOuter();
			if (isCreate() || outerType instanceof InterfaceType ) //since actual object is unknown, assume Object for all interface methods
				paramTypes.add(new SimpleModifiedType(Type.OBJECT));
			else
				paramTypes.add(new SimpleModifiedType(outerType)); // this
		}
		
		paramTypes.addAll(methodType.getParameterTypes());
		return paramTypes;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(getModifiers().toString());
		/*
		// TODO: Uncomment when decorators are generated in .meta
		if(isExportMethod() && getExportTypes() != null) {
			sb.append("[");
			for(Type t : getExportTypes()) {
				sb.append(t.getTypeName());
			}
			sb.append("] ");
		}*/
		
		sb.append(symbol);
		
		//nothing more for destroy
		if( !symbol.equals("destroy") ) {			
			sb.append(type.parametersToString());
			
			//no return types for create
			if( !symbol.equals("create") )
				sb.append(" => ").append(type.getReturnTypes());
		}
		
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return getMangledName().hashCode();
	}
	
	//Is it only the wrapped ones that correspond to interface methods?
	//If so, those are the ones that need special generic attention
	public String getMangledName() {
		if(isImportAssembly() || isExportAssembly()) {
			return symbol;
		}
		
		StringBuilder sb = new StringBuilder();
		
		if( isWrapper() )		
			sb.append(getWrapped().getOuter().toString(Type.MANGLE));			
		else if(isImportMethod()) // we set the first parameter as the owner of the method
			sb.append(getParameterTypes().get(0).getType().toString(Type.MANGLE));
		else
			sb.append(getOuter().toString(Type.MANGLE));
		
		sb.append("_M").append(Type.mangle(symbol)).append(type.getTypeWithoutTypeArguments().toString(Type.MANGLE | Type.TYPE_PARAMETERS | (isImportMethod() ? Type.MANGLE_IMPORT_METHOD : 0)));
		
		if (isWrapper())
			sb.append("_W_").append(getOuter().toString(Type.MANGLE | Type.TYPE_PARAMETERS));
		
		return sb.toString();
	}
	
	public List<String> getParameterNames() {
		return type.getParameterNames();
	}
	
	public SequenceType getParameterTypes() {
		return type.getParameterTypes();
	}
	
	public SequenceType getReturnTypes()
	{
		return type.getReturnTypes();
	}
	
	public Type getOuter()
	{
		return outer;
	}
	
	public MethodType getMethodType() {
		return type;
	}
	
	public MethodSignature replace(List<ModifiedType> values,
			List<ModifiedType> replacements) throws InstantiationException {
		MethodSignature replaced = new MethodSignature(type.replace(values, replacements), symbol, outer.replace(values, replacements), node);
		replaced.signatureWithoutTypeArguments = signatureWithoutTypeArguments;
		
		return replaced;
	}
	
	public MethodSignature partiallyReplace(List<ModifiedType> values,
			List<ModifiedType> replacements) throws InstantiationException {		
		MethodSignature replaced = new MethodSignature(type.partiallyReplace(values, replacements), symbol, outer.partiallyReplace(values, replacements), node);
		replaced.signatureWithoutTypeArguments = signatureWithoutTypeArguments;
		return replaced;
	}

	public void updateFieldsAndMethods() throws InstantiationException {
		type.updateFieldsAndMethods();
	}
	
	public MethodSignature getSignatureWithoutTypeArguments()
	{
		return signatureWithoutTypeArguments; 
	}
	
	public boolean matchesInterface(MethodSignature interfaceSignature) {
			return interfaceSignature.symbol.equals(symbol) && 					
					type.matchesInterface(interfaceSignature.type);
	}

	@Override
	public int compareTo(MethodSignature o) {
		return getMangledName().compareTo(o.getMangledName());
	}

	public boolean isCreate() {		
		return symbol.equals("create");
	}
	
	public boolean isDestroy() {		
		return symbol.equals("destroy");
	}
	
	public boolean isCopy()
	{
		return symbol.equals("copy");
	}
	
	public boolean isGet()
	{	
		return ( type.getModifiers().isGet() && type.getReturnTypes().size() == 1 && type.getParameterTypes().isEmpty() );	
	}
	
	public boolean isSet()
	{	
		return ( type.getModifiers().isSet() && type.getReturnTypes().isEmpty() && type.getParameterTypes().size() == 1 );	
	}
	
	public boolean isLocked()
	{		
		return type.getModifiers().isLocked();
	}
	
	public boolean isVoid()
	{
		return type.getReturnTypes().size() == 0;
	}
	
	public boolean isSingle()
	{
		return type.getReturnTypes().size() == 1;
	}	

	public boolean isSequence()
	{
		return type.getReturnTypes().size() > 1;
	}
	
	public ModifiedType getSingleReturnType()
	{
		if (!isSingle())
			throw new IllegalStateException();
		return getReturnTypes().get(0);
	}	

	public boolean isWrapper() {
		return wrapped != null;
	}
	public MethodSignature getWrapped() {
		return wrapped;
	}

	public MethodSignature wrap(MethodSignature wrapped) {		
		Modifiers modifiers = new Modifiers(wrapped.getModifiers().getModifiers());
		MethodType methodType;
		//if( outer instanceof InterfaceType )
		//	methodType = type.copy(wrapped.getOuter(), modifiers);
			//methodType = signatureWithoutTypeArguments.type.copy(modifiers);
		//else
		methodType = type.copy(modifiers);
		MethodSignature wrapper = new MethodSignature(methodType, symbol, outer, node, wrapped);
		wrapper.signatureWithoutTypeArguments = signatureWithoutTypeArguments;
		return wrapper;
	}

	//makes a copy of the method signature with a different outer type	 
	public MethodSignature copy(Type newOuter) {
		return new MethodSignature(type, symbol, newOuter, node, wrapped);		
	}

	public void setOuter(Type outer) {
		this.outer = outer;
	}

	public boolean hasDocumentation()
	{
		return type.hasDocumentation();
	}
	
	public Documentation getDocumentation()
	{
		return type.getDocumentation();
	}
	
	public void addSingleton(SingletonType type) {
		singletons.add(type);
	}
	
	public Set<SingletonType> getSingletons() {
		return singletons;
	}
	
	public boolean hasBlock() {
		boolean hasBlock = false;
		
		if( node instanceof ShadowParser.CreateDeclarationContext )
			hasBlock = ((ShadowParser.CreateDeclarationContext)node).createBlock() != null;
		else if( node instanceof ShadowParser.DestroyDeclarationContext )
			hasBlock = ((ShadowParser.DestroyDeclarationContext)node).block() != null;
		else if( node instanceof ShadowParser.MethodDeclarationContext )
			hasBlock = ((ShadowParser.MethodDeclarationContext)node).block() != null;
		
		return hasBlock;
	}
	
	public boolean isAbstract() {
		return type.getModifiers().isAbstract();
	}
	
	public void setImportType(int type) {
		this.importType = type;
	}
	
	public void setExportType(int type) {
		this.exportType = type;
	}
	
	public boolean isImport() {
		return importType != -1;
	}
	
	public boolean isImportNative() {
		return importType == IMPORT_NATIVE;
	}
	
	public boolean isImportAssembly() {
		return importType == IMPORT_ASSEMBLY;
	}
	
	public boolean isImportMethod() {
		return importType == IMPORT_METHOD;
	}
	
	public boolean isExport() {
		return exportType != -1;
	}
	
	public boolean isExportNative() {
		return exportType == EXPORT_NATIVE;
	}
	
	public boolean isExportAssembly() {
		return exportType == EXPORT_ASSEMBLY;
	}
	
	public boolean isExportMethod() {
		return exportType == EXPORT_METHOD;
	}
	
	// TODO: Find a better name for this
	public void addExportType(Type type) {
		if(allowedExports == null) {
			allowedExports = new ArrayList<>();
		}
		
		allowedExports.add(type);
	}

	// TODO: Find a better name for this
	public List<Type> getExportTypes() {
		return allowedExports;
	}
}
