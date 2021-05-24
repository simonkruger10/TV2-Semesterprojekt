package com.company.data.mapper;

import com.company.common.AccessLevel;
import com.company.common.IAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Account extends Person implements IAccount {
    private AccessLevel accessLevel = null;

    public Account() {
    }

    public Account(IAccount account) {
        this.setCopyOf(account);
    }

    public static Account createFromQueryResult(ResultSet queryResult) throws SQLException {
        Account account = new Account();
        account.setFirstName(queryResult.getString("f_name"));
        account.setMiddleName(queryResult.getString("m_name"));
        account.setLastName(queryResult.getString("l_name"));
        account.setEmail(queryResult.getString("email"));
        for (AccessLevel accessLevel : AccessLevel.values()) {
            if (accessLevel.equals(queryResult.getInt("access_level"))) {
                account.setAccessLevel(accessLevel);
            }
        }
        return account;
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

        this.setFirstName(account.getFirstName());
        this.setMiddleName(account.getMiddleName());
        this.setLastName(account.getLastName());
        this.setEmail(account.getEmail());
        this.setAccessLevel(account.getAccessLevel());
    }
}
