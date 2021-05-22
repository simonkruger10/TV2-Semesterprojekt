package com.company.data;

import com.company.common.*;
import com.company.presentation.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Postgresql implements DatabaseFacade {
    private static Connection connection = null;

    public Postgresql() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tv2_semesterprojekt_f3f70b5a",
                    "tv2_dbuser_f3f70b5a", "4A03069D");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void attatchCreditsToProduction(Production production) throws SQLException {
        PreparedStatement queryGetCreditIdsMatchingProductionId = connection.prepareStatement(
                "SELECT credit_id FROM production_credit_person_relation WHERE production_id = ?");
        queryGetCreditIdsMatchingProductionId.setInt(1, production.getID());
        ResultSet resultCreditIdsMatchingProductionId = queryGetCreditIdsMatchingProductionId.executeQuery();

        while (resultCreditIdsMatchingProductionId.next()) {
            production.setCredit(this.getCredit(resultCreditIdsMatchingProductionId.getInt("credit_id"), CreditType.PERSON));
        }

        /* //TODO I think the following code is an exact duplicate of the above code,
              //and I can't find a meaning with it. Also nothing seems to break when I remove it.

        PreparedStatement query3 = connection.prepareStatement("SELECT credit_id FROM production_credit_person_relation WHERE production_id = ?");
        query3.setInt(1, production.getID());
        ResultSet queryResult3 = query3.executeQuery();

        while (queryResult3.next()) {
            production.setCredit(this.getCredit(queryResult3.getInt("credit_id"), CreditType.UNIT));
        }*/

        /*
        System.out.println("Test");

        for (ICredit c : production.getCredits()) {
            System.out.println("Credit_ID: " + c.getID() + ", \t Credit_Name: " + c.getName());
        }
        */
    }

    /**
     * Sets the values of the given query's arguments to the value of the given production's fields' values
     * @param production Production which will be saved or updated
     * @param query The query which saves or updates a Production in the database. The Query's arguments must be:
     *              name, release_day, release_month, release_year, description, image, producer_id
     * @throws SQLException
     */
    private void setArguments(IProduction production, PreparedStatement query) throws SQLException {
        //Argument order: name, release_day, release_month, release_year, description, image, producer_id
        query.setString(1, production.getName());
        query.setInt(2, production.getReleaseDay());
        query.setInt(3, production.getReleaseMonth());
        query.setInt(4, production.getReleaseYear());
        query.setString(5, production.getDescription());
        query.setString(6, production.getImage());
        query.setString(7, String.valueOf(production.getProducer()));
    }

    @Override //TODO What does this method do? Is it used anywhere, and what should it check?
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
                producer.setID(queryResult.getInt("id"));
                producer.setName(queryResult.getString("name"));
                producer.setLogo(queryResult.getString("logo"));
                producer.setAccount(getAccount(queryResult.getInt("account_id")));
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
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            if (queryResult.next()) {
                producer.setID(id);
                producer.setName(queryResult.getString("name"));
                producer.setLogo(queryResult.getString("logo"));
                producer.setAccount(getAccount(queryResult.getInt("account_id")));
            } else {
                throw (new Exception("Production with id \"" + id + "\" not found"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * Given a IProducer object that already exists in the database (has an ID),
     * will update all (except ID) of the stored values to match the values of the given object
     * @param producer update database values of producer with @param's ID, to the value of @param's values.
     */
    @Override
    public void updateProducer(IProducer producer) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE producer SET name=?, logo=?, account_id=? WHERE id = ?");
            query.setString(1, producer.getName());
            query.setString(2, producer.getLogo());
            query.setInt(3, producer.getAccount().getID());
            query.setInt(4, producer.getID());
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
                Production production = Production.createFromQueryResult(queryResult);
                production.setProducer(this.getProducer(queryResult.getInt("producer_id")));

                attatchCreditsToProduction(production);
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
            queryResult.next(); //TODO Maybe check to see for multiple results and notify of database corruption.
            production = Production.createFromQueryResult(queryResult);
            production.setProducer(this.getProducer(queryResult.getInt("producer_id")));
            attatchCreditsToProduction(production);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return production;
    }

    @Override
    public IProduction addProduction(IProduction production) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO production (name, release_day, release_month, release_year, description, image, producer_id) VALUES (?,?,?,?,?,?,?)");
            setArguments(production, query);
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return production;
    }

    @Override
    public void updateProduction(IProduction production) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE production SET name =?, release_day =?, release_month =?, release_year =?, description =?, image=?, producer_id =? WHERE ID=?");
            query.setInt(8, production.getID());
            setArguments(production, query);
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public ICredit[] getCredits() {
        HashMap<Integer, ICredit> credits = new HashMap<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_person");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Credit credit = Credit.createFromQueryResult(queryResult, CreditType.PERSON);
                credits.put(credit.getID(), credit);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_unit");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                int id = queryResult.getInt("id");
                Credit credit = (Credit)credits.get(id);
                credit.setName(queryResult.getString("name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return credits.values().toArray((new ICredit[0]));
    }

    @Override
    public ICredit getCredit(Integer id, CreditType type) {
        Credit credit = new Credit();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_person WHERE id = ?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            if(queryResult.next())
                credit = Credit.createFromQueryResult(queryResult, CreditType.PERSON);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_unit WHERE id = ?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            if(queryResult.next())
                credit.setName(queryResult.getString("name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return credit;
    }

    @Override //TODO I am not sure this works, and it needs to be tested!
    public ICredit addCredit(ICredit credit) {
        if (credit.getType() == CreditType.PERSON) {
            try {
                PreparedStatement query = connection.prepareStatement("INSERT INTO credit_person(name, m_name, l_name, image, email) VALUES (?,?,?,?,?)");
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

    @Override //TODO I am not sure this works, and it needs to be tested!
    public void updateCredit(ICredit credit) {
        if (credit.getType() == CreditType.PERSON) try {
            PreparedStatement query = connection.prepareStatement("UPDATE credit_person SET name =?, m_name=?, l_name=?, image=?, email=?");
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
                creditGroup.setID(queryResult.getInt("id"));
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
            creditGroup.setID(queryResult.getInt("id"));
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
                account.setFirstName(queryResult.getString("f_name"));
                account.setMiddleName(queryResult.getString("m_name"));
                account.setLastName(queryResult.getString("l_name"));
                account.setEmail(queryResult.getString("email"));
                for (AccessLevel accessLevel : AccessLevel.values()) { //TODO WHY!? WHY are we iterating this? What purpose does it serve?
                    if (accessLevel.equals(queryResult.getInt("access_level"))) {
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
                account.setFirstName(queryResult.getString("f_name"));
                account.setMiddleName(queryResult.getString("m_name"));
                account.setLastName(queryResult.getString("l_name"));
                account.setEmail(queryResult.getString("email"));
                for (AccessLevel accessLevel : AccessLevel.values()) { //TODO WHY!? WHY are we iterating this? What purpose does it serve?
                    if (accessLevel.equals(queryResult.getInt("access_level"))) {
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
                account.setFirstName(queryResult.getString("f_name"));
                account.setMiddleName(queryResult.getString("m_name"));
                account.setLastName(queryResult.getString("l_name"));
                account.setEmail(queryResult.getString("email"));
                for (AccessLevel accessLevel : AccessLevel.values()) { //TODO WHY!? WHY are we iterating this? What purpose does it serve?
                    if (accessLevel.equals(queryResult.getInt("access_level"))) {
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
            PreparedStatement query = connection.prepareStatement("UPDATE account SET f_name =?, m_name=?, l_name=?, email=?, access_level=? WHERE id = ?");
            query.setString(1, account.getFirstName());
            query.setString(2, account.getMiddleName());
            query.setString(3, account.getLastName());
            query.setString(4, account.getEmail());
            query.setInt(5, account.getAccessLevel().getLevel());
            query.setInt(6, account.getID());
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateAccount(IAccount account, String hashedPassword) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE account SET f_name =?, m_name=?, l_name=?, email=?, access_level=?,password=? WHERE id=?");
            query.setString(1, account.getFirstName());
            query.setString(2, account.getMiddleName());
            query.setString(3, account.getLastName());
            query.setString(4, account.getEmail());
            query.setInt(5, account.getAccessLevel().getLevel());
            query.setString(6, hashedPassword);
            query.setInt(7, account.getID());
            query.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
