package com.company.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.domain.CreditGroupManagement;
import com.company.domain.CreditManagement;
import com.company.gui.entity.Credit;
import com.company.gui.entity.CreditGroup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CreditCreationController extends VBox {
    @FXML
    private TextField firstNameText;

    @FXML
    private TextField middleNameText;

    @FXML
    private TextField lastNameText;

    @FXML
    private TextField creditGroupText;

    @FXML
    private Button addCreditBtn;

    private OnShowHandler handler;

    CreditCreationController(OnShowHandler handler) {
        this.handler = handler;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Layouts/CreditCreation.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addCredit(MouseEvent event) {
        Credit credit = new Credit();
        credit.setFirstName(firstNameText.getText());
        credit.setMiddleName(middleNameText.getText());
        credit.setLastName(lastNameText.getText());

        CreditGroupManagement cMgt = new CreditGroupManagement();
        String creditGroupName = firstNameText.getText();
        ICreditGroup creditGroup = cMgt.getByName(creditGroupName);
        if (creditGroup == null) {
            credit.setCreditGroup(new CreditGroup(creditGroupName));
        } else {
            credit.setCreditGroup(new CreditGroup(creditGroup));
        }

        this.handler.show(new CreditManagement().create(credit).getUUID());
    }

    @FXML
    void initialize() {
        assert firstNameText != null : "fx:id=\"firstNameText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert middleNameText != null : "fx:id=\"middleNameText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert lastNameText != null : "fx:id=\"lastNameText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert creditGroupText != null : "fx:id=\"creditGroupText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert addCreditBtn != null : "fx:id=\"addCreditBtn\" was not injected: check your FXML file 'CreditCreation.fxml'.";
    }
}
