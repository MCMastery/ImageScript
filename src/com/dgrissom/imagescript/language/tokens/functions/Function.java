package com.dgrissom.imagescript.language.tokens.functions;

import com.dgrissom.imagescript.language.Environment;
import com.dgrissom.imagescript.language.tokens.Token;
import com.dgrissom.imagescript.language.tokens.ValueToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Function extends Token {
    public static final SizeFunction SIZE = new SizeFunction();
    public static final FillFunction FILL = new FillFunction();
    public static final TextFunction TEXT = new TextFunction();

    private static Set<Function> defaultFunctions;

    static {
        defaultFunctions = new HashSet<>();

        defaultFunctions.add(SIZE);
        defaultFunctions.add(FILL);
        defaultFunctions.add(TEXT);

        defaultFunctions = Collections.unmodifiableSet(defaultFunctions);
    }

    public static Set<Function> getDefaultFunctions() {
        return defaultFunctions;
    }

    public static Function fromToken(Token token, Environment environment) {
        for (Function function : environment.getFunctions()) {
            if (token.equals(function))
                return function;
            for (String alias : function.getAliases())
                if (token.getToken().equals(alias))
                    return function;
        }
        return null;
    }
    private final String name;
    private final int args;
    private final String[] aliases;

    public Function(String name, int args, String... aliases) {
        super(name);
        this.name = name;
        this.args = args;
        this.aliases = aliases;
    }

    public String getName() {
        return this.name;
    }
    public int getArguments() {
        return this.args;
    }
    public String[] getAliases() {
        return this.aliases;
    }

    public abstract ValueToken evaluate(Environment environment, ValueToken... tokens);
}
