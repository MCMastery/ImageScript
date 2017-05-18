package com.dgrissom.imagescript;

import com.dgrissom.imagescript.menus.FileMenu;
import com.dgrissom.imagescript.utils.MiscUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ImageScript extends Application {
    private static ImageScript instance;

    public static void main(String[] args) {
        launch(args);
    }

    public static ImageScript getInstance() {
        return instance;
    }

    private Stage primaryStage, imageViewer;
    private ImageScriptFile openedFile;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.primaryStage = primaryStage;
        this.openedFile = new ImageScriptFile();

        Platform.setImplicitExit(false);

        Parent root = FXMLLoader.load(getClass().getResource("res/editor.fxml"));
        this.primaryStage.setTitle(Constants.DEFAULT_WINDOW_TITLE);
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.setOnCloseRequest(event -> {
            event.consume();
            if (this.imageViewer != null)
                this.imageViewer.close();
            FileMenu.onExit();
        });
        this.primaryStage.show();

        MiscUtils.centerStage(this.primaryStage);

        showImageViewer();

        this.openedFile.displayOnScreen();
    }

    public void updateTitle() {
        this.primaryStage.setTitle(Constants.DEFAULT_WINDOW_TITLE + " - " + this.openedFile.getFileName() + (!this.openedFile.isSaved() ? "*" : ""));
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
    public ImageScriptFile getOpenedFile() {
        return this.openedFile;
    }
    public void setOpenedFile(ImageScriptFile file) {
        this.openedFile = file;
        this.openedFile.displayOnScreen();
    }
    public void updateFile(List<String> lines) {
        this.openedFile.setLines(lines);
        updateTitle();
    }

    public Stage getImageViewer() {
        return this.imageViewer;
    }

    public void showImageViewer() throws IOException {
        if (this.imageViewer != null)
            return;

        this.imageViewer = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("res/image-viewer.fxml"));
        this.imageViewer.setTitle(Constants.DEFAULT_WINDOW_TITLE + " - Image Viewer");
        this.imageViewer.setScene(new Scene(root));
        this.imageViewer.setAlwaysOnTop(true);
        this.imageViewer.setOnCloseRequest(event -> this.imageViewer = null);
        this.imageViewer.show();
    }
}
