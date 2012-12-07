package shadow.output.llvm;

import java.util.ArrayList;
import java.util.List;

public class LLVMMain
{
	public static void main(String[] args)
	{
		List<String> arguments = new ArrayList<String>();

		arguments.add("shadow/standard/Object.shadow");
		arguments.add("shadow/standard/Class.shadow");
		arguments.add("shadow/standard/Interface.shadow");
		arguments.add("shadow/standard/Array.shadow");
		arguments.add("shadow/standard/String.shadow");
		arguments.add("shadow/io/Console.shadow");
		arguments.add("shadow/test/Test.shadow");
		arguments.add("shadow/test/ConsoleTest.shadow");

		Main.main(arguments.toArray(new String[arguments.size()]));
	}
}
