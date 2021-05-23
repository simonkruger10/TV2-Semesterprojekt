package com.company.data.mapper;

import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Credit extends Person implements ICredit {
    private CreditType type = CreditType.UNIT;
    private String image;
    private String name;
    private final Map<Integer, ICreditGroup> creditGroup = new HashMap<>();

    public Credit() {
    }

    public Credit(ICredit credit) {
        this.setCopyOf(credit);
    }

    public static Credit createFromQueryResult(ResultSet queryResult, CreditType type) throws SQLException {
        Credit credit = new Credit();
        credit.type = type;
        credit.setFirstName(queryResult.getString("name"));
        credit.setMiddleName(queryResult.getString("m_name"));
        credit.setLastName(queryResult.getString("l_name"));
        String image = queryResult.getString("image");
        if (image != null) {
            credit.setImage(credit.getImage());
        }
        credit.setEmail(queryResult.getString("email"));
        credit.setID(queryResult.getInt("id"));

        return credit;
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
    public ICreditGroup[] getCreditGroups() {
        return creditGroup.values().toArray(new ICreditGroup[0]);
    }


    public void addCreditGroup(ICreditGroup creditGroup) {
        this.creditGroup.put(creditGroup.getID(), creditGroup);
    }


    public void setCopyOf(ICredit credit) {
        assert credit != null;

        this.setType(credit.getType());
        this.setFirstName(credit.getFirstName());
        this.setMiddleName(credit.getMiddleName());
        this.setLastName(credit.getLastName());
        this.setImage(credit.getImage());
        this.setName(credit.getName());
        for (ICreditGroup creditGroup: credit.getCreditGroups()) {
            this.addCreditGroup(new CreditGroup(creditGroup));
        }
    }
}
