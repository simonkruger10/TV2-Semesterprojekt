package com.company.data;

import java.io.File;

public class Production {
    private String  productionName;
    private String Description;
    private File Image;
    private Credit credit;

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

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }
}
