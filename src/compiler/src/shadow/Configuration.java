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
	private static final String IMPORTS 	= "I";
	private static final String CONFIG_FILE = "C";
	private static final String CHECK		= "c";

	private String mainClass = null;
	private List<File> shadowFiles = null;
	private int currentShadowFile = 0;
	private List<File> importPaths = null;
	private boolean checkOnly = false;
	
	private static Configuration config = new Configuration();
	
	public Configuration() {
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
	public void parse(CommandLine cmdLine) throws ShadowException {
		// get the main class
		mainClass = cmdLine.getOptionValue(MAIN_CLASS);
		
		// get the import paths
		importPaths = new ArrayList<File>();
		
		if(cmdLine.hasOption("IMPORTS")) {
				for(String importPath:cmdLine.getOptionValues(IMPORTS))
					importPaths.add(new File(importPath));
		}
		
		//
		// TODO: Get all of the configuration information from an XML config file
		//
		
		// see if all we want is to check the file
		checkOnly = cmdLine.hasOption(CHECK);
		
		// get all of the files to compile
		shadowFiles = new ArrayList<File>();
		for(String shadowFile:cmdLine.getArgs()) {
			shadowFiles.add(new File(shadowFile));
		}
		
		// perform the sanity checks here
		if(!checkOnly && mainClass == null) {
			throw new ShadowException("Compilation requested, but main class not specified.");
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
		Option importPaths = OptionBuilder.withLongOpt("imports")
										  .hasArgs()
										  .withDescription("Comma seperated list of import paths")
										  .create(IMPORTS);

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
		options.addOption(importPaths);
		options.addOption(configOption);
		options.addOption(checkOption);

		// add new simple options
		options.addOption(new Option("h", "help", false, "Print this help message"));

		return options;
	}

	public String getMainClass() {
		return mainClass;
	}

	public List<File> getImportPaths() {
		return importPaths;
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
