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

		//args.add("-v");
		args.add("--typecheck");

		String os = System.getProperty("os.name").toLowerCase();
		
		if( os.contains("windows") ) {
			args.add("-c");
			args.add("windows.xml");
		}
		else if( os.contains("mac") ) {
			args.add("-c");
			args.add("mac.xml");
		}
		
		//add desired files to list		
		args.add("tests-negative/parser/plus-plus/Test.shadow");

		Main.main(args.toArray(new String[] { }));		
	}
}
