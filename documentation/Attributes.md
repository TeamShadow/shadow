# Attributes in Shadow

Attributes are similar to annotations in Java - they provide a means to attach simple, arbitrary metadata to methods for
use at compile time. Currently, they are used exclusively in "first party" contexts to enable compiler features that
might not otherwise be a good fit for dedicated keywords or syntax. Eventually, it's likely that two major additions
will be valuable:

- The ability to read attribute values at run-time
- Support for running third-party pre-processing code that consumes attributes at compile-time (a feature that Java also
  provides)

## Syntax

#### Attribute declaration

```
attribute mypackage@FooBar {
    int foo = 5;
    String bar;
	
	create(String input) {
		bar = input;
	}	
}
```

No modifiers are needed (or allowed) on the attribute declaration or on its fields. Every "invocation" of an attribute
calls the constructor. Note that all field definitions must be compile-time constant expressions.

#### Attribute invocation

```
class MyClass {

    [mypackage@Foobar("fizzbuzz")]
    public myMethod() => () { ... }
}
```

Attribute invocations must be placed in square brackets (`[ ]`) block and separated by commas. The parentheses (`( )`) following
the attribute name can be left off if the default (argumentless) create is used. 

Note that attribute invocations persist in .meta files, including the final computed value of any field-defining
expressions.

## TODO

- Support attaching attributes to class and field declarations
- Ensure a less-visible attribute type can't be exposed by a more visible method/field/class

## Code-pointers

- [`Shadow.g4`](../src/main/antlr4/Shadow.g4)
    - `compilationUnit`
    - `attributeDeclaration`
    - `classOrInterfaceBodyDeclaration`
    - `attributeInvocations`
- [`AttributeType`](../src/main/java/shadow/typecheck/type/AttributeType.java)
- [`AttributeInvocation`](../src/main/java/shadow/typecheck/type/AttributeInvocation.java)
- [`MethodSignature`](../src/main/java/shadow/typecheck/type/MethodSignature.java)
    - `attachAttribute()`
    - `processAttributeTypes()`
    - `processAttributeValues()`
- [`TypeCollector`](../src/main/java/shadow/typecheck/TypeCollector.java)
    - `visitAttributeDeclaration()`
    - `createType()`
- [`TypeUpdater`](../src/main/java/shadow/typecheck/TypeUpdater.java)
    - `visitMethodPost()`
    - `visitClassOrInterfaceType()`
- [`ConstantFieldInterpreter`](../src/main/java/shadow/interpreter/ConstantFieldInterpreter.java)
    - `evaluateAttributeFields()`
- [`ClassType`](../src/main/java/shadow/typecheck/type/ClassType.java)
    - `printMetaFile()`