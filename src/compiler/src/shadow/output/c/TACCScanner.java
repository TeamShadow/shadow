package shadow.output.c;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import shadow.tac.AbstractTACVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACPhiBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACComparison;
import shadow.tac.nodes.TACGetLength;
import shadow.tac.nodes.TACIndexed;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariable;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public class TACCScanner extends AbstractTACVisitor
{
	private int tempCounter, labelCounter;
	private Map<String, String> localRenames;
	private Map<String, Type> allocations;
	private Set<String> stringLiterals;
	public TACCScanner(TACModule module)
	{
		super(module);
	}
	private void allocate(TACNode node)
	{
		if (node.getSymbol() == null)
		{
			String symbol = "_Itemp" + tempCounter++;
			node.setSymbol(symbol);
			allocations.put(symbol, node.getType());
		}
	}
	
	@Override
	public void startFile() { }
	@Override
	public void endFile() { }

	@Override
	public void startFields()
	{
		tempCounter = labelCounter = 0;
		allocations = new HashMap<String, Type>();
		stringLiterals = new HashSet<String>();
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
		localRenames = new HashMap<String, String>();
		allocations = new HashMap<String, Type>();
		stringLiterals = new HashSet<String>();
	}
	@Override
	public void endMethod(TACMethod method)
	{
		method.setAllocations(allocations);
		allocations = null;
	}
	
	@Override
	public void visit(TACNode node) throws IOException
	{
		super.visit(node);
	}
	
	@Override
	public void visit(TACAllocation node)
	{
		if (!node.isOnHeap() /*FIXME:*/ && localRenames != null)
		{
			String symbol = node.getSymbol();
			if (symbol != null)
			{
				String newSymbol = symbol;
				if (localRenames.containsKey(symbol))
				{
					newSymbol = localRenames.get(symbol);
					do
						newSymbol += '_';
					while (localRenames.containsKey(newSymbol));
				}
				localRenames.put(symbol, newSymbol);
				localRenames.put(newSymbol, newSymbol);
				node.setSymbol(newSymbol);
			}
		}
		allocate(node);
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
		allocate(node);
	}

	@Override
	public void visit(TACBinary node)
	{
		allocate(node);
	}
	@Override
	public void visit(TACComparison node)
	{
		allocate(node);
	}

	@Override
	public void visit(TACLabel node)
	{
		node.setSymbol("label" + labelCounter++);
	}

	@Override
	public void visit(TACBranch node)
	{
	}

	@Override
	public void visit(TACCall node)
	{
		if (!node.getMethodType().getReturnTypes().isEmpty() && node.getSymbol() == null)
		{
			int index = 0;
			for (ModifiedType type : node.getType())
			{
				node.setSymbol(index, "_Itemp" + tempCounter++);
				allocations.put(node.getSymbol(index++), type.getType());
			}
		}
	}

	@Override
	public void visit(TACCast node)
	{
		allocate(node);
	}

	@Override
	public void visit(TACLiteral node)
	{
		if (node.getValue() instanceof String)
			stringLiterals.add((String)node.getValue());
	}

	@Override
	public void visit(TACPhi node)
	{
		allocate(node); // note that this is not an ssa variable (in c)
	}

	@Override
	public void visit(TACPhiBranch node)
	{
	}
	
	@Override
	public void visit(TACReference node)
	{
	}
	
	@Override
	public void visit(TACVariable node)
	{
		allocate(node); // note that this is not an ssa variable (in c)
		if (!node.expectsPrefix() &&
				localRenames.containsKey(node.getSymbol()))
			node.setSymbol(localRenames.get(node.getSymbol()));
	}
	
	@Override
	public void visit(TACGetLength node)
	{
	}
	
	@Override
	public void visit(TACIndexed node)
	{
//		allocate(node);
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
