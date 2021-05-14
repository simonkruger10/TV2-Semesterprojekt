package com.company.domain;

import com.company.common.ICreditGroup;

public interface ICreditGroupManagement {
    ICreditGroup[] list();
    ICreditGroup[] list(int start);
    ICreditGroup[] list(int start, int max);

    ICreditGroup[] search(String[] words);
    ICreditGroup[] search(String[] words, int maxResults);

    ICreditGroup getByName(String name);

    ICreditGroup getByID(Integer id);

    ICreditGroup create(ICreditGroup credit);

    void update(ICreditGroup credit);
}
