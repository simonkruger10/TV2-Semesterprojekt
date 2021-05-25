package com.company.data.mapper;

import com.company.common.ICreditGroup;
import com.company.presentation.entity.Credit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CreditGroup extends Identifier implements ICreditGroup {
    private String name = null;
    private String description = null;

    public CreditGroup() {
    }

    public CreditGroup(ICreditGroup creditGroup) {
        this.setCopyOf(creditGroup);
    }

    public static CreditGroup createFromQueryResult(ResultSet queryResult) throws SQLException {
        CreditGroup cg = new CreditGroup();
        cg.description = queryResult.getString("description");
        cg.name = queryResult.getString("name");
        cg.setID(queryResult.getInt("id"));

        return cg;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setCopyOf(ICreditGroup creditGroup) {
        assert creditGroup != null;

        this.setName(creditGroup.getName());
        this.setDescription(creditGroup.getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditGroup that = (CreditGroup) o;
        return name.equals(that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
