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
    public static Account createFromQueryResult(ResultSet queryResult) throws SQLException {
        Account account = new Account();
        account.setID(queryResult.getInt("id"));
        account.setFirstName(queryResult.getString("f_name"));
        account.setLastName(queryResult.getString("l_name"));
        account.setEmail(queryResult.getString("email"));
        for (AccessLevel accessLevel : AccessLevel.values()) {
            if (accessLevel.equals(queryResult.getInt("access_level"))) {
                account.setAccessLevel(accessLevel);
            }
        }
        return account;
    }

    public IAccount[] getAccounts(Integer limit, Integer offset) {
        List<IAccount> accounts = new ArrayList<IAccount>();

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM account LIMIT ? OFFSET ?");
            query.setInt(1, limit);
            query.setInt(2, offset);

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                accounts.add(createFromQueryResult(queryResult));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return accounts.toArray(new IAccount[0]);
    }

    public IAccount getAccount(Integer id) {
        Account account = null;

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
        Account account = null;

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM account WHERE email = ?");
            query.setString(1, email);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                account = createFromQueryResult(queryResult);
            } // TODO: throw exception instead of return null
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return account;
    }

    public IAccount getAccount(String email, String hashedPassword) {
        Account account = null;

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM account WHERE email = ? AND password =?");
            query.setString(1, email);
            query.setString(2, hashedPassword);

            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                account = createFromQueryResult(queryResult);
            } // TODO: throw exception instead of return null
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return account;
    }

    public IAccount addAccount(IAccount account, String hashedPassword) {
        Account createdAccount = null;

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("INSERT INTO account (f_name, l_name, email, access_level, password) VALUES (?,?,?,?,?) RETURNING *");
            query.setString(1, account.getFirstName());
            query.setString(2, account.getLastName());
            query.setString(3, account.getEmail());
            query.setInt(4, account.getAccessLevel().getLevel());
            query.setString(5, hashedPassword);

            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                createdAccount = createFromQueryResult(queryResult);
            } // TODO: throw exception instead of return null
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return createdAccount;
    }

    public void updateAccount(IAccount account) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("UPDATE account SET f_name =?, l_name=?, email=?, access_level=? WHERE id = ?");
            query.setString(1, account.getFirstName());
            query.setString(2, account.getLastName());
            query.setString(3, account.getEmail());
            query.setInt(4, account.getAccessLevel().getLevel());
            query.setInt(5, account.getID());

            query.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void updateAccount(IAccount account, String hashedPassword) {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("UPDATE account SET f_name =?, l_name=?, email=?, access_level=?, password=? WHERE id=?");
            query.setString(1, account.getFirstName());
            query.setString(2, account.getLastName());
            query.setString(3, account.getEmail());
            query.setInt(4, account.getAccessLevel().getLevel());
            query.setString(5, hashedPassword);
            query.setInt(6, account.getID());

            query.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public IAccount[] searchAccounts(String word) {
        List<IAccount> producers = new ArrayList<>();

        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT * FROM account WHERE LOWER(f_name) LIKE ? OR LOWER(l_name) LIKE ? OR LOWER(email) LIKE ?");
            query.setString(1, "%" + word.toLowerCase() + "%");
            query.setString(2, "%" + word.toLowerCase() + "%");
            query.setString(3, "%" + word.toLowerCase() + "%");

            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                producers.add(createFromQueryResult(queryResult));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return producers.toArray(new IAccount[0]);
    }

    public Integer countAccounts() {
        try {
            PreparedStatement query = Postgresql.connection.prepareStatement("SELECT count(id) FROM account");
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                return queryResult.getInt("count");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }
}