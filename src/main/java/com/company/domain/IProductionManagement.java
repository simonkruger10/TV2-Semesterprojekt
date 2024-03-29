package com.company.domain;

import com.company.common.ICredit;
import com.company.common.IProducer;
import com.company.common.IProduction;

@SuppressWarnings("unused")
public interface IProductionManagement {
    IProduction[] list();
    IProduction[] list(int start);
    IProduction[] list(int start, int max);

    IProduction[] search(String[] words);
    IProduction[] search(String[] words, int maxResults);

    IProduction[] getByName(String name);

    IProduction[] getProductionByCredit(ICredit credit);

    IProduction[] getProducedBy(IProducer producer);

    IProduction getByID(Integer id);

    IProduction create(IProduction credit);

    void update(IProduction credit);

    Integer count();
}
