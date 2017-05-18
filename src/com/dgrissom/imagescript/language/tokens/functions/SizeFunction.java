package com.dgrissom.imagescript.language.tokens.functions;

import com.dgrissom.imagescript.language.Environment;
import com.dgrissom.imagescript.language.tokens.ValueToken;

import java.awt.image.BufferedImage;

public class SizeFunction extends Function {
    public SizeFunction() {
        super("size", 2, "s");
    }

    @Override
    public ValueToken evaluate(Environment environment, ValueToken... tokens) {
        BufferedImage before = environment.getImage();
        double width = Double.parseDouble(tokens[0].getToken());
        double height = Double.parseDouble(tokens[1].getToken());
        environment.setImage(new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB));

        environment.getImage().getGraphics().drawImage(before, 0, 0, null);
        return null;
    }
}
