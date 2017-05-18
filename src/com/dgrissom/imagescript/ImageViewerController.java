package com.dgrissom.imagescript;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ImageViewerController implements Initializable {
    private static ImageViewerController instance;

    public static ImageViewerController getInstance() {
        return instance;
    }

    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
    }

    public ImageView getImageView() {
        return this.imageView;
    }
}
