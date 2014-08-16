package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACBuilder;
import shadow.tac.TACVisitor;

/**
 * An abstract class that is the base of all TAC operations.
 *
 * Each TACNode is part of a circular doubly-linked list.
 *
 * @author Jacob Young
 */
public abstract class TACNode {
    private TACNode prev, next; // the next and prev nodes in our circular linked list

    // ??? why do we need a reference to the builder here?
    // shouldn't we just make the builder a Singleton?
    private static TACBuilder builder;

    public static TACBuilder getBuilder() {
        return builder;
    }

    public static void setBuilder(TACBuilder builder) {
        TACNode.builder = builder;
    }

    protected TACNode() {
        this(null);
    }

    /**
     * Constructor adds current node *before* parameter node
     *
     * @param node
     */
    protected TACNode(TACNode node) {
        clear();
        insertBefore(node);
    }

    public final TACNode getPrevious() {
        return prev;
    }

    public final TACNode getNext() {
        return next;
    }

    /**
     * Puts input node after current node
     *
     * @param node
     */

    public void append(TACNode node) {
        node.insertAfter(this);
    }

    /**
     * Puts input node before current node
     *
     * @param node
     */
    public void prepend(TACNode node) {
        node.insertBefore(this);
    }

    public void replaceWith(TACNode node) {
        if (node == this)
            return;
        node.insertBefore(this);
        remove();
    }

    public void replace(TACNode node) {
        if (node == this)
            return;
        insertBefore(node);
        node.remove();
    }

    /**
     * Inserts this node after given node: node <-> this
     *
     * @param node the node to insert this node after
     */
    public void insertAfter(final TACNode node) {
        if (node == this)
            return;

        remove();

        if (node != null)
            connect(node, this, node.getNext());
    }

    public void insertBefore(final TACNode node) {
        if (node == this)
            return;
        remove();
        if (node != null)
            connect(node.getPrevious(), this, node);
    }

    /**
     * Removes this node from the circular linked-list.
     */
    public void remove() {
        connect(prev, next);

        // clear(); // ??? this seems more clear, thoughts?
        this.prev = this.next = null;
    }

    /**
     * Clears the circular linked-list by making this node the only node in the list.
     */
    protected final void clear() {
        connect(this, this);
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
        first.next = second;
        second.prev = first;
    }

    /**
     * Connects an arbitrary list of TACNodes together.
     * @param nodes the nodes, in order, to connect.
     */
/*
    protected final void connect(final TACNode... nodes) {
        for(int i=0; i < nodes.length-1; ++i) {
            connect(nodes[i], nodes[i+1]);
        }
    }
*/

    public abstract void accept(TACVisitor visitor) throws ShadowException;
}
