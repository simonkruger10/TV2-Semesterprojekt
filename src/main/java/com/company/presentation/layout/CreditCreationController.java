package com.company.presentation.layout;

import com.company.common.AccessLevel;
import com.company.common.ICreditGroup;
import com.company.common.Tools;
import com.company.domain.CreditGroupManagement;
import com.company.domain.CreditManagement;
import com.company.domain.dto.Credit;
import com.company.domain.dto.CreditGroup;
import com.company.presentation.CallbackHandler;
import com.company.presentation.IDTO;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.trueVisible;

public class CreditCreationController extends VBox implements UpdateHandler {
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
    }

    public void loadCredit(IDTO dto) {
        Credit credit = (Credit) dto.getDTO();

        nameText.setText(credit.getName());

        firstNameText.setText(credit.getFirstName());
        lastNameText.setText(credit.getLastName());
        emailText.setText(credit.getEmail());
        String image = credit.getImage();
        if (image == null) {
            image = "defaultCreditPerson.jpg";
        }
        this.image.setImage(getResourceAsImage("/images/" + image));

        callback.show(Type.CREDIT, () -> new CreditManagement().create(credit));
    }

    @FXML
    public void addCredit(MouseEvent event) {
        Credit credit = new Credit();
        credit.setFirstName(firstNameText.getText());
        credit.setLastName(lastNameText.getText());
        credit.setEmail(emailText.getText());

        CreditGroupManagement cMgt = new CreditGroupManagement();
        String creditGroupName = creditGroupText.getText();
        ICreditGroup creditGroup = cMgt.getByName(creditGroupName);
        if (creditGroup == null) {
            creditGroup = new CreditGroupManagement().create(new CreditGroup(creditGroupName));
        }
        credit.addCreditGroup(new CreditGroup(creditGroup));

        callback.show(Type.CREDIT, () -> new CreditManagement().create(credit));
    }

    @FXML
    void onBrowseClicked(MouseEvent event) {

    }

    @FXML
    public void onCheckClicked(MouseEvent event) {
        boolean state = personCheck.isSelected();
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
