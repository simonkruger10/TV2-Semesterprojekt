package com.company.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.company.common.ICredit;
import com.company.domain.CreditManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;


public class CreditViewController {

    @FXML
    private Text firstName1;

    @FXML
    private Text middleName1;

    @FXML
    private Text groupName1;

    @FXML
    private Text lastName1;

    public CreditViewController(String UUID) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CreditView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        viewCredit(UUID);
    }

    public void viewCredit(String UUID) {
        ICredit credit = new CreditManagement().getByUUID(UUID);
        firstName1.setText(credit.getFirstName());
        middleName1.setText(credit.getMiddleName());
        lastName1.setText(credit.getLastName());
        groupName1.setText(credit.getCreditGroup().getName());
    }

    @FXML
    void initialize() {
        assert firstName1 != null : "fx:id=\"firstName1\" was not injected: check your FXML file 'Untitled'.";
        assert middleName1 != null : "fx:id=\"middleName1\" was not injected: check your FXML file 'Untitled'.";
        assert groupName1 != null : "fx:id=\"groupName1\" was not injected: check your FXML file 'Untitled'.";
        assert lastName1 != null : "fx:id=\"lastName1\" was not injected: check your FXML file 'Untitled'.";

    }
}