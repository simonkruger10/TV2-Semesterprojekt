package com.company.data;

import com.company.common.AccessLevel;
import com.company.common.IAccount;
import com.company.data.entities.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresAccount {
    public PostgresAccount() {
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

    public IAccount[] getAccounts() {
        List<IAccount> accounts = new ArrayList<IAccount>();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM account");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Account account = createFromQueryResult(queryResult);
                accounts.add(account);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return accounts.toArray(new IAccount[0]);
    }

    public IAccount getAccount(Integer id) {
        Account account = new Account();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM account WHERE id = ?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                account = createFromQueryResult(queryResult);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return account;
    }

    public IAccount getAccount(String email) {
        Account account = new Account();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM account WHERE email = ?");
            query.setString(1, email);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                account = createFromQueryResult(queryResult);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return account;
    }

    public IAccount getAccount(String email, String hashedPassword) {
        Account account = new Account();
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM account WHERE email = ? AND password =?");
            query.setString(1, email);
            query.setString(2, hashedPassword);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                account = createFromQueryResult(queryResult);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return account;
    }

    public IAccount addAccount(IAccount account, String hashedPassword) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("INSERT INTO account (id, f_name, m_name, l_name, email, access_level, password) VALUES (?,?,?,?,?,?,?)");
            query.setInt(1, account.getID());
            query.setString(2, account.getFirstName());
            query.setString(3, account.getMiddleName());
            query.setString(4, account.getLastName());
            query.setString(5, account.getEmail());
            query.setString(6, String.valueOf(account.getAccessLevel()));
            query.setString(7, hashedPassword);
            query.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return account;
    }

    public void updateAccount(IAccount account) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("UPDATE account SET f_name =?, m_name=?, l_name=?, email=?, access_level=? WHERE id = ?");
            query.setString(1, account.getFirstName());
            query.setString(2, account.getMiddleName());
            query.setString(3, account.getLastName());
            query.setString(4, account.getEmail());
            query.setInt(5, account.getAccessLevel().getLevel());
            query.setInt(6, account.getID());
            query.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void updateAccount(IAccount account, String hashedPassword) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("UPDATE account SET f_name =?, m_name=?, l_name=?, email=?, access_level=?,password=? WHERE id=?");
            query.setString(1, account.getFirstName());
            query.setString(2, account.getMiddleName());
            query.setString(3, account.getLastName());
            query.setString(4, account.getEmail());
            query.setInt(5, account.getAccessLevel().getLevel());
            query.setString(6, hashedPassword);
            query.setInt(7, account.getID());
            query.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}