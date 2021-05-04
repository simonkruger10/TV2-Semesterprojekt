package com.company.gui;

import com.company.common.AccessLevel;
import com.company.common.Tools;
import com.company.gui.parts.ImageRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.isEven;

public class ProducersOverviewController extends VBox implements UpdateHandler {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    private CallbackHandler callback;

    public ProducersOverviewController(CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/Overview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // TODO Replace with producers from data layer
        showList(new String[]{"Steven Spielberg", "Quentin Tarantino", "Bob Johnson", "Sum Guy"});
    }


    public void showList(String[] producers) {
        // Start the count from the number of children
        int i = main.getChildren().size();

        for (String producer : producers) {
            ImageRowController cRow = new ImageRowController(Type.PRODUCER, "1", callback);

            Image image = null;
            //Image image = producer.getImage();
            if ( image != null) {
                cRow.setImage(image);
            } else if (isEven(i)) {
                cRow.setImage(getResourceAsImage("/images/TV_2_RGB.png"));
            } else {
                cRow.setImage(getResourceAsImage("/images/TV_2_Hvid_RGB.png"));
            }

            cRow.setText(producer);
            //cRow.setText(producer.getName());

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
        return accessLevel.equals(AccessLevel.ADMINISTRATOR);
    }

    @Override
    public void update() {

    }

    @FXML
    private void initialize() {
        assert sortByBtn != null : "fx:id=\"sortByBtn\" was not injected: check your FXML file 'Overview.fxml'.";

    }
}
