package com.company.domain;

import com.company.common.ICredit;
import com.company.common.ICreditGroup;

public interface ICreditManagement {
    ICredit create(String firstName, ICreditGroup creditGroup);
    ICredit create(String firstName, String lastName, ICreditGroup creditGroup);
    ICredit create(String firstName, String middleName, String lastName, ICreditGroup creditGroup);

    ICredit[] search(String[] words);

    ICredit[] getByName(String firstName);
    ICredit[] getByName(String firstName, String lastName);
    ICredit[] getByName(String firstName, String middleName, String lastName);

    ICredit[] getByGroup(String groupName);
}
