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
    public IProduction getProduction(String uuid) {
        return this.database.getProduction(uuid);
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
    public ICredit getCredit(String uuid) {
        return this.database.getCredit(uuid);
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
    public ICreditGroup getCreditGroup(String uuid) {
        return this.database.getCreditGroup(uuid);
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
    public IAccount getAccount(String uuid) {
        return this.database.getAccount(uuid);
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
    public IAccount login(IAccount account, String password) {
        return this.database.login(account, password);
    }
}
