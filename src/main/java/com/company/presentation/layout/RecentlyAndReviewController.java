package com.company.presentation.layout;

import com.company.common.AccessLevel;
import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.common.IProduction;
import com.company.domain.CreditManagement;
import com.company.domain.ProductionManagement;
import com.company.presentation.UpdateHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.company.common.Tools.getResourceAsImage;
import static com.company.common.Tools.getResourceAsUrl;

public class RecentlyAndReviewController extends VBox implements UpdateHandler {
    @SuppressWarnings("unused")
    @FXML
    private HBox latestAddedSlider;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image1;

    @SuppressWarnings("unused")
    @FXML
    private Text title1;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image2;

    @SuppressWarnings("unused")
    @FXML
    private Text title2;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image3;

    @SuppressWarnings("unused")
    @FXML
    private Text title3;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image4;

    @SuppressWarnings("unused")
    @FXML
    private Text title4;

    @SuppressWarnings("unused")
    @FXML
    private HBox latestReviewSlider;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image5;

    @SuppressWarnings("unused")
    @FXML
    private Text title5;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image6;

    @SuppressWarnings("unused")
    @FXML
    private Text title6;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image7;

    @SuppressWarnings("unused")
    @FXML
    private Text title7;

    @SuppressWarnings("unused")
    @FXML
    private ImageView image8;

    @SuppressWarnings("unused")
    @FXML
    private Text title8;

    public RecentlyAndReviewController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getResourceAsUrl("/Layouts/RecentlyAndReview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        load();
    }

    public void load() {
        ImageView[] imageViews = new ImageView[]{image1, image2, image3, image4};
        Text[] texts = new Text[]{title1, title2, title3, title4};

        final IProduction[] productions = new ProductionManagement().list(0, 4);
        for (int i = 0; i < productions.length; i++) {
            set(imageViews[i], texts[i], productions[i]);
        }

        imageViews = new ImageView[]{image5, image6, image7, image8};
        texts = new Text[]{title5, title6, title7, title8};

        final ICredit[] credits = new CreditManagement().list(0, 4);
        for (int i = 0; i < productions.length; i++) {
            set(imageViews[i], texts[i], credits[i]);
        }
    }

    private void set(ImageView imageNode, Text textNode, IProduction production) {
        imageNode.setImage(getResourceAsImage("/images/" + production.getImage()));
        textNode.setText(production.getName());
    }

    private void set(ImageView imageNode, Text textNode, ICredit credit) {
        if (credit.getType() == CreditType.UNIT) {
            textNode.setText(credit.getName());
        } else {
            textNode.setText(credit.getFullName());
            imageNode.setImage(getResourceAsImage("/images/" + credit.getImage()));
        }
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {

    }
}
