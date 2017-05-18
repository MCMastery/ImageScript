package com.dgrissom.imagescript.language.tokens.types;

import com.dgrissom.imagescript.language.Environment;
import com.dgrissom.imagescript.language.tokens.Token;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Type {
    public static final Number NUMBER = new Number();
    public static final Text TEXT = new Text();
    public static final HexColor HEX_COLOR = new HexColor();

    private static Set<Type> defaultTypes;

    static {
        defaultTypes = new HashSet<>();

        defaultTypes.add(NUMBER);
        defaultTypes.add(TEXT);
        defaultTypes.add(HEX_COLOR);

        defaultTypes = Collections.unmodifiableSet(defaultTypes);
    }

    public static Set<Type> getDefaultTypes() {
        return defaultTypes;
    }

    public static Type typeOf(Token token, Environment environment) {
        for (Type type : environment.getTypes())
            if (type.tokenIsInstance(token))
                return type;
        return null;
    }

    public Type() {

    }

    public abstract boolean tokenIsInstance(Token token);
    public abstract Object parseObject(String value);
}
