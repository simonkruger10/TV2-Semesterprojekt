package com.company.presentation.layout;

import com.company.common.*;
import com.company.domain.CreditGroupManagement;
import com.company.domain.CreditManagement;
import com.company.domain.dto.Credit;
import com.company.domain.dto.CreditGroup;
import com.company.presentation.CallbackHandler;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Arrays;

import static com.company.common.Tools.*;

public class CreditCreationController extends HBox implements UpdateHandler {
    @SuppressWarnings("unused")
    @FXML
    private Label title;

    @SuppressWarnings("unused")
    @FXML
    private TextField nameText;

    @SuppressWarnings("unused")
    @FXML
    private TextField firstNameText;

    @SuppressWarnings("unused")
    @FXML
    private TextField lastNameText;

    @SuppressWarnings("unused")
    @FXML
    private TextField emailText;

    @SuppressWarnings("unused")
    @FXML
    private TextField creditGroupText;

    @SuppressWarnings("unused")
    @FXML
    private TextField productionText;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image;

    @SuppressWarnings("unused")
    @FXML
    private Button browse;

    @SuppressWarnings("unused")
    @FXML
    private Text imageText;

    @SuppressWarnings("unused")
    @FXML
    private CheckBox personCheck;

    @SuppressWarnings("unused")
    @FXML
    private Button addCreditBtn;

    private final CallbackHandler callback;
    private ICredit credit = null;

    public CreditCreationController(CallbackHandler callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/CreditCreation.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        image.setImage(getResourceAsImage("/images/defaultCreditPerson.jpg"));
    }

    public void loadCredit(ICredit credit) {
        this.credit = credit;
        System.out.println(Arrays.toString(this.credit.getCreditGroups()));

        nameText.setText(credit.getName());

        if (credit.getType().equals(CreditType.PERSON)) {
            firstNameText.setText(credit.getFirstName());
            lastNameText.setText(credit.getLastName());
            emailText.setText(credit.getEmail());
            image.setImage(getResourceAsImage("/images/" + credit.getImage()));
        }

        trueVisible(personCheck, false);
        trueVisible(creditGroupText, false);
        trueVisible(productionText, false);
        toggleView(credit.getType().equals(CreditType.PERSON));

        title.setText("Edit Credit");
    }

    @SuppressWarnings("unused")
    @FXML
    public void addCredit(@SuppressWarnings("unused") MouseEvent event) {
        Credit credit;
        if (this.credit == null) {
            credit = new Credit();

            if (personCheck.isSelected()) {
                credit.setType(CreditType.PERSON);
            } else {
                credit.setType(CreditType.UNIT);
            }
        } else {
            credit = new Credit(this.credit);
        }

        credit.setName(nameText.getText());
        credit.setFirstName(firstNameText.getText());
        credit.setLastName(lastNameText.getText());
        credit.setEmail(emailText.getText());
        credit.setImage(basename(image.getImage().getUrl()));

        if (this.credit == null) {
            CreditGroupManagement cMgt = new CreditGroupManagement();
            String creditGroupName = creditGroupText.getText();
            ICreditGroup creditGroup = cMgt.getByName(creditGroupName);
            if (creditGroup == null) {
                creditGroup = new CreditGroupManagement().create(new CreditGroup(creditGroupName));
            }
            credit.addCreditGroup(new CreditGroup(creditGroup));
        }

        if (this.credit == null) {
            callback.show(Type.CREDIT, () -> new CreditManagement().create(credit));
        } else {
            new CreditManagement().update(credit);
            callback.show(Type.CREDIT, () -> credit);
        }
    }

    @SuppressWarnings({"EmptyMethod", "unused"})
    @FXML
    void onBrowseClicked(MouseEvent event) {

    }

    @SuppressWarnings("unused")
    @FXML
    public void onCheckClicked(MouseEvent event) {
        toggleView(personCheck.isSelected());
    }

    private void toggleView(boolean state) {
        trueVisible(nameText, !state);

        trueVisible(firstNameText, state);
        trueVisible(lastNameText, state);
        trueVisible(emailText, state);
        trueVisible(image, state);
        trueVisible(browse, state);
        trueVisible(imageText, state);
    }

    @SuppressWarnings("unused")
    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return accessLevel.greater(AccessLevel.CONSUMER);
    }

    @SuppressWarnings({"EmptyMethod", "unused"})
    @Override
    public void update() {

    }
}
