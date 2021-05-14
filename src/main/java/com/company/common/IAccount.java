package com.company.common;

public interface IAccount {
    Integer getID();

    String getFullName();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    String getEmail();

    AccessLevel getAccessLevel();
}
