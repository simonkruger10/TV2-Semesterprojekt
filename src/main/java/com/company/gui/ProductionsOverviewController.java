package com.company.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.company.common.IProduction;
import com.company.domain.IProductionManagement;
import com.company.domain.ProductionManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductionsOverviewController extends VBox {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    private ImageRowHandler handler;

    private IProductionManagement productionManagement = new ProductionManagement();

    ProductionsOverviewController(ImageRowHandler handler) {
        this.handler = handler;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProductionsOverview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showList(productionManagement.list());
    }

    void showList(IProduction[] productions) {
        int i = 1;
        for (IProduction production : productions) {
            ImageRow cRow = new ImageRow(production.getUUID(), new ImageRowHandler() {
                @Override
                public void showCreditOverview(String uuid) {
                    handler.showCreditOverview(uuid);
                }
            });

            ImageView imageView = (ImageView) cRow.getChildren().get(0);
            Text text = (Text) cRow.getChildren().get(1);
            text.setText(production.getName());

            if (i % 2 == 0) {
                cRow.setStyle("-fx-background-color: #FFFFFF;");
                imageView.setImage(new Image("TV_2_RGB.png"));
            } else {
                cRow.setStyle("-fx-background-color: #dcdcdc;");
                imageView.setImage(new Image("TV_2_Hvid_RGB.png"));
            }

            main.getChildren().add(i, cRow);
            i++;
        }
    }

    /*
    void test() {
        for (int i = 0; i < 5; i++) {
            ImageRow r = new ImageRow();

            ImageView iV = (ImageView) r.getChildren().get(0);
            iV.setImage(new Image("TV_2_RGB.png"));
            Text t = (Text) r.getChildren().get(1);
            t.setText("Shrek " + i);

            main.getChildren().add(i+1, r);
        }
    }
     */


    @FXML
    void goToCreditOverview(MouseEvent event) {
        System.out.println("test");
    }

    @FXML
    void initialize() {
        assert sortByBtn != null : "fx:id=\"sortByBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";

    }

}
