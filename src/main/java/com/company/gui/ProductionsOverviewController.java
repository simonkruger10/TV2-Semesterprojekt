package com.company.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.company.common.IProduction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductionsOverviewController extends VBox {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> sortByBtn;

    @FXML
    private HBox row;

    //ProductionsOverviewController(IProduction[] productions)
    ProductionsOverviewController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProductionsOverview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
        for (IProduction production: productions) {
            production.getName();
            row.getChildren().get(0).
            row.setStyle("-fx-background-color: #FFFFFF;");
            row.setStyle("-fx-background-color: #EBE9E9;");
        }

         */
    }


    @FXML
    void goToCreditOverview(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert sortByBtn != null : "fx:id=\"sortByBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
        assert row != null : "fx:id=\"row\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";

    }
}
