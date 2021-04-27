package com.company.gui;

import java.io.IOException;

import com.company.common.*;
import com.company.domain.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HomepageController extends VBox {
    @FXML
    private ImageView homeBtn;

    @FXML
    private Text helloMessage;

    @FXML
    private Text loginBtn;

    @FXML
    private TextField searchBarField;

    @FXML
    private Button productionsBtn;

    @FXML
    private Button creditsBtn;

    @FXML
    private Button producersBtn;

    @FXML
    private Button accountsBtn;

    @FXML
    private Button accountBtn;

    @FXML
    private HBox contentHolder;

    @FXML
    private ScrollPane scrollBar;

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Layouts/Homepage.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initSetup();
    }

    void initSetup() {
        onUserChanges(); // init guest

        contentHolder.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                scrollBar.setPrefHeight((Double) newValue);
            }
        });
    }

    void onUserChanges() {
        AccessLevel accessLevel = aMgt.getCurrentUser().getAccessLevel();

        // Menu
        accountBtn.setVisible(accessLevel.greater(AccessLevel.GUEST));

        boolean state = accessLevel == AccessLevel.ADMINISTRATOR;
        producersBtn.setVisible(state);
        accountsBtn.setVisible(state);

        // Login bar
        if (aMgt.getCurrentUser().getAccessLevel().greater(AccessLevel.GUEST)){
            loginBtn.setText("Log out");
            helloMessage.setText("Hi, " + aMgt.getCurrentUser().getFirstName());
        } else {
            loginBtn.setText("Log in");
            helloMessage.setText("Hi, " + AccessLevel.GUEST);
        }
    }


    @FXML
    void goHome(MouseEvent event) {

    }

    @FXML
    void login(MouseEvent event) {
        if (loginBtn.getText().equals("Log out")) {
            aMgt.logout();
            onUserChanges();
        } else {
            setContent(new LoginController(new LoginHandler() {
                @Override
                public void onSuccessfulLogin() {
                    onUserChanges();
                    setContent(defaultContent);
                }
            }));
        }
    }

    @FXML
    void search(KeyEvent event) {

    }

    @FXML
    void showProducers(MouseEvent event) {

    }

    @FXML
    void showProductions(MouseEvent event) {
        setContent(new ProductionsOverviewController(new CallbackHandler() {
            @Override
            public void show(String uuid) {
                setContent(new ProductViewController(uuid, new CallbackHandler() {
                    @Override
                    public void show(String uuid) {
                        setContent(new CreditViewController(uuid));
                    }
                }));
            }
        }));
    }

    @FXML
    void showCredits(MouseEvent event) {
        setContent(new CreditsOverviewController(new CallbackHandler() {
            @Override
            public void show(String uuid) {
                setContent(new CreditViewController(uuid));
            }
        }));
    }

    @FXML
    void showAccounts(MouseEvent event) {

    }

    @FXML
    void showAccount(MouseEvent event) {

    }

    void setContent(Node node) {
        content.getChildren().set(0, node);
    }

    @FXML
    void initialize() {
        assert homeBtn != null : "fx:id=\"home\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert helloMessage != null : "fx:id=\"helloMessage\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert searchBarField != null : "fx:id=\"searchBarField\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert productionsBtn != null : "fx:id=\"productionsBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert creditsBtn != null : "fx:id=\"creditsBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert producersBtn != null : "fx:id=\"producersBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert accountsBtn != null : "fx:id=\"accountsBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert accountBtn != null : "fx:id=\"accountBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert content != null : "fx:id=\"content\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert defaultContent != null : "fx:id=\"defaultContent\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert latestAddedSlider != null : "fx:id=\"latestAddedSlider\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert latestReviewSlider != null : "fx:id=\"latestReviewSlider\" was not injected: check your FXML file 'Homepage.fxml'.";
    }
}
