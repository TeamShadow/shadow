package shadow;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;

public class Configuration implements Iterator<File> {
	
	private Logger logger = Loggers.SHADOW;
	
	// these are the single letter command line args
	private static final String CONFIG 			= "c";
	private static final String CONFIG_LONG 	= "config";
	private static final String TYPECHECK		= "t";
	private static final String TYPECHECK_LONG	= "typecheck";
	private static final String NO_LINK			= "n";
	private static final String NO_LINK_LONG	= "nolink";
	private static final String HELP			= "h";
	private static final String HELP_LONG		= "help";
	private static final String OUTPUT			= "o";
	private static final String OUTPUT_LONG		= "output";


	private String parentConfig = null; // The parent configuration from a config file	
	private List<File> shadowFiles = null; // All source files given over command line
	private int currentShadowFile = 0;
	private File systemPath = null;	// This is the import path for all the system files
	private List<File> importPaths = null;
	private boolean checkOnly = false; // Run only parser & type-checker
	private boolean noLink = false;	// Compile the files on the command line but do not link
	private int arch = -1;
	private String os = null;
	private File output = null;
	
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
	 * @throws ConfigurationException 
	 * @throws MalformedURLException
	 */
	public void parse(CommandLine cmdLine) throws ConfigurationException, MalformedURLException
	{
		this.reset(); // Reset the counter in case we parse multiple times
		
		// get all of the files to compile
		shadowFiles = new ArrayList<File>();
		for ( String shadowFile : cmdLine.getArgs() )
			shadowFiles.add(new File(shadowFile));
		
		// Receive or find a config file, otherwise the compiler can't continue
		if ( cmdLine.hasOption(CONFIG) )
		{
			// Parse the config file on the command line if we have it
			parseConfigFile(new File(cmdLine.getOptionValue(CONFIG)));
		}
		else // Look for a config file with a default name
		{	
			// Absolute default config name if the platform can't be determined
			String configName = "shadow.xml";
			
			// Get a platform specific name for the default config file
			if ( System.getProperty("os.name").startsWith("Windows") )
			{
				if ( System.getProperty("os.arch").contains("64") )
					configName = "shadow-windows-64.xml";
				else // If not 64 bit, should be 32 bit
					configName = "shadow-windows-32.xml";
			}
			else if ( System.getProperty("os.name").startsWith("Linux") )
			{
				if ( System.getProperty("os.arch").contains("64") )
					configName = "shadow-linux-64.xml";
				else // If not 64 bit, should be 32 bit
					configName = "shadow-linux-32.xml";
			}
			
			// Begin searching locations for the determined default config file
			URL url = null; 
			
			// First, look for the config file in the dir of the .shadow file
			if ( shadowFiles.size() > 0 )
			{
				// Get the directory containing the source files and apply it
				// to the config file.
				File sourceDir = shadowFiles.get(0).getParentFile();
				File configFile = new File(sourceDir, configName);
				
				if( configFile.exists() )
					url = configFile.toURI().toURL();
			}
			
			// Next, check for the file in the compiler's resources
			// Retained from previous code - no guarantee that this is useful
			if ( url == null )
					url = Main.class.getResource(File.separator + configName);
			
			if ( url == null )
			{
				if(System.getenv("SHADOW_CONFIG") != null)
				{
					// At this point, use a system-wide file if it exists
					parseConfigFile(new File(System.getenv("SHADOW_CONFIG")));
				}
				else // Game over if we absolutely haven't found a config file
					throw new ConfigurationException("No configuration file specified!");
			}
			else
				parseConfigFile(url);
		}

		// print the import paths if we're debugging
		if(logger.isDebugEnabled()) {
			for(File i:importPaths) {
				logger.debug("IMPORT: " + i.getAbsolutePath());
			}
		}

		//
		// By the time we get here, all configs & parents have been parsed
		//
		
		// see if all we want is to check the file
		checkOnly = cmdLine.hasOption(TYPECHECK);
		
		// see if we're only compiling files
		noLink = cmdLine.hasOption(NO_LINK);
		
		if( cmdLine.hasOption(OUTPUT))
			output = new File(cmdLine.getOptionValue(OUTPUT));		
	
		if(shadowFiles.size() == 0)
			throw new ConfigurationException("No source files specified to compile");

		// Sanity checks
		
		if(arch == -1)
			throw new ConfigurationException("Architecture not specified");
		
		if(os == null)
			throw new ConfigurationException("OS not specified");
		
		if(this.systemPath == null)
			throw new ConfigurationException("No system import path specified");
	}
	
	/**
	 * Parse a config file, recursively parsing parents when found.
	 * @param configFile The config file to parse
	 */
	private <T>void parseConfigFile(T configFile) {
		ConfigParser parser = new ConfigParser(this);
		
		if(configFile instanceof File) {
			logger.debug("PARSING: " + ((File)configFile).getAbsolutePath());
			parser.parse((File)configFile);
		} else {
			logger.debug("PARSING: " + ((URL)configFile));
			
			parser.parse((URL)configFile);
		}
		
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
	 * 
	 * The options are:
	 * --config Specifies the config.xml file to be used
	 * --typecheck Parses and type-checks the files
	 * --nolink Compiles the Shadow files but does not link them
	 * @return Return options used to parse the command line. 
	 */
	public static Options createCommandLineOptions() {
		Options options = new Options();
		
		String fileName;
		
		if(System.getProperty("os.name").startsWith("Windows"))
			fileName = "shadow-windows.xml";
		else
			fileName = "shadow-linux.xml";

		// setup the configuration file option
		@SuppressWarnings("static-access")
		Option configOption = OptionBuilder.withLongOpt(CONFIG_LONG)
										   .hasArg()
										   .withArgName("config.xml")
										   .withDescription("Specify configuration file\nDefault is " + fileName + " or shadow.xml")
										   .create(CONFIG);

		// create the typecheck option
		@SuppressWarnings("static-access")
		Option checkOption = OptionBuilder.withLongOpt(TYPECHECK_LONG)
										  .withDescription("Parse and type-check the Shadow files")
										  .create(TYPECHECK);

		// create the nolink option
		@SuppressWarnings("static-access")
		Option compileOption = OptionBuilder.withLongOpt(NO_LINK_LONG)											
										    .withDescription("Compile Shadow files but do not link")										    
										    .create(NO_LINK);
		
		// create the nolink option
		@SuppressWarnings("static-access")
		Option outputOption = OptionBuilder.withLongOpt(OUTPUT_LONG)
											.hasArg()
											.withArgName("file")
										    .withDescription("Place output into <file>")										    
										    .create(OUTPUT);

		// add all the options from above
		options.addOption(configOption);
		options.addOption(checkOption);
		options.addOption(compileOption);
		options.addOption(outputOption);

		// add new simple options
		options.addOption(new Option(HELP, HELP_LONG, false, "Print this help message"));
		
		return options;
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
		return systemPath;
	}

	public void setSystemImport(String systemImportPath) {
		if(this.systemPath == null)
			this.systemPath = new File(systemImportPath);
	}

	public void setParent(String parentConfig) {
		this.parentConfig = parentConfig;
	}

	public boolean isCheckOnly() {
		return checkOnly;
	}
	
	public boolean isNoLink() {
		return noLink;
	}
	
	public boolean hasOutput()
	{
		return output != null;
	}
	
	public File getOutput()
	{
		return output;		
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
		importPaths.clear();
	}

}
