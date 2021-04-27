package com.company.gui;

import java.io.IOException;

import com.company.common.Colors;
import com.company.common.IProduction;
import com.company.domain.IProductionManagement;
import com.company.domain.ProductionManagement;
import com.company.gui.parts.ImageRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.isEven;

public class ProductionsOverviewController extends VBox {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    private CallbackHandler handler;

    private IProductionManagement productionManagement = new ProductionManagement();

    ProductionsOverviewController(CallbackHandler handler) {
        this.handler = handler;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Layouts/ProductionsOverview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showList(productionManagement.list());
    }

    void showList(IProduction[] productions) {
        int i = main.getChildren().size();

        for (IProduction production : productions) {
            ImageRowController cRow = new ImageRowController(production.getUUID(), new CallbackHandler() {
                @Override
                public void show(String uuid) {
                    handler.show(uuid);
                }
            });

            Image image = production.getImage();
            if ( image != null) {
                cRow.setImage(image);
            } else if (isEven(i)) {
                cRow.setImage(getResourceAsImage("/images/TV_2_RGB.png"));
            } else {
                cRow.setImage(getResourceAsImage("/images/TV_2_Hvid_RGB.png"));
            }

            cRow.setText(production.getName());

            if (!isEven(i)) {
                cRow.setBackground(Colors.ODD_COLOR);
            }

            if (main.getChildren().size() == 0) {
                cRow.setTopMargin(0);
            }

            main.getChildren().add(i, cRow);
            i++;
        }
    }

    @FXML
    void goToCreditOverview(MouseEvent event) {
        System.out.println("test");
    }

    @FXML
    void initialize() {
        assert sortByBtn != null : "fx:id=\"sortByBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";

    }

}
