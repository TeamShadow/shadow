package shadow;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import shadow.typecheck.BaseChecker;

/** 
 * Represents a specific build/typecheck job. This representation includes
 * information about source files, compiler flags, and output files.
 */
public class Job {
	
	/** The main method containing source file given on the command line */
	private Path mainFile;
	
	/** The linker command used to specify an output file */
	private List<String> outputCommand = new ArrayList<String>();
	
	// Important, job-specific compiler flags
	private boolean checkOnly = false; // Run only parser and type-checker
	private boolean noLink = false;	// Compile the given file, but do not link
	private boolean verbose = false; // Print extra compilation info
	private boolean forceRecompile = false; // Recompile all source files, even if unneeded
	private boolean warningsAsErrors = false; //Treat warnings as errors
	private boolean humanReadable = false;
	
	public Job(Arguments compilerArgs) throws FileNotFoundException {
		
		// Check relevant command line flags
		checkOnly = compilerArgs.hasOption(Arguments.TYPECHECK);
		noLink = compilerArgs.hasOption(Arguments.NO_LINK);
		verbose = compilerArgs.hasOption(Arguments.VERBOSE);
		forceRecompile = compilerArgs.hasOption(Arguments.RECOMPILE);
		humanReadable = compilerArgs.hasOption(Arguments.READABLE);
		
		// Locate main source file if not help or information only
		if( !compilerArgs.hasOption(Arguments.INFORMATION) && !compilerArgs.hasOption(Arguments.HELP)) {
			if( compilerArgs.getMainFileArg() != null )
				mainFile = Paths.get(compilerArgs.getMainFileArg()).toAbsolutePath();
			
			// Ensure that the main source file exists
			if( mainFile == null || !Files.exists(mainFile) )
				throw new FileNotFoundException("Source file at " + mainFile.toAbsolutePath() + " not found");
		
			Path outputFile;
			
			// See if an output file was specified
			if( compilerArgs.hasOption(Arguments.OUTPUT) ) {
				outputFile = Paths.get(compilerArgs.getOutputFileArg());
				
				// Resolve it if necessary
				outputFile = mainFile.getParent().resolve(outputFile);
			}
			else {
				// Determine a path to the default output file
				String outputName = BaseChecker.stripExtension(mainFile.getFileName().toString());
				outputFile = mainFile.getParent().resolve(properExecutableName(outputName));
			}
			
			// Create linker output command
			outputCommand.add("-o");
			outputCommand.add(outputFile.toAbsolutePath().toString());
			
			//deal with warning flags
			if( compilerArgs.hasOption(Arguments.WARNING) ) {
				String flag = compilerArgs.getWarningFlag();
				if( flag.equals("error") )
					warningsAsErrors = true;
				else
					System.err.println("Unknown warning flag: " + flag);
			}
		}
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
	
	public boolean isHumanReadable() {
		return humanReadable;
	}
	
	public Path getMainFile() {
		
		return mainFile;
	}
	
	public boolean treatWarningsAsErrors() {
		return warningsAsErrors;
	}
	
	public List<String> getOutputCommand() {
		
		return outputCommand;
	}
	
	/** 
	 * Takes a file name without extension and outputs it in an OS-appropriate
	 * form.
	 */
	public static String properExecutableName(String executableName) {
		
		if( System.getProperty("os.name").contains("Windows") )
			return executableName + ".exe";
		else
			return executableName;
	}
}
