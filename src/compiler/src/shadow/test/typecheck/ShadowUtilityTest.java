package shadow.test.typecheck;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;
import shadow.typecheck.type.Type;

public class ShadowUtilityTest {
	
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

	 @Test public void testArrayList() throws Exception {		
	
		args.add("shadow/utility/ArrayList.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	
	}

	@Test public void testHashSet() throws Exception {
		args.add("shadow/utility/HashSet.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	 @Test public void testIllegalModificationException() throws Exception {
		args.add("shadow/utility/IllegalModificationException.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
	 
	@Test public void testLinkedList() throws Exception {
		args.add("shadow/utility/LinkedList.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testList() throws Exception {
		args.add("shadow/utility/List.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testRandom() throws Exception {
		args.add("shadow/utility/Random.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}

	@Test public void testSet() throws Exception {
		args.add("shadow/utility/Set.shadow");
		assertEquals(0, Main.test(args.toArray(new String[] { })));
	}
}
