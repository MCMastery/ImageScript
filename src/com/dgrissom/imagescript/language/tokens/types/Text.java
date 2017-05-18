package com.dgrissom.imagescript.language.tokens.types;

import com.dgrissom.imagescript.language.tokens.Token;

public class Text extends Type {
    @Override
    public boolean tokenIsInstance(Token token) {
        return token.getToken().length() >= 2 && token.getToken().startsWith("\"") && token.getToken().endsWith("\"");
    }

    @Override
    public Object parseObject(String value) {
        // strip off quotes
        return value.substring(1, value.length() - 1);
    }
}
