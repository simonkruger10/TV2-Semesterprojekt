package com.company.domain;

import com.company.common.AccessLevel;
import com.company.common.IAccount;

public class AccountDTO extends PersonDTO implements IAccount {
    private String email = null;
    private String hashedPassword = null;
    private AccessLevel accessLevel = AccessLevel.GUEST;

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getPassword() {
        return hashedPassword;
    }

    public void setPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void copyAccount(IAccount account) {
        assert account != null;

        this.setUUID(account.getUUID());
        this.setFirstName(account.getFirstName());
        this.setMiddleName(account.getMiddleName());
        this.setLastName(account.getLastName());
        this.setEmail(account.getEmail());
        this.setAccessLevel(account.getAccessLevel());
    }
}
