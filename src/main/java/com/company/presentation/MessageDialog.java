package com.company.presentation;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import static com.company.common.Tools.getResourceAsImage;

public class MessageDialog extends Alert {

    public MessageDialog(AlertType alertType, String message) {
        super(alertType);

        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(getResourceAsImage("/images/icon.png"));

        if (alertType == AlertType.CONFIRMATION) {
            this.setTitle("Confirmation");
        }
        else if (alertType == AlertType.ERROR) {
            this.setTitle("Error");
        }
        else if (alertType == AlertType.INFORMATION) {
            this.setTitle("Information");
        }
        else if (alertType == AlertType.WARNING) {
            this.setTitle("Warning");
        }
        else {
            this.setTitle("Dialog");
        }

        this.setHeaderText(null);
        this.setContentText(message);

        this.showAndWait();
    }
}
