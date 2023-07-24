package shadow.test.output;

import org.junit.jupiter.api.*;
import shadow.*;
import shadow.typecheck.BaseChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BuildTests {
  private final ArrayList<String> args = new ArrayList<>();

  @BeforeEach
  public void setup() {
    System.out.println("Working Directory = " + System.getProperty("user.dir"));

    String os = System.getProperty("os.name").toLowerCase();

    args.add("-c");
    if (os.contains("windows")) args.add("windows.json");
    else if (os.contains("mac")) args.add("mac.json");
    else args.add("linux.json");

    //args.add("-r");
    //args.add("-f");
    //args.add("-v");
  }

  @Test
  public void testBuildSystem() throws Exception {

    List<Path> sourceFiles = new ArrayList<>();
    List<Path> objectFiles = new ArrayList<>();
    Path sourceRoot = Path.of("src");
    Path objectRoot = Path.of("bin");
    addDirectories(sourceRoot.resolve("shadow"), sourceFiles);
    for (Path file : sourceFiles) {
      Path objectFile = BaseChecker.changeExtension(objectRoot.resolve(sourceRoot.relativize(file)), ".o");
      if (Files.exists(objectFile))
        Files.delete(objectFile);

      objectFiles.add(objectFile);
    }

    args.add("-b");
    Main.run(args.toArray(new String[] {}));

    for (Path file : objectFiles) {
      Assertions.assertTrue(Files.exists(file));
      Files.delete(file);
    }
  }

  private void addDirectories(Path directory, List<Path> files) {
    try (Stream<Path> stream = Files.list(directory)) {
      stream
              .filter(file -> Files.isDirectory(file) && !file.getFileName().toString().equals("test")).forEach(f -> addShadowFiles(f, files));
    } catch (IOException ignored) {
    }
  }

  private void addShadowFiles(Path directory, List<Path> files) {
    try (Stream<Path> stream = Files.walk(directory)) {
      stream
              .filter(file -> file.toString().endsWith(".shadow")).forEach(files::add);
    } catch (IOException ignored) {
    }
  }

}
