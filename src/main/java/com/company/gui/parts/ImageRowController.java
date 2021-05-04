package com.company.gui.parts;

import com.company.common.Tools;
import com.company.gui.ContentHandler;
import com.company.gui.Type;
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

    private final Type type;
    private final String uuid;
    private final ContentHandler callBack;

    public ImageRowController(Type type, String uuid, ContentHandler callBack) {
        this.type = type;
        this.uuid = uuid;
        this.callBack = callBack;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/parts/ImageRow.fxml"));
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

    public void setTopMargin(int top) {
        VBox.setMargin(this, new Insets(top, 0, 0, 0));
    }

    @FXML
    private void show(MouseEvent event) {
        callBack.show(type, uuid);
    }
}
