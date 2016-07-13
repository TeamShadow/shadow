package shadow.tac.nodes;

import java.util.Iterator;
import java.util.NoSuchElementException;

import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACBlock;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

/**
 * An abstract class that is the base of all TAC operations.
 *
 * Each TACNode is part of a circular doubly-linked list.
 *
 * @author Jacob Young
 */
public abstract class TACNode implements Iterable<TACOperand> {
    private TACNode prev, next; // the next and prev nodes in our circular linked list
    private Node ASTNode; // associated AST node, used for error messages
    private TACBlock block; //which block the node is in

    /**
     * Constructor adds current node *before* parameter node
     *
     * @param node
     */
    protected TACNode(TACNode node) {        
        insertBefore(node);        
    }
    
    public TACBlock getBlock()
    {
    	return block;
    }

    public TACMethod getMethod()
    {		
		return block.getMethod();
	}
    
    public void setBlock(TACBlock block)
    {		
		this.block = block;
	}

	public final TACNode getPrevious() 
	{
        return prev;
    }

    public final TACNode getNext()
    {
        return next;
    }

    /**
     * Inserts this node after given node: node <-> this
     *
     * @param node the node to insert this node after
     */
    public void insertAfter(final TACNode node) {
        if (node == this)
            return;
        
        if( node != null ) {
        	ASTNode = node.getASTNode();
        	block = node.getBlock();        	
        }

        remove();

        if (node != null)
            connect(node, this, node.getNext());
    }

    public void insertBefore(final TACNode node) {
        if (node == this)
            return;
        
        if( node != null ) {
        	ASTNode = node.getASTNode();
        	block = node.getBlock();        	
        }
        
        remove();
        if (node != null)
            connect(node.getPrevious(), this, node);
    }

    /**
     * Removes this node from the circular linked-list.
     */
    public void remove() {
        connect(prev, next);        
        this.prev = this.next = this;
    }  

    protected final void connect(TACNode first, TACNode second, TACNode third) {
        connect(first, second, second, third);
    }

    protected final void connect(TACNode first, TACNode second, TACNode third, TACNode fourth) {
        connect(first, second);
        connect(third, fourth);
    }
    
    /**
     * Connects two nodes together: first <-> second
     * @param first the first node to connect.
     * @param second the second node to connect.
     */
    protected final void connect(TACNode first, TACNode second) {
    	if( first != null )
    		first.next = second;
    	if( second != null )
    		second.prev = first;
    }
    
    public Node getASTNode()
    {
    	return ASTNode;
    }
    
    public void setASTNode(Node node)
    {
    	ASTNode = node;
    }
    
    @Override
    public Iterator<TACOperand> iterator() {
        return new OperandIterator();
    }

    private final class OperandIterator implements Iterator<TACOperand> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index != getNumOperands();
        }

        @Override
        public TACOperand next() {
            if (index == getNumOperands())
                throw new NoSuchElementException();
            return getOperand(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public abstract int getNumOperands();

    public abstract TACOperand getOperand(int num);

    /*
     * protected final TACOperand check(TACOperand operand, ModifiedType type) {
     * return check(operand, type, false); }
     */
    protected final TACOperand check(TACOperand operand, ModifiedType type) {
        if (type.getType() instanceof TypeParameter && !(operand.getType() instanceof TypeParameter)  && !operand.getType().equals(Type.NULL))
            type = new SimpleModifiedType(Type.OBJECT);

        operand = operand.checkVirtual(type, this); // puts in casts where needed
        
        final Type operandType = operand.getType();
        final Type typeType = type.getType();

        if( operandType.equals(typeType) || 
          ( operandType instanceof TypeParameter && typeType instanceof TypeParameter))
            return operand;

        if (operandType instanceof SequenceType &&
               typeType instanceof SequenceType &&
               ((SequenceType) operandType).matches(((SequenceType) typeType))) { // replace with subtype? no!
            return operand;
        } else if ((operandType instanceof SequenceType) != (typeType instanceof SequenceType)) {
            throw new IllegalArgumentException(operandType + " and " + typeType + " are not both sequence types");
        }

        final shadow.typecheck.Package operandPackage = operandType.getPackage();
        final shadow.typecheck.Package typePackage = typeType.getPackage();

        if (operandPackage != null &&
               typePackage != null &&
               operandPackage.equals(typePackage) &&
               operand.getType().getTypeName().equals(type.getType().getTypeName()))
            return operand;

        throw new IllegalArgumentException("Check of operand " + operand + " of type " + operandType + " and " + type + " of type " + typeType + " couldn't be resolved!");
    }
    

    public abstract void accept(TACVisitor visitor) throws ShadowException;
}
