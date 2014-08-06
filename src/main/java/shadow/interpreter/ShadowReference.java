package shadow.interpreter;

import shadow.parser.ShadowException;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

/**
 * A reference to an object in Shadow.
 */
public class ShadowReference implements ModifiedType {

    private ModifiedType type; /** The type of the reference **/
    private ShadowValue value; /** The value of the reference **/

    public ShadowReference(ModifiedType type) throws ShadowException {
        this.type = type;
        this.value = ShadowValue.getDefault(type);
    }

    @Override
    public Modifiers getModifiers() {
        return this.type.getModifiers();
    }

    @Override
    public Type getType() {
        return this.type.getType();
    }

    @Override
    public void setType(Type type) {
        this.type.setType(type);
    }

    public ShadowValue getValue() {
        return this.value;
    }

    public void setValue(ShadowValue value) throws ShadowException {
        if (value.getModifiers().isNullable() && !getModifiers().isNullable())
            throw new ShadowException("Cannot store a nullable value to a non-nullable reference");
        if (value.getModifiers().isReadonly() && !getModifiers().isReadonly())
            throw new ShadowException("Cannot store a readonly value to a non-readonly reference");
        if (value.getModifiers().isImmutable() != getModifiers().isImmutable()) {
            if (getModifiers().isImmutable())
                throw new ShadowException("Cannot store a non-immutable value to an immutable reference");
            else if (!getModifiers().isReadonly())
                throw new ShadowException("Cannot store an immutable value to a non-readonly non-immutable reference");
        }
        if (!value.getType().isSubtype(getType()))
            throw new ShadowException("Cannot store a value of type " + value.getType() + " to a reference of type " + getType());
        this.value = value;
    }
}
