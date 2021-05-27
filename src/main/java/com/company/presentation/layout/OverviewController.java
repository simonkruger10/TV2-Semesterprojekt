package com.company.presentation.layout;

import com.company.common.*;
import com.company.domain.AccountManagement;
import com.company.domain.IAccountManagement;
import com.company.presentation.*;
import com.company.presentation.layout.parts.ImageRowController;
import com.company.presentation.layout.parts.TextRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.isEven;

public class OverviewController extends VBox implements UpdateHandler {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    @FXML
    private Button prvBtn;

    @FXML
    private Button nextBtn;

    private final IAccountManagement aMgt = new AccountManagement();
    private final CallbackHandler callback;
    private Type type;
    private final List<TextRowController> textRowControllers = new ArrayList<>();

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
        this.type = type;

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
                    cRow.setClickable(false);
                    cRow.showEdit(aMgt.getCurrentUser().getAccessLevel().equals(AccessLevel.ADMINISTRATOR));
                    textRowControllers.add(cRow);
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
                    image = "defaultCreditPerson.jpg";
                }
            } else if (type == Type.PRODUCER) {
                IProducer producer = (IProducer) dto.getDTO();

                text = producer.getName();

                image = producer.getLogo();
                if (image == null) {
                    image = "defaultProducer.png";
                }
            } else {
                IProduction production = (IProduction) dto.getDTO();

                text = production.getName();

                image = production.getImage();
                if (image == null) {
                    image = "defaultProduction.png";
                }
            }

            ImageRowController iRow = new ImageRowController(type, dto, callback);
            iRow.setText(text);
            iRow.setImage(getResourceAsImage("/images/" + image));
            if (!isEven(i)) {
                iRow.setBackground(Colors.ODD_COLOR);
            }

            main.getChildren().add(i, iRow);
            i++;
        }
    }

    @FXML
    public void onPrv(MouseEvent event) {

    }

    @FXML
    public void onNext(MouseEvent event) {

    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        if (type == Type.PRODUCER || type == Type.ACCOUNT) {
            return accessLevel.equals(AccessLevel.ADMINISTRATOR);
        }
        return true;
    }

    @Override
    public void update() {
        if (textRowControllers.size() > 0) {
            for (TextRowController textRowController: textRowControllers) {
                textRowController.showEdit(aMgt.getCurrentUser().getAccessLevel().equals(AccessLevel.ADMINISTRATOR));
            }
        }
    }
}
