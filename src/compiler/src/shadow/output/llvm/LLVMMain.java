package shadow.output.llvm;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class LLVMMain
{
	public static final boolean DELETE = false, FORCE = false;
	public static void main(String[] args)
	{
		if (DELETE) for (File file : new File("shadow/standard").
				listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".meta");
			}
		})) file.delete();

		List<String> arguments = new ArrayList<String>();
		arguments.add("shadow/standard/Object.shadow");
		arguments.add("shadow/standard/Class.shadow");
		arguments.add("shadow/standard/Array.shadow");
		arguments.add("shadow/standard/String.shadow");
		arguments.add("shadow/standard/MutableString.shadow");
		arguments.add("shadow/standard/Boolean.shadow");
		arguments.add("shadow/standard/Code.shadow");
		arguments.add("shadow/standard/Byte.shadow");
		arguments.add("shadow/standard/UByte.shadow");
		arguments.add("shadow/standard/Short.shadow");
		arguments.add("shadow/standard/UShort.shadow");
		arguments.add("shadow/standard/Int.shadow");
		arguments.add("shadow/standard/UInt.shadow");
		arguments.add("shadow/standard/Long.shadow");
		arguments.add("shadow/standard/ULong.shadow");
		arguments.add("shadow/standard/Float.shadow");
		arguments.add("shadow/standard/Double.shadow");
		arguments.add("shadow/standard/Reference.shadow");
		arguments.add("shadow/standard/System.shadow");
		arguments.add("shadow/io/Console.shadow");
		arguments.add("shadow/test/Test.shadow");
		arguments.add("shadow/test/ConsoleTest.shadow");
		arguments.add("shadow/test/ArrayTest.shadow");
		arguments.add("shadow/test/SortTest.shadow");
		arguments.add("shadow/test/SortMain.shadow");

		if (FORCE)
			for (String file : arguments)
			{
				Main.test(new String[] { "--check", file });
				Main.test(new String[] { file });
			}
		else
		{
			if (DELETE) arguments.add(0, "--check");
			Main.main(arguments.toArray(new String[arguments.size()]));
		}
	}
}
