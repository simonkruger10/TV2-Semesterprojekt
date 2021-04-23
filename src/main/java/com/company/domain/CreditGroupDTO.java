package com.company.domain;

import com.company.common.ICreditGroup;

import java.util.UUID;

public class CreditGroupDTO extends MainDTO implements ICreditGroup {
    private String name = null;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void copyCreditGroup(ICreditGroup creditGroup) {
        assert creditGroup != null;

        this.setUUID(creditGroup.getUUID());
        this.setName(creditGroup.getName());
    }
}
