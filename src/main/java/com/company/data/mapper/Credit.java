package com.company.data.mapper;

import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;

public class Credit extends Person implements ICredit {
    private CreditType type = CreditType.UNIT;
    private String image;
    private String name;
    private ICreditGroup creditGroup;

    public Credit() {
    }

    public Credit(ICredit credit) {
        this.setCopyOf(credit);
    }


    @Override
    public CreditType getType() {
        return type;
    }

    public void setType(CreditType type) {
        this.type = type;
    }


    @Override
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public ICreditGroup getCreditGroup() {
        return creditGroup;
    }

    public void setCreditGroup(ICreditGroup creditGroup) {
        this.creditGroup = creditGroup;
    }


    public void setCopyOf(ICredit credit) {
        assert credit != null;

        this.setType(credit.getType());
        this.setFirstName(credit.getFirstName());
        this.setMiddleName(credit.getMiddleName());
        this.setLastName(credit.getLastName());
        this.setImage(credit.getImage());
        this.setName(credit.getName());
        this.setCreditGroup(new CreditGroup(credit.getCreditGroup()));
    }
}
