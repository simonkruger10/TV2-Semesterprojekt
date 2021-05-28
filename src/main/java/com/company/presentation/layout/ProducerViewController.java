package com.company.presentation.layout;

import com.company.common.*;
import com.company.domain.*;
import com.company.presentation.GUI;
import com.company.presentation.IDTO;
import com.company.presentation.Type;
import com.company.presentation.UpdateHandler;
import com.company.presentation.layout.parts.ImageRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    @FXML
    private Text title;

    @FXML
    private Button editBtn;

    @FXML
    private ImageView image;

    @FXML
    private VBox account;

    @FXML
    private Label accountName;

    @FXML
    private Label accountEmail;

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

        String image = producer.getLogo();
        if (image == null) {
            image = "defaultProducer.png";
        }
        this.image.setImage(getResourceAsImage("/images/" + image));

        IAccount account = producer.getAccount();
        if (account != null) {
            accountName.setText(account.getFullName());
            accountEmail.setText(account.getEmail());
        }

        for (IProduction production: new ProductionManagement().getProducedBy(producer)) {
            ImageRowController imageRowController = new ImageRowController(Type.PRODUCTION, () -> production, callback);
            imageRowController.setText(production.getName());
            image = production.getImage();
            if (image == null) {
                image = "defaultProduction.png";
            }
            imageRowController.setImage(getResourceAsImage("/images/" + image));
            productions.getChildren().add(imageRowController);
        }
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
        trueVisible(account, accessLevel.equals(AccessLevel.PRODUCER) || accessLevel.equals(AccessLevel.ADMINISTRATOR));
    }
}
