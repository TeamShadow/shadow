package shadow.test;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Class that runs all JUnit tests.
 */
@Suite
@SuiteDisplayName("Full Shadow Test Suite")
@SelectPackages({"shadow.test.doctool", "shadow.test.interpreter", "shadow.test.output", "shadow.test.parse", "shadow.test.typecheck" })
public class AllTest {
}
