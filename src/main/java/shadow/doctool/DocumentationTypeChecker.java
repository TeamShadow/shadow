/*
 * Copyright 2015 Team Shadow
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

package shadow.doctool;

import shadow.*;
import shadow.parse.Context;
import shadow.typecheck.Package;
import shadow.typecheck.*;
import shadow.typecheck.type.Type;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A basic type-checker used to gather enough data for documentation.
 *
 * @author Brian Stottler
 */
public class DocumentationTypeChecker {
  /**
   * Checks the types in the given files and returns a set containing those types, ready to be
   * documented.
   *
   * @param files list of files
   * @return set of types in those files
   * @throws ShadowException thrown if there is a type-checking problem
   * @throws IOException thrown if files aren't accessible
   * @throws ConfigurationException thrown if there is a configuration problem
   */
  public static Set<Type> typeCheck(List<Path> files)
      throws ShadowException, IOException, ConfigurationException {
    Package packageTree = new Package();

    ErrorReporter reporter = new ErrorReporter(Loggers.DOC_TOOL);

    /* Collector looks over all files and creates types for everything needed. */
    TypeCollector collector = new TypeCollector(packageTree, reporter, true, false);
    /* Its return value maps all the types to their AST nodes. */
    Map<Type, Context> typeTable = collector.collectTypes(files);

    // Maps file names to nodes.
    Map<String, Context> fileTable = collector.getFileTable();

    /* Updates types, adding:
     *  Fields and methods
     *  Type parameters (including necessary instantiations)
     *  All types with type parameters (except for declarations) are UninitializedTypes
     *  Extends and implements lists
     */
    TypeUpdater updater = new TypeUpdater(packageTree, reporter, fileTable);
    updater.update(typeTable);

    /* Filter out only those types associated with the files being documented. */
    Set<Type> types = new TreeSet<>();
    for (Path file : files) {
      Context node = fileTable.get(BaseChecker.stripExtension(file));
      if (node != null) types.add(node.getType());
    }

    return types;
  }
}
