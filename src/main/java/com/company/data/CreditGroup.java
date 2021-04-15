package com.company.data;

import com.company.crossInterfaces.CreditGroupEntity;

public class CreditGroup implements CreditGroupEntity {
    private final int id;
    private String name;

    public CreditGroup(String name) {
        this(-1, name);
    }

    public CreditGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
