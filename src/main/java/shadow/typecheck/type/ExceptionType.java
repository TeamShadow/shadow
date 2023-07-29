package shadow.typecheck.type;

import shadow.doctool.Documentation;

import java.io.PrintWriter;

public class ExceptionType extends ClassType {
  public ExceptionType(
      String typeName, Modifiers modifiers, Documentation documentation, Type outer) {
    this(typeName, modifiers, documentation, outer, null);
  }

  public ExceptionType(
      String typeName,
      Modifiers modifiers,
      Documentation documentation,
      Type outer,
      ClassType extendType) {
    super(typeName, modifiers, documentation, outer);
    setExtendType(extendType);
  }

  public boolean isSubtype(Type t) {
    if (t == UNKNOWN) return false;

    if (equals(t)) return true;

    if (t.equals(OBJECT)) return true;

    if (t instanceof ExceptionType) return isDescendantOf(t);
    else return false;
  }

  public void printMetaFile(PrintWriter out, String linePrefix) {
    printMetaFile(out, linePrefix, "exception");
  }
}
