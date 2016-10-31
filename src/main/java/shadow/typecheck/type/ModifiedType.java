package shadow.typecheck.type;

/**
 * Interface for types that have been modified.
 */
public interface ModifiedType {
    /**
     * Returns the underlying Shadow {@link Type}.
     * @return the Shadow type.
     */
    public Type getType();

    /**
     * Sets the underlying Shadow {@link Type}.
     * @param type the Shadow {@link Type} to set.
     */
    public void setType(Type type);

    /**
     * Returns the modifiers for this type.
     * @return the {@link Modifiers} for this type.
     */
    public Modifiers getModifiers();
}
