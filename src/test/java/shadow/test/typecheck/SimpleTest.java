package shadow.test.typecheck;

import shadow.Main;

import java.util.ArrayList;

/**
 * This class is designed to execute simple tests during development. The full * JUnit suite is
 * sometimes unwieldy.
 *
 * @author Barry Wittman
 */
public class SimpleTest {

  public static void main(String[] unused) {

    ArrayList<String> args = new ArrayList<>();

    args.add("--typecheck");

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    if (os.contains("windows")) args.add("windows.json");
    else if (os.contains("mac")) args.add("mac.json");
    else args.add("linux.json");

    args.add("tests-negative/parser/missing-left-brace/Test.shadow");

    Main.main(args.toArray(new String[] {}));
  }
}
