package com.dgrissom.imagescript.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertBuilder {
    private Alert alert;

    public AlertBuilder() {
        this.alert = new Alert(Alert.AlertType.NONE);
    }

    public AlertBuilder type(Alert.AlertType type) {
        this.alert.setAlertType(type);
        return this;
    }
    public AlertBuilder none() {
        return type(Alert.AlertType.NONE);
    }
    public AlertBuilder information() {
        return type(Alert.AlertType.INFORMATION);
    }
    public AlertBuilder warning() {
        return type(Alert.AlertType.WARNING);
    }
    public AlertBuilder confirmation() {
        return type(Alert.AlertType.CONFIRMATION);
    }
    public AlertBuilder error() {
        return type(Alert.AlertType.ERROR);
    }

    public AlertBuilder title(String title) {
        this.alert.setTitle(title);
        return this;
    }
    public AlertBuilder header(String header) {
        this.alert.setHeaderText(header);
        return this;
    }
    public AlertBuilder content(String content) {
        this.alert.setHeaderText(content);
        return this;
    }

    public AlertBuilder clearButtons() {
        this.alert.getButtonTypes().clear();
        return this;
    }
    public AlertBuilder button(ButtonType type) {
        this.alert.getButtonTypes().add(type);
        return this;
    }


    public Alert build() {
        return this.alert;
    }
    public Optional<ButtonType> show() {
        return this.alert.showAndWait();
    }
}
