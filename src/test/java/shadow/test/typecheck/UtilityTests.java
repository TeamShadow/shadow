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
    args.add("--typecheck");
    args.add("-c");
    args.add("tests.json");
  }

  @Test
  public void testArrayList() throws Exception {
    args.add("src/shadow/utility/ArrayList.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testHashSet() throws Exception {
    args.add("src/shadow/utility/HashSet.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testIllegalModificationException() throws Exception {
    args.add("src/shadow/utility/IllegalModificationException.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testLinkedList() throws Exception {
    args.add("src/shadow/utility/LinkedList.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testList() throws Exception {
    args.add("src/shadow/utility/List.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testRandom() throws Exception {
    args.add("src/shadow/utility/Random.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testSet() throws Exception {
    args.add("src/shadow/utility/Set.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testArrayDeque() throws Exception {
    args.add("src/shadow/utility/ArrayDeque.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testHashMap() throws Exception {
    args.add("src/shadow/utility/HashMap.shadow");
    new Main(args.toArray(new String[] {})).run();
  }
}
