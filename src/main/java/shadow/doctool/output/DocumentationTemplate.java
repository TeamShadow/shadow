package shadow.doctool.output;

import shadow.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.type.Type;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public abstract class DocumentationTemplate {
  protected Map<String, String> arguments;
  protected Set<Type> typesToDocument;
  protected Set<Package> packagesToDocument;

  public DocumentationTemplate(
      Map<String, String> arguments,
      Set<Type> typesToDocument,
      Set<shadow.typecheck.Package> packagesToDocument) {
    this.arguments = arguments;
    this.typesToDocument = typesToDocument;
    this.packagesToDocument = packagesToDocument;
  }

  /** Outputs the formatted documentation files to the desired directory */
  public abstract void write(Path outputDirectory) throws IOException, ShadowException;
}
