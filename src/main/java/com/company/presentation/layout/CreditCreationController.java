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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.trueVisible;

public class CreditCreationController extends HBox implements UpdateHandler {
    @FXML
    private TextField nameText;

    @FXML
    private TextField firstNameText;

    @FXML
    private TextField lastNameText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField creditGroupText;

    @FXML
    private TextField productionText;

    @FXML
    private ImageView image;

    @FXML
    private Button browse;

    @FXML
    private Text imageText;

    @FXML
    private CheckBox personCheck;

    @FXML
    private Button addCreditBtn;

    private final CallbackHandler callback;
    private boolean edit = false;

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
        this.edit = true;

        nameText.setText(credit.getName());

        firstNameText.setText(credit.getFirstName());
        lastNameText.setText(credit.getLastName());
        emailText.setText(credit.getEmail());
        image.setImage(getResourceAsImage("/images/" + credit.getImage()));

        trueVisible(personCheck, false);
        trueVisible(creditGroupText, false);
        trueVisible(productionText, false);
        toggleView(credit.getType().equals(CreditType.PERSON));
    }

    @FXML
    public void addCredit(MouseEvent event) {
        Credit credit = new Credit();
        credit.setFirstName(firstNameText.getText());
        credit.setLastName(lastNameText.getText());
        credit.setEmail(emailText.getText());
        if (personCheck.isSelected()) {
            credit.setType(CreditType.PERSON);
        } else {
            credit.setType(CreditType.UNIT);
        }
        String[] parts = image.getImage().getUrl().split("([^\\/]+$)"); // Getting basename of the url
        credit.setImage(parts[parts.length - 1]);

        CreditGroupManagement cMgt = new CreditGroupManagement();
        String creditGroupName = creditGroupText.getText();
        ICreditGroup creditGroup = cMgt.getByName(creditGroupName);
        if (creditGroup == null) {
            creditGroup = new CreditGroupManagement().create(new CreditGroup(creditGroupName));
        }
        credit.addCreditGroup(new CreditGroup(creditGroup));

        if (edit) {
            new CreditManagement().update(credit);
            callback.show(Type.CREDIT, () -> credit);
        } else {
            callback.show(Type.CREDIT, () -> new CreditManagement().create(credit));
        }
    }

    @FXML
    void onBrowseClicked(MouseEvent event) {

    }

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

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return accessLevel.greater(AccessLevel.CONSUMER);
    }

    @Override
    public void update() {

    }
}
