package com.company.data;

import com.company.common.ICreditGroup;

import java.util.UUID;

public class MainEntity {
    private String uuid = UUID.randomUUID().toString();

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }
}
