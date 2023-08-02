package shadow.test.output;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Configuration;
import shadow.Main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TACTests {

  // To simplify removal, every unit test executable will have the same name
  private static final Path executable =
      Main.properExecutableName(Paths.get("bin", "shadow", "test", "TacTest"));

  private final ArrayList<String> args = new ArrayList<>();

  @BeforeAll
  public static void clearConfiguration() {
    Configuration.clearConfiguration();
  }

  @BeforeEach
  public void setup() {
    args.add("-o");
    args.add(executable.toString());

    System.out.println("Working Directory = " + System.getProperty("user.dir"));

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    if (os.contains("windows")) args.add("windows.json");
    else if (os.contains("mac")) args.add("mac.json");
    else args.add("linux.json");
  }

  @AfterEach
  public void cleanup() {

    // To to remove the unit test executable
    try {
      Files.delete(executable);
    } catch (Exception ignored) {
    }
  }

  @Test
  public void testCanCreate() throws Exception {
    args.add("shadow/test/CanCreateTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    // can't test output because of timing dependence
  }

  @Test
  public void testFile() throws Exception {
    args.add("shadow/test/FileTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    // can't test this without a file
  }

  @Test
  public void testSort() throws Exception {
    args.add("shadow/test/SortMain.shadow");
    new Main(args.toArray(new String[] {})).run();
    // can't test because output is connected to timing
  }

  @Test
  public void testDouble() throws Exception {
    args.add("shadow/test/DoubleTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    // can't test without more predictable floating point output
  }

  @Test
  public void testFloat() throws Exception {
    args.add("shadow/test/FloatTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    // can't test without more predictable floating point output
  }

  @Test
  public void testGarbageCollection() throws Exception {
    args.add("shadow/test/GarbageCollectionTest.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testMath() throws Exception {
    args.add("shadow/test/MathTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    // can't test without more predictable floating point output
  }

  @Test
  public void testRandom() throws Exception {
    args.add("shadow/test/RandomTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    // can't test without more predictable floating point output
  }

  @Test
  public void testString() throws Exception {
    args.add("shadow/test/StringTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    // can't test without more predictable floating point output
  }

  @Test
  public void testHello() throws Exception {
    args.add("shadow/test/HelloTest.shadow");
    new Main(args.toArray(new String[] {})).run();
    // CAN test, but it's useful to have a testable executable lying around TAC tests
  }
}
