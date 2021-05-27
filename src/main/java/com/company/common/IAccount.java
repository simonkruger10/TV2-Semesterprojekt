package com.company.common;

public interface IAccount {
    Integer getID();

    String getFullName();

    String getFirstName();

    String getLastName();

    String getEmail();

    AccessLevel getAccessLevel();
}
