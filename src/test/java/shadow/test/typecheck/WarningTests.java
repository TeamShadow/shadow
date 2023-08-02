package shadow.test.typecheck;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shadow.Configuration;
import shadow.Main;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeCheckException.Error;

import java.util.ArrayList;

public class WarningTests {

  private final ArrayList<String> args = new ArrayList<>();

  @BeforeAll
  public static void clearConfiguration() {
    Configuration.clearConfiguration();
  }

  @BeforeEach
  public void setup() throws Exception {
    args.add("--typecheck");
    args.add("-w");
    args.add("error");
    args.add("-c");
    args.add("tests.json");
  }

  private void enforce(Error type) throws Exception {
    try {
      new Main(args.toArray(new String[] {})).run();
      throw new Exception("Test failed");
    } catch (TypeCheckException e) {
      if (!e.getError().equals(type)) throw new Exception("Test failed");
    }
  }

  @Test
  public void testFieldNotUsed() throws Exception {
    args.add("tests-negative/warnings/field-not-used/Test.shadow");
    enforce(Error.UNUSED_FIELD);
  }

  @Test
  public void testPrivateMethodNotUsed() throws Exception {
    args.add("tests-negative/warnings/private-method-not-used/Test.shadow");
    enforce(Error.UNUSED_METHOD);
  }

  @Test
  public void testVariableNotUsed() throws Exception {
    args.add("tests-negative/warnings/variable-not-used/Test.shadow");
    enforce(Error.UNUSED_VARIABLE);
  }

  @Test
  public void testImportNotUsed() throws Exception {
    args.add("tests-negative/warnings/import-not-used/Test.shadow");
    enforce(Error.UNUSED_IMPORT);
  }

  @Test
  public void testNoImportsUsedFromDirectory() throws Exception {
    args.add("tests-negative/warnings/no-imports-used-from-directory/Test.shadow");
    enforce(Error.UNUSED_IMPORT);
  }

  @Test
  public void testImportsFromDirectoryCollide() throws Exception {
    args.add("tests-negative/warnings/imports-from-directory-collide/testing/Test.shadow");
    enforce(Error.IMPORT_COLLIDES);
  }
}
