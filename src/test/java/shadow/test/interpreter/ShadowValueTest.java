package shadow.test.interpreter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shadow.interpreter.ShadowDouble;
import shadow.interpreter.ShadowFloat;
import shadow.interpreter.ShadowInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShadowValueTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "0", "12", "127y", "128uy", "28100s", "48213us", "3000000000u", "5000000000L","7000000000000000000L",
            "15000000000000000000uL", "0b1011", "0xdead", "0c777", "0b1011y", "0b1011uy", "0b1011s", "0b1011us",
            "0b1011u", "0b1011L", "0b1011uL", "0x2ay", "0xffuy", "0x1eads", "0xdeadus", "0xdeadL", "0xdeaduL",
            "0c77y", "0c77uy", "0c777s", "0c777us", "0c777u", "0c777L", "0c777uL"
    })
    void shadowInteger_parseNumber_toLiteral_equivalent(String literal) {
        assertEquals(literal, ShadowInteger.parseNumber(literal).toLiteral());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.0f", "3.4028235E38f", "1.4E-45f"})
    void shadowFloat_parseNumber_toLiteral_equivalent(String literal) {
        assertEquals(literal, ShadowFloat.parseFloat(literal).toLiteral());
    }

    @Test
    void shadowFloat_toLiteral_specialValues() {
        assertEquals("0f / 0f", new ShadowFloat(Float.NaN).toLiteral());
        assertEquals("1f / 0f", new ShadowFloat(Float.POSITIVE_INFINITY).toLiteral());
        assertEquals("-1f / 0f", new ShadowFloat(Float.NEGATIVE_INFINITY).toLiteral());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.0", "1.7976931348623157E308", "4.9E-324"})
    void shadowDouble_parseNumber_toLiteral_equivalent(String literal) {
        assertEquals(literal, ShadowDouble.parseDouble(literal).toLiteral());
    }

    @Test
    void shadowDouble_toLiteral_specialValues() {
        assertEquals("0d / 0d", new ShadowDouble(Double.NaN).toLiteral());
        assertEquals("1d / 0d", new ShadowDouble(Double.POSITIVE_INFINITY).toLiteral());
        assertEquals("-1d / 0d", new ShadowDouble(Double.NEGATIVE_INFINITY).toLiteral());
    }
}
