package com.company.gui;

import com.company.common.ICredit;
import com.company.domain.CreditManagement;
import com.company.domain.ICreditManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CreditsOverview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showList(creditManagement.list());
    }

    void showList(ICredit[] credits) {
        int i = 1;
        for (ICredit credit : credits) {
            ImageRowController cRow = new ImageRowController(credit.getUUID(), new CallbackHandler() {
                @Override
                public void show(String uuid) {
                    handler.show(uuid);
                }
            });

            ImageView imageView = (ImageView) cRow.getChildren().get(0);
            Text text = (Text) cRow.getChildren().get(1);
            text.setText(credit.getFullName());

            if (i % 2 == 0) {
                cRow.setStyle("-fx-background-color: #FFFFFF;");
                imageView.setImage(new Image("TV_2_RGB.png"));
            } else {
                cRow.setStyle("-fx-background-color: #dcdcdc;");
                imageView.setImage(new Image("TV_2_Hvid_RGB.png"));
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
