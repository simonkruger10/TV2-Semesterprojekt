package com.company.data.entities;

import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Credit extends Person implements ICredit {
    private CreditType type = CreditType.PERSON;
    private String image;
    private final Map<Integer, ICreditGroup> creditGroup = new HashMap<>();

    public Credit() {
    }

    public Credit(ICredit credit) {
        assert credit != null;
        this.setType(credit.getType());
        this.setFirstName(credit.getFirstName());
        this.setMiddleName(credit.getMiddleName());
        this.setLastName(credit.getLastName());
        this.setImage(credit.getImage());
        this.setEmail(credit.getEmail());

        for (ICreditGroup creditGroup: credit.getCreditGroups()) {
            this.addCreditGroup(new CreditGroup(creditGroup));
        }
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
    public ICreditGroup[] getCreditGroups() {
        if(creditGroup != null)
            return creditGroup.values().toArray(new ICreditGroup[0]);
        else
            return new ICreditGroup[0];
    }


    public void addCreditGroup(ICreditGroup creditGroup) {
        this.creditGroup.put(creditGroup.getID(), creditGroup);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ICredit)) return false;
        ICredit icredit = (ICredit) o;
        Credit credit = new Credit(icredit);
        return type == credit.type &&
                Objects.equals(image, credit.image) &&
                Objects.equals(creditGroup, credit.creditGroup) &&
                super.equals(o);
    }
}
