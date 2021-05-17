package shadow.test.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Main;
import shadow.interpreter.InterpreterException;
import shadow.interpreter.InterpreterException.Error;

import java.util.ArrayList;

public class NegativeTests {

  private final ArrayList<String> args = new ArrayList<>();

  @BeforeEach
  public void setup() throws Exception {
    // args.add("-v");
    args.add("--typecheck");
    // args.add("--force-recompile");

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    if (os.contains("windows")) args.add("windows.xml");
    else if (os.contains("mac")) args.add("mac.xml");
    else args.add("linux.xml");
  }

  private void enforce(Error type) throws Exception {
    try {
      Main.run(args.toArray(new String[] {}));
      throw new Exception("Test failed");
    } catch (InterpreterException e) {
      if (!e.getError().equals(type)) throw new Exception("Test failed");
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Test failed");
    }
  }

  @Test
  public void testConstantInitializationFailure() throws Exception {
    args.add("tests-negative/interpreter/constant-initialization-failure/Test.shadow");
    enforce(Error.INVALID_CREATE);
  }

  @Test
  public void testCircularConstantDependency() throws Exception {
    args.add("tests-negative/interpreter/circular-constant-dependency/Test.shadow");
    enforce(Error.CIRCULAR_REFERENCE);
  }

  @Test
  public void testReferenceToNonConstant() throws Exception {
    args.add("tests-negative/interpreter/reference-to-non-constant/Test.shadow");
    enforce(Error.NON_CONSTANT_REFERENCE);
  }
}
