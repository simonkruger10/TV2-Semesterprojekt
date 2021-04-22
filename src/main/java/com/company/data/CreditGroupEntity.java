package com.company.data;

public class CreditGroupEntity {
    private final int id;
    private String name;

    public CreditGroupEntity(String name) {
        this(-1, name);
    }

    public CreditGroupEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
