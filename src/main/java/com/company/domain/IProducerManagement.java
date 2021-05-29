package com.company.domain;

import com.company.common.ICredit;
import com.company.common.IProducer;

public interface IProducerManagement {
    IProducer[] list();
    IProducer[] list(int start);
    IProducer[] list(int start, int max);

    IProducer[] search(String[] words);
    IProducer[] search(String[] words, int maxResults);

    IProducer getByName(String name);

    IProducer getByID(Integer id);

    IProducer create(IProducer producer);

    void update(IProducer producer);

    Integer count();
}
