package shadow.typecheck.test;

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

	public static void main(String[] unused)
	{
		ArrayList<String> args = new ArrayList<String>();
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
		Loggers.setLoggerToLevel(Loggers.TYPE_CHECKER, Level.DEBUG);
		
		
		//add desired files to list
		//args.add("--compile");
		args.add("pretesting/Checking.shadow"); 		
		int error = Main.test(args.toArray(new String[] { }));
		System.out.println("Error code: " + error);
	}

}
