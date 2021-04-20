package com.company.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class FrontPageController {

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
    void logOut(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void searchProduction(KeyEvent event) {

    }
}
