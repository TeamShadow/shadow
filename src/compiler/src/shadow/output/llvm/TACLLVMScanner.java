package shadow.output.llvm;

import java.io.IOException;
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
import shadow.tac.nodes.TACGetLength;
import shadow.tac.nodes.TACIndexed;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACPhiBranch;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariable;
import shadow.typecheck.type.Type;

public class TACLLVMScanner extends AbstractTACVisitor
{
	private int tempCounter, labelCounter;
	private Map<String, String> localRenames;
	private Map<String, Type> allocations;
	public TACLLVMScanner(TACModule module)
	{
		super(module);
	}

	private void allocate(TACNode node)
	{
		if (allocations == null)
			return;
		String symbol = node.getSymbol();
		if (symbol == null)
		{
			symbol = "." + tempCounter++;
			node.setSymbol(symbol);
			allocations.put(symbol, node.getType());
		}
		else if (!allocations.containsKey(symbol))
			allocations.put(symbol, node.getType());
	}

	@Override
	public void startFile() throws IOException { }
	@Override
	public void endFile() throws IOException { }

	@Override
	public void startFields() throws IOException { }
	@Override
	public void endFields() throws IOException { }

	@Override
	public void startMethod(TACMethod method)
	{
		tempCounter = method.getParameterTypes().size() + (method.isStatic() ?
				1 : 2);
		labelCounter = -1;
		localRenames = new HashMap<String, String>();
		allocations = new HashMap<String, Type>();
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
		node.setSymbol("label" + (++labelCounter == 0 ? "" : labelCounter));
	}

	@Override
	public void visit(TACBranch node)
	{
	}

	@Override
	public void visit(TACCall node)
	{
		if (!node.getMethodType().getReturnTypes().isEmpty())
			allocate(node);
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
		{
			String string = (String)node.getValue();
			node.setSymbol("@.str" + getModule().addString(string));
		}
	}

	@Override
	public void visit(TACPhi node)
	{
		allocate(node); // note that this is not an SSA variable
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
		if (!"class".equals(node.getSymbol()))
		{
			if (node.getSymbol() == null)
				node.setSymbol("" + tempCounter);
			//allocate(node); // note that this is not an SSA variable
			if (!node.expectsPrefix() &&
					localRenames.containsKey(node.getSymbol()))
				node.setSymbol(localRenames.get(node.getSymbol()));
		}
	}
	
	@Override
	public void visit(TACGetLength node)
	{
		//allocate(node);
	}
	
	@Override
	public void visit(TACIndexed node)
	{
		allocate(node);
	}
	
	@Override
	public void visit(TACReturn node)
	{
		tempCounter++; // creates an unnamed label
	}
	
	@Override
	public void visit(TACSequence node)
	{
	}
}
