package com.dgrissom.imagescript.language.tokens.functions;

import com.dgrissom.imagescript.language.Environment;
import com.dgrissom.imagescript.language.tokens.ValueToken;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FillFunction extends Function {
    public FillFunction() {
        super("fill", 1, "f");
    }

    @Override
    public ValueToken evaluate(Environment environment, ValueToken... tokens) {
        BufferedImage image = environment.getImage();
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor((Color) tokens[0].parseValue());
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        return null;
    }
}
