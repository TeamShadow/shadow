package shadow.tac;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import shadow.ShadowException;
import shadow.interpreter.ShadowUndefined;
import shadow.output.text.TextOutput;
import shadow.tac.nodes.TACAllocateVariable;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACChangeReferenceCount;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLocalLoad;
import shadow.tac.nodes.TACLocalStore;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACParameter;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACSequenceElement;
import shadow.tac.nodes.TACStore;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACMethod
{
	private final MethodSignature signature;
	private final Map<String, TACVariable> locals;
	private final Map<String, TACVariable> parameters;
	private final Deque<Map<String, TACVariable>> scopes;	
	private boolean landingpad;
	private int labelCounter = 0;		//counter to keep label numbering unique
	private int variableCounter = 0;	//counter to keep variable number unique
	private TACNode node;
	private boolean garbageCollected;
	
	public TACMethod(MethodSignature methodSignature)
	{
		signature = methodSignature;
		locals = new LinkedHashMap<String, TACVariable>();
		parameters = new LinkedHashMap<String, TACVariable>();
		scopes = new LinkedList<Map<String, TACVariable>>();		
		landingpad = false;			
		enterScope();		
	}
	
	public boolean isGarbageCollected() {
		return garbageCollected;
	}
	
	public void setGarbageCollected(boolean value) {
		garbageCollected = value;
	}
	
	public TACNode getNode()
	{
		return node;		
	}
	
	public void setNode(TACNode node)
	{
		this.node = node;
	}		

	public TACMethod addParameters(TACNode node, boolean isWrapped)
	{		
		//method starts with a label, always
		new TACLabel(this).insertBefore(node);
		
		Type prefixType = signature.getOuter();		
		int parameter = 0;		
		
		if( prefixType instanceof InterfaceType )
			prefixType = Type.OBJECT;
		
		ModifiedType modifiedType = new SimpleModifiedType(prefixType);
		//we mark the primitive "this" as nullable, to show that it's the object version of the primitive
		if( prefixType.isPrimitive() && (signature.isCreate() || isWrapped ) )
			modifiedType.getModifiers().addModifier(Modifiers.NULLABLE);
		
		if (signature.isCreate() ) {
			new TACLocalStore(node, addParameter(modifiedType, "this"), TACCast.cast(node, modifiedType, new TACParameter(node, new SimpleModifiedType(Type.OBJECT), parameter++)));
			if( prefixType.hasOuter()) {
				modifiedType = new SimpleModifiedType(prefixType.getOuter());
				if( prefixType.getOuter().isPrimitive() && (signature.isCreate() || isWrapped ) )
					modifiedType.getModifiers().addModifier(Modifiers.NULLABLE);
				
				new TACLocalStore(node, addParameter(modifiedType, "_outer"), new TACParameter(node, modifiedType, parameter++));				
			}
		}
		else if(!signature.isExternWithoutBlock()) {
			new TACLocalStore(node, addParameter(modifiedType, "this"), new TACParameter(node, modifiedType, parameter++));
		}
		
		MethodType type = signature.getMethodType();		
		if( isWrapped )
			type = signature.getSignatureWithoutTypeArguments().getMethodType();
		
		for (String name : type.getParameterNames()) {
			ModifiedType parameterType = type.getParameterType(name);
			new TACLocalStore(node, addParameter(parameterType, name), new TACParameter(node, parameterType, parameter++));
		}
		
		//variable to hold low level exception data
		//note that variables cannot start with underscore (_) in Shadow, so no collision is possible
		new TACLocalStore(node, addLocal(new SimpleModifiedType(Type.getExceptionType()), "_exception"), new TACLiteral(node, new ShadowUndefined(Type.getExceptionType())));

		
		if( !signature.isCreate() ) {		
			SequenceType returnTypes = signature.getFullReturnTypes(); 
			
			//create variable to hold returns, needed for GC cleanup before return
			if( returnTypes.size() == 1 ) {
				TACVariable variable = addLocal(returnTypes.get(0), "return");				
				//non-GC variables use SSA, which needs an initial storage for phi nodes
				if( !variable.needsGarbageCollection() )
					new TACLocalStore(node, variable, new TACLiteral(node, new ShadowUndefined(variable.getType())));
			}
			else if( returnTypes.size() > 1  ) {
				for( int i = 0; i < returnTypes.size(); ++i ) {
					TACVariable variable = addLocal(returnTypes.get(i), "_return" + i);
					//non-GC variables use SSA, which needs an initial storage for phi nodes
					if( !variable.needsGarbageCollection() )
						new TACLocalStore(node, variable, new TACLiteral(node, new ShadowUndefined(variable.getType())));
				}
			}
		}
		
		return this;
	}
	

    private void addCleanup(Set<TACVariable> storedVariables) {
		//for( Block block : this )
			//block.addGarbageCollection();
				
		//find variables that need garbage collection
    	/*
		List<TACVariable> cleanupVariables = new ArrayList<TACVariable>();
		for( TACVariable variable : storedVariables )
			if( variable.needsGarbageCollection() )
				cleanupVariables.add(variable);
		
		if( cleanupVariables.size() > 0 ) {
			//insert allocations of variables in root block
			//right before instruction after starting label
			TACNode next = getNode().getNext();			
			for( TACVariable variable : cleanupVariables )
				new TACAllocateVariable( next, variable );
			
			//method has been created with built in "finally"
		*/	
			/*
			
			//create block with cleanup stuff (to decrement reference counts)
			TACLabel cleanupLabel = new TACLabel(method);
			//thing before root's label is actually the last instruction in the method
			cleanupLabel.insertAfter(root.getLabel().getPrevious());
			
			//add phi for indirect branching
			TACPhi phi = new TACPhi(root.getLabel(), method.addTempLocal(new SimpleModifiedType(new PointerType())));
			*/
    	
    		TACNode last = getNode().getPrevious().getPrevious(); //should be indirect branch at the end of finally, before final done label
    		TACNode start = last.getPrevious(); //cleanup nodes will be added between start and last
    		    	
    		while( storedVariables.size() > 0 ) {				
				TACNode lastUpdate = null;
				
				//add each decrement before the first thing, making it the new last thing
				for( TACVariable variable : storedVariables )	
					//return doesn't get cleaned up, so that it has an extra reference count
					if( !variable.isReturn() )				
						lastUpdate = new TACChangeReferenceCount(last, variable, false);
			
				boolean changed = true;
				storedVariables.clear();
				
				while( changed ) {					
					changed = false;
				
					//unfortunately, some of the cleanup can require additional variables to be used
					TACNode node = start;
					while( node != last ) {
						TACNode next = node.getNext();			
						//Update changed only if newly garbage collected
						//and creating new things that might need to be garbage collected
						
						if( !node.isGarbageCollected() ) { //if already GC, skip it
							if( node instanceof TACLocalStore ) {
								TACLocalStore store = (TACLocalStore) node;
								TACVariable variable = store.getVariable();
								//type that needs garbage collection will be stored in alloc'ed variable
								if( variable.needsGarbageCollection()  ) {
									store.setGarbageCollected(true);
									storedVariables.add(variable);
										
									if( variable.getType() instanceof ArrayType && store.getClassData() == null ) {
										ArrayType arrayType = (ArrayType) variable.getType();
										TACClass classData = new TACClass(node, arrayType.getBaseType());
										store.setClassData(classData.getClassData());
										
										changed = true;
									}
								}
							}
							else if( node instanceof TACLocalLoad ) {
								TACLocalLoad load = (TACLocalLoad)node;
								TACVariable variable = load.getVariable();
								if( variable.needsGarbageCollection() )
									load.setGarbageCollected(true);
							}
							else if( node instanceof TACPhi ) {
								TACPhi phi = (TACPhi)node;
								TACVariable variable = phi.getVariable();
								if( variable.needsGarbageCollection() )
									phi.setGarbageCollected(true);												
							}
							else if( node instanceof TACStore ) {
								TACStore store = (TACStore) node;
								TACReference reference = store.getReference();
													
								if( reference.needsGarbageCollection() ) {
									store.setGarbageCollected(true);
									if( reference.getType() instanceof ArrayType && store.getClassData() == null ) {
										ArrayType arrayType = (ArrayType) reference.getType();
										TACClass classData = new TACClass(node, arrayType.getBaseType());
										store.setClassData(classData.getClassData());
										
										changed = true;
									}
								}
							}											
							else if( node instanceof TACNewArray ) {
								TACNewArray newArray = (TACNewArray) node;
								if( newArray.hasLocalStore() ) {
									TACLocalStore store = newArray.getLocalStore();
									if( store.getVariable().needsGarbageCollection( ) && !store.getVariable().isReturn() )
										store.setIncrementReference(false);
								}
								else if( newArray.hasMemoryStore() )  {
									TACStore store = newArray.getMemoryStore();
									if( store.getReference().needsGarbageCollection())
										store.setIncrementReference(false);
								}
								else {					
									TACVariable temp = addTempLocal(newArray);							
									//store into temporary for reference count purposes (and change next)
									TACLocalStore store = new TACLocalStore(next, temp, (TACNewArray)node, false);
									store.setGarbageCollected(true);
									storedVariables.add(temp);
									store.setClassData(newArray.getBaseClass());
									next = store;
									//no change here because we're pulling from an existing base class object
								}
							}
							else if( node instanceof TACCall ) {
								TACCall call = (TACCall) node;
								call.setGarbageCollected(true); //marks the call as handled
								if( call.hasLocalStore() ) {
									TACLocalStore store = call.getLocalStore();
									if( store.getVariable().needsGarbageCollection( ) && !store.getVariable().isReturn() )
										store.setIncrementReference(false);
								}
								else if( call.hasMemoryStore() ) {
									TACStore store = call.getMemoryStore();
									if( store.getReference().needsGarbageCollection())
										store.setIncrementReference(false);
								}
								else if( !call.isDelegatedCreate() ) {
									//if method return is not saved, save it for ref counting purposes
									//complex case because of possible multiple return values
									//delegated create return values should not be GC'd
									MethodSignature signature = call.getMethodRef().getSignature();
									SequenceType returns = signature.getSignatureWithoutTypeArguments().getFullReturnTypes();
									TACNode anchor = next;
									if( call.getNoExceptionLabel() != null )
										anchor = call.getNoExceptionLabel().getNext();									
									
									if( returns.size() == 1 ) {
										TACVariable temp = addTempLocal(call);								
										//store into temporary for reference count purposes (and change next)
										if( temp.needsGarbageCollection()) {
											TACLocalStore store = new TACLocalStore(anchor, temp, call, false);
											store.setGarbageCollected(true);
											storedVariables.add(temp);											
										}
									}
									else if( returns.size() > 1 ) {										
										for( int i = 0; i < signature.getReturnTypes().size(); ++i ) {
											TACVariable temp = addTempLocal(returns.get(i));
											//store into temporary for reference count purposes (and change next)
											if( temp.needsGarbageCollection()) {
												TACLocalStore store = new TACLocalStore(anchor, temp, new TACSequenceElement(anchor, call, i), false);
												store.setGarbageCollected(true);
												storedVariables.add(temp);
											}
										}
									}
								}
							}
						}
						
						node = node.getNext();
					}
				}
				
				start = lastUpdate;
    		}
			
			/*
			
			new TACBranch(root.getLabel(), phi);
			//blocks are all messed up now
			//nodeBlocks.put(cleanupLabel, block);
			
			//loop through and add branches to cleanup block
			TACLabel label = root.getLabel();
			TACNode current = label;
			
			while( current !=  cleanupLabel ) {
				if( current instanceof TACLabel )
					label = (TACLabel)current;
				else if( current instanceof TACResume ) {
					//branch to the cleanup label 
					new TACBranch(current, cleanupLabel);
					TACLabel backLabel = new TACLabel(method);
					backLabel.insertBefore(current);
					phi.addPreviousStore(label, new TACLabelAddress(phi, backLabel, method) );					
				}
				else if( current instanceof TACReturn ) {					
					//increment reference counts on return values so that they don't disappear
					//when the function returns
					TACReturn return_ = (TACReturn) current;
					TACOperand value = return_.getReturnValue();
					if( value instanceof TACSequence ) {
						TACSequence sequence = (TACSequence) value;
						for( TACOperand operand : sequence.getOperands() )
							incrementReference(current, operand);
					}
					else
						incrementReference(current, value);
					new TACBranch(current, cleanupLabel);
					TACLabel backLabel = new TACLabel(method);
					backLabel.insertBefore(current);
					phi.addPreviousStore(label, new TACLabelAddress(phi, backLabel, method) );
				}
				
				current = current.getNext();
			}
			
			
		
		}
		*/
		
		//adding in reference count changes can introduce new variable loads
		//thus, it is necessary to update the variable stores, loads, and phis
		//then, it's necessary to propagate constants again
		//it can also add new labels, requiring a label rebuild
		/*
		nodeBlocks.clear();
		createBlocks(method, false);
		addEdges();		
		addPhiNodes();
		propagateConstants();
		*/
	}
    
    void checkStoreAgainstParameters(TACLocalStore newStore, Map<TACVariable, TACLocalStore> parameterStores) {
    	
    	TACVariable variable = newStore.getVariable();
    	TACLocalStore parameterStore = parameterStores.get(variable);
    	if( parameterStore != null ) {
    		//since parameter will actually be changed, it needs an increased reference count
    		//otherwise, it might drop to zero even though it is being used in methods that
    		//are currently on the stack
    		//put the reference increment right after the initial store 
    		new TACChangeReferenceCount(parameterStore.getNext(), variable, true);
    		
    		//then, remove the parameter from the list of parameters that are not later overwritten
    		parameterStores.remove(variable);    		
    	}   	
    }
    
    void addGarbageCollection() {
    	
    	//only stored variables needed to have their reference counts decremented at the end of the method
    	Set<TACVariable> storedVariables = new HashSet<TACVariable>();
    	
	    TACNode start = getNode();
	    
	    //keep track of all the initial parameter stores
	    //if a method parameter is never stored again (which is the typical case), 
	    //then we will neither need to increment it nor clean it up
	    Map<TACVariable, TACLocalStore> parameterStores = new HashMap<TACVariable, TACLocalStore>();
		
		//fix this!  Run stores in a separate loop, after arrays and calls?
		boolean changed = true;
		while( changed ) {
			
			changed = false;
			
			TACNode node = start.getNext();
			while( node != start ) {
				TACNode next = node.getNext();			
				//Update changed only if newly garbage collected
				//and creating new things that might need to be garbage collected
				
				if( !node.isGarbageCollected() ) { //if already GC, skip it
					if( node instanceof TACLocalStore ) {
						TACLocalStore store = (TACLocalStore) node;
						TACVariable variable = store.getVariable();
						//type that needs garbage collection will be stored in alloc'ed variable
						if( variable.needsGarbageCollection()  ) {
							store.setGarbageCollected(true);							
							
							//this optimization works because the initial parameter stores come first in a method
							if( store.getValue() instanceof TACParameter )
								parameterStores.put(variable, store);
							else {
								storedVariables.add(variable);
								checkStoreAgainstParameters(store, parameterStores);
								
								if( variable.getType() instanceof ArrayType && store.getClassData() == null ) {
									ArrayType arrayType = (ArrayType) variable.getType();
									TACClass classData = new TACClass(node, arrayType.getBaseType());
									store.setClassData(classData.getClassData());
									
									changed = true;
								}
							}
						}				
					}
					else if( node instanceof TACLocalLoad ) {
						TACLocalLoad load = (TACLocalLoad)node;
						TACVariable variable = load.getVariable();
						if( variable.needsGarbageCollection() )
							load.setGarbageCollected(true);
					}
					else if( node instanceof TACPhi ) {
						TACPhi phi = (TACPhi)node;
						TACVariable variable = phi.getVariable();
						if( variable.needsGarbageCollection() )
							phi.setGarbageCollected(true);												
					}
					else if( node instanceof TACStore ) {
						TACStore store = (TACStore) node;
						TACReference reference = store.getReference();
											
						if( reference.needsGarbageCollection() ) {
							store.setGarbageCollected(true);
							if( reference.getType() instanceof ArrayType && store.getClassData() == null ) {
								ArrayType arrayType = (ArrayType) reference.getType();
								TACClass classData = new TACClass(node, arrayType.getBaseType());
								store.setClassData(classData.getClassData());
								
								changed = true;
							}
						}
					}											
					else if( node instanceof TACNewArray ) {
						TACNewArray newArray = (TACNewArray) node;
						if( newArray.hasLocalStore() ) {
							TACLocalStore store = newArray.getLocalStore();
							//most local stores should not be incremented, since they are returned with an existing increment
							//however, values that are going to be returned do need to be incremented, to survive the end of method decrement
							if( store.getVariable().needsGarbageCollection( ) && !store.getVariable().isReturn()  )
								store.setIncrementReference(false);
						}
						else if( newArray.hasMemoryStore() )  {
							TACStore store = newArray.getMemoryStore();
							if( store.getReference().needsGarbageCollection())
								store.setIncrementReference(false);
						}
						else {					
							TACVariable temp = addTempLocal(newArray);							
							//store into temporary for reference count purposes (and change next)
							TACLocalStore store = new TACLocalStore(next, temp, (TACNewArray)node, false);
							store.setGarbageCollected(true);
							storedVariables.add(temp);
							store.setClassData(newArray.getBaseClass());
							next = store;
							//no change here because we're pulling from an existing base class object
						}
					}
					else if( node instanceof TACCall ) {
						TACCall call = (TACCall) node;
						call.setGarbageCollected(true);  //marks the call as handled
						if( call.hasLocalStore() ) {
							TACLocalStore store = call.getLocalStore();
							//most local stores should not be incremented, since they are returned with an existing increment
							//however, values that are going to be returned do need to be incremented, to survive the end of method decrement
							if( store.getVariable().needsGarbageCollection( ) && !store.getVariable().isReturn() )
								store.setIncrementReference(false);
						}
						else if( call.hasMemoryStore() ) {
							TACStore store = call.getMemoryStore();
							if( store.getReference().needsGarbageCollection())
								store.setIncrementReference(false);
						}
						else if( !call.isDelegatedCreate() ) {
							//if method return is not saved, save it for ref counting purposes
							//complex case because of possible multiple return values
							//delegated create return values should not be GC'd
							MethodSignature signature = call.getMethodRef().getSignature();
							SequenceType returns = signature.getFullReturnTypes();
							//SequenceType returns = signature.getReturnTypes();
							TACNode anchor = next;
							if( call.getNoExceptionLabel() != null )
								anchor = call.getNoExceptionLabel().getNext();
							
							if( returns.size() == 1 ) {
								TACVariable temp = addTempLocal(call);								
								//store into temporary for reference count purposes (and change next)
								if( temp.needsGarbageCollection())									
									new TACLocalStore(anchor, temp, call, false);								
							}
							else if( returns.size() > 1 ) {								
								for( int i = 0; i < signature.getReturnTypes().size(); ++i ) {
									TACVariable temp = addTempLocal(returns.get(i));
									//store into temporary for reference count purposes (and change next)
									if( temp.needsGarbageCollection())
										new TACLocalStore(anchor, temp, new TACSequenceElement(anchor, call, i), false);
								}
							}
						}
					}
				}
				
				node = node.getNext();
			}
		}
		if( garbageCollected )
			addCleanup(storedVariables);
	}
    
	
	public TACMethod addParameters(TACNode node)
	{
		return addParameters(node, false);
	}

	public MethodSignature getSignature()
	{
		return signature;
	}

	public Collection<TACVariable> getLocals()
	{
		return locals.values();
	}
	public Collection<TACVariable> getParameters()
	{
		return parameters.values();
	}
	public TACVariable getThis()
	{
		return locals.get("this");
	}
	public boolean hasParameter(String name)
	{
		return parameters.containsKey(name);
	}
	public TACVariable getParameter(String name)
	{
		return parameters.get(name);
	}

	public void enterScope()
	{
		scopes.push(new HashMap<String, TACVariable>());
	}
	
	private TACVariable addParameter(ModifiedType type, String name)
	{
		TACVariable variable = addLocal(type, name);
		parameters.put(name, variable);
		
		return variable;		
	}
	
	public TACVariable addLocal(ModifiedType type, String name)
	{
		TACVariable variable = new TACVariable(type, name, this);
		Type varType =  variable.getType();
		
		
		while (locals.containsKey(variable.getName()))
			variable.rename();
		locals.put(variable.getName(), variable);
		scopes.peek().put(name, variable);
		return variable;
	}
	public TACVariable addTempLocal(ModifiedType type)
	{
		return addLocal(type, "_temp");
	}
	public TACVariable getLocal(String name)
	{
		TACVariable result = null;
		Iterator<Map<String, TACVariable>> iterator = scopes.iterator();
		while (result == null && iterator.hasNext())
			result = iterator.next().get(name);
		return result;
	}
	public void exitScope()
	{
		scopes.pop();
		if (scopes.isEmpty())
			throw new NoSuchElementException();
	}

	public void setHasLandingpad()
	{
		landingpad = true;
	}
	public boolean hasLandingpad()
	{
		return landingpad;
	}

	@Override
	public String toString()
	{
		StringWriter writer = new StringWriter();
		try
		{
			new TextOutput(writer).build(this, null);
		}
		catch (ShadowException ex)
		{
			return "Error";
		}
		return writer.toString();
	}
	
	public int incrementLabelCounter()
	{
		return labelCounter++;
	}
	
	public int incrementVariableCounter()
	{
		return variableCounter++;
	}

	//add allocations of garbage collected variables
	public void addAllocations() {
		Set<TACVariable> usedLocals = new HashSet<TACVariable>();
		Set<TACChangeReferenceCount> referenceCountChanges = new HashSet<TACChangeReferenceCount>();
		TACNode start = this.node;
		TACNode node = start;
		
		do {
			if( node instanceof TACLocalLoad ) {
				TACLocalLoad load = (TACLocalLoad)node;
				if( load.getVariable().needsGarbageCollection() )
					usedLocals.add(load.getVariable());
			}
			else if( node instanceof TACLocalStore ) {
				TACLocalStore store = (TACLocalStore)node;
				if( store.getVariable().needsGarbageCollection() )
					usedLocals.add(store.getVariable());
			}
			else if( node instanceof TACChangeReferenceCount ) {
				TACChangeReferenceCount change = (TACChangeReferenceCount) node;
				if( !change.isField() )
					referenceCountChanges.add(change);
			}
			node = node.getNext();
		} while( node != start );
		
		TACNode anchor = this.node.getNext();  //first is a label, then all the allocations
		
		for( TACVariable variable : usedLocals )
			new TACAllocateVariable(anchor, variable);
		
		//remove reference count changes for unused variables
		for( TACChangeReferenceCount change : referenceCountChanges ) {
			if( !usedLocals.contains(change.getVariable()) )
				change.remove(); //takes node out of linked list
		}		
	}

	//For purposes of detecting variables that are not initialized
	//the compiler stores undefined values into local variables
	//these cause problems for garbage collection, since it tries
	//to increment the reference count on undef, crashing the program.
	//This method removes all undefined stores.
	public void removeUndefinedStores() {
		TACNode start = this.node;
		TACNode node = start;
		
		do {
			if( node instanceof TACLocalStore ) {
				TACLocalStore store = (TACLocalStore)node;
				if( store.getValue() instanceof TACLiteral ) {
					TACLiteral literal = (TACLiteral) store.getValue();
					if( literal.getValue() instanceof ShadowUndefined )
						node = node.remove();
				}
			}
			node = node.getNext();
		} while( node != start );
		
	}
}
