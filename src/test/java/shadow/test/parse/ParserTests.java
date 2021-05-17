package shadow.test.parse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Main;

import java.util.ArrayList;

public class ParserTests {

  private final ArrayList<String> args = new ArrayList<>();

  @BeforeEach
  public void setup() throws Exception {
    args.add("--typecheck");

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    if (os.contains("windows")) args.add("windows.xml");
    else if (os.contains("mac")) args.add("mac.xml");
    else args.add("linux.xml");
  }

  @Test
  public void testAttributes() throws Exception {
    args.add("tests/parser/attributes/Attributes.shadow");
    Main.run(args.toArray(new String[] {}));
  }
}
