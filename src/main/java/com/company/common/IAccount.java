package com.company.common;

public interface IAccount {
    String getFullName();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    String getEmail();

    IAccessLevel getAccessLevel();
}
