package com.company.domain;

import com.company.common.ICredit;
import com.company.common.IProduction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductionDTO extends MainDTO implements IProduction {
    private String name = null;
    private String description = null;
    private File image = null;
    private ICredit[] credits = null;

    public ProductionDTO() {
    }

    public ProductionDTO(IProduction production) {
        setCopyOf(production);
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


    public void setCopyOf(IProduction production) {
        assert production != null;

        setUUID(production.getUUID());
        setName(production.getName());
        setDescription(production.getDescription());
        setImage(production.getImage());

        final List<ICredit> credits = new ArrayList<>();
        for (ICredit credit : production.getCredits()) {
            credits.add(new CreditDTO(credit));
        }
        setCredits(credits.toArray(new ICredit[0]));
    }
}
