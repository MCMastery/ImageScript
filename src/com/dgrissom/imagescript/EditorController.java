package com.dgrissom.imagescript;

import com.dgrissom.imagescript.menus.FileMenu;
import com.dgrissom.imagescript.menus.ViewMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class EditorController implements Initializable {
    private static EditorController instance;

    public static EditorController getInstance() {
        return instance;
    }

    @FXML
    private TextArea textArea, console;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        this.textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!ImageScript.getInstance().getOpenedFile().isDisplaying())
                ImageScript.getInstance().updateFile(Arrays.asList(newValue.split(Constants.LINE_SEPARATOR)));
        });
    }

    public TextArea getTextArea() {
        return this.textArea;
    }
    public TextArea getConsole() {
        return this.console;
    }

    public void consoleAppend(String s) {
        this.console.appendText(s + Constants.LINE_SEPARATOR);
    }
    public void consoleExecuting() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        consoleAppend("[" + dateFormat.format(new Date()) + "] Executing...");
    }
    public void consoleExecutionSuccessful(File image) {
        consoleAppend("Execution successful! Image saved to " + image.getAbsolutePath() + ".");
    }
    public void consoleFatal(String error, int lineNumber) {
        consoleAppend("[FATAL] Line " + lineNumber + " - " + error);
    }
    public void consoleAbortedExecution() {
        consoleAppend("Aborted execution!");
    }
    public void consoleClear() {
        this.console.clear();
    }

    public void onNew() {
        FileMenu.onNew();
    }
    public void onOpen() {
        FileMenu.onOpen();
    }
    public void onSave() {
        FileMenu.onSave();
    }
    public void onSaveAs() {
        FileMenu.onSaveAs();
    }
    public void onExit() {
        FileMenu.onExit();
    }
    public void onExecute() {
        FileMenu.onExecute();
    }

    public void onShowImageViewer() {
        ViewMenu.onShowImageViewer();
    }
}
