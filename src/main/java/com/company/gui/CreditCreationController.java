package com.company.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CreditCreationController {

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
    private TextField firstNameText;

    @FXML
    private TextField middleNameText;

    @FXML
    private TextField lastNameText;

    @FXML
    private TextField creditGroupText;

    @FXML
    private Button addCreditToSystemBtn;

    @FXML
    private Font x3;

    @FXML
    void addCreditToSystem(MouseEvent event) {

        //Made for test purposes, will be deleted l8ter
        System.out.println("Credit added");
    }

    @FXML
    void changePassword(MouseEvent event) {

    }

    @FXML
    void goToHomePage(MouseEvent event) {

    }

    @FXML
    void logOut(MouseEvent event) {

    }

    @FXML
    void searchProduction(KeyEvent event) {

    }

    @FXML
    void initialize() {
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert logOutBtn != null : "fx:id=\"logOutBtn\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert changePasswordBtn != null : "fx:id=\"changePasswordBtn\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert searchBarField != null : "fx:id=\"searchBarField\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert firstNameText != null : "fx:id=\"firstNameText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert middleNameText != null : "fx:id=\"middleNameText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert lastNameText != null : "fx:id=\"lastNameText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert creditGroupText != null : "fx:id=\"creditGroupText\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert addCreditToSystemBtn != null : "fx:id=\"addCreditToSystemBtn\" was not injected: check your FXML file 'CreditCreation.fxml'.";
        assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'CreditCreation.fxml'.";

    }
}
