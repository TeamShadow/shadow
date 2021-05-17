package shadow.typecheck.type;

import shadow.parse.ShadowParser.TypeArgumentsContext;
import shadow.typecheck.BaseChecker.SubstitutionKind;

import java.util.List;

public class UninstantiatedClassType extends ClassType implements UninstantiatedType {
  private final ClassType type;
  private final SequenceType typeArguments;
  private final TypeArgumentsContext context;

  public SequenceType getTypeArguments() {
    return typeArguments;
  }

  public UninstantiatedClassType(
      ClassType type, SequenceType typeArguments, TypeArgumentsContext context) {
    this(type, typeArguments, context, type.getOuter());
  }

  public UninstantiatedClassType(
      ClassType type, SequenceType typeArguments, TypeArgumentsContext context, Type outer) {
    super(type.getTypeName(), type.getModifiers(), type.getDocumentation(), outer);
    this.type = type;
    this.typeArguments = typeArguments;
    this.context = context;
  }

  public ClassType getType() {
    return type;
  }

  public TypeArgumentsContext getContext() {
    return context;
  }

  // Doesn't update members and methods
  @Override
  public ClassType partiallyInstantiate() throws InstantiationException {
    for (ModifiedType argument : typeArguments) {
      if (argument.getType() instanceof UninstantiatedType) {
        UninstantiatedType uninstantiatedArgument = (UninstantiatedType) argument.getType();
        argument.setType(uninstantiatedArgument.partiallyInstantiate());
      }
    }

    if (!type.getTypeParameters().canAccept(typeArguments, SubstitutionKind.TYPE_PARAMETER))
      throw new InstantiationException(
          "Supplied type arguments "
              + typeArguments
              + " do not match type parameters "
              + type.getTypeParameters(),
          context);

    return type.partiallyReplace(type.getTypeParameters(), typeArguments);
  }

  @Override
  public ClassType instantiate() throws InstantiationException {
    for (ModifiedType argument : typeArguments) {
      if (argument.getType() instanceof UninstantiatedType) {
        UninstantiatedType uninstantiatedArgument = (UninstantiatedType) argument.getType();
        argument.setType(uninstantiatedArgument.instantiate());
      } else if (argument.getType() instanceof ArrayType) {
        ArrayType arrayArgument = (ArrayType) argument.getType();
        argument.setType(arrayArgument.instantiate());
      }
    }

    if (!type.getTypeParameters().canAccept(typeArguments, SubstitutionKind.TYPE_PARAMETER))
      throw new InstantiationException(
          "Supplied type arguments "
              + typeArguments
              + " do not match type parameters "
              + type.getTypeParameters(),
          context);

    return type.replace(type.getTypeParameters(), typeArguments);
  }

  @Override
  public UninstantiatedClassType partiallyReplace(
      List<ModifiedType> values, List<ModifiedType> replacements) throws InstantiationException {
    return new UninstantiatedClassType(
        type, typeArguments.partiallyReplace(values, replacements), context);
  }

  @Override
  public ClassType replace(List<ModifiedType> values, List<ModifiedType> replacements)
      throws InstantiationException {
    return new UninstantiatedClassType(type, typeArguments.replace(values, replacements), context)
        .instantiate();
  }

  @Override
  public String toString(int options) {
    return type.toString() + typeArguments.toString(" [", "]", options);
  }

  @Override
  public ClassType getTypeWithoutTypeArguments() {
    return type.getTypeWithoutTypeArguments();
  }
}
