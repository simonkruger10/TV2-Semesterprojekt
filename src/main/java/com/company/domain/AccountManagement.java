package com.company.domain;

import com.company.common.IAccount;
import com.company.common.IAccessLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountManagement implements IAccountManagement {
    private final List<AccountDTO> accounts = new ArrayList<>();

    @Override
    public IAccount create(String firstName, String email) {
        AccountDTO account = new AccountDTO(firstName, email);
        accounts.add(account);
        return account;
    }

    @Override
    public IAccount create(String firstName, String email, IAccessLevel accessLevel) {
        AccountDTO account = new AccountDTO(firstName, email, (AccessLevel) accessLevel);
        accounts.add(account);
        return account;
    }

    @Override
    public IAccount create(String firstName, String lastName, String email) {
        AccountDTO account = new AccountDTO(firstName, lastName, email);
        accounts.add(account);
        return account;
    }

    @Override
    public IAccount create(String firstName, String lastName, String email, IAccessLevel accessLevel) {
        AccountDTO account = new AccountDTO(firstName, lastName, email, (AccessLevel) accessLevel);
        accounts.add(account);
        return account;
    }

    @Override
    public IAccount create(String firstName, String middleName, String lastName, String email) {
        AccountDTO account = new AccountDTO(firstName, middleName, lastName, email);
        accounts.add(account);
        return account;
    }

    @Override
    public IAccount create(String firstName, String middleName, String lastName, String email,
                           IAccessLevel accessLevel) {
        AccountDTO account = new AccountDTO(firstName, middleName, lastName, email, (AccessLevel) accessLevel);
        accounts.add(account);
        return account;
    }

    @Override
    public IAccount[] search(String[] words) {
        final List<AccountDTO> result = new ArrayList<>();

        for (AccountDTO account: accounts) {
            for (String word: words) {
                if (account.getFullName().contains(word) || account.getEmail().contains(word)) {
                    result.add(account);
                    break;
                }
            }
        }

        return result.toArray(new IAccount[0]);
    }


    @Override
    public IAccount[] getByName(String firstName) {
        return getByName(firstName, null, null);
    }

    @Override
    public IAccount[] getByName(String firstName, String lastName) {
        return getByName(firstName, lastName, null);
    }

    @Override
    public IAccount[] getByName(String firstName, String middleName, String lastName) {
        final List<AccountDTO> result = new ArrayList<>();

        for (AccountDTO account : accounts) {
            if (Objects.equals(account.getFirstName(), firstName)
                    && (middleName == null || Objects.equals(account.getMiddleName(), middleName))
                    && (lastName == null || Objects.equals(account.getMiddleName(), lastName))) {
                result.add(account);
            }
        }

        return result.toArray(new IAccount[0]);
    }


    @Override
    public IAccount[] getByEmail(String email) {
        final List<AccountDTO> result = new ArrayList<>();

        for (AccountDTO account: accounts) {
            if (account.getEmail().equals(email)) {
                result.add(account);
            }
        }

        return result.toArray(new IAccount[0]);
    }
}
