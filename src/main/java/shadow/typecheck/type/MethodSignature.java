package shadow.typecheck.type;

import shadow.doctool.Documentation;
import shadow.interpreter.ShadowClass;
import shadow.parse.Context;
import shadow.parse.ShadowParser;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.TypeCheckException;

import java.util.*;

public class MethodSignature implements Comparable<MethodSignature> {

  protected final MethodType type;
  protected final String symbol;
  private final Context node;
  /** The AST context that corresponds to the branch of the tree for this method */
  private final MethodSignature wrapped;

  private MethodSignature signatureWithoutTypeArguments;
  private Type outer;
  private final Set<SingletonType> singletons = new HashSet<>();

  public enum ImportExportMode {
    NONE, // Default for all methods - normal Shadow visibility rules
    NATIVE, // Native LLVM IR code
    ASSEMBLY, // Linked assembly code (e.g. compiled C code)
    METHOD, // Shadow code shared between classes (outside of inheritance)
  }

  private ImportExportMode importMode = ImportExportMode.NONE;
  private ImportExportMode exportMode = ImportExportMode.NONE;

  // Only applies for ImportMethod signatures - points to the corresponding exported method
  private MethodSignature importSource;

  private final Map<AttributeType, AttributeInvocation> attributes = new HashMap<>();

  private MethodSignature(
      MethodType type, String symbol, Type outer, Context node, MethodSignature wrapped) {
    this.type = type;
    this.symbol = symbol;
    this.node = node;
    this.wrapped = wrapped;
    this.outer = outer;
    signatureWithoutTypeArguments = this;
    if (node != null) node.setEnclosingType(outer);
  }

  public MethodSignature(MethodType type, String symbol, Type outer, Context node) {
    this(type, symbol, outer, node, null);
  }

  public MethodSignature(
      Type enclosingType,
      String symbol,
      Modifiers modifiers,
      Documentation documentation,
      Context node) {
    this(new MethodType(enclosingType, modifiers, documentation), symbol, enclosingType, node);
  }

  public void addParameter(String name, ModifiedType node) {
    this.type.addParameter(name, node);
  }

  public ModifiedType getParameterType(String paramName) {
    return type.getParameterType(paramName);
  }

  public void addReturn(ModifiedType ret) {
    type.addReturn(ret);
  }

  public Modifiers getModifiers() {
    return type.getModifiers();
  }

  public String getSymbol() {
    return symbol;
  }

  public Context getNode() {
    return node;
  }

  public boolean canAccept(SequenceType argumentTypes) {
    return type.canAccept(argumentTypes);
  }

  public boolean equals(Object o) {
    if (o instanceof MethodSignature) {
      MethodSignature ms = (MethodSignature) o;
      // Is it pretty? No. Should it work?  Probably!
      return ms.getMangledName().equals(getMangledName());
    } else return false;
  }

  public boolean isIndistinguishable(MethodSignature signature)
        // isIndistinguishable() differs from equals() in that differences in return types are
        // ignored
      {
    if (signature != null && signature.symbol.equals(symbol))
      return type.matchesParams(signature.type);
    else return false;
  }

  // These are the true return types that the compiler will use
  public SequenceType getFullReturnTypes() {
    Type outerType = getOuter();
    if (isCreate()) {
      SimpleModifiedType modified;
      if (outerType instanceof InterfaceType) modified = new SimpleModifiedType(Type.OBJECT);
      else modified = new SimpleModifiedType(outerType);

      if (outerType.isPrimitive()) modified.getModifiers().addModifier(Modifiers.NULLABLE);

      return new SequenceType(Collections.singletonList(modified));
    }

    // if( isWrapper() || outerType instanceof InterfaceType )
    // return signatureWithoutTypeArguments.type.getReturnTypes();
    // else
    return type.getReturnTypes();

    // return type.getReturnTypes();
  }

  // These are the true parameter types that the compiler will use
  public SequenceType getFullParameterTypes() {
    SequenceType paramTypes = new SequenceType();

    MethodType methodType;
    // if( isWrapper() || getOuter() instanceof InterfaceType )
    // methodType = signatureWithoutTypeArguments.type;
    // else
    methodType = type;

    if (!isImportAssembly()) {
      Type outerType = getOuter();
      if (isCreate()
          || outerType
              instanceof
              InterfaceType) // since actual object is unknown, assume Object for all interface
        // methods
        paramTypes.add(new SimpleModifiedType(Type.OBJECT));
      else paramTypes.add(new SimpleModifiedType(outerType)); // this
    }

    paramTypes.addAll(methodType.getParameterTypes());
    return paramTypes;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(getModifiers().toString());
    sb.append(symbol);

    // nothing more for destroy
    if (!symbol.equals("destroy")) {
      sb.append(type.parametersToString());

      // no return types for create
      if (!symbol.equals("create")) sb.append(" => ").append(type.getReturnTypes());
    }

    return sb.toString();
  }

  @Override
  public int hashCode() {
    return getMangledName().hashCode();
  }

