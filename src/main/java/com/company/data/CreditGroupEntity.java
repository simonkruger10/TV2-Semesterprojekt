package com.company.data;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;

public class CreditGroupEntity extends MainEntity implements ICreditGroup {
    private String name;

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

    public String toJsonString() {
        return "{" +
                "\"_uuid\":\"" + getUUID() + "\"," +
                "\"name\":\"" + name + "\"" +
                '}';
    }
}
