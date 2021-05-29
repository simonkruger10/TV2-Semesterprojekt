package com.company.presentation.layout;

import com.company.common.*;
import com.company.domain.AccountManagement;
import com.company.domain.ProductionManagement;
import com.company.presentation.GUI;
import com.company.presentation.IDTO;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
import com.company.presentation.layout.parts.ImageRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.trueVisible;

public class ProducerViewController extends VBox implements UpdateHandler {
    @SuppressWarnings("unused")
    @FXML
    private Text title;

    @SuppressWarnings("unused")
    @FXML
    private Button editBtn;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image;

    @SuppressWarnings("unused")
    @FXML
    private VBox account;

    @SuppressWarnings("unused")
    @FXML
    private Label accountName;

    @SuppressWarnings("unused")
    @FXML
    private Label accountEmail;

    @SuppressWarnings("unused")
    @FXML
    private VBox productions;

    private final GUI callback;
    private IProducer producer;

    public ProducerViewController(IProducer producer, GUI callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/ProducerView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        update();
        loadProduction(producer);
    }

    public void loadProduction(IProducer producer) {
        this.producer = producer;

        title.setText(producer.getName());
        image.setImage(getResourceAsImage("/images/" + producer.getLogo()));

        IAccount account = producer.getAccount();
        if (account != null) {
            accountName.setText(account.getFullName());
            accountEmail.setText(account.getEmail());
        }

        for (IProduction production: new ProductionManagement().getProducedBy(producer)) {
            ImageRowController imageRowController = new ImageRowController(Type.PRODUCTION, () -> production, callback);
            imageRowController.setText(production.getName());
            imageRowController.setImage(getResourceAsImage("/images/" + production.getImage()));
            productions.getChildren().add(imageRowController);
        }

        update();
    }

    @FXML
    private void edit(MouseEvent event) {
        callback.edit(Type.PRODUCER, (IDTO<IProducer>) () -> producer);
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {
        AccessLevel accessLevel = new AccountManagement().getCurrentUser().getAccessLevel();
        trueVisible(editBtn, accessLevel.greater(AccessLevel.CONSUMER));

        boolean state = accessLevel.equals(AccessLevel.PRODUCER) || accessLevel.equals(AccessLevel.ADMINISTRATOR);
        trueVisible(account, state);
        for (Node node: productions.getChildren()) {
            if (node instanceof ImageRowController) {
                ((ImageRowController) node).showEdit(state);
            }
        }
    }
}
