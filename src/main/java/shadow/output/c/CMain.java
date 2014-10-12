package shadow.output.c;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import shadow.ConfigurationException;

public class CMain
{
	public static void main(String[] args) throws MalformedURLException, ConfigurationException
	{
		List<String> arguments = new ArrayList<String>();

		arguments.add("../Shadow/shadow/standard/Object.shadow");

		Main.main(arguments.toArray(new String[arguments.size()]));
	}
}
