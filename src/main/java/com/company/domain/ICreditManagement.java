package com.company.domain;

import com.company.common.CreditType;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.common.IProduction;

import java.util.List;
import java.util.Map;

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

    Map<ICreditGroup, List<IProduction>> getCreditedFor(ICredit credit);

    ICredit create(ICredit credit);

    void update(ICredit credit);
}
