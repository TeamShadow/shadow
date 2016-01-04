package shadow.test.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shadow.Job;
import shadow.Main;

public class TACTest {
	
	// To simplify removal, every unit test executable will have the same name
	private static final String executableName = Job.properExecutableName("TACTest");
	private static final Path executable = Paths.get("shadow", "test", executableName);
	
	private ArrayList<String> args = new ArrayList<String>();
	
	@Before
	public void setup() throws Exception {		
		args.add("-o");
		args.add(executableName);
		
		String os = System.getProperty("os.name").toLowerCase();
		
		if( os.contains("windows") ) {
			args.add("-c");
			args.add("windows.xml");
		}
		else if( os.contains("mac") ) {
			args.add("-c");
			args.add("mac.xml");
		}
	}
	
	@After
	public void cleanup() throws IOException {
		
		// To to remove the unit test executable
		try {
			Files.delete(executable);
		}
		catch(Exception e) {}
	}
	
	@Test public void testArrayDeque() throws Exception {
		args.add("shadow/test/ArrayDequeTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	
	@Test public void testLinkedList() throws Exception {
		args.add("shadow/test/LinkedListTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testCommandLine() throws Exception {
		args.add("shadow/test/CommandLine.shadow");
		Main.run(args.toArray(new String[] { }));
	}	

	@Test public void testSort() throws Exception {
		args.add("shadow/test/SortMain.shadow");
		Main.run(args.toArray(new String[] { }));
	}	

	@Test public void testMethodOperations() throws Exception {
		args.add("shadow/test/MethodOperations.shadow");
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
	
	@Test public void testDouble() throws Exception {
		args.add("shadow/test/DoubleTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testFloat() throws Exception {
		args.add("shadow/test/FloatTest.shadow");
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
	

	@Test public void testArrayInitializer() throws Exception {
		args.add("shadow/test/ArrayInitializerTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testRandom() throws Exception {
		args.add("shadow/test/RandomTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testNullablePrimitive() throws Exception {
		args.add("shadow/test/NullablePrimitiveTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testNullableArray() throws Exception {
		args.add("shadow/test/NullableArrayTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testGenericArray() throws Exception {
		args.add("shadow/test/GenericArrayTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}	
	
	@Test public void testMutableString() throws Exception {
		args.add("shadow/test/MutableStringTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}

	@Test public void testCanCreate() throws Exception {
		args.add("shadow/test/CanCreateTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}

	@Test public void testHashSet() throws Exception {
		args.add("shadow/test/HashSetTest.shadow");
		Main.run(args.toArray(new String[] { }));		
	}
}
