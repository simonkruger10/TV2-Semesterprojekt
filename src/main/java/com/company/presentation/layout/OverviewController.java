package com.company.presentation.layout;

import com.company.common.*;
import com.company.domain.AccountManagement;
import com.company.domain.IAccountManagement;
import com.company.presentation.*;
import com.company.presentation.layout.parts.ImageRowController;
import com.company.presentation.layout.parts.TextRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
                }

                if (!isEven(i)) {
                    cRow.setBackground(Colors.ODD_COLOR);
                }

                main.getChildren().add(i, cRow);
                i++;
            }
        }  else if (type == Type.CREDIT) {
            for (IDTO dto : dtos) {
                ICredit credit = (ICredit) dto.getDTO();
                if (credit.getType().equals(CreditType.PERSON)) {
                    ImageRowController iRow = new ImageRowController(type, dto, callback);
                    iRow.setText(credit.getFullName());
                    iRow.setImage(getResourceAsImage("/images/" + credit.getImage()));
                    if (!isEven(i)) {
                        iRow.setBackground(Colors.ODD_COLOR);
                    }

                    main.getChildren().add(i, iRow);
                } else {
                    TextRowController cRow = new TextRowController(type, dto, callback);
                    cRow.setText(credit.getFullName());
                    if (!isEven(i)) {
                        cRow.setBackground(Colors.ODD_COLOR);
                    }

                    main.getChildren().add(i, cRow);
                }

                i++;
            }
        } else {
            String text = null;
            String image = null;

            for (IDTO dto : dtos) {
                if (type == Type.PRODUCER) {
                    IProducer producer = (IProducer) dto.getDTO();
                    text = producer.getName();
                    image = producer.getLogo();
                } else {
                    IProduction production = (IProduction) dto.getDTO();
                    text = production.getName();
                    image = production.getImage();
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

        update();
    }

    @FXML
    public void onPrv(MouseEvent event) {

    }

    @FXML
    public void onNext(MouseEvent event) {

    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        if (type == Type.ACCOUNT) {
            return accessLevel.equals(AccessLevel.ADMINISTRATOR);
        }
        return true;
    }

    @Override
    public void update() {
        AccessLevel accessLevel = aMgt.getCurrentUser().getAccessLevel();
        boolean state = accessLevel.equals(AccessLevel.ADMINISTRATOR);
        for (Node node: main.getChildren()) {
            if (node instanceof ImageRowController) {
                ((ImageRowController) node).showEdit(state);
            } else if (node instanceof TextRowController) {
                ((TextRowController) node).showEdit(state);
            }
        }
    }
}
