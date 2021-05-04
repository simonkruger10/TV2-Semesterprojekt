package com.company.gui;

import com.company.common.AccessLevel;
import javafx.scene.control.Alert.AlertType;

public interface LoginHandler {
    void logout();
    void loginSuccess();
    void loginFailed(AlertType type, String message);
}
