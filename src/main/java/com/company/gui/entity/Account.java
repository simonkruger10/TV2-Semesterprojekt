package com.company.gui.entity;

import com.company.common.AccessLevel;
import com.company.common.IAccount;

public class Account extends Person implements IAccount {
    private AccessLevel accessLevel = null;

    public Account() {
    }

    public Account(IAccount account) {
        this.setCopyOf(account);
    }


    @Override
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }


    public void setCopyOf(IAccount account) {
        assert account != null;

        this.setID(account.getID());
        this.setFirstName(account.getFirstName());
        this.setMiddleName(account.getMiddleName());
        this.setLastName(account.getLastName());
        this.setEmail(account.getEmail());
        this.setAccessLevel(account.getAccessLevel());
    }
}
