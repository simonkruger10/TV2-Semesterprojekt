package com.company.gui;

import com.company.common.AccessLevel;
import com.company.common.IAccount;
import com.company.domain.PersonDTO;

public class AccountGTO extends PersonDTO implements IAccount {
    private String email = null;
    private AccessLevel accessLevel = AccessLevel.GUEST;

    AccountGTO() {}

    AccountGTO(IAccount account) {
        setCopyOf(account);
    }

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

    public void setCopyOf(IAccount account) {
        assert account != null;

        this.setUUID(account.getUUID());
        this.setFirstName(account.getFirstName());
        this.setMiddleName(account.getMiddleName());
        this.setLastName(account.getLastName());
        this.setEmail(account.getEmail());
        this.setAccessLevel(account.getAccessLevel());
    }
}
