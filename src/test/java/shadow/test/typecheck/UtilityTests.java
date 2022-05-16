package shadow.test.typecheck;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Configuration;
import shadow.Main;

import java.util.ArrayList;

public class UtilityTests {

  private final ArrayList<String> args = new ArrayList<>();

  @BeforeAll
  public static void clearConfiguration() {
    Configuration.clearConfiguration();
  }

  @BeforeEach
  public void setup() throws Exception {
    // args.add("-v");
    args.add("--typecheck");

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    args.add("tests.json");
  }

  @Test
  public void testArrayList() throws Exception {
    args.add("src/shadow/utility/ArrayList.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testHashSet() throws Exception {
    args.add("src/shadow/utility/HashSet.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testIllegalModificationException() throws Exception {
    args.add("src/shadow/utility/IllegalModificationException.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testLinkedList() throws Exception {
    args.add("src/shadow/utility/LinkedList.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testList() throws Exception {
    args.add("src/shadow/utility/List.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testRandom() throws Exception {
    args.add("src/shadow/utility/Random.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testSet() throws Exception {
    args.add("src/shadow/utility/Set.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testArrayDeque() throws Exception {
    args.add("src/shadow/utility/ArrayDeque.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testHashMap() throws Exception {
    args.add("src/shadow/utility/HashMap.shadow");
    Main.run(args.toArray(new String[] {}));
  }
}
