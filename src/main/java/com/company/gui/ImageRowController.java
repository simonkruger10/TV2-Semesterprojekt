package com.company.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ImageRowController extends HBox {
    @FXML
    private ImageView image;

    @FXML
    private Text text;

    private CallbackHandler imageRowHandler;
    private String uuid;

    ImageRowController(String uuid, CallbackHandler imageRowHandler) {
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
        imageRowHandler.show(uuid);
    }
}
