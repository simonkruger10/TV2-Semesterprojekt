package com.company.domain.dto;

import com.company.common.ICreditGroup;

import java.util.Objects;

public class CreditGroup extends Identifier implements ICreditGroup {
    private String name = null;
    private String description = null;

    public CreditGroup() {
    }

    public CreditGroup(String name) {
        this.setName(name);
    }

    public CreditGroup(ICreditGroup creditGroup) {
        assert creditGroup != null;

        this.setID(creditGroup.getID());
        this.setName(creditGroup.getName());
        this.setDescription(creditGroup.getDescription());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditGroup)) return false;
        CreditGroup that = (CreditGroup) o;
        return Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
