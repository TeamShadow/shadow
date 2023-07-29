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
    args.add("tests/compile/AnonymousInlineMethod.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testArray() throws Exception {
    args.add("tests/compile/Array.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testArrays() throws Exception {
    args.add("tests/compile/Arrays.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testAssertion() throws Exception {
    args.add("tests/compile/Assertion.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testVar() throws Exception {
    args.add("tests/compile/Var.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testBasic() throws Exception {
    args.add("tests/compile/Basic.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testBill() throws Exception {
    args.add("tests/compile/Bill.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testCast() throws Exception {
    args.add("tests/compile/Cast.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testCasting() throws Exception {
    args.add("tests/compile/Casting.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testChild() throws Exception {
    args.add("tests/compile/Child.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testCoalesce() throws Exception {
    args.add("tests/compile/Coalesce.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testComplexAccess() throws Exception {
    args.add("tests/compile/ComplexAccess.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testComplexCreate() throws Exception {
    args.add("tests/compile/ComplexCreate.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testConditionals() throws Exception {
    args.add("tests/compile/Conditionals.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testCreate() throws Exception {
    args.add("tests/compile/Create.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testControlFlow() throws Exception {
    args.add("tests/compile/ControlFlow.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testDeclareInSequence() throws Exception {
    args.add("tests/compile/DeclareInSequence.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testFoo() throws Exception {
    args.add("tests/compile/Foo.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testFor() throws Exception {
    args.add("tests/compile/For.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testForeach() throws Exception {
    args.add("tests/compile/Foreach.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testGenericInterface() throws Exception {
    args.add("tests/compile/GenericInterface.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testGrandchild() throws Exception {
    args.add("tests/compile/Grandchild.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testIf() throws Exception {
    args.add("tests/compile/If.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testImports() throws Exception {
    args.add("tests/compile/Imports.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testIndex1() throws Exception {
    args.add("tests/compile/Index1.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testIndex2() throws Exception {
    args.add("tests/compile/Index2.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testIndexTest() throws Exception {
    args.add("tests/compile/IndexTest.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testInnerClass() throws Exception {
    args.add("tests/compile/InnerClass.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testInterface() throws Exception {
    args.add("tests/compile/Interface.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  @Disabled // Needs support for local methods
  public void testLocalMethod() throws Exception {
    args.add("tests/compile/LocalMethod.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testLValue() throws Exception {
    args.add("tests/compile/LValue.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testMatrix() throws Exception {
    args.add("tests/compile/Matrix.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testMatrixTest() throws Exception {
    args.add("tests/compile/MatrixTest.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testMember() throws Exception {
    args.add("tests/compile/Member.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testMethod() throws Exception {
    args.add("tests/compile/Method.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testMethodAndField() throws Exception {
    args.add("tests/compile/MethodAndField.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testMethodCall() throws Exception {
    args.add("tests/compile/MethodCall.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testMethodCalls() throws Exception {
    args.add("tests/compile/MethodCalls.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testOperations() throws Exception {
    args.add("tests/compile/Operations.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testOutsideProperty() throws Exception {
    args.add("tests/compile/OutsideProperty.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testPair() throws Exception {
    args.add("tests/compile/Pair.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testPairUser() throws Exception {
    args.add("tests/compile/PairUser.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testParent() throws Exception {
    args.add("tests/compile/Parent.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testPrivate() throws Exception {
    args.add("tests/compile/Private.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testProperty() throws Exception {
    args.add("tests/compile/Property.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testReadonly() throws Exception {
    args.add("tests/compile/Readonly.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testScope() throws Exception {
    args.add("tests/compile/Scope.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testSequences() throws Exception {
    args.add("tests/compile/Sequences.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testSingleton() throws Exception {
    args.add("tests/compile/Singleton.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testUseSingleton() throws Exception {
    args.add("tests/compile/UseSingleton.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testSubtype() throws Exception {
    args.add("tests/compile/Subtype.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testSwitch() throws Exception {
    args.add("tests/compile/Switch.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testTest() throws Exception {
    args.add("tests/compile/Test.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testVariableDeclaration() throws Exception {
    args.add("tests/compile/VariableDeclaration.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testView() throws Exception {
    args.add("tests/compile/View.shadow");
    Main.run(args.toArray(new String[] {}));
  }

  @Test
  public void testAwkwardBreaks() throws Exception {
    args.add("tests/compile/AwkwardBreaks.shadow");
    Main.run(args.toArray(new String[] {}));
  }
}
