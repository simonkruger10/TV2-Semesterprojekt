package com.company.domain;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;

public interface ICreditManagement {
    ICredit[] search(String[] words);
    ICredit[] search(String[] words, int maxResults);

    ICredit[] getByName(String firstName);
    ICredit[] getByName(String firstName, String lastName);
    ICredit[] getByName(String firstName, String middleName, String lastName);

    ICredit[] getByGroup(String groupName);

    ICredit create(ICredit credit);

    void update(ICredit credit);
}
