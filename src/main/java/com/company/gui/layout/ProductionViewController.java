package com.company.gui.layout;

import java.io.IOException;
import java.util.*;

import com.company.common.AccessLevel;
import com.company.common.ICredit;
import com.company.common.IProduction;
import com.company.common.Tools;
import com.company.domain.AccountManagement;
import com.company.domain.ProductionManagement;
import com.company.gui.Colors;
import com.company.gui.GUI;
import com.company.gui.Type;
import com.company.gui.UpdateHandler;
import com.company.gui.layout.parts.HeaderRowController;
import com.company.gui.layout.parts.TextRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static com.company.common.Tools.*;

public class ProductionViewController extends VBox implements UpdateHandler {
    @FXML
    private Text title;

    @FXML
    private Button editProductionBtn;

    @FXML
    private ImageView image;

    @FXML
    private VBox rows;

    private final GUI callback;
    private IProduction production;

    public ProductionViewController(Integer id, GUI callback) {
        this.callback = callback;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/ProductionView.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        update();
        loadProduction(id);
    }

    public void loadProduction(Integer id) {
        production = new ProductionManagement().getByID(id);

        title.setText(production.getName());
        String image = production.getImage();
        if (image != null) {
            this.image.setImage(getResourceAsImage(image));
        }

        // Group credits by creditGroup
        HashMap<String, List<ICredit>> grouped = new HashMap<>();
        for (ICredit credit : production.getCredits()) {
            String key = credit.getCreditGroup().getName();
            if (grouped.get(key) == null) {
                grouped.put(key, new ArrayList<>());
            }
            grouped.get(key).add(credit);
        }

        // Roll-out credits
        rows.getChildren().clear();
        for (String groupName : grouped.keySet()) {
            // Header
            HeaderRowController headerRowController = new HeaderRowController(groupName);
            if (rows.getChildren().size() == 0) {
                headerRowController.setTopMargin(0);
            }

            // Rows
            int i = 0;
            rows.getChildren().add(headerRowController);
            for (ICredit credit: grouped.get(groupName)) {
                TextRowController cRow = new TextRowController(Type.CREDIT, credit.getID(), callback);
                cRow.setText(credit.getFullName());
                if (!isEven(i)) {
                    cRow.setBackground(Colors.ODD_COLOR);
                }
                rows.getChildren().add(cRow);
                i++;
            }
        }
    }

    @FXML
    private void editProduction(MouseEvent event) {
        callback.edit(Type.PRODUCTION, production.getID());
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {
        AccessLevel accessLevel = new AccountManagement().getCurrentUser().getAccessLevel();
        trueVisible(editProductionBtn, accessLevel.greater(AccessLevel.CONSUMER));
    }

    @FXML
    private void initialize() {
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'ProductionView.fxml'.";
        assert editProductionBtn != null : "fx:id=\"editCreditBtn\" was not injected: check your FXML file 'ProductionView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'ProductionView.fxml'.";
        assert rows != null : "fx:id=\"roles\" was not injected: check your FXML file 'ProductionView.fxml'.";
    }
}
