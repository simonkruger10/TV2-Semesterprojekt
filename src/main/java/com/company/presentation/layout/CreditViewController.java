package com.company.presentation.layout;

import com.company.common.AccessLevel;
import com.company.common.ICredit;
import com.company.common.IProduction;
import com.company.common.Tools;
import com.company.domain.AccountManagement;
import com.company.domain.ProductionManagement;
import com.company.presentation.CallbackHandler;
import com.company.presentation.IDTO;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.trueVisible;


public class CreditViewController extends VBox implements UpdateHandler {
    @FXML
    private ImageView image;

    @FXML
    private Text firstName;

    @FXML
    private Text groupName;

    @FXML
    private Text lastName;

    @FXML
    private Button editCreditBtn;

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

        String image = credit.getImage();
        if (image == null) {
            image = "defaultCreditPerson.jpg";
        }
        this.image.setImage(getResourceAsImage("/images/" + image));
        firstName.setText(credit.getFirstName());
        lastName.setText(credit.getLastName());

        List<String> titles = new ArrayList<>();
        for (IProduction production : new ProductionManagement().getProductionByCredit(credit)) {
            titles.add(production.getName());
        }
        groupName.setText(String.join("\n", titles));
    }

    @FXML
    private void editCredit(MouseEvent event) {
        callback.edit(Type.CREDIT, new IDTO<ICredit>() {
            @Override
            public ICredit getDTO() {
                return credit;
            }
        });
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {
        AccessLevel accessLevel = new AccountManagement().getCurrentUser().getAccessLevel();
        trueVisible(editCreditBtn, accessLevel.greater(AccessLevel.CONSUMER));
    }
}