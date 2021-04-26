package com.company.gui;

import com.company.common.IAccount;
import com.company.data.AccountEntity;
import com.company.domain.AccountDTO;
import com.company.domain.AccountManagement;
import com.company.domain.IAccountManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static com.company.common.Tools.isEmailValid;
import static com.company.common.Tools.isNullOrEmpty;

public class LoginController extends VBox {

    @FXML
    private TextField emailPanel;

    @FXML
    private PasswordField passwordPanel;

    private final IAccountManagement aMgt = new AccountManagement();
    private final HomepageController parent;

    LoginController(HomepageController parent) {
        this.parent = parent;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Login button pressed
    public void loginAccount(MouseEvent mouseEvent) {
        String email = emailPanel.getText();
        String password = passwordPanel.getText();

        if (isNullOrEmpty(email)) {
            new MessageDialog(AlertType.INFORMATION, "User is required!");
        }

        if (isNullOrEmpty(password)) {
            new MessageDialog(AlertType.INFORMATION, "Password is required!");
        }

        if (isEmailValid(email)) {
            new MessageDialog(AlertType.INFORMATION, "The username must be an email address!");
        }

        try {
            aMgt.login(email, password);
            parent.onSucLogin();
        } catch (RuntimeException e) {
            if (e.toString().equals("Could not find the user")) {
                new MessageDialog(AlertType.INFORMATION, e.toString() + "!");
            } else {
                new MessageDialog(AlertType.ERROR, e.toString());
            }
        }
    }
}
