package com.company.presentation.layout;

import com.company.common.*;
import com.company.domain.AccountManagement;
import com.company.domain.CreditManagement;
import com.company.presentation.CallbackHandler;
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

    public CreditViewController(ICredit credit, CallbackHandler callback) {
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

        viewCredit(credit);
    }

    public void viewCredit(ICredit credit) {
        this.credit = credit;

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
        callback.edit(Type.CREDIT, new IDTO<ICredit>() {
            @Override
            public ICredit getDTO() {
                return credit;
            }
        });
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
}