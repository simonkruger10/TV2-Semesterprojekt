package com.company.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class TextRowController extends VBox {
    @FXML
    private Text text;

    TextRowController(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TextRow.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setText(text);
    }

    void setText(String text) {
        this.text.setText(text);
    }
}


