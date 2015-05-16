package shadow.test.typecheck;

import java.util.ArrayList;

import org.junit.Test;

import shadow.Main;
import shadow.typecheck.TypeCheckException.Error;

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
		args.add("tests-negative/typechecker/break-outside-loop/Test.shadow");

		Main.main(args.toArray(new String[] { }));		
	}
}
