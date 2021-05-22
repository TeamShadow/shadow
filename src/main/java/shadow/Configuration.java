package shadow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "system",
  "import",
  "target",
  "architecture",
  "os",
  "opt",
  "llc",
  "link",
  "parent"
})
public class Configuration {

  // Deserializes paths relative to the config file
  private static class PathDeserializer extends StdDeserializer<Path> {
    private final Path configFile;

    public PathDeserializer(Path configFile) {
      this(null, configFile);
    }

    public PathDeserializer(Class<?> vc, Path configFile) {
      super(vc);
      this.configFile = configFile;
    }

    @Override
    public Path deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      JsonNode node = jp.getCodec().readTree(jp);
      Path path = Paths.get(node.asText());
      return configFile.getParent().resolve(path);
    }
  }

  public static final String DEFAULT_CONFIG_NAME = "shadow.json";

  private static Configuration globalConfig;

  private static final Logger logger = Loggers.SHADOW;

  // private Path configFile;
  private String dataLayout;

  @JsonProperty("system")
  private Path system;

  @JsonProperty("import")
  private List<Path> _import = null;

  @JsonProperty("target")
  private String target;

  @JsonProperty("architecture")
  private int architecture;

  @JsonProperty("os")
  private String os;

  @JsonProperty("opt")
  private String opt;

  @JsonProperty("llc")
  private String llc;

  @JsonProperty("link")
  private List<String> link;

  @JsonProperty("parent")
  private Path parent;

  @JsonProperty("system")
  public Path getSystem() {
    return system;
  }

  @JsonProperty("import")
  public List<Path> getImport() {
    return _import;
  }

  @JsonProperty("target")
  public String getTarget() {
    return target;
  }

  @JsonProperty("target")
  public void setTarget(String target) {
    this.target = target;
  }

  @JsonProperty("architecture")
  public int getArchitecture() {
    return architecture;
  }

  @JsonProperty("os")
  public String getOs() {
    return os;
  }

  @JsonProperty("os")
  public void setOs(String os) {
    this.os = os;
  }

  @JsonProperty("opt")
  public String getOpt() {
    return opt;
  }

  @JsonProperty("opt")
  public void setOpt(String opt) {
    this.opt = opt;
  }

  @JsonProperty("llc")
  public String getLlc() {
    return llc;
  }

  @JsonProperty("link")
  public List<String> getLink() {
    return link;
  }

  /**
   * Builds the Configuration if necessary. Must be run at least once before getConfiguration() is
   * called.
   */
  public static Configuration buildConfiguration(
      String mainFilePath, String configFilePath, boolean forceRebuild)
      throws ConfigurationException, IOException {

    if (globalConfig == null || forceRebuild)
      globalConfig = readConfiguration(mainFilePath, configFilePath);

    return globalConfig;
  }

  /** Retrieves the global compiler Configuration */
  public static Configuration getConfiguration() throws ConfigurationException {

    if (globalConfig == null)
      throw new ConfigurationException("Configuration data must be built before being accessed.");

    return globalConfig;
  }

  private static Configuration readConfiguration(String mainFilePath, String configFilePath)
      throws ConfigurationException, IOException {
    // Attempt to locate hierarchy of config files
    Path configFile = locateConfig(mainFilePath, configFilePath);

    Configuration configuration;

    // If a config file was located, parse it
    if (configFile != null) configuration = parse(configFile);
    else configuration = new Configuration();

    configuration.inferSettings(); // Auto-fill any empty fields
    return configuration;
  }

  /*
   * Attempts to find a specified (or unspecified) config file. There are
   * three places to look for a config file. In priority order:
   *
   * 1. If the file specified on the command line
   * 		-As is, if absolute
   * 		-Relative to the source directory
   * 		-Relative to the working directory, with a warning
   * 		-Relative to the running directory, with a warning
   * 		-Exception if not found
   * 2. A file in the source directory with the default name
   * 3. A file in the running directory with the default name
   * 4. A file in the working directory with the default name
   */
  private static Path locateConfig(String mainFilePath, String configFilePath)
      throws FileNotFoundException, ConfigurationException {

    // Get the various search directories
    Path sourceDir =
        mainFilePath == null
            ? null
            : Paths.get(mainFilePath).toAbsolutePath().getParent().toAbsolutePath();
    Path workingDir = Paths.get("").toAbsolutePath();
    Path runningDir = getRunningDirectory().toAbsolutePath();

    Path defaultFile = Paths.get(DEFAULT_CONFIG_NAME);

    // 1: Config file specified on the command line

    if (configFilePath != null) {

      Path configFile = Paths.get(configFilePath);

      // If absolute, no need to resolve
      if (configFile.isAbsolute()) {
        if (!Files.exists(configFile))
          throw new FileNotFoundException(
              "Configuration file " + configFile.toAbsolutePath() + " does not exist");

        return configFile;
      }
      // If not, first look in the source directory
      else if (sourceDir != null && Files.exists(sourceDir.resolve(configFile))) {
        return sourceDir.resolve(configFile);
      }
      // Look in the working directory
      else if (Files.exists(workingDir.resolve(configFile))) {
        logger.warn(
            "WARNING: Falling back on the working directory "
                + workingDir
                + " in order to find the config file "
                + configFile
                + ". This should usually only be expected during testing");
        return workingDir.resolve(configFile);
      }
      // Look in the running directory of the program
      else if (Files.exists(runningDir.resolve(configFile))) {
        logger.warn(
            "WARNING: Falling back on the running directory "
                + runningDir
                + " in order to find the config file "
                + configFile
                + ". This should usually only be expected during testing");
        return runningDir.resolve(configFile);
      } else {
        throw new FileNotFoundException("Configuration file " + configFile + " does not exist");
      }
    }
    /// 2: Default config file, local to the main source file
    else if (sourceDir != null && Files.exists(sourceDir.resolve(defaultFile))) {
      return sourceDir.resolve(defaultFile);
    }
    /// 3: Default config file, local to the running directory
    else if (Files.exists(runningDir.resolve(defaultFile))) {
      return runningDir.resolve(defaultFile);
    }
    /// 4: Default config file, local to the working directory
    else if (Files.exists(workingDir.resolve(defaultFile))) {
      return workingDir.resolve(defaultFile);
    } else {
      return null;
    }
  }

  /* Auto-detects values for unfilled fields */
  private void inferSettings() throws ConfigurationException {

    if (architecture == 0) {
      if (System.getProperty("os.arch").contains("64")) architecture = 64;
      else architecture = 32;
    }

    if (os == null) {
      String osName = System.getProperty("os.name").toLowerCase();
      if (osName.contains("windows")) os = "Windows";
      else if (osName.contains("mac")) os = "Mac";
      else if (osName.contains("linux")) os = "Linux";
      else {
        logger.info(
            "Unrecognized operating system \""
                + System.getProperty("os.name")
                + "\" detected, defaulting to Linux.ll");
        os = "Linux";
      }
    }

    // Make sure that llc is specified early, since it's used to get the default target
    if (llc == null) llc = "llc";

    if (opt == null) opt = "opt";

    if (target == null) target = getDefaultTarget();

    if (link == null) {
      link = new ArrayList<>();

      switch (getOs()) {
        case "Mac":
          // Does Mac work at all now?  What about the new M chips?
          link.add("clang");
          link.add("-lm");
          link.add("-lSystem");
          break;
        case "Windows":
          // If properly set up, clang uses Visual Studio to link default Windows libraries
          link.add("clang");
          break;
        case "Linux":
          link.add("clang++");
          link.add("-lm");
          link.add("-lrt");
          link.add("-pthread");
          break;
      }

      if (architecture == 32) link.add("-m32");
      else link.add("-m64");
    }

    if (system == null) system = getRunningDirectory();

    if (_import == null) _import = new ArrayList<>();

    // The import paths list must contain an "empty" path that can later be
    // resolved against source files
    _import.add(Paths.get("." + File.separator));
  }

  /* Parses a config file and fills the corresponding fields */
  private static Configuration parse(Path configFile) throws IOException {
    try {
      ObjectMapper mapper =
          new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      SimpleModule module = new SimpleModule();
      module.addDeserializer(Path.class, new PathDeserializer(configFile));
      mapper.registerModule(module);
      Configuration configuration = mapper.readValue(configFile.toFile(), Configuration.class);

      if (configuration.parent != null) {
        Configuration parent = parse(configuration.parent);
        if (configuration.system == null) configuration.system = parent.system;
        if (configuration._import == null) configuration._import = parent._import;
        if (configuration.target == null) configuration.target = parent.target;
        if (configuration.architecture == 0) configuration.architecture = parent.architecture;
        if (configuration.os == null) configuration.os = parent.os;
        if (configuration.opt == null) configuration.opt = parent.opt;
        if (configuration.llc == null) configuration.llc = parent.llc;
        if (configuration.link == null) configuration.link = parent.link;
      }

      return configuration;
    } catch (IOException e) {
      System.err.println("ERROR PARSING CONFIGURATION FILE: " + configFile.toAbsolutePath());
      throw e;
    }
  }

  /** Gets the directory within which the compiler is currently running */
  public static Path getRunningDirectory() throws ConfigurationException {
    try {
      URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();
      URI uri = url.toURI();
      return Paths.get(uri).getParent().toAbsolutePath();
    } catch (SecurityException e) {
      throw new ConfigurationException("Insufficient permissions to access the running directory");
    } catch (URISyntaxException e) {
      throw new ConfigurationException(e.getLocalizedMessage());
    }
  }

  public List<String> getLinkCommand(Job currentJob) {
    // Merge the output commands with the linker commands
    List<String> linkCommand = new ArrayList<>(link);
    linkCommand.addAll(currentJob.getOutputCommand());
    return linkCommand;
  }

  /** Returns the target platform to be used by the LLVM compiler */
  public String getDefaultTarget() throws ConfigurationException {
    // Some reference available here:
    // http://llvm.org/docs/doxygen/html/Triple_8h_source.html

    // Calling 'llc --version' for current target information
    // Note: Most of the LLVM tools also have this option
    Process process = null;
    try {
      process = new ProcessBuilder(getLlc(), "--version").redirectErrorStream(true).start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      StringBuilder versionOutput = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null)
        versionOutput.append(line).append(System.lineSeparator());

      // Create the regular expression required to find the target "triple"
      Pattern pattern = Pattern.compile("(Default target:\\s)([\\w\\-]+)");
      Matcher matcher = pattern.matcher(versionOutput.toString());

      if (matcher.find()) return matcher.group(2);
      else
        throw new ConfigurationException(
            "Unable to find target in 'llc -version' output:"
                + System.lineSeparator()
                + versionOutput);
    } catch (IOException e) {
      throw new ConfigurationException("LLVM installation not found!");
    } finally {
      if (process != null) process.destroy();
    }
  }

  public static String getLLVMVersion() {
    Process process = null;
    try {
      process =
          new ProcessBuilder(getConfiguration().getLlc(), "--version")
              .redirectErrorStream(true)
              .start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      StringBuilder versionOutput = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null)
        versionOutput.append(line).append(System.lineSeparator());

      // Create the regular expression required to find the version
      Pattern pattern = Pattern.compile("(LLVM version\\s)(\\d+(\\.\\d+)*)");
      Matcher matcher = pattern.matcher(versionOutput.toString());

      if (matcher.find()) return matcher.group(2);
    } catch (IOException | ConfigurationException ignored) {

    } finally {
      if (process != null) process.destroy();
    }

    return "";
  }

  public static String getLLVMInformation() {
    // Calling 'llc -version' for LLVM information
    // Note: Most of the LLVM tools also have this option
    Process process = null;
    try {
      process =
          new ProcessBuilder(getConfiguration().getLlc(), "-version")
              .redirectErrorStream(true)
              .start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      StringBuilder information = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null && !line.isEmpty())
        information.append(line).append(System.lineSeparator());

      return information.toString();
    } catch (IOException | ConfigurationException e) {
      return "LLVM installation not found!";
    } finally {
      if (process != null) process.destroy();
    }
  }

  public String getLLVMOptimizationLevel() {
    // Set to empty string to check for race conditions in threads.
    return "-O3";
  }

  public String getOptimizationLevel() {
    // Set to empty string to check for race conditions in threads.
    return "-O3";
  }

  // TODO: Update this for other architectures, like the new Mac?
  public String getDataLayout() {
    if (dataLayout == null) {
      String endian = "e"; // little Endian
      String mangling;

      switch (getOs()) {
        case "Mac":
          mangling = "m:o-";
          break;
        case "Windows":
          mangling = "m:x-";
          break;
        case "Linux":
          mangling = "m:e-";
          break;
        default:
          mangling = "";
          break;
      }

      String pointerAlignment =
          "p:" + getArchitecture() + ":" + getArchitecture() + ":" + getArchitecture();
      String dataAlignment =
          "i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:64:64-f16:16:16-f32:32:32-f64:64:64-f80:128";
      String aggregateAlignment = "a:0:" + getArchitecture();
      String nativeIntegers = "n8:16:32:64";
      String stackAlignment = "S128";
      dataLayout =
          "--data-layout="
              + endian
              + "-"
              + mangling
              + pointerAlignment
              + "-"
              + dataAlignment
              + "-"
              + aggregateAlignment
              + "-"
              + nativeIntegers
              + "-"
              + stackAlignment;
    }

    return dataLayout;
  }

  public static boolean isWindows() {
    try {
      Configuration configuration = getConfiguration();
      String os = configuration.getOs();
      if (os == null) return false;
      else return os.equals("Windows");
    } catch (ConfigurationException e) {
      return false;
    }
  }
}
