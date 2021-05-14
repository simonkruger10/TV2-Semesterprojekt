package com.company.gui.layout;

import com.company.common.AccessLevel;
import com.company.common.ICredit;
import com.company.common.Tools;
import com.company.domain.CreditManagement;
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

public class CreditsOverviewController extends VBox implements UpdateHandler {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    private final CallbackHandler callback;

    public CreditsOverviewController(CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/Overview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showList(new CreditManagement().list());
    }

    public void showList(ICredit[] credits) {
        // Start the count from the number of children
        int i = main.getChildren().size();

        for (ICredit credit : credits) {
            TextRowController cRow = new TextRowController(Type.CREDIT, credit.getID(), callback);

            cRow.setText(credit.getFullName());

            if (!isEven(i)) {
                cRow.setBackground(Colors.ODD_COLOR);
            }

            main.getChildren().add(i, cRow);
            i++;
        }
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
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
