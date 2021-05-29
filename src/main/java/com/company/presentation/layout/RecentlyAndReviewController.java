package com.company.presentation.layout;

import com.company.common.AccessLevel;
import com.company.common.Tools;
import com.company.presentation.UpdateHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RecentlyAndReviewController extends VBox implements UpdateHandler {
    @SuppressWarnings("unused")
    @FXML
    private HBox latestAddedSlider;

    @SuppressWarnings("unused")
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
}
