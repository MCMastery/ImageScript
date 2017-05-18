package com.dgrissom.imagescript.language.tokens.operators;

import com.dgrissom.imagescript.language.Environment;
import com.dgrissom.imagescript.language.tokens.ValueToken;
import com.dgrissom.imagescript.language.tokens.types.Type;

public class AdditionOperator extends Operator {
    public AdditionOperator() {
        super("+", 2, Associativity.LEFT);
    }

    @Override
    public ValueToken evaluate(Environment environment, ValueToken t1, ValueToken t2) {
        double value = (double) t1.parseValue() + (double) t2.parseValue();
        return new ValueToken(String.valueOf(value), Type.NUMBER);
    }
}
