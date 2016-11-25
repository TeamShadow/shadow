package shadow.tac;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import shadow.ShadowException;
import shadow.output.text.TextOutput;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.CreateDeclarationContext;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
import shadow.tac.analysis.CallGraph;
import shadow.tac.analysis.ControlFlowGraph;
import shadow.tac.analysis.ControlFlowGraph.StorageData;
import shadow.typecheck.DirectedGraph.CycleFoundException;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

/**
 * Represents an entire "class" or "unit" in TAC.
 * 
 * Consists of the module's type, references, fields, constants, methods, and subclasses.
 */
public class TACModule {
    private final Type type;    
    private final Map<String, Type> fields = new LinkedHashMap<String, Type>();
    private final List<TACConstant> constants = new ArrayList<TACConstant>();
    private final List<TACMethod> methods = new ArrayList<TACMethod>();
    private final List<TACModule> innerClasses = new ArrayList<TACModule>();
       

    public TACModule(Type moduleType) {
        type = moduleType;
        
        if (moduleType instanceof ClassType) {
            for (Entry<String, ? extends ModifiedType> field : ((ClassType) moduleType).orderAllFields())
                // if we have a class, then set all the field types
                fields.put(field.getKey(), field.getValue().getType());
        }        
    }    

    public Type getType() {
        return type;
    }

    public boolean isClass() {
        return type instanceof ClassType;
    }

    public ClassType getClassType() {
    	if( isClass() )
    		return (ClassType) type;
    	
    	throw new IllegalStateException();
    }

    public boolean isInterface() {
        return type instanceof InterfaceType;
    }

    public InterfaceType getInterfaceType() {
        return (InterfaceType) type;
    }

    public Set<String> getFieldNames() {
        return fields.keySet();
    }

    public Collection<Type> getFieldTypes() {
        return fields.values();
    }

    public Type getFieldType(String name) {
        return fields.get(name);
    }

    public void addConstant(TACConstant constant) {
        constants.add(constant);
    }

    public List<TACConstant> getConstants() {
        return constants;
    }

    public void addMethod(TACMethod method) {
        methods.add(method);
    }
    
    public void addInnerClass(TACModule innerClass) {
    	innerClasses.add(innerClass);
    }
    
    public List<TACModule> getInnerClasses() {
    	return innerClasses;
    }
    
    public List<TACModule> getAllInnerClasses() {
    	List<TACModule> allInnerClasses = new ArrayList<TACModule>();
    	allInnerClasses.addAll(innerClasses);
    	
    	for(TACModule innerClass : innerClasses)
    		allInnerClasses.addAll(innerClass.getAllInnerClasses());
    	
    	return allInnerClasses;
    }

    public List<TACMethod> getMethods() {
    	
    	List<TACMethod> allMethods = new ArrayList<TACMethod>();
    	allMethods.addAll(methods);
    	
    	for(TACModule innerClass : getAllInnerClasses() )
    		allMethods.addAll(innerClass.methods);
    	
        return allMethods;
    }

    @Override
    public String toString() {
        final StringWriter writer = new StringWriter();
        
        try {
        	TextOutput output = new TextOutput(writer); 
        	
            output.build(this);
        } catch (ShadowException ex) {
            return "Error";
        }
        
        return writer.toString();
    }

	public List<ControlFlowGraph> optimizeTAC(ErrorReporter reporter, boolean checkOnly) {

		List<TACMethod> methodList = getMethods();
		List<ControlFlowGraph> graphs = new ArrayList<ControlFlowGraph>(methodList.size());
		
		for( TACMethod method : methodList  ) {
			MethodSignature signature = method.getSignature();

			//don't bother with unimplemented methods
			if( !signature.getModifiers().isAbstract() && !signature.isNativeOrExtern() ) {			
				ControlFlowGraph graph = new ControlFlowGraph(method);
				
				//do first pass always
				boolean changed = graph.removeUnreachableCode();
				graph.removeRedundantErrors(); //some unreachable code errors are redundant
				
				if( !signature.isVoid() && !graph.returns() )
					graph.addError(signature.getNode(), Error.NOT_ALL_PATHS_RETURN, "Value-returning method " + signature.getSymbol() + signature.getMethodType() + " may not return on all paths");

				graph.addPhiNodes();
				if( graph.propagateConstants() )
					changed = true;
				
				reporter.addAll(graph); //adds errors (if any) to main reporter
				
				//now keep cycling if there is more unreachable code or 
				//more constants propagated
				while( changed ) {	
					changed = graph.removeUnreachableCode();					
					if( changed )
						changed = graph.propagateConstants();
				}
				
				graph.addGarbageCollection();
				
				graphs.add(graph);
			}
		}
		
		return graphs;
	}
	
