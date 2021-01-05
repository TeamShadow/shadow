package shadow.test.interpreter;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shadow.Main;

public class InterpreterTests {
	
	private ArrayList<String> args = new ArrayList<String>();

	@BeforeEach
	public void setup() throws Exception {		
		//args.add("-v");
		args.add("--typecheck");

		String os = System.getProperty("os.name").toLowerCase();
		
		args.add("-c");
		if( os.contains("windows") )
			args.add("windows.xml");		
		else if( os.contains("mac") )
			args.add("mac.xml");
		else
			args.add("linux.xml");
	}
	
	@Test public void testManyConstants() throws Exception {
		args.add("tests/interpreter/many-constants/Test.shadow");
		Main.run(args.toArray(new String[] { }));	
	}
	
	@Test public void testUnusualConstantDependency() throws Exception {
		args.add("tests/interpreter/unusual-constant-dependency/Test.shadow");
		Main.run(args.toArray(new String[] { }));	
	}
}
