package shadow.test.typecheck;

import java.util.ArrayList;

import org.apache.log4j.Level;

import shadow.Loggers;
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

		// set the levels of our loggers
		Loggers.SHADOW.setLevel(Level.DEBUG);
		Loggers.TYPE_CHECKER.setLevel(Level.INFO);
		Loggers.PARSER.setLevel(Level.ALL);	
		
		args.add("-v");
		args.add("--typecheck");

		if( System.getProperty("os.name").contains("Windows")) {
			args.add("-c");
			args.add("windows.xml");
		}
		
		//add desired files to list		
		args.add("tests-negative/typechecker/no-default-constructor-for-array/Test.shadow"); 		

		Main.main(args.toArray(new String[] { }));		
	}
}
