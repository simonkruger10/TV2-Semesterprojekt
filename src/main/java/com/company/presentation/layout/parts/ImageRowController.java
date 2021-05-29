package com.company.presentation.layout.parts;

import com.company.common.Tools;
import com.company.presentation.CallbackHandler;
import com.company.presentation.IDTO;
import com.company.presentation.Type;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.trueVisible;

public class ImageRowController extends VBox {
    @SuppressWarnings("unused")
    @FXML
    private HBox holder;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image;

    @SuppressWarnings("unused")
    @FXML
    private Text text;

    @SuppressWarnings("unused")
    @FXML
    private Button editBtn;

    private final Type type;
    @SuppressWarnings("rawtypes")
    private final IDTO dto;
    private final CallbackHandler callback;

    public ImageRowController(Type type, @SuppressWarnings("rawtypes") IDTO dto, CallbackHandler callback) {
        this.type = type;
        this.dto = dto;
        this.callback = callback;

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

    public void showEdit(boolean state) {
        trueVisible(editBtn, state);
    }

    @SuppressWarnings("unused")
    @FXML
    private void show(MouseEvent event) {
        callback.show(type, dto);
    }

    @SuppressWarnings("unused")
    @FXML
    private void edit(MouseEvent event) {
        callback.edit(type, dto);
    }
}
