package com.company.data;

import java.io.File;
import java.util.ArrayList;

public class ProductionEntity implements com.company.common.IProduction {
    private final int id;
    private String name;
    private String description;
    private File image;
    private ArrayList<CreditEntity> credit;

    public ProductionEntity(String name, String description, File image) {
        this(-1, name, description, image);
    }

    public ProductionEntity(int id, String name, String description, File image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public File getImage() {
        return this.image;
    }

    @Override
    public void setImage(File image) {
        this.image = image;
    }

    public ArrayList<CreditEntity> getCredit() {
        return credit;
    }

    public void addCredit(CreditEntity credit) {
        this.credit.add(credit);
    }

    public boolean removeCredit(CreditEntity credit) {
        return this.credit.remove(credit);
    }

    @Override
    public String toString() {
        return "Production{" +
                "productionName='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", credit=" + credit +
                '}';
    }

    public String toJSONString() {
        return "{" +
                "\"productionName\" :" + "\"" + name + "\"," +
                //"\"description='" + description + '\'' +
                //"\"image=" + image +
                "\"credits\" :" + "[" + credit + "]" +
                '}';
    }

    private String creditJsonString() {
        StringBuilder stringBuilder = new StringBuilder();


        return stringBuilder.toString();
    }
}
