package com.company.data;

import com.company.common.ICredit;
import com.company.common.IProduction;

import java.io.File;
import java.util.*;

public class ProductionEntity extends MainEntity implements IProduction {
    private String name = null;
    private String description = null;
    private File image = null;
    private final Map<String, CreditEntity> credits = new HashMap<>();

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public File getImage() {
        return this.image;
    }

    public void setImage(File image) {
        this.image = image;
    }


    @Override
    public ICredit[] getCredits() {
        return credits.values().toArray(new ICredit[0]);
    }

    public void setCredits(ICredit[] credits) {
        assert credits != null;
        for (ICredit credit : credits) {
            this.credits.put(credit.getUUID(), (CreditEntity) credit);
        }
    }

    public void addCredits(ICredit credit) {
        assert credit != null;
        this.credits.put(credit.getUUID(), (CreditEntity) credit);
    }

    public void removeCredits(String uuid) {
        this.credits.remove(uuid);
    }


    public void setCopyOf(IProduction production) {
        assert production != null;

        setName(production.getName());
        setDescription(production.getDescription());
        setImage(production.getImage());
        setCredits(production.getCredits());
    }

    public String toJsonString() {
        return "{" +
                "\"_uuid\":\"" + getUUID() + "\"," +
                "\"productionName\":\"" + name + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"image\":\"" + image.getPath() + "\"," +
                "\"credits\":" + creditsAsJsonString() +
                '}';
    }

    private String creditsAsJsonString() {
        StringJoiner jsonString = new StringJoiner(",");
        for (CreditEntity credit : this.credits.values()) {
            jsonString.add(credit.toJsonString());
        }
        return "[" + jsonString + "]";
    }
}
