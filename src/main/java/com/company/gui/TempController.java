package com.company.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class TempController extends GuiController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView homeBtn;

    @FXML
    private Text logOutBtn;

    @FXML
    private Text changePasswordBtn;

    @FXML
    private TextField searchBarField;

    @FXML
    void changePassword(MouseEvent event) {

    }

    @FXML
    void goToHomepage(MouseEvent event) {
        super.gui.setScene("/Layouts/Homepage.fxml");
    }

    @FXML
    void logOut(MouseEvent event) {
        super.gui.setScene("/Layouts/Login.fxml");

    }

    @FXML
    void searchProduction(KeyEvent event) {

    }

    @FXML
    void initialize() {
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'Temp.fxml'.";
        assert logOutBtn != null : "fx:id=\"logOutBtn\" was not injected: check your FXML file 'Temp.fxml'.";
        assert changePasswordBtn != null : "fx:id=\"changePasswordBtn\" was not injected: check your FXML file 'Temp.fxml'.";
        assert searchBarField != null : "fx:id=\"searchBarField\" was not injected: check your FXML file 'Temp.fxml'.";

    }
}
