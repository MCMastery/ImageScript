package com.dgrissom.imagescript.language.tokens.functions;

import com.dgrissom.imagescript.language.Environment;
import com.dgrissom.imagescript.language.tokens.ValueToken;

import java.awt.*;

public class TextFunction extends Function {
    public TextFunction() {
        super("text", 1, "t");
    }

    @Override
    public ValueToken evaluate(Environment environment, ValueToken... tokens) {
        String text = (String) tokens[0].parseValue();
        Graphics2D g2d = (Graphics2D) environment.getImage().getGraphics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 0, 12);
        return null;
    }
}
