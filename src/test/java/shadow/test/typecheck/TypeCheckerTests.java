package shadow.test.typecheck;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import shadow.Configuration;
import shadow.Main;

import java.util.ArrayList;

public class TypeCheckerTests {

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
  @Disabled // Needs support for inline methods
  public void testAnonymousInlineMethod() throws Exception {
    args.add("tests/typechecker/AnonymousInlineMethod.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testArray() throws Exception {
    args.add("tests/typechecker/Array.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testArrays() throws Exception {
    args.add("tests/typechecker/Arrays.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testAssertion() throws Exception {
    args.add("tests/typechecker/Assertion.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testSomeImportsUsed() throws Exception {
    args.add("tests/typechecker/SomeImportsUsed.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testVar() throws Exception {
    args.add("tests/typechecker/Var.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testBasic() throws Exception {
    args.add("tests/typechecker/Basic.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testBill() throws Exception {
    args.add("tests/typechecker/Bill.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testCast() throws Exception {
    args.add("tests/typechecker/Cast.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testCasting() throws Exception {
    args.add("tests/typechecker/Casting.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testChild() throws Exception {
    args.add("tests/typechecker/Child.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testCoalesce() throws Exception {
    args.add("tests/typechecker/Coalesce.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testComplexAccess() throws Exception {
    args.add("tests/typechecker/ComplexAccess.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testComplexCreate() throws Exception {
    args.add("tests/typechecker/ComplexCreate.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testConditionals() throws Exception {
    args.add("tests/typechecker/Conditionals.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testCreate() throws Exception {
    args.add("tests/typechecker/Create.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testControlFlow() throws Exception {
    args.add("tests/typechecker/ControlFlow.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testDeclareInSequence() throws Exception {
    args.add("tests/typechecker/DeclareInSequence.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testFoo() throws Exception {
    args.add("tests/typechecker/Foo.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testFor() throws Exception {
    args.add("tests/typechecker/For.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testForeach() throws Exception {
    args.add("tests/typechecker/Foreach.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testGenericInterface() throws Exception {
    args.add("tests/typechecker/GenericInterface.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testGrandchild() throws Exception {
    args.add("tests/typechecker/Grandchild.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testIf() throws Exception {
    args.add("tests/typechecker/If.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testImports() throws Exception {
    args.add("tests/typechecker/Imports.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testIndex1() throws Exception {
    args.add("tests/typechecker/Index1.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testIndex2() throws Exception {
    args.add("tests/typechecker/Index2.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testIndexTest() throws Exception {
    args.add("tests/typechecker/IndexTest.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testInnerClass() throws Exception {
    args.add("tests/typechecker/InnerClass.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testInterface() throws Exception {
    args.add("tests/typechecker/Interface.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  @Disabled // Needs support for local methods
  public void testLocalMethod() throws Exception {
    args.add("tests/typechecker/LocalMethod.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testLValue() throws Exception {
    args.add("tests/typechecker/LValue.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testMatrix() throws Exception {
    args.add("tests/typechecker/Matrix.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testMatrixTest() throws Exception {
    args.add("tests/typechecker/MatrixTest.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testMember() throws Exception {
    args.add("tests/typechecker/Member.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testMethod() throws Exception {
    args.add("tests/typechecker/Method.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testMethodAndField() throws Exception {
    args.add("tests/typechecker/MethodAndField.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testMethodCall() throws Exception {
    args.add("tests/typechecker/MethodCall.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testMethodCalls() throws Exception {
    args.add("tests/typechecker/MethodCalls.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testOperations() throws Exception {
    args.add("tests/typechecker/Operations.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testOutsideProperty() throws Exception {
    args.add("tests/typechecker/OutsideProperty.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testPair() throws Exception {
    args.add("tests/typechecker/Pair.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testPairUser() throws Exception {
    args.add("tests/typechecker/PairUser.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testParent() throws Exception {
    args.add("tests/typechecker/Parent.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testPrivate() throws Exception {
    args.add("tests/typechecker/Private.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testProperty() throws Exception {
    args.add("tests/typechecker/Property.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testReadonly() throws Exception {
    args.add("tests/typechecker/Readonly.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testScope() throws Exception {
    args.add("tests/typechecker/Scope.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testSequences() throws Exception {
    args.add("tests/typechecker/Sequences.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testSingleton() throws Exception {
    args.add("tests/typechecker/Singleton.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testUseSingleton() throws Exception {
    args.add("tests/typechecker/UseSingleton.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testSubtype() throws Exception {
    args.add("tests/typechecker/Subtype.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testSwitch() throws Exception {
    args.add("tests/typechecker/Switch.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testTest() throws Exception {
    args.add("tests/typechecker/Test.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testVariableDeclaration() throws Exception {
    args.add("tests/typechecker/VariableDeclaration.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testView() throws Exception {
    args.add("tests/typechecker/View.shadow");
    new Main(args.toArray(new String[] {})).run();
  }

  @Test
  public void testAwkwardBreaks() throws Exception {
    args.add("tests/typechecker/AwkwardBreaks.shadow");
    new Main(args.toArray(new String[] {})).run();
  }
}
