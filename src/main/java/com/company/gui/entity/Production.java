package com.company.gui.entity;

import com.company.common.ICredit;
import com.company.common.IProducer;
import com.company.common.IProduction;

import java.util.*;

public class Production extends Identifier implements IProduction {
    private String name = null;
    private Integer releaseDay = null;
    private Integer releaseMonth = null;
    private Integer releaseYear = null;
    private String description = null;
    private String image = null;
    private Producer producer = null;
    private final Map<Integer, Credit> credits = new HashMap<>();

    public Production() {
    }

    public Production(IProduction production) {
        this.setCopyOf(production);
    }


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public Integer getReleaseDay() {
        return releaseDay;
    }

    public void setReleaseDay(Integer releaseDay) {
        this.releaseDay = releaseDay;
    }


    @Override
    public Integer getReleaseMonth() {
        return releaseMonth;
    }

    public void setReleaseMonth(Integer releaseMonth) {
        this.releaseMonth = releaseMonth;
    }


    @Override
    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }


    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public IProducer getProducer() {
        return producer;
    }

    public void setProducer(IProducer producer) {
        this.producer = (Producer) producer;
    }


    @Override
    public ICredit[] getCredits() {
        return credits.values().toArray(new ICredit[0]);
    }


    public void setCredit(ICredit credit) {
        credits.put(credit.getID(), (Credit) credit);
    }

    public void setCopyOf(IProduction production) {
        assert production != null;

		this.setID(production.getID());
        this.setName(production.getName());
        this.setReleaseDay(production.getReleaseDay());
        this.setReleaseMonth(production.getReleaseMonth());
        this.setReleaseYear(production.getReleaseYear());
        this.setDescription(production.getDescription());
        this.setImage(production.getImage());
        this.setProducer(production.getProducer());

        for (ICredit credit : production.getCredits()) {
	        this.setCredit(new Credit(credit));
        }
    }
}
