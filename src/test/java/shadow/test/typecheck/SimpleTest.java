package shadow.test.typecheck;

import java.util.ArrayList;

import shadow.Main;

/**
 * 
 * @author Barry Wittman
 * This class is designed to execute simple tests during development.
 * The full JUnit suite is sometimes unwieldy.
 *   
 */

public class SimpleTest
{

	public static void main(String[] unused) {
		
		ArrayList<String> args = new ArrayList<String>();

		//args.add("-i");
		args.add("--typecheck");

		String os = System.getProperty("os.name").toLowerCase();
		
		args.add("-c");
		if( os.contains("windows") )
			args.add("windows.xml");		
		else if( os.contains("mac") )
			args.add("mac.xml");
		else
			args.add("linux.xml");
		
		//add desired files to list		
		//args.add("tests-negative/typechecker/dead-code/Test.shadow");
		args.add("tests-negative/parser/missing-left-brace/Test.shadow");
		//args.add("shadow/test/Matrix.shadow");

		Main.main(args.toArray(new String[] { }));		
	}
}
