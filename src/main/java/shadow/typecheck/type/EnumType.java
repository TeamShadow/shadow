package shadow.typecheck.type;

import shadow.doctool.Documentation;

import java.io.PrintWriter;

public class EnumType extends ClassType {
  public EnumType(String typeName, Modifiers modifiers, Documentation documentation, Type outer) {
    super(typeName, modifiers, documentation, outer);
  }

  public void printMetaFile(PrintWriter out, String linePrefix) {
    printMetaFile(out, linePrefix, "enum");
  }
}
