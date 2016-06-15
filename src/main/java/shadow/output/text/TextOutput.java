package shadow.output.text;

import java.io.Writer;
import java.util.List;
import java.util.Map;

import shadow.output.AbstractOutput;
import shadow.output.Cleanup;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.TACTree;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACLocalLoad;
import shadow.tac.nodes.TACLocalStore;
import shadow.tac.nodes.TACLongToPointer;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACNot;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACParameter;
import shadow.tac.nodes.TACPhiRef;
import shadow.tac.nodes.TACPhiRef.TACPhi;
import shadow.tac.nodes.TACPhiStore;
import shadow.tac.nodes.TACPointerToLong;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSame;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceElement;
import shadow.tac.nodes.TACSimpleNode;
import shadow.tac.nodes.TACSingletonRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACTypeId;
import shadow.tac.nodes.TACUnary;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public class TextOutput extends AbstractOutput
{
	private Inline inline = new Inline();	
	private TACLabelRef block = null;	
	public TextOutput(Writer out) throws ShadowException
	{
		super(out);
	}
	public TextOutput(boolean mode) throws ShadowException
	{
		writer.setLineNumbers(mode);
	}

	@Override
	public void walk(TACTree nodes) throws ShadowException
	{
		try
		{
			super.walk(nodes);
		}
		finally
		{
			Cleanup.getInstance().walk(nodes);
		}
	}
	@Override
	public void walk(TACSimpleNode nodes) throws ShadowException
	{
		try
		{
			super.walk(nodes);
		}
		finally
		{
			Cleanup.getInstance().walk(nodes);
		}
	}

	private void startBlock(TACLabelRef label) throws ShadowException
	{
		if (block != null)
			writer.writeLeft("// Missing terminator!!!");
		block = label;
	}
	private void endBlock(boolean terminate) throws ShadowException
	{
		if (block == null)
			writer.writeLeft("// Missing label!!!");
		if (terminate)
			block = null;
	}

	@Override
	public void startFile(TACModule module) throws ShadowException
	{
		Type type = module.getType();
		StringBuilder sb = new StringBuilder(type.getModifiers().toString()).
				append("class ").append(type.toString(Type.PACKAGES | Type.TYPE_PARAMETERS | Type.PARAMETER_BOUNDS));
		if (type instanceof ClassType)
		{
			ClassType classType = module.getClassType();
			if (classType.getExtendType() != null)
				sb.append(" extends ").
						append(classType.getExtendType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS));
			List<InterfaceType> interfaceTypes = classType.getInterfaces();
			if (!interfaceTypes.isEmpty())
			{
				sb.append(" implements ");
				for (InterfaceType interfaceType : interfaceTypes)
					sb.append(interfaceType.toString(Type.PACKAGES | Type.TYPE_PARAMETERS)).append(", ");
				sb.delete(sb.length() - 2, sb.length());
			}
		}
		writer.write(sb.append(" {").toString());
		writer.indent();
	}

	@Override
	public void endFile(TACModule module) throws ShadowException
	{
		writer.outdent();
		writer.write('}');
	}

	@Override
	public void startMethod(TACMethod method, TACModule module) throws ShadowException {		
		MethodSignature signature = method.getSignature();
		StringBuilder sb = new StringBuilder(
				signature.getModifiers().toString()).
				append(signature.getSymbol()).append('(');
		for (TACVariable param : method.getParameters())
			sb.append(param.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS)).append(' ').
					append(param.getName()).append(", ");
		if (!method.getParameters().isEmpty())
			sb.delete(sb.length() - 2, sb.length());
		sb.append(") => (");
		for (ModifiedType retType : signature.getFullReturnTypes())
			sb.append(retType.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS));
		sb.append(')');
		if (signature.isNative())
			writer.write(sb.append(';').toString());
		else
		{
			writer.write(sb.append(" {").toString());
			writer.indent();
			for (TACVariable param : method.getLocals())
				if (!method.getParameters().contains(param))
					writer.write(param.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS) + ' ' +
							param.getName() + ';');
		}
	}

	@Override
	public void endMethod(TACMethod method, TACModule module) throws ShadowException
	{
		//startBlock(defaultBlock);
		if (!method.getSignature().isNative())
		{
			writer.outdent();
			writer.write('}');
		}
	}

	@Override
	public void visit(TACLabel node) throws ShadowException
	{
		startBlock(node.getRef());
		writer.writeLeft(symbol(node.getRef()) + ':');
	}
	
	protected String symbol(TACLabelRef label) 
	{
		return label.getName();
	}
	
	@Override
	public void visit(TACPhiStore node) throws ShadowException {
		 Map<TACLabel, TACOperand> values = node.getPreviousStores();
		 if( values.size() > 1 ) {
			 StringBuilder sb = new StringBuilder(node.getVariable().toString()).
					 append(" = phi ");			 
			 for( Map.Entry<TACLabel, TACOperand> entry : values.entrySet() ) {
				 inline.visit(sb.append("[ "), entry.getValue());
				 sb.append(", ").append(entry.getKey().getRef()).append(" ],");
			 }
			 writer.write(sb.deleteCharAt(sb.length() - 1).toString());					  
		 }
		 else if( values.size() == 1 )
			 writer.write(inline.visit(new StringBuilder(node.getVariable().toString()).append(" = "), values.values().iterator().next()).
						append(';').toString());
		 else
			 throw new IllegalArgumentException("No nodes stored in phi for " + node.getVariable().toString());			 
	}
	
	@Override
	public void visit(TACLocalStore node) throws ShadowException
	{
		endBlock(false);
		writer.write(inline.visit(new StringBuilder(node.getVariable().toString()).append(" = "), node.getValue()).
				append(';').toString());
	}
	
	
	

	@Override
	public void visit(TACStore node) throws ShadowException
	{
		endBlock(false);
		TACReference reference = node.getReference();
		StringBuilder sb = new StringBuilder();
		
		visitReference(sb, reference);		
		writer.write(inline.visit(sb.append(" = "), node.getValue()).
				append(';').toString());
	}
	

	
	private void visitReference(StringBuilder sb, TACReference reference) throws ShadowException {
		if( reference instanceof TACArrayRef ) {
			TACArrayRef arrayRef = (TACArrayRef) reference;
			inline.visit(inline.visit(sb, arrayRef.getArray()).append('['), arrayRef.getIndex(0));
			for (int i = 1; i < arrayRef.getNumIndices(); i++)
				inline.visit(sb.append(", "), arrayRef.getIndex(i));
			sb.append(']');
		}
		else if( reference instanceof TACFieldRef)
		{
			TACFieldRef fieldRef = (TACFieldRef) reference;
			inline.visit(sb, fieldRef.getPrefix()).append(':').append(fieldRef.getName());
		}
		else if( reference instanceof TACSingletonRef) {
			TACSingletonRef singleton = (TACSingletonRef) reference;			
			sb.append(singleton.getType().toString());			
		}
	}


	@Override
	public void visit(TACBranch node) throws ShadowException
	{		
		endBlock(true);
		StringBuilder sb = new StringBuilder("goto ");
		if (node.isConditional())
			inline.visit(sb, node.getCondition()).append(" ? ").
					append(symbol(node.getTrueLabel())).append(", ").
					append(symbol(node.getFalseLabel()));
		else if (node.isIndirect()) {			
			TACPhi phi = (TACPhi)node.getOperand();
			TACPhiRef phiRef = phi.getRef();
			sb.append("phi ");
			for (int i = 0; i < phiRef.getSize(); i++)
				sb.append("[").append(symbol(phiRef.getValue(i))).append(", ").
						append(symbol(phiRef.getLabel(i))).append("],");
			sb.deleteCharAt(sb.length() - 1).toString();			
		}
		else if (node.isDirect())
			sb.append(symbol(node.getLabel()));
		writer.write(sb.append(';').toString());
	}

	@Override
	public void visit(TACCall node) throws ShadowException
	{
		endBlock(node.getBlock().hasLandingpad()); //if landing pad, 
		if (!node.hasLocalStore() && !node.hasMemoryStore())
			writer.write(inline.visit(new StringBuilder(), node).append(';').
					toString());
	}

	@Override
	public void visit(TACReturn node) throws ShadowException
	{
		endBlock(true);
		StringBuilder sb = new StringBuilder("return");
		if (node.hasReturnValue())
			inline.visit(sb.append(' '), node.getReturnValue());
		writer.write(sb.append(';').toString());
	}

	@Override
	public void visit(TACThrow node) throws ShadowException
	{
		endBlock(true);
		writer.write(inline.visit(new StringBuilder("throw "),
				node.getException()).append(';').toString());
	}
	
	@Override
	public void visit(TACCatch node) throws ShadowException
	{
		endBlock(false);		
		writer.write(new StringBuilder("catch( ").append(node.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS)).append(" )").toString());
	}

	/*
	@Override
	public void visit(TACUnwind node) throws ShadowException
	{
		endBlock(false);
		writer.write("// unwind;");
	}
	*/

	@Override
	public void visit(TACResume node) throws ShadowException
	{
		endBlock(true);
		writer.write("// resume;");
	}

	private class Inline extends TACAbstractVisitor
	{
		private boolean parentheses;
		private StringBuilder sb;
		public StringBuilder visit(StringBuilder builder, TACNode node)
				throws ShadowException
		{
			sb = builder;
			node.accept(this);
			return builder;
		}

		@Override
		public void visit(TACNot node) throws ShadowException
		{
			sb.append('!');
			parentheses = true;
			visit(sb, node.getOperand());
			parentheses = false;
		}

		@Override
		public void visit(TACSame node) throws ShadowException
		{
			boolean paren = parentheses;
			if (paren) sb.append('(');
			parentheses = true;
			visit(sb, node.getFirst()).append(" === ");
			parentheses = true;
			visit(sb, node.getSecond());
			if (paren) sb.append(')');
			parentheses = false;
		}

		@Override
		public void visit(TACUnary node) throws ShadowException
		{
			sb.append(node.getOperation());
			parentheses = true;
			visit(sb, node.getOperand());
			parentheses = false;
		}

		@Override
		public void visit(TACBinary node) throws ShadowException
		{
			boolean paren = parentheses;
			if (paren) sb.append('(');
			parentheses = true;
			visit(sb, node.getFirst()).append(' ').append(node.getOperation()).
					append(' ');
			parentheses = true;
			visit(sb, node.getSecond());
			if (paren) sb.append(')');
			parentheses = false;
		}

		@Override
		public void visit(TACCall node) throws ShadowException
		{
			TACMethodRef method = node.getMethodRef();
			sb.append(method.getOuterType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS)).append(':').
					append(method.getName()).append('(');
			for (TACOperand param : node.getParameters())
				visit(sb, param).append(", ");
			if (node.getNumParameters() != 0)
				sb.delete(sb.length() - 2, sb.length());
			sb.append(')');
			
			if( node.getBlock().hasLandingpad() )
				sb.append(" to label " + symbol(node.getNoExceptionLabel()) + " unwind label " +
						symbol(node.getBlock().getLandingpad()));
		}

		@Override
		public void visit(TACCast node) throws ShadowException
		{
			sb.append("cast<").append(node.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS)).
			append(">(");
			
			TACOperand op = node.getOperand(0);
			while( op instanceof TACCast )		
				op = ((TACCast)op).getOperand(0);			
			
			visit(sb, op).append(')');
		}
		
		@Override
		public void visit(TACLocalLoad node) throws ShadowException
		{
			sb.append(node.getVariable());
		}
		
		@Override
		public void visit(TACLoad node) throws ShadowException
		{			
			TACReference reference = node.getReference();
			visitReference(sb, reference);
		}
		
		/*
		
		@Override
		public void visit(TACPhi node) throws ShadowException {
			TACPhiRef phi = node.getRef();
			sb.append(" = phi *");
			for (int i = 0; i < phi.getSize(); i++)
				sb.append(" [ address(").append(symbol(phi.getValue(i))).append("), ").
						append(symbol(phi.getLabel(i))).append(" ],");
			sb.deleteCharAt(sb.length() - 1).toString();
		}
		*/
		
		@Override
		public void visit(TACSequence node) throws ShadowException
		{
			sb.append('(');
			for (TACOperand operand : node)
				visit(sb, operand).append(", ");
			sb.delete(sb.length() - 2, sb.length()).append(')');
		}

		@Override
		public void visit(TACNewObject node) throws ShadowException
		{
			sb.append(node.getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS)).append(":create");
		}

		@Override
		public void visit(TACNewArray node) throws ShadowException
		{
			sb.append(node.getType().getBaseType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS)).
					append(":create[").append(node.getDimension(0));
			for (int i = 1; i < node.getDimensions(); i++)
				visit(sb.append(", "), node.getDimension(i));
			sb.append(']');
		}
		

		@Override
		public void visit(TACClass node) throws ShadowException
		{
			sb.append(node.getType()).append(":class");
		}
		
		@Override
		public void visit(TACClass.TACClassData node) throws ShadowException
		{
			sb.append(node.getType()).append(":class");
		}
		

		@Override
		public void visit(TACLandingpad node) throws ShadowException
		{
			sb.append("landingpad");		
		}

		@Override
		public void visit(TACTypeId node) throws ShadowException
		{
			visit(sb.append("typeid("), node.getOperand()).append(')');
		}

		/*
		@Override
		public void visit(TACUnwind node) throws ShadowException
		{
			sb.append("_exception");
		}
		*/
		
		@Override
		public void visit(TACSequenceElement node ) throws ShadowException {
			
			TACOperand op = node.getOperand(0);
			
			if( op instanceof TACSequence )
				visit(sb, op.getOperand(node.getIndex()));			
			else {				
				visit(sb.append("extract("), op);
				sb.append(", " + node.getIndex() + ")");
			}
		}
		
		@Override
		public void visit(TACClass.TACMethodTable node) throws ShadowException {
			Type type = node.getClassType().getTypeWithoutTypeArguments();
			if( type instanceof ArrayType )
				type = ((ArrayType)type).recursivelyGetBaseType();
				
			if( type instanceof InterfaceType )
				sb.append("null");
			else			
				sb.append("methodtable(" + type + ")");
		}
		

		@Override
		public void visit(TACLiteral node) throws ShadowException
		{
			sb.append(node);
		}
		
		
		@Override
		public void visit(TACPointerToLong node) throws ShadowException
		{
			visit(sb.append("long("), node.getOperand(0));
			sb.append(")");
		}
		
		@Override
		public void visit(TACLongToPointer node) throws ShadowException
		{
			visit(sb.append("pointer("), node.getOperand(0));
			sb.append(")");
		}
		
		
		@Override
		public void visit(TACParameter node) throws ShadowException
		{
			sb.append(node);
		}
	}
	

	
}
