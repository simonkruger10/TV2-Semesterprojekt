package com.company.data.mapper;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;

public class Credit extends Person implements ICredit {
    private ICreditGroup creditGroup;

    public ICreditGroup getCreditGroup() {
        return creditGroup;
    }

    public void setCreditGroup(ICreditGroup creditGroup) {
        this.creditGroup = creditGroup;
    }


    public void setCopyOf(ICredit credit) {
        assert credit != null;

        this.setFirstName(credit.getFirstName());
        this.setMiddleName(credit.getMiddleName());
        this.setLastName(credit.getLastName());
        this.setCreditGroup(credit.getCreditGroup());
    }

    public String toJsonString() {
        return "{" +
                "\"_uuid\":\"" + getUUID() + "\"," +
                "\"firstName\":\"" + getFirstName() + "\"," +
                "\"middleName\":\"" + getMiddleName() + "\"," +
                "\"lastName\":\"" + getLastName() + "\"," +
                "\"creditGroup\":\"" + creditGroup.getName() + "\"" +
                '}';
    }
}
