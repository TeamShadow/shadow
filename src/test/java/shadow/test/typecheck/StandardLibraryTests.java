package shadow.test.typecheck;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Configuration;
import shadow.Main;

import java.util.ArrayList;

public class StandardLibraryTests {

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
  public void testArray() throws Exception {
    args.add("src/shadow/standard/Array.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testBoolean() throws Exception {
    args.add("src/shadow/standard/Boolean.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testByte() throws Exception {
    args.add("src/shadow/standard/Byte.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testCanCompare() throws Exception {
    args.add("src/shadow/standard/CanCompare.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testEqual() throws Exception {
    args.add("src/shadow/standard/CanEqual.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testCanHash() throws Exception {
    args.add("src/shadow/standard/CanHash.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testCanIndex() throws Exception {
    args.add("src/shadow/standard/CanIndex.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testCanIterate() throws Exception {
    args.add("src/shadow/standard/CanIterate.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testClass() throws Exception {
    args.add("src/shadow/standard/Class.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testCode() throws Exception {
    args.add("src/shadow/standard/Code.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testDouble() throws Exception {
    args.add("src/shadow/standard/Double.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testEnum() throws Exception {
    args.add("src/shadow/standard/Enum.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testException() throws Exception {
    args.add("src/shadow/standard/Exception.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testFloat() throws Exception {
    args.add("src/shadow/standard/Float.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testFloatingPoint() throws Exception {
    args.add("src/shadow/standard/FloatingPoint.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testIllegalArgumentException() throws Exception {
    args.add("src/shadow/standard/IllegalArgumentException.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testIndexOutOfBoundsException() throws Exception {
    args.add("src/shadow/standard/IndexOutOfBoundsException.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testInt() throws Exception {
    args.add("src/shadow/standard/Int.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testInteger() throws Exception {
    args.add("src/shadow/standard/Integer.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testIterator() throws Exception {
    args.add("src/shadow/standard/Iterator.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testLong() throws Exception {
    args.add("src/shadow/standard/Long.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testMutableString() throws Exception {
    args.add("src/shadow/standard/MutableString.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testNumber() throws Exception {
    args.add("src/shadow/standard/Number.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testObject() throws Exception {
    args.add("src/shadow/standard/Object.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testShort() throws Exception {
    args.add("src/shadow/standard/Short.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testString() throws Exception {
    args.add("src/shadow/standard/String.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testSystem() throws Exception {
    args.add("src/shadow/standard/System.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testUByte() throws Exception {
    args.add("src/shadow/standard/UByte.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testUInt() throws Exception {
    args.add("src/shadow/standard/UInt.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testULong() throws Exception {
    args.add("src/shadow/standard/ULong.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testBigInteger() throws Exception {
    args.add("src/shadow/standard/BigInteger.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testUnexpectedNullException() throws Exception {
    args.add("src/shadow/standard/UnexpectedNullException.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testUnsupportedOperationException() throws Exception {
    args.add("src/shadow/standard/UnsupportedOperationException.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testUShort() throws Exception {
    args.add("src/shadow/standard/UShort.shadow");
    new Main(args.toArray(new String[] {})).run();
  }
}
