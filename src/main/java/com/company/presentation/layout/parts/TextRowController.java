package com.company.presentation.layout.parts;

import com.company.common.Tools;
import com.company.presentation.CallbackHandler;
import com.company.presentation.IDTO;
import com.company.presentation.Type;
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
    private final IDTO dto;
    private final CallbackHandler callback;

    public TextRowController(Type type, IDTO dto, CallbackHandler callback) {
        this.type = type;
        this.dto = dto;
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
        callback.show(type, dto);
    }
}


