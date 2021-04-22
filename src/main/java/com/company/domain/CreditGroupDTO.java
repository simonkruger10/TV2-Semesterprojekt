package com.company.domain;

import com.company.common.ICreditGroup;

import java.util.UUID;

public class CreditGroupDTO implements ICreditGroup {
    private String name = null;
    private String uuid = UUID.randomUUID().toString();

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void copyCreditGroup(ICreditGroup creditGroup) {
        assert creditGroup != null;

        this.setName(creditGroup.getName());
    }
}
