package com.company.domain;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;

import java.util.UUID;

public class CreditDTO extends PersonDTO implements ICredit {
    private CreditGroupDTO creditGroup;

    @Override
    public ICreditGroup getCreditGroup() {
        return creditGroup;
    }

    public void setCreditGroup(ICreditGroup creditGroup) {
        this.creditGroup = (CreditGroupDTO) creditGroup;
    }


    public void copyCredit(ICredit credit) {
        assert credit != null;

        this.setUUID(credit.getUUID());
        this.setFirstName(credit.getFirstName());
        this.setMiddleName(credit.getMiddleName());
        this.setLastName(credit.getLastName());
        this.setCreditGroup(credit.getCreditGroup());
    }
}
