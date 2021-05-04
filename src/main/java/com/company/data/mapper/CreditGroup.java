package com.company.data.mapper;

import com.company.common.ICreditGroup;

public class CreditGroup extends Main implements ICreditGroup {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCopyOf(ICreditGroup creditGroup) {
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
