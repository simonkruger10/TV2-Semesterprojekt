package com.company.gui;

import com.company.common.IAccount;
import com.company.data.AccountEntity;
import com.company.domain.AccountDTO;
import com.company.domain.AccountManagement;
import com.company.domain.IAccountManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class LoginController extends VBox {

    @FXML
    private TextField emailPanel;

    @FXML
    private PasswordField passwordPanel;

    @FXML
    private Button loginBtn;

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

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        // TODO: Tjek om brugernavn og kodeord er tomt.
        //       Tjek brugernavn om det er en mail adrasse

        try {
            aMgt.login(email, password);
            parent.onSucLogin();
        } catch (NoSuchAlgorithmException | RuntimeException e) {
            if (!e.toString().equals("Could not find the user.")) {
                // message.setText("Could not find the user.");
            }
            e.printStackTrace();
            parent.goBack();
        }
    }

    public void getThis() {

    }
}
