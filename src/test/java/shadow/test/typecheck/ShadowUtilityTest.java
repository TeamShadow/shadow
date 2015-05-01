package shadow.test.typecheck;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shadow.Main;

public class ShadowUtilityTest {
	
	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setup() throws Exception {
		args.add("-v");
		args.add("--typecheck");
		
		if( System.getProperty("os.name").contains("Windows")) {
			args.add("-c");
			args.add("windows.xml");
		}
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
