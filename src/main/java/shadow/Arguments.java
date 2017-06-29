package shadow;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.logging.log4j.Level;


/** 
 * Represents any information given on the command line. Parses, processes,
 * and provides access to any arguments/options set on the command line.
 * Recognized options are also defined within.
 */
public class Arguments {
	
	// Recognized single-character arguments 
	public static final String CONFIG 			= "c";
	public static final String TYPECHECK		= "t";
	public static final String NO_LINK			= "n";
	public static final String HELP				= "h";
	public static final String OUTPUT			= "o";
	public static final String VERBOSE			= "v";
	public static final String RECOMPILE		= "f";
	public static final String INFORMATION		= "i";
	public static final String WARNING			= "w";
	public static final String READABLE			= "r";
	
	// Recognized long arguments
	private static final String CONFIG_LONG 	= "config";
	private static final String TYPECHECK_LONG	= "typecheck";
	private static final String NO_LINK_LONG	= "nolink";
	private static final String HELP_LONG		= "help";
	private static final String OUTPUT_LONG		= "output";
	private static final String VERBOSE_LONG	= "verbose";
	private static final String RECOMPILE_LONG  = "force-recompile";
	private static final String INFORMATION_LONG	= "information";
	private static final String WARNING_LONG	= "warning";
	public static final String READABLE_LONG	= "human-readable";
	
	private CommandLine commandLine;
	
	private static final Options compilerOptions = createOptions();
	
	public Arguments(String[] args) throws ParseException, ConfigurationException {
		
		// Parse the command line arguments
		CommandLineParser parser = new PosixParser();
		commandLine = parser.parse(compilerOptions, args);
		
		// Increase logging level if VERBOSE is set
		if( hasOption(VERBOSE) )
			Loggers.setAllToLevel(Level.INFO);
		
		// Don't throw argument exceptions if help or information was requested
		if (commandLine.hasOption(HELP) || commandLine.hasOption(INFORMATION))
			return;		

		// Ensure exactly one source file is specified (and that it ends in .shadow)
		if( commandLine.getArgs().length > 1 )
			throw new ConfigurationException("Only one main source file may be specified");
		else if( commandLine.getArgs().length == 0 )
			throw new ConfigurationException("No source file specified to compile");
		else if( !commandLine.getArgs()[0].endsWith(".shadow") )
			throw new ConfigurationException("Source files must end in \".shadow\"");
	}
	

	public boolean hasOption(String option) {		
		return commandLine.hasOption(option);
	}

	public String getMainFileArg() {
		if( commandLine.getArgs().length > 0)
			return commandLine.getArgs()[0];
		else
			return null;
	}
	
	public String getConfigFileArg() {		
		return commandLine.getOptionValue(CONFIG);
	}
	
	public String getOutputFileArg() {		
		return commandLine.getOptionValue(OUTPUT);
	}
	
	public String getWarningFlag() {
		return commandLine.getOptionValue(WARNING);
	}
	
	public static Options getOptions() {		
		return compilerOptions;
	}
	
	/** Create an Options object for parsing the command line. */
	private static Options createOptions() {
		Options options = new Options();
		
		// Build/add options with arguments
		@SuppressWarnings("static-access")
		Option configOption = OptionBuilder.withLongOpt(CONFIG_LONG)
										   .hasArg()
										   .withArgName("config.xml")
										   .withDescription("Specify optional configuration file\nIf shadow.xml exists, it will be checked")
										   .create(CONFIG);
		
		@SuppressWarnings("static-access")
		Option outputOption = OptionBuilder.withLongOpt(OUTPUT_LONG)
											.hasArg()
											.withArgName("file")
										    .withDescription("Place output into <file>")										    
										    .create(OUTPUT);
		
		
		@SuppressWarnings("static-access")
		Option warningOption = OptionBuilder.withLongOpt(WARNING_LONG)
											.hasArg()
											.withArgName("flag")
										    .withDescription("Specify warning flags")										    
										    .create(WARNING);

		options.addOption(configOption);
		options.addOption(outputOption);
		options.addOption(warningOption);

		// Build/add simple options
		options.addOption(new Option(TYPECHECK, TYPECHECK_LONG, false, "Parse and type-check the Shadow files"));
		options.addOption(new Option(NO_LINK, NO_LINK_LONG, false, "Compile Shadow files but do not link"));
		options.addOption(new Option(VERBOSE, VERBOSE_LONG, false, "Print detailed information about the compilation process"));
		options.addOption(new Option(RECOMPILE, RECOMPILE_LONG, false, "Recompile all source files, even if unnecessary"));
		options.addOption(new Option(HELP, HELP_LONG, false, "Display command line options and exit"));
		options.addOption(new Option(INFORMATION, INFORMATION_LONG, false, "Display information about the compiler and exit"));
		options.addOption(new Option(READABLE, READABLE_LONG, false, "Generate human-readable IR code"));
		
		return options;
	}
	
	public static void printHelp() {
		new HelpFormatter().printHelp("shadowc <mainSource.shadow> [-o <output>] [-c <config.xml>]", Arguments.getOptions());
	}
	
	public static void printInformation() {
		System.out.println("Shadow Information:");
		System.out.println("  shadowc version " + Main.VERSION);
		System.out.println();
		System.out.println(Configuration.getLLVMInformation());		
	}
}
