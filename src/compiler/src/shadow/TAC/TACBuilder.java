package shadow.TAC;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker;
import shadow.AST.AbstractASTVisitor;
import shadow.AST.ASTWalker.WalkType;
import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.MethodSignature;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.Type;

public class TACBuilder extends AbstractASTVisitor {
	private boolean debug;
	
	private LinkedList<TACClass> classes;
	
	public TACBuilder() {
		classes = new LinkedList<TACClass>();
	}
	
	public void build(Node node) throws ShadowException {
		ASTWalker walker = new ASTWalker(this);
		
		walker.walk(node);		
	}
	
	public LinkedList<TACClass> getClasses() {
		return classes;
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {
		TACClass curClass = new TACClass(node.getImage());	// the symbol is prob stored elsewhere in a complete form
		ClassType type = (ClassType)node.getType();
		Map<String, Type> fields = type.getFields();
		TACNode initEntry = null;
		TACNode initExit = null;
		
		// go through and make all of the fields
		for(Map.Entry<String, Type> f:fields.entrySet()) {
			ASTUtils.DEBUG("FIELD: " + f.getKey());
			curClass.addField(new TACVariable(f.getKey(), f.getValue()));
			
			// we need to create an init method for any fields
			AST2TAC a2t = new AST2TAC(f.getValue().getASTNode());
			
			a2t.convert();
			
			if(initEntry == null) {
				initEntry = a2t.getEntry();
			} else { // we already have an init, so add this to the end
				initExit.setNext(a2t.getEntry());
			}
			
			// update the exit node
			initExit = a2t.getExit();
		}
		
		// go through and add all the methods
		for(Map.Entry<String, List<MethodSignature>> m:type.getMethodMap().entrySet()) {
			ASTUtils.DEBUG("METHOD: " + m.getKey());
			
			for(MethodSignature ms:m.getValue()) {
				// the constructor here actually converts from AST -> TAC
				curClass.addMethod(new TACMethod(ms.getMangledName(), ms.getASTNode()));
			}
		}
		
		classes.add(curClass);
		
		ASTUtils.DEBUG("Added new class");
		
		//
		// it would be nice to say NO_CHILDREN here, but we have to deal with inner classes
		//
		return WalkType.PRE_CHILDREN;
	}


}
