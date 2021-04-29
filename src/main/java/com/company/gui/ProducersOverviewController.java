package com.company.gui;

import com.company.common.Colors;
import com.company.gui.parts.ImageRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.isEven;

public class ProducersOverviewController extends VBox {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    private OnShowHandler handler;


    ProducersOverviewController(OnShowHandler handler) {
        this.handler = handler;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Layouts/ProducersOverview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //TODO Replace with producers from data layer
        showList(new String[]{"Steven Spielberg", "Quentin Tarantino", "Bob Johnson", "Sum Guy"});
    }


    void showList(String[] producers) {
        // Start the count from the number of children
        int i = main.getChildren().size();

        for (String s : producers) {
            ImageRowController cRow = new ImageRowController("1", new OnShowHandler() {
                @Override
                public void show(String uuid) {
                    handler.show(s);
                }
            });
            ImageView imageView = (ImageView) cRow.getChildren().get(0);
            Text text = (Text) cRow.getChildren().get(1);
            text.setText(s);

            if (isEven(i)) {
                imageView.setImage(getResourceAsImage("/images/TV_2_RGB.png"));
            } else {
                cRow.setBackground(Colors.ODD_COLOR);
                imageView.setImage(getResourceAsImage("/images/TV_2_Hvid_RGB.png"));
            }

            main.getChildren().add(i, cRow);
            i++;
        }
    }

    @FXML
    void initialize() {
        assert sortByBtn != null : "fx:id=\"sortByBtn\" was not injected: check your FXML file 'ProductionsOverview.fxml'.";

    }

}
