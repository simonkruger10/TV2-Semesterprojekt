package com.company.presentation.layout;

import com.company.common.*;
import com.company.domain.AccountManagement;
import com.company.presentation.*;
import com.company.presentation.layout.parts.HeaderRowController;
import com.company.presentation.layout.parts.ImageRowController;
import com.company.presentation.layout.parts.TextRowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;

import static com.company.common.Tools.*;

public class ProductionViewController extends VBox implements UpdateHandler {
    @SuppressWarnings("unused")
    @FXML
    private Text title;

    @SuppressWarnings("unused")
    @FXML
    private Button editProductionBtn;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image;

    @SuppressWarnings("unused")
    @FXML
    private TextArea description;

    @SuppressWarnings("unused")
    @FXML
    private VBox rows;

    private final GUI callback;
    private IProduction production;

    public ProductionViewController(IProduction production, GUI callback) {
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
        description.setFocusTraversable(false);
        loadProduction(production);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public void loadProduction(IProduction production) {
        this.production = production;

        title.setText(production.getName());
        image.setImage(getResourceAsImage("/images/" + production.getImage()));
        description.setText(production.getDescription());

        // Group credits by creditGroup
        HashMap<String, List<ICredit>> grouped = new HashMap<>();
        ICredit[] sortedCredits = production.getCredits();
        Arrays.sort(sortedCredits, Comparator.comparing(ICredit::getFullName));
        for (ICredit credit : sortedCredits) {
            for (ICreditGroup creditGroup : credit.getCreditGroups()) { //TODO this will not allow credits without a creditGroup to be shown.
                String key = creditGroup.getName();
                //noinspection Java8MapApi
                if (grouped.get(key) == null) {
                    grouped.put(key, new ArrayList<>());
                }
                grouped.get(key).add(credit);
            }
        }

        // Roll-out credits
        rows.getChildren().clear();

        HeaderRowController headerRowController = new HeaderRowController("Producenter");
        headerRowController.setTopMargin(0);
        rows.getChildren().add(headerRowController);

        ImageRowController producerRow = new ImageRowController(Type.PRODUCER, production::getProducer, callback);
        producerRow.setText(production.getProducer().getName());
        producerRow.setImage(getResourceAsImage("/images/" + production.getProducer().getLogo()));
        rows.getChildren().add(producerRow);

        for (String groupName : grouped.keySet()) {
            // Header
            rows.getChildren().add(new HeaderRowController(groupName));

            // Rows
            int i = 0;
            for (ICredit credit : grouped.get(groupName)) {
                if (credit.getType().equals(CreditType.PERSON)) {
                    ImageRowController iRow = new ImageRowController(Type.CREDIT, () -> credit, callback);
                    iRow.setText(credit.getFullName());
                    iRow.setImage(getResourceAsImage("/images/" + credit.getImage()));
                    if (!isEven(i)) {
                        iRow.setBackground(Colors.ODD_COLOR);
                    }

                    rows.getChildren().add(iRow);
                } else {
                    TextRowController cRow = new TextRowController(Type.CREDIT, () -> credit, callback);
                    cRow.setText(credit.getName());
                    if (!isEven(i)) {
                        cRow.setBackground(Colors.ODD_COLOR);
                    }

                    rows.getChildren().add(cRow);
                }
                i++;
            }
        }

        update();
    }

    @SuppressWarnings("unused")
    @FXML
    private void editProduction(MouseEvent event) {
        callback.edit(Type.PRODUCTION, (IDTO<IProduction>) () -> production);
    }

    @SuppressWarnings("unused")
    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {
        AccessLevel accessLevel = new AccountManagement().getCurrentUser().getAccessLevel();
        trueVisible(editProductionBtn, accessLevel.greater(AccessLevel.CONSUMER));

        boolean state = accessLevel.equals(AccessLevel.ADMINISTRATOR);
        for (Node node: rows.getChildren()) {
            if (node instanceof ImageRowController) {
                ((ImageRowController) node).showEdit(state);
            } else if (node instanceof TextRowController) {
                ((TextRowController) node).showEdit(state);
            }
        }
    }
}
