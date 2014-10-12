package shadow.test.output;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;

public class TACTest {
	
	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		//args.add("--check");
		args.add("--config");
		
		String osName = System.getProperty("os.name");
		
		if(osName.startsWith("Windows"))
			args.add("shadow-windows-32.xml");
		else
			args.add("shadow-linux-64.xml");

		// set the levels of our loggers
		Loggers.SHADOW.setLevel(Level.INFO);
		Loggers.TYPE_CHECKER.setLevel(Level.OFF);
		Loggers.PARSER.setLevel(Level.OFF);
	}	
		
	@Test public void testArrayList() throws Exception {
		args.add("shadow/test/ArrayListTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testArrayDeque() throws Exception {
		args.add("shadow/test/ArrayDequeTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	
	@Test public void testLinkedList() throws Exception {
		args.add("shadow/test/LinkedListTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}	
	
	
	@Test public void testArray() throws Exception {
		args.add("shadow/test/ArrayTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}	
	
	@Test public void testChild() throws Exception {
		args.add("shadow/test/ChildTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testCommandLine() throws Exception {
		args.add("shadow/test/CommandLine.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testConsole() throws Exception {
		args.add("shadow/test/ConsoleTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testException() throws Exception {
		args.add("shadow/test/ExceptionTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testFile() throws Exception {
		args.add("shadow/test/FileTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testInterface() throws Exception {
		args.add("shadow/test/InterfaceTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testLoop() throws Exception {
		args.add("shadow/test/LoopTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testSort() throws Exception {
		args.add("shadow/test/SortMain.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testTest() throws Exception {
		args.add("shadow/test/Test.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testTry() throws Exception {
		args.add("shadow/test/TryTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testToughTry() throws Exception {
		args.add("shadow/test/ToughTry.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testMethodOperations() throws Exception {
		args.add("shadow/test/MethodOperations.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testPrimitive() throws Exception {
		args.add("shadow/test/PrimitiveTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testMatrix() throws Exception {
		args.add("shadow/test/MatrixTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testComplex() throws Exception {
		args.add("shadow/test/ComplexTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testEcho() throws Exception {
		args.add("shadow/test/Echo.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testBigInteger() throws Exception {
		args.add("shadow/test/BigIntegerTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testDouble() throws Exception {
		args.add("shadow/test/DoubleTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testArrayAsObject() throws Exception {
		args.add("shadow/test/ArrayAsObject.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testGeneric() throws Exception {
		args.add("shadow/test/GenericTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testSubscript() throws Exception {
		args.add("shadow/test/SubscriptTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testProperty() throws Exception {
		args.add("shadow/test/PropertyTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testForeach() throws Exception {
		args.add("shadow/test/ForeachTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testBasic() throws Exception {
		args.add("shadow/test/BasicTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testSwitch() throws Exception {
		args.add("shadow/test/SwitchTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testMath() throws Exception {
		args.add("shadow/test/MathTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	
	@Test public void testString() throws Exception {
		args.add("shadow/test/StringTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testAddressMap() throws Exception {
		args.add("shadow/test/AddressMapTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testCopy() throws Exception {
		args.add("shadow/test/CopyTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}	
	
	@Test public void testArrayOutOfBounds() throws Exception {
		args.add("shadow/test/ArrayOutOfBoundsTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testIs() throws Exception {
		args.add("shadow/test/IsTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	
	@Test public void testCastException() throws Exception {
		args.add("shadow/test/CastExceptionTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testAssert() throws Exception {
		args.add("shadow/test/AssertTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
}
