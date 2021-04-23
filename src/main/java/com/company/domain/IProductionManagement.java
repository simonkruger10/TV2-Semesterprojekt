package com.company.domain;

import com.company.common.IProduction;

public interface IProductionManagement {
    IProduction[] search(String[] words);

    IProduction[] search(String[] words, int maxResults);


    IProduction[] getByName(String name);


    IProduction getByUUID(String uuid);


    IProduction create(IProduction credit);


    void update(IProduction credit);

    void update(String uuid, IProduction credit);
}