  // Is it only the wrapped ones that correspond to interface methods?
  // If so, those are the ones that need special generic attention
  public String getMangledName() {
    if (isImportAssembly() || isExportAssembly()) {
      return symbol;
    }

    StringBuilder sb = new StringBuilder();

    if (isWrapper()) sb.append(getWrapped().getOuter().toString(Type.MANGLE));
    else if (isImportMethod()) // we set the first parameter as the owner of the method
    sb.append(getParameterTypes().get(0).getType().toString(Type.MANGLE));
    else sb.append(getOuter().toString(Type.MANGLE));

    sb.append("_M")
        .append(Type.mangle(symbol))
        .append(
            type.getTypeWithoutTypeArguments()
                .toString(
                    Type.MANGLE
                        | Type.TYPE_PARAMETERS
                        | (isImportMethod() ? Type.MANGLE_IMPORT_METHOD : 0)));

    if (isWrapper())
      sb.append("_W_").append(getOuter().toString(Type.MANGLE | Type.TYPE_PARAMETERS));

    return sb.toString();
  }

  public List<String> getParameterNames() {
    return type.getParameterNames();
  }

  public SequenceType getParameterTypes() {
    return type.getParameterTypes();
  }

  public SequenceType getReturnTypes() {
    return type.getReturnTypes();
  }

  public Type getOuter() {
    return outer;
  }

  public MethodType getMethodType() {
    return type;
  }

  public MethodSignature replace(List<ModifiedType> values, List<ModifiedType> replacements)
      throws InstantiationException {
    MethodSignature replaced =
        new MethodSignature(
            type.replace(values, replacements), symbol, outer.replace(values, replacements), node);
    replaced.signatureWithoutTypeArguments = signatureWithoutTypeArguments;

    return replaced;
  }

  public MethodSignature partiallyReplace(
      List<ModifiedType> values, List<ModifiedType> replacements) throws InstantiationException {
    MethodSignature replaced =
        new MethodSignature(
            type.partiallyReplace(values, replacements),
            symbol,
            outer.partiallyReplace(values, replacements),
            node);
    replaced.signatureWithoutTypeArguments = signatureWithoutTypeArguments;
    return replaced;
  }

  public void updateFieldsAndMethods() throws InstantiationException {
    type.updateFieldsAndMethods();
  }

  public MethodSignature getSignatureWithoutTypeArguments() {
    return signatureWithoutTypeArguments;
  }

  public boolean matchesInterface(MethodSignature interfaceSignature) {
    boolean matches =
        interfaceSignature.symbol.equals(symbol) && type.matchesInterface(interfaceSignature.type);

    if (interfaceSignature.getModifiers().isReadonly() && !getModifiers().isReadonly())
      matches = false;

    return matches;
  }

  @Override
  public int compareTo(MethodSignature o) {
    return getMangledName().compareTo(o.getMangledName());
  }

  public boolean isCreate() {
    return symbol.equals("create");
  }

  public boolean isDestroy() {
    return symbol.equals("destroy");
  }

  public boolean isCopy() {
    return symbol.equals("copy");
  }

  public boolean isGet() {
    return (type.getModifiers().isGet()
        && type.getReturnTypes().size() == 1
        && type.getParameterTypes().isEmpty());
  }

  public boolean isSet() {
    return (type.getModifiers().isSet()
        && type.getReturnTypes().isEmpty()
        && type.getParameterTypes().size() == 1);
  }

  public boolean isLocked() {
    return type.getModifiers().isLocked();
  }

  public boolean isVoid() {
    return type.getReturnTypes().size() == 0;
  }

  public boolean isSingle() {
    return type.getReturnTypes().size() == 1;
  }

  public boolean isSequence() {
    return type.getReturnTypes().size() > 1;
  }

  public boolean isWrapper() {
    return wrapped != null;
  }

  public MethodSignature getWrapped() {
    return wrapped;
  }

  public MethodSignature wrap(MethodSignature wrapped) {
    Modifiers modifiers = new Modifiers(wrapped.getModifiers().getModifiers());
    MethodType methodType;
    // if( outer instanceof InterfaceType )
    //	methodType = type.copy(wrapped.getOuter(), modifiers);
    // methodType = signatureWithoutTypeArguments.type.copy(modifiers);
    // else
    methodType = type.copy(modifiers);
    MethodSignature wrapper = new MethodSignature(methodType, symbol, outer, node, wrapped);
    wrapper.signatureWithoutTypeArguments = signatureWithoutTypeArguments;
    return wrapper;
  }

  // makes a copy of the method signature with a different outer type
  public MethodSignature copy(Type newOuter) {
    return new MethodSignature(type, symbol, newOuter, node, wrapped);
  }

  public void setOuter(Type outer) {
    this.outer = outer;
  }

  public boolean hasDocumentation() {
    return type.hasDocumentation();
  }

  public Documentation getDocumentation() {
    return type.getDocumentation();
  }

  public void addSingleton(SingletonType type) {
    singletons.add(type);
  }

  public Set<SingletonType> getSingletons() {
    return singletons;
  }

