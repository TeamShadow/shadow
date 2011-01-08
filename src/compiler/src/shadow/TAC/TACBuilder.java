package shadow.TAC;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.MethodSignature;
import shadow.typecheck.type.ClassType;

/*
 * This class is a total hack right now
 */
public class TACBuilder extends AbstractASTVisitor {
	private boolean debug;
	private LinkedList<TACClass> classes;
	
	// debug/hack for now
	public LinkedList<TACMethod> methods;
	
	public TACBuilder() {
		classes = new LinkedList<TACClass>();
		methods = new LinkedList<TACMethod>();
	}
	
	public void build(Node node) throws ShadowException {
		ASTWalker walker = new ASTWalker(this);
		
		walker.walk(node);		
	}
	
	public LinkedList<TACClass> getClasses() {
		return classes;
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {
		ClassType type = (ClassType)node.getType();

		// go through the methods
		for(Map.Entry<String, List<MethodSignature>> m:type.getMethodMap().entrySet()) {
			ASTUtils.DEBUG("METHOD: " + m.getKey());
			
			for(MethodSignature ms:m.getValue()) {
				ASTUtils.DEBUG(ms.getASTNode(), "ROOT NODE");
				
				ASTMethodToTAC ast2TAC = new ASTMethodToTAC(ms.getASTNode());
				
				ast2TAC.convert();	// actually do the conversion
				
				ASTUtils.DEBUG(ms.getASTNode(), "ENTRY NODE: " + ((SimpleNode)ms.getASTNode()).getEntryNode());
				
				methods.add(new TACMethod(ms.getMangledName(), ms.getASTNode()));
			}
		}
		
		return WalkType.PRE_CHILDREN;
	}


}
