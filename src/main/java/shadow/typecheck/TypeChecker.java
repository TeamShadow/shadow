/*
 * Copyright 2017 Team Shadow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package shadow.typecheck;

import shadow.ConfigurationException;
import shadow.Loggers;
import shadow.ShadowException;
import shadow.parse.Context;
import shadow.parse.ParseException;
import shadow.typecheck.type.AttributeType;
import shadow.typecheck.type.Type;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Utility class to hold static type-checking methods.
 *
 * @author Barry Wittman
 */
public class TypeChecker {

  public static class TypeCheckerOutput {
    public final List<Context> nodes;
    public final Package packageTree;
    public final Context main;

    public TypeCheckerOutput(List<Context> allNodes, Package packageTree, Context main) {
      this.nodes = Collections.unmodifiableList(allNodes);
      this.packageTree = packageTree;
      this.main = main;
    }
  }

  /**
   * Typechecks files and all files they depend on.
   * @param files files to typecheck (main file is the first)
   * @param useSourceFiles whether the source files should be recompiled
   * @param reporter object used to report errors
   * @param typeCheckOnly whether the source files are only being type-checked
   * @return nodes list of AST nodes for the classes to be compile
   * @throws ShadowException thrown if something goes wrong with type-checking
   * @throws ParseException thrown if the parser has a problem
   * @throws IOException thrown if files aren't accessible
   * @throws ConfigurationException thrown if there's a problem with Configuration
   */
  public static TypeCheckerOutput typeCheck(
      List<Path> files, boolean useSourceFiles, ErrorReporter reporter, boolean typeCheckOnly)
      throws ShadowException, IOException, ConfigurationException {
    Type.clearTypes();
    Package packageTree = new Package(); // Root of all packages, storing all types
    Path mainFile = files.get(0);

    /* Collector looks over all files and creates types for everything needed. */
    TypeCollector collector =
        new TypeCollector(packageTree, reporter, useSourceFiles, typeCheckOnly);

    /* Its return value maps all the types to the nodes that need compiling. */
    Map<Type, Context> nodeTable = collector.collectTypes(files);
    Map<Path, Context> fileTable = collector.getFileTable();

    /* Updates types, adding:
     *  Fields and methods
     *  Type parameters (including necessary instantiations)
     *  All types with type parameters (except for declarations) are UninitializedTypes
     *  Extends and implements lists
     */
    TypeUpdater updater = new TypeUpdater(packageTree, reporter, fileTable);
    nodeTable = updater.update(nodeTable);

    /* Select only nodes corresponding to outer types. */
    List<Context> nodes = new ArrayList<>();
    for (Context node : nodeTable.values()) if (!node.getType().hasOuter()) nodes.add(node);

    // Put all attribute types first so that they're typechecked before classes try to interpret them
    // TODO: Enforce that attributes cannot mark their methods with attributes, to avoid circular problems
    nodes.sort( (first, second) -> {
      boolean firstAttribute = first.getType() instanceof AttributeType;
      boolean secondAttribute = second.getType() instanceof AttributeType;
      if (firstAttribute == secondAttribute) // Both attributes or both not
        return 0;
      else if(firstAttribute)
        return -1;
      else
        return 1;
    });

    /* Do type-checking of statements, i.e., actual code. */
    StatementChecker checker = new StatementChecker(packageTree, reporter);
    for (Context node : nodes) {
      // Check all statements for type safety and other features.
      // Even .meta files need this in order to:
      //   - Populate types on nodes within constant expressions
      //   - Collect used types for a node
      checker.check(node);
    }

    // Note that all files in the fileTable have no extension
    return new TypeCheckerOutput(nodes, packageTree, fileTable.get(BaseChecker.stripExtension(files.get(0))));
  }

  /*
   * Prints a .meta file version of a given node, similar to a header file in C/C++.
   * These .meta files are used for type-checking as a speed optimization, to avoid
   * type-checking the full code.
   */
  public static void printMetaFile(Context node) {
    Path file = BaseChecker.stripExtension(node.getSourcePath());
    try {
      Path shadowVersion = BaseChecker.addExtension(file, ".shadow");
      Path metaVersion = BaseChecker.addExtension(file, ".meta");
      /* Add meta file if an updated one doesn't already exist. */
      if (!Files.exists(metaVersion)
          || (Files.exists(shadowVersion)
              && Files.getLastModifiedTime(shadowVersion).compareTo(Files.getLastModifiedTime(metaVersion)) > 0)) {
        // Because of compiler optimizations, we need a .meta file for all compiled code
        // So we simply copy the normal .shadow file into a .meta for attributes
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(metaVersion.toFile()));
        if (node.getType() instanceof AttributeType) {
          Files.copy(shadowVersion, outputStream);
        }
        else {
          PrintWriter out = new PrintWriter(outputStream);
          node.getType().printMetaFile(out, "");
          out.close();
        }
        outputStream.close();
      }
    } catch (IOException e) {
      Loggers.SHADOW.error("Failed to create meta file for " + node.getType());
    }
  }
}
