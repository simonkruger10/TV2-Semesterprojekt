package com.company.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.company.common.*;
import com.company.domain.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HomepageController extends VBox {
    @FXML
    private ImageView homeBtn;

    @FXML
    private Text helloMessage;

    @FXML
    private Text loginBtn;

    @FXML
    private Text changePasswordBtn;

    @FXML
    private TextField searchBarField;

    @FXML
    private Button productionsBtn;

    @FXML
    private Button creditsBtn;

    @FXML
    private Button producerBtn;

    @FXML
    private Button accountsBtn;

    @FXML
    private VBox content;

    @FXML
    private VBox defaultContent;

    @FXML
    private HBox latestAddedSlider;

    @FXML
    private HBox latestReviewSlider;

    private final IAccountManagement aMgt = new AccountManagement();

    HomepageController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Homepage.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AccessLevel accessLevel = aMgt.getCurrentUser().getAccessLevel();


        loginBtn.setText("Log in");
        helloMessage.setText("Hi, " + AccessLevel.GUEST);
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
    void login(MouseEvent event) {
        if (loginBtn.getText().equals("Log out")) {
            aMgt.logout();

            loginBtn.setText("Log in");
            helloMessage.setText("Hi, " + AccessLevel.GUEST);
        } else {
            content.getChildren().set(0, new LoginController(new LoginHandler() {
                @Override
                public void onSuccessfulLogin() {
                    loginBtn.setText("Log out");
                    helloMessage.setText("Hi, " + aMgt.getCurrentUser().getFirstName());
                    content.getChildren().set(0, defaultContent);
                }
            }));
        }
    }

    @FXML
    void searchProduction(KeyEvent event) {

    }

    @FXML
    void showProducers(MouseEvent event) {

    }

    @FXML
    void showProductions(MouseEvent event) {
        content.getChildren().set(0, new ProductionsOverviewController(new ImageRowHandler() {
            @Override
            public void showCreditOverview(String uuid) {
                content.getChildren().set(0, new ProductViewController(uuid));
            }
        }));
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @FXML
    void showCredits(MouseEvent event) {

    }

    @FXML
    void showUsers(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert helloMessage != null : "fx:id=\"helloMessage\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert changePasswordBtn != null : "fx:id=\"changePasswordBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert searchBarField != null : "fx:id=\"searchBarField\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert productionsBtn != null : "fx:id=\"productionsBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert creditsBtn != null : "fx:id=\"creditsBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert producerBtn != null : "fx:id=\"producerBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert accountsBtn != null : "fx:id=\"accountsBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert content != null : "fx:id=\"content\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert defaultContent != null : "fx:id=\"defaultContent\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert latestAddedSlider != null : "fx:id=\"latestAddedSlider\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert latestReviewSlider != null : "fx:id=\"latestReviewSlider\" was not injected: check your FXML file 'Homepage.fxml'.";

    }
}
