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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.company.common.Tools.isNullOrEmpty;

public class EditAccountController extends VBox implements UpdateHandler {
    @SuppressWarnings("unused")
    @FXML
    private Label title;

    @SuppressWarnings("unused")
    @FXML
    private TextField firstNameText;

    @SuppressWarnings("unused")
    @FXML
    private TextField lastNameText;

    @SuppressWarnings("unused")
    @FXML
    private TextField emailText;

    @SuppressWarnings("unused")
    @FXML
    private PasswordField passwordText1;

    @SuppressWarnings("unused")
    @FXML
    private PasswordField passwordText2;

    @SuppressWarnings("unused")
    @FXML
    private Button addBtn;

    private final CallbackHandler callback;
    private IAccount account = null;

    public EditAccountController(CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/EditAccount.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAccount(IAccount account) {
        this.account = account;

        firstNameText.setText(account.getFirstName());
        lastNameText.setText(account.getLastName());
        emailText.setText(account.getEmail());

        title.setText("Edit Account");
        passwordText1.setPromptText("New Password");
        passwordText2.setPromptText("New Password Again");
    }

    @SuppressWarnings("unused")
    @FXML
    public void add(MouseEvent event) {
        Account account;
        if (this.account == null) {
            account = new Account();
        } else {
            account = new Account(this.account);
        }

        account.setFirstName(firstNameText.getText());
        account.setLastName(lastNameText.getText());
        account.setEmail(emailText.getText());
        account.setAccessLevel(AccessLevel.ADMINISTRATOR);

        String newPassword = passwordText1.getText();
        if (isNullOrEmpty(newPassword) && this.account == null) {
            callback.show(Alert.AlertType.WARNING, "Password is required!");
            return;
        }

        if (!isNullOrEmpty(newPassword)) {
            if (isNullOrEmpty(newPassword)) {
                callback.show(Alert.AlertType.WARNING, "You must fill in both password fields!");
                return;
            }
            if (!newPassword.equals(passwordText2.getText())) {
                callback.show(Alert.AlertType.WARNING, "The two passwords do not match!");
                return;
            }
        }

        try {
            if (this.account == null) {
                IAccount newAccount = new AccountManagement().create(account, newPassword);
                callback.show(Type.ACCOUNT, () -> newAccount);
            } else {
                if (isNullOrEmpty(newPassword)) {
                    new AccountManagement().update(account);
                } else {
                    new AccountManagement().update(account, newPassword);
                }
                callback.show(Type.ACCOUNT, () -> account);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            callback.show(Alert.AlertType.WARNING, e.getMessage());
        }
    }

    @SuppressWarnings("unused")
    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return accessLevel.greater(AccessLevel.CONSUMER);
    }

    @SuppressWarnings("unused")
    @Override
    public void update() {

    }
}
