package com.company.data;

import com.company.common.*;
import com.company.data.mapper.*;
import javafx.util.Pair;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Finds all credits belonging to the given production, and creates a Credit object to each one.
     * Then finds out what each credit is credited for (Its' CreditGroup(s)), creating CreditGroup objects
     * and attaches them to each credit.
     *
     * @param production A Production with at least an ID. The Production to be given credits from the database.
     * @throws SQLException
     */
    private void attachCreditsToProduction(Production production) throws SQLException {
        //Find all the credits that are in this production.
        PreparedStatement queryProductionCreditIDs = connection.prepareStatement(
                "SELECT credit_id FROM production_credit_person_relation WHERE production_id = ?");
        queryProductionCreditIDs.setInt(1, production.getID());
        ResultSet resultProductionCreditIDs = queryProductionCreditIDs.executeQuery();

        while (resultProductionCreditIDs.next()) {
            production.setCredit(this.getCredit(resultProductionCreditIDs.getInt("credit_id"), CreditType.PERSON));
        }

        //Attach each credit with its credit group id for this production.
        PreparedStatement queryCreditGroupID = connection.prepareStatement(
                "SELECT credit_id, credit_group_id FROM production_credit_person_relation WHERE production_id = ?");
        queryCreditGroupID.setInt(1, production.getID());
        ResultSet creditIdCreditGroupPairs = queryCreditGroupID.executeQuery();

        HashMap<Integer, List<Integer>> ccgIdPair = new HashMap<>();
        while (creditIdCreditGroupPairs.next()) {
            int cid = creditIdCreditGroupPairs.getInt("credit_id");
            int cGroupId = creditIdCreditGroupPairs.getInt("credit_group_id");
            ccgIdPair.computeIfAbsent(cid, k -> new ArrayList<>()); //If the List is null, initialize one.
            ccgIdPair.get(cid).add(cGroupId);
        }

        //TODO this pull is faster up until the credit_group table becomes too large.
        PreparedStatement queryCreditGroup = connection.prepareStatement("SELECT * FROM credit_group");
        ResultSet creditGroupResults = queryCreditGroup.executeQuery();

        //Save all CreditGroups in a map
        HashMap<Integer, CreditGroup> idToCreditGroup = new HashMap<>();
        while (creditGroupResults.next()) {
            CreditGroup cg = CreditGroup.createFromQueryResult(creditGroupResults);
            idToCreditGroup.put(cg.getID(), cg);
        }

        //for each credit, find all its credit groups and assign them to the credit.
        for(ICredit c : production.getCredits()) {
            for(int creditGroupID : ccgIdPair.get(c.getID())) {         //Iterate over all the IDs of the CreditGroup, of which the credit is assigned.
                CreditGroup cg = idToCreditGroup.get(creditGroupID);    //Get the CreditGroup matching that id
                ((Credit) c).addCreditGroup(cg);                        //Assign the credit that CreditGroup.
            }
        }
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
                Producer producer = Producer.createFromQueryResult(queryResult);
                producer.setAccount(getAccount(queryResult.getInt("account_id")));
                producers.add(producer);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
                producer = Producer.createFromQueryResult(queryResult);
                producer.setAccount(getAccount(queryResult.getInt("account_id")));
            } else {
                throw (new Exception("Production with id \"" + id + "\" not found"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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

                //TODO re-evaluate weather we NEED to attach credits to ALL productions when we fetch them.
                /*Pros: the "credits" field is not null
                        We have all the data available once pulled.
                  Cons: We almost pull ALL data out of the database - this notably slows down the program.
                        We dont currently save and use references to the saved data - its more: pull, use, toss.
                */
                //attachCreditsToProduction(production);

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
            queryResult.next(); //TODO Maybe check to see for more than 1 results and notify of database corruption/duplication.
            production = Production.createFromQueryResult(queryResult);
            production.setProducer(this.getProducer(queryResult.getInt("producer_id")));
            attachCreditsToProduction(production);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return production;
    }

    @Override
    public IProduction addProduction(IProduction production) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO production (name, release_day, release_month, release_year, description, image, producer_id) VALUES (?,?,?,?,?,?,?)");
            setArguments(production, query);
            query.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_unit");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                int id = queryResult.getInt("id");
                Credit credit = (Credit)credits.get(id);
                credit.setName(queryResult.getString("name"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM credit_unit WHERE id = ?");
            query.setInt(1, id);
            ResultSet queryResult = query.executeQuery();
            if(queryResult.next())
                credit.setName(queryResult.getString("name"));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return credit;
    }

    @Override
    public Map<ICreditGroup, List<IProduction>> getCreditedFor(ICredit credit) {
        Map<ICreditGroup, List<IProduction>> creditedFor = new HashMap<>();

        try { //TODO this whole thing could be done more efficiently if I could figure out how to do it in SQL
            PreparedStatement query = connection.prepareStatement(
                    "SELECT credit_group_id, production_id FROM production_credit_person_relation WHERE credit_id = ?");
            query.setInt(1, credit.getID());
            ResultSet queryResult = query.executeQuery();

            Map<Integer, List<Integer>> idMap = new HashMap<>();
            while (queryResult.next()) {
                int credit_group_id = queryResult.getInt("credit_group_id");
                int production_id = queryResult.getInt("production_id");
                idMap.computeIfAbsent(credit_group_id, k -> new ArrayList<>()); //If the List is null, initialize one.
                idMap.get(credit_group_id).add(production_id);
            }

            List<ICreditGroup> creditGroups = Arrays.stream(getCreditGroups()).collect(Collectors.toList());
            List<IProduction> productions = Arrays.stream(getProductions()).collect(Collectors.toList());


            for (Map.Entry<Integer, List<Integer>> pair : idMap.entrySet()) {
                List<IProduction> productionList = productions.stream()
                        .filter(iProduction -> pair.getValue().contains(iProduction.getID()))
                        .collect(Collectors.toList());
                ICreditGroup cg = creditGroups.stream()
                        .filter(iCreditGroup -> iCreditGroup.getID() == pair.getKey())
                        .findFirst()
                        .get();
                creditedFor.put(cg, productionList);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return creditedFor;
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
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        if (credit.getType() == CreditType.UNIT) {
            try {
                PreparedStatement query = connection.prepareStatement("INSERT INTO credit_unit(name) VALUES (?)");
                query.setString(1, credit.getName());
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
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

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        if (credit.getType() == CreditType.UNIT) {
            try {
                PreparedStatement query = connection.prepareStatement("UPDATE credit_unit SET name=?");
                query.setString(1, credit.getName());
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
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
                CreditGroup creditGroup = CreditGroup.createFromQueryResult(queryResult);
                creditGroups.add(creditGroup);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
            creditGroup = CreditGroup.createFromQueryResult(queryResult);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public IAccount[] getAccounts() {
        List<IAccount> accounts = new ArrayList<>();
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM account");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                Account account = Account.createFromQueryResult(queryResult);
                accounts.add(account);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
                account = Account.createFromQueryResult(queryResult);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
                account = Account.createFromQueryResult(queryResult);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
                account = Account.createFromQueryResult(queryResult);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
