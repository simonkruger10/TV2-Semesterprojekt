package com.company.data;

import com.company.common.*;
import com.company.data.mapper.*;

import java.sql.*;
import java.util.*;

public class Postgresql implements DatabaseFacade {
    protected static Connection connection = null;
    private final PostgresProducer postgresProducer = new PostgresProducer(this);
    private final PostgresProduction postgresProduction = new PostgresProduction(this);
    private final PostgresCredit postgresCredit = new PostgresCredit();
    private final PostgresCreditGroup postgresCreditGroup = new PostgresCreditGroup();
    private final PostgresAccount postgresAccount = new PostgresAccount();

    public Postgresql() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tv2_semesterprojekt_f3f70b5a",
                    "tv2_dbuser_f3f70b5a", "4A03069D");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<ICreditGroup, List<IProduction>> getCreditedFor(ICredit credit) {
        Map<ICreditGroup, List<IProduction>> creditedFor = new HashMap<>();
        try {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT production.*, credit_group.id as cg_id, credit_group.name as cg_name, " +
                            "credit_group.description as cg_description \n" +
                            "FROM production_credit_person_relation as pcpr\n" +
                            "JOIN production ON pcpr.production_id = production.id\n" +
                            "JOIN credit_group ON pcpr.credit_group_id = credit_group.id\n" +
                            "WHERE pcpr.credit_id = ?");
            query.setInt(1, credit.getID());
            ResultSet queryResult = query.executeQuery();

            while (queryResult.next()) {
                CreditGroup creditGroup = new CreditGroup();
                creditGroup.setDescription(queryResult.getString("cg_description"));
                creditGroup.setName(queryResult.getString("cg_name"));
                creditGroup.setID(queryResult.getInt("cg_id"));
                IProduction production = PostgresProduction.createFromQueryResult(queryResult);
                creditedFor.computeIfAbsent(creditGroup, k -> new ArrayList<>());
                creditedFor.get(creditGroup).add(production);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return creditedFor;
    }

    @Override //TODO What does this method do? Is it used anywhere, and what should it check?
    public boolean checkAccess() {
        return false;
    }

    @Override
    public IProducer[] getProducers() {
        return postgresProducer.getProducers();
    }

    @Override
    public IProducer getProducer(Integer id) {
        return postgresProducer.getProducer(id);
    }

    @Override
    public IProducer addProducer(IProducer producer) {
        return postgresProducer.addProducer(producer);
    }

    /**
     * Given a IProducer object that already exists in the database (has an ID),
     * will update all (except ID) of the stored values to match the values of the given object
     * @param producer update database values of producer with @param's ID, to the value of @param's values.
     */
    @Override
    public void updateProducer(IProducer producer) {
        postgresProducer.updateProducer(producer);
    }

    @Override
    public IProduction[] getProductions() {
        return postgresProduction.getProductions();
    }

    @Override
    public IProduction getProduction(Integer id) {
        return postgresProduction.getProduction(id);
    }

    @Override
    public IProduction addProduction(IProduction production) {
        return postgresProduction.addProduction(production);
    }

    @Override
    public void updateProduction(IProduction production) {

        postgresProduction.updateProduction(production);
    }

    @Override
    public ICredit[] getCredits() {
        return postgresCredit.getCredits();
    }

    @Override
    public ICredit getCredit(Integer id, CreditType type) {
        return postgresCredit.getCredit(id, type);
    }

    @Override
    public ICredit addCredit(ICredit credit) {
        return postgresCredit.addCredit(credit);
    }

    @Override
    public void updateCredit(ICredit credit) {
        postgresCredit.updateCredit(credit);
    }

    @Override
    public ICreditGroup[] getCreditGroups() {
        return postgresCreditGroup.getCreditGroups();
    }

    @Override
    public ICreditGroup getCreditGroup(Integer id) {
        return postgresCreditGroup.getCreditGroup(id);
    }

    @Override
    public ICreditGroup addCreditGroup(ICreditGroup creditGroup) {
        return postgresCreditGroup.addCreditGroup(creditGroup);
    }

    @Override
    public void updateCreditGroup(ICreditGroup creditGroup) {
        postgresCreditGroup.updateCreditGroup(creditGroup);
    }

    @Override
    public IAccount[] getAccounts() {
        return postgresAccount.getAccounts();
    }

    @Override
    public IAccount getAccount(Integer id) {
        return postgresAccount.getAccount(id);
    }

    @Override
    public IAccount getAccount(String email) {
        return postgresAccount.getAccount(email);
    }

    @Override
    public IAccount getAccount(String email, String hashedPassword) {
        return postgresAccount.getAccount(email, hashedPassword);
    }

    @Override
    public IAccount addAccount(IAccount account, String hashedPassword) {
        return postgresAccount.addAccount(account, hashedPassword);
    }

    @Override
    public void updateAccount(IAccount account) {
        postgresAccount.updateAccount(account);
    }

    @Override
    public void updateAccount(IAccount account, String hashedPassword) {
        postgresAccount.updateAccount(account, hashedPassword);
    }

    protected PostgresProducer getPostgresProducer() {
        return this.postgresProducer;
    }
}
