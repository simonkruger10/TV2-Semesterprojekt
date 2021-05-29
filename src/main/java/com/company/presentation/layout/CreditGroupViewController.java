package com.company.presentation.layout;

import com.company.common.*;
import com.company.domain.AccountManagement;
import com.company.domain.CreditManagement;
import com.company.presentation.*;
import com.company.presentation.layout.parts.ImageRowController;
import com.company.presentation.layout.parts.TextRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.*;


public class CreditGroupViewController extends VBox implements UpdateHandler {
    @FXML
    private Text creditGroupRole;

    @FXML
    private Button editCreditGroupBtn;

    @FXML
    private VBox credits;

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

    private ICreditGroup creditGroup;
    private final CallbackHandler callback;
    private Integer currentPage;

    public CreditGroupViewController(ICreditGroup creditGroup, CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/CreditGroupView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        update();
        viewCreditGroup(creditGroup);
    }

    public void viewCreditGroup(ICreditGroup creditGroup) {
        this.creditGroup = creditGroup;

        creditGroupRole.setText(creditGroup.getName());

        ICredit[] list = new CreditManagement().getByCreditGroup(creditGroup);

        int i = 0;
        for (ICredit credit : list) {
            // Rows
            if (credit.getType().equals(CreditType.PERSON)) {
                ImageRowController iRow = new ImageRowController(Type.CREDIT, () -> credit, callback);
                iRow.setText(credit.getFullName());
                iRow.setImage(getResourceAsImage("/images/" + credit.getImage()));
                if (!isEven(i)) {
                    iRow.setBackground(Colors.ODD_COLOR);
                }

                credits.getChildren().add(iRow);
            } else {
                TextRowController cRow = new TextRowController(Type.CREDIT, () -> credit, callback);
                cRow.setText(credit.getFullName());
                if (!isEven(i)) {
                    cRow.setBackground(Colors.ODD_COLOR);
                }

                credits.getChildren().add(cRow);
            }

            i++;

            //TODO this break is a temporary hack made before a deadline
            if (i >= 10) {
                showNav(1, calMaxPages(list.length, 10));
                break;
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

    //TODO this calMaxPages is a temporary hack made before a deadline
    private Integer calMaxPages(Integer count, Integer countEachPage) {
        int maxPages = count / countEachPage;
        if (count%countEachPage > 0) {
            return ++maxPages;
        }
        return maxPages;
    }

    @FXML
    public void onPrv(MouseEvent event) {
    }

    @FXML
    public void onGoto(MouseEvent event) {
    }

    @FXML
    public void onNext(MouseEvent event) {
    }

    @FXML
    private void edit(MouseEvent event) {
        callback.edit(Type.CREDIT_GROUP, (IDTO<ICreditGroup>) () -> creditGroup);
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {
        AccessLevel accessLevel = new AccountManagement().getCurrentUser().getAccessLevel();
        trueVisible(editCreditGroupBtn, accessLevel.greater(AccessLevel.CONSUMER));

        boolean state = accessLevel.equals(AccessLevel.ADMINISTRATOR);
        for (Node node : credits.getChildren()) {
            if (node instanceof ImageRowController) {
                ((ImageRowController) node).showEdit(state);
            } else if (node instanceof TextRowController) {
                ((TextRowController) node).showEdit(state);
            }
        }
    }
}