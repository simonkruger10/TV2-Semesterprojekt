package com.company.domain;

import java.util.UUID;

public class MainDTO {
    private String uuid = UUID.randomUUID().toString();

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }
}
