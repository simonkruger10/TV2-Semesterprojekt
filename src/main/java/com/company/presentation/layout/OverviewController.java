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

import static com.company.common.Tools.*;

public class OverviewController extends VBox implements UpdateHandler {
    @FXML
    private VBox main;

    @FXML
    private ComboBox<?> sortByBtn;

    @FXML
    private Button prvBtn;

    @FXML
    private Button slot1;

    @FXML
    private Button slot2;

    @FXML
    private Button slot3;

    @FXML
    private Button slot4;

    @FXML
    private Button slot5;

    @FXML
    private Button slot6;

    @FXML
    private Button slot7;

    @FXML
    private Button nextBtn;

    private final IAccountManagement aMgt = new AccountManagement();
    private final CallbackHandler callback;
    private Type type;
    private Integer currentPage;

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

    public void showList(Type type, @SuppressWarnings("rawtypes") IDTO[] dtos) {
        this.type = type;

        // Start the count from the number of children
        int i = main.getChildren().size();

        if (type == Type.ACCOUNT || type == Type.CREDIT_GROUP) {
            for (@SuppressWarnings("rawtypes") IDTO dto : dtos) {
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
        } else if (type == Type.CREDIT) {
            for (@SuppressWarnings("rawtypes") IDTO dto : dtos) {
                ICredit credit = (ICredit) dto.getDTO();
                if (credit.getType().equals(CreditType.UNIT)) {
                    TextRowController cRow = new TextRowController(type, dto, callback);
                    cRow.setText(credit.getName());
                    if (!isEven(i)) {
                        cRow.setBackground(Colors.ODD_COLOR);
                    }

                    main.getChildren().add(i, cRow);
                } else {
                    ImageRowController iRow = new ImageRowController(type, dto, callback);
                    iRow.setText(credit.getFullName());
                    iRow.setImage(getResourceAsImage("/images/" + credit.getImage()));
                    if (!isEven(i)) {
                        iRow.setBackground(Colors.ODD_COLOR);
                    }

                    main.getChildren().add(i, iRow);
                }

                i++;
            }
        } else {
            String text = null;
            String image = null;

            for (@SuppressWarnings("rawtypes") IDTO dto : dtos) {
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

    //TODO this showNav is a temporary hack made before a deadline
    public void showNav(Integer currentPage, Integer maxPages) {
        if (currentPage < 1 || currentPage > maxPages) {
            currentPage = 1;
        }

        this.currentPage = currentPage;

        int startPage = currentPage;
        if (maxPages - startPage < 3) {
            while (maxPages - startPage < 7 && startPage > 1) {
                startPage--;
            }
        } else {
            while (currentPage - startPage < 3 && startPage > 1) {
                startPage--;
            }
        }

        if (maxPages > 1) {
            trueVisible(prvBtn, currentPage > 1);

            setGotoButton(slot1, startPage, currentPage == startPage);
            setGotoButton(slot2, ++startPage, currentPage == startPage);
            if (maxPages > 3) {
                setGotoButton(slot3, ++startPage, currentPage == startPage);
                if (maxPages > 4) {
                    setGotoButton(slot4, ++startPage, currentPage == startPage);
                    if (maxPages > 5) {
                        setGotoButton(slot5, ++startPage, currentPage == startPage);
                        if (maxPages > 6) {
                            setGotoButton(slot6, ++startPage, currentPage == startPage);
                            if (maxPages > 7) {
                                setGotoButton(slot7, ++startPage, currentPage == startPage);
                            }
                        }
                    }
                }
            }

            trueVisible(nextBtn, currentPage < maxPages);
        }
    }

    //TODO this setGotoButton is a temporary hack made before a deadline
    private void setGotoButton(Button button, int number, boolean bold) {
        trueVisible(button, true);

        button.setText(String.valueOf(number));
        if (bold) {
            button.setStyle("-fx-font-weight: bold");
        }
    }

    @FXML
    public void onPrv(MouseEvent event) {
        callback.list(type, currentPage - 1);
    }

    @FXML
    public void onGoto(MouseEvent event) {
        Button button = (Button) event.getSource();
        Integer pageNumber = Integer.parseInt(button.getText());
        callback.list(type, pageNumber);
    }

    @FXML
    public void onNext(MouseEvent event) {
        callback.list(type, currentPage + 1);
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
        for (Node node : main.getChildren()) {
            if (node instanceof ImageRowController) {
                ((ImageRowController) node).showEdit(state);
            } else if (node instanceof TextRowController) {
                ((TextRowController) node).showEdit(state);
            }
        }
    }
}
