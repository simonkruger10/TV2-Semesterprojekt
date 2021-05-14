package com.company.gui.layout;

import java.io.IOException;

import com.company.common.AccessLevel;
import com.company.common.Tools;
import com.company.gui.UpdateHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RecentlyAndReviewController extends VBox implements UpdateHandler {
    @FXML
    private HBox latestAddedSlider;

    @FXML
    private HBox latestReviewSlider;

    public RecentlyAndReviewController() {
        // TODO: Roll-out a real recently and review view
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Tools.getResourceAsUrl("/Layouts/RecentlyAndReview.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasAccess(AccessLevel accessLevel) {
        return true;
    }

    @Override
    public void update() {

    }

    @FXML
    private void initialize() {
        assert latestAddedSlider != null : "fx:id=\"latestAddedSlider\" was not injected: check your FXML file 'RecentlyAndReviewController.fxml'.";
        assert latestReviewSlider != null : "fx:id=\"latestReviewSlider\" was not injected: check your FXML file 'RecentlyAndReviewController.fxml'.";

    }
}
