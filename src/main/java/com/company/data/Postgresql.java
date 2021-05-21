package com.company.data;

import com.company.common.*;
import com.company.presentation.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Postgresql implements DatabaseFacade {
    private static Connection connection = null;

    public Postgresql() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tv2_semesterprojekt_f3f70b5a", "tv2_dbuser_f3f70b5a", "4A03069D");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCredit(ResultSet queryResult, Credit credit) throws SQLException {
        credit.setFirstName(queryResult.getString("name"));
        credit.setMiddleName(queryResult.getString("m_name"));
        credit.setLastName(queryResult.getString("l_name"));
        String image = queryResult.getString("image");
        if (image != null) {
            credit.setImage(credit.getImage());
        }
        credit.setEmail(queryResult.getString("email"));
    }

    private void creditQuery(Production production, PreparedStatement query) throws SQLException {
        PreparedStatement query2 = connection.prepareStatement("SELECT credit_id FROM production_credit_person_relation WHERE production_id = ?");
        query2.setInt(1, production.getID());
        ResultSet queryResult2 = query.executeQuery();
        while (queryResult2.next()) {
            production.setCredit(this.getCredit(queryResult2.getInt("credit_id"), CreditType.PERSON));
        }

        PreparedStatement query3 = connection.prepareStatement("SELECT credit_id FROM production_credit_person_relation WHERE production_id = ?");
        query3.setInt(1, production.getID());
        ResultSet queryResult3 = query.executeQuery();
        while (queryResult2.next()) {
            production.setCredit(this.getCredit(queryResult3.getInt("credit_id"), CreditType.UNIT));
        }
    }

    private void setProduction(IProduction production, PreparedStatement query) throws SQLException {
        query.setString(1, production.getName());
        query.setInt(2, production.getReleaseDay());
        query.setInt(3, production.getReleaseMonth());
        query.setInt(4, production.getReleaseYear());
        query.setString(5, production.getDescription());
        query.setString(6, production.getImage());
        query.setString(7, String.valueOf(production.getProducer()));
    }

    @Override
    public boolean checkAccess() {
        return false;
    }

    @Override
    public IProducer[] getProducers() {
        List<IProducer> producers = new ArrayList<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM producer");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Producer producer = new Producer();
                producer.setName(queryResult.getString(2));
                producer.setLogo(queryResult.getString(3));
                producers.add(producer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return producers.toArray(new IProducer[0]);
    }

    @Override
    public IProducer getProducer(Integer id) {
        Producer producer = new Producer();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM producer WHERE id=?");
            ResultSet queryResult = query.executeQuery();
            query.setInt(1, id);
            producer.setName(queryResult.getString(2));
            producer.setLogo(queryResult.getString(3));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return producer;
    }

    @Override
    public IProducer addProducer(IProducer producer) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO producer (id, name, logo, account_id) VALUES (?,?,?,?)");
            query.setInt(1, producer.getID());
            query.setString(2, producer.getName());
            query.setString(3, producer.getLogo());
            query.setInt(4, producer.getAccount().getID());
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return producer;
    }

    @Override
    public void updateProducer(IProducer producer) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE producer SET name=?, logo=?");
            query.setString(1, producer.getName());
            query.setString(2, producer.getLogo());
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public IProduction[] getProductions() {
        List<IProduction> productions = new ArrayList<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM production");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Production production = new Production();
                production.setID(queryResult.getInt("id"));
                production.setName(queryResult.getString("name"));
                production.setDescription(queryResult.getString("description"));
                String image = queryResult.getString("Image");
                if (image != null) {
                    production.setImage(production.getImage());
                }
                production.setReleaseDay(queryResult.getInt("release_day"));
                production.setReleaseMonth(queryResult.getInt("release_month"));
                production.setReleaseYear(queryResult.getInt("release_year"));
                production.setProducer(this.getProducer(queryResult.getInt("producer_id")));

                creditQuery(production, query);
                productions.add(production);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return productions.toArray(new IProduction[0]);
    }

    @Override
    public IProduction getProduction(Integer id) {
        Production production = new Production();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM production WHERE ID =?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            production.setName(queryResult.getString("name"));
            production.setDescription(queryResult.getString("description"));
            production.setReleaseDay(queryResult.getInt("release_day"));
            production.setReleaseMonth(queryResult.getInt("release_month"));
            production.setReleaseYear(queryResult.getInt("release_year"));
            String image = queryResult.getString("image");
            if (image != null) {
                production.setImage(production.getImage());
            }
            creditQuery(production, query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return production;
    }

    @Override
    public IProduction addProduction(IProduction production) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO production (name, release_day, release_month, release_year, description, image, producer) VALUES (?,?,?,?,?,?,?)");
            setProduction(production, query);
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return production;
    }

    @Override
    public void updateProduction(IProduction production) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE production SET name =?, release_day =?, release_month =?, release_year =?, description =?, image=?, producer =? WHERE ID=?");
            query.setInt(8, production.getID());
            setProduction(production, query);
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public ICredit[] getCredits() {
        List<ICredit> credits = new ArrayList<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_person");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Credit credit = new Credit();
                credit.setID(queryResult.getInt("id"));
                setCredit(queryResult, credit);
                credits.add(credit);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_unit");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Credit credit = new Credit();
                credit.setID(queryResult.getInt("id"));
                credit.setName(queryResult.getString("name"));
                credits.add(credit);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return credits.toArray((new ICredit[0]));
    }

    @Override
    public ICredit getCredit(Integer id, CreditType type) {
        Credit credit = new Credit();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_person WHERE =?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            setCredit(queryResult, credit);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_unit WHERE =?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            credit.setName(queryResult.getString("name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return credit;
    }

    @Override
    public ICredit addCredit(ICredit credit) {
        if (credit.getType() == CreditType.PERSON) {
            try {
                PreparedStatement query = connection.prepareStatement("INSERT INTO credit_person(name, m_name, l_name, images, email) VALUES (?,?,?,?,?)");
                query.setString(1, credit.getFirstName());
                query.setString(2, credit.getMiddleName());
                query.setString(3, credit.getLastName());
                query.setString(4, credit.getImage());
                query.setString(5, credit.getEmail());
                query.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (credit.getType() == CreditType.UNIT) {
            try {
                PreparedStatement query = connection.prepareStatement("INSERT INTO credit_unit(name) VALUES (?)");
                query.setString(1, credit.getName());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return credit;
    }

    @Override
    public void updateCredit(ICredit credit) {
        if (credit.getType() == CreditType.PERSON) try {
            PreparedStatement query = connection.prepareStatement("UPDATE credit_person SET name =?, m_name=?, l_name=?, image=?, email=?,");
            query.setString(1, credit.getFirstName());
            query.setString(2, credit.getMiddleName());
            query.setString(3, credit.getLastName());
            query.setString(4, credit.getImage());
            query.setString(5, credit.getEmail());
            query.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (credit.getType() == CreditType.UNIT) {
            try {
                PreparedStatement query = connection.prepareStatement("UPDATE credit_unit SET name=?");
                query.setString(1, credit.getName());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }


    }

    @Override
    public ICreditGroup[] getCreditGroups() {
        List<ICreditGroup> creditGroups = new ArrayList<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_group");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                CreditGroup creditGroup = new CreditGroup();
                creditGroup.setName(queryResult.getString("name"));
                creditGroup.setDescription(queryResult.getString("description"));
                creditGroups.add(creditGroup);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return creditGroups.toArray(new ICreditGroup[0]);
    }

    @Override
    public ICreditGroup getCreditGroup(Integer id) {
        CreditGroup creditGroup = new CreditGroup();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_group WHERE id = ?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            creditGroup.setName(queryResult.getString("name"));
            creditGroup.setDescription(queryResult.getString("description"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return creditGroup;
    }

    @Override
    public ICreditGroup addCreditGroup(ICreditGroup creditGroup) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO credit_group (id, name, description) VALUES (?, ?, ?)");
            query.setInt(1, creditGroup.getID());
            query.setString(2, creditGroup.getName());
            query.setString(3, creditGroup.getDescription());
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return creditGroup;
    }

    @Override
    public void updateCreditGroup(ICreditGroup creditGroup) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE credit_group SET name = ?, description = ? WHERE id=?;");
            query.setInt(3, creditGroup.getID());
            query.setString(1, creditGroup.getName());
            query.setString(2, creditGroup.getDescription());
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public IAccount[] getAccounts() {
        List<IAccount> accounts = new ArrayList<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM account");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
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
                accounts.add(account);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return accounts.toArray(new IAccount[0]);
    }

    @Override
    public IAccount getAccount(Integer id) {
        Account account = new Account();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM account WHERE id = ?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                account.setFirstName(queryResult.getString(2));
                account.setMiddleName(queryResult.getString(3));
                account.setLastName(queryResult.getString(4));
                account.setEmail(queryResult.getString(5));
                for (AccessLevel accessLevel : AccessLevel.values()) {
                    if (accessLevel.equals(queryResult.getInt(6))) {
                        account.setAccessLevel(accessLevel);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return account;
    }

    @Override
    public IAccount getAccount(String email) {
        Account account = new Account();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM account WHERE email = ?");
            query.setString(1, email);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                account.setFirstName(queryResult.getString(2));
                account.setMiddleName(queryResult.getString(3));
                account.setLastName(queryResult.getString(4));
                account.setEmail(queryResult.getString(5));
                for (AccessLevel accessLevel : AccessLevel.values()) {
                    if (accessLevel.equals(queryResult.getInt(6))) {
                        account.setAccessLevel(accessLevel);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return account;
    }

    @Override
    public IAccount getAccount(String email, String hashedPassword) {
        Account account = new Account();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM account WHERE email = ? AND password =?");
            query.setString(1, email);
            query.setString(2, hashedPassword);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                account.setFirstName(queryResult.getString(2));
                account.setMiddleName(queryResult.getString(3));
                account.setLastName(queryResult.getString(4));
                account.setEmail(queryResult.getString(5));
                for (AccessLevel accessLevel : AccessLevel.values()) {
                    if (accessLevel.equals(queryResult.getInt(6))) {
                        account.setAccessLevel(accessLevel);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return account;
    }

    @Override
    public IAccount addAccount(IAccount account, String hashedPassword) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO account (id, f_name, m_name, l_name, email, access_level, password) VALUES (?,?,?,?,?,?,?)");
            query.setInt(1, account.getID());
            query.setString(2, account.getFirstName());
            query.setString(3, account.getMiddleName());
            query.setString(4, account.getLastName());
            query.setString(5, account.getEmail());
            query.setString(6, String.valueOf(account.getAccessLevel()));
            query.setString(7, hashedPassword);
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return account;
    }

    @Override
    public void updateAccount(IAccount account) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE account SET f_name =?, m_name=?, l_name=?, email=?, access_level=?");
            query.setString(1, account.getFirstName());
            query.setString(2, account.getMiddleName());
            query.setString(3, account.getLastName());
            query.setString(4, account.getEmail());
            query.setInt(5, account.getAccessLevel().getLevel());
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateAccount(IAccount account, String hashedPassword) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE account SET f_name =?, m_name=?, l_name=?, email=?, access_level=?,password=?");
            query.setString(1, account.getFirstName());
            query.setString(2, account.getMiddleName());
            query.setString(3, account.getLastName());
            query.setString(4, account.getEmail());
            query.setInt(5, account.getAccessLevel().getLevel());
            query.setString(6, hashedPassword);
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
