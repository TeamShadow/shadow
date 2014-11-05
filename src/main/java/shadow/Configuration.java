package shadow;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

public class Configuration {
	
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
	private static final String VERBOSE			= "v";
	private static final String VERBOSE_LONG	= "verbose";


	private String parentConfig = null; // The parent configuration from a config file	
	private File mainFile = null; // The source file given over command line
	private File systemPath = null;	// This is the import path for all the system files
	private List<File> importPaths = null;
	private List<String> linkCommand = null;
	private String target = null;
	
	private boolean checkOnly = false; // Run only parser & type-checker
	private boolean noLink = false;	// Compile the files on the command line but do not link
	private boolean verbose = false;
	
	private int arch = -1;
	private String os = null;
	private File output = null;
	private File configFile = null;
	
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
	
	public File getExecutableDirectory()
	{
		try
		{		
			String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decodedPath = URLDecoder.decode(path, "UTF-8");			
			return new File(decodedPath).getParentFile();
		}
		catch(Exception e)
		{}
		
		return null;
	}
	

	/**
	 * Parses the command line and sets all of the internal variables.
	 * @param cmdLine The command line passed to the compiler.
	 * @throws ConfigurationException 
	 * @throws MalformedURLException
	 */
	public void parse(CommandLine cmdLine) throws ConfigurationException, MalformedURLException {
		if( cmdLine.getArgs().length > 1 ) {
			throw new ConfigurationException("Only one main source file may be specified");
		}
		else if( cmdLine.getArgs().length == 0 ) {
			throw new ConfigurationException("No source file specified to compile");
		}
		
		// Get the main source file for compilation
		mainFile = new File(cmdLine.getArgs()[0]);
		
		// Receive or find a config file, otherwise the compiler can't continue
		if ( cmdLine.hasOption(CONFIG))		
			// Parse the config file on the command line if we have it
			configFile = new File(cmdLine.getOptionValue(CONFIG));
		else // Look for a config file with a default name
		{	
			// First, look for the config file in the working directory
			String configName = getDefaultConfigName();		 
			configFile = new File(configName);
			
			if( !configFile.exists())
				configFile = new File("shadow.xml");
					
			//then look for a system-defined config file
			if ( !configFile.exists() )
			{
				// Use a system-wide file if it exists
				if(System.getenv("SHADOW_CONFIG") != null)
					configFile = new File(System.getenv("SHADOW_CONFIG"));
				
				if( !configFile.exists() ) //look in shadowc directory
					configFile = new File(getExecutableDirectory(), configName);
				
				
				if( !configFile.exists() )
					configFile = new File(getExecutableDirectory(), "shadow.xml");				
				
				if( !configFile.exists() )
					throw new ConfigurationException("No configuration file specified!");
			}
		}	
		
		if( configFile.exists() )
			parseConfigFile(configFile);
		else
			throw new ConfigurationException("Invalid configuration file specified: " + configFile.getPath());

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
		
		// See if we're being verbose about compilation
		verbose = cmdLine.hasOption(VERBOSE);
		
		if( cmdLine.hasOption(OUTPUT))
			output = new File(cmdLine.getOptionValue(OUTPUT));		

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
	public static Options createCommandLineOptions()
	{
		Options options = new Options();
		
		String configName = getDefaultConfigName();

		// setup the configuration file option
		@SuppressWarnings("static-access")
		Option configOption = OptionBuilder.withLongOpt(CONFIG_LONG)
										   .hasArg()
										   .withArgName("config.xml")
										   .withDescription("Specify configuration file\nDefault is " + configName + " or shadow.xml")
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
		
		// Create the verbose option
		@SuppressWarnings("static-access")
		Option verboseOption = OptionBuilder.withLongOpt(VERBOSE_LONG)
											.withDescription("Print detailed information about the compilation process")
											.create(VERBOSE);

		// add all the options from above
		options.addOption(configOption);
		options.addOption(checkOption);
		options.addOption(compileOption);
		options.addOption(outputOption);
		options.addOption(verboseOption);

		// add new simple options
		options.addOption(new Option(HELP, HELP_LONG, false, "Print this help message"));
		
		return options;
	}
	
	private static String getDefaultConfigName()
	{
		// Default config name if the platform can't be determined
		String configName = "shadow.xml";
		
		// Get a platform specific name for the default config file
		if ( System.getProperty("os.name").startsWith("Windows") )
		{
			//for now, always default to 32 for Windows
			
			//if ( System.getProperty("os.arch").contains("64") )
				//configName = "shadow-windows-64.xml";
			//else // If not 64 bit, should be 32 bit
				configName = "shadow-windows-32.xml";
		}
		else if ( System.getProperty("os.name").startsWith("Linux") )
		{
			if ( System.getProperty("os.arch").contains("64") )
				configName = "shadow-linux-64.xml";
			else // If not 64 bit, should be 32 bit
				configName = "shadow-linux-32.xml";
		}
		
		return configName;
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

	public void addImport(String importPath)
	{
		if( FilenameUtils.getPrefixLength(importPath) == 0 ) //relative path
			this.importPaths.add(new File(configFile.getParentFile(),importPath));
		else
			this.importPaths.add(new File(importPath));		
	}

	public File getSystemImport() {
		return systemPath;
	}

	public void setSystemImport(String systemImportPath) {
		if(this.systemPath == null)
		{			
			if( FilenameUtils.getPrefixLength(systemImportPath) == 0 ) //relative path
				this.systemPath = new File(configFile.getParentFile(),systemImportPath);
			else
				this.systemPath = new File(systemImportPath);			
		}
	}
	
	public void setLinkCommand(String linkCommand) {
		if(this.linkCommand == null)
			this.linkCommand = Arrays.asList(linkCommand.split("\\s+"));
	}
	
	public List<String> getLinkCommand() {
		return linkCommand;
	}
	
	public boolean hasLinkCommand() {
		return linkCommand != null;
	}
	
	public void setTarget(String target) {
		if( this.target == null )
			this.target = target;
	}
	
	public String getTarget() {
		return target;
	}
	
	public boolean hasTarget() {
		return target != null;
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
	
	public boolean isVerbose() {
		return verbose;
	}
	
	public boolean hasOutput()
	{
		return output != null;
	}
	
	public File getOutput()
	{
		return output;		
	}
	
	public File getMainFile() throws ConfigurationException {
		if ( mainFile == null )
			throw new ConfigurationException("No source file available to compile");
			
		return mainFile;
	}
}