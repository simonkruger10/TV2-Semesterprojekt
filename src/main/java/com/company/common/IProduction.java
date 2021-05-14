package com.company.common;

public interface IProduction {
    Integer getID();

    String getName();

    String getReleaseDay();

    String getReleaseMonth();

    String getReleaseYear();

    String getDescription();

    String getImage();

    IProducer getProducer();

    ICredit[] getCredits();
}
