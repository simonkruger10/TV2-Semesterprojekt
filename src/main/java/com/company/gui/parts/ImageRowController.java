package com.company.gui.parts;

import com.company.gui.CallbackHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ImageRowController extends VBox {
    @FXML
    private HBox holder;

    @FXML
    private ImageView image;

    @FXML
    private Text text;

    private CallbackHandler handler;
    private String uuid;

    public ImageRowController(String uuid, CallbackHandler handler) {
        this.uuid = uuid;
        this.handler = handler;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Layouts/parts/ImageRow.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setImage(Image image) {
        this.image.setImage(image);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setBackground(String hex) {
        holder.setStyle("-fx-background-color: " + hex + ";");
    }

    public void setCallback(CallbackHandler handler) {
        this.handler = handler;
    }

    @FXML
    public void show(MouseEvent event) {
        handler.show(uuid);
    }

    public void setTopMargin(int top) {
        VBox.setMargin(this, new Insets(top, 0, 0, 0));
    }
}
