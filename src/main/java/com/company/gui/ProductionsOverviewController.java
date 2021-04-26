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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductionsOverviewController extends GuiController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView homeBtn;

    @FXML
    private Text logOutBtn;

    @FXML
    private Text changePasswordBtn;

    @FXML
    private TextField searchBarField;

    @FXML
    private ComboBox<?> sortByBtn;

    @FXML
    private Text goToOverviewBtn;

    @FXML
    private Font x3;

    @FXML
    private HBox row;

    ProductionsOverviewController(IProduction[] productions) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Homepage.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (IProduction production: productions) {
            production.getName();
            row.getChildren().get(0).
            row.setStyle("-fx-background-color: #FFFFFF;");
            row.setStyle("-fx-background-color: #EBE9E9;");
        }
    }

    @FXML
    void changePassword(MouseEvent event) {

    }

    @FXML
    void goToCreditOverview(MouseEvent event) {
        super.gui.setScene("/CreditOverview.fxml");
    }

    @FXML
    void goToHomepage(MouseEvent event) {
        super.gui.setScene("/Homepage.fxml");
    }

    @FXML
    void logOut(MouseEvent event) {
        super.gui.setScene("/Login.fxml");

    }

    @FXML
    void searchProduction(KeyEvent event) {
        super.gui.setScene("/Temp.fxml");
    }

    @FXML
    void initialize() {
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
        assert logOutBtn != null : "fx:id=\"logOutBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
        assert changePasswordBtn != null : "fx:id=\"changePasswordBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
        assert searchBarField != null : "fx:id=\"searchBarField\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
        assert sortByBtn != null : "fx:id=\"sortByBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
        assert goToOverviewBtn != null : "fx:id=\"goToOverviewBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
        assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";

    }
}
