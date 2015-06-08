package shadow.doctool;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.logging.log4j.Level;

import shadow.ConfigurationException;
import shadow.Loggers;

/** 
 * Represents any information given to the documentation tool on the command
 * line. Parses, processes, and provides access to arguments and options
 */
public class DocumentationArguments 
{	
	// Recognized single-character options
	public static final String CONFIG 			= "c";
	public static final String HELP				= "h";
	public static final String VERBOSE			= "v";
	
	// Recognized long-name options
	private static final String CONFIG_LONG 	= "config";
	private static final String HELP_LONG		= "help";
	private static final String VERBOSE_LONG	= "verbose";
	
	// The main description printed in response to the help option
	private static final String HELP_MESSAGE
		= "shadowdoc <mainSource.shadow> [-c <config.xml>]";
	
	private CommandLine commandLine;
	private static final Options options = createOptions();
	
	public DocumentationArguments(String[] args) throws ParseException, ConfigurationException 
	{	
		// Parse the command line arguments
		CommandLineParser parser = new PosixParser();
		commandLine = parser.parse(options, args);
		
		// Increase logging level if VERBOSE is set
		if (hasOption(VERBOSE))
			Loggers.setAllToLevel(Level.ALL);
		
		// Don't throw argument exceptions if help was requested
		if (commandLine.hasOption(HELP)) {
			printHelp();
			return;
		}

		if (commandLine.getArgs().length == 0)
			throw new ConfigurationException("No files specified to document");
	}
	
	public boolean hasOption(String option) 
	{	
		return commandLine.hasOption(option);
	}

	public String[] getMainArguments() 
	{	
		return commandLine.getArgs();
	}
	
	public String getConfigFileArg() 
	{	
		return commandLine.getOptionValue(CONFIG);
	}
	
	public static Options getOptions() 
	{	
		return options;
	}
	
	private static Options createOptions()
	{
		Options options = new Options();
		
		// Build/add options with arguments
		@SuppressWarnings("static-access")
		Option configOption = OptionBuilder.withLongOpt(CONFIG_LONG)
										   .hasArg()
										   .withArgName("config.xml")
										   .withDescription("Specify optional configuration file\nIf shadow.xml exists, it will be checked")
										   .create(CONFIG);
		options.addOption(configOption);
		
		// Build/add simple options
		options.addOption(new Option(VERBOSE, VERBOSE_LONG, false, "Print detailed information about the documentation process"));
		options.addOption(new Option(HELP, HELP_LONG, false, "Print this help message"));
		
		return options;
	}
	
	public static void printHelp() 
	{
		new HelpFormatter().printHelp(HELP_MESSAGE,
			DocumentationArguments.getOptions());
	}
}