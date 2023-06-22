package shadow.typecheck.type;

import shadow.interpreter.InterpreterException;

/** Interface for types that have been modified. */
public interface ModifiedType {
  /**
   * Returns the underlying Shadow {@link Type}.
   *
   * @return the Shadow type.
   */
  Type getType();

  /**
   * Sets the underlying Shadow {@link Type}.
   *
   * @param type the Shadow {@link Type} to set.
   */
  void setType(Type type);

  /**
   * Returns the modifiers for this type.
   *
   * @return the {@link Modifiers} for this type.
   */
  Modifiers getModifiers();
}
