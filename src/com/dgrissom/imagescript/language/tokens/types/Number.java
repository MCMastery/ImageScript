package com.dgrissom.imagescript.language.tokens.types;

import com.dgrissom.imagescript.language.tokens.Token;

public class Number extends Type {
    @Override
    public boolean tokenIsInstance(Token token) {
        try {
            Double.parseDouble(token.getToken());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Double parseObject(String value) {
        return Double.parseDouble(value);
    }
}
