package com.company.gui;

import java.io.IOException;

import com.company.common.AccessLevel;
import com.company.common.ICredit;
import com.company.domain.AccountManagement;
import com.company.domain.CreditManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static com.company.common.Tools.trueVisible;


public class CreditViewController extends VBox {

    @FXML
    private Text firstName;

    @FXML
    private Text middleName;

    @FXML
    private Text groupName;

    @FXML
    private Text lastName;

    @FXML
    private Button editCreditBtn;

    public CreditViewController(String UUID) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Layouts/CreditView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AccessLevel accessLevel = new AccountManagement().getCurrentUser().getAccessLevel();
        trueVisible(editCreditBtn, accessLevel.greater(AccessLevel.CONSUMER));

        viewCredit(UUID);
    }

    public void viewCredit(String UUID) {
        ICredit credit = new CreditManagement().getByUUID(UUID);
        firstName.setText(credit.getFirstName());
        middleName.setText(credit.getMiddleName());
        lastName.setText(credit.getLastName());
        groupName.setText(credit.getCreditGroup().getName());
    }

    @FXML
    void editCredit(MouseEvent event) {
    }

    @FXML
    void initialize() {
        assert firstName != null : "fx:id=\"firstName\" was not injected: check your FXML file 'Untitled'.";
        assert middleName != null : "fx:id=\"middleName\" was not injected: check your FXML file 'Untitled'.";
        assert groupName != null : "fx:id=\"groupName\" was not injected: check your FXML file 'Untitled'.";
        assert lastName != null : "fx:id=\"lastName\" was not injected: check your FXML file 'Untitled'.";
        assert editCreditBtn != null : "fx:id=\"editCreditBtn\" was not injected: check your FXML file 'Untitled'.";
    }
}