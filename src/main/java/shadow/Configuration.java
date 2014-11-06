package shadow;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
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
	private static final String RECOMPILE		= "f";
	private static final String RECOMPILE_LONG  = "force-recompile";

	private String parentConfig = null; // The parent configuration from a config file	
	private File mainFile = null; // The source file given over command line
	private String mainFileName = null;
	private File systemPath = null;	// This is the import path for all the system files
	private List<File> importPaths = null;
	private List<String> linkCommand = null;
	private String target = null;
	
	private boolean checkOnly = false; // Run only parser & type-checker
	private boolean noLink = false;	// Compile the files on the command line but do not link
	private boolean verbose = false;
	private boolean forceRecompile = false; // Recompile all source files, even if unneeded
	
	private int arch = -1;
	private String os = null;
	private File output = null;
	private File configFile = null;
	
	private static final String defaultFile = "shadow.xml";
	
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
	 * @throws IOException 
	 */
	public void parse(CommandLine cmdLine) throws ConfigurationException, IOException {
		if( cmdLine.getArgs().length > 1 ) {
			throw new ConfigurationException("Only one main source file may be specified");
		}
		else if( cmdLine.getArgs().length == 0 ) {
			throw new ConfigurationException("No source file specified to compile");
		}
		
		// Get the main source file for compilation
		mainFileName = cmdLine.getArgs()[0];
		mainFile = new File(mainFileName);
		
		// see if all we want is to check the file
		checkOnly = cmdLine.hasOption(TYPECHECK);
		
		// see if we're only compiling files
		noLink = cmdLine.hasOption(NO_LINK);
		
		// See if we're being verbose about compilation
		verbose = cmdLine.hasOption(VERBOSE);
		
		forceRecompile = cmdLine.hasOption(RECOMPILE);
		
		// If the output file specified is not absolute, create it relative to
		// the main source file.
		if( cmdLine.hasOption(OUTPUT) )
			output = makeAbsolute(cmdLine.getOptionValue(OUTPUT), mainFile.getParentFile());
		
		// Receive or find a config file, otherwise the compiler can't continue
		if ( cmdLine.hasOption(CONFIG) )
			// Parse the config file on the command line if we have it
			configFile = new File(cmdLine.getOptionValue(CONFIG));
		else // Look for a default config file
		{	
			// First, look for the config file in the working directory 
			configFile = new File(defaultFile);
			
			if( !configFile.exists() )
				configFile = new File(getExecutableDirectory(), defaultFile);
			
			if( !configFile.exists() ) {
				// Use a system-wide file if it exists
				if(System.getenv("SHADOW_CONFIG") != null)
					configFile = new File(System.getenv("SHADOW_CONFIG"));
			}
			
			// No worries if we haven't found a file, just autofill the fields
		}
		
		if( configFile.exists() )
			parseConfigFile(configFile);
		else {
			logger.info("No configuration files found, auto-detecting platform details");
			autoFill();
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
	}
	
	/**
	 * Parse a config file, recursively parsing parents when found.
	 * @param configFile The config file to parse
	 * @throws IOException 
	 * @throws ConfigurationException 
	 */
	private <T>void parseConfigFile(T configFile) throws ConfigurationException, IOException {
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
		
		autoFill();
	}
	
	/** Automatically fills any empty config fields */
	private void autoFill() throws ConfigurationException, IOException {
		if( arch == -1 ) {
			if( System.getProperty("os.arch").contains("64") )
				arch = 64;
			else
				arch = 32;
		}
		
		if( os == null ) {
			String fullOs = System.getProperty("os.name");
			
			if( fullOs.startsWith("Windows") )
				os = "Windows";
			else {
				logger.info("Non-Windows OS '" + fullOs + "' detected, defaulting to Linux.ll");
				os = "Linux";
			}
		}
		
		if( target == null ) {
			target = getDefaultTarget();
		}
		
		if( output == null ) {
			if( os == "Windows" )
				output = new File(Main.stripExt(mainFileName) + ".exe");
			else
				output = new File(Main.stripExt(mainFileName));
		}
		
		if( linkCommand == null ) {
			linkCommand = new ArrayList<String>();							
			linkCommand.add("gcc");
			linkCommand.add("-x");
			linkCommand.add("assembler");
			linkCommand.add("-");					
			
			if(config.getOs().equals("Linux")) {
				linkCommand.add("-lm");
				linkCommand.add("-lrt");
			}
			
			linkCommand.add("-o");
			linkCommand.add(output.getPath());
		}

		if( systemPath == null ) {
			systemPath = getExecutableDirectory();
		}
		
		if( importPaths.isEmpty() )
			importPaths.add(getExecutableDirectory());
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

		// setup the configuration file option
		@SuppressWarnings("static-access")
		Option configOption = OptionBuilder.withLongOpt(CONFIG_LONG)
										   .hasArg()
										   .withArgName("config.xml")
										   .withDescription("Specify optional configuration file\nIf " + defaultFile + " exists, it will be checked")
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
		
		// Create the force-recompile option
		@SuppressWarnings("static-access")
		Option recompileOption = OptionBuilder.withLongOpt(RECOMPILE_LONG)
											.withDescription("Recompiles all source files, even if unnecessary")
											.create(RECOMPILE);

		// add all the options from above
		options.addOption(configOption);
		options.addOption(checkOption);
		options.addOption(compileOption);
		options.addOption(outputOption);
		options.addOption(verboseOption);
		options.addOption(recompileOption);

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

	public void addImport(String importPath)
	{
		File importPathFile = makeAbsolute(importPath, getExecutableDirectory());
		
		importPaths.add(importPathFile);
	}

	public File getSystemImport() {
		return systemPath;
	}

	public void setSystemImport(String systemImportPath) {
		if(systemPath == null)
			systemPath = makeAbsolute(systemImportPath, getExecutableDirectory());
	}
	
	public void setLinkCommand(String linkCommand) {
		if(this.linkCommand == null)
			this.linkCommand = Arrays.asList(linkCommand.split("\\s+"));
	}
	
	public List<String> getLinkCommand() {
		return linkCommand;
	}
	
	public void setTarget(String target) {
		if( this.target == null )
			this.target = target;
	}
	
	public String getTarget() {
		return target;
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
	
	public boolean isForceRecompile() {
		return forceRecompile;
	}
	
	public File getOutput() {
		return output;		
	}
	
	public File getMainFile() throws ConfigurationException {
		if ( mainFile == null )
			throw new ConfigurationException("No source file available to compile");
			
		return mainFile;
	}
	
	/** 
	 * Recreates a relative path in terms of a parent path. Used to ensure
	 * relative paths stay relative to the correct directory.
	 */
	public File makeAbsolute(String path, File parent) {
		File file = new File(path);
		
		if( !file.isAbsolute() )
			file = new File(parent, path);
		
		return file;
	}
	
	/** Returns the target platform to be used by the LLVM compiler */
	private static String getDefaultTarget() throws ConfigurationException, IOException {
		// Some reference available here:
		// http://llvm.org/docs/doxygen/html/Triple_8h_source.html
		
		// Calling 'llc --version' for current target information
		// Note: Most of the LLVM tools also have this option
		Process process = new ProcessBuilder("llc", "-version").redirectErrorStream(true).start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String versionOutput = "";
		String line = null;
		while( (line = reader.readLine()) != null ) {
		   versionOutput += line + "\n";
		}
		
		// Create the regular expression required to find the target "triple"
		Pattern pattern = Pattern.compile("(Default target:\\s)([\\w\\-]+)");
		Matcher matcher = pattern.matcher(versionOutput);
		
		if( matcher.find() ) {
			return matcher.group(2);
		}
		else {
			throw new ConfigurationException(
					"Unable to find target in 'llc --version' output:\n" 
					+ versionOutput);
		}
	}
}