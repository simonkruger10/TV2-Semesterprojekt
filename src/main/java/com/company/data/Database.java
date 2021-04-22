package com.company.data;

import com.company.common.IAccount;
import com.company.common.ICredit;
import com.company.common.ICreditGroup;
import com.company.common.IProduction;

public class Database implements DatabaseFacade {
    private static DatabaseFacade instance;
    private final DatabaseFacade database;

    private Database() {
        this.database = new JsonDatabase();
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
    public IProduction[] getProductions() {
        return this.database.getProductions();
    }

    @Override
    public IProduction getProduction(int id) {
        return this.database.getProduction(id);
    }

    @Override
    public void addProduction(IProduction productionInfo) {
        this.database.addProduction(productionInfo);
    }

    @Override
    public void updateProduction(IProduction productionInfo) {
        this.database.updateProduction(productionInfo);
    }

    @Override
    public ICredit[] getCredits() {
        return this.database.getCredits();
    }

    @Override
    public ICredit getCredit(int id) {
        return this.database.getCredit(id);
    }

    @Override
    public void addCredit(ICredit creditInfo) {
        this.database.addCredit(creditInfo);
    }

    @Override
    public void updateCredit(ICredit creditInfo) {
        this.database.updateCredit(creditInfo);
    }

    @Override
    public ICreditGroup[] getCreditGroups() {
        return this.database.getCreditGroups();
    }

    @Override
    public ICreditGroup getCreditGroup(int id) {
        return this.database.getCreditGroup(id);
    }

    @Override
    public void addCreditGroup(ICreditGroup creditGroupInfo) {
        this.database.addCreditGroup(creditGroupInfo);
    }

    @Override
    public void updateCreditGroup(ICreditGroup creditGroupInfo) {
        this.database.updateCreditGroup(creditGroupInfo);
    }

    @Override
    public IAccount[] getAccounts() {
        return this.database.getAccounts();
    }

    @Override
    public IAccount getAccount(int id) {
        return this.database.getAccount(id);
    }

    @Override
    public void addAccount(IAccount accountInfo) {
        this.database.addAccount(accountInfo);
    }

    @Override
    public void updateAccount(IAccount accountInfo) {
        this.database.updateAccount(accountInfo);
    }

    @Override
    public boolean login(String email, String password) {
        return this.database.login(email, password);
    }
}
