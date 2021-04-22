package com.company.domain;

import com.company.common.IAccount;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;

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
        this.setFirstName(credit.getFirstName());
        this.setMiddleName(credit.getMiddleName());
        this.setLastName(credit.getLastName());
        this.setCreditGroup(credit.getCreditGroup());
    }
}
