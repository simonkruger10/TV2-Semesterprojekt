package com.company.presentation.layout;

import com.company.common.*;
import com.company.presentation.*;
import com.company.presentation.layout.parts.ImageRowController;
import com.company.presentation.layout.parts.TextRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.isEven;

public class OverviewController extends VBox implements UpdateHandler {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    private final CallbackHandler callback;

    public OverviewController(CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/Overview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showList(Type type, IDTO[] dtos) {
        // Start the count from the number of children
        int i = main.getChildren().size();

        if (type == Type.ACCOUNT || type == Type.CREDIT_GROUP) {
            for (IDTO dto : dtos) {
                TextRowController cRow = new TextRowController(type, dto, callback);

                if (type == Type.ACCOUNT) {
                    IAccount account = (IAccount) dto.getDTO();
                    cRow.setText(account.getFullName() + ", " + account.getEmail());
                } else {
                    cRow.setText(((ICreditGroup) dto.getDTO()).getName());
                }

                if (!isEven(i)) {
                    cRow.setBackground(Colors.ODD_COLOR);
                }

                main.getChildren().add(i, cRow);
                i++;
            }

            return;
        }

        String text = null;
        String image = null;

        for (IDTO dto : dtos) {
            if (type == Type.CREDIT) {
                ICredit credit = (ICredit) dto.getDTO();

                text = credit.getFullName();

                image = credit.getImage();
                if (image == null) {
                    if (isEven(i)) {
                        image = "/images/TV_2_RGB.png";
                    } else {
                        image = "/images/TV_2_Hvid_RGB.png";
                    }
                }
            } else if (type == Type.PRODUCER) {
                IProducer producer = (IProducer) dto.getDTO();

                text = producer.getName();

                image = producer.getLogo();
                if (image == null) {
                    if (isEven(i)) {
                        image = "/images/TV_2_RGB.png";
                    } else {
                        image = "/images/TV_2_Hvid_RGB.png";
                    }
                }
            } else if (type == Type.PRODUCTION) {
                IProduction production = (IProduction) dto.getDTO();

                text = production.getName();

                image = production.getImage();
                if (image == null) {
                    if (isEven(i)) {
                        image = "/images/TV_2_RGB.png";
                    } else {
                        image = "/images/TV_2_Hvid_RGB.png";
                    }
                }
            }

            ImageRowController cRow = new ImageRowController(type, dto, callback);
            if (image != null) {
                cRow.setText(text);
            }
            if (image != null) {
                cRow.setImage(getResourceAsImage(image));
            }
            if (!isEven(i)) {
                cRow.setBackground(Colors.ODD_COLOR);
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
