package com.company.data;

public class CreditGroupEntity implements com.company.crossInterfaces.ICreditGroup {
    private final int id;
    private String name;

    public CreditGroupEntity(String name) {
        this(-1, name);
    }

    public CreditGroupEntity(int id, String name) {
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