  public boolean hasBlock() {
    boolean hasBlock = false;

    if (node instanceof ShadowParser.CreateDeclarationContext)
      hasBlock = ((ShadowParser.CreateDeclarationContext) node).createBlock() != null;
    else if (node instanceof ShadowParser.DestroyDeclarationContext)
      hasBlock = ((ShadowParser.DestroyDeclarationContext) node).block() != null;
    else if (node instanceof ShadowParser.MethodDeclarationContext)
      hasBlock = ((ShadowParser.MethodDeclarationContext) node).block() != null;

    return hasBlock;
  }

  public boolean isAbstract() {
    return type.getModifiers().isAbstract();
  }

  public boolean isImport() {
    return importMode != ImportExportMode.NONE;
  }

  public boolean isImportNative() {
    return importMode == ImportExportMode.NATIVE;
  }

  public boolean isImportAssembly() {
    return importMode == ImportExportMode.ASSEMBLY;
  }

  public boolean isImportMethod() {
    return importMode == ImportExportMode.METHOD;
  }

  public boolean isExport() {
    return exportMode != ImportExportMode.NONE;
  }

  public boolean isExportNative() {
    return exportMode == ImportExportMode.NATIVE;
  }

  public boolean isExportAssembly() {
    return exportMode == ImportExportMode.ASSEMBLY;
  }

  public boolean isExportMethod() {
    return exportMode == ImportExportMode.METHOD;
  }

  public void setImportSource(MethodSignature method) {
    // TODO: Assert that the import mode is IMPORT_METHOD
    importSource = method;
  }

  public Collection<AttributeInvocation> getAttributes() {
    return Collections.unmodifiableCollection(attributes.values());
  }

  public AttributeInvocation getAttributeInvocation(AttributeType type) {
    return attributes.get(type);
  }

  public void attachAttribute(
      ShadowParser.AttributeInvocationContext ctx, ErrorReporter errorReporter) {
    AttributeInvocation attribute = new AttributeInvocation(ctx, errorReporter, this);
    AttributeType type = attribute.getType();

    if (attributes.containsKey(type)) {
      errorReporter.addError(
          ctx,
          TypeCheckException.Error.REPEATED_ATTRIBUTE,
          type.getTypeName() + " appears more than once on the same method",
          type);
    } else {
      attributes.put(type, attribute);
    }
  }

  /**
   * Performs any required operations based on the attached attribute types, prior to those
   * attributes having their fields evaluated.
   */
  public void processAttributeTypes(ErrorReporter errorReporter) {
    for (AttributeType attributeType : attributes.keySet()) {
      if (attributeType.equals(AttributeType.IMPORT_NATIVE)) {
        processImportExportAttributes(true, ImportExportMode.NATIVE, errorReporter);
      } else if (attributeType.equals(AttributeType.IMPORT_ASSEMBLY)) {
        processImportExportAttributes(true, ImportExportMode.ASSEMBLY, errorReporter);
      } else if (attributeType.equals(AttributeType.IMPORT_METHOD)) {
        processImportExportAttributes(true, ImportExportMode.METHOD, errorReporter);
      } else if (attributeType.equals(AttributeType.EXPORT_NATIVE)) {
        processImportExportAttributes(false, ImportExportMode.NATIVE, errorReporter);
      } else if (attributeType.equals(AttributeType.EXPORT_ASSEMBLY)) {
        processImportExportAttributes(false, ImportExportMode.ASSEMBLY, errorReporter);
      } else if (attributeType.equals(AttributeType.EXPORT_METHOD)) {
        processImportExportAttributes(false, ImportExportMode.METHOD, errorReporter);
      }
    }
  }

  private void processImportExportAttributes(
      boolean isImport, ImportExportMode mode, ErrorReporter errorReporter) {
    if (!(importMode == ImportExportMode.NONE && exportMode == ImportExportMode.NONE)) {
      errorReporter.addError(
          node,
          TypeCheckException.Error.INVALID_TYPE,
          "Only one import or export attribute can be present on a method at all times.");
      return;
    }

    if (isImport) {
      importMode = mode;
    } else {
      exportMode = mode;
    }
  }

  /**
   * Performs any required operations based on the attached attribute types AND the values of their
   * fields. Runs relatively late, after compile-time constants can be evaluated.
   */
  public void processAttributeValues(ErrorReporter errorReporter) {
    for (AttributeType attributeType : attributes.keySet()) {
      if (attributeType.equals(AttributeType.IMPORT_METHOD)) {
        AttributeInvocation export =
            importSource.getAttributeInvocation(AttributeType.EXPORT_METHOD);
        if (export != null) {
          Type exportType = ((ShadowClass) export.getFieldValue("exportedTo")).getRepresentedType();
          if (outer.equals(exportType)) {
            continue;
          }
        }
        errorReporter.addError(
            node,
            TypeCheckException.Error.INVALID_METHOD_IMPORT,
            "The '"
                + importSource.getSymbol()
                + "' method in '"
                + importSource.getOuter()
                + "' is not allowed to be shared with '"
                + outer
                + "'");
      }
    }
  }
}
