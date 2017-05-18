package com.dgrissom.imagescript.language;

import com.dgrissom.imagescript.EditorController;
import com.dgrissom.imagescript.language.tokens.functions.Function;
import com.dgrissom.imagescript.language.tokens.Token;
import com.dgrissom.imagescript.language.tokens.ValueToken;
import com.dgrissom.imagescript.language.tokens.operators.Operator;
import com.dgrissom.imagescript.language.tokens.types.Type;

import java.util.*;

public class ShuntingYard {
    private ShuntingYard() {}

    public static ValueToken evaluate(String expression, Environment environment) {
        return evaluatePostfix(infixToPostfix(tokenize(expression, environment), environment), environment);
    }

    private static String escapeRegex(String s) {
        String escaped = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isAlphabetic(c))
                escaped += "\\";
            escaped += c;
        }
        return escaped;
    }

    public static List<Token> tokenize(String expression, Environment environment) {
        String tokensString = "";
        for (Operator operator : environment.getOperators())
            tokensString += escapeRegex(operator.getToken()) + "|";
        for (Function function : environment.getFunctions())
            tokensString += escapeRegex(function.getName()) + "|";
        tokensString += escapeRegex(Token.ARGUMENT_SEPARATOR.getToken()) + "|";
        tokensString += escapeRegex(Token.LEFT_PARENTHESIS.getToken()) + "|";
        tokensString += escapeRegex(Token.RIGHT_PARENTHESIS.getToken());

        String regex = "((?<=" + tokensString + ")|(?=" + tokensString + "))";
        String[] split = expression.split(regex);
        List<Token> tokens = new ArrayList<>();
        for (String token : split) {
            Token t = new Token(token);
            Type type = Type.typeOf(t, environment);
            if (type != null) {
                // if it is a value, make a value token and add it
                tokens.add(new ValueToken(token, type));
                continue;
            }
            Operator operator = Operator.fromToken(t, environment);
            if (operator != null) {
                // if it is an operator, add operator token
                tokens.add(operator);
                continue;
            }
            Function function = Function.fromToken(t, environment);
            if (function != null) {
                // if it is a function, add function token
                tokens.add(function);
                continue;
            }
            if (token.isEmpty() || token.trim().isEmpty() || t.equals(Token.LEFT_PARENTHESIS) || t.equals(Token.RIGHT_PARENTHESIS) || t.equals(Token.ARGUMENT_SEPARATOR))
                continue;
            // not a value or operator or normal token
            //... what is this???
            EditorController.getInstance().consoleFatal("Unknown token (" + token + ")!", environment.getCurrentLineNumber());
            return null;
        }
        return tokens;
    }

    public static Queue<Token> infixToPostfix(List<Token> tokens, Environment environment) {
        if (tokens == null)
            return null;
        Queue<Token> output = new ArrayDeque<>();
        Stack<Token> operatorStack = new Stack<>();

        for (Token token : tokens) {
            if (token instanceof ValueToken)
                output.add(token);
            else if (token instanceof Operator) {
                Operator o1 = (Operator) token;
                while (operatorStack.size() != 0) {
                    Token t = operatorStack.peek();
                    if (!(t instanceof Operator))
                        break;
                    Operator o2 = (Operator) t;
                    if ((o1.getAssociativity() == Operator.Associativity.LEFT && o1.getPrecedence() <= o2.getPrecedence())
                        || o1.getAssociativity() == Operator.Associativity.RIGHT && o1.getPrecedence() < o2.getPrecedence())
                        output.add(operatorStack.pop());
                    else
                        break;
                }
                operatorStack.push(o1);
            }
            // function, but not an operator
            else if (token instanceof Function)
                operatorStack.push(token);
            else if (token.equals(Token.ARGUMENT_SEPARATOR)) {
                while (operatorStack.size() != 0 && !operatorStack.peek().equals(Token.LEFT_PARENTHESIS))
                    output.add(operatorStack.pop());
                // no left parenthesis found!
                if (operatorStack.size() == 0) {
                    EditorController.getInstance().consoleFatal("Mismatched parentheses - no opening parenthesis found!", environment.getCurrentLineNumber());
                    return null;
                }
            }
            else if (token.equals(Token.LEFT_PARENTHESIS))
                operatorStack.push(token);
            else if (token.equals(Token.RIGHT_PARENTHESIS)) {
                while (operatorStack.size() != 0 && !operatorStack.peek().equals(Token.LEFT_PARENTHESIS))
                    output.add(operatorStack.pop());
                // no left parenthesis found!
                if (operatorStack.size() == 0) {
                    EditorController.getInstance().consoleFatal("Mismatched parentheses - no opening parenthesis found!", environment.getCurrentLineNumber());
                    return null;
                }

                // pop left parenthesis off, but not onto output
                operatorStack.pop();

                // function, not operator
                if (operatorStack.size() != 0 && operatorStack.peek() instanceof Function && !(operatorStack.peek() instanceof Operator))
                    output.add(operatorStack.pop());
            }
        }

        if (operatorStack.size() != 0
                && (operatorStack.peek().equals(Token.LEFT_PARENTHESIS) || operatorStack.peek().equals(Token.RIGHT_PARENTHESIS))) {
            EditorController.getInstance().consoleFatal("Mismatched parentheses - no opening parenthesis found!", environment.getCurrentLineNumber());
            return null;
        }
        while (operatorStack.size() != 0)
            output.add(operatorStack.pop());
        return output;
    }

    public static ValueToken evaluatePostfix(Queue<Token> tokens, Environment environment) {
        if (tokens == null)
            return null;
        Stack<ValueToken> values = new Stack<>();
        for (Token token : tokens) {
            if (token instanceof ValueToken)
                values.push((ValueToken) token);
            // either function or operator
            if (token instanceof Function) {
                Function function = (Function) token;
                int args = function.getArguments();
                if (values.size() < args) {
                    EditorController.getInstance().consoleFatal("Not enough arguments for function " + function.getToken() + "!", environment.getCurrentLineNumber());
                    return null;
                }

                ValueToken[] arguments = new ValueToken[args];
                // reverse arguments - so the first argument given in the expression is actually the first argument in evaluate
                for (int i = 0; i < args; i++)
                    arguments[args - 1 - i] = values.pop();
                // evaluate function with arguments
                // if return result, add that to the values stack
                ValueToken result = function.evaluate(environment, arguments);
                if (result != null)
                    values.add(result);
            }
        }

        if (values.size() == 1)
            return values.peek();
        else if (values.size() > 1) {
            EditorController.getInstance().consoleFatal("Too many values given!", environment.getCurrentLineNumber());
            return null;
        }
        return ValueToken.NO_RETURN_VALUE;
    }
}
