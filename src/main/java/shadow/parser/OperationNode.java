package shadow.parser;

import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.ShadowParser;
import shadow.typecheck.type.MethodSignature;

public class OperationNode extends SimpleNode {

	public OperationNode(int id) {
    	super(id);
    }

    public OperationNode(ShadowParser sp, int id) {
    	super(sp, id);
    }

	private List<MethodSignature> operations = new ArrayList<MethodSignature>();

	public List<MethodSignature> getOperations()
	{
		return operations;
	}

	public void addOperation(MethodSignature signature)
	{
		operations.add(signature);
	}
}
