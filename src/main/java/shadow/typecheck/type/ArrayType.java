package shadow.typecheck.type;

import shadow.ShadowException;
import shadow.parse.ShadowParser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ArrayType extends ClassType {
  private final Type baseType;
  private final boolean nullable;
  private ClassType genericVersion = null;

  @Override
  public int getWidth() {
    return 6;
    // Not necessarily the actual width, just a value that helps sort the fields
    // References have a "width" of 6, which covers either 4 or 8 byte pointers
    // Interfaces first
    // Then longs and ulongs
    // Then regular references and arrays
    // Then smaller primitives
  }

  public Type recursivelyGetBaseType() {
    if (baseType instanceof ArrayType) return ((ArrayType) baseType).recursivelyGetBaseType();
    return baseType;
  }

  public Type getBaseType() {
    return baseType;
  }

  public ArrayType(Type baseType) {
    this(baseType, false);
  }

  protected static Type getLowestBase(Type type) {
    if (type instanceof ArrayType) return ((ArrayType) type).recursivelyGetBaseType();
    return type;
  }

  public ArrayType(Type baseType, boolean nullable) {
    this(baseType, 1, nullable);
  }

  public ArrayType(Type baseType, int dimensions, boolean nullable) {
    super(
        getLowestBase(baseType).getTypeName(),
        new Modifiers(baseType.getModifiers().getModifiers() & ~Modifiers.IMMUTABLE),
        baseType.getDocumentation(),
        baseType.getOuter());

    setExtendType(Type.OBJECT);

    if (dimensions == 1) this.baseType = baseType;
    else this.baseType = new ArrayType(baseType, dimensions - 1, nullable);

    if (baseType.isParameterized()) setParameterized(true);

    this.nullable = nullable;
  }

  @Override
  public String toString(int options) {
    if ((options & MANGLE) != 0) {
      String baseName =
          baseType.isPrimitive() ? baseType.getTypeName() : baseType.toString(options);
      if (nullable) return baseName + "._NA";
      else return baseName + "._A";
    }

    boolean printNullable =
        nullable && (options & NO_NULLABLE) == 0 && !(baseType instanceof ArrayType);
    return (printNullable ? "nullable " : "") + baseType.toString(options) + "[]";
  }

  @Override
  public SequenceType getTypeParameters() {
    return baseType.getTypeParameters();
  }

  @Override
  public boolean equals(Type type) {
    if (type == Type.NULL) return false;

    if (type instanceof ArrayType) {
      ArrayType other = (ArrayType) type;
      if (nullable == other.nullable) return baseType.equals(other.baseType);
    }
    return false;
  }

  @Override
  public MethodSignature getMatchingMethod(
      String methodName,
      SequenceType arguments,
      SequenceType typeArguments,
      List<ShadowException> errors) {
    return convertToGeneric().getMatchingMethod(methodName, arguments, typeArguments, errors);
  }

  @Override
  public List<MethodSignature> recursivelyGetMethodOverloads(String methodName) {
    return convertToGeneric().recursivelyGetMethodOverloads(methodName);
  }

  @Override
  public boolean containsField(String fieldName) {
    return convertToGeneric().containsField(fieldName);
  }

  @Override
  public ShadowParser.VariableDeclaratorContext getField(String fieldName) {
    return convertToGeneric().getField(fieldName);
  }

  @Override
  public LinkedHashMap<String, ShadowParser.VariableDeclaratorContext> getFields() {
    return convertToGeneric().getFields();
  }

  @Override
  public boolean hasInterface(InterfaceType type) {
    return convertToGeneric().hasInterface(type);
  }

  @Override
  public boolean hasUninstantiatedInterface(InterfaceType type) {
    return convertToGeneric().hasUninstantiatedInterface(type);
  }

  @Override
  public boolean isSubtype(Type t) {
    if (t == UNKNOWN) return false;

    if (t == OBJECT) return true;

    if (equals(t)) return true;

    if (t instanceof ArrayType) {
      ArrayType type = this;
      ArrayType other = (ArrayType) t;
      // Invariant subtyping on arrays
      if (type.nullable == other.nullable) return type.getBaseType().equals(other.getBaseType());
      else return false;
    }

    // Check generic version
    return convertToGeneric().isSubtype(t);
  }

  @Override
  public ArrayType replace(List<ModifiedType> values, List<ModifiedType> replacements)
      throws InstantiationException {
    return new ArrayType(baseType.replace(values, replacements), nullable);
  }

  public ClassType convertToGeneric() {

    if (genericVersion == null) {
      Type base = baseType;

      try {
        if (nullable)
          genericVersion =
              Type.ARRAY_NULLABLE.replace(
                  Type.ARRAY_NULLABLE.getTypeParameters(), new SequenceType(base));
        else
          genericVersion =
              Type.ARRAY.replace(Type.ARRAY.getTypeParameters(), new SequenceType(base));
      } catch (InstantiationException ignored) {
      }
    }

    return genericVersion; // shouldn't be null if instantiation succeeded
  }

  public ArrayType convertToNullable() {
    if (nullable) return this;
    else return new ArrayType(baseType, true);
  }

  public boolean isNullable() {
    return nullable;
  }

  public ArrayType instantiate() throws InstantiationException {
    if (recursivelyGetBaseType() instanceof UninstantiatedType) {
      if (baseType instanceof UninstantiatedType)
        return new ArrayType(((UninstantiatedType) baseType).instantiate(), nullable);
      // must be an array of arrays
      else return new ArrayType(((ArrayType) baseType).instantiate(), nullable);
    } else return this;
  }

  @Override
  public boolean isRecursivelyParameterized() {
    return baseType.isRecursivelyParameterized();
  }

  @Override
  public boolean isFullyInstantiated() {
    return baseType.isFullyInstantiated();
  }

  public boolean containsUnboundTypeParameters() {
    if (baseType instanceof TypeParameter) return true;

    if (baseType.isParameterized() && !baseType.isFullyInstantiated()) return true;

    if (baseType instanceof ArrayType)
      return ((ArrayType) baseType).containsUnboundTypeParameters();

    return false;
  }

  @Override
  protected boolean onlyUsesTypeParametersFrom(Type type) {
    return convertToGeneric().onlyUsesTypeParametersFrom(type);
  }

  // Returns true if this uses no parameters or only these parameters
  @Override
  protected boolean onlyUsesTheseParameters(Set<TypeParameter> parameters) {
    return convertToGeneric().onlyUsesTheseParameters(parameters);
  }
}
