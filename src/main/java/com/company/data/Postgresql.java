package com.company.data;

import com.company.common.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    @Override //TODO What does this method do? Is it used anywhere, and what should it check?
    public boolean checkAccess() {
        return false;
    }

    @Override
    public IProducer[] searchProducers(String word) {
        return postgresProducer.searchProducers(word);
    }

    @Override
    public IProducer[] getProducers(Integer limit, Integer offset) {
        return postgresProducer.getProducers(limit, offset);
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
    public IProduction[] searchProductions(String word) {
        return postgresProduction.searchProductions(word);
    }

    @Override
    public IProduction[] getProductions(Integer limit, Integer offset) {
        return postgresProduction.getProductions(limit, offset);
    }

    @Override
    public IProduction[] getProductionByCredit(ICredit credit) {
        return postgresProduction.getProductionByCredit(credit);
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
    public ICredit[] searchCredits(String word) {
        return postgresCredit.searchCredits(word);
    }

    @Override
    public ICredit[] getCredits(Integer limit, Integer offset) {
        return postgresCredit.getCredits(limit, offset);
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
    public boolean deleteCredit(Integer id) {
        return postgresCredit.deleteCredit(id);
    }

    @Override
    public ICreditGroup[] searchCreditGroups(String word) {
        return postgresCreditGroup.searchCreditGroups(word);
    }

    @Override
    public ICreditGroup[] getCreditGroups(Integer limit, Integer offset) {
        return postgresCreditGroup.getCreditGroups(limit, offset);
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
    public IAccount[] searchAccounts(String word) {
        return postgresAccount.searchAccounts(word);
    }

    @Override
    public IAccount[] getAccounts(Integer limit, Integer offset) {
        return postgresAccount.getAccounts(limit, offset);
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
}
