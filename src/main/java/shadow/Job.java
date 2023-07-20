package shadow;

import shadow.typecheck.BaseChecker;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents a specific build/typecheck job. This representation includes information about source
 * files, compiler flags, and output files.
 */
public class Job {

  /** The linker command used to specify an output file */
  private final List<String> outputCommand = new ArrayList<>();

  // Important, job-specific compiler flags
  private final boolean checkOnly; // Run only parser and type-checker
  private final boolean noLink; // Compile the given file, but do not link
  private final boolean verbose; // Print extra compilation info
  private final boolean forceRecompile; // Recompile all source files, even if unneeded
  private boolean warningsAsErrors = false; // Treat warnings as errors
  private final boolean humanReadable;

  private final boolean buildSystem;

  private final List<Path> files = new ArrayList<>();

  public Job(Arguments compilerArgs) throws FileNotFoundException, ConfigurationException, CommandLineException {

    // Check relevant command line flags
    checkOnly = compilerArgs.hasOption(Arguments.TYPECHECK);
    noLink = compilerArgs.hasOption(Arguments.NO_LINK);
    verbose = compilerArgs.hasOption(Arguments.VERBOSE);
    forceRecompile = compilerArgs.hasOption(Arguments.RECOMPILE);
    humanReadable = compilerArgs.hasOption(Arguments.READABLE);
    buildSystem = compilerArgs.hasOption(Arguments.BUILD_SYSTEM);

    String[] fileNames = compilerArgs.getFiles();

    if (compilerArgs.hasOption(Arguments.INFORMATION)) {
      if (fileNames.length > 0)
        throw new CommandLineException("Requests for compiler information should not include files to compile");
    }
    else if (compilerArgs.hasOption(Arguments.HELP)) {
      if (fileNames.length > 0)
        throw new CommandLineException("Requests for help information should not include files to compile");
    }
    else if (compilerArgs.hasOption(Arguments.BUILD_SYSTEM)) {
      if (fileNames.length > 0)
        throw new CommandLineException("Input files should not be specified when building the system library");


    }
    else if(fileNames.length > 0) {
      Map<Path, Path> imports =  Configuration.getConfiguration().getImport();
      for (String file : fileNames) {
        Path path = null;
        for (Path _import : imports.keySet()) {
          Path candidate = _import.resolve(Paths.get(file));
          if (Files.exists(candidate))
            path = candidate.toAbsolutePath().normalize();
          break;
        }

        if (path == null)
          throw new FileNotFoundException("Source file at " + file + " not found");
        else
          files.add(path);
      }

      Path outputFile;

      // See if an output file was specified
      if (compilerArgs.hasOption(Arguments.OUTPUT)) {
        outputFile = Paths.get(compilerArgs.getOutputFileArg());

        // Resolve it if necessary
        outputFile = files.get(0).resolveSibling(outputFile);
      } else {
        // Determine a path to the default output file
        Path outputName = BaseChecker.stripExtension(files.get(0));
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
    else
      throw new CommandLineException("No input files");
  }

  public List<Path> getFiles() {
    return Collections.unmodifiableList(files);
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

  public boolean isBuildSystem() {
    return buildSystem;
  }

  public boolean isHumanReadable() {
    return humanReadable;
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
