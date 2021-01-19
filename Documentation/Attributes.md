# Attributes in Shadow

Attributes are similar to annotations in Java - they provide a means to attach
simple, arbitrary metadata to methods for use at compile time. Currently, they
are used exclusively in "first party" contexts to enable compiler features that
might not otherwise be a good fit for dedicated keywords or syntax. Eventually,
it's likely that two major additions will be valuable:
- The ability to read attribute values at run-time
- Support for running third-party pre-processing code that consumes attributes
  at compile-time (a feature that Java also provides)

## Syntax

#### Attribute declaration
```
attribute mypackage@FooBar {
    int foo = 5;
    String bar;
}
```

No modifiers are needed (or allowed) on the attribute declaration or on its
fields. Every "invocation" of an attribute must populate all fields declared by
its parent type, except for those that are populated directly on the parent
type (e.g. `foo` above) - these are considered to have a "default" value. Note
that all field definitions must be compile-time constant expressions.

#### Attribute invocation
```
class MyClass {

    [mypackage@Foobar(bar = "fizzbuzz")]
    public myMethod() => () { ... }
}
```

Attribute invocations must be placed in `[ ... ]` block and separated by
commas. Any fields/parameters being populated must be assigned by name within
a `( ... )` scope following the attribute name. The `( ... )` block can be
omitted if no parameters are being passed. Ordering of parameters is not
important.

Note that attribute invocations are persisted in .meta files, including the
final computed value of any field-defining expressions.

## TODO

- Allow "repeated" attributes on the same method (i.e. the same type of
  attribute appearing more than once)
- Support attaching attributes to class and field declarations
- Persist attributes at run-time and allow access to them
- Ensure a less-visible attribute type can't be exposed by a more visible
method/field/class
- Allow omitting the field name from an attribute invocation if the attribute
  type only has one field

## Code-pointers

- [`Shadow.g4`](src/main/antlr4/Shadow.g4)
  - `compilationUnit`
  - `attributeDeclaration`
  - `classOrInterfaceBodyDeclaration`  
  - `attributeInvocations`  
- [`AttributeType`](src/main/java/shadow/typecheck/type/AttributeType.java)
- [`AttributeInvocation`](src/main/java/shadow/typecheck/type/AttributeInvocation.java)
- [`MethodSignature`](src/main/java/shadow/typecheck/type/MethodSignature.java)
  - `attachAttribute()`
  - `processAttributeTypes()`
  - `processAttributeValues()`
- [`TypeCollector`](src/main/java/shadow/typecheck/TypeCollector.java)
  - `visitAttributeDeclaration()`
  - `createType()`  
- [`TypeUpdater`](src/main/java/shadow/typecheck/TypeUpdater.java)
  - `visitMethodPost()`
  - `visitClassOrInterfaceType()`  
- [`ConstantFieldInterpreter`](src/main/java/shadow/interpreter/ConstantFieldInterpreter.java)
  - `evaluateAttributeFields()`
- [`ClassType`](src/main/java/shadow/typecheck/type/ClassType.java)
  - `printMetaFile()`