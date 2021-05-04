package com.company.gui;

import com.company.common.AccessLevel;
import javafx.scene.control.Alert.AlertType;

public interface ContentHandler {
    void list(Type type);
    void show(Type type);
    void show(Type type, String uuid);
    void show(Type type, AlertType alertType, String message);
    void add(Type type);
    void edit(Type type, String uuid);
}
