package com.company.presentation.entity;

import com.company.common.ICreditGroup;

public class CreditGroup extends Identifier implements ICreditGroup {
    private String name = null;
    private String description = null;

    public CreditGroup() {
    }

    public CreditGroup(String name) {
        this(name, null);
    }

    public CreditGroup(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    public CreditGroup(ICreditGroup creditGroup) {
        this.setCopyOf(creditGroup);
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

        this.setID(creditGroup.getID());
        this.setName(creditGroup.getName());
        this.setDescription(creditGroup.getDescription());
    }
}