package com.company.presentation.layout;

import java.io.IOException;

import com.company.common.*;
import com.company.domain.*;
import com.company.presentation.CallbackHandler;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static com.company.common.Tools.trueVisible;

public class HomepageController extends VBox implements UpdateHandler {
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
    private Button addProductionBtn;

    @FXML
    private Button creditsBtn;

    @FXML
    private Button addCreditBtn;

    @FXML
    private Button producersBtn;

    @FXML
    private Button addProducerBtn;

    @FXML
    private Button accountsBtn;

    @FXML
    private Button addAccountBtn;

    @FXML
    private Button accountBtn;

    @FXML
    private HBox contentHolder;

    @FXML
    private ScrollPane scrollBar;

    @FXML
    private VBox content;

    private final IAccountManagement aMgt = new AccountManagement();
    private final CallbackHandler callBack;

    public HomepageController(CallbackHandler callBack) {
        this.callBack = callBack;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/Homepage.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        contentHolder.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                scrollBar.setPrefHeight((Double) newValue);
            }
        });

        update();
    }


    @FXML
    private void goHome(MouseEvent event) {
        callBack.show(Type.RECENTLY_AND_REVIEW);
    }

    @FXML
    private void onLogin(MouseEvent event) {
        if (aMgt.getCurrentUser().getAccessLevel().equals(AccessLevel.GUEST)) {
            callBack.show(Type.LOGIN);
        } else {
            callBack.logout();
        }
    }

    @FXML
    private void search(KeyEvent event) {
        callBack.list(Type.SEARCH);
    }

    @FXML
    private void showProductions(MouseEvent event) {
        callBack.list(Type.PRODUCTION);
    }

    @FXML
    private void addProduction(MouseEvent event) {
        callBack.add(Type.PRODUCTION);
    }

    @FXML
    private void showProducers(MouseEvent event) {
        callBack.list(Type.PRODUCER);
    }

    @FXML
    private void addProducer(MouseEvent event) {
        callBack.add(Type.PRODUCER);
    }

    @FXML
    private void showCredits(MouseEvent event) {
        callBack.list(Type.CREDIT);
    }

    @FXML
    private void addCredit(MouseEvent event) {
        callBack.add(Type.CREDIT);
    }

    @FXML
    private void showAccounts(MouseEvent event) {
        callBack.list(Type.ACCOUNT);
    }

    @FXML
    private void addAccount(MouseEvent event) {
        callBack.add(Type.ACCOUNT);
    }

    @FXML
    private void showAccount(MouseEvent event) {
        callBack.show(Type.ACCOUNT, aMgt.getCurrentUser().getID());
    }


    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {
        AccessLevel accessLevel = aMgt.getCurrentUser().getAccessLevel();

        // login in the top bar
        if (accessLevel.equals(AccessLevel.GUEST)) {
            loginBtn.setText("Log in");
        } else {
            loginBtn.setText("Log out");
        }
        helloMessage.setText("Hi, " + aMgt.getCurrentUser().getFirstName());

        // Menu
        trueVisible(accountBtn, accessLevel.greater(AccessLevel.GUEST));

        boolean state = accessLevel.greater(AccessLevel.CONSUMER);
        trueVisible(addProductionBtn, state);
        trueVisible(addCreditBtn, state);

        state = accessLevel.equals(AccessLevel.ADMINISTRATOR);
        trueVisible(producersBtn, state);
        trueVisible(addProducerBtn, state);
        trueVisible(accountsBtn, state);
        trueVisible(addAccountBtn, state);
    }


    public void setContent(Node node) {
        if (content.getChildren().size() > 0) {
            content.getChildren().set(0, node);
        } else {
            content.getChildren().add(node);
        }
    }

    @FXML
    private void initialize() {
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
    }
}