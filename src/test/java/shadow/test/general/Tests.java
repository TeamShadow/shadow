package shadow.test.general;


public class Tests {	
	/** Returns the platform-adjusted name of the unit test executables */
	public static String properExecutableName(String executableName) {
		if( System.getProperty("os.name").startsWith("Windows") )
			return executableName + ".exe";
		else
			return executableName;
	}
}
