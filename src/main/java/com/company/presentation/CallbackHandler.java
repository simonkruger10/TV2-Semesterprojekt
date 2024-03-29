package com.company.presentation;

import javafx.scene.control.Alert.AlertType;

public interface CallbackHandler {
    void list(Type type);
    void list(Type type, Integer page);

    void show(Type type);
    void show(Type type, @SuppressWarnings("rawtypes") IDTO dto);
    void show(AlertType alertType, String message);

    void add(Type type);

    void edit(Type type, @SuppressWarnings("rawtypes") IDTO dto);

    void logout();
    void loginSuccess();
    void loginFailed(AlertType type, String message);
}
