package com.dgrissom.imagescript.language.tokens.operators;

import com.dgrissom.imagescript.language.Environment;
import com.dgrissom.imagescript.language.tokens.ValueToken;
import com.dgrissom.imagescript.language.tokens.types.Type;

public class ExponentiationOperator extends Operator {
    public ExponentiationOperator() {
        super("^", 4, Associativity.RIGHT);
    }

    @Override
    public ValueToken evaluate(Environment environment, ValueToken t1, ValueToken t2) {
        double value = Math.pow((double) t1.parseValue(), (double) t2.parseValue());
        return new ValueToken(String.valueOf(value), Type.NUMBER);
    }
}
