package com.company.gui.entity;

import java.util.UUID;

public class Main {
    private String uuid = UUID.randomUUID().toString();

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }
}
