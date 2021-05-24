package com.company.domain;

import com.company.common.CreditType;
import com.company.common.ICredit;

public interface ICreditManagement {
    ICredit[] list();
    ICredit[] list(int start);
    ICredit[] list(int start, int max);

    ICredit[] search(String[] words);
    ICredit[] search(String[] words, int maxResults);

    ICredit[] getByName(String firstName);
    ICredit[] getByName(String firstName, String lastName);
    ICredit[] getByName(String firstName, String middleName, String lastName);

    ICredit[] getByGroup(String groupName);

    ICredit getByID(Integer id, CreditType type);

    ICredit getWithAllCreditGroups(ICredit credit);

    ICredit create(ICredit credit);

    void update(ICredit credit);
}
