package shadow.doctool;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import shadow.ConfigurationException;
import shadow.Loggers;
import shadow.doctool.output.StandardTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents any information given to the documentation tool on the command line. Parses,
 * processes, and provides access to arguments and options
 */
public class DocumentationArguments {
  // Recognized single-character options
  public static final String CONFIG = "c";
  public static final String HELP = "h";
  public static final String VERBOSE = "v";
  public static final String OUTPUT_DIR = "d";
  // Loading external documentation templates will be supported someday
  // public static final String TEMPLATE			= "t";
  public static final String TEMPLATE_OPTS = "o";

  // Recognized long-name options
  private static final String CONFIG_LONG = "config";
  private static final String HELP_LONG = "help";
  private static final String VERBOSE_LONG = "verbose";
  private static final String OUTPUT_DIR_LONG = "directory";
  // private static final String TEMPLATE_LONG		= "template";
  private static final String TEMPLATE_OPTS_LONG = "options";

  // The main description printed in response to the help option
  private static final String HELP_MESSAGE = "shadox [ source-files ] [ packages ] [ options ]";

  private static final Options options = createOptions();
  private final CommandLine commandLine;

  public DocumentationArguments(String[] args)
      throws ParseException, ConfigurationException, HelpRequestedException {
    // Parse the command line arguments
    CommandLineParser parser = new DefaultParser();
    commandLine = parser.parse(options, args);

    // Increase logging level if VERBOSE is set
    if (hasOption(VERBOSE))
      Loggers.setAllToLevel(Level.INFO); // change from Level.all to Level.Info

    // Don't throw argument exceptions if help was requested
    if (commandLine.hasOption(HELP)) {
      printHelp();
      return;
    }

    if (commandLine.getArgs().length == 0)
      throw new ConfigurationException("No files specified to document");

    buildTemplateArgs();
  }

  private final Map<String, String> templateArgs = new HashMap<>();

  private void buildTemplateArgs() throws ParseException, HelpRequestedException {
    String argText = commandLine.getOptionValue(TEMPLATE_OPTS);
    if (argText == null) return;

    String[] args = argText.split(",");
    for (String arg : args) {
      if (!arg.isEmpty()) {
        String[] keyVal = arg.split("=");
        if ((keyVal.length == 1 || keyVal.length == 2) && !keyVal[0].isEmpty()) {
          if (templateArgs.containsKey(keyVal[0]))
            throw new ParseException("Argument key appears more than once: \"" + keyVal[0] + "\"");
          else if (keyVal.length == 2 && !keyVal[1].isEmpty())
            templateArgs.put(keyVal[0], keyVal[1]);
          else templateArgs.put(keyVal[0], null);
        } else {
          throw new ParseException(
              "Invalid argument \"" + arg + "\": Empty key or improper usage of =");
        }
      }
    }

    // TODO: Make this work with non-standard documentation templates
    if (templateArgs.containsKey(StandardTemplate.ARG_HELP)) {
      StandardTemplate.printHelp();
      throw new HelpRequestedException();
    }
  }

  public boolean hasOption(String option) {
    return commandLine.hasOption(option);
  }

  public String[] getMainArguments() {
    return commandLine.getArgs();
  }

  public String getConfigFileArg() {
    return commandLine.getOptionValue(CONFIG);
  }

  public String getOutputDirectory() {
    return commandLine.getOptionValue(OUTPUT_DIR);
  }

  public Map<String, String> getTemplateArgs() {
    return Collections.unmodifiableMap(templateArgs);
  }

  public static Options getOptions() {
    return options;
  }

  private static Options createOptions() {
    Options options = new Options();

    Option configOption =
        Option.builder(CONFIG)
            .longOpt(CONFIG_LONG)
            .hasArg()
            .argName("config.xml")
            .desc("Specify optional configuration file\nIf shadow.xml exists, it will be checked")
            .build();

    Option outputOption =
        Option.builder(OUTPUT_DIR)
            .longOpt(OUTPUT_DIR_LONG)
            .hasArg()
            .argName("directory/")
            .desc(
                "Specify a root directory for the documentation ouput\nBy default, doc/ will be created/used within the current working directory")
            .build();

    Option argsOption =
        Option.builder(TEMPLATE_OPTS)
            .longOpt(TEMPLATE_OPTS_LONG)
            .hasArg()
            .argName("a,b=value")
            .desc(
                "Options and arguments for the documentation template. Options should be separated by ',' and have values assigned with '='")
            .build();

    options.addOption(argsOption);
    options.addOption(configOption);
    options.addOption(new Option(HELP, HELP_LONG, false, "Display command line options and exit"));
    options.addOption(outputOption);
    options.addOption(
        new Option(
            VERBOSE,
            VERBOSE_LONG,
            false,
            "Print detailed information about the documentation process"));

    return options;
  }

  public static void printHelp() {
    new HelpFormatter().printHelp(HELP_MESSAGE, DocumentationArguments.getOptions());
  }
}
