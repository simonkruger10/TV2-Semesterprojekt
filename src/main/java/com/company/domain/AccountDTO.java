package com.company.domain;

import com.company.common.IAccount;
import com.company.common.IAccessLevel;

public class AccountDTO extends PersonDTO implements IAccount {
    private String email;
    private AccessLevel accessLevel;

    public AccountDTO(String firstName, String email) {
        this(firstName, email, AccessLevel.CONSUMER);
    }

    public AccountDTO(String firstName, String email, AccessLevel accessLevel) {
        super(firstName);
        this.email = email;
        this.accessLevel = accessLevel;
    }

    public AccountDTO(String firstName, String lastName, String email) {
        this(firstName, lastName, email, AccessLevel.CONSUMER);
    }

    public AccountDTO(String firstName, String lastName, String email, AccessLevel accessLevel) {
        super(firstName, lastName);
        this.email = email;
        this.accessLevel = accessLevel;
    }

    public AccountDTO(String firstName, String middleName, String lastName, String email) {
        this(firstName, middleName, lastName, email, AccessLevel.CONSUMER);
    }

    public AccountDTO(String firstName, String middleName, String lastName, String email, AccessLevel accessLevel) {
        super(firstName, middleName, lastName);
        this.email = email;
        this.accessLevel = accessLevel;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public IAccessLevel getAccessLevel() {
        return accessLevel;
    }

    @Override
    public void setAccessLevel(IAccessLevel accessLevel) {
        this.accessLevel = (AccessLevel) accessLevel;
    }
}
