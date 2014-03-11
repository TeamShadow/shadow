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

	public static void main(String[] unused)
	{
		ArrayList<String> args = new ArrayList<String>();
		args.add("--typecheck");
		args.add("--config");
		
		String osName = System.getProperty("os.name");
		
		if(osName.startsWith("Windows"))
			args.add("src/test_windows_config.xml");
		else
			args.add("src/test_linux_config.xml");		

		// set the levels of our loggers
		Loggers.SHADOW.setLevel(Level.DEBUG);
		Loggers.TYPE_CHECKER.setLevel(Level.INFO);
		Loggers.PARSER.setLevel(Level.INFO);	
		
		//add desired files to list
		//args.add("--compile");
		args.add("shadow/test/Cheetah.shadow"); 		
		//args.add("tests/compile/Readonly.shadow");
		Main.main(args.toArray(new String[] { }));		
	}
}
