package com.company.domain.descriptions;

import com.company.common.ICreditGroup;

public class CreditGroup extends Main implements ICreditGroup {
    private String name = null;

    public CreditGroup() {}

    public CreditGroup(ICreditGroup creditGroup) {
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
