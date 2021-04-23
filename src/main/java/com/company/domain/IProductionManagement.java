package com.company.domain;

import com.company.common.IProduction;

public interface IProductionManagement {
    IProduction[] list();
    IProduction[] list(int start);
    IProduction[] list(int start, int max);

    IProduction[] search(String[] words);
    IProduction[] search(String[] words, int maxResults);

    IProduction[] getByName(String name);

    IProduction getByUUID(String uuid);

    IProduction create(IProduction credit);

    void update(IProduction credit);
}
