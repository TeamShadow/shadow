package shadow.output.llvm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import shadow.Loggers;

public class LLVMMain
{
	public static void main(String[] args)
	{
		List<String> arguments = new ArrayList<String>();

		arguments.add("shadow/standard/Object.shadow");
		arguments.add("shadow/standard/Class.shadow");
		arguments.add("shadow/standard/Array.shadow");
		arguments.add("shadow/standard/String.shadow");
		arguments.add("shadow/io/Console.shadow");
		arguments.add("shadow/test/Test.shadow");
		arguments.add("shadow/test/ConsoleTest.shadow");

		// set the levels of our loggers
		// Loggers.setLoggerToLevel(Loggers.TYPE_CHECKER, Level.TRACE);

		Main.main(arguments.toArray(new String[arguments.size()]));
	}
}
