package com.company.presentation.layout.parts;

import com.company.common.Tools;
import com.company.presentation.CallbackHandler;
import com.company.presentation.IDTO;
import com.company.presentation.Type;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
    @FXML
    private HBox holder;

    @FXML
    private ImageView image;

    @FXML
    private Text text;

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

    public void setTopMargin(int top) {
        VBox.setMargin(this, new Insets(top, 0, 0, 0));
    }

    public void showEdit(boolean state) {
        trueVisible(editBtn, state);
    }

    @FXML
    private void show(MouseEvent event) {
        callback.show(type, dto);
    }

    @FXML
    private void edit(MouseEvent event) {
        callback.edit(type, dto);
    }
}
