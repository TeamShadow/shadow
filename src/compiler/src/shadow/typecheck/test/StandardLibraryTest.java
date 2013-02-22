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

public class StandardLibraryTest {
	
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
		Loggers.setLoggerToLevel(Loggers.TYPE_CHECKER, Level.INFO);
		Loggers.setLoggerToLevel(Loggers.PARSER, Level.INFO);
	}
	
	 @Test public void testArray() throws Exception {
		
	
		args.add("shadow/standard/Array.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	
	}

	@Test public void testBoolean() throws Exception {
		args.add("shadow/standard/Boolean.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	 @Test public void testByte() throws Exception {
		args.add("shadow/standard/Byte.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	 
	@Test public void testCanCompare() throws Exception {
		args.add("shadow/standard/CanCompare.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testCanHash() throws Exception {
		args.add("shadow/standard/CanHash.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testCanIndex() throws Exception {
		args.add("shadow/standard/CanIndex.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testCanIterate() throws Exception {
		args.add("shadow/standard/CanIterate.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testClass() throws Exception {
		args.add("shadow/standard/Class.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testCode() throws Exception {
		args.add("shadow/standard/Code.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
		
		@Test public void testDouble() throws Exception {
		args.add("shadow/standard/Double.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testEnum() throws Exception {
		args.add("shadow/standard/Enum.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testError() throws Exception {
		args.add("shadow/standard/Error.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testException() throws Exception {
		args.add("shadow/standard/Exception.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testFloat() throws Exception {
		args.add("shadow/standard/Float.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testIllegalArgumentException() throws Exception {
		args.add("shadow/standard/IllegalArgumentException.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testIndexOutOfBoundsException() throws Exception {
		args.add("shadow/standard/IndexOutOfBoundsException.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testInt() throws Exception {
		args.add("shadow/standard/Int.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testIterator() throws Exception {
		args.add("shadow/standard/Iterator.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

		@Test public void testLong() throws Exception {
		args.add("shadow/standard/Long.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testMutableString() throws Exception {
		args.add("shadow/standard/MutableString.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testNumber() throws Exception {
		args.add("shadow/standard/Number.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testObject() throws Exception {
		args.add("shadow/standard/Object.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testReference() throws Exception {
		args.add("shadow/standard/Reference.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testShort() throws Exception {
		args.add("shadow/standard/Short.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testString() throws Exception {
		args.add("shadow/standard/String.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testSystem() throws Exception {
		args.add("shadow/standard/System.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testUByte() throws Exception {
		args.add("shadow/standard/Ubyte.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testUInt() throws Exception {
		args.add("shadow/standard/UInt.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testULong() throws Exception {
		args.add("shadow/standard/ULong.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testUnexpectedNullException() throws Exception {
		args.add("shadow/standard/UnexpectedNullException.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testUShort() throws Exception {
		args.add("shadow/standard/UShort.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testOutsideProperty() throws Exception {
		args.add("shadow/standard/OutsideProperty.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testPair() throws Exception {
		args.add("shadow/standard/Pair.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testPairUser() throws Exception {
		args.add("shadow/standard/PairUser.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testParent() throws Exception {
		args.add("shadow/standard/Parent.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testPrivate() throws Exception {
		args.add("shadow/standard/Private.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testProperty() throws Exception {
		args.add("shadow/standard/Property.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testScope() throws Exception {
		args.add("shadow/standard/Scope.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}


	@Test public void testSequences() throws Exception {
		args.add("shadow/standard/Sequences.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testSingleton() throws Exception {
		args.add("shadow/standard/Singleton.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	
	@Test public void testUseSingleton() throws Exception {
		args.add("shadow/standard/UseSingleton.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	/*
	@Test public void testStatic() throws Exception {
		args.add("shadow/standard/Static.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	*/

	@Test public void testSubtype() throws Exception {
		args.add("shadow/standard/Subtype.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testSwitch() throws Exception {
		args.add("shadow/standard/Switch.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testTest() throws Exception {
		args.add("shadow/standard/Test.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testVariableDeclaration() throws Exception {
		args.add("shadow/standard/VariableDeclaration.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testView() throws Exception {
		args.add("shadow/standard/View.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

}
