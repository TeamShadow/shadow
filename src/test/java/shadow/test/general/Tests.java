package shadow.test.general;

import java.io.File;

import shadow.Configuration;

public class Tests {
	/** The location of the shadow/test/ directory */
	public static File testDir = new File(
			Configuration.getCompilerDirectory().getParentFile(),
			"shadow" + File.separator + "test");
	
	/** Returns the platform-adjusted name of the unit test executables */
	public static String properExecutableName(String executableName) {
		if( System.getProperty("os.name").startsWith("Windows") )
			return executableName + ".exe";
		else
			return executableName;
	}
}
