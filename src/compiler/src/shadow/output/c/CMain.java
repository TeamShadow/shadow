package shadow.output.c;

import java.util.ArrayList;
import java.util.List;

public class CMain
{
	public static void main(String[] args)
	{
		List<String> arguments = new ArrayList<String>();

		arguments.add("../Shadow/shadow/standard/Object.shadow");

		Main.main(arguments.toArray(new String[arguments.size()]));
	}
}