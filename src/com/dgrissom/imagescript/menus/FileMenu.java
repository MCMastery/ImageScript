package com.dgrissom.imagescript.menus;

import com.dgrissom.imagescript.Constants;
import com.dgrissom.imagescript.ImageScriptFile;
import com.dgrissom.imagescript.ImageScript;
import com.dgrissom.imagescript.utils.AlertBuilder;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileMenu {
    private FileMenu() {}

    private static void showFileSaveErrorAlert() {
        new AlertBuilder()
                .error()
                .title("File Save Error")
                .header(null)
                .content("There was an error while trying to save the file!")
                .show();
    }

    // returns if the file was saved
    private static boolean showSaveDialog() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(Constants.NAME + " - Save File");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(Constants.NAME + " Files", "*.is"));

        File file = chooser.showSaveDialog(ImageScript.getInstance().getPrimaryStage());
        if (file == null) {
            // user cancelled file opening
            return false;
        }

        try {
            ImageScriptFile document = ImageScript.getInstance().getOpenedFile();
            document.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            showFileSaveErrorAlert();
            return false;
        }
    }
    // used for example when exiting
    // returns whether the editor should be closed
    // (returns if the operation should be continued, an operation that would lose changes)
    private static boolean checkIfSaved() {
        ImageScriptFile opened = ImageScript.getInstance().getOpenedFile();
        if (opened.isSaved())
            return true;
        else {
            Optional<ButtonType> result = new AlertBuilder()
                    .confirmation()
                    .title("Unsaved Changes")
                    .header(null)
                    .content("Do you want to save changes to " + opened.getFileName() + "?")
                    .clearButtons()
                    .button(ButtonType.YES)
                    .button(ButtonType.NO)
                    .button(ButtonType.CANCEL)
                    .show();
            // xed out of dialog
            if (!result.isPresent())
                return false;
            else if (result.get() == ButtonType.YES)
                return onSave();
            else if (result.get() == ButtonType.NO)
                return true;
            else
                return false;
        }
    }


    public static void onNew() {
        if (!checkIfSaved())
            return;
        ImageScript.getInstance().setOpenedFile(new ImageScriptFile());
    }

    public static void onOpen() {
        if (!checkIfSaved())
            return;

        FileChooser chooser = new FileChooser();
        chooser.setTitle(Constants.NAME + " - Open File");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(Constants.NAME + " Files", "*.is"));

        File file = chooser.showOpenDialog(ImageScript.getInstance().getPrimaryStage());
        if (file == null) {
            // user cancelled file opening
            return;
        }

        try {
            ImageScript.getInstance().setOpenedFile(ImageScriptFile.read(file));
        } catch (IOException e) {
            e.printStackTrace();

            new AlertBuilder()
                    .error()
                    .title("File Read Error")
                    .header(null)
                    .content("There was an error while trying to read that file!")
                    .show();
        }
    }

    public static void onSaveAs() {
        showSaveDialog();
    }
    public static boolean onSave() {
        ImageScriptFile opened = ImageScript.getInstance().getOpenedFile();
        if (opened.isSaved())
            return true;
        if (opened.getPath() == null)
            return showSaveDialog();
        else {
            File file = opened.getFile();
            try {
                opened.save(file);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                showFileSaveErrorAlert();
                return false;
            }
        }
    }


    public static void onExecute() {
        ImageScript.getInstance().getOpenedFile().execute();
    }


    public static void onExit() {
        if (checkIfSaved()) {
            ImageScript.getInstance().getPrimaryStage().close();
            System.exit(0);
        }
    }
}
