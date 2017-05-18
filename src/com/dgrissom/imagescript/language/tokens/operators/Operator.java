package com.dgrissom.imagescript.language.tokens.operators;

import com.dgrissom.imagescript.language.Environment;
import com.dgrissom.imagescript.language.tokens.functions.Function;
import com.dgrissom.imagescript.language.tokens.Token;
import com.dgrissom.imagescript.language.tokens.ValueToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Operator extends Function {
    public static final AdditionOperator ADDITION = new AdditionOperator();
    public static final SubtractionOperator SUBTRACTION = new SubtractionOperator();
    public static final MultiplicationOperator MULTIPLICATION = new MultiplicationOperator();
    public static final DivisionOperator DIVISION = new DivisionOperator();
    public static final ExponentiationOperator EXPONENTIATION = new ExponentiationOperator();

    private static Set<Operator> defaultOperators;

    static {
        defaultOperators = new HashSet<>();

        defaultOperators.add(ADDITION);
        defaultOperators.add(SUBTRACTION);
        defaultOperators.add(MULTIPLICATION);
        defaultOperators.add(DIVISION);
        defaultOperators.add(EXPONENTIATION);

        defaultOperators = Collections.unmodifiableSet(defaultOperators);
    }

    public static Set<Operator> getDefaultOperators() {
        return defaultOperators;
    }

    public static Operator fromToken(Token token, Environment environment) {
        for (Operator operator : environment.getOperators())
            if (token.equals(operator))
                return operator;
        return null;
    }

    public enum Associativity {
        LEFT, RIGHT
    }

    private final int precedence;
    private final Associativity associativity;

    public Operator(String token, int precedence, Associativity associativity) {
        super(token, 2);
        this.precedence = precedence;
        this.associativity = associativity;
    }

    public int getPrecedence() {
        return this.precedence;
    }
    public Associativity getAssociativity() {
        return this.associativity;
    }

    @Override
    public ValueToken evaluate(Environment environment, ValueToken... args) {
        return evaluate(environment, args[0], args[1]);
    }

    public abstract ValueToken evaluate(Environment environment, ValueToken t1, ValueToken t2);
}
