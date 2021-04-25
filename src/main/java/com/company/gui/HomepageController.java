package com.company.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.common.IProduction;
import com.company.domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HomepageController extends GuiController {

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

    @FXML
    void changePassword(MouseEvent event) {
        super.gui.setScene("/Temp.fxml");

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
    void showDatabase(MouseEvent event) {
        super.gui.setScene("/Temp.fxml");

    }

    @FXML
    void showProducers(MouseEvent event) {
        super.gui.setScene("/Temp.fxml");

    }

    @FXML
    void showProductions(MouseEvent event) {
        super.gui.setScene("/ProductionsOverview.fxml");

    }

    @FXML
    void showSettings(MouseEvent event) {
        super.gui.setScene("/Temp.fxml");

    }

    ICreditGroupManagement cGMgt = new CreditGroupManagement();
    IProductionManagement pMgt = new ProductionManagement();

    @FXML
    void showUsers(MouseEvent event) {
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
        ICredit[] credits = {(ICredit) credit};
        production.setCredits(credits);

        pMgt.create(production);
        super.gui.setScene("/Temp.fxml");
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
