package com.company.presentation.layout;

import com.company.common.AccessLevel;
import com.company.common.Tools;
import com.company.domain.AccountManagement;
import com.company.presentation.CallbackHandler;
import com.company.presentation.UpdateHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.company.common.Tools.isEmailValid;
import static com.company.common.Tools.isNullOrEmpty;

public class LoginController extends VBox implements UpdateHandler {
    @FXML
    private TextField emailPanel;

    @FXML
    private PasswordField passwordPanel;

    private final CallbackHandler callback;

    public LoginController(CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/Login.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Platform.runLater(() -> emailPanel.requestFocus());
    }

    @FXML
    private void onKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    //Login button pressed
    @FXML
    private void login(MouseEvent mouseEvent) {
        login();
    }

    private void login() {
        String email = emailPanel.getText();
        String password = passwordPanel.getText();

        if (isNullOrEmpty(email)) {
            callback.loginFailed(AlertType.INFORMATION, "E-mail is required!");
            return;
        }

        if (!isEmailValid(email)) {
            callback.loginFailed(AlertType.INFORMATION, "The e-mail is invalid.");
            return;
        }

        if (isNullOrEmpty(password)) {
            callback.loginFailed(AlertType.INFORMATION, "Password is required!");
            return;
        }

        try {
            new AccountManagement().login(email, password);
            callback.loginSuccess();
        } catch (RuntimeException e) {
            callback.loginFailed(AlertType.INFORMATION, e.getMessage() + "!");
        } catch (NoSuchAlgorithmException e) {
            callback.loginFailed(AlertType.ERROR, e.getMessage());
        }
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return accessLevel.equals(AccessLevel.GUEST);
    }

    @Override
    public void update() {

    }
}
