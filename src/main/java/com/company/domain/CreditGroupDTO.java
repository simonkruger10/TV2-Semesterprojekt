package com.company.domain;

import com.company.common.ICreditGroup;

public class CreditGroupDTO extends MainDTO implements ICreditGroup {
    private String name = null;

    CreditGroupDTO() {}

    CreditGroupDTO(ICreditGroup creditGroup) {
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
