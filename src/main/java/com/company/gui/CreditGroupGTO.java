package com.company.gui;

import com.company.common.ICreditGroup;
import com.company.domain.MainDTO;

public class CreditGroupGTO extends MainDTO implements ICreditGroup {
    private String name = null;

    CreditGroupGTO() {}

    CreditGroupGTO(ICreditGroup creditGroup) {
        setCopyOf(creditGroup);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCopyOf(ICreditGroup creditGroup) {
        assert creditGroup != null;

        this.setUUID(creditGroup.getUUID());
        this.setName(creditGroup.getName());
    }
}
