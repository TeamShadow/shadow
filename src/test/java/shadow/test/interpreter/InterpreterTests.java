package shadow.test.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Main;

import java.util.ArrayList;

public class InterpreterTests {

  private final ArrayList<String> args = new ArrayList<>();

  @BeforeEach
  public void setup() throws Exception {
    // args.add("-v");
    args.add("--typecheck");

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    if (os.contains("windows")) args.add("windows.json");
    else if (os.contains("mac")) args.add("mac.json");
    else args.add("linux.json");
  }

  @Test
  public void testManyConstants() throws Exception {
    args.add("tests/interpreter/many-constants/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testUnusualConstantDependency() throws Exception {
    args.add("tests/interpreter/unusual-constant-dependency/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testInterfaceConstant() throws Exception {
    args.add("tests/interpreter/interface-constant/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testInheritedConstants() throws Exception {
    args.add("tests/interpreter/inherited-constants/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }
}
