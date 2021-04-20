package com.company.domain;

import com.company.common.AccountEntity;

public interface IAccountManagement {
    default void addAccount(AccountEntity accountinfo, String password) {

    }

    default boolean login(String email, String password) {
        return false;
    }

    default IAccountManagement getInstance() {
        return null;
    }
}
