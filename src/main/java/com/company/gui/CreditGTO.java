package com.company.gui;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.domain.CreditGroupDTO;
import com.company.domain.PersonDTO;

public class CreditGTO extends PersonDTO implements ICredit {
    private CreditGroupDTO creditGroup;

    CreditGTO() {
    }

    CreditGTO(ICredit credit) {
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
        this.setCreditGroup(new CreditGroupGTO(credit.getCreditGroup()));
    }
}
