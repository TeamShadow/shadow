package shadow.output.c;

import java.util.HashMap;
import java.util.Map;

import shadow.tac.AbstractTACVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACComparison;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACPhiBranch;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariable;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public class TACCScanner extends AbstractTACVisitor
{
	@Override
	public void startFile() { }
	@Override
	public void endFile() { }

	private int tempCounter, labelCounter;
	private Map<String, Type> allocations;
	public TACCScanner(TACModule theClass)
	{
		super(theClass);
	}
	private void allocate(TACNode node)
	{
		if (node.getSymbol() == null)
		{
			String symbol = "_Itemp" + tempCounter++;
			node.setSymbol("_Itemp" + tempCounter++);
			allocations.put(node.getSymbol(), node.getType());
		}
	}

	@Override
	public void startFields()
	{
		tempCounter = labelCounter = 0;
		allocations = new HashMap<String, Type>();
	}
	@Override
	public void endFields()
	{
		allocations = null;
	}
	
	@Override
	public void startMethod(TACMethod method)
	{
		tempCounter = labelCounter = 0;
		allocations = new HashMap<String, Type>();
	}
	@Override
	public void endMethod(TACMethod method)
	{
		method.setAllocations(allocations);
		allocations = null;
	}
	
	@Override
	public void visit(TACNode node)
	{
		super.visit(node);
	}
	
	@Override
	public void visit(TACAllocation node)
	{
		if (node.getSymbol() == null)
		{
			node.setSymbol("_Itemp" + tempCounter++);
			allocations.put(node.getSymbol(), node.getType());
		}
	}

	@Override
	public void visit(TACAssign node)
	{
//		TACNode first = node.getFirstOperand();
//		String symbol = first.getSymbol();
//		if (symbol != null)
//			if (!allocations.containsKey(symbol))
//				allocations.put(symbol, first.getType());
	}

	@Override
	public void visit(TACUnary node)
	{
		if (node.getSymbol() == null)
		{
			node.setSymbol("_Itemp" + tempCounter++);
			allocations.put(node.getSymbol(), node.getType());
		}
	}

	@Override
	public void visit(TACBinary node)
	{
		if (node.getSymbol() == null)
		{
			node.setSymbol("_Itemp" + tempCounter++);
			allocations.put(node.getSymbol(), node.getType());
		}
	}
	@Override
	public void visit(TACComparison node)
	{
		if (node.getSymbol() == null)
		{
			node.setSymbol("_Itemp" + tempCounter++);
			allocations.put(node.getSymbol(), node.getType());
		}
	}

	@Override
	public void visit(TACBranch node)
	{
		TACNode trueBranch = node.getTrueBranch(),
				falseBranch = node.getFalseBranch();
		if (trueBranch != null)
			if (trueBranch.getLabel() == null)
				trueBranch.setLabel("label" + labelCounter++);
		if (falseBranch != null)
			if (falseBranch.getLabel() == null)
				falseBranch.setLabel("label" + labelCounter++);
	}

	@Override
	public void visit(TACLabel node)
	{
		TACNode trueBranch = node.getTrueBranch(),
				falseBranch = node.getFalseBranch();
		if (trueBranch != null)
			if (trueBranch.getLabel() == null)
				trueBranch.setLabel("label" + labelCounter++);
		if (falseBranch != null)
			if (falseBranch.getLabel() == null)
				falseBranch.setLabel("label" + labelCounter++);
	}

	@Override
	public void visit(TACCall node)
	{
		if (!node.getSignature().getMethodType().getReturnTypes().isEmpty() && node.getSymbol() == null)
		{
			int index = 0;
			for (ModifiedType type : node.getSequenceType().getTypes())
			{
				node.setSymbol(index, "_Itemp" + tempCounter++);
				allocations.put(node.getSymbol(index++), type.getType());
			}
		}
	}

	@Override
	public void visit(TACCast node)
	{
		if (node.getSymbol() == null)
		{
			node.setSymbol("_Itemp" + tempCounter++);
			allocations.put(node.getSymbol(), node.getType());
		}
	}

	@Override
	public void visit(TACLiteral node)
	{
	}

	@Override
	public void visit(TACPhi node)
	{
		if (node.getSymbol() == null)
		{
			node.setSymbol("_Itemp" + tempCounter++);
			allocations.put(node.getSymbol(), node.getType());
		}
	}

	@Override
	public void visit(TACPhiBranch node)
	{
		TACPhi phi = node.getPhi();
		if (phi.getLabel() == null)
			phi.setLabel("label" + labelCounter++);
	}
	
	@Override
	public void visit(TACReference node)
	{
	}

	@Override
	public void visit(TACVariable node)
	{
	}

	@Override
	public void visit(TACReturn node)
	{
	}

	@Override
	public void visit(TACSequence node)
	{
	}
}
