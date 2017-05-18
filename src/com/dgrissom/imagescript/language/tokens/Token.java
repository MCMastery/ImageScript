package com.dgrissom.imagescript.language.tokens;

public class Token {
    public static final Token LEFT_PARENTHESIS = new Token("(");
    public static final Token RIGHT_PARENTHESIS = new Token(")");
    public static final Token ARGUMENT_SEPARATOR = new Token(",");

    private final String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    @Override
    public String toString() {
        return this.token;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token))
            return false;
        return ((Token) o).getToken().equals(this.token);
    }
}
