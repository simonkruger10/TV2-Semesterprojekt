package com.company.gui;

import java.io.IOException;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.common.IProduction;
import com.company.domain.ProductionManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProductViewController extends VBox {


    @FXML
    private Text title;

    @FXML
    private Button addCreditsBtn;

    @FXML
    private Button editCreditsBtn;

    @FXML
    private ImageView image;

    @FXML
    private Text producerID;

    @FXML
    private VBox rows;


    public ProductViewController(String UUID) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProductView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadProduction(UUID);
    }

    // Den virker ikke når det rulles ud
    public void loadProduction(String UUID) {
        IProduction production = new ProductionManagement().getByUUID(UUID);
        title.setText(production.getName());
        image.setImage(new Image(String.valueOf(production.getImage())));

        int i = 0;
        ICreditGroup creditGroup = null;
        for (ICredit credit : production.getCredits()) {
            if (creditGroup == null || credit.getCreditGroup().getName() != creditGroup.getName()) {
                rows.getChildren().set(i, new HeaderRowController(credit.getCreditGroup().getName()));
                creditGroup = credit.getCreditGroup();
                i++;
            }
            rows.getChildren().set(i, new TextRowController(credit.getFullName()));
            i++;
        }
    }

    /*
    @FXML
    void addCredits(MouseEvent event) {

    }

    @FXML
    void editCredits(MouseEvent event) {

    }
    *\

     */


    @FXML
    void initialize() {
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'ProductView.fxml'.";
        assert addCreditsBtn != null : "fx:id=\"addCreditsBtn\" was not injected: check your FXML file 'ProductView.fxml'.";
        assert editCreditsBtn != null : "fx:id=\"editCreditsBtn\" was not injected: check your FXML file 'ProductView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'ProductView.fxml'.";
        assert producerID != null : "fx:id=\"producerID\" was not injected: check your FXML file 'ProductView.fxml'.";
        assert rows != null : "fx:id=\"roles\" was not injected: check your FXML file 'ProductView.fxml'.";

    }
}
