package com.company.gui.layout.parts;

import com.company.common.Tools;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class HeaderRowController extends VBox {
    @FXML
    private Text text;

    public HeaderRowController(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/parts/HeaderRow.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setText(text);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setTopMargin(int top) {
        VBox.setMargin(this, new Insets(top, 0, 0, 0));
    }
}
