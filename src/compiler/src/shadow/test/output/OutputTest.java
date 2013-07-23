package shadow.test.output;

import static junit.framework.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;

public class OutputTest {
	
	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		args.add("shadow/standard/Object.shadow");
		args.add("shadow/standard/Class.shadow");
		args.add("shadow/standard/Array.shadow");
		args.add("shadow/standard/String.shadow");
		args.add("shadow/standard/MutableString.shadow");
		args.add("shadow/standard/Boolean.shadow");
		args.add("shadow/standard/Code.shadow");
		args.add("shadow/standard/Byte.shadow");
		args.add("shadow/standard/UByte.shadow");
		args.add("shadow/standard/Short.shadow");
		args.add("shadow/standard/UShort.shadow");
		args.add("shadow/standard/Int.shadow");
		args.add("shadow/standard/UInt.shadow");
		args.add("shadow/standard/Long.shadow");
		args.add("shadow/standard/ULong.shadow");
		args.add("shadow/standard/Float.shadow");
		args.add("shadow/standard/Double.shadow");
		args.add("shadow/standard/Number.shadow");
		args.add("shadow/standard/Reference.shadow");
		args.add("shadow/standard/System.shadow");
		args.add("shadow/standard/Exception.shadow");
		args.add("shadow/standard/IllegalArgumentException.shadow");
		args.add("shadow/standard/IndexOutOfBoundsException.shadow");
		args.add("shadow/standard/UnexpectedNullException.shadow");
		args.add("shadow/io/Console.shadow");

		args.add("shadow/utility/Random.shadow");
		args.add("shadow/utility/List.shadow");
		args.add("shadow/utility/Set.shadow");
		args.add("shadow/utility/ArrayList.shadow");
		args.add("shadow/utility/LinkedList.shadow");
//		args.add("shadow/utility/HashSet.shadow");
		args.add("shadow/utility/IllegalModificationException.shadow");
		
		args.add("--config");
		
		String osName = System.getProperty("os.name");
		
		if(osName.startsWith("Windows")) {
			args.add("src/test_windows_config.xml");
		} else {
			args.add("src/test_linux_config.xml");
		}

		// set the levels of our loggers
		Loggers.setLoggerToLevel(Loggers.SHADOW, Level.OFF);
		Loggers.setLoggerToLevel(Loggers.TYPE_CHECKER, Level.OFF);
		Loggers.setLoggerToLevel(Loggers.PARSER, Level.OFF);
	}	
	
	private void run(String[] programArgs, String expectedOutput) throws IOException {
		List<String> programCommand = new ArrayList<String>();
		String osName = System.getProperty("os.name");
		if(osName.startsWith("Windows")) {
			programCommand.add("a.exe");
		} else {
			programCommand.add("a.out");
		}
		for (String arg : programArgs)
			programCommand.add(arg);
		Process program = new ProcessBuilder(programCommand).start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(program.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line;
		do {
			line = reader.readLine();
			if (line != null)
				builder.append(line).append('\n');
		} while (line != null);
		assertEquals(builder.toString(), expectedOutput);
	}
	
	@Test public void testTest() throws Exception {
		args.add(0, "shadow/test/Test.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"shadow.test@Test\n" +
				"3\n");
	}

	
}
