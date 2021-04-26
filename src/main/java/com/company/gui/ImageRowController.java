package com.company.gui;

import com.company.common.IProduction;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImageRowController extends HBox {
    @FXML
    private ImageView image;

    @FXML
    private Text text;

    private ImageRowHandler imageRowHandler;
    private String uuid;

    ImageRowController(String uuid, ImageRowHandler imageRowHandler) {
        this.uuid = uuid;
        this.imageRowHandler = imageRowHandler;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ImageRow.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setImage(Image image) {
        this.image.setImage(image);
    }

    void setText(String text) {
        this.text.setText(text);
    }

    @FXML
    public void goToCreditOverview(MouseEvent event) {
        imageRowHandler.showCreditOverview(uuid);
    }
}
