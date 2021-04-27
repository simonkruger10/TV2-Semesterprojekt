package com.company.gui.parts;

import com.company.gui.CallbackHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class TextRowController extends HBox {
    @FXML
    private Text text;

    private CallbackHandler handler;
    private String uuid;

    public TextRowController(String uuid, CallbackHandler handler) {
        this.uuid = uuid;
        this.handler = handler;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Layouts/parts/TextRow.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setBackground(String hex) {
        this.setStyle("-fx-background-color: " + hex + ";");
    }

    public void setCallback(CallbackHandler handler) {
        this.handler = handler;
    }

    @FXML
    public void show(MouseEvent event) {
        handler.show(uuid);
    }
}

