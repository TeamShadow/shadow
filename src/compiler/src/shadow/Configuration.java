package shadow;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import shadow.parser.javacc.ShadowException;

public class Configuration implements Iterator<File> {
	
	// these are the single letter command line args
	private static final String MAIN_CLASS	= "M";
	private static final String CONFIG_FILE = "C";
	private static final String CHECK		= "c";

	private String parentConfig = null; /** The parent configuration from a config file */
	private String mainClass = null; /** The file containing the class with the Main method */
	private List<File> shadowFiles = null;
	private int currentShadowFile = 0;
	private File systemImportPath = null;	/** This is the import path for all the system files */
	private List<File> importPaths = null;
	private boolean checkOnly = false;
	private int arch = -1;
	private String os = null;
	
	private static Configuration config = new Configuration();
	
	private Configuration() {
		this.importPaths = new ArrayList<File>();
	}
	
	/**
	 * Get the singleton instance of the Configuration.
	 * @return The singleton instance.
	 */
	public static Configuration getInstance() {
		return config;
	}

	/**
	 * Parses the command line and sets all of the internal variables.
	 * @param cmdLine The command line passed to the compiler.
	 * @throws ShadowException
	 * @throws ParseException 
	 */
	public boolean parse(CommandLine cmdLine) throws ShadowException {
		
		// see if we have a config file or not
		if(!cmdLine.hasOption(CONFIG_FILE)) {
			// we just parse the built-in parent config file
			// hoping everything else is set on the cmd line
			ConfigParser systemParser = new ConfigParser(this);
			
			systemParser.parse(System.class.getResource("/system.xml"));
		} else {
			// this will recursively parse through the parents
			parseConfigFile(new File(cmdLine.getOptionValue(CONFIG_FILE)));
		}
		
		// by the time we get here, all configs & parents have been parsed
		// we let anything on the command line override the config files
		
		// get the main class
		if(cmdLine.hasOption(MAIN_CLASS))
			mainClass = cmdLine.getOptionValue(MAIN_CLASS);
		
		// see if all we want is to check the file
		checkOnly = cmdLine.hasOption(CHECK);
		
		//
		// TODO: We should probably compile all the files in the local dir
		//       if not specified on the command line
		//
		
		// get all of the files to compile
		shadowFiles = new ArrayList<File>();
		for(String shadowFile:cmdLine.getArgs()) {
			shadowFiles.add(new File(shadowFile));
		}
		
		// set the main class if only one file is specified
		if(shadowFiles.size() == 1)
			mainClass = shadowFiles.get(0).getAbsolutePath();
		
		//
		// Sanity checks
		//
		if(shadowFiles.size() == 0) {
			System.err.println("No source files specified to compile");
			return false;
		}
		
		if(!checkOnly && mainClass == null) {
			System.err.println("Did not specify a main class");
			return false;
		}
		
		if(arch == -1) {
			System.err.println("Did not specify an architecture");
			return false;
		}
		
		if(os == null) {
			System.err.println("Did not specify an OS");
			return false;
		}
		
		if(this.systemImportPath == null) {
			System.err.println("No system import path specified");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Parse a config file, recursively parsing parents when found.
	 * @param configFile The config file to parse
	 */
	private void parseConfigFile(File configFile) {
		ConfigParser parser = new ConfigParser(this);
		
		parser.parse(configFile);
		
		// see if we found a parent or not
		if(this.parentConfig != null) {
			File parent = new File(parentConfig);
			
			// reset the parent
			this.parentConfig = null;
			
			// parse the parent
			parseConfigFile(parent);
		}
	}
	
	/**
	 * Create an Options object to be used to parse the command line.
	 * @return Return options used to parse the command line. 
	 */
	public static Options createCommandLineOptions() {
		Options options = new Options();

		// setup the configuration file option
		@SuppressWarnings("static-access")
		Option mainClass = OptionBuilder.withLongOpt("mainclass")
										.hasArg()
										.withArgName("package.class")
										.withDescription("The class which contains the main method")
										.create(MAIN_CLASS);

		// setup the configuration file option
		@SuppressWarnings("static-access")
		Option configOption = OptionBuilder.withLongOpt("config")
										   .hasArg()
										   .withArgName("configuration.xml")
										   .withDescription("Configuration file")
										   .create(CONFIG_FILE);

		// create the check option
		@SuppressWarnings("static-access")
		Option checkOption = OptionBuilder.withLongOpt("check")
										  .withDescription("Parse and type-check the Shadow files")
										  .create(CHECK);

		// add all the options from above
		options.addOption(mainClass);
		options.addOption(configOption);
		options.addOption(checkOption);

		// add new simple options
		options.addOption(new Option("h", "help", false, "Print this help message"));

		return options;
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public int getArch() {
		return arch;
	}

	public void setArch(int arch) {
		if(this.arch == -1)
			this.arch = arch;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		if(this.os == null)
			this.os = os;
	}

	public List<File> getImports() {
		return importPaths;
	}

	public void addImport(String importPath) {
		this.importPaths.add(new File(importPath));
	}

	public File getSystemImport() {
		return systemImportPath;
	}

	public void setSystemImport(String systemImportPath) {
		if(this.systemImportPath == null)
			this.systemImportPath = new File(systemImportPath);
	}

	public String getParentConfig() {
		return parentConfig;
	}

	public void setParentConfig(String parentConfig) {
		this.parentConfig = parentConfig;
	}

	public boolean isCheckOnly() {
		return checkOnly;
	}

	/**
	 * Returns true if there is another Shadow file.
	 */
	@Override
	public boolean hasNext() {
		if(currentShadowFile == shadowFiles.size())
			return false;
		else
			return true;
	}

	/**
	 * Gets the next Shadow file to compile.
	 */
	@Override
	public File next() {
		return shadowFiles.get(currentShadowFile++);
	}
	
	/**
	 * Gets the current file to compile.
	 * <b>Must call next() at least once before calling this</b>
	 * @return
	 */
	public File current() {
		if(shadowFiles != null)
			return shadowFiles.get(currentShadowFile == 0 ? currentShadowFile : currentShadowFile - 1);
		else
			return null;
	}

	/**
	 * This does nothing as you're not allowed to remove files.
	 */
	@Override
	public void remove() {
	}

	/**
	 * Resets the internal counter for getting Shadow files
	 */
	public void reset() {
		currentShadowFile = 0;
	}

}
