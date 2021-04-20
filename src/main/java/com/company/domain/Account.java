package com.company.domain;

import com.company.crossInterfaces.IAccount;

public interface Account {
    default void addAccount(IAccount accountinfo, String password) {

    }

    default boolean login(String email, String password) {
        return false;
    }

    default Account getInstance() {
        return null;
    }
}
