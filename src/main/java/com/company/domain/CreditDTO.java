package com.company.domain;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;

public class CreditDTO extends PersonDTO implements ICredit {
    private CreditGroupDTO creditGroup;

    public CreditDTO(String firstName, ICreditGroup creditGroup) {
        super(firstName);
        this.creditGroup = (CreditGroupDTO) creditGroup;
    }

    public CreditDTO(String firstName, String lastName, ICreditGroup creditGroup) {
        super(firstName, lastName);
        this.creditGroup = (CreditGroupDTO) creditGroup;
    }

    public CreditDTO(String firstName, String middleName, String lastName, ICreditGroup creditGroup) {
        super(firstName, middleName, lastName);
        this.creditGroup = (CreditGroupDTO) creditGroup;
    }

    @Override
    public ICreditGroup getCreditGroup() {
        return creditGroup;
    }

    @Override
    public void setCreditGroup(ICreditGroup creditGroup) {
        this.creditGroup = (CreditGroupDTO) creditGroup;
    }
}
