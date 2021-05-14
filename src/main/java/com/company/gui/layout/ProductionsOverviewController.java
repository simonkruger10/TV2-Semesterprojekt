package com.company.gui.layout;

import java.io.IOException;

import com.company.common.AccessLevel;
import com.company.common.IProduction;
import com.company.common.Tools;
import com.company.domain.ProductionManagement;
import com.company.gui.CallbackHandler;
import com.company.gui.Colors;
import com.company.gui.Type;
import com.company.gui.UpdateHandler;
import com.company.gui.layout.parts.ImageRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.isEven;

public class ProductionsOverviewController extends VBox implements UpdateHandler {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    private final CallbackHandler callback;

    public ProductionsOverviewController(CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/Overview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showList(new ProductionManagement().list());
    }

    public void showList(IProduction[] productions) {
        int i = main.getChildren().size();

        for (IProduction production : productions) {
            ImageRowController cRow = new ImageRowController(Type.PRODUCTION, production.getUUID(), callback);

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

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {

    }

    @FXML
    private void initialize() {
        assert main != null : "fx:id=\"main\" was not injected: check your FXML file 'Overview.fxml'.";
        assert sortByBtn != null : "fx:id=\"sortByBtn\" was not injected: check your FXML file 'Overview.fxml'.";
    }
}
