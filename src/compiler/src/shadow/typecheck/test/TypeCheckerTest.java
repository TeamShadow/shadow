package shadow.typecheck.test;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;

public class TypeCheckerTest {
	
	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		args.add("--check");
		args.add("--config");
		
		String osName = System.getProperty("os.name");
		
		if(osName.startsWith("Windows")) {
			args.add("src/test_windows_config.xml");
		} else {
			args.add("src/test_linux_config.xml");
		}

		// set the levels of our loggers
		Loggers.setLoggerToLevel(Loggers.SHADOW, Level.DEBUG);
		Loggers.setLoggerToLevel(Loggers.TYPE_CHECKER, Level.TRACE);
	}
	
	 @Test public void testArray() throws Exception {
		
		Loggers.setAllToDebug();
		/*Level level = Loggers.getLoggerLevel(Loggers.PARSER);
		Loggers.setLoggerToLevel(Loggers.TYPE_CHECKER, Level.OFF);
		Loggers.setLoggerToLevel(Loggers.PARSER, Level.DEBUG);*/	
		//args.add("shadow/standard/Parsable.shadow");
		args.add("tests/compile/Array.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
		/*Loggers.setLoggerToLevel(Loggers.PARSER, level);
		Loggers.setLoggerToLevel(Loggers.TYPE_CHECKER, Level.DEBUG);*/
	}

	@Test public void testArrays() throws Exception {
		args.add("tests/compile/Arrays.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	 @Test public void testAssertion() throws Exception {
		args.add("tests/compile/Assertion.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	 
	@Test public void testAuto() throws Exception {
		args.add("tests/compile/Auto.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testBasic() throws Exception {
		args.add("tests/compile/Basic.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testBill() throws Exception {
		args.add("tests/compile/Bill.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testCast() throws Exception {
		args.add("tests/compile/Cast.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testCasting() throws Exception {
		args.add("tests/compile/Casting.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testChild() throws Exception {
		args.add("tests/compile/Child.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
		
		@Test public void testCoalesce() throws Exception {
		args.add("tests/compile/Coalesce.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testComplexAccess() throws Exception {
		args.add("tests/compile/ComplexAccess.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testConditionals() throws Exception {
		args.add("tests/compile/Conditionals.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testConstructor() throws Exception {
		args.add("tests/compile/Constructor.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testControlFlow() throws Exception {
		args.add("tests/compile/ControlFlow.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testFinal() throws Exception {
		args.add("tests/compile/Final.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testFoo() throws Exception {
		args.add("tests/compile/Foo.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testFor() throws Exception {
		args.add("tests/compile/For.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testForeach() throws Exception {
		args.add("tests/compile/Foreach.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testGrandchild() throws Exception {
		args.add("tests/compile/Grandchild.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testIf() throws Exception {
		args.add("tests/compile/If.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testImports() throws Exception {
		args.add("tests/compile/Imports.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testInnerClass() throws Exception {
		args.add("tests/compile/InnerClass.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testInterface() throws Exception {
		args.add("tests/compile/Interface.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testLValue() throws Exception {
		args.add("tests/compile/LValue.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testMember() throws Exception {
		args.add("tests/compile/Member.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testMethod() throws Exception {
		args.add("tests/compile/Method.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testMethodAndField() throws Exception {
		args.add("tests/compile/MethodAndField.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testMethodCall() throws Exception {
		args.add("tests/compile/MethodCall.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testMethodCalls() throws Exception {
		args.add("tests/compile/MethodCalls.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testOperations() throws Exception {
		args.add("tests/compile/Operations.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testOutsideProperty() throws Exception {
		args.add("tests/compile/OutsideProperty.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testParent() throws Exception {
		args.add("tests/compile/Parent.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testPrivate() throws Exception {
		args.add("tests/compile/Private.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testProperty() throws Exception {
		args.add("tests/compile/Property.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testScope() throws Exception {
		args.add("tests/compile/Scope.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}


	@Test public void testSequences() throws Exception {
		args.add("tests/compile/Sequences.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testStatic() throws Exception {
		args.add("tests/compile/Static.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testSubtype() throws Exception {
		args.add("tests/compile/Subtype.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testSwitch() throws Exception {
		args.add("tests/compile/Switch.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testTest() throws Exception {
		args.add("tests/compile/Test.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testVariableDeclaration() throws Exception {
		args.add("tests/compile/VariableDeclaration.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testView() throws Exception {
		args.add("tests/compile/View.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

}
