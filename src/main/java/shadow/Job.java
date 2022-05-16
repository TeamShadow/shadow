package shadow;

import shadow.typecheck.BaseChecker;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a specific build/typecheck job. This representation includes information about source
 * files, compiler flags, and output files.
 */
public class Job {

  /** The main method containing source file given on the command line */
  private Path mainFile;

  /** The linker command used to specify an output file */
  private final List<String> outputCommand = new ArrayList<>();

  // Important, job-specific compiler flags
  private final boolean checkOnly; // Run only parser and type-checker
  private final boolean noLink; // Compile the given file, but do not link
  private final boolean verbose; // Print extra compilation info
  private final boolean forceRecompile; // Recompile all source files, even if unneeded
  private boolean warningsAsErrors = false; // Treat warnings as errors
  private final boolean humanReadable;

  public Job(Arguments compilerArgs) throws FileNotFoundException, ConfigurationException {

    // Check relevant command line flags
    checkOnly = compilerArgs.hasOption(Arguments.TYPECHECK);
    noLink = compilerArgs.hasOption(Arguments.NO_LINK);
    verbose = compilerArgs.hasOption(Arguments.VERBOSE);
    forceRecompile = compilerArgs.hasOption(Arguments.RECOMPILE);
    humanReadable = compilerArgs.hasOption(Arguments.READABLE);

    // Locate main source file if not help or information only
    if (!compilerArgs.hasOption(Arguments.INFORMATION) && !compilerArgs.hasOption(Arguments.HELP)) {
      if (compilerArgs.getMainFileArg() != null) {
        Map<Path, Path> imports =  Configuration.getConfiguration().getImport();
        for (Path _import : imports.keySet()) {
          Path candidate = _import.resolve(Paths.get(compilerArgs.getMainFileArg()));
          if (Files.exists(candidate))
            mainFile = candidate.toAbsolutePath().normalize();
            break;
        }
      }

      // Ensure that the main source file exists
      if (mainFile == null)
        throw new FileNotFoundException(
            "Source file at " + compilerArgs.getMainFileArg() + " not found");

      Path outputFile;

      // See if an output file was specified
      if (compilerArgs.hasOption(Arguments.OUTPUT)) {
        outputFile = Paths.get(compilerArgs.getOutputFileArg());

        // Resolve it if necessary
        outputFile = mainFile.resolveSibling(outputFile);
      } else {
        // Determine a path to the default output file
        Path outputName = BaseChecker.stripExtension(mainFile);
        outputFile = properExecutableName(outputName);
      }

      // Create linker output command
      outputCommand.add("-o");
      outputCommand.add(outputFile.toString());

      // deal with warning flags
      if (compilerArgs.hasOption(Arguments.WARNING)) {
        String flag = compilerArgs.getWarningFlag();
        if (flag.equals("error")) warningsAsErrors = true;
        else System.err.println("Unknown warning flag: " + flag);
      }
    }
  }

  public boolean isCheckOnly() {
    return checkOnly;
  }

  public boolean isNoLink() {
    return noLink;
  }

  @SuppressWarnings("unused")
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

  /** Takes a file name without extension and outputs it in an OS-appropriate, absolute form. */
  public static Path properExecutableName(Path executableName) {
    if (System.getProperty("os.name").contains("Windows"))
      executableName =  BaseChecker.addExtension(executableName, ".exe");
    return executableName.normalize().toAbsolutePath();
  }
}
