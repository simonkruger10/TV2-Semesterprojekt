package com.company.domain;

import com.company.common.ICreditGroup;

public interface ICreditGroupManagement {

    ICreditGroup[] search(String[] words);

    ICreditGroup[] search(String[] words, int maxResults);


    ICreditGroup[] getByName(String firstName);


    ICreditGroup getByUUID(String uuid);


    ICreditGroup create(ICreditGroup credit);


    void update(ICreditGroup credit);

    void update(String uuid, ICreditGroup credit);
}
