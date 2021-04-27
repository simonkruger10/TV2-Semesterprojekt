package com.company.gui;

import java.io.IOException;
import java.util.*;

import com.company.common.Colors;
import com.company.common.ICredit;
import com.company.common.IProduction;
import com.company.domain.ProductionManagement;
import com.company.gui.parts.HeaderRowController;
import com.company.gui.parts.TextRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static com.company.common.Tools.isEven;

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

    public ProductViewController(String UUID, OnShowHandler handler) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Layouts/ProductView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadProduction(UUID, handler);
    }

    public void loadProduction(String UUID, OnShowHandler handler) {
        IProduction production = new ProductionManagement().getByUUID(UUID);
        title.setText(production.getName());
        Image image = production.getImage();
        if (image != null) {
            this.image.setImage(image);
        }

        // Group credits by creditGroup
        HashMap<String, List<ICredit>> grouped = new HashMap<>();
        for (ICredit credit : production.getCredits()) {
            String key = credit.getCreditGroup().getName();
            if (grouped.get(key) == null) {
                grouped.put(key, new ArrayList<>());
            }
            grouped.get(key).add(credit);
        }

        // Roll-out credits
        rows.getChildren().clear();
        for (String groupName : grouped.keySet()) {
            // Header
            HeaderRowController headerRowController = new HeaderRowController(groupName);
            if (rows.getChildren().size() == 0) {
                headerRowController.setTopMargin(0);
            }

            // Rows
            int i = 0;
            rows.getChildren().add(headerRowController);
            for (ICredit credit: grouped.get(groupName)) {
                TextRowController cRow = new TextRowController(credit.getUUID(), new OnShowHandler() {
                    @Override
                    public void show(String uuid) {
                        handler.show(uuid);
                    }
                });
                cRow.setText(credit.getFullName());
                if (!isEven(i)) {
                    cRow.setBackground(Colors.ODD_COLOR);
                }
                rows.getChildren().add(cRow);
                i++;
            }
        }
    }

    @FXML
    void addCredits(MouseEvent event) {

    }

    @FXML
    void editCredits(MouseEvent event) {

    }


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
