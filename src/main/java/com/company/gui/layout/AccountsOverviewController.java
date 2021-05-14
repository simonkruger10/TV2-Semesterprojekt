package com.company.gui.layout;

import com.company.common.AccessLevel;
import com.company.common.IAccount;
import com.company.common.Tools;
import com.company.domain.AccountManagement;
import com.company.gui.CallbackHandler;
import com.company.gui.Colors;
import com.company.gui.Type;
import com.company.gui.UpdateHandler;
import com.company.gui.layout.parts.TextRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static com.company.common.Tools.isEven;

public class AccountsOverviewController extends VBox implements UpdateHandler {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    private final CallbackHandler callback;

    public AccountsOverviewController(CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/Overview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showList(new AccountManagement().list());
    }

    public void showList(IAccount[] accounts) {
        int i = main.getChildren().size();

        for (IAccount account : accounts) {
            TextRowController cRow = new TextRowController(Type.ACCOUNT, account.getUUID(), callback);

            cRow.setText(account.getFullName());

            if (!isEven(i)) {
                cRow.setBackground(Colors.ODD_COLOR);
            }

            main.getChildren().add(i, cRow);
            i++;
        }
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return accessLevel.equals(AccessLevel.ADMINISTRATOR);
    }

    @Override
    public void update() {

    }

    @FXML
    private void initialize() {
        assert main != null : "fx:id=\"main\" was not injected: check your FXML file 'Overview.fxml'.";
        assert sortByBtn != null : "fx:id=\"sortByBtn\" was not injected: check your FXML file 'Overview.fxml'.";
    }
}