package com.company.common;

public interface IProduction {
    Integer getID();

    String getName();

    Integer getReleaseDay();

    Integer getReleaseMonth();

    Integer getReleaseYear();

    String getDescription();

    String getImage();

    IProducer getProducer();

    ICredit[] getCredits();
}
