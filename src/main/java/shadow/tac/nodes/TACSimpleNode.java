package shadow.tac.nodes;

import java.util.Iterator;
import java.util.NoSuchElementException;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

/**
 * Extends {@link TACNode} by adding iteration over the {@link TACOperand} in the list.
 */
// ??? why do we iterate over TACOperand and not simply TACNode?
public abstract class TACSimpleNode extends TACNode implements Iterable<TACOperand> {
    protected TACSimpleNode(TACNode node) {
        super(node);
    }

    @Override
    public final Iterator<TACOperand> iterator() {
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
        if (type instanceof TACReference)
            type = ((TACReference) type).getGetType();
        
        if (type.getType() instanceof TypeParameter && !(operand.getType() instanceof TypeParameter))
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
}
