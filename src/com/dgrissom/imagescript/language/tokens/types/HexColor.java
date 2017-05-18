package com.dgrissom.imagescript.language.tokens.types;

import com.dgrissom.imagescript.language.tokens.Token;

import java.awt.Color;


public class HexColor extends Type {
    @Override
    public boolean tokenIsInstance(Token token) {
        if (!token.getToken().startsWith("#"))
            return false;
        try {
            parseObject(token.getToken());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public Color parseObject(String value) {
        // convert #ff0 to #ffff00
        if (value.length() == 4) {
            String before = value;
            value = "#";
            for (int i = 1; i < before.length(); i++)
                value += before.charAt(i) + "" +  before.charAt(i);
        }
        return Color.decode(value);
    }
}
