package com.dgrissom.imagescript.language;

import com.dgrissom.imagescript.language.tokens.functions.Function;
import com.dgrissom.imagescript.language.tokens.operators.Operator;
import com.dgrissom.imagescript.language.tokens.types.Type;
import com.dgrissom.imagescript.utils.AlertBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Environment {
    private List<Type> types;
    private List<Operator> operators;
    private List<Function> functions;
    private BufferedImage image;
    private File imageDestination;
    private int currentLineNumber;

    public Environment() {
        this.types = new ArrayList<>(Type.getDefaultTypes());
        this.operators = new ArrayList<>(Operator.getDefaultOperators());
        this.functions = new ArrayList<>(Function.getDefaultFunctions());
        this.image = new BufferedImage(600, 400, BufferedImage.TYPE_INT_ARGB);
        this.imageDestination = new File("is.png");
        this.currentLineNumber = 0;
    }

    public List<Type> getTypes() {
        return this.types;
    }
    public List<Operator> getOperators() {
        return this.operators;
    }
    public List<Function> getFunctions() {
        return this.functions;
    }
    public BufferedImage getImage() {
        return this.image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public File getImageDestination() {
        return this.imageDestination;
    }
    public void setImageDestination(File imageDestination) {
        this.imageDestination = imageDestination;
    }
    public int getCurrentLineNumber() {
        return this.currentLineNumber;
    }
    public void setCurrentLineNumber(int currentLineNumber) {
        this.currentLineNumber = currentLineNumber;
    }

    public boolean saveImage() {
        try {
            ImageIO.write(this.image, this.imageDestination.getName().split("\\.")[1], this.imageDestination);
            return true;
        } catch (IOException e) {
            new AlertBuilder()
                    .error()
                    .title("File Save Error")
                    .header(null)
                    .content("There was an error while trying to save the image to " + this.imageDestination.getName() + "!")
                    .show();
            e.printStackTrace();
            return false;
        }
    }
}
