package com.company.domain;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;

public class CreditDTO extends PersonDTO implements ICredit {
    private CreditGroupDTO creditGroup;

    CreditDTO() {
    }

    CreditDTO(ICredit credit) {
        setCopyOf(credit);
    }

    @Override
    public ICreditGroup getCreditGroup() {
        return creditGroup;
    }

    public void setCreditGroup(ICreditGroup creditGroup) {
        this.creditGroup = (CreditGroupDTO) creditGroup;
    }


    public void setCopyOf(ICredit credit) {
        assert credit != null;

        this.setUUID(credit.getUUID());
        this.setFirstName(credit.getFirstName());
        this.setMiddleName(credit.getMiddleName());
        this.setLastName(credit.getLastName());
        this.setCreditGroup(new CreditGroupDTO(credit.getCreditGroup()));
    }
}
