package com.company.presentation.layout;

import com.company.common.*;
import com.company.domain.AccountManagement;
import com.company.domain.CreditManagement;
import com.company.presentation.CallbackHandler;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.company.common.Tools.trueVisible;


public class CreditViewController extends VBox implements UpdateHandler {
    @FXML
    private Text firstName;

    @FXML
    private Text groupName;

    @FXML
    private Text lastName;

    @FXML
    private Button editCreditBtn;

    private ICredit credit;
    private final CallbackHandler callback;

    public CreditViewController(Integer id, CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/CreditView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        update();

        viewCredit(id);
    }

    public void viewCredit(Integer id) {
        credit = new CreditManagement().getByID(id, null);
        firstName.setText(credit.getFirstName());
        lastName.setText(credit.getLastName());

        Map<ICreditGroup, List<IProduction>> creditedFor = new CreditManagement().getCreditedFor(credit);
        List<String> lines = new ArrayList<>();
        for (ICreditGroup cg : creditedFor.keySet()) {
            StringBuilder names = new StringBuilder();
            names.append(cg.getName());
            names.append(":");
            names.append("\n\t");

            IProduction[] productions = creditedFor.get(cg).toArray(new IProduction[0]);
            List<String> productionNames = Arrays.stream(productions)
                    .map(iProduction -> iProduction.getName())
                    .collect(Collectors.toList());
            names.append(String.join("\n\t", productionNames));
            lines.add(names.toString());
        }
        groupName.setText(String.join("\n", lines));
    }

    @FXML
    private void editCredit(MouseEvent event) {
        callback.edit(Type.CREDIT, credit.getID());
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {
        AccessLevel accessLevel = new AccountManagement().getCurrentUser().getAccessLevel();
        trueVisible(editCreditBtn, accessLevel.greater(AccessLevel.CONSUMER));
    }

    @FXML
    private void initialize() {
        assert firstName != null : "fx:id=\"firstName\" was not injected: check your FXML file 'CreditView.fxml'.";
        assert groupName != null : "fx:id=\"groupName\" was not injected: check your FXML file 'CreditView.fxml'.";
        assert lastName != null : "fx:id=\"lastName\" was not injected: check your FXML file 'CreditView.fxml'.";
        assert editCreditBtn != null : "fx:id=\"editCreditBtn\" was not injected: check your FXML file 'CreditView.fxml'.";
    }
}