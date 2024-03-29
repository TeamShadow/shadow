package shadow;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;

/**
 * Represents any information given on the command line. Parses, processes, and provides access to
 * any arguments/options set on the command line. Recognized options are also defined within.
 */
public class Arguments {

  // Recognized single-character arguments
  public static final String CONFIG = "c";
  public static final String BUILD_SYSTEM = "b";
  public static final String TYPECHECK = "t";
  public static final String NO_LINK = "n";
  public static final String HELP = "h";
  public static final String OUTPUT = "o";
  public static final String VERBOSE = "v";
  public static final String INFORMATION = "i";
  public static final String WARNING = "w";
  public static final String READABLE = "r";

  // Recognized long arguments
  private static final String CONFIG_LONG = "config";
  private static final String BUILD_SYSTEM_LONG = "build-system";
  private static final String TYPECHECK_LONG = "typecheck";
  private static final String NO_LINK_LONG = "no-link";
  private static final String HELP_LONG = "help";
  private static final String OUTPUT_LONG = "output";
  private static final String VERBOSE_LONG = "verbose";
  private static final String INFORMATION_LONG = "information";
  private static final String WARNING_LONG = "warning";
  public static final String READABLE_LONG = "human-readable";

  private final CommandLine commandLine;

  private static final Options compilerOptions = createOptions();

  public Arguments(String[] args) throws ParseException {

    // Parse the command line arguments
    CommandLineParser parser = new DefaultParser();
    commandLine = parser.parse(compilerOptions, args);

    // Increase logging level if VERBOSE is set
    if (hasOption(VERBOSE)) Loggers.setAllToLevel(Level.INFO);
  }

  public boolean hasOption(String option) {
    return commandLine.hasOption(option);
  }

  public String[] getFiles() {
    return commandLine.getArgs();
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
    Option configOption =
        Option.builder(CONFIG)
            .longOpt(CONFIG_LONG)
            .hasArg()
            .argName("shadow.xml")
            .desc("Specify optional configuration file\nIf shadow.xml exists, it will be checked")
            .build();

    Option outputOption =
        Option.builder(OUTPUT)
            .longOpt(OUTPUT_LONG)
            .hasArg()
            .argName("file")
            .desc("Place output into <file>")
            .build();

    Option warningOption =
        Option.builder(WARNING)
            .longOpt(WARNING_LONG)
            .hasArg()
            .argName("flag")
            .desc("Specify warning flags")
            .build();

    options.addOption(configOption);
    options.addOption(outputOption);
    options.addOption(warningOption);

    // Build/add simple options

    options.addOption(
        new Option(
            BUILD_SYSTEM, BUILD_SYSTEM_LONG, false, "Build binary files for system source files"));
    options.addOption(
        new Option(TYPECHECK, TYPECHECK_LONG, false, "Parse and type-check the Shadow files"));
    options.addOption(
        new Option(NO_LINK, NO_LINK_LONG, false, "Compile Shadow files but do not link"));
    options.addOption(
        new Option(
            VERBOSE,
            VERBOSE_LONG,
            false,
            "Print detailed information about the compilation process"));
    options.addOption(new Option(HELP, HELP_LONG, false, "Display command line options and exit"));
    options.addOption(
        new Option(
            INFORMATION,
            INFORMATION_LONG,
            false,
            "Display information about the compiler and exit"));
    options.addOption(
        new Option(READABLE, READABLE_LONG, false, "Generate human-readable IR code"));

    return options;
  }

  public static void printHelp() {
    new HelpFormatter()
        .printHelp(
            "shadowc <mainSource.shadow> [-o <output>] [-c <config.xml>]", Arguments.getOptions());
  }

  public static void printInformation() {
    System.out.println("Shadow Information:");
    System.out.println("  shadowc version " + Main.VERSION);
    System.out.println();
    System.out.println(Configuration.getClangInformation());
  }
}
