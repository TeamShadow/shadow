package shadow.doctool;

import java.util.ArrayList;

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
	public static final String OUTPUT			= "o";
	public static final String TEMPLATE			= "t";
	public static final String TEMPLATE_ARGS	= "a";
	
	// Recognized long-name options
	private static final String CONFIG_LONG 		= "config";
	private static final String HELP_LONG			= "help";
	private static final String VERBOSE_LONG		= "verbose";
	private static final String	OUTPUT_LONG			= "output";
	private static final String TEMPLATE_LONG		= "template";
	private static final String TEMPLATE_ARGS_LONG	= "template-args";
	
	// The main description printed in response to the help option
	private static final String HELP_MESSAGE
		= "shadowdoc [ source-files ] [ packages ] [ options ]";

	private static final Options options = createOptions();
	private CommandLine commandLine;
	private final ArrayList<String> templateArgs = new ArrayList<String>();
	
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
		
		buildTemplateArgs();
	}
	
	private void buildTemplateArgs()
	{
		String argText = commandLine.getOptionValue(TEMPLATE_ARGS);
		if (argText == null)
			return;
		
		String[] args = argText.split(",");
		for (String arg : args)
			templateArgs.add(arg);
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
	
	public String getOutputDirectory()
	{
		return commandLine.getOptionValue(OUTPUT);
	}
	
	public String[] getTemplateArgs()
	{
		return (String[]) templateArgs.toArray();
	}
	
	public static Options getOptions() 
	{	
		return options;
	}
	
	private static Options createOptions()
	{
		Options options = new Options();
		
		@SuppressWarnings("static-access")
		Option configOption = OptionBuilder.withLongOpt(CONFIG_LONG)
										   .hasArg()
										   .withArgName("config.xml")
										   .withDescription("Specify optional configuration file\nIf shadow.xml exists, it will be checked")
										   .create(CONFIG);

		@SuppressWarnings("static-access")
		Option outputOption = OptionBuilder.withLongOpt(OUTPUT_LONG)
										   .hasArg()
										   .withArgName("directory/")
										   .withDescription("Specify a directory for the documentation ouput\nBy default, doc/ will be created/used within the current working directory")
										   .create(OUTPUT);

		@SuppressWarnings("static-access")
		Option templateOption = OptionBuilder.withLongOpt(TEMPLATE_LONG)
										   .hasArg()
										   .withArgName("TemplateClass")
										   .withDescription("Specify a Java class to generate documentation with. This class must be a subclass of shadow.doctool.output.DocumentationTemplate.java")
										   .create(TEMPLATE);
		
		@SuppressWarnings("static-access")
		Option argsOption = OptionBuilder.withLongOpt(TEMPLATE_ARGS_LONG)
										   .hasArg()
										   .withArgName("a,b=value")
										   .withDescription("Arguments for the documentation template. These should be separated by ',' and have values assigned with '='")
										   .create(TEMPLATE_ARGS);


		options.addOption(argsOption);
		options.addOption(configOption);
		options.addOption(new Option(HELP, HELP_LONG, false, "Print this help message"));
		options.addOption(outputOption);
		options.addOption(templateOption);
		options.addOption(new Option(VERBOSE, VERBOSE_LONG, false, "Print detailed information about the documentation process"));
		
		return options;
	}
	
	public static void printHelp() 
	{
		new HelpFormatter().printHelp(HELP_MESSAGE,
			DocumentationArguments.getOptions());
	}
}