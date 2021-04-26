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
    private Text logOutBtn;

    @FXML
    private Text changePasswordBtn;

    @FXML
    private TextField searchBarField;

    @FXML
    private Button productionBtn;

    @FXML
    private Button producerBtn;

    @FXML
    private Button databaseBtn;

    @FXML
    private Button userBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private VBox recentContentBox;

    @FXML
    private HBox latestCreditsSlider;

    @FXML
    private Font x3;

    private final IAccountManagement aMgt = new AccountManagement();
    private VBox defaultLayout;

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


        logOutBtn.setText("Log in");
    }

    @FXML
    void changePassword(MouseEvent event) {
        //super.gui.setScene("/Temp.fxml");
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @FXML
    void goToHomepage(MouseEvent event) {
        //super.gui.setScene("/Homepage.fxml");
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @FXML
    void logOut(MouseEvent event) {
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        if (logOutBtn.getText().equals("Log out")) {
            aMgt.logout();
            logOutBtn.setText("Log in");
        } else {
            defaultLayout = (VBox) recentContentBox.getChildren().get(0);
            recentContentBox.getChildren().set(0, new LoginController(this));
            logOutBtn.setText("Log out");
        }
    }

    void onSucLogin() {
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        logOutBtn.setText("Log out");
        recentContentBox.getChildren().set(0, defaultLayout);
    }

    void goBack() {
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        recentContentBox.getChildren().set(0, defaultLayout);
    }


    @FXML
    void searchProduction(KeyEvent event) {
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        //super.gui.setScene("/Temp.fxml");

    }

    @FXML
    void showDatabase(MouseEvent event) {
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        //super.gui.setScene("/Temp.fxml");

    }

    @FXML
    void showProducers(MouseEvent event) {
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        //super.gui.setScene("/Temp.fxml");

    }

    @FXML
    void showProductions(MouseEvent event) {
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        //super.gui.setScene("/ProductionsOverview.fxml");

    }

    @FXML
    void showSettings(MouseEvent event) {
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        //super.gui.setScene("/Temp.fxml");

    }

    //ICreditGroupManagement cGMgt = new CreditGroupManagement();
    //IProductionManagement pMgt = new ProductionManagement();
    //ICreditManagement cMgt = new CreditManagement();

    @FXML
    void showUsers(MouseEvent event) {
        /*
        ProductionGTO production = new ProductionGTO();
        production.setName("Test");
        production.setDescription("This is a test");

        CreditGTO credit = new CreditGTO();
        credit.setFirstName("Bob");
        credit.setFirstName("Bib");

        CreditGroupGTO creditGroup = new CreditGroupGTO();
        creditGroup.setName("Actor");
        ICreditGroup cg = cGMgt.create(creditGroup);
        credit.setCreditGroup(cg);

        ICredit newCredit = cMgt.create(credit);

        ICredit[] credits = {newCredit};
        production.setCredits(credits);

        pMgt.create(production);
        //super.gui.setScene("/Temp.fxml");
        */
    }

    @FXML
    void initialize() {
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert logOutBtn != null : "fx:id=\"logOutBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert changePasswordBtn != null : "fx:id=\"changePasswordBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert searchBarField != null : "fx:id=\"searchBarField\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert productionBtn != null : "fx:id=\"productionBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert producerBtn != null : "fx:id=\"producerBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert databaseBtn != null : "fx:id=\"databaseBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert userBtn != null : "fx:id=\"userBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert settingsBtn != null : "fx:id=\"settingsBtn\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert recentContentBox != null : "fx:id=\"recentContentBox\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert latestCreditsSlider != null : "fx:id=\"latestCreditsSlider\" was not injected: check your FXML file 'Homepage.fxml'.";
        assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'Homepage.fxml'.";

    }
}
