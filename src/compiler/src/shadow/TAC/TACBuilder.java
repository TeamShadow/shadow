package shadow.TAC;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker;
import shadow.AST.AbstractASTVisitor;
import shadow.AST.ASTWalker.WalkType;
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
		
		// go through and make all of the fields
		for(Map.Entry<String, Type> f:fields.entrySet())
			curClass.addField(new TACVariable(f.getKey(), f.getValue()));
		
		// go through and add all the methods
		for(Map.Entry<String, List<MethodSignature>> m:type.getMethodMap().entrySet()) {
			for(MethodSignature ms:m.getValue()) {
				curClass.addMethod(new TACMethod(m.getKey(), ms));
			}
		}
		
		classes.add(curClass);
		
		ASTUtils.DEBUG("Added new class");
		
		return WalkType.PRE_CHILDREN;	// don't want to visit again
	}


}
