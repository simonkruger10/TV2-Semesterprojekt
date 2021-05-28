package com.company.data;

import com.company.common.*;

public class Database implements DatabaseFacade {
    private static DatabaseFacade instance;
    private final DatabaseFacade database;

    private Database() {
        this.database = new Postgresql();
    }

    public static DatabaseFacade getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    @Override
    public boolean checkAccess() {
        return this.database.checkAccess();
    }

    @Override
    public IProducer[] searchProducers(String word) {
        return this.database.searchProducers(word);
    }

    @Override
    public IProducer[] getProducers(Integer limit, Integer offset) {
        return this.database.getProducers(limit, offset);
    }

    @Override
    public IProducer getProducer(Integer id) {
        return this.database.getProducer(id);
    }

    @Override
    public IProducer addProducer(IProducer producer) {
        return this.database.addProducer(producer);
    }

    @Override
    public void updateProducer(IProducer producer) {
        this.database.updateProducer(producer);
    }

    @Override
    public IProduction[] searchProductions(String word) {
        return this.database.searchProductions(word);
    }

    @Override
    public IProduction[] getProductions(Integer limit, Integer offset) {
        return this.database.getProductions(limit, offset);
    }

    @Override
    public IProduction[] getProductionByCredit(ICredit credit) {
        return this.database.getProductionByCredit(credit);
    }
    @Override
    public IProduction getProduction(Integer id) {
        return this.database.getProduction(id);
    }

    @Override
    public IProduction[] getProductions(IProducer producer) {
        return this.database.getProductions(producer);
    }

    @Override
    public IProduction addProduction(IProduction production) {
        return this.database.addProduction(production);
    }

    @Override
    public void updateProduction(IProduction production) {
        this.database.updateProduction(production);
    }

    @Override
    public ICredit[] searchCredits(String word) {
        return this.database.searchCredits(word);
    }

    @Override
    public ICredit[] getCredits(Integer limit, Integer offset) {
        return this.database.getCredits(limit, offset);
    }

    @Override
    public ICredit getCredit(Integer id, CreditType type) {
        return this.database.getCredit(id,type);
    }

    @Override
    public ICredit addCredit(ICredit credit) {
        return this.database.addCredit(credit);
    }

    @Override
    public void updateCredit(ICredit credit) {
        this.database.updateCredit(credit);
    }

    @Override
    public boolean deleteCredit(Integer id) {
        return this.database.deleteCredit(id);
    }

    @Override
    public ICreditGroup[] searchCreditGroups(String word) {
        return this.database.searchCreditGroups(word);
    }

    @Override
    public ICreditGroup[] getCreditGroups(Integer limit, Integer offset) {
        return this.database.getCreditGroups(limit, offset);
    }

    @Override
    public ICreditGroup getCreditGroup(Integer id) {
        return this.database.getCreditGroup(id);
    }

    @Override
    public ICreditGroup addCreditGroup(ICreditGroup creditGroup) {
        return this.database.addCreditGroup(creditGroup);
    }

    @Override
    public void updateCreditGroup(ICreditGroup creditGroup) {
        this.database.updateCreditGroup(creditGroup);
    }

    @Override
    public IAccount[] searchAccounts(String word) {
        return this.database.searchAccounts(word);
    }

    @Override
    public IAccount[] getAccounts(Integer limit, Integer offset) {
        return this.database.getAccounts(limit, offset);
    }

    @Override
    public IAccount getAccount(Integer id) {
        return this.database.getAccount(id);
    }

    @Override
    public IAccount getAccount(String email) {
        return this.database.getAccount(email);
    }

    @Override
    public IAccount getAccount(String email, String hashedPassword) {
        return this.database.getAccount(email, hashedPassword);
    }

    @Override
    public IAccount addAccount(IAccount account, String hashedPassword) {
        return this.database.addAccount(account, hashedPassword);
    }

    @Override
    public void updateAccount(IAccount account) {
        this.database.updateAccount(account);
    }

    @Override
    public void updateAccount(IAccount account, String hashedPassword) {
        this.database.updateAccount(account, hashedPassword);
    }
}
