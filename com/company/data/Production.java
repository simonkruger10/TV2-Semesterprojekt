package com.company.data;

import java.io.File;
import java.util.ArrayList;

public class Production {
    private String  productionName;
    private String Description;
    private File Image;
    private ArrayList<Credit> credit;

    public Production(String productionName) {
        this.productionName = productionName;
        this.credit = new ArrayList<>();
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public File getImage() {
        return Image;
    }

    public void setImage(File image) {
        Image = image;
    }

    public ArrayList<Credit> getCredit() {
        return credit;
    }

    public void addCredit(Credit credit) {
        this.credit.add(credit);
    }

    public boolean removeCredit(Credit credit) {
        return this.credit.remove(credit);
    }

    @Override
    public String toString() {
        return "Production{" +
                "productionName='" + productionName + '\'' +
                ", Description='" + Description + '\'' +
                ", Image=" + Image +
                ", credit=" + credit +
                '}';
    }

    public String toJSONString() {
        return "{" +
                "\"productionName\" :" + "\"" +productionName + "\"," +
                //"\"Description='" + Description + '\'' +
                //"\"Image=" + Image +
                "\"credits\" :" + "[" + credit + "]" +
                '}';
    }

    private String creditJsonString() {
        StringBuilder stringBuilder = new StringBuilder();


        return stringBuilder.toString();
    }
}
