package com.company.gui;

import com.company.common.Colors;
import com.company.common.ICredit;
import com.company.domain.CreditManagement;
import com.company.domain.ICreditManagement;
import com.company.gui.parts.ImageRowController;
import com.company.gui.parts.TextRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.isEven;

public class CreditsOverviewController extends VBox {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    private CallbackHandler handler;

    private ICreditManagement creditManagement = new CreditManagement();

    CreditsOverviewController(CallbackHandler handler) {
        this.handler = handler;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Layouts/CreditsOverview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showList(creditManagement.list());
    }

    void showList(ICredit[] credits) {
        // Start the count from the number of children
        int i = main.getChildren().size();

        for (ICredit credit : credits) {
            TextRowController cRow = new TextRowController(credit.getUUID(), new CallbackHandler() {
                @Override
                public void show(String uuid) {
                    handler.show(uuid);
                }
            });

            cRow.setText(credit.getFullName());

            if (!isEven(i)) {
                cRow.setBackground(Colors.ODD_COLOR);
            }

            main.getChildren().add(i, cRow);
            i++;
        }
    }

    @FXML
    void initialize() {
        assert sortByBtn != null : "fx:id=\"sortByBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
    }

}
