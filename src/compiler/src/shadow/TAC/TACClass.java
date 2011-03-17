package shadow.TAC;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import shadow.TAC.nodes.TACAllocation;
import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.Node;
import shadow.typecheck.MethodSignature;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;

public class TACClass {

	private LinkedList<TACNode[]> fields;	/** Holds the entry & exit nodes for fields */
	private LinkedList<TACMethod> methods;
	private String className;
	private ClassType type;
	
	public TACClass(String name, ClassType type) {
		this.fields = new LinkedList<TACNode[]>();
		this.methods = new LinkedList<TACMethod>();
		this.className = name;
		this.type = type;
	}
	
	/**
	 * Generates an init method for all of the fields and adds it to the method list
	 * @param node The node for the class
	 */
	public void generateInitMethod(Node node) {
		TACNode entry = null, exit = null;

		//
		// This could all be cleaned up:
		// - keep one list that only contains the allocations for the fields
		// - have an entry & exit node for the __init method and simply link in the fields as they're added
		//
		
		// go through and link all of the field TAC paths together
		for(TACNode[] field:fields) {
			if(field[0] == field[1])
				continue;	// just an allocation
			
			TACNode trueEntry = field[0].getNext();
			
			if(trueEntry instanceof TACAllocation)
				trueEntry = trueEntry.getNext();
			
			trueEntry.getParent().setNext(null);	// we want to unlink the allocation

			if(entry == null) {
				entry = trueEntry;
				exit = field[1];
			} else {
				exit.setNext(trueEntry);
				trueEntry.setParent(exit);
				exit = field[1];
			}
		}
		
		// create the method signature for this method
		MethodSignature ms = new MethodSignature("__init", 0, node);
		
		// add the init method
		methods.add(new TACMethod(ms, entry, exit));
	}
	
	public void addField(TACNode fieldEntry, TACNode fieldExit) {
		fields.add(new TACNode[] { fieldEntry, fieldExit} );
	}
	
	public LinkedList<TACNode[]> getFields() {
		return fields;
	}
	
	public void addMethod(TACMethod method) {
		methods.add(method);
	}
	
	public LinkedList<TACMethod> getMethods() {
		return methods;
	}
	
	public String getName() {
		return className;
	}
	
	public String getExtendClassName() {
		return type.getExtendType().getTypeName();
	}
	
	public List<String> getImplementsClassNames() {
		List<InterfaceType> interfaces = type.getInterfaces();
		List<String> names = new ArrayList<String>(interfaces.size()); 
		
		for(InterfaceType it:interfaces) {
			names.add(it.getTypeName());
		}
		
		return names;
	}
	
	public void dump() {
		System.out.println("CLASS: " + className);
		
		for(TACMethod m:methods) {
			System.out.println("METHOD: " + m.getName());
			m.getEntry().dump(" ");
		}
	}
}
