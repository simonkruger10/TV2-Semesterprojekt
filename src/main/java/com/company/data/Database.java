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
    public IProducer[] getProducers() {
        return this.database.getProducers();
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
    public IProduction[] getProductions() {
        return this.database.getProductions();
    }

    @Override
    public IProduction getProduction(Integer id) {
        return this.database.getProduction(id);
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
    public ICredit[] getCredits() {
        return this.database.getCredits();
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
    public ICreditGroup[] getCreditGroups() {
        return this.database.getCreditGroups();
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
    public IAccount[] getAccounts() {
        return this.database.getAccounts();
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

    @Override
    public ICredit getCreditWithAllCreditGroups(ICredit credit) {
        return this.database.getCreditWithAllCreditGroups(credit);
    }
}
