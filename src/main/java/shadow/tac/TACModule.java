package shadow.tac;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import shadow.output.text.TextOutput;
import shadow.parser.javacc.ShadowException;
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
                // if we have a class, the set all the field types
                fields.put(field.getKey(), field.getValue().getType());
        }        
        
        /* //shouldn't be necessary with inner and outer classes now in the same LLVM
        Type outer = moduleType.getOuter();
        
        while( outer != null )
        {
        	add(outer);
        	outer = outer.getOuter();        	
        }
        */
        
    }    

    public Type getType() {
        return type;
    }

    public boolean isClass() {
        return type instanceof ClassType;
    }

    public ClassType getClassType() {
        // ??? would it be wise to call isClass() first and throw some type of exception?
        return (ClassType) type;
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
}
