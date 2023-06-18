package shadow.typecheck.type;

import shadow.doctool.Documentation;
import shadow.parse.ShadowParser;

import java.io.PrintWriter;
import java.util.*;

/**
 * Type representing an attribute in Shadow. Attributes are much simpler than classes: they have no
 * modifiers, do not support inheritance, and can only contain top-level constant fields.
 *
 * <p>Example definition:
 *
 * <pre><code>
 * attribute ExampleAttribute {
 *     // All fields are implicitly constant
 *     String name; // Must be provided when invoking this attribute
 *     int value = 1; // Can optionally be provided (and overwritten)
 * }
 * </code></pre>
 *
 * Usage:
 *
 * <pre><code>
 * [ExampleAttribute(name = "test")] // value = 1 by default
 * private myMethod() => () { ... }
 * </code></pre>
 */
public class AttributeType extends ClassType {

  // Used as a placeholder for unresolved attribute types during typechecking
  public static final AttributeType UNKNOWN_ATTRIBUTE_TYPE =
      new AttributeType("Unknown Attribute", null, null);

  // First-party attribute types referenced during compilation. These are populated during type
  // collection.
  public static AttributeType IMPORT_ASSEMBLY;
  public static AttributeType EXPORT_ASSEMBLY;
  public static AttributeType IMPORT_NATIVE;
  public static AttributeType EXPORT_NATIVE;
  public static AttributeType IMPORT_METHOD;
  public static AttributeType EXPORT_METHOD;

  /** Called by {@link Type#clearTypes()}. */
  public static void clearTypes() {
    IMPORT_ASSEMBLY = null;
    EXPORT_ASSEMBLY = null;
    IMPORT_NATIVE = null;
    EXPORT_NATIVE = null;
    IMPORT_METHOD = null;
    EXPORT_METHOD = null;
  }

  public AttributeType(String typeName, Documentation documentation, Type outer) {
    super(typeName, new Modifiers(), documentation, outer);
  }

  @Override
  public void printMetaFile(PrintWriter out, String linePrefix) {
    throw new UnsupportedOperationException("Meta files cannot be created for attributes since their definitions are required at compile time");
  }
}
