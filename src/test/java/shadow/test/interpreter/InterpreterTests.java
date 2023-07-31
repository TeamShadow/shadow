package shadow.test.interpreter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Configuration;
import shadow.Main;

import java.util.ArrayList;

public class InterpreterTests {

  private final ArrayList<String> args = new ArrayList<>();

  @BeforeAll
  public static void clearConfiguration() {
    Configuration.clearConfiguration();
  }

  @BeforeEach
  public void setup() throws Exception {
    args.add("--typecheck");
    args.add("-c");
    args.add("tests.json");
  }

  @Test
  public void testManyConstants() throws Exception {
    args.add("tests/interpreter/many-constants/Test.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testUnusualConstantDependency() throws Exception {
    args.add("tests/interpreter/unusual-constant-dependency/Test.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testInterfaceConstant() throws Exception {
    args.add("tests/interpreter/interface-constant/Test.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testInheritedConstants() throws Exception {
    args.add("tests/interpreter/inherited-constants/Test.shadow");
    new Main(args.toArray(new String[] {})).run();
  }
}
