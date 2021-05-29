package com.company.presentation.layout;

import com.company.common.*;
import com.company.domain.AccountManagement;
import com.company.domain.ProductionManagement;
import com.company.presentation.CallbackHandler;
import com.company.presentation.IDTO;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
import com.company.presentation.layout.parts.ImageRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.trueVisible;


public class CreditViewController extends VBox implements UpdateHandler {
    @FXML
    private Button editCreditBtn;

    @FXML
    private ImageView image;

    @FXML
    private GridPane unit;

    @FXML
    private Text name;

    @FXML
    private GridPane person;

    @FXML
    private Text firstName;

    @FXML
    private Text lastName;

    @FXML
    private VBox creditedFor;

    private ICredit credit;
    private final CallbackHandler callback;

    public CreditViewController(ICredit credit, CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/CreditView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        update();

        viewCredit(credit);
    }

    public void viewCredit(ICredit credit) {
        this.credit = credit;
        boolean isUnit = credit.getType().equals(CreditType.UNIT);

        trueVisible(unit, isUnit);
        trueVisible(image, !isUnit);
        trueVisible(person, !isUnit);

        if (isUnit) {
            name.setText(credit.getName());
        } else {
            firstName.setText(credit.getFirstName());
            lastName.setText(credit.getLastName());
            image.setImage(getResourceAsImage("/images/" + credit.getImage()));
        }

        for (IProduction production : new ProductionManagement().getProductionByCredit(credit)) {
            ImageRowController iRow = new ImageRowController(Type.PRODUCTION, (IDTO<IProduction>) () -> production, callback);
            iRow.setText(production.getName());
            iRow.setImage(getResourceAsImage("/images/" + production.getImage()));
            creditedFor.getChildren().add(iRow);
        }

        update();
    }

    @FXML
    private void editCredit(MouseEvent event) {
        callback.edit(Type.CREDIT, (IDTO<ICredit>) () -> credit);
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {
        AccessLevel accessLevel = new AccountManagement().getCurrentUser().getAccessLevel();
        trueVisible(editCreditBtn, accessLevel.greater(AccessLevel.CONSUMER));

        boolean state = accessLevel.equals(AccessLevel.PRODUCER) || accessLevel.equals(AccessLevel.ADMINISTRATOR);
        for (Node node: creditedFor.getChildren()) {
            if (node instanceof ImageRowController) {
                ((ImageRowController) node).showEdit(state);
            }
        }
    }
}