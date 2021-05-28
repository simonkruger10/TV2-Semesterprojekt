package com.company.presentation.layout.parts;

import com.company.common.Tools;
import com.company.presentation.CallbackHandler;
import com.company.presentation.IDTO;
import com.company.presentation.Type;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.trueVisible;

public class TextRowController extends VBox {
    @FXML
    private HBox holder;

    @FXML
    private Text text;

    @FXML
    private Button editBtn;

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

        setClickable(true);
        showEdit(false);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setBackground(String hex) {
        holder.setStyle("-fx-background-color: " + hex + ";");
    }

    public void setClickable(boolean state) {
        if (state) {
            setCursor(Cursor.HAND);
        } else {
            setCursor(Cursor.DEFAULT);
        }
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


