package shadow.typecheck.type;

import shadow.doctool.Documentation;

import java.io.PrintWriter;

public class SingletonType extends ClassType {

  public SingletonType(String typeName, ClassType parent) {
    super(typeName, parent);
  }

  public SingletonType(
      String typeName, Modifiers modifiers, Documentation documentation, Type outer) {
    super(typeName, modifiers, documentation, outer);
  }

  public void printMetaFile(PrintWriter out, String linePrefix) {
    printMetaFile(out, linePrefix, "singleton");
  }
}
