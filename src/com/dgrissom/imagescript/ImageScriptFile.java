package com.dgrissom.imagescript;

import com.dgrissom.imagescript.language.Environment;
import com.dgrissom.imagescript.language.Execution;
import com.dgrissom.imagescript.utils.FileUtils;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageScriptFile {
    private List<String> lines;
    private String path;
    private boolean saved, displaying;

    public ImageScriptFile() {
        this.lines = new ArrayList<>();
        this.path = null;
        this.saved = true;
        this.displaying = false;
    }

    public List<String> getLines() {
        return new ArrayList<>(this.lines);
    }
    public void setLines(List<String> lines) {
        this.lines = lines;
        this.saved = false;
    }

    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getFileName() {
        if (this.path == null)
            return Constants.NEW_FILE_NAME;

        String fileSeparator = File.separator;
        // escape file separator for regex
        if (fileSeparator.equalsIgnoreCase("\\"))
            fileSeparator += fileSeparator;

        String[] pathSplit = this.path.split(fileSeparator);
        return pathSplit[pathSplit.length - 1];
    }
    public File getFile() {
        if (this.path == null)
            return null;
        return new File(this.path);
    }

    public boolean isSaved() {
        return this.saved;
    }
    private void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isDisplaying() {
        return this.displaying;
    }

    public void displayOnScreen() {
        this.displaying = true;

        TextArea textArea = EditorController.getInstance().getTextArea();
        textArea.clear();
        for (String line : this.lines)
            textArea.appendText(line + Constants.LINE_SEPARATOR);
        ImageScript.getInstance().updateTitle();

        this.displaying = false;
    }


    public void execute() {
        EditorController.getInstance().consoleClear();
        EditorController.getInstance().consoleExecuting();

        Environment environment = new Environment();
        Execution.execute(this.lines, environment);
    }


    public void save(File file) throws IOException {
        FileUtils.write(file, this.lines);
        this.path = file.getPath();
        this.saved = true;
        ImageScript.getInstance().updateTitle();
    }
    public static ImageScriptFile read(File file) throws IOException {
        List<String> lines = FileUtils.read(file);
        ImageScriptFile isFile = new ImageScriptFile();
        isFile.setLines(lines);
        isFile.setPath(file.getPath());
        isFile.setSaved(true);
        return isFile;
    }
}
