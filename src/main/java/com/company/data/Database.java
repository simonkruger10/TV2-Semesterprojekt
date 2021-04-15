package com.company.data;

import com.company.data.*;

public class Database implements DatabaseFacade {
    private Database database;
    private DatabaseController instance;

    private Database() {
        database = new JsonDatabase();
    }

    public Database getInstance() {
        if(this.instance == null) {
            this.instance = new DatabaseController();
        }
        return this.instance;
    }

    @Override
    public void getProductions() {

    }

    @Override
    public void addProduction(Production production) {

    }

    @Override
    public void getCredits(Production production) {

    }

    @Override
    public void addCredit(Production production) {

    }

    @Override
    public boolean validLogin(String username, String password) {
        return false;
    }
}
