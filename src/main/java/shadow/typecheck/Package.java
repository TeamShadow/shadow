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

package shadow.typecheck;

import org.jetbrains.annotations.NotNull;
import shadow.doctool.Documentation;
import shadow.typecheck.type.Type;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A representation of a Shadow package, with awareness of both its parent and child packages.
 *
 * @author Barry Wittman
 */
public class Package implements Comparable<Package>, Iterable<Type> {

  /**
   * A PackageException is thrown when there is an attempt to add a type to a package that already
   * contains that type.
   */
  public static class PackageException extends Exception {
    private static final long serialVersionUID = -2535851392865246026L;

    public PackageException(String message) {
      super(message);
    }
  }

  // Packages inside this package
  private final HashMap<String, Package> children = new HashMap<>();
  private final String name;
  private final Package parent;
  private String mangledName = null;

  // Types inside the current package
  private final HashMap<String, Type> types = new HashMap<>();
  private Documentation documentation = null;

  /**
   * Creates a default package. Should only be used for the package at the root of a package tree.
   */
  public Package() {
    this("", null);
  }

  /**
   * Creates a package with a given name and parent.
   *
   * @param name name of this package
   * @param parent parent of this package
   */
  private Package(String name, Package parent) {
    this.name = name;
    this.parent = parent;
  }

  /**
   * Adds a new package within this package and returns it. If a child package with the given name
   * already exists, it returns that instead.
   *
   * @param name name of the new child package
   * @return child package
   */
  public Package addPackage(String name) {
    if (children.containsKey(name)) return children.get(name);

    Package newPackage = new Package(name, this);
    children.put(name, newPackage);

    return newPackage;
  }

  /**
   * Adds an entire package path to the package tree and returns the the deepest child package
   * specified by the path.
   *
   * @param path string representation of the package path
   * @return child package specified by the path
   */
  public Package addQualifiedPackage(String path) {
    if (path.length() > 0 && !path.equals("default")) {
      String[] packages = path.split(":");

      Package parent = this;
      for (String package_ : packages) parent = parent.addPackage(package_);

      return parent;
    } else return this;
  }

  /**
   * Adds a type to the given package, throwing a <code>PackageException</code> if the type is
   * already present.
   *
   * @param type type to be added to the package
   * @throws PackageException thrown when a package around contains a type with the given name
   */
  public void addType(Type type) throws PackageException {
    // name doesn't include packages or type parameters
    String name = type.toString(Type.NO_OPTIONS);

    if (!types.containsKey(name)) {
      types.put(name, type);
      type.setPackage(this); // Informs type of its package
    } else
      throw new PackageException("Package " + this + " already contains type " + type);
  }

  public boolean containsType(Type type) {
    // name doesn't include packages or type parameters
    String name = type.toString(Type.NO_OPTIONS);
    return types.containsKey(name);
  }

  /**
   * Gets all child packages within this package.
   *
   * @return map of child packages
   */
  public HashMap<String, Package> getChildren() {
    return children;
  }

  /**
   * Gets name of package including all parent packages.
   *
   * @return full package name
   */
  public String getQualifiedName() {
    if (parent == null || parent.getName().isEmpty()) return getName();

    return parent.getQualifiedName() + ':' + getName();
  }

  /**
   * Gets name of package mangled for compiler output.
   *
   * @return mangled package name
   */
  public String getMangledName() {
    // Added a cached mangled name since the profiler reported this method in heavy rotation
    if (mangledName == null) {
      if (parent == null || parent.getName().isEmpty())
        mangledName = Type.mangle(getName());
      else
        mangledName = parent.getMangledName() + '.' + Type.mangle(getName());
    }
    return mangledName;
  }

  /**
   * Gets relative file system path corresponding to package name.
   *
   * @return string representation of path
   */
  public String getPath() {
    return getQualifiedName().replace(':', File.separatorChar);
  }

  /**
   * Gets parent package.
   *
   * @return parent package
   */
  public Package getParent() {
    return parent;
  }

  /**
   * Get name of package without parent packages.
   *
   * @return package name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets child package with a given name, which may be several packages deep.
   *
   * @param name name of child package
   * @return named package or <code>null</code> if not found
   */
  public Package getChild(String name) {
    if (name.contains(":")) {
      int separator = name.indexOf(":");
      String child = name.substring(0, separator);
      if (children.containsKey(child))
        return children.get(child).getChild(name.substring(separator + 1));
      else return null;
    }

    return children.get(name);
  }

  /**
   * Gets type with a given name from the package.
   *
   * @param name name of the type
   * @return specified type or <code>null</code> if not found
   */
  public Type getType(String name) {
    return types.get(name);
  }

  /**
   * Gets a collection of all types stored in the package.
   *
   * @return collection of types
   */
  public Collection<Type> getTypes() {
    return types.values();
  }

  /**
   * Adds processed documentation to the package.
   *
   * @param documentation documentation to be added
   */
  public void setDocumentation(Documentation documentation) {
    this.documentation = documentation;
  }

  /**
   * Gets the documentation added to the package.
   *
   * @return documentation
   */
  public Documentation getDocumentation() {
    return documentation;
  }

  /**
   * Determines if the package has documentation.
   *
   * @return <code>true</code> if the package has documentation
   */
  public boolean hasDocumentation() {
    return (documentation != null);
  }

  @Override
  public int hashCode() {
    return getQualifiedName().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o instanceof Package) {
      Package p = (Package) o;
      return getQualifiedName().equals(p.getQualifiedName());
    }

    return false;
  }

  /** Compares packages based on lexicographic ordering of full names. */
  @Override
  public int compareTo(Package o) {
    return getQualifiedName().compareTo(o.getQualifiedName());
  }

  @Override
  public String toString() {
    if (parent == null) return "default";
    else return getQualifiedName();
  }

  /**
   * Clears internal data structures, returning the package to a condition like that immediately
   * after construction.
   */
  public void clear() {
    children.clear();
    types.clear();
    if (documentation != null) documentation.clear();
  }

  @Override
  @NotNull
  public Iterator<Type> iterator() {
    return new PackageIterator();
  }

  /*
   * Iterator allows the package to iterate over all the types inside itself
   * and child packages.
   */
  private class PackageIterator implements Iterator<Type> {
    private Iterator<Type> typeIterator = types.values().iterator();
    private final Iterator<Package> packageIterator = children.values().iterator();

    @Override
    public boolean hasNext() {
      while (!typeIterator.hasNext()) {
        if (packageIterator.hasNext()) typeIterator = packageIterator.next().iterator();
        else return false;
      }
      return true;
    }

    @Override
    public Type next() {
      while (!typeIterator.hasNext()) {
        if (packageIterator.hasNext()) typeIterator = packageIterator.next().iterator();
        else throw new NoSuchElementException();
      }

      return typeIterator.next();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
