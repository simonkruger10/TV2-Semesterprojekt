package com.company.presentation.layout;

import com.company.common.AccessLevel;
import com.company.common.IAccount;
import com.company.common.Tools;
import com.company.domain.AccountManagement;
import com.company.presentation.GUI;
import com.company.presentation.IDTO;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.trueVisible;

public class AccountViewController extends VBox implements UpdateHandler {
    @FXML
    private Button editBtn;

    @FXML
    private Text firstName;

    @FXML
    private Text lastName;

    @FXML
    private Text email;

    private final GUI callback;
    private IAccount account;

    public AccountViewController(IAccount account, GUI callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/AccountView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        update();
        loadAccount(account);
    }

    public void loadAccount(IAccount account) {
        this.account = account;

        firstName.setText(account.getFirstName());
        lastName.setText(account.getLastName());
        email.setText(account.getEmail());
    }

    @FXML
    private void edit(MouseEvent event) {
        callback.edit(Type.ACCOUNT, (IDTO<IAccount>) () -> account);
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return accessLevel.equals(AccessLevel.PRODUCER) || accessLevel.equals(AccessLevel.ADMINISTRATOR);
    }

    @Override
    public void update() {
        AccessLevel accessLevel = new AccountManagement().getCurrentUser().getAccessLevel();
        trueVisible(editBtn, accessLevel.greater(AccessLevel.CONSUMER));
    }
}
