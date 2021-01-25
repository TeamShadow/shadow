package shadow.typecheck.type;

import shadow.doctool.Documentation;
import shadow.parse.ShadowParser;

import java.io.PrintWriter;
import java.util.*;

/**
 * Type representing an attribute in Shadow. Attributes are much simpler than classes: they have
 * no modifiers, do not support inheritance, and can only contain top-level constant fields.
 * <p>Example definition:
 * <pre><code>
 * attribute ExampleAttribute {
 *     // All fields are implicitly constant
 *     String name; // Must be provided when invoking this attribute
 *     int value = 1; // Can optionally be provided (and overwritten)
 * }
 * </code></pre>
 * Usage:
 * <pre><code>
 * [ExampleAttribute(name = "test")] // value = 1 by default
 * private myMethod() => () { ... }
 * </code></pre>
 */
public class AttributeType extends Type {

    // Used as a placeholder for unresolved attribute types during typechecking
    public static final AttributeType UNKNOWN_ATTRIBUTE_TYPE = new AttributeType("Unknown Attribute", null, null);

    // First-party attribute types referenced during compilation. These are populated during type collection.
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

    // Names of fields which do not have default values (i.e. must always be provided when invoking the attribute)
    private final Set<String> uninitializedFields = new HashSet<>();

    public AttributeType(String typeName, Documentation documentation, Type outer) {
        super(typeName, new Modifiers(), documentation, outer);
    }

    @Override
    public void addField(String fieldName, ShadowParser.VariableDeclaratorContext node) {
        if (node.conditionalExpression() == null) {
            uninitializedFields.add(node.generalIdentifier().getText());
        }
        super.addField(fieldName, node);
    }

    /** Returns the names of the fields of this attribute for which default values were not provided. */
    public Set<String> getUninitializedFields() {
        return Collections.unmodifiableSet(uninitializedFields);
    }

    public Map<String, ShadowParser.VariableDeclaratorContext> getInitializedFields() {
        Map<String, ShadowParser.VariableDeclaratorContext> initializedFields = new HashMap<>(getFields());
        for (String fieldName : uninitializedFields) {
            initializedFields.remove(fieldName);
        }
        return Collections.unmodifiableMap(initializedFields);
    }

    @Override
    public void printMetaFile(PrintWriter out, String linePrefix) {
        printImports(out, linePrefix);

        out.print("attribute ");
        if (!hasOuter()) // If this is the outermost class
            out.print(toString(PACKAGES));
        else {
            String name = toString();
            out.print(name.substring(name.lastIndexOf(':') + 1));
        }

        out.println();
        out.println(linePrefix + "{");

        // Fields
        for (Map.Entry<String, ShadowParser.VariableDeclaratorContext> field : getFields().entrySet()) {
            out.print(linePrefix + "\t" + field.getValue().getType().toString(PACKAGES | TYPE_PARAMETERS | NO_NULLABLE) + " " + field.getKey());
            if (!uninitializedFields.contains(field.getKey())) {
                out.print(" = " + field.getValue().getInterpretedValue().toLiteral());
            }
            out.println(";");
        }

        if (!getFields().isEmpty()) {
            out.println();
        }

        out.println(linePrefix + "}");
    }

    // Attributes do not support inheritance
    @Override
    public boolean isSubtype(Type other) {
        return false;
    }

    // Type-parameter related methods (not currently supported by attributes)

    @Override
    public Type replace(List<ModifiedType> values, List<ModifiedType> replacements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type partiallyReplace(List<ModifiedType> values, List<ModifiedType> replacements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateFieldsAndMethods() {

    }
}
