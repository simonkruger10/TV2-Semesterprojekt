package com.company.gui;

import javafx.scene.control.Alert;

public interface CallbackHandler {
    void list(Type type);
    void show(Type type);
    void show(Type type, String uuid);
    void show(Type type, Alert.AlertType alertType, String message);
    void add(Type type);
    void edit(Type type, String uuid);

    void logout();
    void loginSuccess();
    void loginFailed(Alert.AlertType type, String message);
}
