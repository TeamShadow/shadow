package shadow.test.typecheck;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Configuration;
import shadow.Loggers;
import shadow.Main;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.Package;
import shadow.typecheck.TypeCollector;
import shadow.typecheck.type.Type;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class ImportTests {

  private final ArrayList<String> args = new ArrayList<>();

  @BeforeAll
  public static void clearConfiguration() {
    Configuration.clearConfiguration();
  }

  @BeforeEach
  public void setup() {
    args.add("--typecheck");
    args.add("-c");
    args.add("tests.json");
  }

  // File being compiled is fine, but there's a problem with another one in the directory
  @Test
  public void testErrorsInOtherFiles() throws Exception {
    args.add("tests/import/errors-in-other-files/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  // Local file has the same name as a standard class (String)
  @Test
  public void testHidingStandardClass() throws Exception {
    args.add("tests/import/hiding-standard-class/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  // Local files are in a package
  @Test
  public void testPackage() throws Exception {
    args.add("tests/import/package/testing/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  // Type has no import statement because it's fully qualifed
  @Test
  public void testFullyQualifiedType() throws Exception {
    args.add("tests/import/fully-qualified-type/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  // Type imports inner class in the same package
  @Test
  public void testInnerClassInSamePackage() throws Exception {
    args.add("tests/import/inner-class-in-same-package/testing/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  // Type imports inner class in a diffewrent package
  @Test
  public void testInnerClassInDifferentPackage() throws Exception {
    args.add("tests/import/inner-class-in-different-package/testing/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testAllImports() throws Exception {
    String os = System.getProperty("os.name").toLowerCase();

    String config = "tests.json";

    String file = "tests/import/all-imports/Test.shadow";

    Configuration.buildConfiguration(file, config, false);

    Path path = Paths.get(file).toAbsolutePath().normalize();
    Package p = new Package();
    ErrorReporter reporter = new ErrorReporter(Loggers.TYPE_CHECKER);
    TypeCollector collector = new TypeCollector(p, reporter, false, true);
    collector.collectTypes(List.of(path));
    Type testType = collector.getMainType();
    reporter.printAndReportErrors();

    TreeSet<String> expectedNames = new TreeSet<>();
    expectedNames.add("Console");
    expectedNames.add("Cow");
    expectedNames.add("Pig");
    expectedNames.add("String");
    expectedNames.add("Object");

    for (String name : testType.getImportedItems().keySet()) {
      if (expectedNames.contains(name)) expectedNames.remove(name);
      else throw new Exception("Expected type " + name + " to be imported, but it was not");
    }

    if (!expectedNames.isEmpty())
      throw new Exception(
          "Did not expect type " + expectedNames.first() + " to be imported, but it was");
  }
}
