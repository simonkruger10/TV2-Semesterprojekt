package com.company.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
        private Font x3;

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
            assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
            assert logOutBtn != null : "fx:id=\"logOutBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
            assert changePasswordBtn != null : "fx:id=\"changePasswordBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
            assert searchBarField != null : "fx:id=\"searchBarField\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
            assert sortByBtn != null : "fx:id=\"sortByBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";
            assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";

        }
    }

