package com.company.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class HeaderRowController extends VBox {
    @FXML
    private Text text;

    HeaderRowController(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HeaderRow.fxml"));
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
