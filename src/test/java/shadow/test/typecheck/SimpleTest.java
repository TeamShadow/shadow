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

		if( System.getProperty("os.name").contains("Windows")) {
			args.add("-c");
			args.add("windows.xml");
		}
		
		//add desired files to list		
		args.add("shadow/utility/Random.shadow"); 		

		Main.main(args.toArray(new String[] { }));		
	}
}
