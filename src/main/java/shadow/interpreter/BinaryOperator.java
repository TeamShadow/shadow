package shadow.interpreter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public enum BinaryOperator {
    COALESCE("??"),
    OR("or"),
    XOR("xor"),
    AND("and"),
    BITWISE_OR("|"),
    BITWISE_XOR("^"),
    BITWISE_AND("&"),
    EQUAL("=="),
    NOT_EQUAL("!="),
    REFERENCE_EQUAL("==="),
    REFERENCE_NOT_EQUAL("!=="),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_THAN_OR_EQUAL("<="),
    GREATER_THAN_OR_EQUAL(">="),
    CAT("#"),
    RIGHT_SHIFT(">>"),
    LEFT_SHIFT("<<"),
    RIGHT_ROTATE(">>>"),
    LEFT_ROTATE("<<<"),
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULUS("%");

    private static final Map<String, BinaryOperator> nameToOperator = Arrays.stream(BinaryOperator.values()).collect(toMap(BinaryOperator::getName, Function.identity()));

    private final String name;

    BinaryOperator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BinaryOperator fromString(String name) {
        return nameToOperator.get(name);
    }
}
