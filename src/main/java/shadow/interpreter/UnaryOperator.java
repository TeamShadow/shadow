package shadow.interpreter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public enum UnaryOperator {
    CAT("#"), // Also exists in BinaryOperator
    BITWISE_COMPLEMENT("~"),
    NOT("!"),
    NEGATE("-");

    private static final Map<String, UnaryOperator> nameToOperator =
            Arrays.stream(UnaryOperator.values()).collect(
                    toMap(UnaryOperator::getName, Function.identity()));

    public final String name;

    UnaryOperator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UnaryOperator fromString(String name) {
        return nameToOperator.get(name);
    }
}
