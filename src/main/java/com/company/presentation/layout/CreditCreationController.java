package com.company.presentation.layout;

import com.company.common.AccessLevel;
import com.company.common.ICreditGroup;
import com.company.common.Tools;
import com.company.domain.CreditGroupManagement;
import com.company.domain.CreditManagement;
import com.company.domain.dto.Credit;
import com.company.domain.dto.CreditGroup;
import com.company.presentation.CallbackHandler;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CreditCreationController extends VBox implements UpdateHandler {
    @FXML
    private TextField firstNameText;

    @FXML
    private TextField lastNameText;

    @FXML
    private TextField creditGroupText;

    @FXML
    private TextField productionText;

    @FXML
    private Button addCreditBtn;

    private final CallbackHandler callback;

    public CreditCreationController(CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/CreditCreation.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void addCredit(MouseEvent event) {
        Credit credit = new Credit();
        credit.setFirstName(firstNameText.getText());
        credit.setLastName(lastNameText.getText());

        CreditGroupManagement cMgt = new CreditGroupManagement();
        String creditGroupName = creditGroupText.getText();

        ICreditGroup creditGroup = cMgt.getByName(creditGroupName);
        if (creditGroup == null) {
            creditGroup = new CreditGroupManagement().create(new CreditGroup(creditGroupName));
        }
        credit.addCreditGroup(new CreditGroup(creditGroup));

        callback.show(Type.CREDIT, new CreditManagement().create(credit).getID());
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return accessLevel.greater(AccessLevel.CONSUMER);
    }

    @Override
    public void update() {

    }

    @FXML
    private void initialize() {
        assert firstNameText != null : "fx:id=\"firstNameText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert lastNameText != null : "fx:id=\"lastNameText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert creditGroupText != null : "fx:id=\"creditGroupText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert addCreditBtn != null : "fx:id=\"addCreditBtn\" was not injected: check your FXML file 'CreditCreation.fxml'.";
    }
}
