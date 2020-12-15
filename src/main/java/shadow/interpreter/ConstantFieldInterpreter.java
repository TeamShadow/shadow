package shadow.interpreter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import shadow.Loggers;
import shadow.ShadowException;
import shadow.interpreter.InterpreterException.Error;
import shadow.parse.Context;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.Package;
import shadow.typecheck.TypeChecker;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.SubscriptType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.UnboundMethodType;

/**
 * A lightweight wrapper around {@link ASTInterpreter} that evaluates compile-time constant fields.
 *
 * In particular, this class handles dependencies between constant fields and ensures no circular
 * references exist.
 */
public class ConstantFieldInterpreter extends ASTInterpreter {

	/* Stack for current prefix (needed for arbitrarily long chains of expressions). */
	private LinkedList<Context> curPrefix = new LinkedList<>();

	private final Map<FieldKey, VariableDeclaratorContext> allFields;

	// Used to find circular dependencies - should be cleared after each top-level call to
	// visit(variableDeclarator)
	private final Set<FieldKey> visitedFields = new HashSet<>();

	// The "root" of the current dependency graph being evaluated
	private VariableDeclaratorContext rootFieldCtx = null;

	private ConstantFieldInterpreter(
			Package packageTree, ErrorReporter reporter, Map<FieldKey, VariableDeclaratorContext> allFields) {
		super(packageTree, reporter);
		this.allFields = allFields;
	}

	/** A key that uniquely identifies a field (including taking its parent type into account) */
	public static class FieldKey {
		public final Type parentType;
		public final String fieldName;

		public FieldKey(Type parentType, String fieldName) {
			this.parentType = parentType;
			this.fieldName = fieldName;
		}

		@Override
		public int hashCode() {
			return Objects.hash(parentType, fieldName);
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof FieldKey)) {
				return false;
			}

			FieldKey other = (FieldKey) o;
			return Objects.equals(parentType, other.parentType)
					&& Objects.equals(fieldName, other.fieldName);
		}
	}

	/**
	 * Assumes that type-checking has already happened, meaning that all statements
	 * are valid and all references to fields are legitimate.
	 *
	 * @param nodes List of AST nodes for classes under compilation. Generally comes from
	 *  {@link TypeChecker#typeCheck(Path, boolean, ErrorReporter)}.
	 */
	public static void evaluateConstants(Package packageTree, List<Context> nodes) throws ShadowException {
		// We also want to process fields from inner types. Order doesn't really matter.
		List<Type> typesIncludingInner = nodes.stream().map(Context::getType).collect(Collectors.toList());
		nodes.stream()
		.map(Context::getType)
		.map(Type::recursivelyGetInnerTypes)
		.forEach(typesIncludingInner::addAll);

		Map<FieldKey, VariableDeclaratorContext> constantFields = getConstantFields(typesIncludingInner);

		ConstantFieldInterpreter visitor =
				new ConstantFieldInterpreter(
						packageTree, new ErrorReporter(Loggers.AST_INTERPRETER), constantFields);
		for (FieldKey fieldKey : constantFields.keySet()) {
			VariableDeclaratorContext fieldCtx = constantFields.get(fieldKey);

			// Skip fields that were evaluated recursively while evaluating other fields
			if (fieldCtx.getInterpretedValue() != null) {
				continue;
			}

			visitor.visitRootField(fieldKey, fieldCtx);
		}

		visitor.printAndReportErrors();
	}

	private static Map<FieldKey, VariableDeclaratorContext> getConstantFields(List<Type> types) {
		Map<FieldKey, VariableDeclaratorContext> constantFields = new HashMap<>();

		for (Type type : types) {
			for (String fieldName : type.getFields().keySet()) {
				VariableDeclaratorContext fieldCtx = type.getField(fieldName);
				if (fieldCtx.getModifiers().isConstant()) {
					constantFields.put(new FieldKey(type, fieldName), fieldCtx);
				}
			}
		}

		return constantFields;
	}

	public void visitRootField(FieldKey rootFieldKey, VariableDeclaratorContext rootFieldCtx) {
		// TODO: Consider calling BaseChecker#clear (but make sure errors are reported before clearing)
		this.rootFieldCtx = rootFieldCtx;
		visitedFields.clear();
		visitedFields.add(rootFieldKey);
		visit(rootFieldCtx);
	}

	// TODO: Disallow references to inner class fields from outer class fields?
	//  c.f. test-negative/compile/invalid-constant-dependency
	@Override
	protected ShadowValue dereferenceField(
			Type parentType, String fieldName, Context referenceCtx) {
		FieldKey fieldKey = new FieldKey(parentType, fieldName);

		if (!allFields.containsKey(fieldKey)) {
			addError(Error.UNKNOWN_REFERENCE.getException(referenceCtx));
		}
		VariableDeclaratorContext fieldCtx = allFields.get(fieldKey);

		// We don't need to check if a node has been visited if it already has a value - that
		// couldn't have happened if it led to a cycle
		if (fieldCtx.getInterpretedValue() != null) {
			return fieldCtx.getInterpretedValue();
		}

		// If we've already seen this field without getting a value for it, that means we've found
		// a cycle
		if (visitedFields.contains(fieldKey)) {
			addError(Error.CIRCULAR_REFERENCE.getException(rootFieldCtx.generalIdentifier()));
			return ShadowValue.INVALID;
		}
		visitedFields.add(fieldKey);

		visit(fieldCtx);
		return fieldCtx.getInterpretedValue();
	}
}
