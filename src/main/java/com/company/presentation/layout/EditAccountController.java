package com.company.presentation.layout;

import com.company.common.AccessLevel;
import com.company.common.IAccount;
import com.company.common.Tools;
import com.company.domain.AccountManagement;
import com.company.domain.dto.Account;
import com.company.presentation.CallbackHandler;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.company.common.Tools.isNullOrEmpty;

public class EditAccountController extends VBox implements UpdateHandler {
    @FXML
    private TextField firstNameText;

    @FXML
    private TextField lastNameText;

    @FXML
    private TextField emailText;

    @FXML
    private PasswordField passwordText1;

    @FXML
    private PasswordField passwordText2;

    @FXML
    private Button addBtn;

    private final CallbackHandler callback;
    private boolean edit = false;

    public EditAccountController(CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/EditAccountView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAccount(IAccount account) {
        this.edit = true;

        firstNameText.setText(account.getFirstName());
        lastNameText.setText(account.getLastName());
        emailText.setText(account.getEmail());

        passwordText1.setPromptText("New Password");
        passwordText2.setPromptText("New Password Again");
    }

    @FXML
    public void add(MouseEvent event) {
        Account account = new Account();
        account.setFirstName(firstNameText.getText());
        account.setLastName(lastNameText.getText());
        account.setEmail(emailText.getText());

        String newPassword = passwordText1.getText();
        if (isNullOrEmpty(newPassword) && !edit) {
            callback.show(Alert.AlertType.ERROR, "Password is required!");
            return;
        }

        if (!isNullOrEmpty(newPassword) && !newPassword.equals(passwordText2.getText())) {
            callback.show(Alert.AlertType.ERROR, "The two passwords do not match!");
            return;
        }

        try {
            if (edit) {
                if (isNullOrEmpty(newPassword)) {
                    new AccountManagement().update(account);
                } else {
                    new AccountManagement().update(account, newPassword);
                }
                callback.show(Type.ACCOUNT, () -> account);
            } else {
                IAccount newAccount = new AccountManagement().create(account, newPassword);
                callback.show(Type.ACCOUNT, () -> newAccount);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return accessLevel.greater(AccessLevel.CONSUMER);
    }

    @Override
    public void update() {

    }
}
