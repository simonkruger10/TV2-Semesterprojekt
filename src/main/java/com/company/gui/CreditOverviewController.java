package com.company.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CreditOverviewController extends GuiController {

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
    private Text backToOverviewBtn;

    @FXML
    private Text titleID;

    @FXML
    private Text producerID;

    @FXML
    private Text directorID;

    @FXML
    private Text actorID;

    @FXML
    private Font x3;

    @FXML
    void backToOverview(MouseEvent event) {

    }

    @FXML
    void changePassword(MouseEvent event) {

    }

    @FXML
    void goToHomepage(MouseEvent event) {

    }

    @FXML
    void logOut(MouseEvent event) {

    }

    @FXML
    void searchProduction(KeyEvent event) {

    }

    @FXML
    void initialize() {
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'CreditOverview.fxml'.";
        assert logOutBtn != null : "fx:id=\"logOutBtn\" was not injected: check your FXML file 'CreditOverview.fxml'.";
        assert changePasswordBtn != null : "fx:id=\"changePasswordBtn\" was not injected: check your FXML file 'CreditOverview.fxml'.";
        assert searchBarField != null : "fx:id=\"searchBarField\" was not injected: check your FXML file 'CreditOverview.fxml'.";
        assert backToOverviewBtn != null : "fx:id=\"backToOverviewBtn\" was not injected: check your FXML file 'CreditOverview.fxml'.";
        assert titleID != null : "fx:id=\"titleID\" was not injected: check your FXML file 'CreditOverview.fxml'.";
        assert producerID != null : "fx:id=\"producerID\" was not injected: check your FXML file 'CreditOverview.fxml'.";
        assert directorID != null : "fx:id=\"directorID\" was not injected: check your FXML file 'CreditOverview.fxml'.";
        assert actorID != null : "fx:id=\"actorID\" was not injected: check your FXML file 'CreditOverview.fxml'.";
        assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'CreditOverview.fxml'.";

    }
}
