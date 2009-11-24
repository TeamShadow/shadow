package shadow.parser;

import shadow.parser.javacc.ShadowParserConstants;
import shadow.parser.javacc.Token;

public final class GTToken extends Token {
    public int realKind = ShadowParserConstants.GT;
    public GTToken(int ofKind, String image) {
       super(ofKind, image);
    }

 public static Token newToken(int ofKind)
 {
    return newToken(ofKind, null);
 }

}