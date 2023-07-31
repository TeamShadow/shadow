package shadow.test.parse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Configuration;
import shadow.Main;

import java.util.ArrayList;

public class ParserTests {

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
  public void testAttributes() throws Exception {
    args.add("tests/parser/attributes/Attributes.shadow");
    new Main(args.toArray(new String[] {})).run();
  }
}
