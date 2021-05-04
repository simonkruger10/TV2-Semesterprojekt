package com.company.domain.descriptions;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;

public class Credit extends Person implements ICredit {
    private CreditGroup creditGroup;

    public Credit() {
    }

    public Credit(ICredit credit) {
        setCopyOf(credit);
    }

    @Override
    public ICreditGroup getCreditGroup() {
        return creditGroup;
    }

    public void setCreditGroup(ICreditGroup creditGroup) {
        this.creditGroup = (CreditGroup) creditGroup;
    }


    public void setCopyOf(ICredit credit) {
        assert credit != null;

        this.setUUID(credit.getUUID());
        this.setFirstName(credit.getFirstName());
        this.setMiddleName(credit.getMiddleName());
        this.setLastName(credit.getLastName());
        this.setCreditGroup(new CreditGroup(credit.getCreditGroup()));
    }
}
