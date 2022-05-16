package shadow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinReg;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "system",
  "import",
  "target",
  "architecture",
  "cc",
  "os",
  "opt",
  "llc",
  "link",
  "libraryPaths",
  "libraries",
  "parent"
})
public class Configuration {

  public static final int SOURCE = 0;
  public static final int INCLUDE = 1;
  public static final int BINARY = 2;

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
    public Path deserialize(JsonParser jp, DeserializationContext context) throws IOException {
      JsonNode node = jp.getCodec().readTree(jp);
      Path path = Paths.get(node.asText());
      return configFile.resolveSibling(path).toAbsolutePath().normalize();
    }
  }

  // Deserializes paths (as keys) relative to the config file
  private static class PathKeyDeserializer extends KeyDeserializer {
    private final Path configFile;

    public PathKeyDeserializer(Path configFile) {
      this.configFile = configFile;
    }

    @Override
    public Path deserializeKey(String key, DeserializationContext context) {
      Path path = Paths.get(key);
      return configFile.resolveSibling(path).toAbsolutePath().normalize();
    }
  }


  public static final String DEFAULT_CONFIG_NAME = "shadow.json";
  public static final String SHADOW_HOME = "SHADOW_HOME";

  // Reasonable collection of common Windows libraries
  // Some are included with Visual Studio, and others are in the SDK
  public static final String[] WINDOWS_LIBRARIES = {
    "msvcrt.lib",
    "ucrt.lib", //Dynamic linking
    "vcruntime.lib", // Dynamic linking
    "kernel32.lib",
    // "user32.lib",
    // "gdi32.lib",
    // "winspool.lib",
    // "comdlg32.lib",
    "advapi32.lib",
    // "shell32.lib",
    /// "ole32.lib",
    // "oleaut32.lib",
    // "uuid.lib",
    // "odbc32.lib",
    // "odbccp32.lib"
  };

  private static Configuration globalConfig;

  private static final Logger logger = Loggers.SHADOW;

  // private Path configFile;
  private String dataLayout;

  @JsonProperty("system")
  private List<Path> system = new ArrayList<>();

  @JsonProperty("import")
  private Map<Path, Path> _import = new LinkedHashMap<>();

  @JsonProperty("target")
  private String target;

  @JsonProperty("architecture")
  private int architecture;

  @JsonProperty("cc")
  private String cc;

  @JsonProperty("os")
  private String os;

  @JsonProperty("opt")
  private String opt;

  @JsonProperty("llc")
  private String llc;

  @JsonProperty("linker")
  private String linker;

  @JsonProperty("libraryPaths")
  private List<String> libraryPaths;

  @JsonProperty("libraries")
  private List<String> libraries;

  @JsonProperty("parent")
  private Path parent;

  private final List<String> linkCommand = new ArrayList<>();

  @JsonProperty("system")
  public List<Path> getSystem() {
    return system;
  }

  @JsonProperty("import")
  public Map<Path, Path> getImport() {
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

  @JsonProperty("cc")
  public String getCC() {
    return cc;
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

  @JsonProperty("linker")
  public String getLinker() {
    return linker;
  }

  @JsonProperty("libraryPaths")
  public List<String> getLibraryPaths() {
    return libraryPaths;
  }

  @JsonProperty("libraries")
  public List<String> getLibraries() {
    return libraries;
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

  /** Clears the global compiler Configuration */
  public static void clearConfiguration() {
    globalConfig = null;
  }


  private static Configuration readConfiguration(String mainFilePath, String configFilePath)
      throws ConfigurationException, IOException {
    // Attempt to locate hierarchy of config files
    Path configFile = locateConfig(mainFilePath, configFilePath);

    Configuration configuration = parse(configFile);

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
   * 5. A file in the SHADOW_HOME directory with the default name
   */
  private static Path locateConfig(String mainFilePath, String configFilePath)
      throws FileNotFoundException, ConfigurationException {

    // Get the various search directories
    Path sourceDir =
        mainFilePath == null
            ? null
            : Paths.get(mainFilePath).getParent().normalize();
    Path workingDir = Paths.get("").toAbsolutePath().normalize();
    Path runningDir = getRunningDirectory().toAbsolutePath().normalize();

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
    }
    /// 5. A file in the SHADOW_HOME directory with the default name
    Map<String, String> environment = System.getenv();
    if (environment.containsKey(SHADOW_HOME)) {
      Path homeDir = Paths.get(environment.get(SHADOW_HOME)).toAbsolutePath().normalize();
      if (Files.exists(homeDir.resolve(defaultFile))) return homeDir.resolve(defaultFile);
    }

    throw new FileNotFoundException("Configuration file does not exist");
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

    if (cc == null) cc = "clang";


    if (target == null) target = getDefaultTarget();

    if (libraryPaths == null) {
      libraryPaths = new ArrayList<>();

      if (os.equals("Windows")) addWindowsLibraryPaths(libraryPaths);
    }

    if (libraries == null) {
      libraries = new ArrayList<>();

      switch (getOs()) {
        case "Mac":
          libraries.add("m");
          libraries.add("System");
          libraries.add("pthread");
          break;
        case "Windows":
          libraries.addAll(Arrays.asList(WINDOWS_LIBRARIES));
          break;
        case "Linux":
          libraries.add("m");
          libraries.add("rt");
          libraries.add("pthread");
          break;
      }
    }

    if (linker == null) {
      switch (getOs()) {
        case "Windows":
          // linker = "lld-link";
          linker = "clang";
          break;
        case "Mac":
          // Does Mac work at all now?  What about the new M chips?
          linker = "clang";
          break;
        default:
          linker = "clang++";
          break;
      }

      // Build link command
      linkCommand.add(linker);

      if (getOs().equals("Windows")) {
        linkCommand.add("-Wl,-nodefaultlib:libcmt");
        linkCommand.add("-D_DLL");
      }

      if (architecture == 32) linkCommand.add("-m32");
      else linkCommand.add("-m64");

      for (String path : libraryPaths) linkCommand.add("-L" + path);

      for (String library : libraries) linkCommand.add("-l" + library);
    }

    // Using running directory for system src, include, and bin
    if (system.isEmpty()) {
      Path directory = getRunningDirectory();
      system.add(directory);
      system.add(directory);
      system.add(directory);
    }

    if (_import == null) {
      // _import = new ArrayList<>();

      // If there are no imports, add the current directory for both src and bin
      Path currentDirectory = Paths.get("." + File.separator).toAbsolutePath().normalize();
      _import.put(currentDirectory, currentDirectory);
    }

    // Always put the system src and binary in the imports (if they're not already there)
    _import.put(system.get(SOURCE), system.get(BINARY));
  }

  /* Parses a config file and fills the corresponding fields */
  private static Configuration parse(Path configFile) throws IOException {
    try {
      ObjectMapper mapper =
          new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
      SimpleModule module = new SimpleModule();
      module.addDeserializer(Path.class, new PathDeserializer(configFile));
      module.addKeyDeserializer(Path.class, new PathKeyDeserializer(configFile));
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
        if (configuration.cc == null) configuration.cc = parent.cc;
        if (configuration.llc == null) configuration.llc = parent.llc;
        if (configuration.linker == null) configuration.linker = parent.linker;
        if (configuration.libraryPaths == null) configuration.libraryPaths = parent.libraryPaths;
        if (configuration.libraries == null) configuration.libraries = parent.libraries;
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
      return Paths.get(uri).getParent().toAbsolutePath().normalize();
    } catch (SecurityException e) {
      throw new ConfigurationException("Insufficient permissions to access the running directory");
    } catch (URISyntaxException e) {
      throw new ConfigurationException(e.getLocalizedMessage());
    }
  }

  public List<String> getLinkCommand(Job currentJob) {
    // Merge the output commands with the linker commands
    List<String> linkCommand = new ArrayList<>(this.linkCommand);
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

      StringBuilder versionOutput = new StringBuilder();
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null)
          versionOutput.append(line).append(System.lineSeparator());
      }

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
      StringBuilder versionOutput = new StringBuilder();
      try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null)
          versionOutput.append(line).append(System.lineSeparator());
      }

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
      StringBuilder information = new StringBuilder();
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty())
          information.append(line).append(System.lineSeparator());
      }

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

  private static Path getChildWithLargestVersion(Path path) {
    if (Files.isDirectory(path)) {
      try {
        // Get largest version number
        Path largest = null;
        for (Path child : Files.newDirectoryStream(path)) {
          if (largest == null
              || Main.compareVersions(
                      child.getFileName().toString(), largest.getFileName().toString())
                  > 0) largest = child;
        }
        return largest;
      } catch (IOException ignored) {
      }
    }
    return null;
  }

  private boolean addWindowsLibraryPaths(List<String> paths) {
    /* Example result:
    paths.add("C:\\Program Files (x86)\\Windows Kits\\10\\Lib\\10.0.18362.0\\um\\x64");
    paths.add("C:\\Program Files (x86)\\Windows Kits\\10\\Lib\\10.0.18362.0\\ucrt\\x64");
    */
    WinBase.SYSTEM_INFO info = new WinBase.SYSTEM_INFO();
    Kernel32.INSTANCE.GetNativeSystemInfo(info);
    boolean x86Windows =
        info.processorArchitecture.pi.wProcessorArchitecture.intValue()
            == 0; // PROCESSOR_ARCHITECTURE_INTEL

    final String SDK_KEY;
    if (x86Windows) // Unlikely with modern Windows
    SDK_KEY = "SOFTWARE\\Microsoft\\Microsoft SDKs\\Windows\\";
    else SDK_KEY = "SOFTWARE\\WOW6432Node\\Microsoft\\Microsoft SDKs\\Windows\\";

    // First, try to find Windows SDK path in the registry
    Path sdkPath = null;
    if (RegistryAccess.keyExists(WinReg.HKEY_LOCAL_MACHINE, SDK_KEY)) {
      String[] subkeys = RegistryAccess.getKeys(WinReg.HKEY_LOCAL_MACHINE, SDK_KEY);

      Arrays.sort(
          subkeys,
          (String key1, String key2) -> {
            if (key1.toLowerCase().startsWith("v") && key2.toLowerCase().startsWith("v"))
              return Main.compareVersions(key1.substring(1), key2.substring(1));

            return key1.compareTo(key2);
          });

      String subkey =
          SDK_KEY + "\\" + subkeys[subkeys.length - 1]; // Last one should be highest version
      if (RegistryAccess.keyExists(WinReg.HKEY_LOCAL_MACHINE, subkey)) {
        String folder =
            RegistryAccess.readString(WinReg.HKEY_LOCAL_MACHINE, subkey, "InstallationFolder");
        sdkPath = Paths.get(folder).resolve("Lib");
      }
    }

    // If registry didn't work, look in the usual place in the directories
    if (sdkPath == null) {
      if (x86Windows)
        sdkPath =
            getChildWithLargestVersion(Paths.get(System.getenv("ProgramFiles"), "Windows Kits"));
      else // Weirdly, x64 needs the x86 specification
      sdkPath =
            getChildWithLargestVersion(
                Paths.get(System.getenv("ProgramFiles(x86)"), "Windows Kits"));
      if (sdkPath == null) return false;
      sdkPath = sdkPath.resolve("Lib");
    }

    // Inside Lib, look for largest version, like 10.0.18362.0
    sdkPath = getChildWithLargestVersion(sdkPath);
    if (sdkPath == null) return false;

    sdkPath = sdkPath.toAbsolutePath().normalize();

    Path ucrt = sdkPath.resolve("ucrt");
    Path um = sdkPath.resolve("um");
    if (architecture == 32) {
      ucrt = ucrt.resolve("x86");
      um = um.resolve("x86");
    } else {
      ucrt = ucrt.resolve("x64");
      um = um.resolve("x64");
    }

    if (!Files.isDirectory(ucrt) || !Files.isDirectory(um)) return false;

    // Finally, add SDK directories to list
    paths.add(ucrt.toString());
    paths.add(um.toString());

    return true;
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
