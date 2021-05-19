package com.company.presentation;

import javafx.scene.control.Alert;

public interface CallbackHandler {
    void list(Type type);
    void show(Type type);
    void show(Type type, Integer id);
    void show(Type type, Alert.AlertType alertType, String message);
    void add(Type type);
    void edit(Type type, Integer id);

    void logout();
    void loginSuccess();
    void loginFailed(Alert.AlertType type, String message);
}
