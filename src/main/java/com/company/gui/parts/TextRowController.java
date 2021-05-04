package com.company.gui.parts;

import com.company.common.Tools;
import com.company.gui.CallbackHandler;
import com.company.gui.Type;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class TextRowController extends HBox {
    @FXML
    private Text text;

    private final Type type;
    private final String uuid;
    private final CallbackHandler callback;

    public TextRowController(Type type, String uuid, CallbackHandler callback) {
        this.type = type;
        this.uuid = uuid;
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/parts/TextRow.fxml"));
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

    @FXML
    private void show(MouseEvent event) {
        callback.show(type, uuid);
    }
}


