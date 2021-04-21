package com.company.domain;

import com.company.common.IAccount;
import com.company.common.IAccessLevel;

public interface IAccountManagement {
    IAccount create(String firstName, String email);
    IAccount create(String firstName, String email, IAccessLevel accessLevel);
    IAccount create(String firstName, String lastName, String email);
    IAccount create(String firstName, String lastName, String email, IAccessLevel accessLevel);
    IAccount create(String firstName, String middleName, String lastName, String email);
    IAccount create(String firstName, String middleName, String lastName, String email, IAccessLevel accessLevel);

    IAccount[] search(String[] words);

    IAccount[] getByName(String name);
    IAccount[] getByName(String firstName, String lastName);
    IAccount[] getByName(String firstName, String middleName, String lastName);

    IAccount[] getByEmail(String email);
}
