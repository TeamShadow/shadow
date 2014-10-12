package shadow.test.typecheck;

import java.util.ArrayList;

import org.apache.commons.logging.impl.SimpleLog;
import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;

public class ShadowUtilityTest {
	
	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		args.add("--typecheck");
		args.add("--config");
		
		String osName = System.getProperty("os.name");
		
		if(osName.startsWith("Windows"))
			args.add("shadow-windows-32.xml");
		else
			args.add("shadow-linux-64.xml");

		// set the levels of our loggers
		Loggers.SHADOW.setLevel(Level.DEBUG);
		Loggers.TYPE_CHECKER.setLevel(Level.INFO);
		Loggers.PARSER.setLevel(Level.INFO);
	}	

	 @Test public void testArrayList() throws Exception {		
	
		args.add("shadow/utility/ArrayList.shadow");
		Main.run(args.toArray(new String[] { }));
	
	}

	@Test public void testHashSet() throws Exception {
		args.add("shadow/utility/HashSet.shadow");
		Main.run(args.toArray(new String[] { }));
	}

	 @Test public void testIllegalModificationException() throws Exception {
		args.add("shadow/utility/IllegalModificationException.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	 
	@Test public void testLinkedList() throws Exception {
		args.add("shadow/utility/LinkedList.shadow");
		Main.run(args.toArray(new String[] { }));
	}

	@Test public void testList() throws Exception {
		args.add("shadow/utility/List.shadow");
		Main.run(args.toArray(new String[] { }));
	}

	@Test public void testRandom() throws Exception {
		args.add("shadow/utility/Random.shadow");
		Main.run(args.toArray(new String[] { }));
	}

	@Test public void testSet() throws Exception {
		args.add("shadow/utility/Set.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testArrayDeque() throws Exception {
		args.add("shadow/utility/ArrayDeque.shadow");
		Main.run(args.toArray(new String[] { }));
	}
}
