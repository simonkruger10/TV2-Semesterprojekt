package com.company.data;

import com.company.data.*;

import com.company.crossInterfaces.AccountEntity;
import com.company.crossInterfaces.CreditEntity;
import com.company.crossInterfaces.CreditGroupEntity;
import com.company.crossInterfaces.ProductionEntity;

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
    public ProductionEntity[] getProductions() {
        return this.database.getProductions();
    }

    @Override
    public ProductionEntity getProduction(int id) {
        return this.database.getProduction(id);
    }

    @Override
    public void addProduction(ProductionEntity productionInfo) {
        this.database.addProduction(productionInfo);
    }

    @Override
    public void updateProduction(ProductionEntity productionInfo) {
        this.database.updateProduction(productionInfo);
    }

    @Override
    public CreditEntity[] getCredits() {
        return this.database.getCredits();
    }

    @Override
    public CreditEntity getCredit(int id) {
        return this.database.getCredit(id);
    }

    @Override
    public void addCredit(CreditEntity creditInfo) {
        this.database.addCredit(creditInfo);
    }

    @Override
    public void updateCredit(CreditEntity creditInfo) {
        this.database.updateCredit(creditInfo);
    }

    @Override
    public CreditGroupEntity[] getCreditGroups() {
        return this.database.getCreditGroups();
    }

    @Override
    public CreditGroupEntity getCreditGroup(int id) {
        return this.database.getCreditGroup(id);
    }

    @Override
    public void addCreditGroup(CreditGroupEntity creditGroupInfo) {
        this.database.addCreditGroup(creditGroupInfo);
    }

    @Override
    public void updateCreditGroup(CreditGroupEntity creditGroupInfo) {
        this.database.updateCreditGroup(creditGroupInfo);
    }

    @Override
    public AccountEntity[] getAccounts() {
        return this.database.getAccounts();
    }

    @Override
    public AccountEntity getAccount(int id) {
        return this.database.getAccount(id);
    }

    @Override
    public void addAccount(AccountEntity accountInfo) {
        this.database.addAccount(accountInfo);
    }

    @Override
    public void updateAccount(AccountEntity accountInfo) {
        this.database.updateAccount(accountInfo);
    }

    @Override
    public boolean login(String email, String password) {
        return this.database.login(email, password);
    }
}
