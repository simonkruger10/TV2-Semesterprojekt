package com.company.domain;

import com.company.common.ICredit;
import com.company.common.IProduction;

import java.io.File;

public class ProductionDTO implements IProduction {
    private String uuid = null;
    private String name = null;
    private String description = null;
    private File image = null;
    private ICredit[] credits = null;

    @Override
    public String getUUID() {
        return uuid;
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
    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    @Override
    public ICredit[] getCredits() {
        return credits;
    }

    public void setCredits(ICredit[] credits) {
        this.credits = credits;
    }


    public void copyProduction(IProduction production) {
        assert production != null;

        setName(production.getName());
        setDescription(production.getDescription());
        setImage(production.getImage());
        setCredits(production.getCredits());
    }
}
