package shadow.test.typecheck;

import org.apache.commons.cli.ParseException;
import shadow.CommandLineException;
import shadow.ConfigurationException;
import shadow.Main;
import shadow.ShadowException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is designed to execute simple tests during development. The full * JUnit suite is
 * sometimes unwieldy.
 *
 * @author Barry Wittman
 */
public class SimpleTest {

  public static void main(String[] unused) throws ConfigurationException, ParseException, IOException, CommandLineException, ShadowException {

    ArrayList<String> args = new ArrayList<>();

    args.add("--typecheck");

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    if (os.contains("windows")) args.add("windows.json");
    else if (os.contains("mac")) args.add("mac.json");
    else args.add("linux.json");

    args.add("tests-negative/parser/missing-left-brace/Test.shadow");

    new Main(args.toArray(new String[] {})).run();
  }
}
