package com.dgrissom.imagescript.language;

import com.dgrissom.imagescript.EditorController;
import com.dgrissom.imagescript.ImageScript;
import com.dgrissom.imagescript.ImageViewerController;
import com.dgrissom.imagescript.utils.AlertBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class Execution {
    private Execution() {}

    public static boolean execute(String code, Environment environment) {
        return ShuntingYard.evaluate(code, environment) != null;
    }
    public static void execute(List<String> lines, Environment environment) {
        String code = "";
        for (String line : lines)
            code += line;

        int lineNumber = 1;
        for (String line : lines) {
            environment.setCurrentLineNumber(lineNumber);
            if (!execute(line, environment)) {
                EditorController.getInstance().consoleAbortedExecution();
                return;
            }
            lineNumber++;
        }
        if (environment.saveImage()) {
            EditorController.getInstance().consoleExecutionSuccessful(environment.getImageDestination());
            if (ImageScript.getInstance().getImageViewer() != null) {
                try {
                    Image image = new Image(new FileInputStream(environment.getImageDestination()));
                    ImageViewerController.getInstance().getImageView().setImage(image);

                    ImageView imageView = ImageViewerController.getInstance().getImageView();
                    imageView.setFitWidth(image.getWidth());
                    imageView.setFitHeight(image.getHeight());
                } catch (FileNotFoundException e) {
                    new AlertBuilder()
                            .error()
                            .title("Image Read Error")
                            .header(null)
                            .content("The image viewer was unable to load the saved image!")
                            .show();
                    e.printStackTrace();
                }
            }
        }
    }
}
