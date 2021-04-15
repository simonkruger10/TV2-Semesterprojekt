package com.company.Domain;

import com.company.crossInterfaces.AccountEntity;

public interface Account {
    default void addAccount(AccountEntity accountinfo, String password) {

    }

    default boolean login(String email, String password) {
        return false;
    }

    default Account getInstance() {
        return null;
    }
}
