package com.company.data;

import com.company.common.AccessLevel;
import com.company.common.IAccount;

public class AccountEntity extends PersonEntity implements IAccount {
    private String email = null;
    private String hashedPassword = null;
    private AccessLevel accessLevel = null;

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


    public void setCopyOf(IAccount account) {
        assert account != null;

        this.setFirstName(account.getFirstName());
        this.setMiddleName(account.getMiddleName());
        this.setLastName(account.getLastName());
        this.setEmail(account.getEmail());
        this.setAccessLevel(account.getAccessLevel());
    }

    public String toJsonString() {
        return "{" +
                "\"_uuid\":\"" + getUUID() + "\"," +
                "\"firstName\":\"" + getFirstName() + "\"," +
                "\"middleName\":\"" + getMiddleName() + "\"," +
                "\"lastName\":\"" + getLastName() + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"hashedPassword\":\"" + hashedPassword + "\"," +
                "\"accessLevel\":\"" + accessLevel + "\"" +
                '}';
    }
}