	private void addCreateEdges(CallGraph creates)
	{
		for(MethodSignature create : creates) {			
			ShadowParser.CreateDeclarationContext declaration = (CreateDeclarationContext) create.getNode();
			if( declaration.createBlock() != null ) {
				ShadowParser.CreateBlockContext block = declaration.createBlock();
				if( block.explicitCreateInvocation() != null ) {
					ShadowParser.ExplicitCreateInvocationContext explicitCreate = block.explicitCreateInvocation();
					MethodSignature signature = explicitCreate.getSignature(); 
					//calling this rather than super
					//we can't put a dependency on a native method, since it doesn't have a control flow graph
					if( explicitCreate.getChild(0).getText().equals("this") && !signature.isNativeOrExtern() )						
						creates.addEdge(signature, create); //method depends on other signature
				}
			}
		}
	}
	
	private void addCallEdges(CallGraph calls)
	{
		for(MethodSignature method : calls) {
			ControlFlowGraph graph = calls.getControlFlowGraph(method);
			graph.addCallEdges(calls, type);
		}
	}
		
	private void checkFieldInitialization(CallGraph createGraph, CallGraph callGraph, ErrorReporter reporter)
	{		
		try {
			//sort creates by dependence on which calls others
			//usually not a very complicated relationship
			List<MethodSignature> creates = createGraph.topologicalSort();
			Map<MethodSignature, StorageData> methodData = getFieldsLoadedBeforeStores(callGraph);
			
			Set<String> fieldsToCheck = new TreeSet<String>();
			for(Entry<String, VariableDeclaratorContext> entry : type.getFields().entrySet() )
				if( ControlFlowGraph.needsInitialization(entry.getValue()) )
					fieldsToCheck.add(entry.getKey());
			
			//nothing to worry about!
			if( fieldsToCheck.isEmpty() )
				return;			
			
			//fields initialized by each create
			//useful if one create calls another
			Map<MethodSignature, Set<String>> initializedFields = new HashMap<MethodSignature, Set<String>>();
			Map<MethodSignature, Set<String>> priorThisStores = new HashMap<MethodSignature, Set<String>>();
						
			for( MethodSignature create : creates ) {
				ControlFlowGraph graph = createGraph.getControlFlowGraph(create);
				Set<MethodSignature> incoming = createGraph.getIncoming(create); //can only be a single create or none
				Set<String> alreadyInitialized;
				Set<String> thisStores = new HashSet<String>();
				if( incoming.isEmpty() )
					alreadyInitialized = new HashSet<String>();
				else {
					MethodSignature parent = incoming.iterator().next();
					alreadyInitialized = initializedFields.get(parent);
					thisStores.addAll(priorThisStores.get(parent));
				}
				
				Set<String> initialized = graph.getInitializedFields(alreadyInitialized, thisStores, methodData, fieldsToCheck);
				reporter.addAll(graph); //adds any errors found when getting initialized fields
				if( !create.getModifiers().isPrivate() ) {
					for( String field : fieldsToCheck )
						if( !initialized.contains(field) )
							reporter.addError(create.getNode(), Error.UNINITIALIZED_FIELD, "Non-nullable field " + field + " may not be initialized by a create");
				}
				initializedFields.put(create, initialized);
				priorThisStores.put(create, thisStores);
			}			
		}
		catch(CycleFoundException e) {
			reporter.addError(((MethodSignature)e.getCycleCause()).getNode(), Error.CIRCULAR_CREATE, "Create calls are circular");
		}		
	}

	private Map<MethodSignature, StorageData> getFieldsLoadedBeforeStores(CallGraph callGraph ) {
		Map<MethodSignature, StorageData> methodData = new HashMap<MethodSignature, StorageData>();		
		
		for( MethodSignature method : callGraph) {
			ControlFlowGraph graph = callGraph.getControlFlowGraph(method);
			methodData.put(method, graph.getLoadsBeforeStoresInMethods(type, callGraph));
		}
		
		//since methods can call each other, an edge between methods means that the
		//caller (end of the edge) should be considered to use the same fields
		//as the callee (beginning of the edge)
		//things used may need to propagate through the graph
		boolean changed = true;
		while( changed ) {
			changed = false;
			
			for( MethodSignature method : callGraph ) {
				StorageData data = methodData.get(method);
				
				for( MethodSignature callee : callGraph.getIncoming(method) )
					if( data.addAll(methodData.get(callee)) )
						changed = true;
			}			
		}
		
		return methodData;
	}

	public void checkFieldInitialization(ErrorReporter reporter, List<ControlFlowGraph> graphs) {
		CallGraph createGraph = new CallGraph();
		CallGraph callGraph = new CallGraph();
		for( ControlFlowGraph graph : graphs ) {
			MethodSignature method = graph.getMethod().getSignature();						
			if( method.isCreate() && method.getOuter().equals(type) )
				createGraph.addNode(method, graph);
			//inner methods (including creates) can be called,
			//but they must be locked (or be creates), otherwise overrides
			//could make their behavior unpredictable
			else if( type.encloses(method.getOuter()) && (method.isCreate() || method.isLocked()) ) 
				callGraph.addNode(method, graph);			
		}
		
		addCreateEdges(createGraph);
		addCallEdges(callGraph);
		checkFieldInitialization(createGraph, callGraph, reporter);
	}
}
