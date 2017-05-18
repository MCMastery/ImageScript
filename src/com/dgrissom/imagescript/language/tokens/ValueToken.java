package com.dgrissom.imagescript.language.tokens;

import com.dgrissom.imagescript.language.tokens.types.Type;

public class ValueToken extends Token {
    public static final ValueToken NO_RETURN_VALUE = new ValueToken("", null);

    private final Type type;

    public ValueToken(String token, Type type) {
        super(token);
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }
    public Object parseValue() {
        return this.type.parseObject(getToken());
    }
}
