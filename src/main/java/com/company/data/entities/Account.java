package com.company.data.entities;

import com.company.common.AccessLevel;
import com.company.common.IAccount;

public class Account extends Person implements IAccount {
    private AccessLevel accessLevel = null;

    public Account() {
    }

    public Account(IAccount account) {
        assert account != null;

        this.setFirstName(account.getFirstName());
        this.setLastName(account.getLastName());
        this.setEmail(account.getEmail());
        this.setAccessLevel(account.getAccessLevel());
    }


    @Override
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

}
