package shadow.doctool.output;

import shadow.ShadowException;
import shadow.doctool.DocumentationException;
import shadow.doctool.output.HtmlWriter.Attribute;
import shadow.typecheck.Package;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/** Displays a list of all known packages and their descripitons */
public class OverviewPage extends Page {
  public static final String PAGE_NAME = "$overview";
  private static final Path relativePath = Paths.get(PAGE_NAME + EXTENSION);

  private final Set<Package> packages;

  public OverviewPage(StandardTemplate master, Set<Package> packages) {
    super(master);
    this.packages = packages;
  }

  public void write(Path root) throws ShadowException, IOException {
    // Find/create the directory chain where the document will reside
    //noinspection ResultOfMethodCallIgnored
    root.toFile().mkdirs();

    // Begin writing to the document itself
    Path output = root.resolve(relativePath);
    FileWriter fileWriter = new FileWriter(output.toFile());
    HtmlWriter out = new HtmlWriter(fileWriter);

    out.openTab("html");
    writeHtmlHead("Overview", out);
    out.openTab("body");

    writeNavBar(null, out);

    writeHeader(out);
    writeTable(packages, out);

    out.closeUntab();
    out.closeUntab();

    fileWriter.close();
  }

  private static void writeHeader(HtmlWriter out) throws ShadowException {
    out.openTab("div", new Attribute("class", "header"));

    out.fullLine("h2", "Overview");

    out.closeUntab();
  }

  private void writeTable(Set<Package> knownPackages, HtmlWriter out)
      throws ShadowException {
    if (!knownPackages.isEmpty()) {
      // List packages in alphabetical order, documenting only those that
      // have contents
      List<Package> fullList = new ArrayList<>(knownPackages);
      Collections.sort(fullList);
      List<Package> documentList = new ArrayList<>();
      for (Package current : fullList) if (!current.getTypes().isEmpty()) documentList.add(current);

      out.openTab("div", new Attribute("class", "block"));
      out.fullLine("h3", "Package Summary");
      out.openTab("table", new Attribute("class", "summarytable"));

      writeTableRow(out, true, "Package", "Description");
      boolean shaded = false;
      for (Package current : documentList) {
        writeTableEntry(current, knownPackages, out, shaded);
        shaded = !shaded;
      }

      out.closeUntab();
      out.closeUntab();
    }
  }

  private void writeTableEntry(
      Package current, Set<Package> linkablePackages, HtmlWriter out, boolean shaded)
      throws ShadowException {
    if (shaded) out.openTab("tr", new Attribute("class", "shaded"));
    else out.openTab("tr");
    out.open("td");
    writePackageName(current, linkablePackages, out);
    out.closeLine();
    out.open("td");
    if (current.hasDocumentation()) writeInlineTags(current.getDocumentation().getSummary(), out);
    out.closeLine();
    out.closeUntab();
  }

  private static void writePackageName(Package pkg, Set<Package> linkablePackages, HtmlWriter out)
      throws ShadowException {
    String packageName = pkg.getQualifiedName();
    if (packageName.isEmpty()) packageName = "default";

    if (linkablePackages.contains(pkg))
      if (pkg.getQualifiedName().isEmpty())
        writeLink(PackagePage.PAGE_NAME + EXTENSION, "default", out);
      else
        writeLink(
            pkg.getQualifiedName().replaceAll(":", "/") + "/" + PackagePage.PAGE_NAME + EXTENSION,
            packageName,
            out);
    else out.add(packageName);
  }

  public Path getRelativePath() {
    return relativePath;
  }
}
